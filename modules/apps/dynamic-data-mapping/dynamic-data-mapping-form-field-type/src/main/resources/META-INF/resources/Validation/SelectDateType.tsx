/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	if (dateFieldOptions.length) {
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
