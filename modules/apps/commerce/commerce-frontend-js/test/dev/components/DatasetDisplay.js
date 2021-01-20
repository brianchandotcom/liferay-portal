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

import React from 'react';

import datasetDisplayLauncher from '../../../../../frontend-taglib/frontend-taglib-clay/src/main/resources/META-INF/resources/data_set_display/entry';
import sidePanelLauncher from '../../../src/main/resources/META-INF/resources/components/side_panel/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

import '../../../../../frontend-taglib/frontend-taglib-clay/src/main/resources/META-INF/resources/data_set_display/styles/main.scss';

const fluidDataSetDisplayProps = {
	activeViewSettings: {},
	apiURL: '/dataset-display-nested-items',
	appURL: '/o/frontend-taglib-clay/app',
	bulkActions: [
		{
			href: '/side-panel/edit.html',
			icon: 'plus',
			label: 'Add',
			target: 'sidePanel',
		},
		{
			href: '/delete',
			icon: 'trash',
			label: 'Delete',
			method: 'delete',
		},
	],
	creationMenu: {
		primaryItems: [
			{
				href: 'modal/url',
				label: 'Add',
				target: 'modal',
			},
		],
	},
	filters: [
		{
			id: 'number-test',
			inputText: '$',
			label: 'Number',
			max: 200,
			min: 20,
			operator: 'eq',
			type: 'number',
		},
		{
			id: 'order-date',
			label: 'Order Range',
			max: {
				day: 2,
				month: 9,
				year: 2026,
			},
			min: {
				day: 14,
				month: 6,
				year: 2020,
			},
			placeholder: 'dd/mm/yyyy',
			type: 'dateRange',
		},
	],
	id: 'tableTest',
	nestedItemsKey: 'skuId',
	nestedItemsReferenceKey: 'testSubItems',
	pageSize: 5,
	pagination: {
		deltas: [
			{
				label: 5,
			},
			{
				label: 10,
			},
			{
				label: 20,
			},
			{
				label: 30,
			},
			{
				label: 50,
			},
			{
				href:
					'http://localhost:8080/group/test-1/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_delta=75',
				label: 75,
			},
		],
		initialDelta: 10,
		initialPageNumber: 1,
		initialTotalItems: 40,
	},
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/clay/icons.svg',
	style: 'fluid',
	views: [
		{
			contentRenderer: 'table',
			label: 'Table',
			name: 'table',
			schema: {
				fields: [
					{
						contentRenderer: 'image',
						fieldName: 'img',
						hideColumnLabel: true,
						label: 'Thumbnail',
					},
					{
						contentRenderer: 'actionLink',
						fieldName: 'name',
						label: 'Name',
						sortable: true,
					},
					{
						actionId: 'edit',
						contentRenderer: 'actionLink',
						hideColumnLabel: true,
						label: 'Edit action',
					},
					{
						actionId: 'delete',
						contentRenderer: 'actionLink',
						hideColumnLabel: true,
						label: 'Delete Action',
					},
					{
						actionId: 'alert',
						contentRenderer: 'actionLink',
						hideColumnLabel: true,
						label: 'Alert',
					},
					{
						actionId: 'select',
						contentRenderer: 'actionLink',
						hideColumnLabel: true,
						label: 'Select',
					},
					{
						contentRenderer: 'tooltipSummary',
						details: {
							rowsDefinitions: [
								{
									label: 'Name',
									valueFieldKey: 'name',
								},
								{
									label: 'NestedItem',
									valueFieldKey: ['testLink', 'href'],
								},
								{
									contentRenderer: 'image',
									label: 'Thumbnail',
									valueFieldKey: 'img',
								},
								{
									divider: true,
									label: 'Price',
									valueFieldKey: 'price',
								},
								{
									divider: true,
									highlighted: true,
									label: 'Price Copy',
									valueFieldKey: 'price',
								},
							],
						},
						fieldName: 'price',
						label: 'Price',
					},
					{
						contentRenderer: 'quantitySelector',
						fieldName: 'testQuantity',
						label: 'Qt. Selector',
					},
				],
			},
			thumbnail: 'table',
		},
		{
			contentRenderer: 'cards',
			label: 'Cards',
			name: 'cards',
			schema: {
				description: 'name',
				href: 'productPage',
				imgProps: 'img',
				labels: 'status',
				stickerProps: 'type',
				title: 'skuId',
			},
			thumbnail: 'documents-and-media',
		},
		{
			component: (props) => {
				return (
					<>
						<h4 className="bg-dark mb-0 p-3 text-center text-white">
							Hey, I&apos;m a custom template from the outside
						</h4>
						{props.items.map((item) => (
							<div
								className="bg-white p-3 text-center"
								key={item.skuId}
							>
								<strong className="mr-3">{item.skuId}</strong>
								{item.name}
							</div>
						))}
					</>
				);
			},
			label: 'Custom table name',
			name: 'custom-table',
			schema: {},
			thumbnail: 'merge',
		},
	],
};

