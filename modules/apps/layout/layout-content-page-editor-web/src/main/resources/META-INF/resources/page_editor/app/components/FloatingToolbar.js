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
import {Align} from 'metal-position';
import React, {useLayoutEffect, useRef, useState, useEffect} from 'react';
import {createPortal} from 'react-dom';

import useWindowScroll from '../../core/hooks/useWindowScroll';
import useWindowWidth from '../../core/hooks/useWindowWidth';
import ConfigurationPanel from './ConfigurationPanel';
import {useFloatingToolbar, useIsActive} from './Controls';

export default function FloatingToolbar({
	buttons,
	item,
	itemRef,
	onButtonClick
}) {
	const isActive = useIsActive();
	const popoverRef = useRef(null);
	const show = isActive(item.itemId);
	const floatingToolbar = useFloatingToolbar();

	const [activeConfigurationPanel, setActiveConfigurationPanel] = useState(
		null
	);

	const windowWidth = useWindowWidth();
	const {scrollX, scrollY} = useWindowScroll();

	useLayoutEffect(() => {
		if (show && itemRef.current && popoverRef.current) {
			const suggestedAlign = Align.suggestAlignBestRegion(
				popoverRef.current,
				itemRef.current,
				Align.BottomRight
			);

			const bestPosition =
				suggestedAlign.position !== Align.BottomRight
					? Align.TopRight
					: Align.BottomRight;

			Align.align(
				popoverRef.current,
				itemRef.current,
				bestPosition,
				false
			);
		}
	}, [show, itemRef, popoverRef, windowWidth, scrollX, scrollY]);

	useEffect(() => {
		if (!show) {
			setActiveConfigurationPanel(false);
		}

		floatingToolbar(popoverRef);
	}, [floatingToolbar, show]);

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
								if (onButtonClick) {
									onButtonClick(id);
								}

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

function FloatingToolbarButton({active, icon, id, onClick, panelId}) {
	return (
		<ClayButtonWithIcon
			borderless
			className={active && 'active'}
			displayType="secondary"
			onClick={() => {
				if (onClick) {
					onClick(id, panelId);
				}
			}}
			small
			symbol={icon}
		/>
	);
}
