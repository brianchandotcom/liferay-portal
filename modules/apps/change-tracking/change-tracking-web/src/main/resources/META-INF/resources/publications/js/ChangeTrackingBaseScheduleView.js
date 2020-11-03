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

import {fetch} from 'frontend-js-web';
import moment from 'moment';
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
						Liferay.Util.navigate(json.redirect);
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

	getPublishDate(date, time) {
		if (typeof date === 'string') {
			return new Date(
				date +
					' ' +
					time.hours +
					':' +
					time.minutes +
					':00 ' +
					this.timeZone
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
				this.timeZone
		);
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

		if (typeof date === 'string' && validDate) {
			this.setState({date: new Date(date + ' 12:00:00')});
		}
		else {
			this.setState({date});
		}
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

		if (date.match(datePattern) && moment(date).isValid()) {
			return true;
		}

		return false;
	}

	isValidTime(time) {
		if (time.hours !== '--' && time.minutes !== '--') {
			return true;
		}

		return false;
	}
}

export default ChangeTrackingBaseScheduleView;
export {ChangeTrackingBaseScheduleView};
