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
import React, {useEffect, useState} from 'react';

import useForm from '../hooks/useForm';
import CustomSelect from './form/CustomSelect';
import Input from './form/Input';

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
	};
	const [error, setError] = useState<string>('');

	const onSubmit = async ({
		name,
		objectActionExecutor,
		objectActionTrigger,
	}: TInitialValues) => {
		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				active: false,
				name,
				objectActionExecutorKey: objectActionExecutor.key,
				objectActionTriggerKey: objectActionTrigger.key,
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
			} = await response.json();

			setError(title);
		}
	};

	const validate = (values: TInitialValues) => {
		const errors: any = {};

		if (!values.name) {
			errors.name = Liferay.Language.get('required');
		}

		if (!values.objectActionTrigger.label) {
			errors.trigger = Liferay.Language.get('required');
		}

		if (!values.objectActionExecutor.label) {
			errors.executor = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, values} = useForm({
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
						error={errors.trigger}
						label={Liferay.Language.get('when')}
						onChange={(objectActionTrigger: any) => {
							handleChange({
								target: {
									name: 'objectActionTrigger',
									value: objectActionTrigger,
								},
							} as any);
						}}
						options={objectActionTriggers}
						required
						value={values.objectActionTrigger.label}
					>
						{({description, label}) => (
							<>
								<div>{label}</div>
								<span className="text-small">
									{description}
								</span>
							</>
						)}
					</CustomSelect>

					<CustomSelect
						error={errors.executor}
						label={Liferay.Language.get('then')}
						onChange={(objectActionExecutor: any) => {
							handleChange({
								target: {
									name: 'objectActionExecutor',
									value: objectActionExecutor,
								},
							} as any);
						}}
						options={objectActionExecutors}
						required
						value={values.objectActionExecutor.label}
					>
						{({description, label}) => (
							<>
								<div>{label}</div>
								<span className="text-small">
									{description}
								</span>
							</>
						)}
					</CustomSelect>
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
	key: string;
	label: string;
	description: string;
};

type TInitialValues = {
	name: string;
	objectActionExecutor: TObjectAction;
	objectActionTrigger: TObjectAction;
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
