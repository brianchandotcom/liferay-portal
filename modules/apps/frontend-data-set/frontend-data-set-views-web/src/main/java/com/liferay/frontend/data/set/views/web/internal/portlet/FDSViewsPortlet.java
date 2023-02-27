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

package com.liferay.frontend.data.set.views.web.internal.portlet;

import com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsPortletKeys;
import com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsWebKeys;
import com.liferay.frontend.data.set.views.web.internal.display.context.FDSViewsDisplayContext;
import com.liferay.frontend.data.set.views.web.internal.resource.FDSHeadlessResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Locale;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marko Cikos
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.layout-cacheable=true",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/fds_entries.jsp",
		"javax.portlet.name=" + FDSViewsPortletKeys.FDS_VIEWS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class FDSViewsPortlet extends MVCPortlet {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, null, "(osgi.jaxrs.resource=true)",
			new FDSHeadlessResourceServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			_generate(
				themeDisplay.getCompanyId(), themeDisplay.getLocale(),
				themeDisplay.getUserId());
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		renderRequest.setAttribute(
			FDSViewsWebKeys.FDS_VIEWS_DISPLAY_CONTEXT,
			new FDSViewsDisplayContext(renderRequest, _serviceTrackerList));

		super.doDispatch(renderRequest, renderResponse);
	}

	private synchronized void _generate(
			long companyId, Locale locale, long userId)
		throws Exception {

		ObjectDefinition fdsEntryObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				companyId, "C_FDSEntry");

		if (fdsEntryObjectDefinition != null) {
			return;
		}

		fdsEntryObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				userId, false, false,
				LocalizedMapUtil.getLocalizedMap("FDS Entry"), "FDSEntry",
				"100", null, LocalizedMapUtil.getLocalizedMap("FDS Entries"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "name"), "label", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						"entityClassName", "entityClassName", true)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			userId, fdsEntryObjectDefinition.getObjectDefinitionId());

		ObjectDefinition fdsViewObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				userId, false, false,
				LocalizedMapUtil.getLocalizedMap("FDS View"), "FDSView", "200",
				null, LocalizedMapUtil.getLocalizedMap("FDS Views"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "name"), "label", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "symbol"), "symbol", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "description"), "description",
						false)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			userId, fdsViewObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			userId, fdsEntryObjectDefinition.getObjectDefinitionId(),
			fdsViewObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("FDSEntry FDSView Relationship"),
			"fdsEntryFDSViewRelationship",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSViewsPortlet.class);

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private ServiceTrackerList<FDSHeadlessResource> _serviceTrackerList;

	private class FDSHeadlessResourceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, FDSHeadlessResource> {

		@Override
		public FDSHeadlessResource addingService(
			ServiceReference<Object> serviceReference) {

			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			if (entityClassName != null) {
				String[] entityClassNameParts = StringUtil.split(
					entityClassName, StringPool.PERIOD);

				Object object = _bundleContext.getService(serviceReference);

				return new FDSHeadlessResource(
					_getFDSHeadlessResourceBundleLabel(object), entityClassName,
					entityClassNameParts[entityClassNameParts.length - 1],
					entityClassNameParts[entityClassNameParts.length - 2].
						replaceAll(StringPool.UNDERLINE, StringPool.PERIOD));
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference,
			FDSHeadlessResource fdsHeadlessResource) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference,
			FDSHeadlessResource fdsHeadlessResource) {

			_bundleContext.ungetService(serviceReference);
		}

		private FDSHeadlessResourceServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private String _getFDSHeadlessResourceBundleLabel(Object object) {
			Bundle bundle = FrameworkUtil.getBundle(object.getClass());

			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			String bundleName = GetterUtil.getString(
				headers.get(Constants.BUNDLE_NAME));

			return bundleName.substring(
				0, bundleName.lastIndexOf(StringPool.SPACE));
		}

		private final BundleContext _bundleContext;

	}

}