/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.util;

import com.liferay.oauth.configuration.OAuthConfigurationValues;
import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.OAuthApplicationLocalServiceUtil;
import com.liferay.oauth.service.OAuthUserLocalServiceUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.oauth.OAuthException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.server.OAuthServlet;

/**
 * @author Ivica Cardic
 * @author Raymond Augé
 * @author Igor Beslic
 */
public class V10aOAuth implements OAuth {

	public V10aOAuth(OAuthValidator oAuthValidator) {
		_oAuthValidator = oAuthValidator;

		if (_log.isDebugEnabled()) {
			_portalCache.registerPortalCacheListener(
				new V10aOAuthDebugCacheListener<>());
		}
	}

	@Override
	public String addParameters(String url, String... parameters)
		throws OAuthException {

		try {
			return net.oauth.OAuth.addParameters(url, parameters);
		}
		catch (IOException ioe) {
			throw new OAuthException(ioe);
		}
	}

	@Override
	public void authorize(
			OAuthAccessor oAuthAccessor, long userId,
			ServiceContext serviceContext)
		throws PortalException {

		Boolean authorized = (Boolean)oAuthAccessor.getProperty(
			OAuthAccessorConstants.AUTHORIZED);

		if ((authorized != null) && authorized.booleanValue() &&
			Validator.isNotNull(oAuthAccessor.getRequestToken())) {

			throw new OAuthException(net.oauth.OAuth.Problems.TOKEN_EXPIRED);
		}

		oAuthAccessor.setProperty(
			OAuthAccessorConstants.AUTHORIZED, Boolean.TRUE);
		oAuthAccessor.setProperty(OAuthAccessorConstants.USER_ID, userId);

		_put(oAuthAccessor.getRequestToken(), oAuthAccessor);
	}

	@Override
	public void formEncode(
			String token, String tokenSecret, OutputStream outputStream)
		throws OAuthException {

		List<net.oauth.OAuth.Parameter> parameters = net.oauth.OAuth.newList(
			net.oauth.OAuth.OAUTH_TOKEN, token,
			net.oauth.OAuth.OAUTH_TOKEN_SECRET, tokenSecret);

		try {
			net.oauth.OAuth.formEncode(parameters, outputStream);
		}
		catch (IOException ioe) {
			throw new OAuthException(ioe);
		}
	}

	@Override
	public void generateAccessToken(
			OAuthAccessor oAuthAccessor, long userId,
			ServiceContext serviceContext)
		throws PortalException {

		Boolean authorized = (Boolean)oAuthAccessor.getProperty(
			OAuthAccessorConstants.AUTHORIZED);

		if ((authorized != null) && authorized.booleanValue() &&
			Validator.isNotNull(oAuthAccessor.getAccessToken())) {

			throw new OAuthException(net.oauth.OAuth.Problems.TOKEN_EXPIRED);
		}

		OAuthConsumer oAuthConsumer = oAuthAccessor.getOAuthConsumer();

		OAuthApplication oAuthApplication = oAuthConsumer.getOAuthApplication();

		String consumerKey = oAuthApplication.getConsumerKey();

		String token = randomizeToken(consumerKey);

		oAuthAccessor.setAccessToken(token);

		oAuthAccessor.setRequestToken(null);

		String tokenSecret = randomizeToken(consumerKey.concat(token));

		oAuthAccessor.setTokenSecret(tokenSecret);

		OAuthUser oAuthUser = OAuthUserLocalServiceUtil.fetchOAuthUser(
			userId, oAuthApplication.getOAuthApplicationId());

		if (oAuthUser == null) {
			OAuthUserLocalServiceUtil.addOAuthUser(
				userId, oAuthApplication.getOAuthApplicationId(),
				oAuthAccessor.getAccessToken(), oAuthAccessor.getTokenSecret(),
				serviceContext);
		}
		else {
			if (oAuthApplication.isShareableAccessToken()) {
				oAuthAccessor.setAccessToken(oAuthUser.getAccessToken());
				oAuthAccessor.setTokenSecret(oAuthUser.getAccessSecret());
			}
			else {
				OAuthUserLocalServiceUtil.updateOAuthUser(
					userId, oAuthUser.getOAuthApplicationId(),
					oAuthAccessor.getAccessToken(),
					oAuthAccessor.getTokenSecret(), serviceContext);
			}
		}

		_put(token, oAuthAccessor);
	}

	@Override
	public void generateRequestToken(OAuthAccessor oAuthAccessor) {
		OAuthConsumer oAuthConsumer = oAuthAccessor.getOAuthConsumer();

		OAuthApplication oAuthApplication = oAuthConsumer.getOAuthApplication();

		String consumerKey = oAuthApplication.getConsumerKey();

		oAuthAccessor.setAccessToken(null);

		String token = randomizeToken(consumerKey);

		oAuthAccessor.setRequestToken(token);

		String tokenSecret = randomizeToken(consumerKey.concat(token));

		oAuthAccessor.setTokenSecret(tokenSecret);

		_put(token, oAuthAccessor);
	}

