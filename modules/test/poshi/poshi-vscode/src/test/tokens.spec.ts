/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import test from 'ava';

import {Token, TokenType, getToken} from '../lib/tokens';

interface TestCase {
	expectedMatchText: string[];
	input: string;
	optionalDescription?: string;
}

const tokenTypeTestCases: {[K in TokenType]: TestCase[]} = {
	className: [
		{
			expectedMatchText: ['SomeClass'],
			input: 'var response = |SomeClass.someMethod(${curl});',
		},
		{
			expectedMatchText: ['SomeClass'],
			input: 'var response = SomeClass|.someMethod(${curl});',
			optionalDescription: 'with cursor at the end',
		},
		{
			expectedMatchText: ['Some-Class_2'],
			input: 'var response = |Some-Class_2.someMethod(${curl});',
			optionalDescription: 'with numbers and special characters',
		},
	],
	liferaySelenium: [
		{
			expectedMatchText: ['selenium'],
			input: 'var nodeNameValue = |selenium.getElementValue("ProcessBuilderKaleoDesignerReact#NODE_NAME_GETTER");',
		},
		{
			expectedMatchText: ['selenium'],
			input: 'var nodeNameValue = selenium|.getElementValue("ProcessBuilderKaleoDesignerReact#NODE_NAME_GETTER");',
			optionalDescription: 'with cursor at the end',
		},
	],
	liferaySeleniumMethod: [
		{
			expectedMatchText: ['selenium', 'getElementValue'],
			input: 'var nodeNameValue = selenium.|getElementValue("ProcessBuilderKaleoDesignerReact#NODE_NAME_GETTER");',
		},
		{
			expectedMatchText: ['selenium', 'getElementValue'],
			input: 'var nodeNameValue = selenium.getElementValue|("ProcessBuilderKaleoDesignerReact#NODE_NAME_GETTER");',
			optionalDescription: 'with cursor at the end',
		},
	],
	methodDefinition: [
		{
			expectedMatchText: ['fooBar'],
			input: 'macro |fooBar {',
			optionalDescription: 'macro',
		},
		{
			expectedMatchText: ['fooBar'],
			input: 'macro fooBar| {',
			optionalDescription: 'macro with cursor at the end',
		},
		{
			expectedMatchText: ['foo-Bar_Baz8'],
			input: 'macro |foo-Bar_Baz8 {',
			optionalDescription: 'macro with numbers and special characters',
		},
		{
			expectedMatchText: ['fooBar'],
			input: 'function |fooBar {',
			optionalDescription: 'function',
		},
		{
			expectedMatchText: ['fooBar'],
			input: 'function fooBar| {',
			optionalDescription: 'function with cursor at the end',
		},
		{
			expectedMatchText: ['foo-Bar_Baz8'],
			input: 'function |foo-Bar_Baz8 {',
			optionalDescription: 'function with numbers and special characters',
		},
	],
	methodInvocation: [
		{
			expectedMatchText: ['SomeClass', 'someMethod'],
			input: 'var response = SomeClass.|someMethod(${curl});',
		},
		{
			expectedMatchText: ['SomeClass', 'someMethod'],
			input: 'var response = SomeClass.someMethod|(${curl});',
			optionalDescription: 'with cursor at the end',
		},
		{
			expectedMatchText: ['SomeClass', 'foo-Bar_Baz8'],
			input: 'var response = SomeClass.|foo-Bar_Baz8(${curl});',
			optionalDescription: 'with numbers and special characters',
		},
		{
			expectedMatchText: ['Some-Class_2', 'someMethod'],
			input: 'var response = Some-Class_2.|someMethod(${curl});',
			optionalDescription:
				'with numbers and special characters in class name',
		},
	],
	pathFileName: [
		{
			expectedMatchText: ['TextInput'],
			input: 'locator1 = "|TextInput#MAIL_DOMAIN",',
		},
		{
			expectedMatchText: ['TextInput'],
			input: 'locator1 = "TextInput|#MAIL_DOMAIN",',
			optionalDescription: 'with cursor at the end',
		},
		{
			expectedMatchText: ['Abc-_123'],
			input: 'locator1 = "|Abc-_123#MAIL_DOMAIN",',
			optionalDescription: 'with special characters',
		},
	],
	pathLocator: [
		{
			expectedMatchText: ['TextInput', 'MAIL_DOMAIN'],
			input: 'locator1 = "TextInput#|MAIL_DOMAIN",',
		},
		{
			expectedMatchText: ['TextInput', 'MAIL_DOMAIN'],
			input: 'locator1 = "TextInput#MAIL_DOMAIN|",',
			optionalDescription: 'with cursor at the end',
		},
		{
			expectedMatchText: ['TextInput', 'MAIL_DOMAIN2_FOO'],
			input: 'locator1 = "TextInput#|MAIL_DOMAIN2_FOO",',
			optionalDescription: 'with number',
		},
		{
			expectedMatchText: ['Abc-_123', 'MAIL_DOMAIN'],
			input: 'locator1 = "Abc-_123#|MAIL_DOMAIN",',
			optionalDescription: 'with special characters in file name',
		},
	],
	testCaseName: [
		{
			expectedMatchText: ['TestCaseName'],
			input: `test |TestCaseName {`,
		},
		{
			expectedMatchText: ['TestCaseName'],
			input: `test TestCaseName| {`,
			optionalDescription: 'with cursor at the end',
		},
		{
			expectedMatchText: ['Test_Case-Name3Foo'],
			input: `test |Test_Case-Name3Foo {`,
			optionalDescription: 'with numbers and special characters',
		},
		{
			expectedMatchText: ['testCaseName'],
			input: `test |testCaseName {`,
			optionalDescription: 'with starting lower case',
		},
	],
	variable: [
		{
			expectedMatchText: ['variableName'],
			input: 'foo bar ${|variableName} baz',
		},
		{
			expectedMatchText: ['variableName'],
			input: 'foo bar ${variableName|} baz',
			optionalDescription: 'with cursor at the end',
		},
		{
			expectedMatchText: ['AbC-123_'],
			input: 'foo bar ${|AbC-123_} baz',
			optionalDescription: 'with numbers and special characters',
		},
	],
};

const cursor = '|';

const macro = test.macro({
	exec(t, tokenType: TokenType, testCase: TestCase) {
		const cursorIndex = testCase.input.indexOf(cursor);
		const inputText = testCase.input.replace(cursor, '');

		const result = getToken(inputText, cursorIndex) as Token;

		t.assert(
			result,
			`No token found for text "${inputText}" at cursor position ${cursorIndex}`
		);
		t.is(result.type, tokenType, 'Token type');

		const actualMatches = result.match.captures.slice(1);

		t.is(
			actualMatches.length,
			testCase.expectedMatchText.length,
			`Expected ${testCase.expectedMatchText} but got ${actualMatches}`
		);

		for (
			let index = 0;
			index < testCase.expectedMatchText.length;
			index++
		) {
			t.is(
				actualMatches[index],
				testCase.expectedMatchText[index],
				'Match text'
			);
		}
	},
	title(providedTitle = '', tokenType: TokenType, testCase: TestCase) {
		let title = `${providedTitle} > ${tokenType}`;

		if (testCase.optionalDescription) {
			title = `${title} ${testCase.optionalDescription}`;
		}

		return title;
	},
});

for (const [tokenType, testCases] of Object.entries(tokenTypeTestCases)) {
	for (const testCase of testCases) {
		test('getToken', macro, tokenType as TokenType, testCase);
	}
}
