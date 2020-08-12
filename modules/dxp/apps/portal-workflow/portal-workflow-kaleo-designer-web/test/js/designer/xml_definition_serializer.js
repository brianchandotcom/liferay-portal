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

const METADATA = {
	name: 'definition',
	version: 1,
};

const XML_NAMESPACE = {
	xmlns: 'urn:liferay.com:liferay-workflow_7.3.0',
	'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
	'xsi:schemaLocation':
		'urn:liferay.com:liferay-workflow_7.3.0 http://www.liferay.com/dtd/liferay-workflow-definition_7_3_0.xsd',
};

describe('liferay-kaleo-designer-xml-definition-serializer', () => {
	let serializeDefinition;

	beforeEach((done) => {
		require('../../../src/main/resources/META-INF/resources/designer/js/xml_definition_serializer');
		require('../../../src/main/resources/META-INF/resources/designer/js/utils');
		require('../../../src/main/resources/META-INF/resources/designer/js/xml_util');
		require('../../../../../../../apps/frontend-js/frontend-js-aui-web/src/main/resources/META-INF/resources/liferay/xml_formatter');

		AUI().use(
			['liferay-kaleo-designer-xml-util', 'liferay-kaleo-designer-utils'],
			(A) => {

				// Stub for "aui-component", which refuses to load in test env.

				A.Component = {
					create({ATTRS, prototype, ...properties}) {
						const constructor = function () {};

						Object.assign(
							constructor.prototype,
							prototype,
							properties,
							{
								format(content) {
									return content;
								},

								get(key) {
									return ATTRS[key].value;
								},
							}
						);

						return constructor;
					},
				};

				AUI().use(
					['liferay-kaleo-designer-xml-definition-serializer'],
					() => {
						serializeDefinition =
							global.Liferay.KaleoDesignerXMLDefinitionSerializer;

						done();
					}
				);
			}
		);
	});

	it('does not serialize "receptionType" attribute if it has no value.', () => {
		const jsonDefinition = {
			nodes: [
				{
					name: 'task1',
					notifications: {
						name: ['notification1'],
						recipients: [
							{
								receptionType: [null],
							},
						],
					},
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).not.toContain('receptionType="');
	});

	it('does not serialize "receptionType" attribute if it has an empty string value.', () => {
		const jsonDefinition = {
			nodes: [
				{
					name: 'task1',
					notifications: {
						name: ['notification1'],
						recipients: [
							{
								receptionType: [''],
							},
						],
					},
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).not.toContain('receptionType="');
	});

	it('does not serialize <assignment> even when assignment object is empty.', () => {
		const jsonDefinition = {
			nodes: [
				{
					assignments: {},
					name: 'task1',
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).toContain('<assignments');
	});

	it('serializes "receptionType" attribute.', () => {
		const jsonDefinition = {
			nodes: [
				{
					name: 'task1',
					notifications: {
						name: ['notification1'],
						recipients: [
							{
								receptionType: 'bcc',
							},
						],
					},
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).toContain('receptionType="bcc"');
	});

	it('does not serialize <user> element if given.', () => {
		const jsonDefinition = {
			nodes: [
				{
					name: 'task1',
					notifications: {
						name: ['notification1'],
						recipients: [
							{
								assignmentType: ['user'],
								emailAddress: [null],
								screenName: [null],
								userId: [null],
							},
						],
					},
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).toContain('<user');
	});

	it('serialize <screen-name> element if given.', () => {
		const jsonDefinition = {
			nodes: [
				{
					name: 'task1',
					notifications: {
						name: ['notification1'],
						recipients: [
							{
								assignmentType: ['user'],
								emailAddress: [null],
								screenName: ['test'],
								userId: [null],
							},
						],
					},
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).toContain('<screen-name>test</screen-name>');
	});

	it('serialize <email-address>, <screen-name> and <user-id> elements if given.', () => {
		const jsonDefinition = {
			nodes: [
				{
					name: 'task1',
					notifications: {
						name: ['notification1'],
						recipients: [
							{
								assignmentType: ['user'],
								emailAddress: ['test@liferay.com'],
								screenName: ['test'],
								userId: ['0'],
							},
						],
					},
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).toContain(
			'<email-address>test@liferay.com</email-address>'
		);
		expect(definition).toContain('<screen-name>test</screen-name>');
		expect(definition).toContain('<user-id>0</user-id>');
	});

	it('serializes <user> element even if empty.', () => {
		const jsonDefinition = {
			nodes: [
				{
					name: 'task1',
					notifications: {
						name: ['notification1'],
						recipients: [
							{
								assignmentType: ['user'],
							},
						],
					},
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).toContain('<user');
	});

	it('serializes <assignment> element if given.', () => {
		const jsonDefinition = {
			nodes: [
				{
					assignments: {
						assignmentType: 'taskAssignees',
					},
					name: 'task1',
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).toContain('<assignments');
	});

	it('serializes template element and keep format.', () => {
		const jsonDefinition = {
			nodes: [
				{
					name: 'task1',
					notifications: {
						name: ['notification1'],
						recipients: [
							{
								assignmentType: ['user'],
							},
						],
						template: [
							'Your submission was reviewed<#if taskComments?has_content> and the reviewer applied the following ${taskComments}</#if>.\nThank You!',
						],
					},
					xmlType: 'task',
				},
			],
		};

		const definition = serializeDefinition(
			XML_NAMESPACE,
			METADATA,
			jsonDefinition
		);

		expect(definition).toContain(
			'<![CDATA[Your submission was reviewed<#if taskComments?has_content> and the reviewer applied the following ${taskComments}</#if>.\nThank You!]]'
		);
	});
});
