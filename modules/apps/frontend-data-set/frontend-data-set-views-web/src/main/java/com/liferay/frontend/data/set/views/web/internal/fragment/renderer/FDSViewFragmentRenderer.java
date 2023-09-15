/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.views.web.internal.fragment.renderer;

import com.liferay.client.extension.type.FDSCellRendererCET;
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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManagerProvider;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
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

import java.util.ArrayList;
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
public class FDSViewFragmentRenderer implements FragmentRenderer {

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
							"label", "data-set-view"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						).put(
							"typeOptions", JSONUtil.put("itemType", "FDSView")
						))))
		).toString();
	}

	@Override
	public String getIcon() {
		return "table";
	}

	public String getLabel(Locale locale) {
		return _language.get(locale, "data-set");
	}

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

			ObjectEntry fdsViewObjectEntry = null;

			ObjectDefinition fdsViewObjectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					fragmentEntryLink.getCompanyId(), "FDSView");

			if (Validator.isNotNull(externalReferenceCode)) {
				try {
					fdsViewObjectEntry = _getObjectEntry(
						fragmentEntryLink.getCompanyId(), externalReferenceCode,
						fdsViewObjectDefinition);
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

			if ((fdsViewObjectEntry == null) &&
				fragmentRendererContext.isEditMode()) {

				printWriter.write("<div class=\"portlet-msg-info\">");
				printWriter.write("<ul class=\"navbar-nav\">");
				printWriter.write("<li class=\"nav-item\">");
				printWriter.write(
					_language.get(
						httpServletRequest, "select-a-data-set-view"));
				printWriter.write("</li><li class=\"nav-item\"><div id=\"");

				String betaBadgeComponentId =
					fragmentRendererContext.getFragmentElementId() + "Beta";

				printWriter.write(betaBadgeComponentId);

				printWriter.write("\">");

				Writer writer = new CharArrayWriter();

				ComponentDescriptor componentDescriptor =
					new ComponentDescriptor(
						"{BetaBadge} from frontend-js-components-web",
						betaBadgeComponentId, null, true);

				_reactRenderer.renderReact(
					componentDescriptor, new HashMap<>(), httpServletRequest,
					writer);

				printWriter.write(writer.toString());

				printWriter.write("</div></li></ul></div>");
			}

			if (fdsViewObjectEntry == null) {
				return;
			}

			printWriter.write(
				_buildFragmentHTML(
					fdsViewObjectDefinition, fdsViewObjectEntry, 
					fragmentRendererContext, httpServletRequest));
		}
		catch (Exception exception) {
			_log.error("Unable to render frontend data set view", exception);

			throw new IOException(exception);
		}
	}

	private String _buildFragmentHTML(
			ObjectDefinition fdsViewObjectDefinition,
			ObjectEntry fdsViewObjectEntry,
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

		Map<String, Object> fdsViewObjectEntryProperties =
			fdsViewObjectEntry.getProperties();

		String fdsEntryObjectEntryERC = String.valueOf(
			fdsViewObjectEntryProperties.get(
				"r_fdsEntryFDSViewRelationship_c_fdsEntryERC"));

		ObjectDefinition fdsEntryObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				fragmentEntryLink.getCompanyId(), "FDSEntry");

		ObjectEntry fdsEntryObjectEntry = _getObjectEntry(
			fragmentEntryLink.getCompanyId(), fdsEntryObjectEntryERC,
			fdsEntryObjectDefinition);

		_reactRenderer.renderReact(
			componentDescriptor,
			HashMapBuilder.<String, Object>put(
				"apiURL", _getAPIURL(fdsEntryObjectEntry, httpServletRequest)
			).put(
				"filters",
				_getFiltersJSONArray(
					fdsViewObjectDefinition, fdsViewObjectEntry,
					httpServletRequest)
			).put(
				"id", "FDS_" + fragmentRendererContext.getFragmentElementId()
			).put(
				"namespace", fragmentRendererContext.getFragmentElementId()
			).put(
				"pagination", _getPaginationJSONObject(fdsViewObjectEntry)
			).put(
				"sorting", _getSortingsJSONArray(fdsViewObjectDefinition, fdsViewObjectEntry)
			).put(
				"style", "fluid"
			).put(
				"views",
				JSONUtil.putAll(
					JSONUtil.put(
						"contentRenderer", "table"
					).put(
						"name", "table"
					).put(
						"schema",
						JSONUtil.put(
							"fields",
							_getFieldsJSONArray(
								fragmentEntryLink, fdsViewObjectDefinition,
								fdsViewObjectEntry))
					))
			).build(),
			httpServletRequest, writer);

		sb.append(writer.toString());

		sb.append("</div>");

		return sb.toString();
	}

	private String _getAPIURL(
		ObjectEntry fdsEntryObjectEntry,
		HttpServletRequest httpServletRequest) {

		Map<String, Object> properties = fdsEntryObjectEntry.getProperties();

		StringBundler sb = new StringBundler(3);

		sb.append("/o");
		sb.append(
			StringUtil.replaceLast(
				String.valueOf(properties.get("restApplication")), "/v1.0",
				StringPool.BLANK));
		sb.append(String.valueOf(properties.get("restEndpoint")));

		return _interpolateURL(sb.toString(), httpServletRequest);
	}

	private JSONObject _getDateJSONObject(Object object) {
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
			FragmentEntryLink fragmentEntryLink,
			ObjectDefinition fdsViewObjectDefinition,
			ObjectEntry fdsViewObjectEntry)
		throws Exception {

		Set<ObjectEntry> fdsFieldObjectEntries = new TreeSet<>(
			new ObjectEntryComparator(
				ListUtil.toList(
					ListUtil.fromString(
						MapUtil.getString(
							fdsViewObjectEntry.getProperties(),
							"fdsFieldsOrder"),
						StringPool.COMMA),
					Long::parseLong)));

		fdsFieldObjectEntries.addAll(
			_getRelatedObjectEntries(
				fdsViewObjectDefinition, fdsViewObjectEntry,
				"fdsViewFDSFieldRelationship"));

		return JSONUtil.toJSONArray(
			fdsFieldObjectEntries,
			(ObjectEntry fdsFieldObjectEntry) -> {
				Map<String, Object> fdsFieldProperties =
					fdsFieldObjectEntry.getProperties();

				JSONObject jsonObject = JSONUtil.put(
					"contentRenderer",
					String.valueOf(fdsFieldProperties.get("renderer"))
				).put(
					"fieldName", String.valueOf(fdsFieldProperties.get("name"))
				).put(
					"label",
					() -> {
						String label = String.valueOf(
							fdsFieldProperties.get("label"));

						if (Validator.isNotNull(label)) {
							return label;
						}

						return String.valueOf(fdsFieldProperties.get("name"));
					}
				).put(
					"sortable", (boolean)fdsFieldProperties.get("sortable")
				);

				String rendererType = String.valueOf(
					fdsFieldProperties.get("rendererType"));

				if (!Objects.equals(rendererType, "clientExtension")) {
					return jsonObject;
				}

				FDSCellRendererCET fdsCellRendererCET =
					(FDSCellRendererCET)_cetManager.getCET(
						fragmentEntryLink.getCompanyId(),
						String.valueOf(fdsFieldProperties.get("renderer")));

				return jsonObject.put(
					"contentRendererClientExtension", true
				).put(
					"contentRendererModuleURL",
					"default from " + fdsCellRendererCET.getURL()
				);
			});
	}

	private JSONArray _getFiltersJSONArray(
			ObjectDefinition fdsViewObjectDefinition,
			ObjectEntry fdsViewObjectEntry,
			HttpServletRequest httpServletRequest)
		throws Exception {

		Set<ObjectEntry> fdsFilterObjectEntries = new TreeSet<>(
			new ObjectEntryComparator(
				ListUtil.toList(
					ListUtil.fromString(
						MapUtil.getString(
							fdsViewObjectEntry.getProperties(),
							"fdsFiltersOrder"),
						StringPool.COMMA),
					Long::parseLong)));

		fdsFilterObjectEntries.addAll(
			_getRelatedObjectEntries(
				fdsViewObjectDefinition, fdsViewObjectEntry,
				"fdsViewFDSDateFilterRelationship"));
		fdsFilterObjectEntries.addAll(
			_getRelatedObjectEntries(
				fdsViewObjectDefinition, fdsViewObjectEntry,
				"fdsViewFDSDynamicFilterRelationship"));

		return JSONUtil.toJSONArray(
			fdsFilterObjectEntries,
			(ObjectEntry fdsFilterObjectEntry) -> {
				Map<String, Object> properties =
					fdsFilterObjectEntry.getProperties();

				String type = MapUtil.getString(properties, "type");

				if (Objects.equals(type, "date")) {
					return JSONUtil.put(
						"entityFieldType", FDSEntityFieldTypes.DATE
					).put(
						"id", properties.get("fieldName")
					).put(
						"label", properties.get("name")
					).put(
						"max", _getDateJSONObject(properties.get("to"))
					).put(
						"min", _getDateJSONObject(properties.get("from"))
					).put(
						"type", "dateRange"
					);
				}

				String listTypeDefinitionId = MapUtil.getString(
					properties, "listTypeDefinitionId");

				if (Validator.isNotNull(listTypeDefinitionId)) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					ListTypeDefinition listTypeDefinition =
						_listTypeDefinitionLocalService.
							getListTypeDefinitionByExternalReferenceCode(
								listTypeDefinitionId,
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
						"label", properties.get("name")
					).put(
						"multiple", properties.get("multiple")
					).put(
						"selectedData",
						JSONUtil.put(
							"exclude", false
						).put(
							"selectedItems",
							_getSelectedItemsJSONArray(
								listTypeEntries, themeDisplay.getLocale(),
								MapUtil.getString(
									properties, "preselectedValues"))
						)
					).put(
						"type", "selection"
					);
				}

				return null;
			});
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

	private JSONObject _getPaginationJSONObject(ObjectEntry fdsViewObjectEntry)
		throws Exception {

		Map<String, Object> properties = fdsViewObjectEntry.getProperties();

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
			ObjectDefinition fdsViewObjectDefinition,
			ObjectEntry fdsViewObjectEntry, String relationshipName)
		throws Exception {

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null, LocaleUtil.getSiteDefault(),
				null, null);

		DefaultObjectEntryManager defaultObjectEntryManager =
			DefaultObjectEntryManagerProvider.provide(
				_objectEntryManagerRegistry.getObjectEntryManager(
					fdsViewObjectDefinition.getStorageType()));

		Page<ObjectEntry> relatedObjectEntriesPage =
			defaultObjectEntryManager.getObjectEntryRelatedObjectEntries(
				dtoConverterContext, fdsViewObjectDefinition,
				fdsViewObjectEntry.getId(), relationshipName,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		return relatedObjectEntriesPage.getItems();
	}

	private JSONArray _getSortingsJSONArray(
		ObjectDefinition fdsViewObjectDefinition, ObjectEntry fdsViewObjectEntry) {

		try {
			List<ObjectEntry> fdsSortingObjectEntries = new ArrayList<>(
				_getRelatedObjectEntries(
					fdsViewObjectDefinition, fdsViewObjectEntry, "fdsViewFDSSortRelationship"));

			if (ListUtil.isEmpty(fdsSortingObjectEntries)) {
				return _jsonFactory.createJSONArray();
			}

			return JSONUtil.toJSONArray(
				fdsSortingObjectEntries,
				(ObjectEntry fdsSortingObjectEntry) -> _getSortingsJSONObject(
					fdsSortingObjectEntry));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to create frontend data set sort from frontend data set view", exception);
			}
			return _jsonFactory.createJSONArray();
		}
	}

	private JSONObject _getSortingsJSONObject(ObjectEntry fdsSortingObjectEntry) {
		Map<String, Object> fdsSortingProperties = fdsSortingObjectEntry.getProperties();

		return JSONUtil.put(
			"direction", fdsSortingProperties.get("sortingDirection")
		).put(
			"key", fdsSortingProperties.get("fieldName")
		);
	}

	private String _interpolateURL(
		String apiUrl, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		apiUrl = StringUtil.replace(
			apiUrl, "{siteId}", String.valueOf(themeDisplay.getScopeGroupId()));
		apiUrl = StringUtil.replace(
			apiUrl, "{scopeKey}",
			String.valueOf(themeDisplay.getScopeGroupId()));
		apiUrl = StringUtil.replace(
			apiUrl, "{userId}", String.valueOf(themeDisplay.getUserId()));

		if (StringUtil.contains(apiUrl, "{") && _log.isWarnEnabled()) {
			_log.warn("Unsupported parameter in API URL: " + apiUrl);
		}

		return apiUrl;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FDSViewFragmentRenderer.class);

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
				return Long.compare(id1, id2);
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