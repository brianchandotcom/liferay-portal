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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {StoreContextProvider} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import ContentsSidebar from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/contents/components/ContentsSidebar';

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

const renderPageContent = ({
	fragmentEntryLinks = FRAGMENT_ENTRY_LINKS,
	pageContents = PAGE_CONTENTS,
	languageId = 'en_US',
	segmentsExperienceId = '0',
}) =>
	render(
		<StoreContextProvider
			initialState={{
				fragmentEntryLinks,
				languageId,
				pageContents,
				permissions: {UPDATE: true, UPDATE_LAYOUT_CONTENT: true},
				segmentsExperienceId,
			}}
		>
			<ContentsSidebar></ContentsSidebar>
		</StoreContextProvider>
	);

describe('ContentsSidebar', () => {
	afterEach(cleanup);

	it('shows the content list', () => {
		const {getByText} = renderPageContent({});

		expect(getByText('WC1')).toBeInTheDocument();
		expect(getByText('WC2')).toBeInTheDocument();
	});

	it('shows inline text within the content list when the editable type is text', () => {
		const {getByText} = renderPageContent({});

		expect(getByText('Heading Example')).toBeInTheDocument();
	});

	it('shows inline text within the content list when the editable type is rich-text', () => {
		const {getByText} = renderPageContent({
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

		expect(getByText('This is a title')).toBeInTheDocument();
	});

	it('shows inline text corresponding to an experience', () => {
		const {queryByText} = renderPageContent({
			segmentsExperienceId: '1',
		});

		expect(
			queryByText('Heading Example from another experience')
		).toBeInTheDocument();
		expect(queryByText('Heading Example')).not.toBeInTheDocument();
		expect(queryByText('A paragraph')).not.toBeInTheDocument();
	});

	it('shows an alert when there is no content', () => {
		const {getByText} = renderPageContent({
			fragmentEntryLinks: {},
			pageContents: [],
		});

		expect(
			getByText('there-is-no-content-on-this-page')
		).toBeInTheDocument();
	});

	it('shows only text content for inline text (without html) when the editable type is text', () => {
		const {queryByText} = renderPageContent({
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

		expect(queryByText('This is a title')).toBeInTheDocument();
	});

	it('shows only text content for inline text (without html) when the editable type is rich text', () => {
		const {queryByText} = renderPageContent({
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

		expect(queryByText('This is a paragraph')).toBeInTheDocument();
	});

	it('does not show inline text within the content list when the editable type is rich-text and there are only images', () => {
		const {getByText} = renderPageContent({
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
			getByText('there-is-no-content-on-this-page')
		).toBeInTheDocument();
	});
});
