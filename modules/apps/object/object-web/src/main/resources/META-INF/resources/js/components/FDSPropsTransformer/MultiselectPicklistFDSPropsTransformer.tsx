/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

interface MultiselectPicklistDataRendererProps {
	value: {
		key: string;
		name: string;
		name_i18n: string;
	}[];
}

interface SourceDataRenderer {
	value: boolean;
}

function SourceDataRenderer({value}: SourceDataRenderer) {
	return (
		<strong
			className={classNames(
				value ? 'label-info' : 'label-warning',
				'label'
			)}
		>
			{value
				? Liferay.Language.get('system')
				: Liferay.Language.get('custom')}
		</strong>
	);
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
