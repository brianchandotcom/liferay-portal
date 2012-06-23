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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifySocial extends VerifyProcess {

	protected void deleteDuplicateActivities() throws Exception {
		StringBundler sb = new StringBundler(14);

		sb.append("select distinct sa1.* from SocialActivity sa1 ");
		sb.append("inner join SocialActivity sa2 on ");
		sb.append("sa1.activityId != sa2.activityId and ");
		sb.append("sa1.groupId = sa2.groupId and ");
		sb.append("sa1.userId = sa2.userId and ");
		sb.append("sa1.classNameId = sa2.classNameId and ");
		sb.append("sa1.classPK = sa2.classPK and ");
		sb.append("sa1.type_ = sa2.type_ and ");
		sb.append("sa1.extraData = sa2.extraData and ");
		sb.append("sa1.receiverUserId = sa2.receiverUserId ");
		sb.append("where sa1.mirrorActivityId = 0 ");
		sb.append("order by sa1.groupId, sa1.userId, sa1.classNameId, ");
		sb.append("sa1.classPK, sa1.type_, sa1.extraData, ");
		sb.append("sa1.receiverUserId, sa1.createDate desc");

		String sql = sb.toString();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			long groupId = 0;
			long userId = 0;
			long classNameId = 0;
			long classPK = 0;
			long type = 0;
			String extraData = StringPool.BLANK;
			long receiverUserId = 0;

			while (rs.next()) {
				long curActivityId = rs.getLong("activityId");
				long curGroupId = rs.getLong("groupId");
				long curUserId = rs.getLong("userId");
				long curClassNameId = rs.getLong("classNameId");
				long curClassPK = rs.getLong("classPK");
				long curType = rs.getLong("type_");
				String curExtraData = rs.getString("extraData");
				long curReceiverUserId = rs.getLong("receiverUserId");

				if ((curGroupId == groupId) && (curUserId == userId) &&
					(curClassNameId == classNameId) &&
					(curClassPK == classPK) && (curType == type) &&
					curExtraData.equals(extraData) &&
					(curReceiverUserId == receiverUserId)) {

					SocialActivityLocalServiceUtil.deleteActivity(
						curActivityId);
				}
				else {
					groupId = curGroupId;
					userId = curUserId;
					classNameId = curClassNameId;
					classPK = curClassPK;
					type = curType;
					extraData = curExtraData;
					receiverUserId = curReceiverUserId;
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteDuplicateRequests() throws Exception {
		StringBundler sb = new StringBundler(13);

		sb.append("select distinct sr1.* from SocialRequest sr1 ");
		sb.append("inner join SocialRequest sr2 on ");
		sb.append("sr1.requestId != sr2.requestId and ");
		sb.append("sr1.groupId = sr2.groupId and ");
		sb.append("sr1.userId = sr2.userId and ");
		sb.append("sr1.classNameId = sr2.classNameId and ");
		sb.append("sr1.classPK = sr2.classPK and ");
		sb.append("sr1.type_ = sr2.type_ and ");
		sb.append("sr1.extraData = sr2.extraData and ");
		sb.append("sr1.receiverUserId = sr2.receiverUserId ");
		sb.append("order by sr1.groupId, sr1.userId, sr1.classNameId, ");
		sb.append("sr1.classPK, sr1.type_, sr1.extraData, ");
		sb.append("sr1.receiverUserId, sr1.createDate desc");

		String sql = sb.toString();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			long groupId = 0;
			long userId = 0;
			long classNameId = 0;
			long classPK = 0;
			long type = 0;
			String extraData = StringPool.BLANK;
			long receiverUserId = 0;

			while (rs.next()) {
				long curRequestId = rs.getLong("requestId");
				long curGroupId = rs.getLong("groupId");
				long curUserId = rs.getLong("userId");
				long curClassNameId = rs.getLong("classNameId");
				long curClassPK = rs.getLong("classPK");
				long curType = rs.getLong("type_");
				String curExtraData = rs.getString("extraData");
				long curReceiverUserId = rs.getLong("receiverUserId");

				if ((curGroupId == groupId) && (curUserId == userId) &&
					(curClassNameId == classNameId) &&
					(curClassPK == classPK) && (curType == type) &&
					curExtraData.equals(extraData) &&
					(curReceiverUserId == receiverUserId)) {

					SocialRequestLocalServiceUtil.deleteRequest(curRequestId);
				}
				else {
					groupId = curGroupId;
					userId = curUserId;
					classNameId = curClassNameId;
					classPK = curClassPK;
					type = curType;
					extraData = curExtraData;
					receiverUserId = curReceiverUserId;
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doVerify() throws Exception {

		// Temporarily comment these out because of performance issues. This
		// verification is not needed because activities can be added while
		// ensuring a duplicate does not exist. See LEP-6593.

		//deleteDuplicateActivities();
		//deleteDuplicateRequests();
	}

}