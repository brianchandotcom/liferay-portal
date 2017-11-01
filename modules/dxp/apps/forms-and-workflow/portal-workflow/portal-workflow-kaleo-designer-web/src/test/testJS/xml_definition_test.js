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
								AUI().use(
									'aui-io',
									'liferay-kaleo-designer-xml-definition',
									function(A) {
										A.io.request(
											'/base/src/test/resources/metadata-only.xml',
											{
												dataType: 'text',
												on: {
													success: function() {
														var definition = this.get('responseData');

														var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
															{
																value: definition
															}
														);

														var metadata = xmlDefinition.getDefinitionMetadata();

														assert.equal(metadata.name, 'Single Approver');

														done();
													}
												}
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
	}
);