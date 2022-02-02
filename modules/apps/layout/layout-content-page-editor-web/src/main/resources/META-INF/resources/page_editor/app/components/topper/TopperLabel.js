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

import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {config} from '../../config/index';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';

const TOPPER_BAR_HEIGHT = 24;
const TOPPER_BAR_BORDER_WIDTH = 2;

export function TopperLabel({children, itemElement}) {
	const languageId = useSelector(selectLanguageId);
	const globalContext = useGlobalContext();
	const [positionConfig, setPositionConfig] = useState({
		isInset: false,
		style: {},
	});

	useEffect(() => {
		if (itemElement) {
			const controlMenuContainer = globalContext.document.querySelector(
				'.control-menu-container'
			);
			const pageEditorToolbar = globalContext.document.getElementById(
				`${config.portletNamespace}pageEditorToolbar`
			);

			let controlMenuContainerHeight = 0;
			let itemElementLeft = 0;
			let itemElementRight = 0;
			let itemElementTop = 0;
			let itemElementMarginLeft = 0;
			let itemElementMarginRight = 0;
			let pageEditorToolbarHeight = 0;
			let scrollY = globalContext.window.scrollY;

			const updatePosition = () => {
				const languageDirection =
					Liferay.Language.direction[themeDisplay?.getLanguageId()] ||
					'ltr';

				const left =
					itemElementLeft -
					itemElementMarginLeft +
					TOPPER_BAR_BORDER_WIDTH;

				const right =
					itemElementRight -
					itemElementMarginRight +
					TOPPER_BAR_BORDER_WIDTH;

				const isInset =
					itemElementTop - scrollY <
					controlMenuContainerHeight + pageEditorToolbarHeight;

				const top = isInset
					? itemElementTop + TOPPER_BAR_BORDER_WIDTH
					: itemElementTop - TOPPER_BAR_HEIGHT;

				setPositionConfig({
					isInset,
					style:
						languageDirection === 'rtl'
							? {right, top}
							: {left, top},
				});
			};

			const handleScroll = () => {
				scrollY = globalContext.window.scrollY;
				updatePosition();
			};

			const updateItemElementSize = (itemElement) => {
				const boundingClientRect = itemElement.getBoundingClientRect();
				const computedStyle = globalContext.window.getComputedStyle(
					itemElement
				);

				itemElementMarginRight =
					(computedStyle.marginRight.endsWith('px') &&
						parseInt(computedStyle.marginRight, 10)) ||
					0;

				itemElementMarginLeft =
					(computedStyle.marginLeft.endsWith('px') &&
						parseInt(computedStyle.marginLeft, 10)) ||
					0;

				itemElementLeft =
					globalContext.window.scrollX + boundingClientRect.left;

				// We use here body instead of window.innerWidth
				// to prevent issues if the scrollbar is present.

				itemElementRight =
					globalContext.document.body.getBoundingClientRect().width -
					(boundingClientRect.right - globalContext.window.scrollX);

				itemElementTop =
					globalContext.window.scrollY + boundingClientRect.top;
			};

			const resizeObserver = globalContext.window.ResizeObserver
				? new globalContext.window.ResizeObserver((entries) => {
						entries.forEach((entry) => {
							if (entry.target === itemElement) {
								updateItemElementSize(itemElement);
							}
							else if (entry.target === controlMenuContainer) {
								controlMenuContainerHeight =
									entry.contentRect.height;
							}
							else if (entry.target === pageEditorToolbar) {
								pageEditorToolbarHeight =
									entry.contentRect.height;
							}
						});

						updatePosition();
				  })
				: null;

			let resizeIntervalId = null;

			if (resizeObserver) {
				resizeObserver.observe(itemElement);

				if (controlMenuContainer) {
					resizeObserver.observe(controlMenuContainer);
				}

				if (pageEditorToolbar) {
					resizeObserver.observe(pageEditorToolbar);
				}
			}
			else {
				resizeIntervalId = setInterval(() => {
					updateItemElementSize(itemElement);

					controlMenuContainerHeight =
						controlMenuContainer?.getBoundingClientRect().height ||
						0;

					pageEditorToolbarHeight =
						pageEditorToolbar?.getBoundingClientRect().height || 0;

					updatePosition();
				}, 500);
			}

			globalContext.window.addEventListener('scroll', handleScroll);

			return () => {
				globalContext.window.removeEventListener(
					'scroll',
					handleScroll
				);

				if (resizeObserver) {
					resizeObserver.disconnect();
				}
				else {
					clearInterval(resizeIntervalId);
				}
			};
		}
	}, [itemElement, languageId, globalContext]);

	return (
		<ReactPortal container={globalContext.document.body} wrapper={false}>
			<div
				className={classNames(
					'cadmin',
					'page-editor__topper__bar',
					'tbar',
					{'page-editor__topper__bar--inset': positionConfig.isInset}
				)}
				style={positionConfig.style}
			>
				{children}
			</div>
		</ReactPortal>
	);
}

TopperLabel.propTypes = {
	itemElement: PropTypes.object,
};
