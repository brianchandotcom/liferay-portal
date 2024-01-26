/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FragmentImage } from './FragmentImage';
import type { FragmentInlineValue } from './FragmentInlineValue';
import type { FragmentMappedValue } from './FragmentMappedValue';
/**
 * Represents a fragment field with a background image.
 */
export type FragmentFieldBackgroundImage = {
    /**
     * The fragment field's background image.
     */
    backgroundFragmentImage?: FragmentImage;
    /**
     * Deprecated as of Athanasius (7.3.x), replaced by backgroundFragmentImage
     * @deprecated
     */
    backgroundImage?: {
        description?: FragmentInlineValue;
        title?: FragmentInlineValue;
        url?: (FragmentInlineValue | FragmentMappedValue);
    };
};

