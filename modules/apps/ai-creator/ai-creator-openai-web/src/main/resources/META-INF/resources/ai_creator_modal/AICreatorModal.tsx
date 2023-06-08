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
import React, {FormEvent, useState} from 'react';

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
	| {errorMessage: string; type: 'error'}
	| {text: string; type: 'success'};

export default function AICreatorModal({
	getCompletionURL,
	portletNamespace,
}: Props) {
	const closeModal = () => {
		const opener = Liferay.Util.getOpener();

		opener.Liferay.fire('closeModal');
	};

	const [status, setStatus] = useState<RequestStatus>({type: 'idle'});

	const onAdd = () => {
		if (status.type === 'success') {
			const opener = Liferay.Util.getOpener();

			opener.Liferay.fire('closeModal', {text: status.text});
		}
	};

	const onSubmit = (event: FormEvent) => {
		event.preventDefault();
		setStatus({type: 'loading'});

		const setErrorStatus = (
			errorMessage = Liferay.Language.get('an-unexpected-error-occurred')
		) => {
			setStatus({
				errorMessage,
				type: 'error',
			});
		};

		fetch(getCompletionURL, {
			body: new FormData(event.target as HTMLFormElement),
			method: 'POST',
		})
			.then((response) => response.json())
			.then((json) => {
				if (json.error) {
					setErrorStatus(json.error.message);
				}
				else if (json.completion?.content) {
					setStatus({
						text: json.completion.content,
						type: 'success',
					});
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

	return (
		<>
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
						<ErrorMessage message={status.errorMessage} />
					) : null}

					<Container className="c-p-4 flex-grow-1" fluid>
						<FormContent portletNamespace={portletNamespace} />

						{status.type === 'success' ? (
							<TextContent
								content={status.text}
								portletNamespace={portletNamespace}
							/>
						) : null}

						<ClayForm.Group>
							<ClayLink href="#">
								{Liferay.Language.get(
									'learn-more-about-openai-integration'
								)}
							</ClayLink>
						</ClayForm.Group>
					</Container>

					<FormFooter
						onAdd={onAdd}
						onClose={closeModal}
						showAddButton={
							status.type === 'success' && Boolean(status.text)
						}
						showCreateButton={
							status.type === 'idle' || status.type === 'error'
						}
						showRetryButton={status.type === 'success'}
					/>
				</fieldset>
			</ClayForm>
		</>
	);
}
