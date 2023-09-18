/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import classnames from 'classnames';
import {isAfter} from 'date-fns';
import {getOpener} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const SCHEDULE_EVENT_NAME = 'scheduleKBArticle';
export default function ScheduleModal({
	displayDate: initialDisplayDate,
	isScheduled,
	portletNamespace,
}) {
	const [displayDate, setDisplayDate] = useState(
		isScheduled ? initialDisplayDate : null
	);
	const [invalidDate, setInvalidDate] = useState(false);

	const closeModal = () => {
		getOpener().Liferay.fire('closeModal', {
			id: 'scheduleKBArticleDialog',
		});
	};

	const handleScheduleButtonOnClick = () => {
		const openerWindow = getOpener();

		const displayDateInput = openerWindow.document.getElementById(
			`${portletNamespace}displayDate`
		);
		displayDateInput.value = displayDate;

		openerWindow.Liferay.fire(SCHEDULE_EVENT_NAME);
		closeModal();
	};

	const publisNowButtonOnClick = () => {
		getOpener().Liferay.fire(SCHEDULE_EVENT_NAME);
		closeModal();
	};

	useEffect(() => {
		setInvalidDate(
			!(
				isAfter(Date.parse(displayDate), Date.now()) ||
				(Number.isNaN(Date.parse(displayDate)) && !displayDate)
			)
		);
	}, [displayDate]);

	return (
		<div className="schedule-modal">
			<div className="container-fluid p-4">
				<p className="text-secondary">
					{isScheduled
						? Liferay.Language.get(
								'this-article-is-set-to-publish-later'
						  )
						: Liferay.Language.get(
								'set-date-and-time-for-publication'
						  )}
				</p>

				<div className={classnames({'has-error': invalidDate})}>
					<label>{Liferay.Language.get('date-and-time')}</label>

					<ClayDatePicker
						onChange={setDisplayDate}
						placeholder="YYYY-MM-DD HH:mm"
						time
						value={displayDate}
					/>
				</div>

				{invalidDate && (
					<div className="error-container mt-1">
						<ClayAlert
							displayType="danger"
							title={Liferay.Language.get('error-colon') + ' '}
							variant="feedback"
						>
							{Liferay.Language.get('please-enter-a-valid-date')}
						</ClayAlert>
					</div>
				)}
			</div>

			<div className="modal-footer">
				<div className="modal-item-last">
					<ClayButton.Group spaced>
						<ClayButton
							borderless="<%= true %>"
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						{isScheduled && (
							<ClayButton
								displayType="secondary"
								onClick={publisNowButtonOnClick}
							>
								{Liferay.Language.get('publish-now')}
							</ClayButton>
						)}

						<ClayButton
							disabled={invalidDate || !displayDate}
							displayType="primary"
							onClick={handleScheduleButtonOnClick}
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
	displayDate: PropTypes.string,
	isScheduled: PropTypes.bool,
	portletNamespace: PropTypes.string.isRequired,
};
