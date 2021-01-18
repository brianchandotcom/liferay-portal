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

import ClayPopover from '@clayui/popover';
import {ALIGN_POSITIONS, align} from 'frontend-js-web';
import Proptypes from 'prop-types';
import React, {useRef} from 'react';
import ReactDOM from 'react-dom';

/**
 * Tailored implementation of a ClayPopover for Experiences
 *
 * It is triggered on hover, thus it does not need to re-calculate on window resize,
 * scroll or any other event
 */
const Popover = (props) => {
	return ReactDOM.createPortal(
		<PopoverComponent {...props} />,
		document.body
	);
};

const PopoverComponent = ({anchor, children, ...rest}) => {
	const popRef = useRef(null);

	React.useLayoutEffect(() => {
		align(popRef.current, anchor, ALIGN_POSITIONS.Right, false);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayPopover alignPosition="right" ref={popRef} {...rest}>
			{children}
		</ClayPopover>
	);
};

Popover.proptypes = {
	anchor: Proptypes.object,
};

export default Popover;
