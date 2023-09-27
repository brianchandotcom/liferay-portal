/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

/**
 * @author Tina Tian
 */
public interface TemplateResourceCache {

	public void clear();

	public TemplateResource getTemplateResource(String templateId);

	public boolean isEnabled();

	public void put(String templateId, TemplateResource templateResource);

	public void remove(String templateId);

}