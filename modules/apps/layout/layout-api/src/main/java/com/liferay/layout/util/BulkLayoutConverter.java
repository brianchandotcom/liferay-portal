/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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