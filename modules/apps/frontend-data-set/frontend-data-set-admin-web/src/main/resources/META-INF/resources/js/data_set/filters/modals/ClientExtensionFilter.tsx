/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import React, {useState} from 'react';

import RequiredMark from '../../../components/RequiredMark';
import ValidationFeedback from '../../../components/ValidationFeedback';
import {IClientExtensionFilter, IField} from '../../../utils/types';
import {IFilterModal} from '../Filters';
import Configuration from './Configuration';
import Footer from './Footer';

const CLIENT_EXTENSION_ERC_OBJECT_FIELD_NAME = Liferay.FeatureFlags['LPD-15729']
	? 'clientExtensionERC'
	: 'fdsFilterClientExtensionERC';

function Header() {
	return <>{Liferay.Language.get('new-client-extension-filter')}</>;
}

function Body({
	closeModal,
	fieldNames,
	fields,
	filter,
	filterClientExtensions,
	namespace,
	onSave,
}: IFilterModal) {
	const [clientExtensionValidationError, setClientExtensionValidationError] =
		useState<boolean>(false);
	const [fieldInUseValidationError, setFieldInUseValidationError] =
		useState<boolean>(false);
	const [fieldValidationError, setFieldValidationError] =
		useState<boolean>(false);
	const [labelValidationError, setLabelValidationError] =
		useState<boolean>(false);

	const [saveButtonDisabled, setSaveButtonDisabled] =
		useState<boolean>(false);
	const [selectedClientExtension, setSelectedClientExtension] = useState<
		IClientExtensionRenderer | undefined
	>(
		filter
			? filterClientExtensions.find(
					(clientExtensionRenderer: IClientExtensionRenderer) =>
						clientExtensionRenderer.externalReferenceCode ===
						(filter as IClientExtensionFilter).clientExtensionERC
				)
			: undefined
	);
	const [i18nFilterLabels, setI18nFilterLabels] = useState(
		filter?.label_i18n ?? {}
	);
	const inUseFields: (string | undefined)[] = fields.map((item) =>
		fieldNames?.includes(item.name) ? item.name : undefined
	);
	const [selectedField, setSelectedField] = useState<IField | undefined>(
		fields.find((item) => item.name === filter?.fieldName)
	);
	const clientExtensionFormElementId = `${namespace}clientExtensionERC`;

	const isi18nFilterLabelsValid = (
		i18nFilterLabels: Partial<Liferay.Language.FullyLocalizedValue<string>>
	) => {
		let isValid = true;

		if (!i18nFilterLabels || !Object.values(i18nFilterLabels).length) {
			isValid = false;
		}

		Object.values(i18nFilterLabels).forEach((value) => {
			if (!value) {
				isValid = false;
			}
		});

		return isValid;
	};

	const validate = () => {
		let isValid = true;

		if (Liferay.FeatureFlags['LPD-10754']) {
			const isLabelValid = isi18nFilterLabelsValid(i18nFilterLabels);
			setLabelValidationError(!isLabelValid);

			isValid = isLabelValid;
		}

		if (!selectedField) {
			setFieldValidationError(true);

			isValid = false;
		}

		if (selectedField && !filter) {
			if (inUseFields.includes(selectedField?.name)) {
				setFieldInUseValidationError(true);

				isValid = false;
			}
		}

		if (!selectedClientExtension) {
			setClientExtensionValidationError(true);

			isValid = false;
		}

		return isValid;
	};

	const saveClientExtensionFilter = () => {
		setSaveButtonDisabled(true);

		const success = validate();

		if (success) {
			const formData = {
				[CLIENT_EXTENSION_ERC_OBJECT_FIELD_NAME]:
					selectedClientExtension?.externalReferenceCode,
				fieldName: selectedField?.name,
				label_i18n: i18nFilterLabels,
			};

			onSave(formData);
		}
		else {
			setSaveButtonDisabled(false);
		}
	};

	if (!filterClientExtensions.length) {
		return (
			<ClayAlert displayType="info" title="Info">
				{Liferay.Language.get(
					'no-frontend-data-set-filter-client-extensions-are-available.-add-a-client-extension-first-in-order-to-create-a-filter'
				)}
			</ClayAlert>
		);
	}

	return (
		<>
			<ClayModal.Body>
				<Configuration
					fieldInUseValidationError={fieldInUseValidationError}
					fieldNames={fieldNames}
					fieldValidationError={fieldValidationError}
					fields={fields}
					filter={filter}
					labelValidationError={labelValidationError}
					namespace={namespace}
					onBlur={() => {
						setLabelValidationError(
							!isi18nFilterLabelsValid(i18nFilterLabels)
						);
					}}
					onChangeField={(newValue) => {
						setSelectedField(newValue);
						setFieldValidationError(!newValue);
						setFieldInUseValidationError(
							newValue
								? inUseFields.includes(newValue.name)
								: false
						);
					}}
					onChangeLabel={(newValue) => {
						setI18nFilterLabels(newValue);
					}}
				/>

				{!fieldInUseValidationError && (
					<ClayForm.Group
						className={classNames('form-group-autofit', {
							'has-error': clientExtensionValidationError,
						})}
					>
						<div className={classNames('form-group-item')}>
							<label htmlFor={clientExtensionFormElementId}>
								{Liferay.Language.get('client-extension')}

								<RequiredMark />
							</label>

							<ClayDropDown
								closeOnClick
								menuElementAttrs={{
									className:
										'fds-cell-renderers-dropdown-menu',
								}}
								trigger={
									<ClayButton
										aria-labelledby={`${namespace}cellRenderersLabel`}
										className="form-control form-control-select form-control-select-secondary"
										displayType="secondary"
										name={clientExtensionFormElementId}
									>
										{selectedClientExtension
											? selectedClientExtension.name
											: Liferay.Language.get('select')}
									</ClayButton>
								}
							>
								<ClayDropDown.ItemList
									items={filterClientExtensions}
									role="listbox"
								>
									{filterClientExtensions.map(
										(
											filterClientExtension: IClientExtensionRenderer
										) => (
											<ClayDropDown.Item
												className="align-items-center d-flex justify-content-between"
												key={filterClientExtension.name}
												onClick={() => {
													setSelectedClientExtension(
														filterClientExtension
													);
													setClientExtensionValidationError(
														!filterClientExtension
													);
												}}
												roleItem="option"
											>
												{filterClientExtension.name}
											</ClayDropDown.Item>
										)
									)}
								</ClayDropDown.ItemList>
							</ClayDropDown>

							{clientExtensionValidationError && (
								<ValidationFeedback />
							)}
						</div>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<Footer
				closeModal={closeModal}
				onSave={saveClientExtensionFilter}
				saveButtonDisabled={saveButtonDisabled}
			/>
		</>
	);
}

export default {
	Body,
	Header,
};
