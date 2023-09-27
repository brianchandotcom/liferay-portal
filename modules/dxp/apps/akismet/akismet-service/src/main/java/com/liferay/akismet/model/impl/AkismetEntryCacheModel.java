/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.model.impl;

import com.liferay.akismet.model.AkismetEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AkismetEntry in entity cache.
 *
 * @author Jamie Sammons
 * @generated
 */
public class AkismetEntryCacheModel
	implements CacheModel<AkismetEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AkismetEntryCacheModel)) {
			return false;
		}

		AkismetEntryCacheModel akismetEntryCacheModel =
			(AkismetEntryCacheModel)object;

		if (akismetEntryId == akismetEntryCacheModel.akismetEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, akismetEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{akismetEntryId=");
		sb.append(akismetEntryId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", type=");
		sb.append(type);
		sb.append(", permalink=");
		sb.append(permalink);
		sb.append(", referrer=");
		sb.append(referrer);
		sb.append(", userAgent=");
		sb.append(userAgent);
		sb.append(", userIP=");
		sb.append(userIP);
		sb.append(", userURL=");
		sb.append(userURL);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AkismetEntry toEntityModel() {
		AkismetEntryImpl akismetEntryImpl = new AkismetEntryImpl();

		akismetEntryImpl.setAkismetEntryId(akismetEntryId);

		if (modifiedDate == Long.MIN_VALUE) {
			akismetEntryImpl.setModifiedDate(null);
		}
		else {
			akismetEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		akismetEntryImpl.setClassNameId(classNameId);
		akismetEntryImpl.setClassPK(classPK);

		if (type == null) {
			akismetEntryImpl.setType("");
		}
		else {
			akismetEntryImpl.setType(type);
		}

		if (permalink == null) {
			akismetEntryImpl.setPermalink("");
		}
		else {
			akismetEntryImpl.setPermalink(permalink);
		}

		if (referrer == null) {
			akismetEntryImpl.setReferrer("");
		}
		else {
			akismetEntryImpl.setReferrer(referrer);
		}

		if (userAgent == null) {
			akismetEntryImpl.setUserAgent("");
		}
		else {
			akismetEntryImpl.setUserAgent(userAgent);
		}

		if (userIP == null) {
			akismetEntryImpl.setUserIP("");
		}
		else {
			akismetEntryImpl.setUserIP(userIP);
		}

		if (userURL == null) {
			akismetEntryImpl.setUserURL("");
		}
		else {
			akismetEntryImpl.setUserURL(userURL);
		}

		akismetEntryImpl.resetOriginalValues();

		return akismetEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		akismetEntryId = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		type = objectInput.readUTF();
		permalink = objectInput.readUTF();
		referrer = objectInput.readUTF();
		userAgent = objectInput.readUTF();
		userIP = objectInput.readUTF();
		userURL = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(akismetEntryId);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (permalink == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(permalink);
		}

		if (referrer == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(referrer);
		}

		if (userAgent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userAgent);
		}

		if (userIP == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userIP);
		}

		if (userURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userURL);
		}
	}

	public long akismetEntryId;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String type;
	public String permalink;
	public String referrer;
	public String userAgent;
	public String userIP;
	public String userURL;

}