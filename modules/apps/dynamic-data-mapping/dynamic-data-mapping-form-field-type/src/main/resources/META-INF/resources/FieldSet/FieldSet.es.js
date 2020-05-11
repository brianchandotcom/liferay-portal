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

import './FieldSet.scss';

import {getRepeatedIndex} from 'dynamic-data-mapping-form-renderer';
import React, {useEffect, useMemo, useRef} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import PageRendererRows from './PageRendererRows.es';
import Panel from './Panel.es';

class NoRender extends React.Component {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		const {forwardRef, ...otherProps} = this.props;

		return <div ref={forwardRef} {...otherProps} />;
	}
}

// This is a adapter to maintain compatibility with the previous FieldSet,
// being able to call page renderer rows. This should probably be removed
// by a more friendly implementation when we remove the implementation of
// calling the fields dynamically through soy.

const PageRendererAdapter = ({
	activePage,
	context,
	editable,
	onBlur,
	onChange,
	onFocus,
	pageIndex,
	rows = [],
	spritemap,
}) => {
	const component = useRef(null);
	const container = useRef(null);

	useEffect(() => {
		if (!component.current && container.current) {
			component.current = new PageRendererRows(
				{
					activePage,
					editable,
					events: {
						fieldBlurred: onBlur,
						fieldEdited: onChange,
						fieldFocused: onFocus,
					},
					pageIndex,
					parentContext: context,
					rows,
					spritemap,
				},
				container.current
			);
		}

		return () => {
			if (component.current) {
				component.current.dispose();
			}
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (component.current) {
			component.current.setState({rows});
		}
	}, [rows]);

	return <NoRender forwardRef={container} />;
};

const FieldSet = ({
	activePage,
	collapsible,
	context,
	editable,
	label,
	onBlur,
	onChange,
	onFocus,
	onRemoveButton,
	onRepeatButton,
	pageIndex,
	readOnly,
	repeatable,
	rows,
	showLabel,
	showRepeatableRemoveButton,
	spritemap,
}) => (
	<>
		{showLabel && !collapsible && (
			<>
				<label className="text-uppercase">{label}</label>
				<div className="ddm-field-types-fieldset__nested-separator">
					<div className="mt-1 separator" />
				</div>
			</>
		)}

		{collapsible ? (
			<Panel
				onRemoveButton={onRemoveButton}
				onRepeatButton={onRepeatButton}
				readOnly={readOnly}
				repeatable={repeatable}
				showRepeatableRemoveButton={showRepeatableRemoveButton}
				spritemap={spritemap}
				title={label}
			>
				<PageRendererAdapter
					activePage={activePage}
					context={context}
					editable={editable}
					onBlur={onBlur}
					onChange={onChange}
					onFocus={onFocus}
					pageIndex={pageIndex}
					rows={rows}
					spritemap={spritemap}
				/>
			</Panel>
		) : (
			<PageRendererAdapter
				activePage={activePage}
				context={context}
				editable={editable}
				onBlur={onBlur}
				onChange={onChange}
				onFocus={onFocus}
				pageIndex={pageIndex}
				rows={rows}
				spritemap={spritemap}
			/>
		)}
	</>
);

const getRowsArray = (rows) => {
	if (typeof rows === 'string') {
		try {
			return JSON.parse(rows);
		}
		catch (e) {
			return [];
		}
	}

	return rows;
};

const getRows = (rows, nestedFields) => {
	const normalizedRows = getRowsArray(rows);

	return normalizedRows.map((row) => ({
		...row,
		columns: row.columns.map((column) => ({
			...column,
			fields: column.fields.map((fieldName) => {
				return nestedFields.find(
					(nestedField) => nestedField.fieldName === fieldName
				);
			}),
		})),
	}));
};

const FieldSetProxy = connectStore(
	({
		activePage,
		collapsible,
		context,
		dispatch,
		editable,
		label,
		name,
		nestedFields = [],
		pageIndex,
		propagate,
		readOnly,
		repeatable,
		rows,
		showLabel,
		spritemap,
		...otherProps
	}) => {
		const repeatedIndex = useMemo(() => getRepeatedIndex(name), [name]);

		return (
			<FieldBaseProxy
				{...otherProps}
				dispatch={dispatch}
				name={name}
				readOnly={readOnly}
				repeatable={collapsible ? false : repeatable}
				showLabel={false}
				spritemap={spritemap}
			>
				<div className="ddm-field-types-fieldset__nested">
					<FieldSet
						activePage={activePage}
						collapsible={collapsible}
						context={context}
						editable={editable}
						label={label}
						onBlur={(event) => propagate('fieldBlurred', event)}
						onChange={(event) => propagate('fieldEdited', event)}
						onFocus={(event) => propagate('fieldFocused', event)}
						onRemoveButton={() => dispatch('fieldRemoved', name)}
						onRepeatButton={() => dispatch('fieldRepeated', name)}
						pageIndex={pageIndex}
						readOnly={readOnly}
						repeatable={repeatable}
						rows={getRows(rows, nestedFields)}
						showLabel={showLabel}
						showRepeatableRemoveButton={
							repeatable && repeatedIndex > 0
						}
						spritemap={spritemap}
					/>
				</div>
			</FieldBaseProxy>
		);
	}
);

const ReactFieldSetAdapter = getConnectedReactComponentAdapter(
	FieldSetProxy,
	'fieldset'
);

export {ReactFieldSetAdapter};
export default ReactFieldSetAdapter;