const emailsDataSetDisplayProps = {
	activeViewSettings: {},
	apiURL: '/dataset-display-email-data',
	creationMenuItems: [
		{
			href: '/standard/edit',
			label: 'Add',
		},
		{
			href: 'modal/url',
			label: 'Add via modal',
			target: 'modal',
		},
	],
	id: 'emailsDatasetDIsplay',
	pageSize: 5,
	pagination: {
		deltas: [
			{
				label: 5,
			},
			{
				label: 10,
			},
			{
				label: 20,
			},
			{
				label: 30,
			},
			{
				label: 50,
			},
			{
				href:
					'http://localhost:8080/group/test-1/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_delta=75',
				label: 75,
			},
		],
		initialDelta: 10,
	},
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/clay/icons.svg',
	style: 'stacked',
	views: [
		{
			contentRenderer: 'emailsList',
			label: 'Email',
			thumbnail: 'email',
		},
	],
};

const selectableTableProps = {
	activeViewSettings: {},
	apiURL: '/dataset-display-selectable-data',
	formId: 'form-id',
	id: 'tableTest',
	pageSize: 5,
	pagination: {
		deltas: [
			{
				label: 5,
			},
			{
				label: 10,
			},
			{
				label: 20,
			},
			{
				label: 30,
			},
			{
				label: 50,
			},
			{
				href:
					'http://localhost:8080/group/test-1/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_delta=75',
				label: 75,
			},
		],
		initialDelta: 10,
	},
	selectedItemsKey: 'countryId',
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/clay/icons.svg',
	views: [
		{
			contentRenderer: 'selectableTable',
			label: 'Table',
			schema: {
				firstColumnLabel: 'Country',
				firstColumnName: 'countryName',
			},
			thumbnail: 'table',
		},
	],
};

const today = new Date();

