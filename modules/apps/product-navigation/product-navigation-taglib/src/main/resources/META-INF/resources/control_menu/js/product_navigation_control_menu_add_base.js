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
	'liferay-product-navigation-control-menu-add-base',
	A => {
		var DDM = A.DD.DDM;
		var Lang = A.Lang;

		var ControlMenu = Liferay.ControlMenu;
		var Layout = Liferay.Layout;
		var Portlet = Liferay.Portlet;
		var Util = Liferay.Util;

		var CSS_LFR_PORTLET_USED = 'lfr-portlet-used';

		var DATA_CLASS_NAME = 'data-class-name';

		var DATA_CLASS_PK = 'data-class-pk';

		var DATA_PORTLET_ID = 'data-portlet-id';

		var EVENT_SHOWN_COLLAPSE = 'liferay.collapse.shown';

		var PROXY_NODE_ITEM = Layout.PROXY_NODE_ITEM;

		var STR_EMPTY = '';

		var STR_NODE = 'node';

		var TPL_LOADING = '<div class="loading-animation" />';

		var AddBase = A.Component.create({
			ATTRS: {
				focusItem: {
					setter: A.one
				},

				id: {
					validator: Lang.isString
				},

				inputNode: {
					setter: A.one
				},

				nodeList: {
					setter: A.one
				},

				nodeSelector: {
					validator: Lang.isString
				},

				panelBody: {
					setter: A.one
				}
			},

			EXTENDS: A.Base,

			NAME: 'addbase',

			prototype: {
				_bindUIDABase() {
					var instance = this;

					var panelBody = Util.getDOM(instance._panelBody);

					var listGroupPanel = panelBody.querySelectorAll(
						'.list-group-panel'
					);

					var eventType = EVENT_SHOWN_COLLAPSE;

					Liferay.on(eventType, event => {
						var panelId = event.panel.getAttribute('id');

						if (panelId === panelBody.getAttribute('id')) {
							instance._focusOnItem.call(instance);
						}

						listGroupPanel.forEach(element => {
							if (panelId === element.getAttribute('id')) {
								event.stopPropagation();
							}
						});
					});
				},

				_disablePortletEntry(portletId) {
					var instance = this;

					instance._eachPortletEntry(portletId, item => {
						item.addClass(CSS_LFR_PORTLET_USED);
					});
				},

				_eachPortletEntry(portletId, callback) {
					var portlets = A.all('[data-portlet-id=' + portletId + ']');

					portlets.each(callback);
				},

				_enablePortletEntry(portletId) {
					var instance = this;

					instance._eachPortletEntry(portletId, item => {
						item.removeClass(CSS_LFR_PORTLET_USED);
					});
				},

				_focusOnItem() {
					var instance = this;

					var focusItem = instance._focusItem;

					if (focusItem) {
						focusItem.focus();
					}
				},

				_getPortletMetaData(portlet) {
					var portletMetaData = portlet._LFR_portletMetaData;

					if (!portletMetaData) {
						var className = portlet.attr(DATA_CLASS_NAME);
						var classPK = portlet.attr(DATA_CLASS_PK);

						var instanceable =
							portlet.attr('data-instanceable') == 'true';
						var plid = portlet.attr('data-plid');

						var portletData = STR_EMPTY;

						if (className != STR_EMPTY && classPK != STR_EMPTY) {
							portletData = classPK + ',' + className;
						}

						var portletId = portlet.attr(DATA_PORTLET_ID);
						var portletItemId = portlet.attr(
							'data-portlet-item-id'
						);
						var portletUsed = portlet.hasClass(
							CSS_LFR_PORTLET_USED
						);

						portletMetaData = {
							instanceable,
							plid,
							portletData,
							portletId,
							portletItemId,
							portletUsed
						};

						portlet._LFR_portletMetaData = portletMetaData;
					}

					return portletMetaData;
				},

				_isSelected() {
					var instance = this;

					return instance._panelBody.hasClass('in');
				},

				_portletFeedback() {
					new Liferay.Notification({
						closeable: true,
						delay: {
							hide: 5000,
							show: 0
						},
						duration: 500,
						message: Liferay.Language.get(
							'the-application-was-added-to-the-page'
						),
						type: 'success'
					}).render('body');
				},

				addPortlet(portlet, options) {
					var instance = this;

					var portletMetaData = instance._getPortletMetaData(portlet);

					if (!portletMetaData.portletUsed) {
						var portletId = portletMetaData.portletId;

						if (!portletMetaData.instanceable) {
							instance._disablePortletEntry(portletId);
						}

						var beforePortletLoaded = null;
						var placeHolder = A.Node.create(TPL_LOADING);

						if (options) {
							var item = options.item;

							item.placeAfter(placeHolder);
							item.remove(true);

							beforePortletLoaded = options.beforePortletLoaded;
						}
						else {
							var firstColumn = Layout.getActiveDropNodes().item(
								0
							);

							if (firstColumn) {
								var dropColumn = firstColumn.one(
									Layout.options.dropContainer
								);
								var referencePortlet = Layout.findReferencePortlet(
									dropColumn
								);

								if (referencePortlet) {
									referencePortlet.placeBefore(placeHolder);
								}
								else if (dropColumn) {
									dropColumn.append(placeHolder);
								}
							}
						}

						if (Util.isPhone() || Util.isTablet()) {
							placeHolder.guid();

							instance._portletFeedback(portletId, portlet);
						}

						Portlet.add({
							beforePortletLoaded,
							placeHolder,
							plid: portletMetaData.plid,
							portletData: portletMetaData.portletData,
							portletId,
							portletItemId: portletMetaData.portletItemId
						});
					}
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					instance._focusItem = instance.get('focusItem');
					instance._panelBody = instance.get('panelBody');

					var focusItem = instance._focusItem;

					if (focusItem && instance._isSelected()) {
						focusItem.focus();
					}

					instance._guid = A.stamp(instance);

					instance._eventHandles = [];

					instance._bindUIDABase();
				}
			}
		});

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

		ControlMenu.AddBase = AddBase;
		ControlMenu.PortletItem = PortletItem;
	},
	'',
	{
		requires: [
			'anim',
			'aui-base',
			'liferay-layout',
			'liferay-layout-column',
			'liferay-notification',
			'liferay-product-navigation-control-menu',
			'transition'
		]
	}
);