	@Override
	public OAuthAccessor getOAuthAccessor(OAuthMessage oAuthMessage)
		throws OAuthException {

		String token = null;

		try {
			token = oAuthMessage.getToken();
		}
		catch (IOException ioe) {
			throw new OAuthException(ioe);
		}

		OAuthAccessor oAuthAccessor = (OAuthAccessor)_portalCache.get(token);

		if (oAuthAccessor == null) {
			throw new OAuthException(net.oauth.OAuth.Problems.TOKEN_EXPIRED);
		}

		return oAuthAccessor;
	}

	@Override
	public OAuthConsumer getOAuthConsumer(OAuthMessage requestMessage)
		throws PortalException {

		String consumerKey = null;

		try {
			consumerKey = requestMessage.getConsumerKey();
		}
		catch (IOException ioe) {
			throw new OAuthException(ioe);
		}

		OAuthApplication oAuthApplication =
			OAuthApplicationLocalServiceUtil.fetchOAuthApplication(consumerKey);

		if (oAuthApplication == null) {
			throw new OAuthException(
				net.oauth.OAuth.Problems.CONSUMER_KEY_REFUSED);
		}

		return new DefaultOAuthConsumer(oAuthApplication);
	}

	@Override
	public OAuthMessage getOAuthMessage(HttpServletRequest request) {
		return getOAuthMessage(request, null);
	}

	@Override
	public OAuthMessage getOAuthMessage(
		HttpServletRequest request, String url) {

		return new DefaultOAuthMessage(OAuthServlet.getMessage(request, url));
	}

	@Override
	public OAuthMessage getOAuthMessage(PortletRequest portletRequest) {
		return getOAuthMessage(portletRequest, null);
	}

	@Override
	public OAuthMessage getOAuthMessage(
		PortletRequest portletRequest, String url) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getOAuthMessage(request, url);
	}

	@Override
	public void handleException(
			HttpServletRequest request, HttpServletResponse response,
			Exception exception, boolean sendBody)
		throws OAuthException {

		if (exception.getCause() != null) {
			exception = (Exception)exception.getCause();
		}

		try {
			OAuthServlet.handleException(
				response, exception, OAuthConfigurationValues.OAUTH_REALM,
				sendBody);
		}
		catch (Exception e) {
			throw new OAuthException(e);
		}
	}

	@Override
	public String randomizeToken(String token) {
		return DigesterUtil.digestHex(
			Digester.MD5, token, PwdGenerator.getPassword());
	}

	@Override
	public void validateOAuthMessage(
			OAuthMessage oAuthMessage, OAuthAccessor accessor)
		throws OAuthException {

		_oAuthValidator.validateOAuthMessage(oAuthMessage, accessor);
	}

	protected static OAuthAccessor deserialize(byte[] bytes) {
		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(bytes));

		try {
			DefaultOAuthAccessor oAuthAccessor = deserializer.readObject();

			return oAuthAccessor;
		}
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		return null;
	}

	protected byte[] serialize(OAuthAccessor oAuthAccessor) {
		Serializer serializer = new Serializer();

		serializer.writeObject((DefaultOAuthAccessor)oAuthAccessor);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return byteBuffer.array();
	}

	private static String _getServletContextName() {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		return ClassLoaderPool.getContextName(classLoader);
	}

	@SuppressWarnings("unused")
	private static void _put(String key, byte[] bytes) {
		OAuthAccessor oAuthAccessor = deserialize(bytes);

		_portalCache.put(key, oAuthAccessor);
	}

	private void _notifyCluster(String key, OAuthAccessor oAuthAccessor)
		throws Exception {

		MethodHandler putMethodHandler = new MethodHandler(
			_putMethodKey, key, serialize(oAuthAccessor));

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			putMethodHandler, true);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		futureClusterResponses.get();
	}

	private void _put(String key, OAuthAccessor oAuthAccessor) {
		_portalCache.put(key, oAuthAccessor);

		if (ClusterExecutorUtil.isEnabled()) {
			try {
				_notifyCluster(key, oAuthAccessor);

				if (_log.isDebugEnabled()) {
					_log.debug("Notified cluster");
				}
			}
			catch (Exception se) {
				_log.error("Unable to notify cluster", se);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(V10aOAuth.class);

	private static final PortalCache<Serializable, Object> _portalCache =
		SingleVMPoolUtil.getPortalCache(V10aOAuth.class.getName());
	private static final MethodKey _putMethodKey = new MethodKey(
		V10aOAuth.class, "_put", String.class, byte[].class);

	private final OAuthValidator _oAuthValidator;

}