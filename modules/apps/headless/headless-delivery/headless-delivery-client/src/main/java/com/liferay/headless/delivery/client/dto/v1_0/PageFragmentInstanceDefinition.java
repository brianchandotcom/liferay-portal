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
import com.liferay.headless.delivery.client.serdes.v1_0.PageFragmentInstanceDefinitionSerDes;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageFragmentInstanceDefinition implements Cloneable {

	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}

	public void setFragment(
		UnsafeSupplier<Fragment, Exception> fragmentUnsafeSupplier) {

		try {
			fragment = fragmentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Fragment fragment;

	public Map<String, Object> getFragmentConfig() {
		return fragmentConfig;
	}

	public void setFragmentConfig(Map<String, Object> fragmentConfig) {
		this.fragmentConfig = fragmentConfig;
	}

	public void setFragmentConfig(
		UnsafeSupplier<Map<String, Object>, Exception>
			fragmentConfigUnsafeSupplier) {

		try {
			fragmentConfig = fragmentConfigUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> fragmentConfig;

	public FragmentField[] getFragmentFields() {
		return fragmentFields;
	}

	public void setFragmentFields(FragmentField[] fragmentFields) {
		this.fragmentFields = fragmentFields;
	}

	public void setFragmentFields(
		UnsafeSupplier<FragmentField[], Exception>
			fragmentFieldsUnsafeSupplier) {

		try {
			fragmentFields = fragmentFieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentField[] fragmentFields;

	public WidgetInstance[] getWidgetInstances() {
		return widgetInstances;
	}

	public void setWidgetInstances(WidgetInstance[] widgetInstances) {
		this.widgetInstances = widgetInstances;
	}

	public void setWidgetInstances(
		UnsafeSupplier<WidgetInstance[], Exception>
			widgetInstancesUnsafeSupplier) {

		try {
			widgetInstances = widgetInstancesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected WidgetInstance[] widgetInstances;

	@Override
	public PageFragmentInstanceDefinition clone()
		throws CloneNotSupportedException {

		return (PageFragmentInstanceDefinition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageFragmentInstanceDefinition)) {
			return false;
		}

		PageFragmentInstanceDefinition pageFragmentInstanceDefinition =
			(PageFragmentInstanceDefinition)object;

		return Objects.equals(
			toString(), pageFragmentInstanceDefinition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PageFragmentInstanceDefinitionSerDes.toJSON(this);
	}

}