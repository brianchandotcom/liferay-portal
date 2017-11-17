AUI.add(
	'liferay-kaleo-designer-definition-diagram-controller',
	function(A) {
		var XMLDefinition = Liferay.KaleoDesignerXMLDefinition;

		var jsonParse = Liferay.KaleoDesignerUtils.jsonParse;
		var serializeDefinition = Liferay.KaleoDesignerXMLDefinitionSerializer;
		var uniformRandomInt = Liferay.KaleoDesignerUtils.uniformRandomInt;

		var FieldNormalizer = Liferay.KaleoDesignerFieldNormalizer;

		var DEFAULT_LANGUAGE = 'groovy';

		var DefinitionDiagramController = function(content, canvas) {
			var instance = this;

			instance.definition = new XMLDefinition(
				{
					value: content
				}
			);

			instance.canvas = canvas;
		};

		DefinitionDiagramController.prototype = {
			getConnectors: function() {
				var instance = this;

				var connectors = [];

				instance.definition.forEachField(
					function(tagName, fieldData) {
						fieldData.results.forEach(
							function(item1, index1, collection1) {
								item1.transitions.forEach(
									function(item2, index2, collection2) {
										connectors.push(
											{
												connector: {
													'default': item2.default,
													name: item2.name
												},
												source: item1.name,
												target: item2.target
											}
										);
									}
								);
							}
						);
					}
				);

				return connectors;
			},

			getFields: function() {
				var instance = this;

				var fields = [];

				instance.definition.forEachField(
					function(tagName, fieldData) {
						fieldData.results.forEach(
							function(item, index, collection) {
								var description = jsonParse(item.description);

								var type = tagName;

								if (item.initial) {
									type = 'start';
								}

								var metadata = jsonParse(item.metadata);

								if (metadata) {
									if (metadata.terminal) {
										type = 'end';
									}
								}
								else {
									metadata = {
										xy: instance._getRandomXY()
									};
								}

								fields.push(
									{
										actions: FieldNormalizer.normalizeToActions(item.actions),
										assignments: FieldNormalizer.normalizeToAssignments(item.assignments),
										description: description,
										fields: [{}],
										initial: item.initial,
										metadata: metadata,
										name: item.name,
										notifications: FieldNormalizer.normalizeToNotifications(item.notifications),
										script: item.script,
										scriptLanguage: item.scriptLanguage || DEFAULT_LANGUAGE,
										taskTimers: FieldNormalizer.normalizeToTaskTimers(item.taskTimers),
										type: type,
										xy: metadata.xy
									}
								);
							}
						);
					}
				);

				return fields;
			},

			serializeDefinition: function(json) {
				var instance = this;

				return serializeDefinition(
					instance.definition.get('xmlNamespace'),
					instance.definition.getAttrs(['description', 'name', 'version']),
					json
				);
			},

			_getRandomXY: function() {
				var instance = this;

				var region = instance.canvas.get('region');

				return [
					uniformRandomInt(0, region.width - 100),
					uniformRandomInt(0, region.height - 100)
				];
			}
		};

		Liferay.KaleoDesignerDefinitionDiagramController = DefinitionDiagramController;
	},
	'',
	{
		requires: ['liferay-kaleo-designer-field-normalizer', 'liferay-kaleo-designer-utils', 'liferay-kaleo-designer-xml-definition', 'liferay-kaleo-designer-xml-definition-serializer']
	}
);