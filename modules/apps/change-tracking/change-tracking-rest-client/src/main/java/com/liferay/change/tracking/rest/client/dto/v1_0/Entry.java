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

package com.liferay.change.tracking.rest.client.dto.v1_0;

import com.liferay.change.tracking.rest.client.function.UnsafeSupplier;
import com.liferay.change.tracking.rest.client.serdes.v1_0.EntrySerDes;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class Entry {

	public Integer getAffectedByEntriesCount() {
		return affectedByEntriesCount;
	}

	public void setAffectedByEntriesCount(Integer affectedByEntriesCount) {
		this.affectedByEntriesCount = affectedByEntriesCount;
	}

	public void setAffectedByEntriesCount(
		UnsafeSupplier<Integer, Exception>
			affectedByEntriesCountUnsafeSupplier) {

		try {
			affectedByEntriesCount = affectedByEntriesCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer affectedByEntriesCount;

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public void setChangeType(
		UnsafeSupplier<Integer, Exception> changeTypeUnsafeSupplier) {

		try {
			changeType = changeTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer changeType;

	public Long getClassNameId() {
		return classNameId;
	}

	public void setClassNameId(Long classNameId) {
		this.classNameId = classNameId;
	}

	public void setClassNameId(
		UnsafeSupplier<Long, Exception> classNameIdUnsafeSupplier) {

		try {
			classNameId = classNameIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long classNameId;

	public Long getClassPK() {
		return classPK;
	}

	public void setClassPK(Long classPK) {
		this.classPK = classPK;
	}

	public void setClassPK(
		UnsafeSupplier<Long, Exception> classPKUnsafeSupplier) {

		try {
			classPK = classPKUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long classPK;

	public Boolean getCollision() {
		return collision;
	}

	public void setCollision(Boolean collision) {
		this.collision = collision;
	}

	public void setCollision(
		UnsafeSupplier<Boolean, Exception> collisionUnsafeSupplier) {

		try {
			collision = collisionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean collision;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContentType(
		UnsafeSupplier<String, Exception> contentTypeUnsafeSupplier) {

		try {
			contentType = contentTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String contentType;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public Long getEntryId() {
		return entryId;
	}

	public void setEntryId(Long entryId) {
		this.entryId = entryId;
	}

	public void setEntryId(
		UnsafeSupplier<Long, Exception> entryIdUnsafeSupplier) {

		try {
			entryId = entryIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long entryId;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<Long, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long key;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public void setSiteName(
		UnsafeSupplier<String, Exception> siteNameUnsafeSupplier) {

		try {
			siteName = siteNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String siteName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(
		UnsafeSupplier<String, Exception> titleUnsafeSupplier) {

		try {
			title = titleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String title;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserName(
		UnsafeSupplier<String, Exception> userNameUnsafeSupplier) {

		try {
			userName = userNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String userName;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setVersion(
		UnsafeSupplier<String, Exception> versionUnsafeSupplier) {

		try {
			version = versionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String version;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Entry)) {
			return false;
		}

		Entry entry = (Entry)object;

		return Objects.equals(toString(), entry.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return EntrySerDes.toJSON(this);
	}

}