/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_Facet} from './HeadlessAdminTaxonomy_v1_0_Facet';
import type {HeadlessAdminTaxonomy_v1_0_Keyword} from './HeadlessAdminTaxonomy_v1_0_Keyword';
export type HeadlessAdminTaxonomy_v1_0_PageKeyword = {
	items?: Array<HeadlessAdminTaxonomy_v1_0_Keyword>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminTaxonomy_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
