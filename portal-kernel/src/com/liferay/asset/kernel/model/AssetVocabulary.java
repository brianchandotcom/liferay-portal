/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AssetVocabulary service. Represents a row in the &quot;AssetVocabulary&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.asset.model.impl.AssetVocabularyImpl"
)
@ProviderType
public interface AssetVocabulary extends AssetVocabularyModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.asset.model.impl.AssetVocabularyImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AssetVocabulary, Long> VOCABULARY_ID_ACCESSOR =
		new Accessor<AssetVocabulary, Long>() {

			@Override
			public Long get(AssetVocabulary assetVocabulary) {
				return assetVocabulary.getVocabularyId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AssetVocabulary> getTypeClass() {
				return AssetVocabulary.class;
			}

		};

	public java.util.List<AssetCategory> getCategories();

	public int getCategoriesCount();

	public long[] getRequiredClassNameIds();

	public long[] getSelectedClassNameIds();

	public long[] getSelectedClassTypePKs();

	public String getUnambiguousTitle(
			java.util.List<AssetVocabulary> vocabularies, long groupId,
			java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasMoreThanOneCategorySelected(long[] categoryIds);

	public boolean isAssociatedToClassNameId(long classNameId);

	public boolean isAssociatedToClassNameIdAndClassTypePK(
		long classNameId, long classTypePK);

	public boolean isMissingRequiredCategory(
		long classNameId, long classTypePK, long[] categoryIds);

	public boolean isMultiValued();

	public boolean isRequired(long classNameId, long classTypePK);

}