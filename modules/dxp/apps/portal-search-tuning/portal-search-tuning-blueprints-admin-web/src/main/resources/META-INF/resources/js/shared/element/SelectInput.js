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
	label,
	name,
	options,
	setFieldTouched,
	setFieldValue,
	value,
}) {
	return (
		<ClaySelect
			aria-label={label}
			className="form-control-sm"
			disabled={disabled}
			onBlur={() => setFieldTouched(name)}
			onChange={(event) => {
				const value =
					typeof options[0].value == 'boolean' ||
					typeof options[0].value == 'number'
						? JSON.parse(event.target.value)
						: event.target.value;

				setFieldValue(name, value);
			}}
			value={value}
		>
			{options &&
				options.map((item) => (
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
