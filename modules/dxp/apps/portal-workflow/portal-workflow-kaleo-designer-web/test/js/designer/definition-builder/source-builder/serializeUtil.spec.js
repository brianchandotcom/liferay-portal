/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs';
import path from 'path';
import {isEdge, isNode} from 'react-flow-renderer';

import DeserializeUtil from '../../../../../src/main/resources/META-INF/resources/designer/js/definition-builder/source-builder/deserializeUtil';
import {serializeDefinition} from '../../../../../src/main/resources/META-INF/resources/designer/js/definition-builder/source-builder/serializeUtil';

const serializeFixture = (fileName) => {
	const deserializeUtil = new DeserializeUtil(
		fs.readFileSync(
			path.join(__dirname, `../../../../dependencies/${fileName}`),
			'utf8'
		)
	);

	const elements = deserializeUtil.getElements();

	return serializeDefinition(
		{
			'xmlns': 'urn:liferay.com:liferay-workflow_7.4.0',
			'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
		},
		{description: '', name: 'definition', version: 1},
		elements.filter(isNode),
		elements.filter(isEdge)
	).replace(/\s/g, '');
};

describe('Serializing roles that carry an external reference code', () => {
	it('Writes the external reference code of every role', () => {
		const deserializeUtil = new DeserializeUtil(
			fs.readFileSync(
				path.join(
					__dirname,
					'../../../../dependencies/roles-with-external-reference-codes-workflow-definition.xml'
				),
				'utf8'
			)
		);

		const elements = deserializeUtil.getElements();

		const xmlDefinition = serializeDefinition(
			{
				'xmlns': 'urn:liferay.com:liferay-workflow_7.4.0',
				'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
			},
			{description: '', name: 'definition', version: 1},
			elements.filter(isNode),
			elements.filter(isEdge)
		);

		expect(xmlDefinition).toContain(
			'<role-external-reference-code>ROLE1_ERC</role-external-reference-code>'
		);
		expect(xmlDefinition).toContain(
			'<role-external-reference-code>ROLE2_ERC</role-external-reference-code>'
		);
		expect(xmlDefinition).toContain(
			'<role-external-reference-code>ROLE3_ERC</role-external-reference-code>'
		);
		expect(xmlDefinition).toContain(
			'<role-external-reference-code>ROLE4_ERC</role-external-reference-code>'
		);
	});
});

describe('Serializing users that carry an external reference code', () => {
	it('Writes the external reference code of every user', () => {
		const deserializeUtil = new DeserializeUtil(
			fs.readFileSync(
				path.join(
					__dirname,
					'../../../../dependencies/users-with-external-reference-codes-workflow-definition.xml'
				),
				'utf8'
			)
		);

		const elements = deserializeUtil.getElements();

		const xmlDefinition = serializeDefinition(
			{
				'xmlns': 'urn:liferay.com:liferay-workflow_7.4.0',
				'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
			},
			{description: '', name: 'definition', version: 1},
			elements.filter(isNode),
			elements.filter(isEdge)
		);

		expect(xmlDefinition).toContain(
			'<user-external-reference-code>USER1_ERC</user-external-reference-code>'
		);
		expect(xmlDefinition).toContain(
			'<user-external-reference-code>USER2_ERC</user-external-reference-code>'
		);
		expect(xmlDefinition).toContain(
			'<user-external-reference-code>USER3_ERC</user-external-reference-code>'
		);
		expect(xmlDefinition).toContain(
			'<user-external-reference-code>USER4_ERC</user-external-reference-code>'
		);
	});
});

describe('Serializing users that carry an external reference code and users that do not', () => {
	it('Writes each external reference code next to its own user ID', () => {
		const xmlDefinition = serializeFixture(
			'users-with-and-without-external-reference-codes-workflow-definition.xml'
		);

		[
			['USER1_ERC', '1'],
			['USER2_ERC', '2'],
			['USER3_ERC', '3'],
			['USER4_ERC', '4'],
		].forEach(([externalReferenceCode, userId]) => {
			expect(xmlDefinition).toContain(
				`<user><user-external-reference-code>${externalReferenceCode}</user-external-reference-code><user-id>${userId}</user-id></user><user><user-id>${userId}0</user-id></user>`
			);
		});
	});
});
