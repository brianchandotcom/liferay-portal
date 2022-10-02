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
