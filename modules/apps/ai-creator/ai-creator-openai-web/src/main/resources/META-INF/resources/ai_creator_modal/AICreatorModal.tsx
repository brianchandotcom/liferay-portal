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
import React, {FormEvent, useState} from 'react';

import {ErrorMessage} from './ErrorMessage';
import {FormContent} from './FormContent';
import {FormFooter} from './FormFooter';
import {LoadingMessage} from './LoadingMessage';
import {TextContent} from './TextContent';

interface Props {
	namespace: string;
}

type RequestStatus =
	| {type: 'idle'}
	| {type: 'loading'}
	| {errorMessage: string; type: 'error'}
	| {text: string; type: 'success'};

export default function AICreatorModal({namespace}: Props) {
	const closeModal = () => {
		alert('close');
	};

	const [status, setStatus] = useState<RequestStatus>({type: 'idle'});

	const onAdd = () => {
		alert(JSON.stringify(status, null, 2));
	};

	const onSubmit = (event: FormEvent) => {
		event.preventDefault();
		setStatus({type: 'loading'});

		setTimeout(() => {
			if (Math.random() < 0.5) {
				setStatus({text: 'Random result', type: 'success'});
			}
			else {
				setStatus({errorMessage: 'Random error', type: 'error'});
			}
		}, 3000);
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
					{status.type === 'error' ? <ErrorMessage /> : null}

					<Container className="c-p-4 flex-grow-1" fluid>
						<FormContent namespace={namespace} />

						{status.type === 'success' ? (
							<TextContent
								content={status.text}
								namespace={namespace}
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
