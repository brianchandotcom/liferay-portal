/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Channel} from './HeadlessCommerceAdminOrder_v1_0_Channel';
export type HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'channel'?: HeadlessCommerceAdminOrder_v1_0_Channel;
	'channelExternalReferenceCode'?: string;
	'channelId': number;
	readonly 'orderTypeChannelId'?: number;
	'orderTypeExternalReferenceCode'?: string;
	'orderTypeId': number;
	readonly 'x-class-name'?: string;
};
