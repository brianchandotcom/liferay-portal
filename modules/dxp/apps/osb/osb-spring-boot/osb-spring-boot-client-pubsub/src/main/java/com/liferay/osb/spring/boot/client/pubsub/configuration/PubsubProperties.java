/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kyle Bischof
 */
@ConfigurationProperties("pubsub")
public class PubsubProperties {

	public Broker getBroker() {
		return _broker;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getProjectId() {
		return _projectId;
	}

	public Subscriber getSubscriber() {
		return _subscriber;
	}

	public void setBroker(Broker broker) {
		_broker = broker;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setProjectId(String projectId) {
		_projectId = projectId;
	}

	public void setSubscriber(Subscriber subscriber) {
		_subscriber = subscriber;
	}

	public static class Broker {

		public boolean isAutoCreate() {
			return _autoCreate;
		}

		public boolean isDeadLetterEnabled() {
			return _deadLetterEnabled;
		}

		public void setAutoCreate(boolean autoCreate) {
			_autoCreate = autoCreate;
		}

		public void setDeadLetterEnabled(boolean deadLetterEnabled) {
			_deadLetterEnabled = deadLetterEnabled;
		}

		private boolean _autoCreate = true;
		private boolean _deadLetterEnabled = true;

	}

	public static class Subscriber {

		public int getMaxDeliveryAttempts() {
			return _maxDeliveryAttempts;
		}

		public boolean isAutoCreate() {
			return _autoCreate;
		}

		public boolean isDeadLetterEnabled() {
			return _deadLetterEnabled;
		}

		public void setAutoCreate(boolean autoCreate) {
			_autoCreate = autoCreate;
		}

		public void setDeadLetterEnabled(boolean deadLetterEnabled) {
			_deadLetterEnabled = deadLetterEnabled;
		}

		public void setMaxDeliveryAttempts(int maxDeliveryAttempts) {
			_maxDeliveryAttempts = maxDeliveryAttempts;
		}

		private boolean _autoCreate = true;
		private boolean _deadLetterEnabled = true;
		private int _maxDeliveryAttempts = 5;

	}

	private Broker _broker = new Broker();
	private String _namespace = "";
	private String _projectId;
	private Subscriber _subscriber = new Subscriber();

}