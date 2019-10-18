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

package com.liferay.portal.search.similar.results.web.internal.portlet.search;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;

import java.util.Optional;

/**
 * @author André de Oliveira
 */
public class CriteriaBuilderImpl implements CriteriaBuilder {

	public Optional<Criteria> build() {
		if (Validator.isBlank(_criteriaImpl._uid)) {
			return Optional.empty();
		}

		return Optional.of(new CriteriaImpl(_criteriaImpl));
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
		public Optional<String> getTypeOptional() {
			return Optional.ofNullable(_className);
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