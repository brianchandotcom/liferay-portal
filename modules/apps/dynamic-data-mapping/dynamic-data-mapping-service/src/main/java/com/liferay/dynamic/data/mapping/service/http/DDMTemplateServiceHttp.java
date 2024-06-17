/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.http;

import com.liferay.dynamic.data.mapping.service.DDMTemplateServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMTemplateServiceUtil</code> service
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
public class DDMTemplateServiceHttp {

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			addTemplate(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long classNameId, long classPK,
				long resourceClassNameId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String type, String mode, String language, String script,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "addTemplate",
				_addTemplateParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, classNameId, classPK,
				resourceClassNameId, nameMap, descriptionMap, type, mode,
				language, script, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			addTemplate(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId, long classNameId, long classPK,
				long resourceClassNameId, String templateKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String type, String mode, String language, String script,
				boolean cacheable, boolean smallImage, String smallImageURL,
				java.io.File smallImageFile,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "addTemplate",
				_addTemplateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId, classNameId, classPK,
				resourceClassNameId, templateKey, nameMap, descriptionMap, type,
				mode, language, script, cacheable, smallImage, smallImageURL,
				smallImageFile, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			copyTemplate(
				HttpPrincipal httpPrincipal, long sourceTemplateId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "copyTemplate",
				_copyTemplateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceTemplateId, nameMap, descriptionMap,
				serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			copyTemplate(
				HttpPrincipal httpPrincipal, long sourceTemplateId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "copyTemplate",
				_copyTemplateParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, sourceTemplateId, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> copyTemplates(
				HttpPrincipal httpPrincipal, long classNameId,
				long sourceClassPK, long resourceClassNameId,
				long targetClassPK, String type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "copyTemplates",
				_copyTemplatesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, sourceClassPK, resourceClassNameId,
				targetClassPK, type, serviceContext);

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

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteTemplate(
			HttpPrincipal httpPrincipal, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "deleteTemplate",
				_deleteTemplateParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId);

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

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			deleteTemplate(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "deleteTemplate",
				_deleteTemplateParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			fetchTemplate(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "fetchTemplate",
				_fetchTemplateParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, templateKey);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			getTemplate(HttpPrincipal httpPrincipal, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplate",
				_getTemplateParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			getTemplate(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplate",
				_getTemplateParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, templateKey);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			getTemplate(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				String templateKey, boolean includeAncestorTemplates)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplate",
				_getTemplateParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, templateKey,
				includeAncestorTemplates);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			getTemplateByExternalReferenceCode(
				HttpPrincipal httpPrincipal, String externalReferenceCode,
				long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class,
				"getTemplateByExternalReferenceCode",
				_getTemplateByExternalReferenceCodeParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, groupId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long classNameId, long resourceClassNameId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplates",
				_getTemplatesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, resourceClassNameId,
				status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
				HttpPrincipal httpPrincipal, long companyId, long groupId,
				long classNameId, long classPK, long resourceClassNameId,
				boolean includeAncestorTemplates, int status)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplates",
				_getTemplatesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, includeAncestorTemplates, status);

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

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long classNameId, long classPK, long resourceClassNameId,
			int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplates",
				_getTemplatesParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long classNameId, long classPK, long resourceClassNameId,
			String type, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplates",
				_getTemplatesParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, type, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long classNameId, long classPK, long resourceClassNameId,
			String type, String mode, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplates",
				_getTemplatesParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, type, mode, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long[] classNameIds, long[] classPKs, long resourceClassNameId,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplates",
				_getTemplatesParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate>
			getTemplatesByClassPK(
				HttpPrincipal httpPrincipal, long companyId, long groupId,
				long classPK, long resourceClassNameId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplatesByClassPK",
				_getTemplatesByClassPKParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classPK, resourceClassNameId,
				status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate>
			getTemplatesByStructureClassNameId(
				HttpPrincipal httpPrincipal, long groupId,
				long structureClassNameId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMTemplate>
						orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class,
				"getTemplatesByStructureClassNameId",
				_getTemplatesByStructureClassNameIdParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, structureClassNameId, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getTemplatesByStructureClassNameIdCount(
		HttpPrincipal httpPrincipal, long groupId, long structureClassNameId,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class,
				"getTemplatesByStructureClassNameIdCount",
				_getTemplatesByStructureClassNameIdCountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, structureClassNameId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getTemplatesCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, long resourceClassNameId) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplatesCount",
				_getTemplatesCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void revertTemplate(
			HttpPrincipal httpPrincipal, long templateId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "revertTemplate",
				_revertTemplateParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, version, serviceContext);

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

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long classNameId, long classPK, long resourceClassNameId,
			String keywords, String type, String mode, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "search",
				_searchParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, keywords, type, mode, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long classNameId, long classPK, long resourceClassNameId,
			String name, String description, String type, String mode,
			String language, int status, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "search",
				_searchParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long[] classNameIds, long[] classPKs, long resourceClassNameId,
			String keywords, String type, String mode, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "search",
				_searchParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, keywords, type, mode, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long[] classNameIds, long[] classPKs, long resourceClassNameId,
			String name, String description, String type, String mode,
			String language, int status, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "search",
				_searchParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long classNameId, long classPK, long resourceClassNameId,
		String keywords, String type, String mode, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "searchCount",
				_searchCountParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, keywords, type, mode, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long classNameId, long classPK, long resourceClassNameId, String name,
		String description, String type, String mode, String language,
		int status, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "searchCount",
				_searchCountParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, long resourceClassNameId,
		String keywords, String type, String mode, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "searchCount",
				_searchCountParameterTypes29);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, keywords, type, mode, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, long resourceClassNameId,
		String name, String description, String type, String mode,
		String language, int status, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "searchCount",
				_searchCountParameterTypes30);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			updateTemplate(
				HttpPrincipal httpPrincipal, long templateId, long classPK,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String type, String mode, String language, String script,
				boolean cacheable, boolean smallImage, String smallImageURL,
				java.io.File smallImageFile,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "updateTemplate",
				_updateTemplateParameterTypes31);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, classPK, nameMap, descriptionMap, type,
				mode, language, script, cacheable, smallImage, smallImageURL,
				smallImageFile, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			updateTemplate(
				HttpPrincipal httpPrincipal, long templateId, long classPK,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String type, String mode, String language, String script,
				boolean cacheable,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "updateTemplate",
				_updateTemplateParameterTypes32);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, classPK, nameMap, descriptionMap, type,
				mode, language, script, cacheable, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMTemplateServiceHttp.class);

	private static final Class<?>[] _addTemplateParameterTypes0 = new Class[] {
		String.class, long.class, long.class, long.class, long.class,
		java.util.Map.class, java.util.Map.class, String.class, String.class,
		String.class, String.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addTemplateParameterTypes1 = new Class[] {
		String.class, long.class, long.class, long.class, long.class,
		String.class, java.util.Map.class, java.util.Map.class, String.class,
		String.class, String.class, String.class, boolean.class, boolean.class,
		String.class, java.io.File.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _copyTemplateParameterTypes2 = new Class[] {
		long.class, java.util.Map.class, java.util.Map.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _copyTemplateParameterTypes3 = new Class[] {
		long.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _copyTemplatesParameterTypes4 =
		new Class[] {
			long.class, long.class, long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteTemplateParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteTemplateParameterTypes6 =
		new Class[] {String.class, long.class};
	private static final Class<?>[] _fetchTemplateParameterTypes7 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _getTemplateParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getTemplateParameterTypes9 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _getTemplateParameterTypes10 = new Class[] {
		long.class, long.class, String.class, boolean.class
	};
	private static final Class<?>[]
		_getTemplateByExternalReferenceCodeParameterTypes11 = new Class[] {
			String.class, long.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes12 =
		new Class[] {long.class, long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getTemplatesParameterTypes13 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			boolean.class, int.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes14 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			int.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes15 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			String.class, int.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes16 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			String.class, String.class, int.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes17 =
		new Class[] {
			long.class, long[].class, long[].class, long[].class, long.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getTemplatesByClassPKParameterTypes18 =
		new Class[] {long.class, long.class, long.class, long.class, int.class};
	private static final Class<?>[]
		_getTemplatesByStructureClassNameIdParameterTypes19 = new Class[] {
			long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getTemplatesByStructureClassNameIdCountParameterTypes20 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[] _getTemplatesCountParameterTypes21 =
		new Class[] {
			long.class, long[].class, long[].class, long[].class, long.class
		};
	private static final Class<?>[] _revertTemplateParameterTypes22 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _searchParameterTypes23 = new Class[] {
		long.class, long.class, long.class, long.class, long.class,
		String.class, String.class, String.class, int.class, int.class,
		int.class, com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes24 = new Class[] {
		long.class, long.class, long.class, long.class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes25 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, int.class, int.class,
		int.class, com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes26 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes27 = new Class[] {
		long.class, long.class, long.class, long.class, long.class,
		String.class, String.class, String.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes28 = new Class[] {
		long.class, long.class, long.class, long.class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		int.class, boolean.class
	};
	private static final Class<?>[] _searchCountParameterTypes29 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes30 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		int.class, boolean.class
	};
	private static final Class<?>[] _updateTemplateParameterTypes31 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			String.class, String.class, String.class, String.class,
			boolean.class, boolean.class, String.class, java.io.File.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateTemplateParameterTypes32 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			String.class, String.class, String.class, String.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}