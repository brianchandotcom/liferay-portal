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
import {config} from '../../config/index';
import {useSelector} from '../../store/index';
import {useHoverItem, useIsActive} from '../Controls';
import {useGlobalContext} from '../GlobalContext';
import {useUpdatedLayoutDataContext} from '../ResizeContext';

export default React.memo(FloatingToolbar, (prevProps, nextProps) => {
	if (
		prevProps.item !== nextProps.item ||
		prevProps.itemElement !== nextProps.itemElement ||
		prevProps.onButtonClick !== nextProps.onButtonClick
	) {
		return false;
	}

	if (prevProps.buttons.length !== nextProps.buttons.length) {
		return false;
	}

	return prevProps.buttons.every(
		(button, index) => button === nextProps.buttons[index]
	);
});

function FloatingToolbar({
	buttons,
	item,
	itemElement,
	onButtonClick = () => {},
}) {
	const isActive = useIsActive();
	const isMounted = useIsMounted();
	const [panelId, setPanelId] = useState(null);
	const panelRef = useRef(null);
	const hoverItem = useHoverItem();
	const toolbarRef = useRef(null);
	const [hidden, setHidden] = useState(true);

	const [windowRect, setWindowRect] = useState({
		globalContextWindowScrollPosition: 0,
		globalContextWindowWidth: 0,
		windowScrollPosition: 0,
		windowWidth: 0,
	});

	const [show, setShow] = useState(false);
	const updatedLayoutData = useUpdatedLayoutDataContext();
	const globalContext = useGlobalContext();

	const languageId = useSelector((state) => state.languageId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const PanelComponent = useMemo(
		() =>
			FLOATING_TOOLBAR_CONFIGURATIONS[panelId]
				? React.memo(FLOATING_TOOLBAR_CONFIGURATIONS[panelId])
				: null,
		[panelId]
	);

	const alignElement = useCallback(
		(element, anchor, callback) => {
			if (isMounted() && show && element && anchor) {
				try {
					align(element, anchor, {
						globalContext,
						rtl:
							config.languageDirection?.[
								themeDisplay?.getLanguageId()
							] === 'rtl',
					});
				}
				catch (error) {
					console.error(error);
				}

				if (callback) {
					requestAnimationFrame(() => {
						if (isMounted() && show) {
							callback();
						}
					});
				}
			}
			else if (callback && isMounted() && show) {
				callback();
			}
		},
		[globalContext, isMounted, show]
	);

	const handleButtonClick = useCallback(
		(buttonId, newPanelId) => {
			onButtonClick(buttonId);

			if (newPanelId) {
				if (newPanelId === panelId) {
					setPanelId(null);
				}
				else {
					setPanelId(newPanelId);
				}
			}
		},
		[onButtonClick, panelId]
	);

	useEffect(() => {
		setShow(isActive(item.itemId) && !!itemElement);
	}, [isActive, item.itemId, itemElement]);

	useEffect(() => {
		const handleWindowRectChanged = debounceRAF(() => {
			setWindowRect({
				globalContextWindowScrollPosition: globalContext.window.scrollY,
				globalContextWindowWidth: globalContext.window.innerWidth,
				windowScrollPosition: window.scrollY,
				windowWidth: window.innerWidth,
			});
		});

		globalContext.window.addEventListener(
			'resize',
			handleWindowRectChanged
		);
		globalContext.window.addEventListener(
			'scroll',
			handleWindowRectChanged
		);

		if (globalContext.window !== window) {
			window.addEventListener('resize', handleWindowRectChanged);
			window.addEventListener('scroll', handleWindowRectChanged);
		}

		return () => {
			globalContext.window.removeEventListener(
				'resize',
				handleWindowRectChanged
			);
			globalContext.window.removeEventListener(
				'scroll',
				handleWindowRectChanged
			);

			window.removeEventListener('resize', handleWindowRectChanged);
			window.removeEventListener('scroll', handleWindowRectChanged);
		};
	}, [globalContext]);

	useEffect(() => {
		alignElement(toolbarRef.current, itemElement, () => {
			alignElement(
				panelRef.current,
				toolbarRef.current || itemElement,
				() => {
					setHidden(false);
				}
			);
		});
	}, [
		alignElement,
		item.config,
		itemElement,
		panelId,
		selectedViewportSize,
		updatedLayoutData,
		show,
		languageId,
		windowRect,
	]);

	useEffect(() => {
		const sidebarElement = document.querySelector(
			'.page-editor__sidebar__content'
		);

		if (sidebarElement) {
			const handleTransitionEnd = (event) => {
				if (event.target === sidebarElement) {
					alignElement(toolbarRef.current, itemElement, () => {
						alignElement(panelRef.current, toolbarRef.current);
					});
					setHidden(false);
				}
			};

			const handleTransitionStart = (event) => {
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
	}, [alignElement, item, itemElement]);

	useEffect(() => {
		const sideNavigation =
			Liferay.SideNavigation &&
			Liferay.SideNavigation.instance(
				document.querySelector('.product-menu-toggle')
			);

		const handleTransitionEnd = () => {
			alignElement(toolbarRef.current, itemElement, () => {
				alignElement(panelRef.current, toolbarRef.current);
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
			sideNavigationListeners.forEach((listener) =>
				listener.removeListener()
			);
		};
	}, [alignElement, itemElement]);

	useEffect(() => {
		if (panelId && !show) {
			setPanelId(null);
		}
	}, [panelId, show]);

	return (
		show && (
			<div onClick={(event) => event.stopPropagation()}>
				{(buttons.length > 0 || !panelId) &&
					createPortal(
						<div
							className={classNames(
								'p-2',
								'page-editor__floating-toolbar',
								'position-fixed',
								{
									'page-editor__floating-toolbar--hidden': hidden,
								}
							)}
							onMouseOver={(event) => {
								event.stopPropagation();
								hoverItem(null);
							}}
							ref={toolbarRef}
						>
							<div className="popover position-static">
								<div className="d-flex p-2 popover-body">
									{buttons.map((button) => (
										<ClayButtonWithIcon
											borderless
											className={classNames(
												'mx-1',
												button.className,
												{
													active:
														button.panelId ===
														panelId,
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
	itemElement: PropTypes.object,
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

const align = (element, anchor, {globalContext, rtl}) => {
	const horizontal = (() => {
		const {
			left: wrapperLeft,
			right: wrapperRight,
		} = globalContext.document
			?.getElementById('page-editor')
			?.getBoundingClientRect() ?? {left: 0, right: 0};

		const {
			left: anchorLeft,
			right: anchorRight,
		} = anchor.getBoundingClientRect();

		const {width: elementWidth} = element.getBoundingClientRect();

		if (rtl) {
			const fitsOnLeft = anchorLeft + elementWidth < wrapperRight;

			return fitsOnLeft ? 'left' : 'right';
		}
		else {
			const fitsOnRight = anchorRight - elementWidth > wrapperLeft;

			return fitsOnRight ? 'right' : 'left';
		}
	})();

	const vertical = (() => {
		const alignFits = (align, availableAlign) => {
			try {
				return availableAlign.includes(
					Align.suggestAlignBestRegion(element, anchor, align)
						.position
				);
			}
			catch (error) {
				return true;
			}
		};

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

		return vertical;
	})();

	Align.align(element, anchor, ELEMENT_POSITION[vertical][horizontal], false);

	const {
		marginLeft: anchorMarginLeft,
		marginRight: anchorMarginRight,
	} = getComputedStyle(anchor);

	const margin = rtl
		? Math.abs(parseInt(anchorMarginLeft, 10))
		: parseInt(anchorMarginRight, 10);

	if (globalContext.iframe && globalContext.document.contains(anchor)) {
		const {left, top} = globalContext.iframe.getBoundingClientRect();

		element.style.transform = `translateX(${
			left + margin
		}px) translateY(${top}px)`;
	}
	else {
		element.style.transform = '';
	}
};
