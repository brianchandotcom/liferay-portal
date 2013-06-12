/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
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
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil
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

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate copyTemplate(
		HttpPrincipal httpPrincipal, long templateId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"copyTemplate", _copyTemplateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					templateId, nameMap, descriptionMap, serviceContext);

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

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplate copyTemplate(
		HttpPrincipal httpPrincipal, long templateId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"copyTemplate", _copyTemplateParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					templateId, serviceContext);

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
					"copyTemplates", _copyTemplatesParameterTypes4);

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
					"deleteTemplate", _deleteTemplateParameterTypes5);

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
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"fetchTemplate", _fetchTemplateParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, templateKey);

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
		HttpPrincipal httpPrincipal, long templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplate", _getTemplateParameterTypes7);

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
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		java.lang.String templateKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplate", _getTemplateParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, templateKey);

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
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		java.lang.String templateKey, boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplate", _getTemplateParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, templateKey, includeGlobalTemplates);

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
					"getTemplates", _getTemplatesParameterTypes10);

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
					"getTemplates", _getTemplatesParameterTypes11);

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
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		long classPK, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplates", _getTemplatesParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, classPK, type);

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
		long classPK, java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplates", _getTemplatesParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
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

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplatesByClassPK(
		HttpPrincipal httpPrincipal, long groupId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplatesByClassPK",
					_getTemplatesByClassPKParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classPK);

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

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplate> getTemplatesByStructureClassNameId(
		HttpPrincipal httpPrincipal, long groupId, long structureClassNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"getTemplatesByStructureClassNameId",
					_getTemplatesByStructureClassNameIdParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					structureClassNameId, start, end, orderByComparator);

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
					"search", _searchParameterTypes16);

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
					"search", _searchParameterTypes17);

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
		long[] classNameIds, long[] classPKs, java.lang.String keywords,
		java.lang.String type, java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"search", _searchParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, classPKs, keywords,
					type, mode, start, end, orderByComparator);

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
		long[] classNameIds, long[] classPKs, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String mode, java.lang.String language, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"search", _searchParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, classPKs, name,
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
					"searchCount", _searchCountParameterTypes20);

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
					"searchCount", _searchCountParameterTypes21);

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
		long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String keywords, java.lang.String type, java.lang.String mode)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"searchCount", _searchCountParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, classPKs, keywords,
					type, mode);

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
		long[] groupIds, long[] classNameIds, long[] classPKs,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String mode,
		java.lang.String language, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(DDMTemplateServiceUtil.class,
					"searchCount", _searchCountParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, classPKs, name,
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
					"updateTemplate", _updateTemplateParameterTypes24);

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
	private static final Class<?>[] _copyTemplateParameterTypes2 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _copyTemplateParameterTypes3 = new Class[] {
			long.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _copyTemplatesParameterTypes4 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteTemplateParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchTemplateParameterTypes6 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getTemplateParameterTypes7 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getTemplateParameterTypes8 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getTemplateParameterTypes9 = new Class[] {
			long.class, long.class, java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes10 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes11 = new Class[] {
			long.class, long.class, long.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes12 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getTemplatesParameterTypes13 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _getTemplatesByClassPKParameterTypes14 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getTemplatesByStructureClassNameIdParameterTypes15 =
		new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes16 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes17 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes18 = new Class[] {
			long.class, long[].class, long[].class, long[].class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes19 = new Class[] {
			long.class, long[].class, long[].class, long[].class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchCountParameterTypes20 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes21 = new Class[] {
			long.class, long.class, long.class, long.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _searchCountParameterTypes22 = new Class[] {
			long.class, long[].class, long[].class, long[].class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes23 = new Class[] {
			long.class, long[].class, long[].class, long[].class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _updateTemplateParameterTypes24 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			boolean.class, java.lang.String.class, java.io.File.class,
			com.liferay.portal.service.ServiceContext.class
		};
}