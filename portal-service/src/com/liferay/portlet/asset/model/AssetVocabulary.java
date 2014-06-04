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

package com.liferay.portlet.asset.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the AssetVocabulary service. Represents a row in the &quot;AssetVocabulary&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyModel
 * @see com.liferay.portlet.asset.model.impl.AssetVocabularyImpl
 * @see com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl
 * @generated
 */
@ProviderType
public interface AssetVocabulary extends AssetVocabularyModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.asset.model.impl.AssetVocabularyImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.kernel.exception.SystemException;

	public long[] getRequiredClassNameIds();

	public long[] getSelectedClassNameIds();

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated()
	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties();

	public java.lang.String getUnambiguousTitle(
		java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> vocabularies,
		long groupId, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean hasMoreThanOneCategorySelected(long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isAssociatedToClassNameId(long classNameId);

	public boolean isMissingRequiredCategory(long classNameId,
		long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isMultiValued();

	public boolean isRequired(long classNameId);

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@java.lang.Deprecated()
	public void setSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties settingsProperties);
}