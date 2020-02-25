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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.CreatorStatisticsSerDes;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CreatorStatistics implements Cloneable {

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public void setJoinDate(
		UnsafeSupplier<Date, Exception> joinDateUnsafeSupplier) {

		try {
			joinDate = joinDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date joinDate;

	public Date getLastPostDate() {
		return lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		this.lastPostDate = lastPostDate;
	}

	public void setLastPostDate(
		UnsafeSupplier<Date, Exception> lastPostDateUnsafeSupplier) {

		try {
			lastPostDate = lastPostDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date lastPostDate;

	public Integer getPostsNumber() {
		return postsNumber;
	}

	public void setPostsNumber(Integer postsNumber) {
		this.postsNumber = postsNumber;
	}

	public void setPostsNumber(
		UnsafeSupplier<Integer, Exception> postsNumberUnsafeSupplier) {

		try {
			postsNumber = postsNumberUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer postsNumber;

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public void setRank(UnsafeSupplier<String, Exception> rankUnsafeSupplier) {
		try {
			rank = rankUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String rank;

	@Override
	public CreatorStatistics clone() throws CloneNotSupportedException {
		return (CreatorStatistics)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreatorStatistics)) {
			return false;
		}

		CreatorStatistics creatorStatistics = (CreatorStatistics)object;

		return Objects.equals(toString(), creatorStatistics.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CreatorStatisticsSerDes.toJSON(this);
	}

}