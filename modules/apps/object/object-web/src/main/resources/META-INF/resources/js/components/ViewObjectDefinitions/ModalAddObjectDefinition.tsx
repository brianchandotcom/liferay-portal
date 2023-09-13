/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {
	API,
	BetaButton,
	FormError,
	Input,
	REQUIRED_MSG,
	Select,
	openToast,
	useForm,
} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';

import './ModalAddObjectDefinition.scss';
import {normalizeName} from './objectDefinitionUtil';

interface ModalAddObjectDefinitionProps {
	handleOnClose: () => void;
	objectDefinitionsStorageTypes: LabelValueObject[];
	objectFolderExternalReferenceCode?: string;
	onAfterSubmit?: (value: ObjectDefinition) => void;
	reload?: boolean;
}

type TInitialValues = {
	label: string;
	name?: string;
	pluralLabel: string;
	storageType: LabelValueObject;
};

export function ModalAddObjectDefinition({
	handleOnClose,
	objectDefinitionsStorageTypes,
	objectFolderExternalReferenceCode,
	onAfterSubmit,
	reload = true,
}: ModalAddObjectDefinitionProps) {
	const [error, setError] = useState<string>('');

	const {observer, onClose} = useModal({
		onClose: () => handleOnClose(),
	});

	const objectDefinitionStorageTypesSortedByLabel = [
		...objectDefinitionsStorageTypes,
	].sort((firstStorage, secondStorage) => {
		const firstLabel = firstStorage.label.toLowerCase();
		const secondLabel = secondStorage.label.toLowerCase();

		if (firstLabel < secondLabel) {
			return -1;
		}
		else if (firstLabel > secondLabel) {
			return 1;
		}
		else {
			return 0;
		}
	});

	const initialValues: TInitialValues = {
		label: '',
		name: undefined,
		pluralLabel: '',
		storageType: objectDefinitionStorageTypesSortedByLabel[0],
	};

	const onSubmit = async ({
		label,
		name,
		pluralLabel,
		storageType,
	}: TInitialValues) => {
		const objectDefinition: Partial<ObjectDefinition> = {
			label: {
				[defaultLanguageId]: label,
			},
			name: name || normalizeName(label),
			objectFields: [],
			pluralLabel: {
				[defaultLanguageId]: pluralLabel,
			},
			scope: 'company',
		};

		if (
			Liferay.FeatureFlags['LPS-148856'] &&
			objectFolderExternalReferenceCode
		) {
			objectDefinition.objectFolderExternalReferenceCode = objectFolderExternalReferenceCode;
		}

		if (
			Liferay.FeatureFlags['LPS-148856'] &&
			objectFolderExternalReferenceCode
		) {
			objectDefinition.objectFolderExternalReferenceCode = objectFolderExternalReferenceCode;
		}

		if (Liferay.FeatureFlags['LPS-135430']) {
			objectDefinition.storageType = storageType.value;
		}
		try {
			const newObjectDefinition = await API.postObjectDefinition(
				objectDefinition
			);

			onClose();

			openToast({
				message: sub(
					Liferay.Language.get('x-was-created-successfully'),
					`<strong>${label}</strong>`
				),
				type: 'success',
			});

			if (onAfterSubmit) {
				onAfterSubmit(newObjectDefinition);
			}

			if (reload) {
				setTimeout(() => window.location.reload(), 1000);
			}
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	const validate = (values: TInitialValues) => {
		const errors: FormError<TInitialValues> = {};

		if (!values.label) {
			errors.label = REQUIRED_MSG;
		}
		if (!(values.name ?? values.label)) {
			errors.name = REQUIRED_MSG;
		}
		if (!values.pluralLabel) {
			errors.pluralLabel = REQUIRED_MSG;
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	const selectedObjectDefinitionStorageTypes = (
		objectDefinitionStorageType: string
	) => {
		const selectedObjectDefinitionStorageType = objectDefinitionStorageTypesSortedByLabel.find(
			(currentObjectDefinitionStorageType) =>
				currentObjectDefinitionStorageType.value ===
				objectDefinitionStorageType
		);

		return selectedObjectDefinitionStorageType?.value;
	};

	return (
		<ClayModalProvider>
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
							error={errors.name}
							id="objectDefinitionName"
							label={Liferay.Language.get('object-name')}
							name="name"
							onChange={handleChange}
							required
							value={values.name ?? normalizeName(values.label)}
						/>

						{Liferay.FeatureFlags['LPS-135430'] && (
							<div className="lfr__object-web-modal-add-object-definition-storage-type">
								<Select
									label={Liferay.Language.get('storage-type')}
									name="storageType"
									onChange={({target: {value}}) => {
										setValues({
											...values,
											storageType: objectDefinitionStorageTypesSortedByLabel.find(
												(storageType) =>
													storageType.value === value
											),
										});
									}}
									options={objectDefinitionStorageTypesSortedByLabel.map(
										(objectDefinitionStorageType) => {
											return {
												key:
													objectDefinitionStorageType.value,
												label:
													objectDefinitionStorageType.label,
											};
										}
									)}
									tooltip={Liferay.Language.get(
										'object-definition-storage-type-tooltip'
									)}
									value={selectedObjectDefinitionStorageTypes(
										values.storageType.value
									)}
								/>

								<div className="lfr__object-web-modal-add-object-definition-storage-type-beta">
									<BetaButton />
								</div>
							</div>
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
		</ClayModalProvider>
	);
}
