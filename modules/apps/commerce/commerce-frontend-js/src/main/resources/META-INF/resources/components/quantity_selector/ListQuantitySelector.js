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

import {ClaySelectWithOption} from '@clayui/form';
import React, {useEffect, useState} from 'react';

function ListQuantitySelector({
	allowedQuantities,
	onUpdate,
	quantity: startingQuantity,
	...props
}) {
	const [startingAllowedQuantity] = allowedQuantities;
	const [selectedQuantity, setSelectedQuantity] = useState(
		Math.max(startingQuantity, startingAllowedQuantity)
	);

	useEffect(() => {
		onUpdate(selectedQuantity);
	}, [onUpdate, selectedQuantity]);

	return (
		<ClaySelectWithOption
			{...props}
			onChange={({target}) =>
				setSelectedQuantity(parseInt(target.value, 10))
			}
			options={allowedQuantities.map((value) => ({
				label: value.toString(),
				value,
			}))}
			value={selectedQuantity.toString()}
		/>
	);
}

export default ListQuantitySelector;
