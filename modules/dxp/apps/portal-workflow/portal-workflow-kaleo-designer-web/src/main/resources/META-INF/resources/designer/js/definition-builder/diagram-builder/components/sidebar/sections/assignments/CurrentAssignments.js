/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import React, {useContext, useEffect, useState} from 'react';

import lang from '../../../../../util/lang';
import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import {sortElements} from '../utils';
import {options} from './select-assignment/SelectAssignment';
import {getAssignmentType} from './utils';

const CurrentAssignments = ({assignments, setContentName}) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);
	const assignmentType = getAssignmentType(assignments);

	const [assignmentsDetails, setAssignmentsDetails] = useState(null);

	const deleteCurrentAssignments = () => {
		setSelectedItem((previousValue) => ({
			...previousValue,
			data: {
				...previousValue.data,
				assignments: null,
			},
		}));
	};

	const optionFilter = (option) => option.assignmentType === assignmentType;

	useEffect(() => {
		if (assignmentType === 'resourceActions') {
			const resourceActionsArray = assignments.resourceAction.split(
				/(?:,| )+/
			);

			setAssignmentsDetails({
				assignmentsCount: resourceActionsArray.length,
				firstName: resourceActionsArray[0],
			});
		}
		else if (assignmentType === 'roleId') {
			setAssignmentsDetails({
				assignmentsCount: 1,
				firstName: assignments.sectionsData.name,
			});
		}
		else if (assignmentType === 'user') {
			sortElements(assignments.sectionsData, 'name');
			setAssignmentsDetails({
				assignmentsCount: assignments.sectionsData.length,
				firstName: assignments.sectionsData[0].name.split(' ')[0],
			});
		}
	}, [assignmentType, assignments]);

	const getAssignmentsDetails = () => {
		if (assignmentType === 'assetCreator') {
			return [''];
		}
		else if (assignmentsDetails) {
			const result = [assignmentsDetails.firstName || ''];

			if (assignmentsDetails.assignmentsCount !== 1) {
				result.push(
					' ' +
						lang.sub(Liferay.Language.get('and-x-more'), [
							assignmentsDetails.assignmentsCount - 1,
						])
				);
			}

			return result;
		}
		else {
			if (assignmentType === 'roleType') {
				const assignmentSummary = assignments.roleName;

				const result = [assignmentSummary?.[0]];

				if (assignmentSummary?.length > 1) {
					result.push(
						' ' +
							lang.sub(Liferay.Language.get('and-x-more'), [
								assignmentSummary?.length - 1,
							])
					);
				}

				return result;
			}

			return [assignments[Object.keys(assignments)[1]]];
		}
	};

	return (
		<ClayLayout.ContentCol className="current-node-data-area" float>
			<ClayLayout.Row className="current-node-data-row" justify="between">
				<ClayLink
					button={false}
					displayType="secondary"
					href="#"
					onClick={() => setContentName('assignments')}
				>
					<div className="d-flex">
						{options.find(optionFilter)?.label}

						{assignmentType !== 'assetCreator' && ':'}

						{getAssignmentsDetails().map((content, index) => (
							<div
								className="ml-2 truncate-container"
								key={index}
							>
								{content}
							</div>
						))}
					</div>
				</ClayLink>

				<ClayButtonWithIcon
					className="delete-button text-secondary trash-button"
					disabled
					displayType="unstyled"
					onClick={deleteCurrentAssignments}
					symbol="trash"
				/>
			</ClayLayout.Row>
		</ClayLayout.ContentCol>
	);
};

export default CurrentAssignments;
