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

package com.liferay.portal.kernel.version;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.BaseMapBuilder;
import com.liferay.portal.kernel.util.BaseMapWrapper;

import java.util.Collection;
import java.util.Map;

/**
 * @author Luis Ortiz
 */
public class VersionTreeMapBuilder extends BaseMapBuilder {

	public static VersionTreeMapWrapper put(
		BaseMapBuilder.UnsafeSupplier<Version, Exception> keyUnsafeSupplier,
		BaseMapBuilder.UnsafeSupplier<UpgradeProcess, Exception>
			valueUnsafeSupplier) {

		VersionTreeMapWrapper versionTreeMapWrapper =
			new VersionTreeMapWrapper();

		return versionTreeMapWrapper.put(
			keyUnsafeSupplier, valueUnsafeSupplier);
	}

	public static VersionTreeMapWrapper put(
		BaseMapBuilder.UnsafeSupplier<Version, Exception> keyUnsafeSupplier,
		UpgradeProcess value) {

		VersionTreeMapWrapper versionTreeMapWrapper =
			new VersionTreeMapWrapper();

		return versionTreeMapWrapper.put(keyUnsafeSupplier, value);
	}

	public static VersionTreeMapWrapper put(
		Collection<Version> inputCollection,
		BaseMapBuilder.UnsafeFunction<Version, UpgradeProcess, Exception>
			unsafeFunction) {

		VersionTreeMapWrapper versionTreeMapWrapper =
			new VersionTreeMapWrapper();

		return versionTreeMapWrapper.put(inputCollection, unsafeFunction);
	}

	public static VersionTreeMapWrapper put(
		Version key,
		BaseMapBuilder.UnsafeSupplier<UpgradeProcess, Exception>
			valueUnsafeSupplier) {

		VersionTreeMapWrapper versionTreeMapWrapper =
			new VersionTreeMapWrapper();

		return versionTreeMapWrapper.put(key, valueUnsafeSupplier);
	}

	public static VersionTreeMapWrapper put(Version key, UpgradeProcess value) {
		VersionTreeMapWrapper versionTreeMapWrapper =
			new VersionTreeMapWrapper();

		return versionTreeMapWrapper.put(key, value);
	}

	public static VersionTreeMapWrapper put(
		Version key, UpgradeProcess... values) {

		VersionTreeMapWrapper versionTreeMapWrapper =
			new VersionTreeMapWrapper();

		versionTreeMapWrapper.put(key, values);

		return versionTreeMapWrapper;
	}

	public static VersionTreeMapWrapper putAll(
		Map<Version, UpgradeProcess> inputMap) {

		VersionTreeMapWrapper versionTreeMapWrapper =
			new VersionTreeMapWrapper();

		return versionTreeMapWrapper.putAll(inputMap);
	}

	public static final class VersionTreeMapWrapper
		extends BaseMapWrapper<Version, UpgradeProcess> {

		public VersionTreeMap build() {
			return _treeMap;
		}

		public VersionTreeMapWrapper put(
			BaseMapBuilder.UnsafeSupplier<Version, Exception> keyUnsafeSupplier,
			BaseMapBuilder.UnsafeSupplier<UpgradeProcess, Exception>
				valueUnsafeSupplier) {

			doPut(keyUnsafeSupplier, valueUnsafeSupplier);

			return this;
		}

		public VersionTreeMapWrapper put(
			BaseMapBuilder.UnsafeSupplier<Version, Exception> keyUnsafeSupplier,
			UpgradeProcess value) {

			doPut(keyUnsafeSupplier, value);

			return this;
		}

		public VersionTreeMapWrapper put(
			Collection<Version> inputCollection,
			BaseMapBuilder.UnsafeFunction<Version, UpgradeProcess, Exception>
				unsafeFunction) {

			doPut(inputCollection, unsafeFunction);

			return this;
		}

		public VersionTreeMapWrapper put(
			Version key,
			BaseMapBuilder.UnsafeSupplier<UpgradeProcess, Exception>
				valueUnsafeSupplier) {

			doPut(key, valueUnsafeSupplier);

			return this;
		}

		public VersionTreeMapWrapper put(Version key, UpgradeProcess value) {
			_treeMap.put(key, value);

			return this;
		}

		public VersionTreeMapWrapper put(
			Version key, UpgradeProcess... values) {

			_treeMap.put(key, values);

			return this;
		}

		public VersionTreeMapWrapper putAll(
			Map<Version, UpgradeProcess> inputMap) {

			doPutAll(inputMap);

			return this;
		}

		@Override
		protected VersionTreeMap getMap() {
			return _treeMap;
		}

		private final VersionTreeMap _treeMap = new VersionTreeMap();

	}

}