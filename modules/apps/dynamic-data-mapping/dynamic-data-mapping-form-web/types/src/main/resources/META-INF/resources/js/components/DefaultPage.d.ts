/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './DefaultPage.scss';
declare const DefaultPage: React.FC<IProps>;
export default DefaultPage;
interface IProps {
	dataEngineModule: string;
	displayChartAsTable: boolean;
	formDescription?: string;
	formReportDataURL?: string;
	formTitle: string;
	pageDescription: string;
	pageTitle: string;
	showPartialResultsToRespondents?: boolean;
	showSubmitAgainButton?: boolean;
}
