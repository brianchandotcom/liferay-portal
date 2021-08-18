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

package com.liferay.headless.commerce.admin.catalog.client.dto.v1_0;

import com.liferay.headless.commerce.admin.catalog.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0.ProductSubscriptionConfigurationSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class ProductSubscriptionConfiguration
	implements Cloneable, Serializable {

	public static ProductSubscriptionConfiguration toDTO(String json) {
		return ProductSubscriptionConfigurationSerDes.toDTO(json);
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public void setEnable(
		UnsafeSupplier<Boolean, Exception> enableUnsafeSupplier) {

		try {
			enable = enableUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean enable;

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public void setLength(
		UnsafeSupplier<Integer, Exception> lengthUnsafeSupplier) {

		try {
			length = lengthUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer length;

	public Long getNumberOfLength() {
		return numberOfLength;
	}

	public void setNumberOfLength(Long numberOfLength) {
		this.numberOfLength = numberOfLength;
	}

	public void setNumberOfLength(
		UnsafeSupplier<Long, Exception> numberOfLengthUnsafeSupplier) {

		try {
			numberOfLength = numberOfLengthUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long numberOfLength;

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	public String getSubscriptionTypeAsString() {
		if (subscriptionType == null) {
			return null;
		}

		return subscriptionType.toString();
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public void setSubscriptionType(
		UnsafeSupplier<SubscriptionType, Exception>
			subscriptionTypeUnsafeSupplier) {

		try {
			subscriptionType = subscriptionTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SubscriptionType subscriptionType;

	public Map<String, String> getSubscriptionTypeSettings() {
		return subscriptionTypeSettings;
	}

	public void setSubscriptionTypeSettings(
		Map<String, String> subscriptionTypeSettings) {

		this.subscriptionTypeSettings = subscriptionTypeSettings;
	}

	public void setSubscriptionTypeSettings(
		UnsafeSupplier<Map<String, String>, Exception>
			subscriptionTypeSettingsUnsafeSupplier) {

		try {
			subscriptionTypeSettings =
				subscriptionTypeSettingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> subscriptionTypeSettings;

	@Override
	public ProductSubscriptionConfiguration clone()
		throws CloneNotSupportedException {

		return (ProductSubscriptionConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProductSubscriptionConfiguration)) {
			return false;
		}

		ProductSubscriptionConfiguration productSubscriptionConfiguration =
			(ProductSubscriptionConfiguration)object;

		return Objects.equals(
			toString(), productSubscriptionConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ProductSubscriptionConfigurationSerDes.toJSON(this);
	}

	public static enum SubscriptionType {

		DAY("day"), MONTH("month"), WEEK("week"), YEAR("year");

		public static SubscriptionType create(String value) {
			for (SubscriptionType subscriptionType : values()) {
				if (Objects.equals(subscriptionType.getValue(), value) ||
					Objects.equals(subscriptionType.name(), value)) {

					return subscriptionType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private SubscriptionType(String value) {
			_value = value;
		}

		private final String _value;

	}

}