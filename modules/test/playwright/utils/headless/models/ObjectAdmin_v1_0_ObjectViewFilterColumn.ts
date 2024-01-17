/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type ObjectAdmin_v1_0_ObjectViewFilterColumn = {
	'filterType'?: ObjectAdmin_v1_0_ObjectViewFilterColumn.filterType;
	readonly 'id'?: number;
	'json'?: string;
	'objectFieldName'?: string;
	readonly 'valueSummary'?: string;
	readonly 'x-class-name'?: string;
};
export namespace ObjectAdmin_v1_0_ObjectViewFilterColumn {
	export enum filterType {
		EXCLUDES = 'excludes',
		INCLUDES = 'includes',
	}
}
