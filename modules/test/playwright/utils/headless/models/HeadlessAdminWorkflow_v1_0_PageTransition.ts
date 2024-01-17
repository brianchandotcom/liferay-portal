/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Facet} from './HeadlessAdminWorkflow_v1_0_Facet';
import type {HeadlessAdminWorkflow_v1_0_Transition} from './HeadlessAdminWorkflow_v1_0_Transition';
export type HeadlessAdminWorkflow_v1_0_PageTransition = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminWorkflow_v1_0_Transition>;
	pageSize?: number;
	facets?: Array<HeadlessAdminWorkflow_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