const ordersDataSetDisplayProps = {
	activeViewSettings: {
		name: 'table',
	},
	apiURL:
		'/o/headless-commerce-admin-order/v1.0/orders?nestedFields=account,channel',
	appURL: '/o/frontend-taglib-clay/app',
	batchTasksStatusApiURL: '/o/fake-batch-engine/v1.0/import-task',
	bulkActions: [
		{
			bodyKeys: ['id', 'productId'],
			href: '/o/fake-bulk-action/v1.0/products/0/batch',
			icon: 'trash',
			label: 'Delete',
			method: 'delete',
			target: 'async',
		},
	],
	creationMenu: {
		primaryItems: [
			{
				href: 'modal/url',
				label: 'Add',
				target: 'modal',
			},
		],
	},
	filters: [
		{
			apiURL: '/o/headless-commerce-admin-account/v1.0/accounts',
			id: 'accountId',
			inputPlaceholder: 'Search for account',
			itemKey: 'id',
			itemLabel: 'name',
			label: 'Account',
			selectionType: 'single',
			type: 'autocomplete',
		},
		{
			apiURL: '/o/headless-commerce-admin-channel/v1.0/channels',
			id: 'channelId',
			inputPlaceholder: 'Search for Channel',
			itemKey: 'id',
			itemLabel: 'name',
			label: 'Channel',
			selectionType: 'single',
			type: 'autocomplete',
		},
		{
			id: 'createDate',
			label: 'Order Range',
			max: {
				day: today.getDate(),
				month: today.getMonth() + 1,
				year: today.getFullYear(),
			},
			min: {
				day: today.getDate(),
				month: today.getMonth() + 1,
				year: today.getFullYear() - 1,
			},
			placeholder: 'dd/mm/yyyy',
			type: 'dateRange',
		},
		{
			id: 'orderStatus',
			items: [
				{
					label: 'Completed',
					value: 1,
				},
				{
					label: 'Not-completed',
					value: 999,
				},
			],
			label: 'Status',
			operator: 'eq',
			type: 'radio',
		},
	],
	id: 'tableTest',
	itemsActions: [
		{
			href: '/view/{id}',
			icon: 'view',
			id: 'view',
			label: 'View',
		},
		{
			href: '/o/headless-commerce-admin-order/v1.0/orders/{id}',
			icon: 'trash',
			id: 'delete',
			label: 'Delete',
			method: 'delete',
			target: 'async',
		},
	],
	namespace:
		'_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_',
	pageSize: 5,
	pagination: {
		deltas: [
			{
				label: 5,
			},
			{
				label: 10,
			},
			{
				label: 20,
			},
			{
				label: 30,
			},
			{
				label: 50,
			},
			{
				href:
					'http://localhost:8080/group/test-1/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_delta=75',
				label: 75,
			},
		],
		initialDelta: 10,
	},
	selectedItemsKey: 'id',
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	sorting: [
		{
			direction: 'desc',
			key: 'createDate',
		},
	],
	spritemap: './assets/clay/icons.svg',
	views: [
		{
			contentRenderer: 'table',
			label: 'Table',
			name: 'table',
			schema: {
				fields: [
					{
						actionId: 'view',
						contentRenderer: 'actionLink',
						fieldName: 'id',
						label: 'order-id',
					},
					{
						fieldName: ['account', 'name'],
						label: 'account',
					},
					{
						fieldName: ['channel', 'name'],
						label: 'channel',
					},
					{
						fieldName: 'totalFormatted',
						label: 'amount',
					},
					{
						contentRenderer: 'date',
						fieldName: 'createDate',
						label: 'Creation Date',
						sortable: true,
					},
					{
						contentRenderer: 'date',
						fieldName: 'modifiedDate',
						label: 'Modification Date',
						sortable: true,
					},
					{
						contentRenderer: 'status',
						fieldName: 'orderStatusInfo',
						label: 'Status',
					},
					{
						contentRenderer: 'status',
						fieldName: 'workflowStatusInfo',
						label: 'Workflow Status',
					},
				],
			},
			thumbnail: 'table',
		},
	],
};

