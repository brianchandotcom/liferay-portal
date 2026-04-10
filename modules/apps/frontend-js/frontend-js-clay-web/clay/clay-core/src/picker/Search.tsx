/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {InternalDispatch, Keys} from '@clayui/shared';
import React from 'react';

import {PickerMessages} from './Picker';

type Props = {
	activeDescendant: React.Key;
	ariaControls: string;
	getOptions: () => Array<HTMLElement>;
	messages: PickerMessages;
	onActiveChange: InternalDispatch<boolean>;
	onChange: InternalDispatch<string>;
	onKeyDown: InternalDispatch<React.KeyboardEvent<HTMLElement>>;
	onMoveFocus: (
		key: 'PageUp' | 'PageDown',
		position: number,
		list: Array<HTMLElement> | Array<React.Key>
	) => void;
	onPress: () => void;
	triggerRef: React.RefObject<HTMLButtonElement | null>;
	value: string;
	visible: boolean;
};

export const Search = React.forwardRef<HTMLInputElement, Props>(function Search(
	{
		activeDescendant,
		ariaControls,
		getOptions,
		messages,
		onActiveChange,
		onChange: externalOnChange,
		onKeyDown: externalOnKeyDown,
		onMoveFocus,
		onPress,
		triggerRef,
		value,
		visible,
	},
	ref
) {
	function onChange(event: React.ChangeEvent<HTMLInputElement>) {
		externalOnChange(event.target.value);
	}

	function onKeyDown(event: React.KeyboardEvent<HTMLInputElement>) {
		if (event.key === Keys.Enter) {
			event.preventDefault();
			event.stopPropagation();
			onPress();

			return;
		}

		if (event.key === Keys.Esc) {
			event.preventDefault();
			event.stopPropagation();
			onActiveChange(false);
			triggerRef.current?.focus();

			return;
		}

		if (event.key === 'PageUp' || event.key === 'PageDown') {
			event.preventDefault();

			const list = getOptions();

			onMoveFocus(
				event.key,
				list.findIndex(
					(element) =>
						element instanceof HTMLElement &&
						element.getAttribute('id') === String(activeDescendant)
				),
				list
			);

			return;
		}

		externalOnKeyDown(event as React.KeyboardEvent<HTMLElement>);
	}

	return (
		visible && (
			<div className="pb-2 pt-3 px-3">
				<ClayInput.Group small>
					<ClayInput.GroupItem className="input-group-item-focusable">
						<ClayInput
							aria-activedescendant={
								activeDescendant
									? String(activeDescendant)
									: undefined
							}
							aria-autocomplete="list"
							aria-controls={ariaControls}
							aria-label={messages.searchPlaceholder}
							insetAfter
							onChange={onChange}
							onKeyDown={onKeyDown}
							placeholder={messages.searchPlaceholder}
							ref={ref}
							type="text"
							value={value}
						/>

						<ClayInput.GroupInsetItem after tag="span">
							<ClayIcon symbol="search" />
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</div>
		)
	);
});
