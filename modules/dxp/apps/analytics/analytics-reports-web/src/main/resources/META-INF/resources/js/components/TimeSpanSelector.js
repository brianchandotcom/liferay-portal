/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {ChartDispatchContext} from '../context/ChartStateContext';
import ConnectionContext from '../context/ConnectionContext';

export default function TimeSpanSelector({
	disabledNextTimeSpan,
	disabledPreviousPeriodButton,
	timeSpanKey,
	timeSpanOptions,
}) {
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const dispatch = useContext(ChartDispatchContext);

	return (
		<div className="d-flex">
			<ClaySelect
				aria-label={Liferay.Language.get('select-date-range')}
				className="bg-white"
				disabled={!validAnalyticsConnection}
				onChange={(event) => {
					const {value} = event.target;

					dispatch({
						payload: {key: value},
						type: 'CHANGE_TIME_SPAN_KEY',
					});
				}}
				value={timeSpanKey}
			>
				{timeSpanOptions.map((option) => {
					return (
						<ClaySelect.Option
							key={option.key}
							label={option.label}
							value={option.key}
						/>
					);
				})}
			</ClaySelect>

			<div className="d-flex flex-shrink-0 ml-2">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('previous-period')}
					className="mr-1"
					data-tooltip-align="top-right"
					disabled={
						!validAnalyticsConnection ||
						disabledPreviousPeriodButton
					}
					displayType="secondary"
					onClick={() => dispatch({type: 'PREV_TIME_SPAN'})}
					small
					symbol="angle-left"
					title={
						disabledPreviousPeriodButton
							? Liferay.Language.get(
									'you-cannot-choose-a-date-prior-to-the-publication-date'
							  )
							: undefined
					}
				/>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('next-period')}
					disabled={!validAnalyticsConnection || disabledNextTimeSpan}
					displayType="secondary"
					onClick={() => dispatch({type: 'NEXT_TIME_SPAN'})}
					small
					symbol="angle-right"
				/>
			</div>
		</div>
	);
}

TimeSpanSelector.propTypes = {
	disabledNextTimeSpan: PropTypes.bool.isRequired,
	disabledPreviousPeriodButton: PropTypes.bool.isRequired,
	timeSpanKey: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	).isRequired,
};
