/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FragmentImageClassPKReference } from './FragmentImageClassPKReference';
import type { FragmentInlineValue } from './FragmentInlineValue';
import type { FragmentMappedValue } from './FragmentMappedValue';
/**
 * Represents a fragment image.
 */
export type FragmentImage = {
    /**
     * The fragment image's description.
     */
    description?: FragmentInlineValue;
    /**
     * A reference to a fragment image class primary key.
     */
    fragmentImageClassPKReference?: FragmentImageClassPKReference;
    /**
     * The fragment image's title.
     */
    title?: FragmentInlineValue;
    /**
     * The fragment image's url. Can be inline or mapped to an external value.
     */
    url?: (FragmentInlineValue | FragmentMappedValue);
};

