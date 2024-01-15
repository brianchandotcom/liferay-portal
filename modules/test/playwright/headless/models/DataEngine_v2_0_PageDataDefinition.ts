/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataDefinition} from './DataEngine_v2_0_DataDefinition';
import type {DataEngine_v2_0_Facet} from './DataEngine_v2_0_Facet';
export type DataEngine_v2_0_PageDataDefinition = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<DataEngine_v2_0_DataDefinition>;
	pageSize?: number;
	facets?: Array<DataEngine_v2_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
