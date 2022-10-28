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
