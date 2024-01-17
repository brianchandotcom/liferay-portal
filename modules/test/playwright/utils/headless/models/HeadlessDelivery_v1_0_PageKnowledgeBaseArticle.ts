/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
import type {HeadlessDelivery_v1_0_KnowledgeBaseArticle} from './HeadlessDelivery_v1_0_KnowledgeBaseArticle';
export type HeadlessDelivery_v1_0_PageKnowledgeBaseArticle = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessDelivery_v1_0_KnowledgeBaseArticle>;
	pageSize?: number;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
