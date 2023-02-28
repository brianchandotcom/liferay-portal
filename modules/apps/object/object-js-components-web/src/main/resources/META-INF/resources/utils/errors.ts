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
interface IErrorMessage {
	[key: string]: string;
}
export const ERRORS: IErrorMessage = {
	MustBeLessThan41Characters: Liferay.Language.get(
		'only-41-characters-are-allowed'
	),
	MustBeLessThan256Characters: Liferay.Language.get(
		'storage-folder-path-cannot-be-greater-than-255-characters'
	),
	MustBeginWithLowerCaseLetter: Liferay.Language.get(
		'the-first-character-of-a-name-must-be-an-lower-case-letter'
	),
	MustBeginWithUpperCaseLetter: Liferay.Language.get(
		'the-first-character-of-a-name-must-be-an-upper-case-letter'
	),
	MustNotBeDuplicate: Liferay.Language.get(
		'this-name-is-already-in-use-try-another-one'
	),
	MustNotBeNull: Liferay.Language.get('name-is-required'),
	MustNotBeReserved: Liferay.Language.get(
		'name-reserved-by-the-system-try-another-one'
	),
	MustNotStartWithCAndUnderscoreForSystemObject: Liferay.Language.get(
		'system-object-definition-names-must-not-start-with-c'
	),
	MustOnlyContainLettersAndDigits: Liferay.Language.get(
		'name-must-only-contain-letters-and-digits'
	),
	MustStartWithCAndUnderscoreForCustomObject: Liferay.Language.get(
		'custom-object-definition-names-must-start-with-c'
	),
};
