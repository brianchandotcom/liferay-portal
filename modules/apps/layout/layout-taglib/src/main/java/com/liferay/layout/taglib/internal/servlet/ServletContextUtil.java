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

import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.helper.FragmentEntryLinkHelper;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.list.renderer.InfoListRendererRegistry;
import com.liferay.layout.adaptive.media.LayoutAdaptiveMediaProcessor;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.list.retriever.LayoutListRetrieverRegistry;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryRegistry;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.util.LayoutClassedModelUsageRecorder;
import com.liferay.layout.util.LayoutsTree;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.segments.service.SegmentsExperienceLocalService;

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
@Component(service = {})
public class ServletContextUtil {

	public static CollectionPaginationHelper getCollectionPaginationHelper() {
		return _collectionPaginationHelper;
	}

	public static FragmentEntryConfigurationParser
		getFragmentEntryConfigurationParser() {

		return _fragmentEntryConfigurationParser;
	}

	public static FragmentEntryLinkHelper getFragmentEntryLinkHelper() {
		return _fragmentEntryLinkHelper;
	}

	public static FragmentEntryProcessorHelper
		getFragmentEntryProcessorHelper() {

		return _fragmentEntryProcessorHelper;
	}

	public static FragmentRendererController getFragmentRendererController() {
		return _fragmentRendererController;
	}

	public static FrontendTokenDefinitionRegistry
		getFrontendTokenDefinitionRegistry() {

		return _frontendTokenDefinitionRegistry;
	}

	public static InfoItemServiceRegistry getInfoItemServiceRegistry() {
		return _infoItemServiceRegistry;
	}

	public static InfoListRendererRegistry getInfoListRendererRegistry() {
		return _infoListRendererRegistry;
	}

	public static LayoutAdaptiveMediaProcessor
		getLayoutAdaptiveMediaProcessor() {

		return _layoutAdaptiveMediaProcessor;
	}

	public static Map<String, LayoutClassedModelUsageRecorder>
		getLayoutClassedModelUsageRecorders() {

		return _layoutClassedModelUsageRecorders;
	}

	public static LayoutDisplayPageProviderRegistry
		getLayoutDisplayPageProviderRegistry() {

		return _layoutDisplayPageProviderRegistry;
	}

	public static LayoutListRetrieverRegistry getLayoutListRetrieverRegistry() {
		return _layoutListRetrieverRegistry;
	}

	public static LayoutsTree getLayoutsTree() {
		return _layoutsTree;
	}

	public static LayoutStructureProvider getLayoutStructureHelper() {
		return _layoutStructureProvider;
	}

	public static ListObjectReferenceFactoryRegistry
		getListObjectReferenceFactoryRegistry() {

		return _listObjectReferenceFactoryRegistry;
	}

	public static RequestContextMapper getRequestContextMapper() {
		return _requestContextMapper;
	}

	public static SegmentsEntryRetriever getSegmentsEntryRetriever() {
		return _segmentsEntryRetriever;
	}

	public static SegmentsExperienceLocalService
		getSegmentsExperienceLocalService() {

		return _segmentsExperienceLocalService;
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
	protected void setFragmentEntryConfigurationParser(
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser) {

		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
	}

	@Reference(unbind = "-")
	protected void setFragmentEntryLinkHelper(
		FragmentEntryLinkHelper fragmentEntryLinkHelper) {

		_fragmentEntryLinkHelper = fragmentEntryLinkHelper;
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
	protected void setFrontendTokenDefinitionRegistry(
		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry) {

		_frontendTokenDefinitionRegistry = frontendTokenDefinitionRegistry;
	}

	@Reference(unbind = "-")
	protected void setInfoItemServiceRegistry(
		InfoItemServiceRegistry infoItemServiceRegistry) {

		_infoItemServiceRegistry = infoItemServiceRegistry;
	}

	@Reference(unbind = "-")
	protected void setInfoListRendererRegistry(
		InfoListRendererRegistry infoListRendererRegistry) {

		_infoListRendererRegistry = infoListRendererRegistry;
	}

	@Reference(unbind = "-")
	protected void setLayoutAdaptiveMediaProcessor(
		LayoutAdaptiveMediaProcessor layoutAdaptiveMediaProcessor) {

		_layoutAdaptiveMediaProcessor = layoutAdaptiveMediaProcessor;
	}

	@Reference(unbind = "-")
	protected void setLayoutDisplayPageProviderRegistry(
		LayoutDisplayPageProviderRegistry layoutDisplayPageProviderRegistry) {

		_layoutDisplayPageProviderRegistry = layoutDisplayPageProviderRegistry;
	}

	@Reference(unbind = "-")
	protected void setLayoutListRetrieverRegistry(
		LayoutListRetrieverRegistry layoutListRetrieverRegistry) {

		_layoutListRetrieverRegistry = layoutListRetrieverRegistry;
	}

	@Reference(unbind = "-")
	protected void setLayoutsTree(LayoutsTree layoutsTree) {
		_layoutsTree = layoutsTree;
	}

	@Reference(unbind = "-")
	protected void setLayoutStructureHelper(
		LayoutStructureProvider layoutStructureProvider) {

		_layoutStructureProvider = layoutStructureProvider;
	}

	@Reference(unbind = "-")
	protected void setListObjectReferenceFactoryRegistry(
		ListObjectReferenceFactoryRegistry listObjectReferenceFactoryRegistry) {

		_listObjectReferenceFactoryRegistry =
			listObjectReferenceFactoryRegistry;
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

	@Reference(unbind = "-")
	protected void setSegmentsExperienceLocalService(
		SegmentsExperienceLocalService segmentsExperienceLocalService) {

		_segmentsExperienceLocalService = segmentsExperienceLocalService;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.layout.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static CollectionPaginationHelper _collectionPaginationHelper;
	private static FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private static FragmentEntryLinkHelper _fragmentEntryLinkHelper;
	private static FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;
	private static FragmentRendererController _fragmentRendererController;
	private static FrontendTokenDefinitionRegistry
		_frontendTokenDefinitionRegistry;
	private static InfoItemServiceRegistry _infoItemServiceRegistry;
	private static InfoListRendererRegistry _infoListRendererRegistry;
	private static LayoutAdaptiveMediaProcessor _layoutAdaptiveMediaProcessor;
	private static final Map<String, LayoutClassedModelUsageRecorder>
		_layoutClassedModelUsageRecorders = new ConcurrentHashMap<>();
	private static LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;
	private static LayoutListRetrieverRegistry _layoutListRetrieverRegistry;
	private static LayoutsTree _layoutsTree;
	private static LayoutStructureProvider _layoutStructureProvider;
	private static ListObjectReferenceFactoryRegistry
		_listObjectReferenceFactoryRegistry;
	private static RequestContextMapper _requestContextMapper;
	private static SegmentsEntryRetriever _segmentsEntryRetriever;
	private static SegmentsExperienceLocalService
		_segmentsExperienceLocalService;
	private static ServletContext _servletContext;

}