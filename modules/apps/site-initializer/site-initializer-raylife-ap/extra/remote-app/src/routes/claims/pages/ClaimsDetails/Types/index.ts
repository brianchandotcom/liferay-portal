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

export type ClaimType = {
	claimAmount: number;
	claimCreateDate: string;
	claimStatus: {key: string; name: string};
	dataJSON: string;
	id: number;
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
	r_policyToClaims_c_raylifePolicyERC: string;
	r_policyToClaims_c_raylifePolicyId: number;
	settledDate: string;
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
