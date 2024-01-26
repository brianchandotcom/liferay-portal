/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Channel } from './Channel';
export type OrderRuleChannel = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly channel?: Channel;
    channelExternalReferenceCode?: string;
    channelId: number;
    readonly orderRuleChannelId?: number;
    orderRuleExternalReferenceCode?: string;
    orderRuleId: number;
};

