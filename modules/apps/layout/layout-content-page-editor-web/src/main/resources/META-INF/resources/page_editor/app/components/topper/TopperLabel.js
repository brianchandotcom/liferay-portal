/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {useGlobalContext} from '../../contexts/GlobalContext';
import {useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';

const TOPPER_BAR_HEIGHT = 24;
const TOPPER_BAR_BORDER_WIDTH = 2;

export function TopperLabel({children, itemElement, style}) {
	const globalContext = useGlobalContext();
	const languageId = useSelector(selectLanguageId);
	const layoutData = useSelector((state) => state.layoutData);
	const [positionConfig, setPositionConfig] = useState({
		isInset: false,
		style: {},
	});

	const wrapper = useMemo(
		() => globalContext.document.getElementById('wrapper'),
		[globalContext]
	);

	useEffect(() => {
		if (itemElement) {
			const pageEditorWrapper = globalContext.document.getElementById(
				'page-editor'
			);

			let itemElementLeft = 0;
			let itemElementRight = 0;
			let itemElementTop = 0;
			let itemElementMarginLeft = 0;
			let itemElementMarginRight = 0;
			let scrollY = wrapper.scrollTop;

			const updatePosition = () => {
				const languageDirection =
					Liferay.Language.direction?.[
						themeDisplay?.getLanguageId()
					] || 'ltr';

				const left =
					itemElementLeft -
					itemElementMarginLeft +
					TOPPER_BAR_BORDER_WIDTH;

				const right =
					itemElementRight -
					itemElementMarginRight +
					TOPPER_BAR_BORDER_WIDTH;

				const isInset =
					Math.floor(itemElementTop - TOPPER_BAR_HEIGHT - scrollY) <=
					0;

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
				scrollY = wrapper.scrollTop;
				updatePosition();
			};

			const updateItemElementSize = (itemElement) => {
				const boundingClientRect = itemElement.getBoundingClientRect();
				const computedStyle = globalContext.window.getComputedStyle(
					itemElement
				);

				itemElementMarginRight =
					parseInt(computedStyle.marginRight, 10) || 0;

				itemElementMarginLeft =
					parseInt(computedStyle.marginLeft, 10) || 0;

				itemElementLeft =
					boundingClientRect.left -
					wrapper.offsetLeft +
					wrapper.scrollLeft;

				itemElementRight =
					wrapper.getBoundingClientRect().width -
					(boundingClientRect.right -
						wrapper.offsetLeft +
						wrapper.scrollLeft);

				itemElementTop =
					boundingClientRect.top -
					wrapper.offsetTop +
					wrapper.scrollTop;
			};

			const resizeObserver = globalContext.window.ResizeObserver
				? new globalContext.window.ResizeObserver((entries) => {
						entries.forEach((entry) => {
							if (
								entry.target === itemElement ||
								entry.target === wrapper ||
								entry.target === pageEditorWrapper
							) {
								updateItemElementSize(itemElement);
							}
						});

						updatePosition();
				  })
				: null;

			let resizeIntervalId = null;

			if (resizeObserver) {
				resizeObserver.observe(itemElement);

				if (pageEditorWrapper) {
					resizeObserver.observe(pageEditorWrapper);
				}
			}
			else {
				resizeIntervalId = setInterval(() => {
					updateItemElementSize(itemElement);

					updatePosition();
				}, 500);
			}

			wrapper.addEventListener('scroll', handleScroll);
			updateItemElementSize(itemElement);
			updatePosition();

			return () => {
				wrapper.removeEventListener('scroll', handleScroll);

				if (resizeObserver) {
					resizeObserver.disconnect();
				}
				else {
					clearInterval(resizeIntervalId);
				}
			};
		}
	}, [globalContext, itemElement, languageId, layoutData, wrapper]);

	return (
		<ReactPortal container={wrapper} wrapper={false}>
			<div
				className={classNames(
					'cadmin',
					'page-editor__topper__bar',
					'tbar',
					{'page-editor__topper__bar--inset': positionConfig.isInset}
				)}
				style={{...style, ...positionConfig.style}}
			>
				{children}
			</div>
		</ReactPortal>
	);
}

TopperLabel.propTypes = {
	itemElement: PropTypes.object,
	style: PropTypes.object,
};
