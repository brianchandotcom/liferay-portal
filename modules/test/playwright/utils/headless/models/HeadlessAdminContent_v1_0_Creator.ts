/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_UserGroupBrief} from './HeadlessAdminContent_v1_0_UserGroupBrief';

/**
 * The structured content's creator.
 */
export type HeadlessAdminContent_v1_0_Creator = {

	/**
	 * The author's additional name (e.g., middle name).
	 */
	readonly 'additionalName'?: string;

	/**
	 * The type of the content.
	 */
	readonly 'contentType'?: string;

	/**
	 * The author's surname.
	 */
	readonly 'familyName'?: string;

	/**
	 * The author's first name.
	 */
	readonly 'givenName'?: string;

	/**
	 * The author's ID.
	 */
	readonly 'id'?: number;

	/**
	 * A relative URL to the author's profile image.
	 */
	readonly 'image'?: string;

	/**
	 * The author's full name.
	 */
	readonly 'name'?: string;

	/**
	 * A relative URL to the author's user profile. Optional field, can be embedded with nestedFields.
	 */
	readonly 'profileURL'?: string;

	/**
	 * A list of userGroups information.
	 */
	'userGroupBriefs'?: Array<HeadlessAdminContent_v1_0_UserGroupBrief>;
	readonly 'x-class-name'?: string;
};
