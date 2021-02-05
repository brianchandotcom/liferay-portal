/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {createActionURL} from 'frontend-js-web';

const ACTIONS = {
	deletePasswordPolicies(portletNamespace, basePortletURL) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				form.setAttribute('method', 'post');

				const passwordPolicyIdsInput = form.querySelector(
					`#${portletNamespace}passwordPolicyIds`
				);

				if (passwordPolicyIdsInput) {
					passwordPolicyIdsInput.setAttribute(
						'value',
						Liferay.Util.listCheckedExcept(
							form,
							`${portletNamespace}allRowIds`
						)
					);
				}

				const lifecycleInput = form.querySelector('#p_p_lifecycle');

				if (lifecycleInput) {
					lifecycleInput.setAttribute('value', '1');
				}

				const actionURL = createActionURL(basePortletURL, {
					'javax.portlet.action': 'deletePasswordPolicies',
				});

				submitForm(form, actionURL.toString());
			}
		}
	},
};

export default function propsTransformer({
	additionalProps,
	initialActionDropdownItems,
	portletNamespace,
	...props
}) {
	return {
		...props,
		initialActionDropdownItems: initialActionDropdownItems.map((item) => {
			return {
				...item,
				onClick() {
					const action = item.data.action;
					const {basePortletURL} = additionalProps;

					ACTIONS[action](portletNamespace, basePortletURL);
				},
			};
		}),
		onActionButtonClick() {
			const {basePortletURL} = additionalProps;
			ACTIONS.deletePasswordPolicies(portletNamespace, basePortletURL);
		},
	};
}
