/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessCommerceAdminCatalog_v1_0_Currency = {
	'active'?: boolean;
	'code': string;
	'formatPattern'?: Record<string, string>;
	'id'?: number;
	'maxFractionDigits'?: number;
	'minFractionDigits'?: number;
	'name': Record<string, string>;
	'primary'?: boolean;
	'priority'?: number;
	'rate'?: number;
	'symbol'?: string;
	readonly 'x-class-name'?: string;
	'roundingMode'?:
		| 'UP'
		| 'DOWN'
		| 'CEILING'
		| 'FLOOR'
		| 'HALF_UP'
		| 'HALF_DOWN'
		| 'HALF_EVEN'
		| 'UNNECESSARY';
};
