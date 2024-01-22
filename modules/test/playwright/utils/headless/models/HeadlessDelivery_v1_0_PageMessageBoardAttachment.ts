/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
import type {HeadlessDelivery_v1_0_MessageBoardAttachment} from './HeadlessDelivery_v1_0_MessageBoardAttachment';
export type HeadlessDelivery_v1_0_PageMessageBoardAttachment = {
	items?: Array<HeadlessDelivery_v1_0_MessageBoardAttachment>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
