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

	public void addDeletionCount(
		Class<? extends ClassedModel> clazz, long count) {

		addDeletionCount(clazz.getName(), count);
	}

	public void addDeletionCount(String modelName, long count) {
		_deletionCounters.put(modelName, count);

		_modelNames.add(modelName);
	}

	public void addModelCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass, long modelCount) {

		addModelCount(clazz.getName(), referrerClass.getName(), modelCount);
	}

	public void addModelCount(
		Class<? extends ClassedModel> clazz, long modelCount) {

		addModelCount(clazz, clazz, modelCount);
	}

	public void addModelCount(String manifestSummaryKey, long modelCount) {
		_modelCounters.put(manifestSummaryKey, modelCount);
	}

	public void addModelCount(
		String className, String referrerClassName, long modelCount) {

		String manifestSummaryKey = getManifestSummaryKey(
			className, referrerClassName);

		addModelCount(manifestSummaryKey, modelCount);
	}

	public void addSetupPortlet(Portlet portlet) {
		_setupPortlets.add(portlet);
	}

	public List<Portlet> getDataPortlets() {
		return _dataPortlets;
	}

	public long getDeletionCount(Class<? extends ClassedModel> clazz) {
		return getDeletionCount(clazz.getName());
	}

	public long getDeletionCount(String modelName) {
		if (!_deletionCounters.containsKey(modelName)) {
			return -1;
		}

		return _deletionCounters.get(modelName);
	}

	public Map<String, Long> getDeletionCounters() {
		return _deletionCounters;
	}

	public Date getExportDate() {
		return _exportDate;
	}

	public long getModelCount(Class<? extends ClassedModel> clazz) {
		return getModelCount(clazz, clazz);
	}

	public long getModelCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass) {

		return getModelCount(clazz.getName(), referrerClass.getName());
	}

	public long getModelCount(String manifestSummaryKey) {
		if (!_modelCounters.containsKey(manifestSummaryKey)) {
			return -1;
		}

		return _modelCounters.get(manifestSummaryKey);
	}

	public long getModelCount(String className, String referrerClassName) {
		String manifestSummaryKey = getManifestSummaryKey(
			className, referrerClassName);

		return getModelCount(manifestSummaryKey);
	}

	public Map<String, Long> getModelCounters() {
		return _modelCounters;
	}

	public Collection<String> getModelNames() {
		return _modelNames;
	}

	public List<Portlet> getSetupPortlets() {
		return _setupPortlets;
	}

	public void incrementDeletionCount(Class<? extends ClassedModel> clazz) {
		incrementDeletionCount(clazz.getName());
	}

	public void incrementDeletionCount(String modelName) {
		if (!_deletionCounters.containsKey(modelName)) {
			_deletionCounters.put(modelName, 1L);

			_modelNames.add(modelName);

			return;
		}

		long deletionCounter = _deletionCounters.get(modelName);

		_deletionCounters.put(modelName, deletionCounter + 1);
	}

	public void incrementModelCount(Class<? extends ClassedModel> clazz) {
		incrementModelCount(clazz, clazz);
	}

	public void incrementModelCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass) {

		String manifestSummaryKey = getManifestSummaryKey(
			clazz.getName(), referrerClass.getName());

		incrementModelCount(manifestSummaryKey);
	}

	public void incrementModelCount(String manifestSummaryKey) {
		if (!_modelCounters.containsKey(manifestSummaryKey)) {
			_modelCounters.put(manifestSummaryKey, 1L);

			_modelNames.add(manifestSummaryKey);

			return;
		}

		long modelCounter = _modelCounters.get(manifestSummaryKey);

		_modelCounters.put(manifestSummaryKey, modelCounter + 1);
	}

	public void setExportDate(Date exportDate) {
		_exportDate = exportDate;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(3);

		sb.append("{modelCounters=");
		sb.append(MapUtil.toString(_modelCounters));
		sb.append(",{deletionCounters=");
		sb.append(MapUtil.toString(_deletionCounters));
		sb.append("}");

		return sb.toString();
	}

	private List<Portlet> _dataPortlets = new ArrayList<Portlet>();
	private Map<String, Long> _deletionCounters = new HashMap<String, Long>();
	private Date _exportDate;
	private Map<String, Long> _modelCounters = new HashMap<String, Long>();
	private Set<String> _modelNames = new HashSet<String>();
	private List<Portlet> _setupPortlets = new ArrayList<Portlet>();

}