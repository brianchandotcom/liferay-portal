/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Channel } from './Channel';
export type WarehouseChannel = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly channel?: Channel;
    channelExternalReferenceCode?: string;
    channelId: number;
    readonly warehouseChannelId?: number;
    warehouseExternalReferenceCode?: string;
    warehouseId: number;
};

