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

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.cache.CacheField;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMTemplateImpl extends DDMTemplateBaseImpl {

	@Override
	public String getDefaultLanguageId() {
		Document document = null;

		try {
			document = SAXReaderUtil.read(getName());

			if (document != null) {
				Element rootElement = document.getRootElement();

				return rootElement.attributeValue("default-locale");
			}
		}
		catch (Exception e) {
		}

		Locale locale = LocaleUtil.getSiteDefault();

		return locale.toString();
	}

	@Override
	public DDMTemplateVersion getLatestTemplateVersion()
		throws PortalException {

		return DDMTemplateVersionLocalServiceUtil.getLatestTemplateVersion(
			getTemplateId());
	}

	@Override
	public String getResourceClassName() {
		if (_resourceClassName == null) {
			_resourceClassName = PortalUtil.getClassName(
				getResourceClassNameId());
		}

		return _resourceClassName;
	}

	@Override
	public String getSmallImageType() throws PortalException {
		if ((_smallImageType == null) && isSmallImage()) {
			Image smallImage = ImageLocalServiceUtil.getImage(
				getSmallImageId());

			_smallImageType = smallImage.getType();
		}

		return _smallImageType;
	}

	@Override
	public String getTemplateImageURL(ThemeDisplay themeDisplay) {
		if (!isSmallImage()) {
			return null;
		}

		if (Validator.isNotNull(getSmallImageURL())) {
			return getSmallImageURL();
		}

		return StringBundler.concat(
			themeDisplay.getPathImage(), "/template?img_id=", getSmallImageId(),
			"&t=", WebServerServletTokenUtil.getToken(getSmallImageId()));
	}

	@Override
	public DDMTemplateVersion getTemplateVersion() throws PortalException {
		return DDMTemplateVersionLocalServiceUtil.getTemplateVersion(
			getTemplateId(), getVersion());
	}

	/**
	 * Returns the WebDAV URL to access the template.
	 *
	 * @param  themeDisplay the theme display needed to build the URL. It can
	 *         set HTTPS access, the server name, the server port, the path
	 *         context, and the scope group.
	 * @param  webDAVToken the WebDAV token for the URL
	 * @return the WebDAV URL
	 */
	@Override
	public String getWebDavURL(ThemeDisplay themeDisplay, String webDAVToken) {
		StringBundler sb = new StringBundler(11);

		boolean secure = false;

		if (themeDisplay.isSecure() ||
			PropsValues.WEBDAV_SERVLET_HTTPS_REQUIRED) {

			secure = true;
		}

		String portalURL = PortalUtil.getPortalURL(
			themeDisplay.getServerName(), themeDisplay.getServerPort(), secure);

		sb.append(portalURL);

		sb.append(themeDisplay.getPathContext());
		sb.append(StringPool.SLASH);
		sb.append("webdav");

		Group group = themeDisplay.getScopeGroup();

		sb.append(group.getFriendlyURL());

		sb.append(StringPool.SLASH);
		sb.append(webDAVToken);
		sb.append(StringPool.SLASH);
		sb.append("Templates");
		sb.append(StringPool.SLASH);
		sb.append(getTemplateId());

		return sb.toString();
	}

	@Override
	public void setResourceClassName(String resourceClassName) {
		_resourceClassName = resourceClassName;
	}

	@Override
	public void setResourceClassNameId(long resourceClassNameId) {
		super.setResourceClassNameId(resourceClassNameId);

		_resourceClassName = null;
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	@CacheField(propagateToInterface = true)
	private String _resourceClassName;

	private String _smallImageType;

}