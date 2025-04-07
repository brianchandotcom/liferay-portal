/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {updateEditorConfigFilebrowsersURL} from '../../../../src/main/resources/META-INF/resources/js/core/reducers/fieldReducer.es';

describe('core/reducers/fieldReducer', () => {
	describe('updateEditorConfigFilebrowsersURL reducer util function', () => {
		const mockedFieldName = 'RichText123456789';
		const mockedFilebrowserBrowseUrl =
			'http://example.com/firstPathSegment/' +
			'_com_liferay_journal_web_portlet_JournalPortlet_ddm%24%24RichText123456789%24NiOykxlK%240%24%24en_USselectItem' +
			'?_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_itemSelectedEventName' +
			'=_com_liferay_journal_web_portlet_JournalPortlet_ddm%24%24RichText123456789%24NiOykxlK%240%24%24en_USselectItem' +
			'&_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_anotherParam' +
			'=anotherParamValue';
		const mockedNewName =
			'_com_liferay_journal_web_portlet_JournalPortlet_ddm$$RichText123456789$mru6i1i7$1$$en_US';

		it('updates a filebrowser URL path and searchParam with a given name', () => {
			const result = updateEditorConfigFilebrowsersURL(
				{filebrowserBrowseUrl: mockedFilebrowserBrowseUrl},
				mockedNewName,
				mockedFieldName
			);

			expect(result.filebrowserBrowseUrl).not.toBe(
				mockedFilebrowserBrowseUrl
			);

			expect(result.filebrowserBrowseUrl).toBe(
				'http://example.com/firstPathSegment/' +
					'_com_liferay_journal_web_portlet_JournalPortlet_ddm%24%24RichText123456789%24mru6i1i7%241%24%24en_USselectItem' +
					'?_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_itemSelectedEventName' +
					'=_com_liferay_journal_web_portlet_JournalPortlet_ddm%24%24RichText123456789%24mru6i1i7%241%24%24en_USselectItem' +
					'&_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_anotherParam' +
					'=anotherParamValue'
			);
		});

		it('does not modify path segments that are not related to the filebrowser', () => {
			const result = updateEditorConfigFilebrowsersURL(
				{filebrowserBrowseUrl: mockedFilebrowserBrowseUrl},
				mockedNewName,
				mockedFieldName
			);

			const urlObject = new URL(result.filebrowserBrowseUrl);

			expect(urlObject.pathname.split('/')[1]).toBe('firstPathSegment');
		});

		it('does not modify searchParams that are not related to the filebrowser', () => {
			const result = updateEditorConfigFilebrowsersURL(
				{filebrowserBrowseUrl: mockedFilebrowserBrowseUrl},
				mockedNewName,
				mockedFieldName
			);

			const urlObject = new URL(result.filebrowserBrowseUrl);

			const paramValue = urlObject.searchParams.get(
				'_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_anotherParam'
			);

			expect(paramValue).toBe('anotherParamValue');
		});
	});
});
