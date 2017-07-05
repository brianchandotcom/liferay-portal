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

import com.liferay.powwow.service.PowwowMeetingServiceUtil;

import java.util.Arrays;

/**
 * @author Shinn Lok
 * @generated
 */
@ProviderType
public class PowwowMeetingServiceClpInvoker {
	public PowwowMeetingServiceClpInvoker() {
		_methodName36 = "getOSGiServiceIdentifier";

		_methodParameterTypes36 = new String[] {  };

		_methodName41 = "addPowwowMeeting";

		_methodParameterTypes41 = new String[] {
				"long", "java.lang.String", "long", "java.lang.String",
				"java.lang.String", "java.lang.String", "java.util.Map",
				"java.lang.String", "long", "int", "java.util.List",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName42 = "deletePowwowMeeting";

		_methodParameterTypes42 = new String[] { "long" };

		_methodName43 = "getPowwowMeeting";

		_methodParameterTypes43 = new String[] { "long" };

		_methodName44 = "getPowwowMeetings";

		_methodParameterTypes44 = new String[] {
				"long", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName45 = "getPowwowMeetingsCount";

		_methodParameterTypes45 = new String[] { "long" };

		_methodName46 = "updatePowwowMeeting";

		_methodParameterTypes46 = new String[] {
				"long", "long", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Map", "java.lang.String", "long",
				"int", "java.util.List",
				"com.liferay.portal.kernel.service.ServiceContext"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName36.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes36, parameterTypes)) {
			return PowwowMeetingServiceUtil.getOSGiServiceIdentifier();
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			return PowwowMeetingServiceUtil.addPowwowMeeting(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1],
				((Long)arguments[2]).longValue(),
				(java.lang.String)arguments[3], (java.lang.String)arguments[4],
				(java.lang.String)arguments[5],
				(java.util.Map<java.lang.String, java.io.Serializable>)arguments[6],
				(java.lang.String)arguments[7],
				((Long)arguments[8]).longValue(),
				((Integer)arguments[9]).intValue(),
				(java.util.List<com.liferay.powwow.model.PowwowParticipant>)arguments[10],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[11]);
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return PowwowMeetingServiceUtil.deletePowwowMeeting(((Long)arguments[0]).longValue());
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			return PowwowMeetingServiceUtil.getPowwowMeeting(((Long)arguments[0]).longValue());
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return PowwowMeetingServiceUtil.getPowwowMeetings(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName45.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes45, parameterTypes)) {
			return PowwowMeetingServiceUtil.getPowwowMeetingsCount(((Long)arguments[0]).longValue());
		}

		if (_methodName46.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes46, parameterTypes)) {
			return PowwowMeetingServiceUtil.updatePowwowMeeting(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.lang.String)arguments[2], (java.lang.String)arguments[3],
				(java.lang.String)arguments[4],
				(java.util.Map<java.lang.String, java.io.Serializable>)arguments[5],
				(java.lang.String)arguments[6],
				((Long)arguments[7]).longValue(),
				((Integer)arguments[8]).intValue(),
				(java.util.List<com.liferay.powwow.model.PowwowParticipant>)arguments[9],
				(com.liferay.portal.kernel.service.ServiceContext)arguments[10]);
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
	private String _methodName45;
	private String[] _methodParameterTypes45;
	private String _methodName46;
	private String[] _methodParameterTypes46;
}