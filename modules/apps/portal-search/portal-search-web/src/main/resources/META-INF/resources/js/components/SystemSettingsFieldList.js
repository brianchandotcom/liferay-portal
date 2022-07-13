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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import React, {useState} from 'react';

const ADD_DELETE_BUTTON_STYLING = {
	borderRadius: '10px',
	height: '18px',
	padding: '2px',
	position: 'absolute',
	width: '18px',
};

function SystemSettingsFieldList({
	fieldLabel,
	fieldName,
	initialValue,
	namespace,
}) {
	const [value, setValue] = useState(initialValue || []);

	const _handleAddItem = (index) => () => {
		setValue([...value.slice(0, index + 1), '', ...value.slice(index + 1)]);
	};

	const _handleRemoveItem = (index) => () => {
		setValue(value.filter((item, itemIndex) => index !== itemIndex));
	};

	const _handleValueChange = (index) => (event) => {
		const newValue = value.map((item, itemIndex) => {
			if (index === itemIndex) {
				return event.target.value;
			}

			return item;
		});

		setValue(newValue);
	};

	return (
		<div className="system-settings-field-list-container">
			{value.map((item, index) => (
				<React.Fragment key={index}>
					<ClayForm.Group>
						{index > 0 && (
							<ClayButtonWithIcon
								className="system-settings-field-list-delete-button"
								onClick={_handleRemoveItem(index)}
								style={{
									...ADD_DELETE_BUTTON_STYLING,
									right: '30px',
								}}
								symbol="hr"
								title={Liferay.Language.get('remove')}
							/>
						)}

						<ClayButtonWithIcon
							className="system-settings-field-list-add-button"
							onClick={_handleAddItem(index)}
							style={{...ADD_DELETE_BUTTON_STYLING, right: '8px'}}
							symbol="plus"
							title={Liferay.Language.get('duplicate')}
						/>

						<label htmlFor={`${fieldName}${index}`}>
							{fieldLabel}
						</label>

						<textarea
							className="form-control"
							id={`${fieldName}${index}`}
							onChange={_handleValueChange(index)}
							value={item}
						/>
					</ClayForm.Group>
				</React.Fragment>
			))}

			<input
				name={`${namespace}${fieldName}`}
				type="hidden"
				value={value.filter((item) => item.trim() !== '').join('|')}
			/>
		</div>
	);
}

export default SystemSettingsFieldList;
