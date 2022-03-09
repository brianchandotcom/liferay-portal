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

import classNames from 'classnames';

import InputWarning from './InputWarning';

export type InputFileProps = {
	error?: string;
	id?: string;
	label?: string;
	name?: string;
	required?: boolean;
} & React.HTMLAttributes<HTMLInputElement>;

const InputFile: React.FC<InputFileProps> = ({
	error,
	label,
	name,
	id = name,
	required,
	...otherProps
}) => {
	return (
		<>
			{label && (
				<label
					className={classNames(
						'font-weight-normal mt-3 mx-0 text-paragraph',
						{required}
					)}
					htmlFor={id}
				>
					{label}
				</label>
			)}
			<input
				className="testray-input-file"
				id={id}
				name={name}
				type="file"
				{...otherProps}
			>
				{}
			</input>
			{error && <InputWarning>{error}</InputWarning>}
		</>
	);
};

export default InputFile;
