/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

						if (!data) {
							return;
						}

						const fragment = CKEDITOR.htmlParser.fragment.fromHtml(
							data
						);

						let element = fragment.children[0];

						if (element.hasClass('cke_widget_image')) {
							element = element.children[0];
						}

						if (event.editor.pasteFilter && element.name) {
							return event.editor.pasteFilter.check(element.name);
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
