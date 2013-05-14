/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.captcha.simplecaptcha;

import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaMaxChallengesException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Randomizer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

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

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Sanz
 */
public class SimpleCaptchaImpl implements Captcha {

	public SimpleCaptchaImpl() {
		initBackgroundProducers();
		initGimpyRenderers();
		initNoiseProducers();
		initTextProducers();
		initWordRenderers();
	}

	@Override
	public void check(HttpServletRequest request) throws CaptchaException {
		if (!isEnabled(request)) {
			return;
		}

		if (!validateChallenge(request)) {
			incrementCounter(request);

			checkMaxChallenges(request);

			throw new CaptchaTextException();
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
			incrementCounter(portletRequest);

			checkMaxChallenges(portletRequest);

			throw new CaptchaTextException();
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
	public boolean isEnabled(HttpServletRequest request)
		throws CaptchaException {

		checkMaxChallenges(request);

		if (PropsValues.CAPTCHA_MAX_CHALLENGES >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isEnabled(PortletRequest portletRequest)
		throws CaptchaException {

		checkMaxChallenges(portletRequest);

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

		response.setContentType(ContentTypes.IMAGE_JPEG);

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

	protected void checkMaxChallenges(HttpServletRequest request)
		throws CaptchaMaxChallengesException {

		if (PropsValues.CAPTCHA_MAX_CHALLENGES > 0) {
			HttpSession session = request.getSession();

			Integer count = (Integer)session.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			checkMaxChallenges(count);
		}
	}

	protected void checkMaxChallenges(Integer count)
		throws CaptchaMaxChallengesException {

		if ((count != null) && (count > PropsValues.CAPTCHA_MAX_CHALLENGES)) {
			throw new CaptchaMaxChallengesException();
		}
	}

	protected void checkMaxChallenges(PortletRequest portletRequest)
		throws CaptchaMaxChallengesException {

		if (PropsValues.CAPTCHA_MAX_CHALLENGES > 0) {
			PortletSession portletSession = portletRequest.getPortletSession();

			Integer count = (Integer)portletSession.getAttribute(
				WebKeys.CAPTCHA_COUNT);

			checkMaxChallenges(count);
		}
	}

	protected BackgroundProducer getBackgroundProducer() {
		if (_backgroundProducers.length == 1) {
			return _backgroundProducers[0];
		}

		Randomizer randomizer = Randomizer.getInstance();

		int pos = randomizer.nextInt(_backgroundProducers.length);

		return _backgroundProducers[pos];
	}

	protected GimpyRenderer getGimpyRenderer() {
		if (_gimpyRenderers.length == 1) {
			return _gimpyRenderers[0];
		}

		Randomizer randomizer = Randomizer.getInstance();

		int pos = randomizer.nextInt(_gimpyRenderers.length);

		return _gimpyRenderers[pos];
	}

	protected int getHeight() {
		return PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT;
	}

	protected NoiseProducer getNoiseProducer() {
		if (_noiseProducers.length == 1) {
			return _noiseProducers[0];
		}

		Randomizer randomizer = Randomizer.getInstance();

		int pos = randomizer.nextInt(_noiseProducers.length);

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

		Randomizer randomizer = Randomizer.getInstance();

		int pos = randomizer.nextInt(_textProducers.length);

		return _textProducers[pos];
	}

	protected int getWidth() {
		return PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH;
	}

	protected WordRenderer getWordRenderer() {
		if (_wordRenderers.length == 1) {
			return _wordRenderers[0];
		}

		Randomizer randomizer = Randomizer.getInstance();

		int pos = randomizer.nextInt(_wordRenderers.length);

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
			count = new Integer(1);
		}
		else {
			count = new Integer(count.intValue() + 1);
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

			_backgroundProducers[i] = (BackgroundProducer)InstancePool.get(
				backgroundProducerClassName);
		}
	}

	protected void initGimpyRenderers() {
		String[] gimpyRendererClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS;

		_gimpyRenderers = new GimpyRenderer[
			gimpyRendererClassNames.length];

		for (int i = 0; i < gimpyRendererClassNames.length; i++) {
			String gimpyRendererClassName = gimpyRendererClassNames[i];

			_gimpyRenderers[i] = (GimpyRenderer)InstancePool.get(
				gimpyRendererClassName);
		}
	}

	protected void initNoiseProducers() {
		String[] noiseProducerClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS;

		_noiseProducers = new NoiseProducer[noiseProducerClassNames.length];

		for (int i = 0; i < noiseProducerClassNames.length; i++) {
			String noiseProducerClassName = noiseProducerClassNames[i];

			_noiseProducers[i] = (NoiseProducer)InstancePool.get(
				noiseProducerClassName);
		}
	}

	protected void initTextProducers() {
		String[] textProducerClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS;

		_textProducers = new TextProducer[textProducerClassNames.length];

		for (int i = 0; i < textProducerClassNames.length; i++) {
			String textProducerClassName = textProducerClassNames[i];

			_textProducers[i] = (TextProducer)InstancePool.get(
				textProducerClassName);
		}
	}

	protected void initWordRenderers() {
		String[] wordRendererClassNames =
			PropsValues.CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS;

		_wordRenderers = new WordRenderer[wordRendererClassNames.length];

		for (int i = 0; i < wordRendererClassNames.length; i++) {
			String wordRendererClassName = wordRendererClassNames[i];

			_wordRenderers[i] = (WordRenderer)InstancePool.get(
				wordRendererClassName);
		}
	}

	protected boolean validateChallenge(HttpServletRequest request)
		throws CaptchaException {

		HttpSession session = request.getSession();

		String captchaText = (String)session.getAttribute(WebKeys.CAPTCHA_TEXT);

		if (captchaText == null) {
			_log.error(
				"CAPTCHA text is null. User " + request.getRemoteUser() +
					" may be trying to circumvent the CAPTCHA.");

			throw new CaptchaTextException();
		}

		boolean valid = captchaText.equals(
			ParamUtil.getString(request, "captchaText"));

		if (valid) {
			session.removeAttribute(WebKeys.CAPTCHA_TEXT);
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

	private static final String _TAGLIB_PATH =
		"/html/taglib/ui/captcha/simplecaptcha.jsp";

	private static Log _log = LogFactoryUtil.getLog(SimpleCaptchaImpl.class);

	private BackgroundProducer[] _backgroundProducers;
	private GimpyRenderer[] _gimpyRenderers;
	private NoiseProducer[] _noiseProducers;
	private TextProducer[] _textProducers;
	private WordRenderer[] _wordRenderers;

}