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

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementInstanceUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;
import com.liferay.search.experiences.rest.internal.dto.v1_0.converter.SXPBlueprintDTOConverter;
import com.liferay.search.experiences.rest.internal.resource.v1_0.util.TitleMapUtil;
import com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.service.SXPBlueprintService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
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

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private SXPBlueprintDTOConverter _sxpBlueprintDTOConverter;

	@Reference
	private SXPBlueprintService _sxpBlueprintService;

}