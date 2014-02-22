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

package com.liferay.portal.util;

import com.liferay.mail.service.MailService;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Jdk14LogFactoryImpl;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.test.mail.MockMailServiceImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;

/**
 * @author Roberto Díaz
 */
public class BaseMailTestCase {

	@Before
	public void setUp() throws Exception {
		logFactory = LogFactoryUtil.getLogFactory();

		LogFactoryUtil.setLogFactory(new Jdk14LogFactoryImpl());

		mailService = MailServiceUtil.getService();

		MailServiceUtil mailServiceUtil = new MailServiceUtil();

		mailServiceUtil.setService(new LoggerMockMailServiceImpl());
	}

	@After
	public void tearDown() throws Exception {
		LogFactoryUtil.setLogFactory(logFactory);

		MailServiceUtil mailServiceUtil = new MailServiceUtil();

		mailServiceUtil.setService(mailService);
	}

	protected LogFactory logFactory;
	protected MailService mailService;

	protected static class LoggerMockMailServiceImpl
		extends MockMailServiceImpl {

		public LoggerMockMailServiceImpl() {
			_logger.setLevel(Level.INFO);
		}

		@Override
		public void sendEmail(MailMessage mailMessage) {
			_logger.info("Sending email");
		}

		private Logger _logger = Logger.getLogger(
			LoggerMockMailServiceImpl.class.getName());

	}

}