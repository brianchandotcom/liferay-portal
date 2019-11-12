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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';
import {useDrag} from 'react-dnd';

import addFragment from '../../../app/actions/addFragment';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {ConfigContext} from '../../../app/config/index';
import {DispatchContext} from '../../../app/reducers/index';
import {StoreContext} from '../../../app/store/index';

const ImagePreview = ({imagePreviewURL}) => {
	if (imagePreviewURL) {
		return (
			<div className="page-editor__fragments__fragment-card-preview">
				<img alt="thumbnail" src={imagePreviewURL} />
			</div>
		);
	}

	return (
		<div className="page-editor__fragments__fragment-card-no-preview">
			<ClayIcon symbol="picture" />
		</div>
	);
};

export default function FragmentCard({
	fragmentGroupId,
	fragmentKey,
	imagePreviewURL,
	name
}) {
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const store = useContext(StoreContext);

	const [, drag] = useDrag({
		end(_item, monitor) {
			const result = monitor.getDropResult();

			if (!result) {
				return;
			}

			const {parentId, position} = result;

			dispatch(
				addFragment({
					config,
					fragmentGroupId,
					fragmentKey,
					parentId,
					position,
					store
				})
			);
		},
		item: {
			type: LAYOUT_DATA_ITEM_TYPES.fragment
		}
	});

	return (
		<div
			className={classNames(
				'page-editor__fragments__fragment-card',
				'card',
				'card-interactive',
				'card-interactive-secondary',
				'selector-button',
				'overflow-hidden'
			)}
			ref={drag}
		>
			<ImagePreview imagePreviewURL={imagePreviewURL} />

			<div className="card-body">
				<div className="card-row">
					<div className="autofit-col autofit-col-expand autofit-row-center">
						<div className="card-title text-truncate" title={name}>
							{name}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}

FragmentCard.propTypes = {
	imagePreviewURL: PropTypes.string,
	name: PropTypes.string.isRequired
};
