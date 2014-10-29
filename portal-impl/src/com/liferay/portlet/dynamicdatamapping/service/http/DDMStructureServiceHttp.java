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

package com.liferay.portlet.dynamicdatamapping.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil} service utility. The
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
 * @see DDMStructureServiceSoap
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil
 * @generated
 */
@ProviderType
public class DDMStructureServiceHttp {
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		HttpPrincipal httpPrincipal, long userId, long groupId,
		long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"addStructure", _addStructureParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, classNameId, nameMap, descriptionMap, ddmForm,
					serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		HttpPrincipal httpPrincipal, long userId, long groupId,
		long classNameId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"addStructure", _addStructureParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, classNameId, nameMap, descriptionMap, xsd,
					serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		HttpPrincipal httpPrincipal, long groupId, long parentStructureId,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"addStructure", _addStructureParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentStructureId, classNameId, structureKey, nameMap,
					descriptionMap, ddmForm, storageType, type, serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		HttpPrincipal httpPrincipal, long groupId, long parentStructureId,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"addStructure", _addStructureParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentStructureId, classNameId, structureKey, nameMap,
					descriptionMap, xsd, storageType, type, serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		HttpPrincipal httpPrincipal, long userId, long groupId,
		java.lang.String parentStructureKey, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portlet.dynamicdatamapping.model.DDMForm ddmForm,
		java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"addStructure", _addStructureParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, parentStructureKey, classNameId, structureKey,
					nameMap, descriptionMap, ddmForm, storageType, type,
					serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		HttpPrincipal httpPrincipal, long userId, long groupId,
		java.lang.String parentStructureKey, long classNameId,
		java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd, java.lang.String storageType, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"addStructure", _addStructureParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					groupId, parentStructureKey, classNameId, structureKey,
					nameMap, descriptionMap, xsd, storageType, type,
					serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		HttpPrincipal httpPrincipal, long structureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"copyStructure", _copyStructureParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					structureId, nameMap, descriptionMap, serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure copyStructure(
		HttpPrincipal httpPrincipal, long structureId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"copyStructure", _copyStructureParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					structureId, serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteStructure(HttpPrincipal httpPrincipal,
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"deleteStructure", _deleteStructureParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					structureId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure fetchStructure(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"fetchStructure", _fetchStructureParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, structureKey);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getJournalFolderStructures(
		HttpPrincipal httpPrincipal, long[] groupIds, long journalFolderId,
		int restrictionType)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"getJournalFolderStructures",
					_getJournalFolderStructuresParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					groupIds, journalFolderId, restrictionType);

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

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		HttpPrincipal httpPrincipal, long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"getStructure", _getStructureParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					structureId);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"getStructure", _getStructureParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, structureKey);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		HttpPrincipal httpPrincipal, long groupId, long classNameId,
		java.lang.String structureKey, boolean includeAncestorStructures)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"getStructure", _getStructureParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					classNameId, structureKey, includeAncestorStructures);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		HttpPrincipal httpPrincipal, long groupId) {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"getStructures", _getStructuresParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		HttpPrincipal httpPrincipal, long[] groupIds) {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"getStructures", _getStructuresParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		HttpPrincipal httpPrincipal, long[] groupIds, long classNameId) {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"getStructures", _getStructuresParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					groupIds, classNameId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructures(
		HttpPrincipal httpPrincipal, long[] groupIds, long classNameId,
		int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"getStructures", _getStructuresParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					groupIds, classNameId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"search", _searchParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, keywords, start, end,
					orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> search(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType, int type,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"search", _searchParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, name, description,
					storageType, type, andOperator, start, end,
					orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, long[] classNameIds, java.lang.String keywords) {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"searchCount", _searchCountParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, keywords);

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

	public static int searchCount(HttpPrincipal httpPrincipal, long companyId,
		long[] groupIds, long[] classNameIds, java.lang.String name,
		java.lang.String description, java.lang.String storageType, int type,
		boolean andOperator) {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"searchCount", _searchCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupIds, classNameIds, name, description,
					storageType, type, andOperator);

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

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		HttpPrincipal httpPrincipal, long groupId, long parentStructureId,
		long classNameId, java.lang.String structureKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"updateStructure", _updateStructureParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					parentStructureId, classNameId, structureKey, nameMap,
					descriptionMap, xsd, serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		HttpPrincipal httpPrincipal, long structureId, long parentStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(DDMStructureServiceUtil.class,
					"updateStructure", _updateStructureParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					structureId, parentStructureId, nameMap, descriptionMap,
					xsd, serviceContext);

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

			return (com.liferay.portlet.dynamicdatamapping.model.DDMStructure)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureServiceHttp.class);
	private static final Class<?>[] _addStructureParameterTypes0 = new Class[] {
			long.class, long.class, long.class, java.util.Map.class,
			java.util.Map.class,
			com.liferay.portlet.dynamicdatamapping.model.DDMForm.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addStructureParameterTypes1 = new Class[] {
			long.class, long.class, long.class, java.util.Map.class,
			java.util.Map.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addStructureParameterTypes2 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.util.Map.class, java.util.Map.class,
			com.liferay.portlet.dynamicdatamapping.model.DDMForm.class,
			java.lang.String.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addStructureParameterTypes3 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			java.lang.String.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addStructureParameterTypes4 = new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			java.lang.String.class, java.util.Map.class, java.util.Map.class,
			com.liferay.portlet.dynamicdatamapping.model.DDMForm.class,
			java.lang.String.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _addStructureParameterTypes5 = new Class[] {
			long.class, long.class, java.lang.String.class, long.class,
			java.lang.String.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, java.lang.String.class, int.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _copyStructureParameterTypes6 = new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _copyStructureParameterTypes7 = new Class[] {
			long.class, com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteStructureParameterTypes8 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchStructureParameterTypes9 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getJournalFolderStructuresParameterTypes10 = new Class[] {
			long[].class, long.class, int.class
		};
	private static final Class<?>[] _getStructureParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getStructureParameterTypes12 = new Class[] {
			long.class, long.class, java.lang.String.class
		};
	private static final Class<?>[] _getStructureParameterTypes13 = new Class[] {
			long.class, long.class, java.lang.String.class, boolean.class
		};
	private static final Class<?>[] _getStructuresParameterTypes14 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getStructuresParameterTypes15 = new Class[] {
			long[].class
		};
	private static final Class<?>[] _getStructuresParameterTypes16 = new Class[] {
			long[].class, long.class
		};
	private static final Class<?>[] _getStructuresParameterTypes17 = new Class[] {
			long[].class, long.class, int.class, int.class
		};
	private static final Class<?>[] _searchParameterTypes18 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchParameterTypes19 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, int.class,
			boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _searchCountParameterTypes20 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class
		};
	private static final Class<?>[] _searchCountParameterTypes21 = new Class[] {
			long.class, long[].class, long[].class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class, int.class,
			boolean.class
		};
	private static final Class<?>[] _updateStructureParameterTypes22 = new Class[] {
			long.class, long.class, long.class, java.lang.String.class,
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
	private static final Class<?>[] _updateStructureParameterTypes23 = new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class,
			com.liferay.portal.service.ServiceContext.class
		};
}