const productsDataSetDisplayProps = {
	activeViewSettings: {},
	apiURL:
		'/o/headless-commerce-admin-catalog/v1.0/products/?nestedFields=skus%2Ccatalog',
	bulkActions: [
		{
			href: '/delete',
			icon: 'trash',
			label: 'Delete',
			method: 'delete',
		},
	],
	creationMenuItems: [
		{
			href: 'modal/url',
			label: 'Add Product',
			target: 'modal',
		},
	],
	filters: [
		{
			id: 'customFilterId',
			label: 'Custom Filter',
			moduleURL: '/custom/filter/module/url',
		},
		{
			id: 'createDate',
			label: 'Creation date',
			max: {
				day: today.getDate(),
				month: today.getMonth() + 1,
				year: today.getFullYear(),
			},
			min: {
				day: today.getDate(),
				month: today.getMonth() + 1,
				year: today.getFullYear() - 10,
			},
			placeholder: 'dd/mm/yyyy',
			type: 'dateRange',
		},
		{
			apiURL:
				'/o/headless-admin-taxonomy/v1.0/taxonomy-categories/0/taxonomy-categories',
			id: 'categoryIds',
			inputPlaceholder: 'Search for Category',
			itemKey: 'id',
			itemLabel: 'name',
			label: 'Category',
			type: 'autocomplete',
		},
		{
			apiURL: '/o/headless-commerce-admin-catalog/v1.0/catalogs',
			id: 'catalogId',
			inputPlaceholder: 'Search for Catalog',
			itemKey: 'id',
			itemLabel: 'name',
			label: 'Catalog',
			selectionType: 'single',
			type: 'autocomplete',
		},
		{
			id: 'productType',
			items: [
				{
					label: 'Simple',
					value: 'simple',
				},
				{
					label: 'Multiple',
					value: 'multiple',
				},
			],
			label: 'Product Type',
			operator: 'eq',
			type: 'radio',
		},
	],
	id: 'tableTest',
	inlineAddingSettings: {
		apiURL: '/o/fake-new-inline-item-endpoint',
		defaultBodyContent: {
			testKey: 'testValue',
		},
		method: 'POST',
	},
	inlineEditingSettings: true,
	itemsActions: [
		{
			href: '/page/{id}',
			icon: 'view',
			id: 'view',
			label: 'View',
			permissionKey: 'get',
		},
		{
			href:
				'/o/headless-commerce-admin-catalog/v1.0/products/{productId}',
			icon: 'trash',
			id: 'delete',
			label: 'Delete',
			method: 'delete',
			permissionKey: 'delete',
			target: 'async',
		},
	],
	pageSize: 5,
	pagination: {
		deltas: [
			{
				label: 5,
			},
			{
				label: 10,
			},
			{
				label: 20,
			},
			{
				label: 30,
			},
			{
				label: 50,
			},
			{
				href:
					'http://localhost:8080/group/test-1/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_delta=75',
				label: 75,
			},
		],
		initialDelta: 10,
	},
	selectedItemsKey: 'id',
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	sorting: [
		{
			direction: 'desc',
			key: 'modifiedDate',
		},
	],
	spritemap: './assets/clay/icons.svg',
	views: [
		{
			contentRenderer: 'table',
			label: 'Table',
			schema: {
				fields: [
					{
						contentRenderer: 'image',
						fieldName: 'thumbnail',
						hideColumnLabel: true,
						label: 'Thumbnail',
					},
					{
						actionId: 'view',
						contentRenderer: 'actionLink',
						fieldName: ['name', 'LANG'],
						inlineEditSettings: {
							type: 'text',
						},
						label: 'Name',
						sortable: true,
					},
					{
						fieldName: 'productType',
						label: 'Product Type',
					},
					{
						contentRenderer: 'list',
						details: {
							multipleItemsLabel: 'Multiple-skus',
						},
						fieldName: 'skus',
						label: 'Sku',
						labelKey: 'sku',
					},
					{
						fieldName: ['catalog', 'name'],
						label: 'Catalog',
					},
					{
						contentRenderer: 'date',
						fieldName: 'createDate',
						inlineEditSettings: {
							type: 'dateTime',
						},
						label: 'Created Date',
						sortable: true,
					},
					{
						contentRenderer: 'date',
						fieldName: 'modifiedDate',
						label: 'Modified Date',
						sortable: true,
					},
					{
						contentRenderer: 'status',
						fieldName: 'workflowStatusInfo',
						label: 'Status',
					},
					{
						contentRenderer: 'boolean',
						fieldName: 'active',
						label: 'Active',
					},
				],
			},
			thumbnail: 'table',
		},
	],
};

