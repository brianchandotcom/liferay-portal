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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mate Thurzo
 * @author Zsolt Berentey
 */
public class ManifestSummary implements Serializable {

	public static String getManifestSummaryKey(
		String modelName, String referrerModelName) {

		if (Validator.isNull(referrerModelName) ||
			modelName.equals(referrerModelName)) {

			return modelName;
		}

		return referrerModelName.concat(StringPool.POUND).concat(modelName);
	}

	public void addDataPortlet(Portlet portlet) {
		_dataPortlets.add(portlet);
	}

	public void addModelAdditionCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass, long count) {

		addModelAdditionCount(clazz.getName(), referrerClass.getName(), count);
	}

	public void addModelAdditionCount(
		Class<? extends ClassedModel> clazz, long modelCount) {

		addModelAdditionCount(clazz, clazz, modelCount);
	}

	public void addModelAdditionCount(String manifestSummaryKey, long count) {
		LongWrapper modelAdditionCounter = _modelAdditionCounters.get(
			manifestSummaryKey);

		if (modelAdditionCounter == null) {
			modelAdditionCounter = new LongWrapper();

			_modelAdditionCounters.put(
				manifestSummaryKey, modelAdditionCounter);
		}

		modelAdditionCounter.setValue(count);

		_manifestSummaryKeys.add(manifestSummaryKey);
	}

	public void addModelAdditionCount(
		String className, String referrerClassName, long count) {

		String manifestSummaryKey = getManifestSummaryKey(
			className, referrerClassName);

		addModelAdditionCount(manifestSummaryKey, count);
	}

	public void addModelDeletionCount(
		Class<? extends ClassedModel> clazz, long count) {

		addModelDeletionCount(clazz.getName(), count);
	}

	public void addModelDeletionCount(String manifestSummaryKey, long count) {
		LongWrapper modelDeletionCounter = _modelDeletionCounters.get(
			manifestSummaryKey);

		if (modelDeletionCounter == null) {
			modelDeletionCounter = new LongWrapper();

			_modelDeletionCounters.put(
				manifestSummaryKey, modelDeletionCounter);
		}

		modelDeletionCounter.setValue(count);

		_manifestSummaryKeys.add(manifestSummaryKey);
	}

	public void addSetupPortlet(Portlet portlet) {
		_setupPortlets.add(portlet);
	}

	public List<Portlet> getDataPortlets() {
		return _dataPortlets;
	}

	public Date getExportDate() {
		return _exportDate;
	}

	public Collection<String> getManifestSummaryKeys() {
		return _manifestSummaryKeys;
	}

	public long getModelAdditionCount(Class<? extends ClassedModel> clazz) {
		return getModelAdditionCount(clazz, clazz);
	}

	public long getModelAdditionCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass) {

		return getModelAdditionCount(clazz.getName(), referrerClass.getName());
	}

	public long getModelAdditionCount(String manifestSummaryKey) {
		if (!_modelAdditionCounters.containsKey(manifestSummaryKey)) {
			return -1;
		}

		LongWrapper modelAdditionCounter = _modelAdditionCounters.get(
			manifestSummaryKey);

		return modelAdditionCounter.getValue();
	}

	public long getModelAdditionCount(
		String className, String referrerClassName) {

		String manifestSummaryKey = getManifestSummaryKey(
			className, referrerClassName);

		return getModelAdditionCount(manifestSummaryKey);
	}

	public Map<String, LongWrapper> getModelAdditionCounters() {
		return _modelAdditionCounters;
	}

	public long getModelDeletionCount(Class<? extends ClassedModel> clazz) {
		return getModelDeletionCount(clazz.getName());
	}

	public long getModelDeletionCount(String modelName) {
		if (!_modelDeletionCounters.containsKey(modelName)) {
			return -1;
		}

		LongWrapper modelDeletionCounter = _modelDeletionCounters.get(
			modelName);

		return modelDeletionCounter.getValue();
	}

	public Map<String, LongWrapper> getModelDeletionCounters() {
		return _modelDeletionCounters;
	}

	public List<Portlet> getSetupPortlets() {
		return _setupPortlets;
	}

	public void incrementModelAdditionCount(
		Class<? extends ClassedModel> clazz) {

		incrementModelAdditionCount(clazz, clazz);
	}

	public void incrementModelAdditionCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass) {

		String manifestSummaryKey = getManifestSummaryKey(
			clazz.getName(), referrerClass.getName());

		incrementModelAdditionCount(manifestSummaryKey);
	}

	public void incrementModelAdditionCount(String manifestSummaryKey) {
		if (!_modelAdditionCounters.containsKey(manifestSummaryKey)) {
			_modelAdditionCounters.put(manifestSummaryKey, new LongWrapper(1));

			_manifestSummaryKeys.add(manifestSummaryKey);

			return;
		}

		LongWrapper modelAdditionCounter = _modelAdditionCounters.get(
			manifestSummaryKey);

		modelAdditionCounter.increment();
	}

	public void incrementModelDeletionCount(
		Class<? extends ClassedModel> clazz) {

		incrementModelDeletionCount(clazz.getName());
	}

	public void incrementModelDeletionCount(String manifestSummaryKey) {
		if (!_modelDeletionCounters.containsKey(manifestSummaryKey)) {
			_modelDeletionCounters.put(manifestSummaryKey, new LongWrapper(1));

			_manifestSummaryKeys.add(manifestSummaryKey);

			return;
		}

		LongWrapper modelDeletionCounter = _modelDeletionCounters.get(
			manifestSummaryKey);

		modelDeletionCounter.increment();
	}

	public void setExportDate(Date exportDate) {
		_exportDate = exportDate;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{modelAdditionCounters=");
		sb.append(MapUtil.toString(_modelAdditionCounters));
		sb.append(", modelDeletionCounters=");
		sb.append(MapUtil.toString(_modelDeletionCounters));
		sb.append("}");

		return sb.toString();
	}

	private List<Portlet> _dataPortlets = new ArrayList<Portlet>();
	private Date _exportDate;
	private Set<String> _manifestSummaryKeys = new HashSet<String>();
	private Map<String, LongWrapper> _modelAdditionCounters =
		new HashMap<String, LongWrapper>();
	private Map<String, LongWrapper> _modelDeletionCounters =
		new HashMap<String, LongWrapper>();
	private List<Portlet> _setupPortlets = new ArrayList<Portlet>();

}