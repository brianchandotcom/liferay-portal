/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_Facet} from './HeadlessCommerceAdminChannel_v1_0_Facet';
import type {HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm} from './HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm';
export type HeadlessCommerceAdminChannel_v1_0_PagePaymentMethodGroupRelTerm = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminChannel_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
