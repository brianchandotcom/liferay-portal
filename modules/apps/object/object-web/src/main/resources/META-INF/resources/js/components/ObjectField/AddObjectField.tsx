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
import ClayForm, {ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {API, Input} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import {toCamelCase} from '../../utils/string';
import PicklistDefaultValueSelect from './DefaultValueFields/PicklistDefaultValueSelect';
import ObjectFieldFormBase from './ObjectFieldFormBase';
import {useObjectFieldForm} from './useObjectFieldForm';

import './AddObjectField.scss';

interface IModal extends IProps {
	observer: Observer;
	onClose: () => void;
}

interface IProps {
	apiURL: string;
	creationLanguageId: Liferay.Language.Locale;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
}

function ModalAddObjectField({
	apiURL,
	creationLanguageId,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	observer,
	onClose,
}: IModal) {
	const [error, setError] = useState<string>('');

	const initialValues: Partial<ObjectField> = {
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: null,
		listTypeDefinitionExternalReferenceCode: '',
		listTypeDefinitionId: 0,
		required: false,
	};

	const onSubmit = async (field: Partial<ObjectField>) => {
		if (field.label) {
			field = {
				...field,
				name:
					field.name ||
					toCamelCase(field.label[defaultLanguageId] as string, true),
			};

			delete field.listTypeDefinitionId;

			try {
				await API.save(apiURL, field, 'POST');

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

	const showEnableTranslationToggle =
		values.businessType === 'LongText' ||
		values.businessType === 'RichText' ||
		values.businessType === 'Text';

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
						objectDefinitionExternalReferenceCode={
							objectDefinitionExternalReferenceCode
						}
						objectField={values}
						objectFieldTypes={objectFieldTypes}
						objectName={objectName}
						setValues={setValues}
					>
						{Liferay.FeatureFlags['LPS-146755'] &&
							showEnableTranslationToggle && (
								<div className="lfr-objects-add-object-field-enable-translations-toggle">
									<ClayToggle
										label={Liferay.Language.get(
											'enable-entry-translations'
										)}
										onToggle={() =>
											setValues({
												localized: !values.localized,
											})
										}
										toggled={values.localized}
									/>

									<ClayTooltipProvider>
										<span
											title={Liferay.Language.get(
												'users-will-be-able-to-add-translations-for-the-entries-of-this-field'
											)}
										>
											<ClayIcon
												className="lfr-objects-add-object-field-enable-translations-toggle-icon"
												symbol="question-circle-full"
											/>
										</span>
									</ClayTooltipProvider>
								</div>
							)}
					</ObjectFieldFormBase>

					{values.state && (
						<PicklistDefaultValueSelect
							creationLanguageId={creationLanguageId}
							defaultValue={
								Liferay.FeatureFlags['LPS-163716']
									? values.objectFieldSettings?.find(
											(setting) =>
												setting.name === 'defaultValue'
									  )?.value
									: values.defaultValue
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
	);
}

export default function AddObjectField({
	apiURL,
	creationLanguageId,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
}: IProps) {
	const [isVisible, setVisibility] = useState<boolean>(false);
	const {observer, onClose} = useModal({onClose: () => setVisibility(false)});

	useEffect(() => {
		Liferay.on('addObjectField', () => setVisibility(true));

		return () => Liferay.detach('addObjectField');
	}, []);

	const applyFeatureFlag = () => {
		return objectFieldTypes.filter((objectFieldType) => {
			if (!Liferay.FeatureFlags['LPS-164948']) {
				return objectFieldType.businessType !== 'Formula';
			}
		});
	};

	return (
		<ClayModalProvider>
			{isVisible && (
				<ModalAddObjectField
					apiURL={apiURL}
					creationLanguageId={creationLanguageId}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					objectFieldTypes={
						!Liferay.FeatureFlags['LPS-164948']
							? applyFeatureFlag()
							: objectFieldTypes
					}
					objectName={objectName}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
}
