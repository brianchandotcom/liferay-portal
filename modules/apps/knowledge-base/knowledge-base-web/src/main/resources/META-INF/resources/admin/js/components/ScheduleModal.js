/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import {isAfter} from 'date-fns';
import {getOpener} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const SCHEDULE_EVENT_NAME = 'scheduleKBArticle';
export default function ScheduleModal({
	namespace,
	scheduledDate: initialScheduleDate,
}) {
	const [scheduledDate, setScheduledDate] = useState(initialScheduleDate);
	const [currentDate, setCurrentDate] = useState();
	const [invalidDate, setInvalidDate] = useState(false);

	const closeModal = () => {
		getOpener().Liferay.fire('closeModal', {
			id: 'scheduleKBArticleDialog',
		});
	};

	const handleScheduleButtonClick = () => {
		const openerWindow = getOpener();

		const scheduleDateInput = openerWindow.document.getElementById(
			`${namespace}scheduleDate`
		);
		scheduleDateInput.value = scheduledDate;

		getOpener().Liferay.fire(SCHEDULE_EVENT_NAME);
		closeModal();
	};

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
	}, [scheduledDate]);

	return (
		<div className="schedule-modal">
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
								title={
									Liferay.Language.get('error-colon') + ' '
								}
								variant="feedback"
							>
								{Liferay.Language.get(
									'please-enter-a-valid-date'
								)}
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

			<div className="modal-footer">
				<div className="modal-item-last">
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={invalidDate || !scheduledDate}
							displayType="primary"
							onClick={handleScheduleButtonClick}
						>
							{Liferay.Language.get('schedule')}
						</ClayButton>
					</ClayButton.Group>
				</div>
			</div>
		</div>
	);
}

ScheduleModal.propTypes = {
	namespace: PropTypes.string.isRequired,
	scheduledDate: PropTypes.string,
};
