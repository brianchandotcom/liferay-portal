AUI.add(
	'liferay-app-view-move',
	function(A) {
		var History = Liferay.HistoryManager;
		var Lang = A.Lang;
		var UA = A.UA;
		var Util = Liferay.Util;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_ACTIVE_AREA_PROXY = 'active-area-proxy';

		var DATA_FOLDER_ID = 'data-folder-id';

		var SELECTOR_DRAGGABLE_NODES = '[data-draggable]';

		var STR_BLANK = '';

		var STR_DATA = 'data';

		var STR_DELETE = 'delete';

		var STR_DISPLAY_STYLE = 'displayStyleCSSClass';

		var STR_DOT = '.';

		var STR_DRAG_NODE = 'dragNode';

		var STR_FORM = 'form';

		var STR_MOVE = 'move';

		var STR_MOVE_TO_TRASH = 'move_to_trash';

		var STR_MOVE_ENTRY_URL = 'moveEntryRenderUrl';

		var STR_NODE = 'node';

		var STR_PORTLET_GROUP = 'portletGroup';

		var TOUCH = UA.touch;

		var AppViewMove = A.Component.create(
			{
				ATTRS: {
					allRowIds: {
						validator: Lang.isString
					},

					displayStyleCSSClass: {
						validator: Lang.isString
					},

					draggableCSSClass: {
						validator: Lang.isString
					},

					editEntryUrl: {
						validator: Lang.isString
					},

					folderIdHashRegEx: {
						setter: function(value) {
							if (Lang.isString(value)) {
								value = new RegExp(value);
							}

							return value;
						},
						validator: function(value) {
							return (value instanceof RegExp || Lang.isString(value));
						}
					},

					form: {
						validator: Lang.isObject
					},

					moveEntryRenderUrl: {
						validator: Lang.isString
					},

					namespace: {
						validator: Lang.isString
					},

					portletContainerId: {
						validator: Lang.isString
					},

					portletGroup: {
						validator: Lang.isString
					},

					processEntryIds: {
						validator: Lang.isObject
					},

					trashLinkId: {
						validator: Lang.isString
					},

					updateable: {
						validator: Lang.isBoolean
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-app-view-move',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._portletContainer = instance.byId(instance.get('portletContainerId'));

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._eventEditEntry = instance.ns('editEntry');

						var eventHandles = [
							Liferay.on(instance._eventEditEntry, instance._editEntry, instance),
							Liferay.after(instance.ns('dataRetrieveSuccess'), instance._initDropTargets, instance)
						];

						instance._eventHandles = eventHandles;

						if (themeDisplay.isSignedIn() && this.get('updateable')) {
							instance._initDragDrop();
						}
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._ddHandler.destroy();
					},

					_editEntry: function(event) {
						var instance = this;

						var action = event.action;

						var url = instance.get('editEntryUrl');

						if (action === STR_MOVE) {
							url = instance.get(STR_MOVE_ENTRY_URL);
						}

						instance._processEntryAction(action, url);
					},

					_getMoveText: function(selectedItemsCount, targetAvailable) {
						var moveText = STR_BLANK;

						if (targetAvailable) {
							moveText = Liferay.Language.get('x-item-is-ready-to-be-moved-to-x');

							if (selectedItemsCount > 1) {
								moveText = Liferay.Language.get('x-items-are-ready-to-be-moved-to-x');
							}
						}
						else {
							moveText = Liferay.Language.get('x-item-is-ready-to-be-moved');

							if (selectedItemsCount > 1) {
								moveText = Liferay.Language.get('x-items-are-ready-to-be-moved');
							}
						}

						return moveText;
					},

					_initDragDrop: function() {
						var instance = this;

						var ddHandler = new A.DD.Delegate(
							{
								container: instance._portletContainer,
								nodes: SELECTOR_DRAGGABLE_NODES,
								on: {
									'drag:drophit': A.bind('_onDragDropHit', instance),
									'drag:enter': A.bind('_onDragEnter', instance),
									'drag:exit': A.bind('_onDragExit', instance),
									'drag:start': A.bind('_onDragStart', instance)
								}
							}
						);

						var dd = ddHandler.dd;

						dd.set('offsetNode', false);

						dd.removeInvalid('a');

						dd.set('groups', [instance.get(STR_PORTLET_GROUP)]);

						dd.plug(
							[
								{
									cfg: {
										moveOnEnd: false
									},
									fn: A.Plugin.DDProxy
								}
							]
						);

						var trashLink = A.one('#' + instance.get('trashLinkId'));

						if (trashLink) {
							trashLink.attr('data-title', Liferay.Language.get('recycle-bin'));

							trashLink.plug(
								A.Plugin.Drop,
								{
									groups: dd.get('groups')
								}
							).drop.on(
								{
									'drop:hit': function(event) {
										instance._moveEntriesToTrash();
									}
								}
							);

							ddHandler.on(
								['drag:start', 'drag:end'],
								function(event) {
									trashLink.toggleClass('app-view-drop-active', (event.type == 'drag:start'));
								}
							);
						}

						instance._initDropTargets();

						instance._ddHandler = ddHandler;
					},

					_initDropTargets: function() {
						var instance = this;

						if (themeDisplay.isSignedIn()) {
							var items = instance._portletContainer.all('[data-folder="true"]');

							items.each(
								function(item, index, collection) {
									item.plug(
										A.Plugin.Drop,
										{
											groups: [instance.get(STR_PORTLET_GROUP)],
											padding: '-1px'
										}
									);
								}
							);
						}
					},

					_moveEntries: function(folderId) {
						var instance = this;

						var form = instance.get(STR_FORM).node;

						form.get(instance.ns('newFolderId')).val(folderId);

						instance._processEntryAction(STR_MOVE, this.get(STR_MOVE_ENTRY_URL));
					},

					_moveEntriesToTrash: function() {
						var instance = this;

						instance._processEntryAction(STR_MOVE_TO_TRASH, instance.get('editEntryUrl'));
					},

					_onDragDropHit: function(event) {
						var instance = this;

						var proxyNode = event.target.get(STR_DRAG_NODE);

						proxyNode.removeClass(CSS_ACTIVE_AREA_PROXY);

						proxyNode.empty();

						var dropTarget = event.drop.get(STR_NODE);

						dropTarget.removeClass(CSS_ACTIVE_AREA);

						var folderId = dropTarget.attr(DATA_FOLDER_ID);

						if (folderId) {
							var folderContainer = dropTarget.ancestor(STR_DOT + instance.get(STR_DISPLAY_STYLE));

							var selectedItems = instance._ddHandler.dd.get(STR_DATA).selectedItems;

							if (selectedItems.indexOf(folderContainer) == -1) {
								instance._moveEntries(folderId);
							}
						}
					},

					_onDragEnter: function(event) {
						var instance = this;

						var dragNode = event.drag.get(STR_NODE);
						var dropTarget = event.drop.get(STR_NODE);

						dropTarget = dropTarget.ancestor(STR_DOT + instance.get(STR_DISPLAY_STYLE)) || dropTarget;

						if (!dragNode.compareTo(dropTarget)) {
							dropTarget.addClass(CSS_ACTIVE_AREA);

							var proxyNode = event.target.get(STR_DRAG_NODE);

							var dd = instance._ddHandler.dd;

							var selectedItemsCount = dd.get(STR_DATA).selectedItemsCount;

							var moveText = instance._getMoveText(selectedItemsCount, true);

							var itemTitle = Lang.trim(dropTarget.attr('data-title'));

							proxyNode.html(Lang.sub(moveText, [selectedItemsCount, Liferay.Util.escapeHTML(itemTitle)]));
						}
					},

					_onDragExit: function(event) {
						var instance = this;

						var dropTarget = event.drop.get(STR_NODE);

						dropTarget = dropTarget.ancestor(STR_DOT + instance.get(STR_DISPLAY_STYLE)) || dropTarget;

						dropTarget.removeClass(CSS_ACTIVE_AREA);

						var proxyNode = event.target.get(STR_DRAG_NODE);

						var selectedItemsCount = instance._ddHandler.dd.get(STR_DATA).selectedItemsCount;

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));
					},

					_onDragStart: function(event) {
						var instance = this;

						var target = event.target;

						var node = target.get(STR_NODE);

						Liferay.fire(
							'liferay-app-view-move:dragStart',
							{
								node: node
							}
						);

						var proxyNode = target.get(STR_DRAG_NODE);

						proxyNode.setStyles(
							{
								height: STR_BLANK,
								width: STR_BLANK
							}
						);

						var selectedItems = instance._entriesContainer.all(STR_DOT + instance.get(STR_DISPLAY_STYLE) + '.selected');

						var selectedItemsCount = selectedItems.size();

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));

						proxyNode.addClass(CSS_ACTIVE_AREA_PROXY);

						var dd = instance._ddHandler.dd;

						dd.set(
							STR_DATA,
							{
								selectedItemsCount: selectedItemsCount,
								selectedItems: selectedItems
							}
						);
					},

					_processEntryAction: function(action, url) {
						var instance = this;

						var form = instance.get(STR_FORM).node;

						var redirectUrl = location.href;

						if ((action === STR_DELETE || action == STR_MOVE_TO_TRASH) && !History.HTML5 && location.hash) {
							redirectUrl = instance._updateFolderIdRedirectUrl(redirectUrl);
						}

						form.attr('method', instance.get(STR_FORM).method);

						form.get(instance.ns('cmd')).val(action);
						form.get(instance.ns('redirect')).val(redirectUrl);

						var allRowIds = instance.get('allRowIds');

						var allRowsIdCheckbox = instance.ns(allRowIds + 'Checkbox');

						var processEntryIds = instance.get('processEntryIds');

						var entryIds = processEntryIds.entryIds;

						var checkBoxesIds = processEntryIds.checkBoxesIds;

						for (var i = 0, checkBoxesIdsLength = checkBoxesIds.length; i < checkBoxesIdsLength; i++) {
							var listEntryIds = Util.listCheckedExcept(form, allRowsIdCheckbox, checkBoxesIds[i]);

							form.get(entryIds[i]).val(listEntryIds);
						}

						submitForm(form, url);
					},

					_updateFolderIdRedirectUrl: function(redirectUrl) {
						var instance = this;

						var currentFolderMatch = instance.get('folderIdHashRegEx').exec(redirectUrl);

						if (currentFolderMatch) {
							var currentFolderId = currentFolderMatch[1];

							redirectUrl = redirectUrl.replace(
								this.get('folderIdRegEx'),
								function(match, folderId) {
									return match.replace(folderId, currentFolderId);
								}
							);
						}

						return redirectUrl;
					}
				}
			}
		);

		Liferay.AppViewMove = AppViewMove;
	},
	'',
	{
		requires: ['aui-base', 'dd-constrain', 'dd-delegate', 'dd-drag', 'dd-drop', 'dd-proxy', 'liferay-history-manager', 'liferay-portlet-base', 'liferay-util-list-fields']
	}
);