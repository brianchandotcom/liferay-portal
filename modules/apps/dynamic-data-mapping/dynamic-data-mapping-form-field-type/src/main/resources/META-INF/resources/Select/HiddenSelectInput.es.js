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
