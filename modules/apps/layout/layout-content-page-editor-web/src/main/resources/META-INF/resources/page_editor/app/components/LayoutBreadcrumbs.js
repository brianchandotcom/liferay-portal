/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {
	useActiveItemId,
	useActiveItemType,
	useSelectItem,
} from '../contexts/ControlsContext';
import {useGlobalContext} from '../contexts/GlobalContext';
import {useSelectorCallback} from '../contexts/StoreContext';
import selectLayoutDataItemLabel from '../selectors/selectLayoutDataItemLabel';
import {deepEqual} from '../utils/checkDeepEqual';

const DROP_ZONE_BASE_LABEL = Liferay.Language.get('drop-zone');

/**
 * By default ClayBreadcrumbs leaves one item to each side of the breadcrumbs
 * Dropdown, plus the extra element that appears when the dropdown is needed.
 * @type {number}
 */
const ELLIPSIS_BUFFER_MARGIN = 4;

export function LayoutBreadcrumbs() {
	const wrapperElement = useMemo(
		() => document.getElementById('wrapper'),
		[]
	);

	const breadcrumbItems = useBreadcrumbItems();
	const containerRef = useRef(null);
	const ellipsisBuffer = useEllipsisBuffer(breadcrumbItems, containerRef);

	useEffect(() => {
		if (!wrapperElement) {
			return;
		}

		wrapperElement.classList.add('page-editor__wrapper');

		wrapperElement.classList.toggle(
			'page-editor__wrapper--padded-block-end',
			breadcrumbItems.length
		);
	}, [wrapperElement, breadcrumbItems.length]);

	return wrapperElement && breadcrumbItems.length ? (
		<ReactPortal container={wrapperElement}>
			<div
				className="bg-white border-top cadmin page-editor__layout-breadcrumbs position-fixed px-3"
				ref={containerRef}
			>
				<div
					className="overflow-auto"
					style={{opacity: ellipsisBuffer ? 1 : 0}}
				>
					<ClayBreadcrumb
						className="py-1"
						ellipsisBuffer={ellipsisBuffer}
						items={breadcrumbItems}
					/>
				</div>
			</div>
		</ReactPortal>
	) : null;
}

function useBreadcrumbItems() {
	const activeItemId = useActiveItemId();
	const activeItemType = useActiveItemType();
	const globalContext = useGlobalContext();
	const selectItem = useSelectItem();

	return useSelectorCallback(
		(state) => {
			const items = [];

			const addLayoutDataItems = (layoutDataItem) => {
				if (
					!layoutDataItem ||
					layoutDataItem.type === LAYOUT_DATA_ITEM_TYPES.root
				) {
					return;
				}

				if (
					layoutDataItem.type ===
					LAYOUT_DATA_ITEM_TYPES.fragmentDropZone
				) {
					const dropZoneId = document
						.querySelector(`[uuid="${layoutDataItem.itemId}"]`)
						?.getAttribute('data-lfr-drop-zone-id');

					items.push({
						label: dropZoneId
							? `${DROP_ZONE_BASE_LABEL} ${dropZoneId}`
							: DROP_ZONE_BASE_LABEL,
					});
				}
				else if (
					layoutDataItem.type !== LAYOUT_DATA_ITEM_TYPES.column &&
					layoutDataItem.type !==
						LAYOUT_DATA_ITEM_TYPES.collectionItem
				) {
					items.push({
						label: selectLayoutDataItemLabel(state, layoutDataItem),
						onClick: () =>
							selectItem(layoutDataItem.itemId, {
								itemType: ITEM_TYPES.layoutDataItem,
								origin: ITEM_ACTIVATION_ORIGINS.breadcrumbs,
							}),
					});
				}

				addLayoutDataItems(
					state.layoutData.items[layoutDataItem.parentId]
				);
			};

			if (activeItemType === ITEM_TYPES.layoutDataItem) {
				addLayoutDataItems(state.layoutData.items[activeItemId]);
			}
			else if (activeItemType === ITEM_TYPES.editable) {
				const [, fragmentEntryLinkId, editableId] = activeItemId.match(
					/^([^-]+)-([^\n]+)$/
				);

				items.push({
					label: editableId,
					onClick: () =>
						selectItem(activeItemId, {
							itemType: ITEM_TYPES.editable,
							origin: ITEM_ACTIVATION_ORIGINS.breadcrumbs,
						}),
				});

				const layoutDataItem = Object.values(
					state.layoutData.items
				).find(
					(item) =>
						item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
						item.config.fragmentEntryLinkId === fragmentEntryLinkId
				);

				addLayoutDataItems(layoutDataItem);
			}

			items.reverse();

			if (items.length) {
				const lastItem = items[items.length - 1];
				lastItem.active = true;
			}

			return items;
		},
		[activeItemId, activeItemType, globalContext, selectItem],
		deepEqual
	);
}

function useEllipsisBuffer(breacrumbItems, containerRef) {
	const [ellipsisBuffer, setEllipsisBuffer] = useState(0);

	useEffect(() => {
		setEllipsisBuffer(0);
	}, [breacrumbItems]);

	useEffect(() => {
		const containerElement = containerRef.current;

		if (ellipsisBuffer !== 0) {
			return;
		}

		if (!breacrumbItems.length) {
			setEllipsisBuffer(1);

			return;
		}

		if (!containerElement) {
			setEllipsisBuffer(breacrumbItems.length);

			return;
		}

		const containerCopy = containerElement.cloneNode(true);
		const containerRect = containerElement.getBoundingClientRect();

		containerCopy.style.opacity = '0';
		containerCopy.style.pointerEvents = 'none';
		containerCopy.style.height = `${containerRect.height}px`;
		containerCopy.style.width = `${containerRect.width}px`;

		const reduceEllipsisBuffer = () => {
			if (!document.body.contains(containerCopy)) {
				return;
			}

			const scrollContainer = containerCopy.firstElementChild;

			if (scrollContainer.clientWidth === scrollContainer.scrollWidth) {
				let nextEllipsisBuffer =
					containerCopy.querySelectorAll('.breadcrumb-item').length -
					ELLIPSIS_BUFFER_MARGIN;

				if (nextEllipsisBuffer <= 0) {
					nextEllipsisBuffer = breacrumbItems.length;
				}

				document.body.removeChild(containerCopy);
				setEllipsisBuffer(nextEllipsisBuffer);
			}
			else {
				const item = containerCopy.querySelector('.breadcrumb-item');

				if (item) {
					item.parentElement.removeChild(item);
					requestAnimationFrame(reduceEllipsisBuffer);
				}
				else {
					document.body.removeChild(containerCopy);
					setEllipsisBuffer(breacrumbItems.length);
				}
			}
		};

		document.body.appendChild(containerCopy);
		requestAnimationFrame(reduceEllipsisBuffer);

		return () => {
			if (document.body.contains(containerCopy)) {
				document.body.removeChild(containerCopy);
			}
		};
	}, [containerRef, ellipsisBuffer, breacrumbItems.length]);

	return ellipsisBuffer;
}
