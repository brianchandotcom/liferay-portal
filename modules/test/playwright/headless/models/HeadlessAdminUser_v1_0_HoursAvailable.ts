/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * A list of hours when the organization is open. This follows the [`OpeningHoursSpecification`](https://www.schema.org/OpeningHoursSpecification) specification.
 */
export type HeadlessAdminUser_v1_0_HoursAvailable = {

	/**
	 * The organization's closing time (in `HH:MM` format).
	 */
	'closes'?: string;

	/**
	 * The day of the week.
	 */
	'dayOfWeek'?: string;

	/**
	 * The organization's opening time (in `HH:MM` format).
	 */
	'opens'?: string;
	readonly 'x-class-name'?: string;
};
