'use strict';

var isValue;

describe(
	'Liferay.KaleoDesignerXMLDefinition',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-kaleo-designer-xml-definition',
					function(A) {
						isValue = A.Lang.isValue;

						done();
					}
				);
			}
		);

		describe(
			'unit',
			function() {
				describe(
					'.getDefinitionMetadata()',
					function() {
						it(
							'should have name',
							function(done) {
								Liferay.Test.loadResource('metadata-only-definition.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										var metadata = xmlDefinition.getDefinitionMetadata();

										assert.equal(metadata.description, 'It only has metadata');
										assert.equal(metadata.name, 'Metadata Only');
										assert.equal(metadata.version, 42);

										done();
									}
								);
							}
						);
					}
				);
			}
		);

		describe(
			'regression',
			function() {
				describe(
					'.forEachField()',
					function() {
						it(
							'should retrieve "receptionType" attribute value',
							function(done) {
								Liferay.Test.loadResource('recipients-with-reception-type-bcc-definition.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										xmlDefinition.forEachField(
											function(tagName, fieldData) {
												var result = fieldData.results[0];

												var notification = result.notifications[0];

												var recipient = notification.recipients[0];

												assert.equal(recipient.receptionType, 'bcc');
											}
										);

										done();
									}
								);
							}
						);

						it(
							'should not have a "receptionType" attribute if not present in definition',
							function(done) {
								Liferay.Test.loadResource('recipients-with-no-reception-type-definition.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										xmlDefinition.forEachField(
											function(tagName, fieldData) {
												var result = fieldData.results[0];

												var notification = result.notifications[0];

												var recipient = notification.recipients[0];

												assert(!isValue(recipient.receptionType));
											}
										);

										done();
									}
								);
							}
						);

						it(
							'should have "users" as recipient.',
							function(done) {
								Liferay.Test.loadResource('recipients-with-user-definition.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										xmlDefinition.forEachField(
											function(tagName, fieldData) {
												var result = fieldData.results[0];

												var notification = result.notifications[0];

												var recipient = notification.recipients[0];

												assert.equal(recipient.user.length, 1);
											}
										);

										done();
									}
								);
							}
						);

						it(
							'should have "assignees" as recipient.',
							function(done) {
								Liferay.Test.loadResource('recipients-with-assignees-definition.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										xmlDefinition.forEachField(
											function(tagName, fieldData) {
												var result = fieldData.results[0];

												var notification = result.notifications[0];

												var recipient = notification.recipients[0];

												assert(isValue(recipient.taskAssignees));
											}
										);

										done();
									}
								);
							}
						);
					}
				);
			}
		);
	}
);