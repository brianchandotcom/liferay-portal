/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPayment_v1_0_Facet} from './HeadlessCommerceAdminPayment_v1_0_Facet';
import type {HeadlessCommerceAdminPayment_v1_0_Payment} from './HeadlessCommerceAdminPayment_v1_0_Payment';
export type HeadlessCommerceAdminPayment_v1_0_PagePayment = {
	items?: Array<HeadlessCommerceAdminPayment_v1_0_Payment>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminPayment_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
