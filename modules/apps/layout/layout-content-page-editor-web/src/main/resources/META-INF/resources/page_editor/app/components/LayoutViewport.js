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

import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';

import debounceRAF from '../../core/debounceRAF';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {config} from '../config/index';
import {useSelectItem} from '../contexts/ControlsContext';
import {GlobalContextFrame} from '../contexts/GlobalContext';
import {useSelector} from '../contexts/StoreContext';
import DisabledArea from './DisabledArea';
import Layout from './Layout';
import MasterLayout from './MasterLayout';

export default function LayoutViewport() {
	const handleRef = useRef();
	const [element, setElement] = useState(null);
	const [layoutWidth, setLayoutWidth] = useState();
	const [resizing, setResizing] = useState(false);
	const selectItem = useSelectItem();
	const mainItemId = useSelector((state) => state.layoutData.rootItems.main);
	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const sidebarOpen = useSelector(
		(state) => state.sidebar.panelId && state.sidebar.open
	);

	useEffect(() => {
		const handleViewport = handleRef.current;

		let initialWidth = 0;
		let initialX = 0;

		setLayoutWidth(undefined);

		const onDrag = debounceRAF((event) => {
			const {maxWidth, minWidth} = config.availableViewportSizes[
				selectedViewportSize
			];

			setLayoutWidth(
				Math.min(
					Math.max(
						initialWidth + (event.clientX - initialX) * 2,
						minWidth
					),
					maxWidth + 1
				)
			);
		});

		const stopDrag = () => {
			setResizing(false);

			document.removeEventListener('mousemove', onDrag, true);
			document.removeEventListener('mouseup', stopDrag);
		};

		const initDrag = (event) => {
			if (element) {
				setResizing(true);
				selectItem(null);

				event.preventDefault();

				initialX = event.clientX;

				initialWidth =
					element.getBoundingClientRect().width -
					(parseInt(getComputedStyle(element).paddingRight, 10) || 0);

				setLayoutWidth(initialWidth);

				document.addEventListener('mousemove', onDrag, true);
				document.addEventListener('mouseup', stopDrag);
			}
		};

		if (handleViewport) {
			handleViewport.addEventListener('mousedown', initDrag, true);
		}

		return () => {
			if (handleViewport) {
				handleViewport.removeEventListener('mousedown', initDrag, true);
			}
		};
	}, [element, selectItem, selectedViewportSize]);

	return (
		<div
			className={classNames(
				'page-editor__layout-viewport',
				`page-editor__layout-viewport--size-${selectedViewportSize}`,
				{
					'page-editor__layout-viewport__resizing': resizing,
					'page-editor__layout-viewport--with-sidebar-open': sidebarOpen,
				}
			)}
		>
			{resizing && (
				<div className="page-editor__layout-viewport__label-width">
					<span>{layoutWidth}px</span>
				</div>
			)}

			<div
				className="page-editor__layout-viewport__resizer"
				ref={setElement}
				style={{width: layoutWidth}}
			>
				<GlobalContextFrame
					useIframe={selectedViewportSize !== VIEWPORT_SIZES.desktop}
				>
					<DisabledArea />

					{masterLayoutData ? (
						<MasterLayout />
					) : (
						<Layout mainItemId={mainItemId} />
					)}
				</GlobalContextFrame>
			</div>

			{selectedViewportSize !== VIEWPORT_SIZES.desktop && (
				<div
					className="page-editor__layout-viewport__handle"
					ref={handleRef}
				/>
			)}
		</div>
	);
}
