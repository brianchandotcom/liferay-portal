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

import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const GRAYS = [
	'white',
	'gray-100',
	'gray-200',
	'gray-300',
	'gray-400',
	'gray-500',
	'gray-600',
	'gray-700',
	'gray-800',
	'gray-900',
	'black',
	'transparent',
];

const THEME_COLORS = [
	'primary',
	'secondary',
	'success',
	'info',
	'warning',
	'danger',
	'light',
	'lighter',
	'gray',
	'dark',
];

const ColorGuide = () => {
	return (
		<>
			<TokenGroup group="colors" title={Liferay.Language.get('grays')}>
				{GRAYS.map((item) => (
					<TokenItem key={item} label={item} sample={`bg-${item}`} />
				))}
			</TokenGroup>

			<TokenGroup
				group="colors"
				title={Liferay.Language.get('theme-colors')}
			>
				{THEME_COLORS.map((item) => (
					<TokenItem key={item} label={item} sample={`bg-${item}`} />
				))}
			</TokenGroup>
		</>
	);
};

export default ColorGuide;
