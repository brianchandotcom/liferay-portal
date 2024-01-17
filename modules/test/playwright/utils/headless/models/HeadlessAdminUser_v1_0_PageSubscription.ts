/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Facet} from './HeadlessAdminUser_v1_0_Facet';
import type {HeadlessAdminUser_v1_0_Subscription} from './HeadlessAdminUser_v1_0_Subscription';
export type HeadlessAdminUser_v1_0_PageSubscription = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessAdminUser_v1_0_Subscription>;
	facets?: Array<HeadlessAdminUser_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
