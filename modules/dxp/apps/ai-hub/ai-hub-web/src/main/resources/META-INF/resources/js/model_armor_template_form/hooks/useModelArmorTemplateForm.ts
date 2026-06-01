/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from '@liferay/object-js-components-web';
import {useFormik} from 'formik';
import {useCallback, useEffect, useMemo} from 'react';

import {generateExternalReferenceCode} from '../../utils/externalReferenceCode';
import {DEFAULT_MODEL_ARMOR_TEMPLATE} from '../constants';
import {
	getModelArmorTemplate,
	putModelArmorTemplate,
} from '../services/ModelArmorTemplateService';
import {ModelArmorTemplate} from '../types/ModelArmorTemplate';

interface UseModelArmorTemplateFormProps {
	externalReferenceCode: string;
}

export function useModelArmorTemplateForm({
	externalReferenceCode,
}: UseModelArmorTemplateFormProps) {
	const generatedExternalReferenceCode = useMemo(
		() => generateExternalReferenceCode(),
		[]
	);

	const {
		errors,
		handleBlur,
		handleSubmit,
		isSubmitting,
		setFieldTouched,
		setFieldValue,
		setValues,
		touched,
		values,
	} = useFormik<ModelArmorTemplate>({
		initialValues: {
			...DEFAULT_MODEL_ARMOR_TEMPLATE,
			externalReferenceCode: generatedExternalReferenceCode,
		},
		onSubmit: async (formValues) => {
			try {
				await putModelArmorTemplate(formValues);

				openToast({
					message: Liferay.Language.get(
						'guardrail-saved-successfully'
					),
					type: 'success',
				});
			}
			catch (error) {
				openToast({
					message:
						error instanceof Error && error.message
							? error.message
							: Liferay.Language.get('failed-to-save-guardrail'),
					type: 'danger',
				});
			}
		},
		validate: (formValues) => {
			const validationErrors: Partial<
				Record<keyof ModelArmorTemplate, string>
			> = {};

			if (
				!formValues.title_i18n ||
				!Object.values(formValues.title_i18n).some(Boolean)
			) {
				validationErrors.title_i18n = Liferay.Language.get('required');
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
		if (!externalReferenceCode) {
			return;
		}

		async function fetchFormData() {
			try {
				const modelArmorTemplate = await getModelArmorTemplate(
					externalReferenceCode
				);

				setValues({
					active: modelArmorTemplate.active,
					description: modelArmorTemplate.description,
					externalReferenceCode:
						modelArmorTemplate.externalReferenceCode,
					guardrailType: modelArmorTemplate.guardrailType,
					maliciousUriFilterEnabled:
						modelArmorTemplate.maliciousUriFilterEnabled,
					multilanguageDetectionEnabled:
						modelArmorTemplate.multilanguageDetectionEnabled,
					piAndJailbreakConfidenceLevel:
						modelArmorTemplate.piAndJailbreakConfidenceLevel,
					piAndJailbreakFilterEnabled:
						modelArmorTemplate.piAndJailbreakFilterEnabled,
					raiDangerousLevel: modelArmorTemplate.raiDangerousLevel,
					raiHarassmentLevel: modelArmorTemplate.raiHarassmentLevel,
					raiHateSpeechLevel: modelArmorTemplate.raiHateSpeechLevel,
					raiSexuallyExplicitLevel:
						modelArmorTemplate.raiSexuallyExplicitLevel,
					sdpFilterEnabled: modelArmorTemplate.sdpFilterEnabled,
					title_i18n: modelArmorTemplate.title_i18n,
				});
			}
			catch (error) {
				openToast({
					message: Liferay.Language.get(
						'failed-to-load-guardrail-data'
					),
					type: 'danger',
				});
			}
		}

		fetchFormData();
	}, [externalReferenceCode, setValues]);

	return {
		errors,
		handleBlur,
		handleSubmit,
		isSubmitting,
		setField,
		setFieldTouched,
		touched,
		values,
	};
}
