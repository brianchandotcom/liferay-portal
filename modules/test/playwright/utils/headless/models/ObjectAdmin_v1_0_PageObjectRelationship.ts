/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_Facet} from './ObjectAdmin_v1_0_Facet';
import type {ObjectAdmin_v1_0_ObjectRelationship} from './ObjectAdmin_v1_0_ObjectRelationship';
export type ObjectAdmin_v1_0_PageObjectRelationship = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<ObjectAdmin_v1_0_ObjectRelationship>;
	pageSize?: number;
	facets?: Array<ObjectAdmin_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
