/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	BuilderScreen,
	Card,
	MultipleSelect,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import {TBuilderScreenItem} from '@liferay/object-js-components-web/src/main/resources/META-INF/resources/components/BuilderScreen/BuilderScreen';
import React, {useEffect, useState} from 'react';

import {ErrorMessage} from './ErrorMessage';
import {ObjectValidationErrors} from './useObjectValidationForm';

interface isMatchingObjectFieldObjectValidationRuleSettingProps {
	objectField: ObjectField;
	objectValidationRuleSetting: ObjectValidationRuleSetting;
	objectValidationRuleSettingNameMatches:
		| 'compositeKeyObjectFieldExternalReferenceCode'
		| 'outputObjectFieldExternalReferenceCode';
}

interface ModalSelectObjectFieldItem extends ObjectField {
	checked: boolean;
}

interface MultipleSelectOption {
	checked: boolean;
	externalReferenceCode: string;
	label: string;
}

export interface UniqueCompositeKeyProps {
	creationLanguageId: Liferay.Language.Locale;
	customObjectFields: ObjectField[];
	disabled: boolean;
	errors: ObjectValidationErrors;
	setShowUniqueCompositeKeyAlert: (value: boolean) => void;
	setValues: (values: Partial<ObjectValidation>) => void;
	showUniqueCompositeKeyAlert: boolean;
	values: Partial<ObjectValidation>;
}

const isMatchingObjectFieldObjectValidationRuleSetting = ({
	objectField,
	objectValidationRuleSetting,
	objectValidationRuleSettingNameMatches,
}: isMatchingObjectFieldObjectValidationRuleSettingProps) => {
	return (
		objectField.externalReferenceCode ===
			objectValidationRuleSetting.value &&
		objectValidationRuleSetting.name ===
			objectValidationRuleSettingNameMatches
	);
};

