/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model.impl;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommerceDiscount in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountCacheModel
	implements CacheModel<CommerceDiscount>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceDiscountCacheModel)) {
			return false;
		}

		CommerceDiscountCacheModel commerceDiscountCacheModel =
			(CommerceDiscountCacheModel)object;

		if ((commerceDiscountId ==
				commerceDiscountCacheModel.commerceDiscountId) &&
			(mvccVersion == commerceDiscountCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceDiscountId);

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
		StringBundler sb = new StringBundler(67);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceDiscountId=");
		sb.append(commerceDiscountId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", title=");
		sb.append(title);
		sb.append(", target=");
		sb.append(target);
		sb.append(", useCouponCode=");
		sb.append(useCouponCode);
		sb.append(", couponCode=");
		sb.append(couponCode);
		sb.append(", usePercentage=");
		sb.append(usePercentage);
		sb.append(", maximumDiscountAmount=");
		sb.append(maximumDiscountAmount);
		sb.append(", level=");
		sb.append(level);
		sb.append(", level1=");
		sb.append(level1);
		sb.append(", level2=");
		sb.append(level2);
		sb.append(", level3=");
		sb.append(level3);
		sb.append(", level4=");
		sb.append(level4);
		sb.append(", limitationType=");
		sb.append(limitationType);
		sb.append(", limitationTimes=");
		sb.append(limitationTimes);
		sb.append(", limitationTimesPerAccount=");
		sb.append(limitationTimesPerAccount);
		sb.append(", numberOfUse=");
		sb.append(numberOfUse);
		sb.append(", rulesConjunction=");
		sb.append(rulesConjunction);
		sb.append(", active=");
		sb.append(active);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceDiscount toEntityModel() {
		CommerceDiscountImpl commerceDiscountImpl = new CommerceDiscountImpl();

		commerceDiscountImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceDiscountImpl.setUuid("");
		}
		else {
			commerceDiscountImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceDiscountImpl.setExternalReferenceCode("");
		}
		else {
			commerceDiscountImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceDiscountImpl.setCommerceDiscountId(commerceDiscountId);
		commerceDiscountImpl.setCompanyId(companyId);
		commerceDiscountImpl.setUserId(userId);

		if (userName == null) {
			commerceDiscountImpl.setUserName("");
		}
		else {
			commerceDiscountImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setCreateDate(null);
		}
		else {
			commerceDiscountImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setModifiedDate(null);
		}
		else {
			commerceDiscountImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			commerceDiscountImpl.setTitle("");
		}
		else {
			commerceDiscountImpl.setTitle(title);
		}

		if (target == null) {
			commerceDiscountImpl.setTarget("");
		}
		else {
			commerceDiscountImpl.setTarget(target);
		}

		commerceDiscountImpl.setUseCouponCode(useCouponCode);

		if (couponCode == null) {
			commerceDiscountImpl.setCouponCode("");
		}
		else {
			commerceDiscountImpl.setCouponCode(couponCode);
		}

		commerceDiscountImpl.setUsePercentage(usePercentage);
		commerceDiscountImpl.setMaximumDiscountAmount(maximumDiscountAmount);

		if (level == null) {
			commerceDiscountImpl.setLevel("");
		}
		else {
			commerceDiscountImpl.setLevel(level);
		}

		commerceDiscountImpl.setLevel1(level1);
		commerceDiscountImpl.setLevel2(level2);
		commerceDiscountImpl.setLevel3(level3);
		commerceDiscountImpl.setLevel4(level4);

		if (limitationType == null) {
			commerceDiscountImpl.setLimitationType("");
		}
		else {
			commerceDiscountImpl.setLimitationType(limitationType);
		}

		commerceDiscountImpl.setLimitationTimes(limitationTimes);
		commerceDiscountImpl.setLimitationTimesPerAccount(
			limitationTimesPerAccount);
		commerceDiscountImpl.setNumberOfUse(numberOfUse);
		commerceDiscountImpl.setRulesConjunction(rulesConjunction);
		commerceDiscountImpl.setActive(active);

		if (displayDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setDisplayDate(null);
		}
		else {
			commerceDiscountImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setExpirationDate(null);
		}
		else {
			commerceDiscountImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setLastPublishDate(null);
		}
		else {
			commerceDiscountImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commerceDiscountImpl.setStatus(status);
		commerceDiscountImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceDiscountImpl.setStatusByUserName("");
		}
		else {
			commerceDiscountImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceDiscountImpl.setStatusDate(null);
		}
		else {
			commerceDiscountImpl.setStatusDate(new Date(statusDate));
		}

		commerceDiscountImpl.resetOriginalValues();

		return commerceDiscountImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceDiscountId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();
		target = objectInput.readUTF();

		useCouponCode = objectInput.readBoolean();
		couponCode = objectInput.readUTF();

		usePercentage = objectInput.readBoolean();
		maximumDiscountAmount = (BigDecimal)objectInput.readObject();
		level = objectInput.readUTF();
		level1 = (BigDecimal)objectInput.readObject();
		level2 = (BigDecimal)objectInput.readObject();
		level3 = (BigDecimal)objectInput.readObject();
		level4 = (BigDecimal)objectInput.readObject();
		limitationType = objectInput.readUTF();

		limitationTimes = objectInput.readInt();

		limitationTimesPerAccount = objectInput.readInt();

		numberOfUse = objectInput.readInt();

		rulesConjunction = objectInput.readBoolean();

		active = objectInput.readBoolean();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceDiscountId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (target == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(target);
		}

		objectOutput.writeBoolean(useCouponCode);

		if (couponCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(couponCode);
		}

		objectOutput.writeBoolean(usePercentage);
		objectOutput.writeObject(maximumDiscountAmount);

		if (level == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(level);
		}

		objectOutput.writeObject(level1);
		objectOutput.writeObject(level2);
		objectOutput.writeObject(level3);
		objectOutput.writeObject(level4);

		if (limitationType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(limitationType);
		}

		objectOutput.writeInt(limitationTimes);

		objectOutput.writeInt(limitationTimesPerAccount);

		objectOutput.writeInt(numberOfUse);

		objectOutput.writeBoolean(rulesConjunction);

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long commerceDiscountId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String target;
	public boolean useCouponCode;
	public String couponCode;
	public boolean usePercentage;
	public BigDecimal maximumDiscountAmount;
	public String level;
	public BigDecimal level1;
	public BigDecimal level2;
	public BigDecimal level3;
	public BigDecimal level4;
	public String limitationType;
	public int limitationTimes;
	public int limitationTimesPerAccount;
	public int numberOfUse;
	public boolean rulesConjunction;
	public boolean active;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}