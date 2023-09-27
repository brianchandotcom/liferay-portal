/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The Input Move Boxes Touch Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-input-move-boxes-touch
 */

AUI.add(
	'liferay-input-move-boxes-touch',
	(A) => {
		const Lang = A.Lang;

		const STR_DOT = '.';

		const STR_SELECTED = 'selected';

		const STR_SORT_LIST_ACTIVE = 'sort-list-active';

		const SELECTOR_MOVE_OPTION = STR_DOT + 'move-option';

		const SELECTOR_SELECTED = STR_DOT + STR_SELECTED;

		const SELECTOR_SORT_LIST_ACTIVE = STR_DOT + STR_SORT_LIST_ACTIVE;

		const SELECTOR_TITLE = STR_DOT + 'title';

		const STR_CHECKED = 'checked';

		const STR_CLICK = 'click';

		const STR_NODE = 'node';

		const STR_TRUE = 'true';

		const TPL_EDIT_SELECTION =
			'<button class="btn btn-secondary edit-selection" type="button">' +
			'<i class="icon-edit"></i> ' +
			'<span class="btn-text">{0}</span>' +
			'</button>';

		const TPL_MOVE_OPTION = new A.Template(
			'<tpl for="options">',
			'<div class="handle move-option {[ values.selected ? "',
			STR_SELECTED,
			'" : "" ]}" data-value="{value}">',
			'<i class="handle icon-reorder"></i>',

			'<input {[ values.selected ? "',
			STR_CHECKED,
			'" : "" ]} class="checkbox" id="{value}CheckBox" type="checkbox" value="{value}" />',

			'<label class="title" for="{value}CheckBox" title="{name}">{name}</label>',
			'</div>',
			'</tpl>'
		);

		const TPL_SORTABLE_CONTAINER =
			'<div class="sortable-container ' +
			STR_SORT_LIST_ACTIVE +
			'"></div>';

		A.mix(
			Liferay.InputMoveBoxes.prototype,
			{
				_afterDragStart(event) {
					const dragNode = event.target.get('dragNode');

					dragNode.addClass('move-option-dragging');
				},

				_afterDropHit(event) {
					const instance = this;

					const dragNode = event.drag.get(STR_NODE);
					const dropNode = event.drop.get(STR_NODE);

					const value = dragNode.attr('data-value');

					instance._afterDropHitTask({
						dropNode,
						value,
					});
				},

				_afterDropHitFn(event) {
					const instance = this;

					instance._syncSelectedSortList();

					const dropNode = event.dropNode;
					const value = event.value;

					const moveOption = instance._sortableContainer.one(
						SELECTOR_MOVE_OPTION + '[data-value="' + value + '"]'
					);

					const selectedSortList = instance._selectedSortList;

					const dragNodeIndex = selectedSortList.indexOf(moveOption);
					const dropNodeIndex = selectedSortList.indexOf(dropNode);

					let referenceNodeIndex = dragNodeIndex + 1;

					if (dropNodeIndex > dragNodeIndex) {
						referenceNodeIndex = dragNodeIndex;
					}

					const item = instance._getOption(instance._leftBox, value);

					instance._sortLeftBox(item, referenceNodeIndex);
				},

				_getOption(box, value) {
					return box.one('option[value="' + value + '"]');
				},

				_onCheckBoxChange(event) {
					const instance = this;

					const currentTarget = event.currentTarget;

					const selected = !currentTarget.attr(STR_CHECKED);
					const value = currentTarget.val();

					let from = instance._rightBox;
					let to = instance._leftBox;

					if (selected) {
						from = instance._leftBox;
						to = instance._rightBox;
					}

					const option = instance._getOption(from, value);

					option.attr(STR_SELECTED, true);
					option.attr('data-selected', !selected);

					instance._moveItem(from, to);

					to.attr('selectedIndex', -1);

					instance._toggleMoveOption(currentTarget, option);
				},

				_onEditSelectionClick(event) {
					const instance = this;

					const button = event.currentTarget;

					button.toggleClass('active');

					let btnText = Liferay.Language.get('edit');

					if (button.hasClass('active')) {
						btnText = Liferay.Language.get('stop-editing');
					}

					button.one('.btn-text').text(btnText);

					const sortableContainer = instance._sortableContainer;

					sortableContainer.toggleClass('edit-list-active');
					sortableContainer.toggleClass(STR_SORT_LIST_ACTIVE);
				},

				_renderButtons() {
					const instance = this;

					const buttonTpl = Lang.sub(TPL_EDIT_SELECTION, [
						Liferay.Language.get('edit'),
					]);

					instance._editSelection = A.Node.create(buttonTpl);

					instance._sortableContainer.placeBefore(
						instance._editSelection
					);
				},

				_renderSortList() {
					const instance = this;

					const options = instance._contentBox.all(
						'.choice-selector option'
					);

					const sortableContainer = instance._sortableContainer;

					const data = [];

					options.each((item) => {
						data.push({
							name: item.html(),
							selected: item.attr('data-selected') === STR_TRUE,
							value: item.val(),
						});
					});

					TPL_MOVE_OPTION.render(
						{
							options: data,
						},
						sortableContainer
					);

					instance._sortable = new A.Sortable({
						container: sortableContainer,
						handles: [sortableContainer.all('.handle')],
						nodes: SELECTOR_MOVE_OPTION,
						opacity: '0.2',
					});

					instance._sortable.delegate.dd
						.plug(A.Plugin.DDConstrained, {
							constrain: sortableContainer,
						})
						.plug(A.Plugin.DDWinScroll, {
							horizontal: false,
						});

					instance._syncSelectedSortList();
				},

				_sortLeftBox(item, index) {
					const instance = this;

					const leftBox = instance._leftBox;

					const referenceNode = leftBox.all('option').item(index);

					leftBox.insertBefore(item, referenceNode);
				},

				_syncSelectedSortList() {
					const instance = this;

					instance._selectedSortList = instance._sortableContainer.all(
						SELECTOR_MOVE_OPTION + SELECTOR_SELECTED
					);
				},

				_toggleMoveOption(checkbox, option) {
					const instance = this;

					const moveOption = checkbox.ancestor(SELECTOR_MOVE_OPTION);

					moveOption.toggleClass(STR_SELECTED);

					instance._syncSelectedSortList();

					if (moveOption.hasClass(STR_SELECTED)) {
						const index = instance._selectedSortList.indexOf(
							moveOption
						);

						instance._sortLeftBox(option, index);
					}
				},

				bindUI() {
					const instance = this;

					const dd = instance._sortable.delegate.dd;

					instance._editSelection.on(
						STR_CLICK,
						A.bind('_onEditSelectionClick', instance)
					);

					dd.after('drag:drophit', A.bind('_afterDropHit', instance));

					dd.after('drag:start', A.bind('_afterDragStart', instance));

					instance._contentBox.delegate(
						STR_CLICK,
						(event) => {
							event.preventDefault();
						},
						SELECTOR_SORT_LIST_ACTIVE + ' ' + SELECTOR_TITLE
					);

					instance._sortableContainer.delegate(
						'change',
						A.bind('_onCheckBoxChange', instance),
						'.checkbox'
					);
				},

				renderUI() {
					const instance = this;

					instance._contentBox = instance.get('contentBox');

					instance._sortableContainer = A.Node.create(
						TPL_SORTABLE_CONTAINER
					);

					instance._contentBox.append(instance._sortableContainer);

					instance._renderBoxes();
					instance._renderButtons();
					instance._renderSortList();

					instance._afterDropHitTask = A.debounce(
						'_afterDropHitFn',
						50,
						instance
					);
				},
			},
			true
		);
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-template-deprecated',
			'dd-constrain',
			'dd-scroll',
			'liferay-input-move-boxes',
			'sortable',
		],
	}
);
