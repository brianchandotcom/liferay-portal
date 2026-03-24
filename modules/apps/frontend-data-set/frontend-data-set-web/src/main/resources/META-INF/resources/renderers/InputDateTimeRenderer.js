/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import PropType from 'prop-types';
import React from 'react';

function getDateTimeString(date) {
	const dateInstance = new Date(date);

	const dateTimeString =
		dateInstance.getFullYear() +
		'-' +
		`0${dateInstance.getMonth() + 1}`.slice(-2) +
		'-' +
		`0${dateInstance.getDate()}`.slice(-2) +
		'T' +
		`0${dateInstance.getHours()}`.slice(-2) +
		':' +
		`0${dateInstance.getMinutes()}`.slice(-2);

	return dateTimeString;
}

function InputDateTimeRenderer({updateItem, value}) {
	const formattedDate = value ? getDateTimeString(value) : '';

	return (
		<ClayInput.Group small>
			<ClayInput.GroupItem>
				<ClayInput
					onChange={(event) => {
						const newDate = new Date(event.target.value);
						const formattedDateString =
							newDate.toISOString().split('.')[0] + 'Z';
						updateItem(formattedDateString);
					}}
					type="datetime-local"
					value={formattedDate}
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

InputDateTimeRenderer.propTypes = {
	updateItem: PropType.func.isRequired,
	value: PropType.string,
};

export default InputDateTimeRenderer;
