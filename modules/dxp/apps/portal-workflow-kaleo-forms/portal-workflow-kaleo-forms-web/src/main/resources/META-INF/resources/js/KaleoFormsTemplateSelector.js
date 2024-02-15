/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	createRenderURL,
	fetch,
	navigate,
	objectToFormData,
	openSelectionModal,
} from 'frontend-js-web';

export default function ({
	backURL,
	itemSelectorURL,
	portletNamespace,
	saveInPortletSessionURL,
}) {
	window[`${portletNamespace}selectFormTemplate`] = (
		classPK,
		mode,
		workflowDefinition,
		workflowTaskName
	) => {
		const url = createRenderURL(itemSelectorURL, {
			classPK,
			mode,
		});

		openSelectionModal({
			iframeBodyCssClass: '',
			onSelect: (selectedItem) => {
				const data = {};

				data[
					portletNamespace + 'kaleoProcessLinkDDMStructureId'
				] = classPK;
				data[portletNamespace + 'kaleoProcessLinkDDMTemplateId'] =
					selectedItem.ddmtemplateid;
				data[
					portletNamespace + 'kaleoProcessLinkWorkflowDefinition'
				] = workflowDefinition;
				data[
					portletNamespace + 'kaleoProcessLinkWorkflowTaskName'
				] = workflowTaskName;

				fetch(saveInPortletSessionURL, {
					body: objectToFormData(data),
					method: 'POST',
				}).then((response) => {
					if (response.ok) {
						navigate(decodeURIComponent(backURL));
					}
				});
			},
			selectEventName: 'selectStructure',
			title: Liferay.Language.get('form'),
			url: String(url),
		});
	};
}
