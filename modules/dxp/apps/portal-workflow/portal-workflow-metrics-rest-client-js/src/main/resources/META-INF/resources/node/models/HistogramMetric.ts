/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Histogram } from './Histogram';
/**
 * https://www.schema.org/HistogramMetric
 */
export type HistogramMetric = {
    histograms?: Array<Histogram>;
    unit?: 'Days' | 'Hours' | 'Months' | 'Weeks' | 'Years';
    value?: number;
};

