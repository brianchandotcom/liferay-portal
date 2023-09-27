/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import Item from './Item';
import {useInputSets} from './useInputSets';

function InputSets({children}) {
	return (
		<div className="input-sets-root">
			<DndProvider backend={HTML5Backend}>{children}</DndProvider>
		</div>
	);
}

InputSets.Item = Item;

export {useInputSets};
export default InputSets;
