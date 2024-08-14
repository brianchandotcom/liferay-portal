/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {Node} from 'react-flow-renderer';

import {checkPostalAddressUnsupportedObjectRelationship} from '../../components/ModelBuilder/utils';

interface BuildObjectDefinitionNodeMockProps {
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionName: string;
	objectRelationships?: ObjectRelationship[];
}

const objectRelationshipMock = {
	deletionType: 'prevent',
	externalReferenceCode: '',
	id: 33837,
	label: {
		en_US: 'object relationship',
	},
	name: 'objectRelationship',
	objectDefinitionExternalReferenceCode1: 'L_ACCOUNT',
	objectDefinitionExternalReferenceCode2: 'customObjectDefinitionERC',
	objectDefinitionId1: 31670,
	objectDefinitionId2: 33563,
	objectDefinitionModifiable2: true,
	objectDefinitionName2: 'customObject',
	objectDefinitionSystem2: false,
	parameterObjectFieldId: 0,
	parameterObjectFieldName: '',
	reverse: false,
	system: false,
	type: 'oneToMany',
} as ObjectRelationship;

function buildObjectDefinitionNodeMock({
	objectDefinitionExternalReferenceCode,
	objectDefinitionName,
	objectRelationships,
}: BuildObjectDefinitionNodeMockProps): Node<ObjectDefinitionNodeData> {
	return {
		data: {
			accountEntryRestricted: false,
			accountEntryRestrictedObjectFieldId: '',
			accountEntryRestrictedObjectFieldName: '',
			actions: {},
			active: true,
			dateCreated: '',
			dateModified: '',
			dbTableName: '',
			defaultLanguageId: 'en_US',
			enableCategorization: true,
			enableComments: false,
			enableIndexSearch: false,
			enableLocalization: false,
			enableObjectEntryDraft: false,
			enableObjectEntryHistory: false,
			externalReferenceCode: objectDefinitionExternalReferenceCode,
			hasObjectDefinitionDeleteResourcePermission: false,
			hasObjectDefinitionManagePermissionsResourcePermission: true,
			hasObjectDefinitionUpdateResourcePermission: true,
			hasObjectDefinitionViewResourcePermission: true,
			id: 31201,
			label: {
				en_US: '',
			},
			linkedObjectDefinition: false,
			modifiable: true,
			name: objectDefinitionName,
			objectActions: [],
			objectFields: [],
			objectFolderExternalReferenceCode: 'default',
			objectLayouts: [],
			objectRelationships: objectRelationships ?? [],
			objectViews: [],
			panelCategoryKey: 'site_administration.content',
			parameterRequired: false,
			pluralLabel: {
				en_US: '',
			},
			portlet: false,
			restContextPath: '',
			rootObjectDefinitionExternalReferenceCode: '',
			scope: 'site',
			selected: false,
			showAllObjectFields: false,
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Approved',
			},
			system: true,
			titleObjectFieldId: 1233,
			titleObjectFieldName: 'name',
		},
		id: '31201',
		position: {
			x: 260,
			y: 550,
		},
		type: 'objectDefinitionNode',
	};
}

describe('checkPostalAddressUnsupportedObjectRelationship function', () => {
	it('returns true if the target node name is Address', () => {
		const customObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'customObjectDefinitionERC',
			objectDefinitionName: 'customObject',
		});

		const postalAddressObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'L_POSTAL_ADDRESS',
			objectDefinitionName: 'Address',
		});

		const unsupportedObjectRelationship =
			checkPostalAddressUnsupportedObjectRelationship(
				[customObjectDefinition, postalAddressObjectDefinition],
				customObjectDefinition,
				postalAddressObjectDefinition
			);

		expect(unsupportedObjectRelationship).toBe(true);
	});

	it('returns true if the source node name is Address, but the target node does not have a relationship with the Account object', () => {
		const accountObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'L_ACCOUNT',
			objectDefinitionName: 'AccountEntry',
		});

		const customObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'customObjectDefinitionERC',
			objectDefinitionName: 'customObject',
		});

		const postalAddressObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'L_POSTAL_ADDRESS',
			objectDefinitionName: 'Address',
		});

		const unsupportedObjectRelationship =
			checkPostalAddressUnsupportedObjectRelationship(
				[
					accountObjectDefinition,
					customObjectDefinition,
					postalAddressObjectDefinition,
				],
				postalAddressObjectDefinition,
				customObjectDefinition
			);

		expect(unsupportedObjectRelationship).toBe(true);
	});

	it('returns false if the source node name is Address but the target node has a relationship with the Account object', () => {
		const accountObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'L_ACCOUNT',
			objectDefinitionName: 'AccountEntry',
			objectRelationships: [objectRelationshipMock],
		});

		const customObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'customObjectDefinitionERC',
			objectDefinitionName: 'customObject',
		});

		const postalAddressObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'L_POSTAL_ADDRESS',
			objectDefinitionName: 'Address',
		});

		const unsupportedObjectRelationship =
			checkPostalAddressUnsupportedObjectRelationship(
				[
					accountObjectDefinition,
					customObjectDefinition,
					postalAddressObjectDefinition,
				],
				postalAddressObjectDefinition,
				customObjectDefinition
			);

		expect(unsupportedObjectRelationship).toBe(false);
	});

	it('returns false if the source and target nodes are custom objects', () => {
		const customObjectDefinition = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'customObjectDefinitionERC',
			objectDefinitionName: 'customObject',
		});

		const customObjectDefinition2 = buildObjectDefinitionNodeMock({
			objectDefinitionExternalReferenceCode: 'customObjectDefinitionERC2',
			objectDefinitionName: 'customObject2',
		});

		const unsupportedObjectRelationship =
			checkPostalAddressUnsupportedObjectRelationship(
				[customObjectDefinition, customObjectDefinition2],
				customObjectDefinition,
				customObjectDefinition2
			);

		expect(unsupportedObjectRelationship).toBe(false);
	});
});
