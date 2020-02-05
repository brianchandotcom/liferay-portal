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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {Treeview} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function visit(nodes, callback) {
	nodes.forEach(node => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
}

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
	namespace,
	nodes
}) => {
	const [filterQuery, setFilterQuery] = useState();

	const handleSelectionChange = selectedNodeIds => {
		if (!selectedNodeIds.size) {
			return;
		}

		let data = [];

		visit(nodes, node => {
			if (selectedNodeIds.has(node.id)) {
				data.push({
					groupId: node.groupId,
					id: node.id,
					layoutId: node.layoutId,
					name: node.value,
					privateLayout: node.privateLayout,
					value: node.url
				});
			}
		});

		if (!multiSelection) {
			data = data[0];
		}

		if (followURLOnTitleClick) {
			Liferay.Util.getOpener().document.location.href = data.url;
		}
		else {
			Liferay.fire(itemSelectorSaveEvent, {
				data
			});

			Liferay.Util.getOpener().Liferay.fire(itemSelectorSaveEvent, {
				data
			});
		}
	};

	return (
		<div className="select-layout">
			<ClayManagementToolbar>
				<ClayManagementToolbar.Search
					onSubmit={event => {
						event.preventDefault();
					}}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								className="form-control input-group-inset input-group-inset-after"
								name={`${namespace}filterKeywords`}
								onInput={event => {
									setFilterQuery(
										event.target.value.toLowerCase()
									);
								}}
								placeholder={Liferay.Language.get('search-for')}
								type="text"
							/>
							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									className="navbar-breakpoint-d-none"
									displayType="unstyled"
									symbol="times"
								/>
								<ClayButtonWithIcon
									className="navbar-breakpoint-d-block"
									displayType="unstyled"
									symbol="search"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayManagementToolbar.Search>
			</ClayManagementToolbar>

			<div
				className="container-fluid-1280 layouts-selector"
				id={`${namespace}selectLayoutFm`}
			>
				<fieldset className="panel-body">
					<div
						className="layout-tree"
						id={`${namespace}layoutContainer`}
					>
						<Treeview
							filterQuery={filterQuery}
							multiSelection={multiSelection}
							NodeComponent={Treeview.Card}
							nodes={nodes}
							onSelectedNodesChange={handleSelectionChange}
						/>
					</div>
				</fieldset>
			</div>
		</div>
	);
};

SelectLayout.propTypes = {
	followURLOnTitleClick: PropTypes.bool,
	itemSelectorSaveEvent: PropTypes.string,
	multiSelection: PropTypes.bool,
	namespace: PropTypes.string,
	nodes: PropTypes.array.isRequired
};

export default function(props) {
	return <SelectLayout {...props} />;
}
