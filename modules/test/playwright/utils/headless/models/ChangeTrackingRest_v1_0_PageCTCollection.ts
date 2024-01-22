/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTCollection} from './ChangeTrackingRest_v1_0_CTCollection';
import type {ChangeTrackingRest_v1_0_Facet} from './ChangeTrackingRest_v1_0_Facet';
export type ChangeTrackingRest_v1_0_PageCTCollection = {
	items?: Array<ChangeTrackingRest_v1_0_CTCollection>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<ChangeTrackingRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
