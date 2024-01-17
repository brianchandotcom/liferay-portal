/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
import type {HeadlessDelivery_v1_0_NavigationMenu} from './HeadlessDelivery_v1_0_NavigationMenu';
export type HeadlessDelivery_v1_0_PageNavigationMenu = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessDelivery_v1_0_NavigationMenu>;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
