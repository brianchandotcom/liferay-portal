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

import React from 'react';

import {ControlsIdConverterContextProvider} from '../ControlsIdConverterContext';

const COLLECTION_ID_DIVIDER = '$';

function collectionIsMapped(collectionConfig) {
	return collectionConfig.collection;
}

function getCollectionPrefix(collectionId, index) {
	return `collection-${collectionId}-${index}${COLLECTION_ID_DIVIDER}`;
}

function getToControlsId(collectionId, index) {
	return itemId => {
		if (!itemId) {
			return null;
		}

		return `${getCollectionPrefix(collectionId, index)}${itemId}`;
	};
}

function fromControlsId(controlsItemId) {
	if (!controlsItemId) {
		return null;
	}

	const [, itemId] = controlsItemId.split(COLLECTION_ID_DIVIDER);

	return itemId;
}

const NotMappedMessage = () => (
	<div className="page-editor__collection__not-mapped-message">
		{Liferay.Language.get('not-mapped')}
	</div>
);

const Collection = React.forwardRef(({children, item}, ref) => {
	const child = React.Children.toArray(children)[0];
	const collectionConfig = item.config;

	return (
		<div className="page-editor__collection" ref={ref}>
			{collectionIsMapped(collectionConfig) ? (
				Array.from({length: collectionConfig.numberOfItems}).map(
					(_element, idx) => (
						<ControlsIdConverterContextProvider
							key={idx}
							value={{
								fromControlsId,
								toControlsId: getToControlsId(item.itemId, idx),
							}}
						>
							{React.cloneElement(child)}
						</ControlsIdConverterContextProvider>
					)
				)
			) : (
				<NotMappedMessage />
			)}
		</div>
	);
});

export default Collection;
