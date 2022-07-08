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
import {
	Input,
	InputLocalized,
	useForm,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {HEADERS} from '../utils/constants';
import {toCamelCase} from '../utils/string';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ModalAddListTypeEntry: React.FC<IProps> = ({
	apiURL,
	observer,
	onClose,
}) => {
	const [error, setError] = useState<string>('');
	const [selectedLocale, setSelectedLocale] = useState({
		label: defaultLanguageId,
		symbol: defaultLanguageId.replace('_', '-').toLowerCase(),
	});
	const initialValues: TInitialValues = {
		key: undefined,
		name_i18n: {[defaultLanguageId]: ''},
	};

	const onSubmit = async ({key, name_i18n}: TInitialValues) => {
		const response = await fetch(apiURL, {
			body: JSON.stringify({
				key: key || toCamelCase(name_i18n[selectedLocale.label]),
				name_i18n,
			}),
			headers: HEADERS,
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
			} = (await response.json()) as {title: string};

			setError(title);
		}
	};

	const validate = (values: TInitialValues) => {
		const errors: any = {};

		if (!values.name_i18n[selectedLocale.label]) {
			errors.name_i18n = Liferay.Language.get('required');
		}

		if (!(values.key ?? values.name_i18n[selectedLocale.label])) {
			errors.key = Liferay.Language.get('required');
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
					{Liferay.Language.get('new-item')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<InputLocalized
						error={errors.name_i18n}
						id="locale"
						label={Liferay.Language.get('name')}
						onChange={(value, locale) => {
							setValues({name_i18n: value});
							setSelectedLocale(locale);
						}}
						required
						translations={values.name_i18n}
					/>

					<Input
						error={errors.key || errors.name_i18n}
						id="listTypeEntryKey"
						label={Liferay.Language.get('key')}
						name="key"
						onChange={handleChange}
						required
						value={
							values.key ??
							toCamelCase(
								values.name_i18n[selectedLocale.label] ?? ''
							)
						}
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

type TTranslations = {
	[key: string]: string;
};

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	observer: any;
	onClose: () => void;
}

type TInitialValues = {
	key?: string;
	name_i18n: TTranslations;
};

const ModalWithProvider: React.FC<IProps> = ({apiURL}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		Liferay.on('addListTypeEntry', () => setVisibleModal(true));

		return () => {
			Liferay.detach('addListTypeEntry');
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalAddListTypeEntry
					apiURL={apiURL}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
