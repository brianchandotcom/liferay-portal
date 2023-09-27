/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPopover from '@clayui/popover';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import {ALIGN_POSITIONS, align} from 'frontend-js-web';
import Proptypes from 'prop-types';
import React, {useRef} from 'react';

/**
 * Tailored implementation of a ClayPopover for Experiences
 *
 * It is triggered on hover, thus it does not need to re-calculate on window resize,
 * scroll or any other event
 */
const Popover = (props) => {
	return (
		<ReactPortal>
			<PopoverComponent {...props} />
		</ReactPortal>
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
