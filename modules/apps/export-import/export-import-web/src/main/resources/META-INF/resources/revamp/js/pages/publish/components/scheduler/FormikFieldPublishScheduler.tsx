/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useField} from 'formik';
import React from 'react';

import {isCompleteDateTime} from '../../../../utils/dateTime';
import PublishScheduler from './PublishScheduler';
import {ScheduleValues, TimeZoneOption} from './types';
import {getScheduleValuesErrors} from './utils';

function useTouchedField(name: string) {
	const [, meta, helpers] = useField<string>(name);

	return [meta, () => helpers.setTouched(true)] as const;
}

export function FormikFieldPublishScheduler({
	name,
	timeZones,
}: {
	name: string;
	timeZones: TimeZoneOption[];
}) {
	const [field, , helpers] = useField<ScheduleValues>(name);

	const [cronExpressionMeta, onCronExpressionBlur] = useTouchedField(
		`${name}.cronExpression`
	);
	const [endDateTimeMeta, onEndDateTimeBlur] = useTouchedField(
		`${name}.endDateTime`
	);
	const [repeatOnTimeMeta, onRepeatOnTimeBlur] = useTouchedField(
		`${name}.repeatOnTime`
	);
	const [startDateTimeMeta, onStartDateTimeBlur] = useTouchedField(
		`${name}.startDateTime`
	);

	const scheduleValuesErrors = getScheduleValuesErrors(field.value);

	return (
		<PublishScheduler
			cronExpressionErrorMessage={
				cronExpressionMeta.touched
					? scheduleValuesErrors.cronExpression
					: undefined
			}
			endDateTimeErrorMessage={
				endDateTimeMeta.touched ||
				isCompleteDateTime(field.value.endDateTime)
					? scheduleValuesErrors.endDateTime
					: undefined
			}
			onChange={(scheduleValues) => helpers.setValue(scheduleValues)}
			onCronExpressionBlur={onCronExpressionBlur}
			onEndDateTimeBlur={onEndDateTimeBlur}
			onRepeatOnTimeBlur={onRepeatOnTimeBlur}
			onStartDateTimeBlur={onStartDateTimeBlur}
			repeatOnTimeErrorMessage={
				repeatOnTimeMeta.touched || !!field.value.repeatOnTime
					? scheduleValuesErrors.repeatOnTime
					: undefined
			}
			startDateTimeErrorMessage={
				startDateTimeMeta.touched ||
				isCompleteDateTime(field.value.startDateTime)
					? scheduleValuesErrors.startDateTime
					: undefined
			}
			timeZones={timeZones}
			value={field.value}
		/>
	);
}
