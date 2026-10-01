/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React from 'react';

import FieldText from '../../../../../components/forms/FieldText';
import {ScheduleValues} from '../types';

export default function CustomCronFields({
	errorMessage,
	onBlur,
	onChange,
	value,
}: {
	errorMessage?: string;
	onBlur?: () => void;
	onChange: (scheduleValues: Partial<ScheduleValues>) => void;
	value: ScheduleValues;
}) {
	return (
		<ClayLayout.Row>
			<ClayLayout.Col size={12}>
				<FieldText
					errorMessage={errorMessage}
					helpMessage={sub(
						Liferay.Language.get('for-example-x'),
						'0 30 15 ? * MON-FRI *'
					)}
					label={Liferay.Language.get('cron-expression')}
					name="publishScheduleCronExpression"
					onBlur={onBlur}
					onChange={(event) =>
						onChange({cronExpression: event.target.value})
					}
					required
					value={value.cronExpression}
				/>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
}
