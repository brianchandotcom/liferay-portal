/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getItemField} from '../../src/main/resources/META-INF/resources/utils/getItemField';

const itemWithAudit = {
	actions: {
		delete: {
			href: 'http://localhost:8080/o/c/customobjects/38884',
			method: 'DELETE',
		},
		get: {
			href: 'http://localhost:8080/o/c/customobjects/38884',
			method: 'GET',
		},
		permissions: {
			href: 'http://localhost:8080/o/c/customobjects/38884/permissions',
			method: 'GET',
		},
		replace: {
			href: 'http://localhost:8080/o/c/customobjects/38884',
			method: 'PUT',
		},
		update: {
			href: 'http://localhost:8080/o/c/customobjects/38884',
			method: 'PATCH',
		},
	},
	auditEvents: [
		{
			auditFieldChanges: [
				{
					name: 'title',
					newValue: 'The first one',
					oldValue: 'First one',
				},
			],
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				image: '/image/user_portrait?img_id=38678&img_id_token=7NJsMSX85wO4BAtets1FsmiCKXE%3D&t=1713281378882',
				name: 'Test Test',
				userGroupBriefs: [
					{
						id: 123,
						name: 'A team name',
					},
				],
			},
			dateCreated: '2024-04-16T13:50:57Z',
			eventType: 'UPDATE',
		},
		{
			auditFieldChanges: [
				{
					name: 'description',
					newValue: 'My custom object is custom',
					oldValue: 'My custom object',
				},
			],
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				image: '/image/user_portrait?img_id=38678&img_id_token=7NJsMSX85wO4BAtets1FsmiCKXE%3D&t=1713281378882',
				name: 'Test Test',
				userGroupBriefs: [],
			},
			dateCreated: '2024-04-16T13:50:41Z',
			eventType: 'UPDATE',
		},
		{
			auditFieldChanges: [
				{
					name: 'image',
					newValue: {
						dlFileEntryId: 39346,
						title: 'trooper',
					},
					oldValue: {
						dlFileEntryId: 39312,
						title: 'rooper.png',
					},
				},
			],
			creator: {
				additionalName: '',
				contentType: 'UserAccount',
				familyName: 'Test',
				givenName: 'Test',
				id: 20122,
				image: '/image/user_portrait?img_id=38678&img_id_token=7NJsMSX85wO4BAtets1FsmiCKXE%3D&t=1713281378882',
				name: 'Test Test',
				userGroupBriefs: [],
			},
			dateCreated: '2024-04-16T13:50:03Z',
			eventType: 'UPDATE',
		},
	],
	creator: {
		additionalName: '',
		contentType: 'UserAccount',
		familyName: 'Test',
		givenName: 'Test',
		id: 20122,
		image: '/image/user_portrait?img_id=38678&img_id_token=7NJsMSX85wO4BAtets1FsmiCKXE%3D&t=1713281378882',
		name: 'Test Test',
	},
	dateCreated: '2024-04-12T08:28:45Z',
	dateModified: '2024-04-16T13:50:57Z',
	description: 'My custom object is custom',
	description_i18n: {
		en_US: 'My custom object is custom',
	},
	externalReferenceCode: '0b31f896-94e4-8519-ca5b-c74bb374036d',
	id: 38884,
	image: {
		id: 39346,
		link: {
			href: '/documents/20119/0/trooper+%281%29.png/a0f947f9-68c8-2410-c6eb-85de1aa70b4f?version=1.0&t=1713171423854&download=true&objectDefinitionExternalReferenceCode=aab85379-959d-b246-0907-e723df904044&objectEntryExternalReferenceCode=0b31f896-94e4-8519-ca5b-c74bb374036d',
			label: 'trooper (1).png',
		},
		name: 'trooper (1).png',
	},
	keywords: ['array', 'list', 'collection'],
	list: [
		{
			key: 'one',
			name: 'One',
		},
		{
			key: 'two',
			name: 'Two',
		},
	],
	nested: {
		object: {
			property: 'hello',
		},
	},
	status: {
		code: 0,
		label: 'approved',
		label_i18n: 'Approved',
	},
	taxonomyCategoryBriefs: [],
	title: 'The first one',
	title_i18n: {
		en_US: 'The first one',
	},
};

describe('resolveField', () => {
	it('is defined', () => {
		expect(getItemField).toBeDefined();
	});

	describe('single property request', () => {
		it('returns an object containing the name of the field requested, the item itself and the root property of the object requested', () => {
			const given = 'title';

			const expected = itemWithAudit;

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});
	});

	describe('simple nested property request', () => {
		it('returns the requested object property as resolvedItem', () => {
			const given = 'creator.name';

			const expected = itemWithAudit.creator;

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});

		it('returns the parent object when using a wildcard', () => {
			const given = 'creator.*';

			const expected = itemWithAudit;

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});

		it('returns the parent of the deepest object property requested', () => {
			const given = 'nested.object.property';

			const expected = itemWithAudit.nested.object;

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});
	});

	describe('scalar arrays', () => {
		it('returns the contents of the selected array', () => {
			const given = 'keywords';

			const expected = itemWithAudit;

			// NOTE: We are requesting a field of type scalar Array. But it will be treated as
			// if it was a single property request
			// it will be resolved as itemWithAudit.keywords -> ['array', 'list', 'collection']
			// by getLocalizedValue

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});
	});

	describe('single field from a complex arrays', () => {
		it('returns a collection of items with the specified property key', () => {
			const given = 'list[]key';

			const expected = [{key: 'one'}, {key: 'two'}];

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});
	});

	describe('Extra complex cases', () => {
		it('parses selected object properties inside an array', () => {
			const given = 'auditEvents[]creator.name';

			const expected = [
				{name: 'Test Test'},
				{name: 'Test Test'},
				{name: 'Test Test'},
			];

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});

		it('parses selected object properties inside a nested array', () => {
			const given = 'auditEvents[]creator.userGroupBriefs[]name';

			const expected = [{name: 'A team name'}];

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});

		it('does not fail when no property matches the path', () => {
			const given = 'auditEvents[]creator.randomProperty[]name';

			const expected = [
				{name: undefined},
				{name: undefined},
				{name: undefined},
			];

			expect(getItemField(given, itemWithAudit)).toEqual(expected);
		});
	});
});
