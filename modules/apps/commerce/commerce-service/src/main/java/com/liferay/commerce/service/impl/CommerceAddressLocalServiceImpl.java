/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.commerce.model.impl.CommerceAddressImpl;
import com.liferay.commerce.product.model.CommerceChannelRelTable;
import com.liferay.commerce.service.base.CommerceAddressLocalServiceBaseImpl;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.AddressTable;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alec Sloan
 */
@Component(
	property = {
		"liferay.service=false",
		"model.class.name=com.liferay.commerce.model.CommerceAddress"
	},
	service = AopService.class
)
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

		_validate(name, street1, city, zip, countryId, type);

		User user = _userLocalService.getUser(serviceContext.getUserId());

		return CommerceAddressImpl.fromAddress(
			_addressLocalService.addAddress(
				externalReferenceCode, user.getUserId(), className, classPK,
				name, description, street1, street2, street3, city, zip,
				regionId, countryId, CommerceAddressImpl.toAddressTypeId(type),
				false, false, phoneNumber, serviceContext));
	}

	@Override
	public CommerceAddress copyCommerceAddress(
			long sourceCommerceAddressId, String className, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceAddress sourceCommerceAddress = getCommerceAddress(
			sourceCommerceAddressId);

		CommerceAddress targetCommerceAddress =
			commerceAddressLocalService.addCommerceAddress(
				className, classPK, sourceCommerceAddress.getName(),
				sourceCommerceAddress.getDescription(),
				sourceCommerceAddress.getStreet1(),
				sourceCommerceAddress.getStreet2(),
				sourceCommerceAddress.getStreet3(),
				sourceCommerceAddress.getCity(), sourceCommerceAddress.getZip(),
				sourceCommerceAddress.getRegionId(),
				sourceCommerceAddress.getCountryId(),
				sourceCommerceAddress.getPhoneNumber(), false, false,
				serviceContext);

		return CommerceAddressImpl.fromAddress(
			_addressLocalService.getAddress(
				targetCommerceAddress.getCommerceAddressId()));
	}

	@Override
	public CommerceAddress createCommerceAddress(long commerceAddressId) {
		CommerceAddress commerceAddress = new CommerceAddressImpl();

		commerceAddress.setNew(true);
		commerceAddress.setPrimaryKey(commerceAddressId);
		commerceAddress.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceAddress;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceAddress deleteCommerceAddress(
			CommerceAddress commerceAddress)
		throws PortalException {

		// Commerce address

		_addressLocalService.deleteAddress(
			commerceAddress.getCommerceAddressId());

		return commerceAddress;
	}

	@Override
	public void deleteCommerceAddresses(String className, long classPK)
		throws PortalException {

		_addressLocalService.deleteAddresses(
			CompanyThreadLocal.getCompanyId(), className, classPK);
	}

	@Override
	public void deleteCountryCommerceAddresses(long countryId)
		throws PortalException {

		_addressLocalService.deleteCountryAddresses(countryId);
	}

	@Override
	public void deleteRegionCommerceAddresses(long regionId)
		throws PortalException {

		_addressLocalService.deleteRegionAddresses(regionId);
	}

	@Override
	public CommerceAddress fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return CommerceAddressImpl.fromAddress(
			_addressLocalService.fetchAddressByExternalReferenceCode(
				externalReferenceCode, companyId));
	}

	@Override
	public CommerceAddress fetchCommerceAddress(long commerceAddressId) {
		return CommerceAddressImpl.fromAddress(
			_addressLocalService.fetchAddress(commerceAddressId));
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAddress geolocateCommerceAddress(long commerceAddressId)
		throws PortalException {

		Address address = _addressLocalService.getAddress(commerceAddressId);

		double[] coordinates = _commerceGeocoder.getCoordinates(
			address.getStreet1(), address.getCity(), address.getZip(),
			address.getRegion(), address.getCountry());

		address.setLatitude(coordinates[0]);
		address.setLongitude(coordinates[1]);

		return CommerceAddressImpl.fromAddress(
			_addressLocalService.updateAddress(address));
	}

	@Override
	public List<CommerceAddress> getBillingAndShippingCommerceAddresses(
		long companyId, String className, long classPK) {

		return TransformUtil.transform(
			_addressLocalService.getListTypeAddresses(
				companyId, className, classPK,
				new long[] {
					CommerceAddressImpl.toAddressTypeId(
						CommerceAddressConstants.
							ADDRESS_TYPE_BILLING_AND_SHIPPING)
				}),
			CommerceAddressImpl::fromAddress);
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
		long channelId, String className, long classPK, int start, int end) {

		return TransformUtil.transform(
			_addressLocalService.dslQuery(
				_getGroupByStep(
					DSLQueryFactoryUtil.selectDistinct(AddressTable.INSTANCE),
					AddressTable.INSTANCE.listTypeId.eq(
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.ADDRESS_TYPE_BILLING)
					).or(
						AddressTable.INSTANCE.listTypeId.eq(
							CommerceAddressImpl.toAddressTypeId(
								CommerceAddressConstants.
									ADDRESS_TYPE_BILLING_AND_SHIPPING))
					),
					channelId, className, classPK, true, false
				).limit(
					start, end
				)),
			CommerceAddressImpl::fromAddress);
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		BaseModelSearchResult<Address> addressBaseModelSearchResult =
			_addressLocalService.searchAddresses(
				companyId, className, classPK, keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"listTypeIds",
					new long[] {
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.ADDRESS_TYPE_BILLING),
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.
								ADDRESS_TYPE_BILLING_AND_SHIPPING)
					}
				).build(),
				start, end, sort);

		return TransformUtil.transform(
			addressBaseModelSearchResult.getBaseModels(),
			CommerceAddressImpl::fromAddress);
	}

	@Override
	public int getBillingCommerceAddressesCount(
		long channelId, String className, long classPK, int start, int end) {

		return _addressLocalService.dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(AddressTable.INSTANCE),
				AddressTable.INSTANCE.listTypeId.eq(
					CommerceAddressImpl.toAddressTypeId(
						CommerceAddressConstants.ADDRESS_TYPE_BILLING)
				).or(
					AddressTable.INSTANCE.listTypeId.eq(
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.
								ADDRESS_TYPE_BILLING_AND_SHIPPING))
				),
				channelId, className, classPK, true, false
			).limit(
				start, end
			));
	}

	@Override
	public int getBillingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		BaseModelSearchResult<Address> addressBaseModelSearchResult =
			_addressLocalService.searchAddresses(
				companyId, className, classPK, keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"listTypeIds",
					new long[] {
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.ADDRESS_TYPE_BILLING),
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.
								ADDRESS_TYPE_BILLING_AND_SHIPPING)
					}
				).build(),
				-1, -1, null);

		return addressBaseModelSearchResult.getLength();
	}

	@Override
	public CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException {

		return CommerceAddressImpl.fromAddress(
			_addressLocalService.getAddress(commerceAddressId));
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

		return getCommerceAddressesByCompanyId(
			group.getCompanyId(), className, classPK);
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

		return getCommerceAddressesByCompanyId(
			group.getCompanyId(), className, classPK, start, end,
			orderByComparator);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return getCommerceAddressesByCompanyId(
			CompanyThreadLocal.getCompanyId(), className, classPK, start, end,
			orderByComparator);
	}

	@Override
	public List<CommerceAddress> getCommerceAddressesByCompanyId(
		long companyId, String className, long classPK) {

		return TransformUtil.transform(
			_addressLocalService.getAddresses(companyId, className, classPK),
			CommerceAddressImpl::fromAddress);
	}

	@Override
	public List<CommerceAddress> getCommerceAddressesByCompanyId(
		long companyId, String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return TransformUtil.transform(
			_addressLocalService.getAddresses(
				companyId, className, classPK, start, end,
				_getAddressOrderByComparator(orderByComparator)),
			CommerceAddressImpl::fromAddress);
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

		return getCommerceAddressesCountByCompanyId(
			group.getCompanyId(), className, classPK);
	}

	@Override
	public int getCommerceAddressesCount(String className, long classPK) {
		return getCommerceAddressesCountByCompanyId(
			CompanyThreadLocal.getCompanyId(), className, classPK);
	}

	@Override
	public int getCommerceAddressesCountByCompanyId(
		long companyId, String className, long classPK) {

		return _addressLocalService.getAddressesCount(
			companyId, className, classPK);
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
		long channelId, String className, long classPK, int start, int end) {

		return TransformUtil.transform(
			_addressLocalService.dslQuery(
				_getGroupByStep(
					DSLQueryFactoryUtil.selectDistinct(AddressTable.INSTANCE),
					AddressTable.INSTANCE.listTypeId.eq(
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.ADDRESS_TYPE_SHIPPING)
					).or(
						AddressTable.INSTANCE.listTypeId.eq(
							CommerceAddressImpl.toAddressTypeId(
								CommerceAddressConstants.
									ADDRESS_TYPE_BILLING_AND_SHIPPING))
					),
					channelId, className, classPK, false, true
				).limit(
					start, end
				)),
			CommerceAddressImpl::fromAddress);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		BaseModelSearchResult<Address> addressBaseModelSearchResult =
			_addressLocalService.searchAddresses(
				companyId, className, classPK, keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"listTypeIds",
					new long[] {
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.
								ADDRESS_TYPE_BILLING_AND_SHIPPING),
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.ADDRESS_TYPE_SHIPPING)
					}
				).build(),
				start, end, sort);

		return TransformUtil.transform(
			addressBaseModelSearchResult.getBaseModels(),
			CommerceAddressImpl::fromAddress);
	}

	@Override
	public int getShippingCommerceAddressesCount(
		long channelId, String className, long classPK, int start, int end) {

		return _addressLocalService.dslQueryCount(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(AddressTable.INSTANCE),
				AddressTable.INSTANCE.listTypeId.eq(
					CommerceAddressImpl.toAddressTypeId(
						CommerceAddressConstants.ADDRESS_TYPE_SHIPPING)
				).or(
					AddressTable.INSTANCE.listTypeId.eq(
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.
								ADDRESS_TYPE_BILLING_AND_SHIPPING))
				),
				channelId, className, classPK, false, true
			).limit(
				start, end
			));
	}

	@Override
	public int getShippingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		BaseModelSearchResult<Address> addressBaseModelSearchResult =
			_addressLocalService.searchAddresses(
				companyId, className, classPK, keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"listTypeIds",
					new long[] {
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.
								ADDRESS_TYPE_BILLING_AND_SHIPPING),
						CommerceAddressImpl.toAddressTypeId(
							CommerceAddressConstants.ADDRESS_TYPE_SHIPPING)
					}
				).build(),
				-1, -1, null);

		return addressBaseModelSearchResult.getLength();
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

		BaseModelSearchResult<Address> addressBaseModelSearchResult =
			_addressLocalService.searchAddresses(
				companyId, className, classPK, keywords, new LinkedHashMap<>(),
				start, end, sort);

		return new BaseModelSearchResult<>(
			TransformUtil.transform(
				addressBaseModelSearchResult.getBaseModels(),
				CommerceAddressImpl::fromAddress),
			addressBaseModelSearchResult.getLength());
	}

	@Override
	public BaseModelSearchResult<CommerceAddress> searchCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		BaseModelSearchResult<Address> addressBaseModelSearchResult =
			_addressLocalService.searchAddresses(
				companyId, className, classPK, keywords, new LinkedHashMap<>(),
				start, end, sort);

		return new BaseModelSearchResult<>(
			TransformUtil.transform(
				addressBaseModelSearchResult.getBaseModels(),
				CommerceAddressImpl::fromAddress),
			addressBaseModelSearchResult.getLength());
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

		Address address = _addressLocalService.getAddress(commerceAddressId);

		_validate(name, street1, city, zip, countryId, type);

		address = _addressLocalService.updateAddress(
			commerceAddressId, name, description, street1, street2, street3,
			city, zip, regionId, countryId,
			CommerceAddressImpl.toAddressTypeId(type), address.isMailing(),
			address.isPrimary(), phoneNumber);

		return CommerceAddressImpl.fromAddress(address);
	}

	private OrderByComparator<Address> _getAddressOrderByComparator(
		OrderByComparator<CommerceAddress> orderByComparator) {

		if (orderByComparator == null) {
			return null;
		}

		return new OrderByComparator<Address>() {

			@Override
			public int compare(Address address1, Address address2) {
				return orderByComparator.compare(
					CommerceAddressImpl.fromAddress(address1),
					CommerceAddressImpl.fromAddress(address2));
			}

		};
	}

	private GroupByStep _getGroupByStep(
		FromStep fromStep, Predicate listTypeFilterPredicate,
		long commerceChannelId, String className, long classPK,
		boolean billingAllowed, boolean shippingAllowed) {

		JoinStep joinStep = fromStep.from(
			AddressTable.INSTANCE
		).leftJoinOn(
			CountryTable.INSTANCE,
			AddressTable.INSTANCE.countryId.eq(CountryTable.INSTANCE.countryId)
		).leftJoinOn(
			CommerceChannelRelTable.INSTANCE,
			CountryTable.INSTANCE.countryId.eq(
				CommerceChannelRelTable.INSTANCE.classPK)
		);

		return joinStep.where(
			() -> {
				Predicate predicate = CountryTable.INSTANCE.active.eq(true);

				predicate = predicate.and(
					AddressTable.INSTANCE.classNameId.eq(
						_classNameLocalService.getClassNameId(className)
					).and(
						AddressTable.INSTANCE.classPK.eq(classPK)
					));

				predicate = predicate.and(
					listTypeFilterPredicate.withParentheses());

				Predicate groupFilterPredicate =
					CountryTable.INSTANCE.groupFilterEnabled.eq(false);

				Predicate channelFilterPredicate =
					CountryTable.INSTANCE.groupFilterEnabled.eq(true);

				channelFilterPredicate = channelFilterPredicate.and(
					CommerceChannelRelTable.INSTANCE.classNameId.eq(
						_classNameLocalService.getClassNameId(Country.class)));
				channelFilterPredicate = channelFilterPredicate.and(
					CommerceChannelRelTable.INSTANCE.commerceChannelId.eq(
						commerceChannelId));

				groupFilterPredicate = groupFilterPredicate.or(
					channelFilterPredicate.withParentheses());

				predicate = predicate.and(
					groupFilterPredicate.withParentheses());

				if (billingAllowed) {
					predicate = predicate.and(
						CountryTable.INSTANCE.billingAllowed.eq(true));
				}

				if (shippingAllowed) {
					predicate = predicate.and(
						CountryTable.INSTANCE.shippingAllowed.eq(true));
				}

				return predicate;
			});
	}

	private void _validate(
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

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceGeocoder _commerceGeocoder;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}