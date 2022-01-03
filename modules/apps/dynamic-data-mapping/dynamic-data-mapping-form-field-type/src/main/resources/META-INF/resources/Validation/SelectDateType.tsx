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

import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useMemo} from 'react';

const SelectDateType: React.FC<IProps> = ({
	dateFieldName,
	dateFieldOptions,
	label,
	onChange,
	options,
	tooltip,
	type,
}) => {
	const selectedOption = useMemo(() => {
		if (type === 'dateField') {
			const date = dateFieldOptions.find(
				({name}) => dateFieldName === name
			) as IDateFieldOption;

			return date?.label ?? 'Response Date';
		}

		const option = options?.find(({value}) => value === type);

		return option?.label;
	}, [dateFieldName, type, dateFieldOptions, options]);

	const items: IItem[] = [
		...options.map((option) => ({
			...option,
			onClick: () => onChange(option.value),
		})),
	];

	if (dateFieldOptions.length > 0) {
		items.push(
			{
				type: 'divider',
			},
			{
				items: dateFieldOptions.map((option) => ({
					...option,
					onClick: () => {
						onChange('dateField', option.name);
					},
				})),
				label: Liferay.Language.get('date-fields'),
				type: 'group',
			}
		);
	}

	const select = (
		<div className="form-builder-select-field input-group-container">
			<div className="form-control results-chosen select-field-trigger">
				<div className="option-selected">{selectedOption}</div>

				<a className="select-arrow-down-container">
					<ClayIcon symbol="caret-double" />
				</a>
			</div>
		</div>
	);

	return (
		<div className="ddm__validation-date-start-end">
			<div className="ddm__validation-date-start-end-label">
				<label>{label}</label>

				{tooltip && (
					<ClayTooltipProvider>
						<div data-tooltip-align="top" title={tooltip}>
							<ClayIcon
								className="ddm__validation-date-start-end-icon"
								symbol="question-circle-full"
							/>
						</div>
					</ClayTooltipProvider>
				)}
			</div>

			<ClayDropDownWithItems
				items={items}
				menuElementAttrs={{className: 'ddm-select-dropdown'}}
				trigger={select}
			/>
		</div>
	);
};

export default SelectDateType;

interface IProps {
	dateFieldName?: string;
	dateFieldOptions: IDateFieldOption[];
	label: string;
	onChange: (value: Type, dateFieldName?: string) => void;
	options: IOptions[];
	tooltip?: string;
	type: Type;
}

interface IItem {
	items?: {
		label: string;
		name: string;
		onClick: () => void;
	}[];
	label?: string;
	name?: DateType;
	onClick?: () => void;
	type?: 'group' | 'divider';
	value?: DateType;
}

interface IDateFieldOption {
	label: string;
	name: string;
}

interface IOptions {
	label: string;
	name: DateType;
	value: DateType;
}
