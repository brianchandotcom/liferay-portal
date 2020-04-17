/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.client.dto.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.function.UnsafeSupplier;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.ReindexStatusSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class ReindexStatus implements Cloneable {

	public Long getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(Long completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	public void setCompletionPercentage(
		UnsafeSupplier<Long, Exception> completionPercentageUnsafeSupplier) {

		try {
			completionPercentage = completionPercentageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long completionPercentage;

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

	@Override
	public ReindexStatus clone() throws CloneNotSupportedException {
		return (ReindexStatus)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ReindexStatus)) {
			return false;
		}

		ReindexStatus reindexStatus = (ReindexStatus)object;

		return Objects.equals(toString(), reindexStatus.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ReindexStatusSerDes.toJSON(this);
	}

}