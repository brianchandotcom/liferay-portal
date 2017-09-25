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

package com.liferay.layout.page.template.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.service.LayoutPageTemplateServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link LayoutPageTemplateServiceUtil} service utility. The
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
 * @see LayoutPageTemplateServiceSoap
 * @see HttpPrincipal
 * @see LayoutPageTemplateServiceUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateServiceHttp {
	public static com.liferay.layout.page.template.model.LayoutPageTemplate addLayoutPageTemplate(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateFolderId, java.lang.String name,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"addLayoutPageTemplate",
					_addLayoutPageTemplateParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateFolderId, name, serviceContext);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplate deleteLayoutPageTemplate(
		HttpPrincipal httpPrincipal, long pageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"deleteLayoutPageTemplate",
					_deleteLayoutPageTemplateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					pageTemplateId);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> deletePageTemplates(
		HttpPrincipal httpPrincipal, long[] pageTemplatesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"deletePageTemplates", _deletePageTemplatesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					pageTemplatesIds);

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

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplate fetchLayoutPageTemplate(
		HttpPrincipal httpPrincipal, long pageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"fetchLayoutPageTemplate",
					_fetchLayoutPageTemplateParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					pageTemplateId);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> fetchPageTemplates(
		HttpPrincipal httpPrincipal, long layoutPageTemplateFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"fetchPageTemplates", _fetchPageTemplatesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateFolderId);

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

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getLayoutPageTemplateFoldersCount(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateFolderId) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"getLayoutPageTemplateFoldersCount",
					_getLayoutPageTemplateFoldersCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateFolderId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getLayoutPageTemplateFoldersCount(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateFolderId, java.lang.String name) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"getLayoutPageTemplateFoldersCount",
					_getLayoutPageTemplateFoldersCountParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateFolderId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getPageTemplates(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"getPageTemplates", _getPageTemplatesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateFolderId, start, end);

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

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getPageTemplates(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"getPageTemplates", _getPageTemplatesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateFolderId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getPageTemplates(
		HttpPrincipal httpPrincipal, long groupId,
		long layoutPageTemplateFolderId, java.lang.String name, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"getPageTemplates", _getPageTemplatesParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateFolderId, name, start, end,
					orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplate updateLayoutPageTemplate(
		HttpPrincipal httpPrincipal, long pageTemplateId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateServiceUtil.class,
					"updateLayoutPageTemplate",
					_updateLayoutPageTemplateParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					pageTemplateId, name);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutPageTemplateServiceHttp.class);
	private static final Class<?>[] _addLayoutPageTemplateParameterTypes0 = new Class[] {
			long.class, long.class, java.lang.String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteLayoutPageTemplateParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _deletePageTemplatesParameterTypes2 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _fetchLayoutPageTemplateParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchPageTemplatesParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getLayoutPageTemplateFoldersCountParameterTypes5 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getLayoutPageTemplateFoldersCountParameterTypes6 =
		new Class[] { long.class, long.class, java.lang.String.class };
	private static final Class<?>[] _getPageTemplatesParameterTypes7 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[] _getPageTemplatesParameterTypes8 = new Class[] {
			long.class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getPageTemplatesParameterTypes9 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _updateLayoutPageTemplateParameterTypes10 = new Class[] {
			long.class, java.lang.String.class
		};
}