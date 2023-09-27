/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model.impl;

import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing NotificationTemplateAttachment in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationTemplateAttachmentCacheModel
	implements CacheModel<NotificationTemplateAttachment>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationTemplateAttachmentCacheModel)) {
			return false;
		}

		NotificationTemplateAttachmentCacheModel
			notificationTemplateAttachmentCacheModel =
				(NotificationTemplateAttachmentCacheModel)object;

		if ((notificationTemplateAttachmentId ==
				notificationTemplateAttachmentCacheModel.
					notificationTemplateAttachmentId) &&
			(mvccVersion ==
				notificationTemplateAttachmentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationTemplateAttachmentId);

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
		sb.append(", notificationTemplateAttachmentId=");
		sb.append(notificationTemplateAttachmentId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", notificationTemplateId=");
		sb.append(notificationTemplateId);
		sb.append(", objectFieldId=");
		sb.append(objectFieldId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationTemplateAttachment toEntityModel() {
		NotificationTemplateAttachmentImpl notificationTemplateAttachmentImpl =
			new NotificationTemplateAttachmentImpl();

		notificationTemplateAttachmentImpl.setMvccVersion(mvccVersion);
		notificationTemplateAttachmentImpl.setNotificationTemplateAttachmentId(
			notificationTemplateAttachmentId);
		notificationTemplateAttachmentImpl.setCompanyId(companyId);
		notificationTemplateAttachmentImpl.setNotificationTemplateId(
			notificationTemplateId);
		notificationTemplateAttachmentImpl.setObjectFieldId(objectFieldId);

		notificationTemplateAttachmentImpl.resetOriginalValues();

		return notificationTemplateAttachmentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		notificationTemplateAttachmentId = objectInput.readLong();

		companyId = objectInput.readLong();

		notificationTemplateId = objectInput.readLong();

		objectFieldId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(notificationTemplateAttachmentId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(notificationTemplateId);

		objectOutput.writeLong(objectFieldId);
	}

	public long mvccVersion;
	public long notificationTemplateAttachmentId;
	public long companyId;
	public long notificationTemplateId;
	public long objectFieldId;

}