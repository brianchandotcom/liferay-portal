/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_ClientExtension} from './HeadlessDelivery_v1_0_ClientExtension';
import type {HeadlessDelivery_v1_0_MasterPage} from './HeadlessDelivery_v1_0_MasterPage';
import type {HeadlessDelivery_v1_0_StyleBook} from './HeadlessDelivery_v1_0_StyleBook';

/**
 * The page's settings.
 */
export type HeadlessDelivery_v1_0_Settings = {

	/**
	 * The page's color scheme name.
	 */
	'colorSchemeName'?: string;

	/**
	 * The page's CSS.
	 */
	'css'?: string;

	/**
	 * The FavIcon of the page
	 */
	'favIcon'?: Record<string, any>;

	/**
	 * The client extensions for global css associated to the page.
	 */
	'globalCSSClientExtensions'?: Array<HeadlessDelivery_v1_0_ClientExtension>;

	/**
	 * The client extensions for global js associated to the page.
	 */
	'globalJSClientExtensions'?: Array<HeadlessDelivery_v1_0_ClientExtension>;

	/**
	 * The page's JavaScript.
	 */
	'javascript'?: string;
	'masterPage'?: HeadlessDelivery_v1_0_MasterPage;
	'styleBook'?: HeadlessDelivery_v1_0_StyleBook;
	'themeCSSClientExtension'?: HeadlessDelivery_v1_0_ClientExtension;

	/**
	 * The page's theme name.
	 */
	'themeName'?: string;

	/**
	 * The page's theme settings.
	 */
	'themeSettings'?: Record<string, any>;
	readonly 'x-class-name'?: string;
};
