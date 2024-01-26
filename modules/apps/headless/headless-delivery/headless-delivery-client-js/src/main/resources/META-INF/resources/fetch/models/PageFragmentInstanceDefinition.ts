/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomCSSViewport } from './CustomCSSViewport';
import type { Fragment } from './Fragment';
import type { FragmentField } from './FragmentField';
import type { FragmentStyle } from './FragmentStyle';
import type { FragmentViewport } from './FragmentViewport';
import type { WidgetInstance } from './WidgetInstance';
/**
 * Represents a definition of a Page Fragment Instance.
 */
export type PageFragmentInstanceDefinition = {
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
     * The fragment of the page fragment instance.
     */
    fragment?: Fragment;
    /**
     * The page fragment instance's configuration.
     */
    fragmentConfig?: Record<string, Record<string, any>>;
    /**
     * The fragment fields of the page fragment instance.
     */
    fragmentFields?: Array<FragmentField>;
    /**
     * The fragment style of the page fragment instance.
     */
    fragmentStyle?: FragmentStyle;
    /**
     * A list of fragment viewports of the page fragment instance.
     */
    fragmentViewports?: Array<FragmentViewport>;
    /**
     * A flag that indicates whether the page fragment instance is indexed or not.
     */
    indexed?: boolean;
    /**
     * The custom name of a Page Fragment Instance.
     */
    name?: string;
    /**
     * A list of widget instances of the page fragment instance.
     */
    widgetInstances?: Array<WidgetInstance>;
};

