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

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.MultiMatchQuery;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementDefinitionUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;
import com.liferay.search.experiences.rest.internal.dto.v1_0.converter.SXPElementDTOConverter;
import com.liferay.search.experiences.rest.internal.odata.entity.v1_0.SXPElementEntityModel;
import com.liferay.search.experiences.rest.internal.resource.v1_0.util.TitleMapUtil;
import com.liferay.search.experiences.rest.resource.v1_0.SXPElementResource;
import com.liferay.search.experiences.service.SXPElementService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/sxp-element.properties",
	scope = ServiceScope.PROTOTYPE, service = SXPElementResource.class
)
public class SXPElementResourceImpl
	extends BaseSXPElementResourceImpl implements EntityModelResource {

	@Override
	public void deleteSXPElement(Long sxpElementId) throws Exception {
		_sxpElementService.deleteSXPElement(sxpElementId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityEntityModel;
	}

	@Override
	public SXPElement getSXPElement(Long sxpElementId) throws Exception {
		return _sxpElementDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest, sxpElementId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			_sxpElementService.getSXPElement(sxpElementId));
	}

	@Override
	public Response getSXPElementExport(Long sxpElementId) throws Exception {
		com.liferay.search.experiences.model.SXPElement sxpElement =
			_sxpElementService.getSXPElement(sxpElementId);

		return Response.ok(
		).entity(
			JSONUtil.put(
				"description_i18n",
				_jsonFactory.createJSONObject(
					_jsonFactory.looseSerialize(sxpElement.getDescriptionMap()))
			).put(
				"elementDefinition",
				_jsonFactory.createJSONObject(
					sxpElement.getElementDefinitionJSON())
			).put(
				"title_i18n",
				_jsonFactory.createJSONObject(
					_jsonFactory.looseSerialize(sxpElement.getTitleMap()))
			).put(
				"type", sxpElement.getType()
			)
		).header(
			"Content-Disposition",
			StringBundler.concat(
				"attachment; filename=\"",
				sxpElement.getTitle(
					contextAcceptLanguage.getPreferredLocale(), true),
				".json\"")
		).build();
	}

	@Override
	public Page<SXPElement> getSXPElementsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery1 -> {
				if (!Validator.isBlank(search)) {
					BooleanQuery booleanQuery2 = new BooleanQueryImpl() {
						{
							MultiMatchQuery multiMatchQuery =
								new MultiMatchQuery(search);

							multiMatchQuery.addFields(
								Arrays.asList(
									LocalizationUtil.getLocalizedName(
										Field.DESCRIPTION,
										contextAcceptLanguage.
											getPreferredLanguageId()),
									LocalizationUtil.getLocalizedName(
										Field.TITLE,
										contextAcceptLanguage.
											getPreferredLanguageId())));
							multiMatchQuery.setType(
								MultiMatchQuery.Type.PHRASE_PREFIX);
							multiMatchQuery.setOperator(
								MatchQuery.Operator.AND);

							add(multiMatchQuery, BooleanClauseOccur.SHOULD);

							WildcardQuery wildcardQuery = new WildcardQueryImpl(
								Field.USER_NAME, search + "*");

							add(wildcardQuery, BooleanClauseOccur.SHOULD);
						}
					};

					booleanQuery1.add(booleanQuery2, BooleanClauseOccur.MUST);
				}
			},
			filter,
			com.liferay.search.experiences.model.SXPElement.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				if (!Validator.isBlank(search)) {
					searchContext.setKeywords("");
				}

				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			sorts,
			document -> _sxpElementDTOConverter.toDTO(
				new DefaultDTOConverterContext(
					contextAcceptLanguage.isAcceptAllLanguages(),
					new HashMap<>(), _dtoConverterRegistry,
					contextHttpServletRequest,
					document.get(Field.ENTRY_CLASS_PK),
					contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
					contextUser),
				_sxpElementService.getSXPElement(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public SXPElement patchSXPElement(Long sxpElementId, SXPElement sxpElement)
		throws Exception {

		return _sxpElementDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				sxpElement.getId(), contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser),
			_sxpElementService.updateSXPElement(
				sxpElementId,
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpElement.getDescription(),
					sxpElement.getDescription_i18n()),
				_getElementDefinitionJSON(sxpElement),
				GetterUtil.getBoolean(sxpElement.getHidden()),
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpElement.getTitle(), sxpElement.getTitle_i18n()),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	public SXPElement postSXPElement(SXPElement sxpElement) throws Exception {
		return _sxpElementDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				sxpElement.getId(), contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser),
			_sxpElementService.addSXPElement(
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpElement.getDescription(),
					sxpElement.getDescription_i18n()),
				_getElementDefinitionJSON(sxpElement), false,
				LocalizedMapUtil.getLocalizedMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpElement.getTitle(), sxpElement.getTitle_i18n()),
				GetterUtil.getInteger(sxpElement.getType()),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	public SXPElement postSXPElementCopy(Long sxpElementId) throws Exception {
		com.liferay.search.experiences.model.SXPElement sxpElement =
			_sxpElementService.getSXPElement(sxpElementId);

		return _sxpElementDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				sxpElement.getSXPElementId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			_sxpElementService.addSXPElement(
				sxpElement.getDescriptionMap(),
				sxpElement.getElementDefinitionJSON(), false,
				TitleMapUtil.copy(sxpElement.getTitleMap()),
				sxpElement.getType(),
				ServiceContextFactory.getInstance(contextHttpServletRequest)));
	}

	@Override
	public SXPElement postSXPElementValidate(String json) throws Exception {
		return SXPElementUtil.toSXPElement(json);
	}

	private String _getElementDefinitionJSON(SXPElement sxpElement) {
		if (sxpElement.getElementDefinition() == null) {
			return null;
		}

		return String.valueOf(
			ElementDefinitionUtil.unpack(sxpElement.getElementDefinition()));
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	private final SXPElementEntityModel _entityEntityModel =
		new SXPElementEntityModel();

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SXPElementDTOConverter _sxpElementDTOConverter;

	@Reference
	private SXPElementService _sxpElementService;

}