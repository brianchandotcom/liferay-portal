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

import {COLLECTION_LIST_FORMATS} from '../../config/constants/collectionListFormats';
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

const Grid = ({child, collectionId, numberOfColumns, numberOfItems}) => {
	const numberOfRows = Math.ceil(numberOfItems / numberOfColumns);

	const createRows = () => {
		const rows = [];

		for (let i = 0; i < numberOfRows; i++) {
			const columns = [];

			for (let j = 0; j < numberOfColumns; j++) {
				const index = [i, j].join('-');
				const itemCount = i * numberOfColumns + j;

				columns.push(
					<div className={`col col-${12 / numberOfColumns}`}>
						{itemCount < numberOfItems && (
							<ControlsIdConverterContextProvider
								key={index}
								value={{
									collectionFields: [],
									collectionItem: {},
									fromControlsId,
									toControlsId: getToControlsId(
										collectionId,
										index
									),
								}}
							>
								{React.cloneElement(child)}
							</ControlsIdConverterContextProvider>
						)}
					</div>
				);
			}

			rows.push(<div className="row">{columns}</div>);
		}

		return rows;
	};

	return createRows();
};

const Stack = ({child, collectionId, numberOfItems}) => {
	return Array.from({length: numberOfItems}).map((_element, idx) => (
		<ControlsIdConverterContextProvider
			key={idx}
			value={{
				collectionFields: [],
				collectionItem: {},
				fromControlsId,
				toControlsId: getToControlsId(collectionId, idx),
			}}
		>
			{React.cloneElement(child)}
		</ControlsIdConverterContextProvider>
	));
};

const Collection = React.forwardRef(({children, item}, ref) => {
	const child = React.Children.toArray(children)[0];
	const collectionConfig = item.config;

	const ContentComponent =
		collectionConfig.listFormat === COLLECTION_LIST_FORMATS.grid
			? Grid
			: Stack;

	return (
		<div className="page-editor__collection" ref={ref}>
			{collectionIsMapped(collectionConfig) ? (
				<ContentComponent
					child={child}
					collectionId={item.itemId}
					numberOfColumns={collectionConfig.numberOfColumns}
					numberOfItems={collectionConfig.numberOfItems}
				/>
			) : (
				<NotMappedMessage />
			)}
		</div>
	);
});

export default Collection;
