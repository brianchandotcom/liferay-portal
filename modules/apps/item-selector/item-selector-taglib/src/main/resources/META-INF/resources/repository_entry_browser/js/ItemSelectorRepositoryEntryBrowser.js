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

import {delegate} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import ItemSelectorPreview from '../../item_selector_preview/js/ItemSelectorPreview.es';
import SingleFileUploader from '../../item_selector_uploader/js/SingleFileUploader';

export default function ItemSelectorRepositoryEntryBrowser({
	closeCaption,
	eventName: itemSelectedEventName,
	portletNamespace,
	rootNode,
	uploadEnabled = true,
	...uploaderProps
}) {
	const [itemSelectorPreviewOpen, setItemSelectorPreviewOpen] = useState(
		false
	);
	const [itemSelectorPreviewIndex, setItemSelectorPreviewIndex] = useState(0);

	const itemSelectorPreviewItemsRef = useRef();

	const handleSelectedItem = useCallback(
		({returnType, value}) => {
			Liferay.Util.getOpener().Liferay.fire(itemSelectedEventName, {
				returnType,
				value,
			});
		},
		[itemSelectedEventName]
	);

	useEffect(() => {
		const eventListeners = [];

		eventListeners.push(
			delegate(
				document.querySelector(rootNode),
				'click',
				'.item-preview',
				(event) => {
					handleSelectedItem(event.delegateTarget.dataset);
				}
			)
		);

		const itemSelectorPreviewItems = Array.from(
			document.querySelectorAll(
				`#p_p_id${portletNamespace} .item-preview-editable`
			)
		).map((node) => node.dataset);

		const clicablePreviewButtons = Array.from(
			document.querySelectorAll(`#p_p_id${portletNamespace} .icon-view`)
		);

		if (
			itemSelectorPreviewItems.length &&
			itemSelectorPreviewItems.length === clicablePreviewButtons.length
		) {
			if (!itemSelectorPreviewItemsRef.current) {
				itemSelectorPreviewItemsRef.current = itemSelectorPreviewItems;
			}

			clicablePreviewButtons.forEach((clicableItem, index) => {
				const handleOpenPreview = (event) => {
					event.preventDefault();
					event.stopPropagation();

					setItemSelectorPreviewIndex(index);
					setItemSelectorPreviewOpen(true);
				};

				clicableItem.addEventListener('click', handleOpenPreview);

				eventListeners.push({
					dispose: () => {
						clicableItem.removeEventListener(
							'click',
							handleOpenPreview
						);
					},
				});
			});
		}

		return () => {
			eventListeners.forEach(({dispose}) => dispose());
		};
	}, [handleSelectedItem, portletNamespace, rootNode]);

	return (
		<>
			{uploadEnabled && (
				<SingleFileUploader
					closeCaption={closeCaption}
					itemSelectedEventName={itemSelectedEventName}
					{...uploaderProps}
				/>
			)}

			{itemSelectorPreviewOpen && (
				<ItemSelectorPreview
					currentIndex={itemSelectorPreviewIndex}
					handleClose={() => setItemSelectorPreviewOpen(false)}
					handleSelectedItem={handleSelectedItem}
					headerTitle={closeCaption}
					itemSelectedEventName={itemSelectedEventName}
					items={itemSelectorPreviewItemsRef.current}
				/>
			)}
		</>
	);
}
