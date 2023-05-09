/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

interface IObject {
	[key: string]: {
		[key: string]: number;
	};
}

export const partnerLevelProperties: IObject = {
	gold: {
		growthARR: 125000,
		newProjectExistingBusiness: 2,
		partnerMarketingUser: 1,
		partnerSalesUser: 3,
	},
	platinum: {
		partnerMarketingUser: 1,
		partnerSalesUser: 5,
	},
	silver: {
		partnerMarketingUser: 1,
		partnerSalesUser: 1,
	},
};

export const mdf = {
	ProgressClain: {
		approved: {qtd: 120, total: 'USD $6.500,50'},
		pending: {qtd: 100, total: 'USD $5.500,00'},
	},
	ProgressMdf: {
		approved: {qtd: 300, total: 'USD $80.000,29'},
		pending: {qtd: 92, total: 'USD $12.993,00'},
	},
};
