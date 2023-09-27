/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceDiscount}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscount
 * @generated
 */
public class CommerceDiscountWrapper
	extends BaseModelWrapper<CommerceDiscount>
	implements CommerceDiscount, ModelWrapper<CommerceDiscount> {

	public CommerceDiscountWrapper(CommerceDiscount commerceDiscount) {
		super(commerceDiscount);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceDiscountId", getCommerceDiscountId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("title", getTitle());
		attributes.put("target", getTarget());
		attributes.put("useCouponCode", isUseCouponCode());
		attributes.put("couponCode", getCouponCode());
		attributes.put("usePercentage", isUsePercentage());
		attributes.put("maximumDiscountAmount", getMaximumDiscountAmount());
		attributes.put("level", getLevel());
		attributes.put("level1", getLevel1());
		attributes.put("level2", getLevel2());
		attributes.put("level3", getLevel3());
		attributes.put("level4", getLevel4());
		attributes.put("limitationType", getLimitationType());
		attributes.put("limitationTimes", getLimitationTimes());
		attributes.put(
			"limitationTimesPerAccount", getLimitationTimesPerAccount());
		attributes.put("numberOfUse", getNumberOfUse());
		attributes.put("rulesConjunction", isRulesConjunction());
		attributes.put("active", isActive());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceDiscountId = (Long)attributes.get("commerceDiscountId");

		if (commerceDiscountId != null) {
			setCommerceDiscountId(commerceDiscountId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String target = (String)attributes.get("target");

		if (target != null) {
			setTarget(target);
		}

		Boolean useCouponCode = (Boolean)attributes.get("useCouponCode");

		if (useCouponCode != null) {
			setUseCouponCode(useCouponCode);
		}

		String couponCode = (String)attributes.get("couponCode");

		if (couponCode != null) {
			setCouponCode(couponCode);
		}

		Boolean usePercentage = (Boolean)attributes.get("usePercentage");

		if (usePercentage != null) {
			setUsePercentage(usePercentage);
		}

		BigDecimal maximumDiscountAmount = (BigDecimal)attributes.get(
			"maximumDiscountAmount");

		if (maximumDiscountAmount != null) {
			setMaximumDiscountAmount(maximumDiscountAmount);
		}

		String level = (String)attributes.get("level");

		if (level != null) {
			setLevel(level);
		}

		BigDecimal level1 = (BigDecimal)attributes.get("level1");

		if (level1 != null) {
			setLevel1(level1);
		}

		BigDecimal level2 = (BigDecimal)attributes.get("level2");

		if (level2 != null) {
			setLevel2(level2);
		}

		BigDecimal level3 = (BigDecimal)attributes.get("level3");

		if (level3 != null) {
			setLevel3(level3);
		}

		BigDecimal level4 = (BigDecimal)attributes.get("level4");

		if (level4 != null) {
			setLevel4(level4);
		}

		String limitationType = (String)attributes.get("limitationType");

		if (limitationType != null) {
			setLimitationType(limitationType);
		}

		Integer limitationTimes = (Integer)attributes.get("limitationTimes");

		if (limitationTimes != null) {
			setLimitationTimes(limitationTimes);
		}

		Integer limitationTimesPerAccount = (Integer)attributes.get(
			"limitationTimesPerAccount");

		if (limitationTimesPerAccount != null) {
			setLimitationTimesPerAccount(limitationTimesPerAccount);
		}

		Integer numberOfUse = (Integer)attributes.get("numberOfUse");

		if (numberOfUse != null) {
			setNumberOfUse(numberOfUse);
		}

		Boolean rulesConjunction = (Boolean)attributes.get("rulesConjunction");

		if (rulesConjunction != null) {
			setRulesConjunction(rulesConjunction);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public CommerceDiscount cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this commerce discount.
	 *
	 * @return the active of this commerce discount
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public java.util.List<CommerceDiscountCommerceAccountGroupRel>
		getCommerceDiscountCommerceAccountGroupRels() {

		return model.getCommerceDiscountCommerceAccountGroupRels();
	}

	/**
	 * Returns the commerce discount ID of this commerce discount.
	 *
	 * @return the commerce discount ID of this commerce discount
	 */
	@Override
	public long getCommerceDiscountId() {
		return model.getCommerceDiscountId();
	}

	/**
	 * Returns the company ID of this commerce discount.
	 *
	 * @return the company ID of this commerce discount
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the coupon code of this commerce discount.
	 *
	 * @return the coupon code of this commerce discount
	 */
	@Override
	public String getCouponCode() {
		return model.getCouponCode();
	}

	/**
	 * Returns the create date of this commerce discount.
	 *
	 * @return the create date of this commerce discount
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the display date of this commerce discount.
	 *
	 * @return the display date of this commerce discount
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the expiration date of this commerce discount.
	 *
	 * @return the expiration date of this commerce discount
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this commerce discount.
	 *
	 * @return the external reference code of this commerce discount
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the last publish date of this commerce discount.
	 *
	 * @return the last publish date of this commerce discount
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the level of this commerce discount.
	 *
	 * @return the level of this commerce discount
	 */
	@Override
	public String getLevel() {
		return model.getLevel();
	}

	/**
	 * Returns the level1 of this commerce discount.
	 *
	 * @return the level1 of this commerce discount
	 */
	@Override
	public BigDecimal getLevel1() {
		return model.getLevel1();
	}

	/**
	 * Returns the level2 of this commerce discount.
	 *
	 * @return the level2 of this commerce discount
	 */
	@Override
	public BigDecimal getLevel2() {
		return model.getLevel2();
	}

	/**
	 * Returns the level3 of this commerce discount.
	 *
	 * @return the level3 of this commerce discount
	 */
	@Override
	public BigDecimal getLevel3() {
		return model.getLevel3();
	}

	/**
	 * Returns the level4 of this commerce discount.
	 *
	 * @return the level4 of this commerce discount
	 */
	@Override
	public BigDecimal getLevel4() {
		return model.getLevel4();
	}

	/**
	 * Returns the limitation times of this commerce discount.
	 *
	 * @return the limitation times of this commerce discount
	 */
	@Override
	public int getLimitationTimes() {
		return model.getLimitationTimes();
	}

	/**
	 * Returns the limitation times per account of this commerce discount.
	 *
	 * @return the limitation times per account of this commerce discount
	 */
	@Override
	public int getLimitationTimesPerAccount() {
		return model.getLimitationTimesPerAccount();
	}

	/**
	 * Returns the limitation type of this commerce discount.
	 *
	 * @return the limitation type of this commerce discount
	 */
	@Override
	public String getLimitationType() {
		return model.getLimitationType();
	}

	/**
	 * Returns the maximum discount amount of this commerce discount.
	 *
	 * @return the maximum discount amount of this commerce discount
	 */
	@Override
	public BigDecimal getMaximumDiscountAmount() {
		return model.getMaximumDiscountAmount();
	}

	/**
	 * Returns the modified date of this commerce discount.
	 *
	 * @return the modified date of this commerce discount
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce discount.
	 *
	 * @return the mvcc version of this commerce discount
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the number of use of this commerce discount.
	 *
	 * @return the number of use of this commerce discount
	 */
	@Override
	public int getNumberOfUse() {
		return model.getNumberOfUse();
	}

	/**
	 * Returns the primary key of this commerce discount.
	 *
	 * @return the primary key of this commerce discount
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the rules conjunction of this commerce discount.
	 *
	 * @return the rules conjunction of this commerce discount
	 */
	@Override
	public boolean getRulesConjunction() {
		return model.getRulesConjunction();
	}

	/**
	 * Returns the status of this commerce discount.
	 *
	 * @return the status of this commerce discount
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce discount.
	 *
	 * @return the status by user ID of this commerce discount
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce discount.
	 *
	 * @return the status by user name of this commerce discount
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce discount.
	 *
	 * @return the status by user uuid of this commerce discount
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce discount.
	 *
	 * @return the status date of this commerce discount
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the target of this commerce discount.
	 *
	 * @return the target of this commerce discount
	 */
	@Override
	public String getTarget() {
		return model.getTarget();
	}

	/**
	 * Returns the title of this commerce discount.
	 *
	 * @return the title of this commerce discount
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the use coupon code of this commerce discount.
	 *
	 * @return the use coupon code of this commerce discount
	 */
	@Override
	public boolean getUseCouponCode() {
		return model.getUseCouponCode();
	}

	/**
	 * Returns the use percentage of this commerce discount.
	 *
	 * @return the use percentage of this commerce discount
	 */
	@Override
	public boolean getUsePercentage() {
		return model.getUsePercentage();
	}

	/**
	 * Returns the user ID of this commerce discount.
	 *
	 * @return the user ID of this commerce discount
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce discount.
	 *
	 * @return the user name of this commerce discount
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce discount.
	 *
	 * @return the user uuid of this commerce discount
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce discount.
	 *
	 * @return the uuid of this commerce discount
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is active.
	 *
	 * @return <code>true</code> if this commerce discount is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is approved.
	 *
	 * @return <code>true</code> if this commerce discount is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is denied.
	 *
	 * @return <code>true</code> if this commerce discount is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is a draft.
	 *
	 * @return <code>true</code> if this commerce discount is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is expired.
	 *
	 * @return <code>true</code> if this commerce discount is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is inactive.
	 *
	 * @return <code>true</code> if this commerce discount is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is incomplete.
	 *
	 * @return <code>true</code> if this commerce discount is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is pending.
	 *
	 * @return <code>true</code> if this commerce discount is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is rules conjunction.
	 *
	 * @return <code>true</code> if this commerce discount is rules conjunction; <code>false</code> otherwise
	 */
	@Override
	public boolean isRulesConjunction() {
		return model.isRulesConjunction();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is scheduled.
	 *
	 * @return <code>true</code> if this commerce discount is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is use coupon code.
	 *
	 * @return <code>true</code> if this commerce discount is use coupon code; <code>false</code> otherwise
	 */
	@Override
	public boolean isUseCouponCode() {
		return model.isUseCouponCode();
	}

	/**
	 * Returns <code>true</code> if this commerce discount is use percentage.
	 *
	 * @return <code>true</code> if this commerce discount is use percentage; <code>false</code> otherwise
	 */
	@Override
	public boolean isUsePercentage() {
		return model.isUsePercentage();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this commerce discount is active.
	 *
	 * @param active the active of this commerce discount
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the commerce discount ID of this commerce discount.
	 *
	 * @param commerceDiscountId the commerce discount ID of this commerce discount
	 */
	@Override
	public void setCommerceDiscountId(long commerceDiscountId) {
		model.setCommerceDiscountId(commerceDiscountId);
	}

	/**
	 * Sets the company ID of this commerce discount.
	 *
	 * @param companyId the company ID of this commerce discount
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the coupon code of this commerce discount.
	 *
	 * @param couponCode the coupon code of this commerce discount
	 */
	@Override
	public void setCouponCode(String couponCode) {
		model.setCouponCode(couponCode);
	}

	/**
	 * Sets the create date of this commerce discount.
	 *
	 * @param createDate the create date of this commerce discount
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the display date of this commerce discount.
	 *
	 * @param displayDate the display date of this commerce discount
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the expiration date of this commerce discount.
	 *
	 * @param expirationDate the expiration date of this commerce discount
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this commerce discount.
	 *
	 * @param externalReferenceCode the external reference code of this commerce discount
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the last publish date of this commerce discount.
	 *
	 * @param lastPublishDate the last publish date of this commerce discount
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the level of this commerce discount.
	 *
	 * @param level the level of this commerce discount
	 */
	@Override
	public void setLevel(String level) {
		model.setLevel(level);
	}

	/**
	 * Sets the level1 of this commerce discount.
	 *
	 * @param level1 the level1 of this commerce discount
	 */
	@Override
	public void setLevel1(BigDecimal level1) {
		model.setLevel1(level1);
	}

	/**
	 * Sets the level2 of this commerce discount.
	 *
	 * @param level2 the level2 of this commerce discount
	 */
	@Override
	public void setLevel2(BigDecimal level2) {
		model.setLevel2(level2);
	}

	/**
	 * Sets the level3 of this commerce discount.
	 *
	 * @param level3 the level3 of this commerce discount
	 */
	@Override
	public void setLevel3(BigDecimal level3) {
		model.setLevel3(level3);
	}

	/**
	 * Sets the level4 of this commerce discount.
	 *
	 * @param level4 the level4 of this commerce discount
	 */
	@Override
	public void setLevel4(BigDecimal level4) {
		model.setLevel4(level4);
	}

	/**
	 * Sets the limitation times of this commerce discount.
	 *
	 * @param limitationTimes the limitation times of this commerce discount
	 */
	@Override
	public void setLimitationTimes(int limitationTimes) {
		model.setLimitationTimes(limitationTimes);
	}

	/**
	 * Sets the limitation times per account of this commerce discount.
	 *
	 * @param limitationTimesPerAccount the limitation times per account of this commerce discount
	 */
	@Override
	public void setLimitationTimesPerAccount(int limitationTimesPerAccount) {
		model.setLimitationTimesPerAccount(limitationTimesPerAccount);
	}

	/**
	 * Sets the limitation type of this commerce discount.
	 *
	 * @param limitationType the limitation type of this commerce discount
	 */
	@Override
	public void setLimitationType(String limitationType) {
		model.setLimitationType(limitationType);
	}

	/**
	 * Sets the maximum discount amount of this commerce discount.
	 *
	 * @param maximumDiscountAmount the maximum discount amount of this commerce discount
	 */
	@Override
	public void setMaximumDiscountAmount(BigDecimal maximumDiscountAmount) {
		model.setMaximumDiscountAmount(maximumDiscountAmount);
	}

	/**
	 * Sets the modified date of this commerce discount.
	 *
	 * @param modifiedDate the modified date of this commerce discount
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce discount.
	 *
	 * @param mvccVersion the mvcc version of this commerce discount
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the number of use of this commerce discount.
	 *
	 * @param numberOfUse the number of use of this commerce discount
	 */
	@Override
	public void setNumberOfUse(int numberOfUse) {
		model.setNumberOfUse(numberOfUse);
	}

	/**
	 * Sets the primary key of this commerce discount.
	 *
	 * @param primaryKey the primary key of this commerce discount
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this commerce discount is rules conjunction.
	 *
	 * @param rulesConjunction the rules conjunction of this commerce discount
	 */
	@Override
	public void setRulesConjunction(boolean rulesConjunction) {
		model.setRulesConjunction(rulesConjunction);
	}

	/**
	 * Sets the status of this commerce discount.
	 *
	 * @param status the status of this commerce discount
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce discount.
	 *
	 * @param statusByUserId the status by user ID of this commerce discount
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce discount.
	 *
	 * @param statusByUserName the status by user name of this commerce discount
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce discount.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce discount
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce discount.
	 *
	 * @param statusDate the status date of this commerce discount
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the target of this commerce discount.
	 *
	 * @param target the target of this commerce discount
	 */
	@Override
	public void setTarget(String target) {
		model.setTarget(target);
	}

	/**
	 * Sets the title of this commerce discount.
	 *
	 * @param title the title of this commerce discount
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets whether this commerce discount is use coupon code.
	 *
	 * @param useCouponCode the use coupon code of this commerce discount
	 */
	@Override
	public void setUseCouponCode(boolean useCouponCode) {
		model.setUseCouponCode(useCouponCode);
	}

	/**
	 * Sets whether this commerce discount is use percentage.
	 *
	 * @param usePercentage the use percentage of this commerce discount
	 */
	@Override
	public void setUsePercentage(boolean usePercentage) {
		model.setUsePercentage(usePercentage);
	}

	/**
	 * Sets the user ID of this commerce discount.
	 *
	 * @param userId the user ID of this commerce discount
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce discount.
	 *
	 * @param userName the user name of this commerce discount
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce discount.
	 *
	 * @param userUuid the user uuid of this commerce discount
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce discount.
	 *
	 * @param uuid the uuid of this commerce discount
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceDiscountWrapper wrap(CommerceDiscount commerceDiscount) {
		return new CommerceDiscountWrapper(commerceDiscount);
	}

}