/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.impl;

import com.liferay.change.tracking.sample.model.GrandParent;
import com.liferay.change.tracking.sample.service.base.GrandParentLocalServiceBaseImpl;
import com.liferay.change.tracking.sample.service.persistence.ChildPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.sample.model.GrandParent",
	service = AopService.class
)
public class GrandParentLocalServiceImpl
	extends GrandParentLocalServiceBaseImpl {

	@Override
	public GrandParent addGrandParent(long companyId) {
		return addGrandParent(companyId, 0);
	}

	@Override
	public GrandParent addGrandParent(
		long companyId, long parentGrandParentId) {

		long grandParentId = counterLocalService.increment(
			GrandParent.class.getName());

		GrandParent grandParent = grandParentPersistence.create(grandParentId);

		grandParent.setCompanyId(companyId);
		grandParent.setName(String.valueOf(grandParentId));
		grandParent.setParentGrandParentId(parentGrandParentId);

		return grandParentPersistence.update(grandParent);
	}

	@Override
	public GrandParent deleteGrandParent(GrandParent grandParent) {
		_childPersistence.removeByC_G(
			grandParent.getCompanyId(), grandParent.getGrandParentId());

		return grandParentPersistence.remove(grandParent);
	}

	@Override
	public void deleteGrandParents(long companyId) {
		List<GrandParent> grandParents = grandParentPersistence.findByCompanyId(
			companyId);

		for (GrandParent grandParent : grandParents) {
			deleteGrandParent(grandParent);
		}
	}

	@Override
	public List<GrandParent> getGrandParents(long companyId) {
		return grandParentPersistence.findByCompanyId(companyId);
	}

	@Override
	public GrandParent updateGrandParent(long grandParentId)
		throws PortalException {

		GrandParent grandParent = grandParentPersistence.findByPrimaryKey(
			grandParentId);

		grandParent.setName(grandParent.getName() + " updated");

		return grandParentPersistence.update(grandParent);
	}

	@Reference
	private ChildPersistence _childPersistence;

}