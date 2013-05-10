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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;

import java.io.Serializable;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class ExportImportPathUtil {

	public static final String PATH_PREFIX_COMPANY = "company";

	public static final String PATH_PREFIX_GROUP = "group";

	public static final String PATH_PREFIX_LAYOUT = "layout";

	public static final String PATH_PREFIX_PORTLET = "portlet";

	public static String getExpandoPath(String path) {
		if (!Validator.isFilePath(path, false)) {
			throw new IllegalArgumentException(
				path + " is located outside of the LAR");
		}

		int pos = path.lastIndexOf(_FILE_ENDING_XML);

		if (pos == -1) {
			throw new IllegalArgumentException(path + " is not an XML file");
		}

		return path.substring(0, pos).concat("-expando").concat(
			path.substring(pos));
	}

	public static String getLayoutPath(
		PortletDataContext portletDataContext, long plid) {

		StringBundler sb = new StringBundler(5);

		sb.append(getRootPath(portletDataContext));
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(PATH_PREFIX_LAYOUT);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(plid);
		sb.append(_FILE_ENDING_XML);

		return sb.toString();
	}

	public static String getModelPath(
		long groupId, String className, long classPK) {

		return getModelPath(
			PATH_PREFIX_GROUP, groupId, className, classPK, null);
	}

	public static String getModelPath(
		PortletDataContext portletDataContext, String className, long classPK) {

		return getModelPath(portletDataContext, className, classPK, null);
	}

	public static String getModelPath(
		PortletDataContext portletDataContext, String className, long classPK,
		String dependentFileName) {

		return getModelPath(
			PATH_PREFIX_GROUP, portletDataContext.getSourceGroupId(), className,
			classPK, dependentFileName);
	}

	public static String getModelPath(StagedModel stagedModel) {
		return getModelPath(stagedModel, null);
	}

	public static String getModelPath(
		StagedModel stagedModel, String dependentFileName) {

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			return getModelPath(
				PATH_PREFIX_GROUP, stagedGroupedModel.getGroupId(),
				stagedModel.getModelClassName(), stagedModel.getPrimaryKeyObj(),
				dependentFileName);
		}
		else {
			return getModelPath(
				PATH_PREFIX_COMPANY, stagedModel.getCompanyId(),
				stagedModel.getModelClassName(), stagedModel.getPrimaryKeyObj(),
				dependentFileName);
		}
	}

	public static String getPortletPath(
		PortletDataContext portletDataContext, String portletId) {

		StringBundler sb = new StringBundler(5);

		sb.append(getRootPath(portletDataContext));
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(PATH_PREFIX_PORTLET);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(portletId);

		return sb.toString();
	}

	public static String getRootPath(PortletDataContext portletDataContext) {
		return getRootPath(
			PATH_PREFIX_GROUP, portletDataContext.getScopeGroupId());
	}

	public static String getSourceLayoutPath(
		PortletDataContext portletDataContext, long layoutId) {

		StringBundler sb = new StringBundler(5);

		sb.append(getSourceRootPath(portletDataContext));
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(PATH_PREFIX_LAYOUT);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(layoutId);

		return sb.toString();
	}

	public static String getSourcePortletPath(
		PortletDataContext portletDataContext, String portletId) {

		StringBundler sb = new StringBundler(5);

		sb.append(getSourceRootPath(portletDataContext));
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(PATH_PREFIX_PORTLET);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(portletId);

		return sb.toString();
	}

	public static String getSourceRootPath(
		PortletDataContext portletDataContext) {

		return getRootPath(
			PATH_PREFIX_GROUP, portletDataContext.getSourceGroupId());
	}

	protected static String getModelPath(
		String pathPrefix, long pathPrimaryKey, String className,
		Serializable primaryKeyObj, String dependentFileName) {

		StringBundler sb = new StringBundler(7);

		sb.append(getRootPath(pathPrefix, pathPrimaryKey));
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(className);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(primaryKeyObj.toString());

		if (dependentFileName == null) {
			sb.append(_FILE_ENDING_XML);
		}
		else {
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(dependentFileName);
		}

		return sb.toString();
	}

	protected static String getRootPath(
		String pathPrefix, long pathPrimaryKey) {

		StringBundler sb = new StringBundler(4);

		sb.append(StringPool.FORWARD_SLASH);
		sb.append(pathPrefix);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(pathPrimaryKey);

		return sb.toString();
	}

	private static final String _FILE_ENDING_XML = ".xml";

}