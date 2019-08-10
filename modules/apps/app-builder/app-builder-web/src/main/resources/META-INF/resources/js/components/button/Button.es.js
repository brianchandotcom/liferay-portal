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
import React from 'react';
import {Link} from 'react-router-dom';

const withLink = (button, href) => <Link to={href}>{button}</Link>;

const Button = ({
	className,
	children,
	displayType,
	forwardRef,
	href,
	onClick,
	symbol,
	tooltip
}) => {
	if (tooltip) {
		className += ' lfr-portal-tooltip';
	}

	const Button = symbol ? ClayButtonWithIcon : ClayButton;

	let button = (
		<Button
			className={className}
			data-title={tooltip}
			displayType={displayType}
			onClick={onClick}
			ref={forwardRef}
			symbol={symbol}
		>
			{children}
		</Button>
	);

	if (href) {
		button = withLink(button, href);
	}

	return button;
};

export default React.forwardRef(({children, ...props}, ref) => (
	<Button {...props} forwardRef={ref}>
		{children}
	</Button>
));
