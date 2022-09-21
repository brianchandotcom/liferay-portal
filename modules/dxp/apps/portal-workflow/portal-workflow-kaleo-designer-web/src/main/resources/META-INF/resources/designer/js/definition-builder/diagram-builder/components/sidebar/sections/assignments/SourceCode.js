/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
