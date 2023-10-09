/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import {InternalDispatch} from '@clayui/shared';
import classNames from 'classnames';
import React from 'react';

const TriggerLabel = React.forwardRef<HTMLButtonElement, any>(
	({children, className: _className, onClick, ...otherProps}, ref) => (
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
	)
);

interface RuleSelectProps {
	items: {label: string; value: string}[];
	onSelectionChange: InternalDispatch<React.Key>;
	selectedKey?: string;
}

export default function RuleSelect({
	items,
	onSelectionChange,
	selectedKey,
}: RuleSelectProps) {
	return (
		<Picker
			as={TriggerLabel}
			items={items}
			onSelectionChange={onSelectionChange}
			placeholder={Liferay.Language.get('select')}
			selectedKey={selectedKey}
		>
			{(item) => <Option key={item.value}>{item.label}</Option>}
		</Picker>
	);
}
