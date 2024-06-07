/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {sub} from 'frontend-js-web';
import React, {KeyboardEventHandler, ReactNode, Ref} from 'react';

interface RuleBuilderItemProps {
	'aria-label': string;
	'children': ReactNode;
	'description': string;
	'onDeleteButtonClick': () => void;
	'onItemSelected': () => void;
	'showDeleteButton': boolean;
	'type': 'action' | 'condition';
	'wrapperRef'?: Ref<HTMLDivElement>;
}

export default function RuleBuilderItem({
	children,
	description,
	onDeleteButtonClick,
	onItemSelected,
	showDeleteButton,
	type,
	wrapperRef,
	...otherProps
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

		if (event.key === 'Enter' || event.key === ' ') {
			event.preventDefault();

			onItemSelected();
		}

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
			{...otherProps}
		>
			<div className="c-gap-2 d-flex flex-grow-1">{children}</div>

			{showDeleteButton ? (
				<ClayButtonWithIcon
					aria-label={
						type === 'action'
							? sub(
									Liferay.Language.get('delete-action-x'),
									description
								)
							: sub(
									Liferay.Language.get('delete-condition-x'),
									description
								)
					}
					borderless
					className="lfr-portal-tooltip page-editor__rule-builder-delete-button"
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
