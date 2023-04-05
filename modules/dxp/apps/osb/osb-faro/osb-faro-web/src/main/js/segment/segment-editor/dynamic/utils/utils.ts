import dateFns from 'date-fns';
import {
	Conjunctions,
	CustomFunctionOperators,
	isKnown,
	isUnknown,
	NotOperators,
	PropertyTypes,
	SUPPORTED_OPERATORS_MAP
} from './constants';
import {Criteria, Criterion, CriterionGroup, Operator} from './types';
import {Event} from 'event-analysis/utils/types';
import {every, isBoolean, isString, isUndefined} from 'lodash';
import {FieldContexts, FieldOwnerTypes} from 'shared/util/constants';
import {fromJS, Map} from 'immutable';
import {getUid} from 'metal';
import {
	INDIVIDUAL_PROPERTIES,
	ORGANIZATION_PROPERTIES,
	SESSION_PROPERTIES,
	WEB_BEHAVIORS
} from '../utils/properties';
import {Property} from 'shared/util/records';

const GROUP_ID_NAMESPACE = 'group_';
const ROW_ID_NAMESPACE = 'row_';

export const createInterestProperty = (name: string): Property =>
	new Property({
		entityName: Liferay.Language.get('individual'),
		label: name,
		name,
		propertyKey: 'interest',
		type: PropertyTypes.Interest
	});

/**
 * Creates a new group object with items.
 */
export const createNewGroup = (items: Criteria[]): CriterionGroup => ({
	conjunctionName: Conjunctions.And,
	criteriaGroupId: generateGroupId(),
	items
});

/**
 * Generates a unique group id.
 */
export const generateGroupId = (): string => `${GROUP_ID_NAMESPACE}${getUid()}`;

/**
 * Generates a unique row id.
 */
export const generateRowId = (): string => `${ROW_ID_NAMESPACE}${getUid()}`;

/**
 * Gets a list of group ids from a criteria object.
 * Used for disallowing groups to be moved into its own deeper nested groups.
 * Example of returned value: ['group_02', 'group_03']
 */
export const getChildGroupIds = (criteria: Criteria): string[] => {
	let childGroupIds = [];

	if (isCriterionGroup(criteria) && criteria.items.length) {
		childGroupIds = criteria.items.reduce(
			(groupIdList, item) =>
				isCriterionGroup(item)
					? [
							...groupIdList,
							item.criteriaGroupId,
							...getChildGroupIds(item)
					  ]
					: groupIdList,
			[]
		);
	}

	return childGroupIds;
};

/**
 * Gets the property name from the propertyLabel string .
 */
export const getPropertyNameFromRaw = (propertyLabel: string = ''): string => {
	const properties = propertyLabel.split('/');

	return properties.length > 1 ? properties[1] : properties[0];
};

export const getPropertyContextFromRaw = (
	propertyLabel: string = ''
): string => {
	const properties = propertyLabel.split('/');

	return properties.length > 1 ? properties[0] : null;
};

/**
 * Gets the list of operators for a supported type.
 * Used for displaying the operators available for each criteria row.
 */
export const getSupportedOperatorsFromType = (type: string = ''): Operator[] =>
	SUPPORTED_OPERATORS_MAP[type.toLowerCase()] || [];

/**
 * Checks if value is a CriterionGroup.
 */
export const isCriterionGroup = (
	value: CriterionGroup | Criterion
): value is CriterionGroup =>
	!!value && (value as CriterionGroup).items !== undefined;

/**
 * Checks if value is an ImmutableMap
 */
export const isMap = (
	value: Map<string, any> | object
): value is Map<string, any> => Map.isMap(value as Map<string, any>);

/**
 * Checks if value is either isKnown or isUnknown.
 */
export const isOfKnownType = (key: string): boolean =>
	[isKnown, isUnknown].includes(key);

/**
 * Converts an object of key value pairs to a form data object for passing
 * into a fetch body.
 */
export const objectToFormData = (dataObject: object): FormData => {
	const formData = new FormData();

	Object.keys(dataObject).forEach(key => {
		formData.set(key, dataObject[key]);
	});

	return formData;
};

/**
 * Parse an activityKey string into an object.
 */
export const parseActivityKey = (
	activityKey: string = ''
): {eventId: string; id: string; objectType: string} => {
	const [objectType, eventId, id] = activityKey.split('#');

	return {eventId, id, objectType};
};

/**
 * Returns a YYYY-MM-DD date
 * based on a JS Date object
 *
 * @export
 */
export const jsDatetoYYYYMMDD = (dateJsObject: Date): string => {
	const DATE_FORMAT = 'YYYY-MM-DD';
	return dateFns.format(dateJsObject, DATE_FORMAT);
};

/**
 * Finds the matching property based on its Criterion.
 */
