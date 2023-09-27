/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayModal from '@clayui/modal';
import getCN from 'classnames';
import React, {useContext, useEffect, useRef, useState} from 'react';
import MaskedInput from 'react-text-mask';

import {
	defaultDateFormat,
	formatDate,
	getLocaleDateFormat,
	getMaskByDateFormat,
	isValidDate,
} from '../../../../shared/util/date.es';
import {toUppercase} from '../../../../shared/util/util.es';
import {AppContext} from '../../../AppContext.es';
import {ModalContext} from '../ModalProvider.es';

const getTimeOptions = (isAmPm) => {
	const parse = (number) => (number < 10 ? `0${number}` : number);

	if (isAmPm) {
		const times = {
			AM: ['12:00 AM', '12:30 AM'],
			PM: ['12:00 PM', '12:30 PM'],
		};

		Object.keys(times).forEach((type) => {
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
};

function UpdateDueDateStep({className, dueDate = new Date()}) {
	const {isAmPm, timeFormat} = useContext(AppContext);
	const {setUpdateDueDate, updateDueDate} = useContext(ModalContext);

	const dateFormat = getLocaleDateFormat();

	const dateMask = getMaskByDateFormat(dateFormat);

	const [comment, setComment] = useState('');
	const [date, setDate] = useState(
		formatDate(dueDate, dateFormat, defaultDateFormat)
	);
	const [time, setTime] = useState(
		toUppercase(formatDate(dueDate, timeFormat, defaultDateFormat))
	);
	const [validDate, setValidDate] = useState(true);
	const [validTime, setValidTime] = useState(true);

	useEffect(() => {
		let newDueDate = null;

		const validDate = isValidDate(date, dateFormat);

		let validTime = false;

		if (time) {
			if (isAmPm) {
				if (time.includes('AM') || time.includes('PM')) {
					validTime = true;
				}
			}
			else {
				if (!time.includes('AM') && !time.includes('PM')) {
					validTime = true;
				}
			}
		}

		if (date && validDate && validTime) {
			const newDateTime = formatDate(
				`${date} ${time}`,
				defaultDateFormat,
				`${dateFormat} ${timeFormat}`
			);

			newDueDate = isValidDate(newDateTime, defaultDateFormat)
				? newDateTime
				: null;
		}

		setValidDate(validDate);
		setValidTime(validTime);
		setUpdateDueDate({...updateDueDate, dueDate: newDueDate});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [date, time]);

	useEffect(() => {
		setUpdateDueDate((currentState) => ({...currentState, comment}));
	}, [comment, setUpdateDueDate]);

	return (
		<div className={getCN('bg-white', className)}>
			<ClayModal.Body>
				<div className="form-group-autofit">
					<div
						className={`form-group-item ${
							!validDate && 'has-error'
						}`}
					>
						<label htmlFor="dateInput">
							{Liferay.Language.get('new-due-date') + ' '}

							<span className="reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						</label>

						<MaskedInput
							className="form-control"
							mask={dateMask}
							onChange={({target}) => setDate(target.value)}
							placeholder={Liferay.Language.get(
								'mm-dd-yyyy'
							).replace(/[()]/g, '')}
							value={date}
						/>
					</div>

					<UpdateDueDateStep.TimePickerInput
						isAmPm={isAmPm}
						setValue={setTime}
						validTime={validTime}
						value={time}
					/>
				</div>

				<div className="form-group-item mb-4">
					<label htmlFor="commentTextArea">
						{Liferay.Language.get('comment')}
					</label>

					<textarea
						className="form-control"
						onChange={({target}) => setComment(target.value)}
						placeholder={Liferay.Language.get('write-a-note')}
					/>
				</div>
			</ClayModal.Body>
		</div>
	);
}

function TimePickerInputWithOptions({isAmPm, setValue, validTime, value}) {
	const [showOptions, setShowOptions] = useState(false);
	const inputRef = useRef();
	const options = getTimeOptions(isAmPm);

	return (
		<div
			className={`form-group-item form-group-item-label-spacer ${
				!validTime ? 'has-error' : ''
			}`}
		>
			<ClayInput
				onBlur={() => setShowOptions(false)}
				onChange={({target}) => setValue(target.value)}
				onFocus={() => setShowOptions(true)}
				placeholder={isAmPm ? 'HH:mm am/pm' : 'HH:mm'}
				ref={inputRef}
				value={value}
			/>

			{showOptions && (
				<div
					className="clay-popover-bottom custom-time-select fade popover show"
					style={{
						left: `${
							((inputRef.current?.offsetWidth ?? 270) - 120) / 2
						}px`,
					}}
				>
					<div className="arrow"></div>

					<div className="inline-scroller">
						<div className="popover-body">
							{options.map((option, index) => (
								<ClayList.Item
									key={index}
									onMouseDown={() => setValue(option)}
								>
									{option}
								</ClayList.Item>
							))}
						</div>
					</div>
				</div>
			)}
		</div>
	);
}

UpdateDueDateStep.TimePickerInput = TimePickerInputWithOptions;

export default UpdateDueDateStep;
