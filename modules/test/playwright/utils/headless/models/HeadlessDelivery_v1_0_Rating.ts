/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
export type HeadlessDelivery_v1_0_Rating = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * The best possible rating an asset can receive (normalized to 1.0 by default).
	 */
	readonly 'bestRating'?: number;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * The rating's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time a field of the rating changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The rating's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The rating's value.
	 */
	'ratingValue'?: number;

	/**
	 * The worst possible rating an asset can receive (normalized to 0.0 by default).
	 */
	readonly 'worstRating'?: number;
	readonly 'x-class-name'?: string;
};
