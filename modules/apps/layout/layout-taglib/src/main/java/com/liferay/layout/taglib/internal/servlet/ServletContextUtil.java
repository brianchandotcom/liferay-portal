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

package com.liferay.layout.taglib.internal.servlet;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.layout.util.LayoutClassedModelUsageRecorder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Chema Balsas
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

	public static final String getContextPath() {
		return _servletContext.getContextPath();
	}

	public static final FragmentCollectionContributorTracker
		getFragmentCollectionContributorTracker() {

		return _fragmentCollectionContributorTracker;
	}

	public static final FragmentRendererTracker getFragmentRendererTracker() {
		return _fragmentRendererTracker;
	}

	public static final InfoItemServiceTracker getInfoItemServiceTracker() {
		return _infoItemServiceTracker;
	}

	public static final InfoListRendererTracker getInfoListRendererTracker() {
		return _infoListRendererTracker;
	}

	public static final Map<String, LayoutClassedModelUsageRecorder>
		getLayoutClassedModelUsageRecorders() {

		return _layoutClassedModelUsageRecorders;
	}

	public static final LayoutDisplayPageProviderTracker
		getLayoutDisplayPageProviderTracker() {

		return _layoutDisplayPageProviderTracker;
	}

	public static final LayoutListRetrieverTracker
		getLayoutListRetrieverTracker() {

		return _layoutListRetrieverTracker;
	}

	public static final ListObjectReferenceFactoryTracker
		getListObjectReferenceFactoryTracker() {

		return _listObjectReferenceFactoryTracker;
	}

	public static final ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addLayoutClassedModelUsageRecorder(
		LayoutClassedModelUsageRecorder layoutClassedModelUsageRecorder,
		Map<String, Object> properties) {

		String modelClassName = GetterUtil.getString(
			properties.get("model.class.name"));

		if (Validator.isNull(modelClassName)) {
			return;
		}

		_layoutClassedModelUsageRecorders.put(
			modelClassName, layoutClassedModelUsageRecorder);
	}

	protected void removeLayoutClassedModelUsageRecorder(
		LayoutClassedModelUsageRecorder layoutClassedModelUsageRecorder,
		Map<String, Object> properties) {

		String modelClassName = GetterUtil.getString(
			properties.get("model.class.name"));

		if (Validator.isNull(modelClassName)) {
			return;
		}

		_layoutClassedModelUsageRecorders.remove(modelClassName);
	}

	@Reference(unbind = "-")
	protected void setFragmentCollectionContributorTracker(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker) {

		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
	}

	@Reference(unbind = "-")
	protected void setFragmentRendererTracker(
		FragmentRendererTracker fragmentRendererTracker) {

		_fragmentRendererTracker = fragmentRendererTracker;
	}

	@Reference(unbind = "-")
	protected void setInfoItemServiceTracker(
		InfoItemServiceTracker infoItemServiceTracker) {

		_infoItemServiceTracker = infoItemServiceTracker;
	}

	@Reference(unbind = "-")
	protected void setInfoListRendererTracker(
		InfoListRendererTracker infoListRendererTracker) {

		_infoListRendererTracker = infoListRendererTracker;
	}

	@Reference(unbind = "-")
	protected void setLayoutDisplayPageProviderTracker(
		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker) {

		_layoutDisplayPageProviderTracker = layoutDisplayPageProviderTracker;
	}

	@Reference(unbind = "-")
	protected void setLayoutListRetrieverTracker(
		LayoutListRetrieverTracker layoutListRetrieverTracker) {

		_layoutListRetrieverTracker = layoutListRetrieverTracker;
	}

	@Reference(unbind = "-")
	protected void setListObjectReferenceFactoryTracker(
		ListObjectReferenceFactoryTracker listObjectReferenceFactoryTracker) {

		_listObjectReferenceFactoryTracker = listObjectReferenceFactoryTracker;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private static FragmentRendererTracker _fragmentRendererTracker;
	private static InfoItemServiceTracker _infoItemServiceTracker;
	private static InfoListRendererTracker _infoListRendererTracker;
	private static final Map<String, LayoutClassedModelUsageRecorder>
		_layoutClassedModelUsageRecorders = new ConcurrentHashMap<>();
	private static LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;
	private static LayoutListRetrieverTracker _layoutListRetrieverTracker;
	private static ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;
	private static ServletContext _servletContext;

}