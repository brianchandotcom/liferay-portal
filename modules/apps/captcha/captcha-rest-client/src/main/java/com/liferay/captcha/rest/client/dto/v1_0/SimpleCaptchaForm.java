/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.client.dto.v1_0;

import com.liferay.captcha.rest.client.function.UnsafeSupplier;
import com.liferay.captcha.rest.client.serdes.v1_0.SimpleCaptchaFormSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Loc Pham
 * @generated
 */
@Generated("")
public class SimpleCaptchaForm implements Cloneable, Serializable {

	public static SimpleCaptchaForm toDTO(String json) {
		return SimpleCaptchaFormSerDes.toDTO(json);
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setToken(
		UnsafeSupplier<String, Exception> tokenUnsafeSupplier) {

		try {
			token = tokenUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String token;

	@Override
	public SimpleCaptchaForm clone() throws CloneNotSupportedException {
		return (SimpleCaptchaForm)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SimpleCaptchaForm)) {
			return false;
		}

		SimpleCaptchaForm simpleCaptchaForm = (SimpleCaptchaForm)object;

		return Objects.equals(toString(), simpleCaptchaForm.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SimpleCaptchaFormSerDes.toJSON(this);
	}

}