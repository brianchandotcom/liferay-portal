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

import {addItem, updateItem} from '../../utils/client.es';

export default ({
	dataDefinition,
	dataDefinitionId,
	dataLayout,
	dataLayoutId
}) => {
	const updateDefinition = () =>
		updateItem(
			`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`,
			dataDefinition
		);

	if (dataLayoutId) {
		return updateDefinition().then(() =>
			updateItem(
				`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`,
				dataLayout
			)
		);
	}

	return updateDefinition().then(() =>
		addItem(
			`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-layouts`,
			dataLayout
		)
	);
};
