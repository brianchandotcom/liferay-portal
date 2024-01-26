/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ClassTypeReference } from './ClassTypeReference';
import type { ContextReference } from './ContextReference';
import type { CustomCSSViewport } from './CustomCSSViewport';
import type { FragmentStyle } from './FragmentStyle';
import type { FragmentViewport } from './FragmentViewport';
import type { MessageFormSubmissionResult } from './MessageFormSubmissionResult';
import type { SitePageFormSubmissionResult } from './SitePageFormSubmissionResult';
import type { URLFormSubmissionResult } from './URLFormSubmissionResult';
/**
 * Represent a definition of a Page form.
 */
export type PageFormDefinition = {
    /**
     * A list of CSS Classes that are applied to the element.
     */
    cssClasses?: Array<string>;
    /**
     * Custom CSS that is applied on the fragment.
     */
    customCSS?: string;
    /**
     * The custom CSS viewports of the page collection.
     */
    customCSSViewports?: Array<CustomCSSViewport>;
    /**
     * The page form's configuration.
     */
    formConfig?: {
        /**
         * The form reference.
         */
        formReference?: (ClassTypeReference | ContextReference);
        /**
         * The definition for the success message of the form.
         */
        formSuccessSubmissionResult?: (MessageFormSubmissionResult | SitePageFormSubmissionResult | URLFormSubmissionResult);
    };
    /**
     * The fragment style of a Page form.
     */
    fragmentStyle?: FragmentStyle;
    /**
     * A list of fragment viewports of a Page form.
     */
    fragmentViewports?: Array<FragmentViewport>;
    /**
     * A flag that indicates whether the page fragment instance is indexed or not.
     */
    indexed?: boolean;
    /**
     * the page section's layout.
     */
    layout?: {
        align?: 'Baseline' | 'Center' | 'End' | 'None' | 'Start' | 'Stretch';
        contentDisplay?: 'Block' | 'FlexColumn' | 'FlexRow';
        flexWrap?: 'NoWrap' | 'Wrap' | 'WrapReverse';
        justify?: 'Center' | 'End' | 'None' | 'SpaceAround' | 'SpaceBetween' | 'Start';
        /**
         * The width's type (fixed or fluid).
         */
        widthType?: 'Fixed' | 'Fluid';
    };
    /**
     * The custom name of of a Page form.
     */
    name?: string;
};

