/* eslint-disable react-hooks/exhaustive-deps */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import moment from '../../../../shared/util/moment.es';

const Item = ({dateOverdue, name, onTime, remainingTime, status}) => {
	let bgColor = 'danger';
	let iconClassName = 'text-danger';
	let iconName = 'exclamation-circle';
	let statusText = `(${Liferay.Language.get('resolved-overdue')})`;

	if (onTime) {
		bgColor = 'success';
		iconClassName = 'text-success';
		iconName = 'check-circle';
	}

	if (status === 'Paused') {
		statusText = `(${Liferay.Language.get('sla-paused')})`;
	}
	else if (status === 'Running') {
		const remainingTimePositive = onTime
			? remainingTime
			: remainingTime * -1;

		const remainingTimeUTC = moment.utc(remainingTimePositive);

		const durationText =
			remainingTimeUTC.format('D') -
			1 +
			remainingTimeUTC.format('[d] HH[h] mm[min]');

		let onTimeText = Liferay.Language.get('overdue');

		if (onTime) {
			onTimeText = Liferay.Language.get('left');
		}

		statusText = `${moment
			.utc(dateOverdue)
			.format(
				Liferay.Language.get('mmm-dd-yyyy-lt')
			)} (${durationText} ${onTimeText})`;
	}
	else if (status === 'Stopped' && onTime) {
		statusText = `(${Liferay.Language.get('resolved-on-time')})`;
	}

	return (
		<p>
			<span
				className={`bg-${bgColor}-light sticker`}
				style={{height: '1.5rem', width: '1.5rem'}}
			>
				<span className="inline-item" style={{fontSize: '10px'}}>
					<ClayIcon
						className={iconClassName}
						data-testid="resultIcon"
						symbol={iconName}
					/>
				</span>
			</span>

			<span className="font-weight-medium small text-secondary">
				{`${name}`}{' '}
			</span>

			<span className="small" data-testid="resultStatus">
				{statusText}
			</span>
		</p>
	);
};

export {Item};
