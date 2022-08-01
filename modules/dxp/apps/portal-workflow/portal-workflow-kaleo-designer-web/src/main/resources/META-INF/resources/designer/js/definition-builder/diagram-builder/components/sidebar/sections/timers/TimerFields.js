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

import {ClayInput} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import SelectTimeScale from './TimerScale';

const TimerFields = ({
	durationScaleValue,
	durationValue,
	recurrence,
	scaleHelpText,
	setTimerSections,
	timerIdentifier,
	timersIndex,
}) => {
	const [timerScale, setTimerScale] = useState(
		durationScaleValue || 'second'
	);
	const [timerValue, setTimerValue] = useState(durationValue || '');

	useEffect(() => {
		if (timerScale && timerValue) {
			setTimerSections((previousSections) => {
				const updatedSectios = [...previousSections];
				const section = previousSections.find(
					({identifier}) => identifier === timerIdentifier
				);
				if (!recurrence) {
					section.duration = timerValue;
					section.durationScale = timerScale;
				}
				else {
					section.recurrence = timerValue;
					section.recurrenceScale = timerScale;
				}

				updatedSectios.splice(timersIndex, 1, section);

				return updatedSectios;
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [timerIdentifier, timerScale, timersIndex, timerValue]);

	return (
		<div className="form-group-autofit timer-inputs">
			<div className="form-group-item">
				<label htmlFor="timerScale">
					{Liferay.Language.get('scale')}

					{!recurrence && (
						<span className="ml-1 mr-1 text-warning">*</span>
					)}
				</label>

				<SelectTimeScale
					setTimerScale={setTimerScale}
					timerScale={timerScale}
				/>

				{scaleHelpText && (
					<div className="help-text">{scaleHelpText}</div>
				)}
			</div>

			<div className="form-group-item">
				<label htmlFor="timerValue">
					{Liferay.Language.get('duration')}

					{!recurrence && (
						<span className="ml-1 mr-1 text-warning">*</span>
					)}
				</label>

				<ClayInput
					min="0"
					onChange={({target}) => setTimerValue(target.value)}
					onWheel={(event) => event.target.blur()}
					step="1"
					type="number"
					value={timerValue}
				/>
			</div>
		</div>
	);
};

TimerFields.propTypes = {
	recurrence: PropTypes.bool,
	scaleHelpText: PropTypes.string,
	selectedItem: PropTypes.object,
	timersIndex: PropTypes.number,
	updateSelectedItem: PropTypes.func,
};

export default TimerFields;
