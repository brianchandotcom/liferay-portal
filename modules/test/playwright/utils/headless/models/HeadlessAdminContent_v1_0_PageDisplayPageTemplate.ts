/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_DisplayPageTemplate} from './HeadlessAdminContent_v1_0_DisplayPageTemplate';
import type {HeadlessAdminContent_v1_0_Facet} from './HeadlessAdminContent_v1_0_Facet';
export type HeadlessAdminContent_v1_0_PageDisplayPageTemplate = {
	items?: Array<HeadlessAdminContent_v1_0_DisplayPageTemplate>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminContent_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
