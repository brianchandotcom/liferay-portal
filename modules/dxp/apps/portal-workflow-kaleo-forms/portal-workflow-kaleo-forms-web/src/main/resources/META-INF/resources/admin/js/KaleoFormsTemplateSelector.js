/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
