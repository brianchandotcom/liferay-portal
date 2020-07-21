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
import {Text} from 'recharts';

import ellipsize from '../../../utils/ellipsize.es';

const MAX_SIZE = 34;

const minimize = (size) => (size > 5 ? MAX_SIZE / 2 : MAX_SIZE);

export default ({payload, size, x, y}) => (
	<Text
		textAnchor="middle"
		verticalAnchor="start"
		width={220 - size * 17}
		x={x}
		y={y}
	>
		{payload.value.length > MAX_SIZE
			? ellipsize(payload.value, minimize(size))
			: payload.value}
	</Text>
);
