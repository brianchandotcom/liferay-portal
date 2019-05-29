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

package com.liferay.frontend.taglib.form.navigator.internal.osgi.commands;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=getPossibleConfigurations",
		"osgi.command.scope=formNavigator"
	},
	service = FormNavigatorOSGiCommands.class
)
public class FormNavigatorOSGiCommands {

	public void getPossibleConfigurations() {
		for (String formNavigatorId : _getAllFormNavigatorIds()) {
			String[] formNavigatorCategoryKeys =
				FormNavigatorCategoryUtil.getKeys(formNavigatorId);

			System.out.println(formNavigatorId);

			for (String formNavigatorCategoryKey : formNavigatorCategoryKeys) {
				String line = _getCategoryLine(
					formNavigatorId, formNavigatorCategoryKey);

				System.out.print(StringPool.TAB);
				System.out.print(line);
			}
		}
	}

	public void getPossibleConfigurations(String formNavigatorId) {
		String[] formNavigatorCategoryKeys = FormNavigatorCategoryUtil.getKeys(
			formNavigatorId);

		for (String formNavigatorCategoryKey : formNavigatorCategoryKeys) {
			System.out.print(
				_getCategoryLine(formNavigatorId, formNavigatorCategoryKey));
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_formNavigatorEntries = ServiceTrackerListFactory.open(
			bundleContext, FormNavigatorEntry.class);
		_formNavigatorEntriesMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FormNavigatorEntry.class, null,
			(serviceReference, emitter) -> {
				FormNavigatorEntry formNavigatorEntry =
					bundleContext.getService(serviceReference);

				String key = _getKey(
					formNavigatorEntry.getFormNavigatorId(),
					formNavigatorEntry.getCategoryKey());

				emitter.emit(key);

				bundleContext.ungetService(serviceReference);
			});
	}

	private Set<String> _getAllFormNavigatorIds() {
		Set<String> allFormNavigatorIds = new HashSet<>();

		_formNavigatorEntries.forEach(
			formNavigatorEntry -> allFormNavigatorIds.add(
				formNavigatorEntry.getFormNavigatorId()));

		return allFormNavigatorIds;
	}

	private String _getCategoryLine(
		String formNavigatorId, String formNavigatorCategoryKey) {

		List<FormNavigatorEntry> formNavigatorEntries =
			_formNavigatorEntriesMap.getService(
				_getKey(formNavigatorId, formNavigatorCategoryKey));

		if (formNavigatorEntries == null) {
			return StringPool.BLANK;
		}

		Stream<FormNavigatorEntry> formNavigatorEntriesStream =
			formNavigatorEntries.stream();

		Stream<String> formNavigatorKeysStream = formNavigatorEntriesStream.map(
			FormNavigatorEntry::getKey);

		String formNavigatorEntryKeysCSV = formNavigatorKeysStream.collect(
			_collectorCSV);

		StringBundler sb = new StringBundler(4);

		if (Validator.isNotNull(formNavigatorCategoryKey)) {
			sb.append(formNavigatorCategoryKey);
			sb.append(StringPool.EQUAL);
		}

		sb.append(formNavigatorEntryKeysCSV);
		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private String _getKey(
		String formNavigatorId, String formNavigatorCategoryId) {

		return formNavigatorId + formNavigatorCategoryId;
	}

	private final Collector<CharSequence, ?, String> _collectorCSV =
		Collectors.joining(StringPool.COMMA);
	private ServiceTrackerList<FormNavigatorEntry, FormNavigatorEntry>
		_formNavigatorEntries;
	private ServiceTrackerMap<String, List<FormNavigatorEntry>>
		_formNavigatorEntriesMap;

}