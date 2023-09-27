/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {StoreContextProvider} from '../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import ContentsSidebar from '../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/page_content/components/ContentsSidebar';

jest.mock(
	'../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			formTypes: [
				{
					isRestricted: true,
					label: 'Form Type 1',
					subtypes: [
						{
							label: 'Subtype',
							value: '111111',
						},
					],
					value: '111111',
				},
			],
		},
	})
);

const PAGE_CONTENTS = [
	{
		subtype: 'Basic Web Content',
		title: 'WC1',
		type: 'Web Content Article',
	},
	{
		subtype: 'Basic Web Content',
		title: 'WC2',
		type: 'Web Content Article',
	},
];

const FRAGMENT_ENTRY_LINKS = {
	39682: {
		editableTypes: {'element-text': 'text'},
		editableValues: {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'element-text': {
					defaultValue: '\n\tHeading Example\n',
				},
			},
		},
		fragmentEntryLinkId: '39682',
		name: 'Heading',
		segmentsExperienceId: '0',
	},
	39683: {
		editableTypes: {'element-text': 'rich-text'},
		editableValues: {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'element-text': {
					defaultValue: '\n\tA paragraph\n',
				},
			},
		},
		fragmentEntryLinkId: '39683',
		name: 'Paragraph',
		segmentsExperienceId: '0',
	},
	39684: {
		editableTypes: {'element-text': 'text'},
		editableValues: {
			[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
				'element-text': {
					defaultValue:
						'\n\tHeading Example from another experience\n',
				},
			},
		},
		fragmentEntryLinkId: '39684',
		name: 'Heading',
		segmentsExperienceId: '1',
	},
};

const DEFAULT_LAYOUT_DATA = {
	items: {
		form: {
			children: ['fragment'],
			config: {
				classNameId: '111111',
				classTypeId: '0',
			},
			itemId: 'form',
			parentId: '',
			type: 'form',
		},
		fragment: {
			children: [],
			config: {
				fragmentEntryLinkId: '39683',
			},
			itemId: 'fragment',
			parentId: 'form',
			type: 'fragment',
		},
	},
};

const renderPageContent = ({
	fragmentEntryLinks = FRAGMENT_ENTRY_LINKS,
	pageContents = PAGE_CONTENTS,
	languageId = 'en_US',
	segmentsExperienceId = '0',
} = {}) =>
	render(
		<StoreContextProvider
			initialState={{
				fragmentEntryLinks,
				languageId,
				layoutData: DEFAULT_LAYOUT_DATA,
				pageContents,
				permissions: {UPDATE: true, UPDATE_LAYOUT_CONTENT: true},
				segmentsExperienceId,
			}}
		>
			<ContentsSidebar></ContentsSidebar>
		</StoreContextProvider>
	);

describe('ContentsSidebar', () => {
	it('shows the content list', () => {
		renderPageContent();

		expect(screen.getByText('WC1')).toBeInTheDocument();
		expect(screen.getByText('WC2')).toBeInTheDocument();
	});

	it('shows inline text within the content list when the editable type is text', () => {
		renderPageContent();

		expect(screen.getByText('Heading Example')).toBeInTheDocument();
	});

	it('shows inline text within the content list when the editable type is rich-text', () => {
		renderPageContent({
			fragmentEntryLinks: {
				39685: {
					editableTypes: {'element-text': 'rich-text'},
					editableValues: {
						[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
							'element-text': {
								defaultValue: '\n\tHeading example\n',
								en_US: 'This is a title&nbsp&nbsp&nbsp',
							},
						},
					},
					fragmentEntryLinkId: '39685',
					name: 'Heading',
					segmentsExperienceId: '0',
				},
			},
			pageContents: [],
		});

		expect(screen.getByText('This is a title')).toBeInTheDocument();
	});

	it('shows inline text corresponding to an experience', () => {
		renderPageContent({
			segmentsExperienceId: '1',
		});

		expect(
			screen.queryByText('Heading Example from another experience')
		).toBeInTheDocument();
		expect(screen.queryByText('Heading Example')).not.toBeInTheDocument();
		expect(screen.queryByText('A paragraph')).not.toBeInTheDocument();
	});

	it('shows an alert when there is no content', () => {
		renderPageContent({
			fragmentEntryLinks: {},
			pageContents: [],
		});

		expect(
			screen.getByText('there-is-no-content-on-this-page')
		).toBeInTheDocument();
	});

	it('shows only text content for inline text (without html) when the editable type is text', () => {
		renderPageContent({
			fragmentEntryLinks: {
				39685: {
					editableTypes: {'element-text': 'text'},
					editableValues: {
						[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
							'element-text': {
								defaultValue: '\n\tHeading example\n',
								en_US: 'This is a title&nbsp&nbsp&nbsp',
							},
						},
					},
					fragmentEntryLinkId: '39685',
					name: 'Heading',
					segmentsExperienceId: '0',
				},
			},
			pageContents: [],
		});

		expect(screen.queryByText('This is a title')).toBeInTheDocument();
	});

	it('shows only text content for inline text (without html) when the editable type is rich text', () => {
		renderPageContent({
			fragmentEntryLinks: {
				39685: {
					editableTypes: {'element-text': 'rich-text'},
					editableValues: {
						[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
							'element-text': {
								defaultValue: '\n\tParagraph example\n',
								en_US:
									'<span style="background: black;">This is a paragraph&nbsp&nbsp&nbsp<span>',
							},
						},
					},
					fragmentEntryLinkId: '39685',
					name: 'Paragraph',
					segmentsExperienceId: '0',
				},
			},
			pageContents: [],
		});

		expect(screen.queryByText('This is a paragraph')).toBeInTheDocument();
	});

	it('does not show inline text within the content list when the editable type is rich-text and there are only images', () => {
		renderPageContent({
			fragmentEntryLinks: {
				39685: {
					editableTypes: {'element-text': 'rich-text'},
					editableValues: {
						[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
							'element-text': {
								defaultValue: '\n\tParagraph example\n',
								en_US:
									'<img src="first-image"><img src="second-image">',
							},
						},
					},
					fragmentEntryLinkId: '39685',
					name: 'Paragraph',
					segmentsExperienceId: '0',
				},
			},
			pageContents: [],
		});

		expect(
			screen.getByText('there-is-no-content-on-this-page')
		).toBeInTheDocument();
	});

	it('does not show the inline text belonging to a form without permissions', () => {
		Liferay.FeatureFlags['LPS-169923'] = true;

		renderPageContent({});

		expect(screen.queryByText('A paragraph')).not.toBeInTheDocument();

		Liferay.FeatureFlags['LPS-169923'] = false;
	});
});
