import {
	Conjunctions,
	CUSTOM_FUNCTION_OPERATOR_KEY_MAP,
	CustomFunctionOperators,
	FunctionalOperators,
	GROUP,
	NotOperators,
	PropertyTypes,
	RelationalOperators,
	SUPPORTED_PROPERTY_TYPES_MAP
} from './constants';
import {
	Context,
	Criteria,
	Criterion,
	CriterionGroup,
	ODataASTNode
} from './types';
import {CustomValue} from 'shared/util/records';
import {fromJS, Map} from 'immutable';
import {generateGroupId, generateRowId, isCriterionGroup} from './utils';
import {get, invert, isFinite, isNull, isString, isUndefined} from 'lodash';
import {getPropertyValue, setPropertyValue} from './custom-inputs';
import {getSafeDecodedURIComponent} from 'shared/util/util';
import {filter as oDataFilterFn} from 'odata-v4-parser';

const OPERATORS = {
	...CustomFunctionOperators,
	...FunctionalOperators,
	...RelationalOperators
};

const EXPRESSION_TYPES = {
	AND: 'AndExpression',
	BOOL_PAREN: 'BoolParenExpression',
	COMMON: 'CommonExpression',
	EQUALS: 'EqualsExpression',
	FIRST_MEMBER: 'FirstMemberExpression',
	FUNCTION: 'FunctionExpression',
	GREATER_OR_EQUALS: 'GreaterOrEqualsExpression',
	GREATER_THAN: 'GreaterThanExpression',
	LESSER_OR_EQUALS: 'LesserOrEqualsExpression',
	LESSER_THAN: 'LesserThanExpression',
	MEMBER: 'MemberExpression',
	METHOD_CALL: 'MethodCallExpression',
	NOT: 'NotExpression',
	NOT_EQUALS: 'NotEqualsExpression',
	OR: 'OrExpression',
	PAREN: 'ParenExpression',
	PROPERTY_PATH: 'PropertyPathExpression'
};

const EDM_NUMBERS = [
	'Edm.Decimal',
	'Edm.SByte',
	'Edm.Int16',
	'Edm.Int32',
	'Edm.Int64'
];

const EDM_NULL = 'null';

const EDM_STRING = 'Edm.String';

/**
 * Maps Odata-v4-parser generated AST expression names to internally used
 * constants.
 */
const oDataV4ParserNameMap = {
	[EXPRESSION_TYPES.AND]: Conjunctions.And,
	between: OPERATORS.Between,
	[EXPRESSION_TYPES.BOOL_PAREN]: GROUP,
	contains: OPERATORS.Contains,
	[EXPRESSION_TYPES.EQUALS]: OPERATORS.EQ,
	[EXPRESSION_TYPES.GREATER_OR_EQUALS]: OPERATORS.GE,
	[EXPRESSION_TYPES.GREATER_THAN]: OPERATORS.GT,
	[EXPRESSION_TYPES.LESSER_OR_EQUALS]: OPERATORS.LE,
	[EXPRESSION_TYPES.LESSER_THAN]: OPERATORS.LT,
	[EXPRESSION_TYPES.NOT_EQUALS]: OPERATORS.NE,
	[EXPRESSION_TYPES.OR]: Conjunctions.Or
};

/**
 * Constants for characters we will encode ourselves.
 */
const FARO_SPECIAL_CHARS = {
	ampersand: {
		encoded: '_FARO_AMPERSAND_',
		raw: '&'
	},
	at: {
		encoded: '_FARO_AT_',
		raw: '@'
	},
	bracketLeft: {
		encoded: '_FARO_LEFT_BRACKET_',
		raw: '['
	},
	bracketRight: {
		encoded: '_FARO_RIGHT_BRACKET_',
		raw: ']'
	},
	dash: {
		encoded: '_FARO_DASH_',
		raw: '-'
	},
	dollar: {
		encoded: '_FARO_DOLLAR_',
		raw: '$'
	},
	greaterThan: {
		encoded: '_FARO_GREATER_THAN_',
		raw: '>'
	},
	hash: {
		encoded: '_FARO_HASH_',
		raw: '#'
	},
	lessThan: {
		encoded: '_FARO_LESS_THAN_',
		raw: '<'
	},
	percent: {
		encoded: '_FARO_PERCENT_',
		raw: '%'
	},
	plus: {
		encoded: '_FARO_PLUS_',
		raw: '+'
	},
	question: {
		encoded: '_FARO_QUESTION_',
		raw: '?'
	},
	slash: {
		encoded: '_FARO_SLASH_',
		raw: '/'
	},
	underscore: {
		encoded: '_FARO_UNDERSCORE_',
		raw: '_'
	}
};

/**
 * Wraps a node in a grouping node.
 * @returns Object representing the grouping
 */
const addNewGroup = ({oDataASTNode, prevConjunction}: Context): Context => ({
	lastNodeWasGroup: false,
	oDataASTNode: {
		type: EXPRESSION_TYPES.BOOL_PAREN,
		value: oDataASTNode
	},
	prevConjunction
});

