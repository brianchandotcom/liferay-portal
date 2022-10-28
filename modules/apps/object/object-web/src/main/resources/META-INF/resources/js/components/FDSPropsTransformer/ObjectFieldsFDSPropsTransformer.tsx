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

import classNames from 'classnames';
import React from 'react';

interface ObjectFieldSourceDataRenderer {
	value: boolean;
}

function ObjectFieldSourceDataRenderer({value}: ObjectFieldSourceDataRenderer) {
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

export default function ObjectFieldsFDSPropsTransformer({...otherProps}) {
	return {
		...otherProps,
		customDataRenderers: {
			objectFieldSourceDataRenderer: ObjectFieldSourceDataRenderer,
		},
	};
}
