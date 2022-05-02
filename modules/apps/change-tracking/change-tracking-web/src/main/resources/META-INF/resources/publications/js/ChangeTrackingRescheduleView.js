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
import React from 'react';

import ChangeTrackingBaseScheduleView from './ChangeTrackingBaseScheduleView';

class ChangeTrackingRescheduleView extends ChangeTrackingBaseScheduleView {
	constructor(props) {
		super(props);

		const {
			redirect,
			rescheduleURL,
			scheduledDate,
			scheduledTime,
			spritemap,
			timeZone,
			unscheduleURL,
		} = props;

		this.redirect = redirect;
		this.rescheduleURL = rescheduleURL;
		this.spritemap = spritemap;
		this.timeZone = timeZone;
		this.unscheduleURL = unscheduleURL;

		this.state = {
			date: scheduledDate,
			dateError: '',
			formError: null,
			time: scheduledTime,
			timeError: '',
			validationError: null,
		};
	}

	render() {
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
						<div className={this.getDateClassName()}>
							<div>
								<ClayDatePicker
									onValueChange={this.handleDateChange}
									placeholder="YYYY-MM-DD"
									spritemap={this.spritemap}
									timezone={this.timeZone}
									value={
										typeof this.state.date === 'string'
											? this.state.date
											: this.state.date.getFullYear() +
											  '-' +
											  this.pad(
													this.state.date.getMonth() +
														1
											  ) +
											  '-' +
											  this.pad(
													this.state.date.getDate()
											  )
									}
									years={{
										end: new Date().getFullYear() + 1,
										start: new Date().getFullYear() - 1,
									}}
								/>

								{this.getDateHelpText()}
							</div>
						</div>

						<div className={this.getTimeClassName()}>
							<div>
								<ClayTimePicker
									onChange={this.handleTimeChange}
									spritemap={this.spritemap}
									timezone={this.timeZone}
									value={this.state.time}
								/>

								{this.getTimeHelpText()}
							</div>
						</div>
					</div>
				</div>

				{this.state.formError && (
					<ClayAlert
						displayType="danger"
						spritemap={this.spritemap}
						title={this.state.formError}
					/>
				)}

				<div className="sheet-footer sheet-footer-btn-block-sm-down">
					<div className="btn-group">
						<div className="btn-group-item">
							<button
								className="btn btn-primary"
								onClick={() =>
									this.doSchedule(this.rescheduleURL)
								}
								type="button"
							>
								{Liferay.Language.get('reschedule')}
							</button>
						</div>

						<div className="btn-group-item">
							<button
								className="btn btn-secondary"
								onClick={() =>
									submitForm(
										document.hrefFm,
										this.unscheduleURL
									)
								}
								type="button"
							>
								{Liferay.Language.get('unschedule')}
							</button>
						</div>

						<div className="btn-group-item">
							<button
								className="btn btn-outline-borderless btn-secondary"
								onClick={() =>
									Liferay.Util.navigate(this.redirect)
								}
								type="button"
							>
								{Liferay.Language.get('cancel')}
							</button>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

export default function (props) {
	return <ChangeTrackingRescheduleView {...props} />;
}
