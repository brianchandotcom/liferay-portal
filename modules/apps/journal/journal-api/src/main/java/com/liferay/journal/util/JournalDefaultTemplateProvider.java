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

package com.liferay.journal.util;

/**
 * Provides a default template for those JournalArticle instances which has no
 * DDM template defined.
 *
 * @author Pavel Savinov
 */
public interface JournalDefaultTemplateProvider {

	/**
	 * Default template language type. <code>ftl</code>, <code>vm</code> or
	 * <code>xsl</code>.
	 *
	 * @return Default template language type.
	 */
	public String getLanguage();

	/**
	 * Default template script.
	 *
	 * @param  ddmStructureId ID of the DDM structure to provide a template for.
	 * @return Default template script text.
	 */
	public String getScript(long ddmStructureId) throws Exception;

	/**
	 * Defines if default template is cacheable.
	 *
	 * @return <code>true</code> if default template is cacheable,
	 *         <code>false</code> otherwise.
	 */
	public boolean isCacheable();

}