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

import CreatableSelect from 'react-select/creatable';
import i18n from '~/i18n';

import InputWarning from '../../Form/Base/BaseWarning';

type MultiSelectCreateableProps = {
	errors: any;
	name: string;
	setValue: (values: any) => void;
	values: any;
};

const MultiSelectCreateable: React.FC<MultiSelectCreateableProps> = ({
	errors,
	name,
	setValue,
	values,
}) => {
	return (
		<>
			<CreatableSelect
				isMulti
				name={name}
				onChange={setValue}
				value={values}
			/>

			{errors?.issues && (
				<InputWarning>
					{i18n.sub(
						'the-issue-x-does-not-exists',
						errors?.issues?.message
					)}
				</InputWarning>
			)}
		</>
	);
};

export default MultiSelectCreateable;
