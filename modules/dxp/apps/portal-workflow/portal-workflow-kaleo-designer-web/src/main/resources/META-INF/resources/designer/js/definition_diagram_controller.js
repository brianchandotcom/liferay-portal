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

AUI.add(
	'liferay-kaleo-designer-definition-diagram-controller',
	() => {
		var XMLDefinition = Liferay.KaleoDesignerXMLDefinition;

		var jsonParse = Liferay.KaleoDesignerUtils.jsonParse;
		var serializeDefinition = Liferay.KaleoDesignerXMLDefinitionSerializer;
		var uniformRandomInt = Liferay.KaleoDesignerUtils.uniformRandomInt;

		var FieldNormalizer = Liferay.KaleoDesignerFieldNormalizer;

		var DEFAULT_LANGUAGE = 'groovy';

		var DefinitionDiagramController = function(content, canvas) {
			var instance = this;

			instance.definition = new XMLDefinition({
				value: content
			});

			instance.canvas = canvas;
		};

		DefinitionDiagramController.prototype = {
			_getRandomXY() {
				var instance = this;

				var region = instance.canvas.get('region');

				return [
					uniformRandomInt(0, region.width - 100),
					uniformRandomInt(0, region.height - 100)
				];
			},

			getConnectors() {
				var instance = this;

				var connectors = [];

				instance.definition.forEachField((tagName, fieldData) => {
					fieldData.results.forEach(item1 => {
						item1.transitions.forEach(item2 => {
							connectors.push({
								connector: {
									default: item2.default,
									name: item2.name
								},
								source: item1.name,
								target: item2.target
							});
						});
					});
				});

				return connectors;
			},

			getFields() {
				var instance = this;

				var fields = [];

				instance.definition.forEachField((tagName, fieldData) => {
					fieldData.results.forEach(item => {
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

						fields.push({
							actions: FieldNormalizer.normalizeToActions(
								item.actions
							),
							assignments: FieldNormalizer.normalizeToAssignments(
								item.assignments
							),
							description: item.description,
							fields: [{}],
							initial: item.initial,
							metadata,
							name: item.name,
							notifications: FieldNormalizer.normalizeToNotifications(
								item.notifications
							),
							script: item.script,
							scriptLanguage:
								item.scriptLanguage || DEFAULT_LANGUAGE,
							taskTimers: FieldNormalizer.normalizeToTaskTimers(
								item.taskTimers
							),
							type,
							xy: metadata.xy
						});
					});
				});

				return fields;
			},

			serializeDefinition(json) {
				var instance = this;

				return serializeDefinition(
					instance.definition.get('xmlNamespace'),
					instance.definition.getAttrs([
						'description',
						'name',
						'version'
					]),
					json
				);
			}
		};

		Liferay.KaleoDesignerDefinitionDiagramController = DefinitionDiagramController;
	},
	'',
	{
		requires: [
			'liferay-kaleo-designer-field-normalizer',
			'liferay-kaleo-designer-utils',
			'liferay-kaleo-designer-xml-definition',
			'liferay-kaleo-designer-xml-definition-serializer'
		]
	}
);
