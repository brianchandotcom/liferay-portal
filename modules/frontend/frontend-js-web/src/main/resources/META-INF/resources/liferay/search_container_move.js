AUI.add(
	'liferay-search-container-move',
	function(A) {
		var Lang = A.Lang;

		var STR_BLANK = '';

		var STR_CONTENT_BOX = 'contentBox';

		var STR_DATA = 'data';

		var STR_DRAG_NODE = 'dragNode';

		var STR_DROP_TARGET_SELECTORS = 'dropTargets';

		var STR_HOST = 'host';

		var STR_NODE = 'node';

		var SearchContainerMove = A.Component.create(
			{
				ATTRS: {
					activeAreaCSSClass: {
						validator: Lang.isString,
						value: 'active'
					},

					displayStyleCSSClass: {
						validator: Lang.isString
					},

					draggableSelector: {
						validator: Lang.isString,
						value: '[data-draggable="true"]'
					},

					dropTargetIdAttribute: {
						validator: Lang.isString,
						value: 'data-folder-id'
					},

					dropTargets: {
						validator: Lang.isArray,
						value: [
							{
								action: null,
								activeCSSClass: 'active',
								container: null,
								infoCSSCLass: null,
								selector: '[data-folder="true"]'
							}
						]
					},

					extraDropTargets: {
						validator: Lang.isArray,
						value: []
					},

					rowSelector: {
						validator: Lang.isString,
						value: 'li.selectable,tr.selectable'
					},

					tooltipClass: {
						validator: Lang.isString,
						value: 'btn btn-group'
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'searchcontainermove',

				NS: 'move',

				prototype: {
					initializer: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						instance._searchContainerSelect = host.select;

						instance._initDragAndDrop();

						instance._initDropTargets();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
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

					_initDragAndDrop: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						instance._ddHandler = new A.DD.Delegate(
							{
								container: host.get(STR_CONTENT_BOX),
								nodes: instance.get('rowSelector') + instance.get('draggableSelector'),
								on: {
									'drag:drophit': A.bind('_onDragDropHit', instance),
									'drag:enter': A.bind('_onDragEnter', instance),
									'drag:exit': A.bind('_onDragExit', instance),
									'drag:start': A.bind('_onDragStart', instance)
								}
							}
						);

						var dd = instance._ddHandler.dd;

						dd.set('groups', [host.get('id')]);
						dd.set('offsetNode', false);

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
					},

					_initDropTargets: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						if (themeDisplay.isSignedIn()) {
							instance.get(STR_DROP_TARGET_SELECTORS).forEach(
								function(target) {
									var container = target.container || host.get(STR_CONTENT_BOX);

									var targetNodes = container.all(target.selector);

									targetNodes.each(
										function(item, index) {
											item.plug(
												A.Plugin.Drop,
												{
													groups: [host.get('id')]
												}
											).drop.on(
												{
													'drop:enter': function(event) {
														item.addClass(target.activeCSSClass);
													},

													'drop:exit': function(event) {
														item.removeClass(target.activeCSSClass);
													},

													'drop:hit': function(event) {
														item.removeClass(target.activeCSSClass);

														var selectedItems = instance._ddHandler.dd.get(STR_DATA).selectedItems;

														var dropTarget = event.drop.get(STR_NODE);

														host.executeAction(
															target.action,
															{
																selectedItems: selectedItems,
																targetItem: dropTarget
															}
														);
													}
												}
											);
										}
									);

									if (target.infoCSSClass) {
										instance._ddHandler.on(
											['drag:start', 'drag:end'],
											function(event) {
												targetNodes.toggleClass(target.infoCSSClass, event.type == 'drag:start');
											}
										);
									}
								}
							);
						}
					},

					_onDragDropHit: function(event) {
						var instance = this;

						var proxyNode = event.target.get(STR_DRAG_NODE);

						proxyNode.removeClass(instance.get('tooltipClass'));

						proxyNode.empty();
					},

					_onDragEnter: function(event) {
						var instance = this;

						var dragNode = event.drag.get(STR_NODE);

						var dropTarget = event.drop.get(STR_NODE);

						if (!dragNode.compareTo(dropTarget)) {
							var proxyNode = event.target.get(STR_DRAG_NODE);

							var dd = instance._ddHandler.dd;

							var selectedItemsCount = dd.get(STR_DATA).selectedItemsCount;

							var moveText = instance._getMoveText(selectedItemsCount, true);

							var itemTitle = dropTarget.attr('data-title').trim();

							proxyNode.html(Lang.sub(moveText, [selectedItemsCount, A.Lang.String.escapeHTML(itemTitle)]));
						}
					},

					_onDragExit: function(event) {
						var instance = this;

						var proxyNode = event.target.get(STR_DRAG_NODE);

						var selectedItemsCount = instance._ddHandler.dd.get(STR_DATA).selectedItemsCount;

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));
					},

					_onDragStart: function(event) {
						var instance = this;

						var target = event.target;

						var node = target.get(STR_NODE);

						var selectedItems = new A.NodeList(node);

						if (instance._searchContainerSelect) {
							var selected = instance._searchContainerSelect._isSelected(node);

							if (!selected) {
								instance._searchContainerSelect.toggleAllRows(false);
								instance._searchContainerSelect.toggleRow(
									{
										toggleCheckbox: true
									},
									node
								);
							}
							else {
								selectedItems = instance._searchContainerSelect._getCurrentPageSelectedElements();
							}
						}

						var selectedItemsCount = selectedItems.size();

						var dd = instance._ddHandler.dd;

						dd.set(
							STR_DATA,
							{
								selectedItems: selectedItems,
								selectedItemsCount: selectedItemsCount
							}
						);

						var proxyNode = target.get(STR_DRAG_NODE);

						proxyNode.setStyles(
							{
								height: STR_BLANK,
								width: STR_BLANK
							}
						);

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));

						proxyNode.addClass(instance.get('tooltipClass'));
					}
				}
			}
		);

		A.Plugin.SearchContainerMove = SearchContainerMove;
	},
	'',
	{
		requires: ['aui-component', 'dd-constrain', 'dd-delegate', 'dd-drag', 'dd-drop', 'dd-proxy', 'plugin']
	}
);