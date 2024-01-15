/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Channel} from './HeadlessCommerceAdminPricing_v2_0_Channel';
export type HeadlessCommerceAdminPricing_v2_0_PriceListChannel = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'channel'?: HeadlessCommerceAdminPricing_v2_0_Channel;
	'channelExternalReferenceCode'?: string;
	'channelId': number;
	'order'?: number;
	readonly 'priceListChannelId'?: number;
	'priceListExternalReferenceCode'?: string;
	'priceListId': number;
	readonly 'x-class-name'?: string;
};
