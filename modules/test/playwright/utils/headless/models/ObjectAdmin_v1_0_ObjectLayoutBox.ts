/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectLayoutRow} from './ObjectAdmin_v1_0_ObjectLayoutRow';
export type ObjectAdmin_v1_0_ObjectLayoutBox = {
	'collapsable'?: boolean;
	readonly 'id'?: number;
	'name'?: Record<string, string>;
	'objectLayoutRows'?: Array<ObjectAdmin_v1_0_ObjectLayoutRow>;
	'priority'?: number;
	'type'?: ObjectAdmin_v1_0_ObjectLayoutBox.type;
	readonly 'x-class-name'?: string;
};
export namespace ObjectAdmin_v1_0_ObjectLayoutBox {
	export enum type {
		CATEGORIZATION = 'categorization',
		REGULAR = 'regular',
	}
}
