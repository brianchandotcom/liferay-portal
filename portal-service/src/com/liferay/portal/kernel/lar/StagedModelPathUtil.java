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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.StagedModel;

import java.io.Serializable;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class StagedModelPathUtil {

	public static String getPath(
		PortletDataContext portletDataContext, String className, long classPK) {

		return getPath(portletDataContext, className, classPK, null);
	}

	public static String getPath(
		PortletDataContext portletDataContext, String className, long classPK,
		String dependentFileName) {

		return getPath(
			portletDataContext.getSourceGroupId(), className, classPK,
			dependentFileName);
	}

	public static String getPath(StagedModel stagedModel) {
		return getPath(stagedModel, null);
	}

	public static String getPath(
		StagedModel stagedModel, String dependentFileName) {

		return getPath(
			stagedModel.getGroupId(), stagedModel.getModelClassName(),
			stagedModel.getPrimaryKeyObj(), dependentFileName);
	}

	protected static String getPath(
		long groupId, String className, Serializable primaryKeyObj,
		String dependentFileName) {

		StringBundler sb = new StringBundler(7);

		sb.append("/groups/");
		sb.append(groupId);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(className);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(primaryKeyObj.toString());

		if (dependentFileName == null) {
			sb.append(".xml");
		}
		else {
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(dependentFileName);
		}

		return sb.toString();
	}

}