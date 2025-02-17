/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceProduct} from '../../../src/main/resources/META-INF/resources/js/core/MarketplaceProduct';
import product from '../__mock__/product';

describe('MarketplaceProduct', () => {
	const cloudUserProject = {
		environments: [
			{
				isExtensionEnvironment: true,
				projectId: '1234',
			},
		],
		rootProjectId: '5678',
		rootProjectPlanUsage: {
			cpu: {
				free: 10,
				limit: 10,
				used: 0,
			},
			instance: {
				free: 10,
				limit: 10,
				used: 0,
			},
			memory: {
				free: 10000,
				limit: 10000,
				used: 0,
			},
		},
	};

	const marketplaceProduct = new MarketplaceProduct(product);

	it('will return specification values correctly', () => {
		expect(marketplaceProduct.catalogName).toBeTruthy();
		expect(marketplaceProduct.createDate).toBeTruthy();
		expect(marketplaceProduct.friendlyURL).toBeTruthy();
		expect(marketplaceProduct.productImage).toBeTruthy();
		expect(marketplaceProduct.specificationValues).toBeTruthy();

		const categories = marketplaceProduct.getAppCategories();

		expect(categories).toHaveLength(2);
		expect(categories[0].name).toBeTruthy();

		const editions = marketplaceProduct.getEditions();

		expect(editions).toHaveLength(2);
		expect(editions[0].name).toBeTruthy();

		const offerings = marketplaceProduct.getPlatformOfferings();

		expect(offerings).toHaveLength(2);
		expect(offerings[0].name).toBeTruthy();

		const productType = marketplaceProduct.getProductType();

		expect(productType).toBeTruthy();
		expect(marketplaceProduct.getProductResourceLabel()).toBeTruthy();

		const purchasableSKUs = marketplaceProduct.getPurchasableSKUs();

		expect(purchasableSKUs).toHaveLength(1);
		expect(purchasableSKUs[0].price.priceFormatted).toBeTruthy();
		expect(
			marketplaceProduct.getCloudResourceLabel(cloudUserProject)
		).toBeTruthy();
		expect(marketplaceProduct.getCloudResourceLabel(false)).toBeFalsy();

		cloudUserProject.rootProjectPlanUsage.cpu.free = 0;
		cloudUserProject.rootProjectPlanUsage.memory.free = 0;

		expect(
			marketplaceProduct.getCloudResourceLabel(cloudUserProject)
		).toBeTruthy();
		expect(marketplaceProduct.getPrice()).toBeTruthy();
		expect(marketplaceProduct.getProductImages()).toBeTruthy();
		expect(marketplaceProduct.getProductType()).toBeTruthy();
		expect(
			marketplaceProduct.hasEnoughResources(cloudUserProject)
		).toBeTruthy();
		expect(marketplaceProduct.hasEnoughResources(false)).toBeFalsy();

		cloudUserProject.rootProjectPlanUsage.instance.free = 0;

		expect(marketplaceProduct.hasEnoughResources(cloudUserProject)).toBe(
			false
		);

		cloudUserProject.rootProjectPlanUsage.memory.free = -1;
		cloudUserProject.rootProjectPlanUsage.instance.free = 1;

		expect(
			marketplaceProduct.hasEnoughResources(cloudUserProject)
		).toBeFalsy();
	});
});
