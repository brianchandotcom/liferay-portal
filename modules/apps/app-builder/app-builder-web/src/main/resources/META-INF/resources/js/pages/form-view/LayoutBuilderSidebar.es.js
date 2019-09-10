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

import React, {useState, useRef, useEffect} from 'react';
import {useSidebarContent} from '../../hooks/index.es';
import FieldTypeList from '../../components/field-types/FieldTypeList.es';
import Sidebar from '../../components/sidebar/Sidebar.es';
import isClickOutside from '../../utils/clickOutside.es';
import renderSettingsForm from './renderSettingsForm.es';

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
			if (isClickOutside(target, sidebarRef.current)) {
				dataLayoutBuilder.dispatch('sidebarFieldBlurred');
			}
		};

		window.addEventListener('click', eventHandler, true);

		return () => window.removeEventListener('click', eventHandler);
	}, [dataLayoutBuilder, sidebarRef]);

	return (
		<Sidebar
			onSearch={setKeywords}
			onToggle={closed => setSidebarClosed(closed)}
			ref={sidebarRef}
		>
			<Sidebar.Body>
				{Object.keys(focusedField).length > 0 ? (
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
		</Sidebar>
	);
};
