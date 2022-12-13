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

const getToTooltipText = (value: string) => {
	const toArray = value.split(',');
	const lastItem = toArray.length > 1 ? toArray.pop() : null;

	return toArray.length > 1
		? `${toArray.join(',')} ${Liferay.Language.get(
				'and'
		  ).toLocaleLowerCase()} ${lastItem}.`
		: value;
};

export function NotificationQueueEntryToDataRenderer({value}: {value: string}) {
	const [isPopoverVisible, setPopoverVisible] = useState(false);

	return (
		<ClayPopover
			data-testid="clayPopover"
			disableScroll
			header={Liferay.Language.get('to')}
			onShowChange={setPopoverVisible}
			show={isPopoverVisible}
			style={{width: 256}}
			trigger={
				<span
					className="ddm-tooltip"
					onMouseOut={() => setPopoverVisible(false)}
					onMouseOver={() => setPopoverVisible(true)}
				>
					{value.length > 25
						? value.slice(0, 25).trim() + '...'
						: value}
				</span>
			}
		>
			{getToTooltipText(value)}
		</ClayPopover>
	);
}
