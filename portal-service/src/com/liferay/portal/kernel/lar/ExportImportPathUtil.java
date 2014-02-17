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
 * Provides utility methods for generating a path for the entities being
 * serialized with the portal's export/import framework.
 *
 * @author Mate Thurzo
 * @author Daniel Kocsis
 * @since  6.2
 */
@ProviderType
public class ExportImportPathUtil {

	/**
	 * The company prefix used when generating paths.
	 */
	public static final String PATH_PREFIX_COMPANY = "company";

	/**
	 * The group prefix used when generating paths.
	 */
	public static final String PATH_PREFIX_GROUP = "group";

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static final String PATH_PREFIX_LAYOUT = "layout";

	/**
	 * The portlet prefix used when generating paths.
	 */
	public static final String PATH_PREFIX_PORTLET = "portlet";

	/**
	 * Returns an expando-specific path for the entity path. The entity path
	 * must include an XML file.
	 *
	 * <p>
	 * For example, if you had the entity path of
	 * <code>/group/10184/com.liferay.portlet.dynamicdatamapping.model.DDMStructure/10951.xml</code>,
	 * the returned expando-specific path would be the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/10184/com.liferay.portlet.dynamicdatamapping.model.DDMStructure/10951-expando.xml
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  path the previously generated entity path
	 * @return an expando-specific path for the entity path
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
	 * Returns a model path with the group ID, class name, and class PK
	 * parameters generated into the path.
	 *
	 * <p>
	 * For example, a model path would resemble the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/"groupId"/"className"/"classPK".xml
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  groupId the group ID of the entity's group
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity
	 * @return a model path generated based on the given parameters
	 */
	public static String getModelPath(
		long groupId, String className, long classPK) {

		return getModelPath(
			PATH_PREFIX_GROUP, groupId, className, classPK, null);
	}

	/**
	 * Returns a model path with the group ID, class name and class PK, where
	 * the group ID is queried from the portlet data context. This method is
	 * using the source group ID from the context.
	 *
	 * <p>
	 * For example, a model path would resemble the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/"queried groupId"/"className"/"classPK".xml
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity
	 * @return a model path generated based on the given parameters
	 * @see    PortletDataContext#getSourceGroupId()
	 */
	public static String getModelPath(
		PortletDataContext portletDataContext, String className, long classPK) {

		return getModelPath(portletDataContext, className, classPK, null);
	}

	/**
	 * Returns a model path for the entity's dependent object. The dependent
	 * object's file name is appended to the generated prefix.
	 *
	 * <p>
	 * For example, a model path would resemble the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/"queried groupId"/"className"/"classPK"/"dependentFileName"
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  className the related entity's class name
	 * @param  classPK the primary key of the related entity
	 * @param  dependentFileName the dependent object's file name
	 * @return a model path for the entity's dependent object
	 */
	public static String getModelPath(
		PortletDataContext portletDataContext, String className, long classPK,
		String dependentFileName) {

		return getModelPath(
			PATH_PREFIX_GROUP, portletDataContext.getSourceGroupId(), className,
			classPK, dependentFileName);
	}

	/**
	 * Returns a model path for the staged model. The group ID, class name, and
	 * class PK are queried from the staged model to generate the path.
	 *
	 * <p>
	 * For example, a model path would resemble the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/"queried groupId"/"queried className"/"queried classPK".xml
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  stagedModel the staged model the path is needed for
	 * @return a model path for the staged model
	 * @see    StagedModel
	 */
	public static String getModelPath(StagedModel stagedModel) {
		return getModelPath(stagedModel, null);
	}

	/**
	 * Returns a model path for the staged model's dependent object.
	 *
	 * <p>
	 * This method is useful, for example, when generating the path for an image
	 * related to the web content. The staged model's attributes are used to
	 * generate the first part of the path; then the dependent object's file
	 * name is attached to the end of the path.
	 * </p>
	 *
	 * <p>
	 * For example, a model path would resemble the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/"queried groupId"/"queried className"/"queried classPK"/"dependentFileName"
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  stagedModel the staged model the path is needed for
	 * @param  dependentFileName the dependent object's file name
	 * @return a model path for the staged model's dependent object
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
	 * Returns a portlet path for the portlet ID.
	 *
	 * <p>
	 * For example, a portlet path would resemble the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/"queried groupId"/portlet/"portletId"
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  portletId the portlet ID the path is being generated for
	 * @return a portlet path for the portlet ID
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
	 * Returns a root path, or fragment of the model path. The fragment is
	 * generated with the scope group ID from the portlet data context.
	 *
	 * <p>
	 * For example, a root path would resemble the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/"queried groupId"
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @return a root path, or fragment of the model path
	 * @see    PortletDataContext#getScopeGroupId()
	 * @see    #getSourceRootPath(PortletDataContext)
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
	 * Returns a source root path, or fragment of the model path to the {@link
	 * #getRootPath(PortletDataContext)} method. The fragment is generated with
	 * the source group ID from the portlet data context. This helper method is
	 * useful during the import process.
	 *
	 * <p>
	 * For example, a source root path would resemble the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/"queried groupId"
	 * </code>
	 * </pre>
	 * </p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @return a path fragment
	 * @see    PortletDataContext#getSourceGroupId()
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