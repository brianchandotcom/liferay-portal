AUI.add(
	'liferay-ddm-form-builder-dd-support',
	function(A) {
		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_RESIZE_COL_BREAKPOINT = A.getClassName('layout', 'builder', 'resize', 'col', 'breakpoint');

		var CSS_RESIZE_COL_DRAGGABLE = A.getClassName('layout', 'builder', 'resize', 'col', 'draggable');

		var CSS_RESIZE_COL_DRAGGABLE_DRAGGING = A.getClassName('layout', 'builder', 'resize', 'col', 'draggable', 'dragging');

		var CSS_RESIZE_COL_DRAGGING = A.getClassName('layout', 'builder', 'resize', 'col', 'dragging');

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var SELECTOR_ROW = '.layout-row';

		var FormBuilderDDSupport = function() {
		};

		FormBuilderDDSupport.ATTRS = {
		};

		FormBuilderDDSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after('render', instance._afterDragAndDropRender, instance)
				);
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

						if (instance.sidebarSortable) {
							instance.sidebarSortable.set('groups', ['sortable-layout']);
						}

						if (fieldNodeEnd.attr('data-field-type-name') || fieldNodeEnd.getData('field-set-id')) {
							return instance.updateDragAndDropBySidebar(event, fieldNodeEnd);
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

						A.all('.list-group-item').each(
							function(item) {
								instance._removeDropTarget(item, sortable1);
							}
						);

						if (instance._sidebar.isOpen()) {
							instance._sidebar.close();
						}

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

				instance.sidebarSortable.after(
					'drag:start',
					function(event) {
						var clonedNode = A.DD.DDM.activeDrag.get('node').clone();
						var fieldNodeStart = event.target.get('node');
						var fieldParent = fieldNodeStart.ancestor();
						var proxyActive = A.one('.yui3-dd-proxy');

						if (fieldParent.getData('added-as-drop')) {
							fieldParent.setData('added-as-drop', true);
							instance.sidebarSortable.addDropNode(fieldParent);
						}

						A.DD.DDM._activateTargets();

						instance._addToStack(fieldNodeStart);

						if (instance._sidebar.isOpen()) {
							instance._sidebar.close();
						}

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
						dropNodes: '.col-empty',
						proxy: null
					}
				);

				instance.sidebarSortable._getAppendNode = function() {
					this.appendNode = A.one(document.createElement('div'));
					return this.appendNode;
				};

				instance.beforeSidebarDragStart();
				instance.afterPlaceholderAlign(instance.sidebarSortable);
				instance.afterDragEnd(instance.sidebarSortable);
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

			removeEmptyFormClass: function() {
				var instance = this;

				if (A.one('.lfr-initial-col')) {
					A.one('#lfr-initial-col-message').remove();
					A.one('.lfr-initial-col').removeClass('col-empty-over');
					A.one('.lfr-initial-col').removeClass('lfr-initial-col');
				}
			},

			showResizeableColumnsDrop: function(columnSide, dropNode, increment) {
				var instance = this;

				var rowNode = dropNode.ancestor(SELECTOR_ROW);

				var columnsBreakPoints = rowNode.all('.' + CSS_RESIZE_COL_BREAKPOINT);

				for (var i = 0; i < columnSide.get('size'); i++) {
					if (increment && columnsBreakPoints.item(dropNode.getData('layout-position') + i)) {
						columnsBreakPoints.item(dropNode.getData('layout-position') + i).setStyle('display', 'block');
					}
					else if (!increment && columnsBreakPoints.item(dropNode.getData('layout-position') - i)) {
						columnsBreakPoints.item(dropNode.getData('layout-position') - i).setStyle('display', 'block');
					}
				}
			},

			updateDragAndDropBySidebar: function(event, dragFieldNode) {
				var instance = this;

				var appendNode = event.currentTarget.appendNode;
				var fieldSetId = dragFieldNode.getData('field-set-id');
				var fieldType = FieldTypes.get(dragFieldNode.attr('data-field-type-name'));

				if (!appendNode) {
					return;
				}
				else if (!appendNode.ancestor('.col') || appendNode.ancestor('.col').one('.' + CSS_FIELD)) {
					appendNode.remove();
					return;
				}

				instance.removeEmptyFormClass();

				instance._newFieldContainer = appendNode.ancestor('.col').getData('layout-col');
				appendNode.remove();

				if (fieldSetId) {
					instance._addFieldSetInDragAndDropLayout(fieldSetId);
				}
				else {
					instance.createNewField(fieldType);
					instance._traverseFormPages();
					instance._destroySortable(instance.sortable1);
					instance._applyDragAndDrop();
				}
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
						proxy: null,
						proxyNode: '<div></div>'
					}
				);

				instance.afterDragStart(instance.sortable1);

				instance.afterPlaceholderAlign(instance.sortable1);

				instance.afterDragEnd(instance.sortable1);
			},

			_addFieldSetInDragAndDropLayout: function(fieldSetId) {
				var instance = this;

				instance._getFieldSetDefinitionRetriever(
					fieldSetId,
					function(fieldSetDefinition) {
						instance.createFieldSet(fieldSetDefinition);
						instance.unformatFieldsetRows();
						instance._destroySortable(instance.sortable1);
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

			_adjustEmptyForm: function(activeLayout) {
				var instance = this;

				if (activeLayout.get('rows').length == 1 && instance._verifyEmptyForm(activeLayout.get('rows')[0]) && !A.one('#lfr-initial-col-message')) {
					var columnNode = activeLayout.get('rows')[0].get('cols')[0].get('node');
					var columnMessageNode = A.Node.create('<div/>');

					columnMessageNode.text(Liferay.Language.get('drag-from-sidebar-and-drop-here'));
					columnMessageNode.setAttribute('id', 'lfr-initial-col-message');

					columnNode.get('parentNode').all('.layout-builder-add-col-draggable').remove();
					columnNode.addClass('lfr-initial-col');
					columnNode.append(columnMessageNode);
				}
			},

			_afterDragAlign: function(event) {
				var instance = this;

				var dragNode = event.target.get('node');

				if (instance.lastFieldHovered) {
					instance.lastFieldHovered.addClass('hovered-field');
				}

				instance._syncColsSize(dragNode);
			},

			_afterDragAndDropRender: function() {
				var instance = this;

				var layoutBuilder = instance._layoutBuilder;

				instance._applyDragAndDrop();

				layoutBuilder.detach('layout-row:colsChange');

				layoutBuilder._delegateDrag.detach('drag:end');

				layoutBuilder._delegateDrag.after('drag:end', A.bind(instance._afterResizeColEnd, instance));
				layoutBuilder._delegateDrag.after('drag:align', A.bind(instance._afterDragAlign, instance));
				layoutBuilder._delegateDrag.after('drag:start', A.bind(instance._afterDragStart, instance));

				layoutBuilder.set('enableRemoveRows', false);
				layoutBuilder.set('enableMoveRows', false);

				instance._adjustEmptyForm(instance.getActiveLayout());
			},

			_afterDragStart: function(event) {
				var instance = this;

				var dragNode = event.target.get('node');
				var layoutBuilder = instance._layoutBuilder;

				layoutBuilder.dragging = true;

				dragNode.hide();

				instance.initialDragPosition = dragNode.getData('layout-position');

				if (A.one('.last-drag-row')) {
					A.one('.last-drag-row').removeClass('last-drag-row');
				}

				dragNode.ancestor(SELECTOR_ROW).addClass('last-drag-row');

				layoutBuilder._hideColDraggableBoundaries();
			},

			_afterResizeColEnd: function() {
				var instance = this;

				var layoutBuilder = instance._layoutBuilder;

				var dragNode = layoutBuilder._delegateDrag.get('lastNode');
				var row = dragNode.ancestor(SELECTOR_ROW);

				instance.hasColumnRightCreated = false;
				instance.hasColumnLeftCreated = false;
				instance.initialRightSize = false;
				instance.initialLeftSize = false;
				instance.initialDragPosition = false;
				instance.lastRemovedDropPosition = false;

				if (instance.lastColumnAdd) {
					instance.lastColumnAdd.removeClass('col-empty');
					instance.lastColumnAdd = false;
				}

				if (row) {
					if (instance.lastColumnRemoved) {
						row.getData('layout-row').removeCol(instance.lastColumnRemoved.getData('layout-col'));
						instance.lastColumnRemoved.remove();
						instance.lastColumnRemoved = false;
					}

					row.removeClass(CSS_RESIZE_COL_DRAGGING);

					layoutBuilder._hideBreakpoints(row);
				}

				layoutBuilder._syncDragHandles();

				layoutBuilder.dragging = false;

				dragNode.removeClass(CSS_RESIZE_COL_DRAGGABLE_DRAGGING);

				dragNode.show();

				instance._traverseFormPages();
				instance._destroySortable(instance.sortable1);
				instance._applyDragAndDrop();

				instance.addedColumWhileDragging = false;
			},

			_applyDragAndDrop: function(event) {
				var instance = this;

				instance._removeAddWrapper();
				instance._formatGridLayout();
				instance._addDragAndDropActions();
			},

			_clearStack: function() {
				var instance = this;

				while (instance.activeDropColStack.length > 0) {
					instance.activeDropColStack.pop().removeClass('col-empty-over');
				}
			},

			_destroySortable: function(sortable) {
				var instance = this;

				if (sortable) {
					var dropNodes = sortable.get('dropNodes');

					dropNodes.each(
						function(node) {
							var drop = A.DD.DDM.getDrop(node);

							if (drop) {
								drop.destroy();

								drop = null;
							}
						}
					);

					sortable.delegate.destroy();
					sortable.destroy();
				}
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

			_formatNewDropRows: function(activePageIndex) {
				var instance = this;

				var activeLayout = instance.get('layouts')[activePageIndex];
				var rows = activeLayout.get('rows');

				var rowsData = instance._formatDragRow(rows, activePageIndex);

				instance.get('layouts')[activePageIndex].set('rows', rowsData.layoutRows);
			},

			_removeDropTarget: function(dropInstance, sortable) {
				var colDrop = A.DD.DDM.getDrop(dropInstance);

				if (colDrop) {
					colDrop.destroy();
					sortable.removeDropTarget(colDrop);
				}
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

			_syncDragHandles: function(rowNode) {
				var instance = this;

				var dragHandles = rowNode.all('.' + CSS_RESIZE_COL_DRAGGABLE);
				var row = rowNode.getData('layout-row');

				var cols = row.get('cols');
				var currentPos = 0;
				var index;
				var numberOfCols = cols.length - 1;

				if (!numberOfCols) {
					numberOfCols = 1;
				}

				for (index = 0; index <= numberOfCols; index++) {
					var dragNode = dragHandles.item(index);

					currentPos += cols[index].get('size');
					dragNode.setStyle('left', ((currentPos * 100) / 12) + '%');

					if (index == 1) {
						dragNode.setData('layout-col1', cols[cols.length - 2]);
						dragNode.setData('layout-col2', cols[cols.length - 1]);
					}
					else {
						dragNode.setData('layout-col1', cols[index]);
						dragNode.setData('layout-col2', cols[index + 1]);
					}

					dragNode.setData('layout-position', currentPos);
				}
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
				var instance = this;

				return fieldset.set('isDragging', status);
			},

			_verifyEmptyForm: function(row) {
				var instance = this;

				if (row.get('cols').length == 1 && row.get('cols')[0].get('value') && !row.get('cols')[0].get('value').get('fields').length) {
					return true;
				}
			}
		};

		Liferay.namespace('DDM').FormBuilderDDSupport = FormBuilderDDSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-field-types', 'liferay-ddm-form-renderer']
	}
);