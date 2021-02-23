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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.exception.CommerceAddressCityException;
import com.liferay.commerce.exception.CommerceAddressCountryException;
import com.liferay.commerce.exception.CommerceAddressNameException;
import com.liferay.commerce.exception.CommerceAddressStreetException;
import com.liferay.commerce.exception.CommerceAddressTypeException;
import com.liferay.commerce.exception.CommerceAddressZipException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceGeocoder;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.search.facet.NegatableMultiValueFacet;
import com.liferay.commerce.service.base.CommerceAddressLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alec Sloan
 */
public class CommerceAddressLocalServiceImpl
	extends CommerceAddressLocalServiceBaseImpl {

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	@Override
	public CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			ServiceContext serviceContext)
		throws PortalException {

		int type = CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING;

		if (defaultBilling && !defaultShipping) {
			type = CommerceAddressConstants.ADDRESS_TYPE_BILLING;
		}
		else if (!defaultBilling && defaultShipping) {
			type = CommerceAddressConstants.ADDRESS_TYPE_SHIPPING;
		}

		return commerceAddressLocalService.addCommerceAddress(
			className, classPK, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, type, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type, ServiceContext serviceContext)
		throws PortalException {

		return commerceAddressLocalService.addCommerceAddress(
			null, className, classPK, name, description, street1, street2,
			street3, city, zip, regionId, countryId, phoneNumber, type,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAddress addCommerceAddress(
			String externalReferenceCode, String className, long classPK,
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, String phoneNumber, int type,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		long companyId = user.getCompanyId();

		validate(name, street1, city, zip, countryId, type);

		long commerceAddressId = counterLocalService.increment();

		CommerceAddress commerceAddress = commerceAddressPersistence.create(
			commerceAddressId);

		commerceAddress.setExternalReferenceCode(externalReferenceCode);
		commerceAddress.setCompanyId(companyId);
		commerceAddress.setUserId(user.getUserId());
		commerceAddress.setUserName(user.getFullName());
		commerceAddress.setClassName(className);
		commerceAddress.setClassPK(classPK);
		commerceAddress.setName(name);
		commerceAddress.setDescription(description);
		commerceAddress.setStreet1(street1);
		commerceAddress.setStreet2(street2);
		commerceAddress.setStreet3(street3);
		commerceAddress.setCity(city);
		commerceAddress.setZip(zip);
		commerceAddress.setRegionId(regionId);
		commerceAddress.setCountryId(countryId);
		commerceAddress.setPhoneNumber(phoneNumber);
		commerceAddress.setType(type);

		return commerceAddressPersistence.update(commerceAddress);
	}

	@Override
	public CommerceAddress copyCommerceAddress(
			long commerceAddressId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressPersistence.findByPrimaryKey(commerceAddressId);

		CommerceAddress copiedCommerceAddress =
			commerceAddressLocalService.addCommerceAddress(
				className, classPK, commerceAddress.getName(),
				commerceAddress.getDescription(), commerceAddress.getStreet1(),
				commerceAddress.getStreet2(), commerceAddress.getStreet3(),
				commerceAddress.getCity(), commerceAddress.getZip(),
				commerceAddress.getRegionId(), commerceAddress.getCountryId(),
				commerceAddress.getPhoneNumber(), false, false, serviceContext);

		if (Validator.isNotNull(commerceAddress.getExternalReferenceCode())) {
			copiedCommerceAddress.setExternalReferenceCode(
				commerceAddress.getExternalReferenceCode());

			copiedCommerceAddress =
				commerceAddressLocalService.updateCommerceAddress(
					copiedCommerceAddress);
		}

		return copiedCommerceAddress;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceAddress deleteCommerceAddress(
			CommerceAddress commerceAddress)
		throws PortalException {

		// Commerce address

		commerceAddressPersistence.remove(commerceAddress);

		// Commerce orders

		List<CommerceOrder> commerceOrders =
			commerceOrderLocalService.getCommerceOrdersByBillingAddress(
				commerceAddress.getCommerceAddressId());

		removeCommerceOrderAddresses(
			commerceOrders, commerceAddress.getCommerceAddressId());

		commerceOrders =
			commerceOrderLocalService.getCommerceOrdersByShippingAddress(
				commerceAddress.getCommerceAddressId());

		removeCommerceOrderAddresses(
			commerceOrders, commerceAddress.getCommerceAddressId());

		return commerceAddress;
	}

	@Override
	public CommerceAddress deleteCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressPersistence.findByPrimaryKey(commerceAddressId);

		return commerceAddressLocalService.deleteCommerceAddress(
			commerceAddress);
	}

	@Override
	public void deleteCommerceAddresses(String className, long classPK)
		throws PortalException {

		List<CommerceAddress> commerceAddresses =
			commerceAddressPersistence.findByC_C(
				classNameLocalService.getClassNameId(className), classPK);

		for (CommerceAddress commerceAddress : commerceAddresses) {
			commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
		}
	}

	@Override
	public void deleteCountryCommerceAddresses(long countryId)
		throws PortalException {

		List<CommerceAddress> commerceAddresses =
			commerceAddressPersistence.findByCountryId(countryId);

		for (CommerceAddress commerceAddress : commerceAddresses) {
			commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
		}
	}

	@Override
	public void deleteRegionCommerceAddresses(long regionId)
		throws PortalException {

		List<CommerceAddress> commerceAddresses =
			commerceAddressPersistence.findByRegionId(regionId);

		for (CommerceAddress commerceAddress : commerceAddresses) {
			commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
		}
	}

	@Override
	public CommerceAddress fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return commerceAddressPersistence.fetchByC_ERC(
			companyId, externalReferenceCode, true);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAddress geolocateCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressPersistence.findByPrimaryKey(commerceAddressId);

		double[] coordinates = _commerceGeocoder.getCoordinates(
			commerceAddress.getStreet1(), commerceAddress.getCity(),
			commerceAddress.getZip(), commerceAddress.getRegion(),
			commerceAddress.getCountry());

		commerceAddress.setLatitude(coordinates[0]);
		commerceAddress.setLongitude(coordinates[1]);

		return commerceAddressPersistence.update(commerceAddress);
	}

	@Override
	public List<CommerceAddress> getBillingAndShippingCommerceAddresses(
		long companyId, String className, long classPK) {

		return commerceAddressPersistence.findByC_C_C_C(
			companyId, classNameLocalService.getClassNameId(className), classPK,
			CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING);
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		return commerceAddressLocalService.getBillingCommerceAddresses(
			companyId, className, classPK, null, -1, -1, null);
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			new int[] {
				CommerceAddressConstants.ADDRESS_TYPE_BILLING,
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING
			},
			companyId, className, classPK, keywords, start, end, sort);

		BaseModelSearchResult<CommerceAddress> billingAddresses =
			searchCommerceAddresses(searchContext);

		return billingAddresses.getBaseModels();
	}

	@Override
	public int getBillingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			new int[] {
				CommerceAddressConstants.ADDRESS_TYPE_BILLING,
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING
			},
			companyId, className, classPK, keywords, -1, -1, null);

		BaseModelSearchResult<CommerceAddress> billingAddresses =
			searchCommerceAddresses(searchContext);

		return billingAddresses.getLength();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public List<CommerceAddress> getCommerceAddresses(
		long groupId, String className, long classPK) {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return new ArrayList<>();
		}

		return commerceAddressPersistence.findByC_C_C(
			group.getCompanyId(),
			classNameLocalService.getClassNameId(className), classPK);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public List<CommerceAddress> getCommerceAddresses(
		long groupId, String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return new ArrayList<>();
		}

		return commerceAddressPersistence.findByC_C_C(
			group.getCompanyId(),
			classNameLocalService.getClassNameId(className), classPK, start,
			end, orderByComparator);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return commerceAddressPersistence.findByC_C(
			classNameLocalService.getClassNameId(className), classPK, start,
			end, orderByComparator);
	}

	@Override
	public List<CommerceAddress> getCommerceAddressesByCompanyId(
		long companyId, String className, long classPK) {

		return commerceAddressPersistence.findByC_C_C(
			companyId, classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public List<CommerceAddress> getCommerceAddressesByCompanyId(
		long companyId, String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return commerceAddressPersistence.findByC_C_C(
			companyId, classNameLocalService.getClassNameId(className), classPK,
			start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public int getCommerceAddressesCount(
		long groupId, String className, long classPK) {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return 0;
		}

		return commerceAddressPersistence.countByC_C_C(
			group.getCompanyId(),
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public int getCommerceAddressesCount(String className, long classPK) {
		return commerceAddressPersistence.countByC_C(
			classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public int getCommerceAddressesCountByCompanyId(
		long companyId, String className, long classPK) {

		return commerceAddressPersistence.countByC_C_C(
			companyId, classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		return commerceAddressLocalService.getShippingCommerceAddresses(
			companyId, className, classPK, null, -1, -1, null);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			new int[] {
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING,
				CommerceAddressConstants.ADDRESS_TYPE_SHIPPING
			},
			companyId, className, classPK, keywords, start, end, sort);

		BaseModelSearchResult<CommerceAddress> shippingAddresses =
			searchCommerceAddresses(searchContext);

		return shippingAddresses.getBaseModels();
	}

	@Override
	public int getShippingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			new int[] {
				CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING,
				CommerceAddressConstants.ADDRESS_TYPE_SHIPPING
			},
			companyId, className, classPK, keywords, -1, -1, null);

		BaseModelSearchResult<CommerceAddress> billingAddresses =
			searchCommerceAddresses(searchContext);

		return billingAddresses.getLength();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company. Don't need to pass groupId
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<CommerceAddress> searchCommerceAddresses(
			long companyId, long groupId, String className, long classPK,
			String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, className, classPK, keywords, start, end, sort);

		return searchCommerceAddresses(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceAddress> searchCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, className, classPK, keywords, start, end, sort);

		return searchCommerceAddresses(searchContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	@Override
	public CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			ServiceContext serviceContext)
		throws PortalException {

		int type = CommerceAddressConstants.ADDRESS_TYPE_BILLING_AND_SHIPPING;

		if (defaultBilling && !defaultShipping) {
			type = CommerceAddressConstants.ADDRESS_TYPE_BILLING;
		}
		else if (!defaultBilling && defaultShipping) {
			type = CommerceAddressConstants.ADDRESS_TYPE_SHIPPING;
		}

		return updateCommerceAddress(
			commerceAddressId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, type, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type, ServiceContext serviceContext)
		throws PortalException {

		// Commerce address

		CommerceAddress commerceAddress =
			commerceAddressPersistence.findByPrimaryKey(commerceAddressId);

		validate(name, street1, city, zip, countryId, type);

		commerceAddress.setName(name);
		commerceAddress.setDescription(description);
		commerceAddress.setStreet1(street1);
		commerceAddress.setStreet2(street2);
		commerceAddress.setStreet3(street3);
		commerceAddress.setCity(city);
		commerceAddress.setZip(zip);
		commerceAddress.setRegionId(regionId);
		commerceAddress.setCountryId(countryId);
		commerceAddress.setLatitude(0);
		commerceAddress.setLongitude(0);
		commerceAddress.setPhoneNumber(phoneNumber);
		commerceAddress.setType(type);

		commerceAddress = commerceAddressPersistence.update(commerceAddress);

		// Commerce orders

		List<CommerceOrder> commerceOrders =
			commerceOrderLocalService.getCommerceOrdersByShippingAddress(
				commerceAddressId);

		for (CommerceOrder commerceOrder : commerceOrders) {
			commerceOrderLocalService.resetCommerceOrderShipping(
				commerceOrder.getCommerceOrderId());
		}

		return commerceAddress;
	}

	protected SearchContext buildSearchContext(
		int[] addressTypes, long companyId, String className, long classPK,
		String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = buildSearchContext(
			companyId, className, classPK, keywords, start, end, sort);

		NegatableMultiValueFacet negatableMultiValueFacet =
			new NegatableMultiValueFacet(searchContext);

		negatableMultiValueFacet.setFieldName(Field.TYPE);

		searchContext.addFacet(negatableMultiValueFacet);

		negatableMultiValueFacet.setNegated(false);

		searchContext.setAttribute(
			negatableMultiValueFacet.getFieldId(),
			StringUtil.merge(addressTypes));

		return searchContext;
	}

	protected SearchContext buildSearchContext(
		long companyId, String className, long classPK, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.CLASS_NAME_ID,
				classNameLocalService.getClassNameId(className)
			).put(
				Field.CLASS_PK, classPK
			).put(
				Field.NAME, keywords
			).put(
				"city", keywords
			).put(
				"countryName", keywords
			).put(
				"regionName", keywords
			).put(
				"zip", keywords
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<CommerceAddress> getCommerceAddresses(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceAddress> commerceAddresses = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commerceAddressId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceAddress commerceAddress = fetchCommerceAddress(
				commerceAddressId);

			if (commerceAddress == null) {
				commerceAddresses = null;

				Indexer<CommerceAddress> indexer =
					IndexerRegistryUtil.getIndexer(CommerceAddress.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceAddresses != null) {
				commerceAddresses.add(commerceAddress);
			}
		}

		return commerceAddresses;
	}

	protected void removeCommerceOrderAddresses(
			List<CommerceOrder> commerceOrders, long commerceAddressId)
		throws PortalException {

		for (CommerceOrder commerceOrder : commerceOrders) {
			long billingAddressId = commerceOrder.getBillingAddressId();
			long shippingAddressId = commerceOrder.getShippingAddressId();

			long commerceShippingMethodId =
				commerceOrder.getCommerceShippingMethodId();
			String shippingOptionName = commerceOrder.getShippingOptionName();
			BigDecimal shippingPrice = commerceOrder.getShippingAmount();

			if (billingAddressId == commerceAddressId) {
				billingAddressId = 0;
			}

			if (shippingAddressId == commerceAddressId) {
				shippingAddressId = 0;

				commerceShippingMethodId = 0;
				shippingOptionName = null;
				shippingPrice = BigDecimal.ZERO;
			}

			commerceOrderLocalService.updateCommerceOrder(
				commerceOrder.getCommerceOrderId(), billingAddressId,
				shippingAddressId, commerceOrder.getCommercePaymentMethodKey(),
				commerceShippingMethodId, shippingOptionName,
				commerceOrder.getPurchaseOrderNumber(),
				commerceOrder.getSubtotal(), shippingPrice,
				commerceOrder.getTotal(), commerceOrder.getAdvanceStatus(),
				null);
		}
	}

	protected BaseModelSearchResult<CommerceAddress> searchCommerceAddresses(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceAddress> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceAddress.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceAddress> commerceAddresses = getCommerceAddresses(
				hits);

			if (commerceAddresses != null) {
				return new BaseModelSearchResult<>(
					commerceAddresses, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(
			String name, String street1, String city, String zip,
			long countryId, int type)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CommerceAddressNameException();
		}

		if (Validator.isNull(street1)) {
			throw new CommerceAddressStreetException();
		}

		if (Validator.isNull(city)) {
			throw new CommerceAddressCityException();
		}

		if (Validator.isNull(zip)) {
			throw new CommerceAddressZipException();
		}

		if (countryId <= 0) {
			throw new CommerceAddressCountryException();
		}

		if (!ArrayUtil.contains(CommerceAddressConstants.ADDRESS_TYPES, type)) {
			throw new CommerceAddressTypeException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.UID
	};

	@ServiceReference(type = CommerceGeocoder.class)
	private CommerceGeocoder _commerceGeocoder;

	@ServiceReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

}