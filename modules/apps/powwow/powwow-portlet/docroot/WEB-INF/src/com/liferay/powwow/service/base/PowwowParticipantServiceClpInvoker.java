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

package com.liferay.powwow.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.powwow.service.PowwowParticipantServiceUtil;

import java.util.Arrays;

/**
 * @author Shinn Lok
 * @generated
 */
@ProviderType
public class PowwowParticipantServiceClpInvoker {
	public PowwowParticipantServiceClpInvoker() {
		_methodName36 = "getOSGiServiceIdentifier";

		_methodParameterTypes36 = new String[] {  };

		_methodName41 = "deletePowwowParticipant";

		_methodParameterTypes41 = new String[] {
				"com.liferay.powwow.model.PowwowParticipant"
			};

		_methodName42 = "getPowwowParticipants";

		_methodParameterTypes42 = new String[] { "long" };

		_methodName43 = "getPowwowParticipantsCount";

		_methodParameterTypes43 = new String[] { "long" };

		_methodName44 = "updatePowwowParticipant";

		_methodParameterTypes44 = new String[] {
				"long", "long", "java.lang.String", "long", "java.lang.String",
				"int", "int", "com.liferay.portal.kernel.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName36.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes36, parameterTypes)) {
			return PowwowParticipantServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			return PowwowParticipantServiceUtil.deletePowwowParticipant((com.liferay.powwow.model.PowwowParticipant)arguments[0]);
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return PowwowParticipantServiceUtil.getPowwowParticipants(((Long)arguments[0]).longValue());
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return PowwowParticipantServiceUtil.getPowwowParticipantsCount(((Long)arguments[0]).longValue());
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return PowwowParticipantServiceUtil.updatePowwowParticipant(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2],
				((Long)arguments[3]).longValue(),
				(java.lang.String)arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				(com.liferay.portal.kernel.service.ServiceContext)arguments[7]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName36;
	private String[] _methodParameterTypes36;
	private String _methodName41;
	private String[] _methodParameterTypes41;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
	private String _methodName44;
	private String[] _methodParameterTypes44;
}