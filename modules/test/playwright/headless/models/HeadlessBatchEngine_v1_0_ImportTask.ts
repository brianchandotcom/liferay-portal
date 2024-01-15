/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessBatchEngine_v1_0_FailedItem} from './HeadlessBatchEngine_v1_0_FailedItem';
export type HeadlessBatchEngine_v1_0_ImportTask = {

	/**
	 * The item class name for which data will be processed in batch.
	 */
	'className'?: string;

	/**
	 * The file content type.
	 */
	'contentType'?: string;

	/**
	 * The end time of import task operation.
	 */
	'endTime'?: string;

	/**
	 * The error message in case of import task's failed execution.
	 */
	'errorMessage'?: string;

	/**
	 * The status of import task's execution.
	 */
	'executeStatus'?: HeadlessBatchEngine_v1_0_ImportTask.executeStatus;

	/**
	 * The optional external key of this account.
	 */
	'externalReferenceCode'?: string;
	'failedItems'?: Array<HeadlessBatchEngine_v1_0_FailedItem>;

	/**
	 * The task's ID.
	 */
	'id'?: number;

	/**
	 * Defines if import task will fail when error occurs or continue importing rest of the items.
	 */
	'importStrategy'?: HeadlessBatchEngine_v1_0_ImportTask.importStrategy;

	/**
	 * The operation of import task.
	 */
	'operation'?: HeadlessBatchEngine_v1_0_ImportTask.operation;

	/**
	 * Number of items processed by import task opeartion.
	 */
	'processedItemsCount'?: number;

	/**
	 * The start time of import task operation.
	 */
	'startTime'?: string;

	/**
	 * Total number of items that will be processed by import task operation.
	 */
	'totalItemsCount'?: number;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessBatchEngine_v1_0_ImportTask {

	/**
	 * The status of import task's execution.
	 */
	export enum executeStatus {
		COMPLETED = 'COMPLETED',
		FAILED = 'FAILED',
		INITIAL = 'INITIAL',
		STARTED = 'STARTED',
	}

	/**
	 * Defines if import task will fail when error occurs or continue importing rest of the items.
	 */
	export enum importStrategy {
		ON_ERROR_CONTINUE = 'ON_ERROR_CONTINUE',
		ON_ERROR_FAIL = 'ON_ERROR_FAIL',
	}

	/**
	 * The operation of import task.
	 */
	export enum operation {
		CREATE = 'CREATE',
		DELETE = 'DELETE',
		UPDATE = 'UPDATE',
	}
}
