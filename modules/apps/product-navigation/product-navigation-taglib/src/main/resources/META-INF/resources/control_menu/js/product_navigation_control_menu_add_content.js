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
	'liferay-product-navigation-control-menu-add-content',
	function(A) {
		var ControlMenu = Liferay.ControlMenu;

		var SELECTOR_ADD_CONTENT_ITEM = '.add-content-item';

		var STR_CLICK = 'click';

		var STR_RESPONSE_DATA = 'responseData';

		var AddContent = A.Component.create({
			AUGMENTS: [ControlMenu.AddContentSearch, Liferay.PortletBase],

			EXTENDS: ControlMenu.AddBase,

			NAME: 'addcontent',

			prototype: {
				initializer: function(config) {
					var instance = this;

					instance._config = config;
					instance._delta = config.delta;
					instance._displayStyle = config.displayStyle;

					instance._addContentForm = instance.byId('addContentForm');
					instance._entriesPanel = instance.byId('entriesContainer');

					instance._entriesPanel.plug(A.Plugin.ParseContent);

					instance._bindUI();
				},

				destructor: function() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				_afterSuccess: function(event) {
					var instance = this;

					instance._entriesPanel.setContent(
						event.currentTarget.get(STR_RESPONSE_DATA)
					);
				},

				_bindUI: function() {
					var instance = this;

					instance._eventHandles.push(
						instance._entriesPanel.delegate(
							STR_CLICK,
							instance._addContent,
							SELECTOR_ADD_CONTENT_ITEM,
							instance
						),
						Liferay.on(
							'AddContent:refreshContentList',
							instance._refreshContentList,
							instance
						),
						Liferay.on('AddContent:addPortlet', function(event) {
							instance.addPortlet(event.node, event.options);
						})
					);
				},

				_refreshContentList: function(event) {
					var instance = this;

					var delta = event.delta;

					if (delta) {
						instance._delta = delta;

						Liferay.Store(
							'com.liferay.product.navigation.control.menu.web_addPanelNumItems',
							delta
						);
					}

					var displayStyle = event.displayStyle;

					if (displayStyle) {
						instance._displayStyle = displayStyle;

						Liferay.Store(
							'com.liferay.product.navigation.control.menu.web_addPanelDisplayStyle',
							displayStyle
						);
					}

					var data = instance.ns({
						delta: instance._delta,
						displayStyle: instance._displayStyle,
						keywords: instance.get('inputNode').val()
					});

					Liferay.Util.fetch(
						instance._addContentForm.getAttribute('action'),
						{
							body: Liferay.Util.objectToFormData(data),
							method: 'POST'
						}
					).then(function() {
						A.bind('_afterSuccess', instance);
					});
				}
			}
		});

		ControlMenu.AddContent = AddContent;
	},
	'',
	{
		requires: [
			'aui-parse-content',
			'liferay-product-navigation-control-menu',
			'liferay-product-navigation-control-menu-add-base',
			'liferay-product-navigation-control-menu-add-content-search',
			'liferay-store'
		]
	}
);
