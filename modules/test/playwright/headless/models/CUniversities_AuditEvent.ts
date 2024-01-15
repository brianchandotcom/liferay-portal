/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {CUniversities_AuditFieldChange} from './CUniversities_AuditFieldChange';
import type {CUniversities_Creator} from './CUniversities_Creator';

/**
 * Optional field with the audit events associated with this object entry, can be embedded with nestedFields
 */
export type CUniversities_AuditEvent = {
	readonly 'auditFieldChanges'?: Array<CUniversities_AuditFieldChange>;
	'creator'?: CUniversities_Creator;
	readonly 'dateCreated'?: string;
	readonly 'eventType'?: string;
	readonly 'x-class-name'?: string;
};
