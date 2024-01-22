/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * The page's page element.
 */
export type HeadlessDelivery_v1_0_PageElement = {

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
	'pageElements'?: Array<HeadlessDelivery_v1_0_PageElement>;
	readonly 'x-class-name'?: string;

	/**
	 * The page element's type (collection, collection item, column, drop zone, form, fragment, fragment drop zone, root, row, section or widget).
	 */
	'type'?:
		| 'Collection'
		| 'CollectionItem'
		| 'Column'
		| 'DropZone'
		| 'Form'
		| 'Fragment'
		| 'FragmentDropZone'
		| 'Root'
		| 'Row'
		| 'Section'
		| 'Widget';
};
