/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.impl;

import com.liferay.change.tracking.sample.model.Child;
import com.liferay.change.tracking.sample.model.GrandParent;
import com.liferay.change.tracking.sample.service.base.ChildLocalServiceBaseImpl;
import com.liferay.change.tracking.sample.service.persistence.GrandParentPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.sample.model.Child",
	service = AopService.class
)
public class ChildLocalServiceImpl extends ChildLocalServiceBaseImpl {

	@Override
	public Child addChild(long companyId) {
		return addChild(companyId, 0, 0, "");
	}

	@Override
	public Child addChild(
		long companyId, long parentChildId, long grandParentId,
		String parentName) {

		long childId = counterLocalService.increment(Child.class.getName());

		Child child = childPersistence.create(childId);

		child.setCompanyId(companyId);
		child.setName(String.valueOf(childId));
		child.setGrandParentId(grandParentId);
		child.setParentChildId(parentChildId);
		child.setParentName(parentName);

		return childPersistence.update(child);
	}

	@Override
	public Child deleteChild(Child child) {
		List<Child> children = childPersistence.findByC_P(
			child.getCompanyId(), child.getChildId());

		for (Child c : children) {
			deleteChild(c);
		}

		return childPersistence.remove(child);
	}

	@Override
	public void deleteChildren(long companyId) {
		childPersistence.removeByCompanyId(companyId);
	}

	@Override
	public void deleteChildrenByGrandParentId(
		long companyId, long grandParentId) {

		List<Child> children = childPersistence.findByC_G(
			companyId, grandParentId);

		for (Child child : children) {
			deleteChild(child);
		}
	}

	@Override
	public void deleteChildrenByParentChildId(
		long companyId, long parentChildId) {

		List<Child> children = childPersistence.findByC_P(
			companyId, parentChildId);

		for (Child child : children) {
			deleteChild(child);
		}
	}

	@Override
	public List<Child> getChildren(long companyId) {
		return childPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<Child> getChildrenByGrandParentId(long grandParentId) {
		GrandParent grandParent = _grandParentPersistence.fetchByPrimaryKey(
			grandParentId);

		return childPersistence.findByC_G(
			grandParent.getCompanyId(), grandParentId);
	}

	@Override
	public List<Child> getChildrenByParentChildId(long parentChildId)
		throws PortalException {

		Child child = childPersistence.findByPrimaryKey(parentChildId);

		return childPersistence.findByC_P(
			child.getCompanyId(), child.getChildId());
	}

	@Override
	public Child updateChild(Child child) {
		child.setName(child.getName() + " updated");

		return childPersistence.update(child);
	}

	@Reference
	private GrandParentPersistence _grandParentPersistence;

}