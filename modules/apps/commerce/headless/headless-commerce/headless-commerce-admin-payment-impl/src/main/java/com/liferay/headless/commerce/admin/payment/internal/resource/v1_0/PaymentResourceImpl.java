/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.payment.internal.resource.v1_0;

import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.constants.CommercePaymentEntryConstants;
import com.liferay.commerce.payment.exception.NoSuchPaymentEntryException;
import com.liferay.commerce.payment.gateway.CommercePaymentGateway;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryService;
import com.liferay.headless.commerce.admin.payment.dto.v1_0.Payment;
import com.liferay.headless.commerce.admin.payment.internal.odata.entity.v1_0.PaymentEntityModel;
import com.liferay.headless.commerce.admin.payment.resource.v1_0.PaymentResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/payment.properties",
	scope = ServiceScope.PROTOTYPE, service = PaymentResource.class
)
public class PaymentResourceImpl extends BasePaymentResourceImpl {

	@Override
	public Response deletePayment(Long id) throws Exception {
		_commercePaymentEntryService.deleteCommercePaymentEntry(id);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response deletePaymentByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommercePaymentEntry commercePaymentEntry =
			_commercePaymentEntryService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePaymentEntry == null) {
			throw new NoSuchPaymentEntryException(
				"Unable to find payment with external reference code " +
					externalReferenceCode);
		}

		_commercePaymentEntryService.deleteCommercePaymentEntry(
			commercePaymentEntry.getCommercePaymentEntryId());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Payment getPayment(Long id) throws Exception {
		return _toPayment(
			GetterUtil.getLong(id), contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo,
			_getActions(
				_commercePaymentEntryService.getCommercePaymentEntry(
					GetterUtil.getLong(id))));
	}

