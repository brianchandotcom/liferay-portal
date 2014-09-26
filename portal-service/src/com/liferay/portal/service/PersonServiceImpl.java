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

package com.liferay.portal.service;

import com.google.common.base.Optional;
import com.liferay.portal.model.Person;
import com.liferay.portal.model.PersonImpl;
import com.liferay.portal.model.User;

/**
 * @author Carlos Sierra Andrés
 */
public class PersonServiceImpl implements PersonService {

	@Override
	public Optional<Person> getPerson(long personId) {
		User user = UserLocalServiceUtil.fetchUser(personId);

		if (user == null) {
			return Optional.absent();
		}

		else {
			return Optional.of((Person)new PersonImpl(user));
		}
	}


}
