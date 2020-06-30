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

import sidePanelLauncher from './../side_panel/entry.es';
import datasetDisplayLauncher from './entry.es';

import '../../styles/main.scss';

const fluidDataSetDisplayProps = {
	activeView: 2,
	apiUrl:
		'http://localhost:8080/o/commerce-ui/commerce-data-set/20124/commerceOrderItems/commerceOrderItems?plid=1&portletId=com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet&commerceOrderId=38938',
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
	items: [
		{
			actionItems: [
				{
					href: '/view/url',
					icon: 'view',
					id: 'view',
					label: 'View'
				},
				{
					href: '/select/url',
					icon: 'message-boards',
					id: 'select',
					label: 'Select',
					target: 'modal'
				},
				{
					href: '/delete/url',
					icon: 'trash',
					id: 'delete',
					label: 'Delete',
					method: 'delete',
					target: 'async'
				},
				{
					href: '/edit/url',
					icon: 'pencil',
					id: 'edit',
					label: 'Edit',
					target: 'sidePanel'
				},
				{
					icon: 'warning-full',
					id: 'alert',
					label: 'Alert',
					onClick: 'alert("asd")'
				}
			],
			id: 'asd',
			img: {
				src: '//via.placeholder.com/250x250'
			},
			name: 'ABS Sensor',
			price: {
				final: {
					value: '12 Gazillions'
				}
			},
			productPage: '/test/link/1',
			skuId: 35663,
			testLink: {
				href: '/test/link/1',
				label: 'Test 1'
			},
			testQuantity: {
				allowedQuantities: [3, 6, 7, 100],
				disabled: false,
				inputName: 'asd-quantity',
				quantity: 6
			}
		},
		{
			actionItems: [
				{
					href: '/view/url',
					icon: 'view',
					id: 'view',
					label: 'View'
				},
				{
					href: '/select/url',
					icon: 'message-boards',
					id: 'select',
					label: 'Select',
					target: 'modal'
				},
				{
					href: '/delete/url',
					icon: 'trash',
					id: 'delete',
					label: 'Delete',
					method: 'delete',
					target: 'async'
				},
				{
					href: '/edit/url',
					icon: 'pencil',
					id: 'edit',
					label: 'Edit',
					target: 'sidePanel'
				},
				{
					icon: 'warning-full',
					id: 'alert',
					label: 'Alert',
					onClick: 'alert("asd")'
				}
			],
			id: 'sdf',
			img: {
				src: '//via.placeholder.com/500x500'
			},
			name: 'SBA Sensor',
			price: {
				details: [
					{
						label: 'Catalog price',
						value: '$ 15'
					},
					{
						label: 'Final price',
						value: '$ 31.123'
					},
					{
						label: 'Promo price',
						value: '$ 15.600'
					},
					{
						label: 'Discounts',
						value: [40, 30, 20, 10]
					}
				],
				final: {
					label: 'Final price',
					value: '12.000 $'
				}
			},
			productPage: '/test/link/1',
			skuId: 345345,
			testLink: {
				href: '/test/link/1',
				label: 'Test 1'
			},
			testQuantity: {
				inputName: 'sdf-quantity',
				maxQuantity: 1000,
				minQuantity: 2,
				multipleQuantity: 2,
				quantity: 6
			},
			type: {
				content: 'DOC',
				displayType: 'danger'
			}
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

const dataSetDisplayProps = {
	activeView: 2,
	apiUrl:
		'http://localhost:8080/o/commerce-ui/commerce-data-set/20124/commerceOrderItems/commerceOrderItems?plid=1&portletId=com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet&commerceOrderId=38938',
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
			id: 'text-test',
			label: 'Text test',
			operator: 'eq',
			type: 'text',
			value: 'Test input'
		},
		{
			id: 'select-test',
			items: [
				{
					label: 'First option',
					value: 'first-option'
				},
				{
					label: 'Second option',
					value: 'second-option'
				}
			],
			label: 'Select test',
			operator: 'eq',
			type: 'select',
			value: 'second-option'
		},
		{
			id: 'radio-test',
			items: [
				{
					label: 'First option',
					value: 'first-option'
				},
				{
					label: 'Second option',
					value: 'second-option'
				}
			],
			label: 'Radio test',
			operator: 'eq',
			type: 'radio'
		},
		{
			id: 'checkbox-test',
			items: [
				{
					label: 'First option',
					value: 'first-option'
				},
				{
					label: 'Second option',
					value: 'second-option'
				},
				{
					label: 'Third option',
					value: 'third-option'
				}
			],
			label: 'Checkbox test',
			operator: 'eq',
			type: 'checkbox',
			value: ['first-option', 'third-option']
		},
		{
			id: 'number-test',
			inputText: '$',
			label: 'Number test',
			max: 200,
			min: 20,
			operator: 'gt',
			type: 'number',
			value: 123
		}
	],
	formId: 'form-id',
	id: 'tableTest',
	items: [
		{
			actionItems: [
				{
					href: '/delete/url',
					id: 'delete',
					label: 'Delete',
					method: 'delete',
					target: 'async'
				}
			],
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 200,
			id: 37175,
			name: 'ABS Sensor',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 4,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN93015'
			},
			skuExternalReferenceCode: 'min93015',
			skuId: 35663,
			status: {
				displayStyle: 'info',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 50
		},
		{
			actionItems: [
				{
					href: '/delete/url',
					icon: 'trash',
					id: 'delete',
					label: 'Delete',
					method: 'delete',
					target: 'async'
				},
				{
					href: '/edit/url',
					icon: 'pencil',
					id: 'edit',
					label: 'Edit'
				}
			],
			bookedQuantityId: 0,
			comments: {
				name: 'Square pls',
				quantity: "This is a test! I don't like this number btw"
			},
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 304,
			id: 37176,
			name: 'Ball Joints',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 2,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN38794'
			},
			skuExternalReferenceCode: 'min38794',
			skuId: 36456,
			status: {
				displayStyle: 'secondary',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 152
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 70,
			id: 37177,
			name: 'Bearings',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 1,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN00673'
			},
			skuExternalReferenceCode: 'min00673',
			skuId: 36114,
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 70
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 37.8,
			id: 37178,
			name: 'Brake Pads',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 2,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN93018'
			},
			skuExternalReferenceCode: 'min93018',
			skuId: 35798,
			status: {
				displayStyle: 'success',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 21
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 400,
			id: 37197,
			name: 'Brake Rotors',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 10,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN93020'
			},
			skuExternalReferenceCode: 'min93020',
			skuId: 35872,
			status: {
				displayStyle: 'warning',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 40
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 36,
			id: 37198,
			name: 'Bushings',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 2,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN38795'
			},
			skuExternalReferenceCode: 'min38795',
			skuId: 36474,
			status: {
				displayStyle: 'danger',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 18
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 90,
			id: 37199,
			name: 'Calipers',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 1,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN93021',
				size: 'lg'
			},
			skuExternalReferenceCode: 'min93021',
			skuId: 35900,
			status: {
				displayStyle: 'success',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 90
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 4170,
			id: 37200,
			name: 'Cams',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 6,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN00674',
				size: 'sm'
			},
			skuExternalReferenceCode: 'min00674',
			skuId: 36132,
			status: {
				displayStyle: 'success',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 695
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 624,
			id: 37201,
			name: 'Coil Spring - Rear',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 6,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN38799'
			},
			skuExternalReferenceCode: 'min38799',
			skuId: 36553,
			status: {
				displayStyle: 'success',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 104
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 623,
			id: 37202,
			name: 'CV Axles',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 7,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN38796'
			},
			skuExternalReferenceCode: 'min38796',
			skuId: 36492,
			status: {
				displayStyle: 'success',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 89
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 1068,
			id: 37203,
			name: 'CV Axles',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 12,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN55853'
			},
			skuExternalReferenceCode: 'min55853',
			skuId: 36700,
			status: {
				displayStyle: 'success',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 89
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 10550,
			id: 37204,
			name: 'Differential Ring and Pinion - Universal',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 50,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN38801'
			},
			skuExternalReferenceCode: 'min38801',
			skuId: 36604,
			status: {
				displayStyle: 'success',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 211
		},
		{
			bookedQuantityId: 0,
			date: {
				href: '/modal/date/url',
				icon: 'date'
			},
			discountAmount: 0,
			discountPercentageLevel1: 0,
			discountPercentageLevel2: 0,
			discountPercentageLevel3: 0,
			discountPercentageLevel4: 0,
			finalPrice: 396,
			id: 37205,
			name: 'Drive Shafts',
			order: {
				href: '/modal/order/url',
				label: '#37174'
			},
			quantity: 1,
			shippedQuantity: 0,
			shippingAddress: {},
			shippingAddressId: 0,
			sku: {
				href: '/sidepanel-1.html',
				label: 'MIN55855'
			},
			skuExternalReferenceCode: 'min55855',
			skuId: 36744,
			status: {
				displayStyle: 'success',
				label: 'delivered'
			},
			subscription: false,
			thumbnail: {
				alt: 'ABS Sensor',
				shape: 'rounded',
				size: 'lg',
				src: 'https://via.placeholder.com/150'
			},
			unitPrice: 396
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
	selectedItemsKey: 'id',
	selectionType: 'single',
	showPagination: true,
	sidePanelId: 'sidePanelTestId',
	spritemap: './assets/icons.svg',
	views: [
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
			label: 'Custom list'
		},
		{
			contentRendererModuleUrl: '/fake/url',
			icon: 'code',
			label: 'JSON'
		},
		{
			contentRenderer: 'table',
			icon: 'table',
			label: 'Table',
			schema: {
				fields: [
					{
						contentRendererModuleUrl:
							'/fake/content/renderer/picture',
						// contentRenderer: 'image',
						expand: false,
						fieldName: 'thumbnail',
						label: ''
					},
					{
						contentRenderer: 'sidePanelLink',
						expand: true,
						fieldName: 'sku',
						label: 'SKU',
						sortable: true
					},
					{
						fieldName: 'name',
						label: 'Name',
						sortable: true
					},
					{
						fieldName: 'unitPrice',
						label: 'Price',
						sortable: true
					},
					{
						contentRenderer: 'modalLink',
						fieldName: 'order',
						label: 'Order'
					},
					{
						contentRenderer: 'label',
						fieldName: 'status',
						label: 'Status'
					},
					{
						fieldName: 'quantity',
						label: 'Quantity',
						sortable: true
					},
					{
						fieldName: 'finalPrice',
						label: 'Total',
						sortable: false
					},
					{
						contentRenderer: 'modalLink',
						fieldName: 'date'
					}
				]
			}
		},
		{
			activeItemValue: 36553,
			contentRenderer: 'list',
			icon: 'list',
			label: 'List',
			schema: {
				description: 'name',
				thumbnail: 'thumbnail',
				title: 'skuId'
			}
		}
	]
};

const emailsDataSetDisplayProps = {
	apiUrl:
		'http://localhost:8080/o/commerce-ui/commerce-data-set/20124/commerceOrderItems/commerceOrderItems?plid=1&portletId=com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet&commerceOrderId=38938',
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
	items: [
		{
			actionItems: [
				{
					href: '/delete/action/url',
					icon: 'trash',
					label: 'Delete'
				}
			],
			author: {
				avatarSrc: 'https://via.placeholder.com/150',
				email: 'john.doe@gmail.com',
				name: 'John Doe'
			},
			date: '1 day ago',
			href: '/side-panel/email.html',
			status: {
				displayStyle: 'danger',
				label: 'Order not placed'
			},
			subject:
				'Mauris blandit aliquet elit, eget tincidunt nibh pulvinar.',
			summary:
				'Pellentesque in ipsum id orci porta dapibus. Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus. Nulla quis lorem ut libero malesuada feugiat. Pellentesque in ipsum id orci porta dapibus...'
		},
		{
			actionItems: [
				{
					href: '/delete/action/url',
					icon: 'trash',
					label: 'Delete'
				}
			],
			author: {
				avatarSrc: 'https://via.placeholder.com/150',
				email: 'john.doe@gmail.com',
				name: 'John Doe'
			},
			date: '14th April 2018',
			href: '/side-panel/email.html',
			status: {
				displayStyle: 'success',
				label: 'Order placed'
			},
			subject:
				'Curabitur aliquet quam id dui posuere blandit. Proin eget tortor risus.',
			summary:
				'Cras ultricies ligula sed magna dictum porta. Donec rutrum congue leo eget malesuada. Proin eget tortor risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Quisque velit nisi, pretium ut lacinia in, elementum id enim...'
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
	apiUrl:
		'http://localhost:8080/o/commerce-ui/commerce-data-set/20124/commerceOrderItems/commerceOrderItems?plid=1&portletId=com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet&commerceOrderId=38938',
	formId: 'form-id',
	id: 'tableTest',
	items: [
		{
			countryId: '001',
			countryName: 'United States',
			fields: [
				{
					label: 'Money Order',
					name: 'autorizeDotNet',
					value: false
				},
				{
					label: 'Money Order',
					name: 'moneyOrder',
					value: false
				},
				{
					label: 'PayPal',
					name: 'payPal',
					value: false
				}
			]
		},
		{
			countryId: '002',
			countryName: 'Afghanistan',
			fields: [
				{
					label: 'Money Order',
					name: 'autorizeDotNet',
					value: true
				},
				{
					label: 'Money Order',
					name: 'moneyOrder',
					value: false
				},
				{
					label: 'PayPal',
					name: 'payPal',
					value: false
				}
			]
		},
		{
			countryId: '003',
			countryName: 'Albania',
			fields: [
				{
					label: 'Money Order',
					name: 'autorizeDotNet',
					value: false
				},
				{
					label: 'Money Order',
					name: 'moneyOrder',
					value: true
				},
				{
					label: 'PayPal',
					name: 'payPal',
					value: false
				}
			]
		},
		{
			countryId: '004',
			countryName: 'Algeria',
			fields: [
				{
					label: 'Money Order',
					name: 'autorizeDotNet',
					value: false
				},
				{
					label: 'Money Order',
					name: 'moneyOrder',
					value: false
				},
				{
					label: 'PayPal',
					name: 'payPal',
					value: false
				}
			]
		},
		{
			countryId: '005',
			countryName: 'American Samoa',
			fields: [
				{
					label: 'Money Order',
					name: 'autorizeDotNet',
					value: true
				},
				{
					label: 'Money Order',
					name: 'moneyOrder',
					value: false
				},
				{
					label: 'PayPal',
					name: 'payPal',
					value: true
				}
			]
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

datasetDisplayLauncher(
	'fluid-dataset-display',
	'fluid-dataset-display-root-id',
	fluidDataSetDisplayProps
);

datasetDisplayLauncher(
	'selectable-dataset-display',
	'selectable-dataset-display-root-id',
	selectableTableProps
);

datasetDisplayLauncher(
	'emails-dataset-display',
	'emails-dataset-display-root-id',
	emailsDataSetDisplayProps
);

datasetDisplayLauncher(
	'dataset-display',
	'dataset-display-root-id',
	dataSetDisplayProps
);

sidePanelLauncher('sidePanel', 'side-panel-root-id', {
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
