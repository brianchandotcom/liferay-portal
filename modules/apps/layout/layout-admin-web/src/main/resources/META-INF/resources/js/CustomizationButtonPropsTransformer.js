/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function propsTransformer({
	additionalProps: {portletNamespace},
	...otherProps
}) {
	return {
		...otherProps,
		onClick: () => {
			const controlMenu = document.querySelector(
				`#${portletNamespace}customizationBar .control-menu-level-2`
			);

			controlMenu.classList.toggle('open');
		},
	};
}
