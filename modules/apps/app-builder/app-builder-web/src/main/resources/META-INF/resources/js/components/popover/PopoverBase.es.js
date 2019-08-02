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

import getCN from 'classnames';
import omitDefinedProps from '../../utils/omitDefinedProps.es';
import React from 'react';
import {PropTypes} from 'prop-types';

const Header = ({children}) => {
	return <div className="border-0 popover-header">{children}</div>;
};

const Body = ({children}) => {
	return <div className="popover-body">{children}</div>;
};

const Footer = ({children}) => {
	return <div className="popover-footer">{children}</div>;
};

const PopoverBase = ({
	children,
	className,
	elementRef,
	placement = 'none',
	visible = false,
	...otherProps
}) => {
	const classes = getCN('popover', className, {
		[`clay-popover-${placement}`]: placement,
		['hide']: !visible
	});

	return (
		<div
			{...omitDefinedProps(otherProps, PopoverBase.propTypes)}
			className={classes}
			ref={elementRef}
		>
			{placement !== 'none' && <div className="arrow" />}
			{children}
		</div>
	);
};

PopoverBase.propTypes = {
	placement: PropTypes.oneOf(['bottom', 'left', 'none', 'right', 'top']),
	visible: PropTypes.bool
};

PopoverBase.Header = Header;
PopoverBase.Body = Body;
PopoverBase.Footer = Footer;

export {PopoverBase};
export default PopoverBase;
