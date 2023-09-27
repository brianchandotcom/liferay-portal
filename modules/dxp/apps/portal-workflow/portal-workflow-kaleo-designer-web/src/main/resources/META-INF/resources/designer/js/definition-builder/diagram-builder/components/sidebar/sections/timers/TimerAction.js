/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

import SidebarPanel from '../../SidebarPanel';
import ActionTypeAction from './select-action/ActionTypeAction';
import ActionTypeNotification from './select-action/ActionTypeNotification';
import ActionTypeReassignment from './select-action/ActionTypeReassignment';
import SelectActionType from './select-action/SelectActionType';

const actionSectionComponents = {
	reassignments: ActionTypeReassignment,
	timerActions: ActionTypeAction,
	timerNotifications: ActionTypeNotification,
};

const TimerAction = ({
	actionData,
	actionSectionsIndex,
	reassignments,
	sectionsLength,
	setActionSections,
	setContentName,
	setErrors,
	timersIndex,
}) => {
	const ActionSectionComponent =
		actionSectionComponents[actionData.actionType];

	const deleteSection = (identifier) => {
		setActionSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			return newSections;
		});
	};

	const handleClickNew = (prev) => [
		...prev,
		{
			actionType: 'timerActions',
			identifier: `${Date.now()}-${prev.length}`,
		},
	];

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('action')}>
			<SelectActionType
				actionSectionsIndex={actionSectionsIndex}
				actionType={actionData.actionType}
				reassignments={reassignments}
				setActionSections={setActionSections}
				timersIndex={timersIndex}
			/>

			<ActionSectionComponent
				actionData={actionData}
				actionSectionsIndex={actionSectionsIndex}
				actionType={actionData.actionType}
				key={`section-${actionData.identifier}`}
				sectionsLength={sectionsLength}
				setActionSections={setActionSections}
				setContentName={setContentName}
				setErrors={setErrors}
				timersIndex={timersIndex}
			/>

			<div className="autofit-float autofit-padded-no-gutters-x autofit-row autofit-row-center mb-3">
				<div className="autofit-col">
					<ClayButton
						className="mr-3"
						displayType="secondary"
						onClick={() =>
							setActionSections((prev) => handleClickNew(prev))
						}
					>
						{Liferay.Language.get('new-action')}
					</ClayButton>
				</div>

				<div className="autofit-col autofit-col-end">
					{sectionsLength > 1 && (
						<ClayButtonWithIcon
							className="delete-button"
							displayType="unstyled"
							onClick={() => deleteSection(actionData.identifier)}
							symbol="trash"
						/>
					)}
				</div>
			</div>
		</SidebarPanel>
	);
};

TimerAction.propTypes = {
	actionSectionsIndex: PropTypes.number.isRequired,
	identifier: PropTypes.string,
	sectionsLength: PropTypes.number.isRequired,
	setActionSections: PropTypes.func.isRequired,
	timersIndex: PropTypes.number.isRequired,
};

export default TimerAction;
