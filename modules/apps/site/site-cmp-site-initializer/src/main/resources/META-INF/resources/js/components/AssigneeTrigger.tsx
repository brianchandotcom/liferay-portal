/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AssigneeAvatar,
	AssigneeTriggerProps,
	AssigneeValue,
} from '@liferay/object-dynamic-data-mapping-form-field-type';
import classNames from 'classnames';
import React, {Ref, forwardRef} from 'react';

import './AssigneeTrigger.scss';

function AssigneeTrigger(
	{className, selectedItem, value, ...props}: AssigneeTriggerProps,
	ref: Ref<HTMLInputElement>
) {
	const hasItem = selectedItem && 'name' in selectedItem;

	return (
		<div
			className={classNames(
				'site-cmp-site-initializer__assignee-trigger',
				className
			)}
		>
			{hasItem && (
				<AssigneeAvatar
					image={(selectedItem as AssigneeValue).image}
					name={(selectedItem as AssigneeValue).name || ''}
				/>
			)}

			<input
				{...props}
				className="site-cmp-site-initializer__assignee-input"
				placeholder={Liferay.Language.get('unassigned')}
				ref={ref}
				value={value}
			/>
		</div>
	);
}

export default forwardRef(AssigneeTrigger);
