/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
