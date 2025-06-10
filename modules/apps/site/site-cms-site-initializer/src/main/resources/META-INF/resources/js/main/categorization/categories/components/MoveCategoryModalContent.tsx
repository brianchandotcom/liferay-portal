/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

export const FETCH_URLS = {
	getVocabularies: () =>
		`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies`,
};

function MoveCategoryModalContent({
	closeModal,
	itemData,
	loadData,
}: any) {
	const [categoryTree, setCategoryTree] = useState<any[]>([]);
	const [loading, setLoading] = useState(false);

	useEffect(() => {

		// Fetches all vocabularies. This information will
		// be the start of the category tree, in which the children of the
		// vocabulary get added on as the tree gets expanded.

		const tree: any[] = [];

		fetch(FETCH_URLS.getVocabularies(), {
			headers: {
				'Accept': 'application/json',
				'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
				'Content-Type': 'application/json',
			},
			method: 'GET',
		})
			.then((response) => response.json())
			.then((vocabularies) => {
				tree[0] = {
					children: vocabularies.items.map(
						({
							assetLibraries,
							id,
							name,
							numberOfTaxonomyCategories,
						}: {
							assetLibraries: any;
							id: any;
							name: string;
							numberOfTaxonomyCategories: number;
						}) => ({
							assetLibraries: assetLibraries.map(
								(item: any) => item.id
							),
							id: JSON.stringify(id),
							name,
							numberOfTaxonomyCategories,
						})
					),
					descriptiveName: vocabularies.descriptiveName,
					id: JSON.stringify(vocabularies.id),
					name: vocabularies.name,
				};
				setCategoryTree(tree);
			})
			.catch(() => setCategoryTree([]))
			.finally(() => {
				setLoading(true);
			});
	}, []);

	return (
		<>
			{loading && (
				<div>{categoryTree}</div>
			)}
		</>
	);
}

export default MoveCategoryModalContent;