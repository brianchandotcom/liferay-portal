/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import React from 'react';

function SelectInput({
	disabled,
	id,
	label,
	name,
	nullable,
	onBlur,
	onChange,
	options = [],
	value,
}) {
	const _handleKeyDown = (event) => {
		if (event.key === 'Enter') {
			event.preventDefault();
		}
	};

	return (
		<ClaySelect
			aria-label={label}
			className="form-control-sm"
			disabled={disabled}
			id={id}
			name={name}
			onBlur={onBlur}
			onChange={onChange}
			onKeyDown={_handleKeyDown}
			value={value}
		>
			{(nullable || value === '') && (
				<ClaySelect.Option key="nullableOption" label="" value="" />
			)}

			{options.map((item) => (
				<ClaySelect.Option
					key={item.value}
					label={item.label}
					value={item.value}
				/>
			))}
		</ClaySelect>
	);
}

export default React.memo(SelectInput);
