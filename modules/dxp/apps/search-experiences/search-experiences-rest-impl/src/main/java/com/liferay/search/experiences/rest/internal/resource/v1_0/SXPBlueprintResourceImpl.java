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
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.constants.SXPConstants;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;
import com.liferay.search.experiences.rest.internal.dto.v1_0.converter.SXPBlueprintDTOConverter;
import com.liferay.search.experiences.rest.internal.odata.entity.v1_0.SXPBlueprintEntityModel;
import com.liferay.search.experiences.rest.internal.resource.v1_0.util.SearchUtil;
import com.liferay.search.experiences.rest.internal.resource.v1_0.util.TitleMapUtil;
import com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.service.SXPBlueprintService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

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
public class SXPBlueprintResourceImpl
	extends BaseSXPBlueprintResourceImpl implements EntityModelResource {

	@Override
	public void deleteSXPBlueprint(Long sxpBlueprintId) throws Exception {
		_sxpBlueprintService.deleteSXPBlueprint(sxpBlueprintId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityEntityModel;
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
	public Response getSXPBlueprintExport(Long sxpBlueprintId)
		throws Exception {

		com.liferay.search.experiences.model.SXPBlueprint sxpBlueprint =
			_sxpBlueprintService.getSXPBlueprint(sxpBlueprintId);

		return Response.ok(
		).encoding(
			"UTF-8"
		).entity(
			JSONUtil.put(
				"configuration",
				_jsonFactory.createJSONObject(
					sxpBlueprint.getConfigurationJSON())
			).put(
				"description_i18n",
				_jsonFactory.createJSONObject(
					_jsonFactory.looseSerialize(
						sxpBlueprint.getDescriptionMap()))
			).put(
				"elementInstances",
				_jsonFactory.createJSONArray(
					sxpBlueprint.getElementInstancesJSON())
			).put(
				"schemaVersion", sxpBlueprint.getSchemaVersion()
			).put(
				"title_i18n",
				_jsonFactory.createJSONObject(
					_jsonFactory.looseSerialize(sxpBlueprint.getTitleMap()))
			)
		).header(
			"Content-Disposition",
			StringBundler.concat(
				"attachment; filename=\"",
				sxpBlueprint.getTitle(
					contextAcceptLanguage.getPreferredLocale(), true),
				".json\"")
		).build();
	}

	@Override
	public Page<SXPBlueprint> getSXPBlueprintsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		if (sorts == null) {
			sorts = new Sort[] {
				new Sort("modified_sortable", Sort.LONG_TYPE, true)
			};
		}

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> SearchUtil.processSXPBooleanQuery(
				contextAcceptLanguage, booleanQuery, search),
			filter,
			com.liferay.search.experiences.model.SXPBlueprint.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (!Validator.isBlank(search)) {
					searchContext.setKeywords("");
				}
			},
			sorts,
			document -> {
				long sxpBlueprintId = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				SXPBlueprint sxpBlueprint = _sxpBlueprintDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(),
						new HashMap<>(), _dtoConverterRegistry,
						contextHttpServletRequest,
						document.get(Field.ENTRY_CLASS_PK),
						contextAcceptLanguage.getPreferredLocale(),
						contextUriInfo, contextUser),
					_sxpBlueprintService.getSXPBlueprint(sxpBlueprintId));

				String permissionName =
					com.liferay.search.experiences.model.SXPBlueprint.class.
						getName();

				sxpBlueprint.setActions(
					HashMapBuilder.put(
						"create",
						() -> addAction(
							SXPActionKeys.ADD_SXP_BLUEPRINT, "postSXPBlueprint",
							SXPConstants.RESOURCE_NAME,
							contextCompany.getCompanyId())
					).put(
						"delete",
						() -> addAction(
							ActionKeys.DELETE, "deleteSXPBlueprint",
							permissionName, sxpBlueprintId)
					).put(
						"get",
						() -> addAction(
							ActionKeys.VIEW, "getSXPBlueprint", permissionName,
							sxpBlueprintId)
					).put(
						"update",
						() -> addAction(
							ActionKeys.UPDATE, "patchSXPBlueprint",
							permissionName, sxpBlueprintId)
					).build());

				return sxpBlueprint;
			});
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
				_getElementInstancesJSON(sxpBlueprint), _getSchemaVersion(),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpBlueprint.getTitle(), sxpBlueprint.getTitle_i18n()),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	public SXPBlueprint postSXPBlueprint(SXPBlueprint sxpBlueprint)
		throws Exception {

		SXPBlueprintUtil.unpack(sxpBlueprint);

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
				_getElementInstancesJSON(sxpBlueprint), _getSchemaVersion(),
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
				sxpBlueprint.getSchemaVersion(),
				TitleMapUtil.copy(sxpBlueprint.getTitleMap()),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	public SXPBlueprint postSXPBlueprintValidate(String json) throws Exception {
		return SXPBlueprintUtil.toSXPBlueprint(json);
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

	private String _getSchemaVersion() {
		return StringUtils.substringBetween(
			contextUriInfo.getPath(), "v", StringPool.SLASH);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	private final SXPBlueprintEntityModel _entityEntityModel =
		new SXPBlueprintEntityModel();

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SXPBlueprintDTOConverter _sxpBlueprintDTOConverter;

	@Reference
	private SXPBlueprintService _sxpBlueprintService;

}