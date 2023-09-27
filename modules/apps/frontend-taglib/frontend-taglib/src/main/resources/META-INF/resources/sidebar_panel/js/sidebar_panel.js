/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-sidebar-panel',
	(A) => {
		const Lang = A.Lang;

		const SidebarPanel = A.Component.create({
			ATTRS: {
				resourceUrl: {
					validator: Lang.isString,
				},

				searchContainerId: {
					validator: Lang.isString,
				},

				targetNode: {
					setter: A.one,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'liferaysidebarpanel',

			prototype: {
				_bindUI() {
					const instance = this;

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
						Liferay.after('refreshInfoPanel', () => {
							setTimeout(() => {
								instance._getSidebarContent();
							}, 0);
						}),
					];
				},

				_detachSearchContainerRegisterHandle() {
					const instance = this;

					const searchContainerRegisterHandle =
						instance._searchContainerRegisterHandle;

					if (searchContainerRegisterHandle) {
						searchContainerRegisterHandle.detach();

						instance._searchContainerRegisterHandle = null;
					}
				},

				_getSidebarContent() {
					const instance = this;

					Liferay.Util.fetch(instance.get('resourceUrl'), {
						body: new FormData(
							instance._searchContainer.getForm().getDOM()
						),
						method: 'POST',
					})
						.then((response) => response.text())
						.then((response) =>
							instance.get('targetNode').setContent(response)
						);
				},

				_onSearchContainerRegistered(event) {
					const instance = this;

					const searchContainer = event.searchContainer;

					if (
						searchContainer.get('id') ===
						instance.get('searchContainerId')
					) {
						instance._searchContainer = searchContainer;

						instance._detachSearchContainerRegisterHandle();

						instance.get('targetNode').plug(A.Plugin.ParseContent);

						instance._bindUI();
					}
				},

				destructor() {
					const instance = this;

					instance._detachSearchContainerRegisterHandle();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					instance._searchContainerRegisterHandle = Liferay.on(
						'search-container:registered',
						instance._onSearchContainerRegistered,
						instance
					);
				},
			},
		});

		Liferay.SidebarPanel = SidebarPanel;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-debounce',
			'aui-parse-content',
			'liferay-portlet-base',
		],
	}
);
