/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEFAULT_FETCH_HEADERS} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';
import {useEffect, useMemo, useState} from 'react';

const HEADLESS_TAXONOMY_BASE = '/o/headless-admin-taxonomy/v1.0';

const PAGE_SIZE = 100;

export interface ITaxonomyCategory {
	availableLanguages?: string[];
	creator?: unknown;
	dateCreated?: string;
	dateModified?: string;
	description?: string;
	externalReferenceCode?: string;
	id: number;
	name: string;
	name_i18n?: Record<string, string>;
	numberOfTaxonomyCategories?: number;
	parentTaxonomyCategory?: {id: number; name?: string};
	parentTaxonomyVocabulary?: {id: number; name?: string};
	siteId?: number;
	taxonomyCategoryProperties?: Array<{key: string; value: string}>;
}

export interface ITaxonomyCategoryTreeNode {
	children?: ITaxonomyCategoryTreeNode[];
	hasChildren: boolean;
	id: string;
	name: string;
	raw: ITaxonomyCategory;
	vocabularyId: string;
}

interface IPage<T> {
	items?: T[];
	lastPage?: number;
	page?: number;
	totalCount?: number;
}

async function fetchAllPages<T>(baseURL: string): Promise<T[]> {
	const items: T[] = [];

	let page = 1;
	let lastPage = 1;

	do {
		const url = new URL(baseURL, window.location.origin);

		url.searchParams.set('page', String(page));
		url.searchParams.set('pageSize', String(PAGE_SIZE));

		const response = await fetch(url.toString(), {
			headers: DEFAULT_FETCH_HEADERS,
		});

		if (!response.ok) {
			throw new Error(
				`Request to ${url.pathname} failed: ${response.status}`
			);
		}

		const json = (await response.json()) as IPage<T>;

		if (Array.isArray(json.items)) {
			items.push(...json.items);
		}

		lastPage = json.lastPage ?? 1;
		page += 1;
	} while (page <= lastPage);

	return items;
}

function buildTree(
	categories: ITaxonomyCategory[],
	vocabularyId: string
): ITaxonomyCategoryTreeNode[] {
	const nodesById = new Map<string, ITaxonomyCategoryTreeNode>();

	categories.forEach((category) => {
		nodesById.set(String(category.id), {
			hasChildren: (category.numberOfTaxonomyCategories ?? 0) > 0,
			id: String(category.id),
			name: category.name,
			raw: category,
			vocabularyId,
		});
	});

	const roots: ITaxonomyCategoryTreeNode[] = [];

	categories.forEach((category) => {
		const node = nodesById.get(String(category.id));

		if (!node) {
			return;
		}

		const parentId = category.parentTaxonomyCategory?.id;

		if (parentId === undefined) {
			roots.push(node);

			return;
		}

		const parent = nodesById.get(String(parentId));

		if (parent) {
			(parent.children ??= []).push(node);
		}
		else {

			// Defensive: parent missing from page (e.g. permission filter).
			// Surface the orphan as a root so it stays selectable.

			roots.push(node);
		}
	});

	return roots;
}

async function loadVocabularyTree(
	vocabularyId: string
): Promise<ITaxonomyCategoryTreeNode[]> {
	const url = new URL(
		`${HEADLESS_TAXONOMY_BASE}/taxonomy-vocabularies/${vocabularyId}/taxonomy-categories`,
		window.location.origin
	);

	url.searchParams.set('flatten', 'true');

	const categories = await fetchAllPages<ITaxonomyCategory>(url.toString());

	return buildTree(categories, vocabularyId);
}

export interface IUseTaxonomyCategoryTreeNodesResult {
	error: Error | null;
	loading: boolean;
	nodes: ITaxonomyCategoryTreeNode[];
}

export default function useTaxonomyCategoryTreeNodes(
	vocabularyIds: ReadonlyArray<string | number>
): IUseTaxonomyCategoryTreeNodesResult {
	const [nodes, setNodes] = useState<ITaxonomyCategoryTreeNode[]>([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState<Error | null>(null);

	const idsKey = useMemo(() => {
		return vocabularyIds
			.map((id) => String(id).trim())
			.filter(Boolean)
			.sort()
			.join(',');
	}, [vocabularyIds]);

	useEffect(() => {
		let cancelled = false;

		setLoading(true);
		setError(null);

		Promise.all(
			idsKey
				.split(',')
				.filter(Boolean)
				.map((id) => loadVocabularyTree(id))
		)
			.then((trees) => {
				if (cancelled) {
					return;
				}

				setNodes(trees.flat());
			})
			.catch((reason: unknown) => {
				if (cancelled) {
					return;
				}

				setError(
					reason instanceof Error ? reason : new Error(String(reason))
				);
			})
			.finally(() => {
				if (!cancelled) {
					setLoading(false);
				}
			});

		return () => {
			cancelled = true;
		};
	}, [idsKey]);

	return {error, loading, nodes};
}
