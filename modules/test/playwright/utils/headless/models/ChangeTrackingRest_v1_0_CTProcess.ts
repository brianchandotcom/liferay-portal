/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_Status} from './ChangeTrackingRest_v1_0_Status';
export type ChangeTrackingRest_v1_0_CTProcess = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	readonly 'ctCollectionId'?: number;

	/**
	 * The publication's published date.
	 */
	readonly 'datePublished'?: string;
	'description'?: string;
	readonly 'id'?: number;
	'name'?: string;

	/**
	 * The publication's creator.
	 */
	readonly 'ownerName'?: string;
	'status'?: ChangeTrackingRest_v1_0_Status;
	readonly 'x-class-name'?: string;
};
