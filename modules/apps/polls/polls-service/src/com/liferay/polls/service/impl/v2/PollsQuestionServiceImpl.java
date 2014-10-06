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

package com.liferay.polls.service.impl.v2;

import com.google.common.base.Optional;
import com.liferay.counter.service.CounterLocalService;
import com.liferay.kernel.persistence.PersistenceRepository;
import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.model.v2.PollsQuestion;
import com.liferay.polls.service.persistence.PollsQuestionPersistence;
import com.liferay.polls.service.v2.PollsQuestionService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Carlos Sierra Andrés
 */
public class PollsQuestionServiceImpl
	implements PersistenceRepository<PollsQuestion> {

	@Override
	public PollsQuestion create() {
		return null;
	}

	@Override
	public Optional<PollsQuestion> findById(long key) {
		return null;
	}

	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = PollsQuestionService.class)
	PollsQuestionService pollsQuestionService;
	@BeanReference(type = PollsQuestionPersistence.class)
	protected PollsQuestionPersistence pollsQuestionPersistence;

}
