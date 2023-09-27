/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {titleCase} from '../../../../../util/utils';
import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';

const CurrentTimers = ({setContentName, taskTimers}) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);

	const [sections, setSections] = useState([]);

	useEffect(() => {
		const currentSections = [];

		taskTimers.delay.forEach((timerDelay, index) => {
			currentSections.push({
				duration: timerDelay.duration[0],
				durationScale: timerDelay.scale[0],
				identifier: `${Date.now()}-${index}`,
				recurrence: timerDelay.duration[1],
				recurrenceScale: timerDelay.scale[1],
			});
		});
		setSections(currentSections);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const deleteCurrentTimer = (currentIdentifier, currentIndex) => {
		setSelectedItem((previousValue) => {
			if (previousValue.data.taskTimers?.delay?.length === 1) {
				return {
					...previousValue,
					data: {
						...previousValue.data,
						taskTimers: null,
					},
				};
			}
			const updatedTimers = {};

			for (const entry of Object.entries(previousValue.data.taskTimers)) {
				updatedTimers[entry[0]] = entry[1].filter(
					(_, index) => index !== currentIndex
				);
			}

			return {
				...previousValue,
				data: {
					...previousValue.data,
					taskTimers: updatedTimers,
				},
			};
		});
		setSections((previousSections) =>
			previousSections.filter(
				({identifier}) => identifier !== currentIdentifier
			)
		);
	};

	const getTimersDetails = (section, keyword) => {
		return `${titleCase(keyword)}: ${section[keyword]} ${
			section[keyword + 'Scale']
		}`;
	};

	return sections.map((section, index) => (
		<ClayLayout.ContentCol
			className="current-node-data-area mb-3"
			float
			key={section.identifier}
		>
			<ClayLayout.Row className="current-node-data-row" justify="between">
				<ClayLink
					button={false}
					className="truncate-container"
					displayType="secondary"
					href="#"
					onClick={() => setContentName('timers')}
				>
					<span key={index}>
						{getTimersDetails(section, 'duration')}
					</span>

					{section.recurrence && (
						<span key={index}>
							<br />

							{getTimersDetails(section, 'recurrence')}
						</span>
					)}
				</ClayLink>

				<ClayButtonWithIcon
					className="delete-button text-secondary trash-button"
					displayType="unstyled"
					onClick={() =>
						deleteCurrentTimer(section.identifier, index)
					}
					symbol="trash"
				/>
			</ClayLayout.Row>
		</ClayLayout.ContentCol>
	));
};

CurrentTimers.propTypes = {
	setContentName: PropTypes.func.isRequired,
	taskTimers: PropTypes.object.isRequired,
};

export default CurrentTimers;