export const findPropertyByCriterion = (
	criterion: Criterion,
	referencedPropertiesIMap: Map<string, Map<string, Property>>
): Property => {
	const {operatorName, propertyName, type, value} = criterion;

	if (
		[
			CustomFunctionOperators.ActivitiesFilterByCount,
			NotOperators.NotActivitiesFilterByCount
		].includes(operatorName)
	) {
		const {eventId = propertyName} = parseActivityKey(
			(value as Map<string, any>).getIn(
				['criterionGroup', 'items', 0, 'value'],
				''
			)
		);

		return WEB_BEHAVIORS.find(({name}) => name === eventId);
	} else if (
		[
			CustomFunctionOperators.EventsFilterByCount,
			NotOperators.NotEventsFilterByCount
		].includes(operatorName)
	) {
		const eventId = value.getIn(
			['criterionGroup', 'items', 0, 'value'],
			''
		);

		return referencedPropertiesIMap.getIn(['event', eventId]);
	} else if (
		[
			CustomFunctionOperators.AccountsFilter,
			NotOperators.NotAccountsFilter
		].includes(operatorName)
	) {
		return referencedPropertiesIMap.getIn(
			[
				'account',
				getPropertyContextFromRaw(propertyName),
				getPropertyNameFromRaw(propertyName)
			],
			''
		);
	} else if (
		[
			NotOperators.NotOrganizationsFilter,
			CustomFunctionOperators.OrganizationsFilter
		].includes(operatorName)
	) {
		if (getPropertyContextFromRaw(propertyName) !== FieldContexts.Custom) {
			return ORGANIZATION_PROPERTIES.find(
				({name}) => name === propertyName
			);
		}

		return referencedPropertiesIMap.getIn(
			[
				'organization',
				getPropertyContextFromRaw(propertyName),
				getPropertyNameFromRaw(propertyName)
			],
			''
		);
	} else if (
		[
			CustomFunctionOperators.SessionsFilter,
			NotOperators.NotSessionsFilter
		].includes(operatorName) ||
		type === PropertyTypes.SessionDateTime
	) {
		return SESSION_PROPERTIES.find(({name}) => name === propertyName);
	} else if (operatorName === CustomFunctionOperators.InterestsFilter) {
		return createInterestProperty(propertyName);
	} else if (INDIVIDUAL_PROPERTIES.find(({name}) => name === propertyName)) {
		return INDIVIDUAL_PROPERTIES.find(({name}) => name === propertyName);
	} else {
		return referencedPropertiesIMap.getIn(
			[
				'individual',
				getPropertyContextFromRaw(propertyName),
				getPropertyNameFromRaw(propertyName)
			],
			''
		);
	}
};

export const convertFieldMappingToAccountProperty = (
	fieldMapping:
		| Map<string, any>
		| {
				context: string;
				displayName: string;
				id: string;
				name: string;
				rawType: string;
		  }
): Property => {
	const context = isMap(fieldMapping)
		? fieldMapping.get('context')
		: fieldMapping.context;
	const displayName = isMap(fieldMapping)
		? fieldMapping.get('displayName')
		: fieldMapping.displayName;
	const id = isMap(fieldMapping) ? fieldMapping.get('id') : fieldMapping.id;
	const name = isMap(fieldMapping)
		? fieldMapping.get('name')
		: fieldMapping.name;
	const type = isMap(fieldMapping)
		? fieldMapping.get('rawType')
		: fieldMapping.rawType;

	const CUSTOM_REGEX = /custom-/;

	return new Property({
		entityName: Liferay.Language.get('account'),
		id,
		label: displayName || name,
		name: context ? `${context}/${id}/value` : id,
		propertyKey: FieldOwnerTypes.Account,
		type: CUSTOM_REGEX.test(type)
			? type.toLowerCase()
			: `account-${type.toLowerCase()}`
	});
};

export const convertFieldMappingToIndividualProperty = (
	fieldMapping:
		| Map<string, any>
		| {
				context: string;
				displayName: string;
				id: string;
				name: string;
				ownerType: string;
				rawType: string;
				type: string;
		  }
): Property => {
	const context = isMap(fieldMapping)
		? fieldMapping.get('context')
		: fieldMapping.context;
	const displayName = isMap(fieldMapping)
		? fieldMapping.get('displayName')
		: fieldMapping.displayName;
	const id = isMap(fieldMapping) ? fieldMapping.get('id') : fieldMapping.id;
	const name = isMap(fieldMapping)
		? fieldMapping.get('name')
		: fieldMapping.name;
	const type = isMap(fieldMapping)
		? fieldMapping.get('rawType')
		: fieldMapping.rawType;

	return new Property({
		entityName: Liferay.Language.get('individual'),
		id,
		label: displayName || name,
		name: context ? `${context}/${id}/value` : id,
		propertyKey: FieldOwnerTypes.Individual,
		type: type.toLowerCase()
	});
};

