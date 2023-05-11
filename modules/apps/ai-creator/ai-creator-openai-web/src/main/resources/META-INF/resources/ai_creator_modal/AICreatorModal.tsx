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
import ClayLink from '@clayui/link';
import ClayModal, {useModal} from '@clayui/modal';
import React, {FormEvent, useState} from 'react';

import {ErrorMessage} from './ErrorMessage';
import {FormContent} from './FormContent';
import {FormFooter} from './FormFooter';
import {LoadingMessage} from './LoadingMessage';
import {TextContent} from './TextContent';

interface Props {
	namespace: string;
	onClose: () => void;
}

type RequestStatus =
	| {type: 'idle'}
	| {type: 'loading'}
	| {errorMessage: string; type: 'error'}
	| {text: string; type: 'success'};

export function AICreatorModal({namespace, onClose}: Props) {
	const {observer, onClose: closeModal} = useModal({
		defaultOpen: true,
		onClose,
	});

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
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>
				{Liferay.Language.get('ai-creator')}
			</ClayModal.Header>

			{status.type === 'loading' ? <LoadingMessage /> : null}

			<ClayForm onSubmit={onSubmit}>
				<fieldset
					className={status.type === 'loading' ? 'sr-only' : ''}
					disabled={status.type === 'loading'}
				>
					{status.type === 'error' ? <ErrorMessage /> : null}

					<ClayModal.Body>
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
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<FormFooter
								onAdd={onAdd}
								onClose={closeModal}
								showAddButton={
									status.type === 'success' &&
									Boolean(status.text)
								}
								showCreateButton={
									status.type === 'idle' ||
									status.type === 'error'
								}
								showRetryButton={status.type === 'success'}
							/>
						}
					/>
				</fieldset>
			</ClayForm>
		</ClayModal>
	);
}
