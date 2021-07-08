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

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPDefinitionDiagramSetting service. Represents a row in the &quot;CPDefinitionDiagramSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingImpl"
)
@ProviderType
public interface CPDefinitionDiagramSetting
	extends CPDefinitionDiagramSettingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionDiagramSetting, Long>
		CP_DEFINITION_DIAGRAM_SETTING_ID_ACCESSOR =
			new Accessor<CPDefinitionDiagramSetting, Long>() {

				@Override
				public Long get(
					CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

					return cpDefinitionDiagramSetting.
						getCPDefinitionDiagramSettingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPDefinitionDiagramSetting> getTypeClass() {
					return CPDefinitionDiagramSetting.class;
				}

			};

	public com.liferay.commerce.product.model.CPAttachmentFileEntry
			getCPAttachmentFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

}