export const convertFieldMappingToOrganizationProperty = (
	fieldMapping:
		| Map<string, any>
		| {
				context: string;
				displayName: string;
				id: string;
				name: string;
				ownerType: string;
				rawType: string;
				type: string;
		  }
): Property => {
	const context = isMap(fieldMapping)
		? fieldMapping.get('context')
		: fieldMapping.context;
	const displayName = isMap(fieldMapping)
		? fieldMapping.get('displayName')
		: fieldMapping.displayName;
	const id = isMap(fieldMapping) ? fieldMapping.get('id') : fieldMapping.id;
	const name = isMap(fieldMapping)
		? fieldMapping.get('name')
		: fieldMapping.name;
	const type = isMap(fieldMapping)
		? fieldMapping.get('rawType')
		: fieldMapping.rawType;

	return new Property({
		entityName: Liferay.Language.get('organization'),
		id,
		label: displayName || name,
		name: context ? `${context}/${id}/value` : id,
		propertyKey: FieldOwnerTypes.Organization,
		type: `organization-${type.toLowerCase()}` as PropertyTypes
	});
};

export const convertEventToProperty = (
	eventDefinition: Map<string, any> | Event = Map()
): Map<string, Map<string, Property>> => {
	const displayName = isMap(eventDefinition)
		? eventDefinition.get('displayName')
		: eventDefinition.displayName;
	const id = isMap(eventDefinition)
		? eventDefinition.get('id')
		: eventDefinition.id;
	const name = isMap(eventDefinition)
		? eventDefinition.get('name')
		: eventDefinition.name;

	return new Property({
		entityName: Liferay.Language.get('event'),
		id,
		label: displayName || name,
		name: id,
		propertyKey: 'event',
		type: PropertyTypes.Event
	});
};

export const convertFieldMappingsToProperties = (
	fieldMappingsIMap: Map<
		string,
		Map<string, Map<string, Map<string, any>>>
	> = Map()
): Map<string, Map<string, Map<string, Property>>> =>
	fieldMappingsIMap.map((ownerTypeGroup, key) => {
		let conversionFn;

		if (key === FieldOwnerTypes.Account) {
			conversionFn = convertFieldMappingToAccountProperty;
		} else if (key === FieldOwnerTypes.Individual) {
			conversionFn = convertFieldMappingToIndividualProperty;
		} else if (key === FieldOwnerTypes.Organization) {
			conversionFn = convertFieldMappingToOrganizationProperty;
		}

		if (conversionFn) {
			return ownerTypeGroup.map(contextGroup =>
				contextGroup.reduce(
					(acc, fieldMappingIMap, key) =>
						acc.set(key, conversionFn(fieldMappingIMap)),
					Map()
				)
			);
		}
	}) as Map<string, Map<string, Map<string, Property>>>;

export const convertReferencedObjectsToProperties = (
	referencedObjectsIMap: Map<
		string,
		Map<string, Map<string, Map<string, any>>>
	> = Map()
): Map<string, Map<string, Map<string, Property> | Property>> => {
	const fieldMappingProperties = convertFieldMappingsToProperties(
		referencedObjectsIMap.get('fieldMappings')
	);

	const eventProperties = referencedObjectsIMap
		.get('event', Map())
		.map(convertEventToProperty);

	return fieldMappingProperties.merge(fromJS({event: eventProperties}));
};

/**
 * Check to see if the value is a valid input value.
 * The input value cannot be an empty string or undefined.
 * @returns {boolean}
 */
export const isValid = (value: any): boolean =>
	!(isUndefined(value) || (isString(value) && !value.length));

/**
 * Recursively check through all criterions and invalidates those
 * that do not have a matching property
 */
export const invalidateCriterionWithMissingProperty = (
	criteria: Criteria,
	referencedPropertiesIMap: Map<string, Property>
) => {
	if (isCriterionGroup(criteria)) {
		const {items} = criteria;

		if (items.length) {
			return {
				...criteria,
				items: items.map(criterion =>
					invalidateCriterionWithMissingProperty(
						criterion,
						referencedPropertiesIMap
					)
				)
			};
		}
	} else {
		if (findPropertyByCriterion(criteria, referencedPropertiesIMap)) {
			return criteria;
		}

		return {
			...criteria,
			valid: isBoolean(criteria.valid)
				? false
				: Object.keys(criteria.valid).reduce(
						(acc, key) => ({...acc, [key]: false}),
						{}
				  )
		};
	}
};

/**
 * Recursively check through all criteria to see if they're valid.
 */
export const validateSegmentInputs = (criteria: Criteria): boolean => {
	if (isCriterionGroup(criteria)) {
		const {items} = criteria;

		if (items.length) {
			return items.map(validateSegmentInputs).every(Boolean);
		}
	} else if (criteria) {
		if (isBoolean(criteria.valid)) {
			return criteria.valid;
		}

		return every(criteria.valid, Boolean);
	}
};
