/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.jethr0;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Jethr0BuildUpdater;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJethr0Client implements Jethr0Client {

	@Override
	public synchronized void connect() {
		if (_connection != null) {
			return;
		}

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
			getActiveMQBrokerURL());

		try {
			_connection = connectionFactory.createConnection();

			_connection.start();
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	@Override
	public void createBuild(
		String jenkinsJobName, Map<String, String> jenkinsBuildParameters,
		long jobId) {

		createBuild(
			jenkinsJobName, jenkinsBuildParameters, jobId, jenkinsJobName);
	}

	@Override
	public void createBuild(
		String jenkinsJobName, Map<String, String> jenkinsBuildParameters,
		long jobId, String buildName) {

		JSONObject jobJSONObject = new JSONObject();

		jobJSONObject.put("id", Long.valueOf(jobId));

		JSONObject parametersJSONObject = new JSONObject();

		for (Map.Entry<String, String> jenkinsBuildParameter :
				jenkinsBuildParameters.entrySet()) {

			String jenkinsBuildParameterName = jenkinsBuildParameter.getKey();

			if (!jenkinsBuildParameterName.matches("[A-Z0-9_]+")) {
				continue;
			}

			String jobInvocationParameterValue =
				jenkinsBuildParameter.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					jobInvocationParameterValue)) {

				continue;
			}

			parametersJSONObject.put(
				jenkinsBuildParameterName, jobInvocationParameterValue);
		}

		JSONObject buildJSONObject = new JSONObject();

		if (JenkinsResultsParserUtil.isNullOrEmpty(buildName)) {
			buildName = jenkinsJobName;
		}

		buildJSONObject.put(
			"jenkinsJobName", jenkinsJobName
		).put(
			"name", buildName
		).put(
			"parameters", parametersJSONObject
		);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"build", buildJSONObject
		).put(
			"eventTrigger", EventTrigger.CREATE_BUILD
		).put(
			"job", jobJSONObject
		);

		sendMessageToJethr0(jsonObject.toString());
	}

	@Override
	public synchronized void disconnect() {
		if (_connection == null) {
			return;
		}

		try {
			for (MessageConsumer messageConsumer : _messageConsumers.values()) {
				if (messageConsumer != null) {
					messageConsumer.close();
				}
			}

			if (_connection != null) {
				_connection.close();
			}
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
		finally {
			_connection = null;
			_messageConsumers.clear();
		}
	}

	@Override
	public Environment getEnvironment() {
		return _environment;
	}

	@Override
	public JenkinsMaster getJenkinsMaster() {
		return _jenkinsMaster;
	}

	@Override
	public JSONObject getJobJSONObject(long jobId) {
		return new JSONObject(
			springBootRequest(getSpringBootURL() + "/jobs/" + jobId, null));
	}

	@Override
	public String liferayDXPRequest(String urlPath) {
		return _requestLiferayDXPMessage(
			urlPath, null, JenkinsResultsParserUtil.HttpRequestMethod.GET);
	}

	public String liferayDXPRequest(String urlPath, String message) {
		return _requestLiferayDXPMessage(
			urlPath, message, JenkinsResultsParserUtil.HttpRequestMethod.POST);
	}

	@Override
	public void sendMessageToJethr0(String message) {
		connect();

		try {
			Session session = _connection.createSession(
				false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue(
				getActiveMQJRPToJethr0QueueName());

			MessageProducer messageProducer = session.createProducer(queue);

			TextMessage textMessage = session.createTextMessage(message);

			textMessage.setStringProperty(
				"jenkins-master-name", _jenkinsMaster.getName());

			messageProducer.send(textMessage);
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	@Override
	public String springBootRequest(String urlPath) {
		return _requestSpringBootMessage(
			urlPath, null, JenkinsResultsParserUtil.HttpRequestMethod.GET);
	}

	@Override
	public String springBootRequest(String urlPath, String message) {
		return _requestSpringBootMessage(
			urlPath, message, JenkinsResultsParserUtil.HttpRequestMethod.POST);
	}

	@Override
	public void subscribe(Jethr0BuildUpdater jethr0BuildUpdater) {
		long jenkinsBuildID = jethr0BuildUpdater.getJenkinsBuildID();

		if ((jenkinsBuildID <= 0) ||
			_messageConsumers.containsKey(jenkinsBuildID)) {

			return;
		}

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
			getActiveMQBrokerURL());

		try {
			Connection connection = connectionFactory.createConnection();

			connection.start();

			Session session = connection.createSession(
				false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue(
				getActiveMQJethr0ToJRPQueueName());

			MessageConsumer messageConsumer = session.createConsumer(
				queue, jethr0BuildUpdater.getMessageSelector());

			messageConsumer.setMessageListener(jethr0BuildUpdater);

			_messageConsumers.put(jenkinsBuildID, messageConsumer);
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
	}

	@Override
	public void unsubscribe(Jethr0BuildUpdater jethr0BuildUpdater) {
		long jenkinsBuildID = jethr0BuildUpdater.getJenkinsBuildID();

		if ((jenkinsBuildID <= 0) ||
			!_messageConsumers.containsKey(jenkinsBuildID)) {

			return;
		}

		try {
			MessageConsumer messageConsumer = _messageConsumers.get(
				jenkinsBuildID);

			if (messageConsumer == null) {
				return;
			}

			messageConsumer.close();
		}
		catch (JMSException jmsException) {
			throw new RuntimeException(jmsException);
		}
		finally {
			_messageConsumers.remove(jenkinsBuildID);
		}
	}

	protected BaseJethr0Client(JenkinsMaster jenkinsMaster) {
		_jenkinsMaster = jenkinsMaster;

		_environment = _getEnvironment();
	}

	protected abstract String getActiveMQBrokerURL();

	protected abstract String getActiveMQJethr0ToJRPQueueName();

	protected abstract String getActiveMQJRPToJethr0QueueName();

	protected abstract URL getActiveMQURL();

	protected abstract String getActiveMQUserName();

	protected abstract String getActiveMQUserPassword();

	protected String getBuildPropertyString(String buildPropertyName) {
		Environment environment = getEnvironment();

		try {
			return JenkinsResultsParserUtil.getBuildProperty(
				buildPropertyName, environment.getKey());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected URL getBuildPropertyURL(String buildPropertyName) {
		try {
			return new URL(getBuildPropertyString(buildPropertyName));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	protected abstract URL getLiferayDXPURL();

	protected abstract String getOAuthClientSecret();

	protected abstract String getOAuthExternalReferenceCode();

	protected abstract URL getSpringBootURL();

	private Environment _getEnvironment() {
		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			for (Environment environment : Environment.values()) {
				String jenkinsMasterNames =
					JenkinsResultsParserUtil.getProperty(
						buildProperties,
						"jethr0.jenkins.masters[" + environment.getKey() + "]");

				if (JenkinsResultsParserUtil.isNullOrEmpty(
						jenkinsMasterNames)) {

					continue;
				}

				for (String jenkinsMasterName : jenkinsMasterNames.split(",")) {
					if (jenkinsMasterName.equals(_jenkinsMaster.getName())) {
						return environment;
					}
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return Environment.DEVELOPMENT;
	}

	private String _getOAuthAccessToken() {
		if (_oAuthAccessToken != null) {
			return _oAuthAccessToken;
		}

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					String.valueOf(getLiferayDXPURL()),
					"/o/oauth2/token?client_id=", _getOAuthClientId(),
					"&client_secret=", getOAuthClientSecret(),
					"&grant_type=client_credentials"),
				false, JenkinsResultsParserUtil.HttpRequestMethod.POST);

			_oAuthAccessToken = jsonObject.getString("access_token");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _oAuthAccessToken;
	}

	private String _getOAuthClientId() {
		if (_oAuthClientId != null) {
			return _oAuthClientId;
		}

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					String.valueOf(getLiferayDXPURL()),
					"/o/oauth2/application?externalReferenceCode=",
					getOAuthExternalReferenceCode()));

			_oAuthClientId = jsonObject.getString("client_id");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _oAuthClientId;
	}

	private JenkinsResultsParserUtil.HTTPAuthorization
		_getSpringBootHTTPAuthorization() {

		return new JenkinsResultsParserUtil.BearerHTTPAuthorization(
			_getOAuthAccessToken());
	}

	private String _requestLiferayDXPMessage(
		String urlPath, String message,
		JenkinsResultsParserUtil.HttpRequestMethod httpRequestMethod) {

		try {
			return JenkinsResultsParserUtil.toString(
				getLiferayDXPURL() + "/" + urlPath, false, httpRequestMethod,
				message, _getSpringBootHTTPAuthorization());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String _requestSpringBootMessage(
		String urlPath, String message,
		JenkinsResultsParserUtil.HttpRequestMethod httpRequestMethod) {

		try {
			return JenkinsResultsParserUtil.toString(
				getSpringBootURL() + "/" + urlPath, false, httpRequestMethod,
				message, _getSpringBootHTTPAuthorization());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private Connection _connection;
	private final Environment _environment;
	private final JenkinsMaster _jenkinsMaster;
	private final Map<Long, MessageConsumer> _messageConsumers =
		new HashMap<>();
	private String _oAuthAccessToken;
	private String _oAuthClientId;

}