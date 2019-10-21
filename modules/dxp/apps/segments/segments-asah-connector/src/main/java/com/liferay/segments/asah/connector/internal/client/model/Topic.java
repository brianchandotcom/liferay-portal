/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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