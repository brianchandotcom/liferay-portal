/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface ObjectActionStatusDataRendererProps {
	value: {
		code: number;
		label: string;
		label_i18n: string;
	};
}

const getStatusCssClass = (label: string) => {
	switch (label) {
		case 'failed':
			return 'label-danger';
		case 'never-ran':
			return 'label-info';
		case 'success':
			return 'label-success';
		default:
			return '';
	}
};

function ObjectActionStatusDataRenderer({
	value,
}: ObjectActionStatusDataRendererProps) {
	return value ? (
		<strong className={`label ${getStatusCssClass(value.label)}`}>
			{value.label_i18n}
		</strong>
	) : null;
}

export default function ObjectActionsFDSPropsTransformer({...otherProps}) {
	return {
		...otherProps,
		customDataRenderers: {
			objectActionStatusDataRenderer: ObjectActionStatusDataRenderer,
		},
	};
}
