/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_Facet} from './HeadlessAdminTaxonomy_v1_0_Facet';
import type {HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary} from './HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary';
export type HeadlessAdminTaxonomy_v1_0_PageTaxonomyVocabulary = {
	items?: Array<HeadlessAdminTaxonomy_v1_0_TaxonomyVocabulary>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminTaxonomy_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
