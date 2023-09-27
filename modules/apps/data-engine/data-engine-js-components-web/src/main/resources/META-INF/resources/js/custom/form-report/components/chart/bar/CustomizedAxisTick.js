/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
