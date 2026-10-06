/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.client.dto.v1_0;

import com.liferay.headless.cms.client.function.UnsafeSupplier;
import com.liferay.headless.cms.client.serdes.v1_0.BrokenLinkAssetSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class BrokenLinkAsset implements Cloneable, Serializable {

	public static BrokenLinkAsset toDTO(String json) {
		return BrokenLinkAssetSerDes.toDTO(json);
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

	public String getBrokenLinkTitle() {
		return brokenLinkTitle;
	}

	public void setBrokenLinkTitle(String brokenLinkTitle) {
		this.brokenLinkTitle = brokenLinkTitle;
	}

	public void setBrokenLinkTitle(
		UnsafeSupplier<String, Exception> brokenLinkTitleUnsafeSupplier) {

		try {
			brokenLinkTitle = brokenLinkTitleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String brokenLinkTitle;

	public Long getBrokenLinksCount() {
		return brokenLinksCount;
	}

	public void setBrokenLinksCount(Long brokenLinksCount) {
		this.brokenLinksCount = brokenLinksCount;
	}

	public void setBrokenLinksCount(
		UnsafeSupplier<Long, Exception> brokenLinksCountUnsafeSupplier) {

		try {
			brokenLinksCount = brokenLinksCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long brokenLinksCount;

	public Long getDraftBrokenLinksCount() {
		return draftBrokenLinksCount;
	}

	public void setDraftBrokenLinksCount(Long draftBrokenLinksCount) {
		this.draftBrokenLinksCount = draftBrokenLinksCount;
	}

	public void setDraftBrokenLinksCount(
		UnsafeSupplier<Long, Exception> draftBrokenLinksCountUnsafeSupplier) {

		try {
			draftBrokenLinksCount = draftBrokenLinksCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long draftBrokenLinksCount;

	public Long getExpiredBrokenLinksCount() {
		return expiredBrokenLinksCount;
	}

	public void setExpiredBrokenLinksCount(Long expiredBrokenLinksCount) {
		this.expiredBrokenLinksCount = expiredBrokenLinksCount;
	}

	public void setExpiredBrokenLinksCount(
		UnsafeSupplier<Long, Exception> expiredBrokenLinksCountUnsafeSupplier) {

		try {
			expiredBrokenLinksCount =
				expiredBrokenLinksCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long expiredBrokenLinksCount;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setHref(UnsafeSupplier<String, Exception> hrefUnsafeSupplier) {
		try {
			href = hrefUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String href;

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

	public Long getInTrashBrokenLinksCount() {
		return inTrashBrokenLinksCount;
	}

	public void setInTrashBrokenLinksCount(Long inTrashBrokenLinksCount) {
		this.inTrashBrokenLinksCount = inTrashBrokenLinksCount;
	}

	public void setInTrashBrokenLinksCount(
		UnsafeSupplier<Long, Exception> inTrashBrokenLinksCountUnsafeSupplier) {

		try {
			inTrashBrokenLinksCount =
				inTrashBrokenLinksCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long inTrashBrokenLinksCount;

	public String getObjectDefinitionExternalReferenceCode() {
		return objectDefinitionExternalReferenceCode;
	}

	public void setObjectDefinitionExternalReferenceCode(
		String objectDefinitionExternalReferenceCode) {

		this.objectDefinitionExternalReferenceCode =
			objectDefinitionExternalReferenceCode;
	}

	public void setObjectDefinitionExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			objectDefinitionExternalReferenceCodeUnsafeSupplier) {

		try {
			objectDefinitionExternalReferenceCode =
				objectDefinitionExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String objectDefinitionExternalReferenceCode;

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
	public BrokenLinkAsset clone() throws CloneNotSupportedException {
		return (BrokenLinkAsset)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BrokenLinkAsset)) {
			return false;
		}

		BrokenLinkAsset brokenLinkAsset = (BrokenLinkAsset)object;

		return Objects.equals(toString(), brokenLinkAsset.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return BrokenLinkAssetSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1563250787