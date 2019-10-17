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
	'liferay-product-navigation-control-menu-add-application',
	A => {
		var ControlMenu = Liferay.ControlMenu;

		var CSS_LFR_PORTLET_USED = 'lfr-portlet-used';

		var DATA_PORTLET_ID = 'data-portlet-id';

		var SELECTOR_ADD_CONTENT_ITEM = '.add-content-item';

		var STR_CLICK = 'click';

		var STR_KEY = 'key';

		var AddApplication = A.Component.create({
			AUGMENTS: Liferay.PortletBase,

			EXTENDS: ControlMenu.AddBase,

			NAME: 'addapplication',

			prototype: {
				_addApplication(event) {
					var instance = this;

					var portlet = event.currentTarget;

					if (event.type === STR_KEY) {
						portlet = portlet.one(SELECTOR_ADD_CONTENT_ITEM);
					}

					instance.addPortlet(portlet);
				},

				_bindUI() {
					var instance = this;

					instance._eventHandles.push(
						instance._entriesPanel.delegate(
							STR_CLICK,
							instance._addApplication,
							SELECTOR_ADD_CONTENT_ITEM,
							instance
						),
						Liferay.on(
							'closePortlet',
							instance._onPortletClose,
							instance
						)
					);
				},

				_onPortletClose(event) {
					var instance = this;

					var item = instance._entriesPanel.one(
						'.drag-content-item[data-plid=' +
							event.plid +
							'][data-portlet-id=' +
							event.portletId +
							'][data-instanceable=false]'
					);

					if (item && item.hasClass(CSS_LFR_PORTLET_USED)) {
						var portletId = item.attr(DATA_PORTLET_ID);

						instance._enablePortletEntry(portletId);
					}
				},

				destructor() {
					var instance = this;

					instance._panelSearch.destroy();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer(config) {
					var instance = this;

					instance._config = config;

					instance._addApplicationForm = instance.byId(
						'addApplicationForm'
					);
					instance._entriesPanel = instance.byId('applicationList');

					var togglerSelector = instance.ns(
						'addApplicationPanelContainer'
					);

					var togglerDelegate = Liferay.component(togglerSelector);

					if (togglerDelegate) {
						togglerDelegate.plug(Liferay.TogglerInteraction, {
							children: '.lfr-content-item',
							parents: '.lfr-content-category'
						});
					}

					instance._panelSearch = new Liferay.PanelSearch({
						categorySelector: '.panel-page-category',
						inputNode: instance.get('inputNode'),
						nodeContainerSelector: '.lfr-content-item',
						nodeList: config.nodeList,
						nodeSelector: '.drag-content-item',
						togglerId: togglerSelector
					});

					instance._bindUI();
				}
			}
		});

		ControlMenu.AddApplication = AddApplication;
	},
	'',
	{
		requires: [
			'event-key',
			'event-mouseenter',
			'liferay-product-navigation-control-menu',
			'liferay-product-navigation-control-menu-add-base',
			'liferay-panel-search',
			'liferay-portlet-base',
			'liferay-toggler-interaction'
		]
	}
);