const priceListsDataSetDisplayProps = {
	activeViewSettings: {},
	apiURL: '/o/headless-commerce-admin-pricing/v2.0/price-lists',
	enableInlineEditMode: false,
	id: 'tableTest',
	inlineAddingSettings: {
		apiURL: '/o/fake-new-inline-item-endpoint',
		defaultBodyContent: {
			testKey: 'testValue',
		},
		method: 'POST',
	},
	inlineEditingSettings: {
		alwaysOn: true,
	},
	itemsActions: [
		{
			href: '/page/{id}',
			icon: 'view',
			id: 'view',
			label: 'View',
			permissionKey: 'get',
		},
		{
			href:
				'/o/headless-commerce-admin-catalog/v1.0/products/{productId}',
			icon: 'trash',
			id: 'delete',
			label: 'Delete',
			method: 'delete',
			permissionKey: 'delete',
			target: 'async',
		},
	],
	pageSize: 5,
	pagination: {
		deltas: [
			{
				label: 5,
			},
			{
				label: 10,
			},
			{
				label: 20,
			},
			{
				label: 30,
			},
			{
				label: 50,
			},
			{
				href:
					'http://localhost:8080/group/test-1/pending-orders?p_p_id=com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&_com_liferay_commerce_order_content_web_internal_portlet_CommerceOpenOrderContentPortlet_delta=75',
				label: 75,
			},
		],
		initialDelta: 10,
	},
	selectedItemsKey: 'id',
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/clay/icons.svg',
	views: [
		{
			contentRenderer: 'table',
			label: 'Table',
			schema: {
				fields: [
					{
						actionId: 'view',
						contentRenderer: 'actionLink',
						fieldName: 'name',
						inlineEditSettings: {
							type: 'text',
						},
						label: 'Name',
						sortable: true,
					},
					{
						contentRenderer: 'date',
						fieldName: 'createDate',
						inlineEditSettings: {
							type: 'dateTime',
						},
						label: 'Created Date',
						sortable: true,
					},
					{
						contentRenderer: 'date',
						fieldName: 'displayDate',
						inlineEditSettings: {
							type: 'dateTime',
						},
						label: 'Display Date',
						sortable: true,
					},
					{
						contentRenderer: 'status',
						fieldName: 'workflowStatusInfo',
						label: 'Status',
					},
					{
						contentRenderer: 'boolean',
						fieldName: 'active',
						inlineEditSettings: {
							type: 'checkbox',
						},
						label: 'Active',
					},
				],
			},
			thumbnail: 'table',
		},
	],
};

datasetDisplayLauncher(
	ordersDataSetDisplayProps,
	document.getElementById('orders-dataset-display-root')
);

datasetDisplayLauncher(
	productsDataSetDisplayProps,
	document.getElementById('products-dataset-display-root')
);

datasetDisplayLauncher(
	priceListsDataSetDisplayProps,
	document.getElementById('price-list-dataset-display-root')
);

datasetDisplayLauncher(
	fluidDataSetDisplayProps,
	document.getElementById('fluid-dataset-display-root')
);

datasetDisplayLauncher(
	selectableTableProps,
	document.getElementById('selectable-dataset-display-root')
);

datasetDisplayLauncher(
	emailsDataSetDisplayProps,
	document.getElementById('emails-dataset-display-root')
);

sidePanelLauncher('sidePanel', 'side-panel-root', {
	containerSelector: '.container',
	id: 'sidePanelTestId',
	items: [
		{
			href: '/side-panel/comments.html',
			icon: 'comments',
			slug: 'comments',
		},
		{
			href: '/side-panel/edit.html',
			icon: 'pencil',
			slug: 'edit',
		},
		{
			href: '/side-panel/changelog.html',
			icon: 'restore',
			slug: 'changelog',
		},
	],
	size: 'md',
	spritemap: './assets/clay/icons.svg',
	topAnchorSelector: '.top-anchor',
});
