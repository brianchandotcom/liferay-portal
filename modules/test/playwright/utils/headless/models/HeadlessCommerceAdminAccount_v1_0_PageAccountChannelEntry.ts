/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry} from './HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry';
import type {HeadlessCommerceAdminAccount_v1_0_Facet} from './HeadlessCommerceAdminAccount_v1_0_Facet';
export type HeadlessCommerceAdminAccount_v1_0_PageAccountChannelEntry = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminAccount_v1_0_AccountChannelEntry>;
	facets?: Array<HeadlessCommerceAdminAccount_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
