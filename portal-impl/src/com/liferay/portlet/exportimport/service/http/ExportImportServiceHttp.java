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

package com.liferay.portlet.exportimport.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.exportimport.service.ExportImportServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link ExportImportServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportServiceSoap
 * @see HttpPrincipal
 * @see ExportImportServiceUtil
 * @generated
 */
@ProviderType
public class ExportImportServiceHttp {
	public static java.io.File exportLayoutsAsFile(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"exportLayoutsAsFile", _exportLayoutsAsFileParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long exportLayoutsAsFileInBackground(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"exportLayoutsAsFileInBackground",
					_exportLayoutsAsFileInBackgroundParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long exportLayoutsAsFileInBackground(
		HttpPrincipal httpPrincipal, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"exportLayoutsAsFileInBackground",
					_exportLayoutsAsFileInBackgroundParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfigurationId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.io.File exportPortletInfoAsFile(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"exportPortletInfoAsFile",
					_exportPortletInfoAsFileParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long exportPortletInfoAsFileInBackground(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"exportPortletInfoAsFileInBackground",
					_exportPortletInfoAsFileInBackgroundParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void importLayouts(HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"importLayouts", _importLayoutsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, file);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void importLayouts(HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"importLayouts", _importLayoutsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, inputStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long importLayoutsInBackground(HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"importLayoutsInBackground",
					_importLayoutsInBackgroundParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long importLayoutsInBackground(HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"importLayoutsInBackground",
					_importLayoutsInBackgroundParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void importPortletInfo(HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"importPortletInfo", _importPortletInfoParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, file);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void importPortletInfo(HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"importPortletInfo", _importPortletInfoParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, inputStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long importPortletInfoInBackground(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"importPortletInfoInBackground",
					_importPortletInfoInBackgroundParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long importPortletInfoInBackground(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"importPortletInfoInBackground",
					_importPortletInfoInBackgroundParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences validateImportLayoutsFile(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"validateImportLayoutsFile",
					_validateImportLayoutsFileParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.exportimport.lar.MissingReferences)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences validateImportLayoutsFile(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"validateImportLayoutsFile",
					_validateImportLayoutsFileParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.exportimport.lar.MissingReferences)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences validateImportPortletInfo(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"validateImportPortletInfo",
					_validateImportPortletInfoParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.exportimport.lar.MissingReferences)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.exportimport.lar.MissingReferences validateImportPortletInfo(
		HttpPrincipal httpPrincipal,
		com.liferay.portlet.exportimport.model.ExportImportConfiguration exportImportConfiguration,
		java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(ExportImportServiceUtil.class,
					"validateImportPortletInfo",
					_validateImportPortletInfoParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.exportimport.lar.MissingReferences)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExportImportServiceHttp.class);
	private static final Class<?>[] _exportLayoutsAsFileParameterTypes0 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class
		};
	private static final Class<?>[] _exportLayoutsAsFileInBackgroundParameterTypes1 =
		new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class
		};
	private static final Class<?>[] _exportLayoutsAsFileInBackgroundParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _exportPortletInfoAsFileParameterTypes3 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class
		};
	private static final Class<?>[] _exportPortletInfoAsFileInBackgroundParameterTypes4 =
		new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class
		};
	private static final Class<?>[] _importLayoutsParameterTypes5 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.File.class
		};
	private static final Class<?>[] _importLayoutsParameterTypes6 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _importLayoutsInBackgroundParameterTypes7 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.File.class
		};
	private static final Class<?>[] _importLayoutsInBackgroundParameterTypes8 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _importPortletInfoParameterTypes9 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.File.class
		};
	private static final Class<?>[] _importPortletInfoParameterTypes10 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _importPortletInfoInBackgroundParameterTypes11 =
		new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.File.class
		};
	private static final Class<?>[] _importPortletInfoInBackgroundParameterTypes12 =
		new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _validateImportLayoutsFileParameterTypes13 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.File.class
		};
	private static final Class<?>[] _validateImportLayoutsFileParameterTypes14 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _validateImportPortletInfoParameterTypes15 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.File.class
		};
	private static final Class<?>[] _validateImportPortletInfoParameterTypes16 = new Class[] {
			com.liferay.portlet.exportimport.model.ExportImportConfiguration.class,
			java.io.InputStream.class
		};
}