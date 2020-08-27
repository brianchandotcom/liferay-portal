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

package com.liferay.headless.commerce.admin.pricing.client.dto.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceModifierSerDes;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class PriceModifier implements Cloneable {

	public static PriceModifier toDTO(String json) {
		return PriceModifierSerDes.toDTO(json);
	}

	public Map<String, Map<String, String>> getActions() {
		return actions;
	}

	public void setActions(Map<String, Map<String, String>> actions) {
		this.actions = actions;
	}

	public void setActions(
		UnsafeSupplier<Map<String, Map<String, String>>, Exception>
			actionsUnsafeSupplier) {

		try {
			actions = actionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Map<String, String>> actions;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setActive(
		UnsafeSupplier<Boolean, Exception> activeUnsafeSupplier) {

		try {
			active = activeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean active;

	public Date getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		this.displayDate = displayDate;
	}

	public void setDisplayDate(
		UnsafeSupplier<Date, Exception> displayDateUnsafeSupplier) {

		try {
			displayDate = displayDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date displayDate;

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setExpirationDate(
		UnsafeSupplier<Date, Exception> expirationDateUnsafeSupplier) {

		try {
			expirationDate = expirationDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date expirationDate;

	public String getExternalReferenceCode() {
		return externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		this.externalReferenceCode = externalReferenceCode;
	}

	public void setExternalReferenceCode(
		UnsafeSupplier<String, Exception> externalReferenceCodeUnsafeSupplier) {

		try {
			externalReferenceCode = externalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String externalReferenceCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public BigDecimal getModifierAmount() {
		return modifierAmount;
	}

	public void setModifierAmount(BigDecimal modifierAmount) {
		this.modifierAmount = modifierAmount;
	}

	public void setModifierAmount(
		UnsafeSupplier<BigDecimal, Exception> modifierAmountUnsafeSupplier) {

		try {
			modifierAmount = modifierAmountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected BigDecimal modifierAmount;

	public String getModifierType() {
		return modifierType;
	}

	public void setModifierType(String modifierType) {
		this.modifierType = modifierType;
	}

	public void setModifierType(
		UnsafeSupplier<String, Exception> modifierTypeUnsafeSupplier) {

		try {
			modifierType = modifierTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String modifierType;

	public Boolean getNeverExpire() {
		return neverExpire;
	}

	public void setNeverExpire(Boolean neverExpire) {
		this.neverExpire = neverExpire;
	}

	public void setNeverExpire(
		UnsafeSupplier<Boolean, Exception> neverExpireUnsafeSupplier) {

		try {
			neverExpire = neverExpireUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean neverExpire;

	public String getPriceListExternalReferenceCode() {
		return priceListExternalReferenceCode;
	}

	public void setPriceListExternalReferenceCode(
		String priceListExternalReferenceCode) {

		this.priceListExternalReferenceCode = priceListExternalReferenceCode;
	}

	public void setPriceListExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			priceListExternalReferenceCodeUnsafeSupplier) {

		try {
			priceListExternalReferenceCode =
				priceListExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String priceListExternalReferenceCode;

	public Long getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(Long priceListId) {
		this.priceListId = priceListId;
	}

	public void setPriceListId(
		UnsafeSupplier<Long, Exception> priceListIdUnsafeSupplier) {

		try {
			priceListId = priceListIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long priceListId;

	public PriceModifierCategory[] getPriceModifierCategory() {
		return priceModifierCategory;
	}

	public void setPriceModifierCategory(
		PriceModifierCategory[] priceModifierCategory) {

		this.priceModifierCategory = priceModifierCategory;
	}

	public void setPriceModifierCategory(
		UnsafeSupplier<PriceModifierCategory[], Exception>
			priceModifierCategoryUnsafeSupplier) {

		try {
			priceModifierCategory = priceModifierCategoryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PriceModifierCategory[] priceModifierCategory;

	public PriceModifierProduct[] getPriceModifierProduct() {
		return priceModifierProduct;
	}

	public void setPriceModifierProduct(
		PriceModifierProduct[] priceModifierProduct) {

		this.priceModifierProduct = priceModifierProduct;
	}

	public void setPriceModifierProduct(
		UnsafeSupplier<PriceModifierProduct[], Exception>
			priceModifierProductUnsafeSupplier) {

		try {
			priceModifierProduct = priceModifierProductUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PriceModifierProduct[] priceModifierProduct;

	public PriceModifierProductGroup[] getPriceModifierProductGroup() {
		return priceModifierProductGroup;
	}

	public void setPriceModifierProductGroup(
		PriceModifierProductGroup[] priceModifierProductGroup) {

		this.priceModifierProductGroup = priceModifierProductGroup;
	}

	public void setPriceModifierProductGroup(
		UnsafeSupplier<PriceModifierProductGroup[], Exception>
			priceModifierProductGroupUnsafeSupplier) {

		try {
			priceModifierProductGroup =
				priceModifierProductGroupUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PriceModifierProductGroup[] priceModifierProductGroup;

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public void setPriority(
		UnsafeSupplier<Double, Exception> priorityUnsafeSupplier) {

		try {
			priority = priorityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double priority;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setTarget(
		UnsafeSupplier<String, Exception> targetUnsafeSupplier) {

		try {
			target = targetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String target;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String title;

	@Override
	public PriceModifier clone() throws CloneNotSupportedException {
		return (PriceModifier)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PriceModifier)) {
			return false;
		}

		PriceModifier priceModifier = (PriceModifier)object;

		return Objects.equals(toString(), priceModifier.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PriceModifierSerDes.toJSON(this);
	}

}