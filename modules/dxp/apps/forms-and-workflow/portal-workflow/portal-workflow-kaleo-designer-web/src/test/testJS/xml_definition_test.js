'use strict';

describe(
	'Liferay.KaleoDesignerXMLDefinition',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-kaleo-designer-xml-definition',
					function(A) {
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
								var definition = '<?xml version="1.0"?>' +
									'<workflow-definition xmlns="urn:liferay.com:liferay-workflow_6.2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:liferay.com:liferay-workflow_6.2.0 http://www.liferay.com/dtd/liferay-workflow-definition_6_2_0.xsd">' +
									'<name>Single Approver</name>' +
									'<description>A single approver can approve a workflow content.</description>' +
									'<version>1</version>' +
									'</workflow-definition>';

								var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
									{
										value: definition
									}
								);

								var metadata = xmlDefinition.getDefinitionMetadata();

								assert.equal(metadata.name, 'Single Approver');

								done();
							}
						);
					}
				);
			}
		);
	}
);