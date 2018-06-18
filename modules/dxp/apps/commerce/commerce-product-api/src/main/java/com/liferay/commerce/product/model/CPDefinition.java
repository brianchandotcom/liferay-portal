/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

	public String getDefaultImageFileURL()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getDefaultImageThumbnailSrc(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws Exception;

	public java.util.Map<java.util.Locale, String> getDescriptionMap();

	public String getLayoutUuid();

	public java.util.Map<java.util.Locale, String> getMetaDescriptionMap();

	public java.util.Map<java.util.Locale, String> getMetaKeywordsMap();

	public java.util.Map<java.util.Locale, String> getMetaTitleMap();

	public String getNameCurrentValue();

	public java.util.Map<java.util.Locale, String> getNameMap();

	public java.util.Map<java.util.Locale, String> getShortDescriptionMap();

	public String getURL(String languageId);

	public java.util.Map<java.util.Locale, String> getUrlTitleMap();

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, String> descriptionMap);

	public void setLayoutUuid(String layoutUuid);

	public void setNameMap(java.util.Map<java.util.Locale, String> nameMap);

	public void setShortDescriptionMap(
		java.util.Map<java.util.Locale, String> shortDescriptionMap);

	public void setUrlTitleMap(
		java.util.Map<java.util.Locale, String> urlTitleMap);
}