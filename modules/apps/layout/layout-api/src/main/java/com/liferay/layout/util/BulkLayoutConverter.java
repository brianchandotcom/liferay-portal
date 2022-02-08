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

package com.liferay.layout.util;

import com.liferay.layout.util.template.LayoutConversionResult;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Rubén Pulido
 * @review
 */
public interface BulkLayoutConverter {

	/**
	 * Converts a layout of type {@link LayoutConstants#TYPE_PORTLET} into a
	 * layout of type {@link LayoutConstants#TYPE_CONTENT}
	 *
	 * @param  plid the primary key of the layout
	 * @throws PortalException if a portal exception occurred
	 */
	public void convertLayout(long plid) throws PortalException;

	/**
	 * Converts all convertible layouts in the group of type {@link
	 * LayoutConstants#TYPE_PORTLET} into layouts of type {@link
	 * LayoutConstants#TYPE_CONTENT}
	 *
	 * <p>
	 * This method handles the conversion of each layout within a transaction.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @return an array with the plids of the layouts that have been
	 *         successfully converted
	 */
	public long[] convertLayouts(long groupId) throws PortalException;

	/**
	 * Converts multiple layouts of type {@link LayoutConstants#TYPE_PORTLET}
	 * into layouts of type {@link LayoutConstants#TYPE_CONTENT}
	 *
	 * <p>
	 * This method handles the conversion of each layout within a transaction.
	 * </p>
	 *
	 * @param  plids an array with the primary keys of the layouts to be
	 *         converted
	 * @return an array with the plids of the layouts that have been
	 *         successfully converted
	 */
	public long[] convertLayouts(long[] plids);

	public LayoutConversionResult generatePreviewLayout(
			long plid, Locale locale)
		throws Exception;

	/**
	 * Returns the plids of the convertible layouts in the group
	 *
	 * @param  groupId the primary key of the group
	 * @return an array with the plids of the convertible layouts in the group
	 */
	public long[] getConvertibleLayoutPlids(long groupId)
		throws PortalException;

}