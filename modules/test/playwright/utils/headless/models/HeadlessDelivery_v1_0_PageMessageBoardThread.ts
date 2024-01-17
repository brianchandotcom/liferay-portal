/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
import type {HeadlessDelivery_v1_0_MessageBoardThread} from './HeadlessDelivery_v1_0_MessageBoardThread';
export type HeadlessDelivery_v1_0_PageMessageBoardThread = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessDelivery_v1_0_MessageBoardThread>;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
