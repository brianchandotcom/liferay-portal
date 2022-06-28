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
