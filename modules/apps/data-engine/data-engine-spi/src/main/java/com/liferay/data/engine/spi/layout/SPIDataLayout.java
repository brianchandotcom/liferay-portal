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

package com.liferay.data.engine.spi.layout;

import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class SPIDataLayout {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDataLayout)) {
			return false;
		}

		SPIDataLayout spiDataLayout = (SPIDataLayout)obj;

		if (Objects.equals(_dataLayoutPages, spiDataLayout._dataLayoutPages) &&
			Objects.equals(
				_defaultLanguageId, spiDataLayout._defaultLanguageId) &&
			Objects.equals(_paginationMode, spiDataLayout._paginationMode)) {

			return true;
		}

		return false;
	}

	public SPIDataLayoutPage[] getDataLayoutPages() {
		return _dataLayoutPages;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public String getPaginationMode() {
		return _paginationMode;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _dataLayoutPages);

		hash = HashUtil.hash(hash, _defaultLanguageId);

		return HashUtil.hash(hash, _paginationMode);
	}

	public void setDataLayoutPages(SPIDataLayoutPage[] dataLayoutPages) {
		_dataLayoutPages = dataLayoutPages;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setPaginationMode(String paginationMode) {
		_paginationMode = paginationMode;
	}

	private SPIDataLayoutPage[] _dataLayoutPages;
	private String _defaultLanguageId;
	private String _paginationMode;

}