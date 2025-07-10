/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayDualListBox, ClayRadio, ClayRadioGroup, ClaySelectWithOption} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import { useFormik } from 'formik';
import { openToast } from 'frontend-js-components-web';
import { navigate } from 'frontend-js-web';
import React, {useState} from 'react';
import styled from 'styled-components';

import SpacePanel from './SpacePanel';
import SpaceService from '../../common/services/SpaceService';
import {LabelValueObject, Space} from '../../common/types/Space';

export default function SpaceLanguageSettings({
	companyAvailableLanguages,
	setSpace,
	space,
}: {
	companyAvailableLanguages: LabelValueObject[];
	setSpace?: React.Dispatch<React.SetStateAction<any>>;
	space: Space;
}) {
	const [showRemoveDefaultLanguageWarning, setShowRemoveDefaultLanguageWarning] = useState<boolean>(false);

	const {
		handleChange,
		handleSubmit,
		resetForm,
		setFieldValue,
		setValues,
		submitForm,
		values,
	} = useFormik({
		initialValues: {
			availableLanguages: companyAvailableLanguages.filter(availableLanguage =>
				!space.settings?.availableLanguageIds?.includes(availableLanguage.value)
			) || [],
			selectedLanguages: companyAvailableLanguages.filter(availableLanguage =>
				space.settings?.availableLanguageIds?.includes(availableLanguage.value)
			) || [],
			availableLanguageIds: space.settings?.availableLanguageIds ?? [],
			defaultLanguageId: space.settings?.defaultLanguageId ?? '',
			useCustomLanguages: !!space.settings?.useCustomLanguages,
		},
		onSubmit: async (values) => {
			const {availableLanguageIds, defaultLanguageId, useCustomLanguages} = values;

			const { data, error } = await SpaceService.updateSpace(
				space.externalReferenceCode,
				{
					externalReferenceCode: space.externalReferenceCode,
					settings: {
						availableLanguageIds,
						defaultLanguageId,
						useCustomLanguages,
					}
				}
			);

			if (error) {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred-while-saving-the-space'
					),
					type: 'danger',
				});
			}
			else if (data) {
				openToast({
					message: Liferay.Util.sub(
						Liferay.Language.get('x-was-saved-successfully'),
						space.name
					),
					type: 'success',
				});

				if (setSpace) {
					setSpace(data);
				}
			}
		},
	});

	const onCancel = () => {
		const url = new URL(window.location.href);
		const redirect = url.searchParams.get('redirect');

		if (redirect) {
			navigate(redirect);
		}
	};

	const handleItemsChange = (items: LabelValueObject[][]) => {
		const [nextAvailableLanguages, nextSelectedLanguages] = items;

		const removingDefaultLanguage = nextAvailableLanguages.some(
			(language) => language.value === values.defaultLanguageId
		);

		if (removingDefaultLanguage) {
			setShowRemoveDefaultLanguageWarning(true);
		}
		else {
			setValues({
				...values,
				availableLanguages: nextAvailableLanguages,
				selectedLanguages: nextSelectedLanguages,
				availableLanguageIds: nextSelectedLanguages.map(
					(language) => language.value
				),
			});

			setShowRemoveDefaultLanguageWarning(false);
		}
	};

	return (
		<form
			className="container-fluid container-fluid-max-md p-0 p-md-4"
			onSubmit={handleSubmit}
		>
			<SpacePanel title={Liferay.Language.get('languages')}>
				<p>{Liferay.Language.get('select-the-language-configuration-for-the-space')}</p>

				<ClayForm.Group>
					<ClayRadioGroup
						defaultValue={values.useCustomLanguages.toString()}
						name="useCustomLanguages"
						onChange={(value: any) => {
							if (value === 'false') {
								resetForm();
							}

							setFieldValue('useCustomLanguages', JSON.parse(value))
						}}
					>
						<ClayRadio
							label={Liferay.Language.get(
								'use-the-default-language-options'
							)}
							value="false"
						/>

						<ClayRadio
							label={Liferay.Language.get(
								'define-a-custom-default-language-and-additional-active-languages-for-this-space'
							)}
							value="true"
						/>
					</ClayRadioGroup>
				</ClayForm.Group>

				{values.useCustomLanguages &&
					<ClayPanel
						aria-label={Liferay.Language.get('custom-default-language')}
						collapsable
						defaultExpanded={true}
						displayTitle={Liferay.Language.get('custom-default-language')}
						displayType="default"
						role="group"
						showCollapseIcon
					>
						<ClayPanel.Body>
							{showRemoveDefaultLanguageWarning && 
								<ClayAlert
									autoClose
									displayType="danger"
									onClose={() => {
										setShowRemoveDefaultLanguageWarning(false);
									}}
									title={Liferay.Language.get('error')}
								>
									{Liferay.Language.get(
										'you-cannot-remove-a-language-that-is-the-current-default-language'
									)}
								</ClayAlert>
							}

							<ClayForm.Group>
								<label
									htmlFor="defaultLanguageId"
									className="sr-only"
								>
									{Liferay.Language.get('custom-default-language')}
								</label>

								<ClaySelectWithOption
									id="defaultLanguageId"
									name="defaultLanguageId"
									onChange={handleChange}
									options={values.selectedLanguages}
									value={values.defaultLanguageId}
								/>
							</ClayForm.Group>

							<CustomLanguagesSelector defaultLanguageId={values.defaultLanguageId}>
								<ClayDualListBox
									disableLTR={!values.availableLanguages.length}
									disableRTL={values.selectedLanguages.length === 1}
									items={[values.availableLanguages, values.selectedLanguages]}
									left={{
										id: 'availableLanguages',
										label: Liferay.Language.get('available'),
									}}
									onItemsChange={handleItemsChange}
									right={{
										id: 'selectedLanguages',
										label: Liferay.Language.get('selected'),
									}}
									size={10}
								/>
							</CustomLanguagesSelector>
						</ClayPanel.Body>
					</ClayPanel>
				}
			</SpacePanel>

			<ClayButton.Group className="mt-2" spaced>
				<ClayButton onClick={submitForm}>
					{Liferay.Language.get('save')}
				</ClayButton>

				<ClayButton displayType="secondary" onClick={onCancel}>
					{Liferay.Language.get('cancel')}
				</ClayButton>
			</ClayButton.Group>
		</form>
	);
}

const CustomLanguagesSelector = styled.div<{ defaultLanguageId: string; }>`
	option[value='${props => props.defaultLanguageId}']::after {
		background-color: #fff;
		border: 0.0625rem solid #89a7e0;
		border-radius: 0.125rem;
		color: #2e5aac;
		content: '${Liferay.Language.get('default')}';
		display: inline-flex;
		font-size: 0.625rem;
		font-weight: 600;
		line-height: 1;
		margin-left: 0.25rem;
		max-width: 100%;
		padding: 0.125rem 0.25rem;
		text-transform: uppercase;
		white-space: inherit;
		word-wrap: break-word;
		outline: 0;
		vertical-align: bottom;
	}
`;