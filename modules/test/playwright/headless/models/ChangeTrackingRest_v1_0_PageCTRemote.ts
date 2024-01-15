/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTRemote} from './ChangeTrackingRest_v1_0_CTRemote';
import type {ChangeTrackingRest_v1_0_Facet} from './ChangeTrackingRest_v1_0_Facet';
export type ChangeTrackingRest_v1_0_PageCTRemote = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<ChangeTrackingRest_v1_0_CTRemote>;
	pageSize?: number;
	facets?: Array<ChangeTrackingRest_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
