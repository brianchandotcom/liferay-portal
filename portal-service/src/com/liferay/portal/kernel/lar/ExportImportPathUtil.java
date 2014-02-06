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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;

import java.io.Serializable;

/**
 * Provides a set of utility methods for generating path for the entities being
 * serialized with the portal's export/import framework.
 *
 * @author Mate Thurzo
 * @author Daniel Kocsis
 * @since  6.2
 */
@ProviderType
public class ExportImportPathUtil {

	/**
	 * A company prefix being used when generating paths
	 */
	public static final String PATH_PREFIX_COMPANY = "company";

	/**
	 * A group prefix being used when generating paths
	 */
	public static final String PATH_PREFIX_GROUP = "group";

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static final String PATH_PREFIX_LAYOUT = "layout";

	/**
	 * A portlet prefix being used when generating paths
	 */
	public static final String PATH_PREFIX_PORTLET = "portlet";

	/**
	 * Returns an expando specific path for a previously generated entity path.
	 *
	 * @param  path the previously generated entity path
	 * @return the expando specific entity path
	 */
	public static String getExpandoPath(String path) {
		if (!Validator.isFilePath(path, false)) {
			throw new IllegalArgumentException(
				path + " is located outside of the LAR");
		}

		int pos = path.lastIndexOf(_FILE_EXTENSION_XML);

		if (pos == -1) {
			throw new IllegalArgumentException(path + " is not an XML file");
		}

		return path.substring(0, pos).concat("-expando").concat(
			path.substring(pos));
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getModelPath(StagedModel)}
	 */
	@Deprecated
	public static String getLayoutPath(
		PortletDataContext portletDataContext, long plid) {

		StringBundler sb = new StringBundler(5);

		sb.append(getRootPath(portletDataContext));
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(PATH_PREFIX_LAYOUT);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(plid);
		sb.append(_FILE_EXTENSION_XML);

		return sb.toString();
	}

	/**
	 * Returns a model path for a specific class name and class primary key
	 * pair, using a specific group's ID. These parameters will be generated
	 * into the path.
	 *
	 * @param  groupId the group ID of the entity's group
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return a model path generated based on the given parameters
	 */
	public static String getModelPath(
		long groupId, String className, long classPK) {

		return getModelPath(
			PATH_PREFIX_GROUP, groupId, className, classPK, null);
	}

	/**
	 * Returns a model path for a specific class name and class primary key
	 * pair, where the group ID will be queried from the
	 * <code>portletDataContext</code> parameter. This method is using the
	 * <code>_sourceGroupId</code> attribute from the context.
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  className the class name of the entity
	 * @param  classPK the primary key of the entity
	 * @return a model path generated based on the given parameters
	 * @see    PortletDataContext.getSourceGroupId()
	 */
	public static String getModelPath(
		PortletDataContext portletDataContext, String className, long classPK) {

		return getModelPath(portletDataContext, className, classPK, null);
	}

	/**
	 * Returns a path for an object related to an entity. The dependent object's
	 * file name is being appended to the generated prefix.
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  className the class name of the related entity
	 * @param  classPK the primary key of the related entity
	 * @param  dependentFileName the the dependent object's file name
	 * @return a path for an object related to a specific entity
	 */
	public static String getModelPath(
		PortletDataContext portletDataContext, String className, long classPK,
		String dependentFileName) {

		return getModelPath(
			PATH_PREFIX_GROUP, portletDataContext.getSourceGroupId(), className,
			classPK, dependentFileName);
	}

	/**
	 * Returns a model path for a specific staged model. All the necessary
	 * information, such as the group ID, needed to generate the path will be
	 * queried from the staged model parameter.
	 *
	 * @param  stagedModel the staged model the path is needed for
	 * @return a model path generated based on the staged model parameter
	 * @see    StagedModel
	 */
	public static String getModelPath(StagedModel stagedModel) {
		return getModelPath(stagedModel, null);
	}

	/**
	 * Returns a path for a file related to a specific model. This method is
	 * useful for example when generating path for an image related to the web
	 * content. The staged model's attributes are being used to generate the
	 * first part of the path then the <code>dependentFileName</code> will be
	 * attached to the end of the path.
	 *
	 * @param  stagedModel the staged model the path is needed for
	 * @param  dependentFileName the dependent object's file name
	 * @return a path for the dependent object
	 */
	public static String getModelPath(
		StagedModel stagedModel, String dependentFileName) {

		StagedModelType stagedModelType = stagedModel.getStagedModelType();

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			return getModelPath(
				PATH_PREFIX_GROUP, stagedGroupedModel.getGroupId(),
				stagedModelType.getClassName(), stagedModel.getPrimaryKeyObj(),
				dependentFileName);
		}
		else {
			return getModelPath(
				PATH_PREFIX_COMPANY, stagedModel.getCompanyId(),
				stagedModelType.getClassName(), stagedModel.getPrimaryKeyObj(),
				dependentFileName);
		}
	}

	/**
	 * Returns a portlet path for a specific portlet ID.
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  portletId the portlet ID the path is being generated
	 * @return a portlet path for the specific portlet ID
	 */
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

	/**
	 * A helper method used to generate a fragment of the path. The fragment is
	 * being generated with the scope group ID from the portlet data context.
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @return a path fragment
	 * @see    PortletDataContext.getScopeGroupId()
	 * @see    getSourceRootPath(PortletDataContext)
	 */
	public static String getRootPath(PortletDataContext portletDataContext) {
		return getRootPath(
			PATH_PREFIX_GROUP, portletDataContext.getScopeGroupId());
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getModelPath(PortletDataContext, String, long)}
	 */
	@Deprecated
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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
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

	/**
	 * A helper method used to generate a fragment of the path pair to the
	 * <code>getRootPath</code> method. The fragment is being generated with the
	 * source group ID from the portlet data context. This method is useful
	 * during the import process.
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @return a path fragment
	 * @see    PortletDataContext.getSourceGroupId()
	 * @see    getRootPath(PortletDataContext)
	 */
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
			sb.append(_FILE_EXTENSION_XML);
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

	private static final String _FILE_EXTENSION_XML = ".xml";

}