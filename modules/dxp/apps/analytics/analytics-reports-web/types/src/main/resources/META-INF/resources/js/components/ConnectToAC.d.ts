/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MouseEventHandler} from 'react';
interface Props {
	analyticsURL: string;
	hideAnalyticsReportsPanelURL: string;
	isAnalyticsConnected: boolean;
	onHideAnalyticsReportsPanelClick: MouseEventHandler;
	pathToAssets: string;
}
export default function ConnectToAC({
	analyticsURL,
	isAnalyticsConnected,
	hideAnalyticsReportsPanelURL,
	pathToAssets,
	onHideAnalyticsReportsPanelClick,
}: Props): JSX.Element;
export {};
