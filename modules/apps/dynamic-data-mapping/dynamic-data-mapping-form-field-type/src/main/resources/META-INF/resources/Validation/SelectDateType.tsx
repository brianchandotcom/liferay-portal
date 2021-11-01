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
import { ClayTooltipProvider } from '@clayui/tooltip';
import React, { useMemo } from 'react';

const SelectDateType: React.FC<IProps> = ({
    type,
    dateFieldName,
    dateFieldOptions,
    options,
    onChange,
    label,
    tooltip,
}) => {

    const selectedOption = useMemo(()=>{
		if(type === "dateField"){
			const date = dateFieldOptions.find(({name}) => 
				dateFieldName === name
			) as IDateFieldOption;

			return date.label;
		}

		const option = options?.find(({value}) => 
			value === type
		);

		return option?.label;
	},[type, dateFieldName, dateFieldOptions]);

    const items: IItem[] = [

        ...options.map((option) => ({
            ...option,
            onClick: () => onChange(option.value)
        })),
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
		},
	];

    const select = (
        <div className="form-builder-select-field input-group-container">
            <div className="form-control results-chosen select-field-trigger">
                <div className="option-selected">{selectedOption}</div>
                <a className="select-arrow-down-container">
                    <ClayIcon symbol="caret-double" />
                </a>
            </div>
        </div>
    )

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

        <ClayDropDownWithItems items={items} trigger={select} />
    </div>
    )
}

export default SelectDateType;

interface IProps {
    dateFieldOptions: IDateFieldOption[];
    type: Type;
    dateFieldName?: string;
    options: IOptions[];
    onChange: (value: string | number,dateFieldName?: string) => void;
    label: string;
    tooltip?: string;
}

interface IItem {
    items?: {
        onClick: () => void;
        label: string;
        name: string;
    }[];
    onClick?: () => void;
    name?: DateType;
    value?: DateType;
    label?: string;
    type?: 'group' | 'divider';
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

type DateType = 'customDate' | 'responseDate';