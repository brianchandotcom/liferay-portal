/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from '@liferay/object-js-components-web';
import {useFormik} from 'formik';
import {useCallback, useEffect} from 'react';

import {DEFAULT_AGENT_DEFINITION} from '../constants';
import {
	deleteAgentDefinitionToContentRetrievers,
	deleteAgentDefinitionToModelArmorTemplates,
	getAgentDefinition,
	putAgentDefinition,
	putAgentDefinitionToContentRetrievers,
	putAgentDefinitionToModelArmorTemplates,
} from '../services/AgentDefinitionService';
import {getContentRetrievers} from '../services/ContentRetrieverService';
import {getModelArmorTemplates} from '../services/ModelArmorTemplateService';
import {AgentDefinition} from '../types/AgentDefinition';
import {ContentRetriever} from '../types/ContentRetriever';
import {ModelArmorTemplate} from '../types/ModelArmorTemplate';
import {useRelationshipPicker} from './useRelationshipPicker';

interface UseAgentDefinitionFormProps {
	accountEntryExternalReferenceCode: string;
	externalReferenceCode: string;
	readOnly: boolean;
}

export function useAgentDefinitionForm({
	accountEntryExternalReferenceCode,
	externalReferenceCode,
	readOnly,
}: UseAgentDefinitionFormProps) {
	const contentRetrievers = useRelationshipPicker<ContentRetriever>();
	const modelArmorTemplates = useRelationshipPicker<ModelArmorTemplate>();

	const syncRelationships = useCallback(
		async (agentDefinitionERC: string) => {
			const contentRetrieverDiff = contentRetrievers.diff();
			const modelArmorTemplateDiff = modelArmorTemplates.diff();

			const requests = [
				...contentRetrieverDiff.toAdd.map((item) =>
					putAgentDefinitionToContentRetrievers(
						agentDefinitionERC,
						item.externalReferenceCode
					)
				),
				...contentRetrieverDiff.toRemove.map((item) =>
					deleteAgentDefinitionToContentRetrievers(
						agentDefinitionERC,
						item.externalReferenceCode
					)
				),
				...modelArmorTemplateDiff.toAdd.map((item) =>
					putAgentDefinitionToModelArmorTemplates(
						agentDefinitionERC,
						item.externalReferenceCode
					)
				),
				...modelArmorTemplateDiff.toRemove.map((item) =>
					deleteAgentDefinitionToModelArmorTemplates(
						agentDefinitionERC,
						item.externalReferenceCode
					)
				),
			];

			if (requests.length) {
				await Promise.all(requests);

				contentRetrievers.syncToInitial();
				modelArmorTemplates.syncToInitial();
			}
		},
		[contentRetrievers, modelArmorTemplates]
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
	} = useFormik<AgentDefinition>({
		initialValues: {
			...DEFAULT_AGENT_DEFINITION,
			r_accountToAIHubAgentDefinitions_accountEntryERC:
				accountEntryExternalReferenceCode,
		},
		onSubmit: async (formValues) => {
			try {
				const response = await putAgentDefinition(formValues);

				if (formValues.externalReferenceCode) {
					await syncRelationships(formValues.externalReferenceCode);
				}

				if (response?.status?.label === 'approved') {
					openToast({
						message: Liferay.Language.get(
							'agent-saved-successfully'
						),
						type: 'success',
					});
				}
				else {
					openToast({
						message: Liferay.Language.get('failed-to-save-agent'),
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
				Record<keyof AgentDefinition, string>
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

			if (!formValues.description) {
				validationErrors.description = Liferay.Language.get('required');
			}

			if (!formValues.inputVariables) {
				validationErrors.inputVariables =
					Liferay.Language.get('required');
			}

			if (!formValues.outputVariable) {
				validationErrors.outputVariable =
					Liferay.Language.get('required');
			}

			if (!formValues.workflowDefinitionName) {
				validationErrors.workflowDefinitionName =
					Liferay.Language.get('required');
			}

			return validationErrors;
		},
	});

	const setField = useCallback(
		<K extends keyof AgentDefinition>(
			field: K,
			value: AgentDefinition[K]
		) => {
			setFieldValue(field as string, value);
		},
		[setFieldValue]
	);

	const {
		reset: resetContentRetrievers,
		setSourceList: setContentRetrieversSourceList,
	} = contentRetrievers;
	const {
		reset: resetModelArmorTemplates,
		setSourceList: setModelArmorTemplatesSourceList,
	} = modelArmorTemplates;

	useEffect(() => {
		async function fetchContentRetrieversList() {
			try {
				const response = await getContentRetrievers();

				setContentRetrieversSourceList(response.items || []);
			}
			catch (error) {
				console.error(error);
			}
		}

		async function fetchModelArmorTemplatesList() {
			try {
				const response = await getModelArmorTemplates();

				setModelArmorTemplatesSourceList(response.items || []);
			}
			catch (error) {
				console.error(error);
			}
		}

		fetchContentRetrieversList();
		fetchModelArmorTemplatesList();
	}, [setContentRetrieversSourceList, setModelArmorTemplatesSourceList]);

	useEffect(() => {
		async function fetchFormData() {
			if (!externalReferenceCode) {
				return;
			}

			try {
				const agentDefinition = await getAgentDefinition(
					externalReferenceCode
				);

				setValues({
					active: agentDefinition.active,
					description: agentDefinition.description,
					externalReferenceCode:
						agentDefinition.externalReferenceCode,
					inputVariables: agentDefinition.inputVariables,
					outputVariable: agentDefinition.outputVariable,
					r_accountToAIHubAgentDefinitions_accountEntryERC:
						agentDefinition.r_accountToAIHubAgentDefinitions_accountEntryERC,
					title_i18n: agentDefinition.title_i18n,
					workflowDefinitionName:
						agentDefinition.workflowDefinitionName,
				});

				resetContentRetrievers(
					agentDefinition.agentDefinitionsToContentRetrievers || []
				);
				resetModelArmorTemplates(
					agentDefinition.agentDefinitionsToModelArmorTemplates || []
				);
			}
			catch (error) {
				openToast({
					message: Liferay.Language.get('failed-to-load-agent-data'),
					type: 'danger',
				});
			}
		}

		fetchFormData();
	}, [
		accountEntryExternalReferenceCode,
		externalReferenceCode,
		readOnly,
		resetContentRetrievers,
		resetModelArmorTemplates,
		setValues,
	]);

	return {
		contentRetrievers,
		errors,
		handleBlur,
		handleSubmit,
		isSubmitting,
		modelArmorTemplates,
		setField,
		setFieldTouched,
		touched,
		values,
	};
}
