/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';
import {options} from './SelectAssignment';

const Assignments = ({setContentName}) => {
	const {selectedItem} = useContext(DiagramBuilderContext);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('assignments')}>
			{!selectedItem.data.assignments ? (
				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() => setContentName('assignments')}
				>
					{Liferay.Language.get('new')}
				</ClayButton>
			) : (
				<CurrentAssignments
					assignments={{...selectedItem.data.assignments}}
				/>
			)}
		</SidebarPanel>
	);
};

const CurrentAssignments = ({assignments}) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);
	const deleteCurrentAssignments = () => {
		setSelectedItem((previousValue) => ({
			...previousValue,
			data: {
				...previousValue.data,
				assignments: null,
			},
		}));
	};

	return (
		<ClayLayout.ContentCol className="current-assignments-area" float>
			<ClayLayout.Row
				className="current-assignments-row"
				justify="between"
			>
				<span>
					{
						options.find(
							(option) =>
								option.assignmentType ===
								assignments.assignmentType[0]
						)?.label
					}

					{/* {Liferay.Language.get(assignments.assignmentType[0])} */}
				</span>

				<ClayButtonWithIcon
					className="delete-button text-secondary trash-button"
					displayType="unstyled"
					onClick={deleteCurrentAssignments}
					symbol="trash"
				/>
			</ClayLayout.Row>
		</ClayLayout.ContentCol>
	);
};

Assignments.propTypes = {
	setContentName: PropTypes.func.isRequired,
};

export default Assignments;
