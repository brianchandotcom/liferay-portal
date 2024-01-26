/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Configuration } from './Configuration';
export type ElementDefinition = {
    category?: string;
    configuration?: Configuration;
    icon?: string;
    uiConfiguration?: {
        fieldSets?: Array<{
            fields?: Array<{
                defaultValue?: Record<string, any>;
                fieldMappings?: Array<{
                    boost?: number;
                    field?: string;
                    locale?: string;
                }>;
                helpText?: string;
                helpTextLocalized?: string;
                label?: string;
                labelLocalized?: string;
                name?: string;
                type?: string;
                typeOptions?: {
                    boost?: boolean;
                    format?: string;
                    max?: Record<string, any>;
                    min?: Record<string, any>;
                    nullable?: boolean;
                    options?: Array<{
                        label?: string;
                        value?: string;
                    }>;
                    required?: boolean;
                    step?: Record<string, any>;
                    unit?: string;
                    unitSuffix?: string;
                };
            }>;
        }>;
    };
};

