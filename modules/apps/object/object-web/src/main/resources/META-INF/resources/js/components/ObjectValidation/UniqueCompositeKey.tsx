/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	BuilderScreen,
	Card,
	MultiSelectItem,
	MultipleSelect,
	TBuilderScreenItem,
	stringUtils,
} from '@liferay/object-js-components-web';
import {createResourceURL, sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {Alert} from '../ModalSelectObjectFields';
import {ErrorMessage} from './ErrorMessage';
import {ObjectValidationErrors} from './useObjectValidationForm';

import './UniqueCompositeKey.scss';

import {MultiSelectItemChild} from '@liferay/object-js-components-web/src/main/resources/META-INF/resources/components/Select/MultipleSelect';

interface isMatchingObjectFieldObjectValidationRuleSettingProps {
	objectField: ObjectField;
	objectValidationRuleSetting: ObjectValidationRuleSetting;
	objectValidationRuleSettingNameMatches:
		| 'compositeKeyObjectFieldExternalReferenceCode'
		| 'outputObjectFieldExternalReferenceCode';
}

interface ModalSelectObjectFieldItem extends ObjectField {
	checked: boolean;
	disableCheckbox: boolean;
}
export interface UniqueCompositeKeyProps {
	baseResourceURL: string;
	creationLanguageId: Liferay.Language.Locale;
	customObjectFields: ObjectField[];
	disabled: boolean;
	errors: ObjectValidationErrors;
	objectDefinitionExternalReferenceCode: string;
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
	baseResourceURL,
	creationLanguageId,
	customObjectFields,
	disabled,
	errors,
	objectDefinitionExternalReferenceCode,
	setShowUniqueCompositeKeyAlert,
	setValues,
	showUniqueCompositeKeyAlert,
	values,
}: UniqueCompositeKeyProps) {
	const [alerts, setAlerts] = useState<Alert[]>([]);
	const [builderScreenItems, setBuilderScreenItems] = useState<
		TBuilderScreenItem[]
	>([]);
	const [modalSelectObjectFieldsItems, setModalSelectObjectFieldsItems] =
		useState<ModalSelectObjectFieldItem[]>([]);
	const [multipleSelectOptions, setMultipleSelectOptions] = useState<
		MultiSelectItem[]
	>([]);
	const [objectDefinition, setObjectDefinition] =
		useState<ObjectDefinition>();

	const allowedObjectFieldBusinessTypes = [
		'AutoIncrement',
		'Integer',
		'Picklist',
		'Relationship',
		'Text',
	] as ObjectFieldBusinessTypeName[];

	const filteredCustomObjectFields = customObjectFields.filter(
		(customObjectField) =>
			allowedObjectFieldBusinessTypes.includes(
				customObjectField.businessType
			)
	);

	const handleAddObjectFields = () => {
		const parentWindow = Liferay.Util.getOpener();

		if (objectDefinition) {
			parentWindow.Liferay.fire('openModalSelectObjectFields', {
				alerts,
				emptyState: {
					message: Liferay.Language.get(
						'there-are-no-valid-fields-in-this-definition-that-can-be-added-to-the-unique-composite-key'
					),
					title: Liferay.Language.get(
						'there-are-no-valid-fields-created-yet'
					),
				},
				getName: ({label, name}: ObjectField) =>
					stringUtils.getLocalizableLabel(
						creationLanguageId,
						label,
						name
					),
				header: Liferay.Language.get(
					'add-fields-to-unique-composite-key'
				),
				items: modalSelectObjectFieldsItems,
				onAfterClose: setAlerts(([firstItem]) => {
					return firstItem ? [firstItem] : [];
				}),
				onSave: async (selectedObjectFields: ObjectField[]) => {
					if (selectedObjectFields.length) {
						const newSelectedObjectFields =
							selectedObjectFields?.filter(
								(selectedObjectField) =>
									!values.objectValidationRuleSettings?.some(
										(objectValidationRuleSetting) =>
											selectedObjectField.externalReferenceCode ===
												objectValidationRuleSetting.value &&
											objectValidationRuleSetting.name ===
												'compositeKeyObjectFieldExternalReferenceCode'
									)
							);

						if (
							newSelectedObjectFields.length &&
							objectDefinition.status.label === 'approved'
						) {
							const newSelectedObjectFieldsIds =
								newSelectedObjectFields?.map(({id}) => id);

							const addObjectFieldKeyCandidatesUrl =
								createResourceURL(baseResourceURL, {
									objectDefinitionId: (
										objectDefinition as ObjectDefinition
									).id,
									objectFieldsIds:
										newSelectedObjectFieldsIds.length > 1
											? newSelectedObjectFieldsIds.join(
													', '
												)
											: newSelectedObjectFieldsIds[0],
									p_p_resource_id:
										'/object_definitions/add_object_field_composite_key_candidates',
								}).href;

							const addObjectFieldKeyCandidatesResponse =
								await API.fetchJSON<{
									errorLabel: string;
									status: string;
								}>(addObjectFieldKeyCandidatesUrl);

							if (
								addObjectFieldKeyCandidatesResponse.status ===
								'error'
							) {
								setAlerts(([previousAlerts]) => [
									previousAlerts,
									{
										content:
											addObjectFieldKeyCandidatesResponse.errorLabel,
										otherProps: {
											displayType: 'danger',
											title: Liferay.Language.get(
												'error'
											),
											variant: 'stripe',
										},
									},
								]);

								return;
							}
						}
					}

					const objectValidationRuleSettings: ObjectValidationRuleSetting[] =
						[];

					selectedObjectFields.map((selectedObjectField) =>
						values.outputType === 'partialValidation'
							? objectValidationRuleSettings?.push(
									{
										name: 'compositeKeyObjectFieldExternalReferenceCode',
										value: selectedObjectField.externalReferenceCode,
									},
									{
										name: 'outputObjectFieldExternalReferenceCode',
										value: selectedObjectField.externalReferenceCode,
									}
								)
							: objectValidationRuleSettings?.push({
									name: 'compositeKeyObjectFieldExternalReferenceCode',
									value: selectedObjectField.externalReferenceCode,
								})
					);

					setValues({
						objectValidationRuleSettings,
					});
				},
				selected: modalSelectObjectFieldsItems.filter(
					(modalSelectObjectFieldsItem) =>
						modalSelectObjectFieldsItem.checked
				),
				showModal: true,
				title: Liferay.Language.get('select-the-fields'),
			});
		}
	};

	const persistedObjectValidation = useMemo(() => {
		return values;

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (alerts.length >= 2) {
			handleAddObjectFields();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [alerts]);

	useEffect(() => {
		const makeFetch = async () => {
			const objectDefinitionResponse =
				await API.getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode
				);

			setObjectDefinition(objectDefinitionResponse);
			if (objectDefinitionResponse.status.label === 'approved') {
				setAlerts([
					{
						content: sub(
							Liferay.Language.get(
								'x-is-already-published.-as-a-result,-you-can-only-add-fields-to-unique-composite-keys-with-no-data'
							),
							objectDefinitionResponse.name!
						),
						otherProps: {
							displayType: 'info',
							title: Liferay.Language.get('info'),
							variant: 'stripe',
						},
					},
				]);
			}
		};

		makeFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (!values.objectValidationRuleSettings) {
			return;
		}

		const newBuilderScreenItems: TBuilderScreenItem[] = [];
		const newModalSelectObjectFieldsItems: ModalSelectObjectFieldItem[] =
			[];
		const newMultipleSelectOptionsChildren: MultiSelectItemChild[] = [];

		values.objectValidationRuleSettings.forEach(
			(objectValidationRuleSetting) => {
				const filteredObjectFieldObjectValidationRuleSetting =
					filteredCustomObjectFields.find(
						(filteredCustomObjectField) =>
							isMatchingObjectFieldObjectValidationRuleSetting({
								objectField: filteredCustomObjectField,
								objectValidationRuleSetting,
								objectValidationRuleSettingNameMatches:
									'compositeKeyObjectFieldExternalReferenceCode',
							})
					);

				if (filteredObjectFieldObjectValidationRuleSetting) {
					const label = stringUtils.getLocalizableLabel(
						creationLanguageId,
						filteredObjectFieldObjectValidationRuleSetting.label,
						filteredObjectFieldObjectValidationRuleSetting.name
					);

					newBuilderScreenItems.push({
						externalReferenceCode:
							filteredObjectFieldObjectValidationRuleSetting.externalReferenceCode,
						fieldLabel: label,
						label: filteredObjectFieldObjectValidationRuleSetting.label,
						objectFieldBusinessType:
							filteredObjectFieldObjectValidationRuleSetting.businessType,
						objectFieldName:
							filteredObjectFieldObjectValidationRuleSetting.name,
					});

					newMultipleSelectOptionsChildren.push({
						checked: !!values.objectValidationRuleSettings?.find(
							(objectValidationRuleSetting) =>
								isMatchingObjectFieldObjectValidationRuleSetting(
									{
										objectField:
											filteredObjectFieldObjectValidationRuleSetting,
										objectValidationRuleSetting,
										objectValidationRuleSettingNameMatches:
											'outputObjectFieldExternalReferenceCode',
									}
								)
						),
						label,
						value: filteredObjectFieldObjectValidationRuleSetting.externalReferenceCode,
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
				disableCheckbox:
					objectDefinition?.status.label === 'approved' &&
					!!persistedObjectValidation.objectValidationRuleSettings?.find(
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

		setMultipleSelectOptions([
			{
				children: newMultipleSelectOptionsChildren,
				label: '',
				value: 'objectFields',
			},
		]);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectDefinition?.status, values.objectValidationRuleSettings]);

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
				className="lfr-object__object-validation-unique-composite-key-builder-screen"
				title={Liferay.Language.get('fields')}
			>
				<BuilderScreen
					builderScreenItems={builderScreenItems}
					defaultSort={false}
					disableEdit={true}
					emptyState={{
						buttonText: Liferay.Language.get('add-fields'),
						description: Liferay.Language.get(
							'add-a-minimum-of-two-object-fields-to-create-unique-composite-keys'
						),
						title: Liferay.Language.get('no-fields-added-yet'),
					}}
					filter={true}
					firstColumnHeader={Liferay.Language.get('label')}
					onDeleteColumn={(objectFieldName) => {
						const cannotDeleteObjectField = builderScreenItems.some(
							(builderScreenItem) =>
								builderScreenItem.objectFieldName ===
									objectFieldName &&
								(objectDefinition as ObjectDefinition).status
									.label === 'approved' &&
								(
									persistedObjectValidation.objectValidationRuleSettings as ObjectValidationRuleSetting[]
								).some(
									(objectValidationRuleSetting) =>
										objectValidationRuleSetting.value ===
										builderScreenItem.externalReferenceCode
								)
						);

						if (cannotDeleteObjectField) {
							const parentWindow = Liferay.Util.getOpener();

							parentWindow.Liferay.fire(
								'openModalDeletionNotAllowed',
								{
									contentLiferayFire: (
										<span>
											{Liferay.Language.get(
												'fields-cannot-be-deleted-from-unique-composite-keys-after-the-definition-is-published'
											)}
										</span>
									),
								}
							);
						}
						else {
							let removedBuilderScreenItem: TBuilderScreenItem[];

							builderScreenItems.forEach(
								(builderScreenItem, index) => {
									if (
										builderScreenItem.objectFieldName ===
										objectFieldName
									) {
										removedBuilderScreenItem =
											builderScreenItems.splice(index, 1);
									}
								}
							);
							setValues({
								objectValidationRuleSettings:
									values.objectValidationRuleSettings?.filter(
										(objectValidationRuleSetting) =>
											objectValidationRuleSetting.value !==
											removedBuilderScreenItem[0]
												.externalReferenceCode
									),
							});
						}
					}}
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
					disabled={!builderScreenItems.length}
					label={Liferay.Language.get('field')}
					options={multipleSelectOptions}
					setOptions={([newOutputObjectFieldOption]) => {
						const objectValidationRuleSettings =
							values.objectValidationRuleSettings?.filter(
								(objectValidationRuleSetting) =>
									objectValidationRuleSetting.name !==
									'outputObjectFieldExternalReferenceCode'
							);

						newOutputObjectFieldOption.children.forEach(
							(newOutputObjectFieldOptionChild) => {
								if (newOutputObjectFieldOptionChild.checked) {
									objectValidationRuleSettings?.push({
										name: 'outputObjectFieldExternalReferenceCode',
										value: newOutputObjectFieldOptionChild.value,
									});
								}
							}
						);

						setValues({objectValidationRuleSettings});
						setMultipleSelectOptions([newOutputObjectFieldOption]);
					}}
				/>
			</ErrorMessage>
		</>
	);
}
