/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a Widget Instance.
 */
export type WidgetInstance = {
    widgetConfig?: Record<string, Record<string, any>>;
    /**
     * The widget instance's ID.
     */
    widgetInstanceId?: string;
    /**
     * The widget instance's name.
     */
    widgetName?: string;
    /**
     * The widget instance's permissions.
     */
    widgetPermissions?: Array<{
        /**
         * The keys of the actions the role has permission for.
         */
        actionKeys?: Array<string>;
        /**
         * The role's key.
         */
        roleKey?: string;
    }>;
};

