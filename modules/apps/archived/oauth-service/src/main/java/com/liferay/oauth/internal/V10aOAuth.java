/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.oauth.internal;

import com.liferay.oauth.configuration.OAuthConfigurationValues;
import com.liferay.oauth.constants.OAuthAccessorConstants;
import com.liferay.oauth.exception.OAuthException;
import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.OAuthApplicationLocalService;
import com.liferay.oauth.service.OAuthUserLocalService;
import com.liferay.oauth.util.DefaultOAuthAccessor;
import com.liferay.oauth.util.DefaultOAuthConsumer;
import com.liferay.oauth.util.DefaultOAuthMessage;
import com.liferay.oauth.util.DefaultOAuthValidator;
import com.liferay.oauth.util.OAuth;
import com.liferay.oauth.util.OAuthAccessor;
import com.liferay.oauth.util.OAuthConsumer;
import com.liferay.oauth.util.OAuthMessage;
import com.liferay.oauth.util.OAuthValidator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Portal;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Raymond Augé
 * @author Igor Beslic
 */
@Component(immediate = true, service = OAuth.class)
public class V10aOAuth implements IdentifiableOSGiService, OAuth {

	@Override
	public String addParameters(String url, String... parameters)
		throws OAuthException {

		try {
			return net.oauth.OAuth.addParameters(url, parameters);
		}
		catch (IOException ioException) {
			throw new OAuthException(ioException);
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
		catch (IOException ioException) {
			throw new OAuthException(ioException);
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

		oAuthAccessor.setTokenSecret(randomizeToken(consumerKey.concat(token)));

		OAuthUser oAuthUser = _oAuthUserLocalService.fetchOAuthUser(
			userId, oAuthApplication.getOAuthApplicationId());

		if (oAuthUser == null) {
			_oAuthUserLocalService.addOAuthUser(
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
				_oAuthUserLocalService.updateOAuthUser(
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

		oAuthAccessor.setTokenSecret(randomizeToken(consumerKey.concat(token)));

		_put(token, oAuthAccessor);
	}

	@Override
	public OAuthAccessor getOAuthAccessor(OAuthMessage oAuthMessage)
		throws OAuthException {

		String token = null;

		try {
			token = oAuthMessage.getToken();
		}
		catch (IOException ioException) {
			throw new OAuthException(ioException);
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
		catch (IOException ioException) {
			throw new OAuthException(ioException);
		}

		OAuthApplication oAuthApplication =
			_oAuthApplicationLocalService.fetchOAuthApplication(consumerKey);

		if (oAuthApplication == null) {
			throw new OAuthException(
				net.oauth.OAuth.Problems.CONSUMER_KEY_REFUSED);
		}

		return new DefaultOAuthConsumer(oAuthApplication);
	}

	@Override
	public OAuthMessage getOAuthMessage(HttpServletRequest httpServletRequest) {
		return getOAuthMessage(httpServletRequest, null);
	}

	@Override
	public OAuthMessage getOAuthMessage(
		HttpServletRequest httpServletRequest, String url) {

		return new DefaultOAuthMessage(
			OAuthServlet.getMessage(httpServletRequest, url));
	}

	@Override
	public OAuthMessage getOAuthMessage(PortletRequest portletRequest) {
		return getOAuthMessage(portletRequest, null);
	}

	@Override
	public OAuthMessage getOAuthMessage(
		PortletRequest portletRequest, String url) {

		return getOAuthMessage(
			_portal.getHttpServletRequest(portletRequest), url);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return V10aOAuth.class.getName();
	}

	@Override
	public void handleException(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception exception1,
			boolean sendBody)
		throws OAuthException {

		if (exception1.getCause() != null) {
			exception1 = (Exception)exception1.getCause();
		}

		try {
			OAuthServlet.handleException(
				httpServletResponse, exception1,
				OAuthConfigurationValues.OAUTH_REALM, sendBody);
		}
		catch (Exception exception2) {
			throw new OAuthException(exception2);
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

	@Activate
	protected void activate() {
		_oAuthValidator = new DefaultOAuthValidator();

		_portalCache =
			(PortalCache<Serializable, Object>)_singleVMPool.getPortalCache(
				V10aOAuth.class.getName());

		if (_log.isDebugEnabled()) {
			_portalCache.registerPortalCacheListener(
				new V10aOAuthDebugCacheListener());
		}
	}

	private static OAuthAccessor _deserialize(byte[] bytes) {
		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(bytes));

		try {
			return deserializer.readObject();
		}
		catch (ClassNotFoundException classNotFoundException) {
			classNotFoundException.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unused")
	private static void _put(
		String osgiServiceIdentifier, String key, byte[] bytes) {

		OAuthAccessor oAuthAccessor = _deserialize(bytes);

		V10aOAuth v10aOAuth =
			(V10aOAuth)IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
				osgiServiceIdentifier);

		PortalCache<Serializable, Object> portalCache = v10aOAuth._portalCache;

		portalCache.put(key, oAuthAccessor);
	}

	private void _notifyCluster(String key, OAuthAccessor oAuthAccessor)
		throws Exception {

		MethodHandler putMethodHandler = new MethodHandler(
			_putMethodKey, getOSGiServiceIdentifier(), key,
			_serialize(oAuthAccessor));

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			putMethodHandler, true);

		FutureClusterResponses futureClusterResponses =
			_clusterExecutor.execute(clusterRequest);

		futureClusterResponses.get();
	}

	private void _put(String key, OAuthAccessor oAuthAccessor) {
		_portalCache.put(key, oAuthAccessor);

		if (_clusterExecutor.isEnabled()) {
			try {
				_notifyCluster(key, oAuthAccessor);

				if (_log.isDebugEnabled()) {
					_log.debug("Notified cluster");
				}
			}
			catch (Exception exception) {
				_log.error("Unable to notify cluster", exception);
			}
		}
	}

	private byte[] _serialize(OAuthAccessor oAuthAccessor) {
		Serializer serializer = new Serializer();

		serializer.writeObject((DefaultOAuthAccessor)oAuthAccessor);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return byteBuffer.array();
	}

	private static final Log _log = LogFactoryUtil.getLog(V10aOAuth.class);

	private static final MethodKey _putMethodKey = new MethodKey(
		V10aOAuth.class, "_put", String.class, String.class, byte[].class);

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private OAuthApplicationLocalService _oAuthApplicationLocalService;

	@Reference
	private OAuthUserLocalService _oAuthUserLocalService;

	private OAuthValidator _oAuthValidator;

	@Reference
	private Portal _portal;

	private PortalCache<Serializable, Object> _portalCache;

	@Reference
	private SingleVMPool _singleVMPool;

	private static class V10aOAuthDebugCacheListener
		implements PortalCacheListener<Serializable, Object> {

		@Override
		public void dispose() {
		}

		@Override
		public void notifyEntryEvicted(
				PortalCache<Serializable, Object> portalCache, Serializable key,
				Object value, int timeToLive)
			throws PortalCacheException {

			_logDebug("Entry evicted", key, value);
		}

		@Override
		public void notifyEntryExpired(
				PortalCache<Serializable, Object> portalCache, Serializable key,
				Object value, int timeToLive)
			throws PortalCacheException {

			_logDebug("Entry expired", key, value);
		}

		@Override
		public void notifyEntryPut(
				PortalCache<Serializable, Object> portalCache, Serializable key,
				Object value, int timeToLive)
			throws PortalCacheException {

			_logDebug("Entry put", key, value);
		}

		@Override
		public void notifyEntryRemoved(
				PortalCache<Serializable, Object> portalCache, Serializable key,
				Object value, int timeToLive)
			throws PortalCacheException {

			_logDebug("Entry removed", key, value);
		}

		@Override
		public void notifyEntryUpdated(
				PortalCache<Serializable, Object> portalCache, Serializable key,
				Object value, int timeToLive)
			throws PortalCacheException {

			_logDebug("Entry updated", key, value);
		}

		@Override
		public void notifyRemoveAll(
				PortalCache<Serializable, Object> portalCache)
			throws PortalCacheException {

			if (_log.isDebugEnabled()) {
				_log.debug("Remove all " + portalCache.getPortalCacheName());
			}
		}

		private V10aOAuthDebugCacheListener() {
		}

		private void _logDebug(String method, Serializable key, Object value) {
			if (!_log.isDebugEnabled() || !(value instanceof OAuthAccessor)) {
				return;
			}

			OAuthAccessor oAuthAccessor = (OAuthAccessor)value;

			_log.debug(
				StringBundler.concat(
					method, "  ", key, ":", oAuthAccessor.getRequestToken(),
					":",
					oAuthAccessor.getProperty(
						OAuthAccessorConstants.AUTHORIZED)));
		}

	}

}