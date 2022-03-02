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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import useForm from '../hooks/useForm';
import CustomSelect from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const ModalAddObjectAction: React.FC<IProps> = ({
	apiURL,
	objectActionExecutors,
	objectActionTriggers,
	observer,
	onClose,
}) => {
	const initialValues: TInitialValues = {
		name: '',
		objectActionExecutor: {
			description: '',
			key: '',
			label: '',
		},
		objectActionTrigger: {
			description: '',
			key: '',
			label: '',
		},
		secret: '',
		url: '',
	};
	const [error, setError] = useState<string>('');

	const onSubmit = async ({
		name,
		objectActionExecutor,
		objectActionTrigger,
		secret,
		url,
	}: TInitialValues) => {
		const response = await fetch(apiURL, {
			body: JSON.stringify({
				active: true,
				name,
				objectActionExecutorKey: objectActionExecutor.key,
				objectActionTriggerKey: objectActionTrigger.key,
				parameters: {secret, url},
			}),
			headers,
			method: 'POST',
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			onClose();

			window.location.reload();
		}
		else {
			const {
				title = Liferay.Language.get('an-error-occurred'),
			} = (await response.json()) as {title?: string};

			setError(title);
		}
	};

	const validate = (values: TInitialValues) => {
		const errors: any = {};

		if (!values.name) {
			errors.name = Liferay.Language.get('required');
		}

		if (!values.objectActionTrigger.label) {
			errors.objectActionTrigger = Liferay.Language.get('required');
		}

		if (!values.objectActionExecutor.label) {
			errors.objectActionExecutor = Liferay.Language.get('required');
		}

		if (values.objectActionExecutor.label === 'Webhook' && !values.url) {
			errors.url = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-action')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.name}
						id="objectActionName"
						label={Liferay.Language.get('action-name')}
						name="name"
						onChange={handleChange}
						required
						value={values.name}
					/>

					<CustomSelect
						error={errors.objectActionTrigger}
						label={Liferay.Language.get('when[object]')}
						onChange={(objectActionTrigger: any) =>
							setValues({objectActionTrigger})
						}
						options={objectActionTriggers}
						required
						value={values.objectActionTrigger.label}
					/>

					<CustomSelect
						error={errors.objectActionExecutor}
						label={Liferay.Language.get('then[object]')}
						onChange={(objectActionExecutor: any) =>
							setValues({objectActionExecutor})
						}
						options={objectActionExecutors}
						required
						value={values.objectActionExecutor.label}
					/>

					{values.objectActionExecutor.label === 'Webhook' && (
						<>
							<Input
								error={errors.url}
								id="objectActionExecutorUrl"
								label={Liferay.Language.get('url')}
								name="url"
								onChange={handleChange}
								required
								value={values.url}
							/>

							<Input
								id="objectActionExecutorSecret"
								label={Liferay.Language.get('secret')}
								name="secret"
								onChange={handleChange}
								value={values.secret}
							/>
						</>
					)}
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group key={1} spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton displayType="primary" type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	objectActionExecutors: TObjectActionExecutor[];
	objectActionTriggers: TObjectActionTrigger[];
	observer: any;
	onClose: () => void;
}

type TObjectActionTrigger = {
	description: string;
	key: string;
	label: string;
};

type TObjectActionExecutor = {
	description: string;
	key: string;
	label: string;
};

type TObjectAction = {
	description: string;
	key: string;
	label: string;
};

type TInitialValues = {
	name: string;
	objectActionExecutor: TObjectAction;
	objectActionTrigger: TObjectAction;
	secret: string;
	url: string;
};

const ModalWithProvider: React.FC<IProps> = ({
	apiURL,
	objectActionExecutors,
	objectActionTriggers,
}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		Liferay.on('addObjectAction', () => setVisibleModal(true));

		return () => {
			Liferay.detach('addObjectAction', () => setVisibleModal(true));
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalAddObjectAction
					apiURL={apiURL}
					objectActionExecutors={objectActionExecutors}
					objectActionTriggers={objectActionTriggers}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
