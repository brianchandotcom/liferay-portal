/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption} from './HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption';
import type {HeadlessCommerceAdminAccount_v1_0_Facet} from './HeadlessCommerceAdminAccount_v1_0_Facet';
export type HeadlessCommerceAdminAccount_v1_0_PageAccountChannelShippingOption =
	{
		totalCount?: number;
		lastPage?: number;
		items?: Array<HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption>;
		pageSize?: number;
		facets?: Array<HeadlessCommerceAdminAccount_v1_0_Facet>;
		page?: number;
		actions?: Record<string, Record<string, string>>;
	};
