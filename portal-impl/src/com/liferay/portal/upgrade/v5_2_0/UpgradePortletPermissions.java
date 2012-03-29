/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.SmartResultSet;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.persistence.ResourceCodeUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Ferrer
 */
public class UpgradePortletPermissions extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updatePortletPermissions(
			"33", "com.liferay.portlet.blogs", new String[] {"ADD_ENTRY"});

		updatePortletPermissions(
			"28", "com.liferay.portlet.bookmarks", new String[] {"ADD_FOLDER"});

		updatePortletPermissions(
			"8", "com.liferay.portlet.calendar",
			new String[] {"ADD_EVENT", "EXPORT_ALL_EVENTS"});

		updatePortletPermissions(
			"20", "com.liferay.portlet.documentlibrary",
			new String[] {"ADD_FOLDER"});

		updatePortletPermissions(
			"31", "com.liferay.portlet.imagegallery",
			new String[] {"ADD_FOLDER"});

		updatePortletPermissions(
			"15", "com.liferay.portlet.journal",
			new String[] {
				"ADD_ARTICLE", "ADD_FEED", "ADD_STRUCTURE", "ADD_TEMPLATE",
				"APPROVE_ARTICLE"
			});

		updatePortletPermissions(
			"19", "com.liferay.portlet.messageboards",
			new String[] {"ADD_CATEGORY", "BAN_USER"});

		updatePortletPermissions(
			"25", "com.liferay.portlet.polls", new String[] {"ADD_QUESTION"});

		updatePortletPermissions(
			"34", "com.liferay.portlet.shopping",
			new String[] {"ADD_CATEGORY", "MANAGE_COUPONS", "MANAGE_ORDERS"});

		updatePortletPermissions(
			"98", "com.liferay.portlet.softwarecatalog",
			new String[] {"ADD_FRAMEWORK_VERSION", "ADD_PRODUCT_ENTRY"});

		updatePortletPermissions(
			"99", "com.liferay.portlet.tags",
			new String[] {"ADD_ENTRY", "ADD_VOCABULARY"});

		updatePortletPermissions(
			"36", "com.liferay.portlet.wiki", new String[] {"ADD_NODE"});
	}

	protected Object[] getLayout(long plid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_LAYOUT);

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			if (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");

				return new Object[] {groupId, companyId};
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getPortletPermissionsCount(
			String actionId, long resourceId, String modelName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("select count(*) from Permission_ ");
			sb.append("inner join Resource_ on Resource_.resourceId = ");
			sb.append("Permission_.resourceId inner join ResourceCode on ");
			sb.append("ResourceCode.codeId = Resource_.codeId where ");
			sb.append("Permission_.actionId = ? and ");
			sb.append("Permission_.resourceId = ? and ResourceCode.name = ? ");
			sb.append("and ResourceCode.scope = ? ");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, actionId);
			ps.setLong(2, resourceId);
			ps.setString(3, modelName);
			ps.setInt(4, ResourceConstants.SCOPE_INDIVIDUAL);

			rs = ps.executeQuery();

			rs.next();

			return rs.getLong(1);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePortletPermission(
			long permissionId, String actionId, String primKey,
			String modelName, int scope)
		throws Exception {

		long plid = GetterUtil.getLong(
			primKey.substring(
				0, primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR)));

		Object[] layout = getLayout(plid);

		if (layout == null) {
			return;
		}

		long groupId = (Long)layout[0];
		long companyId = (Long)layout[1];

		Resource resource = null;

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			resource = addResource_6(companyId,
					modelName, scope, String.valueOf(groupId));
		}
		else {
			resource = addResource_1to5(companyId,
					modelName, scope, String.valueOf(groupId));
		}

		long portletPermissionCount = getPortletPermissionsCount(
			actionId, resource.getResourceId(), modelName);

		if (portletPermissionCount == 0) {
			runSQL(
				"update Permission_ set resourceId = " +
					resource.getResourceId() + " where permissionId = " +
						permissionId);
		}
		else {
			runSQL(
				"delete from Permission_ where permissionId = " + permissionId);
		}
	}

	protected void updatePortletPermissions(
			String portletName, String modelName, String[] actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(4 * actionIds.length + 7);

			sb.append("select Permission_.permissionId, ");
			sb.append("Permission_.actionId, Resource_.primKey, ");
			sb.append("ResourceCode.scope from Permission_ ");
			sb.append("inner join Resource_ on Resource_.resourceId = ");
			sb.append("Permission_.resourceId inner join ResourceCode on ");
			sb.append("ResourceCode.codeId = Resource_.codeId where (");

			for (int i = 0; i < actionIds.length; i++) {
				String actionId = actionIds[i];

				sb.append("Permission_.actionId = '");
				sb.append(actionId);
				sb.append("'");

				if (i < (actionIds.length - 1)) {
					sb.append(" or ");
				}
			}

			sb.append(") and ResourceCode.name = ? and ResourceCode.scope = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, portletName);
			ps.setInt(2, ResourceConstants.SCOPE_INDIVIDUAL);

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long permissionId = srs.getLong("Permission_.permissionId");
				String actionId = srs.getString("Permission_.actionId");
				String primKey = srs.getString("Resource_.primKey");
				int scope = srs.getInt("ResourceCode.scope");

				try {
					updatePortletPermission(
						permissionId, actionId, primKey, modelName, scope);
				}
				catch (Exception e) {
					_log.error(
						"Unable to upgrade permission " + permissionId, e);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected Resource addResource_1to5(
			long companyId, String name, int scope, String primKey)
		throws SystemException {
		ResourceCode resourceCode = ResourceCodeUtil.fetchByC_N_S(
				companyId, name, scope);

		long codeId = resourceCode.getCodeId();
		// TODO put sql version here

		return null;

//		Resource resource = resourcePersistence.fetchByC_P(codeId, primKey);
//
//		if (resource == null) {
//			long resourceId = counterLocalService.increment(
//				Resource.class.getName());
//
//			resource = resourcePersistence.create(resourceId);
//
//			resource.setCodeId(codeId);
//			resource.setPrimKey(primKey);
//
//			try {
//				resourcePersistence.update(resource, false);
//			}
//			catch (SystemException se) {
//				if (_log.isWarnEnabled()) {
//					_log.warn(
//						"Add failed, fetch {codeId=" + codeId + ", primKey=" +
//							primKey + "}");
//				}
//
//				resource = resourcePersistence.fetchByC_P(
//					codeId, primKey, false);
//
//				if (resource == null) {
//					throw se;
//				}
//			}
//		}
//
//		return resource;
	}

	protected Resource addResource_6(
		long companyId, String name, int scope, String primKey) {

		Resource resource = new ResourceImpl();

		resource.setCompanyId(companyId);
		resource.setName(name);
		resource.setScope(scope);
		resource.setPrimKey(primKey);

		return resource;
	}

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

	private static Log _log = LogFactoryUtil.getLog(
		UpgradePortletPermissions.class);

}