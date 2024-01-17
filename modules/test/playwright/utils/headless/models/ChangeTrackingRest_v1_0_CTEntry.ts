/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ChangeTrackingRest_v1_0_Status} from './ChangeTrackingRest_v1_0_Status';
export type ChangeTrackingRest_v1_0_CTEntry = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	readonly 'changeType'?: string;
	readonly 'ctCollectionId'?: number;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	readonly 'hideable'?: boolean;
	readonly 'id'?: number;
	readonly 'modelClassNameId'?: number;
	readonly 'modelClassPK'?: number;
	readonly 'ownerId'?: number;
	readonly 'ownerName'?: string;
	readonly 'siteId'?: number;
	readonly 'siteName'?: string;
	'status'?: ChangeTrackingRest_v1_0_Status;
	readonly 'title'?: string;
	readonly 'typeName'?: string;
	readonly 'x-class-name'?: string;
};
