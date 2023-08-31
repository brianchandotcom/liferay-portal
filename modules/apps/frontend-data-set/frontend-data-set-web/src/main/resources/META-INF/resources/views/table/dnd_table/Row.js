/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
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

	const Container = Liferay.FeatureFlags['LPS-193005']
		? ClayTable.Row
		: 'div';

	return (
		<Container
			className={classNames(
				{
					'dnd-tr': !Liferay.FeatureFlags['LPS-193005'],
				},
				className
			)}
			style={style}
		>
			{placeholderPaddingCells}

			{children}
		</Container>
	);
}

Row.defaultProps = {
	paddingLeftCells: 0,
};

Row.propTypes = {
	paddingLeftCells: PropTypes.number,
};

export default Row;
