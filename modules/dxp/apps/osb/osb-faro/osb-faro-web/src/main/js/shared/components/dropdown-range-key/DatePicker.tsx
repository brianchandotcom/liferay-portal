import DatePicker from '../date-picker';
import React from 'react';
import {Text as ClayText} from '@clayui/core';
import {formatDateWithTimezone} from './utils';
import {MomentDateRange} from 'shared/components/DateRangeInput';
import {sub} from 'shared/util/lang';
import {useTimeZone} from 'shared/hooks/useTimeZone';

export const DropdownRangeKeyDatePicker = ({
	customDateRange,
	onCustomRangeChange,
	retentionPeriod
}) => {
	const {timeZoneId} = useTimeZone();

	return (
		<DatePicker
			date={customDateRange}
			displayLabel={false}
			header={
				<>
					<ClayText size={2} weight='semi-bold'>
						{Liferay.Language.get('custom-range').toUpperCase()}
					</ClayText>

					<div>
						<ClayText color='secondary' size={2}>
							{sub(
								Liferay.Language.get(
									'dates-prior-to-x-months-cannot-be-selected-due-to-your-workspaces-data-retention-period'
								),
								[retentionPeriod]
							)}
						</ClayText>
					</div>
				</>
			}
			maxDate={formatDateWithTimezone(timeZoneId)
				.clone()
				.subtract(1, 'days')}
			maxRange={365}
			minDate={formatDateWithTimezone(timeZoneId)
				.clone()
				.subtract(retentionPeriod, 'month')}
			onSelect={({end, start}: MomentDateRange) =>
				onCustomRangeChange({
					end,
					start
				})
			}
			timeZoneId={timeZoneId}
		/>
	);
};
