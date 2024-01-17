/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * The page's page element.
 */
export type HeadlessAdminContent_v1_0_PageElement = {

	/**
	 * The page element's definition.
	 */
	'definition'?: Record<string, any>;

	/**
	 * The page element's ID.
	 */
	'id'?: string;

	/**
	 * A list of the page elements this page element has.
	 */
	'pageElements'?: Array<HeadlessAdminContent_v1_0_PageElement>;

	/**
	 * The page element's type (collection, collection item, column, drop zone, form, fragment, fragment drop zone, root, row, section or widget).
	 */
	'type'?: HeadlessAdminContent_v1_0_PageElement.type;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessAdminContent_v1_0_PageElement {

	/**
	 * The page element's type (collection, collection item, column, drop zone, form, fragment, fragment drop zone, root, row, section or widget).
	 */
	export enum type {
		COLLECTION = 'Collection',
		COLLECTION_ITEM = 'CollectionItem',
		COLUMN = 'Column',
		DROP_ZONE = 'DropZone',
		FORM = 'Form',
		FRAGMENT = 'Fragment',
		FRAGMENT_DROP_ZONE = 'FragmentDropZone',
		ROOT = 'Root',
		ROW = 'Row',
		SECTION = 'Section',
		WIDGET = 'Widget',
	}
}
