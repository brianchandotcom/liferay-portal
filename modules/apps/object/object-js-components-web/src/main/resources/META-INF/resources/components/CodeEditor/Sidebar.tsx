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
import React from 'react';

import {Collapsible} from './Collapsible';
import {Element} from './Element';

import './Sidebar.scss';

interface SidebarElement {
	content: string;
	helpText: string;
	label: string;
}

export interface SidebarCategory {
	items: SidebarElement[];
	label: string;
}

type elementClickFunction = (item: SidebarElement) => void;

export interface CustomSidebarContentProps {
	elements?: SidebarCategory[];
	handleElementClick: elementClickFunction;
}

interface IProps {
	CustomSidebarContent?: (
		props: CustomSidebarContentProps
	) => React.ReactNode;
	editorRef: React.RefObject<CodeMirror.Editor>;
	elements?: SidebarCategory[];
}

export function Sidebar({CustomSidebarContent, editorRef, elements}: IProps) {
	const handleClick = (item: SidebarElement) =>
		editorRef.current?.replaceSelection(item.content);

	return (
		<div className="lfr-objects__code-editor-sidebar">
			<div className="px-3">
				<h5 className="my-3">{Liferay.Language.get('elements')}</h5>

				{CustomSidebarContent &&
					CustomSidebarContent({
						elements,
						handleElementClick: handleClick,
					})}

				{!CustomSidebarContent &&
					elements?.map(({items, label}) => (
						<Collapsible key={label} label={label}>
							{items.map((item) => (
								<Element
									helpText={item.helpText}
									key={item.label}
									label={item.label}
									onClick={() => handleClick(item)}
								/>
							))}
						</Collapsible>
					))}
			</div>
		</div>
	);
}
