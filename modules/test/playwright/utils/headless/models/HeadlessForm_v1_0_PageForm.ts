/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessForm_v1_0_Facet} from './HeadlessForm_v1_0_Facet';
import type {HeadlessForm_v1_0_Form} from './HeadlessForm_v1_0_Form';
export type HeadlessForm_v1_0_PageForm = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessForm_v1_0_Form>;
	facets?: Array<HeadlessForm_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
