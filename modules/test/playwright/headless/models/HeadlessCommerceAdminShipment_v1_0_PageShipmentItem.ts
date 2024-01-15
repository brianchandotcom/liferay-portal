/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminShipment_v1_0_Facet} from './HeadlessCommerceAdminShipment_v1_0_Facet';
import type {HeadlessCommerceAdminShipment_v1_0_ShipmentItem} from './HeadlessCommerceAdminShipment_v1_0_ShipmentItem';
export type HeadlessCommerceAdminShipment_v1_0_PageShipmentItem = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminShipment_v1_0_ShipmentItem>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminShipment_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
