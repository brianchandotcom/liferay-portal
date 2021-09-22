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

import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import React, {useState} from 'react';

import CustomSelectComponent from '../custom-select/CustomSelect';
import ErrorFeedback from './ErrorFeedback';
import FeedbackMessage from './FeedbackMessage';
import RequiredMask from './RequiredMask';

interface ICustomSelectProps extends React.HTMLAttributes<HTMLElement> {
	children: (item: any) => React.ReactNode;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	label: string;
	options: any[];
	required?: boolean;
	value: string;
}

const CustomSelect: React.FC<ICustomSelectProps> = ({
	children,
	className,
	disabled = false,
	error,
	feedbackMessage,
	id,
	label,
	onChange,
	options,
	required,
	value,
	...otherProps
}) => {
	const [active, setActive] = useState<boolean>(false);

	return (
		<ClayForm.Group
			className={classNames(className, {
				'has-error': error,
			})}
		>
			<label className={classNames({disabled})} htmlFor={id}>
				{label}

				{required && <RequiredMask />}
			</label>

			<ClayDropDown
				{...otherProps}
				active={active}
				onActiveChange={(value) => setActive(value)}
				trigger={
					<CustomSelectComponent
						placeholder={Liferay.Language.get('choose-an-option')}
						value={value}
					/>
				}
			>
				<ClayDropDown.ItemList>
					{options?.map((option, index) => {
						return (
							<ClayDropDown.Item
								key={index}
								onClick={() => {
									setActive(false);
									onChange && onChange(option);
								}}
							>
								{children && children(option)}
							</ClayDropDown.Item>
						);
					})}
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{error && <ErrorFeedback error={error} />}

			{feedbackMessage && (
				<FeedbackMessage feedbackMessage={feedbackMessage} />
			)}
		</ClayForm.Group>
	);
};

export default CustomSelect;
