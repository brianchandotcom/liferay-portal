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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

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
@GraphQLName("PriceList")
@JsonFilter("Liferay.Vulcan")
@Schema(requiredProperties = {"catalogId", "currencyCode", "name", "type"})
@XmlRootElement(name = "PriceList")
public class PriceList implements Serializable {

	public static PriceList toDTO(String json) {
		return ObjectMapperUtil.readValue(PriceList.class, json);
	}

	public static PriceList unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(PriceList.class, json);
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

	@Schema(example = "admin")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@JsonIgnore
	public void setAuthor(
		UnsafeSupplier<String, Exception> authorUnsafeSupplier) {

		try {
			author = authorUnsafeSupplier.get();
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
	protected String author;

	@Schema(example = "true")
	public Boolean getCatalogBasePriceList() {
		return catalogBasePriceList;
	}

	public void setCatalogBasePriceList(Boolean catalogBasePriceList) {
		this.catalogBasePriceList = catalogBasePriceList;
	}

	@JsonIgnore
	public void setCatalogBasePriceList(
		UnsafeSupplier<Boolean, Exception> catalogBasePriceListUnsafeSupplier) {

		try {
			catalogBasePriceList = catalogBasePriceListUnsafeSupplier.get();
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
	protected Boolean catalogBasePriceList;

	@DecimalMin("0")
	@Schema(example = "23130")
	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	@JsonIgnore
	public void setCatalogId(
		UnsafeSupplier<Long, Exception> catalogIdUnsafeSupplier) {

		try {
			catalogId = catalogIdUnsafeSupplier.get();
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
	protected Long catalogId;

	@Schema(example = "catalog")
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	@JsonIgnore
	public void setCatalogName(
		UnsafeSupplier<String, Exception> catalogNameUnsafeSupplier) {

		try {
			catalogName = catalogNameUnsafeSupplier.get();
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
	protected String catalogName;

	@Schema(example = "2017-07-21")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		try {
			createDate = createDateUnsafeSupplier.get();
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
	protected Date createDate;

	@Schema(example = "EUR")
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@JsonIgnore
	public void setCurrencyCode(
		UnsafeSupplier<String, Exception> currencyCodeUnsafeSupplier) {

		try {
			currencyCode = currencyCodeUnsafeSupplier.get();
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
	protected String currencyCode;

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

	@Schema(example = "Laptops, Beverages")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
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
	protected String name;

	@Schema(example = "true")
	public Boolean getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(Boolean netPrice) {
		this.netPrice = netPrice;
	}

	@JsonIgnore
	public void setNetPrice(
		UnsafeSupplier<Boolean, Exception> netPriceUnsafeSupplier) {

		try {
			netPrice = netPriceUnsafeSupplier.get();
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
	protected Boolean netPrice;

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
	@Schema(example = "30130")
	public Long getParentPriceListId() {
		return parentPriceListId;
	}

	public void setParentPriceListId(Long parentPriceListId) {
		this.parentPriceListId = parentPriceListId;
	}

	@JsonIgnore
	public void setParentPriceListId(
		UnsafeSupplier<Long, Exception> parentPriceListIdUnsafeSupplier) {

		try {
			parentPriceListId = parentPriceListIdUnsafeSupplier.get();
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
	protected Long parentPriceListId;

	@Schema
	@Valid
	public PriceEntry[] getPriceEntries() {
		return priceEntries;
	}

	public void setPriceEntries(PriceEntry[] priceEntries) {
		this.priceEntries = priceEntries;
	}

	@JsonIgnore
	public void setPriceEntries(
		UnsafeSupplier<PriceEntry[], Exception> priceEntriesUnsafeSupplier) {

		try {
			priceEntries = priceEntriesUnsafeSupplier.get();
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
	protected PriceEntry[] priceEntries;

	@Schema
	@Valid
	public PriceListAccountGroup[] getPriceListAccountGroups() {
		return priceListAccountGroups;
	}

	public void setPriceListAccountGroups(
		PriceListAccountGroup[] priceListAccountGroups) {

		this.priceListAccountGroups = priceListAccountGroups;
	}

	@JsonIgnore
	public void setPriceListAccountGroups(
		UnsafeSupplier<PriceListAccountGroup[], Exception>
			priceListAccountGroupsUnsafeSupplier) {

		try {
			priceListAccountGroups = priceListAccountGroupsUnsafeSupplier.get();
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
	protected PriceListAccountGroup[] priceListAccountGroups;

	@Schema
	@Valid
	public PriceListAccount[] getPriceListAccounts() {
		return priceListAccounts;
	}

	public void setPriceListAccounts(PriceListAccount[] priceListAccounts) {
		this.priceListAccounts = priceListAccounts;
	}

	@JsonIgnore
	public void setPriceListAccounts(
		UnsafeSupplier<PriceListAccount[], Exception>
			priceListAccountsUnsafeSupplier) {

		try {
			priceListAccounts = priceListAccountsUnsafeSupplier.get();
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
	protected PriceListAccount[] priceListAccounts;

	@Schema
	@Valid
	public PriceListChannel[] getPriceListChannels() {
		return priceListChannels;
	}

	public void setPriceListChannels(PriceListChannel[] priceListChannels) {
		this.priceListChannels = priceListChannels;
	}

	@JsonIgnore
	public void setPriceListChannels(
		UnsafeSupplier<PriceListChannel[], Exception>
			priceListChannelsUnsafeSupplier) {

		try {
			priceListChannels = priceListChannelsUnsafeSupplier.get();
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
	protected PriceListChannel[] priceListChannels;

	@Schema
	@Valid
	public PriceListDiscount[] getPriceListDiscounts() {
		return priceListDiscounts;
	}

	public void setPriceListDiscounts(PriceListDiscount[] priceListDiscounts) {
		this.priceListDiscounts = priceListDiscounts;
	}

	@JsonIgnore
	public void setPriceListDiscounts(
		UnsafeSupplier<PriceListDiscount[], Exception>
			priceListDiscountsUnsafeSupplier) {

		try {
			priceListDiscounts = priceListDiscountsUnsafeSupplier.get();
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
	protected PriceListDiscount[] priceListDiscounts;

	@Schema
	@Valid
	public PriceListOrderType[] getPriceListOrderTypes() {
		return priceListOrderTypes;
	}

	public void setPriceListOrderTypes(
		PriceListOrderType[] priceListOrderTypes) {

		this.priceListOrderTypes = priceListOrderTypes;
	}

	@JsonIgnore
	public void setPriceListOrderTypes(
		UnsafeSupplier<PriceListOrderType[], Exception>
			priceListOrderTypesUnsafeSupplier) {

		try {
			priceListOrderTypes = priceListOrderTypesUnsafeSupplier.get();
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
	protected PriceListOrderType[] priceListOrderTypes;

	@Schema
	@Valid
	public PriceModifier[] getPriceModifiers() {
		return priceModifiers;
	}

	public void setPriceModifiers(PriceModifier[] priceModifiers) {
		this.priceModifiers = priceModifiers;
	}

	@JsonIgnore
	public void setPriceModifiers(
		UnsafeSupplier<PriceModifier[], Exception>
			priceModifiersUnsafeSupplier) {

		try {
			priceModifiers = priceModifiersUnsafeSupplier.get();
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
	protected PriceModifier[] priceModifiers;

	@Schema(example = "1.2")
	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	@JsonIgnore
	public void setPriority(
		UnsafeSupplier<Double, Exception> priorityUnsafeSupplier) {

		try {
			priority = priorityUnsafeSupplier.get();
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
	protected Double priority;

	@Schema(example = "price-list, promotion, contract")
	@Valid
	public Type getType() {
		return type;
	}

	@JsonIgnore
	public String getTypeAsString() {
		if (type == null) {
			return null;
		}

		return type.toString();
	}

	public void setType(Type type) {
		this.type = type;
	}

	@JsonIgnore
	public void setType(UnsafeSupplier<Type, Exception> typeUnsafeSupplier) {
		try {
			type = typeUnsafeSupplier.get();
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
	protected Type type;

	@Schema
	@Valid
	public Status getWorkflowStatusInfo() {
		return workflowStatusInfo;
	}

	public void setWorkflowStatusInfo(Status workflowStatusInfo) {
		this.workflowStatusInfo = workflowStatusInfo;
	}

	@JsonIgnore
	public void setWorkflowStatusInfo(
		UnsafeSupplier<Status, Exception> workflowStatusInfoUnsafeSupplier) {

		try {
			workflowStatusInfo = workflowStatusInfoUnsafeSupplier.get();
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
	protected Status workflowStatusInfo;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PriceList)) {
			return false;
		}

		PriceList priceList = (PriceList)object;

		return Objects.equals(toString(), priceList.toString());
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

		if (author != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"author\": ");

			sb.append("\"");

			sb.append(_escape(author));

			sb.append("\"");
		}

		if (catalogBasePriceList != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogBasePriceList\": ");

			sb.append(catalogBasePriceList);
		}

		if (catalogId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogId\": ");

			sb.append(catalogId);
		}

		if (catalogName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogName\": ");

			sb.append("\"");

			sb.append(_escape(catalogName));

			sb.append("\"");
		}

		if (createDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(createDate));

			sb.append("\"");
		}

		if (currencyCode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"currencyCode\": ");

			sb.append("\"");

			sb.append(_escape(currencyCode));

			sb.append("\"");
		}

		if (customFields != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(customFields));
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

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		if (netPrice != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"netPrice\": ");

			sb.append(netPrice);
		}

		if (neverExpire != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(neverExpire);
		}

		if (parentPriceListId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentPriceListId\": ");

			sb.append(parentPriceListId);
		}

		if (priceEntries != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceEntries\": ");

			sb.append("[");

			for (int i = 0; i < priceEntries.length; i++) {
				sb.append(String.valueOf(priceEntries[i]));

				if ((i + 1) < priceEntries.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceListAccountGroups != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListAccountGroups\": ");

			sb.append("[");

			for (int i = 0; i < priceListAccountGroups.length; i++) {
				sb.append(String.valueOf(priceListAccountGroups[i]));

				if ((i + 1) < priceListAccountGroups.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceListAccounts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListAccounts\": ");

			sb.append("[");

			for (int i = 0; i < priceListAccounts.length; i++) {
				sb.append(String.valueOf(priceListAccounts[i]));

				if ((i + 1) < priceListAccounts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceListChannels != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListChannels\": ");

			sb.append("[");

			for (int i = 0; i < priceListChannels.length; i++) {
				sb.append(String.valueOf(priceListChannels[i]));

				if ((i + 1) < priceListChannels.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceListDiscounts != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListDiscounts\": ");

			sb.append("[");

			for (int i = 0; i < priceListDiscounts.length; i++) {
				sb.append(String.valueOf(priceListDiscounts[i]));

				if ((i + 1) < priceListDiscounts.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceListOrderTypes != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListOrderTypes\": ");

			sb.append("[");

			for (int i = 0; i < priceListOrderTypes.length; i++) {
				sb.append(String.valueOf(priceListOrderTypes[i]));

				if ((i + 1) < priceListOrderTypes.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceModifiers != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceModifiers\": ");

			sb.append("[");

			for (int i = 0; i < priceModifiers.length; i++) {
				sb.append(String.valueOf(priceModifiers[i]));

				if ((i + 1) < priceModifiers.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priority != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(priority);
		}

		if (type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(type);

			sb.append("\"");
		}

		if (workflowStatusInfo != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowStatusInfo\": ");

			sb.append(String.valueOf(workflowStatusInfo));
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Type")
	public static enum Type {

		PRICE_LIST("price-list"), PROMOTION("promotion"), CONTRACT("contract");

		@JsonCreator
		public static Type create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value)) {
					return type;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

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