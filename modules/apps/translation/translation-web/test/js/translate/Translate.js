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

import Translate from '../../../src/main/resources/META-INF/resources/js/translate/Translate';

const baseProps = {
	aditionalFields: {
		redirect: 'http://redirect-url',
		sourceLanguageId: 'en_US',
		targetLanguageId: 'es_ES',
	},
	autoTranslateEnabled: true,
	getAutoTranslateURL: 'http://translation-url/auto_translate',
	infoFieldSetEntries: [
		{
			fields: [
				{
					editorConfiguration: null,
					html: false,
					id: 'infoField--title--',
					label: 'Title',
					multiline: false,
					sourceContent: 'mock title',
					sourceContentDir: 'ltr',
					targetContent: 'mock title',
					targetContentDir: 'ltr',
					targetLanguageId: 'es_ES',
				},
				{
					editorConfiguration: {},
					html: true,
					id: 'infoField--description--',
					label: 'Description',
					multiline: false,
					sourceContent: '<p>mock summary</p>',
					sourceContentDir: 'ltr',
					targetContent: '<p>mock summary</p>',
					targetContentDir: 'ltr',
					targetLanguageId: 'es_ES',
				},
			],
			legend: 'Basic Information',
		},
		{
			fields: [
				{
					editorConfiguration: {},
					html: true,
					id: 'infoField--content--',
					label: 'Content',
					multiline: true,
					sourceContent: '<p>mock conent</p>',
					sourceContentDir: 'ltr',
					targetContent: '<p>mock conent</p',
					targetContentDir: 'ltr',
					targetLanguageId: 'es_ES',
				},
			],
			legend: 'Content (Basic Web Content)',
		},
	],
	portletId: 'mock_TranslationPortlet',
	portletNamespace: '_mock_TranslationPortlet_',
	publishButtonDisabled: false,
	publishButtonLabel: 'Publish',
	redirectURL: 'http://redirect-url',
	saveButtonDisabled: false,
	saveButtonLabel: 'Save as Draft',
	sourceLanguageId: 'en_US',
	sourceLanguageIdTitle: 'en-US',
	targetLanguageId: 'es_ES',
	targetLanguageIdTitle: 'es-ES',
	translateLanguagesSelectorData: {
		currentUrl: 'http://current-url',
		sourceAvailableLanguages: ['en_US', 'es_ES'],
		sourceLanguageId: 'en_US',
		targetAvailableLanguages: [
			'ar_SA',
			'ca_ES',
			'zh_CN',
			'nl_NL',
			'fi_FI',
			'fr_FR',
			'de_DE',
			'hu_HU',
			'ja_JP',
			'pt_BR',
			'es_ES',
			'sv_SE',
		],
		targetLanguageId: 'es_ES',
	},
	translationPermission: true,
	updateTranslationPortletURL: 'http://update-url',
	workflowActions: {
		PUBLISH: '1',
		SAVE_DRAFT: '2',
	},
};

const renderComponent = (props) => render(<Translate {...props} />);

describe('Translate', () => {
	Liferay.Util.sub.mockImplementation((langKey, ...args) =>
		[langKey, ...args].join('-')
	);

	afterEach(cleanup);

	it('renders with auto-translate enabled', () => {
		const {asFragment} = renderComponent(baseProps);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with auto-translate disabled', () => {
		const {asFragment} = renderComponent({
			...baseProps,
			autoTranslateEnabled: false,
		});

		expect(asFragment()).toMatchSnapshot();
	});
});