	@Override
	public Payment getPaymentByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommercePaymentEntry commercePaymentEntry =
			_commercePaymentEntryService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePaymentEntry == null) {
			throw new NoSuchPaymentEntryException(
				"Unable to find payment with external reference code " +
					externalReferenceCode);
		}

		return _toPayment(
			commercePaymentEntry.getCommercePaymentEntryId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo, _getActions(commercePaymentEntry));
	}

	@Override
	public Page<Payment> getPaymentsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			null, booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			CommercePaymentEntry.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toPayment(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)),
				contextAcceptLanguage.getPreferredLocale(),
				contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
				contextUriInfo,
				_getActions(
					_commercePaymentEntryService.getCommercePaymentEntry(
						GetterUtil.getLong(
							GetterUtil.getLong(
								document.get(Field.ENTRY_CLASS_PK)))))));
	}

	@Override
	public Payment patchPayment(Long id, Payment payment) throws Exception {
		CommercePaymentEntry commercePaymentEntry = _updatePayment(
			_commercePaymentEntryService.getCommercePaymentEntry(id), payment);

		return _toPayment(
			commercePaymentEntry.getCommercePaymentEntryId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo, _getActions(commercePaymentEntry));
	}

	@Override
	public Payment patchPaymentByExternalReferenceCode(
			String externalReferenceCode, Payment payment)
		throws Exception {

		CommercePaymentEntry commercePaymentEntry =
			_commercePaymentEntryService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePaymentEntry == null) {
			throw new NoSuchPaymentEntryException(
				"Unable to find payment with external reference code " +
					externalReferenceCode);
		}

		commercePaymentEntry = _updatePayment(commercePaymentEntry, payment);

		return _toPayment(
			commercePaymentEntry.getCommercePaymentEntryId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo, _getActions(commercePaymentEntry));
	}

	@Override
	public Payment postPayment(Payment payment) throws Exception {
		CommercePaymentEntry commercePaymentEntry = _addOrUpdatePayment(
			payment);

		return _toPayment(
			commercePaymentEntry.getCommercePaymentEntryId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo, _getActions(commercePaymentEntry));
	}

	@Override
	public Payment postPaymentByExternalReferenceCodeRefund(
			String externalReferenceCode)
		throws Exception {

		CommercePaymentEntry commercePaymentEntry =
			_commercePaymentEntryService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePaymentEntry == null) {
			throw new NoSuchPaymentEntryException(
				"Unable to find payment with external reference code " +
					externalReferenceCode);
		}

		if (commercePaymentEntry.getType() !=
				CommercePaymentEntryConstants.TYPE_REFUND) {

			throw new UnsupportedOperationException();
		}

		commercePaymentEntry = _commercePaymentGateway.refund(
			contextHttpServletRequest, commercePaymentEntry);

		return _toPayment(
			commercePaymentEntry.getCommercePaymentEntryId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo, _getActions(commercePaymentEntry));
	}

	@Override
	public Payment postPaymentRefund(Long id) throws Exception {
		CommercePaymentEntry commercePaymentEntry =
			_commercePaymentEntryService.getCommercePaymentEntry(id);

		if (commercePaymentEntry.getType() !=
				CommercePaymentEntryConstants.TYPE_REFUND) {

			throw new UnsupportedOperationException();
		}

		commercePaymentEntry = _commercePaymentGateway.refund(
			contextHttpServletRequest, commercePaymentEntry);

		return _toPayment(
			commercePaymentEntry.getCommercePaymentEntryId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo, _getActions(commercePaymentEntry));
	}

	private Map<String, String> _addAction(
			String actionId, long commercePaymentId, UriInfo uriInfo,
			String methodName, Class<?> clazz)
		throws NoSuchMethodException, PortalException {

		if (!_commercePaymentEntryModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), commercePaymentId,
				actionId)) {

			return null;
		}

		return HashMapBuilder.put(
			"href",
			() -> {
				UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();

				return uriBuilder.path(
					_getVersion(uriInfo)
				).path(
					clazz.getSuperclass(), methodName
				).toTemplate();
			}
		).put(
			"method", _getHttpMethodName(clazz, _getMethod(clazz, methodName))
		).build();
	}

	private CommercePaymentEntry _addOrUpdatePayment(Payment payment)
		throws Exception {

		return _commercePaymentEntryService.addOrUpdateCommercePaymentEntry(
			payment.getExternalReferenceCode(),
			GetterUtil.getLong(
				_classNameLocalService.getClassNameId(
					payment.getRelatedItemName())),
			GetterUtil.getLong(payment.getRelatedItemId()),
			GetterUtil.getLong(payment.getChannelId()), payment.getAmount(),
			payment.getCallbackURL(), payment.getCancelURL(),
			payment.getCurrencyCode(), payment.getErrorMessages(),
			payment.getLanguageId(), payment.getComment(),
			payment.getPaymentIntegrationKey(),
			GetterUtil.getInteger(payment.getPaymentStatus()),
			GetterUtil.getInteger(
				payment.getPaymentStatus(),
				CommerceOrderPaymentConstants.STATUS_PENDING),
			payment.getReasonKey(), payment.getRedirectURL(),
			payment.getTransactionCode(),
			GetterUtil.getInteger(
				payment.getType(), CommercePaymentEntryConstants.TYPE_PAYMENT),
			_serviceContextHelper.getServiceContext(contextUser));
	}

	private Map<String, Map<String, String>> _getActions(
			CommercePaymentEntry commercePaymentEntry)
		throws NoSuchMethodException, PortalException {

		if (contextUriInfo == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			_addAction(
				ActionKeys.DELETE,
				commercePaymentEntry.getCommercePaymentEntryId(),
				contextUriInfo, "deletePayment", getClass())
		).put(
			"get",
			_addAction(
				ActionKeys.VIEW,
				commercePaymentEntry.getCommercePaymentEntryId(),
				contextUriInfo, "getPayment", getClass())
		).put(
			"update",
			_addAction(
				ActionKeys.UPDATE,
				commercePaymentEntry.getCommercePaymentEntryId(),
				contextUriInfo, "patchPayment", getClass())
		).build();
	}

	private String _getHttpMethodName(Class<?> clazz, Method method)
		throws NoSuchMethodException {

		Class<?> superClass = clazz.getSuperclass();

		Method superMethod = superClass.getMethod(
			method.getName(), method.getParameterTypes());

		for (Annotation annotation : superMethod.getAnnotations()) {
			Class<? extends Annotation> annotationType =
				annotation.annotationType();

			Annotation[] annotations = annotationType.getAnnotationsByType(
				HttpMethod.class);

			if (annotations.length > 0) {
				HttpMethod httpMethod = (HttpMethod)annotations[0];

				return httpMethod.value();
			}
		}

		return null;
	}

	private Method _getMethod(Class<?> clazz, String methodName) {
		for (Method method : clazz.getMethods()) {
			if (methodName.equals(method.getName())) {
				return method;
			}
		}

		return null;
	}

	private String _getVersion(UriInfo uriInfo) {
		List<String> matchedURIs = uriInfo.getMatchedURIs();

		if (matchedURIs.isEmpty()) {
			return "";
		}

		return matchedURIs.get(matchedURIs.size() - 1);
	}

	private Payment _toPayment(
			long commercePaymentEntryId, Locale locale,
			boolean acceptAllLanguages, User contextUser,
			UriInfo contextUriInfo, Map<String, Map<String, String>> actions)
		throws Exception {

		return _paymentDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				acceptAllLanguages, actions, _dtoConverterRegistry,
				commercePaymentEntryId, locale, contextUriInfo, contextUser));
	}

	private CommercePaymentEntry _updatePayment(
			CommercePaymentEntry commercePaymentEntry, Payment payment)
		throws Exception {

		return _commercePaymentEntryService.updateCommercePaymentEntry(
			GetterUtil.getString(
				payment.getExternalReferenceCode(),
				commercePaymentEntry.getExternalReferenceCode()),
			commercePaymentEntry.getCommercePaymentEntryId(),
			GetterUtil.getLong(
				payment.getChannelId(),
				commercePaymentEntry.getCommerceChannelId()),
			(BigDecimal)GetterUtil.getNumber(
				payment.getAmount(), commercePaymentEntry.getAmount()),
			GetterUtil.getString(
				payment.getCallbackURL(),
				commercePaymentEntry.getCallbackURL()),
			GetterUtil.getString(
				payment.getCancelURL(), commercePaymentEntry.getCancelURL()),
			GetterUtil.getString(
				payment.getCurrencyCode(),
				commercePaymentEntry.getCurrencyCode()),
			GetterUtil.getString(
				payment.getErrorMessages(),
				commercePaymentEntry.getErrorMessages()),
			GetterUtil.getString(
				payment.getLanguageId(), commercePaymentEntry.getLanguageId()),
			GetterUtil.getString(
				payment.getComment(), commercePaymentEntry.getNote()),
			GetterUtil.getString(
				payment.getPaymentIntegrationKey(),
				commercePaymentEntry.getPaymentIntegrationKey()),
			GetterUtil.getInteger(
				payment.getPaymentIntegrationType(),
				commercePaymentEntry.getPaymentIntegrationType()),
			GetterUtil.getInteger(
				payment.getPaymentStatus(),
				commercePaymentEntry.getPaymentStatus()),
			GetterUtil.getString(
				payment.getReasonKey(), commercePaymentEntry.getReasonKey()),
			GetterUtil.getString(
				payment.getRedirectURL(),
				commercePaymentEntry.getRedirectURL()),
			GetterUtil.getString(
				payment.getTransactionCode(),
				commercePaymentEntry.getTransactionCode()),
			GetterUtil.getInteger(
				payment.getType(), commercePaymentEntry.getType()));
	}

	private static final EntityModel _entityModel = new PaymentEntityModel();

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.payment.model.CommercePaymentEntry)"
	)
	private ModelResourcePermission<CommercePaymentEntry>
		_commercePaymentEntryModelResourcePermission;

	@Reference
	private CommercePaymentEntryService _commercePaymentEntryService;

	@Reference
	private CommercePaymentGateway _commercePaymentGateway;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.payment.internal.dto.v1_0.converter.PaymentDTOConverter)"
	)
	private DTOConverter<CommercePaymentEntry, Payment> _paymentDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}