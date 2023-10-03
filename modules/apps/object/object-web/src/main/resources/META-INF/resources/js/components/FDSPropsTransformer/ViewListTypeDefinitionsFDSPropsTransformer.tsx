/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MultiselectPicklistDataRenderer from './FDSDataRenderers/MultiselectPicklistDataRenderer';
import SourceDataRenderer from './FDSDataRenderers/SourceDataRenderer';

export default function ViewListTypeDefinitionsFDSPropsTransformer({
	...otherProps
}) {
	return {
		...otherProps,
		customDataRenderers: {
			multiselectPicklistDataRenderer: MultiselectPicklistDataRenderer,
			sourceDataRenderer: SourceDataRenderer,
		},
	};
}
