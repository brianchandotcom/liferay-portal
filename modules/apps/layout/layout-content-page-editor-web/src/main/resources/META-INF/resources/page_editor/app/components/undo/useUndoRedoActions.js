/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useDispatch, useSelector} from '../../contexts/StoreContext';
import redo from '../../thunks/redo';
import undo from '../../thunks/undo';

export default function useUndoRedo() {
	const dispatch = useDispatch();
	const store = useSelector((state) => state);

	const onUndo = ({selectItems} = {}) => {
		dispatch(undo({store}, selectItems));
	};

	const onRedo = ({selectItems} = {}) => {
		dispatch(redo({store}, selectItems));
	};

	return {onRedo, onUndo};
}
