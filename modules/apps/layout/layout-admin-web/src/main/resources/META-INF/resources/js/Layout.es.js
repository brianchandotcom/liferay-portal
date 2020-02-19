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

import React, {useEffect, useRef} from 'react';

import Breadcrumbs from './breadcrumbs/Breadcrumbs.es';
import MillerColumns from './miller_columns/MillerColumns.es';
import MillerColumnsContext from './miller_columns/MillerColumnsContext.es';
import actionHandlers from './miller_columns/actionHandlers.es';

const Layout = ({
	breadcrumbEntries,
	layoutColumns,
	namespace,
	searchContainerId
}) => {
	const layoutRef = useRef();
	const searchContainer = useRef();

	useEffect(() => {
		const A = new AUI();

		A.use(
			'liferay-search-container',
			'liferay-search-container-select',
			A => {
				const plugins = [
					{
						cfg: {
							rowSelector: '.miller-columns-item'
						},
						fn: A.Plugin.SearchContainerSelect
					}
				];

				if (searchContainer.current) {
					searchContainer.current.destroy();
				}

				searchContainer.current = new Liferay.SearchContainer({
					contentBox: layoutRef.current,
					id: `${namespace}${searchContainerId}`,
					plugins
				});
			}
		);
	}, [namespace, searchContainerId]);

	return (
		<div ref={layoutRef}>
			<MillerColumnsContext.Provider value={{actionHandlers, namespace}}>
				<Breadcrumbs entries={breadcrumbEntries} />
				<MillerColumns columns={layoutColumns} />
			</MillerColumnsContext.Provider>
		</div>
	);
};

export default function({
	context: {namespace},
	props: {breadcrumbEntries, layoutColumns, searchContainerId}
}) {
	return (
		<Layout
			breadcrumbEntries={breadcrumbEntries}
			layoutColumns={layoutColumns}
			namespace={namespace}
			searchContainerId={searchContainerId}
		/>
	);
}
