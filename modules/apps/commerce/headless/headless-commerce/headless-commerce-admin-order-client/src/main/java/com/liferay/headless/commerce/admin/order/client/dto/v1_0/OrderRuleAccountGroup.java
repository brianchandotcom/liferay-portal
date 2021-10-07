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

package com.liferay.headless.commerce.admin.order.client.dto.v1_0;

import com.liferay.headless.commerce.admin.order.client.function.UnsafeSupplier;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderRuleAccountGroupSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class OrderRuleAccountGroup implements Cloneable, Serializable {

	public static OrderRuleAccountGroup toDTO(String json) {
		return OrderRuleAccountGroupSerDes.toDTO(json);
	}

	public AccountGroup getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(AccountGroup accountGroup) {
		this.accountGroup = accountGroup;
	}

	public void setAccountGroup(
		UnsafeSupplier<AccountGroup, Exception> accountGroupUnsafeSupplier) {

		try {
			accountGroup = accountGroupUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AccountGroup accountGroup;

	public String getAccountGroupExternalReferenceCode() {
		return accountGroupExternalReferenceCode;
	}

	public void setAccountGroupExternalReferenceCode(
		String accountGroupExternalReferenceCode) {

		this.accountGroupExternalReferenceCode =
			accountGroupExternalReferenceCode;
	}

	public void setAccountGroupExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			accountGroupExternalReferenceCodeUnsafeSupplier) {

		try {
			accountGroupExternalReferenceCode =
				accountGroupExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String accountGroupExternalReferenceCode;

	public Long getAccountGroupId() {
		return accountGroupId;
	}

	public void setAccountGroupId(Long accountGroupId) {
		this.accountGroupId = accountGroupId;
	}

	public void setAccountGroupId(
		UnsafeSupplier<Long, Exception> accountGroupIdUnsafeSupplier) {

		try {
			accountGroupId = accountGroupIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long accountGroupId;

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

	public Long getOrderRuleAccountGroupId() {
		return orderRuleAccountGroupId;
	}

	public void setOrderRuleAccountGroupId(Long orderRuleAccountGroupId) {
		this.orderRuleAccountGroupId = orderRuleAccountGroupId;
	}

	public void setOrderRuleAccountGroupId(
		UnsafeSupplier<Long, Exception> orderRuleAccountGroupIdUnsafeSupplier) {

		try {
			orderRuleAccountGroupId =
				orderRuleAccountGroupIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long orderRuleAccountGroupId;

	public String getOrderRuleExternalReferenceCode() {
		return orderRuleExternalReferenceCode;
	}

	public void setOrderRuleExternalReferenceCode(
		String orderRuleExternalReferenceCode) {

		this.orderRuleExternalReferenceCode = orderRuleExternalReferenceCode;
	}

	public void setOrderRuleExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			orderRuleExternalReferenceCodeUnsafeSupplier) {

		try {
			orderRuleExternalReferenceCode =
				orderRuleExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String orderRuleExternalReferenceCode;

	public Long getOrderRuleId() {
		return orderRuleId;
	}

	public void setOrderRuleId(Long orderRuleId) {
		this.orderRuleId = orderRuleId;
	}

	public void setOrderRuleId(
		UnsafeSupplier<Long, Exception> orderRuleIdUnsafeSupplier) {

		try {
			orderRuleId = orderRuleIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long orderRuleId;

	@Override
	public OrderRuleAccountGroup clone() throws CloneNotSupportedException {
		return (OrderRuleAccountGroup)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OrderRuleAccountGroup)) {
			return false;
		}

		OrderRuleAccountGroup orderRuleAccountGroup =
			(OrderRuleAccountGroup)object;

		return Objects.equals(toString(), orderRuleAccountGroup.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return OrderRuleAccountGroupSerDes.toJSON(this);
	}

}