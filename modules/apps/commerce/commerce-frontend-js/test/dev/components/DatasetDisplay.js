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

import datasetDisplayLauncher from '../../../src/main/resources/META-INF/resources/components/dataset_display/entry';
import sidePanelLauncher from '../../../src/main/resources/META-INF/resources/components/side_panel/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

const lang_id = themeDisplay.getLanguageId();

const fluidDataSetDisplayProps = {
	activeView: 2,
	apiUrl: '/dataset-display-nested-items',
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
			href: 'modal/url',
			label: 'Add via modal',
			target: 'modal'
		}
	],
	filters: [
		{
			id: 'number-test',
			inputText: '$',
			label: 'Number test',
			max: 200,
			min: 20,
			operator: 'eq',
			type: 'number',
			value: 123
		},
		{
			id: 'shipment-date',
			label: 'Shipment date',
			max: {
				day: 2,
				month: 9,
				year: 2020
			},
			min: {
				day: 14,
				month: 6,
				year: 2020
			},
			placeholder: 'dd/mm/yyyy',
			type: 'date',
			value: {
				day: 18,
				month: 7,
				year: 2020
			}
		},
		{
			id: 'order-date',
			label: 'Order range',
			max: {
				day: 2,
				month: 9,
				year: 2026
			},
			min: {
				day: 14,
				month: 6,
				year: 2020
			},
			placeholder: 'dd/mm/yyyy',
			type: 'dateRange',
			value: {
				from: {
					day: 18,
					month: 7,
					year: 2020
				},
				to: {
					day: 18,
					month: 7,
					year: 2025
				}
			}
		}
	],
	id: 'tableTest',
	nestedItemsKey: 'skuId',
	nestedItemsReferenceKey: 'testSubItems',
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
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/icons.svg',
	style: 'fluid',
	views: [
		{
			contentRenderer: 'cards',
			icon: 'documents-and-media',
			label: 'Cards',
			schema: {
				description: 'name',
				href: 'productPage',
				imgProps: 'img',
				labels: 'status',
				stickerProps: 'type',
				title: 'skuId'
			}
		},
		{
			component: props => {
				return (
					<>
						<h4 className="p-3 mb-0 bg-dark text-center text-white">
							Hey, I&apos;m a custom template from the outside
						</h4>
						{props.items.map(item => (
							<div
								className="p-3 text-center bg-white"
								key={item.skuId}
							>
								<strong className="mr-3">{item.skuId}</strong>
								{item.name}
							</div>
						))}
					</>
				);
			},
			icon: 'merge',
			id: 'custom-table',
			label: "Hey you don't know me",
			schema: {}
		},
		{
			contentRenderer: 'table',
			icon: 'table',
			label: 'Table',
			schema: {
				fields: [
					{
						contentRenderer: 'image',
						fieldName: 'img',
						label: ''
					},
					{
						contentRenderer: 'actionLink',
						fieldName: 'name',
						label: 'Name',
						sortable: true
					},
					{
						actionId: 'edit',
						contentRenderer: 'actionLink'
					},
					{
						actionId: 'delete',
						contentRenderer: 'actionLink'
					},
					{
						actionId: 'alert',
						contentRenderer: 'actionLink'
					},
					{
						actionId: 'select',
						contentRenderer: 'actionLink'
					},
					{
						contentRenderer: 'tooltipPrice',
						fieldName: 'price',
						label: 'Price'
					},
					{
						contentRenderer: 'quantitySelector',
						fieldName: 'testQuantity',
						label: 'Qt. Selector'
					}
				]
			}
		}
	]
};

const emailsDataSetDisplayProps = {
	apiUrl: '/dataset-display-email-data',
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
	id: 'emailsDatasetDIsplay',
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
		initialDelta: 10
	},
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/icons.svg',
	style: 'stacked',
	views: [
		{
			contentRenderer: 'emailsList',
			icon: 'email',
			label: 'Email'
		}
	]
};

const selectableTableProps = {
	apiUrl: '/dataset-display-selectable-data',
	formId: 'form-id',
	id: 'tableTest',
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
		initialDelta: 10
	},
	selectedItemsKey: 'countryId',
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/icons.svg',
	views: [
		{
			contentRenderer: 'selectableTable',
			icon: 'table',
			label: 'Table',
			schema: {
				firstColumnLabel: 'Country',
				firstColumnName: 'countryName'
			}
		}
	]
};

const headlessDataSetDisplayProps = {
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
		initialDelta: 10
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

datasetDisplayLauncher(
	'headless-dataset-display',
	'headless-dataset-display-root',
	headlessDataSetDisplayProps
);

datasetDisplayLauncher(
	'fluid-dataset-display',
	'fluid-dataset-display-root',
	fluidDataSetDisplayProps
);

datasetDisplayLauncher(
	'selectable-dataset-display',
	'selectable-dataset-display-root',
	selectableTableProps
);

datasetDisplayLauncher(
	'emails-dataset-display',
	'emails-dataset-display-root',
	emailsDataSetDisplayProps
);

sidePanelLauncher('sidePanel', 'side-panel-root', {
	containerSelector: '.container',
	id: 'sidePanelTestId',
	items: [
		{
			href: '/side-panel/comments.html',
			icon: 'comments',
			slug: 'comments'
		},
		{
			href: '/side-panel/edit.html',
			icon: 'pencil',
			slug: 'edit'
		},
		{
			href: '/side-panel/changelog.html',
			icon: 'restore',
			slug: 'changelog'
		}
	],
	size: 'md',
	spritemap: './assets/icons.svg',
	topAnchorSelector: '.top-anchor'
});
