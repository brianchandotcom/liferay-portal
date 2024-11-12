/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSimpleInputModal} from 'frontend-js-web';

export function WorkflowTaskAction({
	namespace: portletNamespace,
	randomId,
	workflowTasks,
}) {
	const onTaskClickFn = (event) => {
		event.preventDefault();

		const icon = event.currentTarget;

		openSimpleInputModal({
			buttonSubmitLabel: Liferay.Language.get('done'),
			center: true,
			dialogTitle: icon.text,
			formSubmitURL: icon.href,
			mainFieldComponent: 'textarea',
			mainFieldLabel: Liferay.Language.get('comment'),
			mainFieldName: 'comment',
			mainFieldPlaceholder: Liferay.Language.get('comment'),
			namespace: portletNamespace,
			onFormSuccess: () => window.location.reload(),
			required: false,
			size: 'lg',
		});
	};

	workflowTasks.forEach((workflowTask) => {
		const workflowTaskElement = document.getElementById(
			`${portletNamespace}${randomId}${workflowTask}taskChangeStatusLink`
		);
		workflowTaskElement?.addEventListener('click', onTaskClickFn);
	});

	return {
		dispose() {
			workflowTasks?.forEach((workflowTask) => {
				const workflowTaskElement = document.getElementById(
					`${portletNamespace}${randomId}${workflowTask}taskChangeStatusLink`
				);

				workflowTaskElement?.removeEventListener(
					'click',
					onTaskClickFn
				);
			});
		},
	};
}

export {default as WorkFlowTaskActionDropdownPropsTransformer} from './WorkFlowTaskActionDropdownPropsTransformer';
