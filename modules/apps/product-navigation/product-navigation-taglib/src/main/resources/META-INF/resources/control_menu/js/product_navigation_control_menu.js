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
	'liferay-product-navigation-control-menu',
	(A) => {
		var Layout = Liferay.Layout;
		var Portlet = Liferay.Portlet;
		var Util = Liferay.Util;

		var CSS_LFR_PORTLET_USED = 'lfr-portlet-used';

		var DATA_CLASS_NAME = 'data-class-name';

		var DATA_CLASS_PK = 'data-class-pk';

		var DATA_PORTLET_ID = 'data-portlet-id';

		var STR_EMPTY = '';

		var TPL_LOADING = '<div class="loading-animation" />';

		var ControlMenu = {
			addPortlet(portlet, options) {
				var instance = this;

				var portletMetaData = instance.getPortletMetaData(portlet);

				if (!portletMetaData.portletUsed) {
					var portletId = portletMetaData.portletId;

					if (!portletMetaData.instanceable) {
						instance.disablePortletEntry(portletId);
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
						var firstColumn = Layout.getActiveDropNodes().item(0);

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

						instance.portletFeedback(portletId, portlet);
					}

					Portlet.add({
						beforePortletLoaded,
						placeHolder,
						plid: portletMetaData.plid,
						portletData: portletMetaData.portletData,
						portletId,
						portletItemId: portletMetaData.portletItemId,
					});
				}
			},

			disablePortletEntry(portletId) {
				var instance = this;

				instance.eachPortletEntry(portletId, (item) => {
					item.addClass(CSS_LFR_PORTLET_USED);
				});
			},

			eachPortletEntry(portletId, callback) {
				var portlets = A.all('[data-portlet-id=' + portletId + ']');

				portlets.each(callback);
			},

			getPortletMetaData(portlet) {
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
					var portletItemId = portlet.attr('data-portlet-item-id');
					var portletUsed = portlet.hasClass(CSS_LFR_PORTLET_USED);

					portletMetaData = {
						instanceable,
						plid,
						portletData,
						portletId,
						portletItemId,
						portletUsed,
					};

					portlet._LFR_portletMetaData = portletMetaData;
				}

				return portletMetaData;
			},

			init(containerId) {
				var instance = this;

				var controlMenu = A.one(containerId);

				if (controlMenu) {
					var namespace = controlMenu.attr('data-namespace');

					instance._namespace = namespace;

					Util.toggleControls(controlMenu);

					var eventHandle = controlMenu.on(
						['focus', 'mousemove', 'touchstart'],
						() => {
							Liferay.fire('initLayout');

							eventHandle.detach();
						}
					);

					if (!instance._eventHandler) {
						instance._eventHandler = Liferay.once(
							'AddContent:addPortlet',
							(event) => {
								instance.addPortlet(event.node, event.options);

								instance._eventHandler.detach();
							}
						);
					}
				}
			},

			portletFeedback() {
				new Liferay.Notification({
					closeable: true,
					delay: {
						hide: 5000,
						show: 0,
					},
					duration: 500,
					message: Liferay.Language.get(
						'the-application-was-added-to-the-page'
					),
					type: 'success',
				}).render('body');
			},
		};

		Liferay.ControlMenu = ControlMenu;
	},
	'',
	{
		requires: [
			'aui-node',
			'aui-overlay-mask-deprecated',
			'event-move',
			'event-touch',
			'liferay-layout',
			'liferay-menu-toggle',
		],
	}
);
