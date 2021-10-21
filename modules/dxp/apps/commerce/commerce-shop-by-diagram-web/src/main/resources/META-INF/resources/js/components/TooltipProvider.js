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

import {ReactPortal} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useLayoutEffect, useRef, useState} from 'react';

import {calculateTooltipStyleFromTarget} from '../utilities/index';

function TooltipProvider({children, closeTooltip, target}) {
	const [tooltipStyle, updateTooltipStyle] = useState({});
	const tooltipRef = useRef();
	const bodyRef = useRef(document.querySelector('body'));

	useLayoutEffect(() => {
		const style = calculateTooltipStyleFromTarget(target);

		updateTooltipStyle(style);
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
			<div className="diagram-tooltip-wrapper">
				<div
					className="diagram-tooltip"
					ref={tooltipRef}
					style={tooltipStyle}
				>
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
