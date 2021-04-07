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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {PagesVisitor} from 'data-engine-js-components-web';
import React from 'react';

export default function ({
	fieldTypes,
	focusedCustomObjectField,
	focusedField,
	onClick,
}) {
	let {settingsContext} = focusedField;

	if (focusedCustomObjectField.settingsContext) {
		settingsContext = focusedCustomObjectField.settingsContext;
	}

	const visitor = new PagesVisitor(settingsContext.pages);
	const typeField = visitor.findField((field) => field.fieldName === 'type');

	const fieldType = fieldTypes.find(({name}) => {
		return name === typeField.value;
	});

	if (!fieldType) {
		return null;
	}

	return (
		<div className="d-flex">
			<ClayButtonWithIcon
				className="mr-2"
				displayType="secondary"
				monospaced={false}
				onClick={onClick}
				symbol="angle-left"
			/>

			<ClayDropDown
				className="d-inline-flex flex-grow-1"
				onActiveChange={() => {}}
				trigger={
					<ClayButton
						className="d-inline-flex flex-grow-1"
						disabled={true}
						displayType="secondary"
					>
						<ClayIcon
							className="mr-2 mt-1"
							symbol={fieldType.icon}
						/>

						{fieldType.label}

						<span className="d-inline-flex ml-auto navbar-breakpoint-down-d-none pt-2">
							<ClayIcon
								className="inline-item inline-item-after"
								symbol="caret-bottom"
							/>
						</span>
					</ClayButton>
				}
			></ClayDropDown>
		</div>
	);
}
