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

package com.liferay.translation.url.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;

import javax.portlet.PortletURL;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface TranslationURLProvider {

	public PortletURL getExportTranslationURL(
		long groupId, long classNameId, long classPK,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory);

	public PortletURL getExportTranslationURL(
		long groupId, long classNameId,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory);

	public PortletURL getImportTranslationURL(
			long groupId, long classNameId, long classPK,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory)
		throws PortalException;

	public PortletURL getImportTranslationURL(
			long groupId, long classNameId,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory)
		throws PortalException;

	public PortletURL getTranslateURL(
			long groupId, long classNameId, long classPK,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getTranslateURL(long, long, long,
	 *             RequestBackedPortletURLFactory)}
	 */
	@Deprecated
	public PortletURL getTranslateURL(
		long classNameId, long classPK,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory);

}