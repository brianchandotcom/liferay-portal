AUI.add(
	'liferay-kaleo-designer-nodes',
	function(A) {
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

		var PropertyListFormatter = Liferay.KaleoDesignerUtils.PropertyListFormatter;

		var Connector = A.Component.create(
			{
				ATTRS: {
					'default': {
						setter: A.DataType.Boolean.parse,
						value: false
					}
				},

				EXTENDS: A.Connector,

				NAME: 'line',

				STRINGS: KaleoDesignerStrings,

				prototype: {
					SERIALIZABLE_ATTRS: A.Connector.prototype.SERIALIZABLE_ATTRS.concat(['default']),

					getPropertyModel: function() {
						var instance = this;

						var parentModel = A.Connector.superclass.getPropertyModel.apply(this, arguments);

						return AArray(parentModel).concat(
							[
								{
									attributeName: 'default',
									editor: new A.RadioCellEditor(
										{
											options: ['true', 'false']
										}
									),
									name: KaleoDesignerStrings.default
								}
							]
						);
					}
				}
			}
		);

		var DiagramNodeState = A.Component.create(
			{
				ATTRS: {
					actions: {
					},

					iconClass: {
						value: 'icon-db-state'
					},

					initial: {
						setter: A.DataType.Boolean.parse,
						value: false
					},

					metadata: {
						validator: isObject,
						value: {}
					},

					notifications: {
					},

					strings: {
						value: KaleoDesignerStrings
					},

					taskTimers: {
					},

					xmlType: {
						value: 'state'
					}
				},

				AUGMENTS: [A.WidgetCssClass],

				EXTENDS: A.DiagramNodeState,

				NAME: 'diagram-node',

				prototype: {
					SERIALIZABLE_ATTRS: A.DiagramNode.prototype.SERIALIZABLE_ATTRS.concat(['actions', 'notifications', 'initial', 'metadata', 'recipients', 'script', 'scriptLanguage', 'taskTimers', 'xmlType']),

					initializer: function() {
						var instance = this;

						instance.after('render', instance._afterNodeRender);
					},

					getConnectionNode: function() {
						var instance = this;

						var node = new Liferay.KaleoDesignerNodes.DiagramNodeTask(
							{
								xy: [100, 100]
							}
						);

						return node;
					},

					getPropertyModel: function() {
						var instance = this;

						var builder = instance.get('builder');

						var availablePropertyModels = builder.get('availablePropertyModels');

						var strings = instance.getStrings();
						var type = instance.get('type');

						var model = AArray(
							[
								{
									attributeName: 'actions',
									editor: new KaleoDesignerEditors.ActionsEditor(
										{
											builder: builder
										}
									),
									formatter: PropertyListFormatter.names,
									name: strings.actions
								},
								{
									attributeName: 'notifications',
									editor: new KaleoDesignerEditors.NotificationsEditor(
										{
											builder: builder
										}
									),
									formatter: PropertyListFormatter.names,
									name: strings.notifications
								},
								{
									attributeName: 'taskTimers',
									editor: new KaleoDesignerEditors.TaskTimersEditor(
										{
											builder: builder
										}
									),
									formatter: PropertyListFormatter.names,
									name: strings.timers
								}
							]
						);

						var typeModel = availablePropertyModels[type];

						var parentModel = DiagramNodeState.superclass.getPropertyModel.apply(this, arguments);

						var returnValue;

						if (typeModel) {
							returnValue = typeModel.call(this, model, parentModel, arguments);
						}
						else {
							returnValue = model.concat(parentModel);
						}

						return returnValue;
					},

					updateMetadata: function(key, value) {
						var instance = this;

						var metadata = instance.get('metadata');

						metadata[key] = value;

						instance.set('metadata', metadata);
					},

					_afterNodeRender: function() {
						var instance = this;

						instance.get('contentBox').addClass(instance.get('iconClass'));
					},

					_uiSetXY: function(val) {
						var instance = this;

						DiagramNodeState.superclass._uiSetXY.apply(this, arguments);

						instance.updateMetadata('xy', val);
					}
				}
			}
		);

		DiagramBuilderTypes.state = DiagramNodeState;

		var DiagramNodeCondition = A.Component.create(
			{
				ATTRS: {
					height: {
						value: 60
					},

					iconClass: {
						value: 'icon-db-condition'
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
					hotPoints: A.DiagramNode.DIAMOND_POINTS,

					getPropertyModel: function() {
						var instance = this;

						var builder = instance.get('builder');

						var availablePropertyModels = builder.get('availablePropertyModels');

						var type = instance.get('type');

						var strings = instance.getStrings();

						var model = AArray(
							[
								{
									attributeName: 'script',
									editor: new KaleoDesignerEditors.ScriptEditor(),
									formatter: PropertyListFormatter.script,
									name: strings.script
								},
								{
									attributeName: 'scriptLanguage',
									editor: new A.DropDownCellEditor(
										{
											options: instance.getScriptLanguageOptions()
										}
									),
									name: strings.scriptLanguage
								}
							]
						);

						var typeModel = availablePropertyModels[type];

						var parentModel = DiagramNodeCondition.superclass.getPropertyModel.apply(this, arguments);

						var returnValue;

						if (typeModel) {
							returnValue = typeModel.call(this, model, parentModel, arguments);
						}
						else {
							returnValue = model.concat(parentModel);
						}

						return returnValue;
					},

					getScriptLanguageOptions: function() {
						var instance = this;

						var scriptLanguages = [];

						instance.getScriptLanguages(scriptLanguages);

						var scriptLanguageOptions = {};

						var strings = instance.getStrings();

						scriptLanguages.forEach(
							function(item) {
								if (item) {
									scriptLanguageOptions[item] = strings[item];
								}
							}
						);

						return scriptLanguageOptions;
					},

					getScriptLanguages: function(scriptLanguages) {
						KaleoDesignerRemoteServices.getScriptLanguages(
							function(data) {
								AArray.each(
									data,
									function(item) {
										if (item) {
											scriptLanguages.push(item.scriptLanguage);
										}
									}
								);
							}
						);
					},

					renderShapeBoundary: A.DiagramNodeCondition.prototype.renderShapeBoundary,

					_valueShapeBoundary: A.DiagramNodeCondition.prototype._valueShapeBoundary
				}
			}
		);

		DiagramBuilderTypes.condition = DiagramNodeCondition;

		var DiagramNodeJoin = A.Component.create(
			{
				ATTRS: {
					height: {
						value: 60
					},

					iconClass: {
						value: 'icon-db-join'
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
					hotPoints: A.DiagramNode.DIAMOND_POINTS,

					renderShapeBoundary: A.DiagramNodeJoin.prototype.renderShapeBoundary,

					_valueShapeBoundary: A.DiagramNodeJoin.prototype._valueShapeBoundary
				}
			}
		);

		DiagramBuilderTypes.join = DiagramNodeJoin;

		var DiagramNodeJoinXOR = A.Component.create(
			{
				ATTRS: {
					iconClass: {
						value: 'icon-db-joinxor'
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

				NAME: 'diagram-node'
			}
		);

		DiagramBuilderTypes['join-xor'] = DiagramNodeJoinXOR;

		var DiagramNodeFork = A.Component.create(
			{
				ATTRS: {
					height: {
						value: 60
					},

					iconClass: {
						value: 'icon-db-fork'
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
					hotPoints: A.DiagramNode.DIAMOND_POINTS,

					getConnectionNode: function() {
						var instance = this;

						var node = new DiagramNodeJoin(
							{
								xy: [100, 100]
							}
						);

						return node;
					},

					renderShapeBoundary: A.DiagramNodeFork.prototype.renderShapeBoundary,

					_valueShapeBoundary: A.DiagramNodeFork.prototype._valueShapeBoundary
				}
			}
		);

		DiagramBuilderTypes.fork = DiagramNodeFork;

		var DiagramNodeStart = A.Component.create(
			{
				ATTRS: {
					iconClass: {
						value: 'icon-db-start'
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
					getConnectionNode: function() {
						var instance = this;

						var node = new DiagramNodeCondition(
							{
								xy: [100, 100]
							}
						);

						return node;
					}
				}
			}
		);

		DiagramBuilderTypes.start = DiagramNodeStart;

		var DiagramNodeEnd = A.Component.create(
			{
				ATTRS: {
					iconClass: {
						value: 'icon-db-end'
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
					_handleAddAnchorEvent: function(event) {
						var instance = this;

						instance.addField(
							{
								maxTargets: 0
							}
						);
					},

					_handleAddNodeEvent: function(event) {
						var instance = this;

						var builder = instance.get('builder');

						var source = instance.findAvailableAnchor();

						if (source) {
							var diagramNode = instance.getConnectionNode();

							builder.addField(diagramNode);
							diagramNode.addField({}).connect(source);
						}
					}
				}
			}
		);

		DiagramBuilderTypes.end = DiagramNodeEnd;

		var DiagramNodeTask = A.Component.create(
			{
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
						value: 'icon-db-task'
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
					SERIALIZABLE_ATTRS: DiagramNodeState.prototype.SERIALIZABLE_ATTRS.concat(['assignments']),

					hotPoints: A.DiagramNode.SQUARE_POINTS,

					renderShapeBoundary: A.DiagramNodeTask.prototype.renderShapeBoundary,

					_valueShapeBoundary: A.DiagramNodeTask.prototype._valueShapeBoundary
				}
			}
		);

		DiagramBuilderTypes.task = DiagramNodeTask;

		A.Connector = Connector;

		Liferay.DiagramBuilderTypes = DiagramBuilderTypes;

		Liferay.KaleoDesignerNodes = {
			DiagramNodeCondition: DiagramNodeCondition,
			DiagramNodeEnd: DiagramNodeEnd,
			DiagramNodeFork: DiagramNodeFork,
			DiagramNodeJoin: DiagramNodeJoin,
			DiagramNodeStart: DiagramNodeStart,
			DiagramNodeState: DiagramNodeState,
			DiagramNodeTask: DiagramNodeTask
		};
	},
	'',
	{
		requires: ['aui-datatable', 'aui-datatype', 'aui-diagram-builder', 'liferay-kaleo-designer-editors', 'liferay-kaleo-designer-remote-services', 'liferay-kaleo-designer-utils']
	}
);