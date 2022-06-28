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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {SelectLayoutTree} from './SelectLayoutTree.es';

/**
 * SelectLayout
 *
 * This component shows a list of available layouts to select in expanded tree
 * and allows to filter them by searching.
 *
 * @review
 */

const SelectLayout = ({
	followURLOnTitleClick,
	itemSelectorSaveEvent,
	multiSelection,
	nodes,
	selectedLayoutIds,
}) => {
	const [filter, setFilter] = useState();

	const empty = !nodes.length;

	return (
		<ClayLayout.ContainerFluid className="p-4 select-layout">
			<ClayForm.Group>
				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							className="input-group-inset input-group-inset-after"
							disabled={empty}
							onChange={(event) => setFilter(event.target.value)}
							placeholder={`${Liferay.Language.get('search')}`}
							type="text"
						/>

						<ClayInput.GroupInsetItem after>
							<div className="link-monospaced">
								<ClayIcon symbol="search" />
							</div>
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			{empty ? (
				<EmptyState />
			) : (
				<SelectLayoutTree
					filter={filter}
					followURLOnTitleClick={followURLOnTitleClick}
					itemSelectorSaveEvent={itemSelectorSaveEvent}
					items={nodes}
					multiSelection={multiSelection}
					selectedLayoutIds={selectedLayoutIds}
				/>
			)}
		</ClayLayout.ContainerFluid>
	);
};

const EmptyState = () => {
	return (
		<div className="sheet taglib-empty-result-message">
			<div className="taglib-empty-result-message-header"></div>

			<div className="sheet-text text-center">
				{Liferay.Language.get('there-are-no-pages')}
			</div>
		</div>
	);
};

SelectLayout.propTypes = {
	followURLOnTitleClick: PropTypes.bool,
	itemSelectorSaveEvent: PropTypes.string,
	multiSelection: PropTypes.bool,
	namespace: PropTypes.string,
	nodes: PropTypes.array.isRequired,
};

export default SelectLayout;
