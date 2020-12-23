/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.RenderedPageSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RenderedPage implements Cloneable, Serializable {

	public static RenderedPage toDTO(String json) {
		return RenderedPageSerDes.toDTO(json);
	}

	public String getMasterPageId() {
		return masterPageId;
	}

	public void setMasterPageId(String masterPageId) {
		this.masterPageId = masterPageId;
	}

	public void setMasterPageId(
		UnsafeSupplier<String, Exception> masterPageIdUnsafeSupplier) {

		try {
			masterPageId = masterPageIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String masterPageId;

	public String getMasterPageName() {
		return masterPageName;
	}

	public void setMasterPageName(String masterPageName) {
		this.masterPageName = masterPageName;
	}

	public void setMasterPageName(
		UnsafeSupplier<String, Exception> masterPageNameUnsafeSupplier) {

		try {
			masterPageName = masterPageNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String masterPageName;

	public String getPageTemplateId() {
		return pageTemplateId;
	}

	public void setPageTemplateId(String pageTemplateId) {
		this.pageTemplateId = pageTemplateId;
	}

	public void setPageTemplateId(
		UnsafeSupplier<String, Exception> pageTemplateIdUnsafeSupplier) {

		try {
			pageTemplateId = pageTemplateIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String pageTemplateId;

	public String getPageTemplateName() {
		return pageTemplateName;
	}

	public void setPageTemplateName(String pageTemplateName) {
		this.pageTemplateName = pageTemplateName;
	}

	public void setPageTemplateName(
		UnsafeSupplier<String, Exception> pageTemplateNameUnsafeSupplier) {

		try {
			pageTemplateName = pageTemplateNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String pageTemplateName;

	public String getRenderedPageURL() {
		return renderedPageURL;
	}

	public void setRenderedPageURL(String renderedPageURL) {
		this.renderedPageURL = renderedPageURL;
	}

	public void setRenderedPageURL(
		UnsafeSupplier<String, Exception> renderedPageURLUnsafeSupplier) {

		try {
			renderedPageURL = renderedPageURLUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String renderedPageURL;

	public String getViewPortType() {
		return viewPortType;
	}

	public void setViewPortType(String viewPortType) {
		this.viewPortType = viewPortType;
	}

	public void setViewPortType(
		UnsafeSupplier<String, Exception> viewPortTypeUnsafeSupplier) {

		try {
			viewPortType = viewPortTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String viewPortType;

	@Override
	public RenderedPage clone() throws CloneNotSupportedException {
		return (RenderedPage)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RenderedPage)) {
			return false;
		}

		RenderedPage renderedPage = (RenderedPage)object;

		return Objects.equals(toString(), renderedPage.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RenderedPageSerDes.toJSON(this);
	}

}