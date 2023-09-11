/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';

interface IErrorMessage {
	[key: string]: string;
}
export const ERRORS: IErrorMessage = {
	'ObjectDefinitionNameException.MustBeLessThan41Characters': sub(
		Liferay.Language.get('only-x-characters-are-allowed-in-the-x-field'),
		['41', 'name']
	),
	'ObjectDefinitionNameException.MustBeginWithUpperCaseLetter': Liferay.Language.get(
		'the-first-character-of-a-name-must-be-an-upper-case-letter'
	),
	'ObjectDefinitionNameException.MustNotBeDuplicate': Liferay.Language.get(
		'this-name-is-already-in-use-try-another-one'
	),
	'ObjectDefinitionNameException.MustNotBeNull': Liferay.Language.get(
		'name-is-required'
	),
	'ObjectDefinitionNameException.MustNotStartWithCAndUnderscoreForSystemObject': Liferay.Language.get(
		'system-object-definition-names-must-not-start-with-c'
	),
	'ObjectDefinitionNameException.MustOnlyContainLettersAndDigits': Liferay.Language.get(
		'name-must-only-contain-letters-and-digits'
	),
	'ObjectDefinitionNameException.MustStartWithCAndUnderscoreForCustomObject': Liferay.Language.get(
		'custom-object-definition-names-must-start-with-c'
	),
	'ObjectFieldNameException.MustBeLessThan41Characters': sub(
		Liferay.Language.get('only-x-characters-are-allowed-in-the-x-field'),
		['41', 'name']
	),
	'ObjectFieldNameException.MustBeginWithLowerCaseLetter': Liferay.Language.get(
		'the-first-character-of-a-name-must-be-an-lower-case-letter'
	),
	'ObjectFieldNameException.MustNotBeDuplicate': Liferay.Language.get(
		'this-name-is-already-in-use-try-another-one'
	),
	'ObjectFieldNameException.MustNotBeNull': Liferay.Language.get(
		'name-is-required'
	),
	'ObjectFieldNameException.MustNotBeReserved': Liferay.Language.get(
		'name-reserved-by-the-system-try-another-one'
	),
	'ObjectFieldNameException.MustOnlyContainLettersAndDigits': Liferay.Language.get(
		'name-must-only-contain-letters-and-digits'
	),
	'ObjectFieldSettingValueException.MustBeLessThan256Characters': Liferay.Language.get(
		'storage-folder-path-cannot-be-greater-than-255-characters'
	),
};
