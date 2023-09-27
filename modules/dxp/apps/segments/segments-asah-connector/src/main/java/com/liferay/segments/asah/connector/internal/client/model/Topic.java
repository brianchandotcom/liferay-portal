/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarai Díaz
 */
public class Topic {

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
		return StringBundler.concat(
			"{id=", _id, ", terms=", _terms, ", weight=", _weight, "}");
	}

	public static class TopicTerm {

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