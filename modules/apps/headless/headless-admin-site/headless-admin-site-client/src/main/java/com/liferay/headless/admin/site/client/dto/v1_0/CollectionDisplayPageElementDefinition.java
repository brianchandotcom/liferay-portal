/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.CollectionDisplayPageElementDefinitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class CollectionDisplayPageElementDefinition
	extends PageElementDefinition implements Cloneable, Serializable {

	public static CollectionDisplayPageElementDefinition toDTO(String json) {
		return CollectionDisplayPageElementDefinitionSerDes.toDTO(json);
	}

	public CollectionDisplayListStyle getCollectionDisplayListStyle() {
		return collectionDisplayListStyle;
	}

	public void setCollectionDisplayListStyle(
		CollectionDisplayListStyle collectionDisplayListStyle) {

		this.collectionDisplayListStyle = collectionDisplayListStyle;
	}

	public void setCollectionDisplayListStyle(
		UnsafeSupplier<CollectionDisplayListStyle, Exception>
			collectionDisplayListStyleUnsafeSupplier) {

		try {
			collectionDisplayListStyle =
				collectionDisplayListStyleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionDisplayListStyle collectionDisplayListStyle;

	public CollectionDisplayViewport[] getCollectionDisplayViewports() {
		return collectionDisplayViewports;
	}

	public void setCollectionDisplayViewports(
		CollectionDisplayViewport[] collectionDisplayViewports) {

		this.collectionDisplayViewports = collectionDisplayViewports;
	}

	public void setCollectionDisplayViewports(
		UnsafeSupplier<CollectionDisplayViewport[], Exception>
			collectionDisplayViewportsUnsafeSupplier) {

		try {
			collectionDisplayViewports =
				collectionDisplayViewportsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionDisplayViewport[] collectionDisplayViewports;

	public CollectionSettings getCollectionSettings() {
		return collectionSettings;
	}

	public void setCollectionSettings(CollectionSettings collectionSettings) {
		this.collectionSettings = collectionSettings;
	}

	public void setCollectionSettings(
		UnsafeSupplier<CollectionSettings, Exception>
			collectionSettingsUnsafeSupplier) {

		try {
			collectionSettings = collectionSettingsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CollectionSettings collectionSettings;

	public Boolean getDisplayAllItems() {
		return displayAllItems;
	}

	public void setDisplayAllItems(Boolean displayAllItems) {
		this.displayAllItems = displayAllItems;
	}

	public void setDisplayAllItems(
		UnsafeSupplier<Boolean, Exception> displayAllItemsUnsafeSupplier) {

		try {
			displayAllItems = displayAllItemsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean displayAllItems;

	public Boolean getDisplayAllPages() {
		return displayAllPages;
	}

	public void setDisplayAllPages(Boolean displayAllPages) {
		this.displayAllPages = displayAllPages;
	}

	public void setDisplayAllPages(
		UnsafeSupplier<Boolean, Exception> displayAllPagesUnsafeSupplier) {

		try {
			displayAllPages = displayAllPagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean displayAllPages;

	public EmptyCollectionConfig getEmptyCollectionConfig() {
		return emptyCollectionConfig;
	}

	public void setEmptyCollectionConfig(
		EmptyCollectionConfig emptyCollectionConfig) {

		this.emptyCollectionConfig = emptyCollectionConfig;
	}

	public void setEmptyCollectionConfig(
		UnsafeSupplier<EmptyCollectionConfig, Exception>
			emptyCollectionConfigUnsafeSupplier) {

		try {
			emptyCollectionConfig = emptyCollectionConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected EmptyCollectionConfig emptyCollectionConfig;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Integer getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(Integer numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public void setNumberOfItems(
		UnsafeSupplier<Integer, Exception> numberOfItemsUnsafeSupplier) {

		try {
			numberOfItems = numberOfItemsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer numberOfItems;

	public Integer getNumberOfItemsPerPage() {
		return numberOfItemsPerPage;
	}

	public void setNumberOfItemsPerPage(Integer numberOfItemsPerPage) {
		this.numberOfItemsPerPage = numberOfItemsPerPage;
	}

	public void setNumberOfItemsPerPage(
		UnsafeSupplier<Integer, Exception> numberOfItemsPerPageUnsafeSupplier) {

		try {
			numberOfItemsPerPage = numberOfItemsPerPageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer numberOfItemsPerPage;

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public void setNumberOfPages(
		UnsafeSupplier<Integer, Exception> numberOfPagesUnsafeSupplier) {

		try {
			numberOfPages = numberOfPagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer numberOfPages;

	public PaginationType getPaginationType() {
		return paginationType;
	}

	public String getPaginationTypeAsString() {
		if (paginationType == null) {
			return null;
		}

		return paginationType.toString();
	}

	public void setPaginationType(PaginationType paginationType) {
		this.paginationType = paginationType;
	}

	public void setPaginationType(
		UnsafeSupplier<PaginationType, Exception>
			paginationTypeUnsafeSupplier) {

		try {
			paginationType = paginationTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected PaginationType paginationType;

	@Override
	public CollectionDisplayPageElementDefinition clone()
		throws CloneNotSupportedException {

		return (CollectionDisplayPageElementDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollectionDisplayPageElementDefinition)) {
			return false;
		}

		CollectionDisplayPageElementDefinition
			collectionDisplayPageElementDefinition =
				(CollectionDisplayPageElementDefinition)object;

		return Objects.equals(
			toString(), collectionDisplayPageElementDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollectionDisplayPageElementDefinitionSerDes.toJSON(this);
	}

	public static enum PaginationType {

		NONE("None"), NUMERIC("Numeric"), SIMPLE("Simple");

		public static PaginationType create(String value) {
			for (PaginationType paginationType : values()) {
				if (Objects.equals(paginationType.getValue(), value) ||
					Objects.equals(paginationType.name(), value)) {

					return paginationType;
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

		private PaginationType(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:-1898350265