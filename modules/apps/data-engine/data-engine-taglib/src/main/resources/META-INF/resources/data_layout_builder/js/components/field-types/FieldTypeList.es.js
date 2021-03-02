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

import classNames from 'classnames';
import React from 'react';

import {getSearchRegex} from '../../utils/search.es';
import CollapsablePanel from '../collapsable-panel/CollapsablePanel.es';
import EmptyState from '../empty-state/EmptyState.es';
import FieldType from './FieldType.es';

const FieldTypeWrapper = ({expanded, fieldType, showArrows, ...otherProps}) => {
	const getIcon = () => {
		if (showArrows) {
			return expanded ? 'angle-down' : 'angle-right';
		}

		return fieldType.icon;
	};

	return <FieldType {...otherProps} {...fieldType} icon={getIcon()} />;
};

const FieldTypeList = ({
	dataDefinition,
	deleteLabel,
	emptyState,
	fieldTypes,
	keywords,
	onClick,
	onDelete,
	onDoubleClick,
	showEmptyState = true,
}) => {
	const regex = getSearchRegex(keywords);

	const filteredFieldTypes = fieldTypes.filter(
		({description, label, system}) => {
			if (system) {
				return false;
			}
			if (!keywords) {
				return true;
			}

			return regex.test(description) || regex.test(label);
		}
	);

	if (showEmptyState && !filteredFieldTypes.length) {
		return <EmptyState emptyState={emptyState} keywords={keywords} small />;
	}

	return filteredFieldTypes.map((fieldType, index) => {
		const {isFieldSet, nestedDataDefinitionFields = []} = fieldType;

		const handleOnClick = (props) => {
			if (fieldType.disabled || !onClick) {
				return;
			}

			onClick(props);
		};

		if (nestedDataDefinitionFields.length) {
			const Header = ({expanded, setExpanded}) => (
				<FieldTypeWrapper
					dataDefinition={dataDefinition}
					deleteLabel={deleteLabel}
					expanded={expanded}
					fieldType={{
						...fieldType,
						className: `${fieldType.className} field-type-header`,
					}}
					onClick={(props) => {
						setExpanded(!expanded);

						handleOnClick(props);
					}}
					onDelete={onDelete}
					onDoubleClick={onDoubleClick}
					setExpanded={setExpanded}
					showArrows
				/>
			);

			return (
				<div className="field-type-list" key={index}>
					<CollapsablePanel
						Header={Header}
						className={classNames({
							'field-type-fieldgroup': !isFieldSet,
							'field-type-fieldset': isFieldSet,
						})}
					>
						<div className="field-type-item position-relative">
							{nestedDataDefinitionFields.map(
								(nestedFieldType) => (
									<FieldTypeWrapper
										dataDefinition={dataDefinition}
										draggable={false}
										fieldType={{
											...nestedFieldType,
											disabled: fieldType.disabled,
										}}
										key={`${nestedFieldType.name}_${index}`}
									/>
								)
							)}
						</div>
					</CollapsablePanel>
				</div>
			);
		}

		return (
			<FieldTypeWrapper
				dataDefinition={dataDefinition}
				deleteLabel={deleteLabel}
				fieldType={fieldType}
				key={index}
				onClick={handleOnClick}
				onDelete={onDelete}
				onDoubleClick={onDoubleClick}
			/>
		);
	});
};

FieldTypeList.displayName = 'FieldTypeList';
export default FieldTypeList;
