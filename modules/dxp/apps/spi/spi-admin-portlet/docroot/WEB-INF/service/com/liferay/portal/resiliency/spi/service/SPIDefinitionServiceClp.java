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

package com.liferay.portal.resiliency.spi.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.InvokableService;

/**
 * @author Michael C. Han
 * @generated
 */
@ProviderType
public class SPIDefinitionServiceClp implements SPIDefinitionService {
	public SPIDefinitionServiceClp(InvokableService invokableService) {
		_invokableService = invokableService;

		_methodName0 = "getPortletIdsAndServletContextNames";

		_methodParameterTypes0 = new String[] {  };

		_methodName1 = "addSPIDefinition";

		_methodParameterTypes1 = new String[] {
				"java.lang.String", "java.lang.String", "int",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName2 = "deleteSPIDefinition";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "getSPIDefinition";

		_methodParameterTypes3 = new String[] { "long", "java.lang.String" };

		_methodName4 = "getSPIDefinition";

		_methodParameterTypes4 = new String[] { "long" };

		_methodName5 = "updateSPIDefinition";

		_methodParameterTypes5 = new String[] {
				"long", "java.lang.String", "int", "java.lang.String",
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName6 = "updateTypeSettings";

		_methodParameterTypes6 = new String[] {
				"long", "long", "java.lang.String",
				"com.liferay.portal.kernel.service.ServiceContext"
			};

		_methodName8 = "getOSGiServiceIdentifier";

		_methodParameterTypes8 = new String[] {  };

		_methodName9 = "getSPIDefinitions";

		_methodParameterTypes9 = new String[] {  };

		_methodName10 = "startSPIinBackground";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "stopSPIinBackground";

		_methodParameterTypes11 = new String[] { "long" };

		_methodName12 = "startSPI";

		_methodParameterTypes12 = new String[] { "long" };

		_methodName13 = "stopSPI";

		_methodParameterTypes13 = new String[] { "long" };
	}

	@Override
	public com.liferay.portal.kernel.util.Tuple getPortletIdsAndServletContextNames()
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName0,
					_methodParameterTypes0, new Object[] {  });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.kernel.util.Tuple)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition addSPIDefinition(
		java.lang.String name, java.lang.String connectorAddress,
		int connectorPort, java.lang.String description,
		java.lang.String jvmArguments, java.lang.String portletIds,
		java.lang.String servletContextNames, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName1,
					_methodParameterTypes1,
					new Object[] {
						ClpSerializer.translateInput(name),
						
					ClpSerializer.translateInput(connectorAddress),
						
					connectorPort,
						
					ClpSerializer.translateInput(description),
						
					ClpSerializer.translateInput(jvmArguments),
						
					ClpSerializer.translateInput(portletIds),
						
					ClpSerializer.translateInput(servletContextNames),
						
					ClpSerializer.translateInput(typeSettings),
						
					ClpSerializer.translateInput(serviceContext)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.resiliency.spi.model.SPIDefinition)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition deleteSPIDefinition(
		long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName2,
					_methodParameterTypes2, new Object[] { spiDefinitionId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.resiliency.spi.model.SPIDefinition)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition getSPIDefinition(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName3,
					_methodParameterTypes3,
					new Object[] { companyId, ClpSerializer.translateInput(name) });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.resiliency.spi.model.SPIDefinition)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition getSPIDefinition(
		long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName4,
					_methodParameterTypes4, new Object[] { spiDefinitionId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.resiliency.spi.model.SPIDefinition)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition updateSPIDefinition(
		long spiDefinitionId, java.lang.String connectorAddress,
		int connectorPort, java.lang.String description,
		java.lang.String jvmArguments, java.lang.String portletIds,
		java.lang.String servletContextNames, java.lang.String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName5,
					_methodParameterTypes5,
					new Object[] {
						spiDefinitionId,
						
					ClpSerializer.translateInput(connectorAddress),
						
					connectorPort,
						
					ClpSerializer.translateInput(description),
						
					ClpSerializer.translateInput(jvmArguments),
						
					ClpSerializer.translateInput(portletIds),
						
					ClpSerializer.translateInput(servletContextNames),
						
					ClpSerializer.translateInput(typeSettings),
						
					ClpSerializer.translateInput(serviceContext)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.resiliency.spi.model.SPIDefinition)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public com.liferay.portal.resiliency.spi.model.SPIDefinition updateTypeSettings(
		long userId, long spiDefinitionId, java.lang.String recoveryOptions,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName6,
					_methodParameterTypes6,
					new Object[] {
						userId,
						
					spiDefinitionId,
						
					ClpSerializer.translateInput(recoveryOptions),
						
					ClpSerializer.translateInput(serviceContext)
					});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (com.liferay.portal.resiliency.spi.model.SPIDefinition)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}

	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName8,
					_methodParameterTypes8, new Object[] {  });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.lang.String)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public java.util.List<com.liferay.portal.resiliency.spi.model.SPIDefinition> getSPIDefinitions()
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName9,
					_methodParameterTypes9, new Object[] {  });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return (java.util.List<com.liferay.portal.resiliency.spi.model.SPIDefinition>)ClpSerializer.translateOutput(returnObj);
	}

	@Override
	public long startSPIinBackground(long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName10,
					_methodParameterTypes10, new Object[] { spiDefinitionId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Long)returnObj).longValue();
	}

	@Override
	public long stopSPIinBackground(long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		Object returnObj = null;

		try {
			returnObj = _invokableService.invokeMethod(_methodName11,
					_methodParameterTypes11, new Object[] { spiDefinitionId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Long)returnObj).longValue();
	}

	@Override
	public void startSPI(long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			_invokableService.invokeMethod(_methodName12,
				_methodParameterTypes12, new Object[] { spiDefinitionId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}

	@Override
	public void stopSPI(long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			_invokableService.invokeMethod(_methodName13,
				_methodParameterTypes13, new Object[] { spiDefinitionId });
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}

	private InvokableService _invokableService;
	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName4;
	private String[] _methodParameterTypes4;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
	private String _methodName10;
	private String[] _methodParameterTypes10;
	private String _methodName11;
	private String[] _methodParameterTypes11;
	private String _methodName12;
	private String[] _methodParameterTypes12;
	private String _methodName13;
	private String[] _methodParameterTypes13;
}