/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../utils/getRandomString';
import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

export type PageData = {
	author?: string;
	pageURL: string;
	title?: string;
	type?: string;
};

export type InsightType = {
	category: string;
	description?: string;
	fixHint?: string;
	id?: number;
	name: string;
	pageURLs: Array<PageData | string>;
	severity: string;
	whyItMatters?: string;
};

export type Scan = {
	accountId: number;
	scanId: number;
	teardown: () => Promise<void>;
};

export class SEOStudioApiHelper {
	readonly apiHelpers: ApiHelpers | DataApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers | DataApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'seo-studio/';
	}

	async createInsights(
		scan: Scan,
		insightTypes: InsightType[]
	): Promise<InsightType[]> {
		const createdInsightTypes: InsightType[] = [];

		for (const insightTypeInput of insightTypes) {
			const insightType = await this._postInsightType(
				scan,
				insightTypeInput
			);

			createdInsightTypes.push({
				...insightTypeInput,
				id: insightType.id,
			});

			for (const pageInput of insightTypeInput.pageURLs) {
				const pageData: PageData =
					typeof pageInput === 'string'
						? {pageURL: pageInput}
						: pageInput;

				const seoStudioPage = await this._postPage(scan, pageData);

				await this._postScanInsight(
					scan,
					insightType.id,
					seoStudioPage.id
				);
			}
		}

		return createdInsightTypes;
	}

	async createScan(scanType: string): Promise<Scan> {
		const account = await this.apiHelpers.headlessAdminUser.postAccount({
			name: `seo-studio-test-${getRandomString()}`,
		});

		const instance = await this._postInstance(account.id);

		const domain = await this._postDomain(account.id, instance.id);

		const scan = await this._postScan(account.id, domain.id, scanType);

		return {
			accountId: account.id,
			scanId: scan.id,
			teardown: async () => {
				await this.apiHelpers.headlessAdminUser.deleteAccount(
					account.id
				);
			},
		};
	}

	private async _postDomain(
		accountId: number,
		instanceId: number
	): Promise<{id: number}> {
		return this.apiHelpers.post(this._url('domains'), {
			data: {
				defaultScanScope: 'entireDomain',
				hostname: `${getRandomString()}.example.com`,
				name: getRandomString(),
				r_accountToSEOStudioDomains_accountEntryId: accountId,
				r_seoStudioInstanceToSEOStudioDomains_seoStudioInstanceId:
					instanceId,
			},
			failOnStatusCode: true,
		});
	}

	private async _postInsightType(
		scan: Scan,
		input: InsightType
	): Promise<{id: number}> {
		return this.apiHelpers.post(this._url('insight-types'), {
			data: {
				category: input.category,
				description: input.description,
				fixHint: input.fixHint,
				name: input.name,
				r_accountToSEOStudioInsightTypes_accountEntryId: scan.accountId,
				r_seoStudioScanToSEOStudioInsightTypes_seoStudioScanId:
					scan.scanId,
				severity: input.severity,
				whyItMatters: input.whyItMatters,
			},
			failOnStatusCode: true,
		});
	}

	private async _postInstance(
		accountId: number
	): Promise<{externalReferenceCode: string; id: number}> {
		return this.apiHelpers.post(this._url('instances'), {
			data: {
				clientId: getRandomString(),
				clientSecret: getRandomString(),
				hostname: `${getRandomString()}.example.com`,
				name: getRandomString(),
				r_accountToSEOStudioInstances_accountEntryId: accountId,
				state: 'active',
			},
			failOnStatusCode: true,
		});
	}

	private async _postPage(scan: Scan, page: PageData): Promise<{id: number}> {
		return this.apiHelpers.post(this._url('pages'), {
			data: {
				author: page.author,
				pageURL: page.pageURL,
				r_accountToSEOStudioPages_accountEntryId: scan.accountId,
				r_seoStudioScanToSEOStudioPages_seoStudioScanId: scan.scanId,
				title: page.title,
				type: page.type,
			},
			failOnStatusCode: true,
		});
	}

	private async _postScan(
		accountId: number,
		domainId: number,
		scanType: string
	): Promise<{id: number}> {
		return this.apiHelpers.post(this._url('scans'), {
			data: {
				name: `${scanType} scan`,
				r_accountToSEOStudioScans_accountEntryId: accountId,
				r_seoStudioDomainToSEOStudioScans_seoStudioDomainId: domainId,
				requestDate: new Date().toISOString(),
				scanRange: 'full',
				scanScope: 'entireDomain',
				scanType,
				state: 'completed',
				triggeredBy: 'manual',
			},
			failOnStatusCode: true,
		});
	}

	private async _postScanInsight(
		scan: Scan,
		insightTypeId: number,
		pageId: number
	) {
		return this.apiHelpers.post(this._url('scan-insights'), {
			data: {
				classification: 'problem',
				detectedDate: new Date().toISOString(),
				r_accountToSEOStudioScanInsights_accountEntryId: scan.accountId,
				r_seoStudioInsightTypeToScanInsights_seoStudioInsightTypeId:
					insightTypeId,
				r_seoStudioPageToSEOStudioScanInsights_seoStudioPageId: pageId,
				r_seoStudioScanToSEOStudioScanInsights_seoStudioScanId:
					scan.scanId,
			},
			failOnStatusCode: true,
		});
	}

	private _url(path: string) {
		return `${this.apiHelpers.baseUrl}${this.basePath}${path}`;
	}
}
