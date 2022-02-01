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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import React, {useRef, useState} from 'react';

import './CustomSelect.scss';
import ErrorFeedback from '../ErrorFeedback';
import FeedbackMessage from '../FeedbackMessage';
import RequiredMask from '../RequiredMask';

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
	const inputRef = useRef(null);

	return (
		<ClayForm.Group
			className={classNames('object__custom-select', className, {
				'has-error': error,
			})}
		>
			<label className={classNames({disabled})} htmlFor={id}>
				{label}

				{required && <RequiredMask />}
			</label>

			<ClayAutocomplete>
				<ClayAutocomplete.Input
					className="object__custom-select-input"
					onClick={() => setActive(!active)}
					placeholder={Liferay.Language.get('choose-an-option')}
					ref={inputRef}
					value={value}
				/>

				<ClayAutocomplete.DropDown
					{...otherProps}
					active={active}
					alignElementRef={inputRef}
					closeOnClickOutside
					onSetActive={setActive}
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
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>

			{error && <ErrorFeedback error={error} />}

			{feedbackMessage && (
				<FeedbackMessage feedbackMessage={feedbackMessage} />
			)}
		</ClayForm.Group>
	);
};

export default CustomSelect;
