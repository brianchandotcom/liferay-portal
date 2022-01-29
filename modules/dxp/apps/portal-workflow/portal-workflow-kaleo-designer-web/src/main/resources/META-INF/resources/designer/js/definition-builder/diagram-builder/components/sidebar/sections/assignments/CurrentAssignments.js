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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import React, {useContext, useEffect} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import {options} from './SelectAssignment';

const CurrentAssignments = ({assignments, setContentName}) => {
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

	const optionFilter = (option) => {
		if (
			assignments.assignmentType[0] === 'user' &&
			!Object.keys(assignments).includes('emailAddress')
		) {
			return option.assignmentType === 'assetCreator';
		}

		return option.assignmentType === assignments.assignmentType[0];
	};

	return (
		<ClayLayout.ContentCol className="current-assignments-area" float>
			<ClayLayout.Row
				className="current-assignments-row"
				justify="between"
			>
				<ClayLink
					button={false}
					displayType="secondary"
					href="#"
					onClick={() => setContentName('assignments')}
				>
					{options.find(optionFilter)?.label}
				</ClayLink>

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

export default CurrentAssignments;
