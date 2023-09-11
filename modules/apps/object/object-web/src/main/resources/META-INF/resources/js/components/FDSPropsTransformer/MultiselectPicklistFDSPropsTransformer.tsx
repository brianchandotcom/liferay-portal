/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SourceDataRenderer from './FDSDataRenderers/SourceDataRenderer';

interface MultiselectPicklistDataRendererProps {
	value: {
		key: string;
		name: string;
		name_i18n: string;
	}[];
}

export default function MultiselectPicklistFDSPropsTransformer({
	...otherProps
}) {
	return {
		...otherProps,
		customDataRenderers: {
			multiselectPicklistDataRenderer: ({
				value,
			}: MultiselectPicklistDataRendererProps) =>
				value ? value.map((v) => v.name).join(', ') : '',
			sourceDataRenderer: SourceDataRenderer,
		},
	};
}
