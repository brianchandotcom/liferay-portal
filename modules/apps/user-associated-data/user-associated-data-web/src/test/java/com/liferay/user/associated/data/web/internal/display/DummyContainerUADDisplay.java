/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.display;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Drew Brokke
 */
public class DummyContainerUADDisplay
	extends BaseDummyUADDisplay<DummyContainer> {

	public DummyContainerUADDisplay(
		DummyService<DummyContainer> dummyContainerService) {

		_dummyContainerService = dummyContainerService;
	}

	@Override
	public DummyContainer getTopLevelContainer(
		Class<?> parentContainerTypeKey, Serializable parentContainerId,
		Object childObject) {

		long parentDummyContainerId = (long)parentContainerId;

		long objectParentContainerId = 0;

		if (childObject instanceof DummyContainer) {
			DummyContainer childDummyContainer = (DummyContainer)childObject;

			objectParentContainerId = childDummyContainer.getContainerId();

			if (parentDummyContainerId == objectParentContainerId) {
				return childDummyContainer;
			}
		}
		else {
			DummyEntry childDummyEntry = (DummyEntry)childObject;

			objectParentContainerId = childDummyEntry.getContainerId();

			if (parentDummyContainerId == objectParentContainerId) {
				return null;
			}
		}

		List<Long> tree = _buildTree((UserAssociatedEntity)childObject);

		if (parentDummyContainerId == 0) {
			return get(tree.get(tree.size() - 1));
		}

		if (tree.contains(parentDummyContainerId)) {
			return get(tree.get(tree.indexOf(parentDummyContainerId) - 1));
		}

		return null;
	}

	@Override
	public Class<DummyContainer> getTypeClass() {
		return DummyContainer.class;
	}

	@Override
	public boolean isInTrash(DummyContainer dummyContainer) {
		return false;
	}

	@Override
	protected DummyService<DummyContainer> getDummyService() {
		return _dummyContainerService;
	}

	private List<Long> _buildTree(UserAssociatedEntity userAssociatedEntity) {
		List<Long> tree = new ArrayList<>();

		long containerId = userAssociatedEntity.getContainerId();

		if (containerId == 0) {
			return tree;
		}

		while (containerId != 0) {
			tree.add(containerId);

			DummyContainer dummyContainer = get(containerId);

			containerId = dummyContainer.getContainerId();
		}

		return tree;
	}

	private final DummyService<DummyContainer> _dummyContainerService;

}