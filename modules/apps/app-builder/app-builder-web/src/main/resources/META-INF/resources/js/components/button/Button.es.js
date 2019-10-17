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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import {Link} from 'react-router-dom';
import React from 'react';

const Button = props => {
	const {
		children,
		className,
		forwardRef,
		href,
		symbol,
		tooltip,
		...restProps
	} = props;

	const Button = symbol ? ClayButtonWithIcon : ClayButton;

	let button = (
		<Button
			className={classNames(className)}
			data-title={tooltip}
			ref={forwardRef}
			symbol={symbol}
			{...restProps}
		>
			{children}
		</Button>
	);

	if (href) {
		button = <Link to={href}>{button}</Link>;
	}

	return button;
};

export default React.forwardRef(({children, ...props}, ref) => (
	<Button {...props} forwardRef={ref}>
		{children}
	</Button>
));
