/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.TaxonomyCategoryBriefSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class TaxonomyCategoryBrief implements Cloneable, Serializable {

	public static TaxonomyCategoryBrief toDTO(String json) {
		return TaxonomyCategoryBriefSerDes.toDTO(json);
	}

	public ParentTaxonomyCategory getParentTaxonomyCategory() {
		return parentTaxonomyCategory;
	}

	public void setParentTaxonomyCategory(
		ParentTaxonomyCategory parentTaxonomyCategory) {

		this.parentTaxonomyCategory = parentTaxonomyCategory;
	}

	public void setParentTaxonomyCategory(
		UnsafeSupplier<ParentTaxonomyCategory, Exception>
			parentTaxonomyCategoryUnsafeSupplier) {

		try {
			parentTaxonomyCategory = parentTaxonomyCategoryUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ParentTaxonomyCategory parentTaxonomyCategory;

	public ParentTaxonomyVocabulary getParentTaxonomyVocabulary() {
		return parentTaxonomyVocabulary;
	}

	public void setParentTaxonomyVocabulary(
		ParentTaxonomyVocabulary parentTaxonomyVocabulary) {

		this.parentTaxonomyVocabulary = parentTaxonomyVocabulary;
	}

	public void setParentTaxonomyVocabulary(
		UnsafeSupplier<ParentTaxonomyVocabulary, Exception>
			parentTaxonomyVocabularyUnsafeSupplier) {

		try {
			parentTaxonomyVocabulary =
				parentTaxonomyVocabularyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ParentTaxonomyVocabulary parentTaxonomyVocabulary;

	public com.liferay.headless.admin.site.client.scope.Scope getScope() {
		return scope;
	}

	public void setScope(
		com.liferay.headless.admin.site.client.scope.Scope scope) {

		this.scope = scope;
	}

	public void setScope(
		UnsafeSupplier
			<com.liferay.headless.admin.site.client.scope.Scope, Exception>
				scopeUnsafeSupplier) {

		try {
			scope = scopeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected com.liferay.headless.admin.site.client.scope.Scope scope;

	public String getTaxonomyCategoryExternalReferenceCode() {
		return taxonomyCategoryExternalReferenceCode;
	}

	public void setTaxonomyCategoryExternalReferenceCode(
		String taxonomyCategoryExternalReferenceCode) {

		this.taxonomyCategoryExternalReferenceCode =
			taxonomyCategoryExternalReferenceCode;
	}

	public void setTaxonomyCategoryExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			taxonomyCategoryExternalReferenceCodeUnsafeSupplier) {

		try {
			taxonomyCategoryExternalReferenceCode =
				taxonomyCategoryExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String taxonomyCategoryExternalReferenceCode;

	@Override
	public TaxonomyCategoryBrief clone() throws CloneNotSupportedException {
		return (TaxonomyCategoryBrief)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TaxonomyCategoryBrief)) {
			return false;
		}

		TaxonomyCategoryBrief taxonomyCategoryBrief =
			(TaxonomyCategoryBrief)object;

		return Objects.equals(toString(), taxonomyCategoryBrief.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return TaxonomyCategoryBriefSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-931989866