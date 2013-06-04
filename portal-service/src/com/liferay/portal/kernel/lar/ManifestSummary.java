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
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Portlet;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class ManifestSummary implements Serializable {

	public static String getManifestSummaryKey(
		String modelName, String referrerModelName) {

		if (modelName.equals(referrerModelName)) {
			return modelName;
		}

		return referrerModelName.concat(StringPool.POUND).concat(modelName);
	}

	public void addDataPortlet(Portlet portlet) {
		_dataPortlets.add(portlet);
	}

	public void addModelCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass, long modelCount) {

		String manifestSummaryKey = getManifestSummaryKey(
			clazz.getName(), referrerClass.getName());

		addModelCount(manifestSummaryKey, modelCount);
	}

	public void addModelCount(
		Class<? extends ClassedModel> clazz, long modelCount) {

		addModelCount(clazz, clazz, modelCount);
	}

	public void addModelCount(String manifestSummaryKey, long modelCount) {
		_modelCounters.put(manifestSummaryKey, modelCount);
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

	public long getModelCount(Class<? extends ClassedModel> clazz) {
		return getModelCount(clazz, clazz);
	}

	public long getModelCount(
		Class<? extends ClassedModel> clazz,
		Class<? extends ClassedModel> referrerClass) {

		String manifestSummaryKey = getManifestSummaryKey(
			clazz.getName(), referrerClass.getName());

		return getModelCount(manifestSummaryKey);
	}

	public long getModelCount(String manifestSummaryKey) {
		if (!_modelCounters.containsKey(manifestSummaryKey)) {
			return -1;
		}

		return _modelCounters.get(manifestSummaryKey);
	}

	public Map<String, Long> getModelCounters() {
		return _modelCounters;
	}

	public List<Portlet> getSetupPortlets() {
		return _setupPortlets;
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

		sb.append("{_modelCounters=");
		sb.append(MapUtil.toString(_modelCounters));
		sb.append("}");

		return sb.toString();
	}

	private List<Portlet> _dataPortlets = new ArrayList<Portlet>();
	private Date _exportDate;
	private Map<String, Long> _modelCounters = new HashMap<String, Long>();
	private List<Portlet> _setupPortlets = new ArrayList<Portlet>();

}