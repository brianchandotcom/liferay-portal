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

package com.liferay.portal.convert.documentlibrary;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.comparator.FileVersionVersionComparator;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.convert.BaseConvertProcess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Minhchau Dang
 * @author Alexander Chow
 * @author László Csontos
 */
public class DocumentLibraryConvertProcess extends BaseConvertProcess {

	public void afterPropertiesSet() {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			SystemBundleUtil.getBundleContext(), Store.class, "(ct.aware=true)",
			(serviceReference1, emitter) -> emitter.emit(
				String.valueOf(serviceReference1.getProperty("store.type"))));
	}

	public void destroy() {
		_serviceTrackerMap.close();
	}

	@Override
	public String getConfigurationErrorMessage() {
		return "there-are-no-stores-configured";
	}

	@Override
	public String getDescription() {
		return "migrate-documents-from-one-repository-to-another";
	}

	@Override
	public String getParameterDescription() {
		return "please-select-a-new-repository-hook";
	}

	@Override
	public String[] getParameterNames() {
		Store store = StoreFactory.getStore();

		Set<String> storeTypes = _serviceTrackerMap.keySet();

		StringBundler sb = new StringBundler((storeTypes.size() * 2) + 2);

		sb.append(PropsKeys.DL_STORE_IMPL);
		sb.append(StringPool.EQUAL);

		for (String storeType : storeTypes) {
			Class<?> clazz = store.getClass();

			if (!storeType.equals(clazz.getName())) {
				sb.append(storeType);
				sb.append(StringPool.SEMICOLON);
			}
		}

		return new String[] {
			sb.toString(), "delete-files-from-previous-repository=checkbox"
		};
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void validate() {
	}

	@Override
	protected void doConvert() throws Exception {
		String targetStoreClassName = getTargetStoreClassName();

		migrateDLStoreConvertProcesses(
			StoreFactory.getStore(),
			_serviceTrackerMap.getService(targetStoreClassName));

		MaintenanceUtil.appendStatus(
			StringBundler.concat(
				"Please set ", PropsKeys.DL_STORE_IMPL,
				" in your portal-ext.properties to use ",
				targetStoreClassName));

		PropsValues.DL_STORE_IMPL = targetStoreClassName;
	}

	protected List<FileVersion> getFileVersions(FileEntry fileEntry) {
		return ListUtil.sort(
			fileEntry.getFileVersions(WorkflowConstants.STATUS_ANY),
			new FileVersionVersionComparator(true));
	}

	protected String getTargetStoreClassName() {
		String[] values = getParameterValues();

		return values[0];
	}

	protected boolean isDeleteFilesFromSourceStore() {
		String[] values = getParameterValues();

		return GetterUtil.getBoolean(values[1]);
	}

	protected void migrateDLStoreConvertProcesses(
			Store sourceStore, Store targetStore)
		throws PortalException {

		Collection<DLStoreConvertProcess> dlStoreConvertProcesses =
			_getDLStoreConvertProcesses();

		for (DLStoreConvertProcess dlStoreConvertProcess :
				dlStoreConvertProcesses) {

			if (isDeleteFilesFromSourceStore()) {
				dlStoreConvertProcess.move(sourceStore, targetStore);
			}
			else {
				dlStoreConvertProcess.copy(sourceStore, targetStore);
			}
		}
	}

	private Collection<DLStoreConvertProcess> _getDLStoreConvertProcesses() {
		return _dlStoreConvertProcesses.toList();
	}

	private static final ServiceTrackerList<DLStoreConvertProcess>
		_dlStoreConvertProcesses = ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(), DLStoreConvertProcess.class);

	private ServiceTrackerMap<String, Store> _serviceTrackerMap;

}