/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {API, Input, Toggle} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import {toCamelCase} from '../../utils/string';
import ListTypeDefaultValueSelect from './DefaultValueFields/ListTypeDefaultValueSelect';
import ObjectFieldFormBase from './ObjectFieldFormBase';
import {useObjectFieldForm} from './useObjectFieldForm';

import './ModalAddObjectField.scss';

import {createResourceURL, fetch} from 'frontend-js-web';

interface ModalAddObjectField {
	baseResourceURL: string;
	creationLanguageId: Liferay.Language.Locale;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionName?: string;
	onAfterSubmit: (value: ObjectField) => void;
	setVisibility: (value: boolean) => void;
}

export function ModalAddObjectField({
	baseResourceURL,
	creationLanguageId,
	objectDefinitionExternalReferenceCode,
	objectDefinitionName,
	onAfterSubmit,
	setVisibility,
}: ModalAddObjectField) {
	const [error, setError] = useState<string>('');
	const [objectDefinition, setObjectDefinition] = useState<
		ObjectDefinition
	>();
	const [objectFieldTypes, setObjectFieldTypes] = useState<ObjectFieldType[]>(
		[]
	);
	const {observer, onClose} = useModal({onClose: () => setVisibility(false)});

	const initialValues: Partial<ObjectField> = {
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: '',
		listTypeDefinitionExternalReferenceCode: '',
		listTypeDefinitionId: 0,
		readOnly: 'false',
		readOnlyConditionExpression: '',
		required: false,
	};

	const onSubmit = async (field: Partial<ObjectField>) => {
		if (
			field.businessType === 'Aggregation' ||
			field.businessType === 'Formula'
		) {
			field.readOnly = 'true';
			delete field.readOnlyConditionExpression;
		}

		if (field.label) {
			field = {
				...field,
				name:
					field.name ||
					toCamelCase(field.label[defaultLanguageId] as string, true),
			};

			delete field.listTypeDefinitionId;

			try {
				const objectFieldResponse = await API.save<ObjectField>({
					item: field,
					method: 'POST',
					returnValue: true,
					url: `/o/object-admin/v1.0/object-definitions/by-external-reference-code/${objectDefinitionExternalReferenceCode}/object-fields`,
				});

				onAfterSubmit(objectFieldResponse as ObjectField);
			}
			catch (error) {
				setError((error as Error).message);
			}
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

	const showEnableTranslationToggle =
		values.businessType === 'LongText' ||
		values.businessType === 'RichText' ||
		values.businessType === 'Text';

	useEffect(() => {
		const makeFetch = async () => {
			const objectDefinitionResponse = await API.getObjectDefinitionByExternalReferenceCode(
				objectDefinitionExternalReferenceCode
			);

			setObjectDefinition(objectDefinitionResponse);

			const url = createResourceURL(baseResourceURL, {
				objectDefinitionId: objectDefinitionResponse.id,
				p_p_resource_id: '/object_definitions/get_object_field_types',
			}).href;

			const objectFieldTypesResponse = await fetch(url, {
				method: 'GET',
			});

			const {
				objectFieldTypes,
			} = (await objectFieldTypesResponse.json()) as {
				objectFieldTypes: ObjectFieldType[];
			};

			setObjectFieldTypes(objectFieldTypes);
		};

		makeFetch();

		setValues({
			localized:
				objectDefinition?.enableLocalization &&
				showEnableTranslationToggle,
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectDefinitionExternalReferenceCode, values.businessType]);

	return (
		<ClayModalProvider>
			<ClayModal center observer={observer}>
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
							label={Liferay.Language.get('label')}
							name="label"
							onChange={({target: {value}}) => {
								setValues({
									label: {[defaultLanguageId]: value},
								});
							}}
							required
							value={values.label?.[defaultLanguageId]}
						/>

						<ObjectFieldFormBase
							errors={errors}
							handleChange={handleChange}
							objectDefinition={objectDefinition}
							objectDefinitionExternalReferenceCode={
								objectDefinitionExternalReferenceCode
							}
							objectDefinitionName={
								objectDefinitionName ?? objectDefinition
									? objectDefinition?.name ?? ''
									: ''
							}
							objectField={values}
							objectFieldTypes={objectFieldTypes}
							setValues={setValues}
						>
							{showEnableTranslationToggle && (
								<div className="lfr-objects-add-object-field-enable-translations-toggle">
									<Toggle
										disabled={
											!objectDefinition?.enableLocalization
										}
										label={Liferay.Language.get(
											'enable-entry-translations'
										)}
										onToggle={(localized) =>
											setValues({
												localized,
												required:
													!localized &&
													values.required,
											})
										}
										toggled={values.localized}
										tooltip={Liferay.Language.get(
											'users-will-be-able-to-add-translations-for-the-entries-of-this-field'
										)}
									/>
								</div>
							)}
						</ObjectFieldFormBase>

						{values.state && (
							<ListTypeDefaultValueSelect
								creationLanguageId={creationLanguageId}
								defaultValue={
									values.objectFieldSettings?.find(
										(setting) =>
											setting.name === 'defaultValue'
									)?.value
								}
								error={errors.defaultValue}
								label={Liferay.Language.get('default-value')}
								required
								setValues={setValues}
								values={values}
							/>
						)}
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

								<ClayButton type="submit">
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
