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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import updateLanguageId from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateLanguageId';
import Translation from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/Translation';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/editableFragmentEntryProcessor';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/freemarkerFragmentEntryProcessor';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({config: {}})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/actions/updateLanguageId',
	() => jest.fn(() => () => {})
);

const availableLanguages = {
	language_1: {languageIcon: 'language-1', languageLabel: 'language-1'},
	language_2: {languageIcon: 'langauge-2', languageLabel: 'langauge-2'},
	language_3: {languageIcon: 'language-3', languageLabel: 'language-3'},
	language_4: {languageIcon: 'language-4', languageLabel: 'language-4'},
};
const FRAGMENT_ENTRY_LINK_ID = '1';
const FRAGMENT_ENTRY_LINK_ID_2 = '2';
const fragmentEntryLink = {
	configuration: {
		fieldSets: [
			{
				fields: [
					{
						dataType: 'string',
						defaultValue: 'h1',
					},
				],
			},
		],
	},
	editableValues: {
		[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
			'element-text': {
				defaultValue: 'Text',
				'segments-experience-id-0': {language_1: 'Text language 1'},
			},
		},
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {},
	},
	fragmentEntryLinkId: FRAGMENT_ENTRY_LINK_ID,
	name: 'Heading',
};
const fragmentEntryLink2 = {
	...fragmentEntryLink,
	editableValues: {
		...fragmentEntryLink.editableValues,
		[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: {
			'element-text': {
				defaultValue: 'Text',
			},
		},
	},
};
const languageId = 'language_2';
const segmentsExperienceId = '0';

const defaultState = {
	fragmentEntryLinks: {[FRAGMENT_ENTRY_LINK_ID]: fragmentEntryLink},
	segmentsExperienceId,
};

const renderTranslation = ({state}) => {
	return render(
		<StoreAPIContextProvider getState={() => state}>
			<Translation
				availableLanguages={availableLanguages}
				defaultLanguageId={languageId}
				dispatch={() => {}}
				fragmentEntryLinks={state.fragmentEntryLinks}
				languageId={languageId}
			/>
		</StoreAPIContextProvider>
	);
};

describe('Translation', () => {
	afterEach(cleanup);

	it('renders Translation component', () => {
		const {getByText} = renderTranslation({state: defaultState});

		expect(getByText('language-4')).toBeInTheDocument();
	});

	it('dispatches languageId when a language is selected', () => {
		const {getByText} = renderTranslation({state: defaultState});
		const button = getByText('language-3').parentElement;

		userEvent.click(button);

		expect(updateLanguageId).toHaveBeenLastCalledWith({
			languageId: 'language_3',
		});
	});

	it('sets label "translated" when there is a translated language', () => {
		const {getByText} = renderTranslation({state: defaultState});
		const indicator = getByText('language-1').nextSibling.textContent;

		expect(indicator).toBe('translated');
	});

	it('sets label 1/n when there is one language traduction', () => {
		const newState = {
			...defaultState,
			fragmentEntryLinks: {
				[FRAGMENT_ENTRY_LINK_ID]: fragmentEntryLink,
				[FRAGMENT_ENTRY_LINK_ID_2]: fragmentEntryLink2,
			},
		};
		const {getByText} = renderTranslation({state: newState});
		const indicator = getByText('language-1').nextSibling.textContent;

		expect(indicator).toBe('translating 1/2');
	});

	it('only takes into account elements which do not come from the master page', () => {
		const newState = {
			...defaultState,
			fragmentEntryLinks: {
				[FRAGMENT_ENTRY_LINK_ID]: {
					...fragmentEntryLink,
					masterLayout: true,
				},
				[FRAGMENT_ENTRY_LINK_ID_2]: {
					...fragmentEntryLink,
					masterLayout: false,
				},
			},
		};
		const {getByText} = renderTranslation({state: newState});
		const indicator = getByText('language-1').nextSibling.textContent;

		expect(indicator).toBe('translated');
	});
});
