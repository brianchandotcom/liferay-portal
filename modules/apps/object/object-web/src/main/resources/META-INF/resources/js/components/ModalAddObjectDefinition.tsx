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
import {
	firstLetterUppercase,
	removeAllSpecialCharacters,
} from '../utils/string';
import Input from './form/Input';

declare global {
	const Liferay: any;
}

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const normalizeName: TNormalizeName = (str) => {
	const split = str.split(' ');
	const capitalizeFirstLetters = split.map((str: string) =>
		firstLetterUppercase(str)
	);
	const join = capitalizeFirstLetters.join('');

	return removeAllSpecialCharacters(join);
};

const ModalAddObjectDefinition: React.FC<IProps> = ({
	apiURL,
	observer,
	onClose,
}) => {
	const initialValues: TInitialValues = {
		label: '',
		name: undefined,
		pluralLabel: '',
	};
	const [error, setError] = useState<string>('');

	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	const onSubmit = async ({label, name, pluralLabel}: TInitialValues) => {
		const response = await Liferay.Util.fetch(apiURL, {
			body: JSON.stringify({
				label: {
					[defaultLanguageId]: label,
				},
				name: name || normalizeName(label),
				objectFields: [],
				pluralLabel: {
					[defaultLanguageId]: pluralLabel,
				},
				scope: 'company',
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

		if (!values.label) {
			errors.label = Liferay.Language.get('required');
		}

		if (!(values.name ?? values.label)) {
			errors.name = Liferay.Language.get('required');
		}

		if (!values.pluralLabel) {
			errors.pluralLabel = Liferay.Language.get('required');
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
					{Liferay.Language.get('new-custom-object')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.label}
						id="objectDefinitionLabel"
						label={Liferay.Language.get('label')}
						name="label"
						onChange={handleChange}
						required
						value={values.label}
					/>

					<Input
						error={errors.pluralLabel}
						id="objectDefinitionPluralLabel"
						label={Liferay.Language.get('plural-label')}
						name="pluralLabel"
						onChange={handleChange}
						required
						value={values.pluralLabel}
					/>

					<Input
						error={errors.name || errors.label}
						id="objectDefinitionName"
						label={Liferay.Language.get('object-name')}
						name="name"
						onChange={handleChange}
						required
						value={values.name ?? normalizeName(values.label)}
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
	label: string;
	name?: string;
	pluralLabel: string;
};

type TNormalizeName = (str: string) => string;

const ModalWithProvider: React.FC<IProps> = ({apiURL}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		Liferay.on('addObjectDefinition', () => setVisibleModal(true));

		return () => {
			Liferay.detach('addObjectDefinition');
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalAddObjectDefinition
					apiURL={apiURL}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
