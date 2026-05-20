/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from '@liferay/object-js-components-web';
import {useFormik} from 'formik';
import {useCallback, useEffect} from 'react';

import {DEFAULT_MODEL_ARMOR_TEMPLATE} from '../constants';
import {
	getModelArmorTemplate,
	putModelArmorTemplate,
} from '../services/ModelArmorTemplateService';
import {ModelArmorTemplate} from '../types/ModelArmorTemplate';

interface UseModelArmorTemplateFormProps {
	accountEntryExternalReferenceCode: string;
	externalReferenceCode: string;
}

export function useModelArmorTemplateForm({
	accountEntryExternalReferenceCode,
	externalReferenceCode,
}: UseModelArmorTemplateFormProps) {
	const {
		errors,
		handleSubmit,
		isSubmitting,
		setFieldValue,
		setValues,
		values,
	} = useFormik<ModelArmorTemplate>({
		initialValues: {
			...DEFAULT_MODEL_ARMOR_TEMPLATE,
			r_accountToAIHubModelArmorTemplates_accountEntryERC:
				accountEntryExternalReferenceCode,
		},
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

			if (
				!formValues.title_i18n ||
				!Object.values(formValues.title_i18n).some(Boolean)
			) {
				validationErrors.title_i18n =
					Liferay.Language.get('required');
			}

			if (!formValues.externalReferenceCode) {
				validationErrors.externalReferenceCode =
					Liferay.Language.get('required');
			}

			if (!formValues.location) {
				validationErrors.location =
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

				setValues(modelArmorTemplate);
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
	}, [externalReferenceCode, setValues]);

	return {
		errors,
		handleSubmit,
		isSubmitting,
		setField,
		values,
	};
}
