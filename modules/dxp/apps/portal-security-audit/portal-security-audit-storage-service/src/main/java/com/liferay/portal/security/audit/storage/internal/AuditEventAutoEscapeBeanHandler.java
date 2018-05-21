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

package com.liferay.portal.security.audit.storage.internal;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.audit.AuditEvent;

import java.lang.reflect.Method;

/**
 * @author Tomas Polesovsky
 */
public class AuditEventAutoEscapeBeanHandler extends AutoEscapeBeanHandler {

	public static AuditEvent createProxy(
		com.liferay.portal.security.audit.storage.model.AuditEvent
			auditEventModel) {

		return createProxy(auditEventModel, false);
	}

	public static AuditEvent createProxy(
		com.liferay.portal.security.audit.storage.model.AuditEvent
			auditEventModel, boolean escaping) {

		AuditEventAutoEscapeBeanHandler auditEventAutoEscapeBeanHandler =
			new AuditEventAutoEscapeBeanHandler(auditEventModel);

		auditEventAutoEscapeBeanHandler._escaping = escaping;

		return (AuditEvent)ProxyUtil.newProxyInstance(
			AuditEventAutoEscapeBeanHandler.class.getClassLoader(),
			new Class<?>[] {AuditEvent.class, BaseModel.class},
			auditEventAutoEscapeBeanHandler);
	}

	public AuditEventAutoEscapeBeanHandler(
		com.liferay.portal.security.audit.storage.model.AuditEvent
			auditEventModel) {

		super(auditEventModel);

		_auditEventModel = auditEventModel;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		String methodName = method.getName();

		Class<?> auditEventModelClass =
			com.liferay.portal.security.audit.storage.model.AuditEvent.class;

		Method originalMethod = auditEventModelClass.getMethod(
			methodName, method.getParameterTypes());

		if (methodName.equals("isEscapedModel")) {
			return _escaping;
		}
		else if (methodName.equals("toEscapedModel")) {
			if (_escaping) {
				return proxy;
			}

			if (_escapedModel == null) {
				Object escapedAuditEvent = originalMethod.invoke(
					_auditEventModel, args);

				_escapedModel = createProxy(
					(com.liferay.portal.security.audit.storage.model.AuditEvent)
						escapedAuditEvent,
					true);
			}

			return _escapedModel;
		}
		else if (methodName.equals("toUnescapedModel")) {
			if (!_escaping) {
				return proxy;
			}

			if (_escapedModel == null) {
				Object unescapedAuditEvent = originalMethod.invoke(
					_auditEventModel, args);

				_escapedModel = createProxy(
					(com.liferay.portal.security.audit.storage.model.AuditEvent)
						unescapedAuditEvent,
					false);
			}

			return _escapedModel;
		}

		return originalMethod.invoke(_auditEventModel, args);
	}

	private final com.liferay.portal.security.audit.storage.model.AuditEvent
		_auditEventModel;
	private AuditEvent _escapedModel;
	private boolean _escaping;

}