/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-export-import-management-bar-button',
	(A) => {
		const Lang = A.Lang;

		const ExportImportManagementBarButton = A.Component.create({
			ATTRS: {
				actionNamespace: {
					validator: Lang.isString(),
				},

				cmd: {
					validator: Lang.isString(),
				},

				exportImportEntityUrl: {
					validator: Lang.isString(),
				},

				searchContainerId: {
					validator: Lang.isString,
				},

				searchContainerMappingId: {
					validator: Lang.isString,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'exportImportManagementBarButton',

			prototype: {
				_bindUI() {
					const instance = this;

					instance._eventHandles = [
						Liferay.on(
							instance.ns(instance.get('cmd')),
							instance._exportImportEntity,
							instance
						),
					];
				},

				_exportImportEntity() {
					const instance = this;

					const searchContainer = instance._searchContainer.plug(
						A.Plugin.SearchContainerSelect
					);

					const selectedRows = searchContainer.select.getAllSelectedElements();

					const namespace = instance.NS;

					const searchContainerMapping = A.one(
						'#' +
							namespace +
							instance.get('searchContainerMappingId')
					);

					const form = document.getElementById('hrefFm');

					if (form) {
						selectedRows._nodes.forEach((selectedElement) => {
							const node = searchContainerMapping.one(
								'div[data-rowpk=' + selectedElement.value + ']'
							);

							const input = document.createElement('input');
							input.setAttribute('type', 'hidden');
							input.setAttribute(
								'name',
								instance.get('actionNamespace') +
									'exportingEntities'
							);
							input.value =
								node.attr('data-classNameId') +
								'#' +
								node.attr('data-groupId') +
								'#' +
								node.attr('data-uuid');

							form.appendChild(input);
						});

						form.setAttribute('method', 'POST');

						submitForm(form, instance.get('exportImportEntityUrl'));
					}
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					const namespace = instance.NS;

					const searchContainer = Liferay.SearchContainer.get(
						namespace + instance.get('searchContainerId')
					);

					instance._searchContainer = searchContainer;

					instance._bindUI();
				},
			},
		});

		Liferay.ExportImportManagementBarButton = ExportImportManagementBarButton;
	},
	'',
	{
		requires: [
			'aui-component',
			'liferay-search-container',
			'liferay-search-container-select',
		],
	}
);
