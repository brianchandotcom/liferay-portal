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

package com.liferay.captcha.simplecaptcha;

import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.backgrounds.BackgroundProducer;
import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.noise.NoiseProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.TextProducer;
import nl.captcha.text.renderer.WordRenderer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Sanz
 */
@Component(
	immediate = true,
	property = {
		"captcha.engine.impl=com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl"
	},
	service = Captcha.class
)
public class SimpleCaptchaImpl implements Captcha {

	@Override
	public void check(HttpServletRequest request) throws CaptchaException {
		if (!isEnabled(request)) {
			return;
		}

		if (!validateChallenge(request)) {
			throw new CaptchaTextException();
		}
		else {
			incrementCounter(request);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("CAPTCHA text is valid");
		}
	}

	@Override
	public void check(PortletRequest portletRequest) throws CaptchaException {
		if (!isEnabled(portletRequest)) {
			return;
		}

		if (!validateChallenge(portletRequest)) {
			throw new CaptchaTextException();
		}
		else {
			incrementCounter(portletRequest);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("CAPTCHA text is valid");
		}
	}

	@Override
	public String getTaglibPath() {
		return _TAGLIB_PATH;
	}

	@Override
	public boolean isEnabled(HttpServletRequest request) {
		if (isExceededMaxChallenges(request)) {
			return false;
		}

		if (PropsValues.CAPTCHA_MAX_CHALLENGES >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isEnabled(PortletRequest portletRequest) {
		if (isExceededMaxChallenges(portletRequest)) {
			return false;
		}

		if (PropsValues.CAPTCHA_MAX_CHALLENGES >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		HttpSession session = request.getSession();

		nl.captcha.Captcha simpleCaptcha = getSimpleCaptcha();

		session.setAttribute(WebKeys.CAPTCHA_TEXT, simpleCaptcha.getAnswer());

		response.setContentType(ContentTypes.IMAGE_PNG);

		CaptchaServletUtil.writeImage(
			response.getOutputStream(), simpleCaptcha.getImage());
	}

	@Override
	public void serveImage(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		PortletSession portletSession = resourceRequest.getPortletSession();

		nl.captcha.Captcha simpleCaptcha = getSimpleCaptcha();

		portletSession.setAttribute(
			WebKeys.CAPTCHA_TEXT, simpleCaptcha.getAnswer());

		resourceResponse.setContentType(ContentTypes.IMAGE_PNG);

		CaptchaServletUtil.writeImage(
			resourceResponse.getPortletOutputStream(),
			simpleCaptcha.getImage());
	}

	@Activate
	protected void activate() {
		initBackgroundProducers();
		initGimpyRenderers();
		initNoiseProducers();
		initTextProducers();
		initWordRenderers();
	}

	protected BackgroundProducer getBackgroundProducer() {
		if (_backgroundProducers.length == 1) {
			return _backgroundProducers[0];
		}

		int pos = RandomUtil.nextInt(_backgroundProducers.length);

		return _backgroundProducers[pos];
	}

	protected GimpyRenderer getGimpyRenderer() {
		if (_gimpyRenderers.length == 1) {
			return _gimpyRenderers[0];
		}

		int pos = RandomUtil.nextInt(_gimpyRenderers.length);

		return _gimpyRenderers[pos];
	}

	protected int getHeight() {
		return PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT;
	}

	protected NoiseProducer getNoiseProducer() {
		if (_noiseProducers.length == 1) {
			return _noiseProducers[0];
		}

		int pos = RandomUtil.nextInt(_noiseProducers.length);

		return _noiseProducers[pos];
	}

	protected nl.captcha.Captcha getSimpleCaptcha() {
		nl.captcha.Captcha.Builder captchaBuilder =
			new nl.captcha.Captcha.Builder(getWidth(), getHeight());

		captchaBuilder.addText(getTextProducer(), getWordRenderer());
		captchaBuilder.addBackground(getBackgroundProducer());
		captchaBuilder.gimp(getGimpyRenderer());
		captchaBuilder.addNoise(getNoiseProducer());
		captchaBuilder.addBorder();

		return captchaBuilder.build();
	}

	protected TextProducer getTextProducer() {
		if (_textProducers.length == 1) {
			return _textProducers[0];
		}

		int pos = RandomUtil.nextInt(_textProducers.length);

		return _textProducers[pos];
	}

	protected int getWidth() {
		return PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH;
	}

	protected WordRenderer getWordRenderer() {
		if (_wordRenderers.length == 1) {
			return _wordRenderers[0];
		}

		int pos = RandomUtil.nextInt(_wordRenderers.length);

		return _wordRenderers[pos];
	}

	protected void incrementCounter(HttpServletRequest request) {
		if ((PropsValues.CAPTCHA_MAX_CHALLENGES > 0) &&
			Validator.isNotNull(request.getRemoteUser())) {

			HttpSession session = request.getSession();

			Integer count = (Integer)session.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			session.setAttribute(
				WebKeys.CAPTCHA_COUNT, incrementCounter(count));
		}
	}

	protected Integer incrementCounter(Integer count) {
		if (count == null) {
			count = Integer.valueOf(1);
		}
		else {
			count = Integer.valueOf(count.intValue() + 1);
		}

		return count;
	}

	protected void incrementCounter(PortletRequest portletRequest) {
		if ((PropsValues.CAPTCHA_MAX_CHALLENGES > 0) &&
			Validator.isNotNull(portletRequest.getRemoteUser())) {

			PortletSession portletSession = portletRequest.getPortletSession();

			Integer count = (Integer)portletSession.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			portletSession.setAttribute(
				WebKeys.CAPTCHA_COUNT, incrementCounter(count));
		}
	}

	protected void initBackgroundProducers() {
		String[] backgroundProducerClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS;

		_backgroundProducers = new BackgroundProducer[
			backgroundProducerClassNames.length];

		for (int i = 0; i < backgroundProducerClassNames.length; i++) {
			String backgroundProducerClassName =
				backgroundProducerClassNames[i];

			_backgroundProducers[i] = (BackgroundProducer)_getInstance(
				backgroundProducerClassName);
		}
	}

	protected void initGimpyRenderers() {
		String[] gimpyRendererClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS;

		_gimpyRenderers = new GimpyRenderer[gimpyRendererClassNames.length];

		for (int i = 0; i < gimpyRendererClassNames.length; i++) {
			String gimpyRendererClassName = gimpyRendererClassNames[i];

			_gimpyRenderers[i] = (GimpyRenderer)_getInstance(
				gimpyRendererClassName);
		}
	}

	protected void initNoiseProducers() {
		String[] noiseProducerClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS;

		_noiseProducers = new NoiseProducer[noiseProducerClassNames.length];

		for (int i = 0; i < noiseProducerClassNames.length; i++) {
			String noiseProducerClassName = noiseProducerClassNames[i];

			_noiseProducers[i] = (NoiseProducer)_getInstance(
				noiseProducerClassName);
		}
	}

	protected void initTextProducers() {
		String[] textProducerClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS;

		_textProducers = new TextProducer[textProducerClassNames.length];

		for (int i = 0; i < textProducerClassNames.length; i++) {
			String textProducerClassName = textProducerClassNames[i];

			_textProducers[i] = (TextProducer)_getInstance(
				textProducerClassName);
		}
	}

	protected void initWordRenderers() {
		String[] wordRendererClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS;

		_wordRenderers = new WordRenderer[wordRendererClassNames.length];

		for (int i = 0; i < wordRendererClassNames.length; i++) {
			String wordRendererClassName = wordRendererClassNames[i];

			_wordRenderers[i] = (WordRenderer)_getInstance(
				wordRendererClassName);
		}
	}

	protected boolean isExceededMaxChallenges(HttpServletRequest request) {
		if (PropsValues.CAPTCHA_MAX_CHALLENGES > 0) {
			HttpSession session = request.getSession();

			Integer count = (Integer)session.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			return isExceededMaxChallenges(count);
		}

		return false;
	}

	protected boolean isExceededMaxChallenges(Integer count) {
		if ((count != null) && (count >= PropsValues.CAPTCHA_MAX_CHALLENGES)) {
			return true;
		}

		return false;
	}

	protected boolean isExceededMaxChallenges(PortletRequest portletRequest) {
		if (PropsValues.CAPTCHA_MAX_CHALLENGES > 0) {
			PortletSession portletSession = portletRequest.getPortletSession();

			Integer count = (Integer)portletSession.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			return isExceededMaxChallenges(count);
		}

		return false;
	}

	protected boolean validateChallenge(HttpServletRequest request)
		throws CaptchaException {

		HttpSession session = request.getSession();

		String captchaText = (String)session.getAttribute(WebKeys.CAPTCHA_TEXT);

		if (request instanceof UploadPortletRequest) {
			UploadPortletRequest uploadPortletRequest =
				(UploadPortletRequest)request;

			PortletRequest portletRequest =
				uploadPortletRequest.getPortletRequest();

			PortletSession portletSession = portletRequest.getPortletSession();

			captchaText = (String)portletSession.getAttribute(
				WebKeys.CAPTCHA_TEXT);
		}

		if (captchaText == null) {
			_log.error(
				"CAPTCHA text is null. User " + request.getRemoteUser() +
					" may be trying to circumvent the CAPTCHA.");

			throw new CaptchaTextException();
		}

		boolean valid = captchaText.equals(
			ParamUtil.getString(request, "captchaText"));

		if (valid) {
			if (request instanceof UploadPortletRequest) {
				UploadPortletRequest uploadPortletRequest =
					(UploadPortletRequest)request;

				PortletRequest portletRequest =
					uploadPortletRequest.getPortletRequest();

				PortletSession portletSession =
					portletRequest.getPortletSession();

				portletSession.removeAttribute(WebKeys.CAPTCHA_TEXT);
			}
			else {
				session.removeAttribute(WebKeys.CAPTCHA_TEXT);
			}
		}

		return valid;
	}

	protected boolean validateChallenge(PortletRequest portletRequest)
		throws CaptchaException {

		PortletSession portletSession = portletRequest.getPortletSession();

		String captchaText = (String)portletSession.getAttribute(
			WebKeys.CAPTCHA_TEXT);

		if (captchaText == null) {
			_log.error(
				"CAPTCHA text is null. User " + portletRequest.getRemoteUser() +
					" may be trying to circumvent the CAPTCHA.");

			throw new CaptchaTextException();
		}

		boolean valid = captchaText.equals(
			ParamUtil.getString(portletRequest, "captchaText"));

		if (valid) {
			portletSession.removeAttribute(WebKeys.CAPTCHA_TEXT);
		}

		return valid;
	}

	private Object _getInstance(String className) {
		className = className.trim();

		Object instance = _instances.get(className);

		if (Validator.isNotNull(instance)) {
			return instance;
		}

		try {
			Class<?> clazz = _loadClass(className);

			instance = clazz.newInstance();

			_instances.put(className, instance);
		}
		catch (Exception e) {
			_log.error("Unable to load " + className, e);
		}

		return instance;
	}

	private Class<?> _loadClass(String className) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.loadClass(className);
	}

	private static final String _TAGLIB_PATH = "/captcha/simplecaptcha.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		SimpleCaptchaImpl.class);

	private BackgroundProducer[] _backgroundProducers;
	private GimpyRenderer[] _gimpyRenderers;
	private final Map<String, Object> _instances = new ConcurrentHashMap<>();
	private NoiseProducer[] _noiseProducers;
	private TextProducer[] _textProducers;
	private WordRenderer[] _wordRenderers;

}