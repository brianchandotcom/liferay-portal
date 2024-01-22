/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessPortalInstances_v1_0_Facet} from './HeadlessPortalInstances_v1_0_Facet';
import type {HeadlessPortalInstances_v1_0_PortalInstance} from './HeadlessPortalInstances_v1_0_PortalInstance';
export type HeadlessPortalInstances_v1_0_PagePortalInstance = {
	items?: Array<HeadlessPortalInstances_v1_0_PortalInstance>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessPortalInstances_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
