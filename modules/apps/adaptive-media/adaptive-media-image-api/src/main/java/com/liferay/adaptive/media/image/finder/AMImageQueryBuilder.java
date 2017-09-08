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

package com.liferay.adaptive.media.image.finder;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.finder.AMQuery;
import com.liferay.adaptive.media.finder.AMQueryBuilder;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Provides a specialized interface for building the query that will be used to
 * fetch the adaptive media images. This builder allows to specify the
 * requirements that should be met by the adaptive media images using the
 * methods available.
 *
 * The recommended use of this interface is by chaining those methods to create
 * the query with the requirements. These methods can either be:
 * <ul>
 * <li>
 * Initial: they need to be invoked as the first method in the chain. They can
 * be chained by other methods to add more requirements.
 * </li>
 * <li>
 * Intermediate: they can be chained to an initial method or to another
 * intermediate method to add more requirements.
 * </li>
 * <li>
 * Terminal: they can be chained bo both initial or intermediate methods but
 * they don't accept any more chaining.
 * </li>
 * </ul>
 *
 * @review
 *
 * @author Adolfo Pérez
 */
public interface AMImageQueryBuilder
	extends AMQueryBuilder<FileVersion, AMImageProcessor> {

	/**
	 * This is an initial method that is used to specify that only the adaptive
	 * media images that belong to an specific file entry should be returned.
	 *
	 * @param fileEntry the file entry that was used to generate the adaptive
	 *        media images.
	 *
	 * @review
	 */
	public InitialStep forFileEntry(FileEntry fileEntry);

	/**
	 * This is an initial method that can be invoked to specify which file
	 * version the adaptive media images need to belong to.
	 *
	 * @param fileVersion the file version where the adaptive media images
	 *        belong
	 *
	 * @review
	 */
	public InitialStep forFileVersion(FileVersion fileVersion);

	public enum ConfigurationStatus {

		ANY(amImageConfigurationEntry -> true),
		ENABLED(AMImageConfigurationEntry::isEnabled),
		DISABLED(
			amImageConfigurationEntry ->
				!amImageConfigurationEntry.isEnabled());

		public Predicate<AMImageConfigurationEntry> getPredicate() {
			return _predicate;
		}

		private ConfigurationStatus(
			Predicate<AMImageConfigurationEntry> predicate) {

			_predicate = predicate;
		}

		private final Predicate<AMImageConfigurationEntry> _predicate;

	}

	public interface ConfigurationStep {

		/**
		 * This is a terminal method that is used to specify that only the
		 * adaptive media images that were created with an specific
		 * configuration should be returned.
		 *
		 * @param configurationUuid the configuration uuid that was used to
		 *        generate the adaptive media images
		 *
		 * @review
		 */

		public FinalStep forConfiguration(String configurationUuid);

		/**
		 * This is an intermediate method that is used to specify that only the
		 * adaptive media images that were created with an specific
		 * configuration status should be returned.
		 *
		 * @param configurationStatus the status of the configuration that was used to
		 *        generate the adaptive media images
		 *
		 * @review
		 */
		public InitialStep withConfigurationStatus(
			ConfigurationStatus configurationStatus);

	}

	public interface FinalStep {

		/**
		 * Creates and returns the query.
		 *
		 * This method can be invoked after any initial, intermediate or
		 * terminal method.
		 *
		 * @return the adaptive media query
		 *
		 * @review
		 */
		public AMQuery<FileVersion, AMImageProcessor> done();

	}

	public interface FuzzySortStep extends FinalStep {

		/**
		 * This is an intermediate method that is used to sort the adaptive
		 * media based on specific attribute values. The sorting is done using
		 * a distance comparator and the adaptive media images that are closer
		 * are returned first.
		 *
		 * The distance comparator is implemented based on the value returned by
		 * the method {@link AMAttribute#distance(Object, Object)}.
		 *
		 * This method doesn't have any effect if the method
		 * {@link StrictSortStep#orderBy(AMAttribute, SortOrder)} is invoked in
		 * the same query builder because that sorting has precedence.
		 *
		 * This method can be invoked with different attributes and, in that
		 * case, all of them will be used in the sorting as follows:
		 *
		 * 1. The first attribute used to invoke the method will be used to sort
		 * all the adaptive media images.
		 * 2. In case that two or more adaptive media images are located at the
		 * same distance, the second attribute will be used to sort those
		 * elements.
		 * 3. If sorting with the second attribute doesn't resolve all the
		 * cases, the third attribute will be used.
		 * 4. And so on.
		 *
		 * @param amAttribute the attribute used to sort the adaptive media
		 *        images
		 * @param valueOptional a non-<code>null</code> optional value for the
		 *        attribute
		 *
		 * @review
		 */
		public <V> FuzzySortStep with(
			AMAttribute<AMImageProcessor, V> amAttribute,
			Optional<V> valueOptional);

		/**
		 * This is an intermediate method that is used to sort the adaptive
		 * media based on specific attribute values. The sorting is done using
		 * a distance comparator and the adaptive media images that are closer
		 * are returned first.
		 *
		 * The distance comparator is implemented based on the value returned by
		 * the method {@link AMAttribute#distance(Object, Object)}.
		 *
		 * This method doesn't have any effect if the method
		 * {@link StrictSortStep#orderBy(AMAttribute, SortOrder)} is invoked in
		 * the same query builder because that sorting has precedence.
		 *
		 * This method can be invoked with different attributes and, in that
		 * case, all of them will be used in the sorting as follows:
		 *
		 * 1. The first attribute used to invoke the method will be used to sort
		 * all the adaptive media images.
		 * 2. In case that two or more adaptive media images are located at the
		 * same distance, the second attribute will be used to sort those
		 * elements.
		 * 3. If sorting with the second attribute doesn't resolve all the
		 * cases, the third attribute will be used.
		 * 4. And so on.
		 *
		 * @param amAttribute the attribute used to sort the adaptive media
		 *        images
		 * @param value the value for the attribute
		 *
		 * @review
		 */
		public <V> FuzzySortStep with(
			AMAttribute<AMImageProcessor, V> amAttribute, V value);

	}

	public interface InitialStep
		extends ConfigurationStep, FuzzySortStep, StrictSortStep {
	}

	public enum SortOrder {

		ASC {

			@Override
			public int getSortValue(int value) {
				return value;
			}

		},

		DESC {

			@Override
			public int getSortValue(int value) {
				return -value;
			}

		};

		public abstract int getSortValue(int value);

	}

	public interface StrictSortStep extends FinalStep {

		/**
		 * This is an intermediate method that is used to sort the adaptive
		 * media based on a specific attribute.
		 *
		 * This method takes precedence over the method {@link
		 * FuzzySortStep#with(AMAttribute, Optional)} and {@link
		 * FuzzySortStep#with(AMAttribute, Object)}
		 *
		 * This method doesn't have any effect if the method
		 * {@link StrictSortStep#orderBy(AMAttribute, SortOrder)} is invoked in
		 * the same query builder because that sorting has precedence.
		 *
		 * This method can be invoked with different attributes and, in that
		 * case, all of them will be used in the sorting as follows:
		 *
		 * 1. The first attribute used to invoke the method will be used to sort
		 * all the adaptive media images.
		 * 2. In case that two or more adaptive media images are located at the
		 * same distance, the second attribute will be used to sort those
		 * elements.
		 * 3. If sorting with the second attribute doesn't resolve all the
		 * cases, the third attribute will be used.
		 * 4. And so on.
		 *
		 * @param  amAttribute the attribute used to sort the adaptive media
		 *                           images
		 * @param  sortOrder the order used to sort the adaptive media images
		 *
		 * @review
		 */
		public <V> StrictSortStep orderBy(
			AMAttribute<AMImageProcessor, V> amAttribute, SortOrder sortOrder);

	}

}