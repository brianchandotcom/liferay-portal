/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Notification_v1_0_Facet} from './Notification_v1_0_Facet';
import type {Notification_v1_0_NotificationTemplate} from './Notification_v1_0_NotificationTemplate';
export type Notification_v1_0_PageNotificationTemplate = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<Notification_v1_0_NotificationTemplate>;
	facets?: Array<Notification_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
