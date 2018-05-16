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

package com.liferay.portal.apio.permission;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.functional.Try;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.function.BiFunction;

/**
 * Instances of this class contains the information necessary to calculate
 * permissions for the models exposed in Liferay Portal APIO APIs.
 *
 * <p>
 * The different methods that this interface exposes are intended to be used
 * only in the {@code Routes.Builder} methods that need to check permissions.
 * And its signature applies exactly so they can be used as either method
 * references, or in a "semantic" form ({@code
 * _hasPermission.forDeleting(BlogsEntry.class)}).
 * </p>
 *
 * @author Alejandro Hernández
 * @author Javier Gamarra
 * @review
 */
public interface HasPermission {

	/**
	 * Returns a {@code BiFunction} that can be used in an {@code
	 * Routes.Builder#addCreator} method to check if the current {@code User}
	 * has permission to perform that action.
	 *
	 * <p>
	 * This method is intended to be used for providing a function to check
	 * permissions in {@code Routes.Builder#addCreator} methods.
	 * </p>
	 *
	 * @param  clazz the class of entries being added
	 * @return the {@code BiFunction} used to check the permissions
	 * @review
	 */
	public BiFunction<Credentials, Long, Boolean> forAddingEntries(
		Class<?> clazz);

	/**
	 * Returns {@code true} if the current {@code User} can add a root {@code
	 * JournalArticle}.
	 *
	 * <p>
	 * This method is intended to be used for providing a function to check
	 * permissions in {@code Routes.Builder#addCreator} methods.
	 * </p>
	 *
	 * @param  credentials the current request credentials
	 * @param  groupId the group ID
	 * @return {@code true} if the current user can add a root {@code
	 *         JournalArticle}; {@code false} otherwise
	 * @review
	 */
	public Boolean forAddingRootJournalArticle(
		Credentials credentials, long groupId);

	/**
	 * Returns {@code true} if the current {@code User} can add an {@code User}.
	 *
	 * <p>
	 * This method is intended to be used for providing a function to check
	 * permissions in {@code Routes.Builder#addCreator} methods.
	 * </p>
	 *
	 * @param  credentials the current request credentials
	 * @return {@code true} if the current user can add an {@code User}; {@code
	 *         false} otherwise
	 * @review
	 */
	public Boolean forAddingUsers(Credentials credentials);

	/**
	 * Returns a {@code BiFunction} that can be used in an {@code
	 * Routes.Builder#addRemover} method to check if the current {@code User}
	 * has permission to perform that action.
	 *
	 * <p>
	 * This method is intended to be used for providing a function to check
	 * permissions in {@code Routes.Builder#addRemover} methods.
	 * </p>
	 *
	 * @param  clazz the class of elements being deleted
	 * @return the {@code BiFunction} used to check the permissions
	 * @review
	 */
	public BiFunction<Credentials, Long, Boolean> forDeleting(
		Class<? extends ClassedModel> clazz);

	/**
	 * Returns {@code true} if the current {@code User} can delete a {@code
	 * Layout} with the provided {@code plid}.
	 *
	 * <p>
	 * This method is intended to be used for providing a function to check
	 * permissions in {@code Routes.Builder#addRemover} methods.
	 * </p>
	 *
	 * @param  credentials the current request credentials
	 * @param  plid the layout being deleted
	 * @return {@code true} if the current user can delete the {@code Layout};
	 *         {@code false} otherwise
	 * @review
	 */
	public Boolean forDeletingLayouts(Credentials credentials, Long plid);

	/**
	 * Returns a {@code BiFunction} that can be used in an {@code
	 * Routes.Builder#addUpdater} method to check if the current {@code User}
	 * has permission to perform that action.
	 *
	 * <p>
	 * This method is intended to be used for providing a function to check
	 * permissions in {@code Routes.Builder#addUpdater} methods.
	 * </p>
	 *
	 * @param  clazz the class of elements being updated
	 * @return the {@code BiFunction} used to check the permissions
	 * @review
	 */
	public BiFunction<Credentials, Long, Boolean> forUpdating(
		Class<? extends ClassedModel> clazz);

	/**
	 * Returns a {@code PermissionChecker} for the current request credentials,
	 * if they are valid, returns a {@code Failure} with the error otherwise.
	 *
	 * @param  credentials the current request credentials
	 * @return the {@code PermissionChecker} for the current request, if valid;
	 *         a {@code Failure} with the error otherwise
	 * @review
	 */
	public Try<PermissionChecker> getPermissionCheckerTry(
		Credentials credentials);

}