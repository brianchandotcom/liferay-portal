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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';

const LinkOrButton = ({
	button,
	children,
	disabled,
	href,
	symbol,
	...otherProps
}) => {
	return (
		<>
			{href && !disabled ? (
				<ClayLink {...otherProps} button={button} href={href}>
					{symbol ? <ClayIcon symbol={symbol} /> : children}
				</ClayLink>
			) : (
				<ClayButton
					{...otherProps}
					block={button?.block}
					disabled={disabled}
				>
					{symbol ? <ClayIcon symbol={symbol} /> : children}
				</ClayButton>
			)}
		</>
	);
};

export default LinkOrButton;
