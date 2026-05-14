import {getRemoteCriterionTypeByOperator} from './registry';
import {RemoteCriterionType} from './RemoteCriterionType';

export interface ExtractedRemoteCriterionEntry {
	criterionType: RemoteCriterionType;
	id: string;
	name: string;
}

/**
 * Walks a criteria object recursively and returns the entries that match
 * registered remote criterion types (vocabulary, tag, …). Each entry pairs
 * the matching `RemoteCriterionType` with the id and human-readable name
 * pulled from the criterion's inner items.
 */
export const extractRemoteCriterionEntries = (
	criteria: any
): ExtractedRemoteCriterionEntry[] => {
	if (!criteria) {
		return [];
	}

	if (criteria.items) {
		return criteria.items.flatMap(extractRemoteCriterionEntries);
	}

	const criterionType = getRemoteCriterionTypeByOperator(
		criteria.operatorName
	);

	if (!criterionType || !criteria.propertyName) {
		return [];
	}

	const id = criteria.propertyName as string;
	const items = criteria.value?.getIn?.(['criterionGroup', 'items']);
	const nameItem = items?.find?.(
		(item: any) => item.get?.('propertyName') === criterionType.nameProperty
	);
	const name = (nameItem?.get?.('value') as string) ?? id;

	return [{criterionType, id, name}];
};
