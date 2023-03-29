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
	hash: {
		encoded: '_FARO_HASH_',
		raw: '#'
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
				const {
					operatorName,
					propertyName,
					type,
					value
				} = criterion as Criterion;

				const parsedValue = isString(value)
					? `'${decodeQuotesToOdataQuotes(encodeQuotes(value))}'`
					: value;

				if (isValueType(RelationalOperators, operatorName)) {
					queryString = queryString.concat(
						`${propertyName} ${operatorName} ${parsedValue}`
					);
				} else if (isValueType(CustomFunctionOperators, operatorName)) {
					const fnName = getFunctionNameFromOperatorName(
						operatorName
					);

					const paramKeys = value.keySeq().toJS();

					const paramsString = paramKeys
						.map(key => {
							if (
								(key === 'value' || key === 'operator') &&
								isNull(value.get(key))
							) {
								return;
							} else if (key === 'criterionGroup') {
								return `filter='${encodeQuotes(
									buildQueryString([value.get(key).toJS()])
								)}'`;
							} else if (
								key === 'value' &&
								!isString(value.get(key))
							) {
								return `${key}=${value.get(key)}`;
							}

							return `${key}='${value.get(key)}'`;
						})
						.filter(val => !isUndefined(val))
						.join();

					queryString = queryString.concat(
						`${fnName}(${decodeQuotesToOdataQuotes(paramsString)})`
					);
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
					const baseOperator = (operatorName as string).replace(
						/not-/g,
						''
					) as Conjunctions &
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
	const {
		ampersand,
		hash,
		percent,
		plus,
		question,
		slash
	} = FARO_SPECIAL_CHARS;

	const specialCharsEncoded = Object.values(FARO_SPECIAL_CHARS)
		.map(({encoded}) => encoded)
		.join('|');

	const pattern = new RegExp(specialCharsEncoded, 'g');

	return queryString.replace(pattern, match => {
		switch (match) {
			case ampersand.encoded:
				return ampersand.raw;
			case hash.encoded:
				return hash.raw;
			case percent.encoded:
				return percent.raw;
			case plus.encoded:
				return plus.raw;
			case question.encoded:
				return question.raw;
			case slash.encoded:
				return slash.raw;
			default:
				return match;
		}
	});
};

const encodeQuotes = (text: string): string => text.replace(/'/g, '%27');

/**
 * Encode certain special characters with our own encoding.
 */
const encodeSpecialCharacters = (queryString: string): string => {
	const {
		ampersand,
		hash,
		percent,
		plus,
		question,
		slash
	} = FARO_SPECIAL_CHARS;

	const charsNeedEscaped = ['+', '?'];

	const specialCharsPattern = Object.values(FARO_SPECIAL_CHARS)
		.map(({raw}) => (charsNeedEscaped.includes(raw) ? `\\${raw}` : raw))
		.join('|');

	const pattern = new RegExp(specialCharsPattern, 'g');

	return queryString.replace(pattern, match => {
		switch (match) {
			case ampersand.raw:
				return ampersand.encoded;
			case hash.raw:
				return hash.encoded;
			case percent.raw:
				return percent.encoded;
			case plus.raw:
				return plus.encoded;
			case question.raw:
				return question.encoded;
			case slash.raw:
				return slash.encoded;
			default:
				return match;
		}
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
	CUSTOM_FUNCTION_OPERATOR_KEY_MAP[`${namespace}.${name}`];

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
const isValueType = (types: object, value: string): boolean =>
	Object.values(types).includes(value);

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
		oDataV4ParserNameMap[prevConjunction] === nextNodeExpressionName ||
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

/**
 * Converts an OData filter query string to an object that can be used by the
 * criteria builder
 */
const translateQueryToCriteria = (initialQueryString: string): Criteria => {
	let criteria;

	const regex = new RegExp(/\[*\]|\[*\[/);
	const queryStringWithBrackets = regex.test(initialQueryString);

	const queryString = queryStringWithBrackets
		? initialQueryString.replaceAll('[', '').replaceAll(']', '')
		: initialQueryString;

	try {
		if (queryString === '()') {
			throw new Error('queryString is ()');
		}

		const oDataASTNode = JSON.parse(
			decodeSpecialCharacters(
				JSON.stringify(
					oDataFilterFn(
						convertBetweenToSubstring(
							encodeSpecialCharacters(
								trimSpacesBeforeParams(queryString)
							)
						)
					)
				)
			)
		);

		const criteriaArray = toCriteria({oDataASTNode});

		criteria = isCriterionGroup(criteriaArray[0])
			? criteriaArray[0]
			: wrapInCriteriaGroup(criteriaArray);
	} catch (e) {
		criteria = null;
	}

	if (queryStringWithBrackets) {
		const initialValueList = initialQueryString.match(/'([^']*)'/g);

		const items = criteria.items.map((item, index) => ({
			...item,
			value: initialValueList[index].slice(1, -1)
		}));

		return {...criteria, items};
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

	let criterion;

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

	return criterion;
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
			const [
				{raw: propertyName},
				{raw: start},
				{raw: end}
			] = nextNodeExpression.value.parameters;

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
			] as Criterion[];
		}
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
		] as Criteria[];
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

	const customValue = new CustomValue(
		params.value.reduce((accIMap, cur) => {
			const {
				name: {
					value: {name}
				},
				value: {value}
			} = cur.value;

			if (name === 'filter') {
				const criterionGroupIMap = fromJS(
					translateQueryToCriteria(parseNestedOdataString(value.raw))
				);

				return accIMap.set('criterionGroup', criterionGroupIMap);
			} else if (name === 'value' && isFinite(parseInt(value.raw))) {
				return accIMap.set(name, parseInt(value.raw));
			} else {
				return accIMap.set(name, removeQuotes(value.raw));
			}
		}, Map())
	);

	const operatorName = getOperatorNameFromFunctionName(name, namespace);

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
			propertyName: customValue.getIn([
				'criterionGroup',
				'items',
				0,
				'propertyName'
			]),
			rowId: generateRowId(),
			touched,
			valid,
			value: customValue
		}
	] as Criterion[];
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
	] as Criterion[];

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

	let returnValue;

	if (nextNodeExpressionName == OPERATORS.Contains) {
		returnValue = [
			{
				...transformFunctionalNode({
					oDataASTNode: nextNodeExpression
				})[0],
				operatorName: NotOperators.NotContains
			}
		];
	} else if (isValueType(CustomFunctionOperators, nextNodeExpressionName)) {
		returnValue = [
			{
				...transformCustomFunctionNode({
					oDataASTNode: nextNodeExpression
				})[0],
				operatorName: `not-${nextNodeExpressionName}`
			}
		];
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
			];
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

	let value: string | number = removeSurroundingQuotes(
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
	] as Criterion[];
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
