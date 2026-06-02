/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.address.internal.resource.v1_0;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.headless.admin.address.dto.v1_0.Region;
import com.liferay.headless.admin.address.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.admin.address.internal.odata.entity.v1_0.RegionEntityModel;
import com.liferay.headless.admin.address.resource.v1_0.RegionResource;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/region.properties",
	property = "export.import.vulcan.batch.engine.task.item.delegate=true",
	scope = ServiceScope.PROTOTYPE, service = RegionResource.class
)
public class RegionResourceImpl
	extends BaseRegionResourceImpl
	implements ExportImportVulcanBatchEngineTaskItemDelegate<Region> {

	@Override
	public void deleteRegion(Long regionId) throws Exception {
		_regionService.deleteRegion(regionId);
	}

	@Override
	public void deleteRegionByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		com.liferay.portal.kernel.model.Region serviceBuilderRegion =
			_regionService.getRegionByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		_regionService.deleteRegion(serviceBuilderRegion.getRegionId());
	}

	@Override
	public Region getCountryRegionByRegionCode(
			Long countryId, String regionCode)
		throws Exception {

		return _toRegion(_regionService.getRegion(countryId, regionCode));
	}

	@Override
	public Page<Region> getCountryRegionsPage(
			Long countryId, Boolean active, String search,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		_countryService.getCountry(countryId);

		BaseModelSearchResult<com.liferay.portal.kernel.model.Region>
			baseModelSearchResult = _regionService.searchRegions(
				contextCompany.getCompanyId(), active, search,
				LinkedHashMapBuilder.<String, Object>put(
					"countryId", countryId
				).build(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				_toOrderByComparator(sorts));

		return Page.of(
			HashMapBuilder.put(
				"createBatch",
				addAction(
					ActionKeys.UPDATE, "postCountryRegionBatch",
					Country.class.getName(), countryId)
			).build(),
			transform(baseModelSearchResult.getBaseModels(), this::_toRegion),
			pagination, baseModelSearchResult.getLength());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public ExportImportDescriptor<? extends BaseModel<?>>
		getExportImportDescriptor() {

		return new ExportImportDescriptor
			<com.liferay.portal.kernel.model.Region>() {

			@Override
			public String getKey() {
				return RegionResourceImpl.class.getName();
			}

			@Override
			public String getLabelLanguageKey() {
				return "regions";
			}

			@Override
			public Class<com.liferay.portal.kernel.model.Region>
				getModelClass() {

				return com.liferay.portal.kernel.model.Region.class;
			}

			@Override
			public List<String> getNestedFields() {
				return List.of("creator");
			}

			@Override
			public String getPortletId() {
				return "com_liferay_address_web_internal_portlet_" +
					"CountriesManagementAdminPortlet";
			}

			@Override
			public Scope getScope() {
				return Scope.COMPANY;
			}

			@Override
			public boolean isActive(PortletDataContext portletDataContext) {
				return false;
			}

		};
	}

	@Override
	public Region getRegion(Long regionId) throws Exception {
		return _toRegion(_regionService.getRegion(regionId));
	}

	@Override
	public Region getRegionByExternalReferenceCode(String externalReferenceCode)
		throws Exception {

		return _toRegion(
			_regionService.getRegionByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId()));
	}

	@Override
	public Page<Region> getRegionsPage(
			Boolean active, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				if (active != null) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter("active", String.valueOf(active)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, com.liferay.portal.kernel.model.Region.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> {
				com.liferay.portal.kernel.model.Region serviceBuilderRegion =
					_regionLocalService.fetchRegion(
						GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));

				if (serviceBuilderRegion == null) {
					return null;
				}

				return _toRegion(serviceBuilderRegion);
			});
	}

	@Override
	public Region patchRegion(Long regionId, Region region) throws Exception {
		com.liferay.portal.kernel.model.Region serviceBuilderRegion =
			_regionService.getRegion(regionId);

		serviceBuilderRegion = _regionService.updateRegion(
			GetterUtil.getString(
				region.getExternalReferenceCode(),
				serviceBuilderRegion.getExternalReferenceCode()),
			regionId,
			GetterUtil.getBoolean(
				region.getActive(), serviceBuilderRegion.isActive()),
			GetterUtil.getString(
				region.getName(), serviceBuilderRegion.getName()),
			GetterUtil.getDouble(
				region.getPosition(), serviceBuilderRegion.getPosition()),
			GetterUtil.getString(
				region.getRegionCode(), serviceBuilderRegion.getRegionCode()));

		return _toRegion(serviceBuilderRegion);
	}

	@Override
	public Region patchRegionByExternalReferenceCode(
			String externalReferenceCode, Region region)
		throws Exception {

		com.liferay.portal.kernel.model.Region serviceBuilderRegion =
			_regionService.getRegionByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		return patchRegion(serviceBuilderRegion.getRegionId(), region);
	}

	@Override
	public Region postCountryRegion(Long countryId, Region region)
		throws Exception {

		_setTitleMap(region);

		com.liferay.portal.kernel.model.Region serviceBuilderRegion =
			_regionService.addRegion(
				GetterUtil.getString(region.getExternalReferenceCode()),
				countryId, GetterUtil.get(region.getActive(), true),
				region.getName(), GetterUtil.getDouble(region.getPosition()),
				region.getRegionCode(), _getServiceContext());

		_regionLocalService.updateRegionLocalizations(
			serviceBuilderRegion, region.getTitle_i18n());

		return _toRegion(serviceBuilderRegion);
	}

	@Override
	public Region putRegion(Long regionId, Region region) throws Exception {
		_setTitleMap(region);

		com.liferay.portal.kernel.model.Region serviceBuilderRegion =
			_regionService.updateRegion(
				null, regionId, GetterUtil.get(region.getActive(), true),
				region.getName(), GetterUtil.getDouble(region.getPosition()),
				region.getRegionCode());

		_regionLocalService.updateRegionLocalizations(
			serviceBuilderRegion, region.getTitle_i18n());

		return _toRegion(serviceBuilderRegion);
	}

	@Override
	public Region putRegionByExternalReferenceCode(
			String externalReferenceCode, Region region)
		throws Exception {

		com.liferay.portal.kernel.model.Region serviceBuilderRegion =
			_regionLocalService.fetchRegionByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (serviceBuilderRegion == null) {
			region.setExternalReferenceCode(() -> externalReferenceCode);

			return postCountryRegion(
				GetterUtil.getLong(region.getCountryId()), region);
		}

		return putRegion(serviceBuilderRegion.getRegionId(), region);
	}

	private ServiceContext _getServiceContext() throws Exception {
		if (contextHttpServletRequest != null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				com.liferay.portal.kernel.model.Region.class.getName(),
				contextHttpServletRequest);

			if (serviceContext.getUserId() != 0) {
				return serviceContext;
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(contextCompany.getCompanyId());
		serviceContext.setUserId(contextUser.getUserId());

		return serviceContext;
	}

	private void _setTitleMap(Region region) {
		if (region.getTitle_i18n() == null) {
			Map<String, String> titleMap = new HashMap<>();

			for (Locale locale : _language.getAvailableLocales()) {
				titleMap.put(_language.getLanguageId(locale), null);
			}

			region.setTitle_i18n(() -> titleMap);
		}
	}

	private OrderByComparator<com.liferay.portal.kernel.model.Region>
		_toOrderByComparator(Sort[] sorts) {

		List<Object> objects = new ArrayList<>();

		if (ArrayUtil.isEmpty(sorts)) {
			objects.add(RegionTable.INSTANCE.regionId.getName());
			objects.add(true);
		}
		else {
			for (Sort sort : sorts) {
				objects.add(sort.getFieldName());
				objects.add(!sort.isReverse());
			}
		}

		return OrderByComparatorFactoryUtil.create(
			RegionTable.INSTANCE.getTableName(),
			objects.toArray(new Object[0]));
	}

	private Region _toRegion(
			com.liferay.portal.kernel.model.Region serviceBuilderRegion)
		throws Exception {

		return _regionResourceDTOConverter.toDTO(serviceBuilderRegion);
	}

	private static final EntityModel _entityModel = new RegionEntityModel();

	@Reference
	private CountryService _countryService;

	@Reference
	private Language _language;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference(target = DTOConverterConstants.REGION_RESOURCE_DTO_CONVERTER)
	private DTOConverter<com.liferay.portal.kernel.model.Region, Region>
		_regionResourceDTOConverter;

	@Reference
	private RegionService _regionService;

}