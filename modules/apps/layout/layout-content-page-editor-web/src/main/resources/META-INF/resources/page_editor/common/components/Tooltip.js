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

import ClayTooltip from '@clayui/tooltip';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';

const DEFAULT_SHOW_DELAY = 600;

export function Tooltip({
	delay = DEFAULT_SHOW_DELAY,
	hoverElement,
	id: tooltipId,
	label,
	positionElement,
}) {
	const [tooltipStyle, setTooltipStyle] = useState(null);

	useEffect(() => {
		if (!hoverElement || !positionElement) {
			return;
		}

		let showTimeoutId;

		const handleMouseLeave = () => {
			clearTimeout(showTimeoutId);
			setTooltipStyle(null);
		};

		const handleMouseOver = () => {
			clearTimeout(showTimeoutId);

			showTimeoutId = setTimeout(() => {
				const rect = positionElement.getBoundingClientRect();

				setTooltipStyle({
					left: rect.left + rect.width / 2,
					top: rect.top,
				});
			}, delay);
		};

		hoverElement.addEventListener('mouseleave', handleMouseLeave);
		hoverElement.addEventListener('mouseover', handleMouseOver);

		return () => {
			clearTimeout(showTimeoutId);
			hoverElement.removeEventListener('mouseleave', handleMouseLeave);
			hoverElement.removeEventListener('mouseover', handleMouseOver);
		};
	}, [delay, hoverElement, positionElement]);

	return tooltipStyle ? (
		<ReactPortal className="cadmin">
			<ClayTooltip
				alignPosition="top"
				className="page-editor__tooltip position-fixed"
				id={tooltipId}
				show
				style={tooltipStyle}
			>
				{label}
			</ClayTooltip>
		</ReactPortal>
	) : null;
}
