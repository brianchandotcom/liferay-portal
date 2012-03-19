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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchPasswordPolicyRelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.service.base.PasswordPolicyRelLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.List;

/**
 * @author Scott Lee
 */
public class PasswordPolicyRelLocalServiceImpl
	extends PasswordPolicyRelLocalServiceBaseImpl {

	public PasswordPolicyRel addPasswordPolicyRel(
			long passwordPolicyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		PasswordPolicyRel passwordPolicyRel =
			passwordPolicyRelPersistence.fetchByP_C_C(
				passwordPolicyId, classNameId, classPK);

		if (passwordPolicyRel != null) {
			return null;
		}

		try {

			// Ensure that models only have one password policy

			passwordPolicyRelPersistence.removeByC_C(classNameId, classPK);
		}
		catch (NoSuchPasswordPolicyRelException nsppre) {
		}

		long passwordPolicyRelId = counterLocalService.increment();

		passwordPolicyRel = passwordPolicyRelPersistence.create(
			passwordPolicyRelId);

		passwordPolicyRel.setPasswordPolicyId(passwordPolicyId);
		passwordPolicyRel.setClassNameId(classNameId);
		passwordPolicyRel.setClassPK(classPK);

		passwordPolicyRelPersistence.update(passwordPolicyRel, false);

		return passwordPolicyRel;
	}

	public void addPasswordPolicyRels(
			long passwordPolicyId, String className, long[] classPKs)
		throws SystemException {

		for (int i = 0; i < classPKs.length; i++) {
			addPasswordPolicyRel(passwordPolicyId, className, classPKs[i]);
		}
	}

	public void deletePasswordPolicyRel(
			long passwordPolicyId, String className, long classPK)
		throws SystemException {

		try {
			long classNameId = PortalUtil.getClassNameId(className);

			PasswordPolicyRel passwordPolicyRel =
				passwordPolicyRelPersistence.findByP_C_C(
					passwordPolicyId, classNameId, classPK);

			deletePasswordPolicyRel(passwordPolicyRel);
		}
		catch (NoSuchPasswordPolicyRelException nsppre) {
		}
	}

	public void deletePasswordPolicyRel(String className, long classPK)
		throws SystemException {

		try {
			long classNameId = PortalUtil.getClassNameId(className);

			PasswordPolicyRel passwordPolicyRel =
				passwordPolicyRelPersistence.findByC_C(classNameId, classPK);

			deletePasswordPolicyRel(passwordPolicyRel);
		}
		catch (NoSuchPasswordPolicyRelException nsppre) {
		}
	}

	public void deletePasswordPolicyRels(long passwordPolicyId)
		throws SystemException {

		List<PasswordPolicyRel> passwordPolicyRels =
			passwordPolicyRelPersistence.findByPasswordPolicyId(
				passwordPolicyId);

		for (PasswordPolicyRel passwordPolicyRel : passwordPolicyRels) {
			deletePasswordPolicyRel(passwordPolicyRel);
		}
	}

	public void deletePasswordPolicyRels(
			long passwordPolicyId, String className, long[] classPKs)
		throws SystemException {

		for (int i = 0; i < classPKs.length; i++) {
			deletePasswordPolicyRel(passwordPolicyId, className, classPKs[i]);
		}
	}

	public PasswordPolicyRel fetchPasswordPolicyRel(
			String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return passwordPolicyRelPersistence.fetchByC_C(classNameId, classPK);
	}

	public PasswordPolicyRel getPasswordPolicyRel(
			long passwordPolicyId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return passwordPolicyRelPersistence.findByP_C_C(
			passwordPolicyId, classNameId, classPK);
	}

	public PasswordPolicyRel getPasswordPolicyRel(
			String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return passwordPolicyRelPersistence.findByC_C(classNameId, classPK);
	}

	public boolean hasPasswordPolicyRel(
			long passwordPolicyId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		PasswordPolicyRel passwordPolicyRel =
			passwordPolicyRelPersistence.fetchByP_C_C(
				passwordPolicyId, classNameId, classPK);

		if (passwordPolicyRel != null) {
			return true;
		}
		else {
			return false;
		}
	}

}