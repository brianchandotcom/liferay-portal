/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './PanelNavigator.scss';
import React from 'react';
import type {ImpactValue} from 'axe-core';
declare type PanelNavigatorProps = {
	helpUrl: string;
	impact?: ImpactValue;
	onBack: (event: React.MouseEvent<HTMLButtonElement>) => void;
	tags: Array<string>;
	title: string;
};
declare function PanelNavigator({
	helpUrl,
	impact,
	onBack,
	tags,
	title,
}: PanelNavigatorProps): JSX.Element;
export default PanelNavigator;
