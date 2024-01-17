/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel} from './HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel';
import type {HeadlessCommerceAdminChannel_v1_0_Facet} from './HeadlessCommerceAdminChannel_v1_0_Facet';
export type HeadlessCommerceAdminChannel_v1_0_PageAccountAddressChannel = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminChannel_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
