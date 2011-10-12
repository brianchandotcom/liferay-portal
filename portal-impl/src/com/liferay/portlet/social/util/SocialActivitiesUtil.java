/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivityHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Zsolt Berentey
 */
public class SocialActivitiesUtil {

	public String[] getSocialActivityClassNames() {
		Set<String> classNames = _socialActivityDefinitions.keySet();

		return classNames.toArray(new String[classNames.size()]);
	}

	public Collection<String> getSocialActivityCounters() {
		return getSocialActivityCounters(
			SocialActivityCounterConstants.TYPE_ALL);
	}

	public Collection<String> getSocialActivityCounters(int ownerType) {
		Set<String> counterSet =
			new HashSet<String>();

		for (Map<Integer, SocialActivityDefinition> classNameMap :
				_socialActivityDefinitions.values()) {

			for (SocialActivityDefinition definition : classNameMap.values()) {
				for (SocialActivityCounterDefinition counter :
						definition.getCounters()) {

					if (ownerType == SocialActivityCounterConstants.TYPE_ALL ||
						counter.getOwnerType() == ownerType) {

						counterSet.add(counter.getName());
					}
				}
			}
		}

		return counterSet;
	}

	public static SocialActivityDefinition getSocialActivityDefinition(
		String modelName, int activityKey) {

		Map<Integer, SocialActivityDefinition> socialActivityDefinitions =
			_socialActivityDefinitions.get(modelName);

		if (socialActivityDefinitions == null) {
			return null;
		}

		return socialActivityDefinitions.get(activityKey);
	}

	public static List<SocialActivityDefinition> getSocialActivityDefinitions(
		String modelName) {

		Map<Integer, SocialActivityDefinition> socialActivityDefinitions =
			_socialActivityDefinitions.get(modelName);

		if (socialActivityDefinitions == null) {
			return Collections.emptyList();
		}

		List<SocialActivityDefinition> SocialActivityDefinitionList =
			new ArrayList<SocialActivityDefinition>();

		for (Map.Entry<Integer, SocialActivityDefinition> entry :
				socialActivityDefinitions.entrySet()) {

			SocialActivityDefinitionList.add(entry.getValue());
		}

		return SocialActivityDefinitionList;
	}

	public static String[] getSocialActivityDefinitonModels() {
		Set<String> classNames = _socialActivityDefinitions.keySet();

		return classNames.toArray(new String[classNames.size()]);
	}

	public static void readXml(
		Document document, HotDeployEvent event,
		Map<String, Object> hotDeployRegistry) throws Exception {

		if (document == null) {
			return;
		}

		Element rootElement = document.getRootElement();

		if (!rootElement.getName().equals("social-activities")) {
			return;
		}

		readActivities(rootElement, event, hotDeployRegistry);
	}

	public static void readXmls(String[] xmls) throws Exception {

		Document document = SAXReaderUtil.read(xmls[0], true);

		if (document != null) {
			readXml(document, null, null);
		}
	}

	public static void removeSocialActivityDefinition(
		SocialActivityDefinition activityDefinition) {

		Map<Integer, SocialActivityDefinition> socialActivityDefinitions =
			_socialActivityDefinitions.get(activityDefinition.getModelName());

		if (socialActivityDefinitions != null) {
			socialActivityDefinitions.remove(
				activityDefinition.getActivityKey());
		}
	}

	protected static int decodeLimitPeriod(String limitPeriod) {
		for (int i=0; i < _LIMIT_PERIODS.length; i++) {
			if (_LIMIT_PERIODS[i].equalsIgnoreCase(limitPeriod)) {
				return i + 1;
			}
		}

		_log.error("Invalid time period given in resource xml: " + limitPeriod);

		return 0;
	}

