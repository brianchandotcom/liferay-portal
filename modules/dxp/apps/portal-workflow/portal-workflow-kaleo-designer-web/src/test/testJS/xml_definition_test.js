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

'use strict';

describe('Liferay.KaleoDesignerXMLDefinition', () => {
	before(done => {
		AUI().use('liferay-kaleo-designer-xml-definition', () => {
			done();
		});
	});

	describe('unit', () => {
		describe('.getDefinitionMetadata()', () => {
			it('test should have name', done => {
				Liferay.Test.loadResource('metadata-only-definition.xml').then(
					definition => {
						var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
							{
								value: definition
							}
						);

						var metadata = xmlDefinition.getDefinitionMetadata();

						assert.equal(
							metadata.description,
							'It only has metadata'
						);
						assert.equal(metadata.name, 'Metadata Only');
						assert.equal(metadata.version, 42);

						done();
					}
				);
			});
		});
	});

	describe('regression', () => {
		describe('.forEachField()', () => {
			it('test should retrieve "receptionType" attribute value', done => {
				Liferay.Test.loadResource(
					'recipients-with-reception-type-bcc-definition.xml'
				).then(definition => {
					var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
						value: definition
					});

					xmlDefinition.forEachField((tagName, fieldData) => {
						var result = fieldData.results[0];

						var notification = result.notifications[0];

						var recipient = notification.recipients[0];

						assert.equal(recipient.receptionType, 'bcc');
					});

					done();
				});
			});

			it('test should not have a "receptionType" attribute if not present in definition', done => {
				Liferay.Test.loadResource(
					'recipients-with-no-reception-type-definition.xml'
				).then(definition => {
					var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
						value: definition
					});

					xmlDefinition.forEachField((tagName, fieldData) => {
						var result = fieldData.results[0];

						var notification = result.notifications[0];

						var recipient = notification.recipients[0];

						Liferay.Test.assertIsNotValue(recipient.receptionType);
					});

					done();
				});
			});

			it('test should have "users" as recipient.', done => {
				Liferay.Test.loadResource(
					'recipients-with-user-definition.xml'
				).then(definition => {
					var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
						value: definition
					});

					xmlDefinition.forEachField((tagName, fieldData) => {
						var result = fieldData.results[0];

						var notification = result.notifications[0];

						var recipient = notification.recipients[0];

						assert.equal(recipient.user.length, 1);
					});

					done();
				});
			});

			it('test should have "assignees" as recipient.', done => {
				Liferay.Test.loadResource(
					'recipients-with-assignees-definition.xml'
				).then(definition => {
					var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
						value: definition
					});

					xmlDefinition.forEachField((tagName, fieldData) => {
						var result = fieldData.results[0];

						var notification = result.notifications[0];

						var recipient = notification.recipients[0];

						Liferay.Test.assertIsValue(recipient.taskAssignees);
					});

					done();
				});
			});
		});
	});
});
