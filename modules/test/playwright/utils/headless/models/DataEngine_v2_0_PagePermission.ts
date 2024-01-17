/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_Facet} from './DataEngine_v2_0_Facet';
import type {DataEngine_v2_0_Permission} from './DataEngine_v2_0_Permission';
export type DataEngine_v2_0_PagePermission = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<DataEngine_v2_0_Permission>;
	facets?: Array<DataEngine_v2_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
