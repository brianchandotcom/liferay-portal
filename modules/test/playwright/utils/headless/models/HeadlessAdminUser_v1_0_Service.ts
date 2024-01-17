/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_HoursAvailable} from './HeadlessAdminUser_v1_0_HoursAvailable';

/**
 * A list of services the organization provides. This follows the [`Service`](https://www.schema.org/Service) specification.
 */
export type HeadlessAdminUser_v1_0_Service = {

	/**
	 * A list of hours when the organization is open. This follows the [`OpeningHoursSpecification`](https://www.schema.org/OpeningHoursSpecification) specification.
	 */
	'hoursAvailable'?: Array<HeadlessAdminUser_v1_0_HoursAvailable>;

	/**
	 * The type of service the organization provides.
	 */
	'serviceType'?: string;
	readonly 'x-class-name'?: string;
};
