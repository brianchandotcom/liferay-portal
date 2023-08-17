/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import {getOpener} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const SELECT_EVENT_NAME = 'selectKBArticleScheduleDate';
export default function ScheduleModal({scheduledDate: initialScheduleDate}) {
	const [scheduledDate, setScheduledDate] = useState(initialScheduleDate);

	useEffect(() => {
		getOpener().Liferay.fire(SELECT_EVENT_NAME, {scheduledDate});
	}, [scheduledDate]);

	return (
		<div className="container-fluid p-3">
			<p>{Liferay.Language.get('set-date-and-time-for-publication')}</p>

			<ClayDatePicker
				onChange={setScheduledDate}
				placeholder="YYYY-MM-DD HH:mm"
				time
				value={scheduledDate}
			/>
		</div>
	);
}

ScheduleModal.propTypes = {
	scheduledDate: PropTypes.string,
};
