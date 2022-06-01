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

import CodeMirror from 'codemirror';
import React, {useMemo} from 'react';

import {Collapsible} from './Collapsible';
import {Element} from './Element';

import './EditorSidebar.scss';

export function EditorSidebar({
	defaultLanguageId,
	editorRef,
	metadataFields,
	sidebarElements,
}: IProps) {
	const onItemClick = (item: ObjectValidationRuleElementItem) =>
		editorRef.current?.replaceSelection(item.content);

	const metadata = metadataFields.map((metadata) => ({
		content: metadata.name,
		label: metadata.label[defaultLanguageId],
		tooltip: '',
	}));

	const objectFields = {...sidebarElements[0]};

	objectFields.items = useMemo(
		() => objectFields.items.concat(metadata),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	const elements = sidebarElements.filter((_, index) => index !== 0);

	elements.unshift(objectFields);

	return (
		<div className="lfr-objects__code-editor-sidebar">
			<div className="px-3">
				<h5 className="my-3">{Liferay.Language.get('elements')}</h5>

				{elements.map(({items, label}) => (
					<Collapsible key={label} label={label}>
						{items.map((item) => (
							<Element
								key={item.label}
								label={item.label}
								onClick={() => onItemClick(item)}
								tooltip={item.tooltip}
							/>
						))}
					</Collapsible>
				))}
			</div>
		</div>
	);
}
interface IProps {
	className?: string;
	defaultLanguageId: Locale;
	editorRef: React.MutableRefObject<CodeMirror.Editor | undefined>;
	metadataFields: any[];
	sidebarElements: ObjectValidationRuleElement[];
}
