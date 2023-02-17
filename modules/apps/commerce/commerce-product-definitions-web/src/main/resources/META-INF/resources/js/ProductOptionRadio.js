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

import ClayForm, {ClayRadio, ClayRadioGroup} from '@clayui/form';
import React, {useState} from 'react';

import Asterisk from './Asterisk';
import {getInitialOption} from './utils';

const ProductOptionRadio = ({
	id,
	label,
	name,
	onChange,
	productOptionValues,
	required,
}) => {
	const initialOption = getInitialOption(productOptionValues);

	const [selectedOption, setSelectedOption] = useState(initialOption);

	const handleChange = (value) => {
		const updatedOption = productOptionValues.find(
			(option) => option.value === value
		);

		setSelectedOption(updatedOption);
		onChange(updatedOption);
	};

	return (
		<ClayForm.Group>
			<label htmlFor={id}>
				{label}

				<Asterisk required={required} />
			</label>

			<ClayRadioGroup
				id={id}
				name={name}
				onChange={handleChange}
				value={selectedOption.value}
			>
				{productOptionValues.map(({key, label, value}) => (
					<ClayRadio key={key} label={label} value={value} />
				))}
			</ClayRadioGroup>
		</ClayForm.Group>
	);
};

export default ProductOptionRadio;
