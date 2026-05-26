/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../utils/getRandomString';
import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

export type InsightTypeInput = {
	category: string;
	name: string;
	pageURLs: string[];
	severity: string;
};

export type CreatedInsightType = {
	id: number;
	name: string;
};

export type CreatedScan = {
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
		scanId: number,
		insightTypes: InsightTypeInput[]
	): Promise<CreatedInsightType[]> {
		const createdInsightTypes: CreatedInsightType[] = [];

		for (const insightTypeInput of insightTypes) {
			const insightType = await this._postInsightType(
				scanId,
				insightTypeInput
			);

			createdInsightTypes.push({
				id: insightType.id,
				name: insightTypeInput.name,
			});

			for (const pageURL of insightTypeInput.pageURLs) {
				const seoStudioPage = await this._postPage(scanId, pageURL);

				await this._postScanInsight(
					scanId,
					insightType.id,
					seoStudioPage.id
				);
			}
		}

		return createdInsightTypes;
	}

	async createScan(): Promise<CreatedScan> {
		const account = await this.apiHelpers.headlessAdminUser.postAccount({
			name: `seo-studio-test-${getRandomString()}`,
		});

		const instance = await this._postInstance(account.id);

		const domain = await this._postDomain(instance.id);

		const scan = await this._postScan(domain.id);

		return {
			scanId: scan.id,
			teardown: async () => {
				await this.apiHelpers.delete(
					this._url(`instances/${instance.id}`)
				);
			},
		};
	}

	private async _postDomain(instanceId: number): Promise<{id: number}> {
		return this.apiHelpers.post(this._url('domains'), {
			data: {
				defaultScanScope: 'entireDomain',
				hostname: `${getRandomString()}.example.com`,
				name: getRandomString(),
				r_seoStudioInstanceToSEOStudioDomains_seoStudioInstanceId:
					instanceId,
			},
			failOnStatusCode: true,
		});
	}

	private async _postInsightType(
		scanId: number,
		input: InsightTypeInput
	): Promise<{id: number}> {
		return this.apiHelpers.post(this._url('insight-types'), {
			data: {
				category: input.category,
				name: input.name,
				r_seoStudioScanToSEOStudioInsightTypes_seoStudioScanId: scanId,
				severity: input.severity,
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

	private async _postPage(
		scanId: number,
		pageURL: string
	): Promise<{id: number}> {
		return this.apiHelpers.post(this._url('pages'), {
			data: {
				pageURL,
				r_seoStudioScanToSEOStudioPages_seoStudioScanId: scanId,
			},
			failOnStatusCode: true,
		});
	}

	private async _postScan(domainId: number): Promise<{id: number}> {
		return this.apiHelpers.post(this._url('scans'), {
			data: {
				r_seoStudioDomainToSEOStudioScans_seoStudioDomainId: domainId,
				requestDate: new Date().toISOString(),
				scanScope: 'entireDomain',
				scanType: 'full',
				state: 'completed',
				triggeredBy: 'manual',
			},
			failOnStatusCode: true,
		});
	}

	private async _postScanInsight(
		scanId: number,
		insightTypeId: number,
		pageId: number
	) {
		return this.apiHelpers.post(this._url('scan-insights'), {
			data: {
				classification: 'problem',
				detectedDate: new Date().toISOString(),
				r_seoStudioInsightTypeToScanInsights_seoStudioInsightTypeId:
					insightTypeId,
				r_seoStudioPageToSEOStudioScanInsights_seoStudioPageId: pageId,
				r_seoStudioScanToSEOStudioScanInsights_seoStudioScanId: scanId,
			},
			failOnStatusCode: true,
		});
	}

	private _url(path: string) {
		return `${this.apiHelpers.baseUrl}${this.basePath}${path}`;
	}
}
