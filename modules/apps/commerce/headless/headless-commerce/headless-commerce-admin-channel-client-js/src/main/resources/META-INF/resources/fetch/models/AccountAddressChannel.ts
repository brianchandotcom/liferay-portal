/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Channel } from './Channel';
export type AccountAddressChannel = {
    readonly accountAddressChannelId?: number;
    readonly actions?: Record<string, Record<string, string>>;
    addressChannelExternalReferenceCode?: string;
    addressChannelId: number;
    addressExternalReferenceCode?: string;
    addressId: number;
    readonly channel?: Channel;
};

