/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Notification_v1_0_Facet} from './Notification_v1_0_Facet';
import type {Notification_v1_0_NotificationQueueEntry} from './Notification_v1_0_NotificationQueueEntry';
export type Notification_v1_0_PageNotificationQueueEntry = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<Notification_v1_0_NotificationQueueEntry>;
	pageSize?: number;
	facets?: Array<Notification_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
