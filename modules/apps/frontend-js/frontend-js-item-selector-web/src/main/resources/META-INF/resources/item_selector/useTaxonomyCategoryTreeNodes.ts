/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEFAULT_FETCH_HEADERS} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';
import {useEffect, useMemo, useState} from 'react';

// The vocabulary-scoped endpoint is used (not /sites/{id}/taxonomy-categories)
// because the site path filters out categories of global vocabularies, while
// this path returns the full set regardless of the requesting site.

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

async function fetchAllPages<T>(
	baseURL: string,
	signal: AbortSignal
): Promise<T[]> {
	const items: T[] = [];

	let page = 1;
	let lastPage = 1;

	do {
		const url = new URL(baseURL, window.location.origin);

		url.searchParams.set('page', String(page));
		url.searchParams.set('pageSize', String(PAGE_SIZE));

		const response = await fetch(url.toString(), {
			headers: DEFAULT_FETCH_HEADERS,
			signal,
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
	const childrenByParent = new Map<string | null, ITaxonomyCategory[]>();

	categories.forEach((category) => {
		const parentId =
			category.parentTaxonomyCategory?.id !== undefined
				? String(category.parentTaxonomyCategory.id)
				: null;

		const siblings = childrenByParent.get(parentId) ?? [];

		siblings.push(category);
		childrenByParent.set(parentId, siblings);
	});

	const buildNode = (
		category: ITaxonomyCategory
	): ITaxonomyCategoryTreeNode => {
		const children = childrenByParent
			.get(String(category.id))
			?.map(buildNode);

		return {
			hasChildren: (category.numberOfTaxonomyCategories ?? 0) > 0,
			id: String(category.id),
			name: category.name,
			raw: category,
			vocabularyId,
			...(children?.length && {children}),
		};
	};

	// Defensive: orphans (parent missing from response, e.g. permission filter)
	// are kept by treating any unknown parent as a root.

	const knownIds = new Set(categories.map((category) => String(category.id)));

	const rootCategories = categories.filter((category) => {
		const parentId = category.parentTaxonomyCategory?.id;

		return parentId === undefined || !knownIds.has(String(parentId));
	});

	return rootCategories.map(buildNode);
}

async function loadVocabularyTree(
	vocabularyId: string,
	signal: AbortSignal
): Promise<ITaxonomyCategoryTreeNode[]> {
	const url = new URL(
		`${HEADLESS_TAXONOMY_BASE}/taxonomy-vocabularies/${vocabularyId}/taxonomy-categories`,
		window.location.origin
	);

	url.searchParams.set('flatten', 'true');

	const categories = await fetchAllPages<ITaxonomyCategory>(
		url.toString(),
		signal
	);

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
		const controller = new AbortController();

		setLoading(true);
		setError(null);

		Promise.all(
			idsKey
				.split(',')
				.filter(Boolean)
				.map((id) => loadVocabularyTree(id, controller.signal))
		)
			.then((trees) => {
				if (controller.signal.aborted) {
					return;
				}

				setNodes(trees.flat());
			})
			.catch((reason: unknown) => {
				if (controller.signal.aborted) {
					return;
				}

				setError(
					reason instanceof Error ? reason : new Error(String(reason))
				);
			})
			.finally(() => {
				if (!controller.signal.aborted) {
					setLoading(false);
				}
			});

		return () => {
			controller.abort();
		};
	}, [idsKey]);

	return {error, loading, nodes};
}
