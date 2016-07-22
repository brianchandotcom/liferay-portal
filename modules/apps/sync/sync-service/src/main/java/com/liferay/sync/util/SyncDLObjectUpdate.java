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

package com.liferay.sync.util;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sync.model.SyncDLObject;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Michael Young
 * @author Shinn Lok
 */
@JSON
public class SyncDLObjectUpdate {

	public SyncDLObjectUpdate(
		List<SyncDLObject> syncDLObjects, int resultsTotal,
		long lastAccessTime) {

		_syncDLObjects = syncDLObjects;
		_resultsTotal = resultsTotal;
		_lastAccessTime = lastAccessTime;
		_settingsModifiedTimes = Collections.emptyMap();
	}

	public SyncDLObjectUpdate(
		List<SyncDLObject> syncDLObjects, int resultsTotal, long lastAccessTime,
		Map<String, Long> settingsModifiedTimes) {

		_syncDLObjects = syncDLObjects;
		_resultsTotal = resultsTotal;
		_lastAccessTime = lastAccessTime;
		_settingsModifiedTimes = settingsModifiedTimes;
	}

	public long getLastAccessTime() {
		return _lastAccessTime;
	}

	public int getResultsTotal() {
		return _resultsTotal;
	}

	public Map<String, Long> getSettingsModifiedTimes() {
		return _settingsModifiedTimes;
	}

	@JSON
	public List<SyncDLObject> getSyncDLObjects() {
		return _syncDLObjects;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(
			(_settingsModifiedTimes.size() * 4) + (_syncDLObjects.size() * 78) +
				5);

		sb.append("{\"lastAccessTime\":");
		append(sb, _lastAccessTime, false);
		sb.append(",\"resultsTotal\":");
		sb.append(_resultsTotal);
		sb.append(",\"settingsModifiedTimes\":{");

		Set<Map.Entry<String, Long>> entries =
			_settingsModifiedTimes.entrySet();

		Iterator<Map.Entry<String, Long>> iterator = entries.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Long> entry = iterator.next();

			String key = entry.getKey();
			long value = entry.getValue();

			append(sb, key, false);
			sb.append(StringPool.COLON);
			append(sb, value, false);

			if (iterator.hasNext()) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append("},\"syncDLObjects\":[");

		for (int i = 0; i < _syncDLObjects.size(); i++) {
			SyncDLObject syncDLObject = _syncDLObjects.get(i);

			sb.append("{\"changeLog\":");
			append(sb, syncDLObject.getChangeLog(), true);
			sb.append(",\"checksum\":");
			append(sb, syncDLObject.getChecksum(), false);
			sb.append(",\"companyId\":");
			append(sb, syncDLObject.getCompanyId(), false);
			sb.append(",\"createTime\":");
			append(sb, syncDLObject.getCreateTime(), false);
			sb.append(",\"description\":");
			append(sb, syncDLObject.getDescription(), true);
			sb.append(",\"event\":");
			append(sb, syncDLObject.getEvent(), false);
			sb.append(",\"extension\":");
			append(sb, syncDLObject.getExtension(), true);
			sb.append(",\"extraSettings\":");
			append(sb, syncDLObject.getExtraSettings(), true);
			sb.append(",\"lockExpirationDate\":");

			Date lockExpirationDate = syncDLObject.getLockExpirationDate();

			if (lockExpirationDate != null) {
				sb.append(lockExpirationDate.getTime());
			}
			else {
				sb.append(StringPool.NULL);
			}

			sb.append(",\"lockUserId\":");
			append(sb, syncDLObject.getLockUserId(), false);
			sb.append(",\"lockUserName\":");
			append(sb, syncDLObject.getLockUserName(), true);
			sb.append(",\"mimeType\":");
			append(sb, syncDLObject.getMimeType(), true);
			sb.append(",\"modifiedTime\":");
			append(sb, syncDLObject.getModifiedTime(), false);
			sb.append(",\"name\":");
			append(sb, syncDLObject.getName(), true);
			sb.append(",\"parentFolderId\":");
			append(sb, syncDLObject.getParentFolderId(), false);
			sb.append(",\"repositoryId\":");
			append(sb, syncDLObject.getRepositoryId(), false);
			sb.append(",\"size\":");
			append(sb, syncDLObject.getSize(), false);
			sb.append(",\"syncDLObjectId\":");
			append(sb, syncDLObject.getSyncDLObjectId(), false);
			sb.append(",\"type\":");
			append(sb, syncDLObject.getType(), false);
			sb.append(",\"typePK\":");
			append(sb, syncDLObject.getTypePK(), false);
			sb.append(",\"typeUuid\":");
			append(sb, syncDLObject.getTypeUuid(), false);
			sb.append(",\"userId\":");
			append(sb, syncDLObject.getUserId(), false);
			sb.append(",\"userName\":");
			append(sb, syncDLObject.getUserName(), true);
			sb.append(",\"version\":");
			append(sb, syncDLObject.getVersion(), false);
			sb.append(",\"versionId\":");
			append(sb, syncDLObject.getVersionId(), false);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			if (i != (_syncDLObjects.size() - 1)) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append("]}");

		return sb.toString();
	}

	protected void append(StringBundler sb, Object s, boolean escape) {
		sb.append(StringPool.QUOTE);

		if (escape) {
			s = StringEscapeUtils.escapeJava(String.valueOf(s));
		}

		sb.append(s);

		sb.append(StringPool.QUOTE);
	}

	private final long _lastAccessTime;
	private final int _resultsTotal;
	private final Map<String, Long> _settingsModifiedTimes;
	private final List<SyncDLObject> _syncDLObjects;

}