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

package com.liferay.portal.workflow.metrics.dto.v1_0;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Process")
public class Process {

	public Long getId() {
		return _id;
	}

	public Integer getInstanceCount() {
		return _instanceCount;
	}

	public Integer getOntimeInstanceCount() {
		return _ontimeInstanceCount;
	}

	public Integer getOverdueInstanceCount() {
		return _overdueInstanceCount;
	}

	public String getTitle() {
		return _title;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setInstanceCount(Integer instanceCount) {
		_instanceCount = instanceCount;
	}

	public void setOntimeInstanceCount(Integer ontimeInstanceCount) {
		_ontimeInstanceCount = ontimeInstanceCount;
	}

	public void setOverdueInstanceCount(Integer overdueInstanceCount) {
		_overdueInstanceCount = overdueInstanceCount;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private Long _id;
	private Integer _instanceCount;
	private Integer _ontimeInstanceCount;
	private Integer _overdueInstanceCount;
	private String _title;

}