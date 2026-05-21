/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.design.library.client.dto.v1_0;

import com.liferay.headless.design.library.client.function.UnsafeSupplier;
import com.liferay.headless.design.library.client.serdes.v1_0.StyleBookEntrySerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author Luis Ortiz
 * @generated
 */
@Generated("")
public class StyleBookEntry implements Cloneable, Serializable {

	public static StyleBookEntry toDTO(String json) {
		return StyleBookEntrySerDes.toDTO(json);
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

	public Creator getCreator() {
		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	public void setCreator(
		UnsafeSupplier<Creator, Exception> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Creator creator;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public Boolean getDefaultStyleBookEntry() {
		return defaultStyleBookEntry;
	}

	public void setDefaultStyleBookEntry(Boolean defaultStyleBookEntry) {
		this.defaultStyleBookEntry = defaultStyleBookEntry;
	}

	public void setDefaultStyleBookEntry(
		UnsafeSupplier<Boolean, Exception>
			defaultStyleBookEntryUnsafeSupplier) {

		try {
			defaultStyleBookEntry = defaultStyleBookEntryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean defaultStyleBookEntry;

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

	public String getFrontendTokensValues() {
		return frontendTokensValues;
	}

	public void setFrontendTokensValues(String frontendTokensValues) {
		this.frontendTokensValues = frontendTokensValues;
	}

	public void setFrontendTokensValues(
		UnsafeSupplier<String, Exception> frontendTokensValuesUnsafeSupplier) {

		try {
			frontendTokensValues = frontendTokensValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String frontendTokensValues;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<String, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String key;

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

	public String getPreviewFileEntryExternalReferenceCode() {
		return previewFileEntryExternalReferenceCode;
	}

	public void setPreviewFileEntryExternalReferenceCode(
		String previewFileEntryExternalReferenceCode) {

		this.previewFileEntryExternalReferenceCode =
			previewFileEntryExternalReferenceCode;
	}

	public void setPreviewFileEntryExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			previewFileEntryExternalReferenceCodeUnsafeSupplier) {

		try {
			previewFileEntryExternalReferenceCode =
				previewFileEntryExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String previewFileEntryExternalReferenceCode;

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public void setThemeId(
		UnsafeSupplier<String, Exception> themeIdUnsafeSupplier) {

		try {
			themeId = themeIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String themeId;

	@Override
	public StyleBookEntry clone() throws CloneNotSupportedException {
		return (StyleBookEntry)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StyleBookEntry)) {
			return false;
		}

		StyleBookEntry styleBookEntry = (StyleBookEntry)object;

		return Objects.equals(toString(), styleBookEntry.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return StyleBookEntrySerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1712627474