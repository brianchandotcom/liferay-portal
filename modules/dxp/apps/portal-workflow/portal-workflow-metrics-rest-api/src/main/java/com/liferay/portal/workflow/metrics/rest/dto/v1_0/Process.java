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

package com.liferay.portal.workflow.metrics.rest.dto.v1_0;

import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public interface Process {

	public Long getId();

	public void setId(
			Long id);

	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier);
	public Integer getInstanceCount();

	public void setInstanceCount(
			Integer instanceCount);

	public void setInstanceCount(
			UnsafeSupplier<Integer, Throwable>
				instanceCountUnsafeSupplier);
	public Integer getOntimeInstanceCount();

	public void setOntimeInstanceCount(
			Integer ontimeInstanceCount);

	public void setOntimeInstanceCount(
			UnsafeSupplier<Integer, Throwable>
				ontimeInstanceCountUnsafeSupplier);
	public Integer getOverdueInstanceCount();

	public void setOverdueInstanceCount(
			Integer overdueInstanceCount);

	public void setOverdueInstanceCount(
			UnsafeSupplier<Integer, Throwable>
				overdueInstanceCountUnsafeSupplier);
	public String getTitle();

	public void setTitle(
			String title);

	public void setTitle(
			UnsafeSupplier<String, Throwable>
				titleUnsafeSupplier);

}