/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminShipment_v1_0_Facet} from './HeadlessCommerceAdminShipment_v1_0_Facet';
import type {HeadlessCommerceAdminShipment_v1_0_Shipment} from './HeadlessCommerceAdminShipment_v1_0_Shipment';
export type HeadlessCommerceAdminShipment_v1_0_PageShipment = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminShipment_v1_0_Shipment>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminShipment_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
