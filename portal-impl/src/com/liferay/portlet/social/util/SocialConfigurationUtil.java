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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.JavaFieldsParser;
import com.liferay.portlet.social.model.SocialAchievement;
import com.liferay.portlet.social.model.SocialActivityCounterConstants;
import com.liferay.portlet.social.model.SocialActivityCounterDefinition;
import com.liferay.portlet.social.model.SocialActivityDefinition;
import com.liferay.portlet.social.model.SocialActivityProcessor;

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
public class SocialConfigurationUtil {

	public static Collection<String> getActivityCounterNames() {
		return getActivityCounterNames(SocialActivityCounterConstants.TYPE_ALL);
	}

	public static Collection<String> getActivityCounterNames(int ownerType) {
		Set<String> activityCounterNames = new HashSet<String>();

		for (Map<Integer, SocialActivityDefinition> activityDefinitions :
				_activityDefinitions.values()) {

			for (SocialActivityDefinition activityDefinition :
					activityDefinitions.values()) {

				for (SocialActivityCounterDefinition activityCounterDefinition :
						activityDefinition.getActivityCounterDefinitions()) {

					if ((ownerType ==
							SocialActivityCounterConstants.TYPE_ALL) ||
						(ownerType ==
							activityCounterDefinition.getOwnerType())) {

						activityCounterNames.add(
							activityCounterDefinition.getName());
					}
				}
			}
		}

		return activityCounterNames;
	}

	public static SocialActivityDefinition getActivityDefinition(
		String modelName, int activityType) {

		Map<Integer, SocialActivityDefinition> activityDefinitions =
			_activityDefinitions.get(modelName);

		if (activityDefinitions == null) {
			return null;
		}

		return activityDefinitions.get(activityType);
	}

	public static List<SocialActivityDefinition> getActivityDefinitions(
		String modelName) {

		Map<Integer, SocialActivityDefinition> activityDefinitions =
			_activityDefinitions.get(modelName);

		if (activityDefinitions == null) {
			return Collections.emptyList();
		}

		return ListUtil.fromCollection(activityDefinitions.values());
	}

	public static String[] getActivityModelNames() {
		Set<String> activityModelNames = _activityDefinitions.keySet();

		return activityModelNames.toArray(
			new String[activityModelNames.size()]);
	}

	public static List<Object> read(ClassLoader classLoader, String[] xmls)
		throws Exception {

		List<Object> objects = new ArrayList<Object>();

		for (String xml : xmls) {
			_read(classLoader, xml, objects);
		}

		return objects;
	}

	public static void removeActivityDefinition(
		SocialActivityDefinition activityDefinition) {

		Map<Integer, SocialActivityDefinition> activityDefinitions =
			_activityDefinitions.get(activityDefinition.getModelName());

		if (activityDefinitions != null) {
			activityDefinitions.remove(activityDefinition.getActivityType());
		}
	}

	private static void _read(
			ClassLoader classLoader, String xml, List<Object> objects)
		throws Exception {

		if (xml == null) {
			return;
		}

		xml = JavaFieldsParser.parse(classLoader, xml);

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> activityElements = rootElement.elements("activity");

		for (Element activityElement : activityElements) {
			_readActivity(classLoader, objects, activityElement);
		}
	}

	private static void _readAchievement(
			ClassLoader classLoader, List<Object> objects,
			SocialActivityDefinition activityDefinition,
			Element achievementElement)
		throws Exception {

		String achievementClassName = GetterUtil.getString(
			achievementElement.elementText("achievement-class"));

		SocialAchievement achievement =
			(SocialAchievement)ProxyFactory.newInstance(
				classLoader, SocialAchievement.class, achievementClassName);

		String name = GetterUtil.getString(
			achievementElement.elementText("name"));

		achievement.setName(name);

		String icon = GetterUtil.getString(
			achievementElement.elementText("icon"));

		achievement.setIcon(icon);

		List<Element> propertyElements = achievementElement.elements(
			"property");

		for (Element propertyElement : propertyElements) {
			_readAchievementProperty(achievement, propertyElement);
		}

		List<SocialAchievement> achievements =
			activityDefinition.getAchievements();

		achievements.add(achievement);

		Tuple tuple = new Tuple(activityDefinition, achievement);

		objects.add(tuple);
	}

	private static void _readAchievementProperty(
		SocialAchievement achievement, Element propertyElement) {

		String name = GetterUtil.getString(
			propertyElement.elementText("name"));
		String value = GetterUtil.getString(
			propertyElement.elementText("value"));

		BeanPropertiesUtil.setProperty(achievement, name, value);
	}

	private static void _readActivity(
			ClassLoader classLoader, Element activityElement,
			SocialActivityDefinition activityDefinition)
		throws Exception {

		String languageKey = GetterUtil.getString(
			activityElement.elementText("language-key"));

		activityDefinition.setLanguageKey(languageKey);

		boolean logActivity = GetterUtil.getBoolean(
			activityElement.elementText("log-activity"));

		activityDefinition.setLogActivity(logActivity);

		String processorClassName = GetterUtil.getString(
			activityElement.elementText("processor-class"));

		if (Validator.isNotNull(processorClassName)) {
			SocialActivityProcessor activityProcessor =
				(SocialActivityProcessor)ProxyFactory.newInstance(
					classLoader, SocialActivityProcessor.class,
					processorClassName);

			activityDefinition.setActivityProcessor(activityProcessor);
		}

		_readActivityContribution(activityElement, activityDefinition);
		_readActivityParticipation(activityElement, activityDefinition);
	}

