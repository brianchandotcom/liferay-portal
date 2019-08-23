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

import React, {useState, useRef, useContext, useEffect} from 'react';
import Button from '../../components/button/Button.es';
import Sidebar from '../../components/sidebar/Sidebar.es';
import {addItem, getItem, updateItem} from '../../utils/client.es';
import UpperToolbar, {
	UpperToolbarItem
} from '../../components/upper-toolbar/UpperToolbar.es';
import useSidebarContent from '../../hooks/useSidebarContent.es';
import FieldTypeList from '../../components/field-types/FieldTypeList.es';
import {AppContext} from '../../AppContext.es';

export default ({
	dataDefinitionId,
	dataLayoutBuilder,
	dataLayoutId,
	isNewCustomObject
}) => {
	const fieldTypes = dataLayoutBuilder.getFieldTypes();

	const [state, setState] = useState({
		dataLayout: null
	});

	const onInput = event => {
		const name = event.target.value;

		setState(prevState => ({
			...prevState,
			dataLayout: {
				...prevState.dataLayout,
				name: {
					en_US: name
				}
			}
		}));
	};

	const validate = () => {
		const {dataLayout} = state;

		if (!dataLayout) {
			return null;
		}

		const name = dataLayout.name.en_US.trim();

		if (name === '') {
			return null;
		}

		return {
			...dataLayout,
			name: {
				en_US: name
			},
			paginationMode: 'wizard'
		};
	};

	const {basePortletURL} = useContext(AppContext);
	const listUrl = `${basePortletURL}/#/custom-object/${dataDefinitionId}/form-views`;

	const onSave = () => {
		const dataLayout = validate();

		if (dataLayout === null) {
			return;
		}

		if (dataLayoutId) {
			updateItem(
				`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`,
				dataLayout
			).then(() => Liferay.Util.navigate(listUrl));
		} else {
			addItem(
				`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-layouts`,
				dataLayout
			).then(() => Liferay.Util.navigate(listUrl));
		}
	};

	const [isSidebarClosed, setSidebarClosed] = useState(false);

	const handleSidebarToggle = closed => setSidebarClosed(closed);

	const {componentId} = dataLayoutBuilder.props;
	const builderElementRef = useRef(
		document.querySelector(`#${componentId}container`)
	);

	useSidebarContent(builderElementRef, isSidebarClosed);

	const handleCancel = () => {
		if (isNewCustomObject) {
			Liferay.Util.navigate(basePortletURL);
		} else {
			Liferay.Util.navigate(listUrl);
		}
	};

	useEffect(() => {
		if (dataLayoutId) {
			getItem(`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`).then(
				dataLayout => setState({dataLayout})
			);
		}
	}, [dataDefinitionId, dataLayoutId]);

	const {dataLayout} = state;
	const {name: {en_US: dataLayoutName = ''} = {}} = dataLayout || {};

	const onKeyDown = event => {
		if (event.keyCode === 13) {
			event.preventDefault();

			event.target.blur();
		}
	};

	const submitDisabled = dataLayoutName.trim() === '';

	const [keywords, setKeywords] = useState('');

	return (
		<div className="app-builder-form-view">
			<UpperToolbar>
				<UpperToolbarItem expand={true}>
					<div className="input-group">
						<div className="input-group-item">
							<input
								aria-label={Liferay.Language.get(
									'untitled-form-view'
								)}
								className="form-control form-control-inline"
								onInput={onInput}
								onKeyDown={onKeyDown}
								placeholder={Liferay.Language.get(
									'untitled-form-view'
								)}
								type="text"
								value={dataLayoutName}
							/>
						</div>
					</div>
				</UpperToolbarItem>
				<UpperToolbarItem>
					<Button
						className="mr-3"
						displayType="secondary"
						onClick={handleCancel}
						small
					>
						{Liferay.Language.get('cancel')}
					</Button>
					<Button
						className="mr-3"
						disabled={submitDisabled}
						onClick={onSave}
						small
					>
						{Liferay.Language.get('save')}
					</Button>
				</UpperToolbarItem>
			</UpperToolbar>
			<Sidebar onSearch={setKeywords} onToggle={handleSidebarToggle}>
				<Sidebar.Body>
					<nav className="component-tbar tbar">
						<div className="container-fluid">
							<ul className="nav nav-underline" role="tablist">
								<li className="nav-item">
									<a
										className="active nav-link"
										href="javascript:;"
										role="tab"
									>
										{Liferay.Language.get('fields')}
									</a>
								</li>
							</ul>
						</div>
					</nav>
					<div className="tab-content">
						<div
							className="active fade mt-3 show tab-pane"
							role="tabpanel"
						>
							<FieldTypeList
								fieldTypes={fieldTypes}
								keywords={keywords}
							/>
						</div>
					</div>
				</Sidebar.Body>
			</Sidebar>
		</div>
	);
};
