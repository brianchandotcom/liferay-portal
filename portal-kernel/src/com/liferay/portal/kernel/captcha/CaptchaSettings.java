/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.captcha;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Pei-Jung Lan
 */
@ProviderType
public interface CaptchaSettings {

	public String getCaptchaEngine();

	public int getMaxChallenges();

	public String getReCaptchaNoScriptURL();

	public String getReCaptchaPrivateKey();

	public String getReCaptchaPublicKey();

	public String getReCaptchaScriptURL();

	public String getReCaptchaVerifyURL();

	public String[] getSimpleCaptchaBackgroundProducers();

	public String[] getSimpleCaptchaGimpyRenderers();

	public int getSimpleCaptchaHeight();

	public String[] getSimpleCaptchaNoiseProducers();

	public String[] getSimpleCaptchaTextProducers();

	public int getSimpleCaptchaWidth();

	public String[] getSimpleCaptchaWordRenderers();

	public boolean isCreateAccountCaptchaEnabled();

	public boolean isMessageBoardsEditCategoryCaptchaEnabled();

	public boolean isMessageBoardsEditMessageCaptchaEnabled();

	public boolean isSendPasswordCaptchaEnabled();

	public void setCaptchaEngine(String className) throws Exception;

}