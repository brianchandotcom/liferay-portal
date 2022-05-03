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

import ClayIcon from '@clayui/icon';
import React from 'react';

const contextPath = window.location.pathname.substring(
	0,
	window.location.pathname.indexOf('/o/')
);

export const spritemap =
	window.location.protocol +
	'//' +
	window.location.host +
	contextPath +
	'/o/icons/pack/clay.svg';

const Icon = (props) => {
	const {symbol, ...otherProps} = props;

	return <ClayIcon spritemap={spritemap} symbol={symbol} {...otherProps} />;
};

export default Icon;
