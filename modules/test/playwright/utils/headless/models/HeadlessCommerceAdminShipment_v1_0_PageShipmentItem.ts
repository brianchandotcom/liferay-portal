/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminShipment_v1_0_Facet} from './HeadlessCommerceAdminShipment_v1_0_Facet';
import type {HeadlessCommerceAdminShipment_v1_0_ShipmentItem} from './HeadlessCommerceAdminShipment_v1_0_ShipmentItem';
export type HeadlessCommerceAdminShipment_v1_0_PageShipmentItem = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminShipment_v1_0_ShipmentItem>;
	facets?: Array<HeadlessCommerceAdminShipment_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
