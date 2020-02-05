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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.PageDefinitionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageDefinition {

	public String getFriendlyURLPath() {
		return friendlyURLPath;
	}

	public void setFriendlyURLPath(String friendlyURLPath) {
		this.friendlyURLPath = friendlyURLPath;
	}

	public void setFriendlyURLPath(
		UnsafeSupplier<String, Exception> friendlyURLPathUnsafeSupplier) {

		try {
			friendlyURLPath = friendlyURLPathUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String friendlyURLPath;

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

	public PageElement[] getPageElements() {
		return pageElements;
	}

	public void setPageElements(PageElement[] pageElements) {
		this.pageElements = pageElements;
	}

	public void setPageElements(
		UnsafeSupplier<PageElement[], Exception> pageElementsUnsafeSupplier) {

		try {
			pageElements = pageElementsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PageElement[] pageElements;

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public void setSettings(
		UnsafeSupplier<Settings, Exception> settingsUnsafeSupplier) {

		try {
			settings = settingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Settings settings;

	public TaxonomyCategory[] getTaxonomyCategories() {
		return taxonomyCategories;
	}

	public void setTaxonomyCategories(TaxonomyCategory[] taxonomyCategories) {
		this.taxonomyCategories = taxonomyCategories;
	}

	public void setTaxonomyCategories(
		UnsafeSupplier<TaxonomyCategory[], Exception>
			taxonomyCategoriesUnsafeSupplier) {

		try {
			taxonomyCategories = taxonomyCategoriesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected TaxonomyCategory[] taxonomyCategories;

	public Long[] getTaxonomyCategoryIds() {
		return taxonomyCategoryIds;
	}

	public void setTaxonomyCategoryIds(Long[] taxonomyCategoryIds) {
		this.taxonomyCategoryIds = taxonomyCategoryIds;
	}

	public void setTaxonomyCategoryIds(
		UnsafeSupplier<Long[], Exception> taxonomyCategoryIdsUnsafeSupplier) {

		try {
			taxonomyCategoryIds = taxonomyCategoryIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] taxonomyCategoryIds;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuid(UnsafeSupplier<String, Exception> uuidUnsafeSupplier) {
		try {
			uuid = uuidUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String uuid;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageDefinition)) {
			return false;
		}

		PageDefinition pageDefinition = (PageDefinition)object;

		return Objects.equals(toString(), pageDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PageDefinitionSerDes.toJSON(this);
	}

}