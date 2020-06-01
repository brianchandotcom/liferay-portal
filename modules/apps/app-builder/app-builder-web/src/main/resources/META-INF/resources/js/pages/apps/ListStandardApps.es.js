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

import ClayPopover from '@clayui/popover';
import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import isClickOutside from '../../utils/clickOutside.es';
import ListApps from './ListApps.es';
import NewAppPopover from './NewAppPopover.es';
import {COLUMNS, FILTERS} from './constants.es';

export default ({scope, ...restProps}) => {
	const {userId} = useContext(AppContext);
	const popoverRef = useRef();

	const [alignElement, setAlignElement] = useState();
	const [isPopoverVisible, setPopoverVisible] = useState(false);
	const [showTooltip, setShowTooltip] = useState(false);

	const onClickAddButton = (event) => {
		event.stopPropagation();
		setAlignElement(event.target);
		setPopoverVisible(!isPopoverVisible);
		setShowTooltip(false);
	};

	const updateAlignElement = (element) => {
		if (!isPopoverVisible) {
			setAlignElement(element);
		}
	};

	const onHoverAddButton = ({target}) => {
		setShowTooltip(true);
		updateAlignElement(target);
	};

	useEffect(() => {
		const clickHandler = ({target}) => {
			if (isClickOutside(target, popoverRef.current)) {
				setPopoverVisible(false);
			}
		};

		window.addEventListener('click', clickHandler);

		return () => window.removeEventListener('click', clickHandler);
	}, [popoverRef]);

	const [firstColumn, ...otherColumns] = COLUMNS;

	const columns = [
		firstColumn,
		{
			key: 'dataDefinitionName',
			value: Liferay.Language.get('object'),
		},
		...otherColumns,
	];

	const emptyState = {
		button: () => (
			<Button
				displayType="secondary"
				onClick={onClickAddButton}
				onMouseOver={({target}) => updateAlignElement(target)}
			>
				{Liferay.Language.get('create-new-app')}
			</Button>
		),
		description: Liferay.Language.get(
			'create-a-standard-app-to-collect-and-manage-an-objects-data'
		),
		title: Liferay.Language.get('there-are-no-apps-yet'),
	};

	const filters = [
		...FILTERS,
		{
			items: [
				{
					label: Liferay.Language.get('me'),
					value: userId,
				},
			],
			key: 'userIds',
			multiple: true,
			name: 'author',
		},
	];

	return (
		<>
			<ListApps
				listViewProps={{
					addButton: () => (
						<ClayPopover
							alignPosition="bottom-right"
							header={Liferay.Language.get('standard-app')}
							show={showTooltip && !isPopoverVisible}
							trigger={
								<Button
									className="nav-btn nav-btn-monospaced"
									onClick={onClickAddButton}
									onMouseOut={() => setShowTooltip(false)}
									onMouseOver={onHoverAddButton}
									symbol="plus"
								/>
							}
						>
							{Liferay.Language.get(
								'create-an-app-to-collect-and-manage-an-objects-data'
							)}
						</ClayPopover>
					),
					columns,
					emptyState,
					endpoint: `/o/app-builder/v1.0/apps?scope=${scope}`,
					filters,
				}}
				{...restProps}
			/>

			<NewAppPopover
				alignElement={alignElement}
				onCancel={() => setPopoverVisible(false)}
				ref={popoverRef}
				setVisible={setPopoverVisible}
				visible={isPopoverVisible}
				{...restProps}
			/>
		</>
	);
};
