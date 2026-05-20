import {RemoteCriterionType} from './RemoteCriterionType';
import {tagCriterionType} from './tagCriterionType';
import {vocabularyCriterionType} from './vocabularyCriterionType';

export const REMOTE_CRITERION_TYPES: ReadonlyArray<RemoteCriterionType> = [
	vocabularyCriterionType,
	tagCriterionType
];

const BY_PROPERTY_KEY: ReadonlyMap<string, RemoteCriterionType> = new Map(
	REMOTE_CRITERION_TYPES.map(ct => [ct.propertyKey, ct])
);

const BY_OPERATOR: ReadonlyMap<string, RemoteCriterionType> = new Map(
	REMOTE_CRITERION_TYPES.flatMap(ct =>
		Array.from(ct.operators, op => [op as string, ct] as const)
	)
);

export const getRemoteCriterionTypeByPropertyKey = (
	propertyKey: string | null | undefined
): RemoteCriterionType | undefined =>
	propertyKey ? BY_PROPERTY_KEY.get(propertyKey) : undefined;

export const getRemoteCriterionTypeByOperator = (
	operatorName: string | null | undefined
): RemoteCriterionType | undefined =>
	operatorName ? BY_OPERATOR.get(operatorName) : undefined;

export const isRemoteCriterionOperator = (
	operatorName: string | null | undefined
): boolean => !!operatorName && BY_OPERATOR.has(operatorName);