	private static void _readActivity(
			ClassLoader classLoader, List<Object> objects,
			Element activityElement)
		throws Exception {

		String modelName = GetterUtil.getString(
			activityElement.elementText("model-name"));

		Map<Integer, SocialActivityDefinition> activityDefinitions =
			_activityDefinitions.get(modelName);

		if (activityDefinitions == null) {
			activityDefinitions =
				new HashMap<Integer, SocialActivityDefinition>();

			_activityDefinitions.put(modelName, activityDefinitions);
		}

		int activityType = GetterUtil.getInteger(
			activityElement.elementText("activity-type"));

		SocialActivityDefinition activityDefinition = activityDefinitions.get(
			activityType);

		if (activityDefinition == null) {
			activityDefinition = new SocialActivityDefinition();

			activityDefinition.setModelName(modelName);
			activityDefinition.setActivityType(activityType);

			_readActivity(classLoader, activityElement, activityDefinition);

			activityDefinitions.put(activityType, activityDefinition);

			objects.add(activityDefinition);
		}

		List<Element> counterElements = activityElement.elements("counter");

		for (Element counterElement : counterElements) {
			_readCounter(objects, activityDefinition, counterElement);
		}

		List<Element> achievementElements = activityElement.elements(
			"achievement");

		for (Element achievementElement : achievementElements) {
			_readAchievement(
				classLoader, objects, activityDefinition, achievementElement);
		}
	}

	private static void _readActivityContribution(
		Element activityElement, SocialActivityDefinition activityDefinition) {

		Element contributionValueElement = activityElement.element(
			"contribution-value");

		if (contributionValueElement == null) {
			return;
		}

		SocialActivityCounterDefinition contributionActivityCounterDefinition =
			new SocialActivityCounterDefinition();

		contributionActivityCounterDefinition.setName(
			SocialActivityCounterConstants.NAME_CONTRIBUTION);
		contributionActivityCounterDefinition.setOwnerType(
			SocialActivityCounterConstants.TYPE_CREATOR);

		int increment = GetterUtil.getInteger(
			contributionValueElement.getText());

		contributionActivityCounterDefinition.setIncrement(increment);

		Element contributionLimitElement = activityElement.element(
			"contribution-limit");

		if (contributionLimitElement != null) {
			String limitPeriod = contributionLimitElement.attributeValue(
				"period");

			contributionActivityCounterDefinition.setLimitPeriod(limitPeriod);

			int limitValue = GetterUtil.getInteger(
				contributionLimitElement.getText());

			contributionActivityCounterDefinition.setLimitValue(limitValue);
		}

		activityDefinition.addCounter(contributionActivityCounterDefinition);

		SocialActivityCounterDefinition popularityActivityCounterDefinition =
			new SocialActivityCounterDefinition();

		popularityActivityCounterDefinition.setName(
			SocialActivityCounterConstants.NAME_POPULARITY);
		popularityActivityCounterDefinition.setOwnerType(
			SocialActivityCounterConstants.TYPE_ASSET);
		popularityActivityCounterDefinition.setIncrement(
			contributionActivityCounterDefinition.getIncrement());
		popularityActivityCounterDefinition.setLimitPeriod(
			contributionActivityCounterDefinition.getLimitPeriod());
		popularityActivityCounterDefinition.setLimitValue(
			contributionActivityCounterDefinition.getLimitValue());

		activityDefinition.addCounter(popularityActivityCounterDefinition);
	}

	private static void _readActivityParticipation(
		Element activityElement, SocialActivityDefinition activityDefinition) {

		Element participationValueElement = activityElement.element(
			"participation-value");

		if (participationValueElement == null) {
			return;
		}

		SocialActivityCounterDefinition activityCounterDefinition =
			new SocialActivityCounterDefinition();

		activityCounterDefinition.setName(
			SocialActivityCounterConstants.NAME_PARTICIPATION);
		activityCounterDefinition.setOwnerType(
			SocialActivityCounterConstants.TYPE_ACTOR);

		int increment = GetterUtil.getInteger(
			participationValueElement.getText());

		activityCounterDefinition.setIncrement(increment);

		Element participationLimitElement = activityElement.element(
			"participation-limit");

		if (participationLimitElement != null) {
			String limitPeriod = participationLimitElement.attributeValue(
				"period");

			activityCounterDefinition.setLimitPeriod(limitPeriod);

			int limitValue = GetterUtil.getInteger(
				participationLimitElement.getText());

			activityCounterDefinition.setLimitValue(limitValue);
		}

		activityDefinition.addCounter(activityCounterDefinition);
	}

	private static void _readCounter(
		List<Object> objects, SocialActivityDefinition activityDefinition,
		Element counterElement) {

		SocialActivityCounterDefinition activityCounterDefinition =
			new SocialActivityCounterDefinition();

		String name = GetterUtil.getString(counterElement.elementText("name"));

		activityCounterDefinition.setName(name);

		String ownerType = GetterUtil.getString(
			counterElement.elementText("owner-type"));

		activityCounterDefinition.setOwnerType(ownerType);

		int increment = GetterUtil.getInteger(
			counterElement.elementText("increment"), 1);

		activityCounterDefinition.setIncrement(increment);

		if (activityCounterDefinition.getOwnerType() == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid owner type " + ownerType + " for  model " +
						activityDefinition.getModelName());
			}

			return;
		}

		activityDefinition.addCounter(activityCounterDefinition);

		Tuple tuple = new Tuple(activityDefinition, activityCounterDefinition);

		objects.add(tuple);
	}

	private static Log _log = LogFactoryUtil.getLog(
		SocialConfigurationUtil.class);

	private static Map<String, Map<Integer, SocialActivityDefinition>>
		_activityDefinitions =
			new HashMap<String, Map<Integer, SocialActivityDefinition>>();

}