/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomCSSViewport } from './CustomCSSViewport';
import type { FragmentImage } from './FragmentImage';
import type { FragmentInlineValue } from './FragmentInlineValue';
import type { FragmentLink } from './FragmentLink';
import type { FragmentMappedValue } from './FragmentMappedValue';
import type { FragmentStyle } from './FragmentStyle';
import type { FragmentViewport } from './FragmentViewport';
/**
 * Represents a definition of a Page section.
 */
export type PageSectionDefinition = {
    /**
     * @deprecated
     */
    backgroundColor?: string;
    /**
     * The background fragment image of the page section.
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
    /**
     * Defines the content visibility of the container.
     */
    contentVisibility?: string;
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
     * The fragment link of the page section.
     */
    fragmentLink?: FragmentLink;
    /**
     * The fragment style of the page section.
     */
    fragmentStyle?: FragmentStyle;
    /**
     * A list of fragment viewports of the page section.
     */
    fragmentViewports?: Array<FragmentViewport>;
    /**
     * The page section's html properties
     */
    htmlProperties?: {
        htmlTag?: 'Article' | 'Aside' | 'Div' | 'Footer' | 'Header' | 'Main' | 'Nav' | 'Section';
    };
    /**
     * A flag that indicates whether the page section is indexed or not.
     */
    indexed?: boolean;
    /**
     * the page section's layout.
     */
    layout?: {
        /**
         * @deprecated
         */
        align?: 'Baseline' | 'Center' | 'End' | 'None' | 'Start' | 'Stretch';
        /**
         * @deprecated
         */
        borderColor?: string;
        /**
         * @deprecated
         */
        borderRadius?: 'Circle' | 'Large' | 'None' | 'Pill' | 'Regular';
        /**
         * @deprecated
         */
        borderWidth?: number;
        /**
         * The container's type (fixed or fluid).
         */
        containerType?: 'Fixed' | 'Fluid';
        /**
         * @deprecated
         */
        contentDisplay?: 'Block' | 'FlexColumn' | 'FlexRow';
        flexWrap?: 'NoWrap' | 'Wrap' | 'WrapReverse';
        /**
         * @deprecated
         */
        justify?: 'Center' | 'End' | 'None' | 'SpaceAround' | 'SpaceBetween' | 'Start';
        /**
         * @deprecated
         */
        marginBottom?: number;
        /**
         * @deprecated
         */
        marginLeft?: number;
        /**
         * @deprecated
         */
        marginRight?: number;
        /**
         * @deprecated
         */
        marginTop?: number;
        /**
         * @deprecated
         */
        opacity?: number;
        /**
         * @deprecated
         */
        paddingBottom?: number;
        /**
         * @deprecated
         */
        paddingHorizontal?: number;
        /**
         * @deprecated
         */
        paddingLeft?: number;
        /**
         * @deprecated
         */
        paddingRight?: number;
        /**
         * @deprecated
         */
        paddingTop?: number;
        /**
         * @deprecated
         */
        shadow?: 'Default' | 'Large' | 'None' | 'Regular' | 'Small';
        /**
         * The width's type (fixed or fluid).
         */
        widthType?: 'Fixed' | 'Fluid';
    };
    /**
     * The custom name of a Page section.
     */
    name?: string;
};

