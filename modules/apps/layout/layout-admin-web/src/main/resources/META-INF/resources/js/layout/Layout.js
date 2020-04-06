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

import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import Breadcrumbs from '../breadcrumbs/Breadcrumbs';
import MillerColumns from '../miller_columns/MillerColumns';
import actionHandlers from './actionHandlers';

const Layout = ({
	getItemChildrenURL,
	initialBreadcrumbEntries,
	initialLayoutColumns,
	moveItemURL,
	namespace,
	searchContainerId,
}) => {
	const layoutRef = useRef();
	const searchContainer = useRef();

	const [breadcrumbEntries, setBreadcrumbEntries] = useState(
		initialBreadcrumbEntries
	);
	const [layoutColumns, setLayoutColumns] = useState(initialLayoutColumns);

	useEffect(() => {
		const A = new AUI();

		A.use(
			'liferay-search-container',
			'liferay-search-container-select',
			A => {
				const plugins = [
					{
						cfg: {
							rowSelector: '.miller-columns-item',
						},
						fn: A.Plugin.SearchContainerSelect,
					},
				];

				if (searchContainer.current) {
					searchContainer.current.destroy();
				}

				searchContainer.current = new Liferay.SearchContainer({
					contentBox: layoutRef.current,
					id: `${namespace}${searchContainerId}`,
					plugins,
				});
			}
		);
	}, [namespace, searchContainerId]);

	const getItemChildren = parentId => {
		const formData = new FormData();

		formData.append(`${namespace}plid`, parentId);

		fetch(getItemChildrenURL, {
			body: formData,
			method: 'POST',
		})
			.then(response => response.json())
			.then(({children}) => {
				const newLayoutColumns = [];

				for (let i = 0; i < layoutColumns.length; i++) {
					const column = layoutColumns[i];
					const newColumn = [];

					let parent;

					for (let j = 0; j < column.length; j++) {
						const newItem = {...column[j]};

						if (!parent && newItem.id === parentId) {
							parent = newItem;
						}

						newColumn.push(newItem);
					}

					newLayoutColumns.push(newColumn);

					if (parent) {
						const oldParent = newColumn.find(item => item.active);

						if (oldParent) {
							oldParent.active = false;
						}

						parent.active = true;
						newLayoutColumns.push(children);
						break;
					}
				}

				setLayoutColumns(newLayoutColumns);
			})
			.catch();
	};

	const saveData = (sourceItemId, parentItemId, position) => {
		const formData = new FormData();

		formData.append(`${namespace}plid`, sourceItemId);
		formData.append(`${namespace}parentPlid`, parentItemId);

		if (Number.isInteger(position)) {
			formData.append(`${namespace}priority`, position);
		}

		fetch(moveItemURL, {
			body: formData,
			method: 'POST',
		});
	};

	const updateBreadcrumbs = columns => {
		const newBreadcrumbEntries = [breadcrumbEntries[0]];

		for (let i = 0; i < columns.length; i++) {
			const item = columns[i].items.find(item => item.active);

			if (item) {
				newBreadcrumbEntries.push({
					title: item.title,
					url: item.url,
				});
			}
		}

		setBreadcrumbEntries(newBreadcrumbEntries);
	};

	return (
		<div ref={layoutRef}>
			<Breadcrumbs entries={breadcrumbEntries} />
			<MillerColumns
				actionHandlers={actionHandlers}
				initialColumns={layoutColumns}
				namespace={namespace}
				onColumnsChange={updateBreadcrumbs}
				onItemMove={saveData}
				onItemStayHover={getItemChildren}
			/>
		</div>
	);
};

export default function({
	context: {namespace},
	props: {
		breadcrumbEntries,
		getItemChildrenURL,
		layoutColumns,
		moveItemURL,
		searchContainerId,
	},
}) {
	return (
		<Layout
			getItemChildrenURL={getItemChildrenURL}
			initialBreadcrumbEntries={breadcrumbEntries}
			initialLayoutColumns={layoutColumns}
			moveItemURL={moveItemURL}
			namespace={namespace}
			searchContainerId={searchContainerId}
		/>
	);
}
