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
	'liferay-journal-navigation',
	A => {
		var Lang = A.Lang;

		var JournalNavigation = A.Component.create({
			ATTRS: {
				editEntryUrl: {
					validator: Lang.isString
				},

				form: {
					validator: Lang.isObject
				},

				moveEntryUrl: {
					validator: Lang.isString
				},

				searchContainerId: {
					validator: Lang.isString
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'journalnavigation',

			prototype: {
				_bindUI() {
					var instance = this;

					instance._eventHandles = [
						Liferay.on(
							instance.ns('editEntry'),
							instance._editEntry,
							instance
						)
					];
				},

				_editEntry(event) {
					var instance = this;

					var action = event.action;

					var url = instance.get('editEntryUrl');

					if (action === 'move' || action === 'moveEntries') {
						url = instance.get('moveEntryUrl');
					}

					instance._processAction(action, url);
				},

				_moveToFolder(obj) {
					var instance = this;

					var namespace = instance.NS;

					var dropTarget = obj.targetItem;

					var selectedItems = obj.selectedItems;

					var folderId = dropTarget.attr('data-folder-id');

					if (folderId) {
						if (
							!instance._searchContainer.select ||
							selectedItems.indexOf(
								dropTarget.one('input[type=checkbox]')
							)
						) {
							var form = instance.get('form').node;

							form.get(namespace + 'newFolderId').val(folderId);

							instance._processAction(
								'move',
								instance.get('moveEntryUrl')
							);
						}
					}
				},

				_moveToTrash() {
					var instance = this;

					instance._processAction(
						'/journal/move_entries_to_trash',
						instance.get('editEntryUrl')
					);
				},

				_processAction(action, url, redirectUrl) {
					var instance = this;

					var namespace = instance.NS;

					var form = instance.get('form').node;

					redirectUrl = redirectUrl || location.href;

					form.attr('method', instance.get('form').method);

					if (form.get(namespace + 'javax-portlet-action')) {
						form.get(namespace + 'javax-portlet-action').val(
							action
						);
					}
					else {
						form.get(namespace + 'cmd').val(action);
					}

					form.get(namespace + 'redirect').val(redirectUrl);

					submitForm(form, url);
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					var namespace = instance.NS;

					var searchContainer = Liferay.SearchContainer.get(
						namespace + instance.get('searchContainerId')
					);

					searchContainer.registerAction(
						'move-to-folder',
						A.bind('_moveToFolder', instance)
					);
					searchContainer.registerAction(
						'move-to-trash',
						A.bind('_moveToTrash', instance)
					);

					instance._searchContainer = searchContainer;

					instance._bindUI();
				}
			}
		});

		Liferay.Portlet.JournalNavigation = JournalNavigation;
	},
	'',
	{
		requires: [
			'aui-component',
			'liferay-portlet-base',
			'liferay-search-container'
		]
	}
);
