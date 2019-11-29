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
	'liferay-kaleo-designer-nodes',
	A => {
		var AArray = A.Array;
		var DiagramBuilder = A.DiagramBuilder;
		var Lang = A.Lang;

		var isNumber = Lang.isNumber;
		var isObject = Lang.isObject;
		var isString = Lang.isString;

		var DiagramBuilderTypes = DiagramBuilder.types;

		var KaleoDesignerEditors = Liferay.KaleoDesignerEditors;
		var KaleoDesignerRemoteServices = Liferay.KaleoDesignerRemoteServices;
		var KaleoDesignerStrings = Liferay.KaleoDesignerStrings;

		var STR_BLANK = '';

		var PropertyListFormatter =
			Liferay.KaleoDesignerUtils.PropertyListFormatter;

		var renderShapeBoundary = function() {
			var instance = this;

			var boundary = (instance.boundary = instance
				.get('graphic')
				.addShape(instance.get('shapeBoundary')));

			return boundary;
		};

		var Connector = A.Component.create({
			ATTRS: {
				default: {
					setter: A.DataType.Boolean.parse,
					value: false
				}
			},

			EXTENDS: A.Connector,

			NAME: 'line',

			STRINGS: KaleoDesignerStrings,

			prototype: {
				SERIALIZABLE_ATTRS: A.Connector.prototype.SERIALIZABLE_ATTRS.concat(
					['default']
				),

				getPropertyModel() {
					var parentModel = A.Connector.superclass.getPropertyModel.apply(
						this,
						arguments
					);

					return AArray(parentModel).concat([
						{
							attributeName: 'default',
							editor: new A.RadioCellEditor({
								options: ['true', 'false']
							}),
							name: KaleoDesignerStrings.default
						}
					]);
				}
			}
		});

		var DiagramNodeState = A.Component.create({
			ATTRS: {
				actions: {},

				iconClass: {
					value: 'circle'
				},

				initial: {
					setter: A.DataType.Boolean.parse,
					value: false
				},

				metadata: {
					validator: isObject,
					value: {}
				},

				notifications: {},

				strings: {
					value: KaleoDesignerStrings
				},

				taskTimers: {},

				xmlType: {
					value: 'state'
				}
			},

			AUGMENTS: [A.WidgetCssClass],

			EXTENDS: A.DiagramNodeState,

			NAME: 'diagram-node',

			prototype: {
				_afterNodeRender() {
					var instance = this;

					var icon = A.Node.create(
						Liferay.Util.getLexiconIconTpl(
							instance.get('iconClass')
						)
					);

					instance
						.get('contentBox')
						.one('svg')
						.prepend(icon);

					if (
						A.instanceOf(
							instance,
							A.DiagramBuilder.types.condition
						) ||
						A.instanceOf(instance, A.DiagramBuilder.types.fork) ||
						A.instanceOf(instance, A.DiagramBuilder.types.join) ||
						A.instanceOf(
							instance,
							A.DiagramBuilder.types['join-xor']
						) ||
						A.instanceOf(instance, A.DiagramBuilder.types.task)
					) {
						icon.setAttribute('height', '60px');
						icon.setAttribute('width', '60px');
						instance.boundary.set(
							'transform',
							'matrix(1,0,0,1,2,2)'
						);
					}
					else {
						icon.setAttribute('height', '34px');
						icon.setAttribute('width', '34px');
						instance.boundary.set(
							'transform',
							'matrix(1,0,0,1,0,0)'
						);
					}

					if (
						A.instanceOf(
							instance,
							A.DiagramBuilder.types.condition
						) ||
						A.instanceOf(instance, A.DiagramBuilder.types.fork) ||
						A.instanceOf(instance, A.DiagramBuilder.types.join) ||
						A.instanceOf(
							instance,
							A.DiagramBuilder.types['join-xor']
						)
					) {
						instance.boundary.rotate(45);
						instance.boundary.translate(10, 0);
					}
				},

				_uiSetXY(val) {
					var instance = this;

					DiagramNodeState.superclass._uiSetXY.apply(this, arguments);

					instance.updateMetadata('xy', val);
				},

				_valueShapeBoundary() {
					var shape = A.DiagramNodeState.prototype._valueShapeBoundary();

					shape.radius = 17;

					return shape;
				},

				SERIALIZABLE_ATTRS: A.DiagramNode.prototype.SERIALIZABLE_ATTRS.concat(
					[
						'actions',
						'notifications',
						'initial',
						'metadata',
						'recipients',
						'script',
						'scriptLanguage',
						'taskTimers',
						'xmlType'
					]
				),

				getConnectionNode() {
					var node = new Liferay.KaleoDesignerNodes.DiagramNodeTask({
						xy: [100, 100]
					});

					return node;
				},

				getPropertyModel() {
					var instance = this;

					var builder = instance.get('builder');

					var availablePropertyModels = builder.get(
						'availablePropertyModels'
					);

					var strings = instance.getStrings();
					var type = instance.get('type');

					var model = AArray([
						{
							attributeName: 'actions',
							editor: new KaleoDesignerEditors.ActionsEditor({
								builder
							}),
							formatter: PropertyListFormatter.names,
							name: strings.actions
						},
						{
							attributeName: 'notifications',
							editor: new KaleoDesignerEditors.NotificationsEditor(
								{
									builder
								}
							),
							formatter: PropertyListFormatter.names,
							name: strings.notifications
						},
						{
							attributeName: 'taskTimers',
							editor: new KaleoDesignerEditors.TaskTimersEditor({
								builder
							}),
							formatter: PropertyListFormatter.names,
							name: strings.timers
						}
					]);

					var typeModel = availablePropertyModels[type];

					var parentModel = DiagramNodeState.superclass.getPropertyModel.apply(
						this,
						arguments
					);

					var returnValue;

					if (typeModel) {
						returnValue = typeModel.call(
							this,
							model,
							parentModel,
							arguments
						);
					}
					else {
						returnValue = model.concat(parentModel);
					}

					return returnValue;
				},

				initializer() {
					var instance = this;

					instance.after('render', instance._afterNodeRender);
				},

				renderShapeBoundary,

				updateMetadata(key, value) {
					var instance = this;

					var metadata = instance.get('metadata');

					metadata[key] = value;

					instance.set('metadata', metadata);
				}
			}
		});

		DiagramBuilderTypes.state = DiagramNodeState;

		var DiagramNodeCondition = A.Component.create({
			ATTRS: {
				height: {
					value: 60
				},

				iconClass: {
					value: 'diamond'
				},

				script: {
					validator: isString,
					value: 'returnValue = "Transition Name";'
				},

				scriptLanguage: {
					validator: isString,
					value: 'groovy'
				},

				type: {
					validator: isString,
					value: 'condition'
				},

				width: {
					value: 60
				},

				xmlType: {
					validator: isString,
					value: 'condition'
				}
			},

			EXTENDS: DiagramNodeState,

			NAME: 'diagram-node',

			prototype: {
				_valueShapeBoundary() {
					var shape = A.DiagramNodeCondition.prototype._valueShapeBoundary();

					shape.width = 41;
					shape.height = 41;

					return shape;
				},

				getPropertyModel() {
					var instance = this;

					var builder = instance.get('builder');

					var availablePropertyModels = builder.get(
						'availablePropertyModels'
					);

					var type = instance.get('type');

					var strings = instance.getStrings();

					var model = AArray([
						{
							attributeName: 'script',
							editor: new KaleoDesignerEditors.ScriptEditor(),
							formatter: PropertyListFormatter.script,
							name: strings.script
						},
						{
							attributeName: 'scriptLanguage',
							editor: new A.DropDownCellEditor({
								options: instance.getScriptLanguageOptions()
							}),
							name: strings.scriptLanguage
						}
					]);

					var typeModel = availablePropertyModels[type];

					var parentModel = DiagramNodeCondition.superclass.getPropertyModel.apply(
						this,
						arguments
					);

					var returnValue;

					if (typeModel) {
						returnValue = typeModel.call(
							this,
							model,
							parentModel,
							arguments
						);
					}
					else {
						returnValue = model.concat(parentModel);
					}

					return returnValue;
				},

				getScriptLanguageOptions() {
					var instance = this;

					var scriptLanguages = [];

					instance.getScriptLanguages(scriptLanguages);

					var scriptLanguageOptions = {};

					var strings = instance.getStrings();

					scriptLanguages.forEach(item => {
						if (item) {
							scriptLanguageOptions[item] = strings[item];
						}
					});

					return scriptLanguageOptions;
				},

				getScriptLanguages(scriptLanguages) {
					KaleoDesignerRemoteServices.getScriptLanguages(data => {
						AArray.each(data, item => {
							if (item) {
								scriptLanguages.push(item.scriptLanguage);
							}
						});
					});
				},

				hotPoints: A.DiagramNode.DIAMOND_POINTS,

				renderShapeBoundary
			}
		});

		DiagramBuilderTypes.condition = DiagramNodeCondition;

		var DiagramNodeJoin = A.Component.create({
			ATTRS: {
				height: {
					value: 60
				},

				iconClass: {
					value: 'arrow-join'
				},

				type: {
					validator: isString,
					value: 'join'
				},

				width: {
					value: 60
				},

				xmlType: {
					validator: isString,
					value: 'join'
				}
			},

			EXTENDS: DiagramNodeState,

			NAME: 'diagram-node',

			prototype: {
				_valueShapeBoundary() {
					var shape = A.DiagramNodeJoin.prototype._valueShapeBoundary();

					shape.width = 41;
					shape.height = 41;

					return shape;
				},

				hotPoints: A.DiagramNode.DIAMOND_POINTS,

				renderShapeBoundary
			}
		});

		DiagramBuilderTypes.join = DiagramNodeJoin;

		var DiagramNodeJoinXOR = A.Component.create({
			ATTRS: {
				iconClass: {
					value: 'arrow-xor'
				},

				type: {
					validator: isString,
					value: 'join-xor'
				},

				xmlType: {
					validator: isString,
					value: 'join-xor'
				}
			},

			EXTENDS: DiagramNodeJoin,

			NAME: 'diagram-node',

			prototype: {
				_valueShapeBoundary() {
					var shape = A.DiagramNodeJoin.prototype._valueShapeBoundary();

					shape.width = 41;
					shape.height = 41;

					return shape;
				},

				renderShapeBoundary
			}
		});

		DiagramBuilderTypes['join-xor'] = DiagramNodeJoinXOR;

		var DiagramNodeFork = A.Component.create({
			ATTRS: {
				height: {
					value: 60
				},

				iconClass: {
					value: 'arrow-split'
				},

				type: {
					validator: isString,
					value: 'fork'
				},

				width: {
					value: 60
				},

				xmlType: {
					validator: isString,
					value: 'fork'
				}
			},

			EXTENDS: DiagramNodeState,

			NAME: 'diagram-node',

			prototype: {
				_valueShapeBoundary() {
					var shape = A.DiagramNodeFork.prototype._valueShapeBoundary();

					shape.width = 41;
					shape.height = 41;

					return shape;
				},

				getConnectionNode() {
					var node = new DiagramNodeJoin({
						xy: [100, 100]
					});

					return node;
				},

				hotPoints: A.DiagramNode.DIAMOND_POINTS,

				renderShapeBoundary
			}
		});

		DiagramBuilderTypes.fork = DiagramNodeFork;

		var DiagramNodeStart = A.Component.create({
			ATTRS: {
				iconClass: {
					value: 'arrow-start'
				},

				initial: {
					value: true
				},

				maxFields: {
					validator: isNumber,
					value: 1
				},

				type: {
					validator: isString,
					value: 'start'
				}
			},

			EXTENDS: DiagramNodeState,

			NAME: 'diagram-node',

			prototype: {
				_valueShapeBoundary() {
					var shape = A.DiagramNodeStart.prototype._valueShapeBoundary();

					shape.radius = 17;

					return shape;
				},

				getConnectionNode() {
					var node = new DiagramNodeCondition({
						xy: [100, 100]
					});

					return node;
				},

				renderShapeBoundary
			}
		});

		DiagramBuilderTypes.start = DiagramNodeStart;

		var DiagramNodeEnd = A.Component.create({
			ATTRS: {
				iconClass: {
					value: 'arrow-end'
				},

				metadata: {
					value: {
						terminal: true
					}
				},

				type: {
					validator: isString,
					value: 'end'
				}
			},

			EXTENDS: DiagramNodeState,

			NAME: 'diagram-node',

			prototype: {
				_handleAddAnchorEvent() {
					var instance = this;

					instance.addField({
						maxTargets: 0
					});
				},

				_handleAddNodeEvent() {
					var instance = this;

					var builder = instance.get('builder');

					var source = instance.findAvailableAnchor();

					if (source) {
						var diagramNode = instance.getConnectionNode();

						builder.addField(diagramNode);
						diagramNode.addField({}).connect(source);
					}
				},

				_valueShapeBoundary() {
					var shape = A.DiagramNodeEnd.prototype._valueShapeBoundary();

					shape.radius = 17;

					return shape;
				},

				renderShapeBoundary
			}
		});

		DiagramBuilderTypes.end = DiagramNodeEnd;

		var DiagramNodeTask = A.Component.create({
			ATTRS: {
				assignments: {
					validator: isObject,
					value: {}
				},

				forms: {
					value: {
						templateId: [0],
						templateName: [STR_BLANK]
					}
				},

				height: {
					value: 70
				},

				iconClass: {
					value: 'square'
				},

				type: {
					validator: isString,
					value: 'task'
				},

				width: {
					value: 70
				},

				xmlType: {
					validator: isString,
					value: 'task'
				}
			},

			EXTENDS: DiagramNodeState,

			NAME: 'diagram-node',

			prototype: {
				_valueShapeBoundary() {
					var shape = A.DiagramNodeTask.prototype._valueShapeBoundary();

					shape.width = 55;
					shape.height = 55;

					return shape;
				},

				SERIALIZABLE_ATTRS: DiagramNodeState.prototype.SERIALIZABLE_ATTRS.concat(
					['assignments']
				),

				hotPoints: A.DiagramNode.SQUARE_POINTS,

				renderShapeBoundary
			}
		});

		DiagramBuilderTypes.task = DiagramNodeTask;

		A.Connector = Connector;

		Liferay.DiagramBuilderTypes = DiagramBuilderTypes;

		Liferay.KaleoDesignerNodes = {
			DiagramNodeCondition,
			DiagramNodeEnd,
			DiagramNodeFork,
			DiagramNodeJoin,
			DiagramNodeStart,
			DiagramNodeState,
			DiagramNodeTask
		};
	},
	'',
	{
		requires: [
			'aui-datatable',
			'aui-datatype',
			'aui-diagram-builder',
			'liferay-kaleo-designer-editors',
			'liferay-kaleo-designer-remote-services',
			'liferay-kaleo-designer-utils'
		]
	}
);
