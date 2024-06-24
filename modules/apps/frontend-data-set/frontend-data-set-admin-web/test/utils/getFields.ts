/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	INVALID_FIELDS,
	getValidFields, ISchemas,
} from '../../src/main/resources/META-INF/resources/js/utils/getFields';
import {IField} from '../../src/main/resources/META-INF/resources/js/utils/types';

/*
 * A points to B using scalar field via $ref
 * A points to C using scalar field via 'x-parent-map' and implied target schema name
 * B points to C using scalar field via $ref
 * C points to A using array field via $ref
 */
const nestedSchemas : ISchemas = {
	A: {
		properties: {
			a_b: {
				$ref: '#/components/schemas/B',
				extensions: {
					'x-parent-map': 'properties',
				},
				type: 'object',
			},
			c: {
				extensions: {
					'x-parent-map': 'properties',
				},
				type: 'string',
			},
		},
		type: 'object',
	},
	B: {
		properties: {
			b_c: {
				$ref: '#/components/schemas/C',
				type: 'object',
			},
		},
		type: 'object',
	},
	C: {
		properties: {
			c_a: {
				items: {
					$ref: '#/components/schemas/A',
				},
				type: 'array',
			},
		},
		type: 'object',
	},
} as ISchemas;

const simpleSchema = {
	Simple: {
		properties: {
			'code': {
				format: 'int32',
				type: 'integer',
			},
			'label': {
				type: 'string',
			},
			'label_i18n': {
				additionalProperties: {
					type: 'string',
				},
				type: 'object',
			},
			'old_codes': {
				items: {
					format: 'int32',
					type: 'integer',
				},
				type: 'array',
			},
			'userNames': {
				additionalProperties: {
					type: 'string',
				},
				type: 'object',
			},
			'x-class-name': {
				default: 'com.liferay.object.rest.dto.v1_0.Simple',
				type: 'string',
			},
		},
		type: 'object',
	},
} as ISchemas;

function assertChildren(f: IField, childrenNames: string[]) {
	if (childrenNames.length) {
		expect(f.children).toBeDefined();
		expect(f.children?.length).toEqual(childrenNames.length);
		childrenNames.forEach((childName, index) =>
			expect(f.children[index]?.name).toEqual(childName)
		);
	}
	else {
		expect(f).not.toHaveProperty('children');
	}
}

describe('getValidFields', () => {
	it('Function is defined', () => {
		expect(getValidFields).toBeDefined();
	});

	it('Returns only valid fields from the schemas of an application', () => {
		const result = getValidFields({
			contextPath: '',
			schemaName: 'Simple',
			schemaStack: [],
			schemas: simpleSchema,
		});

		const expectedValidFields = result.map((item) => item.label);

		expect(Object.keys(simpleSchema.Simple.properties)).toEqual([
			'code',
			'label',
			'label_i18n',
			'old_codes',
			'userNames',
			'x-class-name',
		]);

		expect(expectedValidFields).toEqual([
			'code',
			'label',
			'old_codes',
			'userNames',
		]);

		expect(expectedValidFields).not.toContain('label_i18n');

		expect(expectedValidFields).not.toContain(INVALID_FIELDS);
	});

	it('Non scalar (arrays and object) fields are not sortable, scalar fields are', () => {
		const result = getValidFields({
			contextPath: '',
			schemaName: 'Simple',
			schemaStack: [],
			schemas: simpleSchema,
		});

		const expectedValidScalarFields = result.filter(
			(item) => item.type !== 'object' && item.type !== 'array'
		);

		expect(expectedValidScalarFields.map((item) => item.sortable)).toEqual([
			true,
			true,
		]);

		const expectedValidNonScalarFields = result.filter(
			(item) => item.type === 'object' || item.type === 'array'
		);

		expect(
			expectedValidNonScalarFields.map((item) => item.sortable)
		).toEqual([false, false]);
	});

	it('Include children properties from schemas referenced via $ref property, no loops', () => {
		const result = getValidFields({
			contextPath: '',
			schemaName: 'A',
			schemaStack: [],
			schemas: nestedSchemas,
		});

		/* Excerpt of tree for field a_b:
			{
				name: "a_b.*",
				children:[
					{
						name: "a_b.*b_c.*",
						children:[
							{
								name: "a_b.*b_c.*c_a[]*",
								children:[
									{
										name: "a_b.*b_c.*c_a[]*a_b.*",
										// no children, B schema already visited!
									},
									{
										name: "a_b.*b_c.*c_a[]*c.*",
										// no children, C schema already visited!
									}
								]
							}
						]
					}
				]
			};
		 */

		const a_b = result.find((item) => item.label === 'a_b');
		expect(a_b.name).toEqual('a_b.*');
		assertChildren(a_b, ['a_b.*b_c.*']);

		const b_c = a_b.children[0];
		assertChildren(b_c, ['a_b.*b_c.*c_a[]*']);

		const c_a = b_c.children[0];
		assertChildren(c_a, ['a_b.*b_c.*c_a[]*a_b.*', 'a_b.*b_c.*c_a[]*c.*']);

		// no loops

		assertChildren(c_a.children[0], []);
		assertChildren(c_a.children[1], []);
	});

	it('Include children properties from schemas referenced via x-map-properties property, no loops', () => {
		const result = getValidFields({
			contextPath: '',
			schemaName: 'A',
			schemaStack: [],
			schemas: nestedSchemas,
		});

		/* Excerpt of tree for field c
			{
				name: "c.*",
				children: [
					{
						name: "c.*c_a[]*",
						children:[
							{
								name: "c.*c_a[]*a_b.*",
								children:[
									{
										name: "c.*c_a[]*a_b.*b_c.*",
										// no children, C schema already visited!
									}
								]
							},
							{
								name: "c.*c_a[]*c.*",
								// no children, C schema already visited!
							}
						]
					}
				]
			};
		*/

		const c = result.find((item) => item.label === 'c');
		expect(c.name).toEqual('c.*');
		assertChildren(c, ['c.*c_a[]*']);

		const c_a = c.children[0];
		assertChildren(c_a, ['c.*c_a[]*a_b.*', 'c.*c_a[]*c.*']);

		const a_b = c_a.children[0];
		assertChildren(a_b, ['c.*c_a[]*a_b.*b_c.*']);

		// no loops

		assertChildren(a_b.children[0], []);
		assertChildren(c_a.children[1], []);
	});
});
