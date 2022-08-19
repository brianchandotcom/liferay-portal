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

import React, {useEffect, useRef, useState} from 'react';

import ItemSelectorPreview from '../../item_selector_preview/js/ItemSelectorPreview.es';
import SingleFileUploader from '../../item_selector_uploader/js/SingleFileUploader';

export default function ItemSelectorRepositoryEntryBrowser({
	eventName,
	portletNamespace,
	uploadEnabled = true,
	...uploaderProps
}) {
	const [itemSelectorPreviewOpen, setItemSelectorPreviewOpen] = useState(
		false
	);
	const [itemSelectorPreviewIndex, setItemSelectorPreviewIndex] = useState(0);

	const itemSelectorPreviewItemsRef = useRef();

	useEffect(() => {
		const itemSelectorPreviewItems = Array.from(
			document.querySelectorAll(
				`#p_p_id${portletNamespace} .item-preview-editable`
			)
		).map((node) => node.dataset);

		const clicablePreviewButtons = Array.from(
			document.querySelectorAll(`#p_p_id${portletNamespace} .icon-view`)
		);

		if (
			!itemSelectorPreviewItems.length ||
			itemSelectorPreviewItems.length !== clicablePreviewButtons.length
		) {
			return;
		}

		if (!itemSelectorPreviewItemsRef.current) {
			itemSelectorPreviewItemsRef.current = itemSelectorPreviewItems;
		}

		clicablePreviewButtons.forEach((clicableItem, index) => {
			clicableItem.addEventListener('click', (event) => {
				event.preventDefault();
				event.stopPropagation();

				setItemSelectorPreviewIndex(index);
				setItemSelectorPreviewOpen(true);
			});
		});
	}, [portletNamespace]);

	return (
		<>
			{uploadEnabled && <SingleFileUploader {...uploaderProps} />}
			{itemSelectorPreviewOpen && (
				<ItemSelectorPreview
					currentIndex={itemSelectorPreviewIndex}
					handleClose={() => setItemSelectorPreviewOpen(false)}
					handleSelectedItem={(item) => {
						Liferay.Util.getOpener().Liferay.fire(eventName, item);
					}}
					itemSelectedEventName={eventName}
					items={itemSelectorPreviewItemsRef.current}
				/>
			)}
		</>
	);
}
