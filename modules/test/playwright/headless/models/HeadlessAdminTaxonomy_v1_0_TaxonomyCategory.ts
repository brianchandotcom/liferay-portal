/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminTaxonomy_v1_0_Creator} from './HeadlessAdminTaxonomy_v1_0_Creator';
import type {HeadlessAdminTaxonomy_v1_0_ParentTaxonomyCategory} from './HeadlessAdminTaxonomy_v1_0_ParentTaxonomyCategory';
import type {HeadlessAdminTaxonomy_v1_0_ParentTaxonomyVocabulary} from './HeadlessAdminTaxonomy_v1_0_ParentTaxonomyVocabulary';
import type {HeadlessAdminTaxonomy_v1_0_TaxonomyCategoryProperty} from './HeadlessAdminTaxonomy_v1_0_TaxonomyCategoryProperty';

/**
 * Represents a category, which is a hierarchical classification that can be associated with particular asset types. Properties follow the [category](https://schema.org/category) specification.
 */
export type HeadlessAdminTaxonomy_v1_0_TaxonomyCategory = {
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * A list of languages the category has a translation for.
	 */
	readonly 'availableLanguages'?: Array<string>;
	'creator'?: HeadlessAdminTaxonomy_v1_0_Creator;

	/**
	 * The category's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The category's most recent modification date.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The category's text description.
	 */
	'description'?: string;
	'description_i18n'?: Record<string, string>;

	/**
	 * The category's external reference code
	 */
	'externalReferenceCode'?: string;

	/**
	 * The category's ID.
	 */
	readonly 'id'?: string;

	/**
	 * The category's name.
	 */
	'name': string;
	'name_i18n'?: Record<string, string>;

	/**
	 * The number of times this category has been used in other assets.
	 */
	readonly 'numberOfTaxonomyCategories'?: number;
	'parentTaxonomyCategory'?: HeadlessAdminTaxonomy_v1_0_ParentTaxonomyCategory;
	'parentTaxonomyVocabulary'?: HeadlessAdminTaxonomy_v1_0_ParentTaxonomyVocabulary;

	/**
	 * The ID of the site to which this category is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * The category's properties.
	 */
	'taxonomyCategoryProperties'?: Array<HeadlessAdminTaxonomy_v1_0_TaxonomyCategoryProperty>;
	readonly 'taxonomyCategoryUsageCount'?: number;

	/**
	 * The `TaxonomyVocabulary` id, only if the category does not have a parent category.
	 */
	'taxonomyVocabularyId'?: number;
	'viewableBy'?: HeadlessAdminTaxonomy_v1_0_TaxonomyCategory.viewableBy;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessAdminTaxonomy_v1_0_TaxonomyCategory {
	export enum viewableBy {
		ANYONE = 'Anyone',
		MEMBERS = 'Members',
		OWNER = 'Owner',
	}
}
