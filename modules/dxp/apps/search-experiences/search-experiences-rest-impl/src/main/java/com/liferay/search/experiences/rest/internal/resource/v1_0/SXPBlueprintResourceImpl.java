/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.search.experiences.exception.NoSuchSXPBlueprintException;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;
import com.liferay.search.experiences.rest.internal.dto.v1_0.converter.SXPBlueprintDTOConverter;
import com.liferay.search.experiences.rest.internal.resource.v1_0.util.TitleMapUtil;
import com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.service.SXPBlueprintService;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/sxp-blueprint.properties",
	scope = ServiceScope.PROTOTYPE, service = SXPBlueprintResource.class
)
public class SXPBlueprintResourceImpl extends BaseSXPBlueprintResourceImpl {

	@Override
	public void deleteSXPBlueprint(Long sxpBlueprintId) throws Exception {
		_sxpBlueprintService.deleteSXPBlueprint(sxpBlueprintId);
	}

	@Override
	public SXPBlueprint getSXPBlueprint(Long sxpBlueprintId) throws Exception {
		return _sxpBlueprintDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				sxpBlueprintId, contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser),
			_sxpBlueprintService.getSXPBlueprint(sxpBlueprintId));
	}

	@Override
	public String getSXPBlueprintExport(Long sxpBlueprintId) throws Exception {
		Optional<com.liferay.search.experiences.model.SXPBlueprint> optional =
			getBlueprint(sxpBlueprintId);

		if (!optional.isPresent()) {
			return StringPool.BLANK;
		}

		com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint =
			optional.get();

		String responseString = _buildResponseString(sxpBlueprint);

		String title = _getFileTitle(sxpBlueprint);

		writeResponse(
			contextHttpServletRequest, contextHttpServletResponse, title,
			responseString);

		return responseString;
	}

	@Override
	public Page<SXPBlueprint> getSXPBlueprintsPage(
			String search, Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null,
			com.liferay.search.experiences.model.SXPBlueprint.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {

				// TODO Set the relevant search context attributes

				searchContext.setAttribute(Field.DESCRIPTION, search);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> _sxpBlueprintDTOConverter.toDTO(
				new DefaultDTOConverterContext(
					contextAcceptLanguage.isAcceptAllLanguages(),
					new HashMap<>(), _dtoConverterRegistry,
					contextHttpServletRequest,
					document.get(Field.ENTRY_CLASS_PK),
					contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
					contextUser),
				_sxpBlueprintService.getSXPBlueprint(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public SXPBlueprint patchSXPBlueprint(
			Long sxpBlueprintId, SXPBlueprint sxpBlueprint)
		throws Exception {

		SXPBlueprintUtil.unpack(sxpBlueprint);

		return _sxpBlueprintDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				sxpBlueprint.getId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			_sxpBlueprintService.updateSXPBlueprint(
				sxpBlueprintId, _getConfigurationJSON(sxpBlueprint),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpBlueprint.getDescription(),
					sxpBlueprint.getDescription_i18n()),
				_getElementInstancesJSON(sxpBlueprint),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpBlueprint.getTitle(), sxpBlueprint.getTitle_i18n()),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	public SXPBlueprint postSXPBlueprint(SXPBlueprint sxpBlueprint)
		throws Exception {

		return _sxpBlueprintDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				sxpBlueprint.getId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			_sxpBlueprintService.addSXPBlueprint(
				_getConfigurationJSON(sxpBlueprint),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpBlueprint.getDescription(),
					sxpBlueprint.getDescription_i18n()),
				_getElementInstancesJSON(sxpBlueprint),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpBlueprint.getTitle(), sxpBlueprint.getTitle_i18n()),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	public SXPBlueprint postSXPBlueprintCopy(Long sxpBlueprintId)
		throws Exception {

		com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint =
			_sxpBlueprintService.getSXPBlueprint(sxpBlueprintId);

		return _sxpBlueprintDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				sxpBlueprint.getSXPBlueprintId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			_sxpBlueprintService.addSXPBlueprint(
				sxpBlueprint.getConfigurationJSON(),
				sxpBlueprint.getDescriptionMap(),
				sxpBlueprint.getElementInstancesJSON(),
				TitleMapUtil.copy(sxpBlueprint.getTitleMap()),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	public SXPBlueprint postSXPBlueprintValidate(String json) throws Exception {
		return SXPBlueprintUtil.toSXPBlueprint(json);
	}

	protected Optional<com.liferay.search.experiences.model.SXPBlueprint>
		getBlueprint(Long sxpBlueprintId) {

		if (sxpBlueprintId <= 0) {
			return Optional.empty();
		}

		try {
			return Optional.of(
				_sxpBlueprintService.getSXPBlueprint(sxpBlueprintId));
		}
		catch (NoSuchSXPBlueprintException noSuchSXPBlueprintException) {
			_log.error(
				"Blueprint " + sxpBlueprintId + " not found",
				noSuchSXPBlueprintException);

			SessionErrors.add(
				contextHttpServletRequest, "error",
				noSuchSXPBlueprintException.getMessage());
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				contextHttpServletRequest, "error",
				portalException.getMessage());
		}

		return Optional.empty();
	}

	protected JSONObject mapToJSONObject(Map<Locale, String> map)
		throws JSONException {

		String jsonString = _jsonFactory.looseSerialize(map);

		return _jsonFactory.createJSONObject(jsonString);
	}

	protected void writeResponse(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String title,
		String responseString) {

		try {
			PrintWriter out = httpServletResponse.getWriter();

			httpServletResponse.setContentType("application/json");
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setHeader(
				"Content-disposition", "attachment; filename=" + title);

			out.print(responseString);
			out.flush();
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage(), ioException);

			SessionErrors.add(
				httpServletRequest, "error", ioException.getMessage());
		}
	}

	private String _buildResponseString(
			com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint)
		throws Exception {

		return JSONUtil.put(
			"blueprint-payload",
			JSONUtil.put(
				"configuration",
				_jsonFactory.createJSONObject(
					sxpBlueprint.getConfigurationJSON())
			).put(
				"description", mapToJSONObject(sxpBlueprint.getDescriptionMap())
			).put(
				"selectedElements",
				_jsonFactory.createJSONObject(
					sxpBlueprint.getElementInstancesJSON())
			).put(
				"title", mapToJSONObject(sxpBlueprint.getTitleMap())
			)
		).toString();
	}

	private String _getConfigurationJSON(SXPBlueprint sxpBlueprint) {
		if (sxpBlueprint.getConfiguration() == null) {
			return null;
		}

		return String.valueOf(sxpBlueprint.getConfiguration());
	}

	private String _getElementInstancesJSON(SXPBlueprint sxpBlueprint) {
		if (ArrayUtil.isEmpty(sxpBlueprint.getElementInstances())) {
			return null;
		}

		return Arrays.toString(
			ElementInstanceUtil.unpack(sxpBlueprint.getElementInstances()));
	}

	private String _getFileTitle(
		com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint) {

		String title = sxpBlueprint.getTitle(
			contextAcceptLanguage.getPreferredLocale(), true);

		return title + ".json";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintResourceImpl.class);

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SXPBlueprintDTOConverter _sxpBlueprintDTOConverter;

	@Reference
	private SXPBlueprintService _sxpBlueprintService;

}