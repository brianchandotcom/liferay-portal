/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type ObjectAdmin_v1_0_ObjectViewSortColumn = {
	readonly 'id'?: number;
	'objectFieldName'?: string;
	'priority'?: number;
	'sortOrder'?: ObjectAdmin_v1_0_ObjectViewSortColumn.sortOrder;
	readonly 'x-class-name'?: string;
};
export namespace ObjectAdmin_v1_0_ObjectViewSortColumn {
	export enum sortOrder {
		ASC = 'asc',
		DESC = 'desc',
	}
}
