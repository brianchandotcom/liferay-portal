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
import React, {useState} from 'react';

import {useId} from '../../core/hooks/useId';

export function PopoverTooltip({
	alignPosition = 'top',
	content,
	header = undefined,
	trigger,
}) {
	const id = useId();

	const [showPopover, setShowPopover] = useState(false);

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
				<span
					aria-describedby={id}
					onBlur={() => setShowPopover(false)}
					onFocus={() => setShowPopover(true)}
					onMouseEnter={() => setShowPopover(true)}
					onMouseLeave={() => setShowPopover(false)}
					tabIndex="0"
				>
					{trigger}
				</span>
			}
		>
			{content}
		</ClayPopover>
	);
}
