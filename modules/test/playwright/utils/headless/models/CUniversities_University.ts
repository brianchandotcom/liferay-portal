/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {CUniversities_AuditEvent} from './CUniversities_AuditEvent';
import type {CUniversities_ListEntry} from './CUniversities_ListEntry';
import type {CUniversities_TaxonomyCategoryBrief} from './CUniversities_TaxonomyCategoryBrief';
export type CUniversities_University = {
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * Optional field with the audit events associated with this object entry, can be embedded with nestedFields
	 */
	readonly 'auditEvents'?: Array<CUniversities_AuditEvent>;
	readonly 'creator'?: string;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;

	/**
	 * A list of keywords describing the object entry.
	 */
	'keywords'?: Array<string>;
	readonly 'scopeKey'?: string;
	readonly 'status'?: string;

	/**
	 * The categories associated with this object entry.
	 */
	readonly 'taxonomyCategoryBriefs'?: Array<CUniversities_TaxonomyCategoryBrief>;
	'taxonomyCategoryIds'?: Array<number>;
	readonly 'x-class-name'?: string;
	readonly 'x-schema-name'?: string;
	'universityName': string;
	'universityType': CUniversities_ListEntry;
	'year': number;
};
