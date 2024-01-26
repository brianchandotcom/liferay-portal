/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayPageActionExecutionResult } from './DisplayPageActionExecutionResult';
import type { NoneActionExecutionResult } from './NoneActionExecutionResult';
import type { NotificationActionExecutionResult } from './NotificationActionExecutionResult';
import type { SitePageActionExecutionResult } from './SitePageActionExecutionResult';
import type { URLActionExecutionResult } from './URLActionExecutionResult';
/**
 * Represents a definition of an action execution result.
 */
export type ActionExecutionResult = {
    /**
     * The type of result.
     */
    type?: 'DisplayPage' | 'None' | 'Notification' | 'Page' | 'URL';
    value?: (DisplayPageActionExecutionResult | NoneActionExecutionResult | NotificationActionExecutionResult | SitePageActionExecutionResult | URLActionExecutionResult);
};

