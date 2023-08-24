/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayDatePicker from '@clayui/date-picker';
import {isAfter} from 'date-fns';
import {getOpener} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const SELECT_EVENT_NAME = 'selectKBArticleScheduleDate';
export default function ScheduleModal({scheduledDate: initialScheduleDate}) {
	const [scheduledDate, setScheduledDate] = useState(initialScheduleDate);
	const [currentDate, setCurrentDate] = useState();
	const [invalidDate, setInvalidDate] = useState(false);

	useEffect(() => {
		setCurrentDate(Date.now());

		if (
			isAfter(Date.parse(scheduledDate), currentDate) ||
			(Number.isNaN(Date.parse(scheduledDate)) && !scheduledDate)
		) {
			setInvalidDate(false);
		}
		else {
			setInvalidDate(true);
		}

		getOpener().Liferay.fire(SELECT_EVENT_NAME, {scheduledDate});
	}, [scheduledDate]);

	return (
		<div className="container-fluid p-4">
			<p className="text-secondary">
				{Liferay.Language.get('set-date-and-time-for-publication')}
			</p>

			{invalidDate === true ? (
				<div>
					<div className="has-error">
						<ClayDatePicker
							onChange={setScheduledDate}
							placeholder="YYYY-MM-DD HH:mm"
							time
							value={scheduledDate}
						/>
					</div>

					<div className="error-container mt-1">
						<ClayAlert
							displayType="danger"
							title={Liferay.Language.get('error-colon') + ' '}
							variant="feedback"
						>
							{Liferay.Language.get('please-enter-a-valid-date')}
						</ClayAlert>
					</div>
				</div>
			) : (
				<ClayDatePicker
					onChange={setScheduledDate}
					placeholder="YYYY-MM-DD HH:mm"
					time
					value={scheduledDate}
				/>
			)}
		</div>
	);
}

ScheduleModal.propTypes = {
	scheduledDate: PropTypes.string,
};
