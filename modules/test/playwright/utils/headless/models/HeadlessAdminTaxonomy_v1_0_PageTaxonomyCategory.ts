/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_Facet} from './HeadlessAdminTaxonomy_v1_0_Facet';
import type {HeadlessAdminTaxonomy_v1_0_TaxonomyCategory} from './HeadlessAdminTaxonomy_v1_0_TaxonomyCategory';
export type HeadlessAdminTaxonomy_v1_0_PageTaxonomyCategory = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminTaxonomy_v1_0_TaxonomyCategory>;
	pageSize?: number;
	facets?: Array<HeadlessAdminTaxonomy_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
