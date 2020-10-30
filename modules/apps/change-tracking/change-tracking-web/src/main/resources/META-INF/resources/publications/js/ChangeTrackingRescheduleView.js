/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from '@clayui/alert';
import ClayDatePicker from '@clayui/date-picker';
import ClayTimePicker from '@clayui/time-picker';
import {fetch} from 'frontend-js-web';
import moment from 'moment';
import React, {useState} from 'react';

const ChangeTrackingRescheduleView = ({
	redirect,
	rescheduleURL,
	scheduledDate,
	scheduledTime,
	spritemap,
	timeZone,
	unscheduleURL,
}) => {
	const [dateError, setDateError] = useState('');
	const [formError, setFormError] = useState(null);
	const [timeError, setTimeError] = useState('');
	const [validationError, setValidationError] = useState(null);

	const [datePickerValue, setDatePickerValue] = useState(
		new Date(scheduledDate + ' 12:00:00')
	);
	const [timePickerValue, setTimePickerValue] = useState(scheduledTime);

	const isValidDate = (date) => {
		if (!date) {
			return false;
		}

		if (typeof date !== 'string') {
			return true;
		}

		const datePattern = /^[0-9][0-9][0-9][0-9]-[0-1]?[0-9]-[0-3]?[0-9]$/g;

		date = date.trim();

		if (date.match(datePattern) && moment(date).isValid()) {
			return true;
		}

		return false;
	};

	const isValidTime = (time) => {
		if (time.hours !== '--' && time.minutes !== '--') {
			return true;
		}

		return false;
	};

	const getDateClassName = () => {
		const className = 'input-group-item input-group-item-shrink';

		if (dateError || validationError) {
			return className + ' has-error';
		}

		return className;
	};

	const getDateHelpText = () => {
		if (validationError) {
			return (
				<div className="help-block">
					<div className="required" role="alert">
						{validationError}
					</div>
				</div>
			);
		}
		else if (dateError) {
			return (
				<div className="help-block">
					<div className="required" role="alert">
						{dateError}
					</div>
				</div>
			);
		}

		return '';
	};

	const getPublishDate = (date, time) => {
		if (typeof date === 'string') {
			return new Date(
				date + ' ' + time.hours + ':' + time.minutes + ':00 ' + timeZone
			);
		}

		return new Date(
			date.getFullYear() +
				'-' +
				(date.getMonth() + 1) +
				'-' +
				date.getDate() +
				' ' +
				time.hours +
				':' +
				time.minutes +
				':00 ' +
				timeZone
		);
	};

	const getTimeClassName = () => {
		const className = 'input-group-item';

		if (timeError || validationError) {
			return className + ' has-error';
		}

		return className;
	};

	const getTimeHelpText = () => {
		if (timeError) {
			return (
				<div className="help-block">
					<div className="required" role="alert">
						{timeError}
					</div>
				</div>
			);
		}

		return '';
	};

	const isPublishDateInTheFuture = (publishDate) => {
		const currentDate = new Date();

		if (currentDate.getTime() < publishDate.getTime()) {
			return true;
		}

		return false;
	};

	const handleDateChange = (date) => {
		const validDate = isValidDate(date);

		if (
			validationError &&
			(!validDate ||
				isPublishDateInTheFuture(getPublishDate(date, timePickerValue)))
		) {
			setValidationError(null);
		}

		if (
			(dateError && !datePickerValue) ||
			(dateError && validDate && !isValidDate(datePickerValue))
		) {
			setDateError(null);
		}

		if (typeof date === 'string' && validDate) {
			setDatePickerValue(new Date(date + ' 12:00:00'));
		}
		else {
			setDatePickerValue(date);
		}
	};

	const handleSubmit = () => {
		setFormError(null);

		let errorSet = false;

		if (!datePickerValue) {
			setDateError(Liferay.Language.get('this-field-is-required'));

			errorSet = true;
		}
		else if (!isValidDate(datePickerValue)) {
			setDateError(Liferay.Language.get('please-enter-a-valid-date'));

			errorSet = true;
		}

		if (!isValidTime(timePickerValue)) {
			setTimeError(Liferay.Language.get('this-field-is-required'));

			errorSet = true;
		}

		if (errorSet) {
			return;
		}

		const publishDate = getPublishDate(datePickerValue, timePickerValue);

		if (!isPublishDateInTheFuture(publishDate)) {
			setValidationError(
				Liferay.Language.get('the-publish-time-must-be-in-the-future')
			);

			return;
		}

		AUI().use('liferay-portlet-url', () => {
			const portletURL = Liferay.PortletURL.createURL(rescheduleURL);

			portletURL.setParameter('publishTime', publishDate.getTime());

			fetch(portletURL.toString(), {
				method: 'GET',
			})
				.then((response) => response.json())
				.then((json) => {
					if (json.redirect) {
						Liferay.Util.navigate(json.redirect);
					}
					else if (json.validationError) {
						setValidationError(json.validationError);
					}
					else if (json.error) {
						setFormError(json.error);
					}
				})
				.catch((response) => {
					setFormError(response.error);
				});
		});
	};

	const handleTimeChange = (time) => {
		if (
			validationError &&
			isPublishDateInTheFuture(getPublishDate(datePickerValue, time))
		) {
			setValidationError(null);
		}

		if (timeError && isValidTime(time) && !isValidTime(timePickerValue)) {
			setTimeError(null);
		}

		setTimePickerValue(time);
	};

	return (
		<div className="sheet sheet-lg">
			<div className="sheet-header">
				<h2 className="sheet-title">
					{Liferay.Language.get('reschedule-publication')}
				</h2>
			</div>

			<div className="sheet-section">
				<label>{Liferay.Language.get('date-and-time')}</label>
				<div className="input-group">
					<div className={getDateClassName()}>
						<div>
							<ClayDatePicker
								onValueChange={handleDateChange}
								placeholder="YYYY-MM-DD"
								spritemap={spritemap}
								timezone={timeZone}
								value={datePickerValue}
								years={{
									end: new Date().getFullYear() + 1,
									start: new Date().getFullYear() - 1,
								}}
							/>

							{getDateHelpText()}
						</div>
					</div>
					<div className={getTimeClassName()}>
						<div>
							<ClayTimePicker
								onInputChange={handleTimeChange}
								spritemap={spritemap}
								timezone={timeZone}
								values={timePickerValue}
							/>

							{getTimeHelpText()}
						</div>
					</div>
				</div>
			</div>

			{formError && (
				<ClayAlert
					displayType="danger"
					spritemap={spritemap}
					title={formError}
				/>
			)}

			<div className="sheet-footer sheet-footer-btn-block-sm-down">
				<div className="btn-group">
					<div className="btn-group-item">
						<button
							className="btn btn-primary"
							onClick={() => handleSubmit()}
							type="button"
						>
							{Liferay.Language.get('reschedule')}
						</button>
					</div>
					<div className="btn-group-item">
						<button
							className="btn btn-secondary"
							onClick={() =>
								submitForm(document.hrefFm, unscheduleURL)
							}
							type="button"
						>
							{Liferay.Language.get('unschedule')}
						</button>
					</div>
					<div className="btn-group-item">
						<button
							className="btn btn-outline-borderless btn-secondary"
							onClick={() => Liferay.Util.navigate(redirect)}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</button>
					</div>
				</div>
			</div>
		</div>
	);
};

export default function (props) {
	return <ChangeTrackingRescheduleView {...props} />;
}
