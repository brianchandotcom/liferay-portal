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

package com.liferay.commerce.user.segment.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceUserSegmentCriterionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion}, that is translated to a
 * {@link com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionServiceHttp
 * @see com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap
 * @see CommerceUserSegmentCriterionServiceUtil
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionServiceSoap {
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap addCommerceUserSegmentCriterion(
		long commerceUserSegmentEntryId, String type, String typeSettings,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion returnValue =
				CommerceUserSegmentCriterionServiceUtil.addCommerceUserSegmentCriterion(commerceUserSegmentEntryId,
					type, typeSettings, priority, serviceContext);

			return com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId) throws RemoteException {
		try {
			CommerceUserSegmentCriterionServiceUtil.deleteCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap[] getCommerceUserSegmentCriteria(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> returnValue =
				CommerceUserSegmentCriterionServiceUtil.getCommerceUserSegmentCriteria(commerceUserSegmentEntryId,
					start, end, orderByComparator);

			return com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceUserSegmentCriteriaCount(
		long commerceUserSegmentEntryId) throws RemoteException {
		try {
			int returnValue = CommerceUserSegmentCriterionServiceUtil.getCommerceUserSegmentCriteriaCount(commerceUserSegmentEntryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap getCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId) throws RemoteException {
		try {
			com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion returnValue =
				CommerceUserSegmentCriterionServiceUtil.getCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);

			return com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap updateCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId, String type, String typeSettings,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion returnValue =
				CommerceUserSegmentCriterionServiceUtil.updateCommerceUserSegmentCriterion(commerceUserSegmentCriterionId,
					type, typeSettings, priority, serviceContext);

			return com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceUserSegmentCriterionServiceSoap.class);
}