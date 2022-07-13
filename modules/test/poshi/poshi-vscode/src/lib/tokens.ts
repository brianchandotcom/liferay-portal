import { inRange as _inRange } from 'lodash';

const tokenPatternMap = {
	// matches "${vari|able}"
	variable: /\$\{([A-Za-z_]+)\}/g,
	// matches "PathFile"
	// matches "PathFile|Name.LOCATOR_NAME"
	pathFileName: /"([A-Z][A-Za-z]+)/g,
	// matches PathFileName.LOCATOR_N|AME
	pathLocator: /"([A-Z][A-Za-z]+)#([A-Z][A-Z_-]+)"/g,
	// matches "UtilClass"
	utilClass: /[^\w\.][^Test]([A-Z][A-Za-z]+Util)[\.]/g,
	// matches "UtilClass.methodName"
	utilClassMethod:
		/[^\w\.][^Test]([A-Z][A-Za-z]+Util)\.([A-Za-z_][A-Za-z]+)/g,
	// matches: Class|Name
	// matches Class|Name.methodName
	className: /[^\w\.]([A-Z][A-Za-z]+)[\(\.]/g,
	// matches ClassName.method|Name
	methodInvocation: /[^\w\.]([A-Z][A-Za-z]+)\.([A-Za-z_][A-Za-z]+)/g,
	methodDefinition: /(?:macro|function) ([A-Za-z_][A-Za-z]+) \{/g,
	// matches "selenium"
	liferaySelenium: /[^\w\.](selenium)[\.]/g,
	// matches "selenium.method|Name"
	liferaySeleniumMethod: /[^\w\.](selenium)\.([A-Za-z_][A-Za-z]+)/g,
};

type TokenType = keyof typeof tokenPatternMap;

interface Token {
	matches: string[];
	type: TokenType;
}

function getTextMatchesUnderCursor(
	lineText: string,
	columnNumber: number,
	regex: RegExp,
): string[] | undefined {
	for (const match of lineText.matchAll(regex)) {
		const { index } = match;

		if (index === undefined) {
			continue;
		}

		if (index === -1) {
			continue;
		}

		if (_inRange(columnNumber, index, index + match[0].length)) {
			return Array.from(match);
		}
	}
}

export const getToken = (
	text: string,
	currentIndex: number,
): Token | undefined => {
	for (const key of Object.keys(tokenPatternMap)) {
		const type = key as TokenType;

		let matches = getTextMatchesUnderCursor(
			text,
			currentIndex,
			new RegExp(tokenPatternMap[type]),
		);

		if (matches !== undefined) {
			return {
				matches,
				type,
			};
		}
	}
};