const PARAM_REGEX = /\s+((?:criterionGroup|operator|value)=)/g;

/**
 * Trim the spaces before params.
 * This function is necessary because the odata-v4-parser library can not
 * handle spaces between parameters inside of custom functions.
 */
export const trimSpacesBeforeParams = (queryString: string): string =>
	queryString.replace(PARAM_REGEX, '$1');

const buildVocabularyFilterString = (criterionGroup: any): string => {
	const items: any[] = criterionGroup?.items ?? [];

	const find = (name: string) =>
		items.find((item: any) => item.propertyName === name);

	const vocIdItem = find('vocabularies/id');
	const vocNameItem = find('vocabularies/name');
	const activityKeyItem = find('activityKey');
	const appIdItem = find('applicationId');
	const eventIdItem = find('eventId');
	const categoriesItem = find('categories');
	const dayItem = find('day');

	const parts: string[] = [];

	if (vocIdItem && vocNameItem) {
		parts.push(`vocabularies/id eq '${vocIdItem.value}'`);
		parts.push(`vocabularies/name eq '${vocNameItem.value}'`);
	}

	if (appIdItem && eventIdItem) {
		const appIds = (appIdItem.value as string[])
			.map((id: string) => `'${id}'`)
			.join(',');
		const eventIds = (eventIdItem.value as string[])
			.map((id: string) => `'${id}'`)
			.join(',');
		parts.push(
			`(applicationId in (${appIds}) and eventId in (${eventIds}))`
		);
	} else if (activityKeyItem) {
		parts.push(`activityKey eq '${activityKeyItem.value}'`);
	}

	if (categoriesItem?.value?.length > 0) {
		const catParts = (
			categoriesItem.value as Array<{id: string; name: string}>
		).map(
			cat =>
				`(categories/id eq '${cat.id}' and categories/name eq '${cat.name}')`
		);
		parts.push(`(${catParts.join(' or ')})`);
	}

	if (dayItem) {
		parts.push(
			`${dayItem.propertyName} ${dayItem.operatorName} '${dayItem.value}'`
		);
	}

	const result = parts.join(' and ');

	return result ? `(${result})` : result;
};

const buildTagFilterString = (criterionGroup: any): string => {
	const items: any[] = criterionGroup?.items ?? [];

	const find = (name: string) =>
		items.find((item: any) => item.propertyName === name);

	const tagIdItem = find('tags/id');
	const tagNameItem = find('tags/name');
	const activityKeyItem = find('activityKey');
	const appIdItem = find('applicationId');
	const eventIdItem = find('eventId');
	const dayItem = find('day');

	const parts: string[] = [];

	if (tagIdItem && tagNameItem) {
		parts.push(`tags/id eq '${tagIdItem.value}'`);
		parts.push(`tags/name eq '${tagNameItem.value}'`);
	}

	if (appIdItem && eventIdItem) {
		const appIds = (appIdItem.value as string[])
			.map((id: string) => `'${id}'`)
			.join(',');
		const eventIds = (eventIdItem.value as string[])
			.map((id: string) => `'${id}'`)
			.join(',');
		parts.push(
			`(applicationId in (${appIds}) and eventId in (${eventIds}))`
		);
	} else if (activityKeyItem) {
		parts.push(`activityKey eq '${activityKeyItem.value}'`);
	}

	if (dayItem) {
		parts.push(
			`${dayItem.propertyName} ${dayItem.operatorName} '${dayItem.value}'`
		);
	}

	const result = parts.join(' and ');

	return result ? `(${result})` : result;
};

/**
 * Recursively traverses the criteria object to build an oData filter query
 * string. Properties is required to parse the correctly with or without quotes
 * and formatting the query differently for certain types like collection.
 * @returns An OData query string built from the criteria object.
 */
