/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.webhook.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Inácio Nery
 * @author Brian Wing Shun Chan
 */
@RequestMapping("/")
@RestController
public class WebhookLoggerRestController {

	@GetMapping("{value}")
	public String getValue(@PathVariable(required = false) String value) {
		return value;
	}

	@PostMapping("{value}")
	public String postValue(@RequestBody String value) {
		try {
			JSONObject jsonObject = new JSONObject(value);

			_log.info("\n\n" + jsonObject.toString(4) + "\n");
		}
		catch (Exception exception) {
			_log.info("Value: " + value);
		}

		return value;
	}

	private static final Log _log = LogFactory.getLog(
		WebhookLoggerRestController.class);

}