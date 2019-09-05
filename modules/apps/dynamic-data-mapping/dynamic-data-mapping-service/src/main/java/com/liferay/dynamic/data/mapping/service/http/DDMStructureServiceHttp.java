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

import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDMStructureServiceUtil</code> service
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
 * @see DDMStructureServiceSoap
 * @generated
 */
public class DDMStructureServiceHttp {

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			addStructure(
				HttpPrincipal httpPrincipal, long groupId,
				long parentStructureId, long classNameId, String structureKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "addStructure",
				_addStructureParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentStructureId, classNameId,
				structureKey, nameMap, descriptionMap, ddmForm, ddmFormLayout,
				storageType, type, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			addStructure(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "addStructure",
				_addStructureParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, nameMap, descriptionMap,
				ddmForm, ddmFormLayout, storageType, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			addStructure(
				HttpPrincipal httpPrincipal, long groupId,
				String parentStructureKey, long classNameId,
				String structureKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				String storageType, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "addStructure",
				_addStructureParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentStructureKey, classNameId,
				structureKey, nameMap, descriptionMap, ddmForm, ddmFormLayout,
				storageType, type, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			copyStructure(
				HttpPrincipal httpPrincipal, long structureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "copyStructure",
				_copyStructureParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, structureId, nameMap, descriptionMap,
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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			copyStructure(
				HttpPrincipal httpPrincipal, long structureId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "copyStructure",
				_copyStructureParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, structureId, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteStructure(
			HttpPrincipal httpPrincipal, long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "deleteStructure",
				_deleteStructureParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, structureId);

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

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			fetchStructure(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "fetchStructure",
				_fetchStructureParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, structureKey);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			fetchStructure(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				String structureKey, boolean includeAncestorStructures)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "fetchStructure",
				_fetchStructureParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, structureKey,
				includeAncestorStructures);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			getStructure(HttpPrincipal httpPrincipal, long structureId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructure",
				_getStructureParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, structureId);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			getStructure(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructure",
				_getStructureParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, structureKey);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			getStructure(
				HttpPrincipal httpPrincipal, long groupId, long classNameId,
				String structureKey, boolean includeAncestorStructures)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructure",
				_getStructureParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, classNameId, structureKey,
				includeAncestorStructures);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long classNameId, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructures",
				_getStructuresParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long classNameId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructures",
				_getStructuresParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, status, start, end,
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
				<com.liferay.dynamic.data.mapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long classNameId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructures",
				_getStructuresParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, start, end,
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
				<com.liferay.dynamic.data.mapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> getStructures(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long classNameId, String keywords, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructures",
				_getStructuresParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, keywords, status,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getStructuresCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long classNameId) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructuresCount",
				_getStructuresCountParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId);

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

	public static int getStructuresCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long classNameId, String keywords, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "getStructuresCount",
				_getStructuresCountParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, keywords, status);

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

	public static void revertStructure(
			HttpPrincipal httpPrincipal, long structureId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "revertStructure",
				_revertStructureParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, structureId, version, serviceContext);

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
		<com.liferay.dynamic.data.mapping.model.DDMStructure> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long classNameId, String keywords, int type, int status, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "search",
				_searchParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, keywords, type,
				status, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long classNameId, String keywords, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "search",
				_searchParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, keywords, status,
				start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMStructure> search(
			HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
			long classNameId, String name, String description,
			String storageType, int type, int status, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMStructure>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "search",
				_searchParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, name, description,
				storageType, type, status, andOperator, start, end,
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
				<com.liferay.dynamic.data.mapping.model.DDMStructure>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long[] groupIds,
		long classNameId, String keywords, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "searchCount",
				_searchCountParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, keywords, status);

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
		long classNameId, String keywords, int type, int status) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "searchCount",
				_searchCountParameterTypes22);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, keywords, type,
				status);

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
		long classNameId, String name, String description, String storageType,
		int type, int status, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "searchCount",
				_searchCountParameterTypes23);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupIds, classNameId, name, description,
				storageType, type, status, andOperator);

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

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			updateStructure(
				HttpPrincipal httpPrincipal, long groupId,
				long parentStructureId, long classNameId, String structureKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "updateStructure",
				_updateStructureParameterTypes24);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, parentStructureId, classNameId,
				structureKey, nameMap, descriptionMap, ddmForm, ddmFormLayout,
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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.dynamic.data.mapping.model.DDMStructure
			updateStructure(
				HttpPrincipal httpPrincipal, long structureId,
				long parentStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDMStructureServiceUtil.class, "updateStructure",
				_updateStructureParameterTypes25);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, structureId, parentStructureId, nameMap,
				descriptionMap, ddmForm, ddmFormLayout, serviceContext);

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

			return (com.liferay.dynamic.data.mapping.model.DDMStructure)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMStructureServiceHttp.class);

	private static final Class<?>[] _addStructureParameterTypes0 = new Class[] {
		long.class, long.class, long.class, String.class, java.util.Map.class,
		java.util.Map.class,
		com.liferay.dynamic.data.mapping.model.DDMForm.class,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout.class,
		String.class, int.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addStructureParameterTypes1 = new Class[] {
		long.class, long.class, java.util.Map.class, java.util.Map.class,
		com.liferay.dynamic.data.mapping.model.DDMForm.class,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout.class,
		String.class, com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _addStructureParameterTypes2 = new Class[] {
		long.class, String.class, long.class, String.class, java.util.Map.class,
		java.util.Map.class,
		com.liferay.dynamic.data.mapping.model.DDMForm.class,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout.class,
		String.class, int.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _copyStructureParameterTypes3 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _copyStructureParameterTypes4 =
		new Class[] {
			long.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteStructureParameterTypes5 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchStructureParameterTypes6 =
		new Class[] {long.class, long.class, String.class};
	private static final Class<?>[] _fetchStructureParameterTypes7 =
		new Class[] {long.class, long.class, String.class, boolean.class};
	private static final Class<?>[] _getStructureParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getStructureParameterTypes9 = new Class[] {
		long.class, long.class, String.class
	};
	private static final Class<?>[] _getStructureParameterTypes10 =
		new Class[] {long.class, long.class, String.class, boolean.class};
	private static final Class<?>[] _getStructuresParameterTypes11 =
		new Class[] {long.class, long[].class, long.class, int.class};
	private static final Class<?>[] _getStructuresParameterTypes12 =
		new Class[] {
			long.class, long[].class, long.class, int.class, int.class,
			int.class, com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getStructuresParameterTypes13 =
		new Class[] {
			long.class, long[].class, long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getStructuresParameterTypes14 =
		new Class[] {
			long.class, long[].class, long.class, String.class, int.class,
			int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getStructuresCountParameterTypes15 =
		new Class[] {long.class, long[].class, long.class};
	private static final Class<?>[] _getStructuresCountParameterTypes16 =
		new Class[] {
			long.class, long[].class, long.class, String.class, int.class
		};
	private static final Class<?>[] _revertStructureParameterTypes17 =
		new Class[] {
			long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _searchParameterTypes18 = new Class[] {
		long.class, long[].class, long.class, String.class, int.class,
		int.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes19 = new Class[] {
		long.class, long[].class, long.class, String.class, int.class,
		int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes20 = new Class[] {
		long.class, long[].class, long.class, String.class, String.class,
		String.class, int.class, int.class, boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes21 = new Class[] {
		long.class, long[].class, long.class, String.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes22 = new Class[] {
		long.class, long[].class, long.class, String.class, int.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes23 = new Class[] {
		long.class, long[].class, long.class, String.class, String.class,
		String.class, int.class, int.class, boolean.class
	};
	private static final Class<?>[] _updateStructureParameterTypes24 =
		new Class[] {
			long.class, long.class, long.class, String.class,
			java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.model.DDMForm.class,
			com.liferay.dynamic.data.mapping.model.DDMFormLayout.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateStructureParameterTypes25 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			com.liferay.dynamic.data.mapping.model.DDMForm.class,
			com.liferay.dynamic.data.mapping.model.DDMFormLayout.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}