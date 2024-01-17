/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTCollection} from './ChangeTrackingRest_v1_0_CTCollection';
import type {ChangeTrackingRest_v1_0_Facet} from './ChangeTrackingRest_v1_0_Facet';
export type ChangeTrackingRest_v1_0_PageCTCollection = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<ChangeTrackingRest_v1_0_CTCollection>;
	pageSize?: number;
	facets?: Array<ChangeTrackingRest_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
