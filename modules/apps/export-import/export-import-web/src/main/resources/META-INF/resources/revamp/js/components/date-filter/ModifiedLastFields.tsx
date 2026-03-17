/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import FieldSelectWithOption from '../forms/FieldSelectWithOption';
import {FilterAction, ModifiedLastType} from './types';
import {MODIFIED_LAST_OPTIONS} from './utils';

type Props = {
	dispatch: React.Dispatch<FilterAction>;
	value: ModifiedLastType;
};

const ModifiedLastFields = ({dispatch, value}: Props) => (
	<ClayLayout.ContentCol>
		<FieldSelectWithOption
			id="modifiedLast"
			label={Liferay.Language.get('modified-last')}
			name="modifiedLast"
			onChange={(event) =>
				dispatch({
					payload: {
						modifiedLast: event.target.value as ModifiedLastType,
					},
					type: 'UPDATE_FILTER',
				})
			}
			options={MODIFIED_LAST_OPTIONS}
			value={value}
		/>
	</ClayLayout.ContentCol>
);

export default ModifiedLastFields;
