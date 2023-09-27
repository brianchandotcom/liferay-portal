/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPopover from '@clayui/popover';
import {ReactPortal, useEventListener} from '@liferay/frontend-js-react-web';
import {
	ALIGN_POSITIONS,
	align,
	sub,
	suggestAlignBestRegion,
} from 'frontend-js-web';
import React, {useCallback, useLayoutEffect, useRef, useState} from 'react';

import {useSelectItem} from '../contexts/ControlsContext';
import {useGlobalContext} from '../contexts/GlobalContext';

const DEFAULT_DISABLED_AREA_CLASS = 'page-editor__disabled-area';
const DEFAULT_ORIGIN = '#content';

const DEFAULT_WHITELIST = [
	DEFAULT_ORIGIN,
	`.${DEFAULT_DISABLED_AREA_CLASS}`,
	'.control-menu',
	'.lfr-add-panel',
	'.lfr-product-menu-panel',
	'.page-editor__layout-breadcrumbs',
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
			Array.from(element.parentElement.children).forEach((child) => {
				if (isDisabled(child)) {
					child.classList.add(DEFAULT_DISABLED_AREA_CLASS);

					Array.from(child.children).forEach((grandChild) => {
						grandChild.setAttribute('inert', '');
						grandChild.setAttribute('aria-hidden', 'true');
					});
				}
			});

			element = element.parentElement;
		}

		return () => {
			const elements = globalContext.document.querySelectorAll(
				`.${DEFAULT_DISABLED_AREA_CLASS}`
			);

			elements.forEach((element) => {
				element.classList.remove(DEFAULT_DISABLED_AREA_CLASS);

				Array.from(element.children).forEach((child) => {
					child.removeAttribute('inert');
					child.removeAttribute('aria-hidden');
				});
			});
		};
	}, [globalContext, isDisabled]);

	return (
		show && (
			<ReactPortal
				className="cadmin"
				container={globalContext.document.body}
			>
				<ClayPopover
					alignPosition={position}
					defaultShow
					ref={popoverRef}
				>
					<div
						dangerouslySetInnerHTML={{
							__html: sub(
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
				</ClayPopover>
			</ReactPortal>
		)
	);
};

export default DisabledArea;
