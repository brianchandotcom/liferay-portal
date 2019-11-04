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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useTimeout} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

const FieldMappings = ({
	fieldMappingIndexDisplayContexts,
	fieldMappings,
	selectedIndexName
}) => {
	const [theme, setTheme] = useState('light');

	const editorElementRef = useRef();
	const editorTextInputRef = useRef();

	const delay = useTimeout();

	const copyEditorText = event => {
		const editorTextInput = editorTextInputRef.current;

		editorTextInput.focus();
		editorTextInput.select();

		delay(() => {
			document.execCommand('copy');
		}, 0);

		event.currentTarget.dataset.title = Liferay.Language.get('copied');
	};

	const getEditorFontSize = () => {
		return parseInt(
			window
				.getComputedStyle(editorElementRef.current, null)
				.getPropertyValue('font-size'),
			10
		);
	};

	return (
		<div className="container-fluid container-fluid-max-xl container-form-lg portal-search-admin-field-mappings">
			<div className="row">
				<div className="col-md-3">
					<nav className="menubar menubar-transparent menubar-vertical-expand-lg">
						<ul className="nav nav-nested">
							<li className="nav-header">
								{Liferay.Language.get('index')}
							</li>

							{fieldMappingIndexDisplayContexts.map(item => {
								return (
									<li className="nav-item" key={item.name}>
										<a
											className={`${item.cssClass} nav-link`}
											href={`${item.url}`}
										>
											{item.name}
										</a>
									</li>
								);
							})}
						</ul>
					</nav>
				</div>

				<div className="col-md-9">
					<div className="sheet">
						<div className="sheet-header">
							<h2 className="sheet-title">{selectedIndexName}</h2>

							<div className="sheet-text">
								{Liferay.Language.get(
									'a-list-of-all-field-mappings-in-this-index'
								)}
							</div>
						</div>

						<div className="sheet-section">
							<ClayButton
								className="lfr-portal-tooltip"
								displayType="secondary"
								monospaced
								onClick={() => {
									setTheme(
										theme === 'light' ? 'dark' : 'light'
									);
								}}
								title={
									theme === 'light'
										? Liferay.Language.get('dark-theme')
										: Liferay.Language.get('light-theme')
								}
							>
								<ClayIcon symbol="moon" />
							</ClayButton>

							<ClayButton.Group className="btn-group-font-size">
								<ClayButton
									className="btn-plus lfr-portal-tooltip"
									displayType="secondary"
									monospaced
									onClick={() => {
										editorElementRef.current.style.fontSize =
											getEditorFontSize() + 2 + 'px';
									}}
									title={Liferay.Language.get('zoom-in')}
								>
									<ClayIcon symbol="plus" />
								</ClayButton>

								<ClayButton
									className="btn-hr lfr-portal-tooltip"
									displayType="secondary"
									monospaced
									onClick={() => {
										editorElementRef.current.style.fontSize =
											getEditorFontSize() - 2 + 'px';
									}}
									title={Liferay.Language.get('zoom-out')}
								>
									<ClayIcon symbol="hr" />
								</ClayButton>
							</ClayButton.Group>

							<ClayButton
								className="btn-copy lfr-portal-tooltip"
								displayType="secondary"
								monospaced
								onClick={copyEditorText}
								title={Liferay.Language.get('copy')}
							>
								<ClayIcon symbol="paste" />
							</ClayButton>
						</div>

						<div
							className={`${theme === 'dark' &&
								'ace_dark'} lfr-source-editor lfr-source-editor-content sheet-section`}
						>
							<AceEditor
								fieldMappings={fieldMappings}
								onRender={({
									editorElement,
									editorTextInput
								}) => {
									editorElementRef.current = editorElement;
									editorTextInputRef.current = editorTextInput;
								}}
							/>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

FieldMappings.propTypes = {
	fieldMappingIndexDisplayContexts: PropTypes.array,
	fieldMappings: PropTypes.string,
	selectedIndexName: PropTypes.string
};

export default function(props) {
	return <FieldMappings {...props} />;
}

class AceEditor extends React.Component {
	constructor(props) {
		super(props);

		this.container = React.createRef();
	}

	componentDidMount() {
		AUI().use('aui-ace-editor', A => {
			const editor = new A.AceEditor({
				boundingBox: this.container.current,
				highlightActiveLine: false,
				mode: 'json',
				readOnly: 'true',
				tabSize: 4,
				value: this.props.fieldMappings,
				width: '100%'
			}).render();

			editor.on('render', () => {
				this.props.onRender({
					editorElement: document.querySelector('.ace_editor'),
					editorTextInput: document.querySelector('.ace_text-input')
				});
			});
		});
	}

	shouldComponentUpdate() {
		return false;
	}

	render() {
		return (
			<div className="lfr-source-editor-code" ref={this.container}></div>
		);
	}
}
