/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type Currency = {
    active?: boolean;
    code: string;
    formatPattern?: Record<string, string>;
    id?: number;
    maxFractionDigits?: number;
    minFractionDigits?: number;
    name: Record<string, string>;
    primary?: boolean;
    priority?: number;
    rate?: number;
    roundingMode?: 'UP' | 'DOWN' | 'CEILING' | 'FLOOR' | 'HALF_UP' | 'HALF_DOWN' | 'HALF_EVEN' | 'UNNECESSARY';
    symbol?: string;
};

