/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as EditorVariant from '../components/PageRenderer/EditorVariant.es';

export function mergeVariants(editable, {defaults, overrides, variant}) {
	return {
		...defaults,
		...(editable ? EditorVariant : {}),
		...variant,
		...overrides,
	};
}
