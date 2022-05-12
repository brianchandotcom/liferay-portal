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

package com.liferay.headless.commerce.admin.pricing.dto.v2_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
@GraphQLName("Discount")
@JsonFilter("Liferay.Vulcan")
@Schema(
	requiredProperties = {
		"level", "limitationType", "target", "title", "usePercentage"
	}
)
@XmlRootElement(name = "Discount")
public class Discount implements Serializable {

	public static Discount toDTO(String json) {
		return ObjectMapperUtil.readValue(Discount.class, json);
	}

	public static Discount unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(Discount.class, json);
	}

	@Schema
	@Valid
	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	@JsonIgnore
	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Map<String, Map<String, String>> actions;

	@Schema(example = "true")
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean active;

	@Schema
	public String getAmountFormatted() {
		return amountFormatted;
	}

	public void setAmountFormatted(String amountFormatted) {
		this.amountFormatted = amountFormatted;
	}

	@JsonIgnore
	public void setAmountFormatted(
		UnsafeSupplier<String, Exception> amountFormattedUnsafeSupplier) {

		try {
			amountFormatted = amountFormattedUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String amountFormatted;

	@Schema(example = "SAVE20")
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@JsonIgnore
	public void setCouponCode(
		UnsafeSupplier<String, Exception> couponCodeUnsafeSupplier) {

		try {
			couponCode = couponCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String couponCode;

	@Schema
	@Valid
	public Map<String, ?> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, ?> customFields) {
		this.customFields = customFields;
	}

	@JsonIgnore
	public void setCustomFields(
		UnsafeSupplier<Map<String, ?>, Exception> customFieldsUnsafeSupplier) {

		try {
			customFields = customFieldsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Map<String, ?> customFields;

	@Schema
	@Valid
	public DiscountAccountGroup[] getDiscountAccountGroups() {
		return discountAccountGroups;
	}

	public void setDiscountAccountGroups(
		DiscountAccountGroup[] discountAccountGroups) {

		this.discountAccountGroups = discountAccountGroups;
	}

	@JsonIgnore
	public void setDiscountAccountGroups(
		UnsafeSupplier<DiscountAccountGroup[], Exception>
			discountAccountGroupsUnsafeSupplier) {

		try {
			discountAccountGroups = discountAccountGroupsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DiscountAccountGroup[] discountAccountGroups;

	@Schema
	@Valid
	public DiscountAccount[] getDiscountAccounts() {
		return discountAccounts;
	}

	public void setDiscountAccounts(DiscountAccount[] discountAccounts) {
		this.discountAccounts = discountAccounts;
	}

	@JsonIgnore
	public void setDiscountAccounts(
		UnsafeSupplier<DiscountAccount[], Exception>
			discountAccountsUnsafeSupplier) {

		try {
			discountAccounts = discountAccountsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DiscountAccount[] discountAccounts;

	@Schema
	@Valid
	public DiscountCategory[] getDiscountCategories() {
		return discountCategories;
	}

	public void setDiscountCategories(DiscountCategory[] discountCategories) {
		this.discountCategories = discountCategories;
	}

	@JsonIgnore
	public void setDiscountCategories(
		UnsafeSupplier<DiscountCategory[], Exception>
			discountCategoriesUnsafeSupplier) {

		try {
			discountCategories = discountCategoriesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DiscountCategory[] discountCategories;

	@Schema
	@Valid
	public DiscountChannel[] getDiscountChannels() {
		return discountChannels;
	}

	public void setDiscountChannels(DiscountChannel[] discountChannels) {
		this.discountChannels = discountChannels;
	}

	@JsonIgnore
	public void setDiscountChannels(
		UnsafeSupplier<DiscountChannel[], Exception>
			discountChannelsUnsafeSupplier) {

		try {
			discountChannels = discountChannelsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DiscountChannel[] discountChannels;

	@Schema
	@Valid
	public DiscountOrderType[] getDiscountOrderTypes() {
		return discountOrderTypes;
	}

	public void setDiscountOrderTypes(DiscountOrderType[] discountOrderTypes) {
		this.discountOrderTypes = discountOrderTypes;
	}

	@JsonIgnore
	public void setDiscountOrderTypes(
		UnsafeSupplier<DiscountOrderType[], Exception>
			discountOrderTypesUnsafeSupplier) {

		try {
			discountOrderTypes = discountOrderTypesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DiscountOrderType[] discountOrderTypes;

	@Schema
	@Valid
	public DiscountProductGroup[] getDiscountProductGroups() {
		return discountProductGroups;
	}

	public void setDiscountProductGroups(
		DiscountProductGroup[] discountProductGroups) {

		this.discountProductGroups = discountProductGroups;
	}

	@JsonIgnore
	public void setDiscountProductGroups(
		UnsafeSupplier<DiscountProductGroup[], Exception>
			discountProductGroupsUnsafeSupplier) {

		try {
			discountProductGroups = discountProductGroupsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DiscountProductGroup[] discountProductGroups;

	@Schema
	@Valid
	public DiscountProduct[] getDiscountProducts() {
		return discountProducts;
	}

	public void setDiscountProducts(DiscountProduct[] discountProducts) {
		this.discountProducts = discountProducts;
	}

	@JsonIgnore
	public void setDiscountProducts(
		UnsafeSupplier<DiscountProduct[], Exception>
			discountProductsUnsafeSupplier) {

		try {
			discountProducts = discountProductsUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DiscountProduct[] discountProducts;

	@Schema
	@Valid
	public DiscountRule[] getDiscountRules() {
		return discountRules;
	}

	public void setDiscountRules(DiscountRule[] discountRules) {
		this.discountRules = discountRules;
	}

	@JsonIgnore
	public void setDiscountRules(
		UnsafeSupplier<DiscountRule[], Exception> discountRulesUnsafeSupplier) {

		try {
			discountRules = discountRulesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected DiscountRule[] discountRules;

	@Schema(example = "2017-07-21")
	public Date getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	@JsonIgnore
	public void setDisplayDate(
		UnsafeSupplier<Date, Exception> displayDateUnsafeSupplier) {

		try {
			displayDate = displayDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date displayDate;

	@Schema(example = "2017-08-21")
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@JsonIgnore
	public void setExpirationDate(
		UnsafeSupplier<Date, Exception> expirationDateUnsafeSupplier) {

		try {
			expirationDate = expirationDateUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Date expirationDate;

	@Schema(example = "AB-34098-789-N")
	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	@JsonIgnore
	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String externalReferenceCode;

	@DecimalMin("0")
	@Schema(example = "30130")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	@Schema(example = "L1")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@JsonIgnore
	public void setLevel(
		UnsafeSupplier<String, Exception> levelUnsafeSupplier) {

		try {
			level = levelUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String level;

	@DecimalMin("0")
	@Schema(example = "0")
	public Integer getLimitationTimes() {
		return limitationTimes;
	}

	public void setLimitationTimes(Integer limitationTimes) {
		this.limitationTimes = limitationTimes;
	}

	@JsonIgnore
	public void setLimitationTimes(
		UnsafeSupplier<Integer, Exception> limitationTimesUnsafeSupplier) {

		try {
			limitationTimes = limitationTimesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer limitationTimes;

	@DecimalMin("0")
	@Schema(example = "0")
	public Integer getLimitationTimesPerAccount() {
		return limitationTimesPerAccount;
	}

	public void setLimitationTimesPerAccount(
		Integer limitationTimesPerAccount) {

		this.limitationTimesPerAccount = limitationTimesPerAccount;
	}

	@JsonIgnore
	public void setLimitationTimesPerAccount(
		UnsafeSupplier<Integer, Exception>
			limitationTimesPerAccountUnsafeSupplier) {

		try {
			limitationTimesPerAccount =
				limitationTimesPerAccountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer limitationTimesPerAccount;

	@Schema(example = "unlimited")
	public String getLimitationType() {
		return limitationType;
	}

	public void setLimitationType(String limitationType) {
		this.limitationType = limitationType;
	}

	@JsonIgnore
	public void setLimitationType(
		UnsafeSupplier<String, Exception> limitationTypeUnsafeSupplier) {

		try {
			limitationType = limitationTypeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String limitationType;

	@DecimalMin("0")
	@Schema(example = "25")
	@Valid
	public BigDecimal getMaximumDiscountAmount() {
		return maximumDiscountAmount;
	}

	public void setMaximumDiscountAmount(BigDecimal maximumDiscountAmount) {
		this.maximumDiscountAmount = maximumDiscountAmount;
	}

	@JsonIgnore
	public void setMaximumDiscountAmount(
		UnsafeSupplier<BigDecimal, Exception>
			maximumDiscountAmountUnsafeSupplier) {

		try {
			maximumDiscountAmount = maximumDiscountAmountUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal maximumDiscountAmount;

	@Schema(example = "true")
	public Boolean getNeverExpire() {
		return neverExpire;
	}

	public void setNeverExpire(Boolean neverExpire) {
		this.neverExpire = neverExpire;
	}

	@JsonIgnore
	public void setNeverExpire(
		UnsafeSupplier<Boolean, Exception> neverExpireUnsafeSupplier) {

		try {
			neverExpire = neverExpireUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean neverExpire;

	@DecimalMin("0")
	@Schema(example = "5")
	public Integer getNumberOfUse() {
		return numberOfUse;
	}

	public void setNumberOfUse(Integer numberOfUse) {
		this.numberOfUse = numberOfUse;
	}

	@JsonIgnore
	public void setNumberOfUse(
		UnsafeSupplier<Integer, Exception> numberOfUseUnsafeSupplier) {

		try {
			numberOfUse = numberOfUseUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer numberOfUse;

	@DecimalMin("0")
	@Schema(example = "20")
	@Valid
	public BigDecimal getPercentageLevel1() {
		return percentageLevel1;
	}

	public void setPercentageLevel1(BigDecimal percentageLevel1) {
		this.percentageLevel1 = percentageLevel1;
	}

	@JsonIgnore
	public void setPercentageLevel1(
		UnsafeSupplier<BigDecimal, Exception> percentageLevel1UnsafeSupplier) {

		try {
			percentageLevel1 = percentageLevel1UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal percentageLevel1;

	@DecimalMin("0")
	@Schema(example = "0")
	@Valid
	public BigDecimal getPercentageLevel2() {
		return percentageLevel2;
	}

	public void setPercentageLevel2(BigDecimal percentageLevel2) {
		this.percentageLevel2 = percentageLevel2;
	}

	@JsonIgnore
	public void setPercentageLevel2(
		UnsafeSupplier<BigDecimal, Exception> percentageLevel2UnsafeSupplier) {

		try {
			percentageLevel2 = percentageLevel2UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal percentageLevel2;

	@DecimalMin("0")
	@Schema(example = "0")
	@Valid
	public BigDecimal getPercentageLevel3() {
		return percentageLevel3;
	}

	public void setPercentageLevel3(BigDecimal percentageLevel3) {
		this.percentageLevel3 = percentageLevel3;
	}

	@JsonIgnore
	public void setPercentageLevel3(
		UnsafeSupplier<BigDecimal, Exception> percentageLevel3UnsafeSupplier) {

		try {
			percentageLevel3 = percentageLevel3UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal percentageLevel3;

	@DecimalMin("0")
	@Schema(example = "0")
	@Valid
	public BigDecimal getPercentageLevel4() {
		return percentageLevel4;
	}

	public void setPercentageLevel4(BigDecimal percentageLevel4) {
		this.percentageLevel4 = percentageLevel4;
	}

	@JsonIgnore
	public void setPercentageLevel4(
		UnsafeSupplier<BigDecimal, Exception> percentageLevel4UnsafeSupplier) {

		try {
			percentageLevel4 = percentageLevel4UnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected BigDecimal percentageLevel4;

	@Schema(example = "true")
	public Boolean getRulesConjunction() {
		return rulesConjunction;
	}

	public void setRulesConjunction(Boolean rulesConjunction) {
		this.rulesConjunction = rulesConjunction;
	}

	@JsonIgnore
	public void setRulesConjunction(
		UnsafeSupplier<Boolean, Exception> rulesConjunctionUnsafeSupplier) {

		try {
			rulesConjunction = rulesConjunctionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean rulesConjunction;

	@Schema(example = "subtotal")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@JsonIgnore
	public void setTarget(
		UnsafeSupplier<String, Exception> targetUnsafeSupplier) {

		try {
			target = targetUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String target;

	@Schema(example = "20% Off")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String title;

	@Schema(example = "true")
	public Boolean getUseCouponCode() {
		return useCouponCode;
	}

	public void setUseCouponCode(Boolean useCouponCode) {
		this.useCouponCode = useCouponCode;
	}

	@JsonIgnore
	public void setUseCouponCode(
		UnsafeSupplier<Boolean, Exception> useCouponCodeUnsafeSupplier) {

		try {
			useCouponCode = useCouponCodeUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean useCouponCode;

	@Schema(example = "true")
	public Boolean getUsePercentage() {
		return usePercentage;
	}

	public void setUsePercentage(Boolean usePercentage) {
		this.usePercentage = usePercentage;
	}

	@JsonIgnore
	public void setUsePercentage(
		UnsafeSupplier<Boolean, Exception> usePercentageUnsafeSupplier) {

		try {
			usePercentage = usePercentageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Boolean usePercentage;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Discount)) {
			return false;
		}

		Discount discount = (Discount)object;

		return Objects.equals(toString(), discount.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (actions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(actions));
		}

		if (active != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(active);
		}

		if (amountFormatted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"amountFormatted\": ");

			sb.append("\"");

			sb.append(_escape(amountFormatted));

			sb.append("\"");
		}

		if (couponCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"couponCode\": ");

			sb.append("\"");

			sb.append(_escape(couponCode));

			sb.append("\"");
		}

		if (customFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(customFields));
		}

		if (discountAccountGroups != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountAccountGroups\": ");

			sb.append("[");

			for (int i = 0; i < discountAccountGroups.length; i++) {
				sb.append(String.valueOf(discountAccountGroups[i]));

				if ((i + 1) < discountAccountGroups.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (discountAccounts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountAccounts\": ");

			sb.append("[");

			for (int i = 0; i < discountAccounts.length; i++) {
				sb.append(String.valueOf(discountAccounts[i]));

				if ((i + 1) < discountAccounts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (discountCategories != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountCategories\": ");

			sb.append("[");

			for (int i = 0; i < discountCategories.length; i++) {
				sb.append(String.valueOf(discountCategories[i]));

				if ((i + 1) < discountCategories.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (discountChannels != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountChannels\": ");

			sb.append("[");

			for (int i = 0; i < discountChannels.length; i++) {
				sb.append(String.valueOf(discountChannels[i]));

				if ((i + 1) < discountChannels.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (discountOrderTypes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountOrderTypes\": ");

			sb.append("[");

			for (int i = 0; i < discountOrderTypes.length; i++) {
				sb.append(String.valueOf(discountOrderTypes[i]));

				if ((i + 1) < discountOrderTypes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (discountProductGroups != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountProductGroups\": ");

			sb.append("[");

			for (int i = 0; i < discountProductGroups.length; i++) {
				sb.append(String.valueOf(discountProductGroups[i]));

				if ((i + 1) < discountProductGroups.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (discountProducts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountProducts\": ");

			sb.append("[");

			for (int i = 0; i < discountProducts.length; i++) {
				sb.append(String.valueOf(discountProducts[i]));

				if ((i + 1) < discountProducts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (discountRules != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountRules\": ");

			sb.append("[");

			for (int i = 0; i < discountRules.length; i++) {
				sb.append(String.valueOf(discountRules[i]));

				if ((i + 1) < discountRules.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (displayDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(displayDate));

			sb.append("\"");
		}

		if (expirationDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(expirationDate));

			sb.append("\"");
		}

		if (externalReferenceCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceCode));

			sb.append("\"");
		}

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		if (level != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"level\": ");

			sb.append("\"");

			sb.append(_escape(level));

			sb.append("\"");
		}

		if (limitationTimes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"limitationTimes\": ");

			sb.append(limitationTimes);
		}

		if (limitationTimesPerAccount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"limitationTimesPerAccount\": ");

			sb.append(limitationTimesPerAccount);
		}

		if (limitationType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"limitationType\": ");

			sb.append("\"");

			sb.append(_escape(limitationType));

			sb.append("\"");
		}

		if (maximumDiscountAmount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maximumDiscountAmount\": ");

			sb.append(maximumDiscountAmount);
		}

		if (neverExpire != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(neverExpire);
		}

		if (numberOfUse != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfUse\": ");

			sb.append(numberOfUse);
		}

		if (percentageLevel1 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"percentageLevel1\": ");

			sb.append(percentageLevel1);
		}

		if (percentageLevel2 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"percentageLevel2\": ");

			sb.append(percentageLevel2);
		}

		if (percentageLevel3 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"percentageLevel3\": ");

			sb.append(percentageLevel3);
		}

		if (percentageLevel4 != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"percentageLevel4\": ");

			sb.append(percentageLevel4);
		}

		if (rulesConjunction != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rulesConjunction\": ");

			sb.append(rulesConjunction);
		}

		if (target != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"target\": ");

			sb.append("\"");

			sb.append(_escape(target));

			sb.append("\"");
		}

		if (title != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(title));

			sb.append("\"");
		}

		if (useCouponCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"useCouponCode\": ");

			sb.append(useCouponCode);
		}

		if (usePercentage != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"usePercentage\": ");

			sb.append(usePercentage);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.admin.pricing.dto.v2_0.Discount",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

}