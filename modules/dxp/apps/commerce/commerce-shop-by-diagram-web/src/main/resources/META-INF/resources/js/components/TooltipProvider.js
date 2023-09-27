/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactPortal} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useLayoutEffect, useRef, useState} from 'react';

import {calculateTooltipStyleFromTarget} from '../utilities/index';

function TooltipProvider({children, closeTooltip, target}) {
	const [tooltipStyle, setTooltipStyle] = useState({});
	const tooltipRef = useRef();
	const bodyRef = useRef(document.querySelector('body'));

	useLayoutEffect(() => {
		const style = calculateTooltipStyleFromTarget(target);

		setTooltipStyle(style);
	}, [target]);

	useLayoutEffect(() => {
		function handleWindowClick(event) {
			if (
				!tooltipRef.current.contains(event.target) &&
				event.target.tagName !== 'text' &&
				!event.target.closest('.autocomplete-dropdown-menu')
			) {
				closeTooltip();
			}
		}

		document.addEventListener('mousedown', handleWindowClick);

		return () => {
			document.removeEventListener('mousedown', handleWindowClick);
		};
	}, [closeTooltip]);

	return (
		<ReactPortal container={bodyRef.current}>
			<div className="diagram-tooltip-wrapper" style={tooltipStyle}>
				<div className="diagram-tooltip" ref={tooltipRef}>
					{children}
				</div>
			</div>
		</ReactPortal>
	);
}

TooltipProvider.propTypes = {
	closeTooltip: PropTypes.func.isRequired,
	target: PropTypes.any,
};

export default TooltipProvider;
