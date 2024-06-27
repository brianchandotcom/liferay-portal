/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useMemo} from 'react';

import ViewsContext from '../../ViewsContext';
import Cell from './Cell';
import TableContext from './TableContext';

function Row({children, className, paddingLeftCells}) {
	const {columnNames, isFixed} = useContext(TableContext);
	const [{modifiedFields}] = useContext(ViewsContext);

	const marginLeft = useMemo(() => {
		let margin = 0;

		if (isFixed) {
			for (let i = 0; i < paddingLeftCells; i++) {
				margin += modifiedFields[columnNames[i]].width;
			}
		}

		return margin;
	}, [columnNames, isFixed, modifiedFields, paddingLeftCells]);

	const style = marginLeft
		? {
				marginLeft,
				minWidth: `calc(100% - ${marginLeft}px)`,
			}
		: {};

	const placeholderPaddingCells = [];

	if (!isFixed) {
		for (let i = 0; i < paddingLeftCells; i++) {
			placeholderPaddingCells.push(<Cell key={i} />);
		}
	}

	return (
		<div className={classNames('dnd-tr', className)} style={style}>
			{placeholderPaddingCells}

			{children}
		</div>
	);
}

Row.defaultProps = {
	paddingLeftCells: 0,
};

Row.propTypes = {
	paddingLeftCells: PropTypes.number,
};

export default Row;
