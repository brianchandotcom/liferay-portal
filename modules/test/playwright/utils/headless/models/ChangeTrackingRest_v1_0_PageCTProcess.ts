/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_CTProcess} from './ChangeTrackingRest_v1_0_CTProcess';
import type {ChangeTrackingRest_v1_0_Facet} from './ChangeTrackingRest_v1_0_Facet';
export type ChangeTrackingRest_v1_0_PageCTProcess = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<ChangeTrackingRest_v1_0_CTProcess>;
	pageSize?: number;
	facets?: Array<ChangeTrackingRest_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