	@SuppressWarnings("rawtypes")
	protected static void readAchievement(
		Element achievementElement, SocialActivityDefinition activityDefinition,
		HotDeployEvent event, Map<String, Object> hotDeployRegistry) {

		ClassLoader classLoader = null;

		if (event != null) {
			classLoader = event.getContextClassLoader();
		}
		else {
			classLoader = PortalClassLoaderUtil.getClassLoader();
		}

		Element achievementClassElement = achievementElement.element(
			"achievement-class");

		if (achievementClassElement == null ||
				achievementClassElement.getTextTrim().equals("")) {

			return;
		}

		SocialAchievement achievement = null;

		try {
			Class clazz = classLoader.loadClass(
				achievementClassElement.getTextTrim());

			achievement = (SocialAchievement)clazz.newInstance();

		} catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Cannot instantiate achievement class " +
					achievementClassElement.getTextTrim(), e);
			}

			return;
		}

		achievement.setName(achievementElement.element("name").getTextTrim());
		achievement.setIcon(achievementElement.element("icon").getTextTrim());

		// Properties

		for (Element property : achievementElement.elements("property")) {
			readAchievementProperty(property, achievement);
		}

		activityDefinition.getAchievements().add(achievement);

		if (hotDeployRegistry != null) {
			String path = event.getServletContextName() + StringPool.SLASH +
				activityDefinition.getModelName() + StringPool.SLASH +
				activityDefinition.getActivityKey() + _ACHIEVEMENT_ +
				achievement.getName();

			hotDeployRegistry.put(path, achievement);
		}
	}

	protected static void readAchievementProperty(
		Element property, SocialAchievement achievement) {

		String name = property.element("name").getTextTrim();
		String value = property.element("value").getTextTrim();

		BeanPropertiesUtil.setProperty(achievement, name, value);
	}

	protected static void readActivities(
		Element socialActivitiesElement, HotDeployEvent event,
		Map<String, Object> hotDeployRegistry) {

		if (socialActivitiesElement == null) {
			return;
		}

		for (Element socialActivityDefinitionElement :
				socialActivitiesElement.elements("activity")) {

			readActivity(
				socialActivityDefinitionElement, event, hotDeployRegistry);
		}
	}

	protected static void readActivity(
		Element activityElement, HotDeployEvent event,
		Map<String, Object> hotDeployRegistry) {

		Element modelRefElement = activityElement.element("model-ref");

		if (modelRefElement == null) {
			return;
		}

		String modelName = modelRefElement.getTextTrim();

		Element activityIdElement =
			activityElement.element("activity-key");

		if (activityIdElement == null) {
			return;
		}

		String activityKey = activityIdElement.getTextTrim();

		if (Validator.isNull(activityKey)) {
			return;
		}

		Map<Integer, SocialActivityDefinition> socialActivityDefinitions =
			_socialActivityDefinitions.get(modelName);

		if (socialActivityDefinitions == null) {
			socialActivityDefinitions =
				new HashMap<Integer, SocialActivityDefinition>();

			_socialActivityDefinitions.put(
				modelName, socialActivityDefinitions);
		}

		SocialActivityDefinition socialActivityDefinition =
			socialActivityDefinitions.get(activityKey);

		if (socialActivityDefinition == null) {

			String languageKey = activityElement.elementText(
				"language-key");

			boolean logActivity = GetterUtil.getBoolean(
				activityElement.elementText("log-activity"));

			SocialActivityHandler activityHandler = null;

			String handlerClassName =
				activityElement.elementText("handler-class");

			if (handlerClassName != null) {
				ClassLoader classLoader = null;

				if (event != null) {
					classLoader = event.getContextClassLoader();
				}
				else {
					classLoader = PortalClassLoaderUtil.getClassLoader();
				}

				if (classLoader != null) {
					try {
						activityHandler =
							(SocialActivityHandler)classLoader.loadClass(
								handlerClassName).newInstance();
					}
					catch (ClassNotFoundException cnfe) {
					}
					catch (Exception e) {
						_log.error(
							"Cannot instantiate social activity handler " +
							handlerClassName, e);
					}
				}
				else {
					_log.error("Cannot find class loader to load activity "+
						"handler class " + handlerClassName);

				}
			}

			socialActivityDefinition = new SocialActivityDefinition();

			socialActivityDefinition.setModelName(modelName);
			socialActivityDefinition.setActivityKey(
				GetterUtil.getInteger(activityKey));
			socialActivityDefinition.setActivityHandler(activityHandler);
			socialActivityDefinition.setLanguageKey(languageKey);
			socialActivityDefinition.setLogActivity(logActivity);

			Element contributionValueElement =
				activityElement.element("contribution-value");
			Element contributionLimitElement =
				activityElement.element("contribution-limit");

			if (contributionValueElement != null) {
				SocialActivityCounterDefinition contributionCounter =
					new SocialActivityCounterDefinition(
						SocialActivityCounterConstants.NAME_CONTRIBUTION,
						SocialActivityCounterConstants.TYPE_CREATOR);

				contributionCounter.setIncrement(
					GetterUtil.getInteger(contributionValueElement.getText()));

				if (contributionLimitElement != null) {
					contributionCounter.setLimit(
						GetterUtil.getInteger(
							contributionLimitElement.getText()));
					contributionCounter.setLimitType(
						decodeLimitPeriod(
							contributionLimitElement.attributeValue("period")));
				}

				SocialActivityCounterDefinition popularityCounter =
					new SocialActivityCounterDefinition(
						SocialActivityCounterConstants.NAME_POPULARITY,
						SocialActivityCounterConstants.TYPE_ASSET);

				popularityCounter.setIncrement(
					contributionCounter.getIncrement());
				popularityCounter.setLimitType(
					contributionCounter.getLimitType());
				popularityCounter.setLimit(contributionCounter.getLimit());

				socialActivityDefinition.addCounter(contributionCounter);

				socialActivityDefinition.addCounter(popularityCounter);
			}

			Element participationValueElement =
				activityElement.element("participation-value");
			Element participationLimitElement =
				activityElement.element("participation-limit");

			if (participationValueElement != null) {
				SocialActivityCounterDefinition participationCounter =
					new SocialActivityCounterDefinition(
						SocialActivityCounterConstants.NAME_PARTICIPATION,
						SocialActivityCounterConstants.TYPE_ACTOR);

				participationCounter.setIncrement(
					GetterUtil.getInteger(participationValueElement.getText()));

				if (participationLimitElement != null) {
					participationCounter.setLimit(
						GetterUtil.getInteger(
							participationLimitElement.getText()));
					participationCounter.setLimitType(
						decodeLimitPeriod(
							participationLimitElement.attributeValue(
								"period")));
				}

				socialActivityDefinition.addCounter(participationCounter);
			}

			socialActivityDefinitions.put(
				socialActivityDefinition.getActivityKey(),
				socialActivityDefinition);

			if (hotDeployRegistry != null) {
				String path = event.getServletContextName() +
					StringPool.SLASH + socialActivityDefinition.getModelName() +
					StringPool.SLASH +
					socialActivityDefinition.getActivityKey() + _ACTIVITY_ +
					socialActivityDefinition.getActivityKey();

				hotDeployRegistry.put(path, socialActivityDefinition);
			}
		}

		// Counters

		for (Element socialActivityCounterElement :
				activityElement.elements("counter")) {

			readCounter(
				socialActivityCounterElement, socialActivityDefinition,
				event, hotDeployRegistry);
		}

		// Achievements

		for (Element achievementElement :
			activityElement.elements("achievement")) {

			readAchievement(achievementElement, socialActivityDefinition,
				event, hotDeployRegistry);
		}
	}

	protected static void readCounter(
		Element socialActivityCounterElement,
		SocialActivityDefinition activityDefinition,
		HotDeployEvent event, Map<String, Object> hotDeployRegistry) {

		String name = socialActivityCounterElement.elementText("name");

		String ownerType = socialActivityCounterElement.elementText("owner");

		int increment = GetterUtil.getInteger(
			socialActivityCounterElement.elementText("increment"), 1);

		SocialActivityCounterDefinition counter =
			new SocialActivityCounterDefinition();

		counter.setName(name);
		counter.setOwnerType(ownerType);
		counter.setIncrement(increment);

		if (counter.getOwnerType() == 0) {
			_log.warn(
				"Unknown owner type '"+ ownerType +
				"' in social activity configuration for model '" +
				activityDefinition.getModelName() + "'");

			return;
		}

		activityDefinition.addCounter(counter);

		if (hotDeployRegistry != null) {
			String path = event.getServletContextName() +
				StringPool.SLASH + activityDefinition.getModelName() +
				StringPool.SLASH + activityDefinition.getActivityKey() +
				_COUNTER_ + counter.getKey();

			hotDeployRegistry.put(path, counter);
		}
	}

	private static String _ACTIVITY_ = "/activity/";

	private static String _ACHIEVEMENT_ = "/achievement/";

	private static String _COUNTER_ = "/counter/";

	private static final String[] _LIMIT_PERIODS = {
		"day", "lifetime", "period"
	};

	private static Log _log = LogFactoryUtil.getLog(SocialActivitiesUtil.class);

	private static Map<String, Map<Integer, SocialActivityDefinition>>
		_socialActivityDefinitions =
			new HashMap<String, Map<Integer, SocialActivityDefinition>>();;

}