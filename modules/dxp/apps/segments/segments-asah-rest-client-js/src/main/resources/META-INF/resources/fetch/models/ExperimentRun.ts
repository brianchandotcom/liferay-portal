/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ExperimentVariant } from './ExperimentVariant';
export type ExperimentRun = {
    confidenceLevel: number;
    experimentVariants: Array<ExperimentVariant>;
    readonly status?: string;
};

