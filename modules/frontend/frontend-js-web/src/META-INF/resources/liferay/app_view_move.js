AUI.add(
	'liferay-app-view-move',
	function(A) {

		/**
		 * The AppViewMove Component.
		 *
		 * @module liferay-app-view-move
		 */

		var History = Liferay.HistoryManager;
		var Lang = A.Lang;
		var UA = A.UA;
		var Util = Liferay.Util;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_ACTIVE_AREA_PROXY = 'active-area-proxy';

		var CSS_ICON_REPLY = 'icon-reply-all';

		var CSS_SELECTED = 'selected';

		var DATA_FOLDER_ID = 'data-folder-id';

		var SELECTOR_DRAGGABLE_NODES = '[data-draggable]';

		var STR_BLANK = '';

		var STR_DATA = 'data';

		var STR_DELETE = 'delete';

		var STR_DELETE_ENTRIES = 'deleteEntries';

		var STR_DISPLAY_STYLE = 'displayStyleCSSClass';

		var STR_DOT = '.';

		var STR_DRAG_NODE = 'dragNode';

		var STR_FORM = 'form';

		var STR_MOVE = 'move';

		var STR_MOVE_ENTRIES = 'moveEntries';

		var STR_MOVE_ENTRIES_TO_TRASH = 'moveEntriesToTrash';

		var STR_MOVE_ENTRY_URL = 'moveEntryRenderUrl';

		var STR_NODE = 'node';

		var STR_PORTLET_GROUP = 'portletGroup';

		var TOUCH = UA.touch;

		/**
		 * A base class for `A.AppViewMove`.
		 *
		 * @class A.AppViewMove
		 * @extends Base
		 * @param {Object} config Object literal specifying
		 *     widget configuration properties.
		 * @constructor
		 */
		var AppViewMove = A.Component.create(
			{

				/**
				 * A static property used to define the default attribute
				 * configuration for `AppViewMove`.
				 *
				 * @property ATTRS
				 * @type Object
				 * @static
				 */
				ATTRS: {

					/**
					 * The ID of the all row checkbox.
					 *
					 * @attribute allRowIds
					 * @type String
					 */
					allRowIds: {
						validator: Lang.isString
					},

					/**
					 * The display style CSS class.
					 *
					 * @attribute displayStyleCSSClass
					 * @type String
					 */
					displayStyleCSSClass: {
						validator: Lang.isString
					},

					/**
					 * The draggable CSS class.
					 *
					 * @attribute draggableCSSClass
					 * @type String
					 */
					draggableCSSClass: {
						validator: Lang.isString
					},

					/**
					 * The URL when an entry is edited.
					 *
					 * @attribute editEntryUrl
					 * @type String
					 */
					editEntryUrl: {
						validator: Lang.isString
					},

					/**
					 * The RegEx for the folder ID.
					 *
					 * @attribute folderIdHashRegEx
					 * @type String
					 */
					folderIdHashRegEx: {
						setter: function(value) {
							if (Lang.isString(value)) {
								value = new RegExp(value);
							}

							return value;
						},
						validator: function(value) {
							return value instanceof RegExp || Lang.isString(value);
						}
					},

					/**
					 * The form.
					 *
					 * @attribute form
					 * @type Object
					 */
					form: {
						validator: Lang.isObject
					},

					/**
					 * The URL for an entry when it is moved.
					 *
					 * @attribute moveEntryRenderUrl
					 * @type String
					 */
					moveEntryRenderUrl: {
						validator: Lang.isString
					},

					/**
					 * The action used when an item is moved to the trash.
					 *
					 * @attribute moveToTrashActionName
					 * @type String
					 */
					moveToTrashActionName: {
						validator: Lang.isString
					},

					/**
					 * The namespace.
					 *
					 * @attribute namespace
					 * @type String
					 */
					namespace: {
						validator: Lang.isString
					},

					/**
					 *The ID of the portlet container.
					 *
					 * @attribute portletContainerId
					 * @type String
					 */
					portletContainerId: {
						validator: Lang.isString
					},

					/**
					 * The portlet group.
					 *
					 * @attribute portletGroup
					 * @type String
					 */
					portletGroup: {
						validator: Lang.isString
					},

					/**
					 * Contains IDs of the entries to process.
					 *
					 * @attribute processEntryIds
					 * @type Object
					 */
					processEntryIds: {
						validator: Lang.isObject
					},

					selectedCSSClass: {
						validator: Lang.isString,
						value: CSS_SELECTED
					},

					/**
					 * The ID of the trash link.
					 *
					 * @attribute trashLinkId
					 * @type String
					 */
					trashLinkId: {
						validator: Lang.isString
					},

					/**
					 * Specifies whether an entry can be moved.
					 *
					 * @attribute updateable
					 * @type Boolean
					 */
					updateable: {
						validator: Lang.isBoolean
					}
				},

				/**
				 * Augment the PortletBase module.
				 *
				 * @property AUGMENTS
				 * @type Object
				 * @static
				 */
				AUGMENTS: [Liferay.PortletBase],

				/**
				 * Extend the AUI Base module.
				 *
				 * @property EXTENDS
				 * @type Object
				 * @static
				 */
				EXTENDS: A.Base,

				/**
				 * Static property provides a string to identify the class.
				 *
				 * @property NAME
				 * @type String
				 * @static
				 */
				NAME: 'liferay-app-view-move',

				prototype: {

					/**
					 * Construction lifecycle implementation executed during
					 * `AppViewMove` instantiation.
					 *
					 * @method initializer
					 * @param config
					 * @protected
					 */
					initializer: function(config) {
						var instance = this;

						instance._portletContainer = instance.byId(instance.get('portletContainerId'));

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._eventEditEntry = instance.ns('editEntry');

						instance._selectedCSSClass = instance.get('selectedCSSClass');

						var eventHandles = [
							Liferay.on(instance._eventEditEntry, instance._editEntry, instance)
						];

						instance._eventHandles = eventHandles;

						instance._registerDragDrop();
					},

					/**
					 * Destructor lifecycle implementation for the `AppViewMove`
					 * class.
					 *
					 * @method destructor
					 * @protected
					 */
					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						if (instance._ddHandler) {
							instance._ddHandler.destroy();
						}
					},

					/**
					 * Fires when namespaced `editEntry` event is run.
					 *
					 * @method _editEntry
					 * @param event
					 * @protected
					 */
					_editEntry: function(event) {
						var instance = this;

						var action = event.action;

						var url = instance.get('editEntryUrl');

						if (action === STR_MOVE || action === STR_MOVE_ENTRIES) {
							url = instance.get(STR_MOVE_ENTRY_URL);
						}

						instance._processEntryAction(action, url);
					},

					/**
					 * Returns the text to display when moving according to
					 * whether a target is available. Fires when an item is
					 * moved.
					 *
					 * @method _getMoveText
					 * @param selectedItemsCount
					 * @param targetAvailable
					 * @protected
					 * @return moveText
					 */
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

					/**
					 * Initializes the drag/drop.
					 *
					 * @method _initDragDrop
					 * @protected
					 */
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
									trashLink.toggleClass('app-view-drop-active', event.type == 'drag:start');
								}
							);
						}

						instance._initDropTargets();

						instance._ddHandler = ddHandler;
					},

					/**
					 * Initializes the drop targets.
					 *
					 * @method _initDropTargets
					 * @protected
					 */
					_initDropTargets: function() {
						var instance = this;

						if (themeDisplay.isSignedIn()) {
							var items = instance._portletContainer.all('[data-folder="true"]');

							items.each(
								function(item, index) {
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

					/**
					 * Moves entries.
					 *
					 * @method _moveEntries
					 * @param folderId
					 * @protected
					 */
					_moveEntries: function(folderId) {
						var instance = this;

						var form = instance.get(STR_FORM).node;

						form.get(instance.ns('newFolderId')).val(folderId);

						instance._processEntryAction(STR_MOVE, this.get(STR_MOVE_ENTRY_URL));
					},

					/**
					 * Moves entries to the trash.
					 *
					 * @method _moveEntriesToTrash
					 * @protected
					 */
					_moveEntriesToTrash: function() {
						var instance = this;

						instance._processEntryAction(instance.get('moveToTrashActionName'), instance.get('editEntryUrl'));
					},

					/**
					 * Fires when the `drag:drophit` event is run.
					 *
					 * @method _onDragDropHit
					 * @param event
					 * @protected
					 */
					_onDragDropHit: function(event) {
						var instance = this;

						var proxyNode = event.target.get(STR_DRAG_NODE);

						proxyNode.removeClass(CSS_ACTIVE_AREA_PROXY);
						proxyNode.removeClass(CSS_ICON_REPLY);

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

					/**
					 * Fires when the `drag:enter` event is run.
					 *
					 * @method _onDragEnter
					 * @param event
					 * @protected
					 */
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

							var itemTitle = dropTarget.attr('data-title').trim();

							proxyNode.html(Lang.sub(moveText, [selectedItemsCount, A.Lang.String.escapeHTML(itemTitle)]));
						}
					},

					/**
					 * Fires when the `drag:exit` event is run.
					 *
					 * @method _onDragExit
					 * @param event
					 * @protected
					 */
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

					/**
					 * Fires when the `drag:start` event is run.
					 *
					 * @method _onDragStart
					 * @param event
					 * @protected
					 */
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

						var selectedItems = instance._entriesContainer.all(STR_DOT + instance.get(STR_DISPLAY_STYLE) + STR_DOT + instance._selectedCSSClass);

						var selectedItemsCount = selectedItems.size();

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));

						proxyNode.addClass(CSS_ACTIVE_AREA_PROXY);
						proxyNode.addClass(CSS_ICON_REPLY);

						var dd = instance._ddHandler.dd;

						dd.set(
							STR_DATA,
							{
								selectedItems: selectedItems,
								selectedItemsCount: selectedItemsCount
							}
						);
					},

					/**
					 * Runs when the edit, move, or move to trash actions occur.
					 *
					 * @method _processEntryAction
					 * @param action
					 * @param url
					 * @protected
					 */
					_processEntryAction: function(action, url) {
						var instance = this;

						var form = instance.get(STR_FORM).node;

						var redirectUrl = location.href;

						if (action === STR_DELETE || action === STR_DELETE_ENTRIES || action === instance.get('moveToTrashActionName') || action === STR_MOVE_ENTRIES_TO_TRASH && !History.HTML5 && location.hash) {
							redirectUrl = instance._updateFolderIdRedirectUrl(redirectUrl);
						}

						form.attr('method', instance.get(STR_FORM).method);

						if (form.get(instance.ns('javax-portlet-action'))) {
							form.get(instance.ns('javax-portlet-action')).val(action);
						}
						else {
							form.get(instance.ns('cmd')).val(action);
						}

						form.get(instance.ns('redirect')).val(redirectUrl);

						var allRowIds = instance.get('allRowIds');

						var allRowsIdCheckbox = instance.ns(allRowIds);

						var processEntryIds = instance.get('processEntryIds');

						var entryIds = processEntryIds.entryIds;

						var checkBoxesIds = processEntryIds.checkBoxesIds;

						for (var i = 0; i < checkBoxesIds.length; i++) {
							var listEntryIds = Util.listCheckedExcept(form, allRowsIdCheckbox, checkBoxesIds[i]);

							form.get(entryIds[i]).val(listEntryIds);
						}

						submitForm(form, url);
					},

					/**
					 * Registers the drag/drop events.
					 *
					 * @method _registerDragDrop
					 * @protected
					 */
					_registerDragDrop: function() {
						var instance = this;

						instance._eventHandles.push(Liferay.after(instance.ns('dataRetrieveSuccess'), instance._initDropTargets, instance));

						if (themeDisplay.isSignedIn() && this.get('updateable')) {
							instance._initDragDrop();
						}
					},

					/**
					 * Updates the redirect URL for the folder ID.
					 *
					 * @method _updateFolderIdRedirectUrl
					 * @param redirectUrl
					 * @protected
					 * @return redirectUrl
					 */
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
		requires: ['aui-base', 'dd-constrain', 'dd-delegate', 'dd-drag', 'dd-drop', 'dd-proxy', 'liferay-history-manager', 'liferay-portlet-base']
	}
);