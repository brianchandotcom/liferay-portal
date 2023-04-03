/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.model.impl;

import com.liferay.osb.faro.model.FaroProjectEmailAddressDomain;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FaroProjectEmailAddressDomain in entity cache.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroProjectEmailAddressDomainCacheModel
	implements CacheModel<FaroProjectEmailAddressDomain>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroProjectEmailAddressDomainCacheModel)) {
			return false;
		}

		FaroProjectEmailAddressDomainCacheModel
			faroProjectEmailAddressDomainCacheModel =
				(FaroProjectEmailAddressDomainCacheModel)object;

		if (faroProjectEmailAddressDomainId ==
				faroProjectEmailAddressDomainCacheModel.
					faroProjectEmailAddressDomainId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, faroProjectEmailAddressDomainId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{faroProjectEmailAddressDomainId=");
		sb.append(faroProjectEmailAddressDomainId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", faroProjectId=");
		sb.append(faroProjectId);
		sb.append(", emailAddressDomain=");
		sb.append(emailAddressDomain);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FaroProjectEmailAddressDomain toEntityModel() {
		FaroProjectEmailAddressDomainImpl faroProjectEmailAddressDomainImpl =
			new FaroProjectEmailAddressDomainImpl();

		faroProjectEmailAddressDomainImpl.setFaroProjectEmailAddressDomainId(
			faroProjectEmailAddressDomainId);
		faroProjectEmailAddressDomainImpl.setGroupId(groupId);
		faroProjectEmailAddressDomainImpl.setFaroProjectId(faroProjectId);

		if (emailAddressDomain == null) {
			faroProjectEmailAddressDomainImpl.setEmailAddressDomain("");
		}
		else {
			faroProjectEmailAddressDomainImpl.setEmailAddressDomain(
				emailAddressDomain);
		}

		faroProjectEmailAddressDomainImpl.resetOriginalValues();

		return faroProjectEmailAddressDomainImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		faroProjectEmailAddressDomainId = objectInput.readLong();

		groupId = objectInput.readLong();

		faroProjectId = objectInput.readLong();
		emailAddressDomain = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(faroProjectEmailAddressDomainId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(faroProjectId);

		if (emailAddressDomain == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(emailAddressDomain);
		}
	}

	public long faroProjectEmailAddressDomainId;
	public long groupId;
	public long faroProjectId;
	public String emailAddressDomain;

}