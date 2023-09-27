/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';
import CurrentTimers from './CurrentTimers';

const TimersSummary = ({setContentName}) => {
	const {selectedItem} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('timers')}>
			{!selectedItem?.data.taskTimers ? (
				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() => {
						setContentName('timers');
					}}
				>
					{Liferay.Language.get('new')}
				</ClayButton>
			) : (
				<CurrentTimers
					setContentName={setContentName}
					taskTimers={selectedItem.data.taskTimers}
				/>
			)}
		</SidebarPanel>
	);
};

TimersSummary.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default TimersSummary;
