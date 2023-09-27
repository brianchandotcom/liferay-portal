/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';
import CurrentAssignments from './CurrentAssignments';

const AssignmentsSummary = ({setContentName}) => {
	const {selectedItem} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('assignments')}>
			{!selectedItem?.data?.assignments ? (
				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() => setContentName('assignments')}
				>
					{Liferay.Language.get('new')}
				</ClayButton>
			) : (
				<CurrentAssignments
					assignments={selectedItem.data.assignments}
					setContentName={setContentName}
				/>
			)}
		</SidebarPanel>
	);
};

AssignmentsSummary.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default AssignmentsSummary;
