/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import Link from '@clayui/link';
import {openToast} from '@liferay/object-js-components-web';
import {useFormik} from 'formik';
import React, {useCallback, useEffect} from 'react';

import './ModelArmorTemplateForm.scss';
import Toolbar from '../components/ToolBar';
import DetailsPanel from './DetailsPanel';
import DetectionsPanel from './DetectionsPanel';
import ResponsibleAIPanel from './ResponsibleAIPanel';
import {
	getModelArmorTemplate,
	putModelArmorTemplate,
} from './services/ModelArmorTemplateService';
import {
	FilterLevel,
	ModelArmorTemplate,
	RAILevel,
} from './types/ModelArmorTemplate';

const FORM_ID = 'modelArmorTemplateForm';

export default function ModelArmorTemplateForm({
	accountEntryExternalReferenceCode,
	backURL,
	externalReferenceCode,
}: {
	accountEntryExternalReferenceCode: string;
	backURL: string;
	externalReferenceCode: string;
}) {
	const getDefaults = useCallback(
		(): ModelArmorTemplate => ({
			active: true,
			description: '',
			externalReferenceCode: '',
			guardrailType: 'input',
			maliciousUriFilterEnabled: false,
			multiLanguageDetectionEnabled: false,
			piAndJailbreakConfidenceLevel: 'mediumAndAbove',
			piAndJailbreakFilterEnabled: false,
			r_accountToAIHubModelArmorTemplates_accountEntryERC:
				accountEntryExternalReferenceCode,
			raiDangerousLevel: 'none',
			raiHarassmentLevel: 'none',
			raiHateSpeechLevel: 'none',
			raiSexuallyExplicitLevel: 'none',
			sdpFilterEnabled: false,
			title: '',
		}),
		[accountEntryExternalReferenceCode]
	);

	const {
		errors,
		handleChange,
		handleSubmit,
		isSubmitting,
		setFieldValue,
		setValues,
		values,
	} = useFormik<ModelArmorTemplate>({
		initialValues: getDefaults(),
		onSubmit: async (formValues) => {
			try {
				const response = await putModelArmorTemplate(formValues);

				if (response?.externalReferenceCode) {
					openToast({
						message: Liferay.Language.get(
							'model-armor-template-saved-successfully'
						),
						type: 'success',
					});
				}
				else {
					openToast({
						message: Liferay.Language.get(
							'failed-to-save-model-armor-template'
						),
						type: 'danger',
					});
				}
			}
			catch (error) {
				console.error(error);

				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			}
		},
		validate: (formValues) => {
			const validationErrors: Partial<
				Record<keyof ModelArmorTemplate, string>
			> = {};

			if (!formValues.title) {
				validationErrors.title = Liferay.Language.get('required');
			}

			if (!formValues.externalReferenceCode) {
				validationErrors.externalReferenceCode =
					Liferay.Language.get('required');
			}

			return validationErrors;
		},
	});

	const setField = useCallback(
		<K extends keyof ModelArmorTemplate>(
			field: K,
			value: ModelArmorTemplate[K]
		) => {
			setFieldValue(field, value);
		},
		[setFieldValue]
	);

	useEffect(() => {
		async function fetchFormData() {
			if (!externalReferenceCode) {
				return;
			}

			try {
				const modelArmorTemplate = await getModelArmorTemplate(
					externalReferenceCode
				);

				const pickKey = (value: any, fallback: string) =>
					value?.key || value || fallback;

				setValues({
					active: modelArmorTemplate.active,
					description: modelArmorTemplate.description || '',
					externalReferenceCode:
						modelArmorTemplate.externalReferenceCode,
					guardrailType: pickKey(
						modelArmorTemplate.guardrailType,
						'input'
					),
					maliciousUriFilterEnabled:
						modelArmorTemplate.maliciousUriFilterEnabled ?? false,
					multiLanguageDetectionEnabled:
						modelArmorTemplate.multiLanguageDetectionEnabled ??
						false,
					piAndJailbreakConfidenceLevel: pickKey(
						modelArmorTemplate.piAndJailbreakConfidenceLevel,
						'mediumAndAbove'
					) as FilterLevel,
					piAndJailbreakFilterEnabled:
						modelArmorTemplate.piAndJailbreakFilterEnabled ?? false,
					r_accountToAIHubModelArmorTemplates_accountEntryERC:
						modelArmorTemplate.r_accountToAIHubModelArmorTemplates_accountEntryERC ||
						accountEntryExternalReferenceCode,
					raiDangerousLevel: pickKey(
						modelArmorTemplate.raiDangerousLevel,
						'none'
					) as RAILevel,
					raiHarassmentLevel: pickKey(
						modelArmorTemplate.raiHarassmentLevel,
						'none'
					) as RAILevel,
					raiHateSpeechLevel: pickKey(
						modelArmorTemplate.raiHateSpeechLevel,
						'none'
					) as RAILevel,
					raiSexuallyExplicitLevel: pickKey(
						modelArmorTemplate.raiSexuallyExplicitLevel,
						'none'
					) as RAILevel,
					sdpFilterEnabled:
						modelArmorTemplate.sdpFilterEnabled ?? false,
					title: modelArmorTemplate.title || '',
				});
			}
			catch (error) {
				openToast({
					message: Liferay.Language.get(
						'failed-to-load-model-armor-template-data'
					),
					type: 'danger',
				});
			}
		}

		fetchFormData();
	}, [accountEntryExternalReferenceCode, externalReferenceCode, setValues]);

	return (
		<>
			<Toolbar
				backURL={backURL}
				title={
					externalReferenceCode
						? Liferay.Language.get('edit-guardrail')
						: Liferay.Language.get('new-guardrail')
				}
			>
				<Toolbar.Item>
					<Link
						aria-label={Liferay.Language.get('cancel')}
						borderless
						button
						displayType="secondary"
						href={backURL}
						small
					>
						{Liferay.Language.get('cancel')}
					</Link>
				</Toolbar.Item>

				<Toolbar.Item>
					<Button
						aria-label={Liferay.Language.get('save')}
						disabled={isSubmitting}
						form={FORM_ID}
						size="sm"
						type="submit"
					>
						{Liferay.Language.get('save')}
					</Button>
				</Toolbar.Item>
			</Toolbar>

			<ClayLayout.ContainerFluid className="model-armor-template-form">
				<ClayForm id={FORM_ID} onSubmit={handleSubmit}>
					<ClayLayout.Row>
						<ClayLayout.Col md={12}>
							<DetailsPanel
								errors={errors}
								handleChange={handleChange}
								setField={setField}
								values={values}
							/>

							<DetectionsPanel
								setField={setField}
								values={values}
							/>

							<ResponsibleAIPanel
								setField={setField}
								values={values}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayForm>
			</ClayLayout.ContainerFluid>
		</>
	);
}
