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

import ClayForm, {ClayInput} from '@clayui/form';
import React, {useContext, useState} from 'react';

import {StyleBookContext} from './StyleBookContext';

export default function Token({name}) {
	const {tokenValues = {}, setTokenValues, namespace} = useContext(
		StyleBookContext
	);

	const [tokenValue, setTokenValue] = useState(tokenValues[name] || '');

	const id = `${namespace}_tokenId_${name}`;

	const updateTokenValues = (value) => {
		if (value) {
			setTokenValues({
				...tokenValues,
				[name]: value,
			});
		}
	};

	return (
		<ClayForm.Group small>
			<label htmlFor={id}>{name}</label>
			<ClayInput
				id={id}
				onBlur={() => updateTokenValues(tokenValue)}
				onChange={(event) => setTokenValue(event.target.value)}
				type="text"
				value={tokenValue}
			/>
		</ClayForm.Group>
	);
}
