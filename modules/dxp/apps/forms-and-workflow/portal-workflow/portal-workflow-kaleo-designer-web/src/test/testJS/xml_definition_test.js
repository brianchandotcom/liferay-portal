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
								Liferay.Test.loadResource('metadata-only.xml')
								.then(
									function(definition) {
										var xmlDefinition = new Liferay.KaleoDesignerXMLDefinition(
											{
												value: definition
											}
										);

										var metadata = xmlDefinition.getDefinitionMetadata();

										assert.equal(metadata.description, 'It only has metadata');
										assert.equal(metadata.name, 'Metadata only');
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
	}
);