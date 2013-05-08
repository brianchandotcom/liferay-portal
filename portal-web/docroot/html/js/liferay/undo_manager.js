AUI.add(
	'liferay-undo-manager',
	function(A) {
		var Lang = A.Lang;

		var CSS_ACTION_CLEAR = 'lfr-action-clear';

		var CSS_ACTION_UNDO = 'lfr-action-undo';

		var CSS_HELPER_CLEARFIX = 'helper-clearfix';

		var CSS_ITEMS_LEFT = 'lfr-items-left';

		var CSS_MESSAGE_INFO = 'alert alert-info';

		var CSS_QUEUE = 'lfr-undo-queue';

		var CSS_QUEUE_EMPTY = 'lfr-queue-empty';

		var CSS_QUEUE_SINGLE = 'lfr-queue-single';

		var TPL_UNDO_TEXT = '<span class="' + CSS_ITEMS_LEFT + '">(0)</span>';

		var TPL_ACTION_CLEAR = '<a class="' + CSS_ACTION_CLEAR + '" href="javascript:;"></a>';

		var TPL_ACTION_UNDO = '<a class="' + CSS_ACTION_UNDO + '" href="javascript:;"></a>';

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

		var UndoManager = A.Component.create(
			{
				ATTRS: {
					location: {
						value: 'top'
					}
				},

				NAME: 'undomanager',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._undoCache = new A.DataSet();
					},

					renderUI: function() {
						var instance = this;

						var clearText = Liferay.Language.get('clear-history');
						var undoText = Liferay.Language.get('undo-x');

						undoText = Lang.sub(undoText, [TPL_UNDO_TEXT]);

						var contentBox = instance.get('contentBox');

						var actionClear = A.Node.create(TPL_ACTION_CLEAR);
						var actionUndo = A.Node.create(TPL_ACTION_UNDO);

						actionClear.append(clearText);
						actionUndo.append(undoText);

						contentBox.appendChild(actionUndo);
						contentBox.appendChild(actionClear);

						contentBox.addClass(CSS_HELPER_CLEARFIX);
						contentBox.addClass(CSS_MESSAGE_INFO);
						contentBox.addClass(CSS_QUEUE);
						contentBox.addClass(CSS_QUEUE_EMPTY);

						instance.after('update', instance._updateList);

						instance._undoItemsLeft = contentBox.one('.' + CSS_ITEMS_LEFT);

						instance._actionClear = actionClear;
						instance._actionUndo = actionUndo;
					},

					bindUI: function() {
						var instance = this;

						instance._actionClear.on('click', instance._onActionClear, instance);
						instance._actionUndo.on('click', instance._onActionUndo, instance);

						instance.after('render', instance._afterUndoManagerRender);
					},

					add: function(handler, stateData) {
						var instance = this;

						if (Lang.isFunction(handler)) {
							var undo = {
								handler: handler,
								stateData: stateData
							};

							instance._undoCache.insert(0, undo);

							var eventData = {
								undo: undo
							};

							instance.fire('update', eventData);
							instance.fire('add', eventData);
						}
					},

					clear: function(event) {
						var instance = this;

						instance._undoCache.clear();

						instance.fire('update');
						instance.fire('clearList');
					},

					undo: function(limit) {
						var instance = this;

						limit = limit || 1;

						var undoCache = instance._undoCache;

						undoCache.each(
							function(item, index, collection) {
								if (index < limit) {
									item.handler.call(instance, item.stateData);

									undoCache.removeAt(0);
								}
								else {
									return false;
								}
							}
						);

						instance.fire('update');
						instance.fire('undo');
					},

					_afterUndoManagerRender: function(event) {
						var instance = this;

						var location = instance.get('location');

						if (location !== false) {
							var boundingBox = instance.get('boundingBox');
							var boundingBoxParent = boundingBox.get('parentNode');

							var action = 'append';

							if (location == 'top') {
								action = 'prepend';
							}

							boundingBoxParent[action](boundingBox);
						}
					},

					_onActionClear: function(event) {
						var instance = this;

						instance.clear();
					},

					_onActionUndo: function(event) {
						var instance = this;

						instance.undo(1);
					},

					_updateList: function() {
						var instance = this;

						var itemsLeft = instance._undoCache.size();
						var contentBox = instance.get('contentBox');

						var actionSingle = 'removeClass';
						var actionEmpty = 'addClass';

						if (itemsLeft > 0) {
							if (itemsLeft == 1) {
								actionSingle = 'addClass';
							}

							actionEmpty = 'removeClass';
						}

						contentBox[actionSingle](CSS_QUEUE_SINGLE);
						contentBox[actionEmpty](CSS_QUEUE_EMPTY);

						instance._undoItemsLeft.text('(' + itemsLeft + ')');
					}
				}
			}
		);

		Liferay.UndoManager = UndoManager;
	},
	'',
	{
		requires: ['aui-data-set-deprecated', 'base']
	}
);