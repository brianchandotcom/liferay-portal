/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomCSSViewport } from './CustomCSSViewport';
import type { FragmentStyle } from './FragmentStyle';
import type { FragmentViewport } from './FragmentViewport';
import type { WidgetInstance } from './WidgetInstance';
/**
 * Represents a definition of a Page Widget instance.
 */
export type PageWidgetInstanceDefinition = {
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
     * The fragment style of the page widget instance.
     */
    fragmentStyle?: FragmentStyle;
    /**
     * A list of fragment viewports of the page widget instance.
     */
    fragmentViewports?: Array<FragmentViewport>;
    /**
     * The custom name of a Page Widget instance.
     */
    name?: string;
    /**
     * The widget instance of the page widget instance.
     */
    widgetInstance?: WidgetInstance;
};

