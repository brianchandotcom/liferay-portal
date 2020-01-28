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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {useEventListener} from 'frontend-js-react-web';
import React, {
	useLayoutEffect,
	useRef,
	useState,
	useMemo,
	useCallback
} from 'react';
import {createPortal} from 'react-dom';

import useWindowWidth from '../../core/hooks/useWindowWidth';
import ConfigurationPanel, {alignElement} from './ConfigurationPanel';
import {useIsActive} from './Controls';

export default function FloatingToolbar({
	buttons,
	item,
	itemRef,
	onButtonClick = () => {}
}) {
	const isActive = useIsActive();
	const popoverRef = useRef(null);
	const show = isActive(item.itemId);
	const wrapperContainerEl = useMemo(
		() => document.getElementById('wrapper'),
		[]
	);

	const [activeConfigurationPanel, setActiveConfigurationPanel] = useState(
		null
	);

	const windowWidth = useWindowWidth();

	const alignFloatingToolbar = useCallback(() => {
		if (show && itemRef.current && popoverRef.current) {
			alignElement(popoverRef.current, itemRef.current);
		}
	}, [show, itemRef, popoverRef]);

	useEventListener(
		'scroll',
		() => alignFloatingToolbar(),
		true,
		wrapperContainerEl
	);

	useLayoutEffect(() => alignFloatingToolbar(), [
		alignFloatingToolbar,
		windowWidth
	]);

	return (
		show &&
		buttons.length &&
		createPortal(
			<div className="pb-2 position-absolute pr-2 pt-2" ref={popoverRef}>
				<ClayPopover
					alignPosition={false}
					className="position-static"
					show
				>
					{buttons.map(button => (
						<FloatingToolbarButton
							active={button.panelId === activeConfigurationPanel}
							key={button.panelId}
							onClick={(id, panelId) => {
								onButtonClick(id);

								if (panelId) {
									if (activeConfigurationPanel === panelId) {
										setActiveConfigurationPanel(null);
									} else {
										setActiveConfigurationPanel(panelId);
									}
								}
							}}
							{...button}
						/>
					))}
				</ClayPopover>

				{activeConfigurationPanel && (
					<ConfigurationPanel
						configurationPanel={activeConfigurationPanel}
						item={item}
						popoverRef={popoverRef}
					/>
				)}
			</div>,
			document.body
		)
	);
}

function FloatingToolbarButton({
	active,
	icon,
	id,
	onClick,
	panelId,
	title,
	...otherProps
}) {
	return (
		<ClayButtonWithIcon
			borderless
			className={classNames({
				active,
				'lfr-portal-tooltip': title
			})}
			displayType="secondary"
			onClick={() => {
				if (onClick) {
					onClick(id, panelId);
				}
			}}
			small
			symbol={icon}
			title={title}
			{...otherProps}
		/>
	);
}
