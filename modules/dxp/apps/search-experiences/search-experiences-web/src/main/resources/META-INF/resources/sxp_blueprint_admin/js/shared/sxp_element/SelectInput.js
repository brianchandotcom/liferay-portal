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
