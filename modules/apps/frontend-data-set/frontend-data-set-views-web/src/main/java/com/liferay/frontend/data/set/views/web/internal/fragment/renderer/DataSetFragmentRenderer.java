/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.views.web.internal.fragment.renderer;

import com.liferay.client.extension.type.FDSCellRendererCET;
import com.liferay.client.extension.type.FDSFilterCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 * @author Marko Cikos
 */
@Component(service = FragmentRenderer.class)
public class DataSetFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "data-set"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						).put(
							"typeOptions",
							JSONUtil.put(
								"itemType",
								FeatureFlagManagerUtil.isEnabled("LPD-15729") ?
									"DSMDataSet" : "FDSView")
						))))
		).toString();
	}

	@Override
	public String getIcon() {
		return "table";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "data-set");
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-164563")) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(true);

			PrintWriter printWriter = httpServletResponse.getWriter();

			FragmentEntryLink fragmentEntryLink =
				fragmentRendererContext.getFragmentEntryLink();

			JSONObject jsonObject =
				(JSONObject)_fragmentEntryConfigurationParser.getFieldValue(
					getConfiguration(fragmentRendererContext),
					fragmentEntryLink.getEditableValues(),
					fragmentRendererContext.getLocale(), "itemSelector");

			String externalReferenceCode = jsonObject.getString(
				"externalReferenceCode");

			ObjectEntry dataSetObjectEntry = null;

			ObjectDefinition dataSetObjectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					fragmentEntryLink.getCompanyId(),
					FeatureFlagManagerUtil.isEnabled("LPD-15729") ?
						"DSMDataSet" : "FDSView");

			if (Validator.isNotNull(externalReferenceCode)) {
				try {
					dataSetObjectEntry = _getObjectEntry(
						fragmentEntryLink.getCompanyId(), externalReferenceCode,
						dataSetObjectDefinition);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get frontend data set view with " +
								"external reference code " +
									externalReferenceCode,
							exception);
					}
				}
			}

			if ((dataSetObjectEntry == null) &&
				fragmentRendererContext.isEditMode()) {

				printWriter.write("<div class=\"portlet-msg-info\">");
				printWriter.write("<ul class=\"navbar-nav\">");
				printWriter.write("<li class=\"nav-item\">");
				printWriter.write(
					_language.get(httpServletRequest, "select-a-data-set"));
				printWriter.write("</li><li class=\"nav-item\"><div id=\"");

				String betaBadgeComponentId =
					fragmentRendererContext.getFragmentElementId() + "Beta";

				printWriter.write(betaBadgeComponentId);

				printWriter.write("\">");

				Writer writer = new CharArrayWriter();

				ComponentDescriptor componentDescriptor =
					new ComponentDescriptor(
						"{FeatureIndicator} from frontend-js-components-web",
						betaBadgeComponentId, null, true);

				_reactRenderer.renderReact(
					componentDescriptor, new HashMap<>(), httpServletRequest,
					writer);

				printWriter.write(writer.toString());

				printWriter.write("</div></li></ul></div>");
			}

			if (dataSetObjectEntry == null) {
				return;
			}

			printWriter.write(
				_buildFragmentHTML(
					dataSetObjectDefinition, dataSetObjectEntry,
					fragmentRendererContext, httpServletRequest));
		}
		catch (Exception exception) {
			_log.error("Unable to render frontend data set view", exception);

			throw new IOException(exception);
		}
		finally {
			ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(false);
		}
	}

	private String _buildFragmentHTML(
			ObjectDefinition dataSetObjectDefinition,
			ObjectEntry dataSetObjectEntry,
			FragmentRendererContext fragmentRendererContext,
			HttpServletRequest httpServletRequest)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("<div id=\"");
		sb.append(fragmentRendererContext.getFragmentElementId());
		sb.append("\" >");

		ComponentDescriptor componentDescriptor = new ComponentDescriptor(
			"{FrontendDataSet} from frontend-data-set-web",
			fragmentRendererContext.getFragmentElementId(), null, true);

		Writer writer = new CharArrayWriter();

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		Map<String, Object> dataSetObjectEntryProperties =
			dataSetObjectEntry.getProperties();

		String objectEntryERC = String.valueOf(
			dataSetObjectEntryProperties.get(
				"r_fdsEntryFDSViewRelationship_c_fdsEntryERC"));

		String objectDefinitionName = "FDSEntry";

		Collection<ObjectEntry> cardsSectionObjectEntries;
		Collection<ObjectEntry> listSectionObjectEntries;
		Set<ObjectEntry> tableSectionObjectEntries;

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			objectEntryERC = dataSetObjectEntry.getExternalReferenceCode();

			objectDefinitionName = "DSMDataSet";

			cardsSectionObjectEntries = _getRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry,
				"dsmDataSetCardsSectionRelationship");

			listSectionObjectEntries = _getRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry,
				"dsmDataSetListSectionRelationship");

			tableSectionObjectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry,
				"tableSectionsOrder", "dsmDataSetTableSectionRelationship");
		}
		else {
			cardsSectionObjectEntries = _getRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry,
				"fdsViewFDSCardsSectionRelationship");

			listSectionObjectEntries = _getRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry,
				"fdsViewFDSListSectionRelationship");

			tableSectionObjectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry, "fdsFieldsOrder",
				"fdsViewFDSFieldRelationship");
		}

		_reactRenderer.renderReact(
			componentDescriptor,
			HashMapBuilder.<String, Object>put(
				"apiURL",
				_getAPIURL(
					_getObjectEntry(
						fragmentEntryLink.getCompanyId(), objectEntryERC,
						_objectDefinitionLocalService.fetchObjectDefinition(
							fragmentEntryLink.getCompanyId(),
							objectDefinitionName)),
					tableSectionObjectEntries, httpServletRequest)
			).put(
				"creationMenu",
				_getCreationMenuJSONObject(
					dataSetObjectDefinition, dataSetObjectEntry)
			).put(
				"filters",
				_getFiltersJSONArray(
					dataSetObjectDefinition, dataSetObjectEntry,
					httpServletRequest)
			).put(
				"id", "FDS_" + fragmentRendererContext.getFragmentElementId()
			).put(
				"itemsActions",
				_getItemsActionsJSONArray(
					dataSetObjectDefinition, dataSetObjectEntry)
			).put(
				"namespace", fragmentRendererContext.getFragmentElementId()
			).put(
				"pagination", _getPaginationJSONObject(dataSetObjectEntry)
			).put(
				"sorts",
				_getSortsJSONArray(dataSetObjectDefinition, dataSetObjectEntry)
			).put(
				"style", "fluid"
			).put(
				"views",
				_getViewsJSONArray(
					fragmentEntryLink.getCompanyId(), cardsSectionObjectEntries,
					String.valueOf(
						dataSetObjectEntryProperties.get(
							"defaultVisualizationMode")),
					tableSectionObjectEntries, listSectionObjectEntries,
					httpServletRequest)
			).build(),
			httpServletRequest, writer);

		sb.append(writer.toString());

		sb.append("</div>");

		return sb.toString();
	}

	private String _getAPIURL(
			ObjectEntry fdsEntryObjectEntry,
			Set<ObjectEntry> fdsFieldObjectEntries,
			HttpServletRequest httpServletRequest)
		throws Exception {

		Map<String, Object> properties = fdsEntryObjectEntry.getProperties();

		StringBundler sb = new StringBundler(3);

		sb.append("/o");
		sb.append(
			StringUtil.replaceLast(
				String.valueOf(properties.get("restApplication")), "/v1.0",
				StringPool.BLANK));
		sb.append(String.valueOf(properties.get("restEndpoint")));

		return _interpolateURL(
			_getNestedFields(sb.toString(), fdsFieldObjectEntries),
			httpServletRequest);
	}

	private JSONObject _getCardsViewJSONObject(
			Collection<ObjectEntry> cardsSectionObjectEntries,
			String defaultVisualizationMode,
			HttpServletRequest httpServletRequest)
		throws Exception {

		return JSONUtil.put(
			"contentRenderer", "cards"
		).put(
			"default", defaultVisualizationMode.equals("cards")
		).put(
			"label", _language.get(httpServletRequest, "cards")
		).put(
			"name", "cards"
		).put(
			"schema", _getViewSchemaJSONObject(cardsSectionObjectEntries)
		).put(
			"thumbnail", "cards2"
		);
	}

	private JSONObject _getCreationMenuJSONObject(
			ObjectDefinition dataSetObjectDefinition,
			ObjectEntry dataSetObjectEntry)
		throws Exception {

		Set<ObjectEntry> objectEntries;

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			objectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry,
				"creationActionsOrder", "dsmDataSetCreationActionRelationship");
		}
		else {
			objectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry,
				"fdsCreationActionsOrder",
				"fdsViewFDSCreationActionRelationship");
		}

		return JSONUtil.put(
			"primaryItems",
			JSONUtil.toJSONArray(
				objectEntries,
				(ObjectEntry objectEntry) -> {
					Map<String, Object> properties =
						objectEntry.getProperties();

					return JSONUtil.put(
						"data",
						JSONUtil.put(
							"disableHeader",
							(boolean)Validator.isNull(properties.get("title"))
						).put(
							"permissionKey", properties.get("permissionKey")
						).put(
							"size", properties.get("modalSize")
						).put(
							"title", properties.get("title")
						)
					).put(
						"href", properties.get("url")
					).put(
						"icon", properties.get("icon")
					).put(
						"label", properties.get("label")
					).put(
						"target", properties.get("type")
					);
				}));
	}

	private JSONObject _getDateJSONObject(Object object) {
		if (object == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();

		Timestamp timestamp = (Timestamp)object;

		calendar.setTime(new Date(timestamp.getTime()));

		return JSONUtil.put(
			"day", calendar.get(Calendar.DATE)
		).put(
			"month", calendar.get(Calendar.MONTH) + 1
		).put(
			"year", calendar.get(Calendar.YEAR)
		);
	}

	private JSONArray _getFieldsJSONArray(
			long companyId, Set<ObjectEntry> tableSectionObjectEntries)
		throws Exception {

		return JSONUtil.toJSONArray(
			tableSectionObjectEntries,
			(ObjectEntry objectEntry) -> {
				Map<String, Object> properties = objectEntry.getProperties();

				JSONObject jsonObject = JSONUtil.put(
					"contentRenderer",
					String.valueOf(properties.get("renderer"))
				).put(
					"fieldName", String.valueOf(properties.get("name"))
				).put(
					"label", _getValue("label", "name", properties)
				).put(
					"sortable", (boolean)properties.get("sortable")
				);

				String rendererType = String.valueOf(
					properties.get("rendererType"));

				if (!Objects.equals(rendererType, "clientExtension")) {
					return jsonObject;
				}

				FDSCellRendererCET fdsCellRendererCET =
					(FDSCellRendererCET)_cetManager.getCET(
						companyId, String.valueOf(properties.get("renderer")));

				return jsonObject.put(
					"contentRendererClientExtension", true
				).put(
					"contentRendererModuleURL",
					"default from " + fdsCellRendererCET.getURL()
				);
			});
	}

	private JSONArray _getFiltersJSONArray(
			ObjectDefinition dataSetObjectDefinition,
			ObjectEntry dataSetObjectEntry,
			HttpServletRequest httpServletRequest)
		throws Exception {

		Set<ObjectEntry> objectEntries;

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			objectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry, "filtersOrder",
				"dsmDataSetClientExtensionFilter",
				"dsmDataSetDateFilterRelationship",
				"dsmDataSetSelectionFilterRelationship");
		}
		else {
			objectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry, "fdsFiltersOrder",
				"fdsViewFDSClientExtensionFilter",
				"fdsViewFDSDateFilterRelationship",
				"fdsViewFDSDynamicFilterRelationship");
		}

		return JSONUtil.toJSONArray(
			objectEntries,
			(ObjectEntry objectEntry) -> {
				Map<String, Object> properties = objectEntry.getProperties();

				String type = MapUtil.getString(properties, "type");

				if (Objects.equals(type, "date") ||
					Objects.equals(type, "date-time")) {

					JSONObject fromJSONObject = _getDateJSONObject(
						properties.get("from"));
					JSONObject toJSONObject = _getDateJSONObject(
						properties.get("to"));

					boolean hasPreloadedData =
						(fromJSONObject != null) || (toJSONObject != null);

					return JSONUtil.put(
						"active", hasPreloadedData
					).put(
						"entityFieldType",
						Objects.equals(type, "date") ?
							FDSEntityFieldTypes.DATE :
								FDSEntityFieldTypes.DATE_TIME
					).put(
						"id", properties.get("fieldName")
					).put(
						"label", _getValue("label", "fieldName", properties)
					).put(
						"preloadedData",
						() -> {
							if (!hasPreloadedData) {
								return null;
							}

							return JSONUtil.put(
								"from", fromJSONObject
							).put(
								"to", toJSONObject
							);
						}
					).put(
						"type", "dateRange"
					);
				}

				String listTypeDefinitionERC = null;

				if (FeatureFlagManagerUtil.isEnabled("LPD-10754")) {
					listTypeDefinitionERC = MapUtil.getString(
						properties, "source");
				}
				else {
					listTypeDefinitionERC = MapUtil.getString(
						properties, "listTypeDefinitionERC");
				}

				if (Validator.isNotNull(listTypeDefinitionERC)) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					ListTypeDefinition listTypeDefinition =
						_listTypeDefinitionLocalService.
							getListTypeDefinitionByExternalReferenceCode(
								listTypeDefinitionERC,
								themeDisplay.getCompanyId());

					List<ListTypeEntry> listTypeEntries =
						_listTypeEntryLocalService.getListTypeEntries(
							listTypeDefinition.getListTypeDefinitionId());

					return JSONUtil.put(
						"autocompleteEnabled", true
					).put(
						"entityFieldType", FDSEntityFieldTypes.STRING
					).put(
						"id", properties.get("fieldName")
					).put(
						"items",
						JSONUtil.toJSONArray(
							listTypeEntries,
							listTypeEntry -> JSONUtil.put(
								"key", listTypeEntry.getKey()
							).put(
								"label",
								listTypeEntry.getName(themeDisplay.getLocale())
							).put(
								"value", listTypeEntry.getKey()
							))
					).put(
						"label", _getValue("label", "fieldName", properties)
					).put(
						"multiple", properties.get("multiple")
					).put(
						"preloadedData",
						() -> {
							JSONArray selectedItemsJSONArray =
								_getSelectedItemsJSONArray(
									listTypeEntries, themeDisplay.getLocale(),
									MapUtil.getString(
										properties, "preselectedValues"));

							if (JSONUtil.isEmpty(selectedItemsJSONArray)) {
								return null;
							}

							return JSONUtil.put(
								"exclude",
								() -> Boolean.FALSE.equals(
									(Boolean)properties.get("include"))
							).put(
								"selectedItems", selectedItemsJSONArray
							);
						}
					).put(
						"type", "selection"
					);
				}

				String fdsFilterClientExtensionERC = MapUtil.getString(
					properties, "fdsFilterClientExtensionERC");

				if (Validator.isNotNull(fdsFilterClientExtensionERC)) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					FDSFilterCET fdsFilterCET =
						(FDSFilterCET)_cetManager.getCET(
							themeDisplay.getCompanyId(),
							fdsFilterClientExtensionERC);

					if (fdsFilterCET == null) {
						_log.error(
							StringBundler.concat(
								"No frontend data set filter client extension ",
								"exists with the external reference code ",
								fdsFilterClientExtensionERC));

						return null;
					}

					return JSONUtil.put(
						"clientExtensionFilterURL", fdsFilterCET.getURL()
					).put(
						"entityFieldType", FDSEntityFieldTypes.STRING
					).put(
						"id", properties.get("fieldName")
					).put(
						"label", _getValue("label", "fieldName", properties)
					).put(
						"type", "clientExtension"
					);
				}

				return null;
			});
	}

	private JSONArray _getItemsActionsJSONArray(
			ObjectDefinition dataSetObjectDefinition,
			ObjectEntry dataSetObjectEntry)
		throws Exception {

		Set<ObjectEntry> objectEntries;

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			objectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry, "itemActionsOrder",
				"dsmDataSetItemActionRelationship");
		}
		else {
			objectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry,
				"fdsItemActionsOrder", "fdsViewFDSItemActionRelationship");
		}

		return JSONUtil.toJSONArray(
			objectEntries,
			(ObjectEntry objectEntry) -> {
				Map<String, Object> properties = objectEntry.getProperties();

				return JSONUtil.put(
					"data",
					JSONUtil.put(
						"confirmationMessage",
						properties.get("confirmationMessage")
					).put(
						"disableHeader",
						(boolean)Validator.isNull(properties.get("title"))
					).put(
						"errorMessage", properties.get("errorMessage")
					).put(
						"method", properties.get("method")
					).put(
						"permissionKey", properties.get("permissionKey")
					).put(
						"size", properties.get("modalSize")
					).put(
						"status", properties.get("confirmationMessageType")
					).put(
						"successMessage", properties.get("successMessage")
					).put(
						"title", properties.get("title")
					)
				).put(
					"href", properties.get("url")
				).put(
					"icon", properties.get("icon")
				).put(
					"label", properties.get("label")
				).put(
					"target", properties.get("type")
				);
			});
	}

	private JSONObject _getListViewJSONObject(
			String defaultVisualizationMode,
			Collection<ObjectEntry> listSectionObjectEntries,
			HttpServletRequest httpServletRequest)
		throws Exception {

		return JSONUtil.put(
			"contentRenderer", "list"
		).put(
			"default", defaultVisualizationMode.equals("list")
		).put(
			"label", _language.get(httpServletRequest, "list")
		).put(
			"name", "list"
		).put(
			"schema", _getViewSchemaJSONObject(listSectionObjectEntries)
		).put(
			"thumbnail", "list"
		);
	}

	private String _getNestedFields(
			String apiURL, Set<ObjectEntry> fdsFieldObjectEntries)
		throws Exception {

		if (fdsFieldObjectEntries == null) {
			return apiURL;
		}

		String nestedFields = StringPool.BLANK;
		int nestedFieldsDepth = 1;

		for (ObjectEntry fdsFieldObjectEntry : fdsFieldObjectEntries) {
			Map<String, Object> properties =
				fdsFieldObjectEntry.getProperties();

			String[] fieldNameList = StringUtil.split(
				StringUtil.replace(
					String.valueOf(properties.get("name")), "[]",
					StringPool.PERIOD),
				CharPool.PERIOD);

			if (fieldNameList.length > 1) {
				String[] fieldsName = new String[fieldNameList.length - 1];

				System.arraycopy(
					fieldNameList, 0, fieldsName, 0, fieldNameList.length - 1);

				for (String fieldName : fieldsName) {
					nestedFields = StringUtil.add(nestedFields, fieldName);
				}

				if (fieldNameList.length > nestedFieldsDepth) {
					nestedFieldsDepth = fieldNameList.length - 1;
				}
			}
		}

		if (nestedFields.equals(StringPool.BLANK)) {
			return apiURL;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(apiURL);
		sb.append("?nestedFields=");
		sb.append(
			StringUtil.replaceLast(
				nestedFields, CharPool.COMMA, StringPool.BLANK));

		if (nestedFieldsDepth > 1) {
			sb.append("&nestedFieldsDepth=");
			sb.append(nestedFieldsDepth);
		}

		return sb.toString();
	}

	private ObjectEntry _getObjectEntry(
			long companyId, String externalReferenceCode,
			ObjectDefinition objectDefinition)
		throws Exception {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getStorageType()));

		return defaultObjectEntryManager.getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition, null);
	}

	private JSONObject _getPaginationJSONObject(ObjectEntry dataSetObjectEntry)
		throws Exception {

		Map<String, Object> properties = dataSetObjectEntry.getProperties();

		return JSONUtil.put(
			"deltas",
			JSONUtil.toJSONArray(
				StringUtil.split(
					String.valueOf(properties.get("listOfItemsPerPage")),
					StringPool.COMMA_AND_SPACE),
				(String itemPerPage) -> JSONUtil.put(
					"label", GetterUtil.getInteger(itemPerPage)))
		).put(
			"initialDelta",
			String.valueOf(properties.get("defaultItemsPerPage"))
		);
	}

	private Collection<ObjectEntry> _getRelatedObjectEntries(
			ObjectDefinition dataSetObjectDefinition,
			ObjectEntry dataSetObjectEntry, String relationshipName)
		throws Exception {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					dataSetObjectDefinition.getStorageType()));

		Page<ObjectEntry> relatedObjectEntriesPage =
			defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
				dtoConverterContext, dataSetObjectDefinition,
				dataSetObjectEntry.getId(), relationshipName,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		return relatedObjectEntriesPage.getItems();
	}

	private JSONArray _getSelectedItemsJSONArray(
			List<ListTypeEntry> listTypeEntries, Locale locale,
			String preselectedValues)
		throws JSONException {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		JSONArray preselectedValuesJSONArray = _jsonFactory.createJSONArray(
			preselectedValues);

		for (int i = 0; i < preselectedValuesJSONArray.length(); i++) {
			String key = preselectedValuesJSONArray.getString(i);

			for (ListTypeEntry listTypeEntry : listTypeEntries) {
				if (Objects.equals(
						listTypeEntry.getExternalReferenceCode(), key)) {

					jsonArray.put(
						JSONUtil.put(
							"label", listTypeEntry.getName(locale)
						).put(
							"value", listTypeEntry.getKey()
						));

					break;
				}
			}
		}

		return jsonArray;
	}

	private Set<ObjectEntry> _getSortedRelatedObjectEntries(
			ObjectDefinition dataSetObjectDefinition,
			ObjectEntry dataSetObjectEntry,
			String objectEntryComparatorIdsPropertyKey,
			String... relationshipNames)
		throws Exception {

		Set<ObjectEntry> objectEntries = new TreeSet<>(
			new ObjectEntryComparator(
				ListUtil.toList(
					ListUtil.fromString(
						MapUtil.getString(
							dataSetObjectEntry.getProperties(),
							objectEntryComparatorIdsPropertyKey),
						StringPool.COMMA),
					Long::parseLong)));

		for (String relationshipName : relationshipNames) {
			objectEntries.addAll(
				_getRelatedObjectEntries(
					dataSetObjectDefinition, dataSetObjectEntry,
					relationshipName));
		}

		return objectEntries;
	}

	private JSONArray _getSortsJSONArray(
			ObjectDefinition dataSetObjectDefinition,
			ObjectEntry dataSetObjectEntry)
		throws Exception {

		Set<ObjectEntry> objectEntries;

		if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
			objectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry, "sortsOrder",
				"dsmDataSetSortRelationship");
		}
		else {
			objectEntries = _getSortedRelatedObjectEntries(
				dataSetObjectDefinition, dataSetObjectEntry, "fdsSortsOrder",
				"fdsViewFDSSortRelationship");
		}

		return JSONUtil.toJSONArray(
			objectEntries,
			(ObjectEntry objectEntry) -> {
				Map<String, Object> properties = objectEntry.getProperties();

				if (FeatureFlagManagerUtil.isEnabled("LPD-19465")) {
					return JSONUtil.put(
						"active", properties.get("default")
					).put(
						"default", properties.get("default")
					).put(
						"direction", properties.get("orderType")
					).put(
						"key", properties.get("fieldName")
					).put(
						"label", properties.get("label")
					);
				}

				return JSONUtil.put(
					"direction", properties.get("sortingDirection")
				).put(
					"key", properties.get("fieldName")
				);
			});
	}

	private JSONObject _getTableViewJSONObject(
			long companyId, String defaultVisualizationMode,
			Set<ObjectEntry> tableSectionObjectEntries,
			HttpServletRequest httpServletRequest)
		throws Exception {

		return JSONUtil.put(
			"contentRenderer", "table"
		).put(
			"default", defaultVisualizationMode.equals("table")
		).put(
			"label", _language.get(httpServletRequest, "table")
		).put(
			"name", "table"
		).put(
			"schema",
			JSONUtil.put(
				"fields",
				_getFieldsJSONArray(companyId, tableSectionObjectEntries))
		).put(
			"thumbnail", "table"
		);
	}

	private String _getValue(
		String defaultKey, String fallbackKey,
		Map<String, Object> fdsFieldProperties) {

		String value = String.valueOf(fdsFieldProperties.get(defaultKey));

		if (Validator.isNotNull(value)) {
			return value;
		}

		return String.valueOf(fdsFieldProperties.get(fallbackKey));
	}

	private JSONObject _getViewSchemaJSONObject(
			Collection<ObjectEntry> objectEntries)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		for (ObjectEntry objectEntry : objectEntries) {
			Map<String, Object> properties = objectEntry.getProperties();

			jsonObject.put(
				String.valueOf(properties.get("name")),
				String.valueOf(properties.get("fieldName")));
		}

		return jsonObject;
	}

	private JSONArray _getViewsJSONArray(
			long companyId, Collection<ObjectEntry> cardsSectionObjectEntries,
			String defaultVisualizationMode,
			Set<ObjectEntry> tableSectionObjectEntries,
			Collection<ObjectEntry> listSectionObjectEntries,
			HttpServletRequest httpServletRequest)
		throws Exception {

		JSONArray viewsJSONArray = _jsonFactory.createJSONArray();

		if (!cardsSectionObjectEntries.isEmpty()) {
			viewsJSONArray.put(
				_getCardsViewJSONObject(
					cardsSectionObjectEntries, defaultVisualizationMode,
					httpServletRequest));
		}

		if (!listSectionObjectEntries.isEmpty()) {
			viewsJSONArray.put(
				_getListViewJSONObject(
					defaultVisualizationMode, listSectionObjectEntries,
					httpServletRequest));
		}

		if (!tableSectionObjectEntries.isEmpty()) {
			viewsJSONArray.put(
				_getTableViewJSONObject(
					companyId, defaultVisualizationMode,
					tableSectionObjectEntries, httpServletRequest));
		}

		return viewsJSONArray;
	}

	private String _interpolateURL(
		String apiURL, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		apiURL = StringUtil.replace(
			apiURL, "{siteId}", String.valueOf(themeDisplay.getScopeGroupId()));
		apiURL = StringUtil.replace(
			apiURL, "{scopeKey}",
			String.valueOf(themeDisplay.getScopeGroupId()));
		apiURL = StringUtil.replace(
			apiURL, "{userId}", String.valueOf(themeDisplay.getUserId()));

		if (StringUtil.contains(apiURL, "{") && _log.isWarnEnabled()) {
			_log.warn("Unsupported parameter in API URL: " + apiURL);
		}

		return apiURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataSetFragmentRenderer.class);

	@Reference
	private CETManager _cetManager;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ReactRenderer _reactRenderer;

	private static class ObjectEntryComparator
		implements Comparator<ObjectEntry> {

		public ObjectEntryComparator(List<Long> ids) {
			_ids = ids;
		}

		@Override
		public int compare(ObjectEntry objectEntry1, ObjectEntry objectEntry2) {
			long id1 = objectEntry1.getId();
			long id2 = objectEntry2.getId();

			int index1 = _ids.indexOf(id1);
			int index2 = _ids.indexOf(id2);

			if ((index1 == -1) && (index2 == -1)) {
				Date date = objectEntry1.getDateCreated();

				return date.compareTo(objectEntry2.getDateCreated());
			}

			if (index1 == -1) {
				return 1;
			}

			if (index2 == -1) {
				return -1;
			}

			return Long.compare(index1, index2);
		}

		private final List<Long> _ids;

	}

}