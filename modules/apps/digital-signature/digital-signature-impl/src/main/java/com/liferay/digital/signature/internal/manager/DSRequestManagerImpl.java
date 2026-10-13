/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.internal.manager;

import com.liferay.digital.signature.configuration.DigitalSignatureConfiguration;
import com.liferay.digital.signature.configuration.DigitalSignatureConfigurationUtil;
import com.liferay.digital.signature.constants.DSObjectDefinitionConstants;
import com.liferay.digital.signature.constants.DSRequestConstants;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.manager.DSRequestManager;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(service = DSRequestManager.class)
public class DSRequestManagerImpl implements DSRequestManager {

	@Override
	public void addDSRequest(
			long companyId, long groupId, long userId, DSEnvelope dsEnvelope,
			long[] fileEntryIds)
		throws PortalException {

		if (!_isEnabled(companyId, groupId)) {
			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_addDSRequest(
						companyId, groupId, userId, dsEnvelope, fileEntryIds);

					return null;
				});
		}
		catch (Throwable throwable) {
			if (throwable instanceof PortalException) {
				throw (PortalException)throwable;
			}

			throw new PortalException(
				"Unable to record the signature request for envelope " +
					dsEnvelope.getDSEnvelopeId(),
				throwable);
		}
	}

	@Override
	public void updateDSRequest(
		long companyId, long groupId, String providerRequestId) {

		if (Validator.isNull(providerRequestId) ||
			!_isEnabled(companyId, groupId)) {

			return;
		}

		ObjectDefinition dsRequestObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					DSObjectDefinitionConstants.EXTERNAL_REFERENCE_CODE_REQUEST,
					companyId);
		ObjectDefinition dsRequestRecipientObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					DSObjectDefinitionConstants.
						EXTERNAL_REFERENCE_CODE_REQUEST_RECIPIENT,
					companyId);

		if ((dsRequestObjectDefinition == null) ||
			(dsRequestRecipientObjectDefinition == null)) {

			return;
		}

		try {
			Map<String, DSRecipient> dsRecipients = new HashMap<>();

			DSEnvelope dsEnvelope = _dsEnvelopeManager.getDSEnvelope(
				companyId, groupId, providerRequestId);

			for (DSRecipient dsRecipient : dsEnvelope.getDSRecipients()) {
				dsRecipients.put(dsRecipient.getDSRecipientId(), dsRecipient);
			}

			for (Map<String, Serializable> values :
					_getValuesList(
						companyId,
						StringBundler.concat(
							"(providerRequestId eq '",
							StringUtil.replace(providerRequestId, '\'', "''"),
							"')"),
						dsRequestObjectDefinition)) {

				long dsRequestId = MapUtil.getLong(
					values, dsRequestObjectDefinition.getPKObjectFieldName());

				_updateRecipientStatuses(
					companyId, groupId, dsRecipients, dsRequestId,
					dsRequestRecipientObjectDefinition);
				_updateRequestStatus(
					companyId, groupId, dsEnvelope, dsRequestId);
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to update the signature request for envelope " +
					providerRequestId,
				exception);
		}
	}

	private void _addDSRequest(
			long companyId, long groupId, long userId, DSEnvelope dsEnvelope,
			long[] fileEntryIds)
		throws Exception {

		ObjectDefinition dsRequestDocumentObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					DSObjectDefinitionConstants.
						EXTERNAL_REFERENCE_CODE_REQUEST_DOCUMENT,
					companyId);
		ObjectDefinition dsRequestObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					DSObjectDefinitionConstants.EXTERNAL_REFERENCE_CODE_REQUEST,
					companyId);
		ObjectDefinition dsRequestRecipientObjectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					DSObjectDefinitionConstants.
						EXTERNAL_REFERENCE_CODE_REQUEST_RECIPIENT,
					companyId);

		if ((dsRequestDocumentObjectDefinition == null) ||
			(dsRequestObjectDefinition == null) ||
			(dsRequestRecipientObjectDefinition == null)) {

			return;
		}

		ServiceContext serviceContext = _getServiceContext(
			companyId, groupId, userId);

		ObjectEntry dsRequestObjectEntry =
			_objectEntryLocalService.addObjectEntry(
				0, userId, dsRequestObjectDefinition.getObjectDefinitionId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				null,
				HashMapBuilder.<String, Serializable>put(
					"emailSubject", dsEnvelope.getEmailSubject()
				).put(
					"providerKey", "docusign"
				).put(
					"providerRequestId", dsEnvelope.getDSEnvelopeId()
				).put(
					"requestExpirationDate",
					() -> _toDate(dsEnvelope.getExpireLocalDateTime())
				).put(
					"requestStatus", () -> _getRequestStatus(dsEnvelope)
				).build(),
				serviceContext);

		for (long fileEntryId : fileEntryIds) {
			_objectEntryLocalService.addObjectEntry(
				0, userId,
				dsRequestDocumentObjectDefinition.getObjectDefinitionId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				null,
				HashMapBuilder.<String, Serializable>put(
					"fileEntryId", fileEntryId
				).put(
					"r_dsRequestToDSRequestDocuments_l_dsRequestId",
					dsRequestObjectEntry.getObjectEntryId()
				).build(),
				serviceContext);
		}

		for (DSRecipient dsRecipient : dsEnvelope.getDSRecipients()) {
			_objectEntryLocalService.addObjectEntry(
				0, userId,
				dsRequestRecipientObjectDefinition.getObjectDefinitionId(),
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				null,
				HashMapBuilder.<String, Serializable>put(
					"emailAddress", dsRecipient.getEmailAddress()
				).put(
					"name", dsRecipient.getName()
				).put(
					"providerRecipientId", dsRecipient.getDSRecipientId()
				).put(
					"r_dsRequestToDSRequestRecipients_l_dsRequestId",
					dsRequestObjectEntry.getObjectEntryId()
				).put(
					"r_userToDSRequestRecipients_userId",
					_getRecipientUserId(
						companyId, dsRecipient.getEmailAddress())
				).put(
					"requestRecipientStatus",
					() -> _getRequestRecipientStatus(dsRecipient)
				).put(
					"sentDate",
					() -> _toDate(dsRecipient.getSentLocalDateTime())
				).build(),
				serviceContext);
		}
	}

	private long _getRecipientUserId(long companyId, String emailAddress) {
		if (Validator.isNull(emailAddress)) {
			return 0;
		}

		User user = _userLocalService.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (user == null) {
			return 0;
		}

		return user.getUserId();
	}

	private String _getRequestRecipientStatus(DSRecipient dsRecipient) {
		String status = StringUtil.toLowerCase(dsRecipient.getStatus());

		if (Objects.equals(status, "delivered")) {
			return DSRequestConstants.STATUS_SENT;
		}

		if (ArrayUtil.contains(
				DSRequestConstants.STATUSES_REQUEST_RECIPIENT, status)) {

			return status;
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Unsupported request recipient status \"", status, "\""));
		}

		return null;
	}

	private String _getRequestStatus(DSEnvelope dsEnvelope) {
		LocalDateTime expireLocalDateTime = dsEnvelope.getExpireLocalDateTime();
		String status = StringUtil.toLowerCase(dsEnvelope.getStatus());
		LocalDateTime statusChangedLocalDateTime =
			dsEnvelope.getStatusChangedLocalDateTime();

		if (Objects.equals(status, DSRequestConstants.STATUS_VOIDED) &&
			(expireLocalDateTime != null) &&
			(statusChangedLocalDateTime != null) &&
			!statusChangedLocalDateTime.isBefore(expireLocalDateTime)) {

			return DSRequestConstants.STATUS_EXPIRED;
		}

		if (Objects.equals(status, "delivered") ||
			Objects.equals(status, DSRequestConstants.STATUS_SIGNED)) {

			return DSRequestConstants.STATUS_SENT;
		}

		if (ArrayUtil.contains(DSRequestConstants.STATUSES_REQUEST, status)) {
			return status;
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Unsupported request status \"", status, "\""));
		}

		return null;
	}

	private ServiceContext _getServiceContext(
		long companyId, long groupId, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

	private List<Map<String, Serializable>> _getValuesList(
			long companyId, String filterString,
			ObjectDefinition objectDefinition)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(null);

			return _objectEntryLocalService.getValuesList(
				0, companyId, objectDefinition.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(filterString, objectDefinition), null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private boolean _isEnabled(long companyId, long groupId) {
		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-69290")) {
			return false;
		}

		DigitalSignatureConfiguration digitalSignatureConfiguration =
			DigitalSignatureConfigurationUtil.getDigitalSignatureConfiguration(
				companyId, groupId);

		return digitalSignatureConfiguration.enabled();
	}

	private Date _toDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}

		return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}

	private void _updateRecipientStatuses(
			long companyId, long groupId, Map<String, DSRecipient> dsRecipients,
			long dsRequestId,
			ObjectDefinition dsRequestRecipientObjectDefinition)
		throws Exception {

		for (Map<String, Serializable> values :
				_getValuesList(
					companyId,
					"(r_dsRequestToDSRequestRecipients_l_dsRequestId eq '" +
						dsRequestId + "')",
					dsRequestRecipientObjectDefinition)) {

			DSRecipient dsRecipient = dsRecipients.get(
				MapUtil.getString(values, "providerRecipientId"));

			if (dsRecipient == null) {
				continue;
			}

			long dsRequestRecipientId = MapUtil.getLong(
				values,
				dsRequestRecipientObjectDefinition.getPKObjectFieldName());

			ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
				dsRequestRecipientId);

			if (objectEntry == null) {
				continue;
			}

			_objectEntryLocalService.partialUpdateObjectEntry(
				objectEntry.getUserId(), dsRequestRecipientId,
				ObjectEntryFolderConstants.
					PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
				HashMapBuilder.<String, Serializable>put(
					"requestRecipientStatus",
					() -> _getRequestRecipientStatus(dsRecipient)
				).put(
					"requestRecipientStatusDate",
					() -> _toDate(dsRecipient.getStatusLocalDateTime())
				).put(
					"sentDate",
					() -> _toDate(dsRecipient.getSentLocalDateTime())
				).build(),
				_getServiceContext(
					companyId, groupId, objectEntry.getUserId()));
		}
	}

	private void _updateRequestStatus(
			long companyId, long groupId, DSEnvelope dsEnvelope,
			long dsRequestId)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			dsRequestId);

		if (objectEntry == null) {
			return;
		}

		_objectEntryLocalService.partialUpdateObjectEntry(
			objectEntry.getUserId(), dsRequestId,
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			HashMapBuilder.<String, Serializable>put(
				"requestExpirationDate",
				() -> _toDate(dsEnvelope.getExpireLocalDateTime())
			).put(
				"requestStatus", () -> _getRequestStatus(dsEnvelope)
			).put(
				"requestStatusDate",
				() -> _toDate(dsEnvelope.getStatusChangedLocalDateTime())
			).build(),
			_getServiceContext(companyId, groupId, objectEntry.getUserId()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DSRequestManagerImpl.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private DSEnvelopeManager _dsEnvelopeManager;

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}