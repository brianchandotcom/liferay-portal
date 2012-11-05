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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.journal.DuplicateStructureElementException;
import com.liferay.portlet.journal.DuplicateStructureIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.RequiredStructureException;
import com.liferay.portlet.journal.StructureIdException;
import com.liferay.portlet.journal.StructureInheritanceException;
import com.liferay.portlet.journal.StructureNameException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalStructureConstants;
import com.liferay.portlet.journal.service.base.JournalStructureLocalServiceBaseImpl;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.comparator.StructurePKComparator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalStructureLocalServiceImpl
	extends JournalStructureLocalServiceBaseImpl {

	public JournalStructure addStructure(
			long userId, long groupId, String structureId,
			boolean autoStructureId, String parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);
		structureId = structureId.trim().toUpperCase();
		Date now = new Date();

		try {
			xsd = JournalUtil.validateXSD(xsd);
			xsd = DDMXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}

		if (autoStructureId) {
			structureId = String.valueOf(counterLocalService.increment());
		}

		validate(
			groupId, structureId, autoStructureId, parentStructureId, nameMap,
			xsd);

		long id = counterLocalService.increment();

		JournalStructure structure = journalStructurePersistence.create(id);

		structure.setUuid(serviceContext.getUuid());
		structure.setGroupId(groupId);
		structure.setCompanyId(user.getCompanyId());
		structure.setUserId(user.getUserId());
		structure.setUserName(user.getFullName());
		structure.setCreateDate(serviceContext.getCreateDate(now));
		structure.setModifiedDate(serviceContext.getModifiedDate(now));
		structure.setStructureId(structureId);
		structure.setParentStructureId(parentStructureId);
		structure.setNameMap(nameMap);
		structure.setDescriptionMap(descriptionMap);
		structure.setXsd(xsd);

		journalStructurePersistence.update(structure);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addStructureResources(
				structure, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addStructureResources(
				structure, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Expando

		ExpandoBridge expandoBridge = structure.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return structure;
	}

	public void addStructureResources(
			JournalStructure structure, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), JournalStructure.class.getName(),
			structure.getId(), false, addGroupPermissions, addGuestPermissions);
	}

	public void addStructureResources(
			JournalStructure structure, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			structure.getCompanyId(), structure.getGroupId(),
			structure.getUserId(), JournalStructure.class.getName(),
			structure.getId(), groupPermissions, guestPermissions);
	}

	public void addStructureResources(
			long groupId, String structureId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		addStructureResources(
			structure, addGroupPermissions, addGuestPermissions);
	}

	public void addStructureResources(
			long groupId, String structureId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		addStructureResources(structure, groupPermissions, guestPermissions);
	}

	public void checkNewLine(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		String xsd = structure.getXsd();

		if ((xsd != null) && xsd.contains("\\n")) {
			xsd = StringUtil.replace(
				xsd, new String[] {"\\n", "\\r"}, new String[] {"\n", "\r"});

			structure.setXsd(xsd);

			journalStructurePersistence.update(structure);
		}
	}

	public JournalStructure copyStructure(
			long userId, long groupId, String oldStructureId,
			String newStructureId, boolean autoStructureId)
		throws PortalException, SystemException {

		// Structure

		User user = userPersistence.findByPrimaryKey(userId);
		oldStructureId = oldStructureId.trim().toUpperCase();
		newStructureId = newStructureId.trim().toUpperCase();
		Date now = new Date();

		JournalStructure oldStructure = journalStructurePersistence.findByG_S(
			groupId, oldStructureId);

		if (autoStructureId) {
			newStructureId = String.valueOf(counterLocalService.increment());
		}
		else {
			validateStructureId(newStructureId);

			JournalStructure newStructure =
				journalStructurePersistence.fetchByG_S(groupId, newStructureId);

			if (newStructure != null) {
				throw new DuplicateStructureIdException();
			}
		}

		long id = counterLocalService.increment();

		JournalStructure newStructure = journalStructurePersistence.create(id);

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

		journalStructurePersistence.update(newStructure);

		// Resources

		addStructureResources(newStructure, true, true);

		return newStructure;
	}

	public void deleteStructure(JournalStructure structure)
		throws PortalException, SystemException {

		Group companyGroup = groupLocalService.getCompanyGroup(
			structure.getCompanyId());

		if (structure.getGroupId() == companyGroup.getGroupId()) {
			if (journalArticlePersistence.countByStructureId(
					structure.getStructureId()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_WEB_CONTENT);
			}

			if (journalStructurePersistence.countByParentStructureId(
					structure.getStructureId()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_STRUCTURE);
			}

			if (journalTemplatePersistence.countByStructureId(
					structure.getStructureId()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_TEMPLATE);
			}
		}
		else {
			if (journalArticlePersistence.countByG_C_S(
					structure.getGroupId(),
					JournalArticleConstants.CLASSNAME_ID_DEFAULT,
					structure.getStructureId()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_WEB_CONTENT);
			}

			if (journalStructurePersistence.countByG_P(
					structure.getGroupId(), structure.getStructureId()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_STRUCTURE);
			}

			if (journalTemplatePersistence.countByG_S(
					structure.getGroupId(), structure.getStructureId()) > 0) {

				throw new RequiredStructureException(
					RequiredStructureException.REFERENCED_TEMPLATE);
			}
		}

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			JournalStructure.class.getName(), structure.getId());

		// Expando

		expandoValueLocalService.deleteValues(
			JournalStructure.class.getName(), structure.getId());

		// Resources

		resourceLocalService.deleteResource(
			structure.getCompanyId(), JournalStructure.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, structure.getId());

		// Article

		try {
			long classNameId = PortalUtil.getClassNameId(
				JournalStructure.class.getName());

			List<JournalArticle> articles =
				journalArticlePersistence.findByG_C_C(
					structure.getGroupId(), classNameId, structure.getId());

			for (JournalArticle article : articles) {
				journalArticleLocalService.deleteArticle(article, null, null);
			}
		}
		catch (NoSuchArticleException nsae) {
		}

		// Structure

		journalStructurePersistence.remove(structure);
	}

	public void deleteStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		deleteStructure(structure);
	}

	public void deleteStructures(long groupId)
		throws PortalException, SystemException {

		List<JournalStructure> structures =
			journalStructurePersistence.findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StructurePKComparator());

		for (JournalStructure structure : structures) {
			deleteStructure(structure);
		}
	}

	public JournalStructure getStructure(long id)
		throws PortalException, SystemException {

		return journalStructurePersistence.findByPrimaryKey(id);
	}

	public JournalStructure getStructure(long groupId, String structureId)
		throws PortalException, SystemException {

		return getStructure(groupId, structureId, false);
	}

	public JournalStructure getStructure(
			long groupId, String structureId, boolean includeGlobalStructures)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		if (groupId == 0) {
			_log.error(
				"No group id was passed for " + structureId + ". Group id is " +
					"required since 4.2.0. Please update all custom code and " +
						"data that references structures without a group id.");

			List<JournalStructure> structures =
				journalStructurePersistence.findByStructureId(structureId);

			if (!structures.isEmpty()) {
				return structures.get(0);
			}

			throw new NoSuchStructureException(
				"No JournalStructure exists with the structure id " +
					structureId);
		}

		JournalStructure structure = journalStructurePersistence.fetchByG_S(
			groupId, structureId);

		if (structure != null) {
			return structure;
		}

		if (!includeGlobalStructures) {
			throw new NoSuchStructureException(
				"No JournalStructure exists with the structure id " +
					structureId);
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Group companyGroup = groupLocalService.getCompanyGroup(
			group.getCompanyId());

		return journalStructurePersistence.findByG_S(
			companyGroup.getGroupId(), structureId);
	}

	public List<JournalStructure> getStructures() throws SystemException {
		return journalStructurePersistence.findAll();
	}

	public List<JournalStructure> getStructures(long groupId)
		throws SystemException {

		return journalStructurePersistence.findByGroupId(groupId);
	}

	public List<JournalStructure> getStructures(
			long groupId, int start, int end)
		throws SystemException {

		return journalStructurePersistence.findByGroupId(groupId, start, end);
	}

	public int getStructuresCount(long groupId) throws SystemException {
		return journalStructurePersistence.countByGroupId(groupId);
	}

	public List<JournalStructure> search(
			long companyId, long[] groupIds, String keywords, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return journalStructureFinder.findByKeywords(
			companyId, groupIds, keywords, start, end, obc);
	}

	public List<JournalStructure> search(
			long companyId, long[] groupIds, String structureId, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalStructureFinder.findByC_G_S_N_D(
			companyId, groupIds, structureId, name, description, andOperator,
			start, end, obc);
	}

	public int searchCount(long companyId, long[] groupIds, String keywords)
		throws SystemException {

		return journalStructureFinder.countByKeywords(
			companyId, groupIds, keywords);
	}

	public int searchCount(
			long companyId, long[] groupIds, String structureId, String name,
			String description, boolean andOperator)
		throws SystemException {

		return journalStructureFinder.countByC_G_S_N_D(
			companyId, groupIds, structureId, name, description, andOperator);
	}

	public JournalStructure updateStructure(
			long groupId, String structureId, String parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		structureId = structureId.trim().toUpperCase();

		try {
			xsd = JournalUtil.validateXSD(xsd);
			xsd = DDMXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureXsdException();
		}

		validateParentStructureId(groupId, structureId, parentStructureId);
		validate(groupId, parentStructureId, nameMap, xsd);

		JournalStructure structure = journalStructurePersistence.findByG_S(
			groupId, structureId);

		structure.setModifiedDate(serviceContext.getModifiedDate(null));
		structure.setParentStructureId(parentStructureId);
		structure.setNameMap(nameMap);
		structure.setDescriptionMap(descriptionMap);
		structure.setXsd(xsd);

		journalStructurePersistence.update(structure);

		// Expando

		ExpandoBridge expandoBridge = structure.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return structure;
	}

	protected void appendParentStructureElements(
			long groupId, String parentStructureId, List<Element> elements)
		throws Exception {

		if (Validator.isNull(parentStructureId)) {
			return;
		}

		JournalStructure parentStructure = getParentStructure(
			groupId, parentStructureId);

		appendParentStructureElements(
			groupId, parentStructure.getParentStructureId(), elements);

		Document document = SAXReaderUtil.read(parentStructure.getXsd());

		Element rootElement = document.getRootElement();

		elements.addAll(rootElement.elements());
	}

	protected JournalStructure getParentStructure(
			long groupId, String parentStructureId)
		throws PortalException, SystemException {

		JournalStructure parentStructure =
			journalStructurePersistence.fetchByG_S(groupId, parentStructureId);

		if (parentStructure != null) {
			return parentStructure;
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Group companyGroup = groupLocalService.getCompanyGroup(
			group.getCompanyId());

		if (groupId != companyGroup.getGroupId()) {
			parentStructure = journalStructurePersistence.findByG_S(
				companyGroup.getGroupId(), parentStructureId);
		}

		return parentStructure;
	}

	protected void validate(List<Element> elements, Set<String> elNames)
		throws PortalException {

		for (Element element : elements) {
			if (element.getName().equals("meta-data")) {
				continue;
			}

			String elName = element.attributeValue("name", StringPool.BLANK);
			String elType = element.attributeValue("type", StringPool.BLANK);

			if (Validator.isNull(elName) ||
				elName.startsWith(JournalStructureConstants.RESERVED)) {

				throw new StructureXsdException();
			}
			else {
				String completePath = elName;

				Element parentElement = element.getParent();

				while (!parentElement.isRootElement()) {
					completePath =
						parentElement.attributeValue("name", StringPool.BLANK) +
							StringPool.SLASH + completePath;

					parentElement = parentElement.getParent();
				}

				String elNameLowerCase = completePath.toLowerCase();

				if (elNames.contains(elNameLowerCase)) {
					throw new DuplicateStructureElementException();
				}
				else {
					elNames.add(elNameLowerCase);
				}
			}

			if (Validator.isNull(elType)) {
				throw new StructureXsdException();
			}

			validate(element.elements(), elNames);
		}
	}

	protected void validate(
			long groupId, String structureId, boolean autoStructureId,
			String parentStructureId, Map<Locale, String> nameMap, String xsd)
		throws PortalException, SystemException {

		if (!autoStructureId) {
			validateStructureId(structureId);

			JournalStructure structure = journalStructurePersistence.fetchByG_S(
				groupId, structureId);

			if (structure != null) {
				throw new DuplicateStructureIdException();
			}
		}

		validateParentStructureId(groupId, structureId, parentStructureId);
		validate(groupId, parentStructureId, nameMap, xsd);
	}

	protected void validate(
			long groupId, String parentStructureId, Map<Locale, String> nameMap,
			String xsd)
		throws PortalException {

		Locale locale = LocaleUtil.getDefault();

		if (nameMap.isEmpty() || Validator.isNull(nameMap.get(locale))) {
			throw new StructureNameException();
		}

		if (Validator.isNull(xsd)) {
			throw new StructureXsdException();
		}
		else {
			try {
				List<Element> elements = new ArrayList<Element>();

				appendParentStructureElements(
					groupId, parentStructureId, elements);

				Document document = SAXReaderUtil.read(xsd);

				Element rootElement = document.getRootElement();

				if (rootElement.elements().isEmpty()) {
					throw new StructureXsdException();
				}

				elements.addAll(rootElement.elements());

				Set<String> elNames = new HashSet<String>();

				validate(elements, elNames);
			}
			catch (DuplicateStructureElementException dsee) {
				throw dsee;
			}
			catch (StructureXsdException sxe) {
				throw sxe;
			}
			catch (Exception e) {
				throw new StructureXsdException();
			}
		}
	}

	protected void validateParentStructureId(
			long groupId, String structureId, String parentStructureId)
		throws PortalException, SystemException {

		if (Validator.isNull(parentStructureId)) {
			return;
		}

		if (parentStructureId.equals(structureId)) {
			throw new StructureInheritanceException();
		}

		JournalStructure parentStructure = getParentStructure(
			groupId, parentStructureId);

		while (parentStructure != null) {
			if ((parentStructure != null) &&
				(parentStructure.getStructureId().equals(structureId) ||
				 parentStructure.getParentStructureId().equals(structureId))) {

				throw new StructureInheritanceException();
			}

			try {
				parentStructure = getParentStructure(
					groupId, parentStructure.getParentStructureId());
			}
			catch (NoSuchStructureException nsse) {
				break;
			}
		}
	}

	protected void validateStructureId(String structureId)
		throws PortalException {

		if (Validator.isNull(structureId) ||
			Validator.isNumber(structureId) ||
			(structureId.indexOf(CharPool.SPACE) != -1)) {

			throw new StructureIdException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalStructureLocalServiceImpl.class);

}