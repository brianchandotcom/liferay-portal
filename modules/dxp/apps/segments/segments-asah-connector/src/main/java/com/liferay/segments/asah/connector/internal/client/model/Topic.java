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

package com.liferay.segments.asah.connector.internal.client.model;

import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarai Díaz
 */
public class Topic {

	public Topic() {
	}

	public int getId() {
		return _id;
	}

	public List<TopicTerm> getTerms() {
		return _terms;
	}

	public double getWeight() {
		return _weight;
	}

	public void setId(int id) {
		_id = id;
	}

	public void setTerms(List<TopicTerm> terms) {
		_terms = terms;
	}

	public void setWeight(double weight) {
		_weight = weight;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{id=");
		sb.append(_id);
		sb.append(", terms=");
		sb.append(_terms);
		sb.append(", weight=");
		sb.append(_weight);
		sb.append("}");

		return sb.toString();
	}

	public static class TopicTerm {

		public TopicTerm() {
		}

		public String getKeyword() {
			return _keyword;
		}

		public double getWeight() {
			return _weight;
		}

		public void setKeyword(String keyword) {
			_keyword = keyword;
		}

		public void setWeight(double weight) {
			_weight = weight;
		}

		private String _keyword;
		private double _weight;

	}

	private int _id;
	private List<TopicTerm> _terms = new ArrayList<>();
	private double _weight;

}