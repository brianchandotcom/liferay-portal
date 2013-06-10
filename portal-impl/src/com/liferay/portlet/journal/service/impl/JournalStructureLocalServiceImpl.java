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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.StructureXsdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.journal.DuplicateStructureIdException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.StructureIdException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalStructureAdapter;
import com.liferay.portlet.journal.service.base.JournalStructureLocalServiceBaseImpl;
import com.liferay.portlet.journal.util.JournalConverterUtil;
import com.liferay.portlet.journal.util.JournalUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Marcellus Tavares
 */
public class JournalStructureLocalServiceImpl
	extends JournalStructureLocalServiceBaseImpl {

	@Override
	public JournalStructure addJournalStructure(JournalStructure structure)
		throws PortalException, SystemException {

		structure.setNew(true);

		return update(structure);
	}

	@Override
	public JournalStructure addStructure(
			long userId, long groupId, String structureId,
			boolean autoStructureId, String parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			xsd = JournalConverterUtil.getDDMXSD(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException(e);
		}

		if (autoStructureId) {
			structureId = null;
		}

		DDMStructure ddmStructure = ddmStructureLocalService.addStructure(
			userId, groupId, parentStructureId,
			PortalUtil.getClassNameId(JournalArticle.class), structureId,
			nameMap, descriptionMap, xsd,
			PropsValues.JOURNAL_ARTICLE_STORAGE_TYPE,
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);

		return new JournalStructureAdapter(ddmStructure);
	}

	@Override
	public void addStructureResources(
			JournalStructure structure, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DDMStructure dmmStructure = getDDMStructure(structure);

		ddmStructureLocalService.addStructureResources(
			dmmStructure, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addStructureResources(
			JournalStructure structure, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DDMStructure dmmStructure = getDDMStructure(structure);

		ddmStructureLocalService.addStructureResources(
			dmmStructure, groupPermissions, guestPermissions);
	}

	@Override
	public void addStructureResources(
			long groupId, String structureId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = findByG_S(groupId, structureId);

		addStructureResources(
			structure, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addStructureResources(
			long groupId, String structureId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = findByG_S(groupId, structureId);

		addStructureResources(structure, groupPermissions, guestPermissions);
	}

	@Override
	public void checkNewLine(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = findByG_S(groupId, structureId);

		String xsd = structure.getXsd();

		if ((xsd != null) && xsd.contains("\\n")) {
			xsd = StringUtil.replace(
				xsd, new String[] {"\\n", "\\r"}, new String[] {"\n", "\r"});

			structure.setXsd(xsd);

			update(structure);
		}
	}

	@Override
	public JournalStructure copyStructure(
			long userId, long groupId, String oldStructureId,
			String newStructureId, boolean autoStructureId)
		throws PortalException, SystemException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);
		oldStructureId = oldStructureId.trim().toUpperCase();
		newStructureId = newStructureId.trim().toUpperCase();
		Date now = new Date();

		JournalStructure oldStructure = findByG_S(groupId, oldStructureId);

		if (autoStructureId) {
			newStructureId = String.valueOf(counterLocalService.increment());
		}
		else {
			validateStructureId(newStructureId);

			JournalStructure newStructure = fetchByG_S(groupId, newStructureId);

			if (newStructure != null) {
				throw new DuplicateStructureIdException();
			}
		}

		long id = counterLocalService.increment();

		JournalStructure newStructure = createJournalStructure(id);

		newStructure.setGroupId(groupId);
		newStructure.setCompanyId(user.getCompanyId());
		newStructure.setUserId(user.getUserId());
		newStructure.setUserName(user.getFullName());
		newStructure.setCreateDate(now);
		newStructure.setModifiedDate(now);
		newStructure.setStructureId(newStructureId);
		newStructure.setNameMap(oldStructure.getNameMap());
		newStructure.setDescriptionMap(oldStructure.getDescriptionMap());
		newStructure.setXsd(oldStructure.getXsd());
		newStructure.setExpandoBridgeAttributes(oldStructure);

		return update(newStructure);
	}

	@Override
	public JournalStructure createJournalStructure(long id)
		throws SystemException {

		DDMStructure ddmStructure = ddmStructureLocalService.createDDMStructure(
			id);

		return new JournalStructureAdapter(ddmStructure);
	}

	@Override
	public void deleteStructure(JournalStructure structure)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = getDDMStructure(structure);

		ddmStructureLocalService.deleteDDMStructure(ddmStructure);
	}

	@Override
	public void deleteStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = findByG_S(groupId, structureId);

		deleteStructure(structure);
	}

	@Override
	public void deleteStructures(long groupId)
		throws PortalException, SystemException {

		List<JournalStructure> structures = findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (JournalStructure structure : structures) {
			deleteStructure(structure);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<JournalStructure> findAll() throws SystemException {
		List<DDMStructure> ddmStructures =
			ddmStructureLocalService.getClassStructures(
				PortalUtil.getClassNameId(JournalArticle.class));

		return JournalUtil.toJournalStructures(ddmStructures);
	}

	@Override
	public JournalStructure getStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		return getStructure(groupId, structureId, false);
	}

	@Override
	public JournalStructure getStructure(
			long groupId, String structureId, boolean includeGlobalStructures)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		JournalStructure structure = fetchByG_S(groupId, structureId);

		if (structure != null) {
			return structure;
		}

		if (!includeGlobalStructures) {
			throw new NoSuchStructureException(
				"No JournalStructure exists with the structure ID " +
					structureId);
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Group companyGroup = groupLocalService.getCompanyGroup(
			group.getCompanyId());

		return findByG_S(companyGroup.getGroupId(), structureId);
	}

	@Override
	public List<JournalStructure> getStructures() throws SystemException {
		return findAll();
	}

	@Override
	public List<JournalStructure> getStructures(long groupId)
		throws SystemException {

		return findByGroupId(groupId);
	}

	@Override
	public List<JournalStructure> getStructures(
			long groupId, int start, int end)
		throws SystemException {

		return findByGroupId(groupId, start, end);
	}

	@Override
	public int getStructuresCount(long groupId) throws SystemException {
		return countByGroupId(groupId);
	}

	@Override
	public List<JournalStructure> search(
			long companyId, long[] groupIds, String keywords, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		long[] classNameIds = new long[] {
			PortalUtil.getClassNameId(JournalArticle.class)
		};

		List<DDMStructure> ddmStructures = ddmStructureFinder.findByKeywords(
			companyId, groupIds, classNameIds, keywords, start, end, obc);

		return JournalUtil.toJournalStructures(ddmStructures);
	}

	@Override
	public List<JournalStructure> search(
			long companyId, long[] groupIds, String structureId, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		long[] classNameIds = new long[] {
			PortalUtil.getClassNameId(JournalArticle.class)
		};

		List<DDMStructure> ddmStructures =
			ddmStructureFinder.findByC_G_C_N_D_S_T(
				companyId, groupIds, classNameIds, name, description, null,
				DDMStructureConstants.TYPE_DEFAULT, andOperator, start, end,
				obc);

		return JournalUtil.toJournalStructures(ddmStructures);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds, String keywords)
		throws SystemException {

		long[] classNameIds = new long[] {
			PortalUtil.getClassNameId(JournalArticle.class)
		};

		return ddmStructureFinder.countByKeywords(
			companyId, groupIds, classNameIds, keywords);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, String structureId, String name,
			String description, boolean andOperator)
		throws SystemException {

		long[] classNameIds = new long[] {
			PortalUtil.getClassNameId(JournalArticle.class)
		};

		return ddmStructureFinder.countByC_G_C_N_D_S_T(
			companyId, groupIds, classNameIds, name, description, null,
			DDMStructureConstants.TYPE_DEFAULT, andOperator);
	}

	@Override
	public JournalStructure updateJournalStructure(JournalStructure structure)
			throws PortalException, SystemException {

		return update(structure);
	}

	@Override
	public JournalStructure updateStructure(
			long groupId, String structureId, String parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = getDDMStructure(groupId, structureId);

		long parentDDMStructureId = getParentDDMStructureId(
			groupId, parentStructureId);

		try {
			xsd = JournalConverterUtil.getDDMXSD(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException(e);
		}

		ddmStructure = ddmStructureLocalService.updateStructure(
			ddmStructure.getStructureId(), parentDDMStructureId, nameMap,
			descriptionMap, xsd, serviceContext);

		return new JournalStructureAdapter(ddmStructure);
	}

	protected int countByGroupId(long groupId) throws SystemException {
		return ddmStructureLocalService.getStructuresCount(
			groupId, PortalUtil.getClassNameId(JournalArticle.class));
	}

	protected JournalStructure fetchByG_S(long groupId, String structureId)
		throws SystemException {

		DDMStructure ddmStructure = fetchDDMStructure(groupId, structureId);

		if (ddmStructure != null) {
			return new JournalStructureAdapter(ddmStructure);
		}

		return null;
	}

	protected DDMStructure fetchDDMStructure(JournalStructure structure)
		throws SystemException {

		return fetchDDMStructure(
			structure.getGroupId(), structure.getStructureId());
	}

	protected DDMStructure fetchDDMStructure(long groupId, String structureId)
		throws SystemException {

		return ddmStructureLocalService.fetchStructure(
			groupId, PortalUtil.getClassNameId(JournalArticle.class),
			structureId);
	}

	protected JournalStructure findByG_S(long groupId, String structureId)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = getDDMStructure(groupId, structureId);

		return new JournalStructureAdapter(ddmStructure);
	}

	protected List<JournalStructure> findByGroupId(long groupId)
		throws SystemException {

		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	protected List<JournalStructure> findByGroupId(
			long groupId, int start, int end)
		throws SystemException {

		List<DDMStructure> ddmStructures =
			ddmStructureLocalService.getStructures(
				groupId, PortalUtil.getClassNameId(JournalArticle.class), start,
				end);

		return JournalUtil.toJournalStructures(ddmStructures);
	}

	protected DDMStructure getDDMStructure(JournalStructure structure)
		throws PortalException, SystemException {

		return getDDMStructure(
			structure.getGroupId(), structure.getStructureId());
	}

	protected DDMStructure getDDMStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		try {
			return ddmStructureLocalService.getStructure(
				groupId, PortalUtil.getClassNameId(JournalArticle.class),
				structureId);
		}
		catch (PortalException pe) {
			throw new NoSuchStructureException(pe);
		}
	}

	protected long getParentDDMStructureId(
			long groupId, String parentStructureId)
		throws SystemException {

		DDMStructure parentDDMStructure = fetchDDMStructure(
			groupId, parentStructureId);

		if (parentDDMStructure != null) {
			return parentDDMStructure.getStructureId();
		}

		return 0;
	}

	protected String getUuid(JournalStructure structure) {
		String uuid = structure.getUuid();

		if (Validator.isNotNull(uuid)) {
			return uuid;
		}

		return PortalUUIDUtil.generate();
	}

	protected JournalStructure update(JournalStructure structure)
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		String uuid = getUuid(structure);

		serviceContext.setUuid(uuid);

		if (structure.isNew()) {
			return addStructure(
				structure.getUserId(), structure.getGroupId(),
				structure.getStructureId(), false,
				structure.getParentStructureId(), structure.getNameMap(),
				structure.getDescriptionMap(), structure.getXsd(),
				serviceContext);
		}

		return updateStructure(
			structure.getGroupId(), structure.getStructureId(),
			structure.getParentStructureId(), structure.getNameMap(),
			structure.getDescriptionMap(), structure.getXsd(), serviceContext);
	}

	protected void validateStructureId(String structureId)
		throws PortalException {

		if (Validator.isNull(structureId) ||
			Validator.isNumber(structureId) ||
			(structureId.indexOf(CharPool.SPACE) != -1)) {

			throw new StructureIdException();
		}
	}

}