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
	'liferay-sidebar-panel',
	function(A) {
		var Lang = A.Lang;

		var SidebarPanel = A.Component.create({
			ATTRS: {
				resourceUrl: {
					validator: Lang.isString
				},

				searchContainerId: {
					validator: Lang.isString
				},

				targetNode: {
					setter: A.one
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'liferaysidebarpanel',

			prototype: {
				initializer: function(config) {
					var instance = this;

					instance._searchContainerRegisterHandle = Liferay.on(
						'search-container:registered',
						instance._onSearchContainerRegistered,
						instance
					);
				},

				destructor: function() {
					var instance = this;

					instance._detachSearchContainerRegisterHandle();

					new A.EventHandle(instance._eventHandles).detach();
				},

				_bindUI: function() {
					var instance = this;

					instance._eventHandles = [
						instance._searchContainer.on(
							'rowToggled',
							A.debounce(
								instance._getSidebarContent,
								50,
								instance
							),
							instance
						),
						Liferay.after('refreshInfoPanel', function() {
							setTimeout(function() {
								instance._getSidebarContent();
							}, 0);
						})
					];
				},

				_detachSearchContainerRegisterHandle: function() {
					var instance = this;

					var searchContainerRegisterHandle =
						instance._searchContainerRegisterHandle;

					if (searchContainerRegisterHandle) {
						searchContainerRegisterHandle.detach();

						instance._searchContainerRegisterHandle = null;
					}
				},

				_getSidebarContent: function(event) {
					var instance = this;

					Liferay.Util.fetch(instance.get('resourceUrl'), {
						body: new FormData(
							instance._searchContainer.getForm().getDOM()
						),
						method: 'POST'
					})
						.then(response => response.text())
						.then(response =>
							instance.get('targetNode').setContent(response)
						);
				},

				_onSearchContainerRegistered: function(event) {
					var instance = this;

					var searchContainer = event.searchContainer;

					if (
						searchContainer.get('id') ===
						instance.get('searchContainerId')
					) {
						instance._searchContainer = searchContainer;

						instance._detachSearchContainerRegisterHandle();

						instance.get('targetNode').plug(A.Plugin.ParseContent);

						instance._bindUI();
					}
				}
			}
		});

		Liferay.SidebarPanel = SidebarPanel;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-debounce',
			'aui-parse-content',
			'liferay-portlet-base'
		]
	}
);
