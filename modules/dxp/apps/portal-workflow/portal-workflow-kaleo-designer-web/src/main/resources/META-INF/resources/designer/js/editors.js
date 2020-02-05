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
	'liferay-kaleo-designer-editors',
	A => {
		var AArray = A.Array;
		var AObject = A.Object;
		var getClassName = A.getClassName;
		var Lang = A.Lang;
		var Template = A.Template;
		var WidgetStdMod = A.WidgetStdMod;

		var emptyFn = Lang.emptyFn;
		var isBoolean = Lang.isBoolean;
		var isValue = Lang.isValue;

		var KaleoDesignerRemoteServices = Liferay.KaleoDesignerRemoteServices;
		var KaleoDesignerStrings = Liferay.KaleoDesignerStrings;

		var serializeForm = Liferay.KaleoDesignerUtils.serializeForm;

		var CSS_CELLEDITOR_ASSIGNMENT_VIEW = getClassName(
			'celleditor',
			'assignment',
			'view'
		);

		var CSS_CELLEDITOR_VIEW_TYPE = getClassName(
			'celleditor',
			'view',
			'type'
		);

		var STR_BLANK = '';

		var STR_DASH = '-';

		var STR_DOT = '.';

		var STR_REMOVE_DYNAMIC_VIEW_BUTTON =
			'<div class="celleditor-view-menu">' +
			'<a class="celleditor-view-menu-remove btn btn-link btn-sm" href="#" title="' +
			KaleoDesignerStrings.remove +
			'">' +
			Liferay.Util.getLexiconIconTpl('times') +
			'</a>' +
			'</div>';

		var SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE =
			STR_DOT + CSS_CELLEDITOR_VIEW_TYPE + STR_DASH;

		var BaseAbstractEditor = A.Component.create({
			ATTRS: {
				builder: {},

				editorForm: {},

				editorFormClass: {},

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
				_afterEditorVisibleChange(event) {
					var instance = this;

					if (event.newVal) {
						var editorForm = instance.get('editorForm');

						editorForm.syncViewsUI();
					}
				},

				_afterRender() {
					var instance = this;

					BaseAbstractEditor.superclass._afterRender.apply(
						this,
						arguments
					);

					var editorForm = instance.get('editorForm');

					editorForm.addStaticViews();

					instance.customizeToolbar();

					editorForm.syncToolbarUI();

					editorForm.syncViewsUI();
				},

				_getEditorForm(config) {
					var instance = this;

					var editorFormClass = instance.get('editorFormClass');

					var editorForm = new editorFormClass(config);

					var bodyNode = editorForm.get('bodyNode');

					instance.set('bodyContent', bodyNode);

					return editorForm;
				},

				_onClickViewMenu(event) {
					var anchor = event.currentTarget;

					if (anchor.hasClass('celleditor-view-menu-remove')) {
						anchor.ancestor('.celleditor-view').remove();
					}

					event.halt();
				},

				_onDestroyPortlet() {
					var instance = this;

					instance.destroy(true);
				},

				_onValueChange(event) {
					var instance = this;

					var editorForm = instance.get('editorForm');

					editorForm.set('value', event.newVal);
				},

				_setViewTemplate(val) {
					if (!A.instanceOf(val, A.Template)) {
						val = new Template(val);
					}

					return val;
				},

				_syncElementsFocus() {
					var instance = this;

					var editorForm = instance.get('editorForm');

					editorForm.syncElementsFocus();
				},

				customizeToolbar() {
					var instance = this;

					var editorForm = instance.get('editorForm');

					instance.addSectionButton = editorForm.get(
						'addSectionButton'
					);

					if (instance.addSectionButton) {
						instance.toolbar.add([instance.addSectionButton]);
					}
				},

				destructor() {
					var instance = this;

					var editorForm = instance.get('editorForm');

					if (editorForm) {
						editorForm.destroy(true);
					}
				},

				getValue() {
					var instance = this;

					var editorForm = instance.get('editorForm');

					return editorForm.getValue();
				},

				initializer(config) {
					var instance = this;

					instance.set('editorForm', instance._getEditorForm(config));

					instance
						.get('boundingBox')
						.delegate(
							'click',
							A.bind(instance._onClickViewMenu, instance),
							'.celleditor-view-menu a'
						);

					instance.after('valueChange', instance._onValueChange);

					instance.after(
						'visibleChange',
						instance._afterEditorVisibleChange
					);

					instance.destroyPortletHandler = Liferay.on(
						'destroyPortlet',
						A.bind(instance._onDestroyPortlet, instance)
					);
				}
			}
		});

		var BaseAbstractEditorForm = A.Component.create({
			ATTRS: {
				addSectionButton: {
					valueFn: '_valueAddSectionButton'
				},

				bodyNode: {
					valueFn() {
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

				builder: {},

				dynamicViewSingleton: {
					validator: isBoolean,
					value: false,
					writeOnce: 'initOnly'
				},

				strings: {
					value: KaleoDesignerStrings
				},

				value: {},

				viewTemplate: {
					setter: '_setViewTemplate'
				}
			},

			EXTENDS: A.Component,

			NAME: 'base-abstract-editor-form',

			UI_ATTRS: ['value'],

			prototype: {
				_addRemoveDynamicViewButton(dynamicViewNode) {
					dynamicViewNode.append(
						A.Node.create(STR_REMOVE_DYNAMIC_VIEW_BUTTON)
					);
				},

				_afterRender() {
					var instance = this;

					instance.addStaticViews();

					instance.syncToolbarUI();

					instance.syncViewsUI();
				},

				_onClickAddSectionButton(event) {
					var instance = this;

					instance.handleAddViewSection(event);

					var viewNodes = instance.getDynamicViews();

					instance._addRemoveDynamicViewButton(viewNodes.last());
				},

				_setViewTemplate(val) {
					if (!A.instanceOf(val, A.Template)) {
						val = new Template(val);
					}

					return val;
				},

				_uiSetValue(val) {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					instance.addDynamicViews(val);

					A.each(val, (item1, index1) => {
						var fields = bodyNode.all('[name="' + index1 + '"]');

						item1 = AArray(item1);

						fields.each((item2, index2) => {
							var value = item1[index2];

							if (
								item2.test(
									'input[type=checkbox],input[type=radio]'
								)
							) {
								item2.set(
									'checked',
									A.DataType.Boolean.parse(
										value || typeof value == 'boolean'
											? value
											: true
									)
								);
							}
							else if (
								item2.test('select[multiple]') &&
								Lang.isArray(value)
							) {
								value.forEach(option => {
									for (var key in option) {
										item2
											.one(
												'option[value=' +
													option[key] +
													']'
											)
											.set('selected', true);
									}
								});
							}
							else {
								item2.val(value);
							}
						});
					});
				},

				_valueAddSectionButton() {
					var instance = this;

					if (instance.get('dynamicViewSingleton')) {
						instance._addSectionButton = null;
					}
					else if (!instance._addSectionButton) {
						var strings = instance.get('strings');

						var addSectionButton = new A.Button({
							disabled: true,
							id: 'addSectionButton',
							label: strings.addSection,
							on: {
								click: A.bind(
									instance._onClickAddSectionButton,
									instance
								)
							}
						}).render();

						var bodyNode = instance.get('bodyNode');

						bodyNode.append(addSectionButton.get('boundingBox'));

						instance._addSectionButton = addSectionButton;
					}

					return instance._addSectionButton;
				},

				addDynamicViews: emptyFn,

				addStaticViews: emptyFn,

				appendToDynamicView(view) {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicView = bodyNode.one(
						'.celleditor-view-dynamic-views'
					);

					if (typeof view === 'string') {
						view = A.Node.create(view);
					}

					dynamicView.append(view);

					if (!instance.get('dynamicViewSingleton')) {
						var dynamicViews = dynamicView.all('.celleditor-view');

						dynamicViews.each(
							A.bind(
								instance._addRemoveDynamicViewButton,
								instance
							)
						);
					}
				},

				appendToStaticView(view) {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var staticView = bodyNode.one(
						'.celleditor-view-static-view'
					);

					if (typeof view === 'string') {
						view = A.Node.create(view);
					}

					staticView.append(view);
				},

				convertScriptLanguagesToJSONArray(scriptLanguages) {
					var instance = this;

					var scriptLanguagesJSONArray = [];

					var strings = instance.getStrings();

					scriptLanguages.forEach(item => {
						if (item) {
							scriptLanguagesJSONArray.push({
								label: strings[item],
								value: item
							});
						}
					});

					return scriptLanguagesJSONArray;
				},

				destructor() {
					var instance = this;

					instance.bodyNode.remove(true);
				},

				getDynamicViews() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicView = bodyNode.one(
						'.celleditor-view-dynamic-views'
					);

					return dynamicView.get('childNodes');
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

				getStrings() {
					var instance = this;

					return instance.get('strings');
				},

				getValue() {
					var instance = this;

					return serializeForm(instance.get('bodyNode'));
				},

				handleAddViewSection: emptyFn,

				initializer() {
					var instance = this;

					instance.after('render', instance._afterRender);
				},

				removeAllViews(viewId) {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					if (bodyNode) {
						bodyNode
							.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + viewId)
							.remove();
					}
				},

				syncElementsFocus: emptyFn,

				syncToolbarUI() {
					var instance = this;

					var addSectionButton = instance.get('addSectionButton');

					if (addSectionButton) {
						addSectionButton.set('disabled', !instance.viewId);
					}
				},

				syncViewsUI() {
					var instance = this;

					instance._uiSetValue(instance.get('value'));
				}
			}
		});

		var CompositeEditorFormBase = function() {};

		CompositeEditorFormBase.prototype = {
			getEmbeddedEditorForm(editorFormClass, container, config) {
				var instance = this;

				var editorFormName = editorFormClass.NAME;

				var editorForm = container.getData(
					editorFormName + '-instance'
				);

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

			showEditorForm(editorFormClass, container, value, config) {
				var instance = this;

				config = A.merge(
					{
						render: container,
						value
					},
					config
				);

				var editor = instance.getEmbeddedEditorForm(
					editorFormClass,
					container,
					config
				);

				var bodyNode = editor.get('bodyNode');

				container.append(bodyNode);

				editor.show();
			}
		};

		var AssignmentsEditorForm = A.Component.create({
			ATTRS: {
				assignmentsType: {
					valueFn: '_valueAssignmentsType'
				},

				roleTypes: {
					valueFn() {
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
					valueFn() {
						var instance = this;

						var scriptLanguages = [];

						instance.getScriptLanguages(scriptLanguages);

						var scriptLanguagesJSONArray = instance.convertScriptLanguagesToJSONArray(
							scriptLanguages
						);

						return scriptLanguagesJSONArray;
					}
				},

				strings: {
					valueFn() {
						return A.merge(KaleoDesignerStrings, {
							assignmentTypeLabel:
								KaleoDesignerStrings.assignmentType,
							defaultAssignmentLabel:
								KaleoDesignerStrings.assetCreator
						});
					}
				},

				typeSelect: {},

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
				_countRoleTypeViews(val) {
					var count = 0;

					if (val) {
						count = val.roleType
							? val.roleType.filter(isValue).length
							: 1;
					}

					return count;
				},

				_countUserViews(val) {
					var count = 0;

					if (val) {
						count = Math.max(
							val.emailAddress
								? val.emailAddress.filter(isValue).length
								: 1,
							val.screenName
								? val.screenName.filter(isValue).length
								: 1,
							val.userId ? val.userId.filter(isValue).length : 1
						);
					}

					return count;
				},

				_onTypeValueChange(event) {
					var instance = this;

					instance.showView(event.currentTarget.val());
				},

				_valueAssignmentsType() {
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
				},

				addDynamicViews(val) {
					var instance = this;

					Liferay.KaleoDesignerAutoCompleteUtil.destroyAll();

					instance.removeAllViews('roleType');

					instance.addViewRoleType(instance._countRoleTypeViews(val));

					instance.removeAllViews('user');

					instance.addViewUser(instance._countUserViews(val));

					if (val) {
						instance.showView(val.assignmentType);
					}
				},

				addStaticViews() {
					var instance = this;

					var strings = instance.getStrings();

					var assignmentsViewTpl = instance.get('viewTemplate');
					var inputTpl = Template.get('input');
					var selectTpl = Template.get('select');
					var textareaTpl = Template.get('textarea');

					var select = selectTpl.render({
						auiCssClass: 'form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						label: strings.assignmentTypeLabel,
						name: 'assignmentType',
						options: instance.get('assignmentsType')
					});

					var selectWrapper = A.Node.create('<div/>').append(select);

					var typeSelect = selectWrapper.one('select');

					instance.set('typeSelect', typeSelect);

					instance.appendToStaticView(selectWrapper);

					typeSelect.on(
						['change', 'keyup'],
						A.bind(instance._onTypeValueChange, instance)
					);

					var buffer = [];

					var resourceActionContent = textareaTpl.parse({
						auiCssClass:
							'celleditor-textarea-small form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.resourceActions,
						name: 'resourceAction'
					});

					buffer.push(
						assignmentsViewTpl.parse({
							content: resourceActionContent,
							viewId: 'resourceActions'
						})
					);

					var roleIdContent = [
						inputTpl.parse({
							auiCssClass:
								'assignments-cell-editor-input form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.role,
							name: 'roleNameAC',
							placeholder: KaleoDesignerStrings.search,
							size: 35,
							type: 'text'
						}),

						inputTpl.parse({
							auiCssClass:
								'assignments-cell-editor-input form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							disabled: true,
							id: A.guid(),
							label: strings.roleId,
							name: 'roleId',
							size: 35,
							type: 'text'
						})
					].join(STR_BLANK);

					buffer.push(
						assignmentsViewTpl.parse({
							content: roleIdContent,
							viewId: 'roleId'
						})
					);

					var scriptedAssignmentContent = [
						textareaTpl.parse({
							auiCssClass:
								'celleditor-textarea-small form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.script,
							name: 'script'
						}),

						selectTpl.parse({
							auiCssClass: 'form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.scriptLanguage,
							name: 'scriptLanguage',
							options: instance.get('scriptLanguages')
						})
					].join(STR_BLANK);

					buffer.push(
						assignmentsViewTpl.parse({
							content: scriptedAssignmentContent,
							viewId: 'scriptedAssignment'
						})
					);

					instance.appendToStaticView(buffer.join(STR_BLANK));
				},

				addViewRoleType(num) {
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
							selectTpl.parse({
								auiCssClass:
									'assignments-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.roleType,
								name: 'roleType',
								options: instance.get('roleTypes')
							}),

							inputTpl.parse({
								auiCssClass:
									'assignments-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.roleName,
								name: 'roleName',
								size: 35,
								type: 'text'
							}),

							'<div class="checkbox">',

							checkboxTpl.parse({
								auiCssClass: 'assignments-cell-editor-input',
								auiLabelCssClass: 'celleditor-label-checkbox',
								checked: false,
								id: A.guid(),
								label: strings.autoCreate,
								name: 'autoCreate',
								type: 'checkbox'
							}),

							'</div>'
						].join(STR_BLANK);

						buffer.push(
							assignmentsViewTpl.parse({
								content: roleTypeContent,
								showMenu: true,
								viewId: 'roleType'
							})
						);
					}

					instance.appendToDynamicView(buffer.join(STR_BLANK));
				},

				addViewUser(num) {
					var instance = this;

					num = num || 1;

					var strings = instance.getStrings();

					var assignmentsViewTpl = instance.get('viewTemplate');

					var inputTpl = Template.get('input');

					var buffer = [];

					for (var i = 0; i < num; i++) {
						var userContent = [
							inputTpl.parse({
								auiCssClass:
									'assignments-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.name,
								name: 'fullName',
								placeholder: KaleoDesignerStrings.search,
								size: 35,
								type: 'text'
							}),

							inputTpl.parse({
								auiCssClass:
									'assignments-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								disabled: true,
								id: A.guid(),
								label: strings.screenName,
								name: 'screenName',
								size: 35,
								type: 'text'
							}),

							inputTpl.parse({
								auiCssClass:
									'assignments-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								disabled: true,
								id: A.guid(),
								label: strings.emailAddress,
								name: 'emailAddress',
								size: 35,
								type: 'text'
							}),

							inputTpl.parse({
								auiCssClass:
									'assignments-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								disabled: true,
								id: A.guid(),
								label: strings.userId,
								name: 'userId',
								size: 35,
								type: 'text'
							})
						].join(STR_BLANK);

						buffer.push(
							assignmentsViewTpl.parse({
								content: userContent,
								showMenu: true,
								viewId: 'user'
							})
						);
					}

					instance.appendToDynamicView(buffer.join(STR_BLANK));
				},

				getViewNodes() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					return bodyNode.all(
						STR_DOT + CSS_CELLEDITOR_ASSIGNMENT_VIEW
					);
				},

				handleAddViewSection(event) {
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

				showView(viewId) {
					var instance = this;

					instance.viewId = viewId;

					instance.getViewNodes().hide();

					var bodyNode = instance.get('bodyNode');

					bodyNode
						.all(SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + viewId)
						.show();

					instance.syncToolbarUI();
				},

				syncElementsFocus() {
					var instance = this;

					var typeSelect = instance.get('typeSelect');

					typeSelect.focus();
				},

				syncToolbarUI() {
					var instance = this;

					var viewId = instance.viewId;

					var disabled = viewId !== 'roleType' && viewId !== 'user';

					var addSectionButton = instance.get('addSectionButton');

					if (addSectionButton) {
						addSectionButton.set('disabled', disabled);
					}
				},

				syncViewsUI() {
					var instance = this;

					AssignmentsEditorForm.superclass.syncViewsUI.apply(
						this,
						arguments
					);

					var typeSelect = instance.get('typeSelect');

					instance.showView(typeSelect.val());
				}
			}
		});

		var AssignmentsEditor = A.Component.create({
			ATTRS: {
				editorFormClass: {
					value: AssignmentsEditorForm
				}
			},

			EXTENDS: BaseAbstractEditor,

			NAME: 'assignments-cell-editor'
		});

		var FormsEditorForm = A.Component.create({
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
				addStaticViews() {
					var instance = this;

					var strings = instance.getStrings();

					var formsViewTpl = instance.get('viewTemplate');

					var inputTpl = Template.get('input');

					var buffer = [];

					var formsContent = [
						inputTpl.parse({
							auiCssClass:
								'form-control forms-cell-editor-input input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.formTemplate,
							name: 'templateName',
							placeholder: KaleoDesignerStrings.search,
							size: 35,
							type: 'text'
						}),

						inputTpl.parse({
							auiCssClass:
								'form-control forms-cell-editor-input input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							name: 'templateId',
							size: 35,
							type: 'hidden'
						})
					].join(STR_BLANK);

					buffer.push(
						formsViewTpl.parse({
							content: formsContent,
							viewId: 'formTemplateId'
						})
					);

					instance.appendToStaticView(buffer.join(STR_BLANK));
				}
			}
		});

		var FormsEditor = A.Component.create({
			ATTRS: {
				editorFormClass: {
					value: FormsEditorForm
				}
			},

			EXTENDS: BaseAbstractEditor,

			NAME: 'forms-cell-editor'
		});

		var ExecutionTypesEditorFormBase = function() {};

		ExecutionTypesEditorFormBase.prototype = {
			_executionTypesSetter(val) {
				var instance = this;

				var strings = instance.getStrings();

				var selectedNode = instance.get('builder.selectedNode');
				var type = selectedNode.get('type');

				if (type === 'task') {
					val.push({
						label: strings.onAssignment,
						value: 'onAssignment'
					});
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
					valueFn() {
						return A.merge(KaleoDesignerStrings, {
							assignmentTypeLabel:
								KaleoDesignerStrings.recipientType,
							defaultAssignmentLabel:
								KaleoDesignerStrings.assetCreator
						});
					}
				}
			},

			EXTENDS: AssignmentsEditorForm,

			NAME: 'notification-recipients-editor-form',

			prototype: {
				_valueAssignmentsType() {
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

					var executionTypeSelect = instance.get(
						'executionTypeSelect'
					);

					var executionType = executionTypeSelect.val();

					if (executionType === 'onAssignment') {
						assignmentsTypes.push({
							label: KaleoDesignerStrings.taskAssignees,
							value: 'taskAssignees'
						});
					}

					return assignmentsTypes;
				},

				addStaticViews() {
					var instance = this;

					var strings = instance.getStrings();

					var assignmentsViewTpl = instance.get('viewTemplate');

					var inputTpl = Template.get('input');
					var selectTpl = Template.get('select');
					var textareaTpl = Template.get('textarea');

					var select = selectTpl.render({
						auiCssClass: 'form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						label: strings.assignmentTypeLabel,
						name: 'assignmentType',
						options: instance.get('assignmentsType')
					});

					var selectWrapper = A.Node.create('<div/>').append(select);

					var typeSelect = selectWrapper.one('select');

					instance.set('typeSelect', typeSelect);

					instance.appendToStaticView(selectWrapper);

					typeSelect.on(
						['change', 'keyup'],
						A.bind(instance._onTypeValueChange, instance)
					);

					var receptionType = inputTpl.parse({
						id: A.guid(),
						name: 'receptionType',
						type: 'hidden'
					});

					instance.appendToStaticView(receptionType);

					var buffer = [];

					var roleIdContent = [
						inputTpl.parse({
							auiCssClass:
								'assignments-cell-editor-input form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.role,
							name: 'roleNameAC',
							placeholder: KaleoDesignerStrings.search,
							size: 35,
							type: 'text'
						}),

						inputTpl.parse({
							auiCssClass:
								'assignments-cell-editor-input form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							disabled: true,
							id: A.guid(),
							label: strings.roleId,
							name: 'roleId',
							size: 35,
							type: 'text'
						})
					].join(STR_BLANK);

					buffer.push(
						assignmentsViewTpl.parse({
							content: roleIdContent,
							viewId: 'roleId'
						})
					);

					var scriptedRecipientContent = [
						textareaTpl.parse({
							auiCssClass:
								'celleditor-textarea-small form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.script,
							name: 'script'
						}),

						selectTpl.parse({
							auiCssClass: 'form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.scriptLanguage,
							name: 'scriptLanguage',
							options: instance.get('scriptLanguages')
						})
					].join(STR_BLANK);

					buffer.push(
						assignmentsViewTpl.parse({
							content: scriptedRecipientContent,
							viewId: 'scriptedRecipient'
						})
					);

					instance.appendToStaticView(buffer.join(STR_BLANK));
				}
			}
		};

		var NotificationRecipientsEditorForm = A.Component.create(
			NotificationRecipientsEditorFormConfig
		);

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

		var TimerNotificationRecipientsEditorForm = A.Component.create(
			NotificationRecipientsEditorFormConfig
		);

		var NotificationsEditorFormConfig = {
			ATTRS: {
				notificationTypes: {
					valueFn() {
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
					valueFn() {
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
				_appendRecipientsEditorToLastSection() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'notification'
					);

					var lastDynamicView = dynamicViews.item(
						dynamicViews.size() - 1
					);

					instance._showRecipientsEditor(lastDynamicView);
				},

				_countNotificationViews(val) {
					var count = 0;

					if (val) {
						count = val.notificationTypes
							? val.notificationTypes.filter(isValue).length
							: 1;
					}

					return count;
				},

				_getRecipients(val) {
					var instance = this;

					return instance.get('value.recipients') || val;
				},

				_renderRecipientsEditor() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'notification'
					);

					dynamicViews.each(
						A.bind(instance._showRecipientsEditor, instance)
					);
				},

				_showRecipientsEditor(bodyContentNode, index) {
					var instance = this;

					var executionTypeSelect = bodyContentNode.one(
						'.execution-type-select'
					);

					var editorContainer = bodyContentNode.one(
						'.recipients-editor-container'
					);

					var recipients = instance.get('recipients');

					var value = recipients[index];

					if (value && AObject.isEmpty(value)) {
						value.assignmentType = 'taskAssignees';
						value.receptionType = ['to'];
						value.taskAssignees = [''];
					}

					instance.showEditorForm(
						NotificationRecipientsEditorForm,
						editorContainer,
						value,
						{
							executionTypeSelect
						}
					);
				},

				addDynamicViews(val) {
					var instance = this;

					instance.removeAllViews('notification');

					instance.addNotificationView(
						instance._countNotificationViews(val)
					);
				},

				addNotificationView(num) {
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
							inputTpl.parse({
								auiCssClass:
									'form-control input-sm notifications-cell-editor-input',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.name,
								name: 'name',
								size: 35,
								type: 'text'
							}),

							textareaTpl.parse({
								auiCssClass:
									'celleditor-textarea-small form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.description,
								name: 'description'
							}),

							selectTpl.parse({
								auiCssClass: 'form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.templateLanguage,
								name: 'templateLanguage',
								options: instance.get('templateLanguages')
							}),

							textareaTpl.parse({
								auiCssClass:
									'celleditor-textarea-small form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.template,
								name: 'template'
							}),

							selectMultipleTpl.parse({
								auiCssClass: 'form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.notificationType,
								multiple: true,
								name: 'notificationTypes',
								options: instance.get('notificationTypes')
							}),

							selectTpl.parse({
								auiCssClass:
									'form-control input-sm execution-type-select',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.executionType,
								name: 'executionType',
								options: instance.get('executionTypes')
							})
						].join(STR_BLANK);

						buffer.push(
							notificationsViewTpl.parse({
								content: notificationContent,
								viewId: 'notification'
							})
						);
					}

					instance.appendToDynamicView(buffer.join(STR_BLANK));
				},

				getValue() {
					var instance = this;

					var localRecipients = instance.get('recipients');

					var recipients = [];

					instance.getDynamicViews().each((item, index) => {
						var editorContainer = item.one(
							'.recipients-editor-container'
						);

						var recipientsEditor = instance.getEmbeddedEditorForm(
							NotificationRecipientsEditorForm,
							editorContainer
						);

						if (recipientsEditor) {
							recipients.push(recipientsEditor.getValue());
						}

						localRecipients[index] = recipientsEditor.getValue();
					});

					instance.set('recipients', localRecipients);

					return A.merge(
						NotificationsEditorForm.superclass.getValue.apply(
							this,
							arguments
						),
						{
							recipients
						}
					);
				},

				handleAddViewSection(event) {
					var instance = this;

					var button = event.target;

					if (!button.get('disabled')) {
						instance.addNotificationView();
					}

					instance._appendRecipientsEditorToLastSection();
				},

				syncElementsFocus() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					bodyNode.one(':input').focus();
				},

				syncToolbarUI() {
					var instance = this;

					var addSectionButton = instance.get('addSectionButton');

					if (addSectionButton) {
						addSectionButton.set('disabled', false);
					}
				},

				syncViewsUI() {
					var instance = this;

					NotificationsEditorForm.superclass.syncViewsUI.apply(
						this,
						arguments
					);

					instance._renderRecipientsEditor();
				}
			}
		};

		var NotificationsEditorForm = A.Component.create(
			NotificationsEditorFormConfig
		);

		var NotificationsEditor = A.Component.create({
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
		});

		NotificationsEditorFormConfig.prototype.addNotificationView = function(
			num
		) {
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
					inputTpl.parse({
						auiCssClass:
							'form-control input-sm notifications-cell-editor-input',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.name,
						name: 'name',
						size: 35,
						type: 'text'
					}),

					textareaTpl.parse({
						auiCssClass:
							'celleditor-textarea-small form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.description,
						name: 'description'
					}),

					selectTpl.parse({
						auiCssClass: 'form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.templateLanguage,
						name: 'templateLanguage',
						options: instance.get('templateLanguages')
					}),

					textareaTpl.parse({
						auiCssClass:
							'celleditor-textarea-small form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.template,
						name: 'template'
					}),

					selectMultipleTpl.parse({
						auiCssClass: 'form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.notificationType,
						multiple: true,
						name: 'notificationTypes',
						options: instance.get('notificationTypes')
					})
				].join(STR_BLANK);

				buffer.push(
					notificationsViewTpl.parse({
						content: notificationContent,
						viewId: 'notification'
					})
				);
			}

			instance.appendToDynamicView(buffer.join(STR_BLANK));
		};

		NotificationsEditorFormConfig.prototype.getValue = function() {
			var instance = this;

			var localRecipients = instance.get('recipients');

			var recipients = [];

			instance.getDynamicViews().each((item, index) => {
				var editorContainer = item.one('.recipients-editor-container');

				var recipientsEditor = instance.getEmbeddedEditorForm(
					TimerNotificationRecipientsEditorForm,
					editorContainer
				);

				if (recipientsEditor) {
					recipients.push(recipientsEditor.getValue());
				}

				localRecipients[index] = recipientsEditor.getValue();
			});

			instance.set('recipients', localRecipients);

			return A.merge(
				NotificationsEditorForm.superclass.getValue.apply(
					this,
					arguments
				),
				{
					recipients
				}
			);
		};

		NotificationsEditorFormConfig.prototype._showRecipientsEditor = function(
			bodyContentNode,
			index
		) {
			var instance = this;

			var executionTypeSelect = bodyContentNode.one(
				'.execution-type-select'
			);

			var editorContainer = bodyContentNode.one(
				'.recipients-editor-container'
			);

			var recipients = instance.get('recipients');

			var value = recipients[index];

			instance.showEditorForm(
				TimerNotificationRecipientsEditorForm,
				editorContainer,
				value,
				{
					executionTypeSelect
				}
			);
		};

		var TimerNotificationsEditorForm = A.Component.create(
			NotificationsEditorFormConfig
		);

		var ActionsEditorFormConfig = {
			ATTRS: {
				scriptLanguages: {
					valueFn() {
						var instance = this;

						var scriptLanguages = [];

						instance.getScriptLanguages(scriptLanguages);

						var scriptLanguagesJSONArray = instance.convertScriptLanguagesToJSONArray(
							scriptLanguages
						);

						return scriptLanguagesJSONArray;
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
				_countActionViews(val) {
					var count = 0;

					if (val) {
						count = val.name ? val.name.filter(isValue).length : 1;
					}

					return count;
				},

				addActionView(num) {
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
							inputTpl.parse({
								auiCssClass:
									'actions-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.name,
								name: 'name',
								size: 35,
								type: 'text'
							}),

							textareaTpl.parse({
								auiCssClass:
									'celleditor-textarea-small form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.description,
								name: 'description'
							}),

							textareaTpl.parse({
								auiCssClass:
									'celleditor-textarea-small form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.script,
								name: 'script'
							}),

							selectTpl.parse({
								auiCssClass: 'form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.scriptLanguage,
								name: 'scriptLanguage',
								options: instance.get('scriptLanguages')
							}),

							selectTpl.parse({
								auiCssClass: 'form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.executionType,
								name: 'executionType',
								options: instance.get('executionTypes')
							}),

							inputTpl.parse({
								auiCssClass:
									'actions-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.priority,
								name: 'priority',
								size: 35,
								type: 'text'
							})
						].join(STR_BLANK);

						buffer.push(
							actionsViewTpl.parse({
								content: actionContent,
								viewId: 'action'
							})
						);
					}

					instance.appendToDynamicView(buffer.join(STR_BLANK));
				},

				addDynamicViews(val) {
					var instance = this;

					instance.removeAllViews('action');

					instance.addActionView(instance._countActionViews(val));
				},

				handleAddViewSection(event) {
					var instance = this;

					var button = event.target;

					if (!button.get('disabled')) {
						instance.addActionView();
					}
				},

				syncElementsFocus() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					bodyNode.one(':input').focus();
				},

				syncToolbarUI() {
					var instance = this;

					var addSectionButton = instance.get('addSectionButton');

					if (addSectionButton) {
						addSectionButton.set('disabled', false);
					}
				}
			}
		};

		var ActionsEditorForm = A.Component.create(ActionsEditorFormConfig);

		var ActionsEditor = A.Component.create({
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
		});

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
					inputTpl.parse({
						auiCssClass:
							'actions-cell-editor-input form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.name,
						name: 'name',
						size: 35,
						type: 'text'
					}),

					textareaTpl.parse({
						auiCssClass:
							'celleditor-textarea-small form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.description,
						name: 'description'
					}),

					textareaTpl.parse({
						auiCssClass:
							'celleditor-textarea-small form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.script,
						name: 'script'
					}),

					selectTpl.parse({
						auiCssClass: 'form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.scriptLanguage,
						name: 'scriptLanguage',
						options: instance.get('scriptLanguages')
					}),

					inputTpl.parse({
						auiCssClass:
							'actions-cell-editor-input form-control input-sm',
						auiLabelCssClass: 'celleditor-label',
						id: A.guid(),
						label: strings.priority,
						name: 'priority',
						size: 35,
						type: 'text'
					})
				].join(STR_BLANK);

				buffer.push(
					actionsViewTpl.parse({
						content: actionContent,
						viewId: 'action'
					})
				);
			}

			instance.appendToDynamicView(buffer.join(STR_BLANK));
		};

		var TimerActionsEditorForm = A.Component.create(
			ActionsEditorFormConfig
		);

		var TaskTimerActionsEditorForm = A.Component.create({
			ATTRS: {
				actionTypes: {
					valueFn() {
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
				_appendEditorToLastSection() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timerAction'
					);

					var lastDynamicView = dynamicViews.item(
						dynamicViews.size() - 1
					);

					instance._showEditor(lastDynamicView);
				},

				_countTimerActionViews(val) {
					var count = 0;

					if (val) {
						count = val.actionType
							? val.actionType.filter(isValue).length
							: 1;
					}

					return count;
				},

				_displayEditor(dynamicViewNode) {
					var actionTypeSelect = dynamicViewNode.one(
						'.select-action-type'
					);

					var actionType = actionTypeSelect.val();

					dynamicViewNode.all('.editor-container').hide();

					dynamicViewNode
						.all('.editor-container-' + actionType)
						.show();
				},

				_displayEditors() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timerAction'
					);

					dynamicViews.each(
						A.bind(instance._displayEditor, instance)
					);
				},

				_onActionTypeValueChange(event) {
					var instance = this;

					var actionTypeSelect = event.currentTarget;

					var dynamicViewNode = actionTypeSelect.ancestor(
						'.celleditor-task-timer-actions-view'
					);

					instance._showEditor(dynamicViewNode);

					instance._displayEditor(dynamicViewNode);
				},

				_renderEditor() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timerAction'
					);

					dynamicViews.each(A.bind(instance._showEditor, instance));
				},

				_showEditor(bodyContentNode, index) {
					var instance = this;

					var actionType;

					var timerAction;

					var value = instance.get('value');

					if (value && value.actionType && value.actionType[index]) {
						actionType = value.actionType[index];
						timerAction = value.timerAction[index];
					}
					else {
						var actionTypeSelect = bodyContentNode.one(
							'.select-action-type'
						);

						actionType = actionTypeSelect.val();
					}

					var editorFormClass = instance.get(
						'editorFormClasses.' + actionType
					);

					var editorContainer = bodyContentNode.one(
						'.editor-container-' + actionType
					);

					instance.showEditorForm(
						editorFormClass,
						editorContainer,
						timerAction
					);
				},

				addDynamicViews(val) {
					var instance = this;

					instance.removeAllViews('timerAction');

					instance.addTimerActionView(
						instance._countTimerActionViews(val)
					);
				},

				addTimerActionView(num) {
					var instance = this;

					num = num || 1;

					var strings = instance.getStrings();

					var timerActionViewTpl = instance.get('viewTemplate');

					var selectTpl = Template.get('select');

					var buffer = [];

					for (var i = 0; i < num; i++) {
						var timerActionContent = [
							selectTpl.parse({
								auiCssClass:
									'form-control input-sm select-action-type',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.type,
								name: 'actionType',
								options: instance.get('actionTypes')
							})
						].join(STR_BLANK);

						buffer.push(
							timerActionViewTpl.parse({
								content: timerActionContent,
								viewId: 'timerAction'
							})
						);
					}

					instance.appendToDynamicView(buffer.join(STR_BLANK));
				},

				getValue() {
					var instance = this;

					var value = {
						actionType: [],
						timerAction: []
					};

					var dynamicViews = instance.getDynamicViews();

					dynamicViews.each(item => {
						var actionTypeSelect = item.one('.select-action-type');

						var actionType = actionTypeSelect.val();

						value.actionType.push(actionType);

						var editorContainer = item.one(
							'.editor-container-' + actionType
						);

						var editorFormClass = instance.get(
							'editorFormClasses.' + actionType
						);

						var editor = instance.getEmbeddedEditorForm(
							editorFormClass,
							editorContainer
						);

						value.timerAction.push(editor.getValue());
					});

					return value;
				},

				handleAddViewSection(event) {
					var instance = this;

					var button = event.target;

					if (!button.get('disabled')) {
						instance.addTimerActionView();
					}

					instance._appendEditorToLastSection();

					instance._displayEditors();
				},

				initializer() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					bodyNode.delegate(
						['change', 'keyup'],
						A.bind(instance._onActionTypeValueChange, instance),
						'.select-action-type'
					);
				},

				syncToolbarUI() {
					var instance = this;

					var addSectionButton = instance.get('addSectionButton');

					if (addSectionButton) {
						addSectionButton.set('disabled', false);
					}
				},

				syncViewsUI() {
					var instance = this;

					TaskTimerActionsEditorForm.superclass.syncViewsUI.apply(
						this,
						arguments
					);

					instance._renderEditor();

					instance._displayEditors();
				}
			}
		});

		var TaskTimerDelaysEditorForm = A.Component.create({
			ATTRS: {
				scales: {
					valueFn() {
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
				addStaticViews() {
					var instance = this;

					var delayContent = instance.getDelayContent();

					instance.appendToStaticView(delayContent);

					var recurrenceContent = instance.getRecurrenceContent();

					instance.appendToStaticView(recurrenceContent);
				},

				getDelayContent() {
					var instance = this;

					var strings = instance.getStrings();

					var inputTpl = Template.get('input');
					var selectTpl = Template.get('select');

					return [
						inputTpl.parse({
							auiCssClass: 'form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.duration,
							name: 'duration'
						}),

						selectTpl.parse({
							auiCssClass: 'form-control input-sm',
							auiLabelCssClass: 'celleditor-label',
							id: A.guid(),
							label: strings.scale,
							name: 'scale',
							options: instance.get('scales')
						})
					].join(STR_BLANK);
				},

				getRecurrenceContent() {
					var instance = this;

					var timersViewTpl = instance.get('viewTemplate');

					var delayContent = instance.getDelayContent();

					return [
						timersViewTpl.parse({
							content: delayContent,
							viewId: 'recurrence'
						})
					].join(STR_BLANK);
				}
			}
		});

		var TaskTimersEditorForm = A.Component.create({
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
				_appendDelaysEditorToLastSection() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timer'
					);

					var lastDynamicView = dynamicViews.item(
						dynamicViews.size() - 1
					);

					instance._showDelaysEditor(lastDynamicView);
				},

				_appendTimerActionsEditorToLastSection() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timer'
					);

					var lastDynamicView = dynamicViews.item(
						dynamicViews.size() - 1
					);

					instance._showTimerActionsEditor(lastDynamicView);
				},

				_countTimerViews(val) {
					var count = 0;

					if (val) {
						count = val.name ? val.name.filter(isValue).length : 1;
					}

					return count;
				},

				_getDelays(val) {
					var instance = this;

					return instance.get('value.delay') || val;
				},

				_getTimerActions() {
					var instance = this;

					var actions = instance.get('value.timerActions') || [];

					var notifications =
						instance.get('value.timerNotifications') || [];

					var reassignments =
						instance.get('value.reassignments') || [];

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

						if (reassignments[i]) {
							splitTimerActions = instance._splitTimerActions(
								reassignments[i]
							);

							actionType = actionType.concat(
								instance._repeat(
									'reassignment',
									splitTimerActions.length
								)
							);

							timerAction = timerAction.concat(splitTimerActions);
						}

						if (
							notifications[i] &&
							notifications[i].notificationTypes
						) {
							splitTimerActions = instance._splitTimerActions(
								notifications[i]
							);

							actionType = actionType.concat(
								instance._repeat(
									'notification',
									splitTimerActions.length
								)
							);

							timerAction = timerAction.concat(splitTimerActions);
						}

						if (actions[i] && actions[i].name) {
							splitTimerActions = instance._splitTimerActions(
								actions[i]
							);

							actionType = actionType.concat(
								instance._repeat(
									'action',
									splitTimerActions.length
								)
							);

							timerAction = timerAction.concat(splitTimerActions);
						}

						timerActions.push({
							actionType,
							timerAction
						});
					}

					return timerActions;
				},

				_put(obj, key, value) {
					obj[key] = obj[key] || [];

					obj[key].push(value);
				},

				_renderDelaysEditor() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timer'
					);

					dynamicViews.each(
						A.bind(instance._showDelaysEditor, instance)
					);
				},

				_renderTimerActionsEditor() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					var dynamicViews = bodyNode.all(
						SELECTOR_PREFIX_CELLEDITOR_VIEW_TYPE + 'timer'
					);

					dynamicViews.each(
						A.bind(instance._showTimerActionsEditor, instance)
					);
				},

				_repeat(value, times) {
					var array = [];

					for (var i = 0; i < times; i++) {
						array.push(value);
					}

					return array;
				},

				_showDelaysEditor(bodyContentNode, index) {
					var instance = this;

					var editorContainer = bodyContentNode.one(
						'.delays-editor-container'
					);

					var delays = instance.get('delays');

					var value = delays[index];

					instance.showEditorForm(
						TaskTimerDelaysEditorForm,
						editorContainer,
						value
					);
				},

				_showTimerActionsEditor(bodyContentNode, index) {
					var instance = this;

					var editorContainer = bodyContentNode.one(
						'.timer-actions-editor-container'
					);

					var timerActions = instance.get('timerActions');

					var value = timerActions[index];

					instance.showEditorForm(
						TaskTimerActionsEditorForm,
						editorContainer,
						value
					);
				},

				_splitTimerActions(timerActions) {
					var splitTimerActions = [];

					A.each(timerActions, (item1, index1) => {
						item1.forEach((item2, index2) => {
							if (!splitTimerActions[index2]) {
								splitTimerActions[index2] = {};
							}

							var timerAction = splitTimerActions[index2];

							if (!timerAction[index1]) {
								timerAction[index1] = [];
							}

							timerAction[index1][0] = item2;
						});
					});

					return splitTimerActions;
				},

				addDynamicViews(val) {
					var instance = this;

					instance.removeAllViews('timer');

					instance.addTaskTimerView(instance._countTimerViews(val));
				},

				addTaskTimerView(num) {
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
							inputTpl.parse({
								auiCssClass:
									'task-timers-cell-editor-input form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.name,
								name: 'name',
								size: 35,
								type: 'text'
							}),

							textareaTpl.parse({
								auiCssClass:
									'task-timers-cell-editor-input celleditor-textarea-small form-control input-sm',
								auiLabelCssClass: 'celleditor-label',
								id: A.guid(),
								label: strings.description,
								name: 'description'
							}),

							'<div class="delays-editor-container"></div>',

							checkboxTpl.parse({
								auiCssClass: 'task-timers-cell-editor-input',
								auiLabelCssClass: 'celleditor-label-checkbox',
								checked: false,
								id: A.guid(),
								label: strings.blocking,
								name: 'blocking',
								type: 'checkbox'
							}),

							'<div class="timer-actions-editor-container"></div>'
						].join(STR_BLANK);

						buffer.push(
							timersViewTpl.parse({
								content: taskTimerContent,
								viewId: 'timer'
							})
						);
					}

					instance.appendToDynamicView(buffer.join(STR_BLANK));
				},

				getValue() {
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

					var taskTimerInputs = bodyNode.all(
						'.task-timers-cell-editor-input'
					);

					taskTimerInputs.each(item => {
						if (
							item.get('type') &&
							item.get('type') === 'checkbox'
						) {
							value[item.get('name')].push(item.get('checked'));
						}
						else {
							value[item.get('name')].push(item.val());
						}
					});

					var dynamicViews = instance.getDynamicViews();

					dynamicViews.each((item1, index1) => {
						var delaysEditorContainer = item1.one(
							'.delays-editor-container'
						);

						var delaysEditorForm = instance.getEmbeddedEditorForm(
							TaskTimerDelaysEditorForm,
							delaysEditorContainer
						);

						value.delay.push(delaysEditorForm.getValue());

						var timerActionsEditorContainer = item1.one(
							'.timer-actions-editor-container'
						);

						var timerActionsEditorForm = instance.getEmbeddedEditorForm(
							TaskTimerActionsEditorForm,
							timerActionsEditorContainer
						);

						value.timerActions.push({});

						value.timerNotifications.push({});

						value.reassignments.push({});

						var timerActionValue = timerActionsEditorForm.getValue();

						timerActionValue.actionType.forEach(
							(actionType, index2) => {
								var timerAction =
									timerActionValue.timerAction[index2];

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

								A.each(timerAction, (value, key) => {
									instance._put(object, key, value[0]);
								});
							}
						);
					});

					return value;
				},

				handleAddViewSection(event) {
					var instance = this;

					var button = event.target;

					if (!button.get('disabled')) {
						instance.addTaskTimerView();
					}

					instance._appendDelaysEditorToLastSection();

					instance._appendTimerActionsEditorToLastSection();
				},

				syncElementsFocus() {
					var instance = this;

					var bodyNode = instance.get('bodyNode');

					bodyNode.one(':input').focus();
				},

				syncToolbarUI() {
					var instance = this;

					var addSectionButton = instance.get('addSectionButton');

					if (addSectionButton) {
						addSectionButton.set('disabled', false);
					}
				},

				syncViewsUI() {
					var instance = this;

					TaskTimersEditorForm.superclass.syncViewsUI.apply(
						this,
						arguments
					);

					instance._renderDelaysEditor();

					instance._renderTimerActionsEditor();
				}
			}
		});

		var TaskTimersEditor = A.Component.create({
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
		});

		var ScriptEditor = A.Component.create({
			ATTRS: {
				inputFormatter: {
					value(val) {
						return val;
					}
				}
			},

			EXTENDS: A.BaseCellEditor,

			NAME: 'script-cell-editor',

			prototype: {
				_afterRender() {
					var instance = this;

					var editor = instance.editor;

					ScriptEditor.superclass._afterRender.apply(this, arguments);

					instance.setStdModContent(
						WidgetStdMod.BODY,
						STR_BLANK,
						WidgetStdMod.AFTER
					);

					setTimeout(() => {
						editor.render(instance.bodyNode);
					}, 0);
				},

				_syncElementsFocus: emptyFn,

				_uiSetValue(val) {
					var instance = this;

					var editor = instance.editor;

					if (editor && isValue(val)) {
						editor.set('value', val);
					}
				},

				getValue() {
					var instance = this;

					return instance.editor.get('value');
				},

				initializer() {
					var instance = this;

					instance.editor = new A.AceEditor({
						height: 300,
						width: 550
					});
				}
			}
		});

		Liferay.KaleoDesignerEditors = {
			ActionsEditor,
			AssignmentsEditor,
			BaseAbstractEditor,
			CompositeEditorFormBase,
			ExecutionTypesEditorFormBase,
			FormsEditor,
			NotificationsEditor,
			ScriptEditor,
			TaskTimersEditor
		};
	},
	'',
	{
		requires: [
			'aui-ace-editor',
			'aui-ace-editor-mode-xml',
			'aui-base',
			'aui-datatype',
			'aui-node',
			'liferay-kaleo-designer-autocomplete-util',
			'liferay-kaleo-designer-remote-services',
			'liferay-kaleo-designer-templates',
			'liferay-kaleo-designer-utils'
		]
	}
);
