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

package com.liferay.vulcan.sample.internal.model;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Instances of this class represents a user. This is a mock class for sample
 * purposes. It contains methods for retrieving/updating/deleting users and a
 * in-memory database with fake data.
 *
 * @author Alejandro Hernández
 * @review
 */
public class User {

	/**
	 * Adds a new {@code User} to the database.
	 *
	 * @param  firstName the first name of the user.
	 * @param  lastName the last name of the user.
	 * @param  email the email of the user.
	 * @param  address the address of the user.
	 * @param  jobTitle the job title of the user.
	 * @param  birthDate the birth date of the user.
	 * @param  avatar the avatar of the user.
	 * @return the added {@code User}.
	 * @review
	 */
	public static User addUser(
		String firstName, String lastName, String email, String address,
		String jobTitle, Date birthDate, String avatar) {

		long id = _count.incrementAndGet();

		User user = new User(
			id, firstName, lastName, email, address, jobTitle, birthDate,
			avatar);

		_users.put(id, user);

		return user;
	}

	/**
	 * Deletes a {@code User} with a certain {@code ID} from the database.
	 *
	 * @param  id the ID of the user to delete.
	 * @review
	 */
	public static void deleteUser(long id) {
		_users.remove(id);
	}

	/**
	 * Returns a {@code User} with a certain {@code ID} from the database if
	 * present. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  id the ID of the user to retrieve.
	 * @return the {@code User} for the requested ID if present; {@code
	 *         Optional#empty()} otherwise.
	 * @review
	 */
	public static Optional<User> getUser(long id) {
		User user = _users.get(id);

		return Optional.ofNullable(user);
	}

	/**
	 * Returns a page of {@code User} from the database.
	 *
	 * @param  start the start position.
	 * @param  end the end position.
	 * @return the list of users between {@code start} and {@code end}.
	 * @review
	 */
	public static List<User> getUsers(int start, int end) {
		Collection<User> users = _users.values();

		Stream<User> stream = users.stream();

		return stream.skip(
			start
		).limit(
			end
		).collect(
			Collectors.toList()
		);
	}

	/**
	 * Return the total number of users in the database.
	 *
	 * @return the total number of users in the database.
	 * @review
	 */
	public static int getUsersCount() {
		return _users.size();
	}

	/**
	 * Updates a {@code User} with a certain {@code ID} in the database if
	 * present.
	 *
	 * @param  id the ID of the user to update.
	 * @param  firstName the first name of the user.
	 * @param  lastName the last name of the user.
	 * @param  email the email of the user.
	 * @param  address the address of the user.
	 * @param  jobTitle the job title of the user.
	 * @param  birthDate the birth date of the user.
	 * @param  avatar the avatar of the user.
	 * @return the updated {@code User} if present; {@code Optional#empty()}
	 *         otherwise.
	 * @review
	 */
	public static Optional<User> updateUser(
		long id, String firstName, String lastName, String email,
		String address, String jobTitle, Date birthDate, String avatar) {

		User user = _users.get(id);

		if (user == null) {
			return Optional.empty();
		}

		user = new User(
			id, firstName, lastName, email, address, jobTitle, birthDate,
			avatar);

		_users.put(id, user);

		return Optional.of(user);
	}

	/**
	 * The address of this {@code User}.
	 *
	 * @return the address of the user.
	 * @review
	 */
	public String getAddress() {
		return _address;
	}

	/**
	 * Returns the avatar of this {@code User}.
	 *
	 * @return the avatar of the user.
	 * @review
	 */
	public String getAvatar() {
		return _avatar;
	}

	/**
	 * The birth date of this {@code User}.
	 *
	 * @return the birth date of the user.
	 * @review
	 */
	public Date getBirthDate() {
		return _birthDate;
	}

	/**
	 * Returns the email of this {@code User}.
	 *
	 * @return the email of the user.
	 * @review
	 */
	public String getEmail() {
		return _email;
	}

	/**
	 * Returns the first name of this {@code User}.
	 *
	 * @return the first name of the user.
	 * @review
	 */
	public String getFirstName() {
		return _firstName;
	}

	/**
	 * Returns the full name of this {@code User}.
	 *
	 * @return the last name of the user.
	 * @review
	 */
	public String getFullName() {
		return _firstName + " " + _lastName;
	}

	/**
	 * The ID of this {@code User}.
	 *
	 * @return the ID of the user.
	 * @review
	 */
	public long getId() {
		return _id;
	}

	/**
	 * The job title of this {@code User}.
	 *
	 * @return the job title of the user.
	 * @review
	 */
	public String getJobTitle() {
		return _jobTitle;
	}

	/**
	 * Returns the last name of this {@code User}.
	 *
	 * @return the last name of the user.
	 * @review
	 */
	public String getLastName() {
		return _lastName;
	}

	private User(
		long id, String firstName, String lastName, String email,
		String address, String jobTitle, Date birthDate, String avatar) {

		_id = id;
		_firstName = firstName;
		_lastName = lastName;
		_email = email;
		_address = address;
		_jobTitle = jobTitle;
		_birthDate = birthDate;
		_avatar = avatar;
	}

	private static final AtomicLong _count = new AtomicLong(10);
	private static Map<Long, User> _users;

	static {
		_users = new HashMap<>();

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.YEAR, -21);

		Date adultDate = calendar.getTime();

		for (long i = 0; i < 10; i++) {
			Faker faker = new Faker();

			Name name = faker.name();

			Internet internet = faker.internet();

			DateAndTime dateAndTime = faker.date();

			String firstName = name.firstName();
			String lastName = name.lastName();
			String email = internet.safeEmailAddress();
			String address = faker.address().fullAddress();
			String jobTitle = name.title();

			Date birthDate = dateAndTime.past(10000, TimeUnit.DAYS, adultDate);

			String avatar = internet.avatar();

			User user = new User(
				i, firstName, lastName, email, address, jobTitle, birthDate,
				avatar);

			_users.put(i, user);
		}
	}

	private final String _address;
	private final String _avatar;
	private final Date _birthDate;
	private final String _email;
	private final String _firstName;
	private final long _id;
	private final String _jobTitle;
	private final String _lastName;

}