/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_Status} from './ChangeTrackingRest_v1_0_Status';
export type ChangeTrackingRest_v1_0_CTCollection = {
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * The publication's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any of the publication's fields were changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The date that the publication will be published.
	 */
	readonly 'dateScheduled'?: string;
	'description'?: string;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'name'?: string;

	/**
	 * The publication's creator.
	 */
	readonly 'ownerName'?: string;
	'status'?: ChangeTrackingRest_v1_0_Status;
	readonly 'x-class-name'?: string;
};
