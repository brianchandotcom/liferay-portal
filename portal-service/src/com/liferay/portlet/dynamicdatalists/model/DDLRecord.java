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

package com.liferay.portlet.dynamicdatalists.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DDLRecord service. Represents a row in the &quot;DDLRecord&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordModel
 * @see com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordImpl
 * @see com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordModelImpl
 * @generated
 */
public interface DDLRecord extends DDLRecordModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portlet.dynamicdatamapping.storage.Field getField(
		java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.Serializable getFieldDataType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.dynamicdatamapping.storage.Fields getFields()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.Serializable getFieldType(java.lang.String fieldName)
		throws java.lang.Exception;

	public java.io.Serializable getFieldValue(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<java.io.Serializable> getFieldValues(
		java.lang.String fieldName, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getLatestRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}