/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDatePicker from '@clayui/date-picker';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {format, fromUnixTime, getUnixTime, parse} from 'date-fns';
import React from 'react';

function DateInput({disabled, name, setFieldTouched, setFieldValue, value}) {
	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
		}
	};

	return (
		<div className="date-picker-input" onBlur={() => setFieldTouched(name)}>
			<ClayDatePicker
				dateFormat="MM/dd/yyyy"
				disabled={disabled}
				onKeyDown={_handleKeyDown}
				onValueChange={(value) => {
					setFieldValue(
						name,
						getUnixTime(parse(value, 'MM/dd/yyyy', new Date()))
					);
				}}
				placeholder="MM/DD/YYYY"
				readOnly
				sizing="sm"
				value={value ? format(fromUnixTime(value), 'MM/dd/yyyy') : ''}
				years={{
					end: 2024,
					start: 1997,
				}}
			/>

			{!!value && (
				<ClayInput.GroupItem shrink>
					<ClayButton
						aria-label={Liferay.Language.get('delete')}
						disabled={disabled}
						displayType="unstyled"
						monospaced
						onClick={() => setFieldValue(name, '')}
						small
					>
						<ClayIcon symbol="times-circle" />
					</ClayButton>
				</ClayInput.GroupItem>
			)}
		</div>
	);
}

export default DateInput;
