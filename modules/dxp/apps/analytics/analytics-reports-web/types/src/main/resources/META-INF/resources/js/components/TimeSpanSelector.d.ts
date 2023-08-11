/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TimeSpan} from '../context/ChartStateContext';
interface TimeSpanOption {
	key: TimeSpan;
	label: string;
}
interface Props {
	disabledNextTimeSpan: boolean;
	disabledPreviousPeriodButton: boolean;
	timeSpanKey: TimeSpan;
	timeSpanOptions: TimeSpanOption[];
}
export default function TimeSpanSelector({
	disabledNextTimeSpan,
	disabledPreviousPeriodButton,
	timeSpanKey,
	timeSpanOptions,
}: Props): JSX.Element;
export {};
