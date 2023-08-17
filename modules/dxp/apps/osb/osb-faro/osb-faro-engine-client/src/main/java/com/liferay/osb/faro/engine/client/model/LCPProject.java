/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class LCPProject {

	public long getBuildGroupUidCounter() {
		return _buildGroupUidCounter;
	}

	public String getCluster() {
		return _cluster;
	}

	public Date getCreatedAt() {
		if (_createdAt == null) {
			return null;
		}

		return new Date(_createdAt.getTime());
	}

	public String getESProjectId() {
		Cluster cluster = Cluster.fromString(_cluster);

		if (cluster != null) {
			return cluster.getProjectId();
		}

		return null;
	}

	public String getHealth() {
		return _health;
	}

	public String getId() {
		return _id;
	}

	public String getLoadBalancerIp() {
		return _loadBalancerIp;
	}

	public String getOwnerId() {
		return _ownerId;
	}

	public String getProjectId() {
		return _projectId;
	}

	public String getStatus() {
		return _status;
	}

	public void setBuildGroupUidCounter(long buildGroupUidCounter) {
		_buildGroupUidCounter = buildGroupUidCounter;
	}

	public void setCluster(String cluster) {
		_cluster = cluster;
	}

	public void setCreatedAt(Date createdAt) {
		if (createdAt != null) {
			_createdAt = new Date(createdAt.getTime());
		}
	}

	public void setHealth(String health) {
		_health = health;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLoadBalancerIp(String loadBalancerIp) {
		_loadBalancerIp = loadBalancerIp;
	}

	public void setOwnerId(String ownerId) {
		_ownerId = ownerId;
	}

	public void setProjectId(String projectId) {
		_projectId = projectId;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public enum Cluster {

		AS1("ac-asiasouth1", "asia-south1-ac5-c1"),
		DEV("ac-asahdev", "us-west1-ac-uat-c1-2"),
		EU2("ac-europewest2", "europe-west2-ac2-c1"),
		EU3("ac-europewest3", "europe-west3-ac3-c1"),
		SA("ac-southamericaeast1", "southamerica-east1-ac1-c1"),
		UAT("ac-asahuat", "us-west1-ac-uat-c1"),
		US("ac-uswest1", "us-west1-ac4-c1"),
		US_LRDCOM(
			"ac-uswest1asah652a6babdba143d086a19db542781bc2",
			"us-west1-ac4-c1-2");

		public static Cluster fromString(String value) {
			if (StringUtil.equals(value, Cluster.AS1._value)) {
				return Cluster.AS1;
			}

			if (StringUtil.equals(value, Cluster.DEV._value)) {
				return Cluster.DEV;
			}

			if (StringUtil.equals(value, Cluster.EU2._value)) {
				return Cluster.EU2;
			}

			if (StringUtil.equals(value, Cluster.EU3._value)) {
				return Cluster.EU3;
			}

			if (StringUtil.equals(value, Cluster.SA._value)) {
				return Cluster.SA;
			}

			if (StringUtil.equals(value, Cluster.UAT._value)) {
				return Cluster.UAT;
			}

			if (StringUtil.equals(value, Cluster.US._value)) {
				return Cluster.US;
			}

			if (StringUtil.equals(value, Cluster.US_LRDCOM._value)) {
				return Cluster.US_LRDCOM;
			}

			return null;
		}

		public String getBaseURL() {
			return _baseURL;
		}

		public String getProjectId() {
			return _projectId;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Cluster(String projectId, String value) {
			_projectId = projectId;
			_value = value;

			_baseURL = String.format(
				"https://{service}-%s.lfr.cloud/", projectId);
		}

		private final String _baseURL;
		private final String _projectId;
		private final String _value;

	}

	private long _buildGroupUidCounter;
	private String _cluster;
	private Date _createdAt;
	private String _health;
	private String _id;
	private String _loadBalancerIp;
	private String _ownerId;
	private String _projectId;
	private String _status;

}