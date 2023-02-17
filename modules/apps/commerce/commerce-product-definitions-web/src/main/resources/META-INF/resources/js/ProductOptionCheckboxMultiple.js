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

import ClayForm, {ClayCheckbox} from '@clayui/form';
import React, {useState} from 'react';

import Asterisk from './Asterisk';

const ProductOptionCheckboxMultiple = ({
	label,
	onChange,
	productOptionValues,
	required,
}) => {
	const [options, setOptions] = useState(
		productOptionValues.reduce((acc, cur) => {
			acc[cur.id] = cur;

			return acc;
		}, {})
	);

	const handleChange = ({target: {checked}}, id) => {
		const updatedOptions = {
			...options,
			[id]: {...options[id], selected: checked},
		};

		setOptions(updatedOptions);
		onChange(updatedOptions);
	};

	return (
		<ClayForm.Group>
			<label>
				{label}

				<Asterisk required={required} />
			</label>

			{Object.values(options).map(({id, key, label, name, selected}) => (
				<ClayCheckbox
					checked={selected}
					key={key}
					label={label}
					name={name}
					onChange={(event) => handleChange(event, id)}
				/>
			))}
		</ClayForm.Group>
	);
};

export default ProductOptionCheckboxMultiple;
