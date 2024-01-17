/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_BlogPostingImage} from './HeadlessDelivery_v1_0_BlogPostingImage';
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
export type HeadlessDelivery_v1_0_PageBlogPostingImage = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessDelivery_v1_0_BlogPostingImage>;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
