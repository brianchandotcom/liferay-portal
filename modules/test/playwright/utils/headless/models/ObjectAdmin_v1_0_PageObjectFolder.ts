/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_Facet} from './ObjectAdmin_v1_0_Facet';
import type {ObjectAdmin_v1_0_ObjectFolder} from './ObjectAdmin_v1_0_ObjectFolder';
export type ObjectAdmin_v1_0_PageObjectFolder = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<ObjectAdmin_v1_0_ObjectFolder>;
	facets?: Array<ObjectAdmin_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
