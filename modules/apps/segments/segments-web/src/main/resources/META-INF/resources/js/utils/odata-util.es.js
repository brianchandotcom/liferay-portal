import {
	AND,
	CONTAINS,
	EQ,
	GE,
	GROUP,
	GT,
	LE,
	LT,
	NE,
	OR
} from './constants.es';

const CONJUNCTIONS = [AND, OR];

const FUNCTIONAL_OPERATORS = [CONTAINS];

const RELATIONAL_OPERATORS = [EQ, GE, GT, LE, LT, NE];

const addNewGroup = (queryAST, prevConjunction) => ({
	lastNodeWasGroup: false,
	prevConjunction,
	queryAST: {type: 'BoolParenExpression', value: queryAST}
});

const comparatorTransformation = ({
	lastNodeWasGroup,
	prevConjunction,
	queryAST
}) => {
	return (prevConjunction === queryAST.type || lastNodeWasGroup) ?
		[
			...toCriteriaMap(
				{
					prevConjunction: queryAST.type,
					queryAST: queryAST.value.left
				}
			),
			...toCriteriaMap(
				{
					prevConjunction: queryAST.type,
					queryAST: queryAST.value.right
				}
			)
		] :
		toCriteriaMap(addNewGroup(queryAST, prevConjunction));
};

const groupTransformation = ({lastNodeWasGroup, prevConjunction, queryAST}) => {
	const nextNodeType = getNextNonGroupNodeType(queryAST);

	let returnValue;

	if (
		lastNodeWasGroup ||
		prevConjunction === nextNodeType ||
		isNotConjunction(nextNodeType)
	) {
		returnValue = toCriteriaMap(skipGroup(queryAST, prevConjunction));
	}
	else if (queryAST.value.left) {
		const childType = getChildNodeTypeName(queryAST);

		returnValue = [
			{
				conjunctionName: CONJUNCTIONS.includes(childType) ?
					childType :
					AND,
				items: [
					...toCriteriaMap(
						{
							lastNodeWasGroup: true,
							prevConjunction: queryAST.type,
							queryAST: queryAST.value.left
						}
					),
					...toCriteriaMap(
						{
							lastNodeWasGroup: true,
							prevConjunction: queryAST.type,
							queryAST: queryAST.value.right
						}
					)
				]
			}
		];
	}
	else {
		const childType = getChildNodeTypeName(queryAST);

		returnValue = [
			{
				conjunctionName: CONJUNCTIONS.includes(childType) ?
					childType :
					AND,
				items: [
					...toCriteriaMap(
						{
							lastNodeWasGroup: true,
							prevConjunction,
							queryAST: queryAST.value
						}
					)
				]
			}
		];
	}

	return returnValue;
};

function isCriteriaMapGroup(criteriaMapArray) {
	return !!criteriaMapArray[0].items;
}

function isNotConjunction(nodeType) {
	return !CONJUNCTIONS.includes(getTypeName(nodeType));
}

const getNextNonGroupNodeType = queryAST => {
	let returnValue;

	if (queryAST.value.type === 'BoolParentExpression') {
		returnValue = getNextNonGroupNodeType(queryAST.value);
	}
	else {
		returnValue = queryAST.value.left ?
			queryAST.value.left.type :
			queryAST.value.type;
	}

	return returnValue;
};

const getChildNodeTypeName = query =>
	oDataTransformationMap[query.value.type].name;

const getTypeName = type => oDataTransformationMap[type].name;

const operatorTransformation = ({queryAST}) => {
	return [
		{
			operatorName: oDataTransformationMap[queryAST.type].name,
			propertyName: queryAST.value.left.raw,
			value: queryAST.value.right.raw.replace(/['"]+/g, '')
		}
	];
};

const skipGroup = (queryAST, prevConjunction) => ({
	lastNodeWasGroup: true,
	prevConjunction,
	queryAST: queryAST.value
});

const toCriteriaMap = ({
	lastNodeWasGroup = false,
	prevConjunction,
	queryAST
}) => {
	const oDataParserType = oDataTransformationMap[queryAST.type];

	return oDataParserType.transformationFunction(
		{
			lastNodeWasGroup,
			prevConjunction,
			queryAST
		}
	);
};

const wrapInCriteriaMapGroup = criteriaMapArray => (
	{
		conjunctionName: AND,
		items: criteriaMapArray
	}
);

const oDataTransformationMap = {
	AndExpression: {
		name: AND,
		transformationFunction: comparatorTransformation
	},
	BoolParenExpression: {
		name: GROUP,
		transformationFunction: groupTransformation
	},
	EqualsExpression: {
		name: EQ,
		transformationFunction: operatorTransformation
	},
	GreaterOrEqualsExpression: {
		name: GE,
		transformationFunction: operatorTransformation
	},
	GreaterThanExpression: {
		name: GT,
		transformationFunction: operatorTransformation
	},
	LesserOrEqualsExpression: {
		name: LE,
		transformationFunction: operatorTransformation
	},
	LesserThanExpression: {
		name: LT,
		transformationFunction: operatorTransformation
	},
	NotEqualsExpression: {
		name: NE,
		transformationFunction: operatorTransformation
	},
	OrExpression: {
		name: OR,
		transformationFunction: comparatorTransformation
	}
};

export const buildQueryString = (queryItems, queryConjunction) => {
	let queryString = '';

	queryItems.forEach(
		(item, index) => {
			const {
				conjunctionName,
				items,
				operatorName,
				propertyName,
				value
			} = item;

			if (index > 0) {
				queryString = queryString.concat(` ${queryConjunction} `);
			}

			if (conjunctionName) {
				queryString = queryString.concat(
					`(${buildQueryString(items, conjunctionName)})`
				);
			}
			else if (RELATIONAL_OPERATORS.includes(operatorName)) {
				queryString = queryString.concat(
					`${propertyName} ${operatorName} '${value}'`
				);
			}
			else if (FUNCTIONAL_OPERATORS.includes(operatorName)) {
				queryString = queryString.concat(
					`${operatorName} (${propertyName}, '${value}')`
				);
			}
		}
	);

	return queryString;
};

export const translateToCriteria = query => {
	let returnValue;

	try {
		const queryAST = window.oDataParser.filter(query);

		const criteriaMapArray = toCriteriaMap({queryAST});

		returnValue = isCriteriaMapGroup(criteriaMapArray) ?
			criteriaMapArray[0] :
			wrapInCriteriaMapGroup(criteriaMapArray);
	}
	catch (e) {
		returnValue = null;
	}

	return returnValue;
};