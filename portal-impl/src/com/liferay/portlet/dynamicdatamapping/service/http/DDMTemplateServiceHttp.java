/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;

/**
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
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
 * @author    Brian Wing Shun Chan
 * @see       DDMTemplateServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil
 * @generated
 */
public class DDMTemplateServiceHttp {
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate addTemplate(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classPK,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"addTemplate", _addTemplateParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classPK, nameMap, descriptionMap, type, mode,
					language, script, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate addTemplate(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classPK, java.lang.String templateKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"addTemplate", _addTemplateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classPK, templateKey, nameMap, descriptionMap,
					type, mode, language, script, cacheable, smallImage,
					smallImageURL, smallImageFile, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> copyTemplates(
		HttpPrincipal httpPrincipal, long classNameId, long classPK,
		long newClassPK, java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"copyTemplates", _copyTemplatesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					classNameId, classPK, newClassPK, type, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteTemplate(HttpPrincipal httpPrincipal,
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"deleteTemplate", _deleteTemplateParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					templateId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate fetchTemplate(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"fetchTemplate", _fetchTemplateParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					templateKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		HttpPrincipal httpPrincipal, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplate", _getTemplateParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					templateId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplate", _getTemplateParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					templateKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate getTemplate(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String templateKey, boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplate", _getTemplateParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					templateKey, includeGlobalTemplates);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		HttpPrincipal httpPrincipal, long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplates", _getTemplatesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplates", _getTemplatesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplates(
		HttpPrincipal httpPrincipal, long classNameId, long classPK,
		java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplates", _getTemplatesParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					classNameId, classPK, type, mode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long classNameId, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"search", _searchParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, classNameId, classPK, keywords, type,
					mode, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		long classNameId, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"search", _searchParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, classNameId, classPK, name,
					description, type, mode, language, andOperator, start, end,
					orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String keywords,
		java.lang.String type, java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"search", _searchParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, classPK, keywords, type,
					mode, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"search", _searchParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, classPK, name,
					description, type, mode, language, andOperator, start, end,
					orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long groupId, long classNameId, long classPK,
		java.lang.String keywords, java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"searchCount", _searchCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, classNameId, classPK, keywords, type,
					mode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long groupId, long classNameId, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"searchCount", _searchCountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, classNameId, classPK, name,
					description, type, mode, language, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, long[] classNameIds, long classPK,
		java.lang.String keywords, java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"searchCount", _searchCountParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, classPK, keywords, type,
					mode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, long[] classNameIds, long classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"searchCount", _searchCountParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, classPK, name,
					description, type, mode, language, andOperator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate updateTemplate(
		HttpPrincipal httpPrincipal, long templateId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, java.lang.String script, boolean cacheable,
		boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallImageFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"updateTemplate", _updateTemplateParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					templateId, nameMap, descriptionMap, type, mode, language,
					script, cacheable, smallImage, smallImageURL,
					smallImageFile, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMTemplateServiceHttp.class);
	private static final Class<?>[] _addTemplateParameterTypes0 = new Class[] {
			long.class, long.class, long.class, java.util.Map.class,
			java.util.Map.class, java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addTemplateParameterTypes1 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, boolean.class,
			java.lang.String.class, java.io.File.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _copyTemplatesParameterTypes2 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteTemplateParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchTemplateParameterTypes4 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getTemplateParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getTemplateParameterTypes6 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getTemplateParameterTypes7 = new Class[] {
			long.class, java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes8 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes9 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes10 = new Class[] {
			long.class, long.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _searchParameterTypes11 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes12 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes13 = new Class[] {
			long.class, long[].class, long[].class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes14 = new Class[] {
			long.class, long[].class, long[].class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchCountParameterTypes15 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes16 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _searchCountParameterTypes17 = new Class[] {
			long.class, long[].class, long[].class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes18 = new Class[] {
			long.class, long[].class, long[].class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _updateTemplateParameterTypes19 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			boolean.class, java.lang.String.class, java.io.File.class,
			com.liferay.portal.service.ServiceContext.class
		};
}