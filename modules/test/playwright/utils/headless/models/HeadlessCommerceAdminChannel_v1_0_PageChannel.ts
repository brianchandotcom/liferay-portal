/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_Channel} from './HeadlessCommerceAdminChannel_v1_0_Channel';
import type {HeadlessCommerceAdminChannel_v1_0_Facet} from './HeadlessCommerceAdminChannel_v1_0_Facet';
export type HeadlessCommerceAdminChannel_v1_0_PageChannel = {
	items?: Array<HeadlessCommerceAdminChannel_v1_0_Channel>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminChannel_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
