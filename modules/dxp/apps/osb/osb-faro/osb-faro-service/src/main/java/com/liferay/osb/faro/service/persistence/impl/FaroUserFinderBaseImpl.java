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

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.service.persistence.FaroUserPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthew Kong
 * @generated
 */
public class FaroUserFinderBaseImpl extends BasePersistenceImpl<FaroUser> {

	public FaroUserFinderBaseImpl() {
		setModelClass(FaroUser.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getFaroUserPersistence().getBadColumnNames();
	}

	/**
	 * Returns the faro user persistence.
	 *
	 * @return the faro user persistence
	 */
	public FaroUserPersistence getFaroUserPersistence() {
		return faroUserPersistence;
	}

	/**
	 * Sets the faro user persistence.
	 *
	 * @param faroUserPersistence the faro user persistence
	 */
	public void setFaroUserPersistence(
		FaroUserPersistence faroUserPersistence) {

		this.faroUserPersistence = faroUserPersistence;
	}

	@BeanReference(type = FaroUserPersistence.class)
	protected FaroUserPersistence faroUserPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		FaroUserFinderBaseImpl.class);

}