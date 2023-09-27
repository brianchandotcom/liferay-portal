/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-undo-manager',
	(A) => {
		const Lang = A.Lang;

		const CSS_ACTION_CLEAR = 'float-right lfr-action-clear';

		const CSS_ACTION_UNDO = 'float-left lfr-action-undo';

		const CSS_HELPER_CLEARFIX = 'helper-clearfix';

		const CSS_ITEMS_LEFT = 'lfr-items-left';

		const CSS_MESSAGE_INFO = 'alert alert-info';

		const CSS_QUEUE = 'lfr-undo-queue mx-auto my-2';

		const CSS_QUEUE_EMPTY = 'lfr-queue-empty d-none';

		const CSS_QUEUE_ITEMS = 'd-flex justify-content-between';

		const TPL_ACTION_CLEAR =
			'<a class="' +
			CSS_ACTION_CLEAR +
			'" href="javascript:void(0);"></a>';

		const TPL_ACTION_UNDO =
			'<a class="' +
			CSS_ACTION_UNDO +
			'" href="javascript:void(0);"></a>';

		const TPL_UNDO_TEXT = '<span class="' + CSS_ITEMS_LEFT + '">(0)</span>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * container {string|object}: A selector that contains the rows you wish to duplicate.
		 *
		 * Optional
		 * location {string}: The location in the container (top or bottom) where the manager will be added. Specifying false
		 * will perform default rendering
		 *
		 */

		const UndoManager = A.Component.create({
			ATTRS: {
				location: {
					value: 'top',
				},
			},

			NAME: 'undomanager',

			prototype: {
				_afterUndoManagerRender() {
					const instance = this;

					const location = instance.get('location');

					if (location !== false) {
						const boundingBox = instance.get('boundingBox');
						const boundingBoxParent = boundingBox.get('parentNode');

						let action = 'append';

						if (location === 'top') {
							action = 'prepend';
						}

						boundingBoxParent[action](boundingBox);
					}
				},

				_onActionClear() {
					const instance = this;

					instance.clear();
				},

				_onActionUndo() {
					const instance = this;

					instance.undo(1);
				},

				_updateList() {
					const instance = this;

					const itemsLeft = instance._undoCache.size();

					const contentBox = instance.get('contentBox');

					let action = 'removeClass';

					if (itemsLeft > 0) {
						action = 'addClass';
					}

					contentBox[action](CSS_QUEUE_ITEMS);

					instance._undoItemsLeft.text('(' + itemsLeft + ')');
				},

				add(handler, stateData) {
					const instance = this;

					if (Lang.isFunction(handler)) {
						const undo = {
							handler,
							stateData,
						};

						instance._undoCache.insert(0, undo);

						const eventData = {
							undo,
						};

						instance.fire('update', eventData);
						instance.fire('add', eventData);
					}
				},

				bindUI() {
					const instance = this;

					instance._actionClear.on(
						'click',
						instance._onActionClear,
						instance
					);
					instance._actionUndo.on(
						'click',
						instance._onActionUndo,
						instance
					);

					instance.after('render', instance._afterUndoManagerRender);
				},

				clear() {
					const instance = this;

					instance._undoCache.clear();

					instance.fire('update');
					instance.fire('clearList');
				},

				initializer() {
					const instance = this;

					instance._undoCache = new A.DataSet();
				},

				renderUI() {
					const instance = this;

					const clearText = Liferay.Language.get('clear-history');
					let undoText = Liferay.Language.get('undo-x');

					undoText = Lang.sub(undoText, [TPL_UNDO_TEXT]);

					const contentBox = instance.get('contentBox');

					const actionClear = A.Node.create(TPL_ACTION_CLEAR);
					const actionUndo = A.Node.create(TPL_ACTION_UNDO);

					actionClear.append(clearText);
					actionUndo.append(undoText);

					contentBox.appendChild(actionUndo);
					contentBox.appendChild(actionClear);

					contentBox.addClass(CSS_HELPER_CLEARFIX);
					contentBox.addClass(CSS_MESSAGE_INFO);
					contentBox.addClass(CSS_QUEUE);
					contentBox.addClass(CSS_QUEUE_EMPTY);

					instance.after('update', instance._updateList);

					instance._undoItemsLeft = contentBox.one(
						'.' + CSS_ITEMS_LEFT
					);

					instance._actionClear = actionClear;
					instance._actionUndo = actionUndo;
				},

				undo(limit) {
					const instance = this;

					limit = limit || 1;

					const undoCache = instance._undoCache;

					undoCache.each((item, index) => {
						if (index < limit) {
							item.handler.call(instance, item.stateData);

							undoCache.removeAt(0);
						}
					});

					instance.fire('update');
					instance.fire('undo');
				},
			},
		});

		Liferay.UndoManager = UndoManager;
	},
	'',
	{
		requires: ['aui-data-set-deprecated', 'base'],
	}
);
