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

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.asah.connector.internal.client.model.Experiment;
import com.liferay.segments.asah.connector.internal.client.model.ExperimentStatus;
import com.liferay.segments.asah.connector.internal.client.model.Goal;
import com.liferay.segments.asah.connector.internal.client.model.GoalMetric;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.exception.SegmentsExperimentGoalException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

/**
 * @author Sarai Díaz
 * @author David Arques
 */
public class ExperimentUtil {

	public static Experiment toExperiment(
			CompanyLocalService companyLocalService, String dataSourceId,
			GroupLocalService groupLocalService, Layout layout, Locale locale,
			Portal portal, SegmentsEntryLocalService segmentsEntryLocalService,
			SegmentsExperienceLocalService segmentsExperienceLocalService,
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		return toExperiment(
			dataSourceId,
			SegmentsEntryConstants.getDefaultSegmentsEntryName(locale),
			SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
				locale),
			layout, locale,
			_getLayoutFullURL(
				portal, companyLocalService, groupLocalService, layout),
			segmentsEntryLocalService, segmentsExperienceLocalService,
			segmentsExperiment);
	}

	public static Experiment toExperiment(
			CompanyLocalService companyLocalService, String dataSourceId,
			GroupLocalService groupLocalService,
			LayoutLocalService layoutLocalService, Locale locale, Portal portal,
			SegmentsEntryLocalService segmentsEntryLocalService,
			SegmentsExperienceLocalService segmentsExperienceLocalService,
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		return toExperiment(
			companyLocalService, dataSourceId, groupLocalService,
			layoutLocalService.getLayout(segmentsExperiment.getClassPK()),
			locale, portal, segmentsEntryLocalService,
			segmentsExperienceLocalService, segmentsExperiment);
	}

	protected static Experiment toExperiment(
			String dataSourceId, String defaultSegmentsEntryName,
			String defaultSegmentsExperienceName, Layout layout, Locale locale,
			String pageURL, SegmentsEntryLocalService segmentsEntryLocalService,
			SegmentsExperienceLocalService segmentsExperienceLocalService,
			SegmentsExperiment segmentsExperiment)
		throws PortalException {

		Experiment experiment = new Experiment();

		experiment.setConfidenceLevel(
			segmentsExperiment.getConfidenceLevel() * 100);
		experiment.setCreateDate(segmentsExperiment.getCreateDate());
		experiment.setDataSourceId(dataSourceId);
		experiment.setDescription(segmentsExperiment.getDescription());
		experiment.setDXPLayoutId(layout.getUuid());

		List<SegmentsExperimentRel> segmentsExperimentRels =
			segmentsExperiment.getSegmentsExperimentRels();

		if (ListUtil.isNotEmpty(segmentsExperimentRels)) {
			experiment.setDXPVariants(
				DXPVariantUtil.toDXPVariantList(
					locale, segmentsExperimentRels));
		}

		experiment.setExperimentStatus(
			_toExperimentStatus(segmentsExperiment.getStatus()));
		experiment.setGoal(_toExperimentGoal(segmentsExperiment));
		experiment.setId(segmentsExperiment.getSegmentsExperimentKey());
		experiment.setModifiedDate(segmentsExperiment.getModifiedDate());
		experiment.setName(segmentsExperiment.getName());
		experiment.setPageRelativePath(layout.getFriendlyURL());
		experiment.setPageTitle(layout.getTitle(locale));
		experiment.setPageURL(pageURL);

		if (segmentsExperiment.getStatus() ==
				SegmentsExperimentConstants.STATUS_COMPLETED) {

			experiment.setPublishedDXPVariantId(
				segmentsExperiment.getWinnerSegmentsExperienceKey());
		}

		if (segmentsExperiment.getSegmentsExperienceId() ==
				SegmentsExperienceConstants.ID_DEFAULT) {

			experiment.setDXPExperienceId(
				SegmentsExperienceConstants.KEY_DEFAULT);
			experiment.setDXPExperienceName(defaultSegmentsExperienceName);
			experiment.setDXPSegmentId(SegmentsEntryConstants.KEY_DEFAULT);
			experiment.setDXPSegmentName(defaultSegmentsEntryName);
		}
		else {
			SegmentsExperience segmentsExperience =
				segmentsExperienceLocalService.getSegmentsExperience(
					segmentsExperiment.getSegmentsExperienceId());

			experiment.setDXPExperienceId(
				segmentsExperience.getSegmentsExperienceKey());
			experiment.setDXPExperienceName(segmentsExperience.getName(locale));

			SegmentsEntry segmentsEntry =
				segmentsEntryLocalService.getSegmentsEntry(
					segmentsExperience.getSegmentsEntryId());

			experiment.setDXPSegmentId(segmentsEntry.getSegmentsEntryKey());
			experiment.setDXPSegmentName(segmentsEntry.getName(locale));
		}

		return experiment;
	}

	private static String _getLayoutFullURL(
			Portal portal, CompanyLocalService companyLocalService,
			GroupLocalService groupLocalService, Layout layout)
		throws PortalException {

		StringBundler sb = new StringBundler(4);

		Group group = groupLocalService.getGroup(layout.getGroupId());

		if (group.isLayout()) {
			long parentGroupId = group.getParentGroupId();

			if (parentGroupId > 0) {
				group = groupLocalService.getGroup(parentGroupId);
			}
		}

		String virtualHostname = null;

		LayoutSet layoutSet = layout.getLayoutSet();

		TreeMap<String, String> virtualHostnames =
			layoutSet.getVirtualHostnames();

		if (!virtualHostnames.isEmpty()) {
			virtualHostname = virtualHostnames.firstKey();
		}
		else {
			Company company = companyLocalService.getCompany(
				layout.getCompanyId());

			virtualHostname = company.getVirtualHostname();
		}

		boolean secure = StringUtil.equalsIgnoreCase(
			Http.HTTPS, PropsValues.WEB_SERVER_PROTOCOL);

		String portalURL = portal.getPortalURL(
			virtualHostname, portal.getPortalServerPort(secure), secure);

		sb.append(portalURL);

		if (layout.isPrivateLayout()) {
			sb.append(portal.getPathFriendlyURLPrivateGroup());
		}
		else {
			sb.append(portal.getPathFriendlyURLPublic());
		}

		sb.append(group.getFriendlyURL());
		sb.append(layout.getFriendlyURL());

		return sb.toString();
	}

	private static Goal _toExperimentGoal(SegmentsExperiment segmentsExperiment)
		throws SegmentsExperimentGoalException {

		SegmentsExperimentConstants.Goal goal =
			SegmentsExperimentConstants.Goal.parse(
				segmentsExperiment.getGoal());

		if (goal != null) {
			String goalName = goal.name();

			for (GoalMetric goalMetric : GoalMetric.values()) {
				if (goalName.equals(goalMetric.name())) {
					return new Goal(
						GoalMetric.valueOf(goalName),
						segmentsExperiment.getGoalTarget());
				}
			}
		}

		throw new SegmentsExperimentGoalException();
	}

	private static ExperimentStatus _toExperimentStatus(int status) {
		if (status == SegmentsExperimentConstants.STATUS_COMPLETED) {
			return ExperimentStatus.COMPLETED;
		}
		else if (status ==
					SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER) {

			return ExperimentStatus.FINISHED_NO_WINNER;
		}
		else if (status == SegmentsExperimentConstants.STATUS_FINISHED_WINNER) {
			return ExperimentStatus.FINISHED_WINNER;
		}
		else if (status == SegmentsExperimentConstants.STATUS_PAUSED) {
			return ExperimentStatus.PAUSED;
		}
		else if (status == SegmentsExperimentConstants.STATUS_RUNNING) {
			return ExperimentStatus.RUNNING;
		}
		else if (status == SegmentsExperimentConstants.STATUS_SCHEDULED) {
			return ExperimentStatus.SCHEDULED;
		}
		else if (status == SegmentsExperimentConstants.STATUS_TERMINATED) {
			return ExperimentStatus.TERMINATED;
		}

		return ExperimentStatus.DRAFT;
	}

}