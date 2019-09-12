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

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/util/visitors.es';
import React, {useEffect, useRef, useState} from 'react';
import renderSettingsForm from './renderSettingsForm.es';
import {useSidebarContent} from '../../hooks/index.es';
import FieldTypeList from '../../components/field-types/FieldTypeList.es';
import Sidebar from '../../components/sidebar/Sidebar.es';
import Button from '../../components/button/Button.es';
import isClickOutside from '../../utils/clickOutside.es';

const findFieldType = dataLayoutBuilder => {
	const store = dataLayoutBuilder.getStore();
	const {settingsContext} = store.focusedField;
	const visitor = new PagesVisitor(settingsContext.pages);
	const typeField = visitor.findField(field => field.fieldName === 'type');

	return dataLayoutBuilder.getFieldTypes().find(({name}) => {
		return name === typeField.value;
	});
};

const DefaultSidebarBody = ({dataLayoutBuilder, keywords}) => {
	return (
		<>
			<Sidebar.Tab tabs={[Liferay.Language.get('fields')]} />

			<Sidebar.TabContent>
				<FieldTypeList
					fieldTypes={dataLayoutBuilder.getFieldTypes()}
					keywords={keywords}
				/>
			</Sidebar.TabContent>
		</>
	);
};

const SettingsSidebarBody = ({dataLayoutBuilder, focusedField}) => {
	const {settingsContext} = focusedField;
	const formRef = useRef();
	const [form, setForm] = useState(null);

	useEffect(() => {
		if (form === null) {
			setForm(
				renderSettingsForm(
					{dataLayoutBuilder, settingsContext},
					formRef.current
				)
			);
		}
	}, [dataLayoutBuilder, form, formRef, settingsContext]);

	useEffect(() => {
		return () => form && form.dispose();
	}, [form]);

	return <div ref={formRef}></div>;
};

const SettingsSidebarHeader = ({dataLayoutBuilder}) => {
	const handleFocusedFieldBlur = () => {
		dataLayoutBuilder.dispatch('sidebarFieldBlurred');
	};

	const fieldType = findFieldType(dataLayoutBuilder);

	return (
		<Sidebar.Header className="d-flex">
			<Button
				className="mr-2"
				displayType="secondary"
				monospaced={false}
				onClick={handleFocusedFieldBlur}
				symbol="angle-left"
			/>

			<ClayDropDown
				className="d-inline-flex flex-grow-1"
				onActiveChange={() => {}}
				trigger={
					<Button
						className="d-inline-flex flex-grow-1"
						disabled={true}
						displayType="secondary"
					>
						<ClayIcon
							className="mr-2 mt-1"
							symbol={fieldType.icon}
						/>

						{fieldType.label}

						<span className="d-inline-flex ml-auto navbar-breakpoint-down-d-none pt-2">
							<ClayIcon
								className="inline-item inline-item-after"
								symbol="caret-bottom"
							/>
						</span>
					</Button>
				}
			></ClayDropDown>
		</Sidebar.Header>
	);
};

export default ({dataLayoutBuilder, dataLayoutBuilderElementId}) => {
	const store = dataLayoutBuilder.getStore();

	const [focusedField, setFocusedField] = useState(store.focusedField);
	const [keywords, setKeywords] = useState('');
	const [sidebarClosed, setSidebarClosed] = useState(false);

	const builderElementRef = useRef(
		document.querySelector(`#${dataLayoutBuilderElementId}`)
	);

	useSidebarContent(builderElementRef, sidebarClosed);

	useEffect(() => {
		const provider = dataLayoutBuilder.getProvider();

		const eventHandler = provider.on('focusedFieldChanged', ({newVal}) => {
			setFocusedField(newVal);
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, setFocusedField]);

	const sidebarRef = useRef();

	useEffect(() => {
		const eventHandler = ({target}) => {
			if (isClickOutside(target, sidebarRef.current, '.dropdown-menu')) {
				dataLayoutBuilder.dispatch('sidebarFieldBlurred');
			}
		};

		window.addEventListener('click', eventHandler, true);

		return () => window.removeEventListener('click', eventHandler);
	}, [dataLayoutBuilder, sidebarRef]);

	const hasFocusedField = Object.keys(focusedField).length > 0;

	return (
		<Sidebar
			closeable={!hasFocusedField}
			onSearch={hasFocusedField ? false : setKeywords}
			onToggle={closed => setSidebarClosed(closed)}
			ref={sidebarRef}
		>
			<>
				{hasFocusedField && (
					<SettingsSidebarHeader
						dataLayoutBuilder={dataLayoutBuilder}
						focusedField={focusedField}
					/>
				)}

				<Sidebar.Body>
					{hasFocusedField ? (
						<SettingsSidebarBody
							dataLayoutBuilder={dataLayoutBuilder}
							focusedField={focusedField}
						/>
					) : (
						<DefaultSidebarBody
							dataLayoutBuilder={dataLayoutBuilder}
							keywords={keywords}
						/>
					)}
				</Sidebar.Body>
			</>
		</Sidebar>
	);
};
