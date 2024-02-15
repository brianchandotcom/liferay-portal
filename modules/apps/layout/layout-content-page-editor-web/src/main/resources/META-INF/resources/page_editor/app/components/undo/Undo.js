/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

import {useDispatch, useSelector} from '../../contexts/StoreContext';
import redo from '../../thunks/redo';
import undo from '../../thunks/undo';
import UndoHistory from './UndoHistory';

export function useUndoRedo() {
	const dispatch = useDispatch();
	const store = useSelector((state) => state);

	const onUndo = () => {
		dispatch(undo({store}));
	};

	const onRedo = () => {
		dispatch(redo({store}));
	};

	return {onRedo, onUndo};
}

export function useDisabledUndo() {
	const undoHistory = useSelector((state) => state.undoHistory);

	return !undoHistory || !undoHistory.length;
}

export function useDisabledRedo() {
	const redoHistory = useSelector((state) => state.redoHistory);

	return !redoHistory || !redoHistory.length;
}

export default function Undo() {
	const disabledRedo = useDisabledRedo();
	const disabledUndo = useDisabledUndo();
	const {onRedo, onUndo} = useUndoRedo();

	return (
		<>
			<ClayButton.Group className="flex-nowrap">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('undo')}
					className="btn-monospaced"
					disabled={disabledUndo}
					displayType="secondary"
					onClick={onUndo}
					size="sm"
					symbol="undo"
					title={Liferay.Language.get('undo')}
				/>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('redo')}
					className="btn-monospaced"
					disabled={disabledRedo}
					displayType="secondary"
					onClick={onRedo}
					size="sm"
					symbol="redo"
					title={Liferay.Language.get('redo')}
				/>
			</ClayButton.Group>

			<span className="d-none d-sm-block">
				<UndoHistory />
			</span>
		</>
	);
}

Undo.propTypes = {
	onRedo: PropTypes.func,
	onUndo: PropTypes.func,
};
