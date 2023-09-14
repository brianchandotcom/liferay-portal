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

interface ModalAddObjectField {
	apiURL: string;
	creationLanguageId: Liferay.Language.Locale;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName?: string;
	setVisibility: (value: boolean) => void;
}

export function ModalAddObjectField({
	apiURL,
	creationLanguageId,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	setVisibility,
}: ModalAddObjectField) {
	const [error, setError] = useState<string>('');
	const [objectDefinition, setObjectDefinition] = useState<
		ObjectDefinition
	>();
	const {observer, onClose} = useModal({onClose: () => setVisibility(false)});

	const initialValues: Partial<ObjectField> = {
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: null,
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

		if (!Liferay.FeatureFlags['LPS-170122']) {
			delete field.readOnly;
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
				await API.save({item: field, method: 'POST', url: apiURL});

				onClose();
				window.location.reload();
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

	const applyFeatureFlag = () => {
		return objectFieldTypes.filter((objectFieldType) => {
			if (!Liferay.FeatureFlags['LPS-164948']) {
				return objectFieldType.businessType !== 'Formula';
			}
		});
	};

	const showEnableTranslationToggle =
		values.businessType === 'LongText' ||
		values.businessType === 'RichText' ||
		values.businessType === 'Text';

	useEffect(() => {
		if (!objectDefinition) {
			const makeFetch = async () => {
				const objectDefinitionResponse = await API.getObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode
				);

				setObjectDefinition(objectDefinitionResponse);
			};

			makeFetch();
		}

		if (Liferay.FeatureFlags['LPS-172017']) {
			if (
				objectDefinition?.enableLocalization &&
				showEnableTranslationToggle
			) {
				setValues({
					localized: true,
				});

				return;
			}

			setValues({
				localized: false,
			});

			return;
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.businessType]);

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
							objectField={values}
							objectFieldTypes={
								!Liferay.FeatureFlags['LPS-164948']
									? applyFeatureFlag()
									: objectFieldTypes
							}
							objectName={objectName ?? ''}
							setValues={setValues}
						>
							{Liferay.FeatureFlags['LPS-172017'] &&
								showEnableTranslationToggle && (
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
													required: Liferay
														.FeatureFlags[
														'LPS-172017'
													]
														? !localized &&
														  values.required
														: values.required,
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
