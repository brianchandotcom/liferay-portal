/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayInput} from '@clayui/form';
import React from 'react';

function TextInput({disabled, id, label, name, onBlur, onChange, value}) {
	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
		}
	};

	return (
		<ClayInput.Group small>
			<ClayInput.GroupItem prepend>
				<ClayInput
					aria-label={label}
					disabled={disabled}
					id={id}
					name={name}
					onBlur={onBlur}
					onChange={onChange}
					onKeyDown={_handleKeyDown}
					value={value}
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

export default TextInput;
