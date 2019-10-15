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

describe('Liferay.KaleoDesignerXMLDefinition', function() {
	before(function(done) {
		AUI().use('liferay-kaleo-designer-xml-definition', function() {
			done();
		});
	});

	describe('unit', function() {
		describe('.getDefinitionMetadata()', function() {
			it('test should have name', function(done) {
				Liferay.Test.loadResource('metadata-only-definition.xml').then(
					function(definition) {
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

	describe('regression', function() {
		describe('.forEachField()', function() {
			it('test should retrieve "receptionType" attribute value', function(done) {
				Liferay.Test.loadResource(
					'recipients-with-reception-type-bcc-definition.xml'
				).then(function(definition) {
					var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
						value: definition
					});

					xmlDefinition.forEachField(function(tagName, fieldData) {
						var result = fieldData.results[0];

						var notification = result.notifications[0];

						var recipient = notification.recipients[0];

						assert.equal(recipient.receptionType, 'bcc');
					});

					done();
				});
			});

			it('test should not have a "receptionType" attribute if not present in definition', function(done) {
				Liferay.Test.loadResource(
					'recipients-with-no-reception-type-definition.xml'
				).then(function(definition) {
					var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
						value: definition
					});

					xmlDefinition.forEachField(function(tagName, fieldData) {
						var result = fieldData.results[0];

						var notification = result.notifications[0];

						var recipient = notification.recipients[0];

						Liferay.Test.assertIsNotValue(recipient.receptionType);
					});

					done();
				});
			});

			it('test should have "users" as recipient.', function(done) {
				Liferay.Test.loadResource(
					'recipients-with-user-definition.xml'
				).then(function(definition) {
					var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
						value: definition
					});

					xmlDefinition.forEachField(function(tagName, fieldData) {
						var result = fieldData.results[0];

						var notification = result.notifications[0];

						var recipient = notification.recipients[0];

						assert.equal(recipient.user.length, 1);
					});

					done();
				});
			});

			it('test should have "assignees" as recipient.', function(done) {
				Liferay.Test.loadResource(
					'recipients-with-assignees-definition.xml'
				).then(function(definition) {
					var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition({
						value: definition
					});

					xmlDefinition.forEachField(function(tagName, fieldData) {
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