export function UniqueCompositeKey({
	creationLanguageId,
	customObjectFields,
	disabled,
	errors,
	setShowUniqueCompositeKeyAlert,
	setValues,
	showUniqueCompositeKeyAlert,
	values,
}: UniqueCompositeKeyProps) {
	const [builderScreenItems, setBuilderScreenItems] = useState<
		TBuilderScreenItem[]
	>([]);
	const [
		modalSelectObjectFieldsItems,
		setModalSelectObjectFieldsItems,
	] = useState<ModalSelectObjectFieldItem[]>([]);
	const [multipleSelectOptions, setMultipleSelectOptions] = useState<
		MultipleSelectOption[]
	>([]);
	const filteredCustomObjectFields = customObjectFields.filter(
		(customObjectField) =>
			customObjectField.businessType === 'Integer' ||
			'Picklist' ||
			'Relationship' ||
			'Text'
	);

	const handleAddObjectFields = () => {
		const parentWindow = Liferay.Util.getOpener();

		parentWindow.Liferay.fire('openModalSelectObjectFields', {
			getName: ({label, name}: ObjectField) =>
				getLocalizableLabel(creationLanguageId, label, name),
			header: Liferay.Language.get('add-fields-to-unique-composite-key'),
			items: modalSelectObjectFieldsItems,
			onSave: (selectedObjectFields: ObjectField[]) => {
				const objectValidationRuleSetting = values.objectValidationRuleSettings?.filter(
					(objectValidationRuleSetting) =>
						selectedObjectFields.some((selectedObjectField) => {
							return (
								selectedObjectField.externalReferenceCode ===
									objectValidationRuleSetting.value &&
								objectValidationRuleSetting.name ===
									'outputObjectFieldExternalReferenceCode'
							);
						})
				);
				selectedObjectFields.map((selectedObjectField) =>
					objectValidationRuleSetting?.push({
						name: 'compositeKeyObjectFieldExternalReferenceCode',
						value: selectedObjectField.externalReferenceCode,
					})
				);

				setValues({
					objectValidationRuleSettings: objectValidationRuleSetting,
				});
			},
			selected: modalSelectObjectFieldsItems.filter(
				(modalSelectObjectFieldsItem) =>
					modalSelectObjectFieldsItem.checked
			),
			title: Liferay.Language.get('select-the-fields'),
		});
	};

	useEffect(() => {
		if (!values.objectValidationRuleSettings) {
			return;
		}

		const newBuilderScreenItems: TBuilderScreenItem[] = [];
		const newModalSelectObjectFieldsItems: ModalSelectObjectFieldItem[] = [];
		const newMultipleSelectOptions: MultipleSelectOption[] = [];

		values.objectValidationRuleSettings.forEach(
			(objectValidationRuleSetting) => {
				const filteredObjectFieldObjectValidationRuleSetting = filteredCustomObjectFields.find(
					(filteredCustomObjectField) =>
						isMatchingObjectFieldObjectValidationRuleSetting({
							objectField: filteredCustomObjectField,
							objectValidationRuleSetting,
							objectValidationRuleSettingNameMatches:
								'compositeKeyObjectFieldExternalReferenceCode',
						})
				);

				if (filteredObjectFieldObjectValidationRuleSetting) {
					const label = getLocalizableLabel(
						creationLanguageId,
						filteredObjectFieldObjectValidationRuleSetting.label,
						filteredObjectFieldObjectValidationRuleSetting.name
					);

					newBuilderScreenItems.push({
						externalReferenceCode:
							filteredObjectFieldObjectValidationRuleSetting.externalReferenceCode,
						fieldLabel: label,
						label:
							filteredObjectFieldObjectValidationRuleSetting.label,
						objectFieldBusinessType:
							filteredObjectFieldObjectValidationRuleSetting.businessType,
						objectFieldName:
							filteredObjectFieldObjectValidationRuleSetting.name,
					});

					newMultipleSelectOptions.push({
						checked: !!values.objectValidationRuleSettings?.find(
							(objectValidationRuleSetting) =>
								isMatchingObjectFieldObjectValidationRuleSetting(
									{
										objectField: filteredObjectFieldObjectValidationRuleSetting,
										objectValidationRuleSetting,
										objectValidationRuleSettingNameMatches:
											'outputObjectFieldExternalReferenceCode',
									}
								)
						),
						externalReferenceCode:
							filteredObjectFieldObjectValidationRuleSetting.externalReferenceCode,
						label,
					});
				}
			}
		);

		filteredCustomObjectFields.forEach((filteredCustomObjectField) =>
			newModalSelectObjectFieldsItems.push({
				...filteredCustomObjectField,
				checked: !!values.objectValidationRuleSettings?.find(
					(objectValidationRuleSetting) =>
						isMatchingObjectFieldObjectValidationRuleSetting({
							objectField: filteredCustomObjectField,
							objectValidationRuleSetting,
							objectValidationRuleSettingNameMatches:
								'compositeKeyObjectFieldExternalReferenceCode',
						})
				),
			})
		);

		setBuilderScreenItems(newBuilderScreenItems);
		setModalSelectObjectFieldsItems(newModalSelectObjectFieldsItems);
		setMultipleSelectOptions(newMultipleSelectOptions);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.objectValidationRuleSettings]);

	return (
		<>
			<Card
				alert={{
					content: Liferay.Language.get(
						'a-unique-composite-key-validation-checks-if-the-combination-of-two-or-more-fields-can-be-used-to-uniquely-identify-each-entry'
					),
					otherProps: {
						displayType: 'info',
						title: Liferay.Language.get('info'),
						variant: 'stripe',
					},
					setShowAlert: setShowUniqueCompositeKeyAlert,
					showAlert: showUniqueCompositeKeyAlert,
				}}
				title={Liferay.Language.get('fields')}
			>
				<BuilderScreen
					builderScreenItems={builderScreenItems}
					defaultSort={false}
					emptyState={{
						buttonText: Liferay.Language.get('add-fields'),
						description: Liferay.Language.get(
							'add-a-minimum-of-two-fields-to-create-composite-unique-keys'
						),
						title: Liferay.Language.get('no-fields-added-yet'),
					}}
					filter={true}
					firstColumnHeader={Liferay.Language.get('label')}
					onDeleteColumn={() => {}}
					openModal={handleAddObjectFields}
					secondColumnHeader={Liferay.Language.get('type')}
				/>
			</Card>

			<ErrorMessage
				disabled={disabled}
				errors={errors}
				setValues={setValues}
				values={values}
			>
				<MultipleSelect<MultipleSelectOption>
					disabled={!builderScreenItems.length}
					error={errors.errorLabel}
					label={Liferay.Language.get('field')}
					options={multipleSelectOptions ?? []}
					setOptions={(newOutputObjectFieldOptions) => {
						const objectValidationRuleSettings = values.objectValidationRuleSettings?.filter(
							(objectValidationRuleSetting) =>
								objectValidationRuleSetting.name !==
								'outputObjectFieldExternalReferenceCode'
						);

						newOutputObjectFieldOptions.forEach(
							(newOutputObjectFieldOption) => {
								if (newOutputObjectFieldOption.checked) {
									objectValidationRuleSettings?.push({
										name:
											'outputObjectFieldExternalReferenceCode',
										value:
											newOutputObjectFieldOption.externalReferenceCode,
									});
								}
							}
						);

						setValues({objectValidationRuleSettings});
						setMultipleSelectOptions(newOutputObjectFieldOptions);
					}}
				/>
			</ErrorMessage>
		</>
	);
}
