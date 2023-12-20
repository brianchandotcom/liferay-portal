/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateRanks from './updateRanks';

export default function propsTransformer({
	additionalProps: {defaultLanguageId},
	portletNamespace,
	...props
}) {
	return {
		...props,
		onChange() {
			updateRanks(defaultLanguageId, portletNamespace);
		},
	};
}
