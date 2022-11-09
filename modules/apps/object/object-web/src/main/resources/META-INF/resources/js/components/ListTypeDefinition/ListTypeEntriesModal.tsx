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
import ClayModal, {useModal} from '@clayui/modal';
import {
	API,
	Input,
	InputLocalized,
	invalidateRequired,
} from '@liferay/object-js-components-web';
import {openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {specialCharactersInString, toCamelCase} from '../../utils/string';
import {ObjectValidationErrors} from './ListTypeFormBase';
import {fixLocaleKeys} from './utils';

const REQUIRED_MSG = Liferay.Language.get('required');
const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
export interface IModalState extends Partial<PickListItem> {
	header?: string;
	itemId?: number;
	itemKey?: string;
	modalType?: 'add' | 'edit';
	pickListId?: number;
	readOnly?: boolean;
	reloadIframeWindow?: () => void;
}

function ListTypeEntriesModal() {
	const [
		{
			header,
			itemId,
			itemKey,
			modalType,
			name_i18n,
			pickListId,
			readOnly,
			reloadIframeWindow,
		},
		setState,
	] = useState<IModalState>({});

	const [keyChanged, setKeyChanged] = useState(false);
	const [APIError, setAPIError] = useState<string>('');

	const handleKeyChange = (value: string) => {
		if (keyChanged === false) {
			setKeyChanged(true);
		}
		setState((previousValues) => ({
			...previousValues,
			itemKey: toCamelCase(value),
		}));
	};

	const handleNameChange = (name_i18n: LocalizedValue<string>) => {
		setState((previousValues) => ({
			...previousValues,
			...(modalType !== 'edit' &&
				keyChanged === false &&
				name_i18n?.[defaultLanguageId] && {
					itemKey: toCamelCase(name_i18n[defaultLanguageId]!),
				}),
			name_i18n: {...name_i18n},
		}));
	};

	const [errors, setErrors] = useState<{
		key?: string;
		name?: string;
		name_i18n?: string;
	}>({
		key: '',
		name: '',
		name_i18n: '',
	});

	const resetModal = () => {
		setAPIError('');
		setState({});
		setErrors({});
		setKeyChanged(false);
	};

	const {observer, onClose} = useModal({
		onClose: resetModal,
	});

	useEffect(() => {
		const openModal = (modalProps: Partial<IModalState>) => {
			const newModalProps = {...modalProps};

			if (newModalProps.name_i18n) {
				newModalProps.name_i18n = fixLocaleKeys(
					newModalProps.name_i18n
				);
			}
			setState(newModalProps);
		};

		Liferay.on('openListTypeEntriesModal', openModal);

		return () =>
			Liferay.detach('openListTypeEntriesModal', openModal as () => void);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (APIError) {
			openToast({
				message: APIError,
				type: 'danger',
			});
		}
		setAPIError('');
	}, [APIError]);

	const validate = (entry: Partial<PickListItem>): ObjectValidationErrors => {
		const errors: ObjectValidationErrors = {};
		const name_i18n = entry.name_i18n?.[defaultLanguageId];
		const key = entry.key;

		if (invalidateRequired(name_i18n)) {
			errors.name_i18n = REQUIRED_MSG;
		}

		if (invalidateRequired(key)) {
			errors.name = REQUIRED_MSG;
		}

		if (specialCharactersInString(key as string)) {
			errors.key = Liferay.Language.get(
				'key-must-only-contain-letters-and-digits'
			);
		}

		return errors;
	};

	const handleSave = async () => {
		const errors: ObjectValidationErrors = validate({
			key: itemKey,
			name_i18n,
		});

		if (Object.keys(errors).length) {
			setErrors(errors);
		}
		else {
			setErrors({});
			try {
				if (modalType === 'add') {
					await API.addPickListItem({
						id: pickListId,
						key: itemKey,
						name_i18n,
					});
					openToast({
						message: Liferay.Language.get(
							'the-picklist-item-was-created-successfully'
						),
						type: 'success',
					});
				}
				else if (modalType === 'edit') {
					await API.updatePickListItem({id: itemId, name_i18n});
					openToast({
						message: Liferay.Language.get(
							'the-picklist-item-was-updated-successfully'
						),
						type: 'success',
					});
				}
				onClose();
				if (reloadIframeWindow) {
					reloadIframeWindow();
				}
			}
			catch (error) {
				setAPIError((error as Error).message);
			}
		}
	};

	return header ? (
		<ClayModal observer={observer}>
			<ClayModal.Header>{header}</ClayModal.Header>

			<ClayModal.Body>
				{errors.key && (
					<ClayAlert displayType="danger">{errors.key}</ClayAlert>
				)}

				<InputLocalized
					disabled={readOnly}
					error={errors.name_i18n}
					id="locale"
					label={Liferay.Language.get('name')}
					onChange={(name_i18n) => handleNameChange(name_i18n)}
					required
					translations={name_i18n ?? {[defaultLanguageId]: ''}}
				/>

				<Input
					disabled={modalType === 'edit'}
					error={errors.name}
					label={Liferay.Language.get('key')}
					name="name"
					onChange={({target}) => handleKeyChange(target.value)}
					required
					value={itemKey ?? ''}
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

						<ClayButton
							disabled={readOnly}
							displayType="primary"
							onClick={handleSave}
							type="submit"
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	) : null;
}

export default ListTypeEntriesModal;
