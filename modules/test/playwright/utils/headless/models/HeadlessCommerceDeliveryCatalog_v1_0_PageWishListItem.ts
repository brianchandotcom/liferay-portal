/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
import type {HeadlessCommerceDeliveryCatalog_v1_0_WishListItem} from './HeadlessCommerceDeliveryCatalog_v1_0_WishListItem';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageWishListItem = {
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_WishListItem>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
