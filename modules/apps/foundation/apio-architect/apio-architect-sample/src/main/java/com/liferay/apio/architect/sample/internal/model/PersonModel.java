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

package com.liferay.apio.architect.sample.internal.model;

import static java.util.Calendar.YEAR;
import static java.util.concurrent.TimeUnit.DAYS;

import com.github.javafaker.Address;
import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import com.github.javafaker.service.RandomService;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Represents a person. This is a mock class for sample purposes only. It
 * contains methods for creating, retrieving, updating, and deleting persons in
 * an in-memory database with fake data.
 *
 * @author Alejandro Hernández
 */
public class PersonModel {

	/**
	 * Computes the fake data for this model class.
	 */
	public static void compute() {
		if (!_personModels.isEmpty()) {
			return;
		}

		for (long index = 0; index < 10; index++) {
			Faker faker = new Faker();

			Address address = faker.address();

			Internet internet = faker.internet();

			DateAndTime dateAndTime = faker.date();

			Calendar calendar = Calendar.getInstance();

			calendar.add(YEAR, -21);

			Date birthDate = dateAndTime.past(10000, DAYS, calendar.getTime());

			Name name = faker.name();

			RandomService randomService = faker.random();

			IntStream intStream = IntStream.range(0, randomService.nextInt(5));

			List<String> jobTitles = intStream.mapToObj(
				__ -> name.title()
			).collect(
				Collectors.toList()
			);

			PersonModel personModel = new PersonModel(
				address.fullAddress(), internet.avatar(), birthDate,
				internet.safeEmailAddress(), name.firstName(), jobTitles,
				name.lastName(), _count.get());

			_personModels.put(_count.getAndIncrement(), personModel);
		}
	}

	/**
	 * Adds a new person.
	 *
	 * @param  address the person's address
	 * @param  avatar the person's avatar
	 * @param  birthDate the person's birth date
	 * @param  email the person's email
	 * @param  firstName the person's first name
	 * @param  jobTitles the person's job titles
	 * @param  lastName the person's last name
	 * @return the new person
	 */
	public static PersonModel create(
		String address, String avatar, Date birthDate, String email,
		String firstName, List<String> jobTitles, String lastName) {

		PersonModel personModel = new PersonModel(
			address, avatar, birthDate, email, firstName, jobTitles, lastName,
			_count.get());

		_personModels.put(_count.getAndIncrement(), personModel);

		return personModel;
	}

	/**
	 * Returns the person that matches the specified ID, if that person exists;
	 * returns {@code Optional#empty()} otherwise.
	 *
	 * @param  id the person's ID
	 * @return the person, if present; {@code Optional#empty()} otherwise
	 */
	public static Optional<PersonModel> get(long id) {
		return Optional.ofNullable(_personModels.get(id));
	}

	/**
	 * Returns the total number of persons.
	 *
	 * @return the total number of persons
	 */
	public static int getCount() {
		return _personModels.size();
	}

	/**
	 * Returns a page of persons, as specified by the page's start and end
	 * positions.
	 *
	 * @param  start the page's start position
	 * @param  end the page's end position
	 * @return the page of persons
	 */
	public static List<PersonModel> getPage(int start, int end) {
		Collection<PersonModel> personModels = _personModels.values();

		Stream<PersonModel> stream = personModels.stream();

		return stream.skip(
			start
		).limit(
			end
		).collect(
			Collectors.toList()
		);
	}

	/**
	 * Deletes a person that matches the specified ID.
	 *
	 * @param id the person's ID
	 */
	public static void remove(long id) {
		_personModels.remove(id);
	}

	/**
	 * Updates the person that matches the specified ID, if that person exists;
	 * returns {@code Optional#empty()} otherwise.
	 *
	 * @param  address the person's address
	 * @param  avatar the person's avatar
	 * @param  birthDate the person's birth date
	 * @param  email the person's email
	 * @param  firstName the person's first name
	 * @param  jobTitles the person's job titles
	 * @param  lastName the person's last name
	 * @param  id the person's ID
	 * @return the updated person, if present; {@code Optional#empty()}
	 *         otherwise
	 */
	public static Optional<PersonModel> update(
		String address, String avatar, Date birthDate, String email,
		String firstName, List<String> jobTitles, String lastName, long id) {

		PersonModel personModel = _personModels.get(id);

		if (personModel == null) {
			return Optional.empty();
		}

		personModel = new PersonModel(
			avatar, address, birthDate, email, firstName, jobTitles, lastName,
			id);

		_personModels.put(id, personModel);

		return Optional.of(personModel);
	}

	/**
	 * Returns the person's address.
	 *
	 * @return the person's address
	 */
	public String getAddress() {
		return _address;
	}

	/**
	 * Returns the person's avatar.
	 *
	 * @return the person's avatar
	 */
	public String getAvatar() {
		return _avatar;
	}

	/**
	 * Returns the person's birth date.
	 *
	 * @return the person's birth date
	 */
	public Date getBirthDate() {
		return new Date(_birthDate.getTime());
	}

	/**
	 * Returns the person's email.
	 *
	 * @return the person's email
	 */
	public String getEmail() {
		return _email;
	}

	/**
	 * Returns the person's first name.
	 *
	 * @return the person's first name
	 */
	public String getFirstName() {
		return _firstName;
	}

	/**
	 * Returns the person's full name.
	 *
	 * @return the person's full name
	 */
	public String getFullName() {
		return _firstName + " " + _lastName;
	}

	/**
	 * Returns the person's ID.
	 *
	 * @return the person's ID
	 */
	public long getId() {
		return _id;
	}

	/**
	 * Returns the person's job titles.
	 *
	 * @return the person's job titles
	 */
	public List<String> getJobTitles() {
		return _jobTitles;
	}

	/**
	 * Returns the person's last name.
	 *
	 * @return the person's last name
	 */
	public String getLastName() {
		return _lastName;
	}

	private PersonModel(
		String address, String avatar, Date birthDate, String email,
		String firstName, List<String> jobTitles, String lastName, long id) {

		_address = address;
		_avatar = avatar;
		_birthDate = birthDate;
		_email = email;
		_firstName = firstName;
		_jobTitles = jobTitles;
		_lastName = lastName;
		_id = id;
	}

	private static final AtomicLong _count = new AtomicLong(0);
	private static final Map<Long, PersonModel> _personModels =
		new ConcurrentHashMap<>();

	private final String _address;
	private final String _avatar;
	private final Date _birthDate;
	private final String _email;
	private final String _firstName;
	private final long _id;
	private final List<String> _jobTitles;
	private final String _lastName;

}