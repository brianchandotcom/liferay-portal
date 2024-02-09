/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getValueFromItem from '../../src/main/resources/META-INF/resources/utils/getValueFromItem';

const testItem = {
	creator: {
		name: 'The creator',
	},
	id: 1234,
	label: {
		'en_US': 'The label',
		'fr_FR': "L'etiquette",
		'it-IT': '',
	},
	name: 'Item name',
};

function setupLanguage({
	defaultLanguageId,
	languageId,
}: {
	defaultLanguageId: string;
	languageId: string;
}): void {
	Liferay.ThemeDisplay.getDefaultLanguageId = () => defaultLanguageId;
	Liferay.ThemeDisplay.getLanguageId = () => languageId;
}

describe('getValueFromItem helper', () => {
	beforeEach(() => {
		setupLanguage({defaultLanguageId: 'en_US', languageId: 'en_US'});
	});

	it('returns an empty string if no fieldName is provided', () => {
		expect(getValueFromItem(testItem)).toEqual('');
	});

	it('returns the value of the selected item property', () => {
		const fieldName = 'name';
		const result = getValueFromItem(testItem, fieldName);

		expect(result).toEqual(testItem.name);
	});

	it('returns the value of any selected nested property', () => {
		const fieldName = ['creator', 'name'];
		const result = getValueFromItem(testItem, fieldName);

		expect(result).toEqual(testItem.creator.name);
	});

	it('returns the localized value of a selected nested property identified as a LANG key', () => {
		const arrayFieldName = ['label', 'LANG'];
		const usResult = getValueFromItem(testItem, arrayFieldName);

		expect(usResult).toEqual(testItem.label.en_US);

		setupLanguage({defaultLanguageId: 'fr_FR', languageId: 'fr_FR'});

		const frResult = getValueFromItem(testItem, arrayFieldName);

		expect(frResult).toEqual(testItem.label.fr_FR);
	});
});
