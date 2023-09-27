/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * Provides utility methods for generating paths for entities being serialized
 * with the portal's export/import framework.
 *
 * @author Máté Thurzó
 * @author Daniel Kocsis
 * @since  6.2
 */
public class ExportImportPathUtil {

	/**
	 * The company prefix used in generating paths.
	 */
	public static final String PATH_PREFIX_COMPANY = "company";

	/**
	 * The group prefix used in generating paths.
	 */
	public static final String PATH_PREFIX_GROUP = "group";

	/**
	 * The portlet prefix used in generating paths.
	 */
	public static final String PATH_PREFIX_PORTLET = "portlet";

	/**
	 * The service prefix used in generating paths.
	 */
	public static final String PATH_PREFIX_SERVICE = "service";

	public static String getCompanyModelPath(
		long companyId, String className, long classPK) {

		return getCompanyModelPath(companyId, className, Long.valueOf(classPK));
	}

	public static String getCompanyModelPath(
		long companyId, String className, Serializable classPK) {

		return getModelPath(
			PATH_PREFIX_COMPANY, companyId, className, classPK, null);
	}

	/**
	 * Returns the expando-specific path for the entity path. The entity path
	 * must include an XML file.
	 *
	 * <p>
	 * For example, if you had the entity path of
	 * <code>/group/10184/com.liferay.dynamic.data.mapping.kernel.DDMStructure/10951.xml</code>,
	 * the returned expando-specific path would be the following:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /group/10184/com.liferay.dynamic.data.mapping.kernel.DDMStructure/10951-expando.xml
	 * </code>
	 * </pre></p>
	 *
	 * @param  path the previously generated entity path
	 * @return the expando-specific path for the entity path
	 */
	public static String getExpandoPath(String path) {
		if (!Validator.isFilePath(path, false)) {
			throw new IllegalArgumentException(
				"Unable to get expando path " + path +
					" because it is located outside of the LAR");
		}

		int pos = path.lastIndexOf(_FILE_EXTENSION_XML);

		if (pos == -1) {
			throw new IllegalArgumentException(
				"Unable to get expando path " + path +
					" because it is not an XML file");
		}

		return StringBundler.concat(
			path.substring(0, pos), "-expando", path.substring(pos));
	}

	public static String getModelPath(
		long groupId, String className, long classPK) {

		return getModelPath(groupId, className, Long.valueOf(classPK));
	}

	/**
	 * Returns a model path based on the group ID, class name, and class PK.
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
	 * </pre></p>
	 *
	 * @param  groupId the group ID of the entity's group
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity
	 * @return a model path based on the parameters
	 */
	public static String getModelPath(
		long groupId, String className, Serializable classPK) {

		return getModelPath(
			PATH_PREFIX_GROUP, groupId, className, classPK, null);
	}

	/**
	 * Returns a model path based on the group ID, class name, and class PK,
	 * where the group ID is queried from the portlet data context. This method
	 * uses the source group ID from the context.
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
	 * </pre></p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  className the entity's class name
	 * @param  classPK the primary key of the entity
	 * @return a model path based on the parameters
	 * @see    PortletDataContext#getSourceGroupId()
	 */
	public static String getModelPath(
		PortletDataContext portletDataContext, String className, long classPK) {

		return getModelPath(portletDataContext, className, classPK, null);
	}

	/**
	 * Returns a model path for the named file related to the entity, having the
	 * class name and class PK. The dependent object's file name is appended to
	 * the generated prefix.
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
	 * </pre></p>
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
	 * </pre></p>
	 *
	 * @param  stagedModel the staged model the path is needed for
	 * @return a model path for the staged model
	 * @see    StagedModel
	 */
	public static String getModelPath(StagedModel stagedModel) {
		return getModelPath(stagedModel, null);
	}

