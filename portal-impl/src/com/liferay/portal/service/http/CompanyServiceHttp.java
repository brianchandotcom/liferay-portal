/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.CompanyServiceUtil;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CompanyServiceUtil</code> service
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
public class CompanyServiceHttp {

	public static com.liferay.portal.kernel.model.Company addCompany(
			HttpPrincipal httpPrincipal, long companyId, String webId,
			String virtualHost, String mx, int maxUsers, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "addCompany",
				_addCompanyParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, webId, virtualHost, mx, maxUsers, active);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company addCompany(
			HttpPrincipal httpPrincipal, long companyId, String webId,
			String virtualHost, String mx, int maxUsers, boolean active,
			String siteInitializerKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "addCompany",
				_addCompanyParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, webId, virtualHost, mx, maxUsers, active,
				siteInitializerKey);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company addCompany(
			HttpPrincipal httpPrincipal, String webId, String virtualHost,
			String mx, int maxUsers, boolean active,
			String defaultAdminPassword, String defaultAdminScreenName,
			String defaultAdminEmailAddress, String defaultAdminFirstName,
			String defaultAdminMiddleName, String defaultAdminLastName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "addCompany",
				_addCompanyParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, webId, virtualHost, mx, maxUsers, active,
				defaultAdminPassword, defaultAdminScreenName,
				defaultAdminEmailAddress, defaultAdminFirstName,
				defaultAdminMiddleName, defaultAdminLastName);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company addCompany(
			HttpPrincipal httpPrincipal, String webId, String virtualHost,
			String mx, int maxUsers, boolean active,
			String defaultAdminPassword, String defaultAdminScreenName,
			String defaultAdminEmailAddress, String defaultAdminFirstName,
			String defaultAdminMiddleName, String defaultAdminLastName,
			String siteInitializerKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "addCompany",
				_addCompanyParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, webId, virtualHost, mx, maxUsers, active,
				defaultAdminPassword, defaultAdminScreenName,
				defaultAdminEmailAddress, defaultAdminFirstName,
				defaultAdminMiddleName, defaultAdminLastName,
				siteInitializerKey);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company deleteCompany(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "deleteCompany",
				_deleteCompanyParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteLogo(HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "deleteLogo",
				_deleteLogoParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

	public static void forEachCompany(
			HttpPrincipal httpPrincipal,
			com.liferay.petra.function.UnsafeConsumer
				<com.liferay.portal.kernel.model.Company, Exception>
					unsafeConsumer)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "forEachCompany",
				_forEachCompanyParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, unsafeConsumer);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof Exception) {
					throw (Exception)exception;
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

	public static java.util.List<com.liferay.portal.kernel.model.Company>
		getCompanies(HttpPrincipal httpPrincipal) {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "getCompanies",
				_getCompaniesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<com.liferay.portal.kernel.model.Company>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company getCompanyById(
			HttpPrincipal httpPrincipal, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "getCompanyById",
				_getCompanyByIdParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company getCompanyByLogoId(
			HttpPrincipal httpPrincipal, long logoId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "getCompanyByLogoId",
				_getCompanyByLogoIdParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, logoId);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company getCompanyByMx(
			HttpPrincipal httpPrincipal, String mx)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "getCompanyByMx",
				_getCompanyByMxParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey, mx);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company
			getCompanyByVirtualHost(
				HttpPrincipal httpPrincipal, String virtualHost)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "getCompanyByVirtualHost",
				_getCompanyByVirtualHostParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, virtualHost);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company getCompanyByWebId(
			HttpPrincipal httpPrincipal, String webId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "getCompanyByWebId",
				_getCompanyByWebIdParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, webId);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void removePreferences(
			HttpPrincipal httpPrincipal, long companyId, String[] keys)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "removePreferences",
				_removePreferencesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, keys);

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

	public static com.liferay.portal.kernel.model.Company updateCompany(
			HttpPrincipal httpPrincipal, long companyId, String virtualHost,
			String mx, int maxUsers, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "updateCompany",
				_updateCompanyParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, virtualHost, mx, maxUsers, active);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company updateCompany(
			HttpPrincipal httpPrincipal, long companyId, String virtualHost,
			String mx, String homeURL, boolean hasLogo, byte[] logoBytes,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "updateCompany",
				_updateCompanyParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, virtualHost, mx, homeURL, hasLogo,
				logoBytes, name, legalName, legalId, legalType, sicCode,
				tickerSymbol, industry, type, size);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company updateCompany(
			HttpPrincipal httpPrincipal, long companyId, String virtualHost,
			String mx, String homeURL, boolean hasLogo, byte[] logoBytes,
			String name, String legalName, String legalId, String legalType,
			String sicCode, String tickerSymbol, String industry, String type,
			String size, String languageId, String timeZoneId,
			java.util.List<com.liferay.portal.kernel.model.Address> addresses,
			java.util.List<com.liferay.portal.kernel.model.EmailAddress>
				emailAddresses,
			java.util.List<com.liferay.portal.kernel.model.Phone> phones,
			java.util.List<com.liferay.portal.kernel.model.Website> websites,
			com.liferay.portal.kernel.util.UnicodeProperties unicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "updateCompany",
				_updateCompanyParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, virtualHost, mx, homeURL, hasLogo,
				logoBytes, name, legalName, legalId, legalType, sicCode,
				tickerSymbol, industry, type, size, languageId, timeZoneId,
				addresses, emailAddresses, phones, websites, unicodeProperties);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void updateDisplay(
			HttpPrincipal httpPrincipal, long companyId, String languageId,
			String timeZoneId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "updateDisplay",
				_updateDisplayParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, languageId, timeZoneId);

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

