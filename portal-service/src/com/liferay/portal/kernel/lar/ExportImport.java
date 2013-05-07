/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.xml.Element;

/**
 * @author Zsolt Berentey
 */
public interface ExportImport {

	public String exportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception;

	public String exportDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception;

	public String exportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception;

	public String exportLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception;

	public String importContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception;

	public String importDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content)
		throws Exception;

	public String importLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception;

	public String importLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception;

}