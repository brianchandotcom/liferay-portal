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
import {act, cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import Translate from '../../../src/main/resources/META-INF/resources/js/translate/Translate';

const baseProps = {
	additionalFields: {
		redirect: 'http://redirect-url',
		sourceLanguageId: 'en_US',
		targetLanguageId: 'es_ES',
	},
	autoTranslateEnabled: true,
	currentUrl: 'http://current-url',
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
					sourceContent: '<p>mock content</p>',
					sourceContentDir: 'ltr',
					targetContent: '<p>mock content</p',
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

	Liferay.Util.unescapeHTML =
		Liferay.Util.unescapeHTML ||
		jest.fn((string) =>
			string.replace(/&([^;]+);/g, (match) => {
				return new DOMParser().parseFromString(match, 'text/html')
					.documentElement.textContent;
			})
		);

	afterEach(cleanup);

	it('renders with auto-translate enabled', () => {
		const {asFragment} = renderComponent(baseProps);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with experiences selector', () => {
		const {asFragment} = renderComponent({
			...baseProps,
			experiencesSelectorData: {
				label: 'Experience',
				options: [
					{
						label: 'Default',
						value: '0',
					},
					{
						label: 'Experience 1',
						value: '1',
					},
				],
				value: '0',
			},
		});

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders auto-translate field button disabled when the field sourceContent is empty', () => {
		const {getByText} = renderComponent({
			...baseProps,
			infoFieldSetEntries: [
				{
					...baseProps.infoFieldSetEntries[1],
					fields: [
						{
							...baseProps.infoFieldSetEntries[1].fields[0],
							sourceContent: '',
						},
					],
				},
			],
		});

		expect(
			getByText(
				'auto-translate-x-field-' +
					baseProps.infoFieldSetEntries[1].fields[0].label
			).closest('button')
		).toBeDisabled();
	});

	describe('given a valid server response', () => {
		beforeEach(() => {
			fetch.mockResponseOnce(
				JSON.stringify({
					fields: {
						'infoField--content--': '<p>simulacro de contenido</p>',
						'infoField--description--': '<p>resumen simulado</p>',
						'infoField--title--': 'título simulado&#39;',
					},
					sourceLanguageId: 'en_US',
					targetLanguageId: 'es_ES',
				})
			);
		});

		afterEach(() => {
			fetch.resetMocks();
		});

		describe('when the user clicks on the auto-translate field button', () => {
			let infoFieldContent;
			let result;

			beforeEach(async () => {
				infoFieldContent = baseProps.infoFieldSetEntries[0].fields[0];
				result = renderComponent(baseProps);

				const {getByText} = result;
				const autoTranslateFieldButton = getByText(
					'auto-translate-x-field-' + infoFieldContent.label
				).closest('button');

				await act(async () => {
					fireEvent.click(autoTranslateFieldButton);
				});
			});

			it('sends a POST request to the server', async () => {
				const [url, {body}] = fetch.mock.calls[0];
				const request = JSON.parse(body);

				expect(url).toBe(baseProps.getAutoTranslateURL);
				expect(request.fields[infoFieldContent.id]).toBe(
					infoFieldContent.sourceContent
				);
				expect(request.sourceLanguageId).toBe(
					baseProps.sourceLanguageId
				);
				expect(request.targetLanguageId).toBe(
					baseProps.targetLanguageId
				);
			});

			// LPS-133164

			it('updates the input with the translated message with HTML unescaped character', () => {
				const {getByDisplayValue} = result;

				expect(
					getByDisplayValue("título simulado'")
				).toBeInTheDocument();
			});

			it('renders a success message', () => {
				const {getByText} = result;

				expect(getByText('field-translated')).toBeInTheDocument();
			});
		});

		describe('when the user clicks on the auto-translate general button', () => {
			let result;

			beforeEach(async () => {
				result = renderComponent(baseProps);

				const {getByText} = result;
				const autoTranslateButton = getByText('auto-translate');

				await act(async () => {
					fireEvent.click(autoTranslateButton);
				});
			});

			it('updates the input with the translated message with HTML unescaped character', () => {
				const {getByDisplayValue} = result;

				expect(
					getByDisplayValue("título simulado'")
				).toBeInTheDocument();
			});

			it('renders a success message', () => {
				const {getByText} = result;

				expect(
					getByText('successfully-received-translations')
				).toBeInTheDocument();
			});
		});
	});

	describe('given an error server response', () => {
		beforeEach(() => {
			fetch.mockResponseOnce(
				JSON.stringify({
					error: {
						message: 'mocked error',
					},
				})
			);
		});

		afterEach(() => {
			fetch.resetMocks();
		});

		describe('when the user clicks on the auto-translate field button', () => {
			it('renders an error message', async () => {
				const {getByText} = renderComponent(baseProps);

				const autoTranslateFieldButton = getByText(
					'auto-translate-x-field-' +
						baseProps.infoFieldSetEntries[0].fields[0].label
				).closest('button');

				await act(async () => {
					fireEvent.click(autoTranslateFieldButton);
				});

				expect(getByText('mocked error')).toBeInTheDocument();
			});
		});

		describe('when the user clicks on the auto-translate general button', () => {
			it('renders an error message', async () => {
				const {getByText} = renderComponent(baseProps);

				const autoTranslateFieldButton = getByText('auto-translate');

				await act(async () => {
					fireEvent.click(autoTranslateFieldButton);
				});

				expect(getByText('mocked error')).toBeInTheDocument();
			});
		});
	});

	it('renders with auto-translate disabled', () => {
		const {asFragment} = renderComponent({
			...baseProps,
			autoTranslateEnabled: false,
		});

		expect(asFragment()).toMatchSnapshot();
	});
});
