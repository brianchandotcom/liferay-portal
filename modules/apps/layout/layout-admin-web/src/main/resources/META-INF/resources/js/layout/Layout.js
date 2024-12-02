/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, navigate, openToast} from 'frontend-js-web';
import React, {useContext, useEffect, useRef, useState} from 'react';

import Breadcrumbs from '../breadcrumbs/Breadcrumbs';
import MillerColumns from '../miller_columns/MillerColumns';
import {
	LayoutColumnsContext,
	LayoutColumnsProvider,
} from '../miller_columns/contexts/LayoutColumnsContext';

const Layout = ({
	createPageTemplateURL,
	getItemActionsURL,
	getItemChildrenURL,
	getPageTemplateCollectionsURL,
	initialBreadcrumbEntries,
	isSiteTemplate,
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
	const [searchContainerElement, setSearchContainerElement] = useState();

	const {layoutColumns, setLayoutColumns} = useContext(LayoutColumnsContext);

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

		return fetch(getItemChildrenURL, {
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

	const saveData = (movedItems, parentItemId, redirectURL) => {
		const formData = new FormData();

		formData.append(`${namespace}plids`, JSON.stringify(movedItems));
		formData.append(`${namespace}parentPlid`, parentItemId);

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
				else {
					openToast({
						message: Liferay.Language.get(
							'your-request-processed-successfully'
						),
						toastProps: {
							autoClose: 5000,
						},
						type: 'success',
					});

					if (Liferay.FeatureFlags['LPD-35220']) {
						navigate(redirectURL);
					}
					else {
						setLayoutColumns(updatedLayoutColumns);
					}
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
				createPageTemplateURL={createPageTemplateURL}
				getItemActionsURL={getItemActionsURL}
				getItemChildren={getItemChildren}
				getPageTemplateCollectionsURL={getPageTemplateCollectionsURL}
				isSiteTemplate={isSiteTemplate}
				namespace={namespace}
				onColumnsChange={updateBreadcrumbs}
				rtl={Liferay.Language.direction[languageId] === 'rtl'}
				saveData={saveData}
				searchContainer={searchContainerElement}
			/>
		</div>
	);
};

export default function ({
	context: {namespace},
	props: {
		breadcrumbEntries,
		createLayoutPageTemplateEntryURL,
		getItemActionsURL,
		getItemChildrenURL,
		getLayoutPageTemplateCollectionsURL,
		isLayoutSetPrototype = false,
		languageId,
		layoutColumns,
		moveItemURL,
		searchContainerId,
	},
}) {
	return (
		<LayoutColumnsProvider initialColumns={layoutColumns}>
			<Layout
				createPageTemplateURL={createLayoutPageTemplateEntryURL}
				getItemActionsURL={getItemActionsURL}
				getItemChildrenURL={getItemChildrenURL}
				getPageTemplateCollectionsURL={
					getLayoutPageTemplateCollectionsURL
				}
				initialBreadcrumbEntries={breadcrumbEntries}
				isLayoutSetPrototype={isLayoutSetPrototype}
				languageId={languageId}
				moveItemURL={moveItemURL}
				namespace={namespace}
				searchContainerId={searchContainerId}
			/>
		</LayoutColumnsProvider>
	);
}
