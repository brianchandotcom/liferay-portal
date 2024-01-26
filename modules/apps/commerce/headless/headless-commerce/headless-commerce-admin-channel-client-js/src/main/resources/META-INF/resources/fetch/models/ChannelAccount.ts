/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Account } from './Account';
export type ChannelAccount = {
    readonly account?: Account;
    accountExternalReferenceCode?: string;
    accountId: number;
    readonly actions?: Record<string, Record<string, string>>;
    readonly channelAccountId?: number;
    channelExternalReferenceCode?: string;
    channelId: number;
};

