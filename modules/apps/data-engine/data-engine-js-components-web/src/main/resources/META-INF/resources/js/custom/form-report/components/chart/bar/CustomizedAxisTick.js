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

import ellipsize from '../../../utils/ellipsize';

const MAX_TEXT_SIZE = 34;
const MAX_WIDTH = 220;
const MIN_TICKS_TO_MINIMIZE = 5;
const MIN_TICKS_TO_ELLIPSIZE = 2;
const SPACING_SIZE = 17;

const minimize = (ticksNumber) =>
	ticksNumber > MIN_TICKS_TO_MINIMIZE
		? MAX_TEXT_SIZE / Math.log(ticksNumber)
		: MAX_TEXT_SIZE;

export default function CustomizedAxisTick({payload, ticksNumber, x, y}) {
	return (
		<Text
			textAnchor="middle"
			verticalAnchor="start"
			width={MAX_WIDTH - ticksNumber * SPACING_SIZE}
			x={x}
			y={y}
		>
			{payload.value.length > MAX_TEXT_SIZE &&
			ticksNumber > MIN_TICKS_TO_ELLIPSIZE
				? ellipsize(payload.value, minimize(ticksNumber))
				: payload.value}
		</Text>
	);
}
