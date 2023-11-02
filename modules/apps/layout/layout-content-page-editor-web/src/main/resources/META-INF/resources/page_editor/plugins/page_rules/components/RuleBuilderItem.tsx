/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {KeyboardEventHandler, ReactNode, Ref} from 'react';

interface RuleBuilderItemProps {
	children: ReactNode;
	onDeleteButtonClick: () => void;
	showDeleteButton: boolean;
	type: 'action' | 'condition';
	wrapperRef?: Ref<HTMLDivElement>;
}

export default function RuleBuilderItem({
	children,
	onDeleteButtonClick,
	showDeleteButton,
	type,
	wrapperRef,
}: RuleBuilderItemProps) {
	const onKeyDown: KeyboardEventHandler = (event) => {
		if (event.target !== event.currentTarget) {
			return;
		}

		const items = Array.from<HTMLElement>(
			document.querySelectorAll(
				`.page-editor__rule-builder-item--${type}`
			)
		);

		if (event.key === 'ArrowDown') {
			event.preventDefault();

			const index = items.indexOf(event.target as HTMLElement);

			let nextIndex = index + 1;

			if (index === items.length - 1) {
				nextIndex = 0;
			}

			items[nextIndex]?.focus();
		}

		if (event.key === 'ArrowUp') {
			event.preventDefault();

			const index = items.indexOf(event.target as HTMLElement);

			let nextIndex = index - 1;

			if (index === 0) {
				nextIndex = items.length - 1;
			}

			items[nextIndex]?.focus();
		}
	};

	return (
		<div
			className={`p-2 mb-3 d-flex align-items-center justify-content-between page-editor__rule-builder-item page-editor__rule-builder-item--${type}`}
			onKeyDown={onKeyDown}
			ref={wrapperRef}
			role="menuitem"
			tabIndex={0}
		>
			<div className="c-gap-2 d-flex flex-grow-1">{children}</div>

			{showDeleteButton ? (
				<ClayButtonWithIcon
					aria-label={
						type === 'action'
							? Liferay.Language.get('delete-action')
							: Liferay.Language.get('delete-condition')
					}
					borderless
					className="page-editor__rule-builder-delete-button"
					displayType="secondary"
					onClick={() => onDeleteButtonClick()}
					size="sm"
					symbol="times-circle"
					title={
						type === 'action'
							? Liferay.Language.get('delete-action')
							: Liferay.Language.get('delete-condition')
					}
				/>
			) : null}
		</div>
	);
}
