/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DDMTemplate service. Represents a row in the &quot;DDMTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl"
)
@ProviderType
public interface DDMTemplate extends DDMTemplateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMTemplate, Long> TEMPLATE_ID_ACCESSOR =
		new Accessor<DDMTemplate, Long>() {

			@Override
			public Long get(DDMTemplate ddmTemplate) {
				return ddmTemplate.getTemplateId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMTemplate> getTypeClass() {
				return DDMTemplate.class;
			}

		};

	public DDMTemplateVersion getLatestTemplateVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getResourceClassName();

	public String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getTemplateImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);

	public DDMTemplateVersion getTemplateVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	 * Returns the WebDAV URL to access the template.
	 *
	 * @param themeDisplay the theme display needed to build the URL. It can
	 set HTTPS access, the server name, the server port, the path
	 context, and the scope group.
	 * @param webDAVToken the WebDAV token for the URL
	 * @return the WebDAV URL
	 */
	public String getWebDavURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay,
		String webDAVToken);

	public void setResourceClassName(String resourceClassName);

	public void setSmallImageType(String smallImageType);

}