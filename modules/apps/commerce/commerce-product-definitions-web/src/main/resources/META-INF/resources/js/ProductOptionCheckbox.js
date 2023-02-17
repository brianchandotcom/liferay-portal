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

const ProductOptionCheckbox = ({
	id,
	label,
	onChange,
	productOptionValues,
	required,
}) => {
	const [option, setOption] = useState(productOptionValues[0]);

	const handleChange = ({target: {checked}}) => {
		const updatedOption = {
			...option,
			selected: checked,
		};

		setOption(updatedOption);
		onChange(updatedOption);
	};

	return (
		<ClayForm.Group>
			<label htmlFor={id}>
				{label}

				<Asterisk required={required} />
			</label>

			<ClayCheckbox
				checked={option.selected}
				id={id}
				label={option.label}
				name={option.name}
				onChange={handleChange}
			/>
		</ClayForm.Group>
	);
};

export default ProductOptionCheckbox;
