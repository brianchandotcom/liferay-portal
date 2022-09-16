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
import React, {useEffect, useState} from 'react';

export function PopoverTooltip({
	alignPosition = 'top',
	content,
	header = undefined,
	id,
	trigger,
}) {
	const [showPopover, setShowPopover] = useState(false);

	useEffect(() => {
		if (!showPopover) {
			return;
		}

		const handleKeyDown = (event) => {
			if (event.key === 'Escape') {
				setShowPopover(false);
			}
		};

		window.addEventListener('keydown', handleKeyDown);

		return () => window.removeEventListener('keydown', handleKeyDown);
	}, [showPopover]);

	return (
		<ClayPopover
			alignPosition={alignPosition}
			className="position-fixed"
			disableScroll
			header={header}
			id={id}
			onShowChange={setShowPopover}
			role="tooltip"
			show={showPopover}
			trigger={
				<button
					aria-controls={id}
					onBlur={() => setShowPopover(false)}
					onClick={() => setShowPopover((show) => !show)}
					onFocus={() => setShowPopover(true)}
					onMouseEnter={() => setShowPopover(true)}
					onMouseLeave={() => setShowPopover(false)}
					type="button"
				>
					{trigger}
				</button>
			}
		>
			{content}
		</ClayPopover>
	);
}
