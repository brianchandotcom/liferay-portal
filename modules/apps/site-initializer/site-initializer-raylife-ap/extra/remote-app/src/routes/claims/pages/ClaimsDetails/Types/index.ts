/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type ClaimType = {
	claimAmount: number;
	claimCreateDate: string;
	claimStatus: {key: string; name: string};
	dataJSON: string;
	id: number;
	incidentDate: string;
	r_policyToClaims_c_raylifePolicy: {
		r_quoteToPolicies_c_raylifeQuote: {
			r_applicationToQuotes_c_raylifeApplication: {
				email: string;
				firstName: string;
				lastName: string;
				phone: string;
			};
		};
	};
	r_policyToClaims_c_raylifePolicyERC?: string;
	r_policyToClaims_c_raylifePolicyId?: number;
	settledDate?: string;
};

export type ClaimDetailDataType = {
	data?: string | number;
	greenColor?: boolean;
	icon?: boolean;
	key: string;
	redirectTo?: string;
	text: string;
	type?: string;
};

export type ClaimStatusType = {
	claimStatus: string;
};

export type ClaimActivitiesDataType = {
	activity: string;
	body?: boolean;
	by: string;
	date: string;
	message: string;
};

export type ClaimComponentsType = {
	claimData: ClaimType;
	incidentDate?: string;
	isClaimSettled?: boolean;
};
