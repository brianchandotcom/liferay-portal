/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_Facet} from './ObjectAdmin_v1_0_Facet';
import type {ObjectAdmin_v1_0_ObjectRelationship} from './ObjectAdmin_v1_0_ObjectRelationship';
export type ObjectAdmin_v1_0_PageObjectRelationship = {
	items?: Array<ObjectAdmin_v1_0_ObjectRelationship>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<ObjectAdmin_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
