/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DispatchRest_v1_0_DispatchTrigger} from './DispatchRest_v1_0_DispatchTrigger';
import type {DispatchRest_v1_0_Facet} from './DispatchRest_v1_0_Facet';
export type DispatchRest_v1_0_PageDispatchTrigger = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<DispatchRest_v1_0_DispatchTrigger>;
	facets?: Array<DispatchRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
