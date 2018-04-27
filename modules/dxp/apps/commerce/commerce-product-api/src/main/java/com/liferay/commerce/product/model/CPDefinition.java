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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPDefinition service. Represents a row in the &quot;CPDefinition&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionModel
 * @see com.liferay.commerce.product.model.impl.CPDefinitionImpl
 * @see com.liferay.commerce.product.model.impl.CPDefinitionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPDefinitionImpl")
@ProviderType
public interface CPDefinition extends CPDefinitionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPDefinitionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinition, Long> CP_DEFINITION_ID_ACCESSOR = new Accessor<CPDefinition, Long>() {
			@Override
			public Long get(CPDefinition cpDefinition) {
				return cpDefinition.getCPDefinitionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPDefinition> getTypeClass() {
				return CPDefinition.class;
			}
		};

	@Override
	public boolean equals(Object object);

	public java.util.List<CPDefinitionOptionRel> getCPDefinitionOptionRels();

	public java.util.List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues();

	public java.util.List<CPInstance> getCPInstances();

	public String getDefaultImageThumbnailSrc(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws Exception;

	public java.util.Map<java.util.Locale, String> getDescriptionMap();

	public String getLayoutUuid();

	public java.util.Map<java.util.Locale, String> getMetaDescriptionMap();

	public java.util.Map<java.util.Locale, String> getMetaKeywordsMap();

	public java.util.Map<java.util.Locale, String> getMetaTitleMap();

	public java.util.Map<java.util.Locale, String> getShortDescriptionMap();

	public String getTitleCurrentValue();

	public java.util.Map<java.util.Locale, String> getTitleMap();

	public java.util.Map<java.util.Locale, String> getUrlTitleMap();

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, String> descriptionMap);

	public void setLayoutUuid(String layoutUuid);

	public void setShortDescriptionMap(
		java.util.Map<java.util.Locale, String> shortDescriptionMap);

	public void setTitleMap(java.util.Map<java.util.Locale, String> titleMap);

	public void setUrlTitleMap(
		java.util.Map<java.util.Locale, String> urlTitleMap);
}