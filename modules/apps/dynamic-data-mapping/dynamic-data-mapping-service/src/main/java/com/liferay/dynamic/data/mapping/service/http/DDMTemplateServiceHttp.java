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
 * @see DDMTemplateServiceSoap
 * @generated
 */
public class DDMTemplateServiceHttp {

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			addTemplate(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				long classPK, long resourceClassNameId,
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
				methodKey, groupId, classNameId, classPK, resourceClassNameId,
				nameMap, descriptionMap, type, mode, language, script,
				serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			addTemplate(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				long classPK, long resourceClassNameId, String templateKey,
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
				methodKey, groupId, classNameId, classPK, resourceClassNameId,
				templateKey, nameMap, descriptionMap, type, mode, language,
				script, cacheable, smallImage, smallImageURL, smallImageFile,
				serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			copyTemplate(
				HttpPrincipal httpPrincipal, long templateId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "copyTemplate",
				_copyTemplateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, nameMap, descriptionMap, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			copyTemplate(
				HttpPrincipal httpPrincipal, long templateId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "copyTemplate",
				_copyTemplateParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> copyTemplates(
				HttpPrincipal httpPrincipal, long classNameId, long oldClassPK,
				long resourceClassNameId, long newClassPK, String type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "copyTemplates",
				_copyTemplatesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, classNameId, oldClassPK, resourceClassNameId,
				newClassPK, type, serviceContext);

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

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			fetchTemplate(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "fetchTemplate",
				_fetchTemplateParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, templateKey);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplate
			getTemplate(HttpPrincipal httpPrincipal, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplate",
				_getTemplateParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getTemplateParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, templateKey);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getTemplateParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, templateKey,
				includeAncestorTemplates);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMTemplate> getTemplates(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			long classNameId, long resourceClassNameId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "getTemplates",
				_getTemplatesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, resourceClassNameId,
				status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getTemplatesParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, includeAncestorTemplates, status);

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

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getTemplatesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getTemplatesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, type, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getTemplatesParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, type, mode, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getTemplatesByClassPKParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classPK, resourceClassNameId,
				status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_getTemplatesByStructureClassNameIdParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, structureClassNameId, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getTemplatesByStructureClassNameIdCount(
		HttpPrincipal httpPrincipal, long groupId, long structureClassNameId,
		int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class,
				"getTemplatesByStructureClassNameIdCount",
				_getTemplatesByStructureClassNameIdCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, structureClassNameId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void revertTemplate(
			HttpPrincipal httpPrincipal, long templateId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "revertTemplate",
				_revertTemplateParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, version, serviceContext);

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
				_searchParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, keywords, type, mode, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_searchParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_searchParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, keywords, type, mode, status, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_searchParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long classNameId, long classPK, long resourceClassNameId,
		String keywords, String type, String mode, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "searchCount",
				_searchCountParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, keywords, type, mode, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_searchCountParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, classNameId, classPK,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, long[] classPKs, long resourceClassNameId,
		String keywords, String type, String mode, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMTemplateServiceUtil.class, "searchCount",
				_searchCountParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, keywords, type, mode, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_searchCountParameterTypes26);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameIds, classPKs,
				resourceClassNameId, name, description, type, mode, language,
				status, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_updateTemplateParameterTypes27);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, classPK, nameMap, descriptionMap, type,
				mode, language, script, cacheable, smallImage, smallImageURL,
				smallImageFile, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
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
				_updateTemplateParameterTypes28);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, templateId, classPK, nameMap, descriptionMap, type,
				mode, language, script, cacheable, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMTemplate)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMTemplateServiceHttp.class);

	private static final Class<?>[] _addTemplateParameterTypes0 = new Class[] {
		long.class, long.class, long.class, long.class, java.util.Map.class,
		java.util.Map.class, String.class, String.class, String.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addTemplateParameterTypes1 = new Class[] {
		long.class, long.class, long.class, long.class, String.class,
		java.util.Map.class, java.util.Map.class, String.class, String.class,
		String.class, String.class, boolean.class, boolean.class, String.class,
		java.io.File.class,
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
	private static final Class<?>[] _fetchTemplateParameterTypes6 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _getTemplateParameterTypes7 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getTemplateParameterTypes8 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _getTemplateParameterTypes9 = new Class[] {
		long.class, long.class, String.class, boolean.class
	};
	private static final Class<?>[] _getTemplatesParameterTypes10 =
		new Class[] {long.class, long.class, long.class, long.class, int.class};
	private static final Class<?>[] _getTemplatesParameterTypes11 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			boolean.class, int.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes12 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			int.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes13 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			String.class, int.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes14 =
		new Class[] {
			long.class, long.class, long.class, long.class, long.class,
			String.class, String.class, int.class
		};
	private static final Class<?>[] _getTemplatesByClassPKParameterTypes15 =
		new Class[] {long.class, long.class, long.class, long.class, int.class};
	private static final Class<?>[]
		_getTemplatesByStructureClassNameIdParameterTypes16 = new Class[] {
			long.class, long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[]
		_getTemplatesByStructureClassNameIdCountParameterTypes17 = new Class[] {
			long.class, long.class, int.class
		};
	private static final Class<?>[] _revertTemplateParameterTypes18 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _searchParameterTypes19 = new Class[] {
		long.class, long.class, long.class, long.class, long.class,
		String.class, String.class, String.class, int.class, int.class,
		int.class, com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes20 = new Class[] {
		long.class, long.class, long.class, long.class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes21 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, int.class, int.class,
		int.class, com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes22 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes23 = new Class[] {
		long.class, long.class, long.class, long.class, long.class,
		String.class, String.class, String.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes24 = new Class[] {
		long.class, long.class, long.class, long.class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		int.class, boolean.class
	};
	private static final Class<?>[] _searchCountParameterTypes25 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes26 = new Class[] {
		long.class, long[].class, long[].class, long[].class, long.class,
		String.class, String.class, String.class, String.class, String.class,
		int.class, boolean.class
	};
	private static final Class<?>[] _updateTemplateParameterTypes27 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			String.class, String.class, String.class, String.class,
			boolean.class, boolean.class, String.class, java.io.File.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateTemplateParameterTypes28 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			String.class, String.class, String.class, String.class,
			boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}