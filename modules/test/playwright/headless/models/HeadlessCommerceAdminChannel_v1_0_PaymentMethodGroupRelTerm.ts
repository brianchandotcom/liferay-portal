/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_Term} from './HeadlessCommerceAdminChannel_v1_0_Term';
export type HeadlessCommerceAdminChannel_v1_0_PaymentMethodGroupRelTerm = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'paymentMethodGroupRelId': number;
	readonly 'paymentMethodGroupRelTermId'?: number;
	'term'?: HeadlessCommerceAdminChannel_v1_0_Term;
	'termExternalReferenceCode'?: string;
	'termId': number;
	readonly 'x-class-name'?: string;
};
