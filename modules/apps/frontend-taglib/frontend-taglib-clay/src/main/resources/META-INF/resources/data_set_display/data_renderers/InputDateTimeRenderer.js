/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayInput} from '@clayui/form';
import PropType from 'prop-types';
import React from 'react';

function getDateTimeString(date) {
	const dateInstance = new Date(date);

	const dateTimeString =
		dateInstance.getFullYear() +
		'-' +
		('0' + (dateInstance.getMonth() + 1)).slice(-2) +
		'-' +
		('0' + dateInstance.getDate()).slice(-2) +
		'T' +
		('0' + dateInstance.getHours()).slice(-2) +
		':' +
		('0' + dateInstance.getMinutes()).slice(-2);

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
						updateItem(newDate.toISOString());
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
