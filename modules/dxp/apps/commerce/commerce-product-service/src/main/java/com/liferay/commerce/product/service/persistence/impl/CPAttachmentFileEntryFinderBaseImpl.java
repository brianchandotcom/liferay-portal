/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Marco Leo
 * @generated
 */
public class CPAttachmentFileEntryFinderBaseImpl extends BasePersistenceImpl<CPAttachmentFileEntry> {
	public CPAttachmentFileEntryFinderBaseImpl() {
		setModelClass(CPAttachmentFileEntry.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCPAttachmentFileEntryPersistence().getBadColumnNames();
	}

	/**
	 * Returns the cp attachment file entry persistence.
	 *
	 * @return the cp attachment file entry persistence
	 */
	public CPAttachmentFileEntryPersistence getCPAttachmentFileEntryPersistence() {
		return cpAttachmentFileEntryPersistence;
	}

	/**
	 * Sets the cp attachment file entry persistence.
	 *
	 * @param cpAttachmentFileEntryPersistence the cp attachment file entry persistence
	 */
	public void setCPAttachmentFileEntryPersistence(
		CPAttachmentFileEntryPersistence cpAttachmentFileEntryPersistence) {
		this.cpAttachmentFileEntryPersistence = cpAttachmentFileEntryPersistence;
	}

	@BeanReference(type = CPAttachmentFileEntryPersistence.class)
	protected CPAttachmentFileEntryPersistence cpAttachmentFileEntryPersistence;
	private static final Log _log = LogFactoryUtil.getLog(CPAttachmentFileEntryFinderBaseImpl.class);
}