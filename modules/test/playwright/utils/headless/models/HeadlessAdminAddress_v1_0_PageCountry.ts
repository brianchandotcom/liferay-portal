/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminAddress_v1_0_Country} from './HeadlessAdminAddress_v1_0_Country';
import type {HeadlessAdminAddress_v1_0_Facet} from './HeadlessAdminAddress_v1_0_Facet';
export type HeadlessAdminAddress_v1_0_PageCountry = {
	items?: Array<HeadlessAdminAddress_v1_0_Country>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminAddress_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
