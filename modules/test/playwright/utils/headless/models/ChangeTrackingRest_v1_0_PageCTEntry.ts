/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTEntry} from './ChangeTrackingRest_v1_0_CTEntry';
import type {ChangeTrackingRest_v1_0_Facet} from './ChangeTrackingRest_v1_0_Facet';
export type ChangeTrackingRest_v1_0_PageCTEntry = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<ChangeTrackingRest_v1_0_CTEntry>;
	facets?: Array<ChangeTrackingRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
