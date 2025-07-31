/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDatePicker from '@clayui/date-picker';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import {dateUtils} from 'frontend-js-web';
import React, {useId, useState} from 'react';

import FieldWrapper from '../../../common/components/forms/FieldWrapper';

export default function SchedulePanel({
	expirationDate = '',
}: {
	expirationDate: string;
}) {
	return (
		<div className="px-3">
			<p className="text-3 text-secondary">
				{Liferay.Language.get(
					'including-an-expiration-date-will-allow-your-files-to-expire-automatically-and-become-unpublished'
				)}
			</p>

			<ScheduleField
				date={expirationDate}
				label={Liferay.Language.get('expiration-date')}
			/>
		</div>
	);
}

function ScheduleField({
	date: initialDate = '',
	label,
}: {
	date: string;
	label: string;
}) {
	const [checked, setChecked] = useState<boolean>(initialDate === '');
	const [date, setDate] = useState<string>(initialDate);

	const id = useId();
	const locale = Liferay.ThemeDisplay.getBCP47LanguageId();

	return (
		<div aria-label={label} role="group">
			<FieldWrapper disabled={checked} fieldId={id} label={label}>
				<ClayDatePicker
					disabled={checked}
					firstDayOfWeek={dateUtils.getFirstDayOfWeek(locale)}
					id={id}
					months={dateUtils.getMonthsLong(locale)}
					onChange={(value: string) => {
						setDate(value);
					}}
					placeholder="YYYY-MM-DD --:--"
					time
					value={date}
					weekdaysShort={dateUtils.getWeekdaysShort(locale)}
					years={{
						end: new Date().getFullYear() + 5,
						start: new Date().getFullYear(),
					}}
				/>
			</FieldWrapper>

			<ClayForm.Group>
				<ClayCheckbox
					checked={checked}
					label={Liferay.Language.get('never-expire')}
					onChange={({target: {checked}}) => {
						setChecked(checked);
					}}
				/>
			</ClayForm.Group>
		</div>
	);
}
