/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import ClayModal from '@clayui/modal';
import classnames from 'classnames';
import {isAfter} from 'date-fns';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const noop = () => {};
export default function ScheduleModal({
	callback = noop,
	displayDate: initialDisplayDate,
	scheduled,
	observer,
	onModalClose = noop,
}) {
	const [displayDate, setDisplayDate] = useState(
		scheduled ? initialDisplayDate : ''
	);
	const [invalidDate, setInvalidDate] = useState(false);

	const handleScheduleButtonOnClick = () => {
		onModalClose();
		callback(displayDate);
	};

	const publisNowButtonOnClick = () => {
		onModalClose();
		callback('');
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
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{scheduled
					? Liferay.Language.get('edit-scheduled-publication')
					: Liferay.Language.get('schedule-publication')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="text-secondary">
					{scheduled
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
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							borderless="<%= true %>"
							displayType="secondary"
							onClick={onModalClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						{scheduled && (
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
				}
			/>
		</ClayModal>
	);
}

ScheduleModal.propTypes = {
	callback: PropTypes.func,
	displayDate: PropTypes.string,
	observer: PropTypes.object.isRequired,
	onModalClose: PropTypes.func,
	scheduled: PropTypes.bool,
};
