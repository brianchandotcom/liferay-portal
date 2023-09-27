/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(
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
			bundleContext,
			(Class<FormNavigatorEntry<?>>)(Class<?>)FormNavigatorEntry.class);
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext,
			(Class<FormNavigatorEntry<?>>)(Class<?>)FormNavigatorEntry.class,
			null,
			(serviceReference, emitter) -> {
				FormNavigatorEntry<?> formNavigatorEntry =
					bundleContext.getService(serviceReference);

				emitter.emit(
					_getKey(
						formNavigatorEntry.getFormNavigatorId(),
						formNavigatorEntry.getCategoryKey()));

				bundleContext.ungetService(serviceReference);
			});
	}

	@Deactivate
	protected void deactivate() {
		_formNavigatorEntries.close();
		_serviceTrackerMap.close();
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

		List<FormNavigatorEntry<?>> formNavigatorEntries =
			_serviceTrackerMap.getService(
				_getKey(formNavigatorId, formNavigatorCategoryKey));

		if (formNavigatorEntries == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			(formNavigatorEntries.size() * 2) + 2);

		if (Validator.isNotNull(formNavigatorCategoryKey)) {
			sb.append(formNavigatorCategoryKey);
			sb.append(StringPool.EQUAL);
		}

		for (FormNavigatorEntry<?> formNavigatorEntry : formNavigatorEntries) {
			sb.append(formNavigatorEntry.getKey());
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	private String _getKey(
		String formNavigatorId, String formNavigatorCategoryId) {

		return formNavigatorId + formNavigatorCategoryId;
	}

	private ServiceTrackerList<FormNavigatorEntry<?>> _formNavigatorEntries;
	private ServiceTrackerMap<String, List<FormNavigatorEntry<?>>>
		_serviceTrackerMap;

}