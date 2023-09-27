/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import BaseSourceCode from '../shared-components/BaseSourceCode';

const SourceCode = () => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const scriptSourceCode = selectedItem.data?.assignments?.script;

	const scriptLanguage = selectedItem.data?.assignments?.scriptLanguage;

	const updateSelectedItem = (editor) => {
		if (editor.getData().trim() !== '') {
			setSelectedItem((previousValue) => ({
				...previousValue,
				data: {
					...previousValue.data,
					assignments: {
						assignmentType: ['scriptedAssignment'],
						script: [editor.getData()],
						scriptLanguage,
					},
				},
			}));
		}
	};

	return (
		<BaseSourceCode
			scriptSourceCode={scriptSourceCode}
			updateSelectedItem={updateSelectedItem}
		/>
	);
};

export default SourceCode;
