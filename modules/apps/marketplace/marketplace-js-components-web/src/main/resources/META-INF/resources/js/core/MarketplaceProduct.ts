/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CloudUserProject, Product} from '../types';

export const Specifications = {
	CPU: 'cpu',
	LATEST_VERSION: 'latest-version',
	PRICE_MODEL: 'price-model',
	PUBLISHER_WEBSITE_URL: 'publisherwebsiteurl',
	RAM: 'ram',
	SUPPORT_EMAIL_ADDRESS: 'supportemailaddress',
	SUPPORT_PHONE: 'supportphone',
	TYPE: 'type',
};

enum PriceModel {
	FREE = 'Free',
	PAID = 'Paid',
}

export enum Vocabularies {
	APP_CATEGORY = 'marketplace app category',
	PLATFORM_OFFERING = 'marketplace liferay platform offering',
	EDITION = 'marketplace edition',
}

const productTypeIcons = {
	cloud: 'cloud',
	dxp: 'site-template',
};

export class MarketplaceProduct {
	constructor(private product: Product) {}

	get createDate() {
		return this.product.createDate;
	}

	get catalogName() {
		return this.product.catalogName;
	}

	get friendlyURL() {
		return this.product.urls.en_US;
	}

	getPurchasableSKUs() {
		return this.product.skus.filter(({purchasable}) => purchasable);
	}

	get specificationValues() {
		const _specifications = {} as typeof Specifications;

		for (const specificationKey in Specifications) {
			const _key = specificationKey as keyof typeof Specifications;

			_specifications[_key] = this.getSpecificationValue(
				Specifications[_key]
			);
		}

		return _specifications;
	}

	private getCategories(vocabulary: string) {
		return this.product.categories.filter(
			(category) => category.vocabulary === vocabulary
		);
	}

	public getProductImages() {
		return this.product.images
			.filter((image) => image.priority !== 0)
			.map((image) => image.src);
	}

	public getPrice() {
		const priceModel = this.getPriceModel();

		if (priceModel.toLowerCase() === PriceModel.FREE.toLowerCase()) {
			return PriceModel.FREE;
		}

		const [purchasableSKU] = this.getPurchasableSKUs();

		return purchasableSKU?.price?.priceFormatted;
	}

	public getAppCategories() {
		return this.getCategories(Vocabularies.APP_CATEGORY);
	}

	public getCloudResourceLabel(cloudUserProject: CloudUserProject) {
		let output = '';

		if (!cloudUserProject) {
			return output;
		}

		const round = (value: number) => {
			if (!value) {
				return 0;
			}

			return Math.floor(value);
		};

		output += `${cloudUserProject.environments.length} ${Liferay.Language.get('environment')}, `;
		output += `${round(cloudUserProject.rootProjectPlanUsage.cpu.free)} CPUs, `;
		output += `${round(cloudUserProject.rootProjectPlanUsage.memory.free / 1000)} GB RAM`;

		return output;
	}

	public getEditions() {
		return this.getCategories(Vocabularies.EDITION);
	}

	public hasEnoughResources(cloudUserProject: CloudUserProject) {
		if (!cloudUserProject) {
			return false;
		}

		if (!cloudUserProject.rootProjectPlanUsage.instance.free) {
			return false;
		}

		const cpuSpecification = Number(
			this.getSpecificationValue(Specifications.CPU, '0')
		);

		const ramSpecification = Number(
			this.getSpecificationValue(Specifications.RAM, '0')
		);

		if (
			cloudUserProject.rootProjectPlanUsage.cpu.free < cpuSpecification ||
			Math.floor(
				cloudUserProject.rootProjectPlanUsage.memory.free / 1000
			) < ramSpecification
		) {
			return false;
		}

		return true;
	}

	public getPlatformOfferings() {
		return this.getCategories(Vocabularies.PLATFORM_OFFERING);
	}

	public getProductType() {
		const type = this.getSpecificationValue(Specifications.TYPE);

		return {
			icon: (productTypeIcons as any)[type] || 'cog',
			label: `${type} App`,
			type,
		};
	}

	public getPriceModel() {
		return this.getSpecificationValue(Specifications.PRICE_MODEL, 'Free');
	}

	public getSpecification(specificationKey: string | typeof Specifications) {
		return this.product.productSpecifications.find(
			(specification) =>
				specification.specificationKey === specificationKey
		);
	}

	private getSpecificationValue(
		specificationKey: string | typeof Specifications,
		value = ''
	) {
		return this.getSpecification(specificationKey)?.value || value;
	}

	public get productImage() {
		return this.product.urlImage;
	}

	public getProductResourceLabel() {
		const cpuSpecification = this.getSpecificationValue(
			Specifications.CPU,
			'0'
		);

		const ramSpecification = this.getSpecificationValue(
			Specifications.RAM,
			'0'
		);

		return `${cpuSpecification}CPUs, ${ramSpecification}GB RAM`;
	}
}
