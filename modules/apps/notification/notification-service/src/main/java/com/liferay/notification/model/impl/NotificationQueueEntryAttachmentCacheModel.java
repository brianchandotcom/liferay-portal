/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model.impl;

import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing NotificationQueueEntryAttachment in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationQueueEntryAttachmentCacheModel
	implements CacheModel<NotificationQueueEntryAttachment>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationQueueEntryAttachmentCacheModel)) {
			return false;
		}

		NotificationQueueEntryAttachmentCacheModel
			notificationQueueEntryAttachmentCacheModel =
				(NotificationQueueEntryAttachmentCacheModel)object;

		if ((notificationQueueEntryAttachmentId ==
				notificationQueueEntryAttachmentCacheModel.
					notificationQueueEntryAttachmentId) &&
			(mvccVersion ==
				notificationQueueEntryAttachmentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationQueueEntryAttachmentId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", notificationQueueEntryAttachmentId=");
		sb.append(notificationQueueEntryAttachmentId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", notificationQueueEntryId=");
		sb.append(notificationQueueEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationQueueEntryAttachment toEntityModel() {
		NotificationQueueEntryAttachmentImpl
			notificationQueueEntryAttachmentImpl =
				new NotificationQueueEntryAttachmentImpl();

		notificationQueueEntryAttachmentImpl.setMvccVersion(mvccVersion);
		notificationQueueEntryAttachmentImpl.
			setNotificationQueueEntryAttachmentId(
				notificationQueueEntryAttachmentId);
		notificationQueueEntryAttachmentImpl.setCompanyId(companyId);
		notificationQueueEntryAttachmentImpl.setFileEntryId(fileEntryId);
		notificationQueueEntryAttachmentImpl.setNotificationQueueEntryId(
			notificationQueueEntryId);

		notificationQueueEntryAttachmentImpl.resetOriginalValues();

		return notificationQueueEntryAttachmentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		notificationQueueEntryAttachmentId = objectInput.readLong();

		companyId = objectInput.readLong();

		fileEntryId = objectInput.readLong();

		notificationQueueEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(notificationQueueEntryAttachmentId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(fileEntryId);

		objectOutput.writeLong(notificationQueueEntryId);
	}

	public long mvccVersion;
	public long notificationQueueEntryAttachmentId;
	public long companyId;
	public long fileEntryId;
	public long notificationQueueEntryId;

}