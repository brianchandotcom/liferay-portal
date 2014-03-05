AUI.add(
	'liferay-input-move-boxes-touch',
	function(A) {
		var CSS_MOVE_OPTION_CLASS = '.move-option';

		var CSS_SORT_LIST_ACTIVE = 'sort-list-active';

		var STR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_NODE = 'node';

		var STR_SELECTED = 'selected';

		var STR_TRUE = 'true';

		var TPL_EDIT_SELECTION = '<button class="btn edit-selection" type="button"><i class="icon-edit"></i> ' + Liferay.Language.get('edit') + ' </button>';

		var TPL_MOVE_OPTION = new A.Template(
			'<tpl for="options">',
				'<div class="move-option {[ values.selected ? "', STR_SELECTED, '" : "" ]}" data-value="{value}">',
					'<i class="handle icon-reorder"></i>',
					'<input {[ values.selected ? "', STR_CHECKED, '" : "" ]} class="checkbox" id="{value}CheckBox" type="checkbox" value="{value}" />',
					'<label class="title" for="{value}CheckBox" title="{name}">{name}</label>',
				'</div>',
			'</tpl>'
		);

		var TPL_SORTABLE_CONTAINER = '<div class="sortable-container ' + CSS_SORT_LIST_ACTIVE + '"></div>';

		A.mix(
			Liferay.InputMoveBoxes.prototype,
			{
				renderUI: function() {
					var instance = this;

					instance._contentBox = instance.get('contentBox');

					instance._sortableContainer = A.Node.create(TPL_SORTABLE_CONTAINER);

					instance._contentBox.append(instance._sortableContainer);

					instance._renderBoxes();
					instance._renderButtons();
					instance._renderSortList();

					instance._afterDropHitTask = A.debounce(instance._afterDropHitFn, 50, instance);
				},

				bindUI: function() {
					var instance = this;

					var dd = instance._sortable.delegate.dd;

					instance._editSelection.on(
						STR_CLICK,
						A.bind('_onEditSelectionClick', instance)
					);

					dd.after(
						'drag:drophit',
						A.bind('_afterDropHit', instance)
					);

					dd.after(
						'drag:start',
						A.bind('_afterDragStart', instance)
					);

					instance._contentBox.delegate(
						STR_CLICK,
						function(event) {
							event.preventDefault();
						},
						'.sort-list-active .title'
					);

					instance._sortableContainer.delegate(
						'change',
						A.bind('_onCheckBoxChange', instance),
						'.checkbox'
					);
				},

				_afterDragStart: function(event) {
					var instance = this;

					var dragNode = event.target.get('dragNode');

					dragNode.addClass('move-option-dragging');
				},

				_afterDropHit: function(event) {
					var instance = this;

					var dragNode = event.drag.get(STR_NODE);
					var dropNode = event.drop.get(STR_NODE);

					var value = dragNode.attr('data-value');

					instance._afterDropHitTask(
						{
							dropNode: dropNode,
							value: value
						}
					);
				},

				_afterDropHitFn: function(event) {
					var instance = this;

					instance._syncSelectedSortList();

					var dropNode = event.dropNode;
					var value = event.value;

					var moveOption = instance._sortableContainer.one(CSS_MOVE_OPTION_CLASS + '[data-value="' + value + '"]');

					var dragNodeIndex = instance._selectedSortList.indexOf(moveOption);
					var dropNodeIndex = instance._selectedSortList.indexOf(dropNode);

					var referenceNodeIndex = (dragNodeIndex + 1);

					if (dropNodeIndex > dragNodeIndex) {
						referenceNodeIndex = dragNodeIndex;
					}

					var item = instance._getOption(instance._leftBox, value);

					instance._sortLeftBox(item, referenceNodeIndex);
				},

				_getOption: function(box, value) {
					return box.one('option[value="' + value + '"]');
				},

				_onCheckBoxChange: function(event) {
					var instance = this;

					var currentTarget = event.currentTarget;

					var selected = !currentTarget.attr(STR_CHECKED);
					var value = currentTarget.attr('value');

					var from = instance._rightBox;
					var to = instance._leftBox;

					if (selected) {
						from = instance._leftBox;
						to = instance._rightBox;
					}

					var option = instance._getOption(from, value);

					option.attr(STR_SELECTED, true);

					instance._moveItem(from, to);

					option = instance._getOption(to, value);

					option.attr(STR_SELECTED, false);

					instance._toggleMoveOption(currentTarget, option);
				},

				_onEditSelectionClick: function(event) {
					var instance = this;

					event.currentTarget.toggleClass('active');

					instance._sortableContainer.toggleClass('edit-list-active');
					instance._sortableContainer.toggleClass(CSS_SORT_LIST_ACTIVE);
				},

				_renderButtons: function() {
					var instance = this;

					instance._editSelection = A.Node.create(TPL_EDIT_SELECTION);

					instance._sortableContainer.placeBefore(instance._editSelection);
				},

				_renderSortList: function() {
					var instance = this;

					var options = instance._contentBox.all('.choice-selector option');

					var sortableContainer = instance._sortableContainer;

					var data = [];

					options.each(
						function(item, index, collection) {
							var selected = (item.attr('data-selected') === STR_TRUE);

							data.push(
								{
									name: item.html(),
									selected: selected,
									value: item.val()
								}
							);
						}
					);

					TPL_MOVE_OPTION.render(
						{
							name: data.name,
							options: data,
							selected: data.selected,
							value: data.value
						},
						sortableContainer
					);

					instance._sortable = new A.Sortable(
						{
							container: sortableContainer,
							handles: [sortableContainer.all('.handle')],
							nodes: CSS_MOVE_OPTION_CLASS,
							opacity: '0.2'
						}
					);

					instance._syncSelectedSortList();
				},

				_sortLeftBox: function(item, index) {
					var instance = this;

					var leftBoxOptions = instance._leftBox.all('option');

					var referenceNode = leftBoxOptions.item(index);

					instance._leftBox.insertBefore(item, referenceNode);
				},

				_syncSelectedSortList: function() {
					var instance = this;

					instance._selectedSortList = instance._sortableContainer.all(CSS_MOVE_OPTION_CLASS + '.' + STR_SELECTED);
				},

				_toggleMoveOption: function(checkbox, option) {
					var instance = this;

					var moveOption = checkbox.ancestor(CSS_MOVE_OPTION_CLASS);

					moveOption.toggleClass(STR_SELECTED);

					instance._syncSelectedSortList();

					if (moveOption.hasClass(STR_SELECTED)) {
						var index = instance._selectedSortList.indexOf(moveOption);

						instance._sortLeftBox(option, index);
					}
				}
			},
			true
		);
	},
	'',
	{
		requires: ['aui-base', 'aui-template-deprecated', 'liferay-input-move-boxes', 'sortable']
	}
);