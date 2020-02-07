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
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import {Align} from 'metal-position';
import React, {
	useCallback,
	useEffect,
	useLayoutEffect,
	useMemo,
	useRef,
	useState
} from 'react';
import {createPortal} from 'react-dom';

import debounceRAF from '../../../core/debounceRAF';
import {FLOATING_TOOLBAR_CONFIGURATIONS} from '../../config/constants/floatingToolbarConfigurations';
import {useIsActive} from '../Controls';

export default function FloatingToolbar({
	buttons,
	item,
	itemRef,
	onButtonClick = () => {}
}) {
	const isMounted = useIsMounted();
	const [panelId, setPanelId] = useState(null);
	const panelRef = useRef(null);
	const show = useIsActive()(item.itemId);
	const toolbarRef = useRef(null);
	const [wrapperScrollPosition, setWrapperScrollPosition] = useState(0);
	const [windowWidth, setWindowWidth] = useState(0);

	const PanelComponent = useMemo(
		() => FLOATING_TOOLBAR_CONFIGURATIONS[panelId] || null,
		[panelId]
	);

	const alignElement = useCallback(
		(elementRef, anchorRef, callback) => {
			if (
				isMounted() &&
				show &&
				elementRef.current &&
				anchorRef.current
			) {
				Align.align(
					elementRef.current,
					anchorRef.current,
					getElementAlign(elementRef.current, anchorRef.current),
					false
				);

				if (callback) {
					requestAnimationFrame(() => {
						if (isMounted() && show) {
							callback();
						}
					});
				}
			}
		},
		[isMounted, show]
	);

	const handleButtonClick = useCallback(
		(event, buttonId, newPanelId) => {
			onButtonClick(buttonId, event, itemRef);

			if (newPanelId) {
				if (newPanelId === panelId) {
					setPanelId(null);
				}
				else {
					setPanelId(newPanelId);
				}
			}
		},
		[itemRef, onButtonClick, panelId]
	);

	useEffect(() => {
		const wrapper = document.getElementById('wrapper');

		const handleWindowResize = debounceRAF(() => {
			setWindowWidth(window.innerWidth);
		});

		const handleWrapperScroll = debounceRAF(() => {
			setWrapperScrollPosition(wrapper.scrollTop);
		});

		window.addEventListener('resize', handleWindowResize);
		wrapper.addEventListener('scroll', handleWrapperScroll);

		return () => {
			window.removeEventListener('resize', handleWindowResize);
			wrapper.removeEventListener('scroll', handleWrapperScroll);
		};
	}, []);

	useLayoutEffect(() => {
		alignElement(toolbarRef, itemRef, () => {
			alignElement(panelRef, toolbarRef);
		});
	}, [
		alignElement,
		itemRef,
		panelId,
		show,
		windowWidth,
		wrapperScrollPosition
	]);

	useEffect(() => {
		if (panelId && !show) {
			setPanelId(null);
		}
	}, [panelId, show]);

	return (
		show &&
		buttons.length > 0 && (
			<div onClick={event => event.stopPropagation()}>
				{createPortal(
					<div className="p-2 position-fixed" ref={toolbarRef}>
						<div className="popover position-static">
							<div className="p-2 popover-body">
								{buttons.map(button => (
									<ClayButtonWithIcon
										borderless
										className={classNames({
											active: button.panelId === panelId,
											'lfr-portal-tooltip': button.title
										})}
										displayType="secondary"
										key={button.id}
										onClick={event =>
											handleButtonClick(
												event,
												button.id,
												button.panelId
											)
										}
										small
										symbol={button.icon}
										title={button.title}
									/>
								))}
							</div>
						</div>
					</div>,
					document.body
				)}
				{PanelComponent &&
					createPortal(
						<div
							className="fragments-editor__floating-toolbar-panel p-2 position-fixed"
							ref={panelRef}
						>
							<div className="p-3 popover popover-scrollable position-static">
								<PanelComponent item={item} />
							</div>
						</div>,
						document.body
					)}
			</div>
		)
	);
}

/**
 * @type {object}
 */
const ELEMENT_AVAILABLE_POSITIONS = {
	bottom: [
		Align.Bottom,
		Align.BottomCenter,
		Align.BottomLeft,
		Align.BottomRight
	],

	left: [Align.BottomLeft, Align.Left, Align.LeftCenter, Align.TopRight],
	right: [Align.BottomRight, Align.Right, Align.RightCenter, Align.TopRight],
	top: [Align.Top, Align.TopCenter, Align.TopLeft, Align.TopRight]
};

/**
 * @type {object}
 */
const ELEMENT_POSITION = {
	bottom: {
		left: Align.BottomLeft,
		right: Align.BottomRight
	},

	top: {
		left: Align.TopLeft,
		right: Align.TopRight
	}
};

/**
 * Gets a suggested align of an element to an anchor, following this logic:
 * - Vertically, if the element fits at bottom, it's placed there, otherwise
 *   it is placed at top.
 * - Horizontally, if the element fits at right, it's placed there, otherwise
 *   it is placed at left.
 * @param {HTMLElement|null} element
 * @param {HTMLElement|null} anchor
 * @private
 * @return {number} Selected align
 * @review
 */
const getElementAlign = (element, anchor) => {
	const alignFits = (align, availableAlign) =>
		availableAlign.includes(
			Align.suggestAlignBestRegion(element, anchor, align).position
		);

	const anchorRect = anchor.getBoundingClientRect();
	const elementRect = element.getBoundingClientRect();
	const horizontal =
		anchorRect.right - elementRect.width > 0 ? 'right' : 'left';

	const fallbackVertical = 'top';
	let vertical = 'bottom';

	if (
		!alignFits(
			ELEMENT_POSITION[vertical][horizontal],
			ELEMENT_AVAILABLE_POSITIONS[vertical]
		) &&
		alignFits(
			ELEMENT_POSITION[fallbackVertical][horizontal],
			ELEMENT_AVAILABLE_POSITIONS[fallbackVertical]
		)
	) {
		vertical = fallbackVertical;
	}

	return ELEMENT_POSITION[vertical][horizontal];
};
