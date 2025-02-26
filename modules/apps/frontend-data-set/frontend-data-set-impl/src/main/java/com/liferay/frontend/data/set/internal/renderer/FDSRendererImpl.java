/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.renderer;

import com.liferay.frontend.data.set.renderer.FDSRenderer;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;

import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(service = FDSRenderer.class)
public class FDSRendererImpl implements FDSRenderer {

	@Override
	public void render(
		Map<String, Object> baseProps, String componentId, String fdsName,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean inline,
		String propsTransformer, Writer writer) {

		FDSSerializer fdsSerializer = _getFirstAvailableFDSSerializer(
			fdsName, httpServletRequest);

		if (fdsSerializer == null) {
			_log.error("No FDSSerializer is available for " + fdsName);

			return;
		}

		Map<String, Object> props = new HashMap<>();

		if (baseProps != null) {
			props.putAll(baseProps);
		}

		props.putAll(
			HashMapBuilder.<String, Object>put(
				"apiURL",
				() -> fdsSerializer.serializeAPIURL(fdsName, httpServletRequest)
			).put(
				"bulkActions",
				() -> fdsSerializer.serializeBulkActions(
					fdsName, httpServletRequest)
			).put(
				"creationMenu",
				() -> fdsSerializer.serializeCreationMenu(
					fdsName, httpServletRequest)
			).put(
				"currentURL", _portal.getCurrentURL(httpServletRequest)
			).put(
				"filters",
				() -> fdsSerializer.serializeFilters(
					fdsName, httpServletRequest)
			).put(
				"itemsActions",
				() -> fdsSerializer.serializeItemsActions(
					fdsName, httpServletRequest)
			).put(
				"pagination",
				() -> fdsSerializer.serializePagination(
					fdsName, httpServletRequest)
			).put(
				"sorts",
				() -> fdsSerializer.serializeSorts(fdsName, httpServletRequest)
			).put(
				"views",
				() -> fdsSerializer.serializeViews(fdsName, httpServletRequest)
			).build());

		String finalPropsTransformer = fdsSerializer.serializePropsTransformer(
			fdsName, httpServletRequest);

		if (Validator.isNull(finalPropsTransformer)) {
			finalPropsTransformer = propsTransformer;
		}

		try {
			_reactRenderer.renderReact(
				new ComponentDescriptor(
					"{FrontendDataSet} from frontend-data-set-web", componentId,
					null, inline, finalPropsTransformer),
				props, httpServletRequest, writer);
		}
		catch (Exception exception) {
			_log.error("Unable to render data set " + fdsName, exception);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FDSSerializer.class,
			"frontend.data.set.serializer.type");
	}

	private FDSSerializer _getFirstAvailableFDSSerializer(
		String fdsName, HttpServletRequest httpServletRequest) {

		for (String dataSetType : FDSSerializer.FDS_TYPES) {
			FDSSerializer fdsSerializer = _serviceTrackerMap.getService(
				dataSetType);

			if ((fdsSerializer != null) &&
				fdsSerializer.isAvailable(fdsName, httpServletRequest)) {

				return fdsSerializer;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSRendererImpl.class);

	private BundleContext _bundleContext;

	@Reference
	private Portal _portal;

	@Reference
	private ReactRenderer _reactRenderer;

	private ServiceTrackerMap<String, FDSSerializer> _serviceTrackerMap;

}