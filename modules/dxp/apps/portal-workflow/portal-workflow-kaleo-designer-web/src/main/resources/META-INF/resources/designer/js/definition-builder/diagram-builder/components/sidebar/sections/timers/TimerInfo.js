/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import SidebarPanel from '../../SidebarPanel';

const TimerInfo = ({
	description,
	name,
	setTimerSections,
	timerIdentifier,
	timersIndex,
}) => {
	const [timerDescription, setTimerDescription] = useState([description]);
	const [timerName, setTimerName] = useState([name]);

	useEffect(() => {
		if (timerDescription !== undefined || timerName !== undefined) {
			setTimerSections((previousSections) => {
				const updatedSections = [...previousSections];
				const section = previousSections.find(
					({identifier}) => identifier === timerIdentifier
				);

				section.description = timerDescription;
				section.name = timerName;
				updatedSections.splice(timersIndex, 1, section);

				return updatedSections;
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [timerDescription, timerIdentifier, timerName, timersIndex]);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('information')}>
			<ClayForm.Group>
				<label htmlFor="timerName">
					{Liferay.Language.get('name')}
				</label>

				<ClayInput
					id="timerName"
					onBlur={({target}) => setTimerName(target.value)}
					onChange={({target}) => setTimerName(target.value)}
					placeholder={Liferay.Language.get('my-task-timer')}
					type="text"
					value={timerName}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="timerDescription">
					{Liferay.Language.get('description')}
				</label>

				<ClayInput
					component="textarea"
					id="timerDescription"
					onBlur={({target}) => setTimerDescription(target.value)}
					onChange={({target}) => setTimerDescription(target.value)}
					type="text"
					value={timerDescription}
				/>
			</ClayForm.Group>
		</SidebarPanel>
	);
};

TimerInfo.propTypes = {
	selectedItem: PropTypes.object.isRequired,
	setTimerSections: PropTypes.func.isRequired,
	timerIdentifier: PropTypes.string.isRequired,
	timersIndex: PropTypes.number.isRequired,
};

export default TimerInfo;
