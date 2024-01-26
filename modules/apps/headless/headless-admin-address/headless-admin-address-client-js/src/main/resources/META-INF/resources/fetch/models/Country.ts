/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Region } from './Region';
export type Country = {
    a2: string;
    a3: string;
    active?: boolean;
    billingAllowed?: boolean;
    groupFilterEnabled?: boolean;
    readonly id?: number;
    idd?: number;
    name: string;
    number: number;
    position?: number;
    readonly regions?: Array<Region>;
    shippingAllowed?: boolean;
    subjectToVAT?: boolean;
    title_i18n?: Record<string, string>;
    zipRequired?: boolean;
};

