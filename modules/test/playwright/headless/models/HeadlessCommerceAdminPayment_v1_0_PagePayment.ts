/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPayment_v1_0_Facet} from './HeadlessCommerceAdminPayment_v1_0_Facet';
import type {HeadlessCommerceAdminPayment_v1_0_Payment} from './HeadlessCommerceAdminPayment_v1_0_Payment';
export type HeadlessCommerceAdminPayment_v1_0_PagePayment = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminPayment_v1_0_Payment>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminPayment_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
