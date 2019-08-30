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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLinkException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureLinkLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 * @author Marcellus Tavares
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMStructureLink",
	service = AopService.class
)
public class DDMStructureLinkLocalServiceImpl
	extends DDMStructureLinkLocalServiceBaseImpl {

	@Override
	public DDMStructureLink addStructureLink(
		long classNameId, long classPK, long structureId) {

		long structureLinkId = counterLocalService.increment();

		DDMStructureLink structureLink = ddmStructureLinkPersistence.create(
			structureLinkId);

		structureLink.setClassNameId(classNameId);
		structureLink.setClassPK(classPK);
		structureLink.setStructureId(structureId);

		ddmStructureLinkPersistence.update(structureLink);

		return structureLink;
	}

	@Override
	public void deleteStructureLink(DDMStructureLink structureLink) {
		ddmStructureLinkPersistence.remove(structureLink);
	}

	@Override
	public void deleteStructureLink(long structureLinkId)
		throws PortalException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);

		deleteStructureLink(structureLink);
	}

	@Override
	public void deleteStructureLink(
			long classNameId, long classPK, long structureId)
		throws PortalException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByC_C_S(
				classNameId, classPK, structureId);

		deleteDDMStructureLink(structureLink);
	}

	@Override
	public void deleteStructureLinks(long classNameId, long classPK) {
		List<DDMStructureLink> structureLinks =
			ddmStructureLinkPersistence.findByC_C(classNameId, classPK);

		for (DDMStructureLink ddmStructureLink : structureLinks) {
			deleteStructureLink(ddmStructureLink);
		}
	}

	@Override
	public void deleteStructureStructureLinks(long structureId) {
		List<DDMStructureLink> structureLinks =
			ddmStructureLinkPersistence.findByStructureId(structureId);

		for (DDMStructureLink structureLink : structureLinks) {
			deleteStructureLink(structureLink);
		}
	}

	@Override
	public List<DDMStructureLink> getClassNameStructureLinks(long classNameId) {
		return ddmStructureLinkPersistence.findByClassNameId(classNameId);
	}

	@Override
	public DDMStructureLink getStructureLink(long structureLinkId)
		throws PortalException {

		return ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);
	}

	@Override
	public List<DDMStructureLink> getStructureLinks(long structureId) {
		return ddmStructureLinkPersistence.findByStructureId(structureId);
	}

	@Override
	public List<DDMStructureLink> getStructureLinks(
		long structureId, int start, int end) {

		return ddmStructureLinkPersistence.findByStructureId(
			structureId, start, end);
	}

	@Override
	public List<DDMStructureLink> getStructureLinks(
		long classNameId, long classPK) {

		return ddmStructureLinkPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public List<DDMStructureLink> getStructureLinks(
		long classNameId, long classPK, int start, int end) {

		return ddmStructureLinkPersistence.findByC_C(
			classNameId, classPK, start, end);
	}

	@Override
	public int getStructureLinksCount(long classNameId, long classPK) {
		return ddmStructureLinkPersistence.countByC_C(classNameId, classPK);
	}

	@Override
	public List<DDMStructure> getStructureLinkStructures(
			long classNameId, long classPK)
		throws PortalException {

		List<DDMStructure> structures = new ArrayList<>();

		List<DDMStructureLink> structureLinks = getStructureLinks(
			classNameId, classPK);

		for (DDMStructureLink structureLink : structureLinks) {
			structures.add(structureLink.getStructure());
		}

		return structures;
	}

	@Override
	public List<DDMStructure> getStructureLinkStructures(
			long classNameId, long classPK, int start, int end)
		throws PortalException {

		List<DDMStructure> structures = new ArrayList<>();

		List<DDMStructureLink> structureLinks = getStructureLinks(
			classNameId, classPK, start, end);

		for (DDMStructureLink structureLink : structureLinks) {
			structures.add(structureLink.getStructure());
		}

		return structures;
	}

	@Override
	public List<DDMStructure> getStructureLinkStructures(
			long classNameId, long classPK, String keywords)
		throws PortalException {

		return getStructureLinkStructures(
			classNameId, classPK, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<DDMStructure> getStructureLinkStructures(
			long classNameId, long classPK, String keywords, int start, int end)
		throws PortalException {

		return getStructureLinkStructures(
			classNameId, classPK, keywords, start, end, null);
	}

	@Override
	public List<DDMStructure> getStructureLinkStructures(
			long classNameId, long classPK, String keywords, int start, int end,
			OrderByComparator<DDMStructureLink> orderByComparator)
		throws PortalException {

		List<DDMStructure> structures = new ArrayList<>();

		List<DDMStructureLink> structureLinks =
			ddmStructureLinkFinder.findByKeywords(
				classNameId, classPK, keywords, start, end, orderByComparator);

		for (DDMStructureLink structureLink : structureLinks) {
			structures.add(structureLink.getStructure());
		}

		return structures;
	}

	@Override
	public int getStructureLinkStructuresCount(
		long classNameId, long classPK, String keywords) {

		return ddmStructureLinkFinder.countByKeywords(
			classNameId, classPK, keywords);
	}

	@Override
	public DDMStructureLink getUniqueStructureLink(
			long classNameId, long classPK)
		throws PortalException {

		List<DDMStructureLink> structureLinks =
			ddmStructureLinkPersistence.findByC_C(classNameId, classPK);

		if (structureLinks.isEmpty()) {
			StringBundler sb = new StringBundler(5);

			sb.append("No DDMStructureLink found for {classNameId=");
			sb.append(classNameId);
			sb.append(", classPK=");
			sb.append(classPK);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureLinkException(sb.toString());
		}

		return structureLinks.get(0);
	}

	@Override
	public DDMStructureLink updateStructureLink(
			long structureLinkId, long classNameId, long classPK,
			long structureId)
		throws PortalException {

		DDMStructureLink structureLink =
			ddmStructureLinkPersistence.findByPrimaryKey(structureLinkId);

		structureLink.setClassNameId(classNameId);
		structureLink.setClassPK(classPK);
		structureLink.setStructureId(structureId);

		ddmStructureLinkPersistence.update(structureLink);

		return structureLink;
	}

}