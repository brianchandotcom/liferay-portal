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
	const {collectionConfig, collectionItem, collectionItemIndex} = useContext(
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
						className={classNames(
							Liferay.FeatureFlags['LPD-18221']
								? 'page-editor__collection-item'
								: 'page-editor__collection-item-old',
							{
								empty: !children.length,
							}
						)}
						ref={setRef}
					>
						<div
							className={classNames(
								'page-editor__collection-item__border',
								{
									'position-static':
										Liferay.FeatureFlags['LPD-18221'],
								}
							)}
						>
							<p
								className={classNames(
									'page-editor__collection-item__title',
									{
										'c-m-0 c-p-4':
											Liferay.FeatureFlags['LPD-18221'],
									}
								)}
							>
								{title ||
									Liferay.Language.get(
										'sample-collection-item'
									)}
							</p>

							{Liferay.FeatureFlags['LPD-18221'] &&
							collectionItemIndex === 0 ? (
								<div className="c-mb-4 c-mx-4 d-flex flex-column page-editor__no-fragments-state">
									<img
										className="c-mb-3 page-editor__no-fragments-state__image"
										src={`${config.imagesPath}/collection-item-empty-state.svg`}
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
