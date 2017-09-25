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

import com.liferay.layout.page.template.service.LayoutPageTemplateFragmentServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link LayoutPageTemplateFragmentServiceUtil} service utility. The
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
 * @see LayoutPageTemplateFragmentServiceSoap
 * @see HttpPrincipal
 * @see LayoutPageTemplateFragmentServiceUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentServiceHttp {
	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		HttpPrincipal httpPrincipal,
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateFragmentServiceUtil.class,
					"addLayoutPageTemplateFragment",
					_addLayoutPageTemplateFragmentParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					layoutPageTemplateFragment, serviceContext);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplateFragment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		HttpPrincipal httpPrincipal, long groupId, long layoutPageTemplateId,
		long fragmentId, int position,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateFragmentServiceUtil.class,
					"addLayoutPageTemplateFragment",
					_addLayoutPageTemplateFragmentParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateId, fragmentId, position, serviceContext);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplateFragment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> deleteByLayoutPageTemplate(
		HttpPrincipal httpPrincipal, long groupId, long layoutPageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateFragmentServiceUtil.class,
					"deleteByLayoutPageTemplate",
					_deleteByLayoutPageTemplateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateId);

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

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
		HttpPrincipal httpPrincipal, long groupId, long layoutPageTemplateId,
		long fragmentId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateFragmentServiceUtil.class,
					"deleteLayoutPageTemplateFragment",
					_deleteLayoutPageTemplateFragmentParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateId, fragmentId, serviceContext);

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

			return (com.liferay.layout.page.template.model.LayoutPageTemplateFragment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> getLayoutPageTemplateFragmentsByPageTemplate(
		HttpPrincipal httpPrincipal, long groupId, long layoutPageTemplateId) {
		try {
			MethodKey methodKey = new MethodKey(LayoutPageTemplateFragmentServiceUtil.class,
					"getLayoutPageTemplateFragmentsByPageTemplate",
					_getLayoutPageTemplateFragmentsByPageTemplateParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					layoutPageTemplateId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutPageTemplateFragmentServiceHttp.class);
	private static final Class<?>[] _addLayoutPageTemplateFragmentParameterTypes0 =
		new Class[] {
			com.liferay.layout.page.template.model.LayoutPageTemplateFragment.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addLayoutPageTemplateFragmentParameterTypes1 =
		new Class[] {
			long.class, long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteByLayoutPageTemplateParameterTypes2 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _deleteLayoutPageTemplateFragmentParameterTypes3 =
		new Class[] {
			long.class, long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _getLayoutPageTemplateFragmentsByPageTemplateParameterTypes4 =
		new Class[] { long.class, long.class };
}