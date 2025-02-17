/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {useContext} from 'react';

import useSetRef from '../../../common/hooks/useSetRef';
import {config} from '../../config';
import {CollectionItemContext} from '../../contexts/CollectionItemContext';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import TopperEmpty from '../topper/TopperEmpty';

const CollectionItemWithControls = React.forwardRef(({children, item}, ref) => {
	const {collectionConfig, collectionItem, collectionItemIndex, isDisabled} =
		useContext(CollectionItemContext);
	const title =
		collectionItem.title ||
		collectionItem.name ||
		collectionItem.defaultTitle ||
		collectionConfig?.collection?.title;

	const [setRef, itemElement] = useSetRef(ref);

	return (
		<div
			className={classNames('page-editor__collection__block', {
				'disabled': isDisabled,
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
						<div className="page-editor__collection-item__border position-static">
							<p className="c-m-0 c-p-4 page-editor__collection-item__title">
								{title ||
									Liferay.Language.get(
										'sample-collection-item'
									)}
							</p>

							{collectionItemIndex === 0 ? (
								<div className="c-mb-4 c-mx-4 d-flex flex-column page-editor__no-fragments-state">
									<img
										className="c-mb-3 page-editor__no-fragments-state__image"
										src={`${config.imagesPath}/collection_item_empty_state.svg`}
									/>

									<p className="d-flex flex-column page-editor__no-fragments-state__message">
										<span>
											{Liferay.Language.get(
												'drag-and-drop-fragments-or-widgets-here'
											)}
										</span>

										<span>
											{Liferay.Language.get(
												'all-components-placed-here-will-be-dynamically-replicated-across-the-collection'
											)}
										</span>
									</p>
								</div>
							) : null}
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
