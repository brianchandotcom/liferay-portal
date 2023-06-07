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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

interface Props {
	onAdd: () => void;
	onClose: () => void;
	showAddButton: boolean;
	showCreateButton: boolean;
	showRetryButton: boolean;
}

export function FormFooter({
	onAdd,
	onClose,
	showAddButton,
	showCreateButton,
	showRetryButton,
}: Props) {
	const children = [
		<ClayButton
			borderless
			displayType="secondary"
			key="cancel"
			onClick={onClose}
			type="button"
		>
			{Liferay.Language.get('cancel')}
		</ClayButton>,
	];

	if (showRetryButton) {
		children.push(
			<ClayButton displayType="secondary" key="try-again" type="submit">
				{Liferay.Language.get('try-again')}
			</ClayButton>
		);
	}

	if (showAddButton) {
		children.push(
			<ClayButton displayType="primary" key="add" onClick={onAdd}>
				{Liferay.Language.get('add')}
			</ClayButton>
		);
	}

	if (showCreateButton) {
		children.push(
			<ClayButton displayType="primary" key="create" type="submit">
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="bolt" />
				</span>

				{Liferay.Language.get('create')}
			</ClayButton>
		);
	}

	return (
		<ClayButton.Group
			className="border-top c-px-4 c-py-3 justify-content-end"
			spaced
		>
			{children}
		</ClayButton.Group>
	);
}
