/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useContext} from 'react';

import useSetRef from '../../../common/hooks/useSetRef';
import {CollectionItemContext} from '../../contexts/CollectionItemContext';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import TopperEmpty from '../topper/TopperEmpty';

const CollectionItemWithControls = React.forwardRef(({children, item}, ref) => {
	const {collectionConfig, collectionItem} = useContext(
		CollectionItemContext
	);
	const title =
		collectionItem.title ||
		collectionItem.name ||
		collectionItem.defaultTitle ||
		collectionConfig?.collection?.title;

	const [setRef, itemElement] = useSetRef(ref);

	return (
		<div
			className={classNames('page-editor__collection__block', {
				'empty': !title,
				'flex-grow-1': !children.length,
			})}
		>
			<TopperEmpty
				activable={false}
				className={getLayoutDataItemTopperUniqueClassName(item.itemId)}
				item={item}
				itemElement={itemElement}
			>
				{React.Children.count(children) === 0 ? (
					<div
						className={classNames('page-editor__collection-item', {
							empty: !children.length,
						})}
						ref={setRef}
					>
						<div className="page-editor__collection-item__border">
							<p className="page-editor__collection-item__title">
								{title ||
									Liferay.Language.get(
										'sample-collection-item'
									)}
							</p>
						</div>
					</div>
				) : (
					<div ref={setRef}>{children}</div>
				)}
			</TopperEmpty>
		</div>
	);
});

export default CollectionItemWithControls;
