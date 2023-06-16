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

import PropTypes from 'prop-types';
import React, {forwardRef} from 'react';

import BaseEditor from './BaseEditor';

const ClassicEditor = forwardRef(
	(
		{
			className,
			contents,
			editorConfig,
			initialToolbarSet = 'simple',
			name,
			title,
			...otherProps
		},
		ref
	) => {
		return (
			<div className={className} id={`${name}Container`}>
				{title && (
					<label className="control-label" htmlFor={name}>
						{title}
					</label>
				)}

				<BaseEditor
					className="lfr-editable"
					config={{
						toolbar: initialToolbarSet,
						...editorConfig,
					}}
					contents={contents}
					name={name}
					onBeforeLoad={(CKEDITOR) => {
						CKEDITOR.disableAutoInline = true;
						CKEDITOR.dtd.$removeEmpty.i = 0;
						CKEDITOR.dtd.$removeEmpty.span = 0;

						CKEDITOR.getNextZIndex = function () {
							return CKEDITOR.dialog._.currentZIndex
								? CKEDITOR.dialog._.currentZIndex + 10
								: Liferay.zIndex.WINDOW + 10;
						};
					}}
					onDrop={(event) => {
						const data = event.data.dataTransfer.getData(
							'text/html'
						);
						const editor = event.editor;

						if (data) {
							const fragment = CKEDITOR.htmlParser.fragment.fromHtml(
								data
							);

							const name = fragment.children[0].name;

							if (name) {
								return editor.pasteFilter.check(name);
							}
						}
					}}
					onInstanceReady={({editor}) => {
						editor.setData(contents, {
							callback: () => {
								editor.resetUndo();
							},
							noSnapshot: true,
						});
					}}
					ref={ref}
					{...otherProps}
				/>
			</div>
		);
	}
);

ClassicEditor.propTypes = {
	contents: PropTypes.string,
	editorConfig: PropTypes.object,
	initialToolbarSet: PropTypes.string,
	name: PropTypes.string,
	title: PropTypes.string,
};

export {ClassicEditor};
export default ClassicEditor;
