/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
import type {HeadlessDelivery_v1_0_MessageBoardAttachment} from './HeadlessDelivery_v1_0_MessageBoardAttachment';
export type HeadlessDelivery_v1_0_PageMessageBoardAttachment = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessDelivery_v1_0_MessageBoardAttachment>;
	pageSize?: number;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
