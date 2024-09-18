/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import useSetRef from '../../../common/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {config} from '../../config';
import {useSelector, useSelectorCallback} from '../../contexts/StoreContext';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import isItemEmpty from '../../utils/isItemEmpty';
import TopperEmpty from '../topper/TopperEmpty';
import getParentHeight from './getParentHeight';

const Root = React.forwardRef(({children, item}, ref) => {
	const isEmpty = useSelectorCallback(
		(state) =>
			isItemEmpty(item, state.layoutData, state.selectedViewportSize),
		[item]
	);

	const [setRef, itemElement] = useSetRef(ref);

	const layoutData = useSelector((state) => state.layoutData);

	return (
		<TopperEmpty
			className={getLayoutDataItemTopperUniqueClassName(item.itemId)}
			item={item}
			itemElement={itemElement}
		>
			<div className="page-editor__root" ref={setRef}>
				{isEmpty && (
					<div
						className="d-flex flex-column page-editor__no-fragments-state"
						style={{height: getParentHeight(item, layoutData)}}
					>
						<img
							className="page-editor__no-fragments-state__image"
							src={`${config.imagesPath}/drag_and_drop.svg`}
						/>

						<p className="page-editor__no-fragments-state__message">
							{Liferay.Language.get(
								'drag-and-drop-fragments-or-widgets-here'
							)}
						</p>
					</div>
				)}

				{children}
			</div>
		</TopperEmpty>
	);
});

Root.displayName = 'Root';

Root.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default Root;
