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

import ClayPopover from '@clayui/popover';
import {useEventListener} from 'frontend-js-react-web';
import {ALIGN_POSITIONS, align, suggestAlignBestRegion} from 'frontend-js-web';
import React, {
	useCallback,
	useEffect,
	useLayoutEffect,
	useRef,
	useState,
} from 'react';
import {createPortal} from 'react-dom';

import {useSelector} from '../store/index';
import {useSelectItem} from './Controls';
import {useGlobalContext} from './GlobalContext';

const DEFAULT_DISABLED_AREA_CLASS = 'page-editor__disabled-area';
const DEFAULT_ORIGIN = '#content';

const DEFAULT_WHITELIST = [
	DEFAULT_ORIGIN,
	`.${DEFAULT_DISABLED_AREA_CLASS}`,
	'.control-menu',
	'.lfr-add-panel',
	'.lfr-product-menu-panel',
];

const POPOVER_POSITIONS = {
	[ALIGN_POSITIONS.Bottom]: 'bottom',
	[ALIGN_POSITIONS.BottomLeft]: 'bottom',
	[ALIGN_POSITIONS.BottomRight]: 'bottom',
	[ALIGN_POSITIONS.Left]: 'left',
	[ALIGN_POSITIONS.Right]: 'right',
	[ALIGN_POSITIONS.Top]: 'top',
	[ALIGN_POSITIONS.TopLeft]: 'top',
	[ALIGN_POSITIONS.TopRight]: 'top',
};
const STATIC_POSITIONS = ['', 'static', 'relative'];

const DisabledArea = () => {
	const popoverRef = useRef(null);
	const [currentElementClicked, setCurrentElementClicked] = useState(null);
	const globalContext = useGlobalContext();
	const [show, setShow] = useState(false);
	const [position, setPosition] = useState('bottom');
	const sidebarOpen = useSelector((state) => state.sidebar.open);
	const selectItem = useSelectItem();

	const isDisabled = useCallback(
		(element) => {
			const {height} = element.getBoundingClientRect();
			const {position} = globalContext.window.getComputedStyle(element);

			const hasAbsolutePosition =
				STATIC_POSITIONS.indexOf(position) === -1;
			const hasZeroHeight = height === 0;

			return (
				element.tagName !== 'SCRIPT' &&
				!hasZeroHeight &&
				!hasAbsolutePosition &&
				!DEFAULT_WHITELIST.some(
					(selector) =>
						element.matches(selector) ||
						element.querySelector(selector)
				)
			);
		},
		[globalContext]
	);

	useEffect(() => {
		const element = globalContext.document.querySelector(
			`.${DEFAULT_DISABLED_AREA_CLASS}`
		);

		if (element) {
			if (sidebarOpen) {
				element.classList.add('collapsed');
			}
			else {
				element.classList.remove('collapsed');
			}
		}
	}, [globalContext, sidebarOpen]);

	useEventListener(
		'scroll',
		() => {
			if (show) {
				setCurrentElementClicked(null);
				setShow(false);
			}
		},
		true,
		globalContext.document
	);

	useEventListener(
		'click',
		(event) => {
			if (
				Array.from(event.target.classList).includes(
					DEFAULT_DISABLED_AREA_CLASS
				)
			) {
				setCurrentElementClicked(event.target);
				selectItem(null);
				setShow(true);
			}
			else if (show) {
				setCurrentElementClicked(null);
				setShow(false);
			}
		},
		true,
		globalContext.document.body
	);

	useLayoutEffect(() => {
		if (popoverRef.current && currentElementClicked && show) {
			const suggestedAlign = suggestAlignBestRegion(
				popoverRef.current,
				currentElementClicked,
				ALIGN_POSITIONS.TopCenter
			);

			const bestPosition =
				suggestedAlign.position !== ALIGN_POSITIONS.TopCenter
					? ALIGN_POSITIONS.BottomCenter
					: ALIGN_POSITIONS.TopCenter;

			align(
				popoverRef.current,
				currentElementClicked,
				bestPosition,
				false
			);

			setPosition(POPOVER_POSITIONS[bestPosition]);
		}
	}, [show, popoverRef, currentElementClicked]);

	useLayoutEffect(() => {
		let element = globalContext.document.querySelector(DEFAULT_ORIGIN);

		while (
			element &&
			element.parentElement &&
			element !== globalContext.document.body
		) {
			Array.from(element.parentElement.children).forEach(
				(child) =>
					isDisabled(child) &&
					child.classList.add(DEFAULT_DISABLED_AREA_CLASS)
			);

			element = element.parentElement;
		}

		return () => {
			const elements = globalContext.document.querySelectorAll(
				`.${DEFAULT_DISABLED_AREA_CLASS}`
			);

			elements.forEach((element) =>
				element.classList.remove(DEFAULT_DISABLED_AREA_CLASS)
			);
		};
	}, [globalContext, isDisabled]);

	return (
		show &&
		createPortal(
			<ClayPopover alignPosition={position} ref={popoverRef} show>
				<div
					dangerouslySetInnerHTML={{
						__html: Liferay.Util.sub(
							Liferay.Language.get(
								'this-area-is-defined-by-the-theme.-you-can-change-the-theme-settings-by-clicking-x-in-the-x-panel-on-the-sidebar'
							),
							[
								`<strong>${Liferay.Language.get(
									'more'
								)}</strong>`,
								`<strong>${Liferay.Language.get(
									'page-design-options'
								)}</strong>`,
							]
						),
					}}
				/>
			</ClayPopover>,
			globalContext.document.body
		)
	);
};

export default DisabledArea;
