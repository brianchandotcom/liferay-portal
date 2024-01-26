/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FragmentInlineValue } from './FragmentInlineValue';
/**
 * Represents a definition of a submission result of type message.
 */
export type MessageFormSubmissionResult = {
    /**
     * The localized submission of message type.
     */
    message?: FragmentInlineValue;
    /**
     * The message form submission type (embedded, none).
     */
    messageType?: 'Embedded' | 'None';
    showNotification?: boolean;
};

