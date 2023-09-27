/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useEffect} from 'react';

import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';

const AssetCreator = () => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);

	useEffect(() => {
		setSelectedItem((previousValue) => ({
			...previousValue,
			data: {
				...previousValue.data,
				assignments: {assignmentType: ['user']},
			},
		}));
	}, [setSelectedItem]);

	return null;
};

export default AssetCreator;
