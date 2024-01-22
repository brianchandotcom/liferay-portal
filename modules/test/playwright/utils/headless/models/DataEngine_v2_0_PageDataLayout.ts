/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataLayout} from './DataEngine_v2_0_DataLayout';
import type {DataEngine_v2_0_Facet} from './DataEngine_v2_0_Facet';
export type DataEngine_v2_0_PageDataLayout = {
	items?: Array<DataEngine_v2_0_DataLayout>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<DataEngine_v2_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
