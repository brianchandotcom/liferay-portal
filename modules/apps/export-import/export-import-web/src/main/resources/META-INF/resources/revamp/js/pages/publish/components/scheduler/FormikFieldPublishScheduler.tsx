/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getIn, useField, useFormikContext} from 'formik';
import React from 'react';

import {isCompleteDateTime} from '../../../../utils/dateTime';
import PublishScheduler from './PublishScheduler';
import {ScheduleValues, TimeZoneOption} from './types';
import {getScheduleValuesErrors} from './utils';

export function FormikFieldPublishScheduler({
	name,
	timeZones,
}: {
	name: string;
	timeZones: TimeZoneOption[];
}) {
	const [field, , helpers] = useField<ScheduleValues>(name);
	const {setFieldTouched, touched} = useFormikContext();

	const isTouched = (fieldName: keyof ScheduleValues) =>
		!!getIn(touched, `${name}.${fieldName}`);

	const setTouched = (fieldName: keyof ScheduleValues) =>
		setFieldTouched(`${name}.${fieldName}`);

	const scheduleValuesErrors = getScheduleValuesErrors(field.value);

	return (
		<PublishScheduler
			cronExpressionErrorMessage={
				isTouched('cronExpression')
					? scheduleValuesErrors.cronExpression
					: undefined
			}
			endDateTimeErrorMessage={
				isTouched('endDateTime') ||
				isCompleteDateTime(field.value.endDateTime)
					? scheduleValuesErrors.endDateTime
					: undefined
			}
			onChange={(scheduleValues) => helpers.setValue(scheduleValues)}
			onCronExpressionBlur={() => setTouched('cronExpression')}
			onEndDateTimeBlur={() => setTouched('endDateTime')}
			onRepeatOnTimeBlur={() => setTouched('repeatOnTime')}
			onStartDateTimeBlur={() => setTouched('startDateTime')}
			repeatOnTimeErrorMessage={
				isTouched('repeatOnTime')
					? scheduleValuesErrors.repeatOnTime
					: undefined
			}
			startDateTimeErrorMessage={
				isTouched('startDateTime') ||
				isCompleteDateTime(field.value.startDateTime)
					? scheduleValuesErrors.startDateTime
					: undefined
			}
			timeZones={timeZones}
			value={field.value}
		/>
	);
}
