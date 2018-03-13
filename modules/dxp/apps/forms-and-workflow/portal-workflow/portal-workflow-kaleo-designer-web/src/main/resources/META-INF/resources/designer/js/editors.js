AUI.add(
	'liferay-kaleo-designer-editors',
	function(A) {
		var AArray = A.Array;
		var getClassName = A.getClassName;
		var Lang = A.Lang;
		var Template = A.Template;
		var WidgetStdMod = A.WidgetStdMod;

		var emptyFn = Lang.emptyFn;
		var isBoolean = Lang.isBoolean;
		var isValue = Lang.isValue;

		var KaleoDesignerStrings = Liferay.KaleoDesignerStrings;

		var serializeForm = Liferay.KaleoDesignerUtils.serializeForm;

		var CSS_CELLEDITOR_ASSIGNMENT_VIEW = getClassName('celleditor', 'assignment', 'view');

		var CSS_CELLEDITOR_VIEW_TYPE = getClassName('celleditor', 'view', 'type');

		var STR_BLANK = '';

		var STR_DASH = '-';

		var STR_DOT = '.';

		var STR_REMOVE_DYNAMIC_VIEW_BUTTON = '<div class="celleditor-view-menu">' +
				'<a class="celleditor-view-menu-remove btn btn-link btn-sm" href="#" title="' + KaleoDesignerStrings.remove + '">' + Liferay.Util.getLexiconIconTpl('times') + '</a>' +
			'</div>';

		var SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE = STR_DOT + CSS_CELLEDITOR_VIEW_TYPE + STR_DASH;

		var BaseAbstractEditor = A.Component.create(
			{
				ATTRS: {
					builder: {
					},

					editorForm: {
					},

					editorFormClass: {
					},

					strings: {
						value: KaleoDesignerStrings
					},

					viewTemplate: {
						setter: '_setViewTemplate'
					}
				},

				EXTENDS: A.BaseCellEditor,

				NAME: 'base-abstract-multi-section-editor',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.set('editorForm', instance._getEditorForm(config));

						instance.get('boundingBox').delegate('click', A.bind(instance._onClickViewMenu, instance), '.celleditor-view-menu a');

						instance.after('valueChange', instance._onValueChange);

						instance.after('visibleChange', instance._afterEditorVisibleChange);

						instance.destroyPortletHandler = Liferay.on('destroyPortlet', A.bind(instance._onDestroyPortlet, instance));
					},

					destructor: function() {
						var instance = this;

						var editorForm = instance.get('editorForm');

						if (editorForm) {
							editorForm.destroy(true);
						}
					},

					customizeToolbar: function() {
						var instance = this;

						var editorForm = instance.get('editorForm');

						instance.addSectionButton = editorForm.get('addSectionButton');

						if (instance.addSectionButton) {
							instance.toolbar.add([instance.addSectionButton]);
						}
					},

					getValue: function() {
						var instance = this;

						var editorForm = instance.get('editorForm');

						return editorForm.getValue();
					},

					_afterEditorVisibleChange: function(event) {
						var instance = this;

						if (event.newVal) {
							var editorForm = instance.get('editorForm');

							editorForm.syncViewsUI();
						}
					},

					_afterRender: function() {
						var instance = this;

						BaseAbstractEditor.superclass._afterRender.apply(this, arguments);

						var editorForm = instance.get('editorForm');

						editorForm.addStaticViews();

						instance.customizeToolbar();

						editorForm.syncToolbarUI();

						editorForm.syncViewsUI();
					},

					_getEditorForm: function(config) {
						var instance = this;

						var editorFormClass = instance.get('editorFormClass');

						var editorForm = new editorFormClass(config);

						var bodyNode = editorForm.get('bodyNode');

						instance.set('bodyContent', bodyNode);

						return editorForm;
					},

					_onClickViewMenu: function(event) {
						var anchor = event.currentTarget;

						if (anchor.hasClass('celleditor-view-menu-remove')) {
							anchor.ancestor('.celleditor-view').remove();
						}

						event.halt();
					},

					_onDestroyPortlet: function() {
						var instance = this;

						instance.destroy(true);
					},

					_onValueChange: function(event) {
						var instance = this;

						var editorForm = instance.get('editorForm');

						editorForm.set('value', event.newVal);
					},

					_setViewTemplate: function(val) {
						var instance = this;

						if (!A.instanceOf(val, A.Template)) {
							val = new Template(val);
						}

						return val;
					},

					_syncElementsFocus: function() {
						var instance = this;

						var editorForm = instance.get('editorForm');

						editorForm.syncElementsFocus();
					}
				}
			}
		);

		var BaseAbstractEditorForm = A.Component.create(
			{
				ATTRS: {
					addSectionButton: {
						valueFn: '_valueAddSectionButton'
					},

					bodyNode: {
						valueFn: function() {
							var instance = this;

							if (!instance.bodyNode) {
								var template = instance.get('bodyNodeTemplate');

								var bodyNode = A.Node.create(template);

								bodyNode.addClass('celleditor-full-view');

								instance.bodyNode = bodyNode;
							}

							return instance.bodyNode;
						}
					},

					bodyNodeTemplate: {
						value: [
							'<div class="celleditor-view-full-view">',
							'<div class="celleditor-view-static-view"></div>',
							'<div class="celleditor-view-dynamic-views"></div>',
							'</div>'
						].join(STR_BLANK)
					},

					builder: {
					},

					dynamicViewSingleton: {
						validator: isBoolean,
						value: false,
						writeOnce: 'initOnly'
					},

					strings: {
						value: KaleoDesignerStrings
					},

					value: {
					},

					viewTemplate: {
						setter: '_setViewTemplate'
					}
				},

				EXTENDS: A.Component,

				NAME: 'base-abstract-editor-form',

				UI_ATTRS: ['value'],

				prototype: {
					initializer: function() {
						var instance = this;

						instance.after('render', instance._afterRender);
					},

					destructor: function() {
						var instance = this;

						instance.bodyNode.remove(true);
					},

					addDynamicViews: emptyFn,

					addStaticViews: emptyFn,

					appendToDynamicView: function(view) {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicView = bodyNode.one('.celleditor-view-dynamic-views');

						if (typeof view === 'string') {
							view = A.Node.create(view);
						}

						dynamicView.append(view);

						if (!instance.get('dynamicViewSingleton')) {
							var dynamicViews = dynamicView.all('.celleditor-view');

							dynamicViews.each(A.bind(instance._addRemoveDynamicViewButton, instance));
						}
					},

					appendToStaticView: function(view) {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var staticView = bodyNode.one('.celleditor-view-static-view');

						if (typeof view === 'string') {
							view = A.Node.create(view);
						}

						staticView.append(view);
					},

					getDynamicViews: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicView = bodyNode.one('.celleditor-view-dynamic-views');

						return dynamicView.get('childNodes');
					},

					getStrings: function() {
						var instance = this;

						return instance.get('strings');
					},

					getValue: function() {
						var instance = this;

						return serializeForm(instance.get('bodyNode'));
					},

					handleAddViewSection: emptyFn,

					removeAllViews: function(viewId) {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						if (bodyNode) {
							bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + viewId).remove();
						}
					},

					syncElementsFocus: emptyFn,

					syncToolbarUI: function() {
						var instance = this;

						var addSectionButton = instance.get('addSectionButton');

						if (addSectionButton) {
							addSectionButton.set('disabled', !instance.viewId);
						}
					},

					syncViewsUI: function() {
						var instance = this;

						instance._uiSetValue(instance.get('value'));
					},

					_addRemoveDynamicViewButton: function(dynamicViewNode) {
						var instance = this;

						dynamicViewNode.append(A.Node.create(STR_REMOVE_DYNAMIC_VIEW_BUTTON));
					},

					_afterRender: function() {
						var instance = this;

						instance.addStaticViews();

						instance.syncToolbarUI();

						instance.syncViewsUI();
					},

					_onClickAddSectionButton: function(event) {
						var instance = this;

						instance.handleAddViewSection(event);

						var viewNodes = instance.getDynamicViews();

						instance._addRemoveDynamicViewButton(viewNodes.last());
					},

					_setViewTemplate: function(val) {
						var instance = this;

						if (!A.instanceOf(val, A.Template)) {
							val = new Template(val);
						}

						return val;
					},

					_uiSetValue: function(val) {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						instance.addDynamicViews(val);

						A.each(
							val,
							function(item1, index1, collection1) {
								var fields = bodyNode.all('[name="' + index1 + '"]');

								item1 = AArray(item1);

								fields.each(
									function(item2, index2, collection) {
										var value = item1[index2];

										if (item2.test('input[type=checkbox],input[type=radio]')) {
											item2.set('checked', A.DataType.Boolean.parse(value));
										}
										else if (item2.test('select[multiple]') && Lang.isArray(value)) {
											value.forEach(
												function(option) {
													for (var key in option) {
														item2.one('option[value=' + option[key] + ']').set('selected', true);
													}
												}
											);
										}
										else {
											item2.val(value);
										}
									}
								);
							}
						);
					},

					_valueAddSectionButton: function() {
						var instance = this;

						if (instance.get('dynamicViewSingleton')) {
							instance._addSectionButton = null;
						}
						else if (!instance._addSectionButton) {
							var strings = instance.get('strings');

							var addSectionButton = new A.Button(
								{
									disabled: true,
									icon: 'icon-plus-sign',
									id: 'addSectionButton',
									label: strings.addSection,
									on: {
										click: A.bind(instance._onClickAddSectionButton, instance)
									}
								}
							).render();

							var bodyNode = instance.get('bodyNode');

							bodyNode.append(addSectionButton.get('boundingBox'));

							instance._addSectionButton = addSectionButton;
						}

						return instance._addSectionButton;
					}
				}
			}
		);

		var CompositeEditorFormBase = function() {
		};

		CompositeEditorFormBase.prototype = {
			getEmbeddedEditorForm: function(editorFormClass, container, config) {
				var instance = this;

				var editorFormName = editorFormClass.NAME;

				var editorForm = container.getData(editorFormName + '-instance');

				if (!editorForm) {
					config = A.merge(
						{
							builder: instance.get('builder'),
							parentEditor: instance,
							render: false
						},
						config
					);

					editorForm = new editorFormClass(config);

					container.setData(editorFormName + '-instance', editorForm);
				}

				return editorForm;
			},

			showEditorForm: function(editorFormClass, container, value, config) {
				var instance = this;

				config = A.merge(
					{
						render: container,
						value: value
					},
					config
				);

				var editor = instance.getEmbeddedEditorForm(editorFormClass, container, config);

				var bodyNode = editor.get('bodyNode');

				container.append(bodyNode);

				editor.show();
			}
		};

		var AssignmentsEditorForm = A.Component.create(
			{
				ATTRS: {
					assignmentsType: {
						valueFn: '_valueAssignmentsType'
					},

					roleTypes: {
						valueFn: function() {
							var instance = this;

							var strings = instance.getStrings();

							return [
								{
									label: strings.site,
									value: 'site'
								},
								{
									label: strings.regular,
									value: 'regular'
								},
								{
									label: strings.organization,
									value: 'organization'
								}
							];
						}
					},

					scriptLanguages: {
						valueFn: function() {
							var instance = this;

							var strings = instance.getStrings();

							return [
								{
									label: strings.beanshell,
									value: 'beanshell'
								},
								{
									label: strings.drl,
									value: 'drl'
								},
								{
									label: strings.groovy,
									value: 'groovy'
								},
								{
									label: strings.java,
									value: 'java'
								},
								{
									label: strings.javascript,
									value: 'javascript'
								},
								{
									label: strings.python,
									value: 'python'
								},
								{
									label: strings.ruby,
									value: 'ruby'
								}
							];
						}
					},

					strings: {
						valueFn: function() {
							return A.merge(
								KaleoDesignerStrings,
								{
									assignmentTypeLabel: KaleoDesignerStrings.assignmentType,
									defaultAssignmentLabel: KaleoDesignerStrings.assetCreator
								}
							);
						}
					},

					typeSelect: {
					},

					viewTemplate: {
						value: [
							'<div class="{$ans}celleditor-assignment-view {$ans}celleditor-view {$ans}celleditor-view-type-{viewId} {$ans}hide">',
							'{content}',
							'</div>'
						]
					}
				},

				EXTENDS: BaseAbstractEditorForm,

				NAME: 'assignments-editor-form',

				prototype: {
					addDynamicViews: function(val) {
						var instance = this;

						Liferay.KaleoDesignerAutoCompleteUtil.destroyAll();

						instance.removeAllViews('roleType');

						instance.addViewRoleType(instance._countRoleTypeViews(val));

						instance.removeAllViews('user');

						if (val) {
							instance.showView(val.assignmentType);
						}
					},

					addStaticViews: function() {
						var instance = this;

						var strings = instance.getStrings();

						var assignmentsViewTpl = instance.get('viewTemplate');
						var inputTpl = Template.get('input');
						var selectTpl = Template.get('select');
						var textareaTpl = Template.get('textarea');

						var select = selectTpl.render(
							{
								auiCssClass: 'form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								label: strings.assignmentTypeLabel,
								name: 'assignmentType',
								options: instance.get('assignmentsType')
							}
						);

						var selectWrapper = A.Node.create('<div/>').append(select);

						var typeSelect = selectWrapper.one('select');

						instance.set('typeSelect', typeSelect);

						instance.appendToStaticView(selectWrapper);

						typeSelect.on(['change', 'keyup'], A.bind(instance._onTypeValueChange, instance));

						var buffer = [];

						var resourceActionContent = textareaTpl.parse(
							{
								auiCssClass: 'celleditor-textarea-small form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.resourceActions,
								name: 'resourceAction'
							}
						);

						buffer.push(
							assignmentsViewTpl.parse(
								{
									content: resourceActionContent,
									viewId: 'resourceActions'
								}
							)
						);

						var roleIdContent = [
							inputTpl.parse(
								{
									auiCssClass: 'assignments-cell-editor-input form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.role,
									name: 'roleNameAC',
									placeholder: KaleoDesignerStrings.search,
									size: 35,
									type: 'text'
								}
							),

							inputTpl.parse(
								{
									auiCssClass: 'assignments-cell-editor-input form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									disabled: true,
									id: A.guid(),
									label: strings.roleId,
									name: 'roleId',
									size: 35,
									type: 'text'
								}
							)
						].join(STR_BLANK);

						buffer.push(
							assignmentsViewTpl.parse(
								{
									content: roleIdContent,
									viewId: 'roleId'
								}
							)
						);

						var scriptedAssignmentContent = [
							textareaTpl.parse(
								{
									auiCssClass: 'celleditor-textarea-small form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.script,
									name: 'script'
								}
							),

							selectTpl.parse(
								{
									auiCssClass: 'form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.scriptLanguage,
									name: 'scriptLanguage',
									options: instance.get('scriptLanguages')
								}
							)
						].join(STR_BLANK);

						buffer.push(
							assignmentsViewTpl.parse(
								{
									content: scriptedAssignmentContent,
									viewId: 'scriptedAssignment'
								}
							)
						);

						instance.appendToStaticView(buffer.join(STR_BLANK));
					},

					addViewRoleType: function(num) {
						var instance = this;

						num = num || 1;

						var strings = instance.getStrings();

						var assignmentsViewTpl = instance.get('viewTemplate');

						var checkboxTpl = Template.get('checkbox');
						var inputTpl = Template.get('input');
						var selectTpl = Template.get('select');

						var buffer = [];

						for (var i = 0; i < num; i++) {
							var roleTypeContent = [
								selectTpl.parse(
									{
										auiCssClass: 'assignments-cell-editor-input form-control input-sm',
										auiLabelCssClass: 'celleditor-label',
										id: A.guid(),
										label: strings.roleType,
										name: 'roleType',
										options: instance.get('roleTypes')
									}
								),

								inputTpl.parse(
									{
										auiCssClass: 'assignments-cell-editor-input form-control input-sm',
										auiLabelCssClass: 'celleditor-label',
										id: A.guid(),
										label: strings.roleName,
										name: 'roleName',
										size: 35,
										type: 'text'
									}
								),

								'<div class="checkbox">',

								checkboxTpl.parse(
									{
										auiCssClass: 'assignments-cell-editor-input',
										auiLabelCssClass: 'celleditor-label-checkbox',
										checked: false,
										id: A.guid(),
										label: strings.autoCreate,
										name: 'autoCreate',
										type: 'checkbox'
									}
								),

								'</div>'
							].join(STR_BLANK);

							buffer.push(
								assignmentsViewTpl.parse(
									{
										content: roleTypeContent,
										showMenu: true,
										viewId: 'roleType'
									}
								)
							);
						}

						instance.appendToDynamicView(buffer.join(STR_BLANK));
					},

					addViewUser: function(num) {
						var instance = this;

						num = num || 1;

						var strings = instance.getStrings();

						var assignmentsViewTpl = instance.get('viewTemplate');

						var inputTpl = Template.get('input');

						var buffer = [];

						for (var i = 0; i < num; i++) {
							var userContent = [
								inputTpl.parse(
									{
										auiCssClass: 'assignments-cell-editor-input form-control input-sm',
										auiLabelCssClass: 'celleditor-label',
										id: A.guid(),
										label: strings.name,
										name: 'fullName',
										placeholder: KaleoDesignerStrings.search,
										size: 35,
										type: 'text'
									}
								),

								inputTpl.parse(
									{
										auiCssClass: 'assignments-cell-editor-input form-control input-sm',
										auiLabelCssClass: 'celleditor-label',
										disabled: true,
										id: A.guid(),
										label: strings.screenName,
										name: 'screenName',
										size: 35,
										type: 'text'
									}
								),

								inputTpl.parse(
									{
										auiCssClass: 'assignments-cell-editor-input form-control input-sm',
										auiLabelCssClass: 'celleditor-label',
										disabled: true,
										id: A.guid(),
										label: strings.emailAddress,
										name: 'emailAddress',
										size: 35,
										type: 'text'
									}
								),

								inputTpl.parse(
									{
										auiCssClass: 'assignments-cell-editor-input form-control input-sm',
										auiLabelCssClass: 'celleditor-label',
										disabled: true,
										id: A.guid(),
										label: strings.userId,
										name: 'userId',
										size: 35,
										type: 'text'
									}
								)
							].join(STR_BLANK);

							buffer.push(
								assignmentsViewTpl.parse(
									{
										content: userContent,
										showMenu: true,
										viewId: 'user'
									}
								)
							);
						}

						instance.appendToDynamicView(buffer.join(STR_BLANK));
					},

					getViewNodes: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						return bodyNode.all(STR_DOT + CSS_CELLEDITOR_ASSIGNMENT_VIEW);
					},

					handleAddViewSection: function(event) {
						var instance = this;

						var button = event.target;

						if (!button.get('disabled')) {
							var viewId = instance.viewId;

							if (viewId === 'user') {
								instance.addViewUser();
							}
							else if (viewId === 'roleType') {
								instance.addViewRoleType();
							}

							instance.showView(viewId);
						}
					},

					showView: function(viewId) {
						var instance = this;

						instance.viewId = viewId;

						instance.getViewNodes().hide();

						var bodyNode = instance.get('bodyNode');

						bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + viewId).show();

						instance.syncToolbarUI();
					},

					syncElementsFocus: function() {
						var instance = this;

						var typeSelect = instance.get('typeSelect');

						typeSelect.focus();
					},

					syncToolbarUI: function() {
						var instance = this;

						var viewId = instance.viewId;

						var disabled = viewId !== 'roleType' && viewId !== 'user';

						var addSectionButton = instance.get('addSectionButton');

						if (addSectionButton) {
							addSectionButton.set('disabled', disabled);
						}
					},

					syncViewsUI: function() {
						var instance = this;

						AssignmentsEditorForm.superclass.syncViewsUI.apply(this, arguments);

						var typeSelect = instance.get('typeSelect');

						instance.showView(typeSelect.val());
					},

					_countRoleTypeViews: function(val) {
						var instance = this;

						var count = 0;

						if (val) {
							count = val.roleType ? val.roleType.filter(isValue).length : 1;
						}

						return count;
					},

					_countUserViews: function(val) {
						var instance = this;

						var count = 0;

						if (val) {
							count = Math.max(
								val.emailAddress ? val.emailAddress.filter(isValue).length : 1,
								val.screenName ? val.screenName.filter(isValue).length : 1,
								val.userId ? val.userId.filter(isValue).length : 1
							);
						}

						return count;
					},

					_onTypeValueChange: function(event) {
						var instance = this;

						instance.showView(event.currentTarget.val());
					},

					_valueAssignmentsType: function() {
						var instance = this;

						var strings = instance.getStrings();

						return [
							{
								label: strings.defaultAssignmentLabel,
								value: STR_BLANK
							},
							{
								label: strings.resourceActions,
								value: 'resourceActions'
							},
							{
								label: strings.role,
								value: 'roleId'
							},
							{
								label: strings.roleType,
								value: 'roleType'
							},
							{
								label: strings.scriptedAssignment,
								value: 'scriptedAssignment'
							},
							{
								label: strings.user,
								value: 'user'
							}
						];
					}
				}
			}
		);

		var AssignmentsEditor = A.Component.create(
			{
				ATTRS: {
					editorFormClass: {
						value: AssignmentsEditorForm
					}
				},

				EXTENDS: BaseAbstractEditor,

				NAME: 'assignments-cell-editor'
			}
		);

		var FormsEditorForm = A.Component.create(
			{
				ATTRS: {
					viewTemplate: {
						value: [
							'<div class="{$ans}celleditor-forms-view {$ans}celleditor-view {$ans}celleditor-view-type-{viewId}">{content}</div>'
						]
					}
				},

				EXTENDS: BaseAbstractEditorForm,

				NAME: 'form-editor-form',

				prototype: {
					addStaticViews: function() {
						var instance = this;

						var strings = instance.getStrings();

						var formsViewTpl = instance.get('viewTemplate');

						var inputTpl = Template.get('input');

						var buffer = [];

						var formsContent = [
							inputTpl.parse(
								{
									auiCssClass: 'form-control forms-cell-editor-input input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.formTemplate,
									name: 'templateName',
									placeholder: KaleoDesignerStrings.search,
									size: 35,
									type: 'text'
								}
							),

							inputTpl.parse(
								{
									auiCssClass: 'form-control forms-cell-editor-input input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									name: 'templateId',
									size: 35,
									type: 'hidden'
								}
							)
						].join(STR_BLANK);

						buffer.push(
							formsViewTpl.parse(
								{
									content: formsContent,
									viewId: 'formTemplateId'
								}
							)
						);

						instance.appendToStaticView(buffer.join(STR_BLANK));
					}
				}
			}
		);

		var FormsEditor = A.Component.create(
			{
				ATTRS: {
					editorFormClass: {
						value: FormsEditorForm
					}

				},

				EXTENDS: BaseAbstractEditor,

				NAME: 'forms-cell-editor'
			}
		);

		var ExecutionTypesEditorFormBase = function() {
		};

		ExecutionTypesEditorFormBase.prototype = {
			_executionTypesSetter: function(val) {
				var instance = this;

				var strings = instance.getStrings();

				var selectedNode = instance.get('builder.selectedNode');
				var type = selectedNode.get('type');

				if (type === 'task') {
					val.push(
						{
							label: strings.onAssignment,
							value: 'onAssignment'
						}
					);
				}

				val.push(
					{
						label: strings.onEntry,
						value: 'onEntry'
					},
					{
						label: strings.onExit,
						value: 'onExit'
					}
				);

				return val;
			}
		};

		ExecutionTypesEditorFormBase.ATTRS = {
			executionTypes: {
				setter: '_executionTypesSetter',
				value: []
			}
		};

		var NotificationRecipientsEditorFormConfig = {
			ATTRS: {
				executionTypeSelect: {
					value: null
				},

				strings: {
					valueFn: function() {
						return A.merge(
							KaleoDesignerStrings,
							{
								assignmentTypeLabel: KaleoDesignerStrings.recipientType,
								defaultAssignmentLabel: KaleoDesignerStrings.assetCreator
							}
						);
					}
				}
			},

			EXTENDS: AssignmentsEditorForm,

			NAME: 'notification-recipients-editor-form',

			prototype: {
				addStaticViews: function() {
					var instance = this;

					var strings = instance.getStrings();

					var assignmentsViewTpl = instance.get('viewTemplate');

					var inputTpl = Template.get('input');
					var selectTpl = Template.get('select');
					var textareaTpl = Template.get('textarea');

					var select = selectTpl.render(
						{
							auiCssClass: 'form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							label: strings.assignmentTypeLabel,
							name: 'assignmentType',
							options: instance.get('assignmentsType')
						}
					);

					var selectWrapper = A.Node.create('<div/>').append(select);

					var typeSelect = selectWrapper.one('select');

					instance.set('typeSelect', typeSelect);

					instance.appendToStaticView(selectWrapper);

					typeSelect.on(['change', 'keyup'], A.bind(instance._onTypeValueChange, instance));

					var receptionType = inputTpl.parse(
						{
							id: A.guid(),
							name: 'receptionType',
							type: 'hidden'
						}
					);

					instance.appendToStaticView(receptionType);

					var buffer = [];

					var roleIdContent = [
						inputTpl.parse(
							{
								auiCssClass: 'assignments-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.role,
								name: 'roleNameAC',
								placeholder: KaleoDesignerStrings.search,
								size: 35,
								type: 'text'
							}
						),

						inputTpl.parse(
							{
								auiCssClass: 'assignments-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								disabled: true,
								id: A.guid(),
								label: strings.roleId,
								name: 'roleId',
								size: 35,
								type: 'text'
							}
						)
					].join(STR_BLANK);

					buffer.push(
						assignmentsViewTpl.parse(
							{
								content: roleIdContent,
								viewId: 'roleId'
							}
						)
					);

					var scriptedRecipientContent = [
						textareaTpl.parse(
							{
								auiCssClass: 'celleditor-textarea-small form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.script,
								name: 'script'
							}
						),

						selectTpl.parse(
							{
								auiCssClass: 'form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.scriptLanguage,
								name: 'scriptLanguage',
								options: instance.get('scriptLanguages')
							}
						)
					].join(STR_BLANK);

					buffer.push(
						assignmentsViewTpl.parse(
							{
								content: scriptedRecipientContent,
								viewId: 'scriptedRecipient'
							}
						)
					);

					instance.appendToStaticView(buffer.join(STR_BLANK));
				},

				_valueAssignmentsType: function() {
					var instance = this;

					var strings = instance.getStrings();

					var assignmentsTypes = [
						{
							label: strings.defaultAssignmentLabel,
							value: STR_BLANK
						},
						{
							label: strings.role,
							value: 'roleId'
						},
						{
							label: strings.roleType,
							value: 'roleType'
						},
						{
							label: strings.scriptedRecipient,
							value: 'scriptedRecipient'
						},
						{
							label: strings.user,
							value: 'user'
						}
					];

					var executionTypeSelect = instance.get('executionTypeSelect');

					var executionType = executionTypeSelect.val();

					if (executionType === 'onAssignment') {
						assignmentsTypes.push(
							{
								label: KaleoDesignerStrings.taskAssignees,
								value: 'taskAssignees'
							}
						);
					}

					return assignmentsTypes;
				}
			}
		};

		var NotificationRecipientsEditorForm = A.Component.create(NotificationRecipientsEditorFormConfig);

		NotificationRecipientsEditorFormConfig.prototype._valueAssignmentsType = function() {
			var instance = this;

			var strings = instance.getStrings();

			var assignmentsTypes = [
				{
					label: strings.defaultAssignmentLabel,
					value: STR_BLANK
				},
				{
					label: strings.role,
					value: 'roleId'
				},
				{
					label: strings.roleType,
					value: 'roleType'
				},
				{
					label: strings.scriptedRecipient,
					value: 'scriptedRecipient'
				},
				{
					label: strings.user,
					value: 'user'
				}
			];

			return assignmentsTypes;
		};

		var TimerNotificationRecipientsEditorForm = A.Component.create(NotificationRecipientsEditorFormConfig);

		var NotificationsEditorFormConfig = {
			ATTRS: {
				notificationTypes: {
					valueFn: function() {
						var instance = this;

						var strings = instance.getStrings();

						return [
							{
								label: strings.email,
								value: 'email'
							},
							{
								label: strings.im,
								value: 'im'
							},
							{
								label: strings.privateMessage,
								value: 'private-message'
							},
							{
								label: strings.userNotification,
								value: 'user-notification'
							}
						];
					}
				},

				recipients: {
					getter: '_getRecipients',
					value: []
				},

				templateLanguages: {
					valueFn: function() {
						var instance = this;

						var strings = instance.getStrings();

						return [
							{
								label: strings.freemarker,
								value: 'freemarker'
							},
							{
								label: strings.text,
								value: 'text'
							},
							{
								label: strings.velocity,
								value: 'velocity'
							}
						];
					}
				},

				viewTemplate: {
					value: [
						'<div class="{$ans}celleditor-notifications-view {$ans}celleditor-view {$ans}celleditor-view-type-{viewId}">',
						'{content}',
						'<div class="recipients-editor-container"></div>',
						'</div>'
					]
				}
			},

			AUGMENTS: [CompositeEditorFormBase, ExecutionTypesEditorFormBase],

			EXTENDS: BaseAbstractEditorForm,

			NAME: 'notifications-editor-form',

			prototype: {
				addDynamicViews: function(val) {
					var instance = this;

					instance.removeAllViews('notification');

					instance.addNotificationView(instance._countNotificationViews(val));
				},

				addNotificationView: function(num) {
					var instance = this;

					num = num || 1;

					var strings = instance.getStrings();

					var notificationsViewTpl = instance.get('viewTemplate');

					var inputTpl = Template.get('input');
					var selectMultipleTpl = Template.get('select-multiple');
					var selectTpl = Template.get('select');
					var textareaTpl = Template.get('textarea');

					var buffer = [];

					for (var i = 0; i < num; i++) {
						var notificationContent = [
							inputTpl.parse(
								{
									auiCssClass: 'form-control input-sm notifications-cell-editor-input',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.name,
									name: 'name',
									size: 35,
									type: 'text'
								}
							),

							textareaTpl.parse(
								{
									auiCssClass: 'celleditor-textarea-small form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.description,
									name: 'description'
								}
							),

							selectTpl.parse(
								{
									auiCssClass: 'form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.templateLanguage,
									name: 'templateLanguage',
									options: instance.get('templateLanguages')
								}
							),

							textareaTpl.parse(
								{
									auiCssClass: 'celleditor-textarea-small form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.template,
									name: 'template'
								}
							),

							selectMultipleTpl.parse(
								{
									auiCssClass: 'form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.notificationType,
									multiple: true,
									name: 'notificationTypes',
									options: instance.get('notificationTypes')
								}
							),

							selectTpl.parse(
								{
									auiCssClass: 'form-control input-sm execution-type-select',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.executionType,
									name: 'executionType',
									options: instance.get('executionTypes')
								}
							)
						].join(STR_BLANK);

						buffer.push(
							notificationsViewTpl.parse(
								{
									content: notificationContent,
									viewId: 'notification'
								}
							)
						);
					}

					instance.appendToDynamicView(buffer.join(STR_BLANK));
				},

				getValue: function() {
					var instance = this;

					var localRecipients = instance.get('recipients');

					var recipients = [];

					instance.getDynamicViews().each(
						function(item, index, collection) {
							var editorContainer = item.one('.recipients-editor-container');

							var recipientsEditor = instance.getEmbeddedEditorForm(NotificationRecipientsEditorForm, editorContainer);

							if (recipientsEditor) {
								recipients.push(recipientsEditor.getValue());
							}

							localRecipients[index] = recipientsEditor.getValue();
						}
					);

					instance.set('recipients', localRecipients);

					return A.merge(
						NotificationsEditorForm.superclass.getValue.apply(this, arguments),
						{
							recipients: recipients
						}
					);
				},

				handleAddViewSection: function(event) {
					var instance = this;

					var button = event.target;

					if (!button.get('disabled')) {
						instance.addNotificationView();
					}

					instance._appendRecipientsEditorToLastSection();
				},

				syncElementsFocus: function() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					bodyNode.one(':input').focus();
				},

				syncToolbarUI: function() {
					var instance = this;

					var addSectionButton = instance.get('addSectionButton');

					if (addSectionButton) {
						addSectionButton.set('disabled', false);
					}
				},

				syncViewsUI: function() {
					var instance = this;

					NotificationsEditorForm.superclass.syncViewsUI.apply(this, arguments);

					instance._renderRecipientsEditor();
				},

				_appendRecipientsEditorToLastSection: function() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'notification');

					var lastDynamicView = dynamicViews.item(dynamicViews.size() - 1);

					instance._showRecipientsEditor(lastDynamicView);
				},

				_countNotificationViews: function(val) {
					var instance = this;

					var count = 0;

					if (val) {
						count = val.notificationTypes ? val.notificationTypes.filter(isValue).length : 1;
					}

					return count;
				},

				_getRecipients: function(val) {
					var instance = this;

					return instance.get('value.recipients') || val;
				},

				_renderRecipientsEditor: function() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'notification');

					dynamicViews.each(A.bind(instance._showRecipientsEditor, instance));
				},

				_showRecipientsEditor: function(bodyContentNode, index) {
					var instance = this;

					var executionTypeSelect = bodyContentNode.one('.execution-type-select');

					var editorContainer = bodyContentNode.one('.recipients-editor-container');

					var recipients = instance.get('recipients');

					var value = recipients[index];

					instance.showEditorForm(
						NotificationRecipientsEditorForm,
						editorContainer,
						value,
						{
							executionTypeSelect: executionTypeSelect
						}
					);
				}
			}
		};

		var NotificationsEditorForm = A.Component.create(NotificationsEditorFormConfig);

		var NotificationsEditor = A.Component.create(
			{
				ATTRS: {
					cssClass: {
						value: 'tall-editor'
					},

					editorFormClass: {
						value: NotificationsEditorForm
					}
				},

				AUGMENTS: [A.WidgetCssClass],

				EXTENDS: BaseAbstractEditor,

				NAME: 'notifications-cell-editor'
			}
		);

		NotificationsEditorFormConfig.prototype.addNotificationView = function(num) {
			var instance = this;

			num = num || 1;

			var strings = instance.getStrings();

			var notificationsViewTpl = instance.get('viewTemplate');

			var inputTpl = Template.get('input');
			var selectMultipleTpl = Template.get('select-multiple');
			var selectTpl = Template.get('select');
			var textareaTpl = Template.get('textarea');

			var buffer = [];

			for (var i = 0; i < num; i++) {
				var notificationContent = [
					inputTpl.parse(
						{
							auiCssClass: 'form-control input-sm notifications-cell-editor-input',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.name,
							name: 'name',
							size: 35,
							type: 'text'
						}
					),

					textareaTpl.parse(
						{
							auiCssClass: 'celleditor-textarea-small form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.description,
							name: 'description'
						}
					),

					selectTpl.parse(
						{
							auiCssClass: 'form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.templateLanguage,
							name: 'templateLanguage',
							options: instance.get('templateLanguages')
						}
					),

					textareaTpl.parse(
						{
							auiCssClass: 'celleditor-textarea-small form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.template,
							name: 'template'
						}
					),

					selectMultipleTpl.parse(
						{
							auiCssClass: 'form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.notificationType,
							multiple: true,
							name: 'notificationTypes',
							options: instance.get('notificationTypes')
						}
					)
				].join(STR_BLANK);

				buffer.push(
					notificationsViewTpl.parse(
						{
							content: notificationContent,
							viewId: 'notification'
						}
					)
				);
			}

			instance.appendToDynamicView(buffer.join(STR_BLANK));
		};

		NotificationsEditorFormConfig.prototype.getValue = function() {
			var instance = this;

			var localRecipients = instance.get('recipients');

			var recipients = [];

			instance.getDynamicViews().each(
				function(item, index, collection) {
					var editorContainer = item.one('.recipients-editor-container');

					var recipientsEditor = instance.getEmbeddedEditorForm(TimerNotificationRecipientsEditorForm, editorContainer);

					if (recipientsEditor) {
						recipients.push(recipientsEditor.getValue());
					}

					localRecipients[index] = recipientsEditor.getValue();
				}
			);

			instance.set('recipients', localRecipients);

			return A.merge(
				NotificationsEditorForm.superclass.getValue.apply(this, arguments),
				{
					recipients: recipients
				}
			);
		};

		NotificationsEditorFormConfig.prototype._showRecipientsEditor = function(bodyContentNode, index) {
			var instance = this;

			var executionTypeSelect = bodyContentNode.one('.execution-type-select');

			var editorContainer = bodyContentNode.one('.recipients-editor-container');

			var recipients = instance.get('recipients');

			var value = recipients[index];

			instance.showEditorForm(
				TimerNotificationRecipientsEditorForm,
				editorContainer,
				value,
				{
					executionTypeSelect: executionTypeSelect
				}
			);
		};

		var TimerNotificationsEditorForm = A.Component.create(NotificationsEditorFormConfig);

		var ActionsEditorFormConfig = {
			ATTRS: {
				scriptLanguages: {
					valueFn: function() {
						var instance = this;

						var strings = instance.getStrings();

						return [
							{
								label: strings.beanshell,
								value: 'beanshell'
							},
							{
								label: strings.drl,
								value: 'drl'
							},
							{
								label: strings.groovy,
								value: 'groovy'
							},
							{
								label: strings.java,
								value: 'java'
							},
							{
								label: strings.javascript,
								value: 'javascript'
							},
							{
								label: strings.python,
								value: 'python'
							},
							{
								label: strings.ruby,
								value: 'ruby'
							}
						];
					}
				},

				viewTemplate: {
					value: [
						'<div class="{$ans}celleditor-actions-view {$ans}celleditor-view {$ans}celleditor-view-type-{viewId}">',
						'{content}',
						'</div>'
					]
				}
			},

			AUGMENTS: [ExecutionTypesEditorFormBase],

			EXTENDS: BaseAbstractEditorForm,

			NAME: 'actions-editor-form',

			prototype: {
				addActionView: function(num) {
					var instance = this;

					num = num || 1;

					var strings = instance.getStrings();

					var actionsViewTpl = instance.get('viewTemplate');

					var inputTpl = Template.get('input');
					var selectTpl = Template.get('select');
					var textareaTpl = Template.get('textarea');

					var buffer = [];

					for (var i = 0; i < num; i++) {
						var actionContent = [
							inputTpl.parse(
								{
									auiCssClass: 'actions-cell-editor-input form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.name,
									name: 'name',
									size: 35,
									type: 'text'
								}
							),

							textareaTpl.parse(
								{
									auiCssClass: 'celleditor-textarea-small form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.description,
									name: 'description'
								}
							),

							textareaTpl.parse(
								{
									auiCssClass: 'celleditor-textarea-small form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.script,
									name: 'script'
								}
							),

							selectTpl.parse(
								{
									auiCssClass: 'form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.scriptLanguage,
									name: 'scriptLanguage',
									options: instance.get('scriptLanguages')
								}
							),

							selectTpl.parse(
								{
									auiCssClass: 'form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.executionType,
									name: 'executionType',
									options: instance.get('executionTypes')
								}
							),

							inputTpl.parse(
								{
									auiCssClass: 'actions-cell-editor-input form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.priority,
									name: 'priority',
									size: 35,
									type: 'text'
								}
							)
						].join(STR_BLANK);

						buffer.push(
							actionsViewTpl.parse(
								{
									content: actionContent,
									viewId: 'action'
								}
							)
						);
					}

					instance.appendToDynamicView(buffer.join(STR_BLANK));
				},

				addDynamicViews: function(val) {
					var instance = this;

					instance.removeAllViews('action');

					instance.addActionView(instance._countActionViews(val));
				},

				handleAddViewSection: function(event) {
					var instance = this;

					var button = event.target;

					if (!button.get('disabled')) {
						instance.addActionView();
					}
				},

				syncElementsFocus: function() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					bodyNode.one(':input').focus();
				},

				syncToolbarUI: function() {
					var instance = this;

					var addSectionButton = instance.get('addSectionButton');

					if (addSectionButton) {
						addSectionButton.set('disabled', false);
					}
				},

				_countActionViews: function(val) {
					var instance = this;

					var count = 0;

					if (val) {
						count = val.name ? val.name.filter(isValue).length : 1;
					}

					return count;
				}
			}
		};

		var ActionsEditorForm = A.Component.create(ActionsEditorFormConfig);

		var ActionsEditor = A.Component.create(
			{
				ATTRS: {
					cssClass: {
						value: 'tall-editor'
					},

					editorFormClass: {
						value: ActionsEditorForm
					}
				},

				AUGMENTS: [A.WidgetCssClass],

				EXTENDS: BaseAbstractEditor,

				NAME: 'actions-cell-editor'
			}
		);

		ActionsEditorFormConfig.prototype.addActionView = function(num) {
			var instance = this;

			num = num || 1;

			var strings = instance.getStrings();

			var actionsViewTpl = instance.get('viewTemplate');

			var inputTpl = Template.get('input');
			var selectTpl = Template.get('select');
			var textareaTpl = Template.get('textarea');

			var buffer = [];

			for (var i = 0; i < num; i++) {
				var actionContent = [
					inputTpl.parse(
						{
							auiCssClass: 'actions-cell-editor-input form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.name,
							name: 'name',
							size: 35,
							type: 'text'
						}
					),

					textareaTpl.parse(
						{
							auiCssClass: 'celleditor-textarea-small form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.description,
							name: 'description'
						}
					),

					textareaTpl.parse(
						{
							auiCssClass: 'celleditor-textarea-small form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.script,
							name: 'script'
						}
					),

					selectTpl.parse(
						{
							auiCssClass: 'form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.scriptLanguage,
							name: 'scriptLanguage',
							options: instance.get('scriptLanguages')
						}
					),

					inputTpl.parse(
						{
							auiCssClass: 'actions-cell-editor-input form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.priority,
							name: 'priority',
							size: 35,
							type: 'text'
						}
					)
				].join(STR_BLANK);

				buffer.push(
					actionsViewTpl.parse(
						{
							content: actionContent,
							viewId: 'action'
						}
					)
				);
			}

			instance.appendToDynamicView(buffer.join(STR_BLANK));
		};

		var TimerActionsEditorForm = A.Component.create(ActionsEditorFormConfig);

		var TaskTimerActionsEditorForm = A.Component.create(
			{
				ATTRS: {
					actionTypes: {
						valueFn: function() {
							var instance = this;

							var strings = instance.getStrings();

							return [
								{
									label: strings.action,
									value: 'action'
								},

								{
									label: strings.notification,
									value: 'notification'
								},

								{
									label: strings.reassignment,
									value: 'reassignment'
								}
							];
						}
					},

					editorFormClasses: {
						value: {
							action: TimerActionsEditorForm,
							notification: TimerNotificationsEditorForm,
							reassignment: AssignmentsEditorForm
						}
					},

					viewTemplate: {
						value: [
							'<div class="{$ans}celleditor-task-timer-actions-view {$ans}celleditor-view {$ans}celleditor-view-type-{viewId}">',
							'{content}',
							'<div class="editor-container editor-container-action"></div>',
							'<div class="editor-container editor-container-notification"></div>',
							'<div class="editor-container editor-container-reassignment"></div>',
							'</div>'
						]
					}
				},

				AUGMENTS: [CompositeEditorFormBase],

				EXTENDS: BaseAbstractEditorForm,

				NAME: 'task-timer-actions-editor-form',

				prototype: {
					initializer: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						bodyNode.delegate(['change', 'keyup'], A.bind(instance._onActionTypeValueChange, instance), '.select-action-type');
					},

					addDynamicViews: function(val) {
						var instance = this;

						instance.removeAllViews('timerAction');

						instance.addTimerActionView(instance._countTimerActionViews(val));
					},

					addTimerActionView: function(num) {
						var instance = this;

						num = num || 1;

						var strings = instance.getStrings();

						var timerActionViewTpl = instance.get('viewTemplate');

						var selectTpl = Template.get('select');

						var buffer = [];

						for (var i = 0; i < num; i++) {
							var timerActionContent = [
								selectTpl.parse(
									{
										auiCssClass: 'form-control input-sm select-action-type',
										auiLabelCssClass: 'celleditor-label',
										id: A.guid(),
										label: strings.type,
										name: 'actionType',
										options: instance.get('actionTypes')
									}
								)
							].join(STR_BLANK);

							buffer.push(
								timerActionViewTpl.parse(
									{
										content: timerActionContent,
										viewId: 'timerAction'
									}
								)
							);
						}

						instance.appendToDynamicView(buffer.join(STR_BLANK));
					},

					getValue: function() {
						var instance = this;

						var value = {
							actionType: [],
							timerAction: []
						};

						var dynamicViews = instance.getDynamicViews();

						dynamicViews.each(
							function(item, index) {
								var actionTypeSelect = item.one('.select-action-type');

								var actionType = actionTypeSelect.val();

								value.actionType.push(actionType);

								var editorContainer = item.one('.editor-container-' + actionType);

								var editorFormClass = instance.get('editorFormClasses.' + actionType);

								var editor = instance.getEmbeddedEditorForm(editorFormClass, editorContainer);

								value.timerAction.push(editor.getValue());
							}
						);

						return value;
					},

					handleAddViewSection: function(event) {
						var instance = this;

						var button = event.target;

						if (!button.get('disabled')) {
							instance.addTimerActionView();
						}

						instance._appendEditorToLastSection();

						instance._displayEditors();
					},

					syncToolbarUI: function() {
						var instance = this;

						var addSectionButton = instance.get('addSectionButton');

						if (addSectionButton) {
							addSectionButton.set('disabled', false);
						}
					},

					syncViewsUI: function() {
						var instance = this;

						TaskTimerActionsEditorForm.superclass.syncViewsUI.apply(this, arguments);

						instance._renderEditor();

						instance._displayEditors();
					},

					_appendEditorToLastSection: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timerAction');

						var lastDynamicView = dynamicViews.item(dynamicViews.size() - 1);

						instance._showEditor(lastDynamicView);
					},

					_countTimerActionViews: function(val) {
						var instance = this;

						var count = 0;

						if (val) {
							count = val.actionType ? val.actionType.filter(isValue).length : 1;
						}

						return count;
					},

					_displayEditor: function(dynamicViewNode) {
						var instance = this;

						var actionTypeSelect = dynamicViewNode.one('.select-action-type');

						var actionType = actionTypeSelect.val();

						dynamicViewNode.all('.editor-container').hide();

						dynamicViewNode.all('.editor-container-' + actionType).show();
					},

					_displayEditors: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timerAction');

						dynamicViews.each(A.bind(instance._displayEditor, instance));
					},

					_onActionTypeValueChange: function(event) {
						var instance = this;

						var actionTypeSelect = event.currentTarget;

						var dynamicViewNode = actionTypeSelect.ancestor('.celleditor-task-timer-actions-view');

						instance._showEditor(dynamicViewNode);

						instance._displayEditor(dynamicViewNode);
					},

					_renderEditor: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timerAction');

						dynamicViews.each(A.bind(instance._showEditor, instance));
					},

					_showEditor: function(bodyContentNode, index) {
						var instance = this;

						var actionType;

						var timerAction;

						var value = instance.get('value');

						if (value && value.actionType && value.actionType[index]) {
							actionType = value.actionType[index];
							timerAction = value.timerAction[index];
						}
						else {
							var actionTypeSelect = bodyContentNode.one('.select-action-type');

							actionType = actionTypeSelect.val();
						}

						var editorFormClass = instance.get('editorFormClasses.' + actionType);

						var editorContainer = bodyContentNode.one('.editor-container-' + actionType);

						instance.showEditorForm(editorFormClass, editorContainer, timerAction);
					}
				}
			}
		);

		var TaskTimerDelaysEditorForm = A.Component.create(
			{
				ATTRS: {
					scales: {
						valueFn: function() {
							var instance = this;

							var strings = instance.getStrings();

							return [
								{
									label: strings.second,
									value: 'second'
								},

								{
									label: strings.minute,
									value: 'minute'
								},

								{
									label: strings.hour,
									value: 'hour'
								},

								{
									label: strings.day,
									value: 'day'
								},

								{
									label: strings.week,
									value: 'week'
								},

								{
									label: strings.month,
									value: 'month'
								},

								{
									label: strings.year,
									value: 'year'
								}
							];
						}
					},

					viewTemplate: {
						value: [
							'<div class="{$ans}celleditor-task-timer-delays-view {$ans}celleditor-view {$ans}celleditor-view-type-{viewId}">',
							'{content}',
							'</div>'
						]
					}
				},

				EXTENDS: BaseAbstractEditorForm,

				NAME: 'task-timer-delays-editor-form',

				prototype: {
					addDynamicViews: function(val) {
						var instance = this;

						instance.removeAllViews('recurrence');

						instance.addRecurrenceView(instance._countRecurrenceViews(val));
					},

					addRecurrenceView: function(num) {
						var instance = this;

						num = num || 0;

						var timersViewTpl = instance.get('viewTemplate');

						var buffer = [];

						for (var i = 0; i < num; i++) {
							var delayContent = instance.getDelayContent();

							buffer.push(
								timersViewTpl.parse(
									{
										content: delayContent,
										viewId: 'recurrence'
									}
								)
							);
						}

						instance.appendToDynamicView(buffer.join(STR_BLANK));
					},

					addStaticViews: function(val) {
						var instance = this;

						var delayContent = instance.getDelayContent();

						instance.appendToStaticView(delayContent);
					},

					getDelayContent: function() {
						var instance = this;

						var strings = instance.getStrings();

						var inputTpl = Template.get('input');
						var selectTpl = Template.get('select');

						return [
							inputTpl.parse(
								{
									auiCssClass: 'form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.duration,
									name: 'duration'
								}
							),

							selectTpl.parse(
								{
									auiCssClass: 'form-control input-sm',
									auiLabelCssClass: 'celleditor-label',
									id: A.guid(),
									label: strings.scale,
									name: 'scale',
									options: instance.get('scales')
								}
							)
						].join(STR_BLANK);
					},

					handleAddViewSection: function(event) {
						var instance = this;

						var button = event.target;

						if (!button.get('disabled')) {
							instance.addRecurrenceView(1);
						}
					},

					syncToolbarUI: function() {
						var instance = this;

						var addSectionButton = instance.get('addSectionButton');

						if (addSectionButton) {
							addSectionButton.set('disabled', false);
						}
					},

					_countRecurrenceViews: function(val) {
						var instance = this;

						var count = 0;

						if (val) {
							count = val.duration ? val.duration.filter(isValue).length - 1 : 0;
						}

						return count;
					}
				}
			}
		);

		var TaskTimersEditorForm = A.Component.create(
			{
				ATTRS: {
					delays: {
						getter: '_getDelays',
						value: []
					},

					timerActions: {
						getter: '_getTimerActions',
						value: []
					},

					viewTemplate: {
						value: [
							'<div class="{$ans}celleditor-task-timers-view {$ans}celleditor-view {$ans}celleditor-view-type-{viewId}">',
							'{content}',
							'</div>'
						]
					}
				},

				AUGMENTS: [CompositeEditorFormBase],

				EXTENDS: BaseAbstractEditorForm,

				NAME: 'task-timers-editor-form',

				prototype: {
					addDynamicViews: function(val) {
						var instance = this;

						instance.removeAllViews('timer');

						instance.addTaskTimerView(instance._countTimerViews(val));
					},

					addTaskTimerView: function(num) {
						var instance = this;

						num = num || 1;

						var strings = instance.getStrings();

						var timersViewTpl = instance.get('viewTemplate');

						var checkboxTpl = Template.get('checkbox');
						var inputTpl = Template.get('input');
						var textareaTpl = Template.get('textarea');

						var buffer = [];

						for (var i = 0; i < num; i++) {
							var taskTimerContent = [
								inputTpl.parse(
									{
										auiCssClass: 'task-timers-cell-editor-input form-control input-sm',
										auiLabelCssClass: 'celleditor-label',
										id: A.guid(),
										label: strings.name,
										name: 'name',
										size: 35,
										type: 'text'
									}
								),

								textareaTpl.parse(
									{
										auiCssClass: 'task-timers-cell-editor-input celleditor-textarea-small form-control input-sm',
										auiLabelCssClass: 'celleditor-label',
										id: A.guid(),
										label: strings.description,
										name: 'description'
									}
								),

								'<div class="delays-editor-container"></div>',

								checkboxTpl.parse(
									{
										auiCssClass: 'task-timers-cell-editor-input',
										auiLabelCssClass: 'celleditor-label-checkbox',
										checked: false,
										id: A.guid(),
										label: strings.blocking,
										name: 'blocking',
										type: 'checkbox'
									}
								),

								'<div class="timer-actions-editor-container"></div>'
							].join(STR_BLANK);

							buffer.push(
								timersViewTpl.parse(
									{
										content: taskTimerContent,
										viewId: 'timer'
									}
								)
							);
						}

						instance.appendToDynamicView(buffer.join(STR_BLANK));
					},

					getValue: function() {
						var instance = this;

						var value = {
							blocking: [],
							delay: [],
							description: [],
							name: [],
							reassignments: [],
							timerActions: [],
							timerNotifications: []
						};

						var bodyNode = instance.get('bodyNode');

						var taskTimerInputs = bodyNode.all('.task-timers-cell-editor-input');

						taskTimerInputs.each(
							function(item, index, collection) {
								if (item.get('type') && (item.get('type') === 'checkbox')) {
									value[item.get('name')].push(item.get('checked'));
								}
								else {
									value[item.get('name')].push(item.val());
								}
							}
						);

						var dynamicViews = instance.getDynamicViews();

						dynamicViews.each(
							function(item1, index1) {
								var delaysEditorContainer = item1.one('.delays-editor-container');

								var delaysEditorForm = instance.getEmbeddedEditorForm(TaskTimerDelaysEditorForm, delaysEditorContainer);

								value.delay.push(delaysEditorForm.getValue());

								var timerActionsEditorContainer = item1.one('.timer-actions-editor-container');

								var timerActionsEditorForm = instance.getEmbeddedEditorForm(TaskTimerActionsEditorForm, timerActionsEditorContainer);

								value.timerActions.push({});

								value.timerNotifications.push({});

								value.reassignments.push({});

								var timerActionValue = timerActionsEditorForm.getValue();

								timerActionValue.actionType.forEach(
									function(actionType, index2, collection2) {
										var timerAction = timerActionValue.timerAction[index2];

										var object;

										if (actionType === 'action') {
											object = value.timerActions[index1];
										}
										else if (actionType === 'notification') {
											object = value.timerNotifications[index1];
										}
										else if (actionType === 'reassignment') {
											object = value.reassignments[index1];
										}

										A.each(
											timerAction,
											function(value, key) {
												instance._put(object, key, value[0]);
											}
										);
									}
								);
							}
						);

						return value;
					},

					handleAddViewSection: function(event) {
						var instance = this;

						var button = event.target;

						if (!button.get('disabled')) {
							instance.addTaskTimerView();
						}

						instance._appendDelaysEditorToLastSection();

						instance._appendTimerActionsEditorToLastSection();
					},

					syncElementsFocus: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						bodyNode.one(':input').focus();
					},

					syncToolbarUI: function() {
						var instance = this;

						var addSectionButton = instance.get('addSectionButton');

						if (addSectionButton) {
							addSectionButton.set('disabled', false);
						}
					},

					syncViewsUI: function() {
						var instance = this;

						TaskTimersEditorForm.superclass.syncViewsUI.apply(this, arguments);

						instance._renderDelaysEditor();

						instance._renderTimerActionsEditor();
					},

					_appendDelaysEditorToLastSection: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timer');

						var lastDynamicView = dynamicViews.item(dynamicViews.size() - 1);

						instance._showDelaysEditor(lastDynamicView);
					},

					_appendTimerActionsEditorToLastSection: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timer');

						var lastDynamicView = dynamicViews.item(dynamicViews.size() - 1);

						instance._showTimerActionsEditor(lastDynamicView);
					},

					_countTimerViews: function(val) {
						var instance = this;

						var count = 0;

						if (val) {
							count = val.name ? val.name.filter(isValue).length : 1;
						}

						return count;
					},

					_getDelays: function(val) {
						var instance = this;

						return instance.get('value.delay') || val;
					},

					_getTimerActions: function(val) {
						var instance = this;

						var actions = instance.get('value.timerActions') || [];

						var notifications = instance.get('value.timerNotifications') || [];

						var reassignments = instance.get('value.reassignments') || [];

						var count = Math.max(
							actions.length,
							notifications.length,
							reassignments.length
						);

						var timerActions = [];

						for (var i = 0; i < count; i++) {
							var actionType = [];

							var splitTimerActions;

							var timerAction = [];

							if (reassignments[i].assignmentType && reassignments[i].assignmentType[0] && reassignments[i].assignmentType[0] !== '') {
								splitTimerActions = instance._splitTimerActions(reassignments[i]);

								actionType = actionType.concat(instance._repeat('reassignment', splitTimerActions.length));

								timerAction = timerAction.concat(splitTimerActions);
							}

							if (notifications[i].notificationType) {
								splitTimerActions = instance._splitTimerActions(notifications[i]);

								actionType = actionType.concat(instance._repeat('notification', splitTimerActions.length));

								timerAction = timerAction.concat(splitTimerActions);
							}

							if (actions[i].name) {
								splitTimerActions = instance._splitTimerActions(actions[i]);

								actionType = actionType.concat(instance._repeat('action', splitTimerActions.length));

								timerAction = timerAction.concat(splitTimerActions);
							}

							timerActions.push(
								{
									actionType: actionType,
									timerAction: timerAction
								}
							);
						}

						return timerActions;
					},

					_put: function(obj, key, value) {
						var instance = this;

						obj[key] = obj[key] || [];

						obj[key].push(value);
					},

					_renderDelaysEditor: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timer');

						dynamicViews.each(A.bind(instance._showDelaysEditor, instance));
					},

					_renderTimerActionsEditor: function() {
						var instance = this;

						var bodyNode = instance.get('bodyNode');

						var dynamicViews = bodyNode.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timer');

						dynamicViews.each(A.bind(instance._showTimerActionsEditor, instance));
					},

					_repeat: function(value, times) {
						var instance = this;

						var array = [];

						for (var i = 0; i < times; i++) {
							array.push(value);
						}

						return array;
					},

					_showDelaysEditor: function(bodyContentNode, index) {
						var instance = this;

						var editorContainer = bodyContentNode.one('.delays-editor-container');

						var delays = instance.get('delays');

						var value = delays[index];

						instance.showEditorForm(TaskTimerDelaysEditorForm, editorContainer, value);
					},

					_showTimerActionsEditor: function(bodyContentNode, index) {
						var instance = this;

						var editorContainer = bodyContentNode.one('.timer-actions-editor-container');

						var timerActions = instance.get('timerActions');

						var value = timerActions[index];

						instance.showEditorForm(TaskTimerActionsEditorForm, editorContainer, value);
					},

					_splitTimerActions: function(timerActions) {
						var instance = this;

						var splitTimerActions = [];

						A.each(
							timerActions,
							function(item1, index1, collection1) {
								item1.forEach(
									function(item2, index2, collection2) {
										if (!splitTimerActions[index2]) {
											splitTimerActions[index2] = {};
										}

										var timerAction = splitTimerActions[index2];

										if (!timerAction[index1]) {
											timerAction[index1] = [];
										}

										timerAction[index1][0] = item2;
									}
								);
							}
						);

						return splitTimerActions;
					}
				}
			}
		);

		var TaskTimersEditor = A.Component.create(
			{
				ATTRS: {
					cssClass: {
						value: 'tall-editor'
					},

					editorFormClass: {
						value: TaskTimersEditorForm
					}
				},

				AUGMENTS: [A.WidgetCssClass],

				EXTENDS: BaseAbstractEditor,

				NAME: 'task-timers-cell-editor'
			}
		);

		var ScriptEditor = A.Component.create(
			{
				EXTENDS: A.BaseCellEditor,

				NAME: 'script-cell-editor',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.editor = new A.AceEditor(
							{
								height: 300,
								width: 550
							}
						);
					},

					getValue: function() {
						var instance = this;

						return instance.editor.get('value');
					},

					_afterRender: function() {
						var instance = this;

						var editor = instance.editor;

						ScriptEditor.superclass._afterRender.apply(this, arguments);

						instance.setStdModContent(WidgetStdMod.BODY, STR_BLANK, WidgetStdMod.AFTER);

						setTimeout(
							function() {
								editor.render(instance.bodyNode);
							},
							0
						);
					},

					_syncElementsFocus: emptyFn,

					_uiSetValue: function(val) {
						var instance = this;

						var editor = instance.editor;

						if (editor && isValue(val)) {
							editor.set('value', val);
						}
					}
				}
			}
		);

		Liferay.KaleoDesignerEditors = {
			ActionsEditor: ActionsEditor,
			AssignmentsEditor: AssignmentsEditor,
			BaseAbstractEditor: BaseAbstractEditor,
			CompositeEditorFormBase: CompositeEditorFormBase,
			ExecutionTypesEditorFormBase: ExecutionTypesEditorFormBase,
			FormsEditor: FormsEditor,
			NotificationsEditor: NotificationsEditor,
			ScriptEditor: ScriptEditor,
			TaskTimersEditor: TaskTimersEditor
		};
	},
	'',
	{
		requires: ['aui-ace-editor', 'aui-ace-editor-mode-xml', 'aui-base', 'aui-datatype', 'aui-node', 'liferay-kaleo-designer-autocomplete-util', 'liferay-kaleo-designer-templates', 'liferay-kaleo-designer-utils']
	}
);