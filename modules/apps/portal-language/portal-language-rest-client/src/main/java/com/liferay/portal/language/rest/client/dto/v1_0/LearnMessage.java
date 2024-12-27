/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.rest.client.dto.v1_0;

import com.liferay.portal.language.rest.client.function.UnsafeSupplier;
import com.liferay.portal.language.rest.client.serdes.v1_0.LearnMessageSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Thiago Buarque
 * @generated
 */
@Generated("")
public class LearnMessage implements Cloneable, Serializable {

	public static LearnMessage toDTO(String json) {
		return LearnMessageSerDes.toDTO(json);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<String, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String key;

	public LearnMessageDetail[] getLearnMessageDetails() {
		return learnMessageDetails;
	}

	public void setLearnMessageDetails(
		LearnMessageDetail[] learnMessageDetails) {

		this.learnMessageDetails = learnMessageDetails;
	}

	public void setLearnMessageDetails(
		UnsafeSupplier<LearnMessageDetail[], Exception>
			learnMessageDetailsUnsafeSupplier) {

		try {
			learnMessageDetails = learnMessageDetailsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected LearnMessageDetail[] learnMessageDetails;

	@Override
	public LearnMessage clone() throws CloneNotSupportedException {
		return (LearnMessage)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LearnMessage)) {
			return false;
		}

		LearnMessage learnMessage = (LearnMessage)object;

		return Objects.equals(toString(), learnMessage.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LearnMessageSerDes.toJSON(this);
	}

}