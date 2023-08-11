/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.client.dto.v1_0;

import com.liferay.captcha.rest.client.function.UnsafeSupplier;
import com.liferay.captcha.rest.client.serdes.v1_0.CaptchaFormSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Loc Pham
 * @generated
 */
@Generated("")
public class CaptchaForm implements Cloneable, Serializable {

	public static CaptchaForm toDTO(String json) {
		return CaptchaFormSerDes.toDTO(json);
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setAnswer(
		UnsafeSupplier<String, Exception> answerUnsafeSupplier) {

		try {
			answer = answerUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String answer;

	public String getCaptchaToken() {
		return captchaToken;
	}

	public void setCaptchaToken(String captchaToken) {
		this.captchaToken = captchaToken;
	}

	public void setCaptchaToken(
		UnsafeSupplier<String, Exception> captchaTokenUnsafeSupplier) {

		try {
			captchaToken = captchaTokenUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String captchaToken;

	@Override
	public CaptchaForm clone() throws CloneNotSupportedException {
		return (CaptchaForm)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CaptchaForm)) {
			return false;
		}

		CaptchaForm captchaForm = (CaptchaForm)object;

		return Objects.equals(toString(), captchaForm.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CaptchaFormSerDes.toJSON(this);
	}

}