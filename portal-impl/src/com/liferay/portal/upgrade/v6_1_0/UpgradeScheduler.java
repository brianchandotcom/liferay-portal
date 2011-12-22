/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.JobStateSerializeUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PropsValues;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class UpgradeScheduler extends UpgradeProcess {

	protected void deleteJob(String jobName, String jobGroup)
		throws Exception {

		runSQL(
			"delete from QUARTZ_CRON_TRIGGERS where TRIGGER_NAME = '" +
				jobName + "' and TRIGGER_GROUP = '" + jobGroup + "'");

		runSQL(
			"delete from QUARTZ_JOB_DETAILS where JOB_NAME = '" +
				jobName + "' and JOB_GROUP = '" + jobGroup + "'");

		runSQL(
			"delete from QUARTZ_SIMPLE_TRIGGERS where TRIGGER_NAME = '" +
				jobName + "' and TRIGGER_GROUP = '" + jobGroup + "'");

		runSQL(
			"delete from QUARTZ_TRIGGERS where TRIGGER_NAME = '" +
				jobName + "' and TRIGGER_GROUP = '" + jobGroup + "'");
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		List<Object[]> arrays = getUpgradeQuartzData();

		if (arrays.isEmpty()) {
			return;
		}

		for (Object[] array : arrays) {
			String jobName = (String)array[0];
			String jobGroup = (String)array[1];
			String schedName = (String)array[2];
			byte[] jobData = (byte[])array[3];

			if (jobData == null) {
				deleteJob(jobName, jobGroup);
			}
			else {
				updateJobDetail(jobName, jobGroup, schedName, jobData);
				updateTrigger(jobName, jobGroup, schedName, "QUARTZ_TRIGGERS");
				updateTrigger(
					jobName, jobGroup, schedName, "QUARTZ_SIMPLE_TRIGGERS");
				updateTrigger(
					jobName, jobGroup, schedName, "QUARTZ_CRON_TRIGGERS");
			}
		}
	}

	protected List<Object[]> getUpgradeQuartzData() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select SCHED_NAME, JOB_NAME, JOB_GROUP, JOB_DATA from " +
					"QUARTZ_JOB_DETAILS");

			rs = ps.executeQuery();

			List<Object[]> arrays = new ArrayList<Object[]>();

			while (rs.next()) {
				String schedName = rs.getString("SCHED_NAME");
				String jobName = rs.getString("JOB_NAME");
				String jobGroup = rs.getString("JOB_GROUP");
				byte[] jobData = rs.getBytes("JOB_DATA");

				Object[] array = new Object[4];

				if (jobData == null) {
					array[0] = jobName;
					array[1] = jobGroup;
					array[2] = schedName;
					array[3] = null;

					arrays.add(array);

					continue;
				}

				ObjectInputStream objectInputStream =
					new ObjectInputStream(
						new UnsyncByteArrayInputStream(jobData));

				Map<Object, Object> jobDataMap =
					(Map<Object, Object>)objectInputStream.readObject();

				objectInputStream.close();

				String destinationName = (String)jobDataMap.get(
					SchedulerEngine.DESTINATION_NAME);

				if (!destinationName.equals(
						DestinationNames.LAYOUTS_LOCAL_PUBLISHER) &&
					!destinationName.equals(
						DestinationNames.LAYOUTS_REMOTE_PUBLISHER)) {

					array[0] = jobName;
					array[1] = jobGroup;
					array[2] = schedName;
					array[3] = null;

					arrays.add(array);

					continue;
				}

				Map<String, Object> jobStateMap =
					(Map<String, Object>)jobDataMap.get(
						SchedulerEngine.JOB_STATE);

				if (jobStateMap == null) {
					jobDataMap.put(
						SchedulerEngine.STORAGE_TYPE,
						StorageType.PERSISTED.toString());

					String messageJSON = (String)jobDataMap.get(
						SchedulerEngine.MESSAGE);

					Message message = (Message)JSONFactoryUtil.deserialize(
						messageJSON);

					int exceptionsMaxSize = message.getInteger(
						SchedulerEngine.EXCEPTIONS_MAX_SIZE);

					JobState jobState = new JobState(
						TriggerState.NORMAL, exceptionsMaxSize);

					jobDataMap.put(
						SchedulerEngine.JOB_STATE,
						JobStateSerializeUtil.serialize(jobState));
				}

				UnsyncByteArrayOutputStream newJobDataOutputStream =
					new UnsyncByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					newJobDataOutputStream);

				objectOutputStream.writeObject(jobDataMap);

				objectOutputStream.close();

				jobData = newJobDataOutputStream.toByteArray();

				array[0] = jobName;
				array[1] = jobGroup;
				array[2] = _DEFAULT_SCHED_NAME;
				array[3] = jobData;

				arrays.add(array);
			}

			return arrays;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateJobDetail(
			String jobName, String jobGroup, String schedName, byte[] jobData)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update QUARTZ_JOB_DETAILS set JOB_DATA = ? , SCHED_NAME = ? " +
					"where JOB_NAME = ? and JOB_GROUP = ?");

			ps.setBytes(1, jobData);
			ps.setString(2, schedName);
			ps.setString(3, jobName);
			ps.setString(4, jobGroup);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateTrigger(
			String triggerName, String triggerGroup, String schedName,
			String tableName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update " + tableName + " set SCHED_NAME = ? where " +
					"TRIGGER_NAME = ? and TRIGGER_GROUP = ?");

			ps.setString(1, schedName);
			ps.setString(2, triggerName);
			ps.setString(3, triggerGroup);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _DEFAULT_SCHED_NAME =
		PropsValues.PERSITED_SCHEDULER_ORG_QUARTZ_SCHEDULER_INSTANCENAME;

}