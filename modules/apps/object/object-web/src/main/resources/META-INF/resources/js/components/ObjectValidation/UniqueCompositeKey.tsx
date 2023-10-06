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
	const [multipleSelectOptions, setMultipleSelectOptions] = useState<IItem[]>(
		[]
	);

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
			items: filteredCustomObjectFields.map(
				(filteredCustomObjectField) => ({
					...filteredCustomObjectField,
					checked: false,
				})
			),
			onSave: (selectedObjectFields: ObjectField[]) => {
				const newObjectValidationRuleSettings = selectedObjectFields.map(
					(selectedObjectField) => ({
						name: 'compositeKeyObjectFieldExternalReferenceCode',
						value: selectedObjectField.externalReferenceCode,
					})
				) as ObjectValidationRuleSetting[];

				setValues({
					objectValidationRuleSettings: newObjectValidationRuleSettings,
				});
			},
			selected: [],
			title: Liferay.Language.get('select-the-fields'),
		});
	};

	useEffect(() => {
		const newBuilderScreenItems = values?.objectValidationRuleSettings?.map(
			(objectValidationRuleSetting) => {
				const filteredCustomObjectFieldsInValidationRuleSetting = filteredCustomObjectFields.find(
					(filteredCustomObjectField) => {
						return (
							filteredCustomObjectField.externalReferenceCode ===
							objectValidationRuleSetting.value
						);
					}
				);

				return {
					fieldLabel: getLocalizableLabel(
						creationLanguageId,
						filteredCustomObjectFieldsInValidationRuleSetting?.label,
						filteredCustomObjectFieldsInValidationRuleSetting?.name
					),
					label:
						filteredCustomObjectFieldsInValidationRuleSetting?.label,
					objectFieldBusinessType:
						filteredCustomObjectFieldsInValidationRuleSetting?.businessType,
					objectFieldName:
						filteredCustomObjectFieldsInValidationRuleSetting?.name,
				};
			}
		) as TBuilderScreenItem[];

		setBuilderScreenItems(newBuilderScreenItems);

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
				<MultipleSelect
					error={errors.errorLabel}
					label={Liferay.Language.get('field')}
					options={multipleSelectOptions}
					setOptions={setMultipleSelectOptions}
				/>
			</ErrorMessage>
		</>
	);
}
