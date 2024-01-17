/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessUserNotification_v1_0_Facet} from './HeadlessUserNotification_v1_0_Facet';
import type {HeadlessUserNotification_v1_0_UserNotification} from './HeadlessUserNotification_v1_0_UserNotification';
export type HeadlessUserNotification_v1_0_PageUserNotification = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessUserNotification_v1_0_UserNotification>;
	facets?: Array<HeadlessUserNotification_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
