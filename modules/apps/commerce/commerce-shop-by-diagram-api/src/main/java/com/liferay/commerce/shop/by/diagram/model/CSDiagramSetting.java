/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CSDiagramSetting service. Represents a row in the &quot;CSDiagramSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramSettingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramSettingImpl"
)
@ProviderType
public interface CSDiagramSetting
	extends CSDiagramSettingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.shop.by.diagram.model.impl.CSDiagramSettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CSDiagramSetting, Long>
		CS_DIAGRAM_SETTING_ID_ACCESSOR =
			new Accessor<CSDiagramSetting, Long>() {

				@Override
				public Long get(CSDiagramSetting csDiagramSetting) {
					return csDiagramSetting.getCSDiagramSettingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CSDiagramSetting> getTypeClass() {
					return CSDiagramSetting.class;
				}

			};

	public com.liferay.commerce.product.model.CPAttachmentFileEntry
			getCPAttachmentFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

}