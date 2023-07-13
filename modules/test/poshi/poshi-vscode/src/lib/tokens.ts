/* eslint-disable sort-keys */
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

import {inRange as _inRange} from 'lodash';
import * as vscode from 'vscode';

const tokenPatternMap = {
	/**
	 * matches "test TestCaseName {"
	 */
	testCaseName: /test ([A-Z][A-Za-z]+) \{/g,

	/**
	 * matches "${vari|able}"
	 */
	variable: /\$\{([A-Za-z_]+)\}/g,

	/**
	 * matches "PathFile"
	 * matches "PathFile|Name.LOCATOR_NAME"
	 */
	pathFileName: /"([A-Z][A-Za-z]+)/g,

	/**
	 * matches PathFileName.LOCATOR_N|AME
	 */
	pathLocator: /"([A-Z][A-Za-z]+)#([A-Z][A-Z_-]+)"/g,

	/**
	 * matches "UtilClass"
	 */
	utilClass: /[^\w.][^Test]([A-Z][A-Za-z]+Util)[.]/g,

	// matches "UtilClass.methodName"

	utilClassMethod: /[^\w.][^Test]([A-Z][A-Za-z]+Util)\.([A-Za-z_][A-Za-z]+)/g,

	// matches: Class|Name
	// matches Class|Name.methodName

	className: /[^\w.]([A-Z][A-Za-z]+)[(.]/g,

	/**
	 * matches ClassName.method|Name
	 */
	methodInvocation: /[^\w.]([A-Z][A-Za-z]+)\.([A-Za-z_][A-Za-z]+)/g,
	methodDefinition: /(?:macro|function) ([A-Za-z_][A-Za-z]+) \{/g,

	/**
	 * matches "selenium"
	 */
	liferaySelenium: /[^\w.](selenium)[.]/g,

	/**
	 * matches "selenium.method|Name"
	 */
	liferaySeleniumMethod: /[^\w.](selenium)\.([A-Za-z_][A-Za-z]+)/g,
};

export type TokenType = keyof typeof tokenPatternMap;

export interface Token {
	lineNumber?: number;
	match: Match;
	type: TokenType;
}

function getTokenAtColumn(
	tokens: Token[],
	columnNumber: number
): Token | undefined {
	for (const token of tokens) {
		if (token.match.isInRange(columnNumber)) {
			return token;
		}
	}
}
function getTokensOfType(tokens: Token[], type: TokenType): Token[] {
	return tokens.filter((token) => token.type === type);
}

interface Match {
	captures: string[];
	originalText: string;
	start: number;
	end: number;
	length: number;
	isInRange(columnNumber: number): boolean;
}

function toMatch(regExpMatchArray: RegExpMatchArray): Match | void {
	if (regExpMatchArray.index === undefined) return;
	if (regExpMatchArray.index === -1) return;
	if (!regExpMatchArray.input) return;

	return {
		captures: Array.from(regExpMatchArray),
		originalText: regExpMatchArray.input,
		start: regExpMatchArray.index,
		end: regExpMatchArray.index + regExpMatchArray[0].length,
		length: regExpMatchArray[0].length,
		isInRange(columnNumber: number) {
			return _inRange(columnNumber, this.start, this.end);
		},
	};
}

function getTextMatches(lineText: string, regex: RegExp): Match[] {
	const matches = [];

	for (const regExpMatchArray of lineText.matchAll(regex)) {
		const match = toMatch(regExpMatchArray);

		if (match) {
			matches.push(match);
		}
	}

	return matches;
}

export function getToken(
	text: string,
	currentIndex: number
): Token | undefined {
	return getTokenAtColumn(getTokens(text), currentIndex);
}

export function getTokens(text: string): Token[] {
	const tokens = [];
	for (const key of Object.keys(tokenPatternMap)) {
		const type = key as TokenType;

		for (const match of getTextMatches(
			text,
			new RegExp(tokenPatternMap[type])
		)) {
			tokens.push({
				match: match,
				type,
			});
		}
	}

	return tokens;
}

export function getDocumentTokens(document: vscode.TextDocument): Token[] {
	const tokens: Token[] = [];

	for (let lineNumber = 0; lineNumber < document.lineCount; lineNumber++) {
		const line = document.lineAt(lineNumber);

		for (const token of getTokens(line.text)) {
			token.lineNumber = lineNumber + 1;
			tokens.push(token);
		}
	}

	return tokens;
}
