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

export default function CustomSelect<T extends IItem = IItem>({
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
}: IProps<T>) {
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
					active={active}
					alignElementRef={inputRef}
					closeOnClickOutside
					onSetActive={setActive}
				>
					<ClayDropDown.ItemList>
						{options?.map((option, index) => (
							<ClayDropDown.Item
								key={index}
								onClick={() => {
									setActive(false);
									onChange?.(option);
								}}
							>
								<div>{option.label}</div>

								<span className="text-small">
									{option.description}
								</span>
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>

			{error && <ErrorFeedback error={error} />}

			{feedbackMessage && (
				<FeedbackMessage feedbackMessage={feedbackMessage} />
			)}
		</ClayForm.Group>
	);
}

interface IItem {
	description: string;
	label: string;
}
interface IProps<T extends IItem = IItem> {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label: string;
	onChange?: (selected: T) => void;
	options: T[];
	required?: boolean;
	value?: string | number | string[];
}
