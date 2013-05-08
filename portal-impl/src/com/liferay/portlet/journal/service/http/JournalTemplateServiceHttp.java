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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.journal.service.JournalTemplateServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.journal.service.JournalTemplateServiceUtil} service utility. The
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
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplateServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.journal.service.JournalTemplateServiceUtil
 * @generated
 */
public class JournalTemplateServiceHttp {
	public static com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String templateId,
		boolean autoTemplateId, java.lang.String structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"addTemplate", _addTemplateParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					templateId, autoTemplateId, structureId, nameMap,
					descriptionMap, xsl, formatXsl, langType, cacheable,
					smallImage, smallImageURL, smallFile, serviceContext);

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

			return (com.liferay.portlet.journal.model.JournalTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplate addTemplate(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String templateId,
		boolean autoTemplateId, java.lang.String structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"addTemplate", _addTemplateParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					templateId, autoTemplateId, structureId, nameMap,
					descriptionMap, xsl, formatXsl, langType, cacheable,
					serviceContext);

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

			return (com.liferay.portlet.journal.model.JournalTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplate copyTemplate(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String oldTemplateId, java.lang.String newTemplateId,
		boolean autoTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"copyTemplate", _copyTemplateParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					oldTemplateId, newTemplateId, autoTemplateId);

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

			return (com.liferay.portlet.journal.model.JournalTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteTemplate(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"deleteTemplate", _deleteTemplateParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
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

	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> getStructureTemplates(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"getStructureTemplates",
					_getStructureTemplatesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					structureId);

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

			return (java.util.List<com.liferay.portlet.journal.model.JournalTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String templateId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"getTemplate", _getTemplateParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
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

			return (com.liferay.portlet.journal.model.JournalTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplate getTemplate(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String templateId,
		boolean includeGlobalTemplates)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"getTemplate", _getTemplateParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					templateId, includeGlobalTemplates);

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

			return (com.liferay.portlet.journal.model.JournalTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		java.lang.String keywords, java.lang.String structureId,
		java.lang.String structureIdComparator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"search", _searchParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, keywords, structureId,
					structureIdComparator, start, end, obc);

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

			return (java.util.List<com.liferay.portlet.journal.model.JournalTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalTemplate> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		java.lang.String templateId, java.lang.String structureId,
		java.lang.String structureIdComparator, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"search", _searchParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, templateId, structureId,
					structureIdComparator, name, description, andOperator,
					start, end, obc);

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

			return (java.util.List<com.liferay.portlet.journal.model.JournalTemplate>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, java.lang.String keywords,
		java.lang.String structureId, java.lang.String structureIdComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"searchCount", _searchCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, keywords, structureId,
					structureIdComparator);

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
		long[] groupIds, java.lang.String templateId,
		java.lang.String structureId, java.lang.String structureIdComparator,
		java.lang.String name, java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"searchCount", _searchCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, templateId, structureId,
					structureIdComparator, name, description, andOperator);

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

	public static com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String templateId,
		java.lang.String structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable, boolean smallImage, java.lang.String smallImageURL,
		java.io.File smallFile,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"updateTemplate", _updateTemplateParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					templateId, structureId, nameMap, descriptionMap, xsl,
					formatXsl, langType, cacheable, smallImage, smallImageURL,
					smallFile, serviceContext);

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

			return (com.liferay.portlet.journal.model.JournalTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.journal.model.JournalTemplate updateTemplate(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String templateId,
		java.lang.String structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsl, boolean formatXsl, java.lang.String langType,
		boolean cacheable,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(JournalTemplateServiceUtil.class,
					"updateTemplate", _updateTemplateParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					templateId, structureId, nameMap, descriptionMap, xsl,
					formatXsl, langType, cacheable, serviceContext);

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

			return (com.liferay.portlet.journal.model.JournalTemplate)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalTemplateServiceHttp.class);
	private static final Class<?>[] _addTemplateParameterTypes0 = new Class[] {
			long.class, java.lang.String.class, boolean.class,
			java.lang.String.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, boolean.class, java.lang.String.class,
			boolean.class, boolean.class, java.lang.String.class,
			java.io.File.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addTemplateParameterTypes1 = new Class[] {
			long.class, java.lang.String.class, boolean.class,
			java.lang.String.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, boolean.class, java.lang.String.class,
			boolean.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _copyTemplateParameterTypes2 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			boolean.class
		};
	private static final Class<?>[] _deleteTemplateParameterTypes3 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getStructureTemplatesParameterTypes4 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getTemplateParameterTypes5 = new Class[] {
			long.class, java.lang.String.class
		};
	private static final Class<?>[] _getTemplateParameterTypes6 = new Class[] {
			long.class, java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _searchParameterTypes7 = new Class[] {
			long.class, long[].class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes8 = new Class[] {
			long.class, long[].class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchCountParameterTypes9 = new Class[] {
			long.class, long[].class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes10 = new Class[] {
			long.class, long[].class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _updateTemplateParameterTypes11 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			boolean.class, java.lang.String.class, boolean.class, boolean.class,
			java.lang.String.class, java.io.File.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateTemplateParameterTypes12 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			boolean.class, java.lang.String.class, boolean.class,
			com.liferay.portal.service.ServiceContext.class
		};
}