/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';
import {useDragLayer} from 'react-dnd';

import debounceRAF from '../../common/debounceRAF';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {config} from '../config/index';
import {useSelectItem} from '../contexts/ControlsContext';
import {GlobalContextFrame} from '../contexts/GlobalContext';
import {useSelector} from '../contexts/StoreContext';
import selectItemConfigurationOpen from '../selectors/selectItemConfigurationOpen';
import selectSidebarIsOpened from '../selectors/selectSidebarIsOpened';
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

	const sidebarOpen = useSelector(selectSidebarIsOpened);
	const itemConfigurationOpen = useSelector(selectItemConfigurationOpen);

	const {isDragging} = useDragLayer((monitor) => ({
		isDragging: monitor.isDragging(),
	}));

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
					'cadmin': selectedViewportSize !== VIEWPORT_SIZES.desktop,
					'page-editor__layout-viewport__resizing': resizing,
					'page-editor__layout-viewport--with-item-configuration-open': itemConfigurationOpen,
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
					{!isDragging && <DisabledArea />}

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
