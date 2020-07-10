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

import Collapse from './Collapse';
import {StyleBookContext} from './StyleBookContext';

export default function TokenSet({name, tokens}) {
	return (
		<Collapse label={name}>
			{tokens.map(({name}) => (
				<Token key={name} name={name} />
			))}
		</Collapse>
	);
}

function Token({name}) {
	const {tokenValues = {}, setTokenValues} = useContext(StyleBookContext);

	const [tokenValue, setTokenValue] = useState(tokenValues[name] || '');

	const updateTokenValues = (value) => {
		if (value) {
			const nextTokenValues = {
				...tokenValues,
				[name]: value,
			};

			setTokenValues(nextTokenValues);
		}
	};

	return (
		<ClayForm.Group small>
			<label>{name}</label>
			<ClayInput
				name={name}
				onBlur={() => updateTokenValues(tokenValue)}
				onChange={(event) => setTokenValue(event.target.value)}
				type="text"
				value={tokenValue}
			/>
		</ClayForm.Group>
	);
}
