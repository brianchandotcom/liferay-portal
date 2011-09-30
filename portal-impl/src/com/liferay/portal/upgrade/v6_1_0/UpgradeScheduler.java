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
			byte[] jobData = (byte[])array[2];

			if (jobData == null) {
				deleteJob(jobName, jobGroup);
			}
			else {
				updateJobDetail(jobName, jobGroup, jobData);
			}
		}
	}

	protected List<Object[]> getUpgradeQuartzData() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Object[]> arrays = new ArrayList<Object[]>();

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select JOB_NAME, JOB_GROUP, JOB_DATA from QUARTZ_JOB_DETAILS");

			rs = ps.executeQuery();

			while (rs.next()) {
				String jobName = rs.getString("JOB_NAME");
				String jobGroup = rs.getString("JOB_GROUP");
				byte[] jobData = rs.getBytes("JOB_DATA");

				Object[] array = new Object[3];

				if (jobData == null) {
					array[0] = jobName;
					array[1] = jobGroup;
					array[2] = null;

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
					array[2] = null;

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
				array[2] = jobData;

				arrays.add(array);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return arrays;
	}

	protected void updateJobDetail(
			String jobName, String jobGroup, byte[] jobData)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update QUARTZ_JOB_DETAILS set JOB_DATA = ? where JOB_NAME = " +
					"? and JOB_GROUP = ?");

			ps.setBytes(1, jobData);
			ps.setString(2, jobName);
			ps.setString(3, jobGroup);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}