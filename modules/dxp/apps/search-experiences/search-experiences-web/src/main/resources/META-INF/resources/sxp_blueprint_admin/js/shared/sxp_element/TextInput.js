/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
