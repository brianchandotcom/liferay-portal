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
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.layout.adaptive.media.LayoutAdaptiveMediaProcessor;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.layout.util.LayoutClassedModelUsageRecorder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;

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

	public static CollectionPaginationHelper getCollectionPaginationHelper() {
		return _collectionPaginationHelper;
	}

	public static String getContextPath() {
		return _servletContext.getContextPath();
	}

	public static FragmentCollectionContributorTracker
		getFragmentCollectionContributorTracker() {

		return _fragmentCollectionContributorTracker;
	}

	public static FragmentEntryConfigurationParser
		getFragmentEntryConfigurationParser() {

		return _fragmentEntryConfigurationParser;
	}

	public static FragmentEntryProcessorHelper
		getFragmentEntryProcessorHelper() {

		return _fragmentEntryProcessorHelper;
	}

	public static FragmentRendererController getFragmentRendererController() {
		return _fragmentRendererController;
	}

	public static FragmentRendererTracker getFragmentRendererTracker() {
		return _fragmentRendererTracker;
	}

	public static FrontendTokenDefinitionRegistry
		getFrontendTokenDefinitionRegistry() {

		return _frontendTokenDefinitionRegistry;
	}

	public static InfoItemServiceTracker getInfoItemServiceTracker() {
		return _infoItemServiceTracker;
	}

	public static InfoListRendererTracker getInfoListRendererTracker() {
		return _infoListRendererTracker;
	}

	public static LayoutAdaptiveMediaProcessor
		getLayoutAdaptiveMediaProcessor() {

		return _layoutAdaptiveMediaProcessor;
	}

	public static Map<String, LayoutClassedModelUsageRecorder>
		getLayoutClassedModelUsageRecorders() {

		return _layoutClassedModelUsageRecorders;
	}

	public static LayoutDisplayPageProviderTracker
		getLayoutDisplayPageProviderTracker() {

		return _layoutDisplayPageProviderTracker;
	}

	public static LayoutListRetrieverTracker getLayoutListRetrieverTracker() {
		return _layoutListRetrieverTracker;
	}

	public static ListObjectReferenceFactoryTracker
		getListObjectReferenceFactoryTracker() {

		return _listObjectReferenceFactoryTracker;
	}

	public static RequestContextMapper getRequestContextMapper() {
		return _requestContextMapper;
	}

	public static SegmentsEntryRetriever getSegmentsEntryRetriever() {
		return _segmentsEntryRetriever;
	}

	public static ServletContext getServletContext() {
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
	protected void setCollectionPaginationHelper(
		CollectionPaginationHelper collectionPaginationHelper) {

		_collectionPaginationHelper = collectionPaginationHelper;
	}

	@Reference(unbind = "-")
	protected void setFragmentCollectionContributorTracker(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker) {

		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
	}

	@Reference(unbind = "-")
	protected void setFragmentEntryConfigurationParser(
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser) {

		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
	}

	@Reference(unbind = "-")
	protected void setFragmentEntryProcessorHelper(
		FragmentEntryProcessorHelper fragmentEntryProcessorHelper) {

		_fragmentEntryProcessorHelper = fragmentEntryProcessorHelper;
	}

	@Reference(unbind = "-")
	protected void setFragmentRendererController(
		FragmentRendererController fragmentRendererController) {

		_fragmentRendererController = fragmentRendererController;
	}

	@Reference(unbind = "-")
	protected void setFragmentRendererTracker(
		FragmentRendererTracker fragmentRendererTracker) {

		_fragmentRendererTracker = fragmentRendererTracker;
	}

	@Reference(unbind = "-")
	protected void setFrontendTokenDefinitionRegistry(
		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry) {

		_frontendTokenDefinitionRegistry = frontendTokenDefinitionRegistry;
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
	protected void setLayoutAdaptiveMediaProcessor(
		LayoutAdaptiveMediaProcessor layoutAdaptiveMediaProcessor) {

		_layoutAdaptiveMediaProcessor = layoutAdaptiveMediaProcessor;
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

	@Reference(unbind = "-")
	protected void setRequestContextMapper(
		RequestContextMapper requestContextMapper) {

		_requestContextMapper = requestContextMapper;
	}

	@Reference(unbind = "-")
	protected void setSegmentsEntryRetriever(
		SegmentsEntryRetriever segmentsEntryRetriever) {

		_segmentsEntryRetriever = segmentsEntryRetriever;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static CollectionPaginationHelper _collectionPaginationHelper;
	private static FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private static FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private static FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;
	private static FragmentRendererController _fragmentRendererController;
	private static FragmentRendererTracker _fragmentRendererTracker;
	private static FrontendTokenDefinitionRegistry
		_frontendTokenDefinitionRegistry;
	private static InfoItemServiceTracker _infoItemServiceTracker;
	private static InfoListRendererTracker _infoListRendererTracker;
	private static LayoutAdaptiveMediaProcessor _layoutAdaptiveMediaProcessor;
	private static final Map<String, LayoutClassedModelUsageRecorder>
		_layoutClassedModelUsageRecorders = new ConcurrentHashMap<>();
	private static LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;
	private static LayoutListRetrieverTracker _layoutListRetrieverTracker;
	private static ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;
	private static RequestContextMapper _requestContextMapper;
	private static SegmentsEntryRetriever _segmentsEntryRetriever;
	private static ServletContext _servletContext;

}