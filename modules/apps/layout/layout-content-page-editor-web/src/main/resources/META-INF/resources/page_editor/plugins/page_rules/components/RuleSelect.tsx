/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import classNames from 'classnames';
import React, {MutableRefObject, useEffect} from 'react';

import {getSelectOptions} from '../../../common/getSelectOptions';

const TriggerLabel = React.forwardRef<HTMLButtonElement, any>(
	(
		{children, className: _className, onClick, triggerRef, ...otherProps},
		ref
	) => {
		useEffect(() => {
			if (ref && triggerRef) {

				// @ts-ignore

				triggerRef.current = ref.current;
			}
		});

		return (
			<ClayButton
				className={classNames(
					'page-editor__rule-builder-select form-control form-control-select form-control-sm'
				)}
				displayType="secondary"
				onClick={onClick}
				ref={ref}
				size="sm"
				{...otherProps}
			>
				{children}
			</ClayButton>
		);
	}
);

interface RuleSelectProps<T> {
	'aria-label'?: string;
	'items': ReadonlyArray<{label: string; value: T}>;
	'onSelectionChange': (selection: T) => void;
	'selectedKey'?: string;
	'triggerRef'?: MutableRefObject<HTMLButtonElement | undefined>;
}

export default function RuleSelect<T extends string>({
	items,
	onSelectionChange,
	selectedKey,
	triggerRef,
	...otherProps
}: RuleSelectProps<T>) {
	return (
		<Picker
			as={TriggerLabel}
			items={getSelectOptions(items)}
			onSelectionChange={(selection: React.Key) =>
				onSelectionChange(selection as T)
			}
			placeholder={Liferay.Language.get('select')}
			selectedKey={selectedKey}
			triggerRef={triggerRef}
			{...otherProps}
		>
			{(item) => <Option key={item.value}>{item.label}</Option>}
		</Picker>
	);
}
