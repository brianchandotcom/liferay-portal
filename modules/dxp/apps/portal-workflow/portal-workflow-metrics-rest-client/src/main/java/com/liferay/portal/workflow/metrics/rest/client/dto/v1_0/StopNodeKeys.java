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
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.StopNodeKeysSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class StopNodeKeys implements Cloneable {

	public NodeKey[] getNodeKeys() {
		return nodeKeys;
	}

	public void setNodeKeys(NodeKey[] nodeKeys) {
		this.nodeKeys = nodeKeys;
	}

	public void setNodeKeys(
		UnsafeSupplier<NodeKey[], Exception> nodeKeysUnsafeSupplier) {

		try {
			nodeKeys = nodeKeysUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected NodeKey[] nodeKeys;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Integer, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer status;

	@Override
	public StopNodeKeys clone() throws CloneNotSupportedException {
		return (StopNodeKeys)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StopNodeKeys)) {
			return false;
		}

		StopNodeKeys stopNodeKeys = (StopNodeKeys)object;

		return Objects.equals(toString(), stopNodeKeys.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return StopNodeKeysSerDes.toJSON(this);
	}

}