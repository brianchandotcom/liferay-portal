/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const fs = require('fs');
const path = require('path');

describe('liferay-kaleo-designer-xml-definition', () => {
	beforeEach(done => {
		const add = AUI.add;

		AUI.add = function(name, callback, version, metadata) {
			add.call(AUI, name, callback, version, {
				...metadata,
				requires: metadata.requires.filter(
					name => name !== 'aui-component'
				)
			});
		};

		require('../../../src/main/resources/META-INF/resources/designer/js/xml_definition');

		AUI.add = add;

		require('../../../src/main/resources/META-INF/resources/designer/js/utils');

		AUI().use(['liferay-kaleo-designer-utils'], A => {
			// Stub for "aui-component", which refuses to load in test env.
			A.Component = {
				create({ATTRS, prototype, ...properties}) {
					const constructor = function(config) {
						this.initializer(config);
					};

					Object.assign(
						constructor.prototype,
						prototype,
						properties,
						{
							set(key, value) {
								ATTRS[key].value = value;
							},

							setAttrs(attrs) {
								Object.entries(attrs).forEach(
									([key, value]) => {
										ATTRS[key].value = value;
									}
								);
							}
						}
					);

					return constructor;
				}
			};

			AUI().use(['liferay-kaleo-designer-xml-definition'], () => {
				done();
			});
		});
	});

	describe('getDefinitionMetadata()', () => {
		it('has a name', () => {
			const definition = loadResource('metadata-only-definition.xml');

			const xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
				value: definition
			});

			const metadata = xmlDefinition.getDefinitionMetadata();

			expect(metadata.description).toBe('It only has metadata');
			expect(metadata.name).toBe('Metadata Only');
			expect(metadata.version).toBe('42');
		});
	});

	describe('forEachField()', () => {
		it('retrieves "receptionType" attribute value', () => {
			const definition = loadResource(
				'recipients-with-reception-type-bcc-definition.xml'
			);

			const xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
				value: definition
			});

			// Jest's jsdom's XMLDocument evaluate() implementation
			// isn't identical to the browser, causing the
			// `@receptionType` XPath locator to fail to find the
			// corresponding attribute below; by clobbering the jsdom
			// implementation, we force the dataschema-xml module to
			// fallback to a compatibility path that does work:
			//
			//      https://github.com/yui/yui3/blob/25264e3629/src/dataschema/js/dataschema-xml.js#L182
			//
			xmlDefinition.definitionDoc.evaluate = undefined;

			xmlDefinition.forEachField((_tagName, fieldData) => {
				const result = fieldData.results[0];

				const notification = result.notifications[0];

				const recipient = notification.recipients[0];

				expect(recipient.receptionType).toBe('bcc');
			});
		});

		it('does not have a "receptionType" if not present in definition', () => {
			const definition = loadResource(
				'recipients-with-no-reception-type-definition.xml'
			);

			const xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
				value: definition
			});

			xmlDefinition.forEachField((_tagName, fieldData) => {
				const result = fieldData.results[0];

				const notification = result.notifications[0];

				const recipient = notification.recipients[0];

				expect(recipient.receptionType).toBe(null);
			});
		});

		it('has "users" as recipient', () => {
			const definition = loadResource(
				'recipients-with-user-definition.xml'
			);

			const xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
				value: definition
			});

			xmlDefinition.forEachField((_tagName, fieldData) => {
				const result = fieldData.results[0];

				const notification = result.notifications[0];

				const recipient = notification.recipients[0];

				expect(recipient.user.length).toBe(1);
			});
		});

		it('has "assignees" as recipient', () => {
			const definition = loadResource(
				'recipients-with-assignees-definition.xml'
			);

			const xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
				value: definition
			});

			xmlDefinition.forEachField((_tagName, fieldData) => {
				const result = fieldData.results[0];

				const notification = result.notifications[0];

				const recipient = notification.recipients[0];

				expect(recipient.taskAssignees).not.toBe(null);
			});
		});
	});
});

function loadResource(name) {
	return fs.readFileSync(path.join(__dirname, 'resources', name), 'utf8');
}
