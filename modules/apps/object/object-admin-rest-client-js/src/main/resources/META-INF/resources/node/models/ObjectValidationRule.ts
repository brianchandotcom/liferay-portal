/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectValidationRuleSetting } from './ObjectValidationRuleSetting';
export type ObjectValidationRule = {
    readonly actions?: Record<string, Record<string, string>>;
    active?: boolean;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    engine?: string;
    readonly engineLabel?: string;
    errorLabel?: Record<string, string>;
    externalReferenceCode?: string;
    readonly id?: number;
    name?: Record<string, string>;
    objectDefinitionExternalReferenceCode?: string;
    objectDefinitionId?: number;
    objectValidationRuleSettings?: Array<ObjectValidationRuleSetting>;
    outputType?: 'fullValidation' | 'partialValidation';
    script?: string;
    system?: boolean;
};

