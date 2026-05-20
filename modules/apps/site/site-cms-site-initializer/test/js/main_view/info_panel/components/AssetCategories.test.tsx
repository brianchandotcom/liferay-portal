/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render, screen, within} from '@testing-library/react';
import React from 'react';

import AssetCategories from '../../../../../src/main/resources/META-INF/resources/js/main_view/info_panel/components/AssetCategories';

function MockItemSelector({apiURL}: {apiURL?: string}) {
	return <div data-api-url={apiURL} data-testid="item-selector" />;
}

function MockItemSelectorItem({children}: {children: React.ReactNode}) {
	return <div>{children}</div>;
}

jest.mock('@liferay/frontend-js-item-selector-web', () => {
	return {
		ItemSelector: Object.assign(MockItemSelector, {
			Item: MockItemSelectorItem,
		}),
	};
});

function buildCategoryBrief({
	id,
	name,
	taxonomyVocabularyId,
	vocabularyName,
}: {
	id: number;
	name: string;
	taxonomyVocabularyId: number;
	vocabularyName: string;
}) {
	return {
		embeddedTaxonomyCategory: {
			id,
			name,
			parentTaxonomyVocabulary: {
				id: taxonomyVocabularyId,
				name: vocabularyName,
			},
			taxonomyVocabularyId,
		},
	};
}

function renderComponent({
	classNameId = 1,
	cmsGroupId = 456,
	collapsable,
	scopeId = 123,
	taxonomyCategoryBriefs = [],
}: {
	classNameId?: number;
	cmsGroupId?: number;
	collapsable?: boolean;
	scopeId?: number;
	taxonomyCategoryBriefs?: ReturnType<typeof buildCategoryBrief>[];
} = {}) {
	return render(
		<AssetCategories
			cmsGroupId={cmsGroupId}
			collapsable={collapsable}
			hasUpdatePermission={true}
			objectEntry={
				{
					scopeId,
					systemProperties: {
						objectDefinitionBrief: {classNameId},
					},
					taxonomyCategoryBriefs,
				} as any
			}
			updateObjectEntry={jest.fn()}
		/>
	);
}

describe('AssetCategories', () => {
	beforeEach(() => {
		(global as any).Liferay = {
			Language: {
				get: jest.fn((key: string) => key),
			},
			ThemeDisplay: {
				getPortalURL: () => 'https://www.liferay.com',
			},
		};
	});

	afterEach(() => {
		jest.resetAllMocks();
	});

	it('renders categories grouped under their vocabulary names', () => {
		renderComponent({
			taxonomyCategoryBriefs: [
				buildCategoryBrief({
					id: 1,
					name: 'category-1',
					taxonomyVocabularyId: 10,
					vocabularyName: 'vocabulary-a',
				}),
				buildCategoryBrief({
					id: 2,
					name: 'category-2',
					taxonomyVocabularyId: 10,
					vocabularyName: 'vocabulary-a',
				}),
				buildCategoryBrief({
					id: 3,
					name: 'category-3',
					taxonomyVocabularyId: 20,
					vocabularyName: 'vocabulary-b',
				}),
			],
		});

		const vocabularyAGroup = screen
			.getByText('vocabulary-a')
			.closest('div')!;
		const vocabularyBGroup = screen
			.getByText('vocabulary-b')
			.closest('div')!;

		expect(
			within(vocabularyAGroup).getByText('category-1')
		).toBeInTheDocument();
		expect(
			within(vocabularyAGroup).getByText('category-2')
		).toBeInTheDocument();
		expect(
			within(vocabularyAGroup).queryByText('category-3')
		).not.toBeInTheDocument();

		expect(
			within(vocabularyBGroup).getByText('category-3')
		).toBeInTheDocument();
	});

	it('renders the panel as collapsable by default', () => {
		renderComponent();

		const toggle = screen.getByRole('button', {name: 'categories'});

		expect(toggle).toHaveAttribute('aria-expanded', 'true');
	});

	it('renders the panel as non-collapsable when collapsable is false', () => {
		renderComponent({collapsable: false});

		expect(
			screen.queryByRole('button', {name: 'categories'})
		).not.toBeInTheDocument();

		expect(screen.getByText('categories')).toBeInTheDocument();
	});

	it('builds the categories apiURL against the asset library when the scope is positive', () => {
		renderComponent({classNameId: 1, scopeId: 123});

		const apiURL = screen
			.getByTestId('item-selector')
			.getAttribute('data-api-url');

		expect(apiURL).toContain(
			'/o/headless-admin-taxonomy/v1.0/asset-libraries/123/taxonomy-categories'
		);
		expect(apiURL).toContain("assetTypes in ('0', '1')");
		expect(apiURL).not.toContain('assetLibraries in');
	});

	it('builds the categories apiURL against the site with an asset library filter when the scope is negative', () => {
		renderComponent({classNameId: 1, cmsGroupId: 456, scopeId: -1});

		const apiURL = screen
			.getByTestId('item-selector')
			.getAttribute('data-api-url');

		expect(apiURL).toContain(
			'/o/headless-admin-taxonomy/v1.0/sites/456/taxonomy-categories'
		);
		expect(apiURL).toContain("assetLibraries in ('-1')");
	});
});
