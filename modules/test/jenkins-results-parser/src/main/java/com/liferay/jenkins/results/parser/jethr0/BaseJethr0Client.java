/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.jethr0;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
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
	public void activeMQRequest(String message) {
		try {
			JenkinsResultsParserUtil.toString(
				_getActiveMQQueueURL(), message,
				_getActiveMQHTTPAuthorization());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public void activeMQSendMessage(String message) {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
			getActiveMQBrokerURL());

		try {
			Connection connection = connectionFactory.createConnection();

			connection.start();

			Session session = connection.createSession(
				false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue(getActiveMQQueueName());

			MessageProducer messageProducer = session.createProducer(queue);

			TextMessage textMessage = session.createTextMessage();

			textMessage.setText(message);

			messageProducer.send(textMessage);

			connection.close();
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

			String jobInvocationParameterValue =
				jenkinsBuildParameter.getValue();

			if (JenkinsResultsParserUtil.isNullOrEmpty(
					jobInvocationParameterValue)) {

				continue;
			}

			parametersJSONObject.put(
				jenkinsBuildParameter.getKey(), jobInvocationParameterValue);
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

		activeMQSendMessage(jsonObject.toString());
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
	public String springBootRequest(String urlPath) {
		return _requestSpringBootMessage(
			urlPath, null, JenkinsResultsParserUtil.HttpRequestMethod.GET);
	}

	@Override
	public String springBootRequest(String urlPath, String message) {
		return _requestSpringBootMessage(
			urlPath, message, JenkinsResultsParserUtil.HttpRequestMethod.POST);
	}

	protected BaseJethr0Client(JenkinsMaster jenkinsMaster) {
		_jenkinsMaster = jenkinsMaster;

		_environment = _getEnvironment();
	}

	protected abstract String getActiveMQBrokerURL();

	protected abstract String getActiveMQQueueName();

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

	protected Environment getEnvironment() {
		return _environment;
	}

	protected abstract URL getLiferayDXPURL();

	protected abstract String getOAuthClientSecret();

	protected abstract String getOAuthExternalReferenceCode();

	protected abstract URL getSpringBootURL();

	private JenkinsResultsParserUtil.HTTPAuthorization
		_getActiveMQHTTPAuthorization() {

		return new JenkinsResultsParserUtil.BasicHTTPAuthorization(
			getActiveMQUserName(), getActiveMQUserPassword());
	}

	private String _getActiveMQQueueURL() {
		return JenkinsResultsParserUtil.combine(
			String.valueOf(getActiveMQURL()), "/api/message/",
			getActiveMQQueueName(), "?type=queue");
	}

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

				for (String jenkinsMaster : jenkinsMasterNames.split(",")) {
					if (jenkinsMaster.equals(_jenkinsMaster.getName())) {
						return environment;
					}
				}
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		throw new RuntimeException("Unable to find Jethr0 Environment");
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

	private final Environment _environment;
	private final JenkinsMaster _jenkinsMaster;
	private String _oAuthAccessToken;
	private String _oAuthClientId;

}