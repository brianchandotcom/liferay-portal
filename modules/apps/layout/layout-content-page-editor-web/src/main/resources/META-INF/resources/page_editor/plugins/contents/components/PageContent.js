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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {openModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../app/config/constants/editableFragmentEntryProcessor';
import {ITEM_TYPES} from '../../../app/config/constants/itemTypes';
import {
	useHoverItem,
	useHoveredItemId,
} from '../../../app/contexts/ControlsContext';
import {useSelector} from '../../../app/contexts/StoreContext';

export default function PageContent(props) {
	const [active, setActive] = useState(false);
	const {editURL, permissionsURL, viewUsagesURL} = props.actions;
	const hoverItem = useHoverItem();
	const hoveredItemId = useHoveredItemId();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const [isHovered, setIsHovered] = useState(false);

	useEffect(() => {
		if (hoveredItemId) {
			const [fragmentEntryLinkId, ...editableId] = hoveredItemId.split(
				'-'
			);

			if (fragmentEntryLinks[fragmentEntryLinkId]) {
				const fragmentEntryLink =
					fragmentEntryLinks[fragmentEntryLinkId];

				const editableValue =
					fragmentEntryLink.editableValues[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR
					];

				const editable = editableValue[editableId.join('-')];

				if (editable) {
					setIsHovered(editable.classPK === props.classPK);
				}
			}
		}
		else {
			setIsHovered(false);
		}
	}, [fragmentEntryLinks, hoveredItemId, props.classPK]);

	const handleMouseOver = () => {
		setIsHovered(true);

		hoverItem(`${props.classNameId}-${props.classPK}`, {
			itemType: ITEM_TYPES.mappedContent,
		});
	};

	const handleMouseLeave = () => {
		setIsHovered(false);
		hoverItem(null);
	};

	return (
		<li
			className={classNames('page-editor__contents__page-content', {
				'page-editor__contents__page-content--mapped-item-hovered': isHovered,
			})}
			onMouseLeave={handleMouseLeave}
			onMouseOver={handleMouseOver}
		>
			<div className="d-flex pl-3 pr-2 py-3">
				<ClayLayout.ContentCol expand>
					<strong className="list-group-title text-truncate">
						{props.title}
					</strong>

					<span className="small text-secondary">{props.name}</span>

					<span className="small text-secondary">
						{props.usagesCount === 1
							? Liferay.Language.get('used-in-1-page')
							: Liferay.Util.sub(
									Liferay.Language.get('used-in-x-pages'),
									props.usagesCount
							  )}
					</span>

					<div>
						{props.status.hasApprovedVersion && (
							<ClayLabel displayType="success">
								{Liferay.Language.get('approved')}
							</ClayLabel>
						)}
						<ClayLabel displayType={props.status.style}>
							{props.status.label}
						</ClayLabel>
					</div>
				</ClayLayout.ContentCol>

				{(editURL || permissionsURL || viewUsagesURL) && (
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								className="btn-monospaced btn-sm text-secondary"
								displayType="unstyled"
							>
								<span className="sr-only">
									{Liferay.Language.get('open-actions-menu')}
								</span>
								<ClayIcon symbol="ellipsis-v" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{editURL && (
								<ClayDropDown.Item href={editURL} key="editURL">
									{Liferay.Language.get('edit')}
								</ClayDropDown.Item>
							)}

							{permissionsURL && (
								<ClayDropDown.Item
									key="permissionsURL"
									onClick={() => {
										openModal({
											title: Liferay.Language.get(
												'permissions'
											),
											url: permissionsURL,
										});
									}}
								>
									{Liferay.Language.get('permissions')}
								</ClayDropDown.Item>
							)}

							{viewUsagesURL && (
								<ClayDropDown.Item
									key="viewUsagesURL"
									onClick={() => {
										openModal({
											title: Liferay.Language.get(
												'view-usages'
											),
											url: viewUsagesURL,
										});
									}}
								>
									{Liferay.Language.get('view-usages')}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				)}
			</div>
		</li>
	);
}

PageContent.propTypes = {
	actions: PropTypes.object,
	name: PropTypes.string.isRequired,
	status: PropTypes.shape({
		hasApprovedVersion: PropTypes.bool,
		label: PropTypes.string,
		style: PropTypes.string,
	}),
	title: PropTypes.string.isRequired,
	usagesCount: PropTypes.number.isRequired,
};
