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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import PropTypes from 'prop-types';
import React from 'react';

import {useSelector} from '../../store/index';
import UndoHistory from './UndoHistory';

export default function Undo({onRedo = () => {}, onUndo = () => {}}) {
	const undoHistory = useSelector((state) => state.undoHistory);
	const redoHistory = useSelector((state) => state.redoHistory);

	return (
		<>
			<ClayButton.Group>
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('undo')}
					className="btn-monospaced"
					disabled={!undoHistory || !undoHistory.length}
					displayType="secondary"
					onClick={onUndo}
					small
					symbol="undo"
					title={Liferay.Language.get('undo')}
				/>
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('redo')}
					className="btn-monospaced"
					disabled={!redoHistory || !redoHistory.length}
					displayType="secondary"
					onClick={onRedo}
					small
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
