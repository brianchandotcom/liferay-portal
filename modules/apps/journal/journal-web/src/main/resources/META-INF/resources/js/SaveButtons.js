/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

export default function SaveButtons({
	articleId,
	defaultLanguageId,
	editingDefaultValues,
	portletNamespace,
	publishButtonLabel,
	saveButtonLabel,
	selectedLanguageId,
}) {
	const handleButtonClick = (type) => {
		document
			.querySelectorAll('.journal-alert-container')
			.forEach((alertElement) => {
				alertElement?.parentElement?.removeChild(alertElement);
			});

		const workflowActionInput = document.getElementById(
			`${portletNamespace}workflowAction`
		);

		if (type === 'publish') {
			workflowActionInput.value = Liferay.Workflow.ACTION_PUBLISH;
		}

		const actionInput = document.getElementById(
			`${portletNamespace}javax-portlet-action`
		);

		if (editingDefaultValues) {
			Liferay.component(`${portletNamespace}dataEngineLayoutRenderer`)
				.reactComponentRef.current.getFields()
				.forEach((field) => {
					field.required = false;
				});

			actionInput.value = articleId
				? '/journal/update_data_engine_default_values'
				: '/journal/add_data_engine_default_values';
		}
		else {
			articleId = document.getElementById(`${portletNamespace}articleId`)
				.value;

			actionInput.value = articleId
				? '/journal/update_article'
				: '/journal/add_article';
		}

		const descriptionInputComponent = Liferay.component(
			`${portletNamespace}descriptionMapAsXML`
		);
		const titleInputComponent = Liferay.component(
			`${portletNamespace}titleMapAsXML`
		);

		[titleInputComponent, descriptionInputComponent].forEach(
			(inputComponent) => {
				const translatedLanguages = inputComponent.get(
					'translatedLanguages'
				);

				if (
					!translatedLanguages.has(selectedLanguageId) &&
					selectedLanguageId !== defaultLanguageId
				) {
					inputComponent.updateInput('');

					Liferay.Form.get(`${portletNamespace}fm1`).removeRule(
						`${portletNamespace}${inputComponent.get('id')}`,
						'required'
					);
				}
			}
		);
	};

	return (
		<>
			{!Liferay.FeatureFlags['LPS-141392'] && !editingDefaultValues ? (
				<ClayButton
					className="mr-1"
					displayType="secondary"
					onClick={() => handleButtonClick()}
					type="submit"
				>
					{saveButtonLabel}
				</ClayButton>
			) : null}
			<ClayButton
				onClick={() => handleButtonClick('publish')}
				type="submit"
			>
				{publishButtonLabel}
			</ClayButton>
		</>
	);
}
