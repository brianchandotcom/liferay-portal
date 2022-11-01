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
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import TimerAction from './TimerAction';
import TimerDuration from './TimerDuration';
import TimerInfo from './TimerInfo';

const Timer = ({
	description,
	duration,
	durationScale,
	name,
	recurrence,
	recurrenceScale,
	sectionsLength,
	setContentName,
	setErrors,
	setTimerSections,
	timerActions,
	timerIdentifier,
	timersIndex,
}) => {
	const {selectedItem} = useContext(DiagramBuilderContext);
	const [actionSections, setActionSections] = useState(
		timerActions?.length
			? timerActions
			: [{actionType: 'timerActions', identifier: `${Date.now()}-0`}]
	);

	useEffect(() => {
		if (actionSections.length) {
			const filteredActionSections = [];

			actionSections.forEach((section) => {
				if (
					Object.keys(section).filter(
						(key) => key !== 'identifier' && key !== 'actionType'
					).length
				) {
					filteredActionSections.push(section);
				}
			});

			if (filteredActionSections.length) {
				setTimerSections((previousSections) => {
					const updatedSections = [...previousSections];
					const section = previousSections.find(
						({identifier}) => identifier === timerIdentifier
					);

					section.timerActions = filteredActionSections;
					updatedSections.splice(timersIndex, 1, section);

					return updatedSections;
				});
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [actionSections]);

	const deleteTimer = () => {
		setTimerSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== timerIdentifier
			);

			return newSections;
		});
	};

	const handleClickNew = (prev) => [
		...prev,
		{
			identifier: `${Date.now()}-${prev.length}`,
		},
	];

	return (
		<div className="panel">
			<TimerInfo
				description={description}
				name={name}
				selectedItem={selectedItem}
				setTimerSections={setTimerSections}
				timerIdentifier={timerIdentifier}
				timersIndex={timersIndex}
			/>

			<TimerDuration
				duration={duration}
				durationScale={durationScale}
				recurrence={recurrence}
				recurrenceScale={recurrenceScale}
				selectedItem={selectedItem}
				setTimerSections={setTimerSections}
				timerIdentifier={timerIdentifier}
				timersIndex={timersIndex}
			/>

			{actionSections.map((actionData, index) => (
				<TimerAction
					actionData={actionData}
					actionSectionsIndex={index}
					key={`section-${actionData.identifier}`}
					reassignments={actionSections.some(
						({actionType}) => actionType === 'reassignments'
					)}
					sectionsLength={actionSections?.length}
					setActionSections={setActionSections}
					setContentName={setContentName}
					setErrors={setErrors}
					timersIndex={timersIndex}
				/>
			))}

			<div className="sheet-subtitle" />

			<div className="autofit-float autofit-padded-no-gutters-x autofit-row autofit-row-center mb-3">
				<div className="autofit-col">
					<ClayButton
						displayType="secondary"
						onClick={() => {
							setTimerSections((prev) => handleClickNew(prev));
						}}
					>
						{Liferay.Language.get('new-timer')}
					</ClayButton>
				</div>

				{sectionsLength > 1 && (
					<div className="autofit-col autofit-col-end">
						<ClayButtonWithIcon
							className="delete-button"
							displayType="unstyled"
							onClick={deleteTimer}
							symbol="trash"
						/>
					</div>
				)}
			</div>
		</div>
	);
};

Timer.propTypes = {
	sectionsLength: PropTypes.number.isRequired,
	setTimerSections: PropTypes.func.isRequired,
	timerActions: PropTypes.array,
	timerIdentifier: PropTypes.string.isRequired,
	timersIndex: PropTypes.number.isRequired,
};

export default Timer;