const buildQueryString = (
	criteria: Criteria[],
	queryConjunction?: string
): string =>
	criteria
		.filter(Boolean)
		.reduce((queryString: string, criterion: Criteria, index: number) => {
			if (index > 0) {
				queryString = queryString.concat(` ${queryConjunction} `);
			}

			if (isCriterionGroup(criterion)) {
				const {conjunctionName, items} = criterion as CriterionGroup;

				const val = buildQueryString(items, conjunctionName);

				if (val) {
					queryString = queryString.concat(`(${val})`);
				}
			} else {
				const {operatorName, propertyName, type, value} =
					criterion as Criterion;

				const parsedValue = isString(value)
					? `'${decodeQuotesToOdataQuotes(encodeQuotes(value))}'`
					: value;

				if (isValueType(RelationalOperators, operatorName)) {
					if (operatorName === RelationalOperators.In) {
						const ids = (value as string[])
							.map((id: string) => `'${id}'`)
							.join(',');
						queryString = queryString.concat(
							`${propertyName} in (${ids})`
						);
					} else {
						queryString = queryString.concat(
							`${propertyName} ${operatorName} ${parsedValue}`
						);
					}
				} else if (isValueType(CustomFunctionOperators, operatorName)) {
					if (
						operatorName ===
							CustomFunctionOperators.VocabulariesFilter ||
						operatorName === CustomFunctionOperators.TagsFilter
					) {
						const criterionGroup = value
							.get('criterionGroup')
							?.toJS();
						const filterString =
							operatorName ===
							CustomFunctionOperators.VocabulariesFilter
								? buildVocabularyFilterString(criterionGroup)
								: buildTagFilterString(criterionGroup);
						const occurrenceOperator = value.get('operator');
						const occurrenceCount = value.get('value');

						const params: string[] = [
							`filter='${encodeQuotes(filterString)}'`
						];

						if (!isNull(occurrenceOperator)) {
							params.push(`operator='${occurrenceOperator}'`);
						}

						if (!isNull(occurrenceCount)) {
							params.push(`value=${occurrenceCount}`);
						}

						queryString = queryString.concat(
							`activities.filterByCount(${decodeQuotesToOdataQuotes(
								params.join(',')
							)})`
						);
					} else {
						const fnName = getFunctionNameFromOperatorName(
							operatorName ?? ''
						);

						const paramKeys = value.keySeq().toJS();

						const paramsString = paramKeys
							.map((key: string) => {
								if (
									(key === 'value' || key === 'operator') &&
									isNull(value.get(key))
								) {
									return;
								} else if (key === 'criterionGroup') {
									return `filter='${encodeQuotes(
										buildQueryString([
											value.get(key).toJS()
										])
									)}'`;
								} else if (
									key === 'value' &&
									!isString(value.get(key))
								) {
									return `${key}=${value.get(key)}`;
								}

								return `${key}='${value.get(key)}'`;
							})
							.filter(
								(val: string | undefined) => !isUndefined(val)
							)
							.join();

						queryString = queryString.concat(
							`${fnName}(${decodeQuotesToOdataQuotes(
								paramsString
							)})`
						);
					}
				} else if (isValueType(FunctionalOperators, operatorName)) {
					if (operatorName === FunctionalOperators.Between) {
						const {end, start} = parsedValue;

						queryString = queryString.concat(
							`between(${propertyName},'${start}','${end}')`
						);
					} else {
						queryString = queryString.concat(
							`${operatorName}(${propertyName}, ${parsedValue})`
						);
					}
				} else if (isValueType(NotOperators, operatorName)) {
					const baseOperator = (
						(operatorName ?? '') as string
					).replace(/not-/g, '') as Conjunctions &
						CustomFunctionOperators &
						FunctionalOperators &
						RelationalOperators &
						'GROUP';

					const baseExpression: Criterion[] = [
						{
							operatorName: baseOperator,
							propertyName,
							type,
							value
						}
					];

					queryString = queryString.concat(
						`(not ${buildQueryString(baseExpression)})`
					);
				}
			}

			return queryString;
		}, '');

/**
 * Converts custom encodings back to original characters.
 */
const decodeSpecialCharacters = (queryString: string): string => {
	const specialCharactersArr = Object.values(FARO_SPECIAL_CHARS);

	const specialCharsEncoded = specialCharactersArr
		.map(({encoded}) => encoded)
		.join('|');

	const pattern = new RegExp(specialCharsEncoded, 'g');

	return queryString.replace(pattern, match => {
		const specialCharacter = specialCharactersArr.find(
			({encoded}) => encoded === match
		);

		if (specialCharacter) {
			return specialCharacter.raw;
		}

		return match;
	});
};

