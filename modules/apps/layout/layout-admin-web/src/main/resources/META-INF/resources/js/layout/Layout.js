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

import {fetch, openToast} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import Breadcrumbs from '../breadcrumbs/Breadcrumbs';
import MillerColumns from '../miller_columns/MillerColumns';

const Layout = ({
	getItemChildrenURL,
	initialBreadcrumbEntries,
	initialLayoutColumns,
	isPrivateLayoutsEnabled,
	languageId,
	moveItemURL,
	namespace,
	searchContainerId,
}) => {
	const layoutRef = useRef();
	const searchContainerRef = useRef();

	const [breadcrumbEntries, setBreadcrumbEntries] = useState(
		initialBreadcrumbEntries
	);
	const [layoutColumns, setLayoutColumns] = useState(initialLayoutColumns);
	const [searchContainerElement, setSearchContainerElement] = useState();

	useEffect(() => {
		const A = new AUI();

		A.use(
			'liferay-search-container',
			'liferay-search-container-select',
			(A) => {
				const plugins = [
					{
						cfg: {
							rowSelector: '.miller-columns-item',
						},
						fn: A.Plugin.SearchContainerSelect,
					},
				];

				if (searchContainerRef.current) {
					searchContainerRef.current.destroy();
				}

				searchContainerRef.current = new Liferay.SearchContainer({
					contentBox: layoutRef.current,
					id: `${namespace}${searchContainerId}`,
					plugins,
				});

				setSearchContainerElement(searchContainerRef.current);
			}
		);
	}, [namespace, searchContainerId]);

	const getItemChildren = (parentId) => {
		const formData = new FormData();

		formData.append(`${namespace}plid`, parentId);

		fetch(getItemChildrenURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({children}) => {
				const newLayoutColumns = [];

				for (let i = 0; i < layoutColumns.length; i++) {
					const column = layoutColumns[i];

					if (!column.some((item) => item.id === parentId)) {
						newLayoutColumns.push(column);
					}
					else {
						const newColumn = [];

						column.forEach((item) => {
							if (item.active) {
								newColumn.push({...item, active: false});
							}
							else if (item.id === parentId) {
								newColumn.push({...item, active: true});
							}
							else {
								newColumn.push({...item});
							}
						});

						newLayoutColumns.push(newColumn);

						break;
					}
				}

				newLayoutColumns.push(children);

				setLayoutColumns(newLayoutColumns);
			})
			.catch();
	};

	const saveData = (movedItems, parentItemId) => {
		const formData = new FormData();

		const activeItems = layoutColumns.reduce(
			(acc, column) => [...acc, ...column.filter((item) => item.active)],
			[]
		);

		const activeItem = activeItems[activeItems.length - 1];

		formData.append(`${namespace}plids`, JSON.stringify(movedItems));
		formData.append(`${namespace}parentPlid`, parentItemId);

		if (activeItem) {
			formData.append(`${namespace}selPlid`, activeItem.id);
		}

		fetch(moveItemURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then(({errorMessage, layoutColumns: updatedLayoutColumns}) => {
				if (errorMessage) {
					openToast({
						message: errorMessage,
						type: 'danger',
					});
				}
				if (updatedLayoutColumns) {
					setLayoutColumns(updatedLayoutColumns);
				}
			});
	};

	const updateBreadcrumbs = (columns) => {
		const newBreadcrumbEntries = [breadcrumbEntries[0]];

		for (let i = 0; i < columns.length; i++) {
			const item = columns[i].items.find((item) => item.active);

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
				initialColumns={layoutColumns}
				isPrivateLayoutsEnabled={isPrivateLayoutsEnabled}
				namespace={namespace}
				onColumnsChange={updateBreadcrumbs}
				onItemMove={saveData}
				onItemStayHover={getItemChildren}
				rtl={Liferay.Language.direction[languageId] === 'rtl'}
				searchContainer={searchContainerElement}
			/>
		</div>
	);
};

export default function ({
	context: {namespace},
	props: {
		breadcrumbEntries,
		getItemChildrenURL,
		isPrivateLayoutsEnabled,
		languageId,
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
			isPrivateLayoutsEnabled={isPrivateLayoutsEnabled}
			languageId={languageId}
			moveItemURL={moveItemURL}
			namespace={namespace}
			searchContainerId={searchContainerId}
		/>
	);
}
