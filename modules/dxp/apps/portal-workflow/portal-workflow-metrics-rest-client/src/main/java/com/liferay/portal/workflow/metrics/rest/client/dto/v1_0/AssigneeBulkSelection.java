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
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.AssigneeBulkSelectionSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class AssigneeBulkSelection implements Cloneable {

	public Long[] getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(Long[] instanceIds) {
		this.instanceIds = instanceIds;
	}

	public void setInstanceIds(
		UnsafeSupplier<Long[], Exception> instanceIdsUnsafeSupplier) {

		try {
			instanceIds = instanceIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long[] instanceIds;

	@Override
	public AssigneeBulkSelection clone() throws CloneNotSupportedException {
		return (AssigneeBulkSelection)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AssigneeBulkSelection)) {
			return false;
		}

		AssigneeBulkSelection assigneeBulkSelection =
			(AssigneeBulkSelection)object;

		return Objects.equals(toString(), assigneeBulkSelection.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AssigneeBulkSelectionSerDes.toJSON(this);
	}

}