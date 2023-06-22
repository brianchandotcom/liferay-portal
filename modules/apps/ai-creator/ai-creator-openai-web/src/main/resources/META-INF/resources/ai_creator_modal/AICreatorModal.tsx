/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayForm from '@clayui/form';
import {Container} from '@clayui/layout';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {FormEvent, useEffect, useRef, useState} from 'react';

import {ErrorMessage} from './ErrorMessage';
import {FormContent} from './FormContent';
import {FormFooter} from './FormFooter';
import {LoadingMessage} from './LoadingMessage';
import {TextContent} from './TextContent';

interface Props {
	getCompletionURL: string;
	portletNamespace: string;
}

type RequestStatus =
	| {type: 'idle'}
	| {type: 'loading'}
	| {errorMessage: string; showRetryMessage: boolean; type: 'error'};

export default function AICreatorModal({
	getCompletionURL,
	portletNamespace,
}: Props) {
	const closeModal = () => {
		const opener = Liferay.Util.getOpener();

		opener.Liferay.fire('closeModal');
	};

	const modalContentRef = useRef<HTMLDivElement>(null);
	const [status, setStatus] = useState<RequestStatus>({type: 'idle'});
	const [text, setText] = useState<string | null>(null);

	const onAdd = () => {
		if (text) {
			const opener = Liferay.Util.getOpener();

			opener.Liferay.fire('closeModal', {text});
		}
	};

	const onSubmit = (event: FormEvent) => {
		event.preventDefault();
		setStatus({type: 'loading'});

		const setErrorStatus = (
			errorMessage = Liferay.Language.get('an-unexpected-error-occurred'),
			showRetryMessage = false
		) => {
			setStatus({
				errorMessage,
				showRetryMessage,
				type: 'error',
			});
		};

		const formData = new FormData(event.target as HTMLFormElement);
		const url = new URL(window.location.href);

		formData.append(
			`${portletNamespace}languageId`,
			url.searchParams.get(`${portletNamespace}languageId`)!
		);

		fetch(getCompletionURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((json) => {
				if (json.error) {
					setErrorStatus(json.error.message, json.error.retry);
				}
				else if (json.completion?.content) {
					setText(json.completion.content);
					setStatus({type: 'idle'});
				}
				else {
					setErrorStatus();
				}
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'developm̀ent') {
					console.error(error);
				}

				setErrorStatus();
			});
	};

	useEffect(() => {
		modalContentRef.current?.focus();
	});

	return (
		<div className="h-100" ref={modalContentRef} tabIndex={-1}>
			{status.type === 'loading' ? <LoadingMessage /> : null}

			<ClayForm
				className={classNames('h-100', {
					'sr-only': status.type === 'loading',
				})}
				onSubmit={onSubmit}
			>
				<fieldset
					className="d-flex flex-column h-100"
					disabled={status.type === 'loading'}
				>
					{status.type === 'error' ? (
						<ErrorMessage
							message={status.errorMessage}
							showRetryMessage={status.showRetryMessage}
						/>
					) : null}

					<Container
						className="c-p-4 flex-grow-1 overflow-auto"
						fluid
					>
						<FormContent portletNamespace={portletNamespace} />

						{text ? (
							<TextContent
								content={text}
								portletNamespace={portletNamespace}
							/>
						) : null}

						<ClayForm.Group className="c-mb-0 d-none">
							<ClayLink href="#">
								{Liferay.Language.get(
									'learn-more-about-openai-integration'
								)}
							</ClayLink>
						</ClayForm.Group>
					</Container>

					<div className="d-flex flex-column flex-shrink-0">
						<FormFooter
							onAdd={onAdd}
							onClose={closeModal}
							showAddButton={Boolean(text)}
							showCreateButton={!text}
							showRetryButton={Boolean(text)}
						/>
					</div>
				</fieldset>
			</ClayForm>
		</div>
	);
}
