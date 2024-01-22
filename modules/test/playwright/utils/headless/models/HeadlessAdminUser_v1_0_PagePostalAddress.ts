/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Facet} from './HeadlessAdminUser_v1_0_Facet';
import type {HeadlessAdminUser_v1_0_PostalAddress} from './HeadlessAdminUser_v1_0_PostalAddress';
export type HeadlessAdminUser_v1_0_PagePostalAddress = {
	items?: Array<HeadlessAdminUser_v1_0_PostalAddress>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminUser_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
