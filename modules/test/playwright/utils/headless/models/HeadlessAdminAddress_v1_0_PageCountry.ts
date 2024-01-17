/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminAddress_v1_0_Country} from './HeadlessAdminAddress_v1_0_Country';
import type {HeadlessAdminAddress_v1_0_Facet} from './HeadlessAdminAddress_v1_0_Facet';
export type HeadlessAdminAddress_v1_0_PageCountry = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessAdminAddress_v1_0_Country>;
	facets?: Array<HeadlessAdminAddress_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
