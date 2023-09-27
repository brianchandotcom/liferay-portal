/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useState} from 'react';

import {DEFAULT_LANGUAGE} from '../../../../../source-builder/constants';
import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import Timer from './Timer';

const Timers = ({setContentName, setErrors}) => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);
	const [timerSections, setTimerSections] = useState([
		{identifier: `${Date.now()}-0`},
	]);

	useEffect(() => {
		const serializableSections = timerSections.filter(
			(section) =>
				section.duration &&
				section.durationScale &&
				section.timerActions
		);
		let taskTimers;
		if (serializableSections.length !== 0) {
			taskTimers = {
				blocking: serializableSections.map(
					({recurrence, recurrenceScale}) =>
						recurrence && recurrenceScale ? 'false' : 'true'
				),
				delay: serializableSections.map(
					({
						duration,
						durationScale,
						recurrence,
						recurrenceScale,
					}) => {
						const delay = {
							duration: [duration],
							scale: [durationScale],
						};
						if (recurrence && recurrenceScale) {
							delay.duration.push(recurrence);
							delay.scale.push(recurrenceScale);
						}

						return delay;
					}
				),
				description: serializableSections.map(
					({description}) => description
				),
				name: serializableSections.map(({name}) => name),
				reassignments: serializableSections.map(({timerActions}) => {
					const filteredTimerActions = timerActions.filter(
						({actionType}) => actionType === 'reassignments'
					);
					if (filteredTimerActions.length) {
						const reassignments = {};

						reassignments.assignmentType = filteredTimerActions.map(
							({assignmentType}) => assignmentType
						);

						if (
							reassignments.assignmentType[0] ===
							'resourceActions'
						) {
							reassignments.resourceAction = filteredTimerActions.map(
								({resourceAction}) => resourceAction
							);
						}
						else if (
							reassignments.assignmentType[0] === 'roleId'
						) {
							reassignments.roleId = filteredTimerActions.map(
								({roleId}) => roleId
							);
						}
						else if (
							reassignments.assignmentType[0] ===
							'scriptedReassignment'
						) {
							reassignments.assignmentType = [
								'scriptedAssignment',
							];
							reassignments.script = filteredTimerActions.map(
								({script}) => script
							)[0];

							reassignments.scriptLanguage = filteredTimerActions.map(
								({scriptLanguage}) => [
									scriptLanguage || DEFAULT_LANGUAGE,
								]
							)[0];
						}
						else if (
							reassignments.assignmentType[0] === 'user' &&
							Object.keys(filteredTimerActions[0]).includes(
								'users'
							)
						) {
							reassignments.emailAddress = filteredTimerActions[0].users.map(
								({emailAddress}) => emailAddress
							);
						}
						else if (
							reassignments.assignmentType[0] === 'roleType'
						) {

							// TO DO

						}

						return reassignments;
					}

					return {};
				}),
				timerActions: serializableSections.map(({timerActions}) => {
					const filteredTimerActions = timerActions.filter(
						({actionType}) => actionType === 'timerActions'
					);

					if (filteredTimerActions.length) {
						return {
							description: filteredTimerActions.map(
								({description}) => description
							),
							executionType: filteredTimerActions.map(
								({executionType}) => executionType
							),
							name: filteredTimerActions.map(({name}) => name),
							priority: filteredTimerActions.map(
								({priority}) => priority
							),
							script: filteredTimerActions.map(
								({script}) => script
							),
							scriptLanguage: filteredTimerActions.map(
								({scriptLanguage}) => scriptLanguage
							),
						};
					}

					return {};
				}),

				timerNotifications: serializableSections.map(() => ({})),
			};
		}
		else {
			taskTimers = null;
		}
		setSelectedItem((previousItem) => ({
			...previousItem,
			data: {...previousItem.data, taskTimers},
		}));

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [timerSections]);

	const getAllTimerActions = (index) => {
		const allTimerActions = {
			reassignments: Object.entries(
				selectedItem.data.taskTimers.reassignments[index]
			),
			timerActions: Object.entries(
				selectedItem.data.taskTimers.timerActions[index]
			),
			timerNotifications: Object.entries(
				selectedItem.data.taskTimers.timerNotifications[index]
			),
		};
		const sections = [];

		if (allTimerActions.reassignments.length) {
			const data = allTimerActions.reassignments;
			for (let index = 0; index < data[0][1].length; index++) {
				const section = {};

				section.actionType = 'reassignments';
				section.identifier = `${Date.now()}-${index}`;
				section.assignmentType = data.find(
					(entry) => entry[0] === 'assignmentType'
				)[1][index];

				if (section.assignmentType === `resourceActions`) {
					section.resourceAction = data.find(
						(entry) => entry[0] === 'resourceAction'
					)[1];
				}
				else if (section.assignmentType === 'roleId') {
					section.roleId = data.find(
						(entry) => entry[0] === 'roleId'
					)[1];
				}
				else if (section.assignmentType === 'scriptedAssignment') {
					section.assignmentType = 'scriptedReassignment';

					section.script = data.find(
						(entry) => entry[0] === 'script'
					)[1];
				}
				else if (
					section.assignmentType === 'user' &&
					data.some((entry) => entry[0] === 'emailAddress')
				) {
					section.users = data
						.find((entry) => entry[0] === 'emailAddress')[1]
						.map((email, index) => ({
							emailAddress: email,
							identifier: `${Date.now()}-${index}`,
						}));
				}
				else {
					section.assignmentType = 'assetCreator';
				}

				sections.push(section);
			}
		}
		const reassignmentsLength = sections.length;

		if (allTimerActions.timerActions.length) {
			const data = allTimerActions.timerActions;
			for (let index = 0; index < data[0][1].length; index++) {
				const section = {};

				section.actionType = 'timerActions';
				section.description = data.find(
					(entry) => entry[0] === 'description'
				)[1][index];
				section.executionType = data.find(
					(entry) => entry[0] === 'executionType'
				)[1][index];
				section.identifier = `${Date.now()}-${
					index + reassignmentsLength
				}`;
				section.name = data.find((entry) => entry[0] === 'name')[1][
					index
				];
				section.priority = data.find(
					(entry) => entry[0] === 'priority'
				)[1][index];
				section.script = data.find((entry) => entry[0] === 'script')[1][
					index
				];
				section.scriptLanguage = data.find(
					(entry) => entry[0] === 'scriptLanguage'
				)[1][index];
				sections.push(section);
			}
		}

		return sections;
	};

	useEffect(() => {
		if (selectedItem.data.taskTimers?.delay) {
			const deserializedSections = [];

			for (
				let index = 0;
				index < selectedItem.data.taskTimers.delay.length;
				index++
			) {
				const section = {
					description:
						selectedItem.data.taskTimers.description[index],
					duration:
						selectedItem.data.taskTimers.delay[index].duration[0],
					durationScale:
						selectedItem.data.taskTimers.delay[index].scale[0],
					identifier: `${Date.now()}-${index}`,
					name: selectedItem.data.taskTimers.name[index],
					timerActions: getAllTimerActions(index),
				};

				if (
					selectedItem.data.taskTimers.delay[index].duration
						.length === 2
				) {
					section.recurrence =
						selectedItem.data.taskTimers.delay[index].duration[1];
					section.recurrenceScale =
						selectedItem.data.taskTimers.delay[index].scale[1];
				}

				deserializedSections.push(section);
			}

			setTimerSections(deserializedSections);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return timerSections.map((timerData, index) => (
		<Timer
			description={timerData.description}
			duration={timerData.duration}
			durationScale={timerData.durationScale}
			key={`section-${timerData.identifier}`}
			name={timerData.name}
			recurrence={timerData.recurrence}
			recurrenceScale={timerData.recurrenceScale}
			sectionsLength={timerSections?.length}
			setContentName={setContentName}
			setErrors={setErrors}
			setTimerSections={setTimerSections}
			timerActions={timerData.timerActions}
			timerIdentifier={timerData.identifier}
			timersIndex={index}
		/>
	));
};

export default Timers;
