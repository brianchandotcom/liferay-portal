/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayDatePicker from '@clayui/date-picker';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import moment from 'moment/min/moment-with-locales';
import React, {useEffect} from 'react';

export default function ScheduleOptions({
	displayDate,
	error,
	formId,
	portletNamespace,
	setDisplayDate,
	setError,
	timeZone,
}) {
	const {day, hour, minutes, month, year} = getDate(displayDate);

	useEffect(() => {
		if (displayDate) {
			if (!moment(displayDate, 'yyyy-MM-DD HH:mm', true).isValid()) {
				setError(Liferay.Language.get('please-enter-a-valid-date'));

				return;
			}

			const date = new Date(displayDate);

			if (date.valueOf() <= new Date().valueOf()) {
				setError(
					Liferay.Language.get('the-date-entered-is-in-the-past')
				);
			}
			else {
				setError('');
			}
		}
	}, [setError, displayDate]);

	return (
		<>
			<ClayForm.Group
				className={classNames('mb-0', {'has-error': error})}
			>
				<label htmlFor={`${portletNamespace}displayDatePicker`}>
					{Liferay.Language.get('date-and-time')}

					<ClayIcon
						className="ml-1 reference-mark"
						focusable="false"
						role="presentation"
						symbol="asterisk"
					/>
				</label>

				<ClayDatePicker
					id={`${portletNamespace}displayDatePicker`}
					onChange={setDisplayDate}
					placeholder="YYYY-MM-DD HH:mm"
					required
					time
					timezone={timeZone}
					value={displayDate || ''}
					years={{
						end: 9999,
						start: new Date().getFullYear(),
					}}
				/>

				{error ? (
					<div className="error-container mt-1">
						<ClayAlert
							className="mt-1"
							displayType="danger"
							title={Liferay.Language.get('error-colon') + ' '}
							variant="feedback"
						>
							{error}
						</ClayAlert>
					</div>
				) : null}
			</ClayForm.Group>

			<p className="mt-1 text-3 text-secondary">
				{sub(Liferay.Language.get('time-zone-x'), timeZone)}
			</p>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateDay`}
				type="hidden"
				value={day}
			/>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateHour`}
				type="hidden"
				value={hour}
			/>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateMinutes`}
				type="hidden"
				value={minutes}
			/>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateMonth`}
				type="hidden"
				value={month}
			/>

			<ClayInput
				form={formId}
				name={`${portletNamespace}displayDateYear`}
				type="hidden"
				value={year}
			/>
		</>
	);
}

function getDate(value) {
	const date = new Date(value);

	if (moment(date).isValid()) {
		const date = new Date(value);

		return {
			day: date.getDate(),
			hour: date.getHours(),
			minutes: date.getMinutes(),
			month: date.getMonth(),
			year: date.getFullYear(),
		};
	}

	return {day: '', hour: '', minutes: '', month: '', year: ''};
}
