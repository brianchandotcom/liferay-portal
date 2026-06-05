/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useFormik} from 'formik';
import {openToast} from 'frontend-js-components-web';
import {useCallback, useEffect, useState} from 'react';

import {
	getConfiguration,
	putConfiguration,
} from '../services/ConfigurationService';
import {Configuration} from '../types/Configuration';

interface UseConfigurationFormProps {
	accountEntryId: number;
	externalReferenceCode: string;
}

export function useConfigurationForm({
	accountEntryId,
	externalReferenceCode,
}: UseConfigurationFormProps) {
	const [loading, setLoading] = useState(Boolean(externalReferenceCode));

	const {handleSubmit, isSubmitting, setFieldValue, setValues, values} =
		useFormik<Configuration>({
			initialValues: {
				environmentURLs: '',
				externalReferenceCode,
				recipientEmailAddress: '',
			},
			onSubmit: async (formValues) => {
				try {
					await putConfiguration(externalReferenceCode, {
						...formValues,
						r_accountToAIHubConfigurations_accountEntryId:
							accountEntryId,
					});

					openToast({
						message: Liferay.Language.get(
							'configuration-was-saved-successfully'
						),
						type: 'success',
					});
				}
				catch (error) {
					openToast({
						message:
							error instanceof Error && error.message
								? error.message
								: Liferay.Language.get(
										'failed-to-save-configuration'
									),
						type: 'danger',
					});
				}
			},
		});

	const setField = useCallback(
		<K extends keyof Configuration>(field: K, value: Configuration[K]) => {
			setFieldValue(field, value);
		},
		[setFieldValue]
	);

	useEffect(() => {
		if (!externalReferenceCode) {
			openToast({
				message: Liferay.Language.get('no-associated-account'),
				type: 'warning',
			});

			return;
		}

		getConfiguration(externalReferenceCode)
			.then((configuration) => {
				setValues({
					environmentURLs: configuration.environmentURLs || '',
					externalReferenceCode,
					recipientEmailAddress:
						configuration.recipientEmailAddress || '',
				});
			})
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'failed-to-load-configuration'
					),
					type: 'danger',
				});
			})
			.finally(() => {
				setLoading(false);
			});
	}, [externalReferenceCode, setValues]);

	return {
		handleSubmit,
		isSubmitting,
		loading,
		setField,
		values,
	};
}
