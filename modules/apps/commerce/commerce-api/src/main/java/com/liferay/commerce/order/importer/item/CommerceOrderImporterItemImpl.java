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

package com.liferay.commerce.order.importer.item;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderImporterItemImpl
	implements CommerceOrderImporterItem {

	@Override
	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	@Override
	public String getJSON() {
		return _json;
	}

	@Override
	public int getQuantity() {
		return _quantity;
	}

	public void setCPInstanceId(long cpInstanceId) {
		_cpInstanceId = cpInstanceId;
	}

	public void setJSON(String json) {
		_json = json;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	private long _cpInstanceId;
	private String _json;
	private int _quantity;

}