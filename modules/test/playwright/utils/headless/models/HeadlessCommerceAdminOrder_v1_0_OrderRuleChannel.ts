/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Channel} from './HeadlessCommerceAdminOrder_v1_0_Channel';
export type HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'channel'?: HeadlessCommerceAdminOrder_v1_0_Channel;
	'channelExternalReferenceCode'?: string;
	'channelId': number;
	readonly 'orderRuleChannelId'?: number;
	'orderRuleExternalReferenceCode'?: string;
	'orderRuleId': number;
	readonly 'x-class-name'?: string;
};
