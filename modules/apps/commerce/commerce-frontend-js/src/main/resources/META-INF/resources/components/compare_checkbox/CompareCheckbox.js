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

import {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {
	ADD_ITEM_TO_COMPARE,
	COMPARE_IS_AVAILABLE,
	COMPARE_IS_UNAVAILABLE,
	ITEM_REMOVED_FROM_COMPARE,
	REMOVE_ITEM_FROM_COMPARE,
} from '../../utilities/eventsDefinitions';

function CompareCheckbox(props) {
	const [inCompare, setInCompare] = useState(Boolean(props.inCompare));
	const [disabled, setDisabled] = useState(props.disabled);

	const updateParent = useCallback(() => {
		if (props.onUpdate) {
			props.onUpdate({
				disabled,
				inCompare,
			});
		}
	}, [disabled, inCompare, props]);

	const enableCompare = useCallback(() => {
		setDisabled(false);
		updateParent();
	}, [updateParent]);

	const disableCompare = useCallback(() => {
		setDisabled(true);
		updateParent();
	}, [updateParent]);

	const removeFromCompare = useCallback(
		(data) => {
			if (data.id === props.itemId) {
				setInCompare(false);
			}
			updateParent();
		},
		[props.itemId, updateParent]
	);

	function handleCheckboxClick(_e) {
		setInCompare((v) => {
			Liferay.fire(v ? ADD_ITEM_TO_COMPARE : REMOVE_ITEM_FROM_COMPARE, {
				id: props.itemId,
				thumbnail: props.pictureUrl || null,
			});

			return !v;
		});
	}

	useEffect(() => {
		Liferay.on(COMPARE_IS_AVAILABLE, enableCompare);
		Liferay.on(COMPARE_IS_UNAVAILABLE, disableCompare);
		Liferay.on(ITEM_REMOVED_FROM_COMPARE, removeFromCompare);

		return () => {
			Liferay.detach(COMPARE_IS_AVAILABLE, enableCompare);
			Liferay.detach(COMPARE_IS_UNAVAILABLE, disableCompare);
			Liferay.detach(ITEM_REMOVED_FROM_COMPARE, removeFromCompare);
		};
	}, [disableCompare, enableCompare, removeFromCompare]);

	return (
		<label className="compare-checkbox" disabled={disabled}>
			<ClayCheckbox
				aria-label={props.label || null}
				checked={inCompare}
				disabled={disabled && !inCompare}
				label={props.label || null}
				onChange={handleCheckboxClick}
			/>
		</label>
	);
}

CompareCheckbox.propTypes = {
	disabled: PropTypes.bool,
	inCompare: PropTypes.bool,
	itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
		.isRequired,
	label: PropTypes.string,
	onUpdate: PropTypes.func,
	pictureUrl: PropTypes.string,
};

export default CompareCheckbox;
