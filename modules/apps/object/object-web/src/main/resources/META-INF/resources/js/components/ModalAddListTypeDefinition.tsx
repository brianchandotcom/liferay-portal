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
import {Observer} from '@clayui/modal/lib/types';
import {
	API,
	FormError,
	Input,
	useForm,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {TName} from './Layout/types';

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

	const onSubmit = async (values: TInitialValues) => {
		try {
			await API.save(apiURL, values, 'POST');

			onClose();
			window.location.reload();
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	const validate = (values: TInitialValues) => {
		const errors: FormError<TInitialValues> = {};

		if (!values.name_i18n[defaultLanguageId]) {
			errors.name_i18n = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleSubmit, setValues, values} = useForm({
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
						onChange={({target: {value}}) =>
							setValues({
								name_i18n: {
									[defaultLanguageId]: value,
								},
							})
						}
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
	observer: Observer;
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
