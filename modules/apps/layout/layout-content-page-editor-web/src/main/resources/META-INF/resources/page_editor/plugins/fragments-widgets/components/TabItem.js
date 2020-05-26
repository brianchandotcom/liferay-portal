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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useSelectItem} from '../../../app/components/Controls';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import selectSegmentsExperienceId from '../../../app/selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../../app/store/index';
import addFragment from '../../../app/thunks/addFragment';
import addItem from '../../../app/thunks/addItem';
import addWidget from '../../../app/thunks/addWidget';
import {useDragSymbol} from '../../../app/utils/useDragAndDrop';

export default function TabItem({item}) {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectItem = useSelectItem();
	const [showPreview, setShowPreview] = useState(false);

	const {sourceRef} = useDragSymbol(
		{
			icon: item.icon,
			label: item.label,
			type: item.type,
		},
		(parentId, position) => {
			let thunk;

			if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
				if (item.data.portletId) {
					thunk = addWidget;
				}
				else {
					thunk = addFragment;
				}
			}
			else {
				thunk = addItem;
			}

			dispatch(
				thunk({
					...item.data,
					parentItemId: parentId,
					position,
					selectItem,
					store: {segmentsExperienceId},
				})
			);
		}
	);

	return (
		<li
			className={classNames('page-editor__fragments-widgets__tab-item', {
				disabled: item.disabled,
			})}
			ref={item.disabled ? null : sourceRef}
		>
			<div className="page-editor__fragments-widgets__tab-item-body">
				<ClayIcon className="mr-3" symbol={item.icon} />
				<div className="text-truncate title">{item.label}</div>
			</div>

			{item.preview && (
				<div className="page-editor__fragments-widgets__tab-item-preview">
					<ClayButton
						className="btn-monospaced preview-icon"
						displayType="unstyled"
						onBlur={() => setShowPreview(false)}
						onFocus={() => setShowPreview(true)}
						onMouseLeave={() => setShowPreview(false)}
						onMouseOver={() => setShowPreview(true)}
						small
					>
						<ClayIcon symbol="info-circle-open" />
						<span className="sr-only">{name}</span>
					</ClayButton>
					{showPreview && (
						<ClayPopover disableScroll>
							<img alt="thumbnail" src={item.preview} />
						</ClayPopover>
					)}
				</div>
			)}
		</li>
	);
}

TabItem.propTypes = {
	item: PropTypes.shape({
		data: PropTypes.object.isRequired,
		icon: PropTypes.string.isRequired,
		label: PropTypes.string.isRequired,
		preview: PropTypes.string,
		type: PropTypes.string.isRequired,
	}).isRequired,
};
