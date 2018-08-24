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

package com.liferay.user.associated.data.exporter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.user.associated.data.util.UADDynamicQueryUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author William Newbury
 */
public abstract class DynamicQueryUADExporter<T extends BaseModel>
	implements UADExporter<T> {

	@Override
	public long count(long userId) throws PortalException {
		return getActionableDynamicQuery(userId).performCount();
	}

	@Override
	public byte[] export(T baseModel) throws PortalException {
		String xml = toXmlString(baseModel);

		xml = formatXML(xml);

		try {
			return xml.getBytes(StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
			throw new PortalException(uee);
		}
	}

	@Override
	public File exportAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery(userId);

		Class<T> clazz = getTypeClass();

		ZipWriter zipWriter = getZipWriter(userId, clazz.getName());

		actionableDynamicQuery.setPerformActionMethod(
			(T baseModel) -> {
				try {
					writeToZip(baseModel, zipWriter);
				}
				catch (Exception e) {
					throw new PortalException(e);
				}
			});

		actionableDynamicQuery.performActions();

		return zipWriter.getFile();
	}

	protected File createFolder(long userId) {
		StringBundler sb = new StringBundler(3);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append("/liferay/uad/");
		sb.append(userId);

		File file = new File(sb.toString());

		file.mkdirs();

		return file;
	}

	protected abstract ActionableDynamicQuery doGetActionableDynamicQuery();

	protected abstract String[] doGetUserIdFieldNames();

	protected String formatXML(String xml) {
		return XMLUtil.formatXML(xml);
	}

	protected ActionableDynamicQuery getActionableDynamicQuery(long userId) {
		return UADDynamicQueryUtil.addActionableDynamicQueryCriteria(
			doGetActionableDynamicQuery(), doGetUserIdFieldNames(), userId);
	}

	protected ZipWriter getZipWriter(long userId, String modelClassName) {
		File file = createFolder(userId);

		StringBundler sb = new StringBundler(6);

		sb.append(file.getAbsolutePath());
		sb.append(StringPool.SLASH);
		sb.append(modelClassName);
		sb.append(StringPool.UNDERLINE);
		sb.append(Time.getShortTimestamp());
		sb.append(".zip");

		return ZipWriterFactoryUtil.getZipWriter(new File(sb.toString()));
	}

	protected String toXmlString(T baseModel) {
		return baseModel.toXmlString();
	}

	protected void writeToZip(T baseModel, ZipWriter zipWriter)
		throws Exception {

		byte[] data = export(baseModel);

		zipWriter.addEntry(baseModel.getPrimaryKeyObj() + ".xml", data);
	}

}