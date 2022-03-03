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

import {ERRORS} from '../utils/errors';
import {toCamelCase} from '../utils/string';
import Input from './Form/Input';
import ObjectFieldFormBase, {useObjectFieldForm} from './ObjectFieldFormBase';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId() as Liferay.Language.Locale;

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

function ModalAddObjectField({
	apiURL,
	objectFieldTypes,
	observer,
	onClose,
}: IModal) {
	const [error, setError] = useState<string>('');

	const initialValues: Partial<ObjectField> = {
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: null,
		listTypeDefinitionId: 0,
		required: false,
	};

	const onSubmit = async (field: ObjectField) => {
		const response = await fetch(apiURL, {
			body: JSON.stringify({
				...field,
				name:
					field.name ||
					toCamelCase(field.label[defaultLanguageId] as string),
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
			const {type} = (await response.json()) as any;
			const isMapped = Object.prototype.hasOwnProperty.call(ERRORS, type);
			const errorMessage = isMapped
				? ERRORS[type]
				: Liferay.Language.get('an-error-occurred');

			setError(errorMessage);
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectFieldForm({
		initialValues,
		onSubmit,
	});

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-field')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.label}
						id="objectFieldLabel"
						label={Liferay.Language.get('label')}
						name="label"
						onChange={({target: {value}}) => {
							setValues({label: {[defaultLanguageId]: value}});
						}}
						required
						value={values.label?.[defaultLanguageId]}
					/>

					<ObjectFieldFormBase
						errors={errors}
						handleChange={handleChange}
						objectField={values}
						objectFieldTypes={objectFieldTypes}
						setValues={setValues}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
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
}

export default function ModalWithProvider({apiURL, objectFieldTypes}: IProps) {
	const [isVisible, setVisibility] = useState<boolean>(false);
	const {observer, onClose} = useModal({onClose: () => setVisibility(false)});

	useEffect(() => {
		Liferay.on('addObjectField', () => setVisibility(true));

		return () => Liferay.detach('addObjectField');
	}, []);

	return (
		<ClayModalProvider>
			{isVisible && (
				<ModalAddObjectField
					apiURL={apiURL}
					objectFieldTypes={objectFieldTypes}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
}

interface IModal extends IProps {
	observer: any;
	onClose: () => void;
}

interface IProps {
	apiURL: string;
	objectFieldTypes: ObjectFieldType[];
}
