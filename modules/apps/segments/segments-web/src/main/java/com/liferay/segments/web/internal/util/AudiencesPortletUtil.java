/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.util;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.constants.SegmentsPortletKeys;

import jakarta.portlet.PortletRequest;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesPortletUtil {

	public static boolean isAudiencesPortlet(PortletRequest portletRequest) {
		return Objects.equals(
			PortalUtil.getPortletId(portletRequest),
			SegmentsPortletKeys.AUDIENCES);
	}

}