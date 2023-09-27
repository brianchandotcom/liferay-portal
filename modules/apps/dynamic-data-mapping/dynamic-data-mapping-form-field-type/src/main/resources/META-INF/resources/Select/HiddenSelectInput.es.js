/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelect} from '@clayui/form';
import React from 'react';

const noop = () => {};

const HiddenSelectInput = ({multiple, name, options, value}) => (
	<ClaySelect
		aria-hidden="true"
		className="form-control"
		hidden
		id={name}
		multiple
		name={name}
		onChange={noop}
		size={multiple ? options.length : null}
		value={value}
	>
		{value.length ? (
			options.map((option, index) => {
				const isSelected = value.includes(option.value);

				if (isSelected) {
					return (
						<ClaySelect.Option
							key={`hiddenSelect${index}`}
							label={option.label}
							value={option.value}
						/>
					);
				}
			})
		) : (
			<ClaySelect.Option defaultValue={value.length} disabled value="" />
		)}
	</ClaySelect>
);

export default HiddenSelectInput;
