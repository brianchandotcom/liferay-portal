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

import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import ActionsInfo from './ActionsInfo';

const Actions = (props) => {
	const {selectedItem} = useContext(DiagramBuilderContext);

	const {actions} = selectedItem?.data;
	const [sections, setSections] = useState([{identifier: `${Date.now()}-0`}]);

	useEffect(() => {
		const sectionsData = [];

		if (actions) {
			for (let i = 0; i < actions.name.length; i++) {
				sectionsData.push({
					description: actions.description[i],
					executionType: actions.executionType[i],
					identifier: `${Date.now()}-${i}`,
					name: actions.name[i],
					priority: actions.priority[i],
					template: actions.script[i],
				});
			}

			setSections(sectionsData);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return sections.map(({identifier}, index) => {
		return (
			<ActionsInfo
				{...props}
				identifier={identifier}
				index={index}
				key={`section-${identifier}`}
				sectionsLength={sections?.length}
				setSections={setSections}
			/>
		);
	});
};
export default Actions;
