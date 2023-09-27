/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React, {useContext} from 'react';

import ChartContext from '../ChartContext';
import {VIEWS} from '../utils/constants';

function ViewSelector() {
	const {currentView, updateCurrentView} = useContext(ChartContext);

	const viewTypes = VIEWS.map((view) => ({
		active: view.id === currentView.id,
		label: view.label,
		onClick: () => updateCurrentView(view),
		symbolLeft: view.symbol,
	}));

	return (
		<ClayDropDownWithItems
			items={viewTypes}
			trigger={
				<ClayButtonWithIcon
					className="nav-link nav-link-monospaced"
					displayType="unstyled"
					monospaced
					symbol={currentView.symbol}
				/>
			}
		/>
	);
}

export default ViewSelector;
