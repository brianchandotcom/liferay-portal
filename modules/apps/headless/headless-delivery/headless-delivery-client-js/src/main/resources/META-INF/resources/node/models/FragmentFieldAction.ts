/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ActionExecutionResult } from './ActionExecutionResult';
import type { FragmentInlineValue } from './FragmentInlineValue';
import type { FragmentMappedValue } from './FragmentMappedValue';
/**
 * Represents a fragment field with an action.
 */
export type FragmentFieldAction = {
    /**
     * The fragment field's action. Must be mapped to an external value.
     */
    action?: FragmentMappedValue;
    /**
     * The action execution result in case the action fails.
     */
    onError?: ActionExecutionResult;
    /**
     * The action execution result in case the action succeeds.
     */
    onSuccess?: ActionExecutionResult;
    /**
     * The fragment field's text.
     */
    text?: (FragmentInlineValue | FragmentMappedValue);
};