	public static com.liferay.portal.kernel.model.Company updateLogo(
			HttpPrincipal httpPrincipal, long companyId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "updateLogo",
				_updateLogoParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, bytes);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.model.Company updateLogo(
			HttpPrincipal httpPrincipal, long companyId,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "updateLogo",
				_updateLogoParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, inputStream);

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

			return (com.liferay.portal.kernel.model.Company)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void updatePreferences(
			HttpPrincipal httpPrincipal, long companyId,
			com.liferay.portal.kernel.util.UnicodeProperties unicodeProperties)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "updatePreferences",
				_updatePreferencesParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, unicodeProperties);

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

	public static void updateSecurity(
			HttpPrincipal httpPrincipal, long companyId, String authType,
			boolean autoLogin, boolean sendPassword, boolean strangers,
			boolean strangersWithMx, boolean strangersVerify, boolean siteLogo)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CompanyServiceUtil.class, "updateSecurity",
				_updateSecurityParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, authType, autoLogin, sendPassword,
				strangers, strangersWithMx, strangersVerify, siteLogo);

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

	private static Log _log = LogFactoryUtil.getLog(CompanyServiceHttp.class);

	private static final Class<?>[] _addCompanyParameterTypes0 = new Class[] {
		long.class, String.class, String.class, String.class, int.class,
		boolean.class
	};
	private static final Class<?>[] _addCompanyParameterTypes1 = new Class[] {
		long.class, String.class, String.class, String.class, int.class,
		boolean.class, String.class
	};
	private static final Class<?>[] _addCompanyParameterTypes2 = new Class[] {
		String.class, String.class, String.class, int.class, boolean.class,
		String.class, String.class, String.class, String.class, String.class,
		String.class
	};
	private static final Class<?>[] _addCompanyParameterTypes3 = new Class[] {
		String.class, String.class, String.class, int.class, boolean.class,
		String.class, String.class, String.class, String.class, String.class,
		String.class, String.class
	};
	private static final Class<?>[] _deleteCompanyParameterTypes4 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteLogoParameterTypes5 = new Class[] {
		long.class
	};
	private static final Class<?>[] _forEachCompanyParameterTypes6 =
		new Class[] {com.liferay.petra.function.UnsafeConsumer.class};
	private static final Class<?>[] _getCompaniesParameterTypes7 =
		new Class[] {};
	private static final Class<?>[] _getCompanyByIdParameterTypes8 =
		new Class[] {long.class};
	private static final Class<?>[] _getCompanyByLogoIdParameterTypes9 =
		new Class[] {long.class};
	private static final Class<?>[] _getCompanyByMxParameterTypes10 =
		new Class[] {String.class};
	private static final Class<?>[] _getCompanyByVirtualHostParameterTypes11 =
		new Class[] {String.class};
	private static final Class<?>[] _getCompanyByWebIdParameterTypes12 =
		new Class[] {String.class};
	private static final Class<?>[] _removePreferencesParameterTypes13 =
		new Class[] {long.class, String[].class};
	private static final Class<?>[] _updateCompanyParameterTypes14 =
		new Class[] {
			long.class, String.class, String.class, int.class, boolean.class
		};
	private static final Class<?>[] _updateCompanyParameterTypes15 =
		new Class[] {
			long.class, String.class, String.class, String.class, boolean.class,
			byte[].class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class
		};
	private static final Class<?>[] _updateCompanyParameterTypes16 =
		new Class[] {
			long.class, String.class, String.class, String.class, boolean.class,
			byte[].class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			java.util.List.class, java.util.List.class, java.util.List.class,
			java.util.List.class,
			com.liferay.portal.kernel.util.UnicodeProperties.class
		};
	private static final Class<?>[] _updateDisplayParameterTypes17 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _updateLogoParameterTypes18 = new Class[] {
		long.class, byte[].class
	};
	private static final Class<?>[] _updateLogoParameterTypes19 = new Class[] {
		long.class, java.io.InputStream.class
	};
	private static final Class<?>[] _updatePreferencesParameterTypes20 =
		new Class[] {
			long.class, com.liferay.portal.kernel.util.UnicodeProperties.class
		};
	private static final Class<?>[] _updateSecurityParameterTypes21 =
		new Class[] {
			long.class, String.class, boolean.class, boolean.class,
			boolean.class, boolean.class, boolean.class, boolean.class
		};

}