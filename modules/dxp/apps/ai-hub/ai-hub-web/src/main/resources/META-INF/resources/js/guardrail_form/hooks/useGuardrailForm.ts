/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from '@liferay/object-js-components-web';
import {useFormik} from 'formik';
import {useCallback, useEffect, useMemo} from 'react';

import {generateExternalReferenceCode} from '../../utils/externalReferenceCode';
import {DEFAULT_GUARDRAIL} from '../constants';
import {
	getGuardrail,
	postGuardrail,
	putGuardrail,
} from '../services/GuardrailService';
import {Guardrail} from '../types/Guardrail';

interface UseGuardrailFormProps {
	externalReferenceCode: string;
}

export function useGuardrailForm({
	externalReferenceCode,
}: UseGuardrailFormProps) {
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
	} = useFormik<Guardrail>({
		initialValues: {
			...DEFAULT_GUARDRAIL,
			externalReferenceCode: generatedExternalReferenceCode,
		},
		onSubmit: async (formValues) => {
			try {
				if (externalReferenceCode) {
					await putGuardrail(formValues);
				}
				else {
					await postGuardrail(formValues);
				}

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
			const validationErrors: Partial<Record<keyof Guardrail, string>> =
				{};

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
		<K extends keyof Guardrail>(field: K, value: Guardrail[K]) => {
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
				const guardrail = await getGuardrail(externalReferenceCode);

				setValues({
					active: guardrail.active,
					description: guardrail.description,
					externalReferenceCode: guardrail.externalReferenceCode,
					guardrailType: guardrail.guardrailType,
					maliciousUriFilterEnabled:
						guardrail.maliciousUriFilterEnabled,
					multilanguageDetectionEnabled:
						guardrail.multilanguageDetectionEnabled,
					piAndJailbreakConfidenceLevel:
						guardrail.piAndJailbreakConfidenceLevel,
					piAndJailbreakFilterEnabled:
						guardrail.piAndJailbreakFilterEnabled,
					raiDangerousLevel: guardrail.raiDangerousLevel,
					raiHarassmentLevel: guardrail.raiHarassmentLevel,
					raiHateSpeechLevel: guardrail.raiHateSpeechLevel,
					raiSexuallyExplicitLevel:
						guardrail.raiSexuallyExplicitLevel,
					sdpFilterEnabled: guardrail.sdpFilterEnabled,
					title_i18n: guardrail.title_i18n,
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
