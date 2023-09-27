/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, navigate} from 'frontend-js-web';
import React from 'react';

class ChangeTrackingBaseScheduleView extends React.Component {
	constructor(props) {
		super(props);

		this.handleDateChange = this.handleDateChange.bind(this);
		this.handleTimeChange = this.handleTimeChange.bind(this);
	}

	doSchedule(scheduleURL) {
		this.setState({formError: null});

		let errorSet = false;

		if (!this.state.date) {
			this.setState({
				dateError: Liferay.Language.get('this-field-is-required'),
			});

			errorSet = true;
		}
		else if (!this.isValidDate(this.state.date)) {
			this.setState({
				dateError: Liferay.Language.get('please-enter-a-valid-date'),
			});

			errorSet = true;
		}

		if (!this.isValidTime(this.state.time)) {
			this.setState({
				timeError: Liferay.Language.get('this-field-is-required'),
			});

			errorSet = true;
		}

		if (errorSet) {
			return;
		}

		const date = this.getJSDate(this.state.date, this.state.time);

		if (Number.isNaN(date.getTime())) {
			this.setState({
				validationError: Liferay.Language.get(
					'please-enter-a-valid-date'
				),
			});

			return;
		}

		const publishDate = this.getPublishDate(
			this.state.date,
			this.state.time
		);

		if (!this.isPublishDateInTheFuture(publishDate)) {
			this.setState({
				validationError: Liferay.Language.get(
					'the-publish-time-must-be-in-the-future'
				),
			});

			return;
		}

		AUI().use('liferay-portlet-url', () => {
			const portletURL = Liferay.PortletURL.createURL(scheduleURL);

			portletURL.setParameter('publishTime', publishDate.getTime());

			fetch(portletURL.toString(), {
				method: 'GET',
			})
				.then((response) => response.json())
				.then((json) => {
					if (json.redirect) {
						navigate(json.redirect);
					}
					else if (json.validationError) {
						this.setState({validationError: json.validationError});
					}
					else if (json.error) {
						this.setState({formError: json.error});
					}
				})
				.catch((response) => {
					this.setState({formError: response.error});
				});
		});
	}

	getDateClassName() {
		const className = 'input-group-item input-group-item-shrink';

		if (this.state.dateError || this.state.validationError) {
			return className + ' has-error';
		}

		return className;
	}

	getDateHelpText() {
		if (this.state.validationError) {
			return (
				<div className="help-block">
					<div className="required" role="alert">
						{this.state.validationError}
					</div>
				</div>
			);
		}
		else if (this.state.dateError) {
			return (
				<div className="help-block">
					<div className="required" role="alert">
						{this.state.dateError}
					</div>
				</div>
			);
		}

		return '';
	}

	getJSDate(date, time) {
		if (typeof date === 'string') {
			const split = date.split('-');

			if (split.length === 3) {
				return new Date(
					split[0] +
						'-' +
						this.pad(split[1]) +
						'-' +
						this.pad(split[2]) +
						'T' +
						time.hours +
						':' +
						time.minutes +
						':00'
				);
			}

			return new Date(
				date + 'T' + time.hours + ':' + time.minutes + ':00'
			);
		}

		return new Date(
			date.getFullYear() +
				'-' +
				this.pad(date.getMonth() + 1) +
				'-' +
				this.pad(date.getDate()) +
				'T' +
				time.hours +
				':' +
				time.minutes +
				':00'
		);
	}

	getPublishDate(date, time) {
		let publishDate = this.getJSDate(date, time);

		publishDate = new Date(
			Date.UTC(
				publishDate.getFullYear(),
				publishDate.getMonth(),
				publishDate.getDate(),
				publishDate.getHours(),
				publishDate.getMinutes()
			)
		);

		const tzDate = new Date(
			publishDate.toLocaleString('en-US', {timeZone: this.timeZone})
		);
		const utcDate = new Date(
			publishDate.toLocaleString('en-US', {timeZone: 'UTC'})
		);

		const offset = utcDate.getTime() - tzDate.getTime();

		publishDate.setTime(publishDate.getTime() + offset);

		return publishDate;
	}

	getTimeClassName() {
		const className = 'input-group-item';

		if (this.state.timeError || this.state.validationError) {
			return className + ' has-error';
		}

		return className;
	}

	getTimeHelpText() {
		if (this.state.timeError) {
			return (
				<div className="help-block">
					<div className="required" role="alert">
						{this.state.timeError}
					</div>
				</div>
			);
		}

		return '';
	}

	handleDateChange(date) {
		const validDate = this.isValidDate(date);

		if (
			this.state.validationError &&
			(!validDate ||
				this.isPublishDateInTheFuture(
					this.getPublishDate(date, this.state.time)
				))
		) {
			this.setState({validationError: null});
		}

		if (
			(this.state.dateError && !this.state.date) ||
			(this.state.dateError &&
				validDate &&
				!this.isValidDate(this.state.date))
		) {
			this.setState({dateError: null});
		}

		this.setState({date});
	}

	handleTimeChange(time) {
		if (
			this.state.validationError &&
			(!this.isValidTime(time) ||
				this.isPublishDateInTheFuture(
					this.getPublishDate(this.state.date, time)
				))
		) {
			this.setState({validationError: null});
		}

		if (
			this.state.timeError &&
			this.isValidTime(time) &&
			!this.isValidTime(this.state.time)
		) {
			this.setState({timeError: null});
		}

		this.setState({time});
	}

	isPublishDateInTheFuture(publishDate) {
		const currentDate = new Date();

		if (currentDate.getTime() < publishDate.getTime()) {
			return true;
		}

		return false;
	}

	isValidDate(date) {
		if (!date) {
			return false;
		}

		if (typeof date !== 'string') {
			return true;
		}

		const datePattern = /^[0-9][0-9][0-9][0-9]-[0-1]?[0-9]-[0-3]?[0-9]$/g;

		date = date.trim();

		if (!date.match(datePattern)) {
			return false;
		}

		const split = date.split('-');

		if (split.length !== 3) {
			return false;
		}

		const jsDate = new Date(
			split[0] + '-' + this.pad(split[1]) + '-' + this.pad(split[2])
		);

		if (Number.isNaN(jsDate.getTime())) {
			return false;
		}

		return true;
	}

	isValidTime(time) {
		if (time.hours !== '--' && time.minutes !== '--') {
			return true;
		}

		return false;
	}

	pad(value) {
		let number = value;

		if (typeof value === 'string') {
			number = parseInt(value, 10);
		}

		if (number < 10) {
			return '0' + number;
		}

		return number.toString();
	}
}

export default ChangeTrackingBaseScheduleView;
export {ChangeTrackingBaseScheduleView};
