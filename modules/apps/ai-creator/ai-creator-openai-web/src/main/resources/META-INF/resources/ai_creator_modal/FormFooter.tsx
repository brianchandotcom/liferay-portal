/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

interface Props {
	addButtonLabel?: string;
	disableAddButton?: boolean;
	disableRetryButton?: boolean;
	onAdd: () => void;
	onClose: () => void;
	showAddButton: boolean;
	showCreateButton: boolean;
	showRetryButton: boolean;
}

export function FormFooter({
	addButtonLabel = Liferay.Language.get('add'),
	disableAddButton = false,
	disableRetryButton = false,
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
			<ClayButton
				disabled={disableRetryButton}
				displayType="secondary"
				key="try-again"
				type="submit"
			>
				{Liferay.Language.get('try-again')}
			</ClayButton>
		);
	}

	if (showAddButton) {
		children.push(
			<ClayButton
				disabled={disableAddButton}
				displayType="primary"
				key="add"
				onClick={onAdd}
			>
				{addButtonLabel}
			</ClayButton>
		);
	}

	if (showCreateButton) {
		children.push(
			<ClayButton displayType="primary" key="create" type="submit">
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="stars" />
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
