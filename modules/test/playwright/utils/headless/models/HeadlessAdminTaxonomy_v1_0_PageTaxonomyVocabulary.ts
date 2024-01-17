/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_Facet} from './HeadlessAdminTaxonomy_v1_0_Facet';
import type {HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary} from './HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary';
export type HeadlessAdminTaxonomy_v1_0_PageTaxonomyVocabulary = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary>;
	pageSize?: number;
	facets?: Array<HeadlessAdminTaxonomy_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