const encodeQuotes = (text: string): string => text.replace(/'/g, '%27');

/**
 * Encode certain special characters with our own encoding.
 */
const encodeSpecialCharacters = (queryString: string): string => {
	const charsNeedEscaped = ['+', '?', '$', '[', ']'];
	const specialCharactersArr = Object.values(FARO_SPECIAL_CHARS);

	const specialCharsPattern = specialCharactersArr
		.map(({raw}) => (charsNeedEscaped.includes(raw) ? `\\${raw}` : raw))
		.join('|');

	const pattern = new RegExp(specialCharsPattern, 'g');

	return queryString.replace(pattern, match => {
		const specialCharacter = specialCharactersArr.find(
			({raw}) => raw === match
		);

		if (specialCharacter) {
			return specialCharacter.encoded;
		}

		return match;
	});
};

/**
 * Escape single quotes in a string for general purposes.
 */
export const escapeSingleQuotes = (text: string) => text.replace(/'/g, "''");

/**
 * Escape all %27 encoded quotes.
 */
const decodeQuotesToOdataQuotes = (encodedText: string): string =>
	encodedText.replace(/%27/g, "''");

/**
 * Encode all %22 decoded quotes.
 */
const encodeDoubleQuotesToOdataQuotes = (decodedText: string): string =>
	decodedText.replaceAll('"', '%22');

/**
 * Gets the internal name of a child expression from the oDataV4Parser name
 */
const getChildExpressionName = (oDataASTNode: ODataASTNode): string =>
	getExpressionName(oDataASTNode.value);

/**
 * Gets the conjunction of the group or returns AND as a default.
 * @returns The conjunction name for a group or, if not available, AND.
 */
const getConjunctionForGroup = (oDataASTNode: ODataASTNode): string => {
	const childExpressionName = getChildExpressionName(oDataASTNode);

	return isValueType(Conjunctions, childExpressionName)
		? childExpressionName
		: Conjunctions.And;
};

/**
 * Gets the operatorName from the function name & namespace.
 */
const getOperatorNameFromFunctionName = (
	name: string,
	namespace: string
): CustomFunctionOperators =>
	CUSTOM_FUNCTION_OPERATOR_KEY_MAP[
		`${namespace}.${name}` as keyof typeof CUSTOM_FUNCTION_OPERATOR_KEY_MAP
	];

/**
 * Gets the function name & namespace from the operatorName.
 */
const getFunctionNameFromOperatorName = (operatorName: string): string =>
	invert(CUSTOM_FUNCTION_OPERATOR_KEY_MAP)[operatorName];

/**
 * Gets the internal name of an expression from the oDataV4Parser name.
 */
const getExpressionName = (oDataASTNode: ODataASTNode): string => {
	const {type} = oDataASTNode;

	let returnValue = oDataV4ParserNameMap[type];

	if (type == EXPRESSION_TYPES.METHOD_CALL) {
		returnValue = oDataASTNode.value.method;
	} else if (type === EXPRESSION_TYPES.FUNCTION) {
		const {name, namespace} = oDataASTNode.value.fn.value;

		returnValue = getOperatorNameFromFunctionName(name, namespace);
	}

	return returnValue;
};

const getFunctionName = (oDataASTNode: ODataASTNode): string =>
	oDataV4ParserNameMap[oDataASTNode.value.method];

/**
 * Returns the next expression in the syntax tree that is not a grouping.
 */
const getNextNonGroupExpression = (
	oDataASTNode: ODataASTNode
): ODataASTNode => {
	let returnValue;

	if (oDataASTNode.value.type === EXPRESSION_TYPES.BOOL_PAREN) {
		returnValue = getNextNonGroupExpression(oDataASTNode.value);
	} else {
		returnValue = oDataASTNode.value.left
			? oDataASTNode.value.left
			: oDataASTNode.value;
	}

	return returnValue;
};

/**
 * Returns the next expression in the syntax tree that is not a grouping.
 * Also ignoring Common, Paren, Member, and FirstMember expressions for property
 * path expression types like `cookies/any(c:c eq 'key=value')` since the
 * expressions' value are the same for a collection query.
 */
const getNextOperatorExpression = (
	oDataASTNode: ODataASTNode
): ODataASTNode => {
	let returnValue;

	const nextNode = oDataASTNode.value.left
		? oDataASTNode.value.left
		: oDataASTNode.value;

	const type = nextNode.type;

	if (
		type === EXPRESSION_TYPES.BOOL_PAREN ||
		type === EXPRESSION_TYPES.AND ||
		type === EXPRESSION_TYPES.OR ||
		type === EXPRESSION_TYPES.COMMON ||
		type === EXPRESSION_TYPES.FIRST_MEMBER ||
		type === EXPRESSION_TYPES.MEMBER ||
		type === EXPRESSION_TYPES.PAREN
	) {
		returnValue = getNextOperatorExpression(nextNode);
	} else {
		returnValue = nextNode;
	}

	return returnValue;
};

/**
 * Checks if a grouping has different conjunctions (e.g. (x AND y OR z)).
 */
const hasDifferentConjunctions = ({
	lastNodeWasGroup,
	oDataASTNode,
	prevConjunction
}: Context): boolean =>
	prevConjunction !== oDataASTNode.type && !lastNodeWasGroup;

/**
/**
 * Checks if the value is a certain type.
 * @param {object} types - A map of supported types.
 * @param {*} value - The value to validate.
 */
const isValueType = (types: object, value: string | undefined): boolean =>
	value !== undefined && Object.values(types).includes(value);

/**
 * Checks if the group is needed; It is unnecessary when there are multiple
 * groupings in a row, when the conjunction directly outside the group is the
 * same as the one inside or there is no conjunction within a grouping.
 */
const isRedundantGroup = ({
	lastNodeWasGroup,
	oDataASTNode,
	prevConjunction
}: Context): boolean => {
	const nextNodeExpressionName = getExpressionName(
		getNextNonGroupExpression(oDataASTNode)
	);

	return (
		lastNodeWasGroup ||
		oDataV4ParserNameMap[prevConjunction ?? ''] ===
			nextNodeExpressionName ||
		!isValueType(Conjunctions, nextNodeExpressionName)
	);
};

/**
 * Removes all extra quotes and leaves escaped quotes
 */
const parseNestedOdataString = (text: string): string => {
	const escapedText = text.replace(/''/g, '%27');

	return removeQuotes(escapedText).replace(/%27/g, "'");
};

/**
 * Removes surrounding quotes from a string.
 */
const removeSurroundingQuotes = (text: string) =>
	text.replace(/^['"](.*)['"]$/g, '$1');

/**
 * Removes both single `'` and double `"` quotes from a string.
 */
const removeQuotes = (text: string): string => text.replace(/['"]+/g, '');

/**
 * Removes a grouping node and returns the child node.
 */
const skipGroup = ({oDataASTNode, prevConjunction}: Context): Context => ({
	lastNodeWasGroup: true,
	oDataASTNode: oDataASTNode.value,
	prevConjunction
});

/**
 * Replaces the "between" method with "substring" because the
 * oDataV4Parser can't handle between.
 */
export const convertBetweenToSubstring = (queryString: string): string =>
	queryString.replace(
		/between(?=\([\w-:]+,('[\w-:]+',?){2}\))/g,
		'substring'
	);

export const decodeValueFromCriteria = (criteria: Criteria) => {
	const decodeValue = (value: string) => {
		let decodedValue = value;

		try {
			decodedValue = getSafeDecodedURIComponent(value);
		} catch (e) {}

		return decodedValue;
	};

	const formatCriteria = (criteria: any) => {
		const newCriteria = {...criteria};

		if (newCriteria.value) {
			if (typeof newCriteria.value === 'string') {
				newCriteria.value = decodeValue(newCriteria.value);
			} else if (newCriteria.value?._map) {
				newCriteria.value = setPropertyValue(
					newCriteria.value,
					'value',
					0,
					decodeValue(getPropertyValue(newCriteria.value, 'value', 0))
				);
			}
		}

		if (newCriteria.items) {
			newCriteria.items = newCriteria.items.map(formatCriteria);
		}

		if (newCriteria.propertyName) {
			newCriteria.propertyName = decodeValue(newCriteria.propertyName);
		}

		return newCriteria;
	};

	return formatCriteria(criteria);
};

const tryParseFilterByCountCriteria = (
	queryString: string
): CriterionGroup | null => {
	const match = queryString.match(
		/activities\.filterByCount\(filter='((?:[^']|'')*)'\s*(?:,operator='([^']*)')?(?:,value=(\d+))?\)/
	);

	if (!match) return null;

	const filterContent = match[1].replace(/''/g, "'");
	const occurrenceOperator = match[2] ?? null;
	const occurrenceValue = match[3] !== undefined ? parseInt(match[3]) : null;

	const innerFilter =
		filterContent.startsWith('(') && filterContent.endsWith(')')
			? filterContent.slice(1, -1)
			: filterContent;

	const vocIdMatch = innerFilter.match(/vocabularies\/id eq '([^']+)'/);
	const tagIdMatch = innerFilter.match(/tags\/id eq '([^']+)'/);

	if (!vocIdMatch && !tagIdMatch) return null;

	const isVocabulary = !!vocIdMatch;
	const entityId = isVocabulary ? vocIdMatch![1] : tagIdMatch![1];

	const entityNameMatch = isVocabulary
		? innerFilter.match(/vocabularies\/name eq '([^']+)'/)
		: innerFilter.match(/tags\/name eq '([^']+)'/);
	const entityName = entityNameMatch?.[1] ?? entityId;

	const idPropName = isVocabulary ? 'vocabularies/id' : 'tags/id';
	const namePropName = isVocabulary ? 'vocabularies/name' : 'tags/name';

	const items: Criterion[] = [
		{
			operatorName: RelationalOperators.EQ,
			propertyName: idPropName,
			rowId: generateRowId(),
			touched: false,
			valid: true,
			value: entityId
		} as unknown as Criterion,
		{
			operatorName: RelationalOperators.EQ,
			propertyName: namePropName,
			rowId: generateRowId(),
			touched: false,
			valid: true,
			value: entityName
		} as unknown as Criterion
	];

	const appIdMatch = innerFilter.match(/applicationId in \(([^)]+)\)/);
	const eventIdMatch = innerFilter.match(/eventId in \(([^)]+)\)/);

	if (appIdMatch && eventIdMatch) {
		const parseIds = (s: string) =>
			s.split(',').map(id => id.trim().replace(/^'|'$/g, ''));

		items.push({
			operatorName: RelationalOperators.In,
			propertyName: 'applicationId',
			rowId: generateRowId(),
			touched: false,
			valid: true,
			value: parseIds(appIdMatch[1])
		} as unknown as Criterion);

		items.push({
			operatorName: RelationalOperators.In,
			propertyName: 'eventId',
			rowId: generateRowId(),
			touched: false,
			valid: true,
			value: parseIds(eventIdMatch[1])
		} as unknown as Criterion);
	} else {
		const activityKeyMatch = innerFilter.match(/activityKey eq '([^']+)'/);

		if (activityKeyMatch) {
			items.push({
				operatorName: RelationalOperators.EQ,
				propertyName: 'activityKey',
				rowId: generateRowId(),
				touched: false,
				valid: true,
				value: activityKeyMatch[1]
			} as unknown as Criterion);
		}
	}

	if (isVocabulary) {
		const catRegex =
			/\(categories\/id eq '([^']+)' and categories\/name eq '([^']+)'\)/g;
		const categoryItems: Array<{id: string; name: string}> = [];
		let catMatch: RegExpExecArray | null;

		while ((catMatch = catRegex.exec(innerFilter)) !== null) {
			categoryItems.push({id: catMatch[1], name: catMatch[2]});
		}

		if (categoryItems.length > 0) {
			items.push({
				operatorName: RelationalOperators.In,
				propertyName: 'categories',
				rowId: generateRowId(),
				touched: false,
				valid: true,
				value: categoryItems
			} as unknown as Criterion);
		}
	}

	const dayMatch = innerFilter.match(/day (gt|ge|lt|le|eq|ne) '([^']+)'/);

	if (dayMatch) {
		items.push({
			operatorName: dayMatch[1],
			propertyName: 'day',
			rowId: generateRowId(),
			touched: false,
			valid: true,
			value: dayMatch[2]
		} as unknown as Criterion);
	}

	const criterionGroup: CriterionGroup = {
		conjunctionName: Conjunctions.And,
		criteriaGroupId: generateGroupId(),
		items
	};

	const customValue = new CustomValue(
		Map({
			criterionGroup: fromJS(criterionGroup),
			operator: occurrenceOperator,
			value: occurrenceValue
		})
	);

	return wrapInCriteriaGroup([
		{
			operatorName: isVocabulary
				? CustomFunctionOperators.VocabulariesFilter
				: CustomFunctionOperators.TagsFilter,
			propertyName: entityId,
			rowId: generateRowId(),
			touched: false,
			valid: true,
			value: customValue
		} as unknown as Criteria
	]);
};

/**
 * Converts an OData filter query string to an object that can be used by the
 * criteria builder
 */
const translateQueryToCriteria = (queryString: string): Criteria => {
	let criteria;

	try {
		if (queryString === '()') {
			throw new Error('queryString is ()');
		}

		const encodedQuotes = encodeDoubleQuotesToOdataQuotes(queryString);
		const trimSpaces = trimSpacesBeforeParams(encodedQuotes);
		const encodedSpecialCharacters = encodeSpecialCharacters(trimSpaces);
		const substrings = convertBetweenToSubstring(encodedSpecialCharacters);
		const token = oDataFilterFn(substrings);
		const stringfied = JSON.stringify(token);
		const decodedSpecialCharacters = decodeSpecialCharacters(stringfied);

		const oDataASTNode = JSON.parse(decodedSpecialCharacters);
		const criteriaArray = toCriteria({oDataASTNode});

		criteria = isCriterionGroup(criteriaArray[0])
			? criteriaArray[0]
			: wrapInCriteriaGroup(criteriaArray);

		criteria = decodeValueFromCriteria(criteria);
	} catch (e) {
		try {
			criteria = tryParseFilterByCountCriteria(queryString);
		} catch {
			criteria = null;
		}
	}

	return criteria;
};

/**
 * Recursively transforms the AST generated by the odata-v4-parser library into
 * a shape the criteria builder expects. Returns an array so that left and right
 * arguments can be concatenated together.
 */
const toCriteria = (context: Context): Criteria[] => {
	const {oDataASTNode} = context;

	const expressionName = getExpressionName(oDataASTNode);

	let criterion: Criteria[] | undefined;

	if (oDataASTNode.type === EXPRESSION_TYPES.NOT) {
		criterion = transformNotNode(context);
	} else if (oDataASTNode.type === EXPRESSION_TYPES.COMMON) {
		criterion = transformCommonNode(context);
	} else if (oDataASTNode.type === EXPRESSION_TYPES.METHOD_CALL) {
		criterion = transformFunctionalNode(context);
	} else if (oDataASTNode.type === EXPRESSION_TYPES.FUNCTION) {
		criterion = transformCustomFunctionNode(context);
	} else if (isValueType(RelationalOperators, expressionName)) {
		criterion = transformOperatorNode(context);
	} else if (isValueType(Conjunctions, expressionName)) {
		criterion = transformConjunctionNode(context);
	} else if (expressionName === GROUP) {
		criterion = transformGroupNode(context);
	}

	return criterion ?? [];
};

/**
 * Transform an operator expression node into a criterion for the criteria
 * builder.
 */
const transformCommonNode = ({oDataASTNode}: Context): Criteria[] => {
	const nextNodeExpression = getNextOperatorExpression(oDataASTNode);

	let value;

	if (nextNodeExpression.type === EXPRESSION_TYPES.FUNCTION) {
		return transformCustomFunctionNode({oDataASTNode: nextNodeExpression});
	} else if (nextNodeExpression.type === EXPRESSION_TYPES.METHOD_CALL) {
		const methodExpressionName = getExpressionName(nextNodeExpression);

		if (methodExpressionName === 'substring') {
			const [{raw: propertyName}, {raw: start}, {raw: end}] =
				nextNodeExpression.value.parameters;

			return [
				{
					operatorName: OPERATORS.Between,
					propertyName,
					rowId: generateRowId(),
					touched: false,
					valid: true,
					value: {
						end: removeQuotes(end),
						start: removeQuotes(start)
					}
				}
			] as unknown as Criterion[];
		}

		return [];
	} else {
		const anyExpression = get(nextNodeExpression, [
			'value',
			'next',
			'value'
		]);

		const methodExpression = get(anyExpression, [
			'value',
			'predicate',
			'value'
		]);

		const methodExpressionName = getExpressionName(methodExpression);

		if (methodExpressionName == OPERATORS.Contains) {
			value = removeQuotes(methodExpression.value.parameters[1].raw);
		} else if (methodExpressionName == OPERATORS.EQ) {
			value = removeQuotes(methodExpression.value.right.raw);
		}

		return [
			{
				operatorName: methodExpressionName,
				propertyName: nextNodeExpression.value.current.raw,
				rowId: generateRowId(),
				touched: false,
				valid: true,
				value
			}
		] as unknown as Criteria[];
	}
};

/**
 * Transforms conjunction expression node into a criterion for the criteria
 * builder. If it comes across a grouping sharing an AND and OR conjunction, it
 * will add a new grouping so the criteria builder doesn't require a user to
 * know operator precedence.
 * @returns an array containing the concatenated left and right values of a
 * conjunction expression or a new grouping.
 */
const transformConjunctionNode = (context: Context): Criteria[] => {
	const {oDataASTNode} = context;

	const conjunctionType = oDataASTNode.type;
	const nextNode = oDataASTNode.value;

	return hasDifferentConjunctions(context)
		? toCriteria(addNewGroup(context))
		: [
				...toCriteria({
					oDataASTNode: nextNode.left,
					prevConjunction: conjunctionType
				}),
				...toCriteria({
					oDataASTNode: nextNode.right,
					prevConjunction: conjunctionType
				})
		  ];
};

/**
 * Transform a custom function expression node into a criterion for the criteria
 * builder.
 * @returns an array containing the object representation of an operator
 * criterion
 */
const transformCustomFunctionNode = ({oDataASTNode}: Context): Criterion[] => {
	const {fn, params} = oDataASTNode.value;

	const {name, namespace} = fn.value;

	let detectedFilterType: 'tag' | 'vocabulary' | null = null;
	let detectedEntityId: string | null = null;
	let detectedEntityName: string | null = null;

	const customValue = new CustomValue(
		params.value.reduce((accIMap: Map<string, any>, cur: any) => {
			const {
				name: {
					value: {name}
				},
				value: {value}
			} = cur.value;

			if (name === 'filter') {
				const rawFilterFull = parseNestedOdataString(value.raw);
				const rawFilter =
					rawFilterFull.startsWith('(') && rawFilterFull.endsWith(')')
						? rawFilterFull.slice(1, -1)
						: rawFilterFull;
				const parsed = translateQueryToCriteria(rawFilter);
				const criterionGroupIMap = fromJS(parsed);

				if (!parsed) {
					const vocIdMatch = rawFilter.match(
						/vocabularies\/id eq '([^']+)'/
					);
					const tagIdMatch = rawFilter.match(/tags\/id eq '([^']+)'/);

					if (vocIdMatch) {
						detectedFilterType = 'vocabulary';
						detectedEntityId = vocIdMatch[1];
						detectedEntityName =
							rawFilter.match(
								/vocabularies\/name eq '([^']+)'/
							)?.[1] ?? null;
					} else if (tagIdMatch) {
						detectedFilterType = 'tag';
						detectedEntityId = tagIdMatch[1];
						detectedEntityName =
							rawFilter.match(/tags\/name eq '([^']+)'/)?.[1] ??
							null;
					}
				}

				return detectedEntityName
					? accIMap
							.set('criterionGroup', criterionGroupIMap)
							.set('_name', detectedEntityName)
					: accIMap.set('criterionGroup', criterionGroupIMap);
			} else if (name === 'value' && isFinite(parseInt(value.raw))) {
				return accIMap.set(name, parseInt(value.raw));
			} else {
				return accIMap.set(name, removeQuotes(value.raw));
			}
		}, Map())
	);

	const firstItemPropertyName = customValue.getIn([
		'criterionGroup',
		'items',
		0,
		'propertyName'
	]);
	const isVocabularyFilter =
		firstItemPropertyName === 'vocabularies/id' ||
		detectedFilterType === 'vocabulary';
	const isTagFilter =
		firstItemPropertyName === 'tags/id' || detectedFilterType === 'tag';

	const operatorName = isVocabularyFilter
		? CustomFunctionOperators.VocabulariesFilter
		: isTagFilter
		? CustomFunctionOperators.TagsFilter
		: getOperatorNameFromFunctionName(name, namespace);

	const propertyName =
		isVocabularyFilter || isTagFilter
			? customValue.getIn(['criterionGroup', 'items', 0, 'value']) ??
			  detectedEntityId
			: firstItemPropertyName;

	let touched:
		| boolean
		| {asset: boolean; occurenceCount: boolean}
		| {
				attribute: boolean;
				attributeValue: boolean;
				occurenceCount: boolean;
		  } = false;
	let valid:
		| boolean
		| {asset: boolean; occurenceCount: boolean}
		| {
				attribute: boolean;
				attributeValue: boolean;
				occurenceCount: boolean;
		  } = true;

	// TODO: Prob need one here for PropertyTypes.Event
	if (
		SUPPORTED_PROPERTY_TYPES_MAP[PropertyTypes.Behavior].includes(
			operatorName
		)
	) {
		touched = {asset: false, occurenceCount: false};
		valid = {asset: true, occurenceCount: true};
	} else if (
		SUPPORTED_PROPERTY_TYPES_MAP[PropertyTypes.Event].includes(operatorName)
	) {
		touched = {
			attribute: false,
			attributeValue: false,
			occurenceCount: false
		};
		valid = {attribute: true, attributeValue: true, occurenceCount: true};
	}

	return [
		{
			operatorName,
			propertyName,
			rowId: generateRowId(),
			touched,
			valid,
			value: customValue
		}
	] as unknown as Criterion[];
};

/**
 * Transform a function expression node into a criterion for the criteria
 * builder.
 * @returns an array containing the object representation of an operator
 * criterion
 */
const transformFunctionalNode = ({oDataASTNode}: Context): Criterion[] =>
	[
		{
			operatorName: getFunctionName(oDataASTNode),
			propertyName: oDataASTNode.value.parameters[0].raw,
			rowId: generateRowId(),
			touched: false,
			valid: true,
			value: removeQuotes(oDataASTNode.value.parameters[1].raw)
		}
	] as unknown as Criterion[];

/**
 * Transforms a group expression node into a criterion for the criteria
 * builder. If it comes across a grouping that is redundant (doesn't provide
 * readability improvements, superfluous to order of operations), it will remove
 * it.
 * @returns Criterion representation of an AST expression node in an array.
 */
const transformGroupNode = (context: Context): Criteria[] => {
	const {oDataASTNode, prevConjunction} = context;

	return isRedundantGroup(context)
		? toCriteria(skipGroup(context))
		: [
				{
					conjunctionName: getConjunctionForGroup(oDataASTNode),
					criteriaGroupId: generateGroupId(),
					items: toCriteria({
						lastNodeWasGroup: true,
						oDataASTNode: oDataASTNode.value,
						prevConjunction
					})
				}
		  ];
};

/**
 * Transform an operator expression node into a criterion for the criteria
 * builder.
 * @returns An array containing the object representation of an operator
 * criterion.
 */
const transformNotNode = ({oDataASTNode}: Context): Criteria[] => {
	const nextNodeExpression = getNextOperatorExpression(oDataASTNode);

	const nextNodeExpressionName = getExpressionName(nextNodeExpression);

	let returnValue: Criteria[] = [];

	if (nextNodeExpressionName == OPERATORS.Contains) {
		returnValue = [
			{
				...transformFunctionalNode({
					oDataASTNode: nextNodeExpression
				})[0],
				operatorName: NotOperators.NotContains
			}
		] as unknown as Criteria[];
	} else if (isValueType(CustomFunctionOperators, nextNodeExpressionName)) {
		const criterion = transformCustomFunctionNode({
			oDataASTNode: nextNodeExpression
		})[0];

		returnValue = [
			{
				...criterion,
				operatorName: `not-${criterion.operatorName}`
			}
		] as unknown as Criteria[];
	} else if (nextNodeExpression.type == EXPRESSION_TYPES.PROPERTY_PATH) {
		const anyExpression = nextNodeExpression.value.next.value;

		const methodExpression = anyExpression.value.predicate.value;

		const methodExpressionName = getExpressionName(methodExpression);

		if (methodExpressionName == OPERATORS.Contains) {
			returnValue = [
				{
					...transformFunctionalNode({
						oDataASTNode: nextNodeExpression
					})[0],
					operatorName: NotOperators.NotContains
				}
			] as unknown as Criteria[];
		}
	}

	return returnValue;
};

/**
 * Transform an operator expression node into a criterion for the criteria
 * builder.
 * @returns An array containing the object representation of an operator
 * criterion.
 */
const transformOperatorNode = ({oDataASTNode}: Context): Criterion[] => {
	const valueType = oDataASTNode.value.right.value;

	let value: string | number | null = removeSurroundingQuotes(
		oDataASTNode.value.right.raw
	);

	if (EDM_NUMBERS.includes(valueType)) {
		value = parseFloat(value);
	} else if (valueType === EDM_STRING) {
		value = unescapeSingleQuotes(value as string);
	} else if (valueType === EDM_NULL) {
		value = null;
	}

	return [
		{
			operatorName: getExpressionName(oDataASTNode),
			propertyName: oDataASTNode.value.left.raw,
			rowId: generateRowId(),
			touched: false,
			valid: true,
			value
		}
	] as unknown as Criterion[];
};

/**
 * Unescape single quotes in a string for general purposes.
 */
const unescapeSingleQuotes = (text: string) => text.replace(/''/g, "'");

/**
 * Wraps the criteria items in a criterion group.
 */
export const wrapInCriteriaGroup = (
	criteriaArray: Criteria[]
): CriterionGroup => ({
	conjunctionName: Conjunctions.And,
	criteriaGroupId: generateGroupId(),
	items: criteriaArray
});

export {buildQueryString, translateQueryToCriteria};
