/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Bulk_v1_0_Facet} from './Bulk_v1_0_Facet';
import type {Bulk_v1_0_TaxonomyVocabulary} from './Bulk_v1_0_TaxonomyVocabulary';
export type Bulk_v1_0_PageTaxonomyVocabulary = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<Bulk_v1_0_TaxonomyVocabulary>;
	facets?: Array<Bulk_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
