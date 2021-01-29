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
import React from 'react';

const InputComponent = ({
	displayStyle,
	fieldName,
	inputValue,
	name,
	onFieldBlurred,
	onFieldChanged,
	onFieldFocused,
	placeholder,
	readOnly,
}) => {
	if (displayStyle === 'multiline') {
		return (
			<ClayInput.GroupItem>
				<textarea
					className="ddm-field-text form-control"
					disabled={readOnly}
					id={`${name}inputValue`}
					onBlur={onFieldBlurred}
					onChange={onFieldChanged}
					onFocus={onFieldFocused}
					placeholder={placeholder}
					type="text"
					value={inputValue}
				>
					{inputValue}
				</textarea>
			</ClayInput.GroupItem>
		);
	}

	return (
		<ClayInput.GroupItem append>
			<input
				{...(fieldName === 'submitLabel' && {maxLength: 25})}
				className="ddm-field-text form-control"
				data-testid="visibleChangeInput"
				disabled={readOnly}
				id={`${name}inputValue`}
				onBlur={onFieldBlurred}
				onChange={onFieldChanged}
				onFocus={onFieldFocused}
				placeholder={placeholder}
				type="text"
				value={inputValue}
			/>
		</ClayInput.GroupItem>
	);
};

export default InputComponent;
