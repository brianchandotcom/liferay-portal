/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPayment_v1_0_Status} from './HeadlessCommerceAdminPayment_v1_0_Status';
export type HeadlessCommerceAdminPayment_v1_0_Payment = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'amount'?: number;
	readonly 'amountFormatted'?: string;
	'callbackURL'?: string;
	'cancelURL'?: string;
	'channelId'?: number;
	'comment'?: string;
	readonly 'createDate'?: string;
	'currencyCode'?: string;
	'errorMessages'?: string;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'languageId'?: string;
	'paymentIntegrationKey'?: string;
	'paymentIntegrationType'?: number;
	'paymentStatus'?: number;
	'paymentStatusStatus'?: HeadlessCommerceAdminPayment_v1_0_Status;
	'reasonKey'?: string;
	readonly 'reasonName'?: Record<string, string>;
	'redirectURL'?: string;
	'relatedItemId'?: number;
	'relatedItemName'?: string;
	'relatedItemNameLabel'?: string;
	'transactionCode'?: string;
	'type'?: number;
	readonly 'typeLabel'?: string;
	readonly 'x-class-name'?: string;
};
