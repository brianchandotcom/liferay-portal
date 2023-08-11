/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TimeSpan} from '../context/ChartStateContext';
interface ViewURL {
	default: boolean;
	languageId: string;
	languageLabel: string;
	selected: boolean;
	viewURL: string;
}
interface Props {
	onSelectedLanguageClick: (
		url: string,
		timeSpanKey: TimeSpan | undefined,
		timeSpanOffset: number
	) => void;
	viewURLs: ViewURL[];
}
export default function Translation({
	onSelectedLanguageClick,
	viewURLs,
}: Props): JSX.Element;
export {};
