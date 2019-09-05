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

package com.liferay.exportimport.changeset;

import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Máté Thurzó
 */
public class Changeset implements Serializable {

	public static Builder create() {
		return new Builder(new Changeset());
	}

	public static RawBuilder createRaw() {
		return new RawBuilder(new Changeset());
	}

	public String getUuid() {
		return _uuid;
	}

	public Stream<StagedModel> stream() {
		if (_rawMode) {
			return _rawModels.stream();
		}

		Stream<Supplier<Collection<? extends StagedModel>>>
			multiSupplierStream = _multiSuppliers.stream();

		List<StagedModel> multiStagedModels = multiSupplierStream.flatMap(
			s -> {
				Collection<? extends StagedModel> collection = s.get();

				return collection.stream();
			}
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);

		Stream<Supplier<StagedModel>> supplierStream = _suppliers.stream();

		List<StagedModel> stagedModels = supplierStream.map(
			Supplier::get
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);

		List<StagedModel> hierarchyStagedModels = new ArrayList<>();

		for (Map.Entry
				<Supplier<? extends StagedModel>,
				 Function<StagedModel, Collection<?>>> entry :
					_hierarchySuppliers.entrySet()) {

			Supplier<? extends StagedModel> supplier = entry.getKey();

			StagedModel stagedModel = supplier.get();

			String stagedModelClassName =
				ExportImportClassedModelUtil.getClassName(stagedModel);

			hierarchyStagedModels.addAll(
				_getChildrenStagedModels(
					stagedModel, stagedModelClassName, entry.getValue()));
		}

		return Stream.concat(
			hierarchyStagedModels.stream(),
			Stream.concat(stagedModels.stream(), multiStagedModels.stream()));
	}

	public static class Builder {

		public Builder(Changeset changeset) {
			_changeset = changeset;

			_changeset._hierarchySuppliers = new HashMap<>();
			_changeset._multiSuppliers = new ArrayList<>();
			_changeset._rawMode = false;
			_changeset._suppliers = new ArrayList<>();
		}

		public Builder addModel(
			Supplier<ClassedModel> supplier,
			Function<ClassedModel, StagedModel> adapterFunction) {

			Supplier<StagedModel> stagedModelSupplier = () -> {
				ClassedModel classedModel = supplier.get();

				return adapterFunction.apply(classedModel);
			};

			_changeset._suppliers.add(stagedModelSupplier);

			return this;
		}

		public Builder addMultipleStagedModel(
			Supplier<Collection<? extends StagedModel>> supplier) {

			_changeset._multiSuppliers.add(supplier);

			return this;
		}

		public Builder addStagedModel(Supplier<StagedModel> supplier) {
			_changeset._suppliers.add(supplier);

			return this;
		}

		@SuppressWarnings("unchecked")
		public <T extends StagedModel> Builder addStagedModelHierarchy(
			Supplier<T> supplier,
			Function<T, Collection<?>> hierarchyFunction) {

			Function<StagedModel, Collection<?>> function =
				(Function<StagedModel, Collection<?>>)hierarchyFunction;

			_changeset._hierarchySuppliers.put(supplier, function);

			return this;
		}

		public Changeset build() {
			return _changeset;
		}

		private final Changeset _changeset;

	}

	public static class RawBuilder {

		public RawBuilder(Changeset changeset) {
			_changeset = changeset;

			_changeset._rawMode = true;
			_changeset._rawModels = new ArrayList<>();
		}

		public RawBuilder addMultipleStagedModel(
			Collection<? extends StagedModel> stagedModels) {

			Stream<? extends StagedModel> stream = stagedModels.stream();

			stream.filter(
				Objects::nonNull
			).forEach(
				stagedModel -> _changeset._rawModels.add(stagedModel)
			);

			return this;
		}

		public RawBuilder addStagedModel(StagedModel stagedModel) {
			_changeset._rawModels.add(stagedModel);

			return this;
		}

		public Changeset build() {
			return _changeset;
		}

		private final Changeset _changeset;

	}

	private Changeset() {
	}

	private List<StagedModel> _getChildrenStagedModels(
		final StagedModel parentStagedModel, final String parentClassName,
		Function<StagedModel, Collection<?>> hierarchyFunction) {

		List<StagedModel> stagedModels = new ArrayList<>();

		for (Object object : hierarchyFunction.apply(parentStagedModel)) {
			StagedModel stagedModel = (StagedModel)object;

			String stagedModelClassName = stagedModel.getModelClassName();

			if (stagedModelClassName.equals(parentClassName)) {
				stagedModels.addAll(
					_getChildrenStagedModels(
						stagedModel, parentClassName, hierarchyFunction));
			}

			stagedModels.add(stagedModel);
		}

		return stagedModels;
	}

	private Map
		<Supplier<? extends StagedModel>, Function<StagedModel, Collection<?>>>
			_hierarchySuppliers;
	private List<Supplier<Collection<? extends StagedModel>>> _multiSuppliers;
	private boolean _rawMode;
	private List<StagedModel> _rawModels;
	private List<Supplier<StagedModel>> _suppliers;
	private String _uuid = PortalUUIDUtil.generate();

}