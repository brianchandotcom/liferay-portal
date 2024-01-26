/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ShippingOption } from './ShippingOption';
export type ShippingMethod = {
    active?: boolean;
    description?: Record<string, string>;
    engineKey?: string;
    id?: number;
    name?: Record<string, string>;
    priority?: number;
    shippingOptions?: Array<ShippingOption>;
};

