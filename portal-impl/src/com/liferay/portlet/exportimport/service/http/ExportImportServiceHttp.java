/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.exportimport.service.http;

import com.liferay.exportimport.kernel.service.ExportImportServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ExportImportServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
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
 * @generated
 */
public class ExportImportServiceHttp {

	public static java.io.File exportLayoutsAsFile(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "exportLayoutsAsFile",
				_exportLayoutsAsFileParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long exportLayoutsAsFileInBackground(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class,
				"exportLayoutsAsFileInBackground",
				_exportLayoutsAsFileInBackgroundParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long exportLayoutsAsFileInBackground(
			HttpPrincipal httpPrincipal, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class,
				"exportLayoutsAsFileInBackground",
				_exportLayoutsAsFileInBackgroundParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfigurationId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.io.File exportPortletInfoAsFile(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "exportPortletInfoAsFile",
				_exportPortletInfoAsFileParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long exportPortletInfoAsFileInBackground(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class,
				"exportPortletInfoAsFileInBackground",
				_exportPortletInfoAsFileInBackgroundParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void importLayouts(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importLayouts",
				_importLayoutsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void importLayouts(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importLayouts",
				_importLayoutsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long importLayoutsInBackground(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importLayoutsInBackground",
				_importLayoutsInBackgroundParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long importLayoutsInBackground(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importLayoutsInBackground",
				_importLayoutsInBackgroundParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void importPortletInfo(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importPortletInfo",
				_importPortletInfoParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void importPortletInfo(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importPortletInfo",
				_importPortletInfoParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long importPortletInfoInBackground(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importPortletInfoInBackground",
				_importPortletInfoInBackgroundParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static long importPortletInfoInBackground(
			HttpPrincipal httpPrincipal,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importPortletInfoInBackground",
				_importPortletInfoInBackgroundParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportLayoutsFile(
				HttpPrincipal httpPrincipal,
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "validateImportLayoutsFile",
				_validateImportLayoutsFileParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportLayoutsFile(
				HttpPrincipal httpPrincipal,
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "validateImportLayoutsFile",
				_validateImportLayoutsFileParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportPortletInfo(
				HttpPrincipal httpPrincipal,
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "validateImportPortletInfo",
				_validateImportPortletInfoParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportPortletInfo(
				HttpPrincipal httpPrincipal,
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "validateImportPortletInfo",
				_validateImportPortletInfoParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExportImportServiceHttp.class);

	private static final Class<?>[] _exportLayoutsAsFileParameterTypes0 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[]
		_exportLayoutsAsFileInBackgroundParameterTypes1 = new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[]
		_exportLayoutsAsFileInBackgroundParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _exportPortletInfoAsFileParameterTypes3 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[]
		_exportPortletInfoAsFileInBackgroundParameterTypes4 = new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[] _importLayoutsParameterTypes5 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _importLayoutsParameterTypes6 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _importLayoutsInBackgroundParameterTypes7 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _importLayoutsInBackgroundParameterTypes8 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _importPortletInfoParameterTypes9 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _importPortletInfoParameterTypes10 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[]
		_importPortletInfoInBackgroundParameterTypes11 = new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[]
		_importPortletInfoInBackgroundParameterTypes12 = new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _validateImportLayoutsFileParameterTypes13 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _validateImportLayoutsFileParameterTypes14 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _validateImportPortletInfoParameterTypes15 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _validateImportPortletInfoParameterTypes16 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};

}