/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Comment} from './HeadlessDelivery_v1_0_Comment';
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
export type HeadlessDelivery_v1_0_PageComment = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessDelivery_v1_0_Comment>;
	pageSize?: number;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
