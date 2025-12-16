/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayPopover, {ALIGN_POSITIONS} from '@clayui/popover';
import React, {useEffect, useState} from 'react';

type Props = {
	alignPosition?: (typeof ALIGN_POSITIONS)[number];
	content: string;
	header?: string;
	id?: string;
	showTooltipOnClick?: boolean;
	trigger: React.ReactElement;
};

const BUTTON_COMPONENTS: Set<any> = new Set([
	ClayButton,
	ClayButtonWithIcon,
	'button',
]);

export function PopoverTooltip({
	alignPosition = 'top',
	content,
	header = undefined,
	id,
	showTooltipOnClick = true,
	trigger,
}: Props) {
	const [showPopover, setShowPopover] = useState(false);

	useEffect(() => {
		if (!showPopover) {
			return;
		}

		const handleKeyDown = (event: KeyboardEvent) => {
			if (event.key === 'Escape') {
				setShowPopover(false);
			}
		};

		window.addEventListener('keydown', handleKeyDown);

		return () => window.removeEventListener('keydown', handleKeyDown);
	}, [showPopover]);

	const triggerProps: any = {
		'aria-describedby': showPopover ? id : null,
		'onBlur': () => setShowPopover(false),
		'onFocus': () => setShowPopover(true),
		'onMouseEnter': () => setShowPopover(true),
		'onMouseLeave': () => setShowPopover(false),
		'type': 'button',
	};

	const handleClick = (event: React.MouseEvent) => {
		if (showTooltipOnClick) {
			setShowPopover((show) => !show);
		}

		(trigger as React.ReactElement)?.props?.onClick?.(event);
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
				BUTTON_COMPONENTS.has((trigger as React.ReactElement).type) ? (
					React.cloneElement(trigger as React.ReactElement, {
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
