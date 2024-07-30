import {
	Attribute,
	AttributeOwnerTypes,
	AttributeTypes,
	DataTypes
} from 'event-analysis/utils/types';

const IndividualAttributes = [
	{
		dataType: DataTypes.String,
		displayName: 'jobTitle',
		id: 'jobTitle',
		name: 'jobTitle',
		type: AttributeTypes.Global
	},
	{
		dataType: DataTypes.String,
		displayName: 'languageId',
		id: 'languageId',
		name: 'languageId',
		type: AttributeTypes.Global
	},
	{
		dataType: DataTypes.String,
		displayName: Liferay.Language.get('role'),
		id: 'role',
		name: 'role',
		type: AttributeTypes.Local
	},
	{
		dataType: DataTypes.String,
		displayName: Liferay.Language.get('site-membership'),
		id: 'group',
		name: 'group',
		type: AttributeTypes.Local
	},
	{
		dataType: DataTypes.String,
		displayName: Liferay.Language.get('team'),
		id: 'team',
		name: 'team',
		type: AttributeTypes.Local
	},
	{
		dataType: DataTypes.String,
		displayName: Liferay.Language.get('user-group'),
		id: 'userGroup',
		name: 'userGroup',
		type: AttributeTypes.Local
	}
];

export function getModifiedEventAttributeDefinitions({
	attribute,
	attributeOwnerType,
	eventAttributeDefinitions
}: {
	attribute: Attribute;
	attributeOwnerType: AttributeOwnerTypes;
	eventAttributeDefinitions: Attribute[];
}): Attribute[] {
	let modifiedEventAttributeDefinitions = [];

	if (attributeOwnerType === AttributeOwnerTypes.Event) {
		modifiedEventAttributeDefinitions = attribute
			? eventAttributeDefinitions.map(eventAttributeDefinition => {
					if (attribute.id === eventAttributeDefinition.id) {
						return attribute;
					}

					return eventAttributeDefinition;
			  })
			: eventAttributeDefinitions;
	} else if (attributeOwnerType === AttributeOwnerTypes.Individual) {
		modifiedEventAttributeDefinitions = IndividualAttributes;
	}

	return modifiedEventAttributeDefinitions;
}

export const getTabs = (
	setAttributeOwnerType: (type: AttributeOwnerTypes) => void
) => [
	{
		onClick: () => setAttributeOwnerType(AttributeOwnerTypes.Event),
		tabId: AttributeOwnerTypes.Event,
		title: Liferay.Language.get('event')
	},
	{
		onClick: () => setAttributeOwnerType(AttributeOwnerTypes.Individual),
		tabId: AttributeOwnerTypes.Individual,
		title: Liferay.Language.get('individual')
	}
];
