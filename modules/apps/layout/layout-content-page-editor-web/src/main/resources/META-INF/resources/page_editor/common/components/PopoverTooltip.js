/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayPopover from '@clayui/popover';
import React, {useEffect, useState} from 'react';

const BUTTON_COMPONENTS = new Set([ClayButton, ClayButtonWithIcon, 'button']);

export function PopoverTooltip({
	alignPosition = 'top',
	content,
	header = undefined,
	id,
	showTooltipOnClick = true,
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

	const triggerProps = {
		'aria-describedby': showPopover ? id : null,
		'onBlur': () => setShowPopover(false),
		'onFocus': () => setShowPopover(true),
		'onMouseEnter': () => setShowPopover(true),
		'onMouseLeave': () => setShowPopover(false),
		'type': 'button',
	};

	const handleClick = (event) => {
		if (showTooltipOnClick) {
			setShowPopover((show) => !show);
		}

		trigger.props.onClick?.(event);
	};

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
				BUTTON_COMPONENTS.has(trigger.type) ? (
					React.cloneElement(trigger, {
						...triggerProps,
						onClick: handleClick,
					})
				) : (
					<button {...triggerProps}>{trigger}</button>
				)
			}
		>
			{content}
		</ClayPopover>
	);
}
