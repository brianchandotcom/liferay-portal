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

import {createActionURL, getCheckedCheckboxes} from 'frontend-js-web';

import openConfirm from './openConfirm';

const ACTIONS = {
	deletePasswordPolicies(portletNamespace, basePortletURL) {
		openConfirm({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (!form) {
						return;
					}

					form.setAttribute('method', 'post');

					const passwordPolicyIdsInput = form.querySelector(
						`#${portletNamespace}passwordPolicyIds`
					);

					if (passwordPolicyIdsInput) {
						passwordPolicyIdsInput.setAttribute(
							'value',
							getCheckedCheckboxes(
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
			},
		});
	},
};

export default function propsTransformer({
	additionalProps,
	initialActionDropdownItems,
	portletNamespace,
	...props
}) {
	const {basePortletURL} = additionalProps;

	return {
		...props,
		initialActionDropdownItems: initialActionDropdownItems.map((item) => {
			return {
				...item,
				onClick() {
					const action = item?.data?.action;

					ACTIONS[action](portletNamespace, basePortletURL);
				},
			};
		}),
		onActionButtonClick() {
			ACTIONS.deletePasswordPolicies(portletNamespace, basePortletURL);
		},
	};
}
