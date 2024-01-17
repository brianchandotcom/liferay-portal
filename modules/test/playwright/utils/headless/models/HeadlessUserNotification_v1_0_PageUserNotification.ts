/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessUserNotification_v1_0_Facet} from './HeadlessUserNotification_v1_0_Facet';
import type {HeadlessUserNotification_v1_0_UserNotification} from './HeadlessUserNotification_v1_0_UserNotification';
export type HeadlessUserNotification_v1_0_PageUserNotification = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessUserNotification_v1_0_UserNotification>;
	pageSize?: number;
	facets?: Array<HeadlessUserNotification_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
