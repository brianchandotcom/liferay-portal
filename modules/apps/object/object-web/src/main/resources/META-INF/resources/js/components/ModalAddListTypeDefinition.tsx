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
import Input from './form/Input';
import {TName} from './layout/types';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ModalAddListTypeDefinition: React.FC<IProps> = ({
	apiURL,
	observer,
	onClose,
}) => {
	const initialValues: TInitialValues = {
		name_i18n: {[defaultLanguageId]: ''},
	};
	const [error, setError] = useState<string>('');

	const onSubmit = async ({name_i18n}: TInitialValues) => {
		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				name_i18n,
			}),
			headers: new Headers({
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			}),
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

		if (!values.name_i18n[defaultLanguageId]) {
			errors.name_i18n = Liferay.Language.get('required');
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
					{Liferay.Language.get('new-picklist')}
				</ClayModal.Header>
				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.name_i18n}
						id="listTypeDefinitionName"
						label={Liferay.Language.get('name')}
						name="name_i18n"
						onChange={({target: {value}}: any) => {
							handleChange({
								target: {
									name: 'name_i18n',
									value: {
										[defaultLanguageId]: value,
									},
								},
							} as any);
						}}
						required
						value={values.name_i18n[defaultLanguageId]}
					/>
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
	observer: any;
	onClose: () => void;
}

type TInitialValues = {
	name_i18n: TName;
};

const ModalWithProvider: React.FC<IProps> = ({apiURL}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		Liferay.on('addListTypeDefinition', () => setVisibleModal(true));

		return () => {
			Liferay.detach('addListTypeDefinition', () =>
				setVisibleModal(true)
			);
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalAddListTypeDefinition
					apiURL={apiURL}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
