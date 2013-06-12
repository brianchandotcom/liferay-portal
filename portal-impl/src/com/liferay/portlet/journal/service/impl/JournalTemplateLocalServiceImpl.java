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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.journal.DuplicateTemplateIdException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.TemplateIdException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.JournalTemplateAdapter;
import com.liferay.portlet.journal.service.base.JournalTemplateLocalServiceBaseImpl;
import com.liferay.portlet.journal.util.JournalUtil;

import java.io.File;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Marcellus Tavares
 * @deprecated As of 6.2.0, see LPS-35112
 */
public class JournalTemplateLocalServiceImpl
	extends JournalTemplateLocalServiceBaseImpl {

	@Override
	public JournalTemplate addJournalTemplate(JournalTemplate template)
		throws PortalException, SystemException {

		template.setNew(true);

		return updateTemplate(template);
	}

	@Override
	public JournalTemplate addTemplate(
			long userId, long groupId, String templateId,
			boolean autoTemplateId, String structureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsl, boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallImageFile,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long classPK = 0;

		if (Validator.isNotNull(structureId)) {
			JournalStructure structure =
				journalStructureLocalService.getStructure(
					groupId, structureId, true);

			classPK = structure.getPrimaryKey();
		}

		DDMTemplate ddmTemplate = ddmTemplateLocalService.addTemplate(
			userId, groupId, PortalUtil.getClassNameId(DDMStructure.class),
			classPK, templateId, nameMap, descriptionMap,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
			DDMTemplateConstants.TEMPLATE_MODE_CREATE, langType, xsl, cacheable,
			smallImage, smallImageURL, smallImageFile, serviceContext);

		return new JournalTemplateAdapter(ddmTemplate);
	}

	@Override
	public void addTemplateResources(
			JournalTemplate template, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = getDDMTemplate(template);

		ddmTemplateLocalService.addTemplateResources(
			ddmTemplate, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addTemplateResources(
			JournalTemplate template, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = getDDMTemplate(template);

		ddmTemplateLocalService.addTemplateResources(
			ddmTemplate, groupPermissions, guestPermissions);
	}

	@Override
	public void addTemplateResources(
			long groupId, String templateId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalTemplate template = doGetTemplate(groupId, templateId);

		addTemplateResources(
			template, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addTemplateResources(
			long groupId, String templateId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalTemplate template = doGetTemplate(groupId, templateId);

		addTemplateResources(template, groupPermissions, guestPermissions);
	}

	@Override
	public void checkNewLine(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplate template = doGetTemplate(groupId, templateId);

		String xsl = template.getXsl();

		if ((xsl != null) && xsl.contains("\\n")) {
			xsl = StringUtil.replace(
				xsl, new String[] {"\\n", "\\r"}, new String[] {"\n", "\r"});

			template.setXsl(xsl);

			updateTemplate(template);
		}
	}

	@Override
	public JournalTemplate copyTemplate(
			long userId, long groupId, String oldTemplateId,
			String newTemplateId, boolean autoTemplateId)
		throws PortalException, SystemException {

		// Template

		User user = userPersistence.findByPrimaryKey(userId);
		oldTemplateId = oldTemplateId.trim().toUpperCase();
		newTemplateId = newTemplateId.trim().toUpperCase();
		Date now = new Date();

		JournalTemplate oldTemplate = doGetTemplate(groupId, oldTemplateId);

		if (autoTemplateId) {
			newTemplateId = String.valueOf(counterLocalService.increment());
		}
		else {
			validateTemplateId(newTemplateId);

			JournalTemplate newTemplate = fetchTemplate(groupId, newTemplateId);

			if (newTemplate != null) {
				throw new DuplicateTemplateIdException();
			}
		}

		long id = counterLocalService.increment();

		JournalTemplate newTemplate = createJournalTemplate(id);

		newTemplate.setGroupId(groupId);
		newTemplate.setCompanyId(user.getCompanyId());
		newTemplate.setUserId(user.getUserId());
		newTemplate.setUserName(user.getFullName());
		newTemplate.setCreateDate(now);
		newTemplate.setModifiedDate(now);
		newTemplate.setTemplateId(newTemplateId);
		newTemplate.setStructureId(oldTemplate.getStructureId());
		newTemplate.setNameMap(oldTemplate.getNameMap());
		newTemplate.setDescriptionMap(oldTemplate.getDescriptionMap());
		newTemplate.setXsl(oldTemplate.getXsl());
		newTemplate.setLangType(oldTemplate.getLangType());
		newTemplate.setCacheable(oldTemplate.isCacheable());
		newTemplate.setSmallImage(oldTemplate.isSmallImage());
		newTemplate.setSmallImageId(counterLocalService.increment());
		newTemplate.setSmallImageURL(oldTemplate.getSmallImageURL());
		newTemplate.setExpandoBridgeAttributes(oldTemplate);

		updateTemplate(newTemplate);

		// Small image

		if (oldTemplate.getSmallImage()) {
			Image image = imageLocalService.getImage(
				oldTemplate.getSmallImageId());

			byte[] smallImageBytes = image.getTextObj();

			imageLocalService.updateImage(
				newTemplate.getSmallImageId(), smallImageBytes);
		}

		return newTemplate;
	}

	@Override
	public JournalTemplate createJournalTemplate(long id) {
		DDMTemplate ddmTemplate = ddmTemplateLocalService.createDDMTemplate(id);

		return new JournalTemplateAdapter(ddmTemplate);
	}

	@Override
	public void deleteTemplate(JournalTemplate template)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = getDDMTemplate(template);

		ddmTemplateLocalService.deleteTemplate(ddmTemplate);
	}

	@Override
	public void deleteTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplate template = doGetTemplate(groupId, templateId);

		deleteTemplate(template);
	}

	@Override
	public void deleteTemplates(long groupId)
		throws PortalException, SystemException {

		List<JournalTemplate> templates = doGetTemplates(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (JournalTemplate template : templates) {
			deleteTemplate(template);
		}
	}

	@Override
	public List<JournalTemplate> getStructureTemplates(
			long groupId, String structureId)
		throws PortalException, SystemException {

		return getStructureTemplates(groupId, structureId, false);
	}

	@Override
	public List<JournalTemplate> getStructureTemplates(
			long groupId, String structureId, boolean includeGlobalTemplates)
		throws PortalException, SystemException {

		long[] groupIds = new long[] {groupId};

		if (includeGlobalTemplates) {
			groupIds = PortalUtil.getSiteAndCompanyGroupIds(groupId);
		}

		JournalStructure structure = journalStructureLocalService.getStructure(
			groupId, structureId, true);

		List<DDMTemplate> ddmTemplates =
			ddmTemplateLocalService.getTemplatesByClassPK(
				groupIds, structure.getPrimaryKey());

		return JournalUtil.toJournalTemplates(ddmTemplates);
	}

	@Override
	public List<JournalTemplate> getStructureTemplates(
			long groupId, String structureId, int start, int end)
		throws PortalException, SystemException {

		JournalStructure structure = journalStructureLocalService.getStructure(
			groupId, structureId, true);

		List<DDMTemplate> ddmTemplates =
			ddmTemplateLocalService.getTemplatesByClassPK(
				groupId, structure.getPrimaryKey(), start, end);

		return JournalUtil.toJournalTemplates(ddmTemplates);
	}

	@Override
	public int getStructureTemplatesCount(long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructure structure = journalStructureLocalService.getStructure(
			groupId, structureId, true);

		return ddmTemplateLocalService.getTemplatesByClassPKCount(
			groupId, structure.getPrimaryKey());
	}

	@Override
	public JournalTemplate getTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		return getTemplate(groupId, templateId, false);
	}

	@Override
	public JournalTemplate getTemplate(
			long groupId, String templateId, boolean includeGlobalTemplates)
		throws PortalException, SystemException {

		templateId = GetterUtil.getString(templateId).toUpperCase();

		JournalTemplate template = fetchTemplate(groupId, templateId);

		if (template != null) {
			return template;
		}

		if (!includeGlobalTemplates) {
			throw new NoSuchTemplateException(
				"No JournalTemplate exists with the template ID " + templateId);
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Group companyGroup = groupLocalService.getCompanyGroup(
			group.getCompanyId());

		return doGetTemplate(companyGroup.getGroupId(), templateId);
	}

	@Override
	public JournalTemplate getTemplateBySmallImageId(long smallImageId)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate =
			ddmTemplateLocalService.getTemplateBySmallImageId(smallImageId);

		return new JournalTemplateAdapter(ddmTemplate);
	}

	@Override
	public List<JournalTemplate> getTemplates() throws SystemException {
		List<DDMTemplate> ddmTemplates = ddmTemplateFinder.findByG_C_C_SC(
			null, PortalUtil.getClassNameId(DDMStructure.class), 0,
			PortalUtil.getClassNameId(JournalArticle.class), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return JournalUtil.toJournalTemplates(ddmTemplates);
	}

	@Override
	public List<JournalTemplate> getTemplates(long groupId)
		throws SystemException {

		return doGetTemplates(groupId);
	}

	@Override
	public List<JournalTemplate> getTemplates(long groupId, int start, int end)
		throws SystemException {

		return doGetTemplates(groupId, start, end);
	}

	@Override
	public int getTemplatesCount(long groupId) throws SystemException {
		return doGetTemplatesCount(groupId);
	}

	@Override
	public boolean hasTemplate(long groupId, String templateId)
		throws SystemException {

		try {
			getTemplate(groupId, templateId);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

	@Override
	public List<JournalTemplate> search(
			long companyId, long[] groupIds, String keywords,
			String structureId, String structureIdComparator, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		long[] classNameIds = {PortalUtil.getClassNameId(DDMStructure.class)};
		long[] classPKs = JournalUtil.getStructureClassPKs(
			groupIds, structureId);

		List<DDMTemplate> ddmTemplates = ddmTemplateFinder.findByKeywords(
			companyId, groupIds, classNameIds, classPKs, keywords, null, null,
			start, end, obc);

		return JournalUtil.toJournalTemplates(ddmTemplates);
	}

	@Override
	public List<JournalTemplate> search(
			long companyId, long[] groupIds, String templateId,
			String structureId, String structureIdComparator, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		long[] classNameIds = {PortalUtil.getClassNameId(DDMStructure.class)};
		long[] classPKs = JournalUtil.getStructureClassPKs(
			groupIds, structureId);

		List<DDMTemplate> ddmTemplates =
			ddmTemplateFinder.findByC_G_C_C_N_D_T_M_L(
				companyId, groupIds, classNameIds, classPKs, name, description,
				null, null, null, andOperator, start, end, obc);

		return JournalUtil.toJournalTemplates(ddmTemplates);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, String keywords,
			String structureId, String structureIdComparator)
		throws SystemException {

		long[] classNameIds = {PortalUtil.getClassNameId(DDMStructure.class)};
		long[] classPKs = JournalUtil.getStructureClassPKs(
			groupIds, structureId);

		return ddmTemplateFinder.countByKeywords(
			companyId, groupIds, classNameIds, classPKs, keywords, null, null);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, String templateId,
			String structureId, String structureIdComparator, String name,
			String description, boolean andOperator)
		throws SystemException {

		long[] classNameIds = {PortalUtil.getClassNameId(DDMStructure.class)};
		long[] classPKs = JournalUtil.getStructureClassPKs(
			groupIds, structureId);

		return ddmTemplateFinder.countByC_G_C_C_N_D_T_M_L(
			companyId, groupIds, classNameIds, classPKs, name, description,
			null, null, null, andOperator);
	}

	@Override
	public JournalTemplate updateJournalTemplate(JournalTemplate template)
			throws PortalException, SystemException {

		return updateTemplate(template);
	}

	@Override
	public JournalTemplate updateTemplate(
			long groupId, String templateId, String structureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsl, boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallImageFile,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalTemplate template = doGetTemplate(groupId, templateId);

		DDMTemplate ddmTemplate = ddmTemplateLocalService.updateTemplate(
			template.getPrimaryKey(), nameMap, descriptionMap,
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
			DDMTemplateConstants.TEMPLATE_MODE_CREATE, langType, xsl, cacheable,
			smallImage, smallImageURL, smallImageFile, serviceContext);

		if (Validator.isNull(template.getTemplateId()) &&
			Validator.isNotNull(structureId)) {

			// Allow users to set the structure if and only if it currently does
			// not have one. Otherwise, you can have bad data because there may
			// be an existing article that has chosen to use a structure and
			// template combination that no longer exists.

			JournalStructure structure =
				journalStructureLocalService.getStructure(
					groupId, structureId, true);

			ddmTemplate.setClassPK(structure.getPrimaryKey());

			ddmTemplatePersistence.update(ddmTemplate);
		}

		return new JournalTemplateAdapter(ddmTemplate);
	}

	protected JournalTemplate doGetTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = getDDMTemplate(groupId, templateId);

		return new JournalTemplateAdapter(ddmTemplate);
	}

	protected List<JournalTemplate> doGetTemplates(long groupId)
		throws SystemException {

		return doGetTemplates(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	protected List<JournalTemplate> doGetTemplates(
			long groupId, int start, int end)
		throws SystemException {

		List<DDMTemplate> ddmTemplates = ddmTemplateFinder.findByG_C_C_SC(
			groupId, PortalUtil.getClassNameId(DDMStructure.class), 0,
			PortalUtil.getClassNameId(JournalArticle.class), start, end, null);

		return JournalUtil.toJournalTemplates(ddmTemplates);
	}

	protected int doGetTemplatesCount(long groupId) throws SystemException {
		return ddmTemplateFinder.countByG_C_C_SC(
			groupId, PortalUtil.getClassNameId(DDMStructure.class), 0,
			PortalUtil.getClassNameId(JournalArticle.class));
	}

	protected DDMTemplate fetchDDMTemplate(long groupId, String templateId)
		throws SystemException {

		return ddmTemplateLocalService.fetchTemplate(
			groupId, PortalUtil.getClassNameId(DDMStructure.class), templateId);
	}

	protected JournalTemplate fetchTemplate(long groupId, String templateId)
		throws SystemException {

		DDMTemplate ddmTemplate = fetchDDMTemplate(groupId, templateId);

		if (ddmTemplate != null) {
			return new JournalTemplateAdapter(ddmTemplate);
		}

		return null;
	}

	protected DDMTemplate getDDMTemplate(JournalTemplate template)
		throws PortalException, SystemException {

		return getDDMTemplate(template.getGroupId(), template.getTemplateId());
	}

	protected DDMTemplate getDDMTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		try {
			return ddmTemplateLocalService.getTemplate(
				groupId, PortalUtil.getClassNameId(DDMStructure.class),
				templateId);
		}
		catch (PortalException pe) {
			throw new NoSuchTemplateException(pe);
		}
	}

	protected String getUuid(JournalTemplate template) {
		String uuid = template.getUuid();

		if (Validator.isNotNull(uuid)) {
			return uuid;
		}

		return PortalUUIDUtil.generate();
	}

	protected JournalTemplate updateTemplate(JournalTemplate template)
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		String uuid = getUuid(template);

		serviceContext.setUuid(uuid);

		if (template.isNew()) {
			return addTemplate(
				template.getUserId(), template.getGroupId(),
				template.getTemplateId(), false, template.getStructureId(),
				template.getNameMap(), template.getDescriptionMap(),
				template.getXsl(), true, template.getLangType(),
				template.isCacheable(), template.isSmallImage(),
				template.getSmallImageURL(), template.getSmallImageFile(),
				serviceContext);
		}

		return updateTemplate(
			template.getGroupId(), template.getTemplateId(),
			template.getStructureId(), template.getNameMap(),
			template.getDescriptionMap(), template.getXsl(), true,
			template.getLangType(), template.isCacheable(),
			template.isSmallImage(), template.getSmallImageURL(),
			template.getSmallImageFile(), serviceContext);
	}

	protected void validateTemplateId(String templateId)
		throws PortalException {

		if (Validator.isNull(templateId) ||
			Validator.isNumber(templateId) ||
			(templateId.indexOf(CharPool.SPACE) != -1)) {

			throw new TemplateIdException();
		}
	}

}