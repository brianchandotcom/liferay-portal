/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.portlet.shared.search;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;

/**
 * @author André de Oliveira
 */
public class CriteriaBuilderImpl implements CriteriaBuilder {

	public Criteria build() {
		if (Validator.isBlank(_criteriaImpl._uid)) {
			return null;
		}

		return new CriteriaImpl(_criteriaImpl);
	}

	@Override
	public CriteriaBuilder type(String className) {
		_criteriaImpl._className = className;

		return this;
	}

	@Override
	public CriteriaBuilder uid(String uid) {
		_criteriaImpl._uid = uid;

		return this;
	}

	public static class CriteriaImpl implements Criteria {

		public CriteriaImpl() {
		}

		public CriteriaImpl(CriteriaImpl criteriaImpl) {
			_className = criteriaImpl._className;
			_uid = criteriaImpl._uid;
		}

		@Override
		public String getType() {
			return _className;
		}

		@Override
		public String getUID() {
			return _uid;
		}

		private String _className;
		private String _uid;

	}

	private final CriteriaImpl _criteriaImpl = new CriteriaImpl();

}