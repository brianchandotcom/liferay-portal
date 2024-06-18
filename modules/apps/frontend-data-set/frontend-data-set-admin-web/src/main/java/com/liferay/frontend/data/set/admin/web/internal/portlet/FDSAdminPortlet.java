/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.portlet;

import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminPortletKeys;
import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminWebKeys;
import com.liferay.frontend.data.set.admin.web.internal.display.context.FDSAdminDisplayContext;
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
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
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
		"javax.portlet.init-param.view-template=/data_sets.jsp",
		"javax.portlet.name=" + FDSAdminPortletKeys.FDS_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class FDSAdminPortlet extends MVCPortlet {

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
			bundleContext, "com.liferay.frontend.data.set.admin.web");
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
			FDSAdminWebKeys.FDS_ADMIN_DISPLAY_CONTEXT,
			new FDSAdminDisplayContext(
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

	private void _createActionObjectDefinition(
			Locale locale, ObjectDefinition relatedObjectDefinition,
			long userId)
		throws Exception {

		String erc = "FDSAction";
		String className = "FDSAction";
		String name = "FDSAction";
		String creationActionRelationshipName =
			"fdsViewFDSCreationActionRelationship";
		String itemActionRelationshipName =
			"fdsViewFDSCreationActionRelationship";

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			erc = "L_DATA_SET_ACTION";
			className = "DataSetAction";
			name = "DataSetAction";
			creationActionRelationshipName = "dataSetCreationActions";
			itemActionRelationshipName = "dataSetItemActions";
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				erc, userId, 0, className, null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set Action"), true, name,
				null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Set Actions"), false,
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

		_enableLocalization(objectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "confirmation-message"),
			"confirmationMessage", objectDefinition, userId);
		_addLocalizedCustomObjectField(
			_language.get(locale, "error-message"), "errorMessage",
			objectDefinition, userId);
		_addLocalizedCustomObjectField(
			_language.get(locale, "label"), "label", objectDefinition, userId);
		_addLocalizedCustomObjectField(
			_language.get(locale, "success-message"), "successMessage",
			objectDefinition, userId);
		_addLocalizedCustomObjectField(
			_language.get(locale, "title"), "title", objectDefinition, userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set Creation Actions"),
			creationActionRelationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set Item Actions"),
			itemActionRelationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createCardsSectionObjectDefinition(
			Locale locale, ObjectDefinition relatedObjectDefinition,
			long userId)
		throws Exception {

		String erc = "FDSCardsSection";
		String className = "FDSCardsSection";
		String name = "FDSCardsSection";
		String relationshipName = "fdsViewFDSCardsSectionRelationship";

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			erc = "L_DATA_SET_CARDS_SECTION";
			className = "DataSetCardsSection";
			name = "DataSetCardsSection";
			relationshipName = "dataSetCardsSections";
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				erc, userId, 0, className, null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set Cards Section"),
				true, name, null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Set Cards Sections"),
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
						_language.get(locale, "name"), "name", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "renderer-name"), "rendererName",
						false)));

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set Cards Sections"),
			relationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createClientExtensionFilterObjectDefintion(
			Locale locale, ObjectDefinition relatedObjectDefinition,
			long userId)
		throws Exception {

		String erc = "FDSClientExtensionFilter";
		String className = "FDSClientExtensionFilter";
		String name = "FDSClientExtensionFilter";
		String relationshipName = "fdsViewFDSClientExtensionFilter";

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			erc = "L_DATA_SET_CLIENT_EXTENSION_FILTER";
			className = "DataSetClientExtensionFilter";
			name = "DataSetClientExtensionFilter";
			relationshipName = "dataSetClientExtensionFilters";
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				erc, userId, 0, className, null, false, true,
				LocalizedMapUtil.getLocalizedMap(
					"Data Set Client Extension Filter"),
				true, name, null, null, null, null,
				LocalizedMapUtil.getLocalizedMap(
					"Data Set Client Extension Filters"),
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
							locale, "client-extension-external-reference-code"),
						"clientExtensionERC", true)));

		_enableLocalization(objectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "label"), "label", objectDefinition, userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(
				"Data Set Client Extension Filters"),
			relationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private ObjectDefinition _createDataSetObjectDefinition(
			Locale locale, long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"L_DATA_SET", userId, 0, "DataSet", null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set"), true, "DataSet",
				null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Sets"), false,
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
						_language.get(locale, "description"), "description",
						false),
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
						true),
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
						"creationActionsOrder", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "table-sections-order"),
						"tableSectionsOrder", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "filters-order"), "filtersOrder",
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "item-actions-order"),
						"itemActionsOrder", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "sorts-order"), "sortsOrder",
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "default-visualization-mode"),
						"defaultVisualizationMode", false)));

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		return objectDefinition;
	}

	private void _createDateFilterObjectDefinition(
			Locale locale, ObjectDefinition relatedObjectDefinition,
			long userId)
		throws Exception {

		String erc = "FDSDateFilter";
		String className = "FDSDateFilter";
		String name = "FDSDateFilter";
		String relationshipName = "fdsViewFDSDateFilterRelationship";

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			erc = "L_DATA_SET_DATE_FILTER";
			className = "DataSetDateFilter";
			name = "DataSetDateFilter";
			relationshipName = "dataSetDateFilters";
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				erc, userId, 0, className, null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set Date Filter"), true,
				name, null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Set Date Filters"),
				false, ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
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

		_enableLocalization(objectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "label"), "label", objectDefinition, userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set Date Filters"),
			relationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private ObjectDefinition _createFDSEntryObjectDefinition(
			Locale locale, long userId)
		throws Exception {

		ObjectDefinition fdsEntryObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSEntry", userId, 0, "FDSEntry", null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set"), true, "FDSEntry",
				null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Sets"), false,
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

	private ObjectDefinition _createFDSViewObjectDefinition(
			ObjectDefinition fdsEntryObjectDefinition, Locale locale,
			long userId)
		throws Exception {

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				"FDSView", userId, 0, "FDSView", null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set View"), true,
				"FDSView", null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Set Views"), false,
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
			userId, relatedObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, fdsEntryObjectDefinition.getObjectDefinitionId(),
			relatedObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set Views"),
			"fdsEntryFDSViewRelationship", false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);

		return relatedObjectDefinition;
	}

	private void _createListSectionObjectDefinition(
			Locale locale, ObjectDefinition relatedObjectDefinition,
			long userId)
		throws Exception {

		String erc = "FDSListSection";
		String className = "FDSListSection";
		String name = "FDSListSection";
		String relationshipName = "fdsViewFDSListSectionRelationship";

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			erc = "L_DATA_SET_LIST_SECTION";
			className = "DataSetListSection";
			name = "DataSetListSection";
			relationshipName = "dataSetListSections";
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				erc, userId, 0, className, null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set List Section"), true,
				name, null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Set List Sections"),
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
						_language.get(locale, "name"), "name", true),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						_language.get(locale, "renderer-name"), "rendererName",
						false)));

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set List Sections"),
			relationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createSelectionFilterObjectDefintion(
			Locale locale, ObjectDefinition relatedObjectDefinition,
			long userId)
		throws Exception {

		String erc = "FDSDynamicFilter";
		String className = "FDSDynamicFilter";
		String name = "FDSDynamicFilter";
		String relationshipName = "fdsViewFDSDynamicFilterRelationship";

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			erc = "L_DATA_SET_SELECTION_FILTER";
			className = "DataSetSelectionFilter";
			name = "DataSetSelectionFilter";
			relationshipName = "dataSetSelectionFilters";
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				erc, userId, 0, className, null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set Selection Filter"),
				true, name, null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Set Selection Filters"),
				false, ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
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
						ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
						ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
						_language.get(locale, "multiple"), "multiple", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_CLOB, true, false, null,
						_language.get(locale, "preselected-values"),
						"preselectedValues", false)));

		if (FeatureFlagManagerUtil.isEnabled("LPD-10754")) {
			ObjectField itemKeyObjectField = ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
				_language.get(locale, "item-key"), "itemKey", false);

			_objectFieldLocalService.addCustomObjectField(
				itemKeyObjectField.getExternalReferenceCode(), userId,
				itemKeyObjectField.getListTypeDefinitionId(),
				objectDefinition.getObjectDefinitionId(),
				itemKeyObjectField.getBusinessType(),
				itemKeyObjectField.getDBType(), itemKeyObjectField.isIndexed(),
				itemKeyObjectField.isIndexedAsKeyword(),
				itemKeyObjectField.getIndexedLanguageId(),
				itemKeyObjectField.getLabelMap(), false,
				itemKeyObjectField.getName(), itemKeyObjectField.getReadOnly(),
				itemKeyObjectField.getReadOnlyConditionExpression(),
				itemKeyObjectField.isRequired(), itemKeyObjectField.isState(),
				itemKeyObjectField.getObjectFieldSettings());

			ObjectField itemLabelObjectField =
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					_language.get(locale, "item-label"), "itemLabel", false);

			_objectFieldLocalService.addCustomObjectField(
				itemLabelObjectField.getExternalReferenceCode(), userId,
				itemLabelObjectField.getListTypeDefinitionId(),
				objectDefinition.getObjectDefinitionId(),
				itemLabelObjectField.getBusinessType(),
				itemLabelObjectField.getDBType(),
				itemLabelObjectField.isIndexed(),
				itemLabelObjectField.isIndexedAsKeyword(),
				itemLabelObjectField.getIndexedLanguageId(),
				itemLabelObjectField.getLabelMap(), false,
				itemLabelObjectField.getName(),
				itemLabelObjectField.getReadOnly(),
				itemLabelObjectField.getReadOnlyConditionExpression(),
				itemLabelObjectField.isRequired(),
				itemLabelObjectField.isState(),
				itemLabelObjectField.getObjectFieldSettings());

			ObjectField restApplicationObjectField =
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					_language.get(locale, "rest-application"),
					"restApplication", false);

			_objectFieldLocalService.addCustomObjectField(
				restApplicationObjectField.getExternalReferenceCode(), userId,
				restApplicationObjectField.getListTypeDefinitionId(),
				objectDefinition.getObjectDefinitionId(),
				restApplicationObjectField.getBusinessType(),
				restApplicationObjectField.getDBType(),
				restApplicationObjectField.isIndexed(),
				restApplicationObjectField.isIndexedAsKeyword(),
				restApplicationObjectField.getIndexedLanguageId(),
				restApplicationObjectField.getLabelMap(), false,
				restApplicationObjectField.getName(),
				restApplicationObjectField.getReadOnly(),
				restApplicationObjectField.getReadOnlyConditionExpression(),
				restApplicationObjectField.isRequired(),
				restApplicationObjectField.isState(),
				restApplicationObjectField.getObjectFieldSettings());

			ObjectField restEndpointObjectField =
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					_language.get(locale, "rest-endpoint"), "restEndpoint",
					false);

			_objectFieldLocalService.addCustomObjectField(
				restEndpointObjectField.getExternalReferenceCode(), userId,
				restEndpointObjectField.getListTypeDefinitionId(),
				objectDefinition.getObjectDefinitionId(),
				restEndpointObjectField.getBusinessType(),
				restEndpointObjectField.getDBType(),
				restEndpointObjectField.isIndexed(),
				restEndpointObjectField.isIndexedAsKeyword(),
				restEndpointObjectField.getIndexedLanguageId(),
				restEndpointObjectField.getLabelMap(), false,
				restEndpointObjectField.getName(),
				restEndpointObjectField.getReadOnly(),
				restEndpointObjectField.getReadOnlyConditionExpression(),
				restEndpointObjectField.isRequired(),
				restEndpointObjectField.isState(),
				restEndpointObjectField.getObjectFieldSettings());

			ObjectField restSchemaObjectField =
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					_language.get(locale, "rest-schema"), "restSchema", false);

			_objectFieldLocalService.addCustomObjectField(
				restSchemaObjectField.getExternalReferenceCode(), userId,
				restSchemaObjectField.getListTypeDefinitionId(),
				objectDefinition.getObjectDefinitionId(),
				restSchemaObjectField.getBusinessType(),
				restSchemaObjectField.getDBType(),
				restSchemaObjectField.isIndexed(),
				restSchemaObjectField.isIndexedAsKeyword(),
				restSchemaObjectField.getIndexedLanguageId(),
				restSchemaObjectField.getLabelMap(), false,
				restSchemaObjectField.getName(),
				restSchemaObjectField.getReadOnly(),
				restSchemaObjectField.getReadOnlyConditionExpression(),
				restSchemaObjectField.isRequired(),
				restSchemaObjectField.isState(),
				restSchemaObjectField.getObjectFieldSettings());

			ObjectField sourceObjectField = ObjectFieldUtil.createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
				_language.get(locale, "source"), "source", false);

			_objectFieldLocalService.addCustomObjectField(
				sourceObjectField.getExternalReferenceCode(), userId,
				sourceObjectField.getListTypeDefinitionId(),
				objectDefinition.getObjectDefinitionId(),
				sourceObjectField.getBusinessType(),
				sourceObjectField.getDBType(), sourceObjectField.isIndexed(),
				sourceObjectField.isIndexedAsKeyword(),
				sourceObjectField.getIndexedLanguageId(),
				sourceObjectField.getLabelMap(), false,
				sourceObjectField.getName(), sourceObjectField.getReadOnly(),
				sourceObjectField.getReadOnlyConditionExpression(),
				sourceObjectField.isRequired(), sourceObjectField.isState(),
				sourceObjectField.getObjectFieldSettings());

			ObjectField sourceTypeObjectField =
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					_language.get(locale, "source-type"), "sourceType", false);

			_objectFieldLocalService.addCustomObjectField(
				sourceTypeObjectField.getExternalReferenceCode(), userId,
				sourceTypeObjectField.getListTypeDefinitionId(),
				objectDefinition.getObjectDefinitionId(),
				sourceTypeObjectField.getBusinessType(),
				sourceTypeObjectField.getDBType(),
				sourceTypeObjectField.isIndexed(),
				sourceTypeObjectField.isIndexedAsKeyword(),
				sourceTypeObjectField.getIndexedLanguageId(),
				sourceTypeObjectField.getLabelMap(), false,
				sourceTypeObjectField.getName(),
				sourceTypeObjectField.getReadOnly(),
				sourceTypeObjectField.getReadOnlyConditionExpression(),
				sourceTypeObjectField.isRequired(),
				sourceTypeObjectField.isState(),
				sourceTypeObjectField.getObjectFieldSettings());
		}
		else {
			ObjectField listTypeDefinitionERCObjectField =
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					_language.get(locale, "list-type-definition-erc"),
					"listTypeDefinitionERC", false);

			_objectFieldLocalService.addCustomObjectField(
				listTypeDefinitionERCObjectField.getExternalReferenceCode(),
				userId,
				listTypeDefinitionERCObjectField.getListTypeDefinitionId(),
				objectDefinition.getObjectDefinitionId(),
				listTypeDefinitionERCObjectField.getBusinessType(),
				listTypeDefinitionERCObjectField.getDBType(),
				listTypeDefinitionERCObjectField.isIndexed(),
				listTypeDefinitionERCObjectField.isIndexedAsKeyword(),
				listTypeDefinitionERCObjectField.getIndexedLanguageId(),
				listTypeDefinitionERCObjectField.getLabelMap(), false,
				listTypeDefinitionERCObjectField.getName(),
				listTypeDefinitionERCObjectField.getReadOnly(),
				listTypeDefinitionERCObjectField.
					getReadOnlyConditionExpression(),
				listTypeDefinitionERCObjectField.isRequired(),
				listTypeDefinitionERCObjectField.isState(),
				listTypeDefinitionERCObjectField.getObjectFieldSettings());
		}

		_enableLocalization(objectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "label"), "label", objectDefinition, userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set Selection Filters"),
			relationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createSortObjectDefinition(
			Locale locale, ObjectDefinition relatedObjectDefinition,
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

		String erc = "FDSSort";
		String className = "FDSSort";
		String name = "FDSSort";
		String relationshipName = "fdsViewFDSSortRelationship";

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			erc = "L_DATA_SET_SORT";
			className = "DataSetSort";
			name = "DataSetSort";
			relationshipName = "dataSetSorts";
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				erc, userId, 0, className, null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set Sort"), true, name,
				"300", null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Set Sorts"), false,
				ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
				WorkflowConstants.STATUS_DRAFT, objectFields);

		if (FeatureFlagManagerUtil.isEnabled("LPD-19465")) {
			_enableLocalization(objectDefinition);

			_addLocalizedCustomObjectField(
				_language.get(locale, "label"), "label", objectDefinition,
				userId);
		}

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set Sorts"),
			relationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _createTableSectionObjectDefinition(
			Locale locale, ObjectDefinition relatedObjectDefinition,
			long userId)
		throws Exception {

		String erc = "FDSField";
		String className = "FDSField";
		String name = "FDSField";
		String relationshipName = "fdsViewFDSFieldRelationship";

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			erc = "L_DATA_SET_TABLE_SECTION";
			className = "DataSetTableSection";
			name = "DataSetTableSection";
			relationshipName = "dataSetTableSections";
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addSystemObjectDefinition(
				erc, userId, 0, className, null, false, true,
				LocalizedMapUtil.getLocalizedMap("Data Set Table Section"),
				true, name, null, null, null, null,
				LocalizedMapUtil.getLocalizedMap("Data Set Table Sections"),
				false, ObjectDefinitionConstants.SCOPE_COMPANY, null, 1,
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

		_enableLocalization(objectDefinition);

		_addLocalizedCustomObjectField(
			_language.get(locale, "column-label"), "label", objectDefinition,
			userId);

		_objectDefinitionLocalService.publishSystemObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			null, userId, relatedObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap("Data Set Table Sections"),
			relationshipName, false,
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY, null);
	}

	private void _enableLocalization(ObjectDefinition objectDefinition) {
		objectDefinition.setEnableLocalization(true);

		_objectDefinitionLocalService.updateObjectDefinition(objectDefinition);
	}

	private synchronized void _generate(
			long companyId, Locale locale, long userId)
		throws Exception {

		ObjectDefinition relatedObjectDefinition;

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			ObjectDefinition dataSetObjectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					companyId, "DataSet");

			if (dataSetObjectDefinition != null) {
				return;
			}

			dataSetObjectDefinition = _createDataSetObjectDefinition(
				locale, userId);

			relatedObjectDefinition = dataSetObjectDefinition;
		}
		else {
			ObjectDefinition fdsEntryObjectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					companyId, "FDSEntry");

			if (fdsEntryObjectDefinition != null) {
				return;
			}

			fdsEntryObjectDefinition = _createFDSEntryObjectDefinition(
				locale, userId);

			relatedObjectDefinition = _createFDSViewObjectDefinition(
				fdsEntryObjectDefinition, locale, userId);
		}

		_createActionObjectDefinition(locale, relatedObjectDefinition, userId);
		_createCardsSectionObjectDefinition(
			locale, relatedObjectDefinition, userId);
		_createClientExtensionFilterObjectDefintion(
			locale, relatedObjectDefinition, userId);
		_createDateFilterObjectDefinition(
			locale, relatedObjectDefinition, userId);
		_createSelectionFilterObjectDefintion(
			locale, relatedObjectDefinition, userId);
		_createTableSectionObjectDefinition(
			locale, relatedObjectDefinition, userId);
		_createListSectionObjectDefinition(
			locale, relatedObjectDefinition, userId);
		_createSortObjectDefinition(locale, relatedObjectDefinition, userId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSAdminPortlet.class);

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