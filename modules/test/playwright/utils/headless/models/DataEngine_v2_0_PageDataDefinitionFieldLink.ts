/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataDefinitionFieldLink} from './DataEngine_v2_0_DataDefinitionFieldLink';
import type {DataEngine_v2_0_Facet} from './DataEngine_v2_0_Facet';
export type DataEngine_v2_0_PageDataDefinitionFieldLink = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<DataEngine_v2_0_DataDefinitionFieldLink>;
	facets?: Array<DataEngine_v2_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
