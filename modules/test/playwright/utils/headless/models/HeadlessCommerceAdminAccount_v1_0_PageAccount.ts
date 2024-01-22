/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_Account} from './HeadlessCommerceAdminAccount_v1_0_Account';
import type {HeadlessCommerceAdminAccount_v1_0_Facet} from './HeadlessCommerceAdminAccount_v1_0_Facet';
export type HeadlessCommerceAdminAccount_v1_0_PageAccount = {
	items?: Array<HeadlessCommerceAdminAccount_v1_0_Account>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminAccount_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
