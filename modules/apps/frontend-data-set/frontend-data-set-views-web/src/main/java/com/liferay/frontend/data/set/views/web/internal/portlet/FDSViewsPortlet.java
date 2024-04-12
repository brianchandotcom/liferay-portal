/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.views.web.internal.portlet;

import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsPortletKeys;
import com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsWebKeys;
import com.liferay.frontend.data.set.views.web.internal.display.context.FDSViewsDisplayContext;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.Bundle;
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

	public static class CompanyScopedOpenAPIResource {

		public CompanyScopedOpenAPIResource(
			long companyId, String openAPIResourcePath) {

			_companyId = companyId;
			_openAPIResourcePath = openAPIResourcePath;
		}

		public long getCompanyId() {
			return _companyId;
		}

		public String getOpenAPIResourcePath() {
			return _openAPIResourcePath;
		}

		public boolean matches(long companyId) {
			if ((_companyId == 0) || (_companyId == companyId)) {
				return true;
			}

			return false;
		}

		private final long _companyId;
		private final String _openAPIResourcePath;

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = BundleUtil.getBundle(
			bundleContext, "com.liferay.frontend.data.set.views.web");
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, null, "(openapi.resource=true)",
			new CompanyScopedRESTApplicationServiceTrackerCustomizer(
				bundleContext));
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
			BatchEngineUnitThreadLocal.setFileName(_bundle.toString());

			_generate(
				themeDisplay.getCompanyId(), themeDisplay.getLocale(),
				themeDisplay.getUserId());
		}
		catch (Exception exception) {
			_log.error(exception);
		}
		finally {
			BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
		}

		renderRequest.setAttribute(
			FDSViewsWebKeys.FDS_VIEWS_DISPLAY_CONTEXT,
			new FDSViewsDisplayContext(
				_cetManager, _objectDefinitionLocalService, renderRequest,
				renderResponse, _serviceTrackerList));

		super.doDispatch(renderRequest, renderResponse);
	}

	private void _addLocalizedCustomObjectField(
			String label, String name, ObjectDefinition objectDefinition,
			long userId)
		throws Exception {

		ObjectField objectField = ObjectFieldUtil.createObjectField(
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, true, false, null, label, name,
			false);

		_objectFieldLocalService.addCustomObjectField(
			objectField.getExternalReferenceCode(), userId,
			objectField.getListTypeDefinitionId(),
			objectDefinition.getObjectDefinitionId(),
			objectField.getBusinessType(), objectField.getDBType(),
			objectField.isIndexed(), objectField.isIndexedAsKeyword(),
			objectField.getIndexedLanguageId(), objectField.getLabelMap(), true,
			objectField.getName(), objectField.getReadOnly(),
			objectField.getReadOnlyConditionExpression(),
			objectField.isRequired(), objectField.isState(),
			objectField.getObjectFieldSettings());
	}

	private void _createFDSActionObjectDefintion(
			ObjectDefinition fdsViewObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition fdsActionObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSAction", userId, 0, "FDSAction", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS Action"), true,
				"FDSAction", null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS Actions"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "type"), "type", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "icon"), "icon", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "confirmation-message-type"),
						"confirmationMessageType", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "method"), "method", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "modal-size"), "modalSize",
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "permission-key"),
						"permissionKey", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "url"), "url", false)));

		_enableLocalization(fdsActionObjectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "confirmation-message"),
			"confirmationMessage", fdsActionObjectDefinition, userId);
		_addLocalizedCustomObjectField(
			_language.get(locale, "error-message"), "errorMessage",
			fdsActionObjectDefinition, userId);
		_addLocalizedCustomObjectField(
			_language.get(locale, "label"), "label", fdsActionObjectDefinition,
			userId);
		_addLocalizedCustomObjectField(
			_language.get(locale, "success-message"), "successMessage",
			fdsActionObjectDefinition, userId);
		_addLocalizedCustomObjectField(
			_language.get(locale, "title"), "title", fdsActionObjectDefinition,
			userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsActionObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsActionObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSCreationAction Relationship"),
			"fdsViewFDSCreationActionRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsActionObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSItemAction Relationship"),
			"fdsViewFDSItemActionRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createFDSCardsSectionObjectDefinition(
			ObjectDefinition fdsViewObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition fdsCardsSectionObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSCardsSection", userId, 0, "FDSCardsSection", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS Cards Section"), true,
				"FDSCardsSection", null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS Cards Sections"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
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
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "renderer-name"), "rendererName",
						false)));

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsCardsSectionObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsCardsSectionObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSCardsSection Relationship"),
			"fdsViewFDSCardsSectionRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createFDSClientExtensionFilterObjectDefintion(
			ObjectDefinition fdsViewObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition fdsClientExtensionFilterObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSClientExtensionFilter", userId, 0,
				"FDSClientExtensionFilter", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS Client Extension Filter"),
				true, "FDSClientExtensionFilter", null, null, null, null,
				LocalizedMapUtil.getLocalizedMap(
					"FDS Client Extension Filters"),
				false, ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "field-name"), "fieldName", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(
							locale, "fds-filter-client-extension-erc"),
						"fdsFilterClientExtensionERC", true)));

		_enableLocalization(fdsClientExtensionFilterObjectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "label"), "label",
			fdsClientExtensionFilterObjectDefinition, userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId,
			fdsClientExtensionFilterObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsClientExtensionFilterObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSClientExtensionFilter"),
			"fdsViewFDSClientExtensionFilter", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createFDSDateFilterObjectDefinition(
			ObjectDefinition fdsViewObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition fdsDateFilterObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSDateFilter", userId, 0, "FDSDateFilter", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS Date Filter"), true,
				"FDSDateFilter", null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS Date Filters"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
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
						_language.get(locale, "type"), "type", false)));

		_enableLocalization(fdsDateFilterObjectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "label"), "label",
			fdsDateFilterObjectDefinition, userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsDateFilterObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsDateFilterObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSDateFilter Relationship"),
			"fdsViewFDSDateFilterRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createFDSDynamicFilterObjectDefintion(
			ObjectDefinition fdsViewObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition fdsDynamicFilterObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSDynamicFilter", userId, 0, "FDSDynamicFilter", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS Dynamic Filter"), true,
				"FDSDynamicFilter", null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS Dynamic Filters"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "field-name"), "fieldName", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
						ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
						_language.get(locale, "include"), "include", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "list-type-definition-erc"),
						"listTypeDefinitionERC", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
						ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
						_language.get(locale, "multiple"), "multiple", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "preselected-values"),
						"preselectedValues", false)));

		_enableLocalization(fdsDynamicFilterObjectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "label"), "label",
			fdsDynamicFilterObjectDefinition, userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsDynamicFilterObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsDynamicFilterObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSDynamicFilter Relationship"),
			"fdsViewFDSDynamicFilterRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private ObjectDefinition _createFDSEntryObjectDefinition(
			Locale locale, long userId)
		throws Exception {

		ObjectDefinition fdsEntryObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSEntry", userId, 0, "FDSEntry", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS Entry"), true, "FDSEntry",
				null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS Entries"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
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

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsEntryObjectDefinition.getObjectDefinitionId());

		return fdsEntryObjectDefinition;
	}

	private void _createFDSFieldObjectDefinition(
			ObjectDefinition fdsViewObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition fdsFieldObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSField", userId, 0, "FDSField", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS Field"), true, "FDSField",
				null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS Fields"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
				Arrays.asList(
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
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "rendererType"), "rendererType",
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
						ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
						_language.get(locale, "sortable"), "sortable", false)));

		_enableLocalization(fdsFieldObjectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "column-label"), "label",
			fdsFieldObjectDefinition, userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsFieldObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsFieldObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("FDSView FDSField Relationship"),
			"fdsViewFDSFieldRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createFDSListSectionObjectDefinition(
			ObjectDefinition fdsViewObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition fdsListSectionObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSListSection", userId, 0, "FDSListSection", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS List Section"), true,
				"FDSListSection", null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS List Sections"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
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
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "renderer-name"), "rendererName",
						false)));

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsListSectionObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsListSectionObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"FDSView FDSListSection Relationship"),
			"fdsViewFDSListSectionRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createFDSSortObjectDefinition(
			ObjectDefinition fdsViewObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		List<ObjectField> objectFields = Arrays.asList(
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
				_language.get(locale, "field-name"), "fieldName", true),
			ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
				_language.get(locale, "sorting"), "sortingDirection", true));

		if (FeatureFlagManagerUtil.isEnabled("LPD-19465")) {
			objectFields = Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
					ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
					_language.get(locale, "default"), "default", false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					_language.get(locale, "field-name"), "fieldName", true),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					_language.get(locale, "order-type"), "orderType", true));
		}

		ObjectDefinition fdsSortObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSSort", userId, 0, "FDSSort", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS Sort"), true, "FDSSort",
				"300", null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS Sorts"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT, objectFields);

		if (FeatureFlagManagerUtil.isEnabled("LPD-19465")) {
			_enableLocalization(fdsSortObjectDefinition);

			_addLocalizedCustomObjectField(
				_language.get(locale, "label"), "label",
				fdsSortObjectDefinition, userId);
		}

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsSortObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsViewObjectDefinition.getObjectDefinitionId(),
			fdsSortObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("FDSView FDSSort Relationship"),
			"fdsViewFDSSortRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private ObjectDefinition _createFDSViewObjectDefinition(
			ObjectDefinition fdsEntryObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition fdsViewObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSView", userId, 0, "FDSView", null, false,
				LocalizedMapUtil.getLocalizedMap("FDS View"), true, "FDSView",
				null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("FDS Views"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT,
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
						_language.get(locale, "creation-actions-order"),
						"fdsCreationActionsOrder", false),
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
						_language.get(locale, "item-actions-order"),
						"fdsItemActionsOrder", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "sorts-order"), "fdsSortsOrder",
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "default-visualization-mode"),
						"defaultVisualizationMode", false)));

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, fdsViewObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsEntryObjectDefinition.getObjectDefinitionId(),
			fdsViewObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("FDSEntry FDSView Relationship"),
			"fdsEntryFDSViewRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);

		return fdsViewObjectDefinition;
	}

	private void _enableLocalization(ObjectDefinition objectDefinition) {
		objectDefinition.setEnableLocalization(true);

		_objectDefinitionLocalService.updateObjectDefinition(objectDefinition);
	}

	private synchronized void _generate(
			long companyId, Locale locale, long userId)
		throws Exception {

		ObjectDefinition fdsEntryObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				companyId, "FDSEntry");

		if (fdsEntryObjectDefinition != null) {
			return;
		}

		fdsEntryObjectDefinition = _createFDSEntryObjectDefinition(
			locale, userId);

		ObjectDefinition fdsViewObjectDefinition =
			_createFDSViewObjectDefinition(
				fdsEntryObjectDefinition, locale, userId);

		_createFDSActionObjectDefintion(
			fdsViewObjectDefinition, locale, userId);
		_createFDSCardsSectionObjectDefinition(
			fdsViewObjectDefinition, locale, userId);
		_createFDSClientExtensionFilterObjectDefintion(
			fdsViewObjectDefinition, locale, userId);
		_createFDSDateFilterObjectDefinition(
			fdsViewObjectDefinition, locale, userId);
		_createFDSDynamicFilterObjectDefintion(
			fdsViewObjectDefinition, locale, userId);
		_createFDSFieldObjectDefinition(
			fdsViewObjectDefinition, locale, userId);
		_createFDSListSectionObjectDefinition(
			fdsViewObjectDefinition, locale, userId);
		_createFDSSortObjectDefinition(fdsViewObjectDefinition, locale, userId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSViewsPortlet.class);

	private Bundle _bundle;

	@Reference
	private CETManager _cetManager;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private ServiceTrackerList<CompanyScopedOpenAPIResource>
		_serviceTrackerList;

	private class CompanyScopedRESTApplicationServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<Object, CompanyScopedOpenAPIResource> {

		@Override
		public CompanyScopedOpenAPIResource addingService(
			ServiceReference<Object> serviceReference) {

			String openAPIResourcePath = (String)serviceReference.getProperty(
				"openapi.resource.path");

			if (openAPIResourcePath == null) {
				return null;
			}

			String apiVersion = (String)serviceReference.getProperty(
				"api.version");

			if (apiVersion != null) {
				openAPIResourcePath = openAPIResourcePath + "/" + apiVersion;
			}

			long companyId = GetterUtil.getLong(
				(String)serviceReference.getProperty("companyId"));

			return new CompanyScopedOpenAPIResource(
				companyId, openAPIResourcePath);
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference,
			CompanyScopedOpenAPIResource companyScopedOpenAPIResource) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference,
			CompanyScopedOpenAPIResource companyScopedOpenAPIResource) {

			_bundleContext.ungetService(serviceReference);
		}

		private CompanyScopedRESTApplicationServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}