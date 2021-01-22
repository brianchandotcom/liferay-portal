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

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React from 'react';

const IconWithPopover = ({children: content, header, show, triggerProps}) => {
	return (
		<ClayPopover
			alignPosition="left"
			disableScroll
			header={header}
			show={show}
			trigger={<TriggerIcon {...triggerProps} />}
		>
			{content}
		</ClayPopover>
	);
};

const TriggerIcon = ({
	className,
	triggerProps: {onMouseOut, onMouseOver, symbol, ...restProps},
}) => {
	return (
		<div className={className}>
			<ClayIcon
				onMouseOut={onMouseOut}
				onMouseOver={onMouseOver}
				symbol={symbol}
				{...restProps}
			/>
		</div>
	);
};

IconWithPopover.TriggerIcon = TriggerIcon;
export default IconWithPopover;
