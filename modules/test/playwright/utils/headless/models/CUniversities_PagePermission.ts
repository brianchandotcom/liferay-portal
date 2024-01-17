/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {CUniversities_Facet} from './CUniversities_Facet';
import type {CUniversities_Permission} from './CUniversities_Permission';
export type CUniversities_PagePermission = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<CUniversities_Permission>;
	pageSize?: number;
	facets?: Array<CUniversities_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
