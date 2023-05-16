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
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Locale;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
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
			bundleContext, null, "(openapi.resource=true)",
			new RESTApplicationServiceTrackerCustomizer(bundleContext));
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
						_language.get(locale, "rest-application"),
						"restApplication", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "rest-endpoint"), "restEndpoint",
						true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "rest-schema"), "restSchema",
						true)));

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
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "list-of-items-per-page"),
						"listOfItemsPerPage", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
						ObjectFieldConstants.DB_TYPE_INTEGER, true, false, null,
						_language.get(locale, "default-items-per-page"),
						"defaultItemsPerPage", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "fields-order"), "fdsFieldsOrder",
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "filters-order"),
						"fdsFiltersOrder", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "sorts-order"), "fdsSortsOrder",
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

		ObjectDefinition fdsFieldObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				userId, false, false,
				LocalizedMapUtil.getLocalizedMap("FDS Field"), "FDSField",
				"300", null, LocalizedMapUtil.getLocalizedMap("FDS Fields"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "column-label"), "label", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "name"), "name", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "type"), "type", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "renderer"), "renderer", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
						ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
						_language.get(locale, "sortable"), "sortable", true)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			userId, fdsFieldObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsFieldObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("FDSView FDSField Relationship"),
			"fdsViewFDSFieldRelationship",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectDefinition fdsDateFilterObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				userId, false, false,
				LocalizedMapUtil.getLocalizedMap("FDS Date Filter"),
				"FDSDateFilter", "300", null,
				LocalizedMapUtil.getLocalizedMap("FDS Date Filters"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_DATE,
						ObjectFieldConstants.DB_TYPE_DATE, true, false, null,
						_language.get(locale, "to"), "to", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_DATE,
						ObjectFieldConstants.DB_TYPE_DATE, true, false, null,
						_language.get(locale, "from"), "from", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "field-name"), "fieldName", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "name"), "name", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "type"), "type", false)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			userId, fdsDateFilterObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsDateFilterObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSDateFilter Relationship"),
			"fdsViewFDSDateFilterRelationship",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectDefinition fdsDynamicFilterObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				userId, false, false,
				LocalizedMapUtil.getLocalizedMap("FDS Dynamic Filter"),
				"FDSDynamicFilter", "300", null,
				LocalizedMapUtil.getLocalizedMap("FDS Dynamic Filters"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "field-name"), "fieldName", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "name"), "name", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
						ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
						_language.get(locale, "include"), "include", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "list-type-definition-id"),
						"listTypeDefinitionId", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
						ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
						_language.get(locale, "multiple"), "multiple", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "preselected-values"),
						"preselectedValues", false)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			userId, fdsDynamicFilterObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsDynamicFilterObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSDynamicFilter Relationship"),
			"fdsViewFDSDynamicFilterRelationship",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectDefinition fdsSortObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				userId, false, false,
				LocalizedMapUtil.getLocalizedMap("FDS Sort"), "FDSSort", "300",
				null, LocalizedMapUtil.getLocalizedMap("FDS Sorts"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "field-name"), "fieldName", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "sorting"), "sortingDirection",
						true)));

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			userId, fdsSortObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsSortObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("FDSView FDSSort Relationship"),
			"fdsViewFDSSortRelationship",
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

	private ServiceTrackerList<String> _serviceTrackerList;

	private class RESTApplicationServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, String> {

		@Override
		public String addingService(ServiceReference<Object> serviceReference) {
			String openapiResourcePath = (String)serviceReference.getProperty(
				"openapi.resource.path");

			if (openapiResourcePath == null) {
				return null;
			}

			String apiVersion = (String)serviceReference.getProperty(
				"api.version");

			if (apiVersion != null) {
				return openapiResourcePath + "/" + apiVersion;
			}

			return openapiResourcePath;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, String restApplication) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, String restApplication) {

			_bundleContext.ungetService(serviceReference);
		}

		private RESTApplicationServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}