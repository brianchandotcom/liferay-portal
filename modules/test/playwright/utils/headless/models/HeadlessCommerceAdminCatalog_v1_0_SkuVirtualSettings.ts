/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettingsFileEntry} from './HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettingsFileEntry';
import type {HeadlessCommerceAdminCatalog_v1_0_Status} from './HeadlessCommerceAdminCatalog_v1_0_Status';
export type HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings = {
	'activationStatus'?: number;
	'activationStatusInfo'?: HeadlessCommerceAdminCatalog_v1_0_Status;

	/**
	 * Base64 encoded file
	 */
	'attachment'?: string;

	/**
	 * Number of days to download the attachment
	 */
	'duration'?: number;

	/**
	 * Number of downloads available for attachment
	 */
	'maxUsages'?: number;

	/**
	 * Override product virtual settings
	 */
	'override'?: boolean;

	/**
	 * Base64 encoded sample file
	 */
	'sampleAttachment'?: string;

	/**
	 * URL to download the sample file
	 */
	readonly 'sampleSrc'?: string;

	/**
	 * URL of the sample file
	 */
	'sampleURL'?: string;
	'skuVirtualSettingsFileEntries'?: Array<HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettingsFileEntry>;

	/**
	 * URL to download the file
	 */
	readonly 'src'?: string;

	/**
	 * Terms of Use content
	 */
	'termsOfUseContent'?: Record<string, string>;

	/**
	 * Terms of Use related Article Id
	 */
	'termsOfUseJournalArticleId'?: number;

	/**
	 * Terms of Use required
	 */
	'termsOfUseRequired'?: boolean;

	/**
	 * URL of the file
	 */
	'url'?: string;

	/**
	 * Enable sample file
	 */
	'useSample'?: boolean;
	readonly 'x-class-name'?: string;
};
