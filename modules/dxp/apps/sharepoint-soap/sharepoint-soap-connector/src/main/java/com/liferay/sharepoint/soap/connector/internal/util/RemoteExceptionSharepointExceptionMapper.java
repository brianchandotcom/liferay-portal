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

package com.liferay.sharepoint.soap.connector.internal.util;

import com.liferay.portal.kernel.repository.AuthenticationRepositoryException;
import com.liferay.sharepoint.soap.connector.SharepointException;

import java.rmi.RemoteException;

import org.apache.axis.AxisFault;

/**
 * @author Adolfo Pérez
 */
public class RemoteExceptionSharepointExceptionMapper {

	public static SharepointException map(RemoteException remoteException) {
		if (remoteException instanceof AxisFault) {
			AxisFault axisFault = (AxisFault)remoteException;

			String faultString = axisFault.getFaultString();

			if (faultString.startsWith("(401)Unauthorized")) {
				throw new AuthenticationRepositoryException(remoteException);
			}
		}

		return new SharepointException(
			"Unable to communicate with the Sharepoint server",
			remoteException);
	}

}