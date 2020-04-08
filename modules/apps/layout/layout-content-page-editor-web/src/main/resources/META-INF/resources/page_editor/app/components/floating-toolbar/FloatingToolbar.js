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
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';
import {createPortal} from 'react-dom';

import debounceRAF from '../../../core/debounceRAF';
import {
	getEditableItemPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {EDITABLE_FLOATING_TOOLBAR_CLASSNAMES} from '../../config/constants/editableFloatingToolbarClassNames';
import {FLOATING_TOOLBAR_CONFIGURATIONS} from '../../config/constants/floatingToolbarConfigurations';
import {useHoverItem, useIsActive} from '../Controls';

export default function FloatingToolbar({
	buttons,
	item,
	itemRef,
	onButtonClick = () => {},
}) {
	const isMounted = useIsMounted();
	const [panelId, setPanelId] = useState(null);
	const panelRef = useRef(null);
	const show = useIsActive()(item.itemId);
	const hoverItem = useHoverItem();
	const toolbarRef = useRef(null);
	const [hidden, setHidden] = useState(false);
	const [windowScrollPosition, setWindowScrollPosition] = useState(0);
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
				try {
					Align.align(
						elementRef.current,
						anchorRef.current,
						getElementAlign(elementRef.current, anchorRef.current),
						false
					);
				}
				catch (error) {}

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
		(buttonId, newPanelId) => {
			onButtonClick(buttonId, itemRef);

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
		const handleWindowResize = debounceRAF(() => {
			setWindowWidth(window.innerWidth);
		});

		const handleWindowScroll = debounceRAF(() => {
			setWindowScrollPosition(window.scrollY);
		});

		window.addEventListener('resize', handleWindowResize);
		window.addEventListener('scroll', handleWindowScroll);

		return () => {
			window.removeEventListener('resize', handleWindowResize);
			window.removeEventListener('scroll', handleWindowScroll);
		};
	}, []);

	useEffect(() => {
		if (!itemRef.current) {
			return;
		}

		const {marginRight: itemRefMarginRight} = getComputedStyle(
			itemRef.current
		);

		if (show && parseInt(itemRefMarginRight, 10) < 0) {
			toolbarRef.current.style.transform = `translate(${itemRefMarginRight})`;
		}
	}, [itemRef, show]);

	useEffect(() => {
		alignElement(toolbarRef, itemRef, () => {
			alignElement(panelRef, toolbarRef);
		});
	}, [
		alignElement,
		item.config,
		itemRef,
		panelId,
		show,
		windowScrollPosition,
		windowWidth,
	]);

	useEffect(() => {
		const sidebarElement = document.querySelector(
			'.page-editor__sidebar__content'
		);

		if (sidebarElement) {
			const handleTransitionEnd = event => {
				if (event.target === sidebarElement) {
					alignElement(toolbarRef, itemRef, () => {
						alignElement(panelRef, toolbarRef);
					});
					setHidden(false);
				}
			};

			const handleTransitionStart = event => {
				if (event.target === sidebarElement) {
					setHidden(true);
				}
			};

			sidebarElement.addEventListener(
				'transitionend',
				handleTransitionEnd
			);
			sidebarElement.addEventListener(
				'transitionstart',
				handleTransitionStart
			);

			return () => {
				sidebarElement.removeEventListener(
					'transitionend',
					handleTransitionEnd
				);

				sidebarElement.removeEventListener(
					'transitionstart',
					handleTransitionStart
				);
			};
		}
	}, [alignElement, item, itemRef]);

	useEffect(() => {
		const sideNavigation =
			Liferay.sideNavigation &&
			Liferay.SideNavigation.instance(
				document.querySelector('.product-menu-toggle')
			);

		const handleTransitionEnd = () => {
			alignElement(toolbarRef, itemRef, () => {
				alignElement(panelRef, toolbarRef);
			});
			setHidden(false);
		};

		const handleTransitionStart = () => {
			setHidden(true);
		};

		let sideNavigationListeners = [];

		if (sideNavigation) {
			sideNavigationListeners = [
				sideNavigation.on('open.lexicon.sidenav', handleTransitionEnd),
				sideNavigation.on(
					'openStart.lexicon.sidenav',
					handleTransitionStart
				),
				sideNavigation.on(
					'closed.lexicon.sidenav',
					handleTransitionEnd
				),
				sideNavigation.on(
					'closedStart.lexicon.sidenav',
					handleTransitionStart
				),
			];
		}

		return () => {
			sideNavigationListeners.forEach(listener =>
				listener.removeListener()
			);
		};
	}, [alignElement, itemRef]);

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
					<div
						className={classNames(
							'p-2',
							'page-editor__floating-toolbar',
							'position-fixed',
							{
								'page-editor__floating-toolbar--hidden': hidden,
							}
						)}
						onMouseOver={event => {
							event.stopPropagation();
							hoverItem(null);
						}}
						ref={toolbarRef}
					>
						<div className="popover position-static">
							<div className="p-2 popover-body">
								{buttons.map(button => (
									<ClayButtonWithIcon
										borderless
										className={classNames(
											'mx-1',
											button.className,
											{
												active:
													button.panelId === panelId,
												'lfr-portal-tooltip':
													button.title,
											}
										)}
										displayType="secondary"
										key={button.id}
										onClick={() =>
											handleButtonClick(
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
							className={
								EDITABLE_FLOATING_TOOLBAR_CLASSNAMES.panel
							}
							ref={panelRef}
						>
							<div
								className={classNames(
									'p-3 popover popover-scrollable position-static',
									{
										'page-editor__floating-toolbar--hidden': hidden,
									}
								)}
							>
								<PanelComponent item={item} />
							</div>
						</div>,
						document.body
					)}
			</div>
		)
	);
}

FloatingToolbar.propTypes = {
	buttons: PropTypes.arrayOf(
		PropTypes.shape({
			icon: PropTypes.string.isRequired,
			id: PropTypes.string.isRequired,
			panelId: PropTypes.string,
			title: PropTypes.string.isRequired,
		})
	),
	item: PropTypes.oneOfType([
		getEditableItemPropTypes(),
		getLayoutDataItemPropTypes(),
	]),
	itemRef: PropTypes.shape({current: PropTypes.instanceOf(Element)}),
	onButtonClick: PropTypes.func,
};

/**
 * @type {object}
 */
const ELEMENT_AVAILABLE_POSITIONS = {
	bottom: [
		Align.Bottom,
		Align.BottomCenter,
		Align.BottomLeft,
		Align.BottomRight,
	],

	left: [Align.BottomLeft, Align.Left, Align.LeftCenter, Align.TopRight],
	right: [Align.BottomRight, Align.Right, Align.RightCenter, Align.TopRight],
	top: [Align.Top, Align.TopCenter, Align.TopLeft, Align.TopRight],
};

/**
 * @type {object}
 */
const ELEMENT_POSITION = {
	bottom: {
		left: Align.BottomLeft,
		right: Align.BottomRight,
	},

	top: {
		left: Align.TopLeft,
		right: Align.TopRight,
	},
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
	const alignFits = (align, availableAlign) => {
		try {
			return availableAlign.includes(
				Align.suggestAlignBestRegion(element, anchor, align).position
			);
		}
		catch (error) {
			return true;
		}
	};

	const fallbackVertical = 'top';
	let horizontal = 'left';
	let vertical = 'bottom';

	const productMenu = document.querySelector('.product-menu-toggle');

	const productMenuOpen = productMenu
		? Liferay.SideNavigation.instance(productMenu).visible()
		: false;

	const wrapperWidth = document
		.getElementById('wrapper')
		.getBoundingClientRect().width;

	try {
		const {right: anchorRight} = anchor.getBoundingClientRect();
		const {width: elementWidth} = element.getBoundingClientRect();

		if (productMenuOpen && anchorRight - elementWidth < wrapperWidth / 2) {
			horizontal = 'left';
		}
		else {
			horizontal = anchorRight - elementWidth > 0 ? 'right' : 'left';
		}
	}
	catch (error) {}

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
