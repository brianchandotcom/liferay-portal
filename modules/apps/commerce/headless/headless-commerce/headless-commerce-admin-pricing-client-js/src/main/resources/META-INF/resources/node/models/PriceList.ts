/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PriceEntry } from './PriceEntry';
import type { PriceListAccountGroup } from './PriceListAccountGroup';
export type PriceList = {
    active?: boolean;
    catalogId?: number;
    currencyCode: string;
    customFields?: Record<string, any>;
    displayDate?: string;
    expirationDate?: string;
    externalReferenceCode?: string;
    id?: number;
    name: string;
    neverExpire?: boolean;
    priceEntries?: Array<PriceEntry>;
    priceListAccountGroups?: Array<PriceListAccountGroup>;
    priority?: number;
};

