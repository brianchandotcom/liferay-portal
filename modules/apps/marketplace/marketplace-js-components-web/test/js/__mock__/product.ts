/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Image} from '../../../src/main/resources/META-INF/resources/js/types';
import categories from './categories';
import productSpecifications from './productSpecifications';
import skus from './skus';

const product: any = {
	catalogId: 100,
	catalogName: 'Liferay Labs',
	categories,
	createDate: '2025-01-01',
	customFields: [],
	description: 'product description',
	expando: {},
	externalReferenceCode: '',
	id: 0,
	images: [
		{
			priority: 1,
			src: 'image1.png',
		},
	] as Image[],
	metaDescription: '',
	metaKeyword: '',
	metaTitle: '',
	modifiedDate: '',
	name: 'Minium Theme',
	productConfiguration: {
		allowBackOrder: false,
		allowedOrderQuantities: [],
		availabilityEstimateId: 0,
		inventoryEngine: '',
		maxOrderQuantity: 0,
		minOrderQuantity: 0,
		multipleOrderQuantity: 0,
	},
	productId: 0,
	productSpecifications,
	productType: 'virtual',
	shortDescription: 'this is a short description of the app',
	skus,
	slug: '',
	tags: [],
	urlImage: 'https://liferay.com/liferay-icon.png',
	urls: {en_US: 'friendly-url'},
};

export default product;
