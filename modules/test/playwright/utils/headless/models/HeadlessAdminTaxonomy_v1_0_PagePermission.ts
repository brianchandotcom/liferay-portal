/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_Facet} from './HeadlessAdminTaxonomy_v1_0_Facet';
import type {HeadlessAdminTaxonomy_v1_0_Permission} from './HeadlessAdminTaxonomy_v1_0_Permission';
export type HeadlessAdminTaxonomy_v1_0_PagePermission = {
	items?: Array<HeadlessAdminTaxonomy_v1_0_Permission>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminTaxonomy_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
