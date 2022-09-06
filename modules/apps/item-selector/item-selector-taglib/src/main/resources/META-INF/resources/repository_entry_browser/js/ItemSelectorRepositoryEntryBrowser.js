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
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import ItemSelectorPreview from '../../item_selector_preview/js/ItemSelectorPreview.es';
import SingleFileUploader from '../../item_selector_uploader/js/SingleFileUploader';

export default function ItemSelectorRepositoryEntryBrowser({
	closeCaption,
	itemSelectedEventName,
	portletNamespace,
	rootNode,
	uploaderEnabled = true,
	...uploaderProps
}) {
	const [itemSelectorPreviewOpen, setItemSelectorPreviewOpen] = useState(
		false
	);
	const [itemSelectorPreviewIndex, setItemSelectorPreviewIndex] = useState(0);

	const itemSelectorPreviewItemsRef = useRef();

	const handleSelectedItem = useCallback(
		({returntype, value}) => {
			Liferay.Util.getOpener().Liferay.fire(itemSelectedEventName, {
				returnType: returntype,
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

		const clickablePreviewButtons = Array.from(
			document.querySelectorAll(`#p_p_id${portletNamespace} .icon-view`)
		);

		if (
			itemSelectorPreviewItems.length &&
			itemSelectorPreviewItems.length === clickablePreviewButtons.length
		) {
			if (!itemSelectorPreviewItemsRef.current) {
				itemSelectorPreviewItemsRef.current = itemSelectorPreviewItems;
			}

			clickablePreviewButtons.forEach((clickableItem, index) => {
				const handleOpenPreview = (event) => {
					event.preventDefault();
					event.stopPropagation();

					setItemSelectorPreviewIndex(index);
					setItemSelectorPreviewOpen(true);
				};

				clickableItem.addEventListener('click', handleOpenPreview);

				eventListeners.push({
					dispose: () => {
						clickableItem.removeEventListener(
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
			{uploaderEnabled && (
				<SingleFileUploader
					closeCaption={closeCaption}
					itemSelectedEventName={itemSelectedEventName}
					{...uploaderProps}
				/>
			)}

			{itemSelectorPreviewOpen && (
				<div className="item-selector-preview-container">
					<ItemSelectorPreview
						currentIndex={itemSelectorPreviewIndex}
						handleClose={() => setItemSelectorPreviewOpen(false)}
						handleSelectedItem={handleSelectedItem}
						headerTitle={closeCaption}
						itemSelectedEventName={itemSelectedEventName}
						items={itemSelectorPreviewItemsRef.current}
					/>
				</div>
			)}
		</>
	);
}

ItemSelectorRepositoryEntryBrowser.propTypes = {
	itemSelectedEventName: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	rootNode: PropTypes.string.isRequired,
	uploaderEnabled: PropTypes.bool,
};
