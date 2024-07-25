/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {TMDFRequestGoal} from '../../types/mdf';
import {EMDFRequestLiferayBusinessSalesGoals} from '../../utils/constants';

export class MDFRequestFormGoalsPage {
	readonly additionalOptions: {
		dxpMigrationUpgrade: Locator;
		migrationFromCompetitorPlatform: Locator;
	};
	readonly companyName: Locator;
	readonly liferayBusinessSalesGoals: {
		leadGeneration: Locator;
		nurtureExistingProspects: Locator;
		other: Locator;
		thoughtLeadership: Locator;
	};
	readonly liferayBusinessSalesGoalsOther: Locator;
	readonly overallCampaignDescription: Locator;
	readonly overallCampaignName: Locator;
	readonly page: Page;
	readonly targetAudienceRoles: {
		administrator: Locator;
		associateAnalyst: Locator;
		cLevelExecutiveVP: Locator;
		developerEngineer: Locator;
		directorManager: Locator;
		ecommerceLeadership: Locator;
		independentContractor: Locator;
		projectManager: Locator;
	};
	readonly targetMarkets: {
		aerospaceDefense: Locator;
		agriculture: Locator;
		automotive: Locator;
		constructionEngineering: Locator;
		consultingMarketResearch: Locator;
		education: Locator;
		energy: Locator;
		financialServices: Locator;
		foodServices: Locator;
		governmentFederal: Locator;
		governmentStateLocal: Locator;
		healthcare: Locator;
		hospitalityLeisure: Locator;
		insurance: Locator;
		manufacturing: Locator;
		mediaEntertainment: Locator;
		notForProfitNGO: Locator;
		pharmaceuticals: Locator;
		professionalServicesAgencyBusiness: Locator;
		professionalServicesTechnicalWebIT: Locator;
		retailConsumerProducts: Locator;
		technology: Locator;
		telecommunications: Locator;
		transportation: Locator;
		utilities: Locator;
		wholesaleDistribution: Locator;
	};

	constructor(page: Page) {
		this.additionalOptions = {
			dxpMigrationUpgrade: page.getByLabel(
				'6.x to DXP Migration/Upgrade'
			),
			migrationFromCompetitorPlatform: page.getByLabel(
				'Migration from competitor platform'
			),
		};
		this.companyName = page.locator('select[name="company"]');
		this.liferayBusinessSalesGoals = {
			leadGeneration: page.getByLabel('Lead generation'),
			nurtureExistingProspects: page.getByLabel(
				'Nurture existing prospects'
			),
			other: page.getByLabel('Other - Please describe'),
			thoughtLeadership: page.getByLabel('Thought leadership'),
		};
		this.liferayBusinessSalesGoalsOther = page.locator(
			'input[name="liferayBusinessSalesGoalsOther"]'
		);
		this.overallCampaignDescription = page.locator(
			'input[name="overallCampaignDescription"]'
		);
		this.overallCampaignName = page.locator(
			'input[name="overallCampaignName"]'
		);
		this.page = page;
		this.targetAudienceRoles = {
			administrator: page.getByLabel('Administrator'),
			associateAnalyst: page.getByLabel('Associate/Analyst'),
			cLevelExecutiveVP: page.getByLabel('C-Level/Executive/VP'),
			developerEngineer: page.getByLabel('Developer/Engineer'),
			directorManager: page.getByLabel('Director/Manager'),
			ecommerceLeadership: page.getByLabel('eCommerce Leadership'),
			independentContractor: page.getByLabel('Independent Contractor'),
			projectManager: page.getByLabel('Project Manager'),
		};
		this.targetMarkets = {
			aerospaceDefense: page.getByLabel('Aerospace & Defense'),
			agriculture: page.getByLabel('Agriculture'),
			automotive: page.getByLabel('Automotive'),
			constructionEngineering: page.getByLabel(
				'Construction/Engineering'
			),
			consultingMarketResearch: page.getByLabel(
				'Consulting/Market Research'
			),
			education: page.getByLabel('Education'),
			energy: page.getByLabel('Energy'),
			financialServices: page.getByLabel('Financial Services'),
			foodServices: page.getByLabel('Food Services'),
			governmentFederal: page.getByLabel('Government (Federal)'),
			governmentStateLocal: page.getByLabel('Government (State/Local)'),
			healthcare: page.getByLabel('Healthcare'),
			hospitalityLeisure: page.getByLabel('Hospitality/Leisure'),
			insurance: page.getByLabel('Insurance'),
			manufacturing: page.getByLabel('Manufacturing'),
			mediaEntertainment: page.getByLabel('Media/Entertainment'),
			notForProfitNGO: page.getByLabel('Not For Profit/NGO'),
			pharmaceuticals: page.getByLabel('Pharmaceuticals'),
			professionalServicesAgencyBusiness: page.getByLabel(
				'Professional Services (Agency/Business)'
			),
			professionalServicesTechnicalWebIT: page.getByLabel(
				'Professional Services (Technical/Web/IT)'
			),
			retailConsumerProducts: page.getByLabel('Retail/Consumer Products'),
			technology: page.getByLabel('Technology'),
			telecommunications: page.getByLabel('Telecommunications'),
			transportation: page.getByLabel('Transportation'),
			utilities: page.getByLabel('Utilities'),
			wholesaleDistribution: page.getByLabel('Wholesale/Distribution'),
		};
	}

	async selectCompany(companyName: string) {
		await this.companyName.selectOption({label: companyName});
	}

	async fillLiferayBusinessSalesGoalsOther(text: string | undefined) {
		await this.liferayBusinessSalesGoals.other.check();

		await expect(this.liferayBusinessSalesGoals.other).toBeChecked();

		await this.liferayBusinessSalesGoalsOther.fill(text || '');
	}

	async fillForm({
		additionalOptions,
		companyName,
		liferayBusinessSalesGoals,
		liferayBusinessSalesGoalsOther,
		overallCampaignDescription,
		overallCampaignName,
		targetAudienceRoles,
		targetMarkets,
	}: TMDFRequestGoal) {
		await this.selectCompany(companyName);

		await this.overallCampaignName.fill(overallCampaignName);

		await this.overallCampaignDescription.fill(overallCampaignDescription);

		for (const option of liferayBusinessSalesGoals) {
			if (option === EMDFRequestLiferayBusinessSalesGoals.OTHER) {
				this.fillLiferayBusinessSalesGoalsOther(
					liferayBusinessSalesGoalsOther
				);

				continue;
			}

			await this.liferayBusinessSalesGoals[option].check();
		}

		for (const option of targetMarkets) {
			await this.targetMarkets[option].check();
		}

		if (additionalOptions?.length) {
			for (const option of additionalOptions) {
				await this.additionalOptions[option].check();
			}
		}

		for (const option of targetAudienceRoles) {
			await this.targetAudienceRoles[option].check();
		}
	}
}
