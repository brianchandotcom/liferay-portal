/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.workflow;

/**
 * @author Jorge Ferrer
 * @author Zsolt Berentey
 */
public class WorkflowConstants {

	public static final int ACTION_PUBLISH = 1;

	public static final int ACTION_SAVE_DRAFT = 2;

	public static final String CONTEXT_COMMAND = "command";

	public static final String CONTEXT_COMPANY_ID = "companyId";

	public static final String CONTEXT_ENTRY_CLASS_NAME = "entryClassName";

	public static final String CONTEXT_ENTRY_CLASS_PK = "entryClassPK";

	public static final String CONTEXT_ENTRY_TYPE = "entryType";

	public static final String CONTEXT_GROUP_ID = "groupId";

	public static final String CONTEXT_NOTIFICATION_SENDER_ADDRESS =
		"notificationSenderAddress";

	public static final String CONTEXT_NOTIFICATION_SENDER_NAME =
		"notificationSenderName";

	public static final String CONTEXT_NOTIFICATION_SUBJECT =
		"notificationSubject";

	public static final String CONTEXT_SERVICE_CONTEXT = "serviceContext";

	public static final String CONTEXT_TASK_COMMENTS = "taskComments";

	public static final String CONTEXT_TRANSITION_NAME = "transitionName";

	public static final String CONTEXT_URL = "url";

	public static final String CONTEXT_USER_ID = "userId";

	public static final String CONTEXT_USER_PORTRAIT_URL = "userPortraitURL";

	public static final String CONTEXT_USER_URL = "userURL";

	public static final long DEFAULT_GROUP_ID = 0;

	public static final String LABEL_ANY = "any";

	public static final String LABEL_APPROVED = "approved";

	public static final String LABEL_DENIED = "denied";

	public static final String LABEL_DRAFT = "draft";

	public static final String LABEL_EXPIRED = "expired";

	public static final String LABEL_IN_TRASH = "in-trash";

	public static final String LABEL_INACTIVE = "inactive";

	public static final String LABEL_INCOMPLETE = "incomplete";

	public static final String LABEL_PENDING = "pending";

	public static final String LABEL_SCHEDULED = "scheduled";

	public static final String SERVICE_NAME = "com.liferay.portal.workflow";

	public static final int STATUS_ANY = -1;

	public static final int STATUS_APPROVED = 0;

	public static final int STATUS_DENIED = 4;

	public static final int STATUS_DRAFT = 2;

	public static final int STATUS_EXPIRED = 3;

	public static final int STATUS_IN_TRASH = 8;

	public static final int STATUS_INACTIVE = 5;

	public static final int STATUS_INCOMPLETE = 6;

	public static final int STATUS_PENDING = 1;

	public static final int STATUS_SCHEDULED = 7;

	public static final int TYPE_ASSIGN = 10000;

	public static final int TYPE_COMPLETE = 10001;

	public static int getLabelStatus(String label) {
		if (label.equals(LABEL_ANY)) {
			return STATUS_ANY;
		}
		else if (label.equals(LABEL_APPROVED)) {
			return STATUS_APPROVED;
		}
		else if (label.equals(LABEL_DENIED)) {
			return STATUS_DENIED;
		}
		else if (label.equals(LABEL_DRAFT)) {
			return STATUS_DRAFT;
		}
		else if (label.equals(LABEL_EXPIRED)) {
			return STATUS_EXPIRED;
		}
		else if (label.equals(LABEL_INACTIVE)) {
			return STATUS_INACTIVE;
		}
		else if (label.equals(LABEL_IN_TRASH)) {
			return STATUS_IN_TRASH;
		}
		else if (label.equals(LABEL_INCOMPLETE)) {
			return STATUS_INCOMPLETE;
		}
		else if (label.equals(LABEL_PENDING)) {
			return STATUS_PENDING;
		}
		else if (label.equals(LABEL_SCHEDULED)) {
			return STATUS_SCHEDULED;
		}

		return STATUS_ANY;
	}

	public static String getStatusCssClass(int status) {
		if (status == STATUS_ANY) {
			return LABEL_ANY;
		}
		else if (status == STATUS_APPROVED) {
			return LABEL_APPROVED;
		}
		else if (status == STATUS_DENIED) {
			return LABEL_DENIED;
		}
		else if (status == STATUS_DRAFT) {
			return LABEL_DRAFT;
		}
		else if (status == STATUS_EXPIRED) {
			return LABEL_EXPIRED;
		}
		else if (status == STATUS_IN_TRASH) {
			return LABEL_IN_TRASH;
		}
		else if (status == STATUS_INACTIVE) {
			return LABEL_INACTIVE;
		}
		else if (status == STATUS_INCOMPLETE) {
			return LABEL_INCOMPLETE;
		}
		else if (status == STATUS_PENDING) {
			return LABEL_PENDING;
		}
		else if (status == STATUS_SCHEDULED) {
			return LABEL_SCHEDULED;
		}

		return LABEL_ANY;
	}

	public static String getStatusLabel(int status) {
		if (status == STATUS_ANY) {
			return LABEL_ANY;
		}
		else if (status == STATUS_APPROVED) {
			return LABEL_APPROVED;
		}
		else if (status == STATUS_DENIED) {
			return LABEL_DENIED;
		}
		else if (status == STATUS_DRAFT) {
			return LABEL_DRAFT;
		}
		else if (status == STATUS_EXPIRED) {
			return LABEL_EXPIRED;
		}
		else if (status == STATUS_IN_TRASH) {
			return LABEL_IN_TRASH;
		}
		else if (status == STATUS_INACTIVE) {
			return LABEL_INACTIVE;
		}
		else if (status == STATUS_INCOMPLETE) {
			return LABEL_INCOMPLETE;
		}
		else if (status == STATUS_PENDING) {
			return LABEL_PENDING;
		}
		else if (status == STATUS_SCHEDULED) {
			return LABEL_SCHEDULED;
		}

		return LABEL_ANY;
	}

}