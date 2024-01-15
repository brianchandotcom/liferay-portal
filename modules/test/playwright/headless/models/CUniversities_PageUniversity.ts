/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {CUniversities_Facet} from './CUniversities_Facet';
import type {CUniversities_University} from './CUniversities_University';
export type CUniversities_PageUniversity = {
	'totalCount'?: number;
	'lastPage'?: number;
	'items'?: Array<CUniversities_University>;
	'pageSize'?: number;
	'facets'?: Array<CUniversities_Facet>;
	'page'?: number;
	'actions'?: Record<string, Record<string, string>>;
	readonly 'x-schema-name'?: string;
};
