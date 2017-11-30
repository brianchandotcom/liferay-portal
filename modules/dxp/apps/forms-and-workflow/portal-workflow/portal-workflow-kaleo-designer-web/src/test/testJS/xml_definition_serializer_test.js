'use strict';

Liferay.XMLFormatter = function() {};

Liferay.XMLFormatter.prototype.format = function(content) {
	return content;
};

var METADATA = {
	name: 'definition',
	version: 1
};

var XML_NAMESPACE = {
	'xmlns': 'urn:liferay.com:liferay-workflow_7.0.0',
	'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
	'xsi:schemaLocation': 'urn:liferay.com:liferay-workflow_7.0.0 http://www.liferay.com/dtd/liferay-workflow-definition_7_0_0.xsd'
};

var serializeDefinition;

describe(
	'Liferay.KaleoDesignerXMLDefinitionSerializer',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-kaleo-designer-xml-definition-serializer',
					function(A) {
						serializeDefinition = Liferay.KaleoDesignerXMLDefinitionSerializer;

						done();
					}
				);
			}
		);

		describe(
			'regression',
			function() {
				it(
					'should serialize "receptionType" attribute.',
					function(done) {
						var jsonDefinition = {
							nodes: [
								{
									name: 'task1',
									notifications: {
										name: ['notification1'],
										recipients: [
											{
												receptionType: 'bcc'
											}
										]
									},
									xmlType: 'task'
								}
							]
						};

						var definition = serializeDefinition(
							XML_NAMESPACE,
							METADATA,
							jsonDefinition
						);

						assert(
							definition.indexOf('receptionType="bcc"') > 0,
							'receptionType attribute not serialized.'
						);

						done();
					}
				);

				it(
					'should not serialize "receptionType" attribute if it has no value.',
					function(done) {
						var jsonDefinition = {
							nodes: [
								{
									name: 'task1',
									notifications: {
										name: ['notification1'],
										recipients: [
											{
												receptionType: [null]
											}
										]
									},
									xmlType: 'task'
								}
							]
						};

						var definition = serializeDefinition(
							XML_NAMESPACE,
							METADATA,
							jsonDefinition
						);

						assert(
							definition.indexOf('receptionType="') < 0,
							'Empty receptionType attribute is serialized.'
						);

						done();
					}
				);

				it(
					'should serialize <user> element if given.',
					function(done) {
						var jsonDefinition = {
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
												userId: [null]
											}
										]
									},
									xmlType: 'task'
								}
							]
						};

						var definition = serializeDefinition(
							XML_NAMESPACE,
							METADATA,
							jsonDefinition
						);

						assert(
							definition.indexOf('<user') > 0,
							'<users/> element not serialized.'
						);

						done();
					}
				);

				it(
					'should serialize <user> element even if empty.',
					function(done) {
						var jsonDefinition = {
							nodes: [
								{
									name: 'task1',
									notifications: {
										name: ['notification1'],
										recipients: [
											{
												assignmentType: ['user']
											}
										]
									},
									xmlType: 'task'
								}
							]
						};

						var definition = serializeDefinition(
							XML_NAMESPACE,
							METADATA,
							jsonDefinition
						);

						assert(
							definition.indexOf('<user') > 0,
							'<users/> element not serialized.'
						);

						done();
					}
				);

				it(
					'should not serialize <assignment> if assignment object is empty.',
					function(done) {
						var jsonDefinition = {
							nodes: [
								{
									name: 'task1',
									assignments: {},
									xmlType: 'task'
								}
							]
						};

						var definition = serializeDefinition(
							XML_NAMESPACE,
							METADATA,
							jsonDefinition
						);

						assert(
							definition.indexOf('<assignments') < 0,
							'<assignments/> element serialized from empty object.'
						);

						done();
					}
				);
			}
		);
	}
);