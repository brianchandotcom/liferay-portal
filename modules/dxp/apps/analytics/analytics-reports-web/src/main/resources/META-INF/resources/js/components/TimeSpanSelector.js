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
