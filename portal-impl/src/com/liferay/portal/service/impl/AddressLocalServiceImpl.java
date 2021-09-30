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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.AddressCityException;
import com.liferay.portal.kernel.exception.AddressStreetException;
import com.liferay.portal.kernel.exception.AddressZipException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.SystemEventConstants;
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
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CountryPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.AddressLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class AddressLocalServiceImpl extends AddressLocalServiceBaseImpl {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addAddress(String, long, String, long, String, String,
	 *             String, String, String, String, String, long, long, long,
	 *             boolean, boolean, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Address addAddress(
			long userId, String className, long classPK, String street1,
			String street2, String street3, String city, String zip,
			long regionId, long countryId, long typeId, boolean mailing,
			boolean primary, ServiceContext serviceContext)
		throws PortalException {

		return addressLocalService.addAddress(
			null, userId, className, classPK, null, null, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary,
			null, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Address addAddress(
			String externalReferenceCode, long userId, String className,
			long classPK, String name, String description, String street1,
			String street2, String street3, String city, String zip,
			long regionId, long countryId, long typeId, boolean mailing,
			boolean primary, String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);
		long classNameId = _classNameLocalService.getClassNameId(className);

		validate(
			0, user.getCompanyId(), classNameId, classPK, street1, city, zip,
			regionId, countryId, typeId, mailing, primary);

		long addressId = counterLocalService.increment();

		Address address = addressPersistence.create(addressId);

		address.setUuid(serviceContext.getUuid());
		address.setExternalReferenceCode(externalReferenceCode);
		address.setCompanyId(user.getCompanyId());
		address.setUserId(user.getUserId());
		address.setUserName(user.getFullName());
		address.setClassNameId(classNameId);
		address.setClassPK(classPK);
		address.setCountryId(countryId);
		address.setRegionId(regionId);
		address.setTypeId(typeId);
		address.setCity(city);
		address.setDescription(description);
		address.setMailing(mailing);
		address.setName(name);
		address.setPrimary(primary);
		address.setStreet1(street1);
		address.setStreet2(street2);
		address.setStreet3(street3);
		address.setZip(zip);

		address = addressPersistence.update(address);

		if (Validator.isNotNull(phoneNumber)) {
			_addAddressPhone(addressId, phoneNumber);
		}

		return address;
	}

	@Override
	public Address copyAddress(
			long addressId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		Address address = addressPersistence.findByPrimaryKey(addressId);

		return addressLocalService.addAddress(
			address.getExternalReferenceCode(), serviceContext.getUserId(),
			className, classPK, address.getName(), address.getDescription(),
			address.getStreet1(), address.getStreet2(), address.getStreet3(),
			address.getCity(), address.getZip(), address.getRegionId(),
			address.getCountryId(), address.getTypeId(), address.isMailing(),
			address.isPrimary(), address.getPhoneNumber(), serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public Address deleteAddress(Address address) {
		addressPersistence.remove(address);

		_phoneLocalService.deletePhones(
			address.getCompanyId(), address.getClassName(),
			address.getAddressId());

		return address;
	}

	@Override
	public Address deleteAddress(long addressId) throws PortalException {
		Address address = addressPersistence.findByPrimaryKey(addressId);

		return addressLocalService.deleteAddress(address);
	}

	@Override
	public void deleteAddresses(
		long companyId, String className, long classPK) {

		List<Address> addresses = addressPersistence.findByC_C_C(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK);

		for (Address address : addresses) {
			addressLocalService.deleteAddress(address);
		}
	}

	@Override
	public void deleteCountryAddresses(long countryId) {
		List<Address> addresses = addressPersistence.findByCountryId(countryId);

		for (Address address : addresses) {
			addressLocalService.deleteAddress(address);
		}
	}

	@Override
	public void deleteRegionAddresses(long regionId) {
		List<Address> addresses = addressPersistence.findByRegionId(regionId);

		for (Address address : addresses) {
			addressLocalService.deleteAddress(address);
		}
	}

	@Override
	public List<Address> getAddresses() {
		return addressPersistence.findAll();
	}

	@Override
	public List<Address> getAddresses(
		long companyId, String className, long classPK) {

		return addressPersistence.findByC_C_C(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public List<Address> getAddresses(
		long companyId, String className, long classPK, int start, int end,
		OrderByComparator<Address> orderByComparator) {

		return addressPersistence.findByC_C_C(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK, start, end, orderByComparator);
	}

	@Override
	public List<Address> getAddressesByTypeIds(
		long companyId, String className, long classPK, long[] typeIds) {

		return addressPersistence.findByC_C_C_T(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK, typeIds);
	}

	@Override
	public List<Address> getAddressesByTypeIds(
		long companyId, String className, long classPK, long[] typeIds,
		int start, int end, OrderByComparator<Address> orderByComparator) {

		return addressPersistence.findByC_C_C_T(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK, typeIds, start, end, orderByComparator);
	}

	@Override
	public int getAddressesCount(
		long companyId, String className, long classPK) {

		return addressPersistence.countByC_C_C(
			companyId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public BaseModelSearchResult<Address> searchAddresses(
			long companyId, String className, long classPK, String keywords,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, className, classPK, keywords, params, start, end, sort);

		return searchAddresses(searchContext);
	}

	@Override
	public Address updateAddress(
			long addressId, String street1, String street2, String street3,
			String city, String zip, long regionId, long countryId, long typeId,
			boolean mailing, boolean primary)
		throws PortalException {

		Address address = addressPersistence.findByPrimaryKey(addressId);

		return addressLocalService.updateAddress(
			addressId, address.getName(), address.getDescription(), street1,
			street2, street3, city, zip, regionId, countryId, typeId, mailing,
			primary, address.getPhoneNumber());
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Address updateAddress(
			long addressId, String name, String description, String street1,
			String street2, String street3, String city, String zip,
			long regionId, long countryId, long typeId, boolean mailing,
			boolean primary, String phoneNumber)
		throws PortalException {

		validate(
			addressId, 0, 0, 0, street1, city, zip, regionId, countryId, typeId,
			mailing, primary);

		Address address = addressPersistence.findByPrimaryKey(addressId);

		address.setCountryId(countryId);
		address.setRegionId(regionId);
		address.setTypeId(typeId);
		address.setCity(city);
		address.setDescription(description);
		address.setMailing(mailing);
		address.setName(name);
		address.setPrimary(primary);
		address.setStreet1(street1);
		address.setStreet2(street2);
		address.setStreet3(street3);
		address.setZip(zip);

		address = addressPersistence.update(address);

		if (Validator.isNotNull(phoneNumber)) {
			List<Phone> phones = _phoneLocalService.getPhones(
				address.getCompanyId(), Address.class.getName(), addressId);

			if (ListUtil.isEmpty(phones)) {
				_addAddressPhone(addressId, phoneNumber);
			}
			else {
				Phone phone = phones.get(0);

				phone.setNumber(phoneNumber);

				_phoneLocalService.updatePhone(phone);
			}
		}

		return address;
	}

	protected SearchContext buildSearchContext(
		long companyId, String className, long classPK, String keywords,
		LinkedHashMap<String, Object> params, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.CLASS_NAME_ID,
				_classNameLocalService.getClassNameId(className)
			).put(
				Field.CLASS_PK, classPK
			).put(
				Field.NAME, keywords
			).put(
				"city", keywords
			).put(
				"countryName", keywords
			).put(
				"params", params
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

	protected List<Address> getAddresses(Hits hits) throws PortalException {
		List<Document> documents = hits.toList();

		List<Address> addresses = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long addressId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			Address address = fetchAddress(addressId);

			if (address == null) {
				addresses = null;

				Indexer<Address> indexer = IndexerRegistryUtil.getIndexer(
					Address.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (addresses != null) {
				addresses.add(address);
			}
		}

		return addresses;
	}

	protected BaseModelSearchResult<Address> searchAddresses(
			SearchContext searchContext)
		throws PortalException {

		Indexer<Address> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Address.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<Address> addresses = getAddresses(hits);

			if (addresses != null) {
				return new BaseModelSearchResult<>(addresses, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(
		long addressId, long companyId, long classNameId, long classPK,
		boolean mailing, boolean primary) {

		// Check to make sure there isn't another address with the same company
		// id, class name, and class pk that also has mailing set to true

		if (mailing) {
			List<Address> addresses = addressPersistence.findByC_C_C_M(
				companyId, classNameId, classPK, mailing);

			for (Address address : addresses) {
				if ((addressId <= 0) || (address.getAddressId() != addressId)) {
					address.setMailing(false);

					addressPersistence.update(address);
				}
			}
		}

		// Check to make sure there isn't another address with the same company
		// id, class name, and class pk that also has primary set to true

		if (primary) {
			List<Address> addresses = addressPersistence.findByC_C_C_P(
				companyId, classNameId, classPK, primary);

			for (Address address : addresses) {
				if ((addressId <= 0) || (address.getAddressId() != addressId)) {
					address.setPrimary(false);

					addressPersistence.update(address);
				}
			}
		}
	}

	protected void validate(
			long addressId, long companyId, long classNameId, long classPK,
			String street1, String city, String zip, long regionId,
			long countryId, long typeId, boolean mailing, boolean primary)
		throws PortalException {

		if (Validator.isNull(street1)) {
			throw new AddressStreetException();
		}
		else if (Validator.isNull(city)) {
			throw new AddressCityException();
		}
		else if (Validator.isNull(zip)) {
			Country country = _countryPersistence.fetchByPrimaryKey(countryId);

			if ((country != null) && country.isZipRequired()) {
				throw new AddressZipException();
			}
		}

		if (addressId > 0) {
			Address address = addressPersistence.findByPrimaryKey(addressId);

			companyId = address.getCompanyId();
			classNameId = address.getClassNameId();
			classPK = address.getClassPK();
		}

		if ((classNameId == _classNameLocalService.getClassNameId(
				Company.class)) ||
			(classNameId == _classNameLocalService.getClassNameId(
				Contact.class)) ||
			(classNameId == _classNameLocalService.getClassNameId(
				Organization.class))) {

			_listTypeLocalService.validate(
				typeId, classNameId, ListTypeConstants.ADDRESS);
		}

		validate(addressId, companyId, classNameId, classPK, mailing, primary);
	}

	private void _addAddressPhone(long addressId, String phoneNumber)
		throws PortalException {

		ListType listType = _listTypeLocalService.getListType(
			"phone-number", ListTypeConstants.ADDRESS_PHONE);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		_phoneLocalService.addPhone(
			serviceContext.getUserId(), Address.class.getName(), addressId,
			phoneNumber, null, listType.getListTypeId(), false, serviceContext);
	}

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	@BeanReference(type = CountryPersistence.class)
	private CountryPersistence _countryPersistence;

	@BeanReference(type = ListTypeLocalService.class)
	private ListTypeLocalService _listTypeLocalService;

	@BeanReference(type = PhoneLocalService.class)
	private PhoneLocalService _phoneLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}