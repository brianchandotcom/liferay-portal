AUI.add(
	'liferay-ddm-form-builder',
	function(A) {
		var AArray = A.Array;

		var CSS_DELETE_FIELD_BUTTON = A.getClassName('lfr-delete-field');

		var CSS_DUPLICATE_FIELD_BUTTON = A.getClassName('lfr-duplicate-field');

		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_FIELD_LIST_CONTAINER = A.getClassName('form', 'builder', 'field', 'list', 'container');

		var CSS_FORM_BUILDER_TABS = A.getClassName('form', 'builder', 'tabs');

		var CSS_LAYOUT_BUILDER_CONTAINER = A.getClassName('layout', 'builder', 'layout', 'container');

		var CSS_PAGE_HEADER = A.getClassName('form', 'builder', 'pages', 'header');

		var CSS_PAGES = A.getClassName('form', 'builder', 'pages', 'lexicon');

		var CSS_RESIZE_COL_BREAKPOINT = A.getClassName('layout', 'builder', 'resize', 'col', 'breakpoint');

		var CSS_RESIZE_COL_DRAGGABLE = A.getClassName('layout', 'builder', 'resize', 'col', 'draggable');

		var CSS_RESIZE_COL_DRAGGABLE_HANDLE = A.getClassName('layout', 'builder', 'resize', 'col', 'draggable', 'handle');

		var CSS_ROW_CONTAINER_ROW = A.getClassName('layout', 'row', 'container', 'row');

		var FormBuilderConfirmDialog = Liferay.DDM.FormBuilderConfirmationDialog;

		var FormBuilderUtil = Liferay.DDM.FormBuilderUtil;

		var FieldSets = Liferay.DDM.FieldSets;

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var FIELD_ACTIONS = A.getClassName('lfr', 'ddm', 'field', 'actions', 'container');

		var Lang = A.Lang;

		var TPL_CONFIRM_CANCEL_FIELD_EDITION = '<p>' + Liferay.Language.get('are-you-sure-you-want-to-cancel') + '</p>';

		var TPL_CONFIRM_DELETE_FIELD = '<p>' + Liferay.Language.get('are-you-sure-you-want-to-delete-this-field') + '</p>';

		var TPL_REQURIED_FIELDS = '<label class="hide required-warning">{message}</label>';

		var Util = Liferay.DDM.Renderer.Util;

		var CSS_RESIZE_COL_DRAGGING = A.getClassName('layout', 'builder', 'resize', 'col', 'dragging');

		var CSS_RESIZE_COL_DRAGGABLE_DRAGGING = A.getClassName('layout', 'builder', 'resize', 'col', 'draggable', 'dragging');

		var SELECTOR_ROW = '.layout-row';

		var MOVE_COLUMN_CONTAINER = '<div class="' + CSS_RESIZE_COL_DRAGGABLE_HANDLE + '">' + Liferay.Util.getLexiconIconTpl('horizontal-scroll') + '</div>';

		var MOVE_COLUMN_TPL = '<div class="' + CSS_RESIZE_COL_DRAGGABLE + ' lfr-tpl">' + MOVE_COLUMN_CONTAINER + '</div>';

		var FormBuilder = A.Component.create(
			{
				ATTRS: {
					container: {
						getter: function() {
							var instance = this;

							return instance.get('contentBox');
						}
					},

					context: {
						value: {}
					},

					defaultLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					deserializer: {
						valueFn: '_valueDeserializer'
					},

					editingLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					fieldSets: {
						valueFn: '_valueFieldSets'
					},

					fieldSettingsPanel: {
						getter: '_getFieldSettingsPanel',
						valueFn: '_valueFieldSettingsPanel'
					},

					fieldTypes: {
						setter: '_setFieldTypes',
						valueFn: '_valueFieldTypes'
					},

					fieldTypesPanel: {
						getter: '_getFieldTypesPanel',
						valueFn: '_valueFieldTypesPanel'
					},

					formInstanceId: {
						value: 0
					},

					layouts: {
						valueFn: '_valueLayouts'
					},

					pageManager: {
						value: {}
					},

					showPagination: {
						value: true
					},

					strings: {
						value: {
							addColumn: Liferay.Language.get('add-column'),
							addField: Liferay.Language.get('add-field'),
							cancelRemoveRow: Liferay.Language.get('cancel'),
							confirmRemoveRow: Liferay.Language.get('delete'),
							formTitle: Liferay.Language.get('build-your-form'),
							modalHeader: Liferay.Language.get('delete-row'),
							pasteHere: Liferay.Language.get('paste-here'),
							removeRowModal: Liferay.Language.get('you-will-also-delete-fields-with-this-row-are-you-sure-you-want-delete-it')
						},
						writeOnce: true
					},

					visitor: {
						getter: '_getVisitor',
						valueFn: '_valueVisitor'
					}
				},

				AUGMENTS: [Liferay.DDM.FormBuilderLayoutBuilderSupport, Liferay.DDM.Renderer.NestedFieldsSupport],

				CSS_PREFIX: 'form-builder',

				EXTENDS: A.FormBuilder,

				NAME: 'liferay-ddm-form-builder',

				prototype: {
					TPL_PAGES: '<div class="' + CSS_PAGES + '" ></div>',

					initializer: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance._eventHandlers = [
							boundingBox.delegate('click', A.bind('_afterFieldClick', instance), '.' + CSS_FIELD, instance),
							boundingBox.delegate('click', instance._onClickPaginationItem, '.pagination li a'),
							boundingBox.delegate('click', instance._onClickRemoveFieldButton, '.' + CSS_DELETE_FIELD_BUTTON, instance),
							boundingBox.delegate('click', instance._onClickDuplicateFieldButton, '.' + CSS_DUPLICATE_FIELD_BUTTON, instance),
							A.one('body').delegate('hover', instance.openSidebarByButton, '.lfr-ddm-add-field', instance),
							boundingBox.delegate('mouseleave', instance.onLeaveLayoutBuilder, '.' + CSS_LAYOUT_BUILDER_CONTAINER, instance),
							instance.after('editingLanguageIdChange', instance._afterEditingLanguageIdChange),
							instance.after('liferay-ddm-form-builder-field-list:fieldsChange', instance._afterFieldListChange, instance),
							instance.before('render', instance._beforeFormBuilderRender, instance),
							instance.after('render', instance._afterFormBuilderRender, instance),
							instance.after(instance._afterRemoveField, instance, 'removeField')
						];
					},

					destructor: function() {
						var instance = this;

						var visitor = instance.get('visitor');

						visitor.set('fieldHandler', instance.destroyField);

						instance._sidebar.destroy();
						instance.sortable1.delegate.destroy();
						instance.sortable1.destroy();

						visitor.visit();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					addColumnOnDragAction: function(dragNode, dropNode) {
						var instance = this;

						instance.addedColumWhileDragging = true;

						if (!instance.isValidNewLayoutColumn(dragNode.getData('layout-col1')) || !instance.isValidNewLayoutColumn(dragNode.getData('layout-col2'))) {
							return;
						}

						var newCol = new A.LayoutCol(
							{
								size: 1
							}
						);
						var rowNode = dragNode.ancestor(SELECTOR_ROW);

						var draggable = A.Node.create(instance._layoutBuilder.TPL_RESIZE_COL_DRAGGABLE);

						rowNode.append(draggable);

						instance.addNewColumnInLayout(newCol, dropNode, rowNode);
						instance._syncDragHandles(rowNode);
					},

					addNewColumnInLayout: function(newCol, dropNode, rowNode) {
						var instance = this;

						var colNode = newCol.get('node');
						var cols = rowNode.getData('layout-row').get('cols');

						instance.lastColumnAdd = colNode;

						colNode.addClass('col-empty');

						if (dropNode.getData('layout-position') == 1) {
							cols.unshift(newCol);
							rowNode.prepend(colNode);
							instance.hasColumnLeftCreated = true;
							instance.startedPosition = true;
						}
						else {
							cols.push(newCol);
							rowNode.append(colNode);
							instance.hasColumnRightCreated = true;
							instance.endedPosition = true;
						}
					},

					afterDragEnd: function(sortable) {
						var instance = this;

						sortable.on(
							'drag:end',
							function(event) {
								var fieldColumnStart = A.one('.current-dragging');
								var fieldNodeEnd = event.target.get('node');
								var layoutRows = instance.get('layouts')[instance._getActiveLayoutIndex()].get('rows');
								var positions = {};

								var fieldColumnEnd = fieldNodeEnd.ancestor('.col');

								if (fieldNodeEnd.getData('field-type') || fieldNodeEnd.getData('field-set-id')) {
									return instance.updateDragAndDropBySidebar(fieldNodeEnd);
								}

								if (!fieldColumnStart) {
									return false;
								}

								instance.gridDOM.forEach(
									function(cols, indexRow) {
										cols.forEach(
											function(col, indexCol) {
												if (fieldColumnStart._node.id == col._node.id) {
													positions.positionRowStart = indexRow;
													positions.positionColumnStart = indexCol;
												}
											}
										);

										cols.forEach(
											function(col, indexCol) {
												if (fieldColumnEnd._node.id == col._node.id) {
													positions.positionRowEnd = indexRow;
													positions.positionColumnEnd = indexCol;
												}
											}
										);
									}
								);

								if ((positions.positionRowEnd == positions.positionRowStart - 1) && (layoutRows[positions.positionRowStart].get('cols').length == 1)) {
									return instance._updateDragAndDropUI(fieldNodeEnd, sortable, positions);
								}

								return instance._updateDragAndDropContext(fieldNodeEnd, sortable, positions);
							}
						);
					},

					afterDragStart: function(sortable1) {
						var instance = this;

						sortable1.after(
							'drag:start',
							function(event) {
								var fieldNodeStart = event.target.get('node');
								var proxyActive;

								var fieldColumnStart = fieldNodeStart.ancestor('.col');

								fieldNodeStart.addClass('hidden');
								fieldColumnStart.addClass('current-dragging');
								fieldColumnStart.addClass('col-empty');

								sortable1.addDropNode(fieldColumnStart);
								A.DD.DDM._activateTargets();

								instance._addToStack(fieldColumnStart);

								proxyActive = A.one('.yui3-dd-proxy');

								if (proxyActive) {
									proxyActive.empty();
									proxyActive.append(fieldNodeStart._node.innerHTML);
								}
							}
						);
					},

					afterPlaceholderAlign: function(sortable) {
						var instance = this;

						sortable.after(
							'placeholderAlign',
							function(event) {
								var activeDropNode = event.drop.get('node');

								instance._addToStack(activeDropNode);
							}
						);
					},

					beforeSidebarDragStart: function() {
						var instance = this;

						instance.sidebarSortable.before(
							'drag:start',
							function(event) {
								var clonedNode = A.DD.DDM.activeDrag.get('node').clone();
								var fieldNodeStart = event.target.get('node');
								var proxyActive = A.one('.yui3-dd-proxy');

								instance.currentFieldTypeDrag = event.target;

								instance.sidebarSortable.addDropNode(fieldNodeStart.ancestor());
								A.DD.DDM._activateTargets();

								instance._addToStack(fieldNodeStart);

								if (proxyActive) {
									proxyActive.empty();
									proxyActive.append(clonedNode);
								}

								if (fieldNodeStart.getData('field-set-id')) {
									instance.formatDragRowsToReceiveFieldset();
								}
							}
						);
					},

					beforeSidebarItemPlaceholderAlign: function() {
						var instance = this;

						instance.sidebarSortable.before(
							'placeholderAlign',
							function(event) {
								var fieldStart = instance.currentFieldTypeDrag;
								var newTarget = A.one(document.createElement('div'));

								if (fieldStart.get('node').attr('data-field-set-id')) {
									newTarget.setData('field-set-id', fieldStart.get('node').getData('field-set-id'));
									fieldStart.set('node', newTarget);
								}

								if (!fieldStart.get('node').getData('field-type') && fieldStart.get('node').attr('data-field-type-name')) {
									newTarget.setData('field-type', FieldTypes.get(fieldStart.get('node').attr('data-field-type-name')));
									fieldStart.set('node', newTarget);
								}
							}
						);
					},

					bindSidebarFieldDragAction: function() {
						var instance = this;
						var rows = instance.getActiveLayout().get('rows');

						instance._newFieldContainer = rows[rows.length - 1].get('cols')[0];

						if (instance.sidebarSortable) {
							return;
						}

						instance.sidebarSortable = new A.SortableLayout(
							{
								delegateConfig: {
									target: true,
									useShim: true
								},
								dragNodes: '.lfr-ddm-form-builder-draggable-item',
								dropNodes: '.layout-row .col-empty',
								proxy: null
							}
						);

						instance.beforeSidebarDragStart();
						instance.beforeSidebarItemPlaceholderAlign();
						instance.afterPlaceholderAlign(instance.sidebarSortable);
						instance.afterDragEnd(instance.sidebarSortable);
					},

					cancelFieldEdition: function(field) {
						var instance = this;

						var fieldSettingsPanel = instance.getFieldSettingsPanel();

						var fieldContext = fieldSettingsPanel.getPreviousContext();

						if (fieldSettingsPanel.hasChanges()) {
							instance.openConfirmCancelFieldChangesDiolog(
								function() {
									instance.confirmCancelFieldChanges(field, fieldContext, fieldSettingsPanel);

									fieldSettingsPanel.close();
								}
							);
						}
						else {
							fieldSettingsPanel.close();
						}
					},

					confirmCancelFieldChanges: function(field, fieldContext, fieldSettingsPanel) {
						var instance = this;

						var settingForm = fieldSettingsPanel.settingsForm;

						settingForm.set('context', fieldSettingsPanel._previousFormContext);

						field.set('context', fieldContext);
						field.set('context.settingsContet', fieldContext);

						field.render();
					},

					contains: function(field) {
						var instance = this;

						var contains = false;

						instance.eachFields(
							function(currentField) {
								if (currentField === field) {
									contains = true;
								}
							}
						);

						return contains;
					},

					createField: function(fieldType, config) {
						var instance = this;

						var fieldClass = FormBuilderUtil.getFieldClass(fieldType.get('name'));

						return new fieldClass(
							A.merge(
								fieldType.get('defaultConfig'),
								{
									builder: instance,
									evaluatorURL: instance.get('evaluatorURL'),
									readOnly: true
								},
								config
							)
						);
					},

					createFieldFromContext: function(fieldContext) {
						var instance = this;

						var newFieldName = fieldContext.fieldName + Util.generateInstanceId(6);

						var config = A.merge(
							fieldContext,
							{
								fieldName: newFieldName,
								name: newFieldName
							}
						);

						delete config.settingsContext;

						var field = instance.createField(FieldTypes.get(fieldContext.type), config);

						field.set(
							'context',
							A.merge(
								config,
								{
									portletNamespace: Liferay.DDM.Settings.portletNamespace
								}
							)
						);

						var settingsContext = fieldContext.settingsContext;

						FormBuilderUtil.visitLayout(
							settingsContext.pages,
							function(settingsFieldContext) {
								var fieldName = settingsFieldContext.fieldName;

								if (fieldName === 'name') {
									settingsFieldContext.value = newFieldName;
								}
							}
						);

						field.set('context.settingsContext', settingsContext);

						return field;
					},

					createFieldSet: function(fieldSetDefinition) {
						var instance = this;

						var visitor = new Liferay.DDM.LayoutVisitor();

						visitor.set('pages', fieldSetDefinition.pages);

						var fieldColumns = [];

						visitor.set(
							'fieldHandler',
							function(fieldContext) {
								var field = instance.createFieldFromContext(fieldContext);

								fieldColumns.push(field);

								field.render();
							}
						);

						var layoutColumns = [];

						visitor.set(
							'columnHandler',
							function(column) {
								var layoutColumn = new A.LayoutCol(
									{
										size: column.size,
										value: new Liferay.DDM.FormBuilderFieldList(
											{
												fields: fieldColumns
											}
										)
									}
								);

								layoutColumns.push(layoutColumn);

								fieldColumns = [];
							}
						);

						visitor.set(
							'rowHandler',
							function(row) {
								var layout = instance.getActiveLayout();

								layout.addRow(
									instance._currentRowIndex(),
									new A.LayoutRow({cols: layoutColumns})
								);

								layoutColumns = [];
							}
						);

						visitor.visit();
					},

					createNewField: function(fieldType) {
						var instance = this;

						var field = instance.createField(fieldType);

						instance._insertField(field);

						field.newField = true;

						instance.showFieldSettingsPanel(field);
					},

					destroyField: function(field) {
						var instance = this;

						field.destroy();
					},

					duplicateField: function(field) {
						var instance = this;

						var activeLayout = instance.getActiveLayout();
						var layoutColumn = new A.LayoutCol(
							{
								size: 12,
								value: new Liferay.DDM.FormBuilderFieldList(
									{
										fields: []
									}
								)
							}
						);

						var row = instance.getFieldRow(field);

						var newRowIndex = activeLayout.get('rows').indexOf(row.getData('layout-row')) + 1;

						instance._duplicateFieldToColumn(field, layoutColumn);

						var newRow = new A.LayoutRow(
							{
								cols: [layoutColumn]
							}
						);

						activeLayout.addRow(
							newRowIndex,
							newRow
						);

						layoutColumn.get('value').get('fields')[0].get('container').append(instance._getFieldActionsLayout());

						instance._traverseFormPages();
						instance._applyDragAndDrop();

						activeLayout.normalizeColsHeight(new A.NodeList(newRow));
					},

					eachFields: function(callback) {
						var instance = this;

						var visitor = instance.get('visitor');

						visitor.set('pages', instance.get('layouts'));

						visitor.set('fieldHandler', callback);

						visitor.visit();
					},

					editField: function(field) {
						var instance = this;

						instance.showFieldSettingsPanel(field);
					},

					findField: function(fieldName, ignoreCase) {
						var instance = this;

						var field;

						var visitor = instance.get('visitor');

						visitor.set(
							'fieldHandler',
							function(currentField) {
								var currentFieldName = currentField.get('context.fieldName');

								if (currentFieldName) {
									if (currentFieldName === fieldName) {
										field = currentField;
									}
									else if (ignoreCase && currentFieldName.toLowerCase() === fieldName.toLowerCase()) {
										field = currentField;
									}
								}
							}
						);

						visitor.visit();

						return field;
					},

					findTypeOfField: function(field) {
						var instance = this;

						return FieldTypes.get(field.get('type'));
					},

					formatDragRowsToReceiveFieldset: function() {
						var instance = this;

						A.all('.col-empty').each(
							function(col) {
								if (!col.hasClass('col-md-12')) {
									col.removeClass('col-empty');
									col.setAttribute('data-removed-col-empty', true);
								}
							}
						);
					},

					getFieldSettingsPanel: function() {
						var instance = this;

						return instance.get('fieldSettingsPanel');
					},

					getFieldTypesPanel: function() {
						var instance = this;

						return instance.get('fieldTypesPanel');
					},

					getPagesTitle: function() {
						var instance = this;

						return instance._getPageManagerInstance().get('titles');
					},

					getSuccessPageDefinition: function() {
						var instance = this;

						var pageManager = instance._getPageManagerInstance();

						return pageManager.getSuccessPageDefinition();
					},

					isEditMode: function() {
						var instance = this;

						var translating = instance.get('defaultLanguageId') !== instance.get('editingLanguageId');

						return instance.get('formInstanceId') > 0 || translating;
					},


					onHoverColumn: function(event) {
						var instance = this;

						var col = event.currentTarget;

						instance._removeLastFieldHoveredClass();

						if (col.one('.' + CSS_FIELD_LIST_CONTAINER)) {
							instance.lastFieldHovered = col.one('.' + CSS_FIELD_LIST_CONTAINER).addClass('hovered-field');
							return;
						}

						delete instance.lastFieldHovered;
					},

					onLeaveLayoutBuilder: function(event) {
						var instance = this;

						instance._removeLastFieldHoveredClass();
					},

					openConfirmCancelFieldChangesDiolog: function(confirmFn) {
						var instance = this;

						var config = {
							body: TPL_CONFIRM_CANCEL_FIELD_EDITION,
							confirmFn: confirmFn,
							id: 'cancelFieldChangesDialog',
							labelHTML: Liferay.Language.get('yes-cancel'),
							title: Liferay.Language.get('cancel-field-changes-question')
						};

						FormBuilderConfirmDialog.open(config);
					},

					openConfirmDeleteFieldDialog: function(confirmFn) {
						var instance = this;

						var config = {
							body: TPL_CONFIRM_DELETE_FIELD,
							confirmFn: confirmFn,
							id: 'deleteFieldDialog',
							labelHTML: Liferay.Language.get('yes-delete'),
							title: Liferay.Language.get('delete-field-dialog-title'),
							width: 300
						};

						FormBuilderConfirmDialog.open(config);
					},

					openSidebarByButton: function() {
						var instance = this;

						instance.showFieldTypesPanel();
						instance.bindSidebarFieldDragAction();
					},

					showFieldSettingsPanel: function(field) {
						var instance = this;

						if (instance._sidebar) {
							instance._sidebar.close();
						}

						var settingsPanel = instance.getFieldSettingsPanel();

						settingsPanel.set('field', field);

						settingsPanel.open();
					},

					showFieldTypesPanel: function() {
						var instance = this;

						var fieldTypesPanel = instance.getFieldTypesPanel();

						fieldTypesPanel.open();
					},

					unformatFieldsetRows: function() {
						A.all('[data-removed-col-empty="true"]').each(
							function(col) {
								col.addClass('col-empty');
								col.removeAttribute('data-removed-col-empty', true);
							}
						);
					},

					updateDragAndDropBySidebar: function(fieldNode) {
						var instance = this;

						var fieldSetId = fieldNode.getData('field-set-id');
						var fieldType = fieldNode.getData('field-type');

						instance._newFieldContainer = fieldNode.ancestor('.col').getData('layout-col');

						if (fieldSetId) {
							return instance._addFieldSetInDragAndDropLayout(fieldSetId);
						}

						instance.createNewField(fieldType);
					},

					_addColumnInRow: function(row, hasContext) {
						var instance = this;

						if (hasContext) {
							row.push(
								{
									columns: [{
										fields: [],
										size: 12
									}]
								}
							);
						}
						else {
							row.push(new A.LayoutRow());
						}

						return row;
					},

					_addDragAndDropActions: function() {
						var instance = this;

						instance.sortable1 = new A.SortableLayout(
							{
								delegateConfig: {
									target: false,
									useShim: false
								},
								dragNodes: '.layout-col-content',
								dropNodes: '.layout-row .col-empty',
								proxyNode: '<div></div>'
							}
						);

						instance.afterDragStart(instance.sortable1);

						instance.afterPlaceholderAlign(instance.sortable1);

						instance.afterDragEnd(instance.sortable1);

						instance._getPageManagerInstance()._getWizard().after(
							'selectedChange',
							function() {
								setTimeout(
									function() {
										instance._destroySortable(instance.sortable1);
										instance._applyDragAndDrop();
									},
									0
								);
							}
						);
					},

					_addFieldsChangeListener: function(layouts) {
						var instance = this;

						layouts.forEach(
							function(layout) {
								instance._fieldsChangeHandles.push(
									layout.after(
										'liferay-ddm-form-builder-field-list:fieldsChange',
										A.bind(instance._afterFieldsChange, instance)
									)
								);
							}
						);
					},

					_addFieldSetInDragAndDropLayout: function(fieldSetId) {
						var instance = this;

						instance._getFieldSetDefinitionRetriever(
							fieldSetId,
							function(fieldSetDefinition) {
								instance.createFieldSet(fieldSetDefinition);
								instance.unformatFieldsetRows();
								instance._traverseFormPages();
								instance._applyDragAndDrop();
							}
						);
					},

					_addToStack: function(node) {
						var instance = this;

						instance._clearStack();

						node.addClass('col-empty-over');
						instance.activeDropColStack.push(node);
					},

					_afterActivePageNumberChange: function(event) {
						var instance = this;

						if (event.newVal > instance.get('layouts').length) {
							instance.fire(
								'successPageVisibility',
								{
									visible: true
								}
							);
						}
						else {
							instance.fire(
								'successPageVisibility',
								{
									visible: false
								}
							);

							FormBuilder.superclass._afterActivePageNumberChange.apply(instance, arguments);

							instance._syncRequiredFieldsWarning();
							instance._syncRowsLastColumnUI();
						}
					},

					_afterEditingLanguageIdChange: function(event) {
						var instance = this;

						instance.eachFields(
							function(field) {
								field.set('locale', event.newVal);

								field.saveSettings();
							}
						);

						var pageManager = instance.get('pageManager');

						pageManager.set('editingLanguageId', event.newVal);
					},

					_afterFieldClick: function(event) {
						var instance = this;

						var field = event.currentTarget.getData('field-instance');

						if (event.target.ancestor('.lfr-ddm-field-actions-container')) {
							return;
						}
						instance.editField(field);
					},

					_afterFieldListChange: function() {
						var instance = this;

						instance._syncRequiredFieldsWarning();
					},

					_afterFormBuilderRender: function() {
						var instance = this;

						instance._fieldToolbar.destroy();

						instance.getFieldSettingsPanel();
						instance._renderArrowActions();
						instance._renderFields();
						instance._renderPages();
						instance._renderRequiredFieldsWarning();
						instance._syncRequiredFieldsWarning();
						instance._syncRowsLastColumnUI();
						instance._traverseFormPages();
						instance._applyDragAndDrop();

						layoutBuilder.detach('layout-row:colsChange');

						layoutBuilder._delegateDrag.detach('drag:end');

						layoutBuilder._delegateDrag.after('drag:end', A.bind(instance._afterResizeColEnd, instance));
						layoutBuilder._delegateDrag.after('drag:align', A.bind(instance._afterDragAlign, instance));
						layoutBuilder._delegateDrag.after('drag:start', A.bind(instance._afterDragStart, instance));

						layoutBuilder.set('enableRemoveRows', false);
						layoutBuilder.set('enableMoveRows', false);
					},

					_afterLayoutColsChange: function(event) {
						var instance = this;

						FormBuilder.superclass._afterLayoutColsChange.apply(instance, arguments);

						instance._syncRowLastColumnUI(event.target);
					},

					_afterLayoutRowsChange: function(event) {
						var instance = this;

						FormBuilder.superclass._afterLayoutRowsChange.apply(instance, arguments);

						event.newVal.forEach(instance._syncRowLastColumnUI);
					},

					_afterLayoutsChange: function() {
						var instance = this;

						FormBuilder.superclass._afterLayoutsChange.apply(instance, arguments);

						instance._syncRequiredFieldsWarning();
						instance._syncRowsLastColumnUI();
					},

					_afterRemoveField: function(field) {
						var instance = this;

						instance.removeChild(field);

						instance.getFieldSettingsPanel().close();
					},

					_afterResizeColEnd: function() {
						var instance = this;

						var layoutBuilder = instance._layoutBuilder;

						var dragNode = layoutBuilder._delegateDrag.get('lastNode');
						var row = dragNode.ancestor(SELECTOR_ROW);

						if (row) {
							if (dragNode.getData('layout-action') && dragNode.getData('layout-action') === 'addColumn') {
								layoutBuilder._insertColumnAfterDropHandles(dragNode);
							}
							else {
								layoutBuilder._resize(dragNode);
								layoutBuilder.get('layout').normalizeColsHeight(new A.NodeList(row));
							}

							row.removeClass(CSS_RESIZE_COL_DRAGGING);

							layoutBuilder._hideBreakpoints(row);
						}

						layoutBuilder._syncDragHandles();

						layoutBuilder.dragging = false;

						dragNode.removeClass(CSS_RESIZE_COL_DRAGGABLE_DRAGGING);

						dragNode.show();
						instance._traverseFormPages();
						instance._applyDragAndDrop();
					},

					_afterSelectFieldType: function(event) {
						var instance = this;

						instance.createNewField(event.fieldType);
					},

					_applyDragAndDrop: function(event) {
						var instance = this;

						instance._removeAddWrapper();
						instance._formatGridLayout();
						instance._addDragAndDropActions();
					},

					_beforeFormBuilderRender: function() {
						var instance = this;

						instance.activeDropColStack = [];
					},

					_clearStack: function() {
						var instance = this;

						while (instance.activeDropColStack.length > 0) {
							instance.activeDropColStack.pop().removeClass('col-empty-over');
						}
					},

					_createFieldActions: function() {
						var instance = this;

						instance.eachFields(
							function(field) {
								field.get('container').append(instance._getFieldActionsLayout());
							}
						);
					},

					_currentRowIndex: function() {
						var instance = this;

						var layout = instance.getActiveLayout();

						var rows = layout.get('rows');

						if (A.instanceOf(instance._newFieldContainer.get('value'), A.FormBuilderFieldList)) {
							var row = instance._newFieldContainer.get('node').ancestor('.row').getData('layout-row');

							return A.Array.indexOf(rows, row);
						}

						if (rows.length > 0) {
							return rows.length - 1;
						}

						return 0;
					},

					_destroySortable: function(sortable) {
						var instance = this;

						var dropNodes = sortable.get('dropNodes');

						dropNodes.each(
							function(node) {
								var drop = A.DD.DDM.getDrop(node);

								if (drop) {
									drop.destroy();
								}
							}
						);

						sortable.delegate.destroy();
						sortable.destroy();
					},

					_duplicateFieldToColumn: function(field, column) {
						var instance = this;

						var fieldCopy = field.copy();

						fieldCopy.set('fieldName', fieldCopy.get('fieldName') + 1);
						fieldCopy.render();

						column.get('value').addField(fieldCopy, column.get('value').get('fields').length);
					},

					_formatDragRow: function(rows, activePageIndex) {
						var instance = this;

						var newRows = {
							contextRows: [],
							layoutRows: []
						};
						var rowsIndex = [];

						instance.get('layouts')[activePageIndex].get('rows').forEach(
							function(row, index) {
								row.get('cols').forEach(
									function(col) {
										if (col.get('value') && col.get('value').get('fields').length) {
											rowsIndex.push(index);
										}
									}
								);
							}
						);

						rows.forEach(
							function(row, index) {
								if (rowsIndex.indexOf(index) != -1) {
									newRows.layoutRows = instance._addColumnInRow(newRows.layoutRows);
									newRows.layoutRows.push(row);
								}

								if (rows.length - 1 == index) {
									newRows.layoutRows = instance._addColumnInRow(newRows.layoutRows);
								}
							}
						);

						return newRows;
					},

					_formatGridLayout: function() {
						var instance = this;
						var rows = instance.get('layouts')[instance._getActiveLayoutIndex()].get('rows');

						instance.gridDOM = [];

						rows.forEach(
							function(row) {
								instance.gridDOM.push(row.get('node').all('.col').get('nodes'));
							}
						);
					},

					_formatNewDropRows: function(activePageIndex) {
						var instance = this;

						var activeLayout = instance.get('layouts')[activePageIndex];
						var rows = activeLayout.get('rows');

						var rowsData = instance._formatDragRow(rows, activePageIndex);

						instance.get('layouts')[activePageIndex].set('rows', rowsData.layoutRows);
					},

					_getFieldActionsLayout: function() {
						var instance = this;

						return '<div class="lfr-ddm-field-actions-container"> ' +
							'<button class="btn btn-monospaced btn-sm label-primary lfr-duplicate-field" type="button">' + Liferay.Util.getLexiconIconTpl('paste') + '</button>' +
							'<button class="btn btn-monospaced btn-sm label-primary lfr-delete-field" type="button">' + Liferay.Util.getLexiconIconTpl('trash') + '</button>' +
							'</div>';
					},

					_getFieldSetDefinitionRetriever: function(fieldSetId, callback) {
						var fieldSetSelected = FieldSets.get(fieldSetId);

						var definitionRetriever = FieldSets.getDefinitionRetriever();

						definitionRetriever.getDefinition(fieldSetSelected).then(callback);
					},

					_getFieldSettingsPanel: function(fieldSettingsPanel) {
						var instance = this;

						instance._sidebar = fieldSettingsPanel;

						return fieldSettingsPanel;
					},

					_getFieldTypesPanel: function(fieldTypesPanel) {
						var instance = this;

						instance._sidebar = fieldTypesPanel;

						return fieldTypesPanel;
					},

					_getPageManagerInstance: function(config) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var deserializer = instance.get('deserializer');

						var layouts = instance.get('layouts');

						if (!instance._pageManager) {
							var context = instance.get('context');

							instance._pageManager = new Liferay.DDM.FormBuilderPagesManager(
								A.merge(
									{
										builder: instance,
										defaultLanguageId: instance.get('defaultLanguageId'),
										editingLanguageId: instance.get('editingLanguageId'),
										localizedDescriptions: deserializer.get('descriptions'),
										localizedTitles: deserializer.get('titles'),
										mode: context.paginationMode,
										pageHeader: contentBox.one('.' + CSS_PAGE_HEADER),
										pagesQuantity: layouts.length,
										paginationContainer: contentBox.one('.' + CSS_PAGES),
										showPagination: instance.get('showPagination'),
										tabviewContainer: contentBox.one('.' + CSS_FORM_BUILDER_TABS)
									},
									config
								)
							);

							instance._pageManager.setSuccessPage(context.successPageSettings);

							instance.set('pageManager', instance._pageManager);
						}

						return instance._pageManager;
					},

					_getVisitor: function(visitor) {
						var instance = this;

						visitor.set('pages', instance.get('layouts'));

						return visitor;
					},

					_insertCutRowIcon: function(row) {
						var instance = this;

						var cutButton = row.ancestor('.' + CSS_ROW_CONTAINER_ROW).one('.layout-builder-move-cut-button');

						if (cutButton) {
							cutButton.insert(Liferay.Util.getLexiconIconTpl('cut'));
						}
					},

					_insertField: function(field) {
						var instance = this;

						field.set(
							'context',
							{
								label: '',
								placeholder: '',
								portletNamespace: Liferay.DDM.Settings.portletNamespace,
								readOnly: true,
								showLabel: true,
								type: field.get('type'),
								value: '',
								visible: true
							}
						);

						if (instance._newFieldContainer) {
							if (A.instanceOf(instance._newFieldContainer.get('value'), A.FormBuilderFieldList)) {
								instance._newFieldContainer.get('value').addField(field);
								instance._newFieldContainer.set('removable', false);
							}
							else {
								instance._addNestedField(
									instance._newFieldContainer,
									field,
									instance._newFieldContainer.get('nestedFields').length
								);
							}

							var newFieldContent = instance._newFieldContainer.get('value').get('content');
							var newFieldParent = newFieldContent.ancestor();

							if (newFieldParent) {
								instance._newFieldContainer.get('node').append(newFieldParent);
							}
							else {
								var newLayoutContainer = A.one(document.createElement('div'));

								newLayoutContainer.addClass('layout-col-content');
								instance._newFieldContainer.get('node').append(newLayoutContainer);
								newLayoutContainer.append(newFieldContent);
							}

							instance._newFieldContainer = null;
						}

						instance._traverseFormPages();
						instance._applyDragAndDrop();

						instance._syncRequiredFieldsWarning();

						instance._renderField(field);
					},

					_insertRemoveRowButton: function(layoutRow, row) {
						var instance = this;

						var deleteButton = row.ancestor('.' + CSS_ROW_CONTAINER_ROW).all('.layout-builder-remove-row-button');

						if (deleteButton) {
							deleteButton.empty();
							deleteButton.insert(Liferay.Util.getLexiconIconTpl('trash'));
						}
					},

					_makeEmptyFieldList: function(col) {
						col.set('value', new Liferay.DDM.FormBuilderFieldList());
					},

					_onClickDuplicateFieldButton: function(event) {
						var instance = this;

						var field = event.currentTarget.ancestor('.' + CSS_FIELD).getData('field-instance');

						return instance.duplicateField(field);
					},

					_onClickPaginationItem: function(event) {
						var instance = this;

						event.halt();
					},

					_onClickRemoveFieldButton: function(event) {
						var instance = this;

						var field = event.currentTarget.ancestor('.' + CSS_FIELD).getData('field-instance');

						return instance._removeFieldCol(field);
					},

					_openNewFieldPanel: function(target) {
						var instance = this;

						instance._newFieldContainer = target.ancestor('.col').getData('layout-col');
						instance.showFieldTypesPanel();
					},

					_removeAddWrapper: function() {
						var instance = this;
						var listCols = A.all('.form-builder-field-list-empty');

						if (listCols) {
							listCols.get('node').forEach(
								function(element) {
									if (!element.ancestor('.col-empty')) {
										element.ancestor('.col').addClass('col-empty');
										element.ancestor('.col').empty();
									}
								}
							);

							A.all('.form-builder-field-list-add-container').remove();
						}
					},

					_removeDropTarget: function(dropInstance, sortable) {
						var colDrop = A.DD.DDM.getDrop(dropInstance);

						if (colDrop) {
							colDrop.destroy();
							sortable.removeDropTarget(colDrop);
						}
					},

					_removeFieldCol: function(field) {
						var instance = this;

						var fieldNode = field.get('container');

						var col = field.get('content').ancestor('.col').getData('layout-col');
						var row;

						field._col = col;

						instance.openConfirmDeleteFieldDialog(
							function() {
								var layout = instance.getActiveLayout();

								field._col.get('value').removeField(field);
								row = field.get('content').ancestor('.layout-row');
								layout.normalizeColsHeight(new A.NodeList(row));
								fieldNode.remove();
								instance.getFieldSettingsPanel().close();
								instance._traverseFormPages();
								instance._applyDragAndDrop();
							}
						);
					},

					_removeLastFieldHoveredClass: function() {
						var instance = this;

						if (instance.lastFieldHovered) {
							instance.lastFieldHovered.removeClass('hovered-field');
						}
					},

					_renderArrowActions: function() {
						var instance = this;

						var layoutBuilder = instance._layoutBuilder;

						layoutBuilder.TPL_RESIZE_COL_DRAGGABLE = MOVE_COLUMN_TPL;
						layoutBuilder._uiSetEnableResizeCols(layoutBuilder.get('enableResizeCols'));

						var boundingBox = instance.get('boundingBox');

						boundingBox.all('.' + CSS_RESIZE_COL_DRAGGABLE + ':not(.lfr-tpl)').each(
							function(handler) {
								handler.html(MOVE_COLUMN_CONTAINER);
							}
						);
					},

					_renderContentBox: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var strings = instance.get('strings');

						var headerTemplate = A.Lang.sub(
							instance.TPL_HEADER,
							{
								formTitle: strings.formTitle
							}
						);

						contentBox.append(instance.TPL_TABVIEW);
						contentBox.append(instance.TPL_PAGE_HEADER);
						contentBox.append(headerTemplate);
						contentBox.append(instance.TPL_LAYOUT);
						contentBox.append(instance.TPL_PAGES);
					},

					_renderField: function(field) {
						var instance = this;

						var activeLayout = instance.getActiveLayout();

						field.set('builder', instance);

						field.after(
							'render',
							function() {
								var row = instance.getFieldRow(field);

								activeLayout.normalizeColsHeight(new A.NodeList(row));
								field.get('container').append(instance._getFieldActionsLayout());
								field.get('container').ancestor('.col').removeClass('col-empty');
							}
						);

						field.render();
					},

					_renderFields: function() {
						var instance = this;

						var visitor = instance.get('visitor');

						visitor.set('fieldHandler', A.bind('_renderField', instance));

						visitor.visit();

						instance._createFieldActions();
					},

					_renderPages: function() {
						var instance = this;

						var pages = instance.get('pages');

						pages._uiSetActivePageNumber(pages.get('activePageNumber'));
					},

					_renderRequiredFieldsWarning: function() {
						var instance = this;

						var pageManager = instance._getPageManagerInstance();

						if (!instance._requiredFieldsWarningNode) {
							instance._requiredFieldsWarningNode = A.Node.create(
								Lang.sub(
									TPL_REQURIED_FIELDS,
									{
										message: Lang.sub(
											Liferay.Language.get('all-fields-marked-with-x-are-required'),
											['<i class="icon-asterisk text-warning"></i>']
										)
									}
								)
							);
						}

						instance._requiredFieldsWarningNode.appendTo(pageManager.get('pageHeader'));
					},

					_renderRowIcons: function() {
						var instance = this;

						var rows = A.all('.layout-row');

						rows.each(
							function(row) {
								instance._insertCutRowIcon(row);
								instance._insertRemoveRowButton(null, row);
							}
						);
					},

					_setDragAndDropDelegateConfig: function(sortableInstance, dragNodes) {
						var config = {
							bubbleTargets: sortableInstance,
							dragConfig: {},
							nodes: dragNodes,
							target: false
						};

						A.mix(
							config.dragConfig,
							{
								groups: sortableInstance.get('groups'),
								startCentered: true
							}
						);

						return config;
					},

					_setFieldTypes: function(fieldTypes) {
						var instance = this;

						return AArray.filter(
							fieldTypes,
							function(item) {
								return !item.get('system');
							}
						);
					},

					_syncRequiredFieldsWarning: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var hasRequiredField = false;

						var visitor = instance.get('visitor');

						visitor.set('pages', instance.get('layouts'));

						instance.eachFields(
							function(field) {
								var fieldVisible = boundingBox.contains(field.get('container'));

								if (fieldVisible && field.get('required')) {
									hasRequiredField = true;
								}
							}
						);

						instance._requiredFieldsWarningNode.toggle(hasRequiredField);
					},

					_syncRowIcons: function() {
						var instance = this;

						instance._renderRowIcons();

						instance._layoutBuilder.after(instance._insertCutRowIcon, instance._layoutBuilder, '_insertCutButtonOnRow');

						instance._layoutBuilder.after(instance._insertRemoveRowButton, instance._layoutBuilder, '_insertRemoveButtonBeforeRow');
					},

					_syncRowLastColumnUI: function(row) {
						var lastColumn = row.get('node').one('.last-col');

						if (lastColumn) {
							lastColumn.removeClass('last-col');
						}

						var cols = row.get('cols');

						cols[cols.length - 1].get('node').addClass('last-col');
					},

					_syncRowsLastColumnUI: function() {
						var instance = this;

						var rows = instance.getActiveLayout().get('rows');

						rows.forEach(instance._syncRowLastColumnUI);
					},

					_traverseFormPages: function() {
						var instance = this;
						var pages = instance.get('layouts');

						pages.forEach(
							function(activePage, index) {
								instance._formatNewDropRows(index);
							}
						);
					},

					_updateDragAndDropContext: function(fieldNodeEnd, sortable, positions) {
						var instance = this;

						var activePageIndex = instance._getActiveLayoutIndex();
						var fieldColumnEnd = fieldNodeEnd.ancestor('.col');
						var fieldColumnStart = A.one('.current-dragging');

						var activeLayout = instance.get('layouts')[activePageIndex];
						var layoutRows = activeLayout.get('rows');
						var positionColumnEnd = positions.positionColumnEnd;
						var positionColumnStart = positions.positionColumnStart;
						var positionRowEnd = positions.positionRowEnd;
						var positionRowStart = positions.positionRowStart;

						var columnEnd = layoutRows[positionRowEnd].get('cols')[positionColumnEnd];

						fieldColumnStart.addClass('col-empty');
						fieldColumnStart.removeClass('col-empty-over');
						fieldColumnEnd.removeClass('col-empty');
						fieldNodeEnd.removeClass('hidden');
						fieldColumnStart.removeClass('current-dragging');

						instance._removeDropTarget(fieldColumnEnd, sortable);

						var field = fieldColumnEnd.one('.' + CSS_FIELD).getData('field-instance');

						if (positionRowEnd != positionRowStart || (positionRowEnd == positionRowStart && positionColumnEnd != positionColumnStart)) {
							layoutRows[positionRowStart].get('cols')[positionColumnStart].get('value').removeField(field);
							layoutRows[positionRowEnd].get('cols')[positionColumnEnd].get('value').addField(field);

							columnEnd.get('node').one('.layout-col-content').empty();
							columnEnd.get('node').one('.layout-col-content').append(columnEnd.get('value').get('content'));
							field.render();
						}

						instance._clearStack();

						setTimeout(
							function() {
								if (positionRowEnd != positionRowStart || (positionRowEnd == positionRowStart && positionColumnEnd != positionColumnStart)) {
									instance._formatNewDropRows(instance._getActiveLayoutIndex());
								}

								instance._destroySortable(sortable);
								instance._applyDragAndDrop();
							},
							0
						);
					},

					_updateDragAndDropUI: function(fieldNodeEnd, sortable, positions) {
						var instance = this;

						var layoutRows = instance.get('layouts')[instance._getActiveLayoutIndex()].get('rows');
						var positionColumnEnd = positions.positionColumnEnd;
						var positionColumnStart = positions.positionColumnStart;
						var positionRowEnd = positions.positionRowEnd;
						var positionRowStart = positions.positionRowStart;

						var columnEnd = layoutRows[positionRowEnd].get('cols')[positionColumnEnd];
						var columnStart = layoutRows[positionRowStart].get('cols')[positionColumnStart];
						var fieldColumnEnd = fieldNodeEnd.ancestor('.col');
						var fieldColumnStart = A.one('.current-dragging');

						columnStart.get('node').append(columnEnd.get('node').one('> div'));
						columnEnd.get('node').empty();

						fieldColumnEnd.removeClass('col-empty-over');

						fieldColumnStart.removeClass('current-dragging');
						fieldColumnStart.removeClass('col-empty');
						fieldNodeEnd.removeClass('hidden');

						return instance._removeDropTarget(fieldColumnStart, sortable);
					},

					_updateFieldsetDraggingStatus: function(fieldset, status) {
						return fieldset.set('isDragging', status);
					},

					_valueDeserializer: function() {
						var instance = this;

						return new Liferay.DDM.LayoutDeserializer(
							{
								builder: instance
							}
						);
					},

					_valueFieldSets: function() {
						var instance = this;

						return FieldSets.getAll();
					},

					_valueFieldSettingsPanel: function() {
						var instance = this;

						var fieldSettingsPanel = new Liferay.DDM.FormBuilderFieldsSettingsSidebar(
							{
								builder: instance
							}
						);

						fieldSettingsPanel.render('#wrapper');

						return fieldSettingsPanel;
					},

					_valueFieldTypes: function() {
						var instance = this;

						return FieldTypes.getAll();
					},

					_valueFieldTypesPanel: function() {
						var instance = this;

						var fieldTypesPanel = new Liferay.DDM.FormBuilderFieldTypesSidebar(
							{
								builder: instance,
								fieldSets: instance.get('fieldSets'),
								fieldTypes: instance.get('fieldTypes')
							}
						);

						fieldTypesPanel.render('#wrapper');

						return fieldTypesPanel;
					},

					_valueLayouts: function() {
						var instance = this;

						var deserializer = instance.get('deserializer');

						var context = instance.get('context');

						deserializer.set('pages', context.pages);

						return deserializer.deserialize();
					},

					_valueVisitor: function() {
						var instance = this;

						return new Liferay.DDM.FormBuilderLayoutVisitor(
							{
								pages: instance.get('layouts')
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilder = FormBuilder;
	},
	'',
	{
		requires: ['aui-form-builder', 'aui-form-builder-pages', 'aui-popover', 'aui-sortable-layout', 'liferay-ddm-form-builder-confirmation-dialog', 'liferay-ddm-form-builder-field-list', 'liferay-ddm-form-builder-field-options-toolbar', 'liferay-ddm-form-builder-field-settings-sidebar', 'liferay-ddm-form-builder-field-support', 'liferay-ddm-form-builder-field-type', 'liferay-ddm-form-builder-field-types-sidebar', 'liferay-ddm-form-builder-fieldset', 'liferay-ddm-form-builder-layout-deserializer', 'liferay-ddm-form-builder-layout-visitor', 'liferay-ddm-form-builder-pages-manager', 'liferay-ddm-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-util']
	}
);