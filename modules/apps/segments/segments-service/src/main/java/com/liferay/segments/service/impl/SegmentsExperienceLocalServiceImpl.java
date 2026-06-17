/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.LockedLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.exception.LockedSegmentsExperimentException;
import com.liferay.segments.exception.RequiredSegmentsExperienceException;
import com.liferay.segments.exception.SegmentsExperienceNameException;
import com.liferay.segments.exception.SegmentsExperiencePriorityException;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.base.SegmentsExperienceLocalServiceBaseImpl;
import com.liferay.segments.service.persistence.SegmentsExperimentPersistence;
import com.liferay.segments.service.persistence.SegmentsExperimentRelPersistence;
import com.liferay.segments.util.comparator.SegmentsExperiencePriorityComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsExperience",
	service = AopService.class
)
public class SegmentsExperienceLocalServiceImpl
	extends SegmentsExperienceLocalServiceBaseImpl {

	@Override
	public SegmentsExperience addDefaultSegmentsExperience(
			String externalReferenceCode, long userId, long plid,
			ServiceContext serviceContext)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(plid);

		return addSegmentsExperience(
			externalReferenceCode, userId, layout.getGroupId(), null, null,
			SegmentsExperienceConstants.KEY_DEFAULT, layout.getPlid(),
			Collections.singletonMap(
				LocaleUtil.getSiteDefault(),
				_language.get(LocaleUtil.getSiteDefault(), "default")),
			0, true, new UnicodeProperties(true), serviceContext);
	}

	@Override
	public SegmentsExperience addSegmentsExperience(
			String externalReferenceCode, long userId, long groupId,
			String segmentsEntryERC, String segmentsEntryScopeERC, long plid,
			Map<Locale, String> nameMap, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		int lowestPriority = getLowestPriority(groupId, plid);

		return addSegmentsExperience(
			externalReferenceCode, userId, groupId, segmentsEntryERC,
			segmentsEntryScopeERC, plid, nameMap, lowestPriority - 1, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	/**
	 * Adds a segments experience to a page, generating its key automatically.
	 *
	 * <p>
	 * The supplied priority is compacted with the priorities of the other
	 * experiences on the page so the priority of the returned segments
	 * experience may differ from the value supplied. See {@link
	 * #addSegmentsExperience(String, long, long, String, String, String, long,
	 * Map, int, boolean, UnicodeProperties, ServiceContext)} for the full
	 * priority-compaction contract.
	 * </p>
	 *
	 * @param  externalReferenceCode the segments experience's external reference
	 *         code
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @param  segmentsEntryERC the external reference code of the segments entry
	 *         the experience is associated with
	 * @param  segmentsEntryScopeERC the external reference code of the segments
	 *         entry's scope
	 * @param  plid the primary key of the layout
	 * @param  nameMap the segments experience's locales and localized names
	 * @param  priority the requested priority within the page. It must be unique
	 *         among the page's experiences, but it is compacted, so the
	 *         persisted value may differ.
	 * @param  active whether the segments experience is active
	 * @param  typeSettingsUnicodeProperties the segments experience's type
	 *         settings
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the segments experience.
	 * @return the segments experience
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public SegmentsExperience addSegmentsExperience(
			String externalReferenceCode, long userId, long groupId,
			String segmentsEntryERC, String segmentsEntryScopeERC, long plid,
			Map<Locale, String> nameMap, int priority, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		return addSegmentsExperience(
			externalReferenceCode, userId, groupId, segmentsEntryERC,
			segmentsEntryScopeERC,
			String.valueOf(counterLocalService.increment()), plid, nameMap,
			priority, active, typeSettingsUnicodeProperties, serviceContext);
	}

	/**
	 * Adds a segments experience to a page and compacts the priorities of every
	 * experience on that page.
	 *
	 * <p>
	 * The supplied priority must be unique within the page but it is not
	 * persisted verbatim. After the experience is created, the service renumbers
	 * the priorities of all the experiences on the page into sequential values
	 * (1, 2, 3, ... for active experiences and -1, -2, -3, ... for inactive
	 * experiences), mirroring {@link #updateSegmentsExperiencePriority(long,
	 * long, int)} and {@link #deleteSegmentsExperience(SegmentsExperience)}. The
	 * priority of the returned segments experience as well as the priorities of
	 * the other experiences on the same page, may therefore differ from the
	 * value supplied.
	 * </p>
	 *
	 * @param  externalReferenceCode the segments experience's external reference
	 *         code
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @param  segmentsEntryERC the external reference code of the segments entry
	 *         the experience is associated with
	 * @param  segmentsEntryScopeERC the external reference code of the segments
	 *         entry's scope
	 * @param  segmentsExperienceKey the key that identifies the segments
	 *         experience
	 * @param  plid the primary key of the layout
	 * @param  nameMap the segments experience's locales and localized names
	 * @param  priority the requested priority within the page. It must be unique
	 *         among the page's experiences, but it is compacted as described
	 *         above, so the persisted value may differ.
	 * @param  active whether the segments experience is active
	 * @param  typeSettingsUnicodeProperties the segments experience's type
	 *         settings
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the segments experience.
	 * @return the segments experience
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public SegmentsExperience addSegmentsExperience(
			String externalReferenceCode, long userId, long groupId,
			String segmentsEntryERC, String segmentsEntryScopeERC,
			String segmentsExperienceKey, long plid,
			Map<Locale, String> nameMap, int priority, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		_checkUnlockedLayout(plid, userId);

		// Segments experience

		User user = _userLocalService.getUser(userId);

		_validateName(nameMap);
		_validatePriority(groupId, plid, priority);

		long segmentsExperienceId = counterLocalService.increment();

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.create(segmentsExperienceId);

		segmentsExperience.setUuid(serviceContext.getUuid());
		segmentsExperience.setExternalReferenceCode(externalReferenceCode);
		segmentsExperience.setGroupId(groupId);
		segmentsExperience.setCompanyId(user.getCompanyId());
		segmentsExperience.setUserId(user.getUserId());
		segmentsExperience.setUserName(user.getFullName());
		segmentsExperience.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsExperience.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsExperience.setSegmentsEntryERC(segmentsEntryERC);
		segmentsExperience.setSegmentsEntryScopeERC(segmentsEntryScopeERC);
		segmentsExperience.setSegmentsExperienceKey(segmentsExperienceKey);
		segmentsExperience.setPlid(plid);
		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setPriority(priority);
		segmentsExperience.setActive(active);
		segmentsExperience.setTypeSettingsUnicodeProperties(
			typeSettingsUnicodeProperties);

		segmentsExperience = segmentsExperiencePersistence.update(
			segmentsExperience);

		segmentsExperiencePersistence.flush();

		// Resources

		_resourceLocalService.addModelResources(
			segmentsExperience, serviceContext);

		// Segments experiences priorities

		_compactSegmentsExperiencesPriorities(segmentsExperience);

		return segmentsExperiencePersistence.findByPrimaryKey(
			segmentsExperience.getSegmentsExperienceId());
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long userId, long groupId, String segmentsEntryERC,
			String segmentsEntryScopeERC, long plid,
			Map<Locale, String> nameMap, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		return appendSegmentsExperience(
			userId, groupId, segmentsEntryERC, segmentsEntryScopeERC, plid,
			nameMap, active, new UnicodeProperties(true), serviceContext);
	}

	@Override
	public SegmentsExperience appendSegmentsExperience(
			long userId, long groupId, String segmentsEntryERC,
			String segmentsEntryScopeERC, long plid,
			Map<Locale, String> nameMap, boolean active,
			UnicodeProperties typeSettingsUnicodeProperties,
			ServiceContext serviceContext)
		throws PortalException {

		int highestPriority = _getHighestPriority(groupId, plid);

		return addSegmentsExperience(
			null, userId, groupId, segmentsEntryERC, segmentsEntryScopeERC,
			plid, nameMap, highestPriority + 1, active,
			typeSettingsUnicodeProperties, serviceContext);
	}

	@Override
	public void deleteSegmentsEntrySegmentsExperiences(
			long groupId, String segmentsEntryERC, String segmentsEntryScopeERC)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findByG_SEERC_SESERC(
				groupId, segmentsEntryERC, segmentsEntryScopeERC);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience);
		}
	}

	@Override
	public void deleteSegmentsEntrySegmentsExperiences(
			String segmentsEntryERC, String segmentsEntryScopeERC)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findBySEERC_SESERC(
				segmentsEntryERC, segmentsEntryScopeERC);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience);
		}
	}

	/**
	 * Deletes the segments experience with the primary key and compacts the
	 * priorities of the remaining experiences on the page.
	 *
	 * <p>
	 * See {@link #deleteSegmentsExperience(SegmentsExperience)} for the full
	 * priority-compaction contract.
	 * </p>
	 *
	 * @param  segmentsExperienceId the primary key of the segments experience
	 * @return the deleted segments experience
	 * @throws PortalException if a segments experience with the primary key
	 *         could not be found
	 */
	@Override
	public SegmentsExperience deleteSegmentsExperience(
			long segmentsExperienceId)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		return segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience);
	}

	/**
	 * Deletes a segments experience and compacts the priorities of the
	 * remaining experiences on the page.
	 *
	 * <p>
	 * After the experience is removed, the service renumbers the priorities of
	 * the experiences left on the page into sequential values (1, 2, 3, ... for
	 * active experiences and -1, -2, -3, ... for inactive experiences) so their
	 * priorities may differ from the values previously returned. This matches
	 * the compaction performed by {@link #addSegmentsExperience(String, long,
	 * long, String, String, String, long, Map, int, boolean, UnicodeProperties,
	 * ServiceContext)} and {@link #updateSegmentsExperiencePriority(long, long,
	 * int)}. Compaction is skipped when the experience is removed as part of a
	 * group deletion.
	 * </p>
	 *
	 * @param  segmentsExperience the segments experience
	 * @return the deleted segments experience
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsExperience deleteSegmentsExperience(
			SegmentsExperience segmentsExperience)
		throws PortalException {

		// Segments experience

		if (!GroupThreadLocal.isDeleteInProcess() &&
			segmentsExperience.hasSegmentsExperiment()) {

			throw new RequiredSegmentsExperienceException.
				MustNotDeleteSegmentsExperienceReferencedBySegmentsExperiments(
					segmentsExperience.getSegmentsExperienceId());
		}

		if (!GroupThreadLocal.isDeleteInProcess()) {
			_checkUnlockedLayout(
				segmentsExperience.getPlid(), GuestOrUserUtil.getUserId());
		}

		segmentsExperiencePersistence.remove(segmentsExperience);

		segmentsExperiencePersistence.flush();

		// Segments experiences priorities

		if (!GroupThreadLocal.isDeleteInProcess()) {
			_compactSegmentsExperiencesPriorities(segmentsExperience);
		}

		// Segments experiments

		_deleteSegmentsExperiment(
			segmentsExperience.getGroupId(),
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getPlid());

		// Resources

		_resourceLocalService.deleteResource(
			segmentsExperience, ResourceConstants.SCOPE_INDIVIDUAL);

		return segmentsExperience;
	}

	/**
	 * Deletes the segments experience with the external reference code and
	 * compacts the priorities of the remaining experiences on the page.
	 *
	 * <p>
	 * See {@link #deleteSegmentsExperience(SegmentsExperience)} for the full
	 * priority-compaction contract.
	 * </p>
	 *
	 * @param  externalReferenceCode the external reference code of the segments
	 *         experience
	 * @param  groupId the primary key of the group
	 * @return the deleted segments experience
	 * @throws PortalException if a segments experience with the external
	 *         reference code could not be found
	 */
	@Override
	public SegmentsExperience deleteSegmentsExperience(
			String externalReferenceCode, long groupId)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByERC_G(
				externalReferenceCode, groupId);

		return segmentsExperienceLocalService.deleteSegmentsExperience(
			segmentsExperience);
	}

	@Override
	public void deleteSegmentsExperiences(long groupId, long plid)
		throws PortalException {

		// Segments experiments

		SegmentsExperience defaultSegmentsExperience = fetchSegmentsExperience(
			groupId, SegmentsExperienceConstants.KEY_DEFAULT, plid);

		if (defaultSegmentsExperience != null) {
			_deleteSegmentsExperiment(
				groupId, defaultSegmentsExperience.getSegmentsExperienceId(),
				plid);
		}

		// Segments experiences

		List<SegmentsExperience> segmentsExperiences =
			segmentsExperiencePersistence.findByG_P(groupId, plid);

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperience);
		}
	}

	@Override
	public SegmentsExperience fetchDefaultSegmentsExperience(long plid) {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return null;
		}

		return fetchSegmentsExperience(
			layout.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT, plid);
	}

	@Override
	public long fetchDefaultSegmentsExperienceId(long plid) {
		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return SegmentsExperienceConstants.ID_DEFAULT;
		}

		SegmentsExperience segmentsExperience = fetchSegmentsExperience(
			layout.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT, plid);

		if (segmentsExperience == null) {
			return SegmentsExperienceConstants.ID_DEFAULT;
		}

		return segmentsExperience.getSegmentsExperienceId();
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long segmentsExperienceId) {

		return segmentsExperiencePersistence.fetchByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, long plid, int priority) {

		return segmentsExperiencePersistence.fetchByG_P_P(
			groupId, plid, priority);
	}

	@Override
	public SegmentsExperience fetchSegmentsExperience(
		long groupId, String segmentsExperienceKey, long plid) {

		return segmentsExperiencePersistence.fetchByG_SEK_P(
			groupId, segmentsExperienceKey, plid);
	}

	@Override
	public int getLowestPriority(long groupId, long plid) {
		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_P_First(
				groupId, plid,
				SegmentsExperiencePriorityComparator.getInstance(true));

		if (segmentsExperience == null) {
			return 0;
		}

		return segmentsExperience.getPriority();
	}

	@Override
	public SegmentsExperience getSegmentsExperience(long segmentsExperienceId)
		throws PortalException {

		return segmentsExperiencePersistence.findByPrimaryKey(
			segmentsExperienceId);
	}

	@Override
	public SegmentsExperience getSegmentsExperience(
			long groupId, String segmentsExperienceKey, long plid)
		throws PortalException {

		return segmentsExperiencePersistence.findByG_SEK_P(
			groupId, segmentsExperienceKey, plid);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, boolean active)
		throws PortalException {

		return segmentsExperiencePersistence.findByG_A(groupId, active);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long plid) {

		return segmentsExperiencePersistence.findByG_P(groupId, plid);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
			long groupId, long plid, boolean active)
		throws PortalException {

		return segmentsExperiencePersistence.findByG_P_A(groupId, plid, active);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, long plid, boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return segmentsExperiencePersistence.findByG_P_A(
			groupId, plid, active, start, end, orderByComparator);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, String[] segmentsEntryERCs, String segmentsEntryScopeERC,
		long plid, boolean active) {

		return segmentsExperiencePersistence.findByG_SEERC_SESERC_P_A(
			groupId, segmentsEntryERCs, segmentsEntryScopeERC, plid, active);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long groupId, String[] segmentsEntryERCs, String segmentsEntryScopeERC,
		long plid, boolean active, int start, int end,
		OrderByComparator<SegmentsExperience> orderByComparator) {

		return segmentsExperiencePersistence.findByG_SEERC_SESERC_P_A(
			groupId, segmentsEntryERCs, segmentsEntryScopeERC, plid, active,
			start, end, orderByComparator);
	}

	@Override
	public List<SegmentsExperience> getSegmentsExperiences(
		long[] groupIds, boolean active) {

		return segmentsExperiencePersistence.findByG_A(groupIds, active);
	}

	@Override
	public int getSegmentsExperiencesCount(long groupId, long plid) {
		return segmentsExperiencePersistence.countByG_P(groupId, plid);
	}

	@Override
	public int getSegmentsExperiencesCount(
		long groupId, long plid, boolean active) {

		return segmentsExperiencePersistence.countByG_P_A(
			groupId, plid, active);
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long userId, long segmentsExperienceId, String segmentsEntryERC,
			String segmentsEntryScopeERC, Map<Locale, String> nameMap,
			boolean active)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		return updateSegmentsExperience(
			userId, segmentsExperienceId, segmentsEntryERC,
			segmentsEntryScopeERC, nameMap, active,
			segmentsExperience.getTypeSettingsUnicodeProperties());
	}

	@Override
	public SegmentsExperience updateSegmentsExperience(
			long userId, long segmentsExperienceId, String segmentsEntryERC,
			String segmentsEntryScopeERC, Map<Locale, String> nameMap,
			boolean active, UnicodeProperties typeSettingsUnicodeProperties)
		throws PortalException {

		_validateName(nameMap);

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		if (segmentsExperience.hasSegmentsExperiment()) {
			throw new LockedSegmentsExperimentException(
				"Segments experience " + segmentsExperienceId +
					" has a locked segments experiment");
		}

		_checkUnlockedLayout(segmentsExperience.getPlid(), userId);

		segmentsExperience.setSegmentsEntryERC(segmentsEntryERC);
		segmentsExperience.setSegmentsEntryScopeERC(segmentsEntryScopeERC);
		segmentsExperience.setNameMap(nameMap);
		segmentsExperience.setActive(active);
		segmentsExperience.setTypeSettingsUnicodeProperties(
			typeSettingsUnicodeProperties);

		return segmentsExperiencePersistence.update(segmentsExperience);
	}

	@Override
	public SegmentsExperience updateSegmentsExperienceActive(
			long userId, long segmentsExperienceId, boolean active)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		_checkUnlockedLayout(segmentsExperience.getPlid(), userId);

		segmentsExperience.setActive(active);

		return segmentsExperiencePersistence.update(segmentsExperience);
	}

	/**
	 * Updates the priority of a segments experience and compacts the priorities
	 * of every experience on the page.
	 *
	 * <p>
	 * If another experience already holds <code>newPriority</code>, the two
	 * experiences swap priorities; otherwise the experience is simply moved to
	 * <code>newPriority</code>. A <code>newPriority</code> of <code>0</code>
	 * deactivates an active experience and activates an inactive one. After the
	 * move, the service renumbers the priorities of all the experiences on the
	 * page into sequential values (1, 2, 3, ... for active experiences and -1,
	 * -2, -3, ... for inactive experiences) so the priority of the returned
	 * segments experience as well as the priorities of the other experiences on
	 * the same page, may differ from <code>newPriority</code>. This matches the
	 * compaction performed by {@link #addSegmentsExperience(String, long, long,
	 * String, String, String, long, Map, int, boolean, UnicodeProperties,
	 * ServiceContext)} and {@link
	 * #deleteSegmentsExperience(SegmentsExperience)}.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  segmentsExperienceId the primary key of the segments experience
	 * @param  newPriority the requested priority within the page. Use
	 *         <code>0</code> to toggle the experience between active and
	 *         inactive.
	 * @return the updated segments experience
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public SegmentsExperience updateSegmentsExperiencePriority(
			long userId, long segmentsExperienceId, int newPriority)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperienceId);

		if (segmentsExperience.hasSegmentsExperiment()) {
			throw new LockedSegmentsExperimentException(
				"Segments experience " + segmentsExperienceId +
					" has a locked segments experiment");
		}

		_checkUnlockedLayout(segmentsExperience.getPlid(), userId);

		boolean swap = true;

		if ((newPriority == 0) && (segmentsExperience.getPriority() > 0)) {
			newPriority = -1;
			swap = false;
		}
		else if ((newPriority == 0) && (segmentsExperience.getPriority() < 0)) {
			newPriority = 1;
			swap = false;
		}

		SegmentsExperience swapSegmentsExperience =
			segmentsExperiencePersistence.fetchByG_P_P(
				segmentsExperience.getGroupId(), segmentsExperience.getPlid(),
				newPriority);

		if (swapSegmentsExperience == null) {
			_updateSegmentsExperiencePriorityAndFlush(
				newPriority, segmentsExperience);

			_compactSegmentsExperiencesPriorities(segmentsExperience);

			return segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperience.getSegmentsExperienceId());
		}

		int oldPriority = segmentsExperience.getPriority();

		_releaseSegmentExperiencesPriority(
			newPriority, segmentsExperience, swapSegmentsExperience);

		_updateSegmentsExperiencePriorityAndFlush(
			newPriority,
			segmentsExperiencePersistence.findByPrimaryKey(
				segmentsExperience.getSegmentsExperienceId()));

		if (swap) {
			_updateSegmentsExperiencePriorityAndFlush(
				oldPriority,
				segmentsExperiencePersistence.findByPrimaryKey(
					swapSegmentsExperience.getSegmentsExperienceId()));
		}

		_compactSegmentsExperiencesPriorities(segmentsExperience);

		return segmentsExperiencePersistence.findByPrimaryKey(
			segmentsExperience.getSegmentsExperienceId());
	}

	private void _checkUnlockedLayout(long plid, long userId)
		throws PortalException {

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if ((layout != null) && !layout.isUnlocked(Constants.EDIT, userId)) {
			throw new LockedLayoutException();
		}
	}

	private void _compactSegmentsExperiencesPriorities(
		SegmentsExperience segmentsExperience) {

		List<SegmentsExperience> segmentsExperiences = new ArrayList<>(
			segmentsExperiencePersistence.findByG_P_GtP(
				segmentsExperience.getGroupId(), segmentsExperience.getPlid(),
				0));

		int updatedPriority = 1;

		for (int i = segmentsExperiences.size(); i > 0;
			 i--, updatedPriority++) {

			SegmentsExperience curSegmentsExperience = segmentsExperiences.get(
				i - 1);

			if (curSegmentsExperience.getPriority() != updatedPriority) {
				_updateSegmentsExperiencePriorityAndFlush(
					updatedPriority, curSegmentsExperience);
			}
		}

		segmentsExperiences = new ArrayList<>(
			segmentsExperiencePersistence.findByG_P_LtP(
				segmentsExperience.getGroupId(), segmentsExperience.getPlid(),
				0));

		updatedPriority = -1;

		for (int i = 0; i < segmentsExperiences.size();
			 i++, updatedPriority--) {

			SegmentsExperience curSegmentsExperience = segmentsExperiences.get(
				i);

			if (curSegmentsExperience.getPriority() != updatedPriority) {
				_updateSegmentsExperiencePriorityAndFlush(
					updatedPriority, curSegmentsExperience);
			}
		}
	}

	private void _deleteSegmentsExperiment(
			long groupId, long segmentsExperienceId, long plid)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			_segmentsExperimentPersistence.fetchByG_S_P(
				groupId, segmentsExperienceId, plid);

		if (segmentsExperiment == null) {
			return;
		}

		_segmentsExperimentPersistence.remove(segmentsExperiment);

		_resourceLocalService.deleteResource(
			segmentsExperiment, ResourceConstants.SCOPE_INDIVIDUAL);

		_segmentsExperimentRelPersistence.removeBySegmentsExperimentId(
			segmentsExperiment.getSegmentsExperimentId());
	}

	private int _getHighestPriority(long groupId, long plid) {
		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_P_First(groupId, plid, null);

		if (segmentsExperience == null) {
			return 0;
		}

		return segmentsExperience.getPriority();
	}

	private void _releaseSegmentExperiencesPriority(
		int priority, SegmentsExperience segmentsExperience,
		SegmentsExperience swapSegmentsExperience) {

		if (priority > 0) {
			List<SegmentsExperience> segmentsExperiences = new ArrayList<>(
				segmentsExperiencePersistence.findByG_P_GtP(
					segmentsExperience.getGroupId(),
					segmentsExperience.getPlid(), priority));

			segmentsExperiences.add(swapSegmentsExperience);

			for (SegmentsExperience curSegmentsExperience :
					segmentsExperiences) {

				_updateSegmentsExperiencePriorityAndFlush(
					curSegmentsExperience.getPriority() + 1,
					curSegmentsExperience);
			}
		}
		else {
			List<SegmentsExperience> segmentsExperiences = ListUtil.fromArray(
				swapSegmentsExperience);

			segmentsExperiences.addAll(
				segmentsExperiencePersistence.findByG_P_LtP(
					segmentsExperience.getGroupId(),
					segmentsExperience.getPlid(), priority));

			for (int i = segmentsExperiences.size(); i > 0; i--) {
				SegmentsExperience curSegmentsExperience =
					segmentsExperiences.get(i - 1);

				_updateSegmentsExperiencePriorityAndFlush(
					curSegmentsExperience.getPriority() - 1,
					curSegmentsExperience);
			}
		}
	}

	private void _updateSegmentsExperiencePriorityAndFlush(
		int priority, SegmentsExperience segmentsExperience) {

		segmentsExperience.setPriority(priority);

		segmentsExperiencePersistence.update(segmentsExperience);

		segmentsExperiencePersistence.flush();
	}

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if (nameMap.isEmpty() || Validator.isNull(nameMap.get(locale))) {
			throw new SegmentsExperienceNameException();
		}
	}

	private void _validatePriority(long groupId, long plid, int priority)
		throws PortalException {

		SegmentsExperience segmentsExperience =
			segmentsExperiencePersistence.fetchByG_P_P(groupId, plid, priority);

		if (segmentsExperience != null) {
			throw new SegmentsExperiencePriorityException(
				"A segments experience with the priority " + priority +
					" already exists");
		}
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private SegmentsExperimentPersistence _segmentsExperimentPersistence;

	@Reference
	private SegmentsExperimentRelPersistence _segmentsExperimentRelPersistence;

	@Reference
	private UserLocalService _userLocalService;

}