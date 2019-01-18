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

package com.liferay.headless.person.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.ActionRouter;
import com.liferay.headless.person.dto.Person;
import com.liferay.headless.person.resource.PersonResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 * @author Cristina González
 */
@Component(immediate = true, service = ActionRouter.class)
public class PersonResourceImpl implements PersonResource {

	@Override
	public Person createPerson(Person person) throws PortalException {
		return null;
	}

	@Override
	public void deletePerson(long personId) throws PortalException {
	}

	@Override
	public PageItems<Person> getPageItems(
		Pagination pagination, Company company) {

		return new PageItems<>(Collections.emptyList(), 0);
	}

	@Override
	public Person getPerson(long personId) throws PortalException {
		return null;
	}

	@Override
	public Person replacePerson(long personId, Person person)
		throws PortalException {

		return null;
	}

}