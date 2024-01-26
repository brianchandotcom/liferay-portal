/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FragmentInlineValue } from './FragmentInlineValue';
import type { FragmentLink } from './FragmentLink';
import type { FragmentMappedValue } from './FragmentMappedValue';
/**
 * Represents a fragment field with text.
 */
export type FragmentFieldText = {
    /**
     * A link to a fragment.
     */
    fragmentLink?: FragmentLink;
    /**
     * The fragment field's text.
     */
    text?: (FragmentInlineValue | FragmentMappedValue);
};

