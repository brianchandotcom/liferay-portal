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
import ClayModal from '@clayui/modal';
import React, {useContext, useEffect, useMemo, useRef, useState} from 'react';
import MaskedInput from 'react-text-mask';

import Icon from '../../../../shared/components/Icon.es';
import {formatDate, isValidDate} from '../../../../shared/util/date.es';
import {toUppercase} from '../../../../shared/util/util.es';
import {AppContext} from '../../../AppContext.es';
import {ModalContext} from '../ModalContext.es';

const UpdateDueDateStep = ({dueDate = new Date()}) => {
	const {isAmPm} = useContext(AppContext);
	const {setUpdateDueDate, updateDueDate} = useContext(ModalContext);

	const dateFormat = 'MM/DD/YYYY';
	const dateMask = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
	const dateTimeFormat = 'YYYY-MM-DDTHH:mm:ss\\Z';
	const timeFormat = useMemo(() => (isAmPm ? 'h:mm a' : 'H:mm'), [isAmPm]);

	const [invalidDate, setInvalidDate] = useState(false);
	const [comment, setComment] = useState('');
	const [date, setDate] = useState(
		formatDate(dueDate, dateFormat, dateTimeFormat)
	);
	const [time, setTime] = useState(
		toUppercase(formatDate(dueDate, timeFormat, dateTimeFormat))
	);

	useEffect(() => {
		let newDueDate = null;
		const validDate = isValidDate(date, dateFormat);

		if (validDate && isValidDate(time, timeFormat)) {
			const newDateTime = formatDate(
				`${date} ${time}`,
				dateTimeFormat,
				`${dateFormat} ${timeFormat}`
			);

			newDueDate = isValidDate(newDateTime, dateTimeFormat)
				? newDateTime
				: null;
		}

		setInvalidDate(!validDate);
		setUpdateDueDate({...updateDueDate, dueDate: newDueDate});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [date, time]);

	useEffect(() => {
		setUpdateDueDate(currentState => ({...currentState, comment}));
	}, [comment, setUpdateDueDate]);

	return (
		<ClayModal.Body>
			<div className="form-group-autofit">
				<div
					className={`form-group-item ${invalidDate && 'has-error'}`}
				>
					<label htmlFor="dateInput">
						{Liferay.Language.get('new-due-date')}{' '}
						<span className="reference-mark">
							<Icon iconName="asterisk" />
						</span>
					</label>

					<MaskedInput
						className="form-control"
						data-testid="dateInput"
						mask={dateMask}
						onChange={({target}) => setDate(target.value)}
						placeholder={dateFormat}
						value={date}
					/>
				</div>

				<UpdateDueDateStep.TimePickerInput
					format={timeFormat}
					isAmPm={isAmPm}
					setValue={setTime}
					value={time}
				/>
			</div>

			<div className="form-group-item">
				<label htmlFor="commentTextArea">
					{Liferay.Language.get('comment')}
				</label>

				<textarea
					className="form-control"
					data-testid="commentInput"
					onChange={({target}) => setComment(target.value)}
					placeholder={Liferay.Language.get('write-a-note')}
				/>
			</div>
		</ClayModal.Body>
	);
};

const TimePickerInputWithOptions = ({format, isAmPm, setValue, value}) => {
	const [invalidTime, setInvalidTime] = useState(false);
	const [showOptions, setShowOptions] = useState(false);
	const inputRef = useRef();

	const {offsetWidth} = inputRef.current || {};

	useEffect(() => {
		setInvalidTime(!isValidDate(value, format));
	}, [format, value]);

	const options = useMemo(() => {
		const parse = number => (number < 10 ? `0${number}` : number);

		if (isAmPm) {
			const times = {
				AM: ['12:00 AM', '12:30 AM'],
				PM: ['12:00 PM', '12:30 PM']
			};

			Object.keys(times).forEach(type => {
				for (let i = 1; i < 12; i++) {
					times[type].push(`${parse(i)}:00 ${type}`);
					times[type].push(`${parse(i)}:30 ${type}`);
				}
			});

			return [...times.AM, ...times.PM];
		}

		const times = [];

		for (let i = 0; i < 24; i++) {
			times.push(`${parse(i)}:00`);
			times.push(`${parse(i)}:30`);
		}

		return times;
	}, [isAmPm]);
	const popoverStyle = useMemo(() => {
		const left = offsetWidth ? `${(offsetWidth - 120) / 2}px` : '75px';

		return {left};
	}, [offsetWidth]);

	return (
		<div
			className={`form-group-item form-group-item-label-spacer ${invalidTime &&
				'has-error'}`}
			data-testid="timePicker"
		>
			<ClayInput
				data-testid="timeInput"
				onBlur={() => setShowOptions(false)}
				onChange={({target}) => setValue(target.value)}
				onFocus={() => setShowOptions(true)}
				placeholder={isAmPm ? 'HH:mm am' : 'HH:mm'}
				ref={inputRef}
				value={value}
			/>

			{showOptions && (
				<div
					className="clay-popover-bottom custom-time-select fade popover show"
					style={popoverStyle}
				>
					<div className="arrow" />
					<div className="inline-scroller">
						<div className="popover-body">
							{options.map((option, index) => (
								<li
									data-testid="timeListItem"
									key={index}
									onMouseDown={() => setValue(option)}
								>
									{option}
								</li>
							))}
						</div>
					</div>
				</div>
			)}
		</div>
	);
};

UpdateDueDateStep.TimePickerInput = TimePickerInputWithOptions;

export {UpdateDueDateStep};
