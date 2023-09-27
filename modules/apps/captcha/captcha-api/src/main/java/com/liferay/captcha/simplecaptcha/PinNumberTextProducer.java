/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.simplecaptcha;

import com.liferay.portal.kernel.util.PwdGenerator;

import nl.captcha.text.producer.TextProducer;

/**
 * @author Brian Wing Shun Chan
 */
public class PinNumberTextProducer implements TextProducer {

	@Override
	public String getText() {
		return PwdGenerator.getPinNumber();
	}

}