/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ClassFieldReference } from './ClassFieldReference';
import type { ClassFieldsReference } from './ClassFieldsReference';
import type { ClassPKReference } from './ClassPKReference';
import type { ContextReference } from './ContextReference';
import type { FragmentInlineValue } from './FragmentInlineValue';
/**
 * Represents a fragment mapped value.
 */
export type FragmentMappedValue = {
    /**
     * The default value of the fragment mapped value.
     */
    defaultFragmentInlineValue?: FragmentInlineValue;
    /**
     * Deprecated as of Athanasius (7.3.x), replaced by defaultFragmentInlineValue
     * @deprecated
     */
    defaultValue?: {
        value?: string;
        value_i18n?: Record<string, string>;
    };
    /**
     * The mapping of the fragment mapped value.
     */
    mapping?: {
        /**
         * The mapping's field key.
         */
        fieldKey?: string;
        /**
         * The mapping's item reference.
         */
        itemReference?: (ClassFieldReference | ClassFieldsReference | ClassPKReference | ContextReference);
    };
};

