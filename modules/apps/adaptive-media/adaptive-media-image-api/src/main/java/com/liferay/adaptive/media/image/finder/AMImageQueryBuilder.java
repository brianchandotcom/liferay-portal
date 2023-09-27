/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.finder;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.finder.AMQuery;
import com.liferay.adaptive.media.finder.AMQueryBuilder;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.function.Predicate;

/**
 * Provides a specialized interface to build a query to fetch adaptive media
 * images. This builder lets you specify the requirements for the adaptive media
 * images using the given methods.
 *
 * <p>
 * It's recommended that you use this interface by chaining those methods to
 * create the query with the requirements. These methods can either be
 * </p>
 *
 * <ul>
 * <li>
 * Initial: must be invoked as the first method in the chain. They can be
 * chained by other methods to add more requirements.
 * </li>
 * <li>
 * Intermediate: can be chained to an initial method or to another intermediate
 * method to add more requirements.
 * </li>
 * <li>
 * Terminal: can be chained to both initial or intermediate methods, but they
 * don't accept additional chaining.
 * </li>
 * </ul>
 *
 * @author Adolfo Pérez
 */
public interface AMImageQueryBuilder
	extends AMQueryBuilder<FileVersion, AMProcessor<FileVersion>> {

	/**
	 * An initial method that specifies that only adaptive media images that
	 * belong to a specific file entry should be returned.
	 *
	 * @param fileEntry the adaptive media images' file entry
	 */
	public InitialStep forFileEntry(FileEntry fileEntry);

	/**
	 * An initial method that specifies the file version of the adaptive media
	 * images.
	 *
	 * @param fileVersion the adaptive media images' file version
	 */
	public InitialStep forFileVersion(FileVersion fileVersion);

	public enum ConfigurationStatus {

		ANY(amImageConfigurationEntry -> true),
		DISABLED(
			amImageConfigurationEntry ->
				!amImageConfigurationEntry.isEnabled()),
		ENABLED(AMImageConfigurationEntry::isEnabled);

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
		 * A terminal method that specifies that only adaptive media images with
		 * the given configuration should be returned.
		 *
		 * @param configurationUuid the adaptive media images' configuration
		 *        UUID
		 */
		public FinalStep forConfiguration(String configurationUuid);

		/**
		 * An intermediate method that specifies that only adaptive media images
		 * with the given configuration status should be returned.
		 *
		 * @param configurationStatus the adaptive media images' configuration
		 *        status
		 */
		public InitialStep withConfigurationStatus(
			ConfigurationStatus configurationStatus);

	}

	public interface FinalStep {

		/**
		 * Creates and returns the query. This method can be invoked after any
		 * initial, intermediate, or terminal method.
		 *
		 * @return the adaptive media query
		 */
		public AMQuery<FileVersion, AMProcessor<FileVersion>> done();

	}

	public interface FuzzySortStep extends FinalStep {

		/**
		 * An intermediate method that sorts the adaptive media based on
		 * specific attribute values. Sorting is done using a distance
		 * comparator that returns the adaptive media images that are a closer
		 * match first.
		 *
		 * <p>
		 * The distance comparator is implemented based on the value returned by
		 * the method {@link AMAttribute#distance(Object, Object)}.
		 * </p>
		 *
		 * <p>
		 * If the method {@link StrictSortStep#orderBy} is invoked in the same
		 * query builder, it takes precedence and this method has no effect.
		 * </p>
		 *
		 * <p>
		 * If this method is invoked with multiple attributes, they will be used
		 * in the following order:
		 * </p>
		 *
		 * <ol>
		 * <li>
		 * The first attribute sorts all the adaptive media images.
		 * </li>
		 * <li>
		 * If two or more adaptive media images are located at the same
		 * distance, the second attribute is used to sort those elements.
		 * </li>
		 * <li>
		 * If the second attribute doesn't resolve all the cases, the third
		 * attribute is used, and so on.
		 * </li>
		 * </ol>
		 *
		 * @param amAttribute the attribute used to sort the adaptive media
		 *        images
		 * @param value the attribute's value
		 */
		public <V> FuzzySortStep with(
			AMAttribute<AMProcessor<FileVersion>, V> amAttribute, V value);

	}

	public interface InitialStep
		extends ConfigurationStep, FuzzySortStep, StrictSortStep {
	}

	public enum SortOrder {

		ASC {

			@Override
			public long getSortValue(long value) {
				return value;
			}

		},
		DESC {

			@Override
			public long getSortValue(long value) {
				return -value;
			}

		};

		public abstract long getSortValue(long value);

	}

	public interface StrictSortStep extends FinalStep {

		/**
		 * An intermediate method that sorts the adaptive media based on a
		 * specific attribute.
		 *
		 * <p>
		 * This method takes precedence over the methods
		 * {@link FuzzySortStep#with(AMAttribute, Object)}.
		 * </p>
		 *
		 * <p>
		 * If the method {@link #orderBy} is invoked in the same query builder,
		 * it takes precedence and this method has no effect.
		 * </p>
		 *
		 * <p>
		 * If this method is invoked with multiple attributes, they will be used
		 * in the following order:
		 * </p>
		 *
		 * <ol>
		 * <li>
		 * The first attribute sorts all the adaptive media images.
		 * </li>
		 * <li>
		 * If two or more adaptive media images are located at the same
		 * distance, the second attribute is used to sort those elements.
		 * </li>
		 * <li>
		 * If the second attribute doesn't resolve all the cases, the third
		 * attribute is used, and so on.
		 * </li>
		 * </ol>
		 *
		 * @param amAttribute the attribute used to sort the adaptive media
		 *        images
		 * @param sortOrder the order used to sort the adaptive media images
		 */
		public <V> StrictSortStep orderBy(
			AMAttribute<AMProcessor<FileVersion>, V> amAttribute,
			SortOrder sortOrder);

	}

}