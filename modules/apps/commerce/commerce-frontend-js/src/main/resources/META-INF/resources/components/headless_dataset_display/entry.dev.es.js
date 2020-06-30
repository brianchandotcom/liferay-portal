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

import headlessDatasetDisplayLauncher from './entry.es';

import '../../styles/main.scss';

const lang_id = themeDisplay.getLanguageId();

const dataSetDisplayProps = {
	apiUrl: '/o/headless-commerce-admin-catalog/v1.0/products',
	bulkActions: [
		{
			href: '/side-panel/edit.html',
			icon: 'plus',
			label: 'Add',
			target: 'sidePanel'
		},
		{
			href: '/delete',
			icon: 'trash',
			label: 'Delete',
			method: 'delete'
		}
	],
	creationMenuItems: [
		{
			href: '/standard/edit',
			label: 'Add'
		},
		{
			href: 'modal/url',
			label: 'Add via modal',
			target: 'modal'
		}
	],
	filters: [
		// {
		// 	id: 'name',
		// 	label: 'Name',
		// 	operator: 'eq',
		// 	type: 'text',
		// 	value: 'Test input'
		// },
		{
			id: 'createDate',
			label: 'Creation date',
			operator: 'eq',
			type: 'date'
		},
		{
			apiUrl: '/o/headless-commerce-admin-catalog/v1.0/products',
			id: 'accountId',
			inputPlaceholder: 'Search for products...',
			itemKey: 'productId',
			itemLabel: ['name', lang_id],
			label: 'Product ID',
			selectionType: 'single',
			type: 'autocomplete'
		}
	],
	id: 'tableTest',
	itemActions: [
		{
			href: '/edit/{productId}',
			icon: 'pencil',
			label: 'Edit'
		},
		{
			href: '/delete/{productId}',
			icon: 'trash',
			label: 'Delete',
			method: 'delete',
			target: 'async'
		}
	],
	pageSize: 5,
	pagination: {
		deltas: [
			{
				label: 5
			},
			{
				label: 10
			},
			{
				label: 20
			},
			{
				label: 30
			},
			{
				label: 50
			},
			{
				href:
					'http://localhost:8080/group/test-1/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_delta=75',
				label: 75
			}
		],
		initialDelta: 10,
		initialPageNumber: 1,
		initialTotalItems: 40
	},
	selectedItemsKey: 'productId',
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/icons.svg',
	views: [
		{
			contentRenderer: 'table',
			icon: 'table',
			label: 'Table',
			schema: {
				fields: [
					{
						contentRenderer: 'actionLink',
						fieldName: ['name', lang_id],
						label: 'Name',
						sortable: true
					},
					{
						fieldName: 'productType',
						label: 'Product Type',
						mapData: value => value.toUpperCase()
					},
					{
						fieldName: 'externalReferenceCode',
						label: 'SKU'
					},
					{
						contentRenderer: 'date',
						fieldName: 'modifiedDate',
						label: 'Modified Date',
						type: 'relative'
					},
					{
						contentRenderer: 'label',
						fieldName: 'active',
						label: 'Status',
						mapData: value =>
							value
								? {
										displayStyle: 'success',
										label: 'Active'
								  }
								: {
										displayStyle: 'danger',
										label: 'Disabled'
								  }
					}
				]
			}
		}
	]
};

headlessDatasetDisplayLauncher(
	'dataset-display',
	'headless-dataset-display-root-id',
	dataSetDisplayProps
);
