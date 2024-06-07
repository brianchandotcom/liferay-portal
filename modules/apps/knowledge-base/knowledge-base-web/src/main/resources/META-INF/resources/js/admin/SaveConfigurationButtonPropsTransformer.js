/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function propsTransformer({
	additionalProps: {emailParam},
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				if (emailParam) {
					const emailParamEditor =
						window[`${portletNamespace}${emailParam}`].getHTML();

					const emailParamBody = emailParam + 'Body';

					document.getElementById(
						`${portletNamespace}${emailParamBody}`
					).value = emailParamEditor;
				}

				submitForm(form);
			}
		},
	};
}
