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

package com.liferay.portlet.messageboards.util.test;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 * @author Daniel Kocsis
 */
public class MBTestUtil {

	public static List<ObjectValuePair<String, InputStream>> getInputStreamOVPs(
		String fileName, Class<?> clazz, String keywords) {

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(1);

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		ObjectValuePair<String, InputStream> inputStreamOVP = null;

		if (Validator.isBlank(keywords)) {
			inputStreamOVP = new ObjectValuePair<>(fileName, inputStream);
		}
		else {
			inputStreamOVP = new ObjectValuePair<>(keywords, inputStream);
		}

		inputStreamOVPs.add(inputStreamOVP);

		return inputStreamOVPs;
	}

	public static void populateNotificationsServiceContext(
			ServiceContext serviceContext, String command)
		throws Exception {

		serviceContext.setAttribute("entryURL", "http://localhost");

		if (Validator.isNotNull(command)) {
			serviceContext.setCommand(command);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

}