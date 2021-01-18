/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayPopover from '@clayui/popover';
import {ALIGN_POSITIONS, align as alignElement} from 'frontend-js-web';
import Proptypes from 'prop-types';
import React, {useLayoutEffect, useRef} from 'react';
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

const PopoverComponent = ({
	anchor,
	align = ALIGN_POSITIONS.Top,
	children,
	position = 'top',
	...rest
}) => {
	const popRef = useRef(null);

	useLayoutEffect(() => {
		alignElement(popRef.current, anchor, align, false);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayPopover alignPosition={position} ref={popRef} {...rest}>
			{children}
		</ClayPopover>
	);
};

Popover.proptypes = {
	anchor: Proptypes.instanceOf(Element),
};

export default Popover;
