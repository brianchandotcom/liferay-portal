/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-product-navigation-control-menu-add-content-drag-drop',
	A => {
		var DDM = A.DD.DDM;

		var ControlMenu = Liferay.ControlMenu;
		var Layout = Liferay.Layout;

		var PROXY_NODE_ITEM = Layout.PROXY_NODE_ITEM;

		var STR_NODE = 'node';

		var AddContentDragDrop = function() {};

		AddContentDragDrop.prototype = {
			_bindUIDragDrop() {
				var instance = this;

				var portletItemOptions = {
					delegateConfig: {
						container: instance._entriesPanel,
						dragConfig: {
							clickPixelThresh: 0,
							clickTimeThresh: 0
						},
						invalid: '.lfr-portlet-used',
						target: false
					},
					dragNodes: '[data-draggable]',
					dropContainer(dropNode) {
						return dropNode.one(Layout.options.dropContainer);
					}
				};

				var defaultLayoutOptions = Layout.DEFAULT_LAYOUT_OPTIONS;

				if (defaultLayoutOptions) {
					portletItemOptions.on = defaultLayoutOptions.on;
				}

				var portletItem = new ControlMenu['PortletItem'](
					portletItemOptions
				);

				portletItem.on('drag:end', instance._onDragEnd, instance);

				instance._portletItem = portletItem;

				Liferay.fire('initLayout');
			},

			_onDragEnd(event) {
				var instance = this;

				var portletItem = event.currentTarget;

				var appendNode = portletItem.appendNode;

				if (appendNode && appendNode.inDoc()) {
					var portletNode = event.target.get(STR_NODE);

					instance.addPortlet(portletNode, {
						item: appendNode
					});
				}
			},

			initializer() {
				var instance = this;

				instance._bindUIDragDrop();
			}
		};

		var PortletItem = A.Component.create({
			ATTRS: {
				lazyStart: {
					value: true
				},

				proxyNode: {
					value: PROXY_NODE_ITEM
				}
			},

			EXTENDS: Layout.ColumnLayout,

			NAME: 'PortletItem',

			prototype: {
				_getAppendNode() {
					var instance = this;

					instance.appendNode = DDM.activeDrag.get(STR_NODE).clone();

					return instance.appendNode;
				},

				_onDragStart() {
					var instance = this;

					PortletItem.superclass._onDragStart.apply(this, arguments);

					instance._syncProxyTitle();

					instance.lazyEvents = false;
				},

				_onPlaceholderAlign(event) {
					var drop = event.drop;
					var portletItem = event.currentTarget;

					if (drop && portletItem) {
						var dropNodeId = drop.get(STR_NODE).get('id');

						if (Layout.EMPTY_COLUMNS[dropNodeId]) {
							portletItem.activeDrop = drop;
							portletItem.lazyEvents = false;
							portletItem.quadrant = 1;
						}
					}
				},

				_positionNode(event) {
					var portalLayout = event.currentTarget;

					var activeDrop =
						portalLayout.lastAlignDrop || portalLayout.activeDrop;

					if (activeDrop) {
						var dropNode = activeDrop.get(STR_NODE);

						if (dropNode.isStatic) {
							var dropColumn = dropNode.ancestor(
								Layout.options.dropContainer
							);
							var foundReferencePortlet = Layout.findReferencePortlet(
								dropColumn
							);

							if (!foundReferencePortlet) {
								foundReferencePortlet = Layout.getLastPortletNode(
									dropColumn
								);
							}

							if (foundReferencePortlet) {
								var drop = DDM.getDrop(foundReferencePortlet);

								if (drop) {
									portalLayout.quadrant = 4;
									portalLayout.activeDrop = drop;
									portalLayout.lastAlignDrop = drop;
								}
							}
						}

						PortletItem.superclass._positionNode.apply(
							this,
							arguments
						);
					}
				},

				_syncProxyTitle() {
					var instance = this;

					var node = DDM.activeDrag.get(STR_NODE);
					var title = node.attr('data-title');

					instance.PROXY_TITLE.html(title);
				},

				PROXY_TITLE: PROXY_NODE_ITEM.one('.portlet-title'),

				bindUI() {
					var instance = this;

					PortletItem.superclass.bindUI.apply(this, arguments);

					instance.on(
						'placeholderAlign',
						instance._onPlaceholderAlign
					);
				}
			}
		});

		ControlMenu.AddContentDragDrop = AddContentDragDrop;
		ControlMenu.PortletItem = PortletItem;
	},
	'',
	{
		requires: [
			'aui-base',
			'dd',
			'liferay-product-navigation-control-menu',
			'liferay-layout',
			'liferay-layout-column',
			'liferay-portlet-base'
		]
	}
);
