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
 * @see ExportImportServiceSoap
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
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.io.File exportLayoutsAsFile(
			HttpPrincipal httpPrincipal, long userId, long groupId,
			boolean privateLayout, java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "exportLayoutsAsFile",
				_exportLayoutsAsFileParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, privateLayout, parameterMap);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
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
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class,
				"exportLayoutsAsFileInBackground",
				_exportLayoutsAsFileInBackgroundParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
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
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class,
				"exportLayoutsAsFileInBackground",
				_exportLayoutsAsFileInBackgroundParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfigurationId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
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
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "exportPortletInfoAsFile",
				_exportPortletInfoAsFileParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
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
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class,
				"exportPortletInfoAsFileInBackground",
				_exportPortletInfoAsFileInBackgroundParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_importLayoutsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_importLayoutsParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void importLayouts(
			HttpPrincipal httpPrincipal, long userId, long groupId,
			boolean privateLayout, java.util.Map<String, String[]> parameterMap,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importLayouts",
				_importLayoutsParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, groupId, privateLayout, parameterMap, file);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_importLayoutsInBackgroundParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_importLayoutsInBackgroundParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_importPortletInfoParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_importPortletInfoParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_importPortletInfoInBackgroundParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
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
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExportImportServiceUtil.class, "importPortletInfoInBackground",
				_importPortletInfoInBackgroundParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_validateImportLayoutsFileParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_validateImportLayoutsFileParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_validateImportPortletInfoParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, file);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_validateImportPortletInfoParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, exportImportConfiguration, inputStream);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.exportimport.kernel.lar.MissingReferences)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExportImportServiceHttp.class);

	private static final Class<?>[] _exportLayoutsAsFileParameterTypes0 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[] _exportLayoutsAsFileParameterTypes1 =
		new Class[] {
			long.class, long.class, boolean.class, java.util.Map.class
		};
	private static final Class<?>[]
		_exportLayoutsAsFileInBackgroundParameterTypes2 = new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[]
		_exportLayoutsAsFileInBackgroundParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _exportPortletInfoAsFileParameterTypes4 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[]
		_exportPortletInfoAsFileInBackgroundParameterTypes5 = new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class
		};
	private static final Class<?>[] _importLayoutsParameterTypes6 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _importLayoutsParameterTypes7 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _importLayoutsParameterTypes8 =
		new Class[] {
			long.class, long.class, boolean.class, java.util.Map.class,
			java.io.File.class
		};
	private static final Class<?>[] _importLayoutsInBackgroundParameterTypes9 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _importLayoutsInBackgroundParameterTypes10 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _importPortletInfoParameterTypes11 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _importPortletInfoParameterTypes12 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[]
		_importPortletInfoInBackgroundParameterTypes13 = new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[]
		_importPortletInfoInBackgroundParameterTypes14 = new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _validateImportLayoutsFileParameterTypes15 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _validateImportLayoutsFileParameterTypes16 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};
	private static final Class<?>[] _validateImportPortletInfoParameterTypes17 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.File.class
		};
	private static final Class<?>[] _validateImportPortletInfoParameterTypes18 =
		new Class[] {
			com.liferay.exportimport.kernel.model.ExportImportConfiguration.
				class,
			java.io.InputStream.class
		};

}