	/**
	 * Returns a model path for the named file related to the staged model.
	 *
	 * <p>
	 * This method is useful, for example, for generating the path for an image
	 * related to a web content article. The staged model's attributes are used
	 * to generate the first part of the path; then the dependent object's file
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
	 * </pre></p>
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

		return getModelPath(
			PATH_PREFIX_COMPANY, stagedModel.getCompanyId(),
			stagedModelType.getClassName(), stagedModel.getPrimaryKeyObj(),
			dependentFileName);
	}

	public static String getPortletDataPath(
		PortletDataContext portletDataContext) {

		return getPortletDataPath(
			portletDataContext, portletDataContext.getPortletId(),
			portletDataContext.getPlid());
	}

	public static String getPortletDataPath(
		PortletDataContext portletDataContext, String portletId, long plid) {

		StringBundler sb = new StringBundler(4);

		sb.append(getPortletPath(portletDataContext, portletId));
		sb.append(StringPool.SLASH);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		if (portlet.isPreferencesUniquePerLayout()) {
			sb.append(plid);
		}
		else {
			sb.append(portletDataContext.getScopeGroupId());
		}

		sb.append("/portlet-data.xml");

		return sb.toString();
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
	 * </pre></p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @return a portlet path for the portlet ID
	 */
	public static String getPortletPath(PortletDataContext portletDataContext) {
		return getPortletPath(
			portletDataContext, portletDataContext.getPortletId());
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
	 * </pre></p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @param  portletId the portlet ID the path is being generated for
	 * @return a portlet path for the portlet ID
	 */
	public static String getPortletPath(
		PortletDataContext portletDataContext, String portletId) {

		return StringBundler.concat(
			getRootPath(portletDataContext), StringPool.FORWARD_SLASH,
			PATH_PREFIX_PORTLET, StringPool.FORWARD_SLASH, portletId);
	}

	public static String getPortletPreferencesPath(
		PortletDataContext portletDataContext, String portletId, long ownerId,
		int ownerType, long plid) {

		return StringBundler.concat(
			getPortletPath(portletDataContext, portletId), "/preferences/",
			getOwnerTypePath(ownerType), ownerId, CharPool.FORWARD_SLASH, plid,
			"/portlet-preferences.xml");
	}

	/**
	 * Returns a root path, or fragment, of the model path based on the scope
	 * group ID from the portlet data context.
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
	 * </pre></p>
	 *
	 * @param  portletDataContext the context of the current export/import
	 *         process
	 * @return a root path, or fragment, of the model path
	 * @see    PortletDataContext#getScopeGroupId()
	 * @see    #getSourceRootPath(PortletDataContext)
	 */
	public static String getRootPath(PortletDataContext portletDataContext) {
		return getRootPath(
			PATH_PREFIX_GROUP, portletDataContext.getScopeGroupId());
	}

	public static String getServicePortletPreferencesPath(
		PortletDataContext portletDataContext, String serviceName, long ownerId,
		int ownerType) {

		return StringBundler.concat(
			getRootPath(portletDataContext), StringPool.FORWARD_SLASH,
			PATH_PREFIX_SERVICE, StringPool.FORWARD_SLASH, serviceName,
			StringPool.FORWARD_SLASH, getOwnerTypePath(ownerType), ownerId,
			"/portlet-preferences.xml");
	}

	/**
	 * Returns a source root path, or fragment, of the model path. This method
	 * is called by the {@link #getRootPath(PortletDataContext)} method. The
	 * fragment is generated with the source group ID from the portlet data
	 * context. This helper method is useful during the import process.
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
	 * </pre></p>
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

	protected static String getOwnerTypePath(int ownerType) {
		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			return "archived/";
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) {
			return "company/";
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
			return "group/";
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
			return "layout/";
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			return "user/";
		}

		return StringPool.BLANK;
	}

	protected static String getRootPath(
		String pathPrefix, long pathPrimaryKey) {

		return StringBundler.concat(
			StringPool.FORWARD_SLASH, pathPrefix, StringPool.FORWARD_SLASH,
			pathPrimaryKey);
	}

	private static final String _FILE_EXTENSION_XML = ".xml";

}