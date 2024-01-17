/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessPortalInstances_v1_0_Facet} from './HeadlessPortalInstances_v1_0_Facet';
import type {HeadlessPortalInstances_v1_0_PortalInstance} from './HeadlessPortalInstances_v1_0_PortalInstance';
export type HeadlessPortalInstances_v1_0_PagePortalInstance = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessPortalInstances_v1_0_PortalInstance>;
	facets?: Array<HeadlessPortalInstances